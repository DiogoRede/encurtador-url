package com.diogorede.lighturl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

import com.diogorede.lighturl.dtos.AuthDto;
import com.diogorede.lighturl.enums.Role;
import com.diogorede.lighturl.models.Usuario;
import com.diogorede.lighturl.services.UsuarioService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // @Autowired
    // private AuthenticationManager authenticationManager;
    
    @GetMapping("/login")
    public String login(){
        return "/auth/login";
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(){
        ModelAndView model = new ModelAndView();
        
        model.setViewName("auth/login");
        model.addObject("mensagemCadastro", "Login incorreto!");
        return model;
    }

    @GetMapping("/cadastro")
    public String cadastro(){
        return "/auth/register";
    }

    @PostMapping("/cadastrar")
    public ModelAndView register(AuthDto auth){
        ModelAndView model = new ModelAndView();
        try {
            Usuario usuario = new Usuario(auth.email(), BCrypt.hashpw(auth.senha(), BCrypt.gensalt()), Role.USUARIO);
            
            Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(usuario.getEmail());
            if(usuarioOptional.isPresent()){
                model.setViewName("/auth/register");
                model.addObject("mensagemErro", "Email já está em uso!");
            }else {
                usuario = usuarioService.save(usuario);
                if(usuario!=null){
                    // Cadastrou
                    model.setViewName("/auth/login");
                    model.addObject("mensagemCadastro", "Sua conta foi criada com sucesso!");
                }else{
                    model.setViewName("/auth/register");
                    model.addObject("mensagemErro", "Não foi possivel fazer o cadastro!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            model.setViewName("/auth/register");
            model.addObject("mensagemErro", "Não foi possivel fazer o cadastro!");
        }
        return model;
    }
}