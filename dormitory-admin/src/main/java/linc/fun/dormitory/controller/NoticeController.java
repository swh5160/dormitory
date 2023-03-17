package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.NoticeBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.NoticeForm;
import linc.fun.dormitory.po.Notice;
import linc.fun.dormitory.query.NoticeQuery;
import linc.fun.dormitory.service.NoticeService;
import linc.fun.dormitory.vo.NoticeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/16 05:54
 * @description
 */
@ResponseResult
@RequestMapping("/api/notice")
@Api(tags = "NoticeController", description = "公告管理")
public class NoticeController {

    @Resource
    private NoticeService service;
    @Resource
    private SysConverter converter;


    @TokenCheck
    @ApiOperation("根据分页查询公告列表")
    @GetMapping("/get/page")
    public IPage<NoticeVO> getPage(Page<Notice> page, NoticeQuery query) {
        IPage<NoticeBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToNoticeVO);
    }

    @TokenCheck
    @ApiOperation("添加/修改公告")
    @PostMapping
    public void saveOrUpdate(@RequestBody @Validated NoticeForm form) {
        service.saveOrUpdate(converter.convertToNotice(form));
    }

    @TokenCheck
    @ApiOperation("根据id删除公告")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

    @TokenCheck
    @ApiOperation("获取最近的公共信息")
    @GetMapping("/get/recent/{nums}")
    public List<NoticeVO> getRecent(@PathVariable Integer nums) {
        return service.getRecent(nums);
    }


}
