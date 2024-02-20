package com.burhan.userorg.Service.Impl;

import com.burhan.userorg.Entity.OrganizationEntity;
import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;
import com.burhan.userorg.Entity.UsersEntity;
import com.burhan.userorg.Repository.OrganizationRepository;
import com.burhan.userorg.Service.OrganizationService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    @Override
    public List<OrganizationEntity> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<OrganizationEntity> findById(Long id) {
        return organizationRepository.findById(id);
    }

    @Override
    public List<UserOrganizationEntity> findAllUsersById(Long id) {
        List<OrganizationEntity> list=null;
        List<UserOrganizationEntity> userOrganizationList = new ArrayList<>();
        list= organizationRepository.findUsersById(id);
        if(!CollectionUtils.isEmpty(list))
        {
            for (int i = 0; i < list.size(); i++) {
                OrganizationEntity organizationEntity=list.get(i);

                List<UsersEntity> usersList = organizationEntity.getUsers();

                for (UsersEntity usersEntity : usersList) {
                    UserOrganizationEntity userOrganizationEntity = new UserOrganizationEntity();
                    userOrganizationEntity.setId(usersEntity.getId());
                    userOrganizationEntity.setAge(usersEntity.getAge());
                    userOrganizationEntity.setName(usersEntity.getUserName());
                    userOrganizationEntity.setEmail(usersEntity.getEmail());
                    userOrganizationEntity.setOrganization_id(organizationEntity.getId());
                    userOrganizationEntity.setOrganization_name(organizationEntity.getName());
                    userOrganizationEntity.setCreatedAt(usersEntity.getCreatedAt());

                    userOrganizationList.add(userOrganizationEntity);
                }
            }

        }else {
            throw new RuntimeException("List is empty");
        }
        return userOrganizationList;
    }

    @Override
    public OrganizationEntity saveOrganization(OrganizationEntity organizationEntity) {
        organizationEntity.setCreatedAt(LocalDateTime.now());
        return organizationRepository.save(organizationEntity);
    }

    @Override
    public UpdateResultEntity updateOrganization(OrganizationEntity organizationEntity) {
        organizationEntity.setCreatedAt(LocalDateTime.now());
        OrganizationEntity updatedEntity = organizationRepository.save(organizationEntity);

        // Check if the data is updated successfully
        if (areFieldsUpdated(organizationEntity, updatedEntity)) {
            // Data is updated
            return new UpdateResultEntity(true, "Data updated successfully");
        } else {
            // Data is not updated as expected
            return new UpdateResultEntity(false, "Failed to update user data");
        }
    }

    @Override
    public String deleteOrganization(Long id) {
        try {
            organizationRepository.deleteById(id);
            return "Organization deleted successfully";
        } catch (EmptyResultDataAccessException ex) {
            return "Organization with ID " + id + " not found";
        } catch (Exception e) {
            // Handle other exceptions if needed
            return "An error occurred while deleting the user";
        }
    }

    private boolean areFieldsUpdated(OrganizationEntity original, OrganizationEntity updated) {
        // Compare relevant fields
        return Objects.equals(original.getContactNumber(), updated.getContactNumber()) &&
                Objects.equals(original.getName(), updated.getName()) &&
                Objects.equals(original.getDescription(), updated.getDescription());
    }
}
