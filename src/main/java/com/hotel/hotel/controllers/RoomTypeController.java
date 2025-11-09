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

import com.hotel.hotel.roomTypes.RoomType;
import com.hotel.hotel.roomTypes.RoomTypeRepository;
import com.hotel.hotel.roomTypes.RoomTypeSaveDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {

    @Autowired
    private RoomTypeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RoomTypeSaveDTO data, UriComponentsBuilder uriBuilder) {
        var roomType = new RoomType(data);
        repository.save(roomType);

        var uri = uriBuilder.path("/roomType/{id}").buildAndExpand(roomType.getId()).toUri();

        return ResponseEntity.created(uri).body(roomType);
    }

    @GetMapping
    public ResponseEntity<Page<RoomType>> list(Pageable pagination) {
        var pages = repository.findAll(pagination);
        return ResponseEntity.ok(pages);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity edit(@RequestBody RoomTypeSaveDTO data, @PathVariable Long id) {
        var roomType = repository.getReferenceById(id);
        roomType.edit(data);
        return ResponseEntity.ok(roomType);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
