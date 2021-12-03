package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class HistoryUserAddAdmountTargetDTO {
    private String name_target;
    private String date_deposit;
    private float amount;
    private float amount_to_vnd;
    private String currency;
    private float rate_of_time_add;
}
