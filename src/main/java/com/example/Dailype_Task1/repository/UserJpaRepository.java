package com.example.Dailype_Task1.repository;

import com.example.Dailype_Task1.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    User findByMobileNo(String mobileNo);

}