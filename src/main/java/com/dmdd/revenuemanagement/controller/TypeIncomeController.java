package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dto.IncomeAddTypeDTO;
import com.dmdd.revenuemanagement.dto.ObjectNameDTO;
import com.dmdd.revenuemanagement.dto.SpendingAddTypeDTO;
import com.dmdd.revenuemanagement.service.ITypeIncomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/addmainincome")
public class TypeIncomeController {

    private ITypeIncomeService iTypeIncomeService;

    @PostMapping("/addname")
    public ResponseEntity AddNameMainIncome(@RequestBody ObjectNameDTO objectNameDTO)
    {
        boolean check=iTypeIncomeService.AddMainTypeIncome(objectNameDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping("/addtypeincome")
    public ResponseEntity AddTypeIncome(@RequestBody IncomeAddTypeDTO incomeAddTypeDTO) throws UnsupportedEncodingException {
        boolean check= iTypeIncomeService.AddTypeIncomeChild(incomeAddTypeDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
