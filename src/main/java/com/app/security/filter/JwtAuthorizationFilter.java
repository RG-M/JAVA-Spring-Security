package com.app.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtAuthorizationFilter extends OncePerRequestFilter{

	// The first filter to verify the validation of the JWT token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String headerToken = request.getHeader("Authorization");
		if(headerToken != null && headerToken.startsWith("bearer ")) {
			
			try {
				String token = headerToken.substring(7);
				Algorithm algorithm = Algorithm.HMAC256("test");
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(token);
				String username = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<GrantedAuthority> auths = new ArrayList<>();
				for (String r : roles) {
					auths.add(new SimpleGrantedAuthority(r));
				}
				
				UsernamePasswordAuthenticationToken upat = 
						new UsernamePasswordAuthenticationToken(username,null,auths);
				SecurityContextHolder.getContext().setAuthentication(upat);
				filterChain.doFilter(request, response);
				System.out.println("jwt success");
				
			} catch (Exception e) {
				System.out.println("jwt verif failed");
				response.setHeader("error", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			
		}
		// if no authorization is present in the header, move to the next filter
		// if the resource is public you can enter
		// if the resource is private spring will force you to authenticate
		else
		{
			System.out.println("header token null or not exist");
			filterChain.doFilter(request, response);
		}
					
	}

	
}
