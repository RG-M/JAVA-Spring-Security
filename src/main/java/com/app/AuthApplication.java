package com.app;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.entity.User;
import com.app.service.RoleService;
import com.app.service.UserService;


@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
//		SpringApplication.run(AuthApplication.class, args);
//		String[] array = {"abc", "2","cc", "10", "0","ab"};
//		List<String> list = Arrays.asList(array);
//		Collections.sort(list);
//		System.out.println(Arrays.toString(array));
//		
		
		
		
		
		
		
		
		
		
		User newUser1 = new User();
		
		newUser1.setFirstName("doaa");
		
		System.out.println(newUser1.showMe());
		
		User newUser2 = new User();

		
		System.out.println(newUser2.getFirstName());

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
//	@Bean
//	CommandLineRunner start(RoleService roleService, UserService userService) {
//		return args -> {
//			roleService.saveRole("User");			
//			roleService.saveRole("Admin");
//
//			User userAD = userService.addNewUser(
//					new User(null, "adminfirstname", "adminlastname","adminemail","admin", "admin", false, null));
//			userService.addRoleToUser(userAD.getId(), "Admin");
//			
//			for (int i = 0; i < 5; i++) {
//			User user = userService.addNewUser(
//					new User(null, "firstname"+i, "lastname"+i,"email"+i,"user"+i, "user"+i, false, null));
//			userService.addRoleToUser(user.getId(), "User");
//
//			}
//
//		};
//	}

}
