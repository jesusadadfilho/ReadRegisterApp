package com.example.jesus.readregister;

import android.support.annotation.NonNull;

import junit.framework.Test;

import org.w3c.dom.Text;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by jesus on 06/03/2018.
 */
@Entity
public class Comentario implements Comparable<Comentario> {
    @Id
    private  long id;
    private String texto;
    private String capitulo;
    private ToOne<Livro> livroToOne;

    public Comentario() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }

    public ToOne<Livro> getLivroToOne() {
        return livroToOne;
    }

    public void setLivroToOne(ToOne<Livro> livroToOne) {
        this.livroToOne = livroToOne;
    }


    @Override
    public int compareTo(@NonNull Comentario comentario) {
        return getCapitulo().compareToIgnoreCase(comentario.getCapitulo());
    }
}
