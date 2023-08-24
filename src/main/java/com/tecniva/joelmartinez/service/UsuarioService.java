package com.tecniva.joelmartinez.service;

import com.tecniva.joelmartinez.model.UsuarioDTO;
import com.tecniva.joelmartinez.model.UsuarioPageable;

public interface UsuarioService {

    UsuarioPageable findAll(int page, int size);

    UsuarioDTO findOne(Long id);

    UsuarioDTO create(UsuarioDTO dto);

    void update(UsuarioDTO dto);

    void delete(Long id);

}
