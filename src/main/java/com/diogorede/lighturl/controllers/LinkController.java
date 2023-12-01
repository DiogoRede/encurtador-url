package com.diogorede.lighturl.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.diogorede.lighturl.dtos.LinkDto;
import com.diogorede.lighturl.models.AnaliseLink;
import com.diogorede.lighturl.models.Link;
import com.diogorede.lighturl.models.Usuario;
import com.diogorede.lighturl.services.AnaliseLinkService;
import com.diogorede.lighturl.services.LinkService;
import com.diogorede.lighturl.services.UsuarioService;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnaliseLinkService analiseLinkService;

    
    @GetMapping("/cadastro")
    public String cadastro(){
        return "/link/register";
    }

    @GetMapping("/minha-lista")
    public ModelAndView linkList(){
        ModelAndView model = new ModelAndView();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(username);
            if(usuarioOptional.isPresent()){
                List<Link> links = linkService.findByLinksUsuario(username);
                model.setViewName("link/list");
                model.addObject("listLink", links);
            }
        }else {
            model.setViewName("/auth/login");
            model.addObject("mensagemErro", "Usuario não logado");
        }
        return model;
    }

    @GetMapping("/{linkEncurtado}")
    public ModelAndView linkRedirect(@PathVariable(value = "linkEncurtado") String link){
        ModelAndView model = new ModelAndView("/link/redirect");
        //Otimizar essa busca para buscar apenas o link (está fazendo 2 select um de link e outro de usuario)
        Optional<Link> linkOptional = linkService.findByLinkEncurtado(link);
        if(linkOptional.isPresent()){
            model.addObject("link", linkOptional.get().getLink());
            AnaliseLink analiseLink = new AnaliseLink();
            analiseLink.setData(LocalDate.now());
            analiseLink.setLink(linkOptional.get());
            analiseLinkService.save(analiseLink);
        }
        return model;
    }

    @GetMapping("/analise/{id}")
    public ModelAndView informacoesLink(@PathVariable(value = "id") String id){
        ModelAndView model = new ModelAndView("/link/analise");

        Optional<Link> linkOptional = linkService.findById(id);

        if(linkOptional.isEmpty()){
            model.addObject("mensagem", "Link não encontrado");
        }else{
            model.addObject("link", linkOptional.get());
            String cliques = String.valueOf(analiseLinkService.findAnaliseLinksByLinkId(id).size());
            String cliquesDiario = String.valueOf(analiseLinkService.findAnaliseLinksDiarioByLinkIdAndData(id, LocalDate.now()).size());
            System.out.println(cliques + " | " + cliquesDiario);
            model.addObject("cliquesTotais", cliques);
            model.addObject("cliquesHoje", cliquesDiario);
        }

        return model;
    }

    @PostMapping("/excluir/{id}")
    public String excluirLink(@PathVariable(value = "id") String id){
        ModelAndView model = new ModelAndView("/link/list");

        linkService.delete(id);

        model.addObject("mensagem", "Excluido com sucesso!");

        return "redirect:/link/minha-lista";
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastrar(LinkDto linkDto){
        ModelAndView model = new ModelAndView();
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Optional<Usuario> usuarioOptional = usuarioService.buscarPorEmail(username);
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
                        Link link = new Link();
                        link.setLink(linkDto.url());
                        link.setLinkencurtado(shortLink);
                        link.setUsuario(usuarioOptional.get());

                        linkService.save(link);
                        model.setViewName("/link/success-register");
                        model.addObject("linkEncurtado", link.linkencurtado);
                    }
                } catch (NoSuchAlgorithmException e) {
                    model.setViewName("/link/register");
                    model.addObject("mensagemErro", "Não foi possível registar, tente mais tarde");
                }
            }
        } else {
            model.setViewName("/auth/login");
        }
        return model;
    }
}