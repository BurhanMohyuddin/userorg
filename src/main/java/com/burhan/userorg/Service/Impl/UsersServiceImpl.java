package com.burhan.userorg.Service.Impl;

import com.burhan.userorg.Service.UsersService;
import com.burhan.userorg.Entity.OrganizationEntity;
import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;
import com.burhan.userorg.Entity.UsersEntity;
import com.burhan.userorg.Exception.ApiException;
import com.burhan.userorg.Repository.OrganizationRepository;
import com.burhan.userorg.Repository.UsersRepository;
import com.burhan.userorg.Security.JwtTokenHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenHelper jwtTokenHelper;
    @Autowired
    private HttpServletRequest request;

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<UsersEntity> findAllUsers() {

        String token = extractTokenFromRequest(request);

        Claims claims = this.jwtTokenHelper.getAllClaimsFromToken(token);

        String name = claims.getSubject();

        List<UsersEntity> usersWithSameName = usersRepository.findAllOrganizationWithUserToken(name);

        if (!usersWithSameName.isEmpty()) {
            Long id = usersWithSameName.get(0).getOrganizationId();
            System.out.println("Id that fetching  : " + id);

            return usersRepository.findAllUsersWithSameOrgId(id);
        }
        return null;
    }

    @Override
    public List<UserOrganizationEntity> findUsersByOrganizationId(Long id) {
        List<UsersEntity> list=null;
        List<UserOrganizationEntity> userOrganizationList = new ArrayList<>();
        list= usersRepository.findByOrganizationId(id);
        if(!CollectionUtils.isEmpty(list))
        {
            for (int i = 0; i < list.size(); i++) {
                UsersEntity usersEntity=list.get(i);
                UserOrganizationEntity userOrganizationEntity = new UserOrganizationEntity();
                userOrganizationEntity.setId(usersEntity.getId());
                userOrganizationEntity.setAge(usersEntity.getAge());
                userOrganizationEntity.setName(usersEntity.getUserName());
                userOrganizationEntity.setEmail(usersEntity.getEmail());
                userOrganizationEntity.setOrganization_id(usersEntity.getOrganization().getId());
                userOrganizationEntity.setOrganization_name(usersEntity.getOrganization().getName());
                userOrganizationEntity.setCreatedAt(usersEntity.getCreatedAt());

                userOrganizationList.add(userOrganizationEntity);
            }

        }else {
            throw new RuntimeException("List is empty");
        }
        return userOrganizationList;
    }

    @Override
    public UsersEntity saveUsers(UsersEntity usersEntity, Authentication authentication) {

        // Get the organization ID of the authenticated user
        String requestingUserUsername = authentication.getName();
        UsersEntity requestingUser = usersRepository.findByUserName(requestingUserUsername);
        Long requestingUserOrganizationId = requestingUser.getOrganization().getId();

        System.out.println("value of org in saving new user :-  "+requestingUserOrganizationId);

        // Check if the requesting user is an admin of the organization
        if (!requestingUser.getRole().equals("ROLE_ADMIN")) {
            throw new AccessDeniedException("You are not authorized to add users");
        }

        // Check if the organization ID of the user to be added matches the requesting user's organization ID
        else if (!usersEntity.getOrganizationId().equals(requestingUserOrganizationId)) {
            throw new AccessDeniedException("You are not authorized to add users to this organization");
        }

        if (usersEntity.getPassword() == null || usersEntity.getPassword().isEmpty()) {
            throw new ApiException("Password cannot be null or empty");
        }
        usersEntity.setCreatedAt(LocalDateTime.now());
        usersEntity.setPassword(this.passwordEncoder.encode(usersEntity.getPassword()));
        // Fetch the organization from the database using the provided organizationId
        OrganizationEntity organization = organizationRepository.findById(usersEntity.getOrganizationId()).orElse(null);

        if (organization != null) {
            // Set the fetched organization on the UsersEntity
            usersEntity.setOrganization(organization);
        } else {
            // Handle case where organization is not found
            throw new ApiException("Organization not found for id: " + usersEntity.getOrganizationId());
        }
        return usersRepository.save(usersEntity);
    }

    @Override
    public UpdateResultEntity updateUsers(UsersEntity usersEntity) {
        usersEntity.setCreatedAt(LocalDateTime.now());

        if (usersEntity.getPassword() == null || usersEntity.getPassword().isEmpty()) {
            throw new ApiException("Password cannot be null or empty");
        }
        usersEntity.setPassword(this.passwordEncoder.encode(usersEntity.getPassword()));
        UsersEntity updatedEntity = usersRepository.save(usersEntity);

        // Check if the data is updated successfully
        if (areFieldsUpdated(usersEntity, updatedEntity)) {
            // Data is updated
            return new UpdateResultEntity(true, "Data updated successfully");
        } else {
            // Data is not updated as expected
            return new UpdateResultEntity(false, "Failed to update user data");
        }
    }

    @Override
    public String deleteUser(Long id) {
        try {
            usersRepository.deleteById(id);
            return "User deleted successfully";
        } catch (EmptyResultDataAccessException ex) {
            return "User with ID " + id + " not found";
        } catch (Exception e) {
            // Handle other exceptions if needed
            return "An error occurred while deleting the user";
        }
    }

    private boolean areFieldsUpdated(UsersEntity original, UsersEntity updated) {
        // Compare relevant fields
        return Objects.equals(original.getAge(), updated.getAge()) &&
                Objects.equals(original.getUserName(), updated.getUserName()) &&
                Objects.equals(original.getEmail(), updated.getEmail());
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
