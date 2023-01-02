package com.email.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.email.dto.EmailDto;
import com.email.model.Users;

public interface EmailService {

	ResponseEntity<Object> saveUser(EmailDto hospitalDto);

	List<Users> getData();

	ResponseEntity<Object> updateUser(Long id, EmailDto emailDto);

	ResponseEntity<Object> deleteUser(Long id);

	ResponseEntity<Object> sendOtp(String email);

	ResponseEntity<Object> verifyOtp( String email, Users users);

}
