package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dao.UserInfoRepository;
import com.dmdd.revenuemanagement.dto.*;
import com.dmdd.revenuemanagement.entity.UserInfo;
import com.dmdd.revenuemanagement.jwt.JwtGeneration;
import com.dmdd.revenuemanagement.service.IUserService;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;


@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class LoginRegisterController {
    private final AuthenticationManager authenticationManager;
    private IUserService iUserService;
    private UserInfoRepository userInfoRepository;
    private JwtGeneration jwtGeneration;

    @PostMapping("/login")
    public ResponseEntity LoginUser(@RequestBody LoginDTO loginDTO) throws UnsupportedEncodingException {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
        String jwt=jwtGeneration.tokenJwt(authentication,loginDTO.getEmail());
        if(!Strings.isNullOrEmpty(jwt))
        {
            LoginSuccessDTO loginSuccessDTO=new LoginSuccessDTO();
            loginSuccessDTO.setToken(jwt);
            loginSuccessDTO.setEmail(loginDTO.getEmail());
            return ResponseEntity.ok(loginSuccessDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/register")
    public ResponseEntity RegisterUser(@RequestBody RegisterDTO registerDTO) {
        boolean checkRegister=iUserService.RegisterUser(registerDTO);
        if(checkRegister)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/confirm")
    public ResponseEntity ConfirmEmail(String token)
    {
        UserInfo userInfo= userInfoRepository.findByToken_confirm_email(token);
        if(userInfo!=null)
        {
            userInfo.setConfirm_email(true);
            userInfo.setToken_email("");
            userInfo.setStatus(true);
            userInfo.setDate_Updated(new Date());
            userInfoRepository.save(userInfo);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/forgotpassword")
    public  ResponseEntity ForgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO)
    {
        boolean check= iUserService.ForgotPassword(forgotPasswordDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/updatedefault")
    public ResponseEntity UpdateDefaultPassword(String token)
    {
        boolean check= iUserService.UpdateDefaultPassword(token);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}