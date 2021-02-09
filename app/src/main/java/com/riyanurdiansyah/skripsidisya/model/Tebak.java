package com.riyanurdiansyah.skripsidisya.model;

public class Tebak {

    int id;
    String soal;
    String jawaban;
    int metode_id;

    public Tebak(int id, String soal, String jawaban, int metode_id) {
        this.id = id;
        this.soal = soal;
        this.jawaban = jawaban;
        this.metode_id = metode_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public int getMetode_id() {
        return metode_id;
    }

    public void setMetode_id(int metode_id) {
        this.metode_id = metode_id;
    }
}
