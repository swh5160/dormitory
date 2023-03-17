package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.BuildingBO;
import linc.fun.dormitory.po.Building;
import linc.fun.dormitory.query.BuildingQuery;
import org.apache.ibatis.annotations.Param;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface BuildingMapper extends BaseMapper<Building> {
    IPage<BuildingBO> selectByPage(Page<BuildingBO> page,@Param("query") BuildingQuery query);
}