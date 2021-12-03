package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "user_spending")
public class User_Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_spending_id")
    private UserInfo user_spending;


    @ManyToOne
    @JoinColumn(nullable = false, name = "type_spending_id")
    private Type_Spending type_spending;


    @Column(name = "currency",nullable = false)
    private String currency;

    @Column(name = "rate_balance_to_vnd",nullable = false)
    private float rate_balance_to_vnd;

    @Column(name = "balance",nullable = false)
    private float balance;

    @Column(name = "date_created")
    private Date Date_Created;

    @Column(name = "status")
    private boolean status;

    @Column(name = "date_updated")
    private Date Date_Updated;
}
