package br.mhm.passwordmanagerapi.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
    @Autowired private Environment env;
    
    public String generateToken(UUID userId, String userEmail) {
        Date issueDate = new Date();
        Date expirationDate = new Date(issueDate.getTime() +
                Integer.parseInt(env.getProperty("jwt.expiration.time")));

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userId);

        if (userEmail != null) {
            claims.put("email", userEmail);
        }

        return Jwts.builder()
                .setSubject(userId.toString())
                .setClaims(claims)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, "84554d5c-3072-11ec-8d3d-0242ac130003")
                .compact();
    }

}
