package com.hotel.hotel.client;

import java.time.LocalDate;

import com.hotel.hotel.contactInformation.ContactInformationDTO;

import jakarta.validation.constraints.NotNull;

public record ClientEditDTO(
    
    @NotNull
    Long id, 
    String name, 
    String email, 
    LocalDate dateOfBirth, 
    ContactInformationDTO contactInformation
) {}
