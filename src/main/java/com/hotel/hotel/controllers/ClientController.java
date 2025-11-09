package com.hotel.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hotel.hotel.domain.client.Client;
import com.hotel.hotel.domain.client.ClientDetailsDTO;
import com.hotel.hotel.domain.client.ClientEditDTO;
import com.hotel.hotel.domain.client.ClientListDTO;
import com.hotel.hotel.domain.client.ClientSaveDTO;
import com.hotel.hotel.domain.client.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ClientSaveDTO data, UriComponentsBuilder uriBuilder) {
        var client = new Client(data);
        repository.save(client);

        var uri = uriBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(new ClientDetailsDTO(client));
    }

    @GetMapping
    public ResponseEntity<Page<ClientListDTO>> list(Pageable pagination) {
        var clients = repository.findAllByDeletedFalse(pagination).map(ClientListDTO::new);
        return ResponseEntity.ok(clients);
    }
    
    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity edit(@RequestBody @Valid ClientEditDTO data, @PathVariable Long id) {
        Client client = repository.getReferenceById(id);
        client.edit(data);
        return ResponseEntity.ok(new ClientDetailsDTO(client));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        Client client = repository.getReferenceById(id);
        client.delete();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getClientById(@PathVariable Long id) {
        var client = repository.getReferenceById(id);
        return ResponseEntity.ok(new ClientDetailsDTO(client));
    }
}