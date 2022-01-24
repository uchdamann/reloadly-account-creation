package com.reloadly.devops.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);
	public boolean existsByUsername(String username);
	
}