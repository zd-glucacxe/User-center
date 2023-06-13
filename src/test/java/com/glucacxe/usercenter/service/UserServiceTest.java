package com.glucacxe.usercenter.service;

import com.glucacxe.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 *  用户服务测试
 *
 * @author zuodong
 * @create 2023-06-11 19:30
 */

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("testYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epibLzNRmobZK7wjDHMqUGtTdpHebwYccLZt7tU5B5bRViaxoYrlEF26OOHAAAGeDLTDaJqZNo2hiauw/132");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println("user.getId() = " + user.getId());
        Assertions.assertTrue(result);

    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu` pi`";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "12345611";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "testYupi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);

    }
}