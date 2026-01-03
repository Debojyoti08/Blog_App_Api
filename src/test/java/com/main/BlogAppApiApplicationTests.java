package com.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.main.repository.UserRepository;

@SpringBootTest
class BlogAppApiApplicationTests {
	
	@Autowired
	private UserRepository userRepo;

	@Test
	void contextLoads() {
	}
	
	public void repoTest() {
		String classname = this.userRepo.getClass().getName();
		String packName = this.userRepo.getClass().getPackageName();
		System.out.println(classname);
		System.out.println(packName);
	}

}
