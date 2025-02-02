
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
    class StartProtoServer
    {
        private static int DEFAULT_PORT = 55556;
        private static String DEFAULT_IP = "127.0.0.1";

        static void Main(string[] args)
        {
            Console.WriteLine("Reading properties from app.config ... ");
            int port = DEFAULT_PORT;
            String ip = DEFAULT_IP;
            String portS = ConfigurationManager.AppSettings["port"];
            if (portS == null)
            {
                Console.WriteLine("Port property not set. Using default value " + DEFAULT_PORT);
                
            }
            else
            {
                bool result = Int32.TryParse(portS, out port);
                if (!result)
                {
                    Console.WriteLine("Port propery not a number. Using default value " + DEFAULT_PORT);
                    port = DEFAULT_PORT;
                    Console.WriteLine("Portul " + port);
                }
            }

            String ipS = ConfigurationManager.AppSettings["ip"];

            if (ipS == null)
            {
                Console.WriteLine("Port property not set. Using default value " + DEFAULT_IP);
            }
            //Console.WriteLine("Configuration Settings for database {0}", GetConnectionStringByName("MPPCurse"));
            //IDictionary<String, String> props = new SortedList<string, string>();
            //props.Add("ConnectionString", GetConnectionStringByName("MPPCurse"));
            DBRepoPersoana persoanaRepo =
                new DBRepoPersoana(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");

            DBRepoArtist artistRepo =
                new DBRepoArtist(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");

            DBRepoBilet biletRepo =
                new DBRepoBilet(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");

            DBRepoSpectacol spectacolRepo =
                new DBRepoSpectacol(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");

            DBRepoAngajat angajatRepo =
                new DBRepoAngajat(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");
            DBRepoClient clientRepo =
                new DBRepoClient(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");
            AuthenticationService autentificare =
                new AuthenticationService(
                    "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp_c_app1-SERVER_c/festival.db");

            // IUserRepository userRepo=new UserRepositoryMock();
            // IMessageRepository messageRepository=new MessageRepositoryMock();
            IService serviceImpl = new Service(biletRepo,artistRepo,spectacolRepo,angajatRepo,clientRepo,persoanaRepo,autentificare);

            Console.WriteLine("Starting server on IP {0} and port {1}", ip, port);
            //SerialServer serialServer = new SerialServer(ip, port, service);
            //serialServer.Start();
            ProtoV3ChatServer scs = new ProtoV3ChatServer(ip, port, serviceImpl);
            scs.Start();
            Console.WriteLine("Server started ... ");
            Console.ReadLine();
        }

        static string GetConnectionStringByName(string name)
        {
            string returnValue = null;

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
        
        public class SerialServer: ConcurrentServer 
        {
            private IService server;
            private ClientWorker worker;
            public SerialServer(string host, int port, IService server) : base(host, port)
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
        
        public class ProtoV3ChatServer : ConcurrentServer
        {
            private IService server;
            private ProtoWorker worker;
            public ProtoV3ChatServer(string host, int port, IService server)
                : base(host, port)
            {
                this.server = server;
                Console.WriteLine("ProtoChatServer...");
            }
            protected override Thread createWorker(TcpClient client)
            {
                worker = new ProtoWorker(server, client);
                return new Thread(new ThreadStart(worker.run));
            }
        }

    }
    
}/**/