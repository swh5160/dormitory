package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.BuildingRoomBO;
import linc.fun.dormitory.po.BuildingRoom;
import linc.fun.dormitory.po.RoomBed;
import linc.fun.dormitory.query.BuildingRoomQuery;
import linc.fun.dormitory.vo.BuildingRoomInfoVO;
import linc.fun.dormitory.vo.StatisticalVO;
import org.apache.ibatis.annotations.Param;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface BuildingRoomMapper extends BaseMapper<BuildingRoom> {
    IPage<BuildingRoomBO> selectByPage(Page<BuildingRoomBO> page, @Param("query") BuildingRoomQuery query);

    BuildingRoomInfoVO selectAccommodationInfo();
}