
using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using FestivalModel;

namespace FestivalPersistence
{

    public interface IDBRepoArtist : IRepository<long, Artist>
    {
    }

    public class DBRepoArtist : IDBRepoArtist
    {
        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoArtist");

        public DBRepoArtist(string connectionString)
        {
            logger.Info("Initializing DBRepoArtist...");
            connection = new SQLiteConnection(connectionString);
        }

        public IEnumerable<Artist> findAll()
        {
            logger.InfoFormat("findAll method called.");
            List<Artist> artisti = new List<Artist>();
            using (SQLiteCommand command = new SQLiteCommand("SELECT P.nume, P.prenume, A.IDArtist " +
                                                             "FROM persoana P " +
                                                             "JOIN artist A ON A.IDPersoana=P.IDPerosana", connection))
            {
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        string nume = reader.GetString(0);
                        string prenume = reader.GetString(1);
                        long id = reader.GetInt64(2);

                        Artist artist = new Artist(id, nume, prenume);

                        artisti.Add(artist);
                    }
                }

                connection.Close();
            }

            logger.InfoFormat("Found {0} artists", artisti.Count);
            return artisti;
        }

        public Artist findOne(long id)
        {
            logger.InfoFormat("findOne method called for id: {0}", id);
            using (SQLiteCommand command = new SQLiteCommand("SELECT P.nume, P.prenume, A.IDArtist " +
                                                             "FROM persoana P " +
                                                             "JOIN artist A ON A.IDPersoana=P.IDPerosana " +
                                                             "WHERE P.IDPerosana = @id", connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        string nume = reader.GetString(0);
                        string prenume = reader.GetString(1);
                        connection.Close();
                        return new Artist(id, nume, prenume);
                    }
                }

                connection.Close();
            }

            return null;
        }

        public void save(Artist entity)
        {
            logger.InfoFormat("save method called for artist with id: {0}", entity.Id);
            using (SQLiteCommand command =
                   new SQLiteCommand("INSERT INTO persoana (nume, prenume) VALUES ( @nume, @prenume)", connection))
            {
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