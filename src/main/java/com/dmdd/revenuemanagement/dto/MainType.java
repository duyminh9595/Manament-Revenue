package com.dmdd.revenuemanagement.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainType {
    private Long id;
    private String name;
    private String image_url;
    //    0 la spend, 1 la income
    private boolean spend_or_income;

    private List<TypeDTO>typeDTOList=new ArrayList<>();
}
