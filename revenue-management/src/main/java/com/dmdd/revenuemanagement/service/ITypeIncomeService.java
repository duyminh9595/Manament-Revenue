package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.dto.IncomeAddTypeDTO;
import com.dmdd.revenuemanagement.dto.ObjectNameDTO;

public interface ITypeIncomeService {
    boolean AddMainTypeIncome(ObjectNameDTO objectNameDTO);
    boolean AddTypeIncomeChild(IncomeAddTypeDTO incomeAddTypeDTO);
}
