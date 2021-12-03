package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class UserAddAmountTargetDTO {
    private Long targetid;
    private String currency;
    private float amount;
    private String description;
}
