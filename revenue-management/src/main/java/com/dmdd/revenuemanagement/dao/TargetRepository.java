package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Target;
import com.dmdd.revenuemanagement.entity.Type_Spending;
import com.dmdd.revenuemanagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "target",collectionResourceRel = "target")
@CrossOrigin
public interface TargetRepository extends JpaRepository<Target,Long> {
    @Query("select p from Target  p  where p.id=?1")
    Target findByTargetId(Long id);

    @Query("select p from Target  p  where p.user_info.id=?1")
    List<Target> findTargetByUserId(Long userid);
}
