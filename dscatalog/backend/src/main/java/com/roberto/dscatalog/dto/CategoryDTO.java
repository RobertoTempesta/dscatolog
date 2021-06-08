package com.roberto.dscatalog.dto;

import java.io.Serializable;

import com.roberto.dscatalog.entities.Category;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO implements Serializable {

    private Long id;
    private String name;

    public CategoryDTO(){}

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
