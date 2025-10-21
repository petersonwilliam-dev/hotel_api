package com.hotel.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void register(@RequestBody @Valid RoomTypeSaveDTO data) {
        repository.save(new RoomType(data));
    }

    
}
