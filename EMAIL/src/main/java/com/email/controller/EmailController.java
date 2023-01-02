package com.email.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.dto.EmailDto;
import com.email.model.Users;
import com.email.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	// Insert User Data
	@PostMapping("/saveUser")
	public ResponseEntity<Object> saveUser(@RequestBody EmailDto hospitalDto) {
		return emailService.saveUser(hospitalDto);
	}

	// Get all User Details
	@GetMapping("/users")
	public List<Users> allUsers() {
		return emailService.getData();
	}

	// Update User Data
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody EmailDto emailDto) {
		return emailService.updateUser(id, emailDto);
	}

	// Delete User detail
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
		return emailService.deleteUser(id);
	}

	// Email OTP send
	@PostMapping("/emailotp/{email}")
	public ResponseEntity<Object> sendOTP(@PathVariable("email") String email) {
		return emailService.sendOtp(email);
	}
	// Email OTP Verify
	@PostMapping("/verifyemailotp/{email}")
	public ResponseEntity<Object> verifyEmailOtp(@PathVariable("email") String email, @RequestBody Users users){
		
		return emailService.verifyOtp(email,users);
	}
	
	

}
