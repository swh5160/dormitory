package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.StudentAttendanceBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.query.StudentAttendanceQuery;
import linc.fun.dormitory.service.StudentService;
import linc.fun.dormitory.vo.StudentAttendanceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author yqlin
 * @date 2022/4/16 22:33
 * @description
 */
@ResponseResult
@RequestMapping("/api/attendance")
@Api(tags = "AttendanceController", description = "考勤管理")
public class AttendanceController {
    @Resource
    private StudentService service;
    @Resource
    private SysConverter converter;

    @TokenCheck
    @ApiOperation("查询考勤信息")
    @GetMapping("/get/page")
    public IPage<StudentAttendanceVO> getAttendancePage(Page<StudentAttendanceBO> page, StudentAttendanceQuery query) {
        IPage<StudentAttendanceBO> iPage = service.getAttendancePage(page, query);
        return iPage.convert(converter::convertToStudentAttendanceVO);
    }

}
