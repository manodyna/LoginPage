package com.Dyna.personalDiary;

import com.Dyna.personalDiary.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PersonalDiaryApplication implements CommandLineRunner {

	private EmailService emailService;

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PersonalDiaryApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		emailService.prepareAndSendEmail();
	}

}
