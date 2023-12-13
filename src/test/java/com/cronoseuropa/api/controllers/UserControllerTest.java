package com.cronoseuropa.api.controllers;

import com.cronoseuropa.api.models.dtos.SaveUserRequestDTO;
import com.cronoseuropa.api.services.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Controller ✨HAPPY PATH✨ tests

    @Test
    @Order(0)
    public void testCreateUser() throws Exception {

        var userToAdd = new SaveUserRequestDTO();
        userToAdd.setName("João");
        userToAdd.setSurname("Carreira");
        userToAdd.setBirthDate(LocalDate.now().minusYears(30));
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(userToAdd))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").exists());
    }

    @Test
    @Order(1)
    public void testFindAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].surname").isNotEmpty());
    }

    @Test
    @Order(2)
    public void testFindOneUserById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("João"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Carreira"));
    }

    @Test
    @Order(3)
    public void testAlterUserFieldsById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/1?surname=de Carreira")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("João"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("de Carreira"));
    }


    @Test
    @Order(4)
    public void testAlterUser() throws Exception {

        SaveUserRequestDTO  user1 = new SaveUserRequestDTO();
        user1.setSurname("to CronosEuropa");
        user1.setBirthDate("2023-12-18");

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/users/1")
                        .content(asJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("João"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("to CronosEuropa"));
    }

    @Test
    @Order(5)
    public void testDeleteUserById() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            var mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setSerializationInclusion (JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}