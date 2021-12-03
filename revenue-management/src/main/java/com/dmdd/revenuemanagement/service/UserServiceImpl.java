package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.common.DateFormat;
import com.dmdd.revenuemanagement.dao.*;
import com.dmdd.revenuemanagement.dto.*;
import com.dmdd.revenuemanagement.entity.*;
import com.dmdd.revenuemanagement.sendemail.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService{

    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private UserInfoRepository userInfoRepository;
    private IncomeTypeRepository incomeTypeRepository;
    private UserIncomeRepository userIncomeRepository;
    private SpendingTypeRepository spendingTypeRepository;
    private UserSpendingRepository userSpendingRepository;
    private TargetRepository targetRepository;
    private UserAddAmountTargetRepository userAddAmountTargetRepository;
    private DateFormat dateFormat;
    private MainTypeIncomeRepository mainTypeIncomeRepository;
    private MainTypeSpendingRepository mainTypeSpendingRepository;

    @Override
    public boolean RegisterUser(RegisterDTO registerDTO) {
        UserInfo userInfo=new UserInfo();
        UserInfo userInfoInDb=userInfoRepository.findByEmail(registerDTO.getEmail());
        if(userInfoInDb!=null)
            return false;
        userInfo.setEmail(registerDTO.getEmail());
        userInfo.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userInfo.setToken_email(uuid.toString());
        userInfo.setDate_Created(new Date());
        userInfoRepository.save(userInfo);
        String link="http://localhost:8585/user/confirm?token="+uuid;
        emailSenderService.sendSimpleMessage(registerDTO.getEmail(),"xac nhan dang ky tai ngok ngek boy",link);
        return true;
    }

    @Override
    public boolean AddIncomeForUser(UserAddIncomeDTO userAddIncomeDTO) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        Type_Income type_income=incomeTypeRepository.findByTypeInComeID(userAddIncomeDTO.getIncomeid());
        if(userInfo==null || type_income==null)
            return false;
        User_Income user_income=new User_Income();
        user_income.setBalance(userAddIncomeDTO.getAmout());
        user_income.setCurrency(userAddIncomeDTO.getCurrency());
        user_income.setDescription(userAddIncomeDTO.getDescription());
        user_income.setDate_Created(new Date());
        if(!userAddIncomeDTO.getCurrency().equals("VND"))
        {
            float rateExchange=ConvertCurrency(userAddIncomeDTO.getCurrency());
            user_income.setRate_balance_to_vnd(rateExchange);
            userInfo.setBalance(userInfo.getBalance() +userAddIncomeDTO.getAmout()*rateExchange);
        }
        else
        {
            user_income.setRate_balance_to_vnd(1);
            userInfo.setBalance(userInfo.getBalance() +userAddIncomeDTO.getAmout());
        }
        user_income.setBalance(user_income.getBalance());
        type_income.AddIncome(user_income);
        userInfo.AddIncome(user_income);
        user_income.setStatus(true);
        userIncomeRepository.save(user_income);
        return true;
    }

    @Override
    public boolean AddSpendingForUser(UserAddSpendingDTO userAddSpendingDTO) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        Type_Spending type_spending= spendingTypeRepository.findByTypeSpendingId(userAddSpendingDTO.getSpendingid());
        if(userInfo==null || type_spending==null)
            return false;
        User_Spending user_spending=new User_Spending();
        if(!userAddSpendingDTO.getCurrency().equals("VND"))
        {
            float rateExchange=ConvertCurrency(userAddSpendingDTO.getCurrency());
            user_spending.setRate_balance_to_vnd(rateExchange);
            userInfo.setBalance(userInfo.getBalance()- userAddSpendingDTO.getAmout()*rateExchange);
        }
        else
        {
            user_spending.setRate_balance_to_vnd(1);
            userInfo.setBalance(userInfo.getBalance() -userAddSpendingDTO.getAmout());
        }
        user_spending.setDate_Created(new Date());
        user_spending.setBalance(userAddSpendingDTO.getAmout());
        user_spending.setCurrency(userAddSpendingDTO.getCurrency());
        userInfo.AddSpending(user_spending);
        type_spending.AddSpending(user_spending);
        user_spending.setStatus(true);
        userSpendingRepository.save(user_spending);
        return true;
    }

    @Override
    public float ConvertCurrency(String currency) throws IOException {
        URL url = new URL("https://free.currconv.com/api/v7/convert?q="+currency.toUpperCase(Locale.ROOT)+"_VND&compact=ultra&apiKey=560461275d73930bc0ab");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.getResponseMessage();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String data=content.substring(content.toString().indexOf(":")+1,content.length()-1);
        float rateExchangeCurrencyToVND=Float.parseFloat(data);
        return rateExchangeCurrencyToVND;
    }

    @Override
    public UserInfoDTO ShowInforDTO(String currency) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        float rate=1;
        if(currency.toUpperCase(Locale.ROOT).equals("VND"))
        {
            userInfoDTO.setBalance(userInfo.getBalance());
        }
        else
        {
            rate=ConvertCurrency(currency.toLowerCase());
            userInfoDTO.setBalance(userInfo.getBalance()/rate);
        }

        userInfoDTO.setEmail(userInfo.getEmail());
        int month=new Date().getMonth()+1;
        int year=new Date().getYear();
        List<User_Spending> user_spendingList=userSpendingRepository.findByUserId(userInfo.getId());
        List<User_Income>user_incomeList=userIncomeRepository.findByInComeID(userInfo.getId());
        float totalIncome=0;
        float toalSpending=0;
        for(User_Income data:user_incomeList)
        {
            if(data.isStatus())
            {
                if(month==data.getDate_Created().getMonth()+1 && year==data.getDate_Created().getYear())
                {
                    totalIncome=totalIncome+data.getBalance()*data.getRate_balance_to_vnd();
                }
            }
        }
        for(User_Spending data:user_spendingList)
        {
            if(data.isStatus())
            {
                if(month==data.getDate_Created().getMonth()+1 && year==data.getDate_Created().getYear())
                {
                    toalSpending=toalSpending+data.getBalance()*data.getRate_balance_to_vnd();
                }
            }
        }
        userInfoDTO.setIncomeAmount(totalIncome/rate);
        userInfoDTO.setSpendingAmount(toalSpending/rate);
        userInfoDTO.setImage_url(userInfoDTO.getImage_url());
        userInfoDTO.setCurrency(currency);
        userInfoDTO.setFullName(userInfo.getFullName());
        return userInfoDTO;
    }

    @Override
    public UserIncomeIdDTO ShowInforDTOIncomeId(String currency, Long incomeId) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return null;
        Type_Income type_income=incomeTypeRepository.findByTypeInComeID(incomeId);
        if(type_income==null)
            return null;
        float rate=1;
        if(currency.toUpperCase(Locale.ROOT).equals("VND"))
        {
            rate=1;
        }
        else
        {
            rate=ConvertCurrency(currency.toLowerCase());
        }
        UserIncomeIdDTO userIncomeIdDTO=new UserIncomeIdDTO();
        userIncomeIdDTO.setNameIncome(type_income.getName());
        List<User_Income>user_incomeList=userIncomeRepository.findByIncomeIdAndUserId(incomeId,userInfo.getId());
        int month=new Date().getMonth()+1;
        int year=new Date().getYear();
        float total=0;
        for(User_Income data:user_incomeList)
        {
            if(data.isStatus())
            {
                if(data.getDate_Created().getMonth()+1==month && data.getDate_Created().getYear()==year)
                {
                    total=total+data.getBalance()*data.getRate_balance_to_vnd();
                }
            }
        }
        userIncomeIdDTO.setBalance(total/rate);
        return userIncomeIdDTO;
    }

    @Override
    public UserSpendIdDTO ShowInforDTOSpendId(String currency, Long spendId) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return null;
        Type_Spending type_spending= spendingTypeRepository.findByTypeSpendingId(spendId);
        if(type_spending==null)
            return null;
        UserSpendIdDTO userSpendIdDTO=new UserSpendIdDTO();
        float rate=1;
        if(currency.toUpperCase(Locale.ROOT).equals("VND"))
        {
            rate=1;
        }
        else
        {
            rate=ConvertCurrency(currency.toLowerCase());
        }
        int month=new Date().getMonth()+1;
        int year=new Date().getYear();
        float total=0;
        List<User_Spending>user_spendingList=userSpendingRepository.findBySpendingIdAndUserId(spendId,userInfo.getId());
        for(User_Spending data:user_spendingList)
        {
            if(data.isStatus())
            {
                if(data.getDate_Created().getMonth()+1==month && data.getDate_Created().getYear()==year)
                {
                    total=total+data.getBalance()*data.getRate_balance_to_vnd();
                }
            }
        }
        userSpendIdDTO.setBalance(total/rate);
        userSpendIdDTO.setNameSpending(type_spending.getName());
        return userSpendIdDTO;
    }

    @Override
    public boolean UpdatePassword(UpdatePasswordDTO updatePasswordDTO) {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return false;
        if(passwordEncoder.matches(updatePasswordDTO.getOldpass(),userInfo.getPassword()))
        {
            userInfo.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewpass()));
            userInfoRepository.save(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean ForgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        UserInfo userInfo=userInfoRepository.findByEmail(forgotPasswordDTO.getEmail());
        if(userInfo==null)
            return false;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userInfo.setToken_email(uuid.toString());
        userInfoRepository.save(userInfo);
        String link="http://localhost:8585/user/updatedefault?token="+uuid;
        emailSenderService.sendSimpleMessage(forgotPasswordDTO.getEmail(),"Click link de cap nhat mat khau sang mat khau mac dinh: 123456",link);
        return true;
    }

    @Override
    public boolean UpdateDefaultPassword(String token) {
        UserInfo userInfo=userInfoRepository.findByToken_confirm_email(token);
        if(userInfo==null)
            return false;
        userInfo.setPassword(passwordEncoder.encode("123456"));
        userInfo.setToken_email("");
        userInfoRepository.save(userInfo);
        return true;
    }

    @Override
    public List<RecentUserTransactionDTO> GetRecentTransaction() {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return null;
        List<User_Spending>user_spendingList=userSpendingRepository.findAllUserSpendingOrderDateDesc(userInfo.getId(),true);
        List<User_Income>user_incomeList=userIncomeRepository.findAllUserIncomeOrderDateDesc(userInfo.getId(),true);
        List<RecentUserTransactionDTO>recentUserTransactionDTOS=new ArrayList<>();
        int i=0,j=0;
        for(User_Spending data:user_spendingList)
        {
            RecentUserTransactionDTO recentUserTransactionDTO=new RecentUserTransactionDTO();
            recentUserTransactionDTO.setNameType(data.getType_spending().getName());
            recentUserTransactionDTO.setTypeId(data.getType_spending().getId());
            recentUserTransactionDTO.setCurrency(data.getCurrency());
            recentUserTransactionDTO.setRateExchangeToVND(data.getRate_balance_to_vnd());
            recentUserTransactionDTO.setDateOfCreated(data.getDate_Created().toString());
            recentUserTransactionDTO.setAmount(data.getBalance());
            recentUserTransactionDTO.setSpend_or_income(false);
            recentUserTransactionDTO.setDescription("");
            recentUserTransactionDTOS.add(recentUserTransactionDTO);
        }
        for(User_Income data:user_incomeList)
        {
            RecentUserTransactionDTO recentUserTransactionDTO=new RecentUserTransactionDTO();
            recentUserTransactionDTO.setNameType(data.getType_income().getName());
            recentUserTransactionDTO.setTypeId(data.getType_income().getId());
            recentUserTransactionDTO.setCurrency(data.getCurrency());
            recentUserTransactionDTO.setRateExchangeToVND(data.getRate_balance_to_vnd());
            recentUserTransactionDTO.setDateOfCreated(data.getDate_Created().toString());
            recentUserTransactionDTO.setAmount(data.getBalance());
            recentUserTransactionDTO.setSpend_or_income(true);
            recentUserTransactionDTO.setDescription(data.getDescription());
            recentUserTransactionDTOS.add(recentUserTransactionDTO);
        }
        recentUserTransactionDTOS=recentUserTransactionDTOS.stream().sorted((data1,data2)->data2.getDateOfCreated().compareTo(data1.getDateOfCreated())).collect(Collectors.toList());
        return recentUserTransactionDTOS;
    }

    @Override
    public boolean UserAddTarget(UserAddTargetDTO userAddTargetDTO) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return false;
        Target target=new Target();
        target.setDate_created(new Date());
        if(userAddTargetDTO.getDate_end().length()>4)
        {
            target.setDate_end(dateFormat.ConvertStringToDate(userAddTargetDTO.getDate_end()));
        }
        target.setCurrency(userAddTargetDTO.getCurrency());
        if(userAddTargetDTO.getCurrency().equals("VND"))
        {
            target.setRate(1);
            target.setTotal(userAddTargetDTO.getTotal());
        }
        else
        {
            float rate=ConvertCurrency(userAddTargetDTO.getCurrency());
            target.setRate(rate);
            target.setTotal(userAddTargetDTO.getTotal() * rate);
        }
        target.setDescription(userAddTargetDTO.getDescription());
        target.setName(userAddTargetDTO.getName());
        userInfo.AddTarget(target);
        targetRepository.save(target);
        return true;
    }

    @Override
    public boolean UserAddAmountTarget(UserAddAmountTargetDTO userAddAmountTargetDTO) throws IOException {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return false;
        Target target=targetRepository.findByTargetId(userAddAmountTargetDTO.getTargetid());
        if(target==null)
        {
            return false;
        }
        UserAddAmountTarget userAddAmountTarget=new UserAddAmountTarget();
        userAddAmountTarget.setCurrency(userAddAmountTargetDTO.getCurrency());
        if(userAddAmountTargetDTO.getCurrency().equals("VND"))
        {
            userAddAmountTarget.setRate(1);
            userAddAmountTarget.setAmount(userAddAmountTargetDTO.getAmount());
            userInfo.setBalance(userInfo.getBalance()- userAddAmountTarget.getAmount());
        }
        else
        {
            float rate=ConvertCurrency(userAddAmountTargetDTO.getCurrency());
            userAddAmountTarget.setRate(rate);
            userAddAmountTarget.setAmount(userAddAmountTargetDTO.getAmount()*rate);
            userInfo.setBalance(userInfo.getBalance()-userAddAmountTarget.getAmount());
        }
        userAddAmountTarget.setDate_created(new Date());
        userAddAmountTarget.setDescription(userAddAmountTargetDTO.getDescription());
        target.setCurrent(target.getCurrent()+ userAddAmountTargetDTO.getAmount());
        target.AddUserAmountTarget(userAddAmountTarget);
        userAddAmountTargetRepository.save(userAddAmountTarget);
        return true;
    }

    @Override
    public List<HistoryUserAddAdmountTargetDTO> GetAllHistoryUserAddAmountTarget() {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return null;
        List<UserAddAmountTarget>userAddAmountTargetList=userAddAmountTargetRepository.findUserAddAmountTargetByUserId(userInfo.getId(),true);
        List<HistoryUserAddAdmountTargetDTO>historyUserAddAdmountTargetDTOS=new ArrayList<>();
        for(UserAddAmountTarget data:userAddAmountTargetList)
        {
            HistoryUserAddAdmountTargetDTO historyUserAddAdmountTargetDTO=new HistoryUserAddAdmountTargetDTO();
            historyUserAddAdmountTargetDTO.setAmount(data.getAmount());
            historyUserAddAdmountTargetDTO.setCurrency(data.getCurrency());
            historyUserAddAdmountTargetDTO.setDate_deposit(data.getDate_created().toString());
            historyUserAddAdmountTargetDTO.setRate_of_time_add(data.getRate());
            historyUserAddAdmountTargetDTO.setName_target(data.getTarget().getName());
            historyUserAddAdmountTargetDTO.setAmount_to_vnd(data.getAmount()*data.getRate());
            historyUserAddAdmountTargetDTO.setTotal(data.getTarget().getTotal());
            historyUserAddAdmountTargetDTO.setTotal_to_vnd(data.getRate()* historyUserAddAdmountTargetDTO.getTotal());
            historyUserAddAdmountTargetDTOS.add(historyUserAddAdmountTargetDTO);
        }
        return historyUserAddAdmountTargetDTOS;
    }

    @Override
    public List<HistoryTargetDTO> GetHistoryTarget() {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo==null)
            return null;
        List<HistoryTargetDTO>historyTargetDTOS=new ArrayList<>();
        List<Target>targets=targetRepository.findTargetByUserId(userInfo.getId());
        for(Target data:targets)
        {
            HistoryTargetDTO historyTargetDTO=new HistoryTargetDTO();
            historyTargetDTO.setCurrency(data.getCurrency());
            historyTargetDTO.setCurrent(data.getCurrent());
            historyTargetDTO.setDescription(data.getDescription());
            historyTargetDTO.setDate_created(data.getDate_created().toString());
            historyTargetDTO.setDate_end(data.getDate_end().toString());
            historyTargetDTO.setName(data.getName());
            historyTargetDTO.setId(data.getId());
            historyTargetDTO.setRate(data.getRate());
            historyTargetDTO.setTotal(data.getTotal());


            historyTargetDTOS.add(historyTargetDTO);
        }
        return historyTargetDTOS;
    }

    @Override
    public List<MainType> GetAllType() {
        List<Main_Type_Income>main_type_incomes= mainTypeIncomeRepository.findAllTypeIncome();
        List<Main_Type_Spending>main_type_spendings= mainTypeSpendingRepository.findAllTypeSpending();
        List<MainType>mainTypeList=new ArrayList<>();
        for(Main_Type_Spending data:main_type_spendings)
        {
            MainType mainType=new MainType();
            mainType.setId(data.getId());
            mainType.setSpend_or_income(false);
            mainType.setImage_url(data.getImage_url());
            mainType.setName(data.getName());
            List<TypeDTO>typeDTOList=new ArrayList<>();
            for(Type_Spending dataInData:data.getType_spendingList())
            {
                TypeDTO typeDTO=new TypeDTO();
                typeDTO.setId(dataInData.getId());
                typeDTO.setName(dataInData.getName());
                typeDTO.setImage_url(dataInData.getImage_url());
                typeDTOList.add(typeDTO);
            }
            mainType.setTypeDTOList(typeDTOList);
            mainTypeList.add(mainType);
        }
        for(Main_Type_Income data:main_type_incomes)
        {
            MainType mainType=new MainType();
            mainType.setId(data.getId());
            mainType.setSpend_or_income(true);
            mainType.setImage_url(data.getImage_url());
            mainType.setName(data.getName());
            List<TypeDTO>typeDTOList=new ArrayList<>();
            for(Type_Income dataInData:data.getType_incomeList())
            {
                TypeDTO typeDTO=new TypeDTO();
                typeDTO.setId(dataInData.getId());
                typeDTO.setName(dataInData.getName());
                typeDTO.setImage_url(dataInData.getImage_url());
                typeDTOList.add(typeDTO);
            }
            mainType.setTypeDTOList(typeDTOList);
            mainTypeList.add(mainType);
        }
        return mainTypeList;
    }

    @Override
    public boolean UpdateUserName(String username) {
        UserInfo userInfo=userInfoRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName()));
        if(userInfo!=null)
        {
            userInfo.setFullName(username);
            userInfoRepository.save(userInfo);
            return true;
        }
        return false;
    }
}
