package linc.fun.dormitory.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.dto.LoginDTO;
import linc.fun.dormitory.service.LoginService;
import linc.fun.dormitory.vo.CaptchaVO;
import linc.fun.dormitory.vo.TokenVO;
import linc.fun.dormitory.vo.UserInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author yqlin
 * @date 2022/4/15 01:00
 * @description 登录控制层
 */
@ResponseResult
@RequestMapping("/api/auth")
@Api(tags = "LoginController", description = "登录管理")
public class AuthController {
    @Resource
    private LoginService service;

    @TokenCheck(required = false)
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public TokenVO login(@RequestBody @Validated LoginDTO dto) {
        return service.login(dto);
    }


    @TokenCheck
    @ApiOperation("获取用户信息")
    @GetMapping("/get/info")
    public UserInfoVO getInfo() {
        return service.getInfo();
    }


    @TokenCheck(required = false)
    @ApiOperation("获取图片验证码")
    @GetMapping("/get/captcha")
    public CaptchaVO getCaptcha() {
        return service.getCaptcha();
    }
}
