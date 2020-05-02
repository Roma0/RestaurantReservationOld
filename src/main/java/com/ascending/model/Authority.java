package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.*;

@Entity
@Table(name = "Authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "role", referencedColumnName = "role")
    private Role role;

    @Column(name = "allowed_resource")
    private String isAllowedResource;

    @Column(name = "allowed_Read")
    private boolean isAllowedRead;

    @Column(name = "allowed_create")
    private boolean isAllowedCreate;

    @Column(name = "allowed_update")
    private boolean isAllowedUpdate;

    @Column(name = "allowed_delete")
    private boolean isAllowedDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("role")
    public String getRole() {
        return role.getRole();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getIsAllowedResource() {
        return isAllowedResource;
    }

    public void setIsAllowedResource(String isAllowedResource) {
        this.isAllowedResource = isAllowedResource;
    }

    public boolean isAllowedRead() {
        return isAllowedRead;
    }

    public void setAllowedRead(boolean allowedRead) {
        isAllowedRead = allowedRead;
    }

    public boolean isAllowedCreate() {
        return isAllowedCreate;
    }

    public void setAllowedCreate(boolean allowedCreate) {
        isAllowedCreate = allowedCreate;
    }

    public boolean isAllowedUpdate() {
        return isAllowedUpdate;
    }

    public void setAllowedUpdate(boolean allowedUpdate) {
        isAllowedUpdate = allowedUpdate;
    }

    public boolean isAllowedDelete() {
        return isAllowedDelete;
    }

    public void setAllowedDelete(boolean allowedDelete) {
        isAllowedDelete = allowedDelete;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return str;
    }
}
