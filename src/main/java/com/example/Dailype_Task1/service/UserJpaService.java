package com.example.Dailype_Task1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Dailype_Task1.DTOs.UserDto;
import com.example.Dailype_Task1.model.User;
import com.example.Dailype_Task1.repository.UserJpaRepository;
import com.example.Dailype_Task1.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserJpaService implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public ArrayList<User> getUsers() {
        List<User> userList = userJpaRepository.findAll();
        ArrayList<User> users = new ArrayList<>(userList);
        return users;
    }

    @Override
    public User getUserById(int userId) {
        try {
            User user = userJpaRepository.findById(userId).get();
            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public User addUser(User user) {
        userJpaRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(int userId, User user) {
        try {
            User newUser = userJpaRepository.findById(userId).get();
            if (user.getManagerId() != null) {
                newUser.setManagerId(user.getManagerId());
            }
            if (user.getFullName() != null) {
                newUser.setFullName(user.getFullName());
            }
            if (user.getMobileNo() != null) {
                newUser.setMobileNo(user.getMobileNo());
            }
            if (user.getPanNo() != null) {
                newUser.setPanNo(user.getPanNo());
            }
            userJpaRepository.save(newUser);
            return newUser;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteUser(int userId) {
        try {
            userJpaRepository.deleteById(userId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public User getUserByMobileNo(String mobileNo) {
        return userJpaRepository.findByMobileNo(mobileNo);
    }

    @Transactional
    public void bulkInsert(List<UserDto> employeeDTOs) {
        List<User> employees = employeeDTOs.stream()
                .map(this::convertToEmployee)
                .collect(Collectors.toList());
        userJpaRepository.saveAll(employees);
    }

    private User convertToEmployee(UserDto employeeDTO) {
        User employee = new User();
        employee.setFullName(employeeDTO.getFullName());
        employee.setMobileNo(employeeDTO.getMobileNo());
        employee.setPanNo(employeeDTO.getPanNo());
        return employee;
    }

    public void bulkUpdateUserDetails(List<Integer> userIds, String fullName, String mobileNo, String panNo) {
        for (Integer userId : userIds) {
            updateUserDetails(userId, fullName, mobileNo, panNo);
        }
    }

    private void updateUserDetails(Integer userId, String fullName, String mobileNo, String panNo) {
        // Retrieve the user from the database
        User user = userJpaRepository.findById(userId).orElse(null);

        // Update the user details if the user exists
        if (user != null) {
            if (fullName != null) {
                user.setFullName(fullName);
            }
            if (mobileNo != null) {
                user.setMobileNo(mobileNo);
            }
            if (panNo != null) {
                user.setPanNo(panNo);
            }

            // Save the updated user
            userJpaRepository.save(user);
        }
    }

}