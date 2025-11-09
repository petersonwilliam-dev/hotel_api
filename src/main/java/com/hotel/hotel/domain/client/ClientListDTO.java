package com.hotel.hotel.domain.client;

import java.time.LocalDate;

import com.hotel.hotel.domain.contactInformation.ContactInformationDTO;

public record ClientListDTO(Long id, String name, String email, LocalDate dateOfBirth, ContactInformationDTO contactInformation) {
    public ClientListDTO(Client client) {
        this(client.getId(), client.getName(), client.getEmail(), client.getDateOfBirth(), new ContactInformationDTO(client.getContactInformation()));
    }
}
