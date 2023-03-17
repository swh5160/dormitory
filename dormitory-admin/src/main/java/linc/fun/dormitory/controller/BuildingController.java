package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.BuildingBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.BuildingForm;
import linc.fun.dormitory.query.BuildingQuery;
import linc.fun.dormitory.service.BuildingService;
import linc.fun.dormitory.vo.BuildingVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 楼宇管理
 *
 * @author linc
 */
@ResponseResult
@RequestMapping("/api/building")
@Api(tags = "BuildingController", description = "楼宇管理")
public class BuildingController {

    @Resource
    private BuildingService service;
    @Resource
    private SysConverter converter;


    @TokenCheck
    @ApiOperation("根据分页查询楼宇列表")
    @GetMapping("/get/page")
    public IPage<BuildingVO> getPage(Page<BuildingBO> page, BuildingQuery query) {
        IPage<BuildingBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToBuildingVO);
    }


    @TokenCheck
    @ApiOperation("添加/修改楼宇")
    @PostMapping
    public void saveOrUpdate(@RequestBody @Validated BuildingForm form) {
        service.saveOrUpdate(converter.convertToBuilding(form));
    }


    @TokenCheck
    @ApiOperation("根据id删除楼宇")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }


    @TokenCheck
    @ApiOperation("查出所有楼宇信息")
    @GetMapping("/get/all")
    public List<BuildingVO> getAll() {
        return service.listAll();
    }
}
