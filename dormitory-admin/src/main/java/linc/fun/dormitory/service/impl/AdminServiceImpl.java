package linc.fun.dormitory.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import linc.fun.dormitory.bo.AdminBO;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.dto.TokenDTO;
import linc.fun.dormitory.exception.BizException;
import linc.fun.dormitory.mapper.AdminMapper;
import linc.fun.dormitory.po.Admin;
import linc.fun.dormitory.query.AdminQuery;
import linc.fun.dormitory.service.AdminService;
import linc.fun.dormitory.util.JwtUtils;
import linc.fun.dormitory.util.ServletUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {
    @Resource
    private SysConverter converter;

    @Override
    public IPage<AdminBO> getPage(Page<Admin> page, AdminQuery query) {
        HttpServletRequest request = ServletUtils.getRequest();
        TokenDTO tokenDTO = JwtUtils.getPayloadOfRequest(request);
        IPage<Admin> adminPage;
        // 超级管理员可以查询出自己
        if (CommonConstants.SUPER_ADMIN_ID.equals(tokenDTO.getId())) {
            // SELECT id,username,`password`,`name`,gender,age,phone,email,avatar,gmt_create,gmt_modified FROM `admin` ORDER BY gmt_modified DESC LIMIT ?
            // 分页查询
            adminPage = lambdaQuery()
                    .like(StringUtils.isNotBlank(query.getUsername()), Admin::getUsername, query.getUsername())
                    .like(StringUtils.isNotBlank(query.getName()), Admin::getName, query.getName())
                    .orderBy(true, false, List.of(Admin::getGmtModified))
                    .page(page);
        } else {
            // SELECT id,username,`password`,`name`,gender,age,phone,email,avatar,gmt_create,gmt_modified FROM `admin` WHERE (id NOT IN (?)) ORDER BY gmt_modified DESC LIMIT ?
            adminPage = lambdaQuery()
                    .notIn(Admin::getId, CommonConstants.SUPER_ADMIN_ID)
                    .like(StringUtils.isNotBlank(query.getUsername()), Admin::getUsername, query.getUsername())
                    .like(StringUtils.isNotBlank(query.getName()), Admin::getName, query.getName())
                    .orderBy(true, false, List.of(Admin::getGmtModified))
                    .page(page);
        }
        return adminPage.convert(converter::convertToAdminBO);
    }

    @Override
    public void delete(Long id) {
        // 如果是超级管理员不能被删除
        if (CommonConstants.SUPER_ADMIN_ID.equals(id)) {
            throw new BizException("超级管理员不能被删除");
        }
        this.removeById(id);
    }
}
