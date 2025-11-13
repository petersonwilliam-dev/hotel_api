package com.hotel.hotel.domain.room;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RoomSaveDTO(

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    String code,

    @NotBlank
    String floor,
    
    @NotNull
    StatusRoom status,
    
    @NotNull
    @DecimalMin("100.00")
    BigDecimal customPrice,

    @NotNull
    Long roomType
    ) {
}
