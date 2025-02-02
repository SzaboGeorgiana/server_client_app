package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Bilet;
import festival.model.Identifier;
import festival.model.Persoana;
import festival.model.Spectecol;
//import org.apache.logging.log4j.core.config.builder.api.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
public class DBRepoSpectacol implements IDBRepoSpectacol{
    private JdbcUtils dbUtils;
//    private static final Logger logger= LogManager.getLogger();


    @Autowired
    public DBRepoSpectacol(Properties props) {
     //   logger.info("Initializing DBRepoSpectacol with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {

      //  logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Spectecol> spectecole = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT * \n" +
                        "FROM spectacol  \n" ))
        {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {

                    int id_spectacol = result.getInt("IDSpectacol");
                    int id_artist= result.getInt("IDArtist");
                    int nrLocuri = result.getInt("numarLocuri");
                    Timestamp data= result.getTimestamp("data");
                    LocalDateTime localDateTime = data.toLocalDateTime();

                    String locul=result.getString("locul");

                    Spectecol spectecol= new Spectecol((long) id_artist, locul, localDateTime,nrLocuri);
                    spectecol.setId((long) id_spectacol);
                    spectecole.add(spectecol);

                }
            }
        } catch (SQLException e) {
          //  logger.error(e);
            System.err.println("Error DB " + e);
        }
//        logger.traceExit(spectecole);
        return spectecole;

    }

//    @Override
//    public Optional save(Identifier entity) {
//        return Optional.empty();
//
//    }

//    @Override
//    public Optional findOne(Object longID) {
//        return Optional.empty();
//    }

//    @Override
//    public Optional delete(Object aLong) {
//        return Optional.empty();
//    }


//    public Optional update(Identifier entity) {
//        return Optional.empty();
//    }


    @Override
    public Optional<Spectecol> save(Identifier entity) {
//        logger.traceEntry("saving spectecol {}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO spectacol (IDArtist, numarLocuri, data, locul) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            if (entity instanceof Spectecol) {
                Spectecol spectecol = (Spectecol) entity;
                preStmt.setInt(1, spectecol.getId_artist().intValue());
                preStmt.setInt(2, spectecol.getNr_locuri());
                preStmt.setTimestamp(3, Timestamp.valueOf(spectecol.getData_inc()));
                preStmt.setString(4, spectecol.getLocatie());
                int result = preStmt.executeUpdate();
                if (result > 0) {
                    try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            spectecol.setId(generatedKeys.getLong(1));
                        }
                    }
//                    logger.traceExit();
                    return Optional.of(spectecol);
                }
            }
        } catch (SQLException e) {
//            logger.error(e);
            System.err.println("Error DB " + e);
        }
//        logger.traceExit("save failed");
        return Optional.empty();
    }

    @Override
    public Optional<Spectecol> findOne(Object longID) {
//        logger.traceEntry("finding spectecol with id {}", longID);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM spectacol WHERE IDSpectacol = ?")) {
            preStmt.setLong(1, (Long) longID);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id_spectacol = result.getInt("IDSpectacol");
                    int id_artist = result.getInt("IDArtist");
                    int nrLocuri = result.getInt("numarLocuri");
                    Timestamp data = result.getTimestamp("data");
                    LocalDateTime localDateTime = data.toLocalDateTime();
                    String locul = result.getString("locul");

                    Spectecol spectecol = new Spectecol((long) id_artist, locul, localDateTime, nrLocuri);
                    spectecol.setId((long) id_spectacol);
//                    logger.traceExit(spectecol);
                    return Optional.of(spectecol);
                }
            }
        } catch (SQLException e) {
//            logger.error(e);
            System.err.println("Error DB " + e);
        }
//        logger.traceExit("no spectecol found with id {}", longID);
        return Optional.empty();
    }
//
    @Override
    public Optional<Spectecol> delete(Object aLong) {
//        logger.traceEntry("deleting spectecol with id {}", aLong);
        Connection con = dbUtils.getConnection();
        Optional<Spectecol> spectecol = findOne(aLong);
        if (spectecol.isPresent()) {
            try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM spectacol WHERE IDSpectacol = ?")) {
                preStmt.setLong(1, (Long) aLong);
                int result = preStmt.executeUpdate();
                if (result > 0) {
//                    logger.traceExit("deleted spectecol with id {}", aLong);
                    return spectecol;
                }
            } catch (SQLException e) {
//                logger.error(e);
                System.err.println("Error DB " + e);
            }
        }
//        logger.traceExit("no spectecol found with id {}", aLong);
        return Optional.empty();
    }

    public Optional<Spectecol> update(Identifier entity,Long id) {
//        logger.traceEntry("updating spectecol {}", entity);
        Connection con = dbUtils.getConnection();
        if (entity instanceof Spectecol) {
            Spectecol spectecol = (Spectecol) entity;
            try (PreparedStatement preStmt = con.prepareStatement("UPDATE spectacol SET IDArtist = ?, numarLocuri = ?, data = ?, locul = ? WHERE IDSpectacol = ?")) {
                preStmt.setInt(1, spectecol.getId_artist().intValue());
                preStmt.setInt(2, spectecol.getNr_locuri());
                preStmt.setTimestamp(3, Timestamp.valueOf(spectecol.getData_inc()));
                preStmt.setString(4, spectecol.getLocatie());
                preStmt.setLong(5, id);
                int result = preStmt.executeUpdate();
                if (result > 0) {
//                    logger.traceExit("updated spectecol {}", spectecol);
                    return Optional.of(spectecol);
                }
            } catch (SQLException e) {
//                logger.error(e);
                System.err.println("Error DB " + e);
            }
        }
//        logger.traceExit("update failed for spectecol {}", entity);
        return Optional.empty();
    }

}
