package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.BuildingBO;
import linc.fun.dormitory.po.Building;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.query.BuildingQuery;
import linc.fun.dormitory.vo.BuildingVO;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface BuildingService extends IService<Building>{


    IPage<BuildingBO> getPage(Page<BuildingBO> page, BuildingQuery query);

    List<BuildingVO> listAll();
}
