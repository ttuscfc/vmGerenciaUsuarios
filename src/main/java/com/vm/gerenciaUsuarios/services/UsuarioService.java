package com.vm.gerenciaUsuarios.services;

import com.vm.gerenciaUsuarios.dto.UsuarioDto;
import com.vm.gerenciaUsuarios.models.Usuario;
import com.vm.gerenciaUsuarios.repositories.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    EmailService emailService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.orElse(null);
    }

    public Usuario newUser(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        Usuario newUser = usuarioRepository.save(usuario);
        emailService.enviaEmail(false, newUser.getEmail());
        return newUser;
    }

    public Usuario updateUser(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = findById(id);
        BeanUtils.copyProperties(usuarioDto, usuario);
        Usuario updateUser = usuarioRepository.save(usuario);
        emailService.enviaEmail(true, updateUser.getEmail());
        return updateUser;
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
