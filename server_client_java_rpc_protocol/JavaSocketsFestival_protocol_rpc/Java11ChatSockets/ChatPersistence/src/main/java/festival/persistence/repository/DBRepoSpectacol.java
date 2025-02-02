package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Bilet;
import festival.model.Identifier;
import festival.model.Persoana;
import festival.model.Spectecol;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBRepoSpectacol implements IDBRepoSpectacol{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();


    public DBRepoSpectacol(Properties props) {
        logger.info("Initializing DBRepoSpectacol with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {

        logger.traceEntry();
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
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(spectecole);
        return spectecole;

    }

    @Override
    public Optional save(Identifier entity) {
        return Optional.empty();

    }

    @Override
    public Optional findOne(Object longID) {
        return Optional.empty();
    }

    @Override
    public Optional delete(Object aLong) {
        return Optional.empty();
    }


}
