package com.burhan.userorg.Repository;

import com.burhan.userorg.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long>{

    UsersEntity findByUserName(String userName);

    List<UsersEntity> findByOrganizationId(Long organizationId);

    @Query("SELECT u FROM UsersEntity u WHERE u.userName = :userName")
    List<UsersEntity> findAllOrganizationWithUserToken(@Param("userName") String userName);

    @Query("SELECT u FROM UsersEntity u WHERE u.organizationId = :organizationId")
    List<UsersEntity> findAllUsersWithSameOrgId(@Param("organizationId") Long organizationId);
}
