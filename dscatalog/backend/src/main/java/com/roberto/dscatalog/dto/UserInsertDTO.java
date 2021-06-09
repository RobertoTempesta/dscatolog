package com.roberto.dscatalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInsertDTO extends UserDTO {
    
    private static final long serialVersionUID = 1L;

    private String password;

    UserInsertDTO() {
        super();
    }
        
}
