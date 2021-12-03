package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dao.IncomeTypeRepository;
import com.dmdd.revenuemanagement.dao.SpendingTypeRepository;
import com.dmdd.revenuemanagement.dto.UserAddIncomeDTO;
import com.dmdd.revenuemanagement.dto.UserAddSpendingDTO;
import com.dmdd.revenuemanagement.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/spending")
public class SpendingControlle {
    private IncomeTypeRepository incomeTypeRepository;
    private IUserService iUserService;
    private SpendingTypeRepository spendingTypeRepository;

    private ObjectMapper objectMapper;

    @PostMapping("/addspending")
    public ResponseEntity AddUserSpending(@RequestBody UserAddSpendingDTO userAddSpendingDTO) throws IOException {
        boolean check=iUserService.AddSpendingForUser(userAddSpendingDTO);
        if(check)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
