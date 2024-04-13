package com.baradwaj.bankingapplication.controller;

import com.baradwaj.bankingapplication.dto.AccountDto;
import com.baradwaj.bankingapplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("addAccount")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(this.accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("getAccountById/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id){
        AccountDto accountDto = this.accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("deposit/{id}")
    public ResponseEntity<AccountDto> deposit(@PathVariable("id") Long id, @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = this.accountService.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("withDraw/{id}")
    public ResponseEntity<AccountDto> withDraw(@PathVariable("id") Long id, @RequestBody Map<String, Double> request){
        Double amount = request.get("amount");
        AccountDto accountDto = this.accountService.withDraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("allAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = this.accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        this.accountService.deleteAccount(id);
        return ResponseEntity.ok("Successfully Deleted ");
    }

}
