package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.bo.StudentAttendanceBO;
import linc.fun.dormitory.bo.StudentBO;
import linc.fun.dormitory.form.StudentApplyBedForm;
import linc.fun.dormitory.form.StudentUpdatePasswordForm;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.StudentAttendanceQuery;
import linc.fun.dormitory.query.StudentQuery;
import linc.fun.dormitory.vo.StudentClockInVO;
import linc.fun.dormitory.vo.StudentSelfInfoVO;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface StudentService extends IService<Student>{


    IPage<StudentBO> getPage(Page<StudentBO> page, StudentQuery query);

    StudentSelfInfoVO getSelfInfo();

    void applyBed(StudentApplyBedForm form);

    void clockIn();

    StudentClockInVO getClockInfo();

    Boolean checkClockIn();

    IPage<StudentAttendanceBO> getAttendancePage(Page<StudentAttendanceBO> page, StudentAttendanceQuery query);


    void updatePassword(StudentUpdatePasswordForm form);

    void removeStudent(Long id);
}
