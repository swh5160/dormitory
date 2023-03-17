package linc.fun.dormitory.util;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linc
 */
@Component
public class BitsOpUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean setBits(String key, int index, int value) {
        // tag为true就是设置1  否则就是设置0
        boolean tag = value == 1;
        return Boolean.TRUE.equals(stringRedisTemplate.execute((RedisCallback<Boolean>) con -> con.setBit(key.getBytes(), index, tag)));
    }

    /**
     * 计算有多少位
     */
    public Long countBits(String key) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    /**
     * 一次过设置多个位
     */
    public List<Long> setBitFields(String key, int index, int value) {
        return stringRedisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(), BitFieldSubCommands.create().set(BitFieldSubCommands.BitFieldType.UINT_8).valueAt(index).to(value)));
    }

    /**
     * 获取从某个位到某个位的值
     */
    public List<Long> count(String key, int index, int limit) {
        return stringRedisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(), BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(index)));
    }
}
