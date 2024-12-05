package com.vm.gerenciaUsuarios.services;

import com.vm.gerenciaUsuarios.dto.UsuarioDto;
import com.vm.gerenciaUsuarios.models.Usuario;
import com.vm.gerenciaUsuarios.repositories.UsuarioRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /*public Page<Usuario> findAll(String nome, Pageable pageable) {
        if (StringUtils.isNotBlank(nome)) {
            return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable);
        }
        return usuarioRepository.findAll(pageable);
    }*/

    /*public List<Usuario> findAll(String nome, int page, int size, String sort, String direction) {
        int offset = page * size;

        // Garantir que a direção está correta
        String orderDirection = direction.equalsIgnoreCase("DESC") ? "DESC" : "ASC";

        // Se houver filtro por nome
        if (nome != null && !nome.trim().isEmpty()) {
            return usuarioRepository.findAllWithNameFilter(nome, offset, size, sort, orderDirection);
        }

        // Sem filtro por nome
        return usuarioRepository.findAllUsuarios(offset, size, sort, orderDirection);
    }*/

    public Usuario findById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.orElse(null);
    }

    public Usuario newUser(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUser(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = findById(id);
        BeanUtils.copyProperties(usuarioDto, usuario);
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        /*Usuario user = findById(id);
        if (user != null) {
            usuarioRepository.deleteById(id);
        }
        return user;*/
        usuarioRepository.deleteById(id);
    }
}
