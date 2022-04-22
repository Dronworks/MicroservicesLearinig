package com.dronworks.jpastudy.service;

import com.dronworks.jpastudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
