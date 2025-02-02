using System;
using System.Collections.Generic;
using log4net;
using System.Data.SQLite;
using FestivalModel;
using SQLiteCommand = System.Data.SQLite.SQLiteCommand;
using SQLiteConnection = System.Data.SQLite.SQLiteConnection;


namespace FestivalPersistence
{

    public interface IDBRepoPersoana : IRepository<long, Persoana>
    {
    }

    public class DBRepoPersoana : IDBRepoPersoana
    {
        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoPersoana");

        public DBRepoPersoana(string connectionString)
        {
            logger.Info("Initializing DBRepoPersoana...");
            connection = new SQLiteConnection(connectionString);
        }


        public IEnumerable<Persoana> findAll()
        {
            logger.InfoFormat("FindAll method called.");
            List<Persoana> persoane = new List<Persoana>();
            using (SQLiteCommand command = new SQLiteCommand("SELECT * FROM persoana", connection))
            {
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id = reader.GetInt32(0);
                        String nume = reader.GetString(1);
                        String prenume = reader.GetString(2);

                        Persoana p = new Persoana(id, nume, prenume);
                        persoane.Add(p);
                    }
                }

                connection.Close();
            }

            logger.InfoFormat("Found {0} persoane", persoane.Count);
            return persoane;
        }

        public Persoana findOne(long id)
        {
            logger.InfoFormat("findOne method called for id: {0}", id);
            using (SQLiteCommand command =
                   new SQLiteCommand("SELECT * FROM persoana WHERE IDPerosana = @id", connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        int persoanaId = reader.GetInt32(0);
                        string nume = reader.GetString(1);
                        string prenume = reader.GetString(2);
                        connection.Close();
                        return new Persoana(persoanaId, nume, prenume);
                    }
                }

                connection.Close();
            }

            return null;
        }


        public void save(Persoana entity)
        {
            logger.InfoFormat("save method called for persoana with id: {0}", entity.Id);
            using (SQLiteCommand command =
                   new SQLiteCommand("INSERT INTO persoana (nume, prenume) VALUES ( @nume, @prenume)", connection))
            {
                //command.Parameters.AddWithValue("@id", entity.Id);
                command.Parameters.AddWithValue("@nume", entity.Nume);
                command.Parameters.AddWithValue("@prenume", entity.Prenume);
                connection.Open();
                command.ExecuteNonQuery();
                connection.Close();
            }
        }

        public void delete(long id)
        {
            logger.InfoFormat("delete method called for id: {0}", id);
            using (SQLiteCommand command = new SQLiteCommand("DELETE FROM persoana WHERE id = @id", connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                command.ExecuteNonQuery();
                connection.Close();
            }
        }
    }
}