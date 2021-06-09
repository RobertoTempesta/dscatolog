package com.roberto.dscatalog.dto;

import java.io.Serializable;

import com.roberto.dscatalog.entities.Role;

import lombok.Data;

@Data
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String authority;

    public RoleDTO() {}


    public RoleDTO(Role entity) {
        this.id = entity.getId();
        this.authority = entity.getAuthority();
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
}
