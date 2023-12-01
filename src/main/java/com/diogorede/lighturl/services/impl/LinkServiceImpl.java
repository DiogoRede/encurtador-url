package com.diogorede.lighturl.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogorede.lighturl.models.Link;
import com.diogorede.lighturl.repositories.LinkRepository;
import com.diogorede.lighturl.services.LinkService;

@Service
public class LinkServiceImpl implements LinkService{

    @Autowired
    private LinkRepository linkRepository;

    @Override
    public Link save(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public void delete(String id) {
        linkRepository.deleteById(id);
    }

    @Override
    public Optional<Link> findById(String id) {
        return linkRepository.findById(id);
    }

    @Override
    public Optional<Link> findByLinkEncurtado(String link) {
        return linkRepository.findByLinkencurtado(link);
    }

    @Override
    public List<Link> findByLinksUsuario(String id) {
        return linkRepository.findByUsuarioEmail(id);
    }
    
}