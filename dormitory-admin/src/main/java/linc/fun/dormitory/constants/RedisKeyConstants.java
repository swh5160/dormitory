package linc.fun.dormitory.constants;

/**
 * @author yqlin
 * @date 2022/4/14 21:10
 * @description Redis相关常量信息
 */
public interface RedisKeyConstants {
    /**
     * 学生打卡key
     */
    String STUDENT_SIGN_IN_KEY = "STUDENT_SIGN_IN_KEY";

    /**
     * 后缀
     */
    String SUFFIX = "_";



    /**
     * 生成： SIMBOT_RANDOM_ALGORITHM_QUESTION_KEY_9_10
     *
     * @param redisKey SIMBOT_RANDOM_ALGORITHM_QUESTION_KEY
     * @param args     9,10
     *
     * @return SIMBOT_RANDOM_ALGORITHM_QUESTION_KEY_9_10
     */
    static String generateKeyConstantWithSuffix(String redisKey, String... args) {
        if (args.length == 0) {
            return redisKey + RedisKeyConstants.SUFFIX;
        } else {
            StringBuilder sb = new StringBuilder(redisKey);
            for (String arg : args) {
                sb.append(RedisKeyConstants.SUFFIX).append(arg);
            }
            return sb.toString();
        }
    }

    /**
     * 如 SIMBOT_RANDOM_ALGORITHM_QUESTION_KEY_
     * 判断是否以这个key为前缀
     *
     * @param text        需要校验的字符串
     * @param keyConstant 常量
     *
     * @return true|false
     */
    static boolean startsWithKeySuffix(String text, String keyConstant) {
        return text.startsWith(keyConstant + SUFFIX);
    }

    /**
     * 分割如 SIMBOT_SENSITIVE_ACCOUNT_KEY_1220127046
     *
     * @param text        SIMBOT_SENSITIVE_ACCOUNT_KEY_1220127046
     * @param keyConstant SIMBOT_SENSITIVE_ACCOUNT_KEY_
     *
     * @return [1220127046]
     */
    static String[] splitKeyConstantParam(String text, String keyConstant) {
        if (startsWithKeySuffix(text, keyConstant)) {
            return text.substring((keyConstant + SUFFIX).length()).split(SUFFIX);
        }
        return null;
    }
}
