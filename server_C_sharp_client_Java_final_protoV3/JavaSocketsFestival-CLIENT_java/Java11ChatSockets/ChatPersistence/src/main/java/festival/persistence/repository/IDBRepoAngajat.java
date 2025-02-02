package festival.persistence.repository;

import festival.model.Angajat;
import festival.model.Identifier;

import java.sql.*;
import java.util.Optional;

public interface IDBRepoAngajat<ID,E extends Identifier<ID>> extends Repository<ID,E>{

    @Override
    Iterable<E> findAll();

    @Override
    Optional<E> save(E entity);

    @Override
    Optional<E> findOne(ID longID);

    @Override
    Optional<E> delete(ID aLong);


    public String hashPassword(String password) ;

    //     Metoda pentru verificarea parolei
    public static boolean verifyPassword(String candidate, String hashedPassword) {
        return false;
    }

    // Metoda pentru autentificare
    public Angajat authenticate(String u, String pass) ;
}
