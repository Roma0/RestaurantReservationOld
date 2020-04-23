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

import java.util.Comparator;
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
    private User user;
    private String userName = "Han";
    private String email = "hanwang@gmail.com";
    private String rawPassword = "123456789";

    @Before
    public void setUp(){
        logger.debug("Setting up before the testing ...");
        user  = new User(userName, email);
        user.setPassword(rawPassword);
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

    @Test
    public void getUsers(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        List<User> users = userDao.getUsers();
        int size = users.size();
        Assert.assertEquals(user.getId(), users.stream().map(User::getId).max(Comparator.naturalOrder()).get());
    }

    @Test
    public void getUserByNameOrEmail(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(userName, userDao.getUserByNameOrEmail(userName).getName());
        Assert.assertEquals(email, userDao.getUserByNameOrEmail(email).getEmail());
    }

    @Test
    public void getUserByCredential(){
        logger.debug(String.format("Testing %s for '%s()' method.", className, testName.getMethodName()));
        Assert.assertEquals(userName, userDao.getUserByCredential(userName, user.getPassword()).getName());
        Assert.assertEquals(email, userDao.getUserByCredential(email, user.getPassword()).getEmail());
    }
}
