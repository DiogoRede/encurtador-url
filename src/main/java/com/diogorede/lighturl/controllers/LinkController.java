package com.diogorede.lighturl.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.diogorede.lighturl.dtos.LinkDto;
import com.diogorede.lighturl.models.Link;
import com.diogorede.lighturl.models.Usuario;
import com.diogorede.lighturl.services.LinkService;
import com.diogorede.lighturl.services.UsuarioService;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UsuarioService usuarioService;

    
    @GetMapping("/cadastro")
    public String cadastro(){
        return "/link/register";
    }

    @GetMapping("/{linkEncurtado}")
    public ModelAndView linkRedirect(@PathVariable(value = "linkEncurtado") String link){
        ModelAndView model = new ModelAndView("/link/redirect");
        System.out.println(link);
        
        //Otimizar essa busca para buscar apenas o link (está fazendo 2 select um de link e outro de usuario)
        Optional<Link> linkOptional = linkService.findByLinkEncurtado(link);
        if(linkOptional.isPresent()){
            model.addObject("link", linkOptional.get().getLink());
        }
        return model;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(LinkDto linkDto){
        ModelAndView model = new ModelAndView();
        System.out.println(linkDto.url());

        Optional<Usuario> usuarioOptional = usuarioService.findById(linkDto.id());
        if(usuarioOptional.isEmpty()){
            model.setViewName("/link/register");
            model.addObject("mensagemErro", "Usuario não encontrado!");
        }else{
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(linkDto.url().getBytes());
                
                // Substituir "/" por "-" e "+" por "_"
                String base64Hash = Base64.getEncoder().encodeToString(hashBytes)
                        .replace("/", "-")
                        .replace("+", "_");
                
                // Pegar os primeiros 10 caracteres do hash
                String shortLink = base64Hash.substring(0, 10);
                
                System.out.println("Short Link: " + shortLink);
                
                Optional<Link> linkOptional = linkService.findByLinkEncurtado(shortLink);

                if(linkOptional.isPresent()){
                    model.setViewName("/link/register");
                    model.addObject("mensagemErro", "Essa url já foi utilizada");
                }else{
                    Link link = linkService.save(new Link(linkDto.url(), shortLink, usuarioOptional.get()));
                    if(link!=null){
                        model.setViewName("/link/success-register");
                        model.addObject("linkEncurtado", link.linkencurtado);
                    }else{
                        model.setViewName("/link/register");
                        model.addObject("mensagemErro", "Não foi possível registar, tente mais tarde");
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                model.setViewName("/link/register");
                model.addObject("mensagemErro", "Não foi possível registar, tente mais tarde");
            }
        }
        return model;
    }
}