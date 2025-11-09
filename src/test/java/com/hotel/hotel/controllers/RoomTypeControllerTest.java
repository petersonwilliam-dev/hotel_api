package com.hotel.hotel.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hotel.hotel.domain.roomTypes.RoomType;
import com.hotel.hotel.domain.roomTypes.RoomTypeRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomTypeController.class)
class RoomTypeControllerTest {

  @Autowired MockMvc mvc;
  @MockBean RoomTypeRepository repository;

  @Test
  void list_shouldReturn200AndEmptyPage() throws Exception {
    when(repository.findAll(Mockito.any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 20), 0));

    mvc.perform(get("/roomtypes"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.content.length()").value(0));
  }

  @Test
  void delete_shouldReturn204() throws Exception {
    mvc.perform(delete("/roomtypes/{id}", 99L))
      .andExpect(status().isNoContent());

    verify(repository).deleteById(99L);
  }

    @Test
    void register_shouldReturn201() throws Exception {
        String body = """
            {
            "name": "Deluxe",
            "description": "Quarto com vista",
            "basePrice": 350.0,
            "amenities": "Ar, TV, Wi-Fi",
            "category": "DLX",
            "bedConfig": "1 Queen + 1 Single"
            }
            """;

        when(repository.save(any())).thenAnswer(invocation -> {
            var arg = invocation.getArgument(0, com.hotel.hotel.domain.roomTypes.RoomType.class);
            // 🔧 Simula o ID gerado pelo JPA no MESMO objeto usado pelo controller
            ReflectionTestUtils.setField(arg, "id", 7L);
            return arg;
        });

        mvc.perform(post("/roomtypes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/roomType/7")))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(7))
            .andExpect(jsonPath("$.name").value("Deluxe"))
            .andExpect(jsonPath("$.category").value("DLX"));
    }

  @Test
  void edit_shouldReturn200() throws Exception {
    String body = """
      {
        "name": "Deluxe",
        "description": "Quarto com vista Praiana",
        "basePrice": 350.0,
        "amenities": "Ar, TV, Wi-Fi, 3 quartos",
        "category": "DLX",
        "bedConfig": "1 Queen + 1 Single + 1 Queen"
      }
      """;

    RoomType entity = Mockito.mock(RoomType.class);
    when(repository.getReferenceById(7L)).thenReturn(entity);

    mvc.perform(patch("/roomtypes/{id}", 7L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
