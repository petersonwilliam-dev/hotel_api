package com.hotel.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.hotel.client.Client;
import com.hotel.hotel.client.ClientListDTO;
import com.hotel.hotel.client.ClientSaveDTO;
import com.hotel.hotel.client.ClientRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository repository;

    @PostMapping
    @Transactional
    public void siginup(@RequestBody @Valid ClientSaveDTO data) {
        repository.save(new Client(data));
    }

    public Page<ClientListDTO> listar(Pageable pagination) {
        return repository.findAll(pagination).map(ClientListDTO::new);
    }
    
}