package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role {

//    public Role(String role){
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Authority> authorities;

    @Column(name = "role")
    private String role;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private List<User> users;

    @Column(name = "allowed_read_resources")
    private String allowedReadResources;

    @Column(name = "allowed_create_resources")
    private String allowedCreateResources;

    @Column(name = "allowed_update_resources")
    private String allowedUpdateResources;

    @Column(name = "allowed_delete_resources")
    private String allowedDeleteResources;

    public String getAllowedReadResources() {
        return allowedReadResources;
    }

    public void setAllowedReadResources(String allowedReadResources) {
        this.allowedReadResources = allowedReadResources;
    }

    public String getAllowedCreateResources() {
        return allowedCreateResources;
    }

    public void setAllowedCreateResources(String allowedCreateResources) {
        this.allowedCreateResources = allowedCreateResources;
    }

    public String getAllowedUpdateResources() {
        return allowedUpdateResources;
    }

    public void setAllowedUpdateResources(String allowedUpdateResources) {
        this.allowedUpdateResources = allowedUpdateResources;
    }

    public String getAllowedDeleteResources() {
        return allowedDeleteResources;
    }

    public void setAllowedDeleteResources(String allowedDeleteResources) {
        this.allowedDeleteResources = allowedDeleteResources;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
}
