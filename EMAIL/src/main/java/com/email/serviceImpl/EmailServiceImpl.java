package com.email.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.email.dao.EmailRepository;
import com.email.dto.EmailDto;
import com.email.model.Users;
import com.email.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public ResponseEntity<Object> saveUser(EmailDto emailDto) {
		Users users = new Users();

		// Check Id is Present
		Optional<Users> Id = emailRepository.findById(emailDto.getId());
		if (!Id.isPresent()) {
			users.setId(emailDto.getId());
		} else {
			return new ResponseEntity<>("Id Not Available...", HttpStatus.ALREADY_REPORTED);
		}

		// Check Name available
		Optional<Users> name = emailRepository.findByName(emailDto.getName());

		if (!name.isPresent()) {
			users.setName(emailDto.getName());
		} else {
			return new ResponseEntity<>("Name Already Available...", HttpStatus.OK);
		}

		// Check Contact Available
		Optional<Users> contact = emailRepository.findByContact(emailDto.getContact());
		if (!contact.isPresent()) {
			users.setContact(emailDto.getContact());
			users.setEmail(emailDto.getEmail());
			emailRepository.save(users);
			return new ResponseEntity<>("User Registered Successfully...", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Contact Already Registered...", HttpStatus.ALREADY_REPORTED);
		}
	}

	@Override
	public List<Users> getData() {
		List<Users> list = emailRepository.findAll();
		return  list ;
	}

	@Override
	public ResponseEntity<Object> updateUser(Long id, EmailDto crudDto) {
		ResponseEntity<Object> msg = new ResponseEntity<>("", HttpStatus.OK);
		Optional<Users> value = emailRepository.findById(id);
		System.out.println(value.isPresent());
		if (value.isPresent()) {
			System.out.println(id);
			Users users = emailRepository.getById(id);
			users.setName(crudDto.getName());
			users.setContact(crudDto.getContact());
			emailRepository.save(users);
			msg = new ResponseEntity<>("User Data Updated Successfully... ", HttpStatus.OK);
		} else {
			msg = new ResponseEntity<>("User Not Exist...", HttpStatus.NOT_FOUND);
		}
		return msg;
	}

	@Override
	public ResponseEntity<Object> deleteUser(Long id) {
		Optional<Users> optional = emailRepository.findById(id);
		if (optional.isPresent()) {
			emailRepository.deleteById(id);
			return new ResponseEntity<>("User Data Deleted Successfully... ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User Not Exist... ", HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public ResponseEntity<Object> sendOtp(String email) {
		Optional<Users> mail = emailRepository.findByEmail(email);
		int max = 10000000;
		int min = 99999999;
		Long a = (long) (Math.random() * (max - min + 1) + min);
		if (mail.isPresent()) {
			try {
				Users users = emailRepository.getByEmail(email);
				SimpleMailMessage message = new SimpleMailMessage();
				String msg = "Hey " + email + " , Your One Time Password is  " + a + " and Your OTP will expire in 2 min .";
				message.setFrom(from);
				message.setTo(email);
				message.setSubject("OTP Verification");
				message.setText(msg);
				javaMailSender.send(message);
				users.setOtp(a);
				users.setOtpCreatedOn(new Date());
				emailRepository.save(users);
				return new ResponseEntity<>("Check Your Email For the OTP...", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("OTP Send Failed...", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("User Not Register...", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> verifyOtp(String email, Users users) {

		Optional<Users> user = emailRepository.findByEmail(email);
		long otpTime = user.get().getOtpCreatedOn().getTime() + 2 * 60 * 1000;
		long currentTime = System.currentTimeMillis();
		if (user.isPresent()) {
			if (user.get().getOtp().equals(users.getOtp())) {
				if (otpTime > currentTime) {
					return new ResponseEntity<>("OTP Matched...", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Your OTP is Expired , Generate a new OTP...", HttpStatus.OK);
				}
			} else {
					return new ResponseEntity<>("Invalid OTP...", HttpStatus.OK);
			}
		} else {
					return new ResponseEntity<>("User Not Found...", HttpStatus.NOT_FOUND);
		}
	}
}
