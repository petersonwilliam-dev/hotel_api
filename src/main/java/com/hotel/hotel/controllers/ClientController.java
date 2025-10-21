package com.hotel.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.hotel.client.Client;
import com.hotel.hotel.client.ClientEditDTO;
import com.hotel.hotel.client.ClientListDTO;
import com.hotel.hotel.client.ClientSaveDTO;
import com.hotel.hotel.client.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository repository;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid ClientSaveDTO data) {
        repository.save(new Client(data));
    }

    @GetMapping
    public Page<ClientListDTO> list(Pageable pagination) {
        return repository.findAllByDeletedFalse(pagination).map(ClientListDTO::new);
    }
    
    @PatchMapping("/{id}")
    @Transactional
    public void edit(@RequestBody @Valid ClientEditDTO data, @PathVariable Long id) {
        Client client = repository.getReferenceById(id);
        client.edit(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Client client = repository.getReferenceById(id);
        client.delete();
    }
}