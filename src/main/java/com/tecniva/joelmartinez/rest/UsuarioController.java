package com.tecniva.joelmartinez.rest;

import com.tecniva.joelmartinez.model.UsuarioDTO;
import com.tecniva.joelmartinez.model.UsuarioPageable;
import com.tecniva.joelmartinez.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/usuario", produces = "application/json")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<UsuarioPageable> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(usuarioService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.findOne(id), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> update(UsuarioDTO dto) {
        usuarioService.update(dto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(Long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

}
