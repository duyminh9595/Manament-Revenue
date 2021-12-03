package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.Target;
import com.dmdd.revenuemanagement.entity.UserAddAmountTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource(path = "useraddamounttarget",collectionResourceRel = "useraddamounttarget")
@CrossOrigin
public interface UserAddAmountTargetRepository extends JpaRepository<UserAddAmountTarget,Long> {
    @Query("select p from UserAddAmountTarget  p  where p.target.user_info.id=?1 and p.status=?2 order by p.date_created desc ")
    List<UserAddAmountTarget> findUserAddAmountTargetByUserId(Long userid,boolean status);
}
