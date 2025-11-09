package com.hotel.hotel.domain.client;

import java.time.LocalDate;

import com.hotel.hotel.domain.contactInformation.ContactInformationDTO;

import jakarta.validation.constraints.NotNull;

public record ClientEditDTO(
    
    @NotNull
    Long id, 
    String name, 
    String email, 
    LocalDate dateOfBirth, 
    ContactInformationDTO contactInformation
) {}
