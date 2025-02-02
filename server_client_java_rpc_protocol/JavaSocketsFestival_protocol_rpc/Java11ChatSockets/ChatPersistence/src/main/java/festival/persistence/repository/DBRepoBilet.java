package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Artist;
import festival.model.Bilet;
import festival.model.Identifier;
import festival.model.Spectecol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBRepoBilet implements IDBRepoBilet{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();


    public DBRepoBilet(Properties props) {
        logger.info("Initializing DBRepoBilet with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Bilet> Bilete = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT * \n" +
                        "FROM bilet  \n" ))
        {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {

                    int id_spectacol = result.getInt("IDSpectacol");
                    int id_angajat= result.getInt("IDAngajat");
                    int id_client = result.getInt("IDClient");
                    int id_bilet = result.getInt("IDBilet");
                    int nrLocuri = result.getInt("numarLocuriB");


                    Bilet bilet= new Bilet((long) id_angajat, (long) id_client, (long) id_spectacol,nrLocuri);
                   bilet.setId((long) id_bilet);
                    Bilete.add(bilet);

                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(Bilete);
        return Bilete;

    }

    @Override
    public Optional save(Identifier entity) {
        logger.traceEntry("saving task {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into bilet (IDAngajat,IDSpectacol,IDClient,numarLocuriB) values (?,?,?,?)")) {
            preStmt.setInt(1, ((Bilet)entity).getId_angajat().intValue());
            preStmt.setInt(2, ((Bilet)entity).getId_spectacol().intValue());
            preStmt.setInt(3, ((Bilet)entity).getId_client().intValue());
            preStmt.setInt(4,  ((Bilet)entity).getNr_locuri());
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
