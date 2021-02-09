package com.riyanurdiansyah.skripsidisya;

public class Question {
    String soal;
    String jawaban;

    public Question(String soal, String jawaban) {
        this.soal = soal;
        this.jawaban = jawaban;
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
}
