package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_add_amount_target")
@Data
public class UserAddAmountTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "target_id")
    private Target target;

    @Column(name = "amount")
    private float amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rate_exchange_to_VND")
    private float rate;

    @Column(name = "date_created")
    private Date date_created;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status=true;
}
