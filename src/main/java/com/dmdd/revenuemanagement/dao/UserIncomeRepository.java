package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.User_Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "userincome",collectionResourceRel = "userincome")
@CrossOrigin
public interface UserIncomeRepository extends JpaRepository<User_Income, Long> {
    @Query("select p from User_Income  p  where p.user_income.id=?1")
    List<User_Income> findByInComeID(Long id);

    @Query("select p from User_Income  p  where p.type_income.id=?1 and p.user_income.id=?2")
    List<User_Income> findByIncomeIdAndUserId(Long id, Long userid);

    @Query("select p from User_Income p where p.user_income.id=?1 and p.status=?2 order by p.Date_Created desc ")
    List<User_Income>findAllUserIncomeOrderDateDesc(Long userid,boolean status);
}
