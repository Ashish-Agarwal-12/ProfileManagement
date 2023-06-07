package com.policymanagement.profile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.policymanagement.profile.entity.Userprofile;


@Repository
@EnableJpaRepositories
public interface UserprofileDAO extends JpaRepository<Userprofile,Long>{

	
}