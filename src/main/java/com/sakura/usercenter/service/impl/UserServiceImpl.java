package com.sakura.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.usercenter.common.ErrorCode;
import com.sakura.usercenter.exception.BusinessException;
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
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号小于四位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码小于八位");
        }
        // 账户不能含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含特殊字符");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码和校验密码不同");
        }
        // 账户不能重复
        Long count = lambdaQuery().eq(User::getUserAccount, userAccount).count();
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号重复");
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.插入数据
        User user = User.builder().userAccount(userAccount).userPassword(encryptPassword).build();
        boolean saveResult = save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号小于四位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码小于八位");
        }
        // 账户不能含特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        if (!userAccount.matches(validPattern)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号包含特殊字符");
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
            throw new BusinessException(40102, "账号或密码错误");
        }

        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
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

    @Override
    public Boolean userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }
}




