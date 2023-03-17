package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.ProblemBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.ProblemAuditForm;
import linc.fun.dormitory.form.ProblemForm;
import linc.fun.dormitory.po.Problem;
import linc.fun.dormitory.query.ProblemQuery;
import linc.fun.dormitory.service.ProblemService;
import linc.fun.dormitory.vo.ProblemVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/16 08:12
 * @description
 */
@ResponseResult
@RequestMapping("/api/problem")
@Api(tags = "ProblemController", description = "问题管理")
public class ProblemController {


    @Resource
    private ProblemService service;
    @Resource
    private SysConverter converter;


    @TokenCheck
    @ApiOperation("根据分页查询问题列表")
    @GetMapping("/get/page")
    public IPage<ProblemVO> getPage(Page<Problem> page, ProblemQuery query) {
        IPage<ProblemBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToProblemVO);
    }

    @TokenCheck
    @ApiOperation("添加/修改问题")
    @PostMapping
    public void saveOrUpdate(@RequestBody @Validated ProblemForm form) {
        service.toSave(converter.convertToProblem(form));
    }

    @TokenCheck
    @ApiOperation("根据id删除问题")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }


    @TokenCheck
    @ApiOperation("根据id审核问题")
    @PutMapping("/audit")
    public void audit(@RequestBody @Validated ProblemAuditForm form) {
        service.audit(form);
    }



    @TokenCheck
    @ApiOperation("根据分页查询我的问题列表")
    @GetMapping("/get/my/page")
    public IPage<ProblemVO> getMyPage(Page<Problem> page, ProblemQuery query) {
        IPage<ProblemBO> iPage = service.getMyPage(page, query);
        return iPage.convert(converter::convertToProblemVO);
    }



    @TokenCheck
    @ApiOperation("获取最近的问题信息")
    @GetMapping("/get/recent/{nums}")
    public List<ProblemVO> getRecent(@PathVariable Integer nums) {
        return service.getRecent(nums);
    }

}
