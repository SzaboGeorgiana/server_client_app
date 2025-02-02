package festival.model;
import java.io.Serializable;


public class Client extends Persoana implements Identifier<Long>, Serializable
{

    public Client(Long id_persoana, String nume, String prenume) {
        super(id_persoana, nume, prenume);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id_client) {
        super.setId(id_client);
    }


    @Override
    public String toString() {
        return "Client{" +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                '}';
    }
}