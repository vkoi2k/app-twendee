package com.twendee.app.controller;


import com.twendee.app.config.JwtTokenProvider;
import com.twendee.app.model.dto.JwtAuthenticationResponse;
import com.twendee.app.model.dto.LoginRequest;
import com.twendee.app.model.entity.CustomUserDetail;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		System.out.println("Auth" + loginRequest.toString());
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword())
				);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
		System.out.println("Auth"+authentication);
		Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
		User user = jwtTokenProvider.getUserFromToken(jwt);
		List<String> roles = new ArrayList<>();
		for(GrantedAuthority grantedAuthority : role) {
			roles.add(grantedAuthority.getAuthority());
		}
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, roles,user));
	}
}
