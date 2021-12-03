package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "balance")
    private float balance;

    @Column(name = "token_email")
    private String token_email;

    @Column(name = "confirm_email")
    private boolean confirm_email;

    @Column(name = "date_created")
    private Date Date_Created;

    @Column(name = "status")
    private boolean status;

    @Column(name = "date_updated")
    private Date Date_Updated;

    @Column(name = "amount")
    private float amount;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_income")
    private List<User_Income> user_incomeList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_spending")
    private List<User_Spending> user_spendingList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user_info")
    private List<Target> targetList=new ArrayList<>();

    public void AddIncome(User_Income user_income)
    {
        if(user_income!=null)
        {
            if(user_incomeList==null)
            {
                user_incomeList=new ArrayList<>();
            }
            user_incomeList.add(user_income);
            user_income.setUser_income(this);
        }
    }

    public void AddSpending(User_Spending user_spending)
    {
        if(user_spending!=null)
        {
            if(user_spendingList==null)
            {
                user_spendingList=new ArrayList<>();
            }
            user_spendingList.add(user_spending);
            user_spending.setUser_spending(this);
        }
    }

    public void AddTarget(Target target)
    {
        if(target!=null)
        {
            if(targetList==null)
                targetList=new ArrayList<>();
            targetList.add(target);
            target.setUser_info(this);
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image_url='" + image_url + '\'' +
                ", balance=" + balance +
                ", token_email='" + token_email + '\'' +
                ", confirm_email=" + confirm_email +
                ", user_incomeList=" + user_incomeList +
                ", user_spendingList=" + user_spendingList +
                '}';
    }
}
