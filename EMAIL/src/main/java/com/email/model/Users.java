package com.email.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private Long contact;
	private Long otp;

	@CreationTimestamp
	private Date otpCreatedOn;

	public Date getOtpCreatedOn() {
		return otpCreatedOn;
	}

	public void setOtpCreatedOn(Date otpCreatedOn) {
		this.otpCreatedOn = otpCreatedOn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public Long getOtp() {
		return otp;
	}

	public void setOtp(Long otp) {
		this.otp = otp;
	}

}
