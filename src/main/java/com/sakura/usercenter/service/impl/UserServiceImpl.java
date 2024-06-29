package com.sakura.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.usercenter.modal.domain.User;
import com.sakura.usercenter.service.UserService;
import com.sakura.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.sakura.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author sakura
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-06-27 21:53:47
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "sakura";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 账户不能含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        Long count = lambdaQuery().eq(User::getUserAccount, userAccount).count();
        if (count > 0) {
            return -1;
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.插入数据
        User user = User.builder().userAccount(userAccount).userPassword(encryptPassword).build();
        boolean saveResult = save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        // 账户不能含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            return null;
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户数据是否存在
        User user = lambdaQuery()
                .eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword)
                .one();
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser) {
        return User.builder()
                .id(originUser.getId())
                .userAccount(originUser.getUserAccount())
                .username(originUser.getUsername())
                .avatarUrl(originUser.getAvatarUrl())
                .gender(originUser.getGender())
                .phone(originUser.getPhone())
                .email(originUser.getEmail())
                .userRole(originUser.getUserRole())
                .userStatus(originUser.getUserStatus())
                .createTime(originUser.getCreateTime())
                .build();
    }
}




