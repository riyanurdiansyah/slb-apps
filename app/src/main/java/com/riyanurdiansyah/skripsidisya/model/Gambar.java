package com.riyanurdiansyah.skripsidisya.model;

public class Gambar {
    int id;
    String foto;
    String nama;
    int kategori_id;

    public Gambar(int id, String foto, String nama, int kategori_id) {
        this.id = id;
        this.foto = foto;
        this.nama = nama;
        this.kategori_id = kategori_id;
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

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }
}
