package com.roberto.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.roberto.dscatalog.dto.CategoryDTO;
import com.roberto.dscatalog.entities.Category;
import com.roberto.dscatalog.repositories.CategoryRepository;
import com.roberto.dscatalog.services.exceptions.DataBaseException;
import com.roberto.dscatalog.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
    }

    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(c -> new CategoryDTO(c));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> entity = categoryRepository.findById(id);
        entity.orElseThrow(() -> new ResourceNotFoundException("Entity not found!!"));
        return new CategoryDTO(entity.get());
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = categoryRepository.getOne(id);
            entity.setName(dto.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyErr) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException dataErr) {
            throw new DataBaseException("Integrity Violation !");
        }
        
    }
}
