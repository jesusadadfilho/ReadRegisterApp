package com.example.jesus.readregister;

import android.support.annotation.NonNull;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Livro implements Comparable<Livro>{
    @Id private  long id;
    private String titulo;
    private String ano;
    private String genero;
    private String autor;
    private String tag;
    private int paginas;
    private String data;
    private String dataTermino;
    private float ratin;
    private String situacion;
    private int paginaAtual;
    public ToOne<Conta> dono;

    public ToMany<Comentario> comentarios;

    public Livro() {
    }

    @Override

    public int compareTo(@NonNull Livro livro) {
        return getTitulo().compareToIgnoreCase(livro.getTitulo());
    }

    public ToMany<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ToMany<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public float getRatin() {
        return ratin;
    }
    public void setRatin(float ratin) {
        this.ratin = ratin;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }
    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPaginas() {
        return paginas;
    }

    public ToOne<Conta> getDono() {
        return dono;
    }

    public void setDono(ToOne<Conta> dono) {
        this.dono = dono;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }




}
