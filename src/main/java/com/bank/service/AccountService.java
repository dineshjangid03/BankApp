package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exception.AccountException;
import com.bank.exception.UserException;

public interface AccountService {
	
    public Account createAccount(Account account)throws UserException;
    
    public Account deleteAccount(Long accountId) throws AccountException;
    
    public User addUserToAccount(Long accountId, User user)throws AccountException;
    
    public String removeUserFromAccount(Long accountId, Long userId)throws AccountException, UserException;

}
