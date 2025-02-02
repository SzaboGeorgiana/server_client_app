package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Artist;
import festival.model.Identifier;
import festival.model.Persoana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBRepoArtist implements IDBRepoArtist{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();


    public DBRepoArtist(Properties props) {
        logger.info("Initializing DBRepoArtist with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Artist> artisti = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT P.nume, P.prenume, P.IDPerosana, A.IDArtist\n" +
                "FROM persoana P  \n" +
                "JOIN artist A ON A.IDPersoana=P.IDPerosana \n"))
        {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");

                    int id_persoana = result.getInt("IDArtist");

                    Artist artist= new Artist((long) id_persoana,nume,prenume);
//                    artist.setNume(nume);
//                    artist.setPrenume(prenume);
                    artisti.add(artist);

                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(artisti);
        return artisti;

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
