package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class UserAddSpendingDTO {
    private Long spendingid;
    private String currency;
    private float amout;
    private String description;
}
