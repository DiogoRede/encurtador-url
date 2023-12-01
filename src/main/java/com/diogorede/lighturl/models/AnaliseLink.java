package com.diogorede.lighturl.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AnaliseLink {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /*
     * 1 analiseLink 1,1 Link
     * 1 Link 0,n analiseLink
    */

    @ManyToOne
    private Link link;

    private LocalDate data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}