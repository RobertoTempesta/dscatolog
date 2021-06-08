package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    
    @Autowired
    private CityRepository repository;

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e1) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e2) {
            throw new DataBaseException("Integrity Violation !");
        } 
    }

    public List<CityDTO> findAll() {
        return repository.findAll(Sort.by("name"))
            .stream()
            .map(c -> new CityDTO(c))
            .collect(Collectors.toList());
    }
}
