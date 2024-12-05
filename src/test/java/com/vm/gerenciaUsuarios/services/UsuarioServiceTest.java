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

import java.util.Arrays;
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
    public void testFindAllWithNome() {
        String nome = "João";
        Usuario usuario1 = new Usuario("João Silva", "joao@email.com", "1234");
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario("João Souza", "joaos@email.com", "5678");
        usuario2.setId(2L);

        List<Usuario> mockUsuarios = Arrays.asList(
                usuario1,
                usuario2
        );

        Mockito.when(usuarioRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(mockUsuarios);

        List<Usuario> result = usuarioService.findAll(nome, 0, 10);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("João Silva", result.get(0).getNome());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByNomeContainingIgnoreCase(nome);
        Mockito.verify(usuarioRepository, Mockito.never()).findAll();
    }

    @Test
    public void testFindAllWithoutNome() {
        Usuario usuario1 = new Usuario( "Ana Silva", "ana@email.com", "1234");
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario("Carlos Souza", "carlos@email.com", "5678");
        usuario2.setId(2L);
        List<Usuario> mockUsuarios = Arrays.asList(
                usuario1,
                usuario2
        );

        Mockito.when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> result = usuarioService.findAll(null, 0, 10);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Ana Silva", result.get(0).getNome());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
        Mockito.verify(usuarioRepository, Mockito.never()).findByNomeContainingIgnoreCase(Mockito.anyString());
    }

    @Test
    public void testPaginationWithinBounds() {
        Usuario usuario1 = new Usuario("Ana Silva", "ana@email.com", "1234");
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario("Carlos Souza", "carlos@email.com", "5678");
        usuario2.setId(2L);
        Usuario usuario3 = new Usuario("João Oliveira", "joao@email.com", "7890");
        usuario3.setId(3L);
        List<Usuario> mockUsuarios = Arrays.asList(
                usuario1,
                usuario2,
                usuario3
        );

        Mockito.when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> result = usuarioService.findAll(null, 1, 2); // Página 1, 2 itens por página

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("João Oliveira", result.get(0).getNome());
    }

    @Test
    public void testPaginationOutOfBounds() {
        Usuario usuario1 = new Usuario("Ana Silva", "ana@email.com", "1234");
        usuario1.setId(1L);
        Usuario usuario2 = new Usuario("Carlos Souza", "carlos@email.com", "5678");
        usuario2.setId(2L);
        List<Usuario> mockUsuarios = Arrays.asList(
                usuario1,
                usuario2
        );

        Mockito.when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> result = usuarioService.findAll(null, 10, 2);

        Assertions.assertTrue(result.isEmpty());
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
