package com.glucacxe.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 *  用户注册请求体
 *
 * @author zuodong
 * @create 2023-06-12 18:22
 */

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -8220315272023136398L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
