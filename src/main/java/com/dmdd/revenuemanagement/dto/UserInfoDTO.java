package com.dmdd.revenuemanagement.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserInfoDTO {
    private String fullName;

    private String email;

    private String image_url;

    private float balance;

    private float incomeAmount;

    private float spendingAmount;

    private String currency;
}
