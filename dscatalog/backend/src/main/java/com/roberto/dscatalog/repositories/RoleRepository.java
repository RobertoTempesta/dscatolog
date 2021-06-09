package com.roberto.dscatalog.repositories;

import com.roberto.dscatalog.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
