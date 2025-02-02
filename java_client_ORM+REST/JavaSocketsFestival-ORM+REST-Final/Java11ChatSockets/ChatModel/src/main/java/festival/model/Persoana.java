package festival.model;

import java.io.Serializable;

public class Persoana implements Identifier<Long>, Serializable{
    private String nume;
    private String prenume;
    private Long id_persoana;

    public Persoana(Long id_persoana, String nume, String prenume) {
        this.id_persoana=id_persoana;
        this.nume = nume;
        this.prenume = prenume;
    }

    public Persoana() {
    }

    public Persoana(int idPersoana) {
    }

    public Persoana(String nume, String prenume) {
        this.nume=nume;
        this.prenume=prenume;
    }

    @Override
    public Long getId() {
        return id_persoana;
    }

    @Override
    public void setId(Long id_persoana) {
        this.id_persoana=id_persoana;

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getPrenume() {
        return prenume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}