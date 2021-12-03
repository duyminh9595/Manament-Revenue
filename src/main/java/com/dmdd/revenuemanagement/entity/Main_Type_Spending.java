package com.dmdd.revenuemanagement.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "main_type_spending")
public class Main_Type_Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String  name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "main_type_spending")
    private List<Type_Spending> type_spendingList=new ArrayList<>();

    @Column(name = "image_url")
    private String image_url;

    public void AddTypeSpending(Type_Spending type_spending)
    {
        if(type_spending!=null)
        {
            if(type_spendingList==null)
                type_spendingList=new ArrayList<>();
            type_spendingList.add(type_spending);
            type_spending.setMain_type_spending(this);
        }
    }
}
