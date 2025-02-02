/*
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Sockets;

using System.Threading;
using FestivalNetworking;
using FestivalNetworking.ServerTemplate;
using FestivalPersistence;
using FestivalService;


namespace FestivalServer
{
    class StartServer
    {
        private static int DEFAULT_PORT=55556;
        private static String DEFAULT_IP="127.0.0.1";
        static void Main(string[] args)
        {
            
           // IUserRepository userRepo = new UserRepositoryMock();
           Console.WriteLine("Reading properties from app.config ...");
           int port = DEFAULT_PORT;
           String ip = DEFAULT_IP;
           String portS= ConfigurationManager.AppSettings["port"];
           if (portS == null)
           {
               Console.WriteLine("Port property not set. Using default value "+DEFAULT_PORT);
           }
           else
           {
               bool result = Int32.TryParse(portS, out port);
               if (!result)
               {
                   Console.WriteLine("Port property not a number. Using default value "+DEFAULT_PORT);
                   port = DEFAULT_PORT;
                   Console.WriteLine("Portul "+port);
               }
           }
           String ipS=ConfigurationManager.AppSettings["ip"];
           
           if (ipS == null)
           {
               Console.WriteLine("Port property not set. Using default value "+DEFAULT_IP);
           }
          /* Console.WriteLine("Configuration Settings for database {0}",GetConnectionStringByName("festival"));
           IDictionary<String, string> props = new SortedList<String, String>();
           props.Add("ConnectionString", GetConnectionStringByName("festival"));
           */
        //   IUserRepository userRepo=new UserRepositoryDb(props);
          //  IMessageRepository messageRepository=new MessageRepositoryDb(props);
            
          /*
                DBRepoPersoana persoanaRepo =
                  new DBRepoPersoana(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");

                   DBRepoArtist artistRepo =
                  new DBRepoArtist(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");

                   DBRepoBilet biletRepo =
                  new DBRepoBilet(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");

                   DBRepoSpectacol spectacolRepo =
                  new DBRepoSpectacol(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");

                   DBRepoAngajat angajatRepo =
                  new DBRepoAngajat(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");
                   DBRepoClient clientRepo =
                  new DBRepoClient(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");
                   AuthenticationService autentificare =
                  new AuthenticationService(
                      "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1/festival.db");

                 // IUserRepository userRepo=new UserRepositoryMock();
                 // IMessageRepository messageRepository=new MessageRepositoryMock();
                  IService serviceImpl = new Service(biletRepo,artistRepo,spectacolRepo,angajatRepo,clientRepo,persoanaRepo,autentificare);


                  Console.WriteLine("Starting server on IP {0} and port {1}", ip, port);
                  SerialChatServer server = new SerialChatServer(ip,port, serviceImpl);
                  server.Start();
                  Console.WriteLine("Server started ...");
                  //Console.WriteLine("Press <enter> to exit...");
                  Console.ReadLine();

              }



              static string GetConnectionStringByName(string name)
              {
                  // Assume failure.
                  string returnValue = null;

                  // Look for the name in the connectionStrings section.
                  ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

                  // If found, return the connection string.
                  if (settings != null)
                      returnValue = settings.ConnectionString;

                  return returnValue;
              }
          }

          public class SerialChatServer: ConcurrentServer
          {
              private IService server;
              private ClientWorker worker;
              public SerialChatServer(string host, int port, IService server) : base(host, port)
                  {
                      this.server = server;
                      Console.WriteLine("SerialChatServer...");
              }
              protected override Thread createWorker(TcpClient client)
              {
                  worker = new ClientWorker(server, client);
                  return new Thread(new ThreadStart(worker.run));
              }
          }

      }

      */