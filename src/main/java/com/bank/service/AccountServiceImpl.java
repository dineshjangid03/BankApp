package com.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exception.AccountException;
import com.bank.exception.UserException;
import com.bank.repo.AccountRepo;
import com.bank.repo.UserRepo;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepo ar;
	
	@Autowired
	private UserRepo ur;

	@Override
	public Account createAccount(Account account) throws UserException {

		Account ac=ar.save(account);
		
		return ac;
	}

	@Override
	public Account deleteAccount(Long accountId) throws AccountException {
		Optional<Account> ac=ar.findById(accountId);
		if(ac.isEmpty())
			throw new AccountException("account not found with id "+accountId);
		
		ar.delete(ac.get());
		return ac.get();
	}

	@Override
	public User addUserToAccount(Long accountId, User user) throws AccountException {
		Optional<Account> ac=ar.findById(accountId);
		if(ac.isEmpty())
			throw new AccountException("account not found with id "+accountId);
		
		Account account=ac.get();
		
		user.setAccount(account);
		
		return ur.save(user);
		
	}

	@Override
	public String removeUserFromAccount(Long accountId, Long userId) throws AccountException, UserException {
		System.out.println("hello");
		Optional<Account> ac=ar.findById(accountId);
		if(ac.isEmpty())
			throw new AccountException("account not found with id "+accountId);
		
		Account account=ac.get();
		boolean isRemoved=account.getUsers().removeIf(u-> u.getUserId()==userId);
		
		if(!isRemoved)
			throw new UserException("user not linked with account "+accountId);
		
		User user=ur.findById(userId).get();
		
		user.setAccount(null);
		
		ur.save(user);
		
		ar.save(account);
		
		
		return "user id "+userId+" removed from account "+accountId;
		
		
	}

}
