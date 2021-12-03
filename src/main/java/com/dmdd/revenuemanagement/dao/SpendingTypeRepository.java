package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Type_Income;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "typespending",collectionResourceRel = "typespending")
@CrossOrigin
public interface SpendingTypeRepository extends JpaRepository<Type_Spending,Long> {
    @Query("select p from Type_Spending  p  where p.id=?1")
    Type_Spending findByTypeSpendingId(Long id);
}
