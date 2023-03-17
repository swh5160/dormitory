package linc.fun.dormitory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.BuildingBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.query.BuildingQuery;
import linc.fun.dormitory.vo.BuildingVO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import linc.fun.dormitory.po.Building;
import linc.fun.dormitory.mapper.BuildingMapper;
import linc.fun.dormitory.service.BuildingService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>
        implements BuildingService {
    @Resource
    private SysConverter converter;

    @Override
    public IPage<BuildingBO> getPage(Page<BuildingBO> page, BuildingQuery query) {
        return baseMapper.selectByPage(page, query);
    }

    @Override
    public List<BuildingVO> listAll() {
        // select id,name from building
        List<Building> buildingList = this.list(new LambdaQueryWrapper<Building>().select(Building::getId, Building::getName, Building::getBno));
        if (CollectionUtils.isEmpty(buildingList)) {
            return new ArrayList<>();
        }
        return converter.convertToBuildingVOList(buildingList);
    }
}
