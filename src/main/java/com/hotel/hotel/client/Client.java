package com.hotel.hotel.client;

import java.time.LocalDate;

import com.hotel.hotel.contactInformation.ContactInformation;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Client")
@Table(name = "client")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pin;
    private String email;
    private LocalDate dateOfBirth;

    @Embedded
    private ContactInformation contactInformation;

    public Client(ClientSaveDTO data) {
        this.name = data.name();
        this.pin = data.pin();
        this.email = data.email();
        this.dateOfBirth = data.dateOfBirth();
        this.contactInformation = new ContactInformation(data.contactInformation());
    }

    public void edit(ClientEditDTO data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
        if (data.dateOfBirth() != null) {
            this.dateOfBirth = data.dateOfBirth();
        }
        if (data.contactInformation() != null) {
            this.contactInformation.edit(data.contactInformation());
        }
    }
}
