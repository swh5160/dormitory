package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.BuildingRoomBO;
import linc.fun.dormitory.form.BuildingRoomForm;
import linc.fun.dormitory.form.RoomBedPlaceHolderForm;
import linc.fun.dormitory.po.BuildingRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.query.BuildingQuery;
import linc.fun.dormitory.query.BuildingRoomQuery;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface BuildingRoomService extends IService<BuildingRoom> {


    IPage<BuildingRoomBO> getPage(Page<BuildingRoomBO> page, BuildingRoomQuery query);

    void toSave(BuildingRoomForm form);


    void placeholderBed(RoomBedPlaceHolderForm form);

    void clearPlaceholderBed(Long roomBedId);

    void delete(Long id);
}
