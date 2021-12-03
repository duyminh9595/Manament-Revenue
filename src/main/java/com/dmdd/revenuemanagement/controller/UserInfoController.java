package com.dmdd.revenuemanagement.controller;

import com.dmdd.revenuemanagement.dto.*;
import com.dmdd.revenuemanagement.entity.Type_Income;
import com.dmdd.revenuemanagement.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/userinfo")
public class UserInfoController {
    private IUserService iUserService;

    @GetMapping("/index")
    public ResponseEntity GetInforBalance(String currency) throws IOException {
        UserInfoDTO userInfoDTO=iUserService.ShowInforDTO(currency);
        return ResponseEntity.ok(userInfoDTO);
    }

    @GetMapping("/spendingid")
    public ResponseEntity GetInforBalanceSpendIdNow(String currency,Long spendid) throws IOException {
        UserSpendIdDTO userSpendIdDTO=iUserService.ShowInforDTOSpendId(currency,spendid);
        return ResponseEntity.ok(userSpendIdDTO);
    }
    @GetMapping("/incomeid")
    public ResponseEntity GetInforBalanceComeIdNow(String currency,Long incomeid) throws IOException {
        UserIncomeIdDTO userIncomeIdDTO=iUserService.ShowInforDTOIncomeId(currency,incomeid);
        return ResponseEntity.ok(userIncomeIdDTO);
    }
    @PostMapping("/changepassworod")
    public ResponseEntity UpdatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO)
    {
        boolean check= iUserService.UpdatePassword(updatePasswordDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getrecenttransaction")
    public ResponseEntity GetRecentTransaction()
    {
        List<RecentUserTransactionDTO> recentUserTransactionDTOList= iUserService.GetRecentTransaction();
        return ResponseEntity.ok(recentUserTransactionDTOList);
    }
    @PostMapping("/addtarget")
    public ResponseEntity AddTarget(@RequestBody UserAddTargetDTO userAddTargetDTO) throws IOException {
        boolean check=iUserService.UserAddTarget(userAddTargetDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/addamounttarget")
    public ResponseEntity AddAmountTarget(@RequestBody UserAddAmountTargetDTO userAddAmountTargetDTO) throws IOException {
        boolean check=iUserService.UserAddAmountTarget(userAddAmountTargetDTO);
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/historyaddamountarget")
    public ResponseEntity HistoryAddAmountTarget()
    {
        List<HistoryUserAddAdmountTargetDTO>historyUserAddAdmountTargetDTOS=iUserService.GetAllHistoryUserAddAmountTarget();
        return ResponseEntity.ok(historyUserAddAdmountTargetDTOS);
    }
    @GetMapping("/historytarget")
    public ResponseEntity HistoryTarget()
    {
        List<HistoryTargetDTO>historyTargetDTOS=iUserService.GetHistoryTarget();
        return ResponseEntity.ok(historyTargetDTOS);
    }
    @GetMapping("/gettypeall")
    public ResponseEntity GetAllType()
    {
        List<MainType>mainTypeList=iUserService.GetAllType();
        return ResponseEntity.ok(mainTypeList);
    }
    @PostMapping("/updateusername")
    public ResponseEntity UpdateUsername(@RequestBody UpdateUsernameDTO updateUsernameDTO)
    {
        boolean check=iUserService.UpdateUserName(updateUsernameDTO.getUsername());
        if(check)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

}
