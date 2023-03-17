package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import linc.fun.dormitory.po.Notice;
import linc.fun.dormitory.vo.NoticeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    List<NoticeVO> selectByNums(@Param("nums") Integer nums);
}