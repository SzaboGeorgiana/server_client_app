using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using FestivalModel;


namespace FestivalPersistence
{


    public interface IDBRepoBilet : IRepository<long, Bilet>
    {
    }

    public class DBRepoBilet : IDBRepoBilet
    {
        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoBilet");

        public DBRepoBilet(string connectionString)
        {
            logger.Info("Initializing DBRepoBilet...");
            connection = new SQLiteConnection(connectionString);
        }

        public Bilet findOne(long id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Bilet> findAll()
        {
            logger.InfoFormat("findAll method called.");
            List<Bilet> Bilete = new List<Bilet>();
            using (SQLiteCommand command = new SQLiteCommand("SELECT * FROM bilet", connection))
            {
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id_spectacol = reader.GetInt32(reader.GetOrdinal("IDSpectacol"));
                        int id_angajat = reader.GetInt32(reader.GetOrdinal("IDAngajat"));
                        int id_client = reader.GetInt32(reader.GetOrdinal("IDClient"));
                        int id_bilet = reader.GetInt32(reader.GetOrdinal("IDBilet"));
                        int nrLocuri = reader.GetInt32(reader.GetOrdinal("numarLocuriB"));

                        Bilet bilet = new Bilet(id_angajat, id_client, id_spectacol, nrLocuri);
                        bilet.IdBilet = id_bilet;
                        Bilete.Add(bilet);
                    }
                }

                connection.Close();
            }

            return Bilete;
        }

        public void save(Bilet entity)
        {
            logger.InfoFormat("saving task {0} ", entity);
            using (SQLiteCommand command =
                   new SQLiteCommand(
                       "INSERT INTO bilet (IDAngajat, IDSpectacol, IDClient, numarLocuriB) VALUES (@id_angajat, @id_spectacol, @id_client, @nr_locuri)",
                       connection))
            {
                command.Parameters.AddWithValue("@id_angajat", entity.IdAngajat);
                command.Parameters.AddWithValue("@id_spectacol", entity.IdSpectacol);
                command.Parameters.AddWithValue("@id_client", entity.IdClient);
                command.Parameters.AddWithValue("@nr_locuri", entity.NrLocuri);

                connection.Open();
                int result = command.ExecuteNonQuery();
                connection.Close();
                logger.InfoFormat("Saved {0} instances", result);
            }

        }

        public void delete(long id)
        {
            throw new NotImplementedException();
        }
    }
}