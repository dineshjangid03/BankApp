package com.bank.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exception.AccountException;
import com.bank.exception.AmountException;
import com.bank.exception.UserException;
import com.bank.repo.UserRepo;


@Service
public class TransactionServiceImpl implements TransactionService{
	
    
	@Autowired
	private UserRepo userRepository;
	
	@Override
	@Transactional
    public User creditAccount(Long userId, Long amount) throws AccountException, UserException, AmountException {
        
        Optional<User> userOpt=userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new UserException("User not found");
        }
        
        User user=userOpt.get();
        
        Account account=user.getAccount();
        
        if(account==null)
        	throw new AccountException("account is not linked to user id "+userId);
        
        synchronized (account) {
            if (account.getBalance() + amount > 10000000) {
                throw new AmountException("Credit amount too large");
            }
            account.setBalance(account.getBalance() + amount);
        }
        
        return userRepository.save(user);
	}
	
	@Override
	@Transactional
    public User debitAccount(Long userId, Long amount) throws AccountException, UserException, AmountException {
		
		Optional<User> userOpt=userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new UserException("User not found");
        }
        
        User user=userOpt.get();
        
        Account account=user.getAccount();
        
        if(account==null)
        	throw new AccountException("account is not linked to user id "+userId);
        
        synchronized (account) {
            if (account.getBalance() - amount < 0) {
                throw new AmountException("insufficient balance");
            }
            account.setBalance(account.getBalance() - amount);
        }
        
        return userRepository.save(user);
    }

}
