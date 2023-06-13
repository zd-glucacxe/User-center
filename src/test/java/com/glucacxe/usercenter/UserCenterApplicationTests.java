package com.glucacxe.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;


@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void testDigest() {
        String newPassword = DigestUtils.md5DigestAsHex(("abc" + "mypassword").getBytes());
        System.out.println("newPassword = " + newPassword);
    }

    @Test
    void contextLoads() {
    }

}
