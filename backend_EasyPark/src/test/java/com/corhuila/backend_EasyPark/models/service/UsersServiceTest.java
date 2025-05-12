package com.corhuila.backend_EasyPark.models.service;

import com.corhuila.backend_EasyPark.models.entity.Users;
import com.corhuila.backend_EasyPark.models.repository.IUsersRepository;
import com.corhuila.backend_EasyPark.requests.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private IUsersRepository usersRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private UsersService usersService;

    private final String rawPassword = "myPass123";
    private String hashedPassword;

    @BeforeEach
    void setUp() throws Exception {
        // precomputar hash SHA-256 de rawPassword
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        hashedPassword = HexFormat.of().formatHex(hash);
    }

    @Test
    void existsByEmail_delegatesToRepository() {
        when(usersRepository.existsByEmail("u@e.com")).thenReturn(true);
        assertTrue(usersService.existsByEmail("u@e.com"));
        verify(usersRepository).existsByEmail("u@e.com");
    }

    @Test
    void findById_returnsUserOrNull() {
        Users u = new Users("a@b.com","Name", rawPassword);
        when(usersRepository.findById("a@b.com")).thenReturn(Optional.of(u));
        assertSame(u, usersService.findById("a@b.com"));

        when(usersRepository.findById("x@y.com")).thenReturn(Optional.empty());
        assertNull(usersService.findById("x@y.com"));
    }

    @Test
    void hashContrasenia_producesCorrectSHA256() {
        String out = usersService.hashContrasenia(rawPassword);
        assertEquals(hashedPassword, out);
    }

    @Test
    void addUser_savesAndHashes_whenEmailNotExists() {
        Users newUser = new Users("new@u.com","Nuevo", rawPassword);
        when(usersRepository.existsByEmail("new@u.com")).thenReturn(false);
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        when(usersRepository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));

        Users saved = usersService.addUser(newUser);
        assertEquals("new@u.com", saved.getEmail());
        assertEquals(hashedPassword, saved.getPassword());
        verify(usersRepository).save(any());
    }

    @Test
    void addUser_throwsWhenEmailExists() {
        when(usersRepository.existsByEmail("dup@u.com")).thenReturn(true);
        Users dup = new Users("dup@u.com","Dup", rawPassword);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usersService.addUser(dup));
        assertEquals("El correo ya está registrado.", ex.getMessage());
        verify(usersRepository, never()).save(any());
    }

    @Test
    void loginUser_variousScenarios() {
        // no existe
        LoginRequest r1 = new LoginRequest("nobody","pwd");
        when(usersRepository.findById("nobody")).thenReturn(Optional.empty());
        assertFalse(usersService.loginUser(r1));

        // existe y pwd mal
        Users u = new Users("usr","X", hashedPassword);
        when(usersRepository.findById("usr")).thenReturn(Optional.of(u));
        LoginRequest r2 = new LoginRequest("usr","wrong");
        assertFalse(usersService.loginUser(r2));

        // existe y pwd bien
        LoginRequest r3 = new LoginRequest("usr", rawPassword);
        assertTrue(usersService.loginUser(r3));
    }

    @Test
    void verificarContrasenia_checksHashEquality() {
        assertTrue(usersService.verificarContrasenia(rawPassword, hashedPassword));
        assertFalse(usersService.verificarContrasenia("other", hashedPassword));
    }

    @Test
    void save_findAll_delete_behaviors() {
        Users u1 = new Users("a1","A1", rawPassword);
        Users u2 = new Users("a2","A2", rawPassword);
        when(usersRepository.save(u1)).thenReturn(u1);
        when(usersRepository.findAll()).thenReturn(List.of(u1, u2));

        assertSame(u1, usersService.save(u1));
        List<Users> list = usersService.findAll();
        assertEquals(2, list.size());

        usersService.delete("any@e.com");
        verify(usersRepository).deleteById("any@e.com");
    }

    @Test
    void recuperarContrasenia_sendsEmailAndSavesNewPassword() {
        Users u = new Users("test@e.com","T","oldhash");
        when(usersRepository.findById("test@e.com")).thenReturn(Optional.of(u));
        // capturar usuario guardado y mensaje enviado
        ArgumentCaptor<Users> userCap = ArgumentCaptor.forClass(Users.class);
        ArgumentCaptor<SimpleMailMessage> msgCap = ArgumentCaptor.forClass(SimpleMailMessage.class);

        usersService.recuperarContrasenia("test@e.com");

        verify(usersRepository).save(userCap.capture());
        verify(mailSender).send(msgCap.capture());

        Users saved = userCap.getValue();
        SimpleMailMessage msg = msgCap.getValue();

        // la nueva contraseña en BD debe ser distinta al antiguo hash
        assertNotEquals("oldhash", saved.getPassword());
        // el texto del correo debe contener el password plano que se envió
        String text = msg.getText();
        assertTrue(text.startsWith("Tu nueva contraseña temporal es: "));
        assertEquals("test@e.com", msg.getTo()[0]);
        assertEquals("Recuperación de contraseña EasyPark", msg.getSubject());
    }

    @Test
    void recuperarContrasenia_throwsWhenEmailNotFound() {
        when(usersRepository.findById("no@e.com")).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> usersService.recuperarContrasenia("no@e.com"));
        assertEquals("El correo no está registrado.", ex.getMessage());
    }

}