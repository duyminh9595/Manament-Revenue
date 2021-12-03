package com.dmdd.revenuemanagement.dao;

import com.dmdd.revenuemanagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource(path = "userinfo",collectionResourceRel = "userinfo")
@CrossOrigin
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    UserInfo findByEmail(@Param("email")String email);

    @Query("select p from UserInfo  p  where p.token_email=?1")
    UserInfo findByToken_confirm_email(String token_confirm_email);
}
