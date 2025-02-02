package festival.persistence.repository;

import  festival.model.Identifier;

import java.util.Optional;

public interface Repository <ID, E extends Identifier<ID>>  {
    public Iterable<E> findAll();

    Optional<E> save(E entity);

    public Optional<E> findOne(ID longID) ;

    public Optional<E> delete(ID aLong) ;

//    <E extends Identifier<ID>> Optional<E> authenticate(String u, String pass);
}