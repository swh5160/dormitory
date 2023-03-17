package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import linc.fun.dormitory.po.RoomBed;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yqlin
 * @date 2022/4/16 01:29
 * @description
 */
public interface RoomBedMapper extends BaseMapper<RoomBed> {
    void insertList(@Param("beds") List<RoomBed> roomBeds);
}