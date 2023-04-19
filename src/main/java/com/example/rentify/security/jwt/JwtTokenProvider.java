package com.example.rentify.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validityInMinutes}")
    private long tokenValidityInMinutes;

    @Value("${jwt.refreshValidityInMinutes}")
    private long refreshTokenValidityInMinutes;

    private static final String AUTHORITIES_KEY = "auth";

    /**
     * Create token from authentication
     *
     * @param authentication authentication object
     * @return String as token
     */
    public Map<String, String> createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // (ROLE_USER,ROLE_ADMIN,ROLE_REGISTERED)
        long now = new Date().getTime();
        Date tokenValidity = new Date(now + tokenValidityInMinutes * 60_000);
        Date refreshTokenValidity = new Date(now + refreshTokenValidityInMinutes * 60_000);
        Map<String, String> map = new HashMap<>();
        String token = generateToken(authentication, authorities, tokenValidity, "access");
        String refreshToken = generateToken(authentication, authorities, refreshTokenValidity, "refresh");
        map.put("refresh-token", refreshToken);
        map.put("token", token);
        return map;
    }

    public String refreshToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // (ROLE_USER,ROLE_ADMIN,ROLE_REGISTERED)
        long now = new Date().getTime();
        Date tokenValidity = new Date(now + tokenValidityInMinutes * 60_000);
        return generateToken(authentication, authorities, tokenValidity, "access");
    }

    private String generateToken(Authentication authentication, String authorities, Date validity, String tokenType) {
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("token-type", tokenType)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * Create authentication from token
     *
     * @param token jwt token
     * @return Authentication Object
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        String principal = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * Validate token
     *
     * @param authToken jwt token
     * @return boolean value (is valid | not)
     */
    public boolean validateToken(String authToken, String type) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
            String tokenType = (String) claims.get("token-type");
            return type.equals(tokenType);
        } catch (JwtException e) {// token is invalid or has expired
            return false;
        }
    }

    public String getTokenUser(String authToken) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken).getBody();
        return (String) claims.get("sub");
    }
}