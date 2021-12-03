package com.dmdd.revenuemanagement.jwt;

import com.dmdd.revenuemanagement.dao.UserInfoRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtGeneration {
    private Environment environment;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public JwtGeneration(Environment environment, UserInfoRepository userInfoRepository) {
        this.environment = environment;
        this.userInfoRepository = userInfoRepository;
    }


    public String tokenJwt(Authentication authentication, String email) throws UnsupportedEncodingException {

        String key = environment.getProperty("token.secret");
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject("He thong ngok ngek")
                .claim("email", email)
                .claim("role","ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(Long.parseLong(environment.getProperty("token.expirationday")))))
                .signWith(SignatureAlgorithm.HS512, key.getBytes(Charset.forName("UTF-8")))
                .compact();
    }
}