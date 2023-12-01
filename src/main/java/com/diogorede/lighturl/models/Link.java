package com.diogorede.lighturl.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="links")
public class Link {

    public Link(){}
    public Link(String link, String linkEncurtado, Usuario usuario){
        setLink(link);
        setLinkencurtado(linkEncurtado);
        setUsuario(usuario);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String link;

    public String linkencurtado;

    /*
     * Um usuario 0,*
     * um link 1,1 
     */

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkencurtado() {
        return linkencurtado;
    }

    public void setLinkencurtado(String linkencurtado) {
        this.linkencurtado = linkencurtado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}