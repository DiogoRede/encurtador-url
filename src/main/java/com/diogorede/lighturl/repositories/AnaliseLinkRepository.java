package com.diogorede.lighturl.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogorede.lighturl.models.AnaliseLink;

@Repository
public interface AnaliseLinkRepository extends JpaRepository<AnaliseLink, String>{
    List<AnaliseLink> findByLinkId(String id);
    List<AnaliseLink> findByLinkIdAndData(String id, LocalDate data);
}