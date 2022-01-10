package cn.com.xuxiaowei;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密工厂
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
class PasswordEncoderFactoriesTests {

    /**
     * 加密
     * <p>
     * 与 MD5 加密不同，{@link PasswordEncoder} 每次加密相同原文时，密文不同
     * <p>
     * 123：{bcrypt}$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y
     */
    @Test
    void encode() {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = "123";
        String encode = delegatingPasswordEncoder.encode(password);
        System.out.println("原文：" + password);
        System.out.println("加密结果：" + encode);
    }

    /**
     * 验证
     */
    @Test
    void matches() {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean matches = delegatingPasswordEncoder.matches("123",
                "{bcrypt}$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y");
        System.out.println("比较结果：" + matches);
    }

    /**
     * MD5 与兼容运算
     * <p>
     * 123：202cb962ac59075b964b07152d234b70
     */
    @Test
    void compatible() {
        String password = "123";

        MD5 md5 = SecureUtil.md5();
        String digestHex = md5.digestHex(password);

        System.out.println("原文：" + password);
        System.out.println("加密结果：" + digestHex);

        String encode = "{MD5}" + digestHex;
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        boolean matches = delegatingPasswordEncoder.matches("123", encode);
        System.out.println("MD5兼容比较结果：" + matches);

        String nowEncode = delegatingPasswordEncoder.encode(password);
        System.out.println("原文：" + password);
        System.out.println("新加密（bcrypt）结果：" + nowEncode);

    }

}
