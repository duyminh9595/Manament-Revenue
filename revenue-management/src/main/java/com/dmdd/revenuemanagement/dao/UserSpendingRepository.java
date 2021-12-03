package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.User_Income;
import com.dmdd.revenuemanagement.entity.User_Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "userspending",collectionResourceRel = "userspending")
@CrossOrigin
public interface UserSpendingRepository extends JpaRepository<User_Spending, Long> {
    @Query("select p from User_Spending  p  where p.user_spending.id=?1")
    List<User_Spending> findByUserId(Long id);

    @Query("select p from User_Spending  p  where p.type_spending.id=?1 and p.user_spending.id=?2")
    List<User_Spending> findBySpendingIdAndUserId(Long id,Long userid);

    @Query("select p from User_Spending p where p.user_spending.id=?1 and p.status=?2 order by p.Date_Created desc ")
    List<User_Spending>findAllUserSpendingOrderDateDesc(Long userid, boolean status);
}
