package com.tecniva.joelmartinez.rest;

import com.tecniva.joelmartinez.model.UsuarioDTO;
import com.tecniva.joelmartinez.model.UsuarioPageable;
import com.tecniva.joelmartinez.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UsuarioControllerTest {

    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    public void testFindAll() {
        UsuarioPageable usuarioPageable = new UsuarioPageable();
        when(usuarioService.findAll(anyInt(), anyInt()))
                .thenReturn(usuarioPageable);

        ResponseEntity<UsuarioPageable> responseEntity =
                usuarioController.findAll(0, 10);

        assertEquals(usuarioPageable, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFindOne() {
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(usuarioService.findOne(userId))
                .thenReturn(usuarioDTO);

        ResponseEntity<UsuarioDTO> responseEntity = usuarioController.findOne(userId);

        assertEquals(usuarioDTO, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdate() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        ResponseEntity<String> responseEntity = usuarioController.update(usuarioDTO);

        verify(usuarioService, times(1)).update(usuarioDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDelete() {
        Long userId = 1L;

        ResponseEntity<String> responseEntity = usuarioController.delete(userId);

        verify(usuarioService, times(1)).delete(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}