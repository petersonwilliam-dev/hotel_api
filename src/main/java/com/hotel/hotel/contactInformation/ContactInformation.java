package com.hotel.hotel.contactInformation;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContactInformation {
    private String phoneNumber; 
    private String street; 
    private String neighborhood; 
    private String number; 
    private String city; 
    private String state; 
    private String complement; 
    private String postalCode;

    public ContactInformation(ContactInformationDTO data) {
        this.phoneNumber = data.phoneNumber();
        this.street = data.street();
        this.neighborhood = data.neighborhood();
        this.state = data.state();
        this.city = data.city();
        this.complement = data.complement();
        this.postalCode = data.postalCode();
        this.number = data.number();
    }
}
