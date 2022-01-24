package com.reloadly.devops.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.ContactInfo;

@Repository
public interface ContactInfoRepo extends JpaRepository<ContactInfo, Long> {

}
