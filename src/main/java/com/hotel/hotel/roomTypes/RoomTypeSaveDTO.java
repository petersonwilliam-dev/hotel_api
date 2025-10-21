package com.hotel.hotel.roomTypes;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomTypeSaveDTO(
    
    @NotBlank
    String name, 
    
    @Min(1)
    int capacity, 

    @NotNull
    @DecimalMin("100.00")
    BigDecimal basePrice,
    
    @NotBlank
    String bedConfig,
    
    @NotBlank
    String amenities,
    
    @NotNull
    Category category
    
) {}
