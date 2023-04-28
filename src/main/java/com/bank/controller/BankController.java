package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exception.AccountException;
import com.bank.exception.AmountException;
import com.bank.exception.UserException;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;

@RestController
@RequestMapping("/account")
public class BankController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody Account Account) throws UserException {
        Account createdAccount=accountService.createAccount(Account);
        return new ResponseEntity<>(createdAccount,HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("accountId") Long accountId) throws AccountException {
        Account deleted=accountService.deleteAccount(accountId);
        return new ResponseEntity<>(deleted,HttpStatus.OK);
    }

    @PostMapping("/addUser/{accountId}/{userId}")
    public ResponseEntity<User> addUserToAccount(@PathVariable("accountId") Long accountId, @RequestBody User user) throws AccountException {
    	User account=accountService.addUserToAccount(accountId, user);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @DeleteMapping("/removeUser/{accountId}/{userId}")
    public ResponseEntity<String> removeUserFromAccount(@PathVariable("accountId") Long accountId, @PathVariable("userId") Long userId) throws AccountException, UserException {
    	String res=accountService.removeUserFromAccount(accountId, userId);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    
    @PutMapping("/credit/{userId}/{amount}")
    public ResponseEntity<User> creditAccount(@PathVariable("userId") Long userId, @PathVariable("amount") Long amount) throws AccountException, UserException, AmountException{
    	User user=transactionService.creditAccount(userId, amount);
    	return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/debit/{userId}/{amount}")
	public ResponseEntity<User> debitAccount(@PathVariable("userId") Long userId, @PathVariable("amount") Long amount) throws AccountException, UserException, AmountException {
		User user=transactionService.debitAccount(userId, amount);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}
