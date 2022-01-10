package cn.com.xuxiaowei;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密工厂
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
class PasswordEncoderFactoriesTests {

    /**
     * {SoGBcQjzo//jymrVnddobFbeY6vcyDTesL37nXCeL6Y=}7505099ac55f72289339866185816de5
     * {SnRha+br053FD13/AWf3QHQPxWfz6eK8NA3atnvXyo4=}2242b4e60ffc064ce63a2ea6d72676a6
     */
    @Test
    void md5() {
        MessageDigestPasswordEncoder md5 = new MessageDigestPasswordEncoder("MD5");
        String encode = md5.encode("123");
        System.out.println(encode);
    }

    /**
     * 加密
     * <p>
     * 与 MD5 加密不同，{@link PasswordEncoder} 每次加密相同原文时，密文不同
     * <p>
     * 123：{bcrypt}$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y
     * {bcrypt}$2a$10$Fz6Ke5Ie3H3w/Sjg3FzM4eokaIhbze6GMWQkjfbhj3ZF98bjPFMIu
     * {bcrypt}$2a$10$BEKzLjdvAZpNSxksTpaEQey0QL3uHg.3aUcdymwTK2FiunRMm6PjK
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
                "{bcrypt}$2a$10$Fz6Ke5Ie3H3w/Sjg3FzM4eokaIhbze6GMWQkjfbhj3ZF98bjPFMIu");
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
//        String digestHex = md5.digestHex(password);
        String digestHex = "202cb962ac59075b964b07152d234b70";

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
