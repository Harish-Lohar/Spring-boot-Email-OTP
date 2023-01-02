package com.email.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.email.model.Users;

public interface EmailRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByContact(Long contact);

	Optional<Users> findByName(String name);

	Optional<Users> findByEmail(String email);

	Users getByEmail(String email);

}
