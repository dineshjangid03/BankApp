package com.bank.service;

import com.bank.entity.User;
import com.bank.exception.AccountException;
import com.bank.exception.AmountException;
import com.bank.exception.UserException;

public interface TransactionService {
	
	public User creditAccount(Long userId, Long amount) throws AccountException, UserException, AmountException ;
        
	public User debitAccount(Long userId, Long amount) throws AccountException, UserException, AmountException ;
	
}
