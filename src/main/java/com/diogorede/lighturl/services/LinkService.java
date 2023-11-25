package com.diogorede.lighturl.services;

import java.util.Optional;

import com.diogorede.lighturl.models.Link;

public interface LinkService {
    Link save(Link link);
    void delete(String id);
    Optional<Link> findById(String id);
    Optional<Link> findByLinkEncurtado(String link);
}