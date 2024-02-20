package com.burhan.userorg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UserOrgApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserOrgApplication.class, args);
	}

	@PostConstruct
	public void run(){
		System.out.println("Encrypt Password for pass : ");
		System.out.println(this.passwordEncoder.encode("pass"));
		System.out.println("Encrypt Password for bad : ");
		System.out.println(this.passwordEncoder.encode("bad"));
		System.out.println("6 Testing proj");
	}

}
