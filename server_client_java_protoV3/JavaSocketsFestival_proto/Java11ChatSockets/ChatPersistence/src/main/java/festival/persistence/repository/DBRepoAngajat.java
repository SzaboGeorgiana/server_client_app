package festival.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Identifier;
import festival.model.Persoana;

import java.sql.*;
import java.util.*;

import java.util.Optional;

//import org.mindrot.jbcrypt.BCrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class DBRepoAngajat <ID,E extends Identifier<ID>> implements IDBRepoAngajat{

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public DBRepoAngajat(Properties props) {
        System.out.println("Initializing DBRepoAngajat with properties: ");
        logger.info("Initializing DBRepoAngajat with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Iterable findAll() {
        return null;
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


//    private String hashPassword(String password) {
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//    // Metoda pentru verificarea parolei
//    private boolean verifyPassword(String candidate, String hashedPassword) {
//        return BCrypt.checkpw(candidate, hashedPassword);
//    }

    // Metoda pentru criptarea parolei
    public String hashPassword(String password) {
        return encoder.encode(password);
//
//        return password;
    }

    //     Metoda pentru verificarea parolei
    public static boolean verifyPassword(String candidate, String hashedPassword) {
        return encoder.matches(candidate, hashedPassword);
//        return false;
    }


    // Metoda pentru autentificare
    public Angajat authenticate(String u, String pass) {
       // hashPassword("Mirel");
//        hashPassword("Dana");
        System.out.println("repo autentificate: "+u);

        logger.traceEntry();
        Connection con = dbUtils.getConnection();
//        List<Bilet> Bilete = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement(
                "select * from persoana P JOIN angajat A ON A.IDPersoana=P.IDPerosana where A.username = ?" ))

        {

            preStmt.setString(1, u);
            ResultSet resultSet = preStmt.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password"); // coloana pentru parola criptată
                if (verifyPassword(pass, hashedPassword)) {
                    String firstName = resultSet.getString("nume");
                    String lastName = resultSet.getString("prenume");
                    String oficiu = resultSet.getString("oficiu");
                    Long id_ang = Long.valueOf(resultSet.getString("IDAngajat"));

                    Angajat ut = new Angajat(id_ang,firstName, lastName);
                    ut.setOficiu(oficiu);
                    ut.setPassword(pass);
                    ut.setUsername(u);
                    System.out.println(ut.getUsername());

                    return ut;
                }
//                else
//                { try (PreparedStatement updateStmt = con.prepareStatement(
//                        "UPDATE angajat SET password = ? WHERE username = ?")) {
//                                    pass=hashPassword(pass);
//
//                    updateStmt.setString(1, pass);
//                    updateStmt.setString(2, u);
//                    updateStmt.executeUpdate();
//                }
//                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}