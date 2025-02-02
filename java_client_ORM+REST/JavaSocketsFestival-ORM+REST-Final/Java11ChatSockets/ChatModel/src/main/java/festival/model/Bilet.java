package festival.model;

//package festival.model;
//
//import java.io.Serializable;
//
//public class Bilet  implements Identifier<Long>,Serializable {
//    private Long id_bilet;
//    private Long id_angajat;
//
//    private Long id_client;
//    private Long id_spectacol;
//    private int nr_locuri;
//
//    public Bilet(Long id_ar, Long cl, Long s, int nr_locuri) {
//        this.id_angajat = id_ar;
//        this.id_client = cl;
//        this.id_spectacol = s;
//        this.nr_locuri = nr_locuri;
//    }
//
//    @Override
//    public Long getId() {
//        return id_bilet;
//    }
//
//    @Override
//    public void setId(Long aLong) {
//        this.id_bilet = aLong;
//    }
//
//
//    public int getNr_locuri() {
//        return nr_locuri;
//    }
//
//    public void setNr_locuri(int nr_locuri) {
//        this.nr_locuri = nr_locuri;
//    }
//
//    public Long getId_angajat() {
//        return id_angajat;
//    }
//
//    public void setId_angajat(Long id_angajat) {
//        this.id_angajat = id_angajat;
//    }
//
//    public Long getId_client() {
//        return id_client;
//    }
//
//    public void setId_client(Long id_client) {
//        this.id_client = id_client;
//    }
//
//    public Long getId_spectacol() {
//        return id_spectacol;
//    }
//
//    public void setId_spectacol(Long id_spectacol) {
//        this.id_spectacol = id_spectacol;
//    }
//
//    @Override
//    public String toString() {
//        return "Bilet{" +
//                "id_an=" + id_angajat +
//                ", id_cl=" + id_client +
//                ", id_s=" + id_spectacol +
//                ", nr_locuri=" + nr_locuri +
//                '}';
//    }
//}

//import javax.persistence.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Table(name = "bilet")
public class Bilet implements Identifier<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBilet")
    private Long id;

    @Column(name = "IDAngajat")
    private Long idAngajat;

    @Column(name = "IDSpectacol")
    private Long idSpectacol;

    @Column(name = "IDClient")
    private Long idClient;

    @Column(name = "numarLocuriB")
    private int numarLocuri;

    // Constructori, getteri și setteri
    public Bilet(Long id, Long idAngajat, Long idSpectacol, Long idClient, int numarLocuri) {
        this.id = id;
        this.idAngajat = idAngajat;
        this.idSpectacol = idSpectacol;
        this.idClient = idClient;
        this.numarLocuri = numarLocuri;
    }

    public Bilet() {

    }

    public Bilet(Long idAngajat, Long idClient, Long idSpectacol, int numarLocuriB) {
        this.idAngajat = idAngajat;
        this.idSpectacol = idSpectacol;
        this.idClient = idClient;
        this.numarLocuri = numarLocuriB;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(Long idAngajat) {
        this.idAngajat = idAngajat;
    }

    public Long getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(Long idSpectacol) {
        this.idSpectacol = idSpectacol;
    }

    public Long getIdClient() {
        return idClient;
    }



    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }
}
