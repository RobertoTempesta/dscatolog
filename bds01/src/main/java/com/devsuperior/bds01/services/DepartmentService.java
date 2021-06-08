package com.devsuperior.bds01.services;

import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.bds01.dto.DepartmentDTO;
import com.devsuperior.bds01.repositories.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        return repository.findAll(Sort.by("name"))
            .stream()
            .map(d -> new DepartmentDTO(d))
            .collect(Collectors.toList());
    }
    
}
