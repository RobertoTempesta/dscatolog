package com.roberto.dscatalog.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
