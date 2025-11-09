package com.hotel.hotel.domain.contactInformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ContactInformationTest {

  @Test
  void edit_shouldUpdateOnlyNonNulls() {
    ContactInformation ci = new ContactInformation(
        "(81) 99999-9999","Rua X","Centro","123","Casinhas","PE",null,"55750000"
    );

    // altera apenas phoneNumber e complement
    ContactInformationDTO dto = new ContactInformationDTO(
        "(81) 98888-7777", null, null, null, null, null, "Casa", null
    );

    ci.edit(dto);

    assertEquals("(81) 98888-7777", ci.getPhoneNumber());
    assertEquals("Casa", ci.getComplement());
    // demais campos preservados
    assertEquals("Rua X", ci.getStreet());
    assertEquals("55750000", ci.getPostalCode());
  }
}
