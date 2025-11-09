package com.hotel.hotel.client;

import java.time.LocalDate;

import com.hotel.hotel.contactInformation.ContactInformationDTO;

public record ClientDetailsDTO(Long id, String name, String pin, String email, LocalDate dateOfBirth, ContactInformationDTO contactInformation) {
    public ClientDetailsDTO(Client client) {
        this(client.getId(), client.getName(), client.getPin(), client.getEmail(), client.getDateOfBirth(), new ContactInformationDTO(client.getContactInformation()));
    }
}
