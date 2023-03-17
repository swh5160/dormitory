package linc.fun.dormitory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import linc.fun.dormitory.bo.ProblemBO;
import linc.fun.dormitory.form.ProblemAuditForm;
import linc.fun.dormitory.po.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import linc.fun.dormitory.query.ProblemQuery;
import linc.fun.dormitory.vo.ProblemVO;

import java.util.List;

/**
 * @author yqlin
 * @date 2022/4/14 21:29
 * @description
 */
public interface ProblemService extends IService<Problem> {
    IPage<ProblemBO> getPage(Page<Problem> page, ProblemQuery query);

    void toSave(Problem problem);

    void audit(ProblemAuditForm form);

    IPage<ProblemBO> getMyPage(Page<Problem> page, ProblemQuery query);

    List<ProblemVO> getRecent(Integer nums);
}
