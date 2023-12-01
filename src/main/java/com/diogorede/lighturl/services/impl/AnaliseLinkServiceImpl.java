package com.diogorede.lighturl.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogorede.lighturl.models.AnaliseLink;
import com.diogorede.lighturl.repositories.AnaliseLinkRepository;
import com.diogorede.lighturl.services.AnaliseLinkService;

@Service
public class AnaliseLinkServiceImpl implements AnaliseLinkService{

    @Autowired
    private AnaliseLinkRepository analiseRepository;

    @Override
    public AnaliseLink save(AnaliseLink link) {
        return analiseRepository.save(link);
    }

    @Override
    public void delete(String id) {
        analiseRepository.deleteById(id);
    }

    @Override
    public Optional<AnaliseLink> findById(String id) {
        return analiseRepository.findById(id);
    }

    @Override
    public List<AnaliseLink> findAnaliseLinksByLinkId(String id) {
        return analiseRepository.findByLinkId(id);
    }

    @Override
    public List<AnaliseLink> findAnaliseLinksDiarioByLinkIdAndData(String id, LocalDate data) {
        return analiseRepository.findByLinkIdAndData(id, data);
    }
    
}
