package com.xh;

import com.xh.entity.User;
import com.xh.security.mapper.MenuMapper;
import com.xh.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author 35238
 * @date 2023/7/11 0011 20:27
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void yyTestBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("12345");
        System.out.println(encode);
        //模拟用户输入了1234(第一个参数)，然后我们去跟数据库的密文进行比较(第二个参数)
        boolean result = passwordEncoder.matches("1234", "$2a$10$zOitKu6UNk.b/iPFTtIj2u80sH/dfJI9vFr57qhDGteuXj/Wl8uSy");

        //看一下比对结果
        System.out.println(result);

    }
    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testSelectPermsByUserId(){
        //L表示Long类型
        List<String> list = menuMapper.selectPermsByUserId(2L);
        System.out.println(list);
    }

    @Test
    public void testUserMapper() {
        //查询所有用户
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}
