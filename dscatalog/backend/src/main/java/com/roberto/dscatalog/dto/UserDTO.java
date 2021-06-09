package com.roberto.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.roberto.dscatalog.entities.User;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @Setter(AccessLevel.NONE)
    Set<RoleDTO> roles = new HashSet<>();
    
    public UserDTO() {}

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
