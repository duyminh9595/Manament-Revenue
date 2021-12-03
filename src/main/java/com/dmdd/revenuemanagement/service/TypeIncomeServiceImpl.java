package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.dao.IncomeTypeRepository;
import com.dmdd.revenuemanagement.dao.MainTypeIncomeRepository;
import com.dmdd.revenuemanagement.dto.IncomeAddTypeDTO;
import com.dmdd.revenuemanagement.dto.ObjectNameDTO;
import com.dmdd.revenuemanagement.entity.Main_Type_Income;
import com.dmdd.revenuemanagement.entity.Type_Income;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TypeIncomeServiceImpl implements ITypeIncomeService{

    private MainTypeIncomeRepository mainTypeIncomeRepository;
    private IncomeTypeRepository  incomeTypeRepository;

    @Override
    public boolean AddMainTypeIncome(ObjectNameDTO objectNameDTO) {
        Main_Type_Income main_type_income=new Main_Type_Income();
        main_type_income.setName(objectNameDTO.getName());
        mainTypeIncomeRepository.save(main_type_income);
        return true;
    }

    @Override
    public boolean AddTypeIncomeChild(IncomeAddTypeDTO incomeAddTypeDTO) {
        Main_Type_Income main_type_income=mainTypeIncomeRepository.findByMainTypeIncomeId(incomeAddTypeDTO.getMain_type_income_id());
        if(main_type_income==null)
            return false;
        Type_Income type_income=new Type_Income();
        type_income.setName(incomeAddTypeDTO.getName());
        main_type_income.AddTypeIncome(type_income);
        incomeTypeRepository.save(type_income);
        return true;
    }
}
