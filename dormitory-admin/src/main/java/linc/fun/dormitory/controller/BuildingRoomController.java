package linc.fun.dormitory.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import linc.fun.dormitory.annotation.ResponseResult;
import linc.fun.dormitory.annotation.TokenCheck;
import linc.fun.dormitory.bo.BuildingRoomBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.form.BuildingRoomForm;
import linc.fun.dormitory.form.RoomBedPlaceHolderForm;
import linc.fun.dormitory.query.BuildingRoomQuery;
import linc.fun.dormitory.service.BuildingRoomService;
import linc.fun.dormitory.vo.BuildingRoomVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yqlin
 * @date 2022/4/15 22:22
 * @description
 */
@ResponseResult
@RequestMapping("/api/building_room")
@Api(tags = "BuildingRoomController", description = "宿舍管理")
public class BuildingRoomController {
    @Resource
    private BuildingRoomService service;
    @Resource
    private SysConverter converter;

    @TokenCheck
    @ApiOperation("根据分页查询宿舍列表")
    @GetMapping("/get/page")
    public IPage<BuildingRoomVO> getPage(Page<BuildingRoomBO> page, BuildingRoomQuery query) {
        IPage<BuildingRoomBO> iPage = service.getPage(page, query);
        return iPage.convert(converter::convertToBuildingRoomVO);
    }


    @TokenCheck
    @ApiOperation("添加宿舍")
    @PostMapping
    public void save(@RequestBody @Validated BuildingRoomForm form) {
        service.toSave(form);
    }


    @TokenCheck
    @ApiOperation("根据id删除宿舍")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @TokenCheck
    @ApiOperation("床位占位")
    @PostMapping("/bed/placeholder")
    public void placeholderBed(@RequestBody @Validated RoomBedPlaceHolderForm form) {
        service.placeholderBed(form);
    }

    @TokenCheck
    @ApiOperation("床位清空")
    @DeleteMapping("/bed/placeholder/clear/{roomBedId}")
    public void clearPlaceholderBed(@PathVariable Long roomBedId) {
        service.clearPlaceholderBed(roomBedId);
    }
}
