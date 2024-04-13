package com.baradwaj.bankingapplication.service.impl;

import com.baradwaj.bankingapplication.dto.AccountDto;
import com.baradwaj.bankingapplication.entity.Account;
import com.baradwaj.bankingapplication.mapper.AccountMapper;
import com.baradwaj.bankingapplication.repository.AccountRepository;
import com.baradwaj.bankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = this.accountRepository.save(account);
        AccountDto savedAccountDto = AccountMapper.mapToAccountDto(savedAccount);
        return savedAccountDto;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
        AccountDto accountDto = AccountMapper.mapToAccountDto(account);
        return accountDto;
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
        double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);
        Account savedAccount = this.accountRepository.save(account);
        AccountDto savedAccountDto = AccountMapper.mapToAccountDto(savedAccount);
        return savedAccountDto;
    }

    @Override
    public AccountDto withDraw(Long id, double amount) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient Balance");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = this.accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = this.accountRepository.findAll();
        return accounts.stream().map(account -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account Not Found"));
        this.accountRepository.delete(account);
    }
}
