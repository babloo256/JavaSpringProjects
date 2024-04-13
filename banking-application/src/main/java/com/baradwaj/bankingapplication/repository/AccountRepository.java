package com.baradwaj.bankingapplication.repository;

import com.baradwaj.bankingapplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
