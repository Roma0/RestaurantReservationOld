package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.User;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class UserDaoTest {
    @Rule
    public TestName testName = new TestName();
    public String className = this.getClass().getName().replaceAll("Test$", "");

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;

    private Long id;
    private String email;
    private String name;
    private User user;


    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        name  = "yd";
        email = "yd13_0@163.com";
        user  = new User(name, email);
        id = null;
        id = userDao.save(user).getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        if(id != null)Assert.assertTrue(userDao.deleteById(id));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String phone = "1234567890";
        user.setPhone(phone);
        Assert.assertEquals(phone, userDao.update(user).getPhone());
    }

    @Test
    public void getUsers(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<User> users = userDao.getUsers();
        int size = users.size();
        Assert.assertEquals(id, users.get(size - 1).getId());
    }

    @Test
    public void getUserByNameOrEmail(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(name, userDao.getUserByNameOrEmail(name).getName());
        Assert.assertEquals(email, userDao.getUserByNameOrEmail(email).getEmail());
    }

    @Test
    public void getUserByCredential(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        user.setPassword("123456789");
        String password = userDao.update(user).getPassword();
        Assert.assertEquals(name, userDao.getUserByCredential(name, password).getName());
        Assert.assertEquals(email, userDao.getUserByCredential(email, password).getEmail());
    }
}
