package com.riyanurdiansyah.skripsidisya.model;

public class Kategori {
    int id;
    String foto;
    String nama;

    public Kategori(int id, String foto, String nama) {
        this.id = id;
        this.foto = foto;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
