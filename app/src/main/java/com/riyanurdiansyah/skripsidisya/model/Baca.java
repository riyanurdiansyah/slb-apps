package com.riyanurdiansyah.skripsidisya.model;

public class Baca {

    int id;
    String materi;
    String penjelasan;
    int metode_id;

    public Baca(int id, String materi, String penjelasan, int metode_id) {
        this.id = id;
        this.materi = materi;
        this.penjelasan = penjelasan;
        this.metode_id = metode_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

    public int getMetode_id() {
        return metode_id;
    }

    public void setMetode_id(int metode_id) {
        this.metode_id = metode_id;
    }
}
