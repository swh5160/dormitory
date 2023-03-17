package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.AdminBO;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.AdminForm;
import linc.fun.dormitory.po.Admin;
import linc.fun.dormitory.query.AdminQuery;
import linc.fun.dormitory.service.AdminService;
import linc.fun.dormitory.util.Md5Utils;
import linc.fun.dormitory.vo.AdminVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 管理控制层
 */
@ResponseResult 
@RequestMapping("/api/admin")
@Api(tags = "AdminController", description = "管理员管理")
public class AdminController {
    @Resource
    private AdminService service;
    @Resource
    private SysConverter converter;


    @TokenCheck
    @ApiOperation("根据分页查询管理员列表")
    @GetMapping("/get/page")
    public IPage<AdminVO> getPage(Page<Admin> page, AdminQuery query) {
        IPage<AdminBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToAdminVO);
    }

    @TokenCheck
    @ApiOperation("添加/修改管理员")
    @PostMapping
    public void saveOrUpdate(@RequestBody @Validated AdminForm form) {
        // 设置初始化密码
        if (Objects.isNull(form.getId())) {
            form.setPassword(Md5Utils.toMD5(CommonConstants.SUPER_ADMIN_INIT_PASSWORD));
            form.setAvatar(CommonConstants.INIT_AVATAR);
        }
        service.saveOrUpdate(converter.convertToAdmin(form));
    }

    @TokenCheck
    @ApiOperation("根据id删除管理员")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
