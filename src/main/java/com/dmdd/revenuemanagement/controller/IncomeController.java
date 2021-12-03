package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dao.IncomeTypeRepository;
import com.dmdd.revenuemanagement.dao.SpendingTypeRepository;
import com.dmdd.revenuemanagement.dto.*;
import com.dmdd.revenuemanagement.entity.Type_Income;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import com.dmdd.revenuemanagement.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/income")
public class IncomeController {

    private IncomeTypeRepository incomeTypeRepository;
    private IUserService iUserService;
    private SpendingTypeRepository spendingTypeRepository;

    private ObjectMapper objectMapper;

    @PostMapping("/addtype")
    public ResponseEntity AddTypeIncome(@RequestBody IncomeAddTypeDTO incomeAddTypeDTO) throws UnsupportedEncodingException {
        Type_Income type_income=new Type_Income();
        type_income.setName(incomeAddTypeDTO.getName());
        incomeTypeRepository.save(type_income);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addincome")
    public ResponseEntity AddUserIncome(@RequestBody UserAddIncomeDTO userAddIncomeDTO) throws IOException {
        boolean check=iUserService.AddIncomeForUser(userAddIncomeDTO);
        if(check)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/addtypespending")
    public ResponseEntity AddTypeIncome(@RequestBody SpendingAddTypeDTO spendingAddTypeDTO) throws UnsupportedEncodingException {
        Type_Spending type_spending=new Type_Spending();
        type_spending.setName(spendingAddTypeDTO.getName());
        spendingTypeRepository.save(type_spending);
        return ResponseEntity.ok().build();
    }
}
