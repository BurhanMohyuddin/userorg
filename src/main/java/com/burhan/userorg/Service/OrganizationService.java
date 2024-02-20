package com.burhan.userorg.Service;


import com.burhan.userorg.Entity.OrganizationEntity;
import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<OrganizationEntity> findAllOrganizations();
    Optional<OrganizationEntity> findById(Long id);
    List<UserOrganizationEntity> findAllUsersById(Long id);
    OrganizationEntity saveOrganization(OrganizationEntity organizationEntity);
    UpdateResultEntity updateOrganization(OrganizationEntity organizationEntity);
    String deleteOrganization(Long id);
}
