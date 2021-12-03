package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class UserAddIncomeDTO {
    private Long incomeid;
    private String currency;
    private float amout;
    private String description;
}
