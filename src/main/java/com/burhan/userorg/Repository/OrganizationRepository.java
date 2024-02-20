package com.burhan.userorg.Repository;

import com.burhan.userorg.Entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity,Long>{
    List<OrganizationEntity> findUsersById(Long Id);
}
