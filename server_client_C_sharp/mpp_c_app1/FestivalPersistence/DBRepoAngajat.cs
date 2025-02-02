using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Data.SQLite;
using log4net;
//using Mysqlx.Crud;


using System;
using System.Security.Cryptography;
using System.Text;
using FestivalModel;

namespace FestivalPersistence
{


    public class PasswordHasher
    {
        // Encoderul pentru hash
        private static readonly SHA256 sha256 = SHA256.Create();

        // Metoda pentru criptarea parolei
        public string HashPassword(string password)
        {
            byte[] hashedBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
            return Convert.ToBase64String(hashedBytes);
        }

        // Metoda pentru verificarea parolei
        public bool VerifyPassword(string candidate, string hashedPassword)
        {
            string candidateHash = HashPassword(candidate);
            return candidateHash == hashedPassword;
        }
    }


    public class AuthenticationService
    {
        private PasswordHasher hasher;
        private SQLiteConnection connection;

        public AuthenticationService(string connectionString)
        {
            hasher = new PasswordHasher();
            connection = new SQLiteConnection(connectionString);
        }

        public void Update(string newHashedPassword, string u)
        {
            using (connection)
            {
                connection.Open();
                string query1 = "UPDATE angajat SET password = @newHashedPassword WHERE username = @username";
                using (SQLiteCommand command1 = new SQLiteCommand(query1, connection))
                {
                    command1.Parameters.AddWithValue("@newHashedPassword", newHashedPassword);
                    command1.Parameters.AddWithValue("@username", u);
                    command1.ExecuteNonQuery();
                }

                connection.Close();
            }
        }

        // Metoda pentru autentificare
        public Angajat Authenticate(string u, string pass)
        {
            string query =
                "select * from persoana P JOIN angajat A ON A.IDPersoana=P.IDPerosana where A.username = @username";
            using (SQLiteCommand command = new SQLiteCommand(query, connection))
            {
                command.Parameters.AddWithValue("@username", u);
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        string hashedPassword = reader["password"].ToString();
                        if (hasher.VerifyPassword(pass, hashedPassword))
                        {
                            // Dacă parola este corectă, creează și întoarce obiectul Angajat
                            string firstName = reader["nume"].ToString();
                            string lastName = reader["prenume"].ToString();
                            string office = reader["oficiu"].ToString();
                            long id_ang = Convert.ToInt32(reader["IDAngajat"]);

                            Angajat angajat = new Angajat(id_ang, firstName, lastName);
                            angajat.IdPersoana = id_ang;
                            angajat.Oficiu = office;
                            connection.Close();

                            return angajat;
                        }
                    }
                }
            }

            // Dacă nu s-a găsit un utilizator cu acel username sau parola este incorectă, întoarce null
            connection.Close();
            return null;
        }
    }



    public interface IDBRepoAngajat : IRepository<long, Angajat>
    {
    }

    public class DBRepoAngajat : IDBRepoAngajat
    {

        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoArtist");

        public DBRepoAngajat(string connectionString)
        {
            logger.Info("Initializing DBRepoArtist...");
            connection = new SQLiteConnection(connectionString);
        }

        public Angajat findOne(long id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Angajat> findAll()
        {
            throw new NotImplementedException();
        }

        public void save(Angajat entity)
        {
            throw new NotImplementedException();
        }

        public void delete(long id)
        {
            throw new NotImplementedException();
        }



    }


}