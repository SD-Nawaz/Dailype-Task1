package com.example.Dailype_Task1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import com.example.Dailype_Task1.DTOs.UserDto;
import com.example.Dailype_Task1.DTOs.UserUpdateDto;
import com.example.Dailype_Task1.model.User;
import com.example.Dailype_Task1.service.UserJpaService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    public UserJpaService userService;

    @GetMapping("/users")
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@Valid @PathVariable("userId") int userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/users/byMob/{mobileNo}")
    public User getUserByMobileNo(@PathVariable String mobileNo) {
        return userService.getUserByMobileNo(mobileNo);
    }

    @PostMapping("/users/bulk-insert")
    public String bulkInsertEmployees(@RequestBody List<UserDto> employeeDTOs) {
        userService.bulkInsert(employeeDTOs);
        return "Bulk insert completed successfully.";
    }

    @PutMapping("/users/bulk-update")
    public String bulkUpdateUsers(@RequestParam List<Integer> userIds, @RequestBody UserUpdateDto UserUpdateDto) {
        userService.bulkUpdateUserDetails(userIds, UserUpdateDto.getFullName(), UserUpdateDto.getMobileNo(),
                UserUpdateDto.getPanNo());
        return "Bulk update successful.";
    }
}