package com.diogorede.lighturl.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diogorede.lighturl.enums.Role;
import com.diogorede.lighturl.models.Usuario;
import com.diogorede.lighturl.repositories.UsuarioRepository;
import com.diogorede.lighturl.services.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(String id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    
    @Override
    @Transactional(readOnly = true)
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        return new User(usuario.getEmail(), usuario.getSenha(), AuthorityUtils.createAuthorityList(getAuthorities(usuario.getRole())));
    }
    // Recuperar a Role do usuario
    private String getAuthorities(Role role){
        String authorities = new String();
        authorities = role.getAuth();
        return authorities;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.buscarEmail(email);
    }
}
