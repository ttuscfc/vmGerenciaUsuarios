package com.vm.gerenciaUsuarios.services;

import com.vm.gerenciaUsuarios.dto.UsuarioDto;
import com.vm.gerenciaUsuarios.models.Usuario;
import com.vm.gerenciaUsuarios.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testFindAll() {
        Usuario usuario = new Usuario("João", "joao@email.com", "senha123");
        usuario.setId(1L);
        List<Usuario> mockUsuarios = List.of(usuario);
        Mockito.when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> usuarios = usuarioService.findAll();

        Assertions.assertNotNull(usuarios);
        Assertions.assertEquals(1, usuarios.size());
        Assertions.assertEquals("João", usuarios.get(0).getNome());
    }

    @Test
    void testFindByIdWhenExists() {
        Usuario mockUsuario = new Usuario("João", "joao@email.com", "senha123");
        mockUsuario.setId(1L);
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(mockUsuario));

        Usuario usuario = usuarioService.findById(1L);

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("João", usuario.getNome());
    }

    @Test
    void testFindByIdWhenDoesNotExist() {
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario usuario = usuarioService.findById(1L);

        Assertions.assertNull(usuario);
    }

    @Test
    void testNewUser() {
        UsuarioDto usuarioDto = new UsuarioDto("Maria", "maria@email.com", "senha456");
        Usuario mockUsuario = new Usuario("Maria", "maria@email.com", "senha456");
        mockUsuario.setId(1L);
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(mockUsuario);

        Usuario usuario = usuarioService.newUser(usuarioDto);

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("Maria", usuario.getNome());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
        Mockito.verify(emailService, Mockito.times(1)).enviaEmail(false, "maria@email.com");
    }

    @Test
    void testUpdateUser() {
        UsuarioDto usuarioDto = new UsuarioDto("Carlos", "carlos@email.com", "novaSenha");
        Usuario existingUsuario = new Usuario("João", "joao@email.com", "senha123");
        existingUsuario.setId(1L);
        Usuario updatedUsuario = new Usuario("Carlos", "carlos@email.com", "novaSenha");
        updatedUsuario.setId(1L);

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existingUsuario));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(updatedUsuario);

        Usuario usuario = usuarioService.updateUser(1L, usuarioDto);

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("Carlos", usuario.getNome());
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));
        Mockito.verify(emailService, Mockito.times(1)).enviaEmail(true, "carlos@email.com");
    }

    @Test
    void testDelete() {
        Long userId = 1L;
        Mockito.doNothing().when(usuarioRepository).deleteById(userId);

        usuarioService.delete(userId);

        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(userId);
    }
}
