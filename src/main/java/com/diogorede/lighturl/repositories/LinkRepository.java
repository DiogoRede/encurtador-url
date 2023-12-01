package com.diogorede.lighturl.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogorede.lighturl.models.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, String>{
    Optional<Link> findByLinkencurtado(String linkEncurtado);
    List<Link> findByUsuarioEmail(String id);
}