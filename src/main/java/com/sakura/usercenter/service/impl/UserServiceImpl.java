package com.sakura.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.usercenter.modal.User;
import com.sakura.usercenter.service.UserService;
import com.sakura.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author sakura
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-06-27 21:53:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
        final String SALT = "sakura";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.插入数据
        User user = User.builder().userAccount(userAccount).userPassword(encryptPassword).build();
        boolean saveResult = save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }
}




