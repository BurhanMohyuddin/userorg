package com.burhan.userorg.Service;

import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;
import com.burhan.userorg.Entity.UsersEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UsersService {
    List<UsersEntity> findAllUsers();
    List<UserOrganizationEntity> findUsersByOrganizationId(Long id);
    UsersEntity saveUsers(UsersEntity usersEntity, Authentication authentication);
    UpdateResultEntity updateUsers(UsersEntity usersEntity);
    String deleteUser(Long id);
}
