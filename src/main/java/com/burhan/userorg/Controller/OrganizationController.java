package com.burhan.userorg.Controller;

import com.burhan.userorg.Entity.OrganizationEntity;
import com.burhan.userorg.Entity.UpdateResultEntity;
import com.burhan.userorg.Entity.UserOrganizationEntity;
import com.burhan.userorg.Service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<OrganizationEntity> findAllOrganizations(){
        return organizationService.findAllOrganizations();
    }

    @GetMapping("/{id}")
    public Optional<OrganizationEntity> findOrganizationById(@PathVariable("id") Long id){
        return organizationService.findById(id);
    }

    @GetMapping("/allUsers/{id}")
    public List<UserOrganizationEntity> findAllUsersById(@PathVariable("id") Long id){
        return organizationService.findAllUsersById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public OrganizationEntity saveOrganization(@RequestBody OrganizationEntity organizationEntity){
        return organizationService.saveOrganization(organizationEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UpdateResultEntity> updateOrganization(@RequestBody OrganizationEntity organizationEntity){
        UpdateResultEntity result = organizationService.updateOrganization(organizationEntity);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable("id") Long id){
        String response = organizationService.deleteOrganization(id);

        if (response.equals("User deleted successfully")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
