package linc.fun.dormitory.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.service.StatisticalService;
import linc.fun.dormitory.vo.StatisticalVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author yqlin
 * @date 2022/4/17 00:30
 * @description
 */
@ResponseResult
@RequestMapping("/api/statistical")
@Api(tags = "StatisticalController", description = "统计管理")
public class StatisticalController {
    @Resource
    private StatisticalService service;

    @TokenCheck
    @ApiOperation("获取统计信息")
    @GetMapping("/get")
    public StatisticalVO getStatistical() {
        return service.getStatistical();
    }
}
