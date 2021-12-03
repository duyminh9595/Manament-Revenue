package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class LoginSuccessDTO {
    private String email;
    private String token;
}
