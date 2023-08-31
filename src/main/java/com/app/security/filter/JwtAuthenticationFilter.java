package com.app.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.security.CustomUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	 public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
	      this.authenticationManager = authenticationManager;
	      setFilterProcessesUrl("/api/login");
	   }
	 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("attemptAuthentication");
        Map<String, String> requestMap;
        String username = null , password = null;
		try {
			requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
			username = requestMap.get("username");
			password = requestMap.get("password");
		} catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
		} 
		//this is form parameters 
		//String username = request.getParameter("username");
		//String password = request.getParameter("password");
		System.out.println("username : " + username + " and Password : " + password);
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(upat);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		
		CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
		Algorithm alg = Algorithm.HMAC256("test");
		String jwtAccessToken = JWT.create()
								.withSubject(user.getUsername())
								.withIssuer("auth app")
								.withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
								.withClaim("roles", user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList()))
								.sign(alg);
		
		String jwtRefreshToken = JWT.create()
				.withSubject(user.getUsername())
				.withIssuer("auth app")
				.withExpiresAt(new Date(System.currentTimeMillis()+20*60*1000))
				.sign(alg);
		
		Map<String, String> jwtToken = new HashMap<>();
		jwtToken.put("access token", jwtAccessToken);
		jwtToken.put("refresh token", jwtRefreshToken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), jwtToken);
	}

}
