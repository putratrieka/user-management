package com.trieka.usermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trieka.usermanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
