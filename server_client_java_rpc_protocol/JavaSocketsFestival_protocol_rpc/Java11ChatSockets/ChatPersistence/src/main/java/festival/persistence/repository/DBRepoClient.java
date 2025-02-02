package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Artist;
import festival.model.Client;
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

public class DBRepoClient implements IDBRepoClient{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();


    public DBRepoClient(Properties props) {
        logger.info("Initializing DBRepoArtist with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Client> clienti = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT P.nume, P.prenume, P.IDPerosana, A.IDClient\n" +
                        "FROM persoana P  \n" +
                        "JOIN client A ON A.IDPersoana=P.IDPerosana \n"))
        {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");

                    int id_persoana = result.getInt("IDClient");

                    Client artist= new Client((long) id_persoana,nume,prenume);
//                    artist.setNume(nume);
//                    artist.setPrenume(prenume);
                    clienti.add(artist);

                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(clienti);
        return clienti;
    }

    @Override
    public Optional save(Identifier entity) {
        logger.traceEntry("saving task {} ");
         Connection con = dbUtils.getConnection();
         try (PreparedStatement preStmt = con.prepareStatement("insert into client (IDPersoana) values (?)")) {
         preStmt.setInt(1,((Persoana)entity).getId().intValue());

         int result = preStmt.executeUpdate();
         logger.trace("Saved {} instances", result);
         } catch (SQLException ex) {
         logger.error(ex);
         System.err.println("Error DB " + ex);
         }
         logger.traceExit();
         return Optional.ofNullable(entity);
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

