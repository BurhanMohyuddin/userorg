package com.burhan.userorg.Controller;

import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;
import com.burhan.userorg.Entity.UsersEntity;
import com.burhan.userorg.Service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<UsersEntity> findAllUsers(){
        return usersService.findAllUsers();
    }

//    @PreAuthorize("@userSecurity.hasUserId(authentication,#id)")
    @GetMapping("/org/{id}")
    public List<UserOrganizationEntity> findUserByOrgId(@PathVariable("id") Long id){
        return usersService.findUsersByOrganizationId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UsersEntity saveUsers(@RequestBody UsersEntity usersEntity, Authentication authentication){
        return usersService.saveUsers(usersEntity, authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UpdateResultEntity> updateUser(@RequestBody UsersEntity usersEntity){
        UpdateResultEntity result = usersService.updateUsers(usersEntity);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        String response = usersService.deleteUser(id);

        System.out.println("Admin role");
        if (response.equals("User deleted successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
