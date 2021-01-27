package com.twendee.app.config;


import com.twendee.app.service.impl.UserDetailServiceImpl;
import com.twendee.app.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
	                                HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getJwtFromRequest(httpServletRequest);
			
			if (StringUtils.hasText(token)) {
//			lấy username từ JWT
				String usernameFromToken = jwtTokenProvider.getUsernameFromToken(token);

//			lấy thông tin user từ username
				UserDetails userDetails = userDetailService.loadUserByUsername(usernameFromToken);
				if (userDetails != null) {
//				Nếu user hợp lệ thì truyền vào cho SecurityContext
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authenticationToken);
					LOGGER.info("Authentication successfully");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error to set authentication", e);
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(Constant.HEADER_TOKEN);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.BEARER_TOKEN)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
