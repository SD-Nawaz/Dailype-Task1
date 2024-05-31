package com.example.Dailype_Task1.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Dailype_Task1.model.User;

@Repository
public interface UserRepository {
    ArrayList<User> getUsers();

    User getUserById(int userId);

    User addUser(User user);

    User updateUser(int userId, User user);

    void deleteUser(int userId);
}