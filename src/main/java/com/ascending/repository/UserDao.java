package com.ascending.repository;

import com.ascending.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User update(User user);
    boolean delete(User user);
    List<User> getUsers();
    User getUserByEmail(String email);
    User getUserByCredential(String email, String password);
}
