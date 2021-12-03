package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Main_Type_Income;
import com.dmdd.revenuemanagement.entity.Main_Type_Spending;
import com.dmdd.revenuemanagement.entity.Type_Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "maintypeincome",collectionResourceRel = "maintypeincome")
@CrossOrigin
public interface MainTypeIncomeRepository extends JpaRepository<Main_Type_Income,Long> {
    @Query("select p from Main_Type_Income  p  where p.id=?1")
    Main_Type_Income findByMainTypeIncomeId(Long id);

    @Query("select p from Main_Type_Income  p  ")
    List<Main_Type_Income> findAllTypeIncome();
}
