package com.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
