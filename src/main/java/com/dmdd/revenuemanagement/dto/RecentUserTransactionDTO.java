package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class RecentUserTransactionDTO {
    private String dateOfCreated;
    private float amount;
    private String currency;
    private float rateExchangeToVND;
//    0 la spend, 1 la income
    private boolean spend_or_income;
    private Long typeId;
    private String nameType;
}
