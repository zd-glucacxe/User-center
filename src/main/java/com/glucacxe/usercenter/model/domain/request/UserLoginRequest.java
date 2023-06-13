package com.glucacxe.usercenter.model.domain.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *  用户登录请求体
 *
 * @author zuodong
 * @create 2023-06-12 18:40
 */

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 1500736697168030644L;

    private String userAccount;
    private String userPassword;

}
