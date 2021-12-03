package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User_Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_income_id")
    private UserInfo user_income;


    @ManyToOne
    @JoinColumn(nullable = false, name = "type_income_id")
    private Type_Income type_income;

    @Column(name = "currency",nullable = false)
    private String currency;

    @Column(name = "rate_balance_to_vnd",nullable = false)
    private float rate_balance_to_vnd;

    @Column(name = "balance",nullable = false)
    private float balance;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private Date Date_Created;

    @Column(name = "status")
    private boolean status;

    @Column(name = "date_updated")
    private Date Date_Updated;

    @Override
    public String toString() {
        return "User_Income{" +
                "user_income=" + user_income +
                ", type_income=" + type_income +
                ", currency='" + currency + '\'' +
                ", rate_balance_to_vnd=" + rate_balance_to_vnd +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                '}';
    }
}
