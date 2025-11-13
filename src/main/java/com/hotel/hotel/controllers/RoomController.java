package com.hotel.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.hotel.hotel.domain.room.Room;
import com.hotel.hotel.domain.room.RoomRepository;
import com.hotel.hotel.domain.room.RoomSaveDTO;
import com.hotel.hotel.domain.roomTypes.RoomTypeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/room")
public class RoomController {
    
    @Autowired
    private RoomRepository repository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid RoomSaveDTO data, UriComponentsBuilder uriBuilder) {
        var roomType = roomTypeRepository.getReferenceById(data.roomType());
        var room = new Room(data, roomType);
        repository.save(room);

        var uri = uriBuilder.path("/room/{id}").buildAndExpand(room.getId()).toUri();

        return ResponseEntity.created(uri).body(room);
    }
}
