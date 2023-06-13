package com.glucacxe.usercenter.controller;

import com.glucacxe.usercenter.model.domain.User;
import com.glucacxe.usercenter.model.domain.request.UserLoginRequest;
import com.glucacxe.usercenter.model.domain.request.UserRegisterRequest;
import com.glucacxe.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  用户接口
 * @author zuodong
 * @create 2023-06-12 18:14
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        // 前端传递过来的参数为空
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        // 前端传递过来的参数为空
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }


    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        return userService.searchUsers(username, request);
    }


    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id, HttpServletRequest request) {
        return userService.deleteUsers(id, request);
    }


}
