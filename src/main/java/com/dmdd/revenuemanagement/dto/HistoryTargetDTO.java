package com.dmdd.revenuemanagement.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class HistoryTargetDTO {
    private Long id;
    private String name;
    private float total;
    private float current;
    private String currency;

    private float rate;

    private String date_created;

    private String date_end;

    private String description;
}
