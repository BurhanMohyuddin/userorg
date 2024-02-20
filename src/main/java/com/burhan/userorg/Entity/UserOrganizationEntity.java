package com.burhan.userorg.Entity;
import java.time.LocalDateTime;

public class UserOrganizationEntity {

    private Long id;
    private String name;
    private Long age;
    private String email;
    private Long organization_id;
    private String organization_name;
    private LocalDateTime createdAt;

    public UserOrganizationEntity() {
    }

    public UserOrganizationEntity(String name, Long age, String email, Long organization_id, String organization_name, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.organization_id = organization_id;
        this.organization_name = organization_name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(Long organization_id) {
        this.organization_id = organization_id;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
