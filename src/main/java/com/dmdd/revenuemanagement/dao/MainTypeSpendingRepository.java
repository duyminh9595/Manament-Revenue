package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Main_Type_Spending;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "maintypespending",collectionResourceRel = "maintypespending")
@CrossOrigin
public interface MainTypeSpendingRepository extends JpaRepository<Main_Type_Spending,Long> {
    @Query("select p from Main_Type_Spending  p  where p.id=?1")
    Main_Type_Spending findByMainTypeSpendingId(Long id);
}
