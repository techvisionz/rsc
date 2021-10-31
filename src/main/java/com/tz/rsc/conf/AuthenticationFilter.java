package com.tz.rsc.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	int tokenValidityMs = 1200000;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			com.tz.rsc.entities.User creds = new ObjectMapper().readValue(request.getInputStream(), com.tz.rsc.entities.User.class);			
			
			System.out.println("Authenticatting.... " + creds.getUsername() + " = " + creds.getPassword());
			
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),new ArrayList<>()));
			
			return auth;
		} catch (IOException e) {
			System.out.println("Failed.... ");
			throw new RuntimeException("Could not read request" + e);
		} catch (Exception e) {
			System.out.println("Failed.... " + e.getMessage());
			throw new RuntimeException("Could not read request" + e);
		}
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) {
		String token = Jwts.builder().setSubject(((User) authentication.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + tokenValidityMs))
				.signWith(SignatureAlgorithm.HS256, "SecretKeyToGenJWTs".getBytes()).compact();
		response.addHeader("Authorization", "Bearer " + token);
	}
}