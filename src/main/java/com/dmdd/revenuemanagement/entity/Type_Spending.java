package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_spending")
@Data
public class Type_Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String image_url;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "type_spending")
    private List<User_Spending> user_spendingList=new ArrayList<>();


    @ManyToOne
    @JoinColumn(nullable = false, name = "main_type_spending_id")
    private Main_Type_Spending main_type_spending;


    public void AddSpending(User_Spending user_spending)
    {
        if(user_spending!=null)
        {
            if(user_spendingList==null)
            {
                user_spendingList=new ArrayList<>();
            }
            user_spendingList.add(user_spending);
            user_spending.setType_spending(this);
        }
    }

}
