package com.ascending.repository;

import com.ascending.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User update(User user);
    boolean deleteById(Long id);
    List<User> getUsers();
    User getUserByNameOrEmail(String nameOrEmail);
    User getUserByCredential(String nameOrEmail, String password);
}
