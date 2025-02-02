package festival.model;
import java.io.Serializable;

public class Artist extends Persoana implements Identifier<Long>,Serializable

        {

    public Artist(Long id_persoana, String nume, String prenume) {
        super(id_persoana, nume, prenume);
    }

    public Artist() {
        super();
    }

    public Artist(int idPersoana) {
        super(idPersoana);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id_artist) {
        super.setId(id_artist);
    }

    @Override
    public String toString() {
        return "Artist{" +
                " nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                '}';
    }
}