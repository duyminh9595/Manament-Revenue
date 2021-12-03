package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dto.ObjectNameDTO;
import com.dmdd.revenuemanagement.dto.SpendingAddTypeDTO;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import com.dmdd.revenuemanagement.service.ITypeSpendingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/addmainspending")
public class TypeSpendingController {

    private ITypeSpendingService iTypeSpendingService;

    @PostMapping("/addname")
    public ResponseEntity AddNameMainSpending(@RequestBody ObjectNameDTO objectNameDTO)
    {
        boolean check=iTypeSpendingService.AddMainTypeSpending(objectNameDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/addtypespending")
    public ResponseEntity AddTypeIncome(@RequestBody SpendingAddTypeDTO spendingAddTypeDTO) throws UnsupportedEncodingException {
        boolean check= iTypeSpendingService.AddNameTypeSpendingChild(spendingAddTypeDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
