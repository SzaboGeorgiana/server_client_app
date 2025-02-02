package festival.model;

import java.io.Serializable;

public class Bilet  implements Identifier<Long>,Serializable {
    private Long id_bilet;
    private Long id_angajat;

    private Long id_client;
    private Long id_spectacol;
    private int nr_locuri;

    public Bilet(Long id_ar, Long cl, Long s, int nr_locuri) {
        this.id_angajat = id_ar;
        this.id_client = cl;
        this.id_spectacol = s;
        this.nr_locuri = nr_locuri;
    }

    @Override
    public Long getId() {
        return id_bilet;
    }

    @Override
    public void setId(Long aLong) {
        this.id_bilet = aLong;
    }


    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    public Long getId_angajat() {
        return id_angajat;
    }

    public void setId_angajat(Long id_angajat) {
        this.id_angajat = id_angajat;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Long getId_spectacol() {
        return id_spectacol;
    }

    public void setId_spectacol(Long id_spectacol) {
        this.id_spectacol = id_spectacol;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "id_an=" + id_angajat +
                ", id_cl=" + id_client +
                ", id_s=" + id_spectacol +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}