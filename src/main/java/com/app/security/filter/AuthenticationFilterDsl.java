package com.app.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

// this was another solution to access the authenticationManager from local
// I used the authLanager in the constructor of securityFilterChain which is cool !
public class AuthenticationFilterDsl extends AbstractHttpConfigurer<AuthenticationFilterDsl, HttpSecurity>{
	   
		@Override
	    public void configure(HttpSecurity http) throws Exception {
	        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
	        http.addFilter(new JwtAuthenticationFilter(authenticationManager));
	    }

	    public static AuthenticationFilterDsl authenticationFilterDsl() {
	        return new AuthenticationFilterDsl();
	    }
}
