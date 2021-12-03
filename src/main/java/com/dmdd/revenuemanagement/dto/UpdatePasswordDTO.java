package com.dmdd.revenuemanagement.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String oldpass;
    private String newpass;
}
