package com.diogorede.lighturl.services;

import java.util.Optional;

import com.diogorede.lighturl.models.Usuario;

public interface UsuarioService {
    Usuario save(Usuario usuario);
    void delete(String id);
    Optional<Usuario> findById(String id);
    Usuario findByEmail(String email);
    Optional<Usuario> buscarPorEmail(String email);
}
