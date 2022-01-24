package com.reloadly.devops.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reloadly.devops.models.PersonalInfo;

@Repository
public interface PersonalInfoRepo extends JpaRepository<PersonalInfo, Long> {

}
