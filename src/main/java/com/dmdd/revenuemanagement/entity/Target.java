package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "target")
@Data
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_info_id")
    private UserInfo user_info;

    @Column(name = "name")
    private String name;

    @Column(name="total")
    private float total;

    @Column(name = "current")
    private float current;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rate_exchange_to_VND")
    private float rate;

    @Column(name = "date_created")
    private Date date_created;

    @Column(name = "date_end")
    private Date date_end;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "target")
    private List<UserAddAmountTarget> userAddAmountTargetList=new ArrayList<>();

    public void AddUserAmountTarget(UserAddAmountTarget userAddAmountTarget)
    {
        if(userAddAmountTarget!=null)
        {
            if(userAddAmountTargetList==null)
                userAddAmountTargetList=new ArrayList<>();
            userAddAmountTargetList.add(userAddAmountTarget);
            userAddAmountTarget.setTarget(this);
        }
    }
}
