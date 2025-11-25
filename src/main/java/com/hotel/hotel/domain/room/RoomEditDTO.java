package com.hotel.hotel.domain.room;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;

public record RoomEditDTO(
    StatusRoom status,
    @DecimalMin("100.00")
    BigDecimal customPrice,
    Long roomType
) {
}
