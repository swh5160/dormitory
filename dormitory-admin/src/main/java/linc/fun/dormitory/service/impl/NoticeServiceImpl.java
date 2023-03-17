package linc.fun.dormitory.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.NoticeBO;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.query.NoticeQuery;
import linc.fun.dormitory.vo.NoticeVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import linc.fun.dormitory.mapper.NoticeMapper;
import linc.fun.dormitory.po.Notice;
import linc.fun.dormitory.service.NoticeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
        implements NoticeService {
    @Resource
    private SysConverter converter;

    @Override
    public IPage<NoticeBO> getPage(Page<Notice> page, NoticeQuery query) {
        Page<Notice> noticePage = lambdaQuery()
                .like(StringUtils.isNotBlank(query.getTitle()), Notice::getTitle, query.getTitle())
                .orderBy(true, false, List.of(Notice::getGmtModified))
                .page(page);
        return noticePage.convert(converter::convertToNoticeBO);
    }

    @Override
    public List<NoticeVO> getRecent(Integer nums) {
        return baseMapper.selectByNums(nums);
    }
}
