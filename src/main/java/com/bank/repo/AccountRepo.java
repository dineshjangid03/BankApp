package com.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long>{

}
