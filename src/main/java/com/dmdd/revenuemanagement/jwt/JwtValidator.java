package com.dmdd.revenuemanagement.jwt;


import com.dmdd.revenuemanagement.dao.UserInfoRepository;
import com.dmdd.revenuemanagement.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JwtValidator extends OncePerRequestFilter {
    private Environment environment;
    private UserInfoRepository userInfoRepository;
    @Autowired
    public JwtValidator(Environment environment, UserInfoRepository userInfoRepository)
    {
        this.environment=environment;
        this.userInfoRepository=userInfoRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("Authorization");
        if(token!=null)
        {
            String key=environment.getProperty("token.secret");
            SecretKey secretKey= Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            Claims claims= Jwts.parser().setSigningKey(key.getBytes(Charset.forName("UTF-8")))
                    .parseClaimsJws(token)
                    .getBody();
            String email=(String)claims.get("email");
            String role=(String)claims.get("role");
            UserInfo userInfo=userInfoRepository.findByEmail(email);
            if( userInfo!=null)
            {
                if(userInfo.isStatus())
                {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email,null, AuthorityUtils.commaSeparatedStringToAuthorityList(role)));
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
