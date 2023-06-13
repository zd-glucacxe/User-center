package com.glucacxe.usercenter.service.impl;
import java.util.Collections;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glucacxe.usercenter.model.domain.User;
import com.glucacxe.usercenter.service.UserService;
import com.glucacxe.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.glucacxe.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static com.glucacxe.usercenter.constant.UserConstant.ADMIN_ROLE;


/**
* @author DaVinci
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-06-11 19:25:20
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;
    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            //TODO 修改为自定义异常
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        //TODO
        // 账户不能包含特殊字符
        String validPattern =  ".*[~!@#$%^&*()_+-={}\\[\\]|;:'\",.<>/\\?].*`";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 密码和校验密码
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 2. 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            //TODO 修改为自定义异常
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        //TODO
        // 账户不能包含特殊字符
        String validPattern =  ".*[~!@#$%^&*()_+-={}\\[\\]|;:'\",.<>/\\?].*`";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        // 2. 加密密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
         if (user == null) {
            log.info("user login failed, userAccount can not match userPassword");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }



    @Override
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return Collections.emptyList();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        // TODO
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUsers(long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }

        if (id <= 0) {
            return false;
        }
        int result = userMapper.deleteById(id);
        return result> 0;
    }


    /**
     * 判断当前用户是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }


    /**
     * 用户脱敏
     * @param originUser 后端查到的用户
     * @return 脱敏后的用户
     */

    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }
}




