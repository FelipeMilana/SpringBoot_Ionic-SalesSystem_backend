package com.projects.SalesSystem.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.SalesSystem.security.JWTUtil;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping(value = "/refreshToken")
	public ResponseEntity<Void> refreshToken(HttpServletResponse res) {
		UserSS authUser = userService.getAuthenticatedUser();
		String token = jwtUtil.generateToken(authUser.getUsername());
		res.addHeader("Authorization", "Bearer " + token);
		res.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.ok().build();
	}
}
