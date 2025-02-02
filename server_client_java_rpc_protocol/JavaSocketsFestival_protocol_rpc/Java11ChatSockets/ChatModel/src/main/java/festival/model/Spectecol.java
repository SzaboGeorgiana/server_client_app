package festival.model;
import java.io.Serializable;


import java.time.LocalDateTime;

public class Spectecol implements Identifier<Long>,Serializable
{
    private Long id_spectacol;
    private Long id_artist;

    private String locatie;
    private LocalDateTime data_inc;
    private int nr_locuri;

    public Spectecol(Long id_ar, String locatie, LocalDateTime data_inc, int nr_locuri) {
        this.id_artist = id_ar;
        this.locatie = locatie;
        this.data_inc = data_inc;
        this.nr_locuri = nr_locuri;
    }

    @Override
    public Long getId() {
        return id_spectacol;
    }

    @Override
    public void setId(Long id_spectacol) {
        this.id_spectacol=id_spectacol;
    }

    public Long getId_artist() {
        return id_artist;
    }

    public void setId_artist(Long id_p) {
        this.id_artist = id_p;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public LocalDateTime getData_inc() {
        return data_inc;
    }

    public void setData_inc(LocalDateTime data_inc) {
        this.data_inc = data_inc;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "Spectecol{" +
                "id_ar=" + id_artist +
                ", locatie='" + locatie + '\'' +
                ", data_inc=" + data_inc +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}