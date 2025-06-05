package com.smartmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartmanager.services.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Test
	void contextLoads() {
	}
  @Autowired
  private EmailService emailService;

	@Test
	void sendEmailTest() {
		// This test is to check if the email service is working properly
		// EmailService emailService = new EmailServiceImpl();
		// emailService.sendEmail("
             emailService.sendEmail("kumarabhishekk16dec@gmail.com", "testing email service", "hey this is a test email to check if the email service is working properly");
	}

}
