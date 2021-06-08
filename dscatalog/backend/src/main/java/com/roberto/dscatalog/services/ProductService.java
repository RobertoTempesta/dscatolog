package com.roberto.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.roberto.dscatalog.dto.CategoryDTO;
import com.roberto.dscatalog.dto.ProductDTO;
import com.roberto.dscatalog.entities.Category;
import com.roberto.dscatalog.entities.Product;
import com.roberto.dscatalog.repositories.CategoryRepository;
import com.roberto.dscatalog.repositories.ProductRepository;
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
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
    }

    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        return productRepository.findAll(pageable).map(p -> new ProductDTO(p));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> entity = productRepository.findById(id);
        entity.orElseThrow(() -> new ResourceNotFoundException("Entity not found!!"));
        return new ProductDTO(entity.get(), entity.get().getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDTO(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getOne(id);
            copyDTO(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyErr) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException dataErr) {
            throw new DataBaseException("Integrity Violation !");
        }
        
    }

    private void copyDTO(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()) {
            Category category = categoryRepository.getOne(catDto.getId());
            entity.getCategories().add(category);
        }               
    }
}
