package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class UserAddTargetDTO {
    private String name;
    private String currency;
    private float total;
    private String date_end;
    private String description;
}
