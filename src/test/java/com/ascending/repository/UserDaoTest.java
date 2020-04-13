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

    private String email;
    private String name;
    private User user;

    //Todo reset ReviewDaoTest without seeding data
    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        name  = "yd";
        email = "yd13_0@163.com";
        user  = new User(name, email);
        user = userDao.save(user);
        Assert.assertNotNull(user.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        if(user.getId() != null)Assert.assertTrue(userDao.deleteById(user.getId()));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        String phone = "1234567890";
        user.setPhone(phone);
        Assert.assertEquals(phone, userDao.update(user).getPhone());
    }

    //Todo Modify size() - 1
    @Test
    public void getUsers(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<User> users = userDao.getUsers();
        int size = users.size();
        Assert.assertEquals(user.getId(), users.get(size - 1).getId());
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
