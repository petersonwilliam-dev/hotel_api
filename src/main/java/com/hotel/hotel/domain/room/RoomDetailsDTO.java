package com.hotel.hotel.domain.room;

import java.math.BigDecimal;

import com.hotel.hotel.domain.roomTypes.RoomTypeDetailsDTO;

public record RoomDetailsDTO(Long id, String code, String floor, BigDecimal customPrice, Boolean active, StatusRoom statusRoom, RoomTypeDetailsDTO roomTypeDetails) {
    public RoomDetailsDTO(Room room) {
        this(room.getId(), room.getCode(), room.getFloor(), room.getCustomPrice(), room.getActive(), room.getStatus(), new RoomTypeDetailsDTO(room.getRoomType()));
    }
}
