package com.hotel.hotel.domain.contactInformation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ContactInformationDTO(
    
    @NotBlank
    @Pattern(regexp = "^\\(?\\d{2}\\)? ?9?\\d{4}-?\\d{4}$", message = "Esse telefone deve estar no formato correto")
    String phoneNumber, 
    
    @NotBlank
    String street, 
    
    @NotBlank
    String neighborhood, 
    
    String number, 
    
    @NotBlank
    String city, 
    
    @NotBlank
    String state, 
    
    String complement, 
    
    @NotBlank
    @Pattern(regexp = "\\d{8}")
    String postalCode
    
) {
    public ContactInformationDTO(ContactInformation contactInformation) {
        this(contactInformation.getPhoneNumber(), contactInformation.getStreet(), contactInformation.getNeighborhood(), contactInformation.getNumber(), contactInformation.getCity(), contactInformation.getState(), contactInformation.getComplement(), contactInformation.getPostalCode());
    }
}
