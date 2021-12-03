package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.dao.MainTypeSpendingRepository;
import com.dmdd.revenuemanagement.dao.SpendingTypeRepository;
import com.dmdd.revenuemanagement.dto.ObjectNameDTO;
import com.dmdd.revenuemanagement.dto.SpendingAddTypeDTO;
import com.dmdd.revenuemanagement.entity.Main_Type_Spending;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TypeSpendingServiceImpl implements ITypeSpendingService{
    private MainTypeSpendingRepository mainTypeSpendingRepository;
    private SpendingTypeRepository spendingTypeRepository;

    @Override
    public boolean AddMainTypeSpending(ObjectNameDTO objectNameDTO) {
        Main_Type_Spending main_type_spending=new Main_Type_Spending();
        main_type_spending.setName(objectNameDTO.getName());
        mainTypeSpendingRepository.save(main_type_spending);
        return true;
    }

    @Override
    public boolean AddNameTypeSpendingChild(SpendingAddTypeDTO spendingAddTypeDTO) {
        Main_Type_Spending main_type_spending=mainTypeSpendingRepository.findByMainTypeSpendingId(spendingAddTypeDTO.getId_main_type());
        if(main_type_spending==null)
            return false;
        Type_Spending type_spending=new Type_Spending();
        type_spending.setName(spendingAddTypeDTO.getName());
        main_type_spending.AddTypeSpending(type_spending);
        spendingTypeRepository.save(type_spending);
        return true;
    }
}
