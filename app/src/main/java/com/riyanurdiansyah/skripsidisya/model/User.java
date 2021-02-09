package com.riyanurdiansyah.skripsidisya.model;

@SuppressWarnings("unused")
public class User {
    String id;
    String username;
    String nama;
    String no_hp;
    String jenis_kelamin;
    String tgl_lahir;
    String alamat;
    String kelas;
    String foto;
    String role_id;

    public User(String id, String username, String nama, String no_hp,
                String jenis_kelamin, String tgl_lahir, String alamat,
                String kelas, String foto, String role_id) {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.no_hp = no_hp;
        this.jenis_kelamin = jenis_kelamin;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.kelas = kelas;
        this.foto = foto;
        this.role_id = role_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
