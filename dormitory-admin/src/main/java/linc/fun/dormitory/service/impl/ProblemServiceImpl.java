package linc.fun.dormitory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import linc.fun.dormitory.bo.ProblemBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.dto.TokenDTO;
import linc.fun.dormitory.enums.RoleEnum;
import linc.fun.dormitory.exception.BizException;
import linc.fun.dormitory.form.ProblemAuditForm;
import linc.fun.dormitory.mapper.BuildingRoomMapper;
import linc.fun.dormitory.mapper.ProblemMapper;
import linc.fun.dormitory.mapper.RoomBedMapper;
import linc.fun.dormitory.mapper.StudentMapper;
import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.po.Problem;
import linc.fun.dormitory.po.RoomBed;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.ProblemQuery;
import linc.fun.dormitory.service.ProblemService;
import linc.fun.dormitory.util.JwtUtils;
import linc.fun.dormitory.util.ServletUtils;
import linc.fun.dormitory.vo.ProblemVO;
import org.springframework.stereotype.Service;
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
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
        implements ProblemService {
    @Resource
    private SysConverter converter;
    @Resource
    private RoomBedMapper roomBedMapper;
    @Resource
    private BuildingRoomMapper buildingRoomMapper;
    @Resource
    private StudentMapper studentMapper;

    @Override
    public IPage<ProblemBO> getPage(Page<Problem> page, ProblemQuery query) {
        Page<Problem> problemPage = getPageLambdaQuery(query)
                .orderBy(true, false, List.of(Problem::getGmtModified))
                .page(page);
        return problemPage.convert(converter::convertToProblemBO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void toSave(Problem problem) {
        TokenDTO payload = getTokenDTO();
        Long id = payload.getId();
        // 判断是不是学生的token
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            // 查询学生信息
            Student student = studentMapper.selectById(id);
            // 查询床位信息
            RoomBed roomBed = roomBedMapper.selectOne(new LambdaQueryWrapper<RoomBed>().eq(RoomBed::getStudentId, id));
            if (Objects.isNull(roomBed)) {
                throw new BizException("您还未入住,无法发布问题");
            }
            // 查询房间信息
            BuildingRoom buildingRoom = buildingRoomMapper.selectById(roomBed.getBuildingRoomId());
            // 设置缺省信息
            problem.setStudentId(id);
            problem.setStudentName(student.getName());
            problem.setBuildingId(buildingRoom.getBuildingId());
            problem.setBno(buildingRoom.getBno());
            problem.setBuildingRoomId(buildingRoom.getId());
            problem.setRno(buildingRoom.getRno());
            // 默认未通过
            problem.setPass(false);
            this.saveOrUpdate(problem);
        }
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void audit(ProblemAuditForm form) {
        Problem problem = this.getById(form.getId());
        if (Objects.isNull(problem)) {
            throw new BizException("当前问题不存在");
        }
        problem.setPass(form.getPass());
        problem.setReply(form.getReply());
        this.updateById(problem);
    }

    @Override
    public IPage<ProblemBO> getMyPage(Page<Problem> page, ProblemQuery query) {
        TokenDTO payload = getTokenDTO();
        Long id = payload.getId();
        Page<Problem> problemPage;
        // 判断是不是学生token
        if (RoleEnum.STUDENT.equals(payload.getRoleEnum())) {
            problemPage = getPageLambdaQuery(query)
                    .eq(Problem::getStudentId, id)
                    .orderBy(true, false, List.of(Problem::getGmtModified))
                    .page(page);
            return problemPage.convert(converter::convertToProblemBO);
        }
        return null;
    }

    @Override
    public List<ProblemVO> getRecent(Integer nums) {
        return baseMapper.selectByNums(nums);
    }

    /**
     * 查询条件
     */
    private LambdaQueryChainWrapper<Problem> getPageLambdaQuery(ProblemQuery query) {
        return lambdaQuery()
                .like(StringUtils.checkValNotNull(query.getStudentName()), Problem::getStudentName, query.getStudentName())
                .like(StringUtils.checkValNotNull(query.getBno()), Problem::getBno, query.getBno())
                .like(StringUtils.checkValNotNull(query.getRno()), Problem::getRno, query.getRno())
                .like(StringUtils.checkValNotNull(query.getTitle()), Problem::getTitle, query.getTitle())
                .like(StringUtils.checkValNotNull(query.getType()), Problem::getType, query.getType())
                .eq(StringUtils.checkValNotNull(query.getPass()), Problem::getPass, query.getPass());
    }
}
