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

public class DBRepoPersoana  implements IDBRepoPersoana{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public DBRepoPersoana(Properties props) {
        logger.info("Initializing DBRepoPersoana with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }



    @Override
    public Iterable findAll() {

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Persoana> persoane = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from \"persoana\"")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id_persoana = result.getInt("IDPerosana");
                    String nume=result.getString("nume");
                    String prenume=result.getString("prenume");
                    Persoana persoana= new Persoana(nume,prenume);
                    persoana.setId((long) id_persoana);

                    persoane.add(persoana);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(persoane);
        return persoane;

    }

    @Override
    public Optional save(Identifier entity) {
        logger.traceEntry("saving task {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into persoana (nume,prenume) values (?,?)")) {
            preStmt.setString(1, ((Persoana)entity).getNume());
            preStmt.setString(2,  ((Persoana)entity).getPrenume());
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

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Persoana> persoane = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from \"persoana\" where IDPerosana=?")) {
            preStmt.setInt(  1, (Integer) longID);
            try (ResultSet result = preStmt.executeQuery()) {

                    int id_persoana = result.getInt("IDPerosana");
                    String nume=result.getString("nume");
                    String prenume=result.getString("prenume");
                    Persoana persoana= new Persoana(nume,prenume);
                    persoana.setId((long) id_persoana);
                     return Optional.ofNullable(persoana);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(persoane);
       return Optional.empty();

    }

    @Override
    public Optional delete(Object aLong) {
        return Optional.empty();
    }



}
