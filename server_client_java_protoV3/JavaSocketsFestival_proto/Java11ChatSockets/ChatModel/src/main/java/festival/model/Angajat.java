package festival.model;
import java.io.Serializable;


public class Angajat extends Persoana implements Identifier<Long>, Serializable {

    private String username;

    public Angajat() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private String oficiu;


    public Angajat(Long id_persoana, String nume, String prenume) {
        super(id_persoana, nume, prenume);
    }
    public Angajat(String usern, String parola) {
        username=usern;
        password=parola;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id_angajat) {
        super.setId(id_angajat);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOficiu() {
        return oficiu;
    }

    public void setOficiu(String oficiu) {
        this.oficiu = oficiu;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "username='" + username + '\'' +
                ", oficiu='" + oficiu + '\'' +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                '}';
    }
}