package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Type_Income;
import com.dmdd.revenuemanagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "typeincome",collectionResourceRel = "typeincome")
@CrossOrigin
public interface IncomeTypeRepository extends JpaRepository<Type_Income,Long> {
    @Query("select p from Type_Income  p  where p.id=?1")
    Type_Income findByTypeInComeID(Long id);
}
