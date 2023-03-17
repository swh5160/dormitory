package linc.fun.dormitory.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wf.captcha.base.Captcha;
import linc.fun.dormitory.config.captcha.CaptchaFactory;
import linc.fun.dormitory.constants.CommonConstants;
import linc.fun.dormitory.converter.SysConverter;
import linc.fun.dormitory.dto.LoginDTO;
import linc.fun.dormitory.dto.TokenDTO;
import linc.fun.dormitory.enums.RoleEnum;
import linc.fun.dormitory.exception.BizException;
import linc.fun.dormitory.mapper.AdminMapper;
import linc.fun.dormitory.mapper.StudentMapper;
import linc.fun.dormitory.po.Admin;
import linc.fun.dormitory.po.Student;
import linc.fun.dormitory.util.JwtUtils;
import linc.fun.dormitory.util.ServletUtils;
import linc.fun.dormitory.vo.CaptchaVO;
import linc.fun.dormitory.vo.TokenVO;
import linc.fun.dormitory.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yqlin
 * @date 2022/4/15 01:30
 * @description
 */
@Slf4j
@Service
public class LoginService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private SysConverter converter;
    @Resource
    private CaptchaFactory captchaFactory;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public TokenVO login(LoginDTO dto) {
        // 验证码key
        String codeKey = CommonConstants.CAPTCHA_CODE_KEY + dto.getUuid();
        // 从redis中拿出结果信息
        String captcha = stringRedisTemplate.opsForValue().get(codeKey);
        //删除key
        stringRedisTemplate.delete(codeKey);
        // 判断验证码是否正确
        if (!Objects.equals(captcha, dto.getCode())) {
            throw new BizException("验证码错误");
        }
        if (RoleEnum.ADMIN.getCode().equals(dto.getType())) {
            return doAdminLogin(dto);
        } else {
            return doStudentLogin(dto);
        }

    }

    private TokenVO doStudentLogin(LoginDTO dto) {
        // 查询当前用户名
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getUsername, dto.getUsername()));
        if (Objects.isNull(student)) {
            throw new BizException("当前学生不存在");
        }
        // 判断密码是否一致
        if (!student.getPassword().equals(dto.getPassword())) {
            throw new BizException("学生密码错误");
        }
        // 生成token
        String token = JwtUtils.sign(student.getId(), RoleEnum.STUDENT, CommonConstants.EXPIRE_TIME);
        return new TokenVO(token);
    }

    private TokenVO doAdminLogin(LoginDTO dto) {
        // 查询当前用户名
        Admin admin = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, dto.getUsername()));
        if (Objects.isNull(admin)) {
            throw new BizException("当前管理员不存在");
        }
        // 判断密码是否一致
        if (!admin.getPassword().equals(dto.getPassword())) {
            throw new BizException("管理员密码错误");
        }
        // 生成token
        String token = JwtUtils.sign(admin.getId(), RoleEnum.ADMIN, CommonConstants.EXPIRE_TIME);
        return new TokenVO(token);
    }

    public UserInfoVO getInfo() {
        String token = JwtUtils.getToken(Objects.requireNonNull(ServletUtils.getRequest()));
        if (StringUtils.isEmpty(token)) {
            throw new BizException("令牌失效");
        }
        // 从token中拿出payload
        TokenDTO payload = JwtUtils.getPayload(token);
        Long id = payload.getId();
        switch (payload.getRoleEnum()) {
            // 判断是否是学生
            case STUDENT:
                // 根据id查询当前用户
                Student student = studentMapper.selectById(id);
                // 判断当前用户是否存在
                if (Objects.isNull(student)) {
                    throw new BizException("当前学生不存在");
                }
                // 返回用户信息
                return converter.convertToUserInfoVO(student).setRole(RoleEnum.STUDENT);
            // 判断是否是管理员
            case ADMIN:
                // 根据id查询当前用户
                Admin admin = adminMapper.selectById(id);
                // 判断当前用户是否存在
                if (Objects.isNull(admin)) {
                    throw new BizException("当前管理员不存在");
                }
                // 返回用户信息
                return converter.convertToUserInfoVO(admin).setRole(RoleEnum.ADMIN);
            default:
                return null;
        }
    }


    public CaptchaVO getCaptcha() {
        // 获取运算的结果
        Captcha captcha = captchaFactory.getCaptcha();
        // 唯一标识
        String uuid = IdUtil.fastSimpleUUID();
        log.info("current code: {}, uuid: {}", captcha.text(), uuid);
        String codeKey = CommonConstants.CAPTCHA_CODE_KEY + uuid;
        // 保存到redis
        stringRedisTemplate.opsForValue().setIfAbsent(codeKey, captcha.text(), captchaFactory.getCaptchaCode().getExpiration(), TimeUnit.MINUTES);
        return new CaptchaVO(uuid, captcha.toBase64());
    }
}
