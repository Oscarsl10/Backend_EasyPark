package com.corhuila.backend_EasyPark.controllers;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.repository.IAdminRepository;
import com.corhuila.backend_EasyPark.models.repository.IUsersRepository;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IAdminRepository adminRepository;

    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        usersRepository.deleteAll();
        adminRepository.deleteAll();

        Admin admin = new Admin();
        admin.setEmail("admin@easypark.com");
        admin.setFull_name("Administrador");
        admin.setPassword("hash-admin");
        admin.setTelefono("3000000000");
        adminRepository.save(admin);
    }

    @Test
    void addUserShouldCreateUserWhenEmailIsNew() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", "usuario@easypark.com");
        request.put("full_name", "Usuario Nuevo");
        request.put("password", "Secret123");
        request.put("telefono", "3112223333");

        mockMvc.perform(post("/api/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("usuario@easypark.com"))
                .andExpect(jsonPath("$.full_name").value("Usuario Nuevo"))
                .andExpect(jsonPath("$.password").value(org.hamcrest.Matchers.not("Secret123")))
                .andExpect(jsonPath("$.password").value(org.hamcrest.Matchers.hasLength(64)));
    }

    @Test
    void addUserShouldRejectAdminEmail() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", "admin@easypark.com");
        request.put("full_name", "Usuario Rechazado");
        request.put("password", "Secret123");
        request.put("telefono", "3112223333");

        mockMvc.perform(post("/api/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string("ADMIN_EMAIL"));
    }

    @Test
    void checkEmailShouldReturnTrueForExistingUser() throws Exception {
        requestUser("existente@easypark.com", "Usuario Existente", "Secret123", "3110000000");

        mockMvc.perform(get("/api/checkEmail").param("email", "existente@easypark.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    private void requestUser(String email, String fullName, String password, String telefono) throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", email);
        request.put("full_name", fullName);
        request.put("password", password);
        request.put("telefono", telefono);

        mockMvc.perform(post("/api/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}