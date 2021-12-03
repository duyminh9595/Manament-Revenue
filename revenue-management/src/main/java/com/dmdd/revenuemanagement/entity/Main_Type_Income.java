package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "main_type_income")
public class Main_Type_Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String  name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "main_type_income")
    private List<Type_Income> type_incomeList=new ArrayList<>();

    @Column(name = "image_url")
    private String image_url;

    public void AddTypeIncome(Type_Income type_income)
    {
        if(type_income!=null)
        {
            if(type_incomeList==null)
                type_incomeList=new ArrayList<>();
            type_incomeList.add(type_income);
            type_income.setMain_type_income(this);
        }
    }
}
