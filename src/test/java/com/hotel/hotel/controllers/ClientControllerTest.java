package com.hotel.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hotel.hotel.domain.client.Client;
import com.hotel.hotel.domain.client.ClientRepository;
import com.hotel.hotel.domain.contactInformation.ContactInformation;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

  @Autowired MockMvc mvc;
  @MockBean ClientRepository repository;

  // ---------- HELPERS ----------
  private String validContactInformationJson() {
    return """
      {
        "phoneNumber": "(81) 99999-9999",
        "street": "Rua X",
        "neighborhood": "Centro",
        "number": "123",
        "city": "Casinhas",
        "state": "PE",
        "complement": "Ap 101",
        "postalCode": "55750000"
      }
      """;
  }

  // ---------- LIST ----------
  @Test
  void list_shouldReturn200_andPagePayload() throws Exception {
    Client c = Mockito.mock(Client.class);
    when(c.getId()).thenReturn(1L);
    when(c.getName()).thenReturn("Ana");
    when(c.getEmail()).thenReturn("ana@example.com");
    when(c.getDateOfBirth()).thenReturn(LocalDate.of(1990,1,1));
    when(c.getContactInformation()).thenReturn(new ContactInformation(
        "(81) 99999-9999","Rua X","Centro","123","Casinhas","PE","Ap 101","55750000"
    ));

    when(repository.findAllByDeletedFalse(any()))
        .thenReturn(new PageImpl<>(List.of(c), PageRequest.of(0, 20), 1));

    mvc.perform(get("/client"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.content[0].id").value(1))
      .andExpect(jsonPath("$.content[0].name").value("Ana"))
      .andExpect(jsonPath("$.content[0].email").value("ana@example.com"))
      .andExpect(jsonPath("$.content[0].dateOfBirth").value("1990-01-01"))
      .andExpect(jsonPath("$.content[0].contactInformation.phoneNumber").value("(81) 99999-9999"))
      .andExpect(jsonPath("$.content[0].contactInformation.postalCode").value("55750000"));
  }

  // ---------- GET BY ID ----------
  @Test
  void getById_shouldReturn200_andDetailsDTO() throws Exception {
    Client c = Mockito.mock(Client.class);
    when(c.getId()).thenReturn(7L);
    when(c.getName()).thenReturn("Bruno");
    when(c.getPin()).thenReturn("12345678901");
    when(c.getEmail()).thenReturn("bruno@example.com");
    when(c.getDateOfBirth()).thenReturn(LocalDate.of(1988,5,20));
    when(c.getContactInformation()).thenReturn(new ContactInformation(
        "(81) 98888-7777","Rua Y","Bairro","456","Casinhas","PE",null,"55750001"
    ));

    when(repository.getReferenceById(7L)).thenReturn(c);

    mvc.perform(get("/client/{id}", 7L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(7))
      .andExpect(jsonPath("$.name").value("Bruno"))
      .andExpect(jsonPath("$.pin").value("12345678901"))
      .andExpect(jsonPath("$.email").value("bruno@example.com"))
      .andExpect(jsonPath("$.dateOfBirth").value("1988-05-20"))
      .andExpect(jsonPath("$.contactInformation.phoneNumber").value("(81) 98888-7777"))
      .andExpect(jsonPath("$.contactInformation.postalCode").value("55750001"));
  }

  // ---------- PATCH ----------
  @Test
  void edit_shouldReturn200_andApplyChanges() throws Exception {
    Client entity = Mockito.mock(Client.class);
    when(repository.getReferenceById(10L)).thenReturn(entity);

    when(entity.getId()).thenReturn(10L);
    when(entity.getName()).thenReturn("Nome Atualizado");
    when(entity.getPin()).thenReturn("12345678901");
    when(entity.getEmail()).thenReturn("novo@example.com");
    when(entity.getDateOfBirth()).thenReturn(LocalDate.of(1995, 5, 10));
    when(entity.getContactInformation()).thenReturn(
        new ContactInformation("(81) 90000-0000", "Nova Rua", "Novo Bairro", "777",
                                "Casinhas", "PE", "Casa", "55750002")
    );

    String body = """
      {
        "id": 10,
        "name": "Nome Atualizado",
        "email": "novo@example.com",
        "contactInformation": {
          "phoneNumber": "(81) 90000-0000",
          "street": "Nova Rua",
          "neighborhood": "Novo Bairro",
          "number": "777",
          "city": "Casinhas",
          "state": "PE",
          "complement": "Casa",
          "postalCode": "55750002"
        }
      }
      """;

    mvc.perform(patch("/client/{id}", 10L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(10));

    verify(repository).getReferenceById(10L);
  }

  @Test
  void edit_shouldReturn400_whenIdIsNullInBody() throws Exception {
    String invalidBody = """
      {
        "name": "Sem Id no body"
      }
      """;

    mvc.perform(patch("/client/{id}", 10L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidBody))
      .andExpect(status().isBadRequest());
  }

  // ---------- DELETE ----------
  @Test
  void delete_shouldReturn204_andSoftDeleteEntity() throws Exception {
    Client entity = Mockito.mock(Client.class);
    when(repository.getReferenceById(3L)).thenReturn(entity);

    mvc.perform(delete("/client/{id}", 3L))
      .andExpect(status().isNoContent());

    verify(repository).getReferenceById(3L);
    Mockito.verify(entity).delete();
  }

  // ---------- POST 201 ----------
  @Test
  void create_shouldReturn201_withLocation_andBody() throws Exception {
    String body = """
      {
        "name": "Carla",
        "pin": "12345678901",
        "email": "carla@example.com",
        "dateOfBirth": "1995-05-10",
        "contactInformation": %s
      }
      """.formatted(validContactInformationJson());

    // Ao salvar, setamos o ID no MESMO objeto recebido (para a URI usar getId()).
    when(repository.save(any())).thenAnswer(invocation -> {
      Client arg = invocation.getArgument(0);
      ReflectionTestUtils.setField(arg, "id", 42L);
      return arg;
    });

    mvc.perform(post("/client")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", Matchers.endsWith("/client/42")))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(42))
      .andExpect(jsonPath("$.name").value("Carla"))
      .andExpect(jsonPath("$.pin").value("12345678901"))
      .andExpect(jsonPath("$.email").value("carla@example.com"))
      .andExpect(jsonPath("$.dateOfBirth").value("1995-05-10"))
      .andExpect(jsonPath("$.contactInformation.phoneNumber").value("(81) 99999-9999"))
      .andExpect(jsonPath("$.contactInformation.postalCode").value("55750000"));
  }

  // ---------- POST 400 (validações) ----------
  @Test
  void create_shouldReturn400_whenPinIsInvalid() throws Exception {
    String body = """
      {
        "name": "Carla",
        "pin": "1234567890",
        "email": "carla@example.com",
        "dateOfBirth": "1995-05-10",
        "contactInformation": %s
      }
      """.formatted(validContactInformationJson());

    mvc.perform(post("/client")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isBadRequest());
  }

  @Test
  void create_shouldReturn400_whenPhoneInvalid() throws Exception {
    String contactInvalidPhone = """
      {
        "phoneNumber": "9999",
        "street": "Rua X",
        "neighborhood": "Centro",
        "number": "123",
        "city": "Casinhas",
        "state": "PE",
        "complement": "Ap 101",
        "postalCode": "55750000"
      }
      """;

    String body = """
      {
        "name": "Carla",
        "pin": "12345678901",
        "email": "carla@example.com",
        "dateOfBirth": "1995-05-10",
        "contactInformation": %s
      }
      """.formatted(contactInvalidPhone);

    mvc.perform(post("/client")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isBadRequest());
  }

  @Test
  void create_shouldReturn400_whenPostalCodeInvalid() throws Exception {
    String contactInvalidCep = """
      {
        "phoneNumber": "(81) 99999-9999",
        "street": "Rua X",
        "neighborhood": "Centro",
        "number": "123",
        "city": "Casinhas",
        "state": "PE",
        "complement": "Ap 101",
        "postalCode": "55750"
      }
      """;

    String body = """
      {
        "name": "Carla",
        "pin": "12345678901",
        "email": "carla@example.com",
        "dateOfBirth": "1995-05-10",
        "contactInformation": %s
      }
      """.formatted(contactInvalidCep);

    mvc.perform(post("/client")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isBadRequest());
  }
}
