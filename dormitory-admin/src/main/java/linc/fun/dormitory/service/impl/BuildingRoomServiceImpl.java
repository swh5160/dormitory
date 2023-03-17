package linc.fun.dormitory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import linc.fun.dormitory.bo.BuildingRoomBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.exception.BizException;
import linc.fun.dormitory.form.BuildingRoomForm;
import linc.fun.dormitory.form.RoomBedPlaceHolderForm;
import linc.fun.dormitory.mapper.BuildingMapper;
import linc.fun.dormitory.mapper.RoomBedMapper;
import linc.fun.dormitory.mapper.StudentMapper;
import linc.fun.dormitory.po.Building;
import linc.fun.dormitory.po.RoomBed;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.BuildingRoomQuery;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.mapper.BuildingRoomMapper;
import linc.fun.dormitory.service.BuildingRoomService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
@Service
public class BuildingRoomServiceImpl extends ServiceImpl<BuildingRoomMapper, BuildingRoom>
        implements BuildingRoomService {
    @Resource
    private BuildingMapper buildingMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private RoomBedMapper roomBedMapper;
    @Resource
    private SysConverter converter;


    @Override
    public IPage<BuildingRoomBO> getPage(Page<BuildingRoomBO> page, BuildingRoomQuery query) {
        // 分页查询
        return baseMapper.selectByPage(page, query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void toSave(BuildingRoomForm form) {
        Long buildingId = form.getBuildingId();
        // 根据id查询楼宇信息
        Building building = buildingMapper.selectById(buildingId);
        if (Objects.isNull(building)) {
            throw new BizException("当前楼宇信息不存在");
        }
        // 转换实体
        BuildingRoom buildingRoom = converter.convertToBuildingRoom(form);
        buildingRoom.setBno(building.getBno());
        // 保存
        this.saveOrUpdate(buildingRoom);
        // 批量生成床
        List<RoomBed> roomBeds = doGenerateBed(building, buildingRoom);
        roomBedMapper.insertList(roomBeds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void placeholderBed(RoomBedPlaceHolderForm form) {
        // 判断学号是否存在
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getSno, form.getSno()));
        if (Objects.isNull(student)) {
            throw new BizException("当前学号不存在");
        }
        // 根据id查询床位
        RoomBed roomBed = roomBedMapper.selectById(form.getRoomBedId());
        if (Objects.isNull(roomBed)) {
            throw new BizException("当前床位不存在");
        }
        // 判断学号是否已经占用了
        Long count = roomBedMapper.selectCount(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getStudentId, student.getId()));
        if (count == 1) {
            throw new BizException("当前学号已存在床位,请不要重复占位");
        }
        // 学生占位
        roomBed.setStudentId(student.getId());
        roomBed.setSno(student.getSno());
        // 更新床位信息
        roomBedMapper.updateById(roomBed);
        // 根据id查询房间信息
        BuildingRoom buildingRoom = baseMapper.selectById(roomBed.getBuildingRoomId());
        if (Objects.isNull(buildingRoom)) {
            throw new BizException("当前房间不存在");
        }
        // 更新当前入住人数,当前入住人数+1
        buildingRoom.setCurrentOccupancy(buildingRoom.getCurrentOccupancy() + 1);
        baseMapper.updateById(buildingRoom);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clearPlaceholderBed(Long roomBedId) {
        // 根据id查询床位
        RoomBed roomBed = roomBedMapper.selectById(roomBedId);
        if (Objects.isNull(roomBed)) {
            throw new BizException("当前床位不存在");
        }
        // 清空roomBed的学生相关信息
        roomBed.setStudentId(null);
        roomBed.setSno(null);
        roomBedMapper.updateById(roomBed);
        // 根据id查询房间信息
        BuildingRoom buildingRoom = baseMapper.selectById(roomBed.getBuildingRoomId());
        if (Objects.isNull(buildingRoom)) {
            throw new BizException("当前房间不存在");
        }
        // 更新当前入住人数,当前入住人数+1
        buildingRoom.setCurrentOccupancy(buildingRoom.getCurrentOccupancy() - 1);
        baseMapper.updateById(buildingRoom);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        // 判断下面有没有正在入住的学生,如果存在就不能进行删除
        Long count = roomBedMapper.selectCount(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getBuildingRoomId, id).isNotNull(RoomBed::getStudentId));
        if (count > 0) {
            throw new BizException("当前房间存在学生入住,不允许删除");
        }
        // 没有学生入住,就可以进行删除
        // 需要删除所有房间信息以及对应的床位信息
        roomBedMapper.delete(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getBuildingRoomId, id));
        baseMapper.deleteById(id);
    }


    private List<RoomBed> doGenerateBed(Building building, BuildingRoom buildingRoom) {
        buildingRoom.setBno(building.getBno());
        // 拿到最大入住人数
        Integer maxOccupancy = buildingRoom.getMaxOccupancy();
        List<RoomBed> roomBeds = Lists.newArrayListWithCapacity(maxOccupancy);
        for (int i = 1; i <= maxOccupancy; i++) {
            RoomBed roomBed = new RoomBed();
            roomBed.setBuildingRoomId(buildingRoom.getId());
            roomBed.setName(i + "号床");
            // 一号楼: 3201
            roomBed.setRemark(building.getBno() + ": " + buildingRoom.getRno());
            roomBeds.add(roomBed);
        }
        return roomBeds;
    }
}
