package cn.com.xuxiaowei.crypto;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 基于前缀的 BCrypt 密码编辑器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class BCryptPasswordEncoder {

    private static final String PREFIX = "{";

    private static final String BCRYPT_ENCODING_ID = "bcrypt";

    private static final String SUFFIX = "}";

    /**
     * 获取除前缀外的标准密码密文
     *
     * @param prefixEncodedPassword 带有（或不含）前缀的密码密文
     * @return 返回 除前缀外的标准密码内容
     */
    public static String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf(SUFFIX);
        return prefixEncodedPassword.substring(start + 1);
    }

    /**
     * 获取密码前缀
     *
     * @param prefixEncodedPassword 带有（或不含）前缀的密码密文
     * @return 返回 密码前缀
     */
    public static String extractId(String prefixEncodedPassword) {
        if (prefixEncodedPassword == null) {
            return null;
        }
        int start = prefixEncodedPassword.indexOf(PREFIX);
        if (start != 0) {
            return null;
        }
        int end = prefixEncodedPassword.indexOf(SUFFIX, start);
        if (end < 0) {
            return null;
        }
        return prefixEncodedPassword.substring(start + 1, end);
    }

    /**
     * 比较密码
     * <p>
     * 加密后的密码可包含（不包含）密码前缀
     *
     * @param password              原密码
     * @param prefixEncodedPassword 带有（或不含）前缀的密码密文
     * @return 返回 比较密码结果
     */
    public static boolean checkpw(String password, String prefixEncodedPassword) {
        String encodedPassword = BCryptPasswordEncoder.extractEncodedPassword(prefixEncodedPassword);
        return BCrypt.checkpw(password, encodedPassword);
    }

}
