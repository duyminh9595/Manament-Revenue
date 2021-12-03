package com.dmdd.revenuemanagement.service;

import com.dmdd.revenuemanagement.dto.ObjectNameDTO;
import com.dmdd.revenuemanagement.dto.SpendingAddTypeDTO;

public interface ITypeSpendingService {
    boolean AddMainTypeSpending(ObjectNameDTO objectNameDTO);
    boolean AddNameTypeSpendingChild(SpendingAddTypeDTO spendingAddTypeDTO);
}
