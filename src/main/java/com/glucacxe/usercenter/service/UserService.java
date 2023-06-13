package com.glucacxe.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glucacxe.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author DaVinci
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-06-11 19:25:20
 */
public interface UserService extends IService<User> {





    /**
     * 用户注册
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 查询用户
     *
     * @param username 用户昵称
     * @return 用户信息
     */
    List<User> searchUsers(String username, HttpServletRequest request);

    /**
     * 删除用户
     *
     * @param id id
     * @return 删除结果
     */
    boolean deleteUsers(long id, HttpServletRequest request);


}
