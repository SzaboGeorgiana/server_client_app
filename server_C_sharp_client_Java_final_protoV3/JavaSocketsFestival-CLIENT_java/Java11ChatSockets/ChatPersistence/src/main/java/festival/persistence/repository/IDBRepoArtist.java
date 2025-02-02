package festival.persistence.repository;

import festival.model.Identifier;

import java.util.Optional;

public interface IDBRepoArtist <ID,E extends Identifier<ID>> extends Repository<ID,E> {

    @Override
    Iterable<E> findAll();

    @Override
    Optional<E> save(E entity);

    @Override
    Optional<E> findOne(ID longID);

    @Override
    Optional<E> delete(ID aLong);
}