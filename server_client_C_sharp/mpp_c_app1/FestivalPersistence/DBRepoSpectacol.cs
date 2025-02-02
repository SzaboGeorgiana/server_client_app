using System.Globalization;
using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using FestivalModel;


namespace FestivalPersistence
{


    public interface IDBRepoSpectacol : IRepository<long, Spectacol>
    {

    }



    public class DBRepoSpectacol : IDBRepoSpectacol
    {

        private SQLiteConnection connection;
        private static readonly ILog logger = LogManager.GetLogger("DBRepoSpectacol");

        public DBRepoSpectacol(string connectionString)
        {
            logger.Info("Initializing DBRepoSpectacol...");
            connection = new SQLiteConnection(connectionString);
        }

        public Spectacol findOne(long id)
        {
            throw new NotImplementedException();
        }


        public void save(Spectacol entity)
        {
            throw new NotImplementedException();
        }

        public void delete(long id)
        {
            throw new NotImplementedException();
        }



        public IEnumerable<Spectacol> findAll()
        {
            logger.InfoFormat("FindAll method called.");
            List<Spectacol> spectacole = new List<Spectacol>();
            using (SQLiteCommand command = new SQLiteCommand("SELECT * FROM spectacol", connection))
            {
                connection.Open();
                using (SQLiteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id_spectacol = reader.GetInt32(reader.GetOrdinal("IDSpectacol"));
                        int id_artist = reader.GetInt32(reader.GetOrdinal("IDArtist"));
                        int nrLocuri = reader.GetInt32(reader.GetOrdinal("numarLocuri"));
                        string dateString = reader.GetString(reader.GetOrdinal("data"));

                        DateTime date = DateTime.ParseExact(dateString, "yyyy-MM-dd HH:mm:ss",
                            CultureInfo.InvariantCulture);
                        /* if (DateTime.TryParseExact(dateString, 'yyyy-MM-dd HH:mm:ss, CultureInfo.InvariantCulture, DateTimeStyles.None, out date))
                         {
                             // Conversia a reușit, folosește valoarea `date` în continuare
                             // Adaugă `date` în lista sau colecția ta sau efectuează alte operații necesare
                         }
                         else
                         {
                             // Dacă conversia a eșuat, poți trata datele invalide sau goale într-un alt mod,
                             // cum ar fi atribuirea unei valori implicite sau ignorarea acelei înregistrări
                             Console.WriteLine("Data invalidă sau goală: " + dateString);
                         }*/
                        string locul = reader.GetString(reader.GetOrdinal("locul"));

                        Spectacol spectacol = new Spectacol(id_artist, locul, date, nrLocuri);
                        spectacol.IdSpectacol = (id_spectacol);
                        spectacole.Add(spectacol);
                    }
                }

                connection.Close();
            }

            return spectacole;
        }
    }
}