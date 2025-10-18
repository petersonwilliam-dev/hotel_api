package com.hotel.hotel.client;
import java.time.LocalDate;

import com.hotel.hotel.contactInformation.ContactInformationDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public record ClientSaveDTO(
    
    @NotBlank
    String name,
    
    @NotBlank
    @Pattern(regexp = "\\d{11}") 
    String pin,
    
    @NotBlank
    @Email
    String email, 
    
    @NotNull
    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate dateOfBirth,

    @NotNull
    @Valid
    ContactInformationDTO contactInformation

) {}
