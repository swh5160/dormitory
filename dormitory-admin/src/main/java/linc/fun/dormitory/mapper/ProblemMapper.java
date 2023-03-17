package linc.fun.dormitory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import linc.fun.dormitory.po.Problem;
import linc.fun.dormitory.vo.ProblemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface ProblemMapper extends BaseMapper<Problem> {
    List<ProblemVO> selectByNums(@Param("nums") Integer nums);
}