package com.trieka.usermanagement.security.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.trieka.usermanagement.security.TokenProvider;
import com.trieka.usermanagement.service.IUserSecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProvider{
	
	public static String AUTH_HEADER_NAME = "Authorization";
	public static String AUTH_TOKEN_PREFIX = "Bearer ";
	
	@Autowired
	private IUserSecurityService userSecurityService;
	
	@Setter
	private String secretKey;
	
	@Setter
	private long jwtValidityInMinutes;

	@Override
	public String createToken(String phone, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(phone);
		claims.put("roles", roles);
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expiryDate = now.plusMinutes(jwtValidityInMinutes);
		
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	@Override
	public Authentication validateToken(String token) {
		log.info("FALIDATE TOKEN");
		try {
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			String username = claims.getBody().getSubject();
			
			UserDetails userDetails = userSecurityService.findUserDetailsByUsername(username);
			
			Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
			return new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Authentication failed");
		}
	}
	
	@Override
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTH_HEADER_NAME);
		if (bearerToken != null && bearerToken.startsWith(AUTH_TOKEN_PREFIX)) {
			return bearerToken.substring(AUTH_TOKEN_PREFIX.length(), bearerToken.length());
		}
		return null;
	}

}
