package com.sakura.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * @author: sakura
 * @date: 2024/6/27 下午11:10
 * @description:
 */
@SpringBootTest
public class Tests {

    @Test
    void testDigest() {
        String s = DigestUtils.md5DigestAsHex("20011123xwj".getBytes());
        System.out.println(s);
    }
}
