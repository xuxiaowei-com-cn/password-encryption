package cn.com.xuxiaowei;

import cn.com.xuxiaowei.crypto.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

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
     * 与 MD5 加密不同，{@link BCrypt} 每次加密相同原文时，密文不同
     * <p>
     * 123：{bcrypt}$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y
     */
    @Test
    void encode() {
        String password = "123";
        String gensalt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(password, gensalt);

        System.out.println("原文：" + password);
        System.out.println("加密结果：" + hashpw);
    }

    /**
     * 验证
     */
    @Test
    void matches() {
        boolean matches = BCryptPasswordEncoder.checkpw("123",
                "{bcrypt}$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y");
        System.out.println("比较结果：" + matches);

        boolean matches2 = BCryptPasswordEncoder.checkpw("123",
                "$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y");
        System.out.println("比较结果：" + matches2);

        boolean matches3 = BCrypt.checkpw("123",
                "$2a$10$KHRctsDUxgu9FVGfPWD3e.5SY4DD0rG1YNVrJ87tZLybwYIu2626y");
        System.out.println("比较结果：" + matches3);
    }

}
