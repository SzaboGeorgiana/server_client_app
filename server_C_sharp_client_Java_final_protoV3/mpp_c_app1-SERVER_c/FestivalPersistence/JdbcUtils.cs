/*

using System;
using System.Data;
using Microsoft.Data.Sqlite;
using Microsoft.Extensions.Logging;

namespace lab3_clasele_C.repository
{
    public class JdbcUtils
    {
        private readonly string _url;
        private SqliteConnection _instance;
        private static readonly ILogger<JdbcUtils> Logger = LoggerFactory.GetLogger<JdbcUtils>();

        public JdbcUtils(Properties props)
        {
            _url = props["jdbc.url"].ToString();
        }

        private SqliteConnection GetNewConnection()
        {
            Logger.LogTrace("GetNewConnection method called.");

            Logger.LogInformation("Trying to connect to database: {url}", _url);

            SqliteConnection con = null;
            try
            {
                con = new SqliteConnection(_url);
                con.Open();
            }
            catch (SqliteException ex)
            {
                Logger.LogError(ex, "Error getting connection: {message}", ex.Message);
                Console.WriteLine($"Error getting connection: {ex.Message}");
            }

            Logger.LogTrace("GetNewConnection method exiting.");
            return con;
        }

        public SqliteConnection GetConnection()
        {
            Logger.LogTrace("GetConnection method called.");

            try
            {
                if (_instance == null || _instance.State == ConnectionState.Closed)
                    _instance = GetNewConnection();
            }
            catch (SqliteException ex)
            {
                Logger.LogError(ex, "Error getting connection: {message}", ex.Message);
                Console.WriteLine($"Error getting connection: {ex.Message}");
            }

            Logger.LogTrace("GetConnection method exiting.");
            return _instance;
        }
    }
}

*/