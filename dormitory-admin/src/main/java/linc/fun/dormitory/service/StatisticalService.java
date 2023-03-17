package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import linc.fun.dormitory.bo.StudentAttendanceBO;
import linc.fun.dormitory.constants.RedisKeyConstants;
import linc.fun.dormitory.mapper.BuildingRoomMapper;
import linc.fun.dormitory.mapper.StudentMapper;
import linc.fun.dormitory.query.StudentAttendanceQuery;
import linc.fun.dormitory.util.BitsOpUtils;
import linc.fun.dormitory.util.DateUtils;
import linc.fun.dormitory.vo.BuildingRoomInfoVO;
import linc.fun.dormitory.vo.StatisticalVO;
import linc.fun.dormitory.vo.StudentAccommodationInfoVO;
import linc.fun.dormitory.vo.StudentClockInfoVO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisClusterCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/17 00:46
 * @description
 */
@Service
public class StatisticalService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BuildingRoomMapper buildingRoomMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private BitsOpUtils bitsOpUtils;

    public StatisticalVO getStatistical() {
        // 学生打卡信息
        StudentClockInfoVO studentClockInfo = new StudentClockInfoVO();
        // 宿舍信息
        StudentAccommodationInfoVO studentAccommodationInfo = new StudentAccommodationInfoVO();
        // 查询学生总人数
        Long studentCount = studentMapper.selectCount(null);
        studentClockInfo.setAllCount(Math.toIntExact(studentCount));
        studentAccommodationInfo.setAllCount(Math.toIntExact(studentCount));
        // 学生住宿信息
        BuildingRoomInfoVO buildingRoomInfo = buildingRoomMapper.selectAccommodationInfo();
        studentAccommodationInfo.setAccommodationCount(buildingRoomInfo.getAllCount() - buildingRoomInfo.getEmptyCount());
        // 从Redis中读出
        String keyForStudentId = RedisKeyConstants.generateKeyConstantWithSuffix(RedisKeyConstants.STUDENT_SIGN_IN_KEY, DateUtils.getDate());
        // 统计学生签到人数
        Long clockCount = bitsOpUtils.countBits(keyForStudentId);
        if (Objects.isNull(clockCount)) clockCount = 0L;
        studentClockInfo.setClockCount(Math.toIntExact(clockCount));
        return new StatisticalVO(studentClockInfo, studentAccommodationInfo, buildingRoomInfo);
    }
}
