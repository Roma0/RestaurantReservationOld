package com.ascending.repository;

import com.ascending.AppInitializer;
import com.ascending.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;

    private Long id;
    private String email = "yd13_0@163.com";
    private String name = "yd";
    private User user = new User(name, email);


    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        id = null;
        id = userDao.save(user).getId();
        Assert.assertNotNull(id);
    }

    @After
    public void tearDown(){
        logger.debug("Tearing down after the testing ...");
        Assert.assertTrue(userDao.delete(user));
    }

    @Test
    public void update(){
        logger.debug("Testing UserDao.update() ...");
        String phone = "1234567890";
        user.setPhone(phone);
        Assert.assertEquals(phone, userDao.update(user).getPhone());
    }

    @Test
    public void getUsers(){
        logger.debug("Testing UserDao.getUser() ...");
        List<User> users = userDao.getUsers();
        int size = users.size();
        Assert.assertEquals(id, users.get(size - 1).getId());
    }

    @Test
    public void getUserByEmail(){
        logger.debug("Testing UserDao.getUsetByEmail() ...");
        Assert.assertEquals(email, userDao.getUserByEmail(email).getEmail());
    }

    @Test
    public void getUserByCredential(){
        logger.debug("Testing UserDao.getUserByCredential() ...");
        user.setPassword("123456789");
        String password = userDao.update(user).getPassword();
        Assert.assertEquals(name, userDao.getUserByCredential(email, password).getName());
    }
}
