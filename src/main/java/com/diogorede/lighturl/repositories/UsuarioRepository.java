package com.diogorede.lighturl.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diogorede.lighturl.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByEmail(String email);

    @Query("SELECT u.email FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> buscarEmail(String email);

    Optional<Usuario> findById(String id);
}