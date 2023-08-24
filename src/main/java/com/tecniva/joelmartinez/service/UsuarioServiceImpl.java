package com.tecniva.joelmartinez.service;

import com.tecniva.joelmartinez.entity.UsuarioEntity;
import com.tecniva.joelmartinez.model.UsuarioDTO;
import com.tecniva.joelmartinez.model.UsuarioPageable;
import com.tecniva.joelmartinez.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioPageable findAll(int page, int size) {
        List<UsuarioDTO> list = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<UsuarioEntity> entities = usuarioRepository.findAll(pageRequest);

        entities.forEach(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            BeanUtils.copyProperties(usuario, dto);
            list.add(dto);
        });

        UsuarioPageable usuarioPageable = new UsuarioPageable();
        usuarioPageable.setUsuarios(list);
        usuarioPageable.setCurrentPage(entities.getNumber());
        usuarioPageable.setTotalElements(entities.getTotalElements());
        usuarioPageable.setTotalPages(entities.getTotalPages());

        return usuarioPageable;
    }

    @Override
    public UsuarioDTO findOne(Long id) {
        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            UsuarioDTO dto = new UsuarioDTO();
            BeanUtils.copyProperties(usuarioOptional.get(), dto);
            return dto;
        }

        throw new EntityNotFoundException("Resource not found with id: " + id);
    }

    @Override
    public UsuarioDTO create(UsuarioDTO dto) {
        UsuarioEntity usuario = new UsuarioEntity();
        BeanUtils.copyProperties(dto, usuario);
        UsuarioEntity saved = usuarioRepository.save(usuario);

        BeanUtils.copyProperties(saved, dto);

        return dto;
    }

    @Override
    public void update(UsuarioDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id was not provided");
        }

        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(dto.getId());

        if (usuarioOptional.isPresent()) {
            UsuarioEntity usuario = new UsuarioEntity();
            BeanUtils.copyProperties(dto, usuario);
            usuarioRepository.save(usuario);
            return;
        }

        throw new EntityNotFoundException("Resource not found with id: " + dto.getId());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id was not provided");
        }

        usuarioRepository.deleteById(id);
    }
}
