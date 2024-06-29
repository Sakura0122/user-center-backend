package com.sakura.usercenter.service;

import com.sakura.usercenter.modal.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author sakura
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-06-27 21:53:47
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 确认密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 处理HTTP请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     * @param originUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originUser);
}
