package linc.fun.dormitory.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import linc.fun.dormitory.bo.StudentAttendanceBO;
import linc.fun.dormitory.bo.StudentBO;
import linc.fun.dormitory.constants.RedisKeyConstants;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.dto.TokenDTO;
import linc.fun.dormitory.enums.RoleEnum;
import linc.fun.dormitory.exception.BizException;
import linc.fun.dormitory.form.StudentApplyBedForm;
import linc.fun.dormitory.form.StudentUpdatePasswordForm;
import linc.fun.dormitory.mapper.BuildingRoomMapper;
import linc.fun.dormitory.mapper.RoomBedMapper;
import linc.fun.dormitory.mapper.StudentMapper;
import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.po.RoomBed;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.StudentAttendanceQuery;
import linc.fun.dormitory.query.StudentQuery;
import linc.fun.dormitory.service.StudentService;
import linc.fun.dormitory.util.DateUtils;
import linc.fun.dormitory.util.JwtUtils;
import linc.fun.dormitory.util.Md5Utils;
import linc.fun.dormitory.util.ServletUtils;
import linc.fun.dormitory.vo.StudentClockInVO;
import linc.fun.dormitory.vo.StudentSelfInfoVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private SysConverter converter;
    @Resource
    private RoomBedMapper roomBedMapper;
    @Resource
    private BuildingRoomMapper buildingRoomMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 签到缓存Map
     */
    private Map<String, StudentClockInVO> signMap = Maps.newConcurrentMap();

    @Override
    public IPage<StudentBO> getPage(Page<StudentBO> page, StudentQuery query) {
        // 分页查询
        return this.baseMapper.selectByPage(page, query);
    }

    @Override
    public StudentSelfInfoVO getSelfInfo() {
        // 从head中解析出token
        String token = JwtUtils.getToken(Objects.requireNonNull(ServletUtils.getRequest()));
        // 判断token是否为空
        if (StringUtils.isEmpty(token)) {
            throw new BizException("令牌失效");
        }
        // 从token中拿出payload
        TokenDTO payload = JwtUtils.getPayload(token);
        Long id = payload.getId();
        // 判断是不是学生的token
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            // 查询出当前学生的信息
            Student student = this.getById(id);
            // 查询出当前学生所在宿舍楼以及宿舍相关信息
            RoomBed roomBed = roomBedMapper.selectOne(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getStudentId, id));
            if (Objects.nonNull(roomBed)) {
                // 查出所在房间的所有床位信息
                List<RoomBed> roomBeds = roomBedMapper.selectList(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getBuildingRoomId, roomBed.getBuildingRoomId()));
                // 查出个人房间信息
                BuildingRoom buildingRoom = buildingRoomMapper.selectById(roomBed.getBuildingRoomId());
                StudentSelfInfoVO vo = converter.convertToStudentSelfInfoVO(student);
                vo.setBuildingRoom(buildingRoom);
                vo.setMyRoomBed(roomBed);
                vo.setRoomBeds(roomBeds);
                return vo;
            }

        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void applyBed(StudentApplyBedForm form) {
        // 判断是否已经占位了宿舍
        RoomBed roomBed = roomBedMapper.selectOne(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getSno, form.getSno()));
        if (Objects.nonNull(roomBed)) {
            throw new BizException("当前学生已经入住了,不要重复申请");
        }
        Student student = this.getOne(new LambdaQueryWrapper<Student>().eq(Student::getSno, form.getSno()));
        if (Objects.isNull(student)) {
            throw new BizException("当前学生不存在");
        }
        roomBed = roomBedMapper.selectById(form.getRoomBedId());
        if (Objects.isNull(roomBed)) {
            throw new BizException("当前房间不存在");
        }
        if (Objects.nonNull(roomBed.getStudentId())) {
            throw new BizException("当前房间已经被占用,不能申请");
        }
        roomBed.setSno(student.getSno());
        roomBed.setStudentId(student.getId());
        BuildingRoom buildingRoom = buildingRoomMapper.selectById(roomBed.getBuildingRoomId());
        buildingRoom.setCurrentOccupancy(buildingRoom.getCurrentOccupancy() + 1);
        // 更新床位信息
        roomBedMapper.updateById(roomBed);
        // 更新房间信息
        buildingRoomMapper.updateById(buildingRoom);
    }


    @SneakyThrows
    @Override
    public void clockIn() {
        TokenDTO payload = getTokenDTO();
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            String keyForStudentId = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, DateUtils.getDate());
            // STUDENT_SIGN_IN_KEY_2022-04-16 -> key_<日期>
            // 用于统计每个人当日是否打卡  id作为偏移量
            stringRedisTemplate.opsForValue().setBit(keyForStudentId, payload.getId(), true);
            //  2022-04-16 21:10:15
            DateTime date = DateUtil.date();
            // 2022
            int year = DateUtil.year(date);
            String keyForStudentYear = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, String.valueOf(year), String.valueOf(payload.getId()));
            // STUDENT_SIGN_IN_KEY_2022_1 -> key_<年份>_<studentId> 用于记录学生今年的打卡记录
            stringRedisTemplate.opsForValue().setBit(keyForStudentYear, DateUtil.dayOfYear(date), true);
            // 如果当天签到了就得去更新缓存
            if (signMap.containsKey(keyForStudentYear)) {
                String dateStr = year - 1 + "-12-31";
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                Date beginDate = sd.parse(dateStr);
                // 从缓存中拿出当前学生的打卡信息
                StudentClockInVO studentClockInVO = signMap.get(keyForStudentYear);
                // 添加一条缓存
                DateTime dateTime = DateUtil.offset(beginDate, DateField.DAY_OF_YEAR, DateUtil.dayOfYear(date));
                List<StudentClockInVO.ClockIn> clockInfo = studentClockInVO.getClockInfo();
                // 加上新的缓存记录
                clockInfo.add(new StudentClockInVO.ClockIn(sd.format(dateTime), true));
            }
        }
    }

    @SneakyThrows
    @Override
    public StudentClockInVO getClockInfo() {
        TokenDTO payload = getTokenDTO();
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            //  2022-04-16 21:10:15
            DateTime date = DateUtil.date();
            int year = DateUtil.year(date);
            // STUDENT_SIGN_IN_KEY_2022_1 -> key_<年份>_<studentId> 用于记录学生今年的打卡记录
            String keyForStudentYear = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, String.valueOf(year), String.valueOf(payload.getId()));
            // 从本地缓存中拿,如果存在
            if (signMap.containsKey(keyForStudentYear)) return signMap.get(keyForStudentYear);
            String keyInfo = stringRedisTemplate.opsForValue().get(keyForStudentYear);
            StudentClockInVO vo = new StudentClockInVO();
            // 目前没有进行过打卡
            if (StringUtils.isEmpty(keyInfo)) {
                return vo;
            }
            // 现在是今年的第几天
            int dayOfYear = DateUtil.dayOfYear(date);
            // 设置上次打卡之前的总天数
            List<StudentClockInVO.ClockIn> clockInfo = Lists.newArrayListWithCapacity(dayOfYear);
            // 2022
            String dateStr = year - 1 + "-12-31";
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = sd.parse(dateStr);
            // 存入打卡信息
            for (int i = 1; i <= dayOfYear; i++) {
                DateTime dateTime = DateUtil.offset(beginDate, DateField.DAY_OF_YEAR, i);
                // 是否已经打卡
                Boolean clock = stringRedisTemplate.opsForValue().getBit(keyForStudentYear, i);
                StudentClockInVO.ClockIn clockIn = new StudentClockInVO.ClockIn(sd.format(dateTime), clock);
                clockInfo.add(clockIn);
            }
            vo.setClockInfo(clockInfo);
            signMap.put(keyForStudentYear, vo);
            return vo;
        }
        return null;
    }


    @Override
    public Boolean checkClockIn() {
        TokenDTO payload = getTokenDTO();
        // 判断当前是否是学生
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            String keyForStudentId = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, DateUtils.getDate());
            if (Objects.isNull(stringRedisTemplate.opsForValue().get(keyForStudentId))) {
                return false;
            }
            // 是否打卡
            return stringRedisTemplate.opsForValue().getBit(keyForStudentId, payload.getId());
        }
        return false;
    }

    @Override
    public IPage<StudentAttendanceBO> getAttendancePage(Page<StudentAttendanceBO> page, StudentAttendanceQuery query) {
        String keyForStudentId = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, DateUtils.getDate());
        // 分页查询
        IPage<StudentAttendanceBO> iPage = baseMapper.selectStudentAttendanceBOByPage(page, query);
        // 批量处理数据
        iPage.getRecords().forEach(record -> {
            Boolean isClock = stringRedisTemplate.opsForValue().getBit(keyForStudentId, record.getId());
            record.setClock(isClock);
        });
        // 查询是否打卡
        if (Objects.nonNull(query) && Objects.nonNull(query.getClock())) {
            List<StudentAttendanceBO> queriedRecords = iPage.getRecords().stream().filter(record -> record.getClock().equals(query.getClock())).collect(Collectors.toList());
            iPage.setRecords(queriedRecords);
            iPage.setTotal(queriedRecords.size());
        }
        return iPage;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(StudentUpdatePasswordForm form) {
        TokenDTO payload = getTokenDTO();
        // 判断当前是否是学生
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            Student student = this.getById(payload.getId());
            if (Objects.isNull(student)) {
                throw new BizException("当前学生不存在");
            }
            String prePassword = student.getPassword();
            // 和旧密码匹配
            if (!prePassword.equals(Md5Utils.toMD5(form.getOldPassword()))) {
                throw new BizException("旧密码错误");
            }
            // 旧密码和新密码是否相同
            if (prePassword.equals(Md5Utils.toMD5(form.getNewPassword()))) {
                throw new BizException("新密码不能与旧密码相同");
            }
            student.setPassword(Md5Utils.toMD5(form.getNewPassword()));
            this.updateById(student);
        }


    }

    @Override
    public void removeStudent(Long id) {
        // 如果学生已经入住了就不能进行删除
        RoomBed roomBed = roomBedMapper.selectById(id);
        if (Objects.nonNull(roomBed)) {
            throw new BizException("当前学生已经入住,暂时无法删除");
        }
        this.removeById(id);
    }


    /**
     * 解析token
     */
    private TokenDTO getTokenDTO() {
        // 从head中解析出token
        String token = JwtUtils.getToken(Objects.requireNonNull(ServletUtils.getRequest()));
        // 判断token是否为空
        if (StringUtils.isEmpty(token)) {
            throw new BizException("令牌失效");
        }
        // 从token中拿出payload
        return JwtUtils.getPayload(token);
    }


    @SneakyThrows
    public static void main(String[] args) {
        DateTime date = DateUtil.date();
        System.out.println(DateUtil.dayOfYear(date));
        System.out.println(date);
        System.out.println(DateUtil.year(date));
        System.out.println(RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, DateUtils.getDate()));
        System.out.println(DateUtil.current());


        int lengthOfYear = DateUtil.lengthOfYear(2022);
        String dateStr = 2022 - 1 + "-12-31";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sd.parse(dateStr);
        for (int i = 1; i <= lengthOfYear; i++) {
            DateTime dateTime = DateUtil.offset(beginDate, DateField.DAY_OF_YEAR, i);
            String format = sd.format(dateTime);
            System.out.println(format);
        }
    }
}
