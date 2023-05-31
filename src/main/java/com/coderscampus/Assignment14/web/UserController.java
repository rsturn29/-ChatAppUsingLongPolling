package com.coderscampus.Assignment14.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.Assignment14.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
	
	@PostMapping("/users_names")
	public void storeUser(@RequestBody User user, HttpServletRequest request) {
		String username = user.getUsername();
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
	}

}
