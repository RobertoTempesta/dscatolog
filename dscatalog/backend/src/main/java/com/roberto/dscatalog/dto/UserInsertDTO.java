package com.roberto.dscatalog.dto;

import com.roberto.dscatalog.services.validation.UserInsertValid;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@UserInsertValid
public class UserInsertDTO extends UserDTO {
    
    private static final long serialVersionUID = 1L;

    private String password;

    UserInsertDTO() {
        super();
    }
        
}
