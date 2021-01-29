package com.twendee.app.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.twendee.app.model.entity.CustomUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
	
	public String generateToken(CustomUserDetail userDetail) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + SecurityConstant.JWT_EXPIRATION);
		
		return JWT.create()
				.withSubject(userDetail.getUsername())
				.withExpiresAt(expiration)
				.sign(Algorithm.HMAC512(SecurityConstant.JWT_SECRET));
	}
	
	String getUsernameFromToken(String token) {
		return JWT.decode(token).getSubject();
	}
	
}
