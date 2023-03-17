package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.StudentAttendanceBO;
import linc.fun.dormitory.bo.StudentBO;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.StudentAttendanceQuery;
import linc.fun.dormitory.query.StudentQuery;
import org.apache.ibatis.annotations.Param;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface StudentMapper extends BaseMapper<Student> {
    IPage<StudentBO> selectByPage(Page<StudentBO> page, @Param("query") StudentQuery query);

    IPage<StudentAttendanceBO> selectStudentAttendanceBOByPage(Page<StudentAttendanceBO> page, @Param("query") StudentAttendanceQuery query);
}