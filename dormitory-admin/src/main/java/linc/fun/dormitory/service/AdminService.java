package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.AdminBO;
import linc.fun.dormitory.po.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.query.AdminQuery;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface AdminService extends IService<Admin>{


    IPage<AdminBO> getPage(Page<Admin> page, AdminQuery query);

    void delete(Long id);
}
