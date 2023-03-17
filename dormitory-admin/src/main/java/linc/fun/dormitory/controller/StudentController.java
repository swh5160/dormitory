package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.StudentBO;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.StudentApplyBedForm;
import linc.fun.dormitory.form.StudentForm;
import linc.fun.dormitory.form.StudentUpdatePasswordForm;
import linc.fun.dormitory.form.StudentUpdateSelfForm;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.query.StudentQuery;
import linc.fun.dormitory.service.StudentService;
import linc.fun.dormitory.util.Md5Utils;
import linc.fun.dormitory.vo.StudentClockInVO;
import linc.fun.dormitory.vo.StudentSelfInfoVO;
import linc.fun.dormitory.vo.StudentVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/15 17:02
 * @description
 */
@ResponseResult
@RequestMapping("/api/student")
@Api(tags = "StudentController", description = "学生管理")
public class StudentController {
    @Resource
    private StudentService service;
    @Resource
    private SysConverter converter;


    @TokenCheck
    @ApiOperation("根据分页查询学生列表")
    @GetMapping("/get/page")
    public IPage<StudentVO> getPage(Page<StudentBO> page, StudentQuery query) {
        IPage<StudentBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToStudentVO);
    }

    @TokenCheck
    @ApiOperation("添加/修改学生")
    @PostMapping
    public void saveOrUpdate(@RequestBody @Validated StudentForm form) {
        // 设置初始化密码
        if (Objects.isNull(form.getId())) {
            form.setPassword(Md5Utils.toMD5(CommonConstants.STUDENT_INIT_PASSWORD));
            form.setAvatar(CommonConstants.INIT_AVATAR);
        }
        // 转换为student
        Student student = converter.convertToStudent(form);
        student.setUsername(student.getSno());
        service.saveOrUpdate(student);
    }


    @TokenCheck
    @ApiOperation("根据id删除学生")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeStudent(id);
    }

    @TokenCheck
    @ApiOperation("根据id查询学生")
    @GetMapping("/{id}")
    public StudentVO getById(@PathVariable Long id) {
        return converter.convertToStudentVO(service.getById(id));
    }


    @TokenCheck
    @ApiOperation("根据id查询个人信息")
    @GetMapping("/self/info")
    public StudentSelfInfoVO getSelfInfo() {
        return service.getSelfInfo();
    }


    @TokenCheck
    @ApiOperation("申请床位")
    @PostMapping("/apply/bed")
    public void applyBed(@RequestBody @Validated StudentApplyBedForm form) {
        service.applyBed(form);
    }


    @TokenCheck
    @ApiOperation("学生打卡")
    @PostMapping("/clock/in")
    public void clockIn() {
        service.clockIn();
    }

    @TokenCheck
    @ApiOperation("获取学生打卡信息")
    @GetMapping("/get/clock/info")
    public StudentClockInVO getClockInfo() {
        return service.getClockInfo();
    }


    @TokenCheck
    @ApiOperation("判断当天学生是否打卡")
    @GetMapping("/check/clock")
    public Boolean checkClockIn() {
        return service.checkClockIn();
    }


    @TokenCheck
    @ApiOperation("修改个人信息")
    @PutMapping("/update/self")
    public void updateSelf(@RequestBody @Validated StudentUpdateSelfForm form) {
        Student student = converter.convertToStudent(form);
        student.setId(form.getStudentId());
        service.updateById(student);
    }

    @TokenCheck
    @ApiOperation("修改个人信息")
    @PutMapping("/update/password")
    public void updatePassword(@RequestBody @Validated StudentUpdatePasswordForm form) {
        service.updatePassword(form);
    }

}
