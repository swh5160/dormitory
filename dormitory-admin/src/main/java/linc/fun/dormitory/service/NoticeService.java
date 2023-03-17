package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.NoticeBO;
import linc.fun.dormitory.po.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.query.NoticeQuery;
import linc.fun.dormitory.vo.NoticeVO;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface NoticeService extends IService<Notice>{


    IPage<NoticeBO> getPage(Page<Notice> page, NoticeQuery query);

    List<NoticeVO> getRecent(Integer nums);
}
