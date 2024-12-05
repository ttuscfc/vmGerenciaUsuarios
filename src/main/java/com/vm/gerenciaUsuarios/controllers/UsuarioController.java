package com.vm.gerenciaUsuarios.controllers;

import com.vm.gerenciaUsuarios.dto.UsuarioDto;
import com.vm.gerenciaUsuarios.models.Usuario;
import com.vm.gerenciaUsuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<Usuario> users = usuarioService.findAll();
        return ResponseEntity.ok(users);
        //return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /*@GetMapping
    public ResponseEntity<Page<Usuario>> findAll(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "nome") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
        Page<Usuario> users = usuarioService.findAll(nome, pageable);
        return ResponseEntity.ok(users);
    }*/

    /*@GetMapping
    public ResponseEntity<List<Usuario>> findAll(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "email") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        // Validação do tamanho da página
        if (size > 100) size = 100;

        // Obter a lista de usuários paginada
        List<Usuario> usuarios = usuarioService.findAll(nome, page, size, sort, direction);

        return ResponseEntity.ok(usuarios);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable(value = "id") Long id) {
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
        //return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.newUser(usuarioDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id") Long id, @RequestBody UsuarioDto usuarioDto) {
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(usuarioService.updateUser(id, usuarioDto));
        //return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
        Usuario user = usuarioService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        usuarioService.delete(id);
        return ResponseEntity.ok("User deleted");
        //return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }
}
