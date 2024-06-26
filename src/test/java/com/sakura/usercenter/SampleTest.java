package com.sakura.usercenter;

import com.sakura.usercenter.mapper.UserMapper;
import com.sakura.usercenter.modal.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: sakura
 * @date: 2024/6/25 下午10:14
 * @description:
 */
@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
