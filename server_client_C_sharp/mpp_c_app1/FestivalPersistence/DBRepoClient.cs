using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using FestivalModel;


namespace FestivalPersistence
{


    public interface IDBRepoClient : IRepository<long, Client>
    {
    }


    public class DBRepoClient : IDBRepoClient
    {
        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoClient");

        public DBRepoClient(string connectionString)
        {
            logger.Info("Initializing DBRepoClient...");
            connection = new SQLiteConnection(connectionString);
        }

        public Client findOne(long id)
        {
            throw new NotImplementedException();
        }


        public void delete(long id)
        {
            throw new NotImplementedException();
        }

        IEnumerable<Client> IRepository<long, Client>.findAll()
        {
            // În această metodă trebuie să selectăm toate persoanele care sunt și clienți.
            logger.InfoFormat("findAll method called.");
            List<Client> clients = new List<Client>();
            using (SQLiteCommand command = new SQLiteCommand("SELECT P.nume, P.prenume, A.IDClient " +
                                                             "FROM persoana P " +
                                                             "JOIN client A ON A.IDPersoana=P.IDPerosana", connection))
            {
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        long id = reader.GetInt64(reader.GetOrdinal("IDClient"));
                        string nume = reader.GetString(reader.GetOrdinal("nume"));
                        string prenume = reader.GetString(reader.GetOrdinal("prenume"));

                        Client client = new Client(nume, prenume);
                        client.IdPersoana = id;
                        clients.Add(client);
                    }
                }

                connection.Close();
            }

            logger.InfoFormat("Found {0} clients", clients.Count);
            return clients;
        }

        public void save(Client entity)
        {
            logger.InfoFormat("saving task {} ");
            using (SQLiteCommand command =
                   new SQLiteCommand("INSERT INTO client (IDPersoana) VALUES (@id_persoana)", connection))
            {
                command.Parameters.AddWithValue("@id_persoana", ((Persoana)entity).IdPersoana);

                connection.Open();
                int result = command.ExecuteNonQuery();
                connection.Close();
                logger.InfoFormat("Saved {0} instances", result);
            }
        }

    }
}
