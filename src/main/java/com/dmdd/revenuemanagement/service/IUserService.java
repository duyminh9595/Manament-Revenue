package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.dto.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface IUserService {
    boolean RegisterUser(RegisterDTO registerDTO);
    boolean AddIncomeForUser(UserAddIncomeDTO userAddIncomeDTO) throws IOException;
    boolean AddSpendingForUser(UserAddSpendingDTO userAddSpendingDTO) throws IOException;
    float ConvertCurrency(String currency) throws IOException;
    UserInfoDTO ShowInforDTO(String currency) throws IOException;
    UserIncomeIdDTO ShowInforDTOIncomeId(String currency,Long incomeId) throws IOException;
    UserSpendIdDTO ShowInforDTOSpendId(String currency, Long spendId) throws IOException;
    boolean UpdatePassword(UpdatePasswordDTO updatePasswordDTO);
    boolean ForgotPassword(ForgotPasswordDTO forgotPasswordDTO);
    boolean UpdateDefaultPassword(String token);

    List<RecentUserTransactionDTO>GetRecentTransaction();

    boolean UserAddTarget(UserAddTargetDTO userAddTargetDTO ) throws IOException;
    boolean UserAddAmountTarget(UserAddAmountTargetDTO userAddAmountTargetDTO) throws IOException;

    List<HistoryUserAddAdmountTargetDTO>GetAllHistoryUserAddAmountTarget();

}
