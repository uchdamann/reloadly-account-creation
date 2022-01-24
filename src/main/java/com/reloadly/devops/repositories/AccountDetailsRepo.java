package com.reloadly.devops.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.AccountDetails;

@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, Long> {
	public boolean existsByAccountNumber(String accountNumber);
	public Optional<AccountDetails> findByAccountNumber(String accountNumber);
}
