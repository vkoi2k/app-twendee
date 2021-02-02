package com.twendee.app.model.dto;

import com.twendee.app.model.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private List<String> role;
	private User user;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public JwtAuthenticationResponse(String accessToken, List<String> role, User user){
		this.accessToken = accessToken;
		this.role = role;
		this.user = user;
	}
}
