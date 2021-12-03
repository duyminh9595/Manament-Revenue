package com.dmdd.revenuemanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_income")
@Data
public class Type_Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String image_url;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "type_income")
    private List<User_Income> user_incomeList=new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false, name = "main_type_income_id")
    private Main_Type_Income main_type_income;

    public void AddIncome(User_Income user_income)
    {
        if(user_income!=null)
        {
            if(user_incomeList==null)
            {
                user_incomeList=new ArrayList<>();
            }
            user_incomeList.add(user_income);
            user_income.setType_income(this);
        }
    }

    @Override
    public String toString() {
        return "Type_Income{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", user_incomeList=" + user_incomeList +
                '}';
    }
}
