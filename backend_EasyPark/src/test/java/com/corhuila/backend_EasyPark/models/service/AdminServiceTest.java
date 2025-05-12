package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Admin;
import com.corhuila.backend_EasyPark.models.repository.IAdminRepository;
import com.corhuila.backend_EasyPark.requests.LoginAdminRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private IAdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private final String rawPassword = "secret";
    private String hashedPassword;

    @BeforeEach
    void setUp() throws Exception {
        // calcular hash de ejemplo para comparaciones
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        hashedPassword = HexFormat.of().formatHex(hash);
    }

    @Test
    void existsAdminByEmail_returnsTrueWhenExists() {
        when(adminRepository.existsAdminByEmail("a@b.com")).thenReturn(true);
        assertTrue(adminService.existsAdminByEmail("a@b.com"));
    }

    @Test
    void findById_returnsAdminOrNull() {
        Admin adm = new Admin("a@b.com", "Full Name", rawPassword);
        when(adminRepository.findById("a@b.com")).thenReturn(Optional.of(adm));

        Admin found = adminService.findById("a@b.com");
        assertNotNull(found);
        assertEquals("a@b.com", found.getEmail());

        when(adminRepository.findById("x@y.com")).thenReturn(Optional.empty());
        assertNull(adminService.findById("x@y.com"));
    }

    @Test
    void hashContrasenia_producesCorrectSHA256() {
        String result = adminService.hashContrasenia(rawPassword);
        assertEquals(hashedPassword, result);
    }

    @Test
    void addAdmin_savesWhenEmailNotExists() {
        Admin toSave = new Admin("new@admin.com", "New Admin", rawPassword);
        when(adminRepository.existsAdminByEmail("new@admin.com")).thenReturn(false);
        // capturamos el Admin que se envía a save
        ArgumentCaptor<Admin> captor = ArgumentCaptor.forClass(Admin.class);
        when(adminRepository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));

        Admin saved = adminService.addAdmin(toSave);
        assertEquals("new@admin.com", saved.getEmail());
        // la contraseña debe venir hasheada
        assertEquals(hashedPassword, saved.getPassword());

        verify(adminRepository).save(any());
    }

    @Test
    void addAdmin_throwsWhenEmailExists() {
        when(adminRepository.existsAdminByEmail("dup@admin.com")).thenReturn(true);
        Admin dup = new Admin("dup@admin.com", "Dup", rawPassword);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adminService.addAdmin(dup));
        assertEquals("El correo ya está registrado.", ex.getMessage());
        verify(adminRepository, never()).save(any());
    }

    @Test
    void loginAdmin_variousScenarios() {
        // 1) no existe
        LoginAdminRequest req = new LoginAdminRequest("no@existe.com", rawPassword);
        when(adminRepository.findById("no@existe.com")).thenReturn(Optional.empty());
        assertFalse(adminService.loginAdmin(req));

        // 2) existe pero contraseña mal
        Admin adm = new Admin("u@e.com", "X", hashedPassword);
        when(adminRepository.findById("u@e.com")).thenReturn(Optional.of(adm));
        LoginAdminRequest bad = new LoginAdminRequest("u@e.com", "wrong");
        assertFalse(adminService.loginAdmin(bad));

        // 3) existe y contraseña bien
        LoginAdminRequest good = new LoginAdminRequest("u@e.com", rawPassword);
        assertTrue(adminService.loginAdmin(good));
    }

    @Test
    void save_and_findAll_and_delete_behaviors() {
        Admin a1 = new Admin("a1@x.com","A1", rawPassword);
        Admin a2 = new Admin("a2@x.com","A2", rawPassword);
        when(adminRepository.save(a1)).thenReturn(a1);
        when(adminRepository.findAll()).thenReturn(List.of(a1, a2));

        Admin out = adminService.save(a1);
        assertSame(a1, out);

        List<Admin> list = adminService.findAll();
        assertEquals(2, list.size());

        // delete
        adminService.delete("any@x.com");
        verify(adminRepository).deleteById("any@x.com");
    }

}