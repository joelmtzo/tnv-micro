package com.tecniva.joelmartinez.service;

import com.tecniva.joelmartinez.entity.UsuarioEntity;
import com.tecniva.joelmartinez.model.UsuarioDTO;
import com.tecniva.joelmartinez.model.UsuarioPageable;
import com.tecniva.joelmartinez.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void testFindAll() {
        List<UsuarioEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new UsuarioEntity());
        Page<UsuarioEntity> mockPage = new PageImpl<>(mockEntities);

        when(usuarioRepository.findAll(any(PageRequest.class)))
                .thenReturn(mockPage);

        UsuarioPageable result = usuarioService.findAll(1, 10);

        verify(usuarioRepository).findAll(any(PageRequest.class));

        assertEquals(mockEntities.size(), result.getUsuarios().size());
    }

    @Test
    public void testFindOne_ExistingId() {
        Long userId = 1L;
        UsuarioEntity mockEntity = new UsuarioEntity();
        mockEntity.setId(userId);
        mockEntity.setNombre("Ximena");

        when(usuarioRepository.findById(userId))
                .thenReturn(Optional.of(mockEntity));

        UsuarioDTO result = usuarioService.findOne(userId);

        verify(usuarioRepository).findById(userId);
        assertNotNull(result);
        assertEquals(mockEntity.getNombre(), result.getNombre());
    }

    @Test
    public void testFindOne_NonExistingId() {
        Long nonExistingId = 100L;

        when(usuarioRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.findOne(nonExistingId));

        verify(usuarioRepository).findById(nonExistingId);
    }

    @Test
    public void create() {
        UsuarioDTO mockDto = new UsuarioDTO();
        mockDto.setNombre("Joel M");

        UsuarioEntity mockEntity = new UsuarioEntity();
        mockEntity.setId(1L);
        mockEntity.setNombre(mockDto.getNombre());

        when(usuarioRepository.save(any(UsuarioEntity.class)))
                .thenReturn(mockEntity);

        UsuarioDTO result = usuarioService.create(mockDto);

        verify(usuarioRepository).save(any(UsuarioEntity.class));

        assertNotNull(mockEntity.getId());
        assertEquals(mockEntity.getNombre(), result.getNombre());
    }

    @Test
    public void testUpdate_ExistingId() {
        Long userId = 1L;
        UsuarioDTO mockDto = new UsuarioDTO();
        mockDto.setId(userId);
        mockDto.setNombre("Nombre actualizado");

        UsuarioEntity mockEntity = new UsuarioEntity();
        mockEntity.setId(userId);

        when(usuarioRepository.findById(userId))
                .thenReturn(Optional.of(mockEntity));

        when(usuarioRepository.save(any(UsuarioEntity.class)))
                .thenReturn(mockEntity);

        assertDoesNotThrow(() -> usuarioService.update(mockDto));

        verify(usuarioRepository).findById(userId);
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    public void testUpdate_NonExistingId() {
        Long nonExistingId = 100L;
        UsuarioDTO mockDto = new UsuarioDTO();
        mockDto.setId(nonExistingId);
        mockDto.setNombre("Nombre actualizado");

        when(usuarioRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.update(mockDto));

        verify(usuarioRepository).findById(nonExistingId);
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
    }

    @Test
    public void testDelete_ExistingId() {
        Long userId = 1L;

        assertDoesNotThrow(() -> usuarioService.delete(userId));

        verify(usuarioRepository).deleteById(userId);
    }

    @Test
    public void testDelete_NullId() {
        assertThrows(IllegalArgumentException.class, () -> usuarioService.delete(null));

        verify(usuarioRepository, never()).deleteById(any(Long.class));
    }
}