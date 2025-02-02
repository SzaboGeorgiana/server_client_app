package festival.persistence.repository;

import festival.model.Bilet;
import festival.model.Identifier;
import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.Transaction;
        import java.util.List;
        import java.util.Optional;
import java.util.Properties;

public class DBRepoBiletHibernate implements IDBRepoBilet {
    private final SessionFactory sessionFactory;
    private JdbcUtils dbUtils;

    public DBRepoBiletHibernate(Properties props, SessionFactory sessionFactory) {
        this.dbUtils=new JdbcUtils(props);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Iterable<Bilet> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Bilet", Bilet.class).list();
        }
    }



    @Override
    public Optional findOne(Object longID) {
        return Optional.empty();
    }

    @Override
    public Optional delete(Object aLong) {
        return Optional.empty();
    }

    @Override
    public Optional save(Identifier bilet) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(bilet);
            transaction.commit();
            bilet.setId(id);
            return Optional.of(bilet);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Implementați metodele restante pentru findOne și delete
}
