package com.alten.practica.service.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
    private static final String SECRET_KEY="586E32723575387996255454572B4B625065536850502155454";


	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user);
	}
	
	 private String getToken(Map<String,Object> extraClaims, UserDetails user) {
	        return Jwts
	            .builder()
	            .setClaims(extraClaims)
	            .setSubject(user.getUsername())
	            .setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
	            .signWith(getKey(), SignatureAlgorithm.HS256)
	            .compact();
	    }
	 
	 private Key getKey() {
	       byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
	       return Keys.hmacShaKeyFor(keyBytes);
	    }


}
