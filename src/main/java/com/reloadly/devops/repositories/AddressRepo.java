package com.reloadly.devops.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
