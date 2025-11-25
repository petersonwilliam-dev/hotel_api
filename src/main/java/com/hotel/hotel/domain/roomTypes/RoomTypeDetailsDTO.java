package com.hotel.hotel.domain.roomTypes;

import java.math.BigDecimal;

public record RoomTypeDetailsDTO(Long id ,String name, Integer capacity, BigDecimal basePrice, String bedConfig, String amenities) {
    public RoomTypeDetailsDTO(RoomType room) {
        this(room.getId() ,room.getName(), room.getCapacity(), room.getBasePrice(), room.getBedConfig(), room.getAmenities());
    }
}
