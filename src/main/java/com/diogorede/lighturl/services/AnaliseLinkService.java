package com.diogorede.lighturl.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.diogorede.lighturl.models.AnaliseLink;

public interface AnaliseLinkService {
    AnaliseLink save(AnaliseLink link);
    void delete(String id);
    Optional<AnaliseLink> findById(String id);
    List<AnaliseLink> findAnaliseLinksByLinkId(String id);
    List<AnaliseLink> findAnaliseLinksDiarioByLinkIdAndData(String id, LocalDate data);
}
