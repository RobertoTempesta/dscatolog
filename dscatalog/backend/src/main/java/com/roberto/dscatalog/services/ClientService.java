package com.roberto.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.roberto.dscatalog.dto.ClientDTO;
import com.roberto.dscatalog.entities.Client;
import com.roberto.dscatalog.repositories.ClientRepository;
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
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(p -> new ClientDTO(p)).collect(Collectors.toList());
    }

    public Page<ClientDTO> findAllPaged(Pageable pageable) {
        return clientRepository.findAll(pageable).map(p -> new ClientDTO(p));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> entity = clientRepository.findById(id);
        entity.orElseThrow(() -> new ResourceNotFoundException("Entity not found!!"));
        return new ClientDTO(entity.get());
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        copyDTO(dto, entity);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getOne(id);
            copyDTO(dto, entity);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        
    }

    public void delete(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException emptyErr) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException dataErr) {
            throw new DataBaseException("Integrity Violation !");
        }
        
    }

    private void copyDTO(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());           
    }
}
