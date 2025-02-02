
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Net.Sockets;
using System.Runtime.Remoting;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using FestivalService;
using Google.Protobuf;
using Proto;
using Bilet = FestivalModel.Bilet;

namespace FestivalNetworking
{
    public class ProtoWorker : IObserver
    {
         private IService service;
        private TcpClient connection;

        private NetworkStream stream;
     //   private IFormatter formatter;
        private volatile bool connected;

      //  private static Response okResponse = new Response.Builder().Type(ResponseType.OK).Build();
        public ProtoWorker(IService server, TcpClient connection)
        {
            this.service = server;
            this.connection = connection;
            try
            {
				
                stream=connection.GetStream();
              //  formatter = new BinaryFormatter();
                connected=true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        
         public virtual void run()
         {
            while(connected)
            {
                try
                {
                    Console.WriteLine("in run in ProtoWorker: " );
                    FestRequest request = FestRequest.Parser.ParseDelimitedFrom(stream);
                    Console.WriteLine("Citim in run requestul: " + request);
                    Proto.FestResponse response = handleRequest(request);
                    if (response!=null)
                    {
                        Console.WriteLine(response);
                        sendResponse((FestResponse)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
				        
                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error "+e);
            }
        }
         

      /*  public void RezervareReceived(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            Proto.Response response = ProtoUtils.CreateNewMessageResponse(rezervare);
            Console.WriteLine("Rezervare received " + rezervare);
            try
            {
                Console.WriteLine("Se trimite raspunsul.");
                sendResponse(response);
            }
            catch (IOException ex)
            {
                throw new ServerException("Sending error: " + ex.StackTrace);
            }
        }
*/
        private Proto.FestResponse handleRequest(Proto.FestRequest request)
        {
            lock (stream)
            {
                Console.WriteLine("Suntem in handleRequest la Client; c#######");
                Response response = null;
                FestivalModel.Angajat user;
                if (request.Type == Proto.FestRequest.Types.Type.Valid)
                {
                    Console.WriteLine("Login request ..." + request.Type);
                    user = ProtoUtils.GetUser(request.User);

                    try
                    {
                        FestivalModel.Angajat an = this.service.Valid(user.Password, user.Username, this);
                        //return okResponse;
                        return ProtoUtils.CreateLoginResponse(an);
                    }
                    catch (ServerException var7)
                    {
                        this.connected = false;
                        return ProtoUtils.CreateErrorResponse(var7.Message);
                    }
                    catch (Exceptions ex)
                    {
                        this.connected = false;
                        return ProtoUtils.CreateErrorResponse(ex.Message);

                    }
                }

                 if (request.Type == Proto.FestRequest.Types.Type.FindArtistiSpectacoleBilete)
                {
                    Console.WriteLine("FindArtistiSpectacoleBilete request ...." + request.Type);
                    try
                    {
                        List<string> lista = this.service.FindArtistiSpectacoleBilete();
                        Console.WriteLine("Aici in worker lista f1");
                        return ProtoUtils.CreateOkResponseListaStringuri(lista);
                    }
                    catch (ServerException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);

                    }

                }

                if (request.Type == Proto.FestRequest.Types.Type.Logout)
                {
                    Console.WriteLine("logout request ...." + request.Type);
                    
                     user=ProtoUtils.GetUser(request.User);
                    try {
                        service.logout(user, this);
                        connected=false;
                        return ProtoUtils.CreateOkResponse();

                    } catch (ServerException e) {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                   
                }

                if (request.Type == Proto.FestRequest.Types.Type.FindArtistiSpectacoleBileteDupaData)
                {
                    Console.WriteLine("FindArtistiSpectacoleBileteDupaData request ...." + request.Type);
                    DateTime dateTime = default;

                    try
                    {
                        String data = ProtoUtils.GetStringul(request.Message);
                        Console.WriteLine("Data request ...." + data);
                        try
                        {
                            // Parsarea șirului într-un obiect DateTime
                            dateTime = DateTime.ParseExact(data, "yyyy-MM-dd'T'HH:mm", CultureInfo.InvariantCulture, DateTimeStyles.None);

                            // Afișarea rezultatului
                            Console.WriteLine("Data și ora parsate: " + dateTime);
                        }
                        catch (FormatException e)
                        {
                            // În caz de eroare în parsare
                            Console.WriteLine("Nu s-a putut parsa șirul într-o dată și oră.");
                            Console.WriteLine(e.Message);
                        }

                        List<string> lista =
                            this.service.FindArtistiSpectacoleBileteDupaData(dateTime);
                        return ProtoUtils.CreateOkResponseListaStringuri(lista);
                    }
                    catch (ServerException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }

                if (request.Type == Proto.FestRequest.Types.Type.FindArtistiSpectacoleBileteIndex)
                {
                    Console.WriteLine("FindArtistiSpectacoleBileteIndex request ...." + request.Type);
                    try
                    {
                        String ind = ProtoUtils.GetStringul(request.Message);
                        int id = int.Parse(ind);
                        FestivalModel.Spectacol sp = service.FindArtistiSpectacoleBileteIndex(id);
                        Console.WriteLine("Suntem la server");
                        return ProtoUtils.CreateOkResponseSpectacol(sp);
                    }
                    catch (ServerException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }

                if (request.Type == Proto.FestRequest.Types.Type.SaveBilet)
                {
                    Console.WriteLine("Save request ...." + request.Type);
                    try
                    {
                        String
                            rezervare = ProtoUtils.GetStringul(request.Message);
                        // Parsarea șirului de intrare

// Împărțirea șirului în părți mai mici
                        string[] parts = rezervare.Split(' ');

// Extragerea datelor din părțile șirului și conversia la tipurile de date corespunzătoare
                        string nume = parts[0].Trim();
                        string prenume = parts[1].Trim();
                        int numarLocuriB;
                        if (!int.TryParse(parts[2].Trim(), out numarLocuriB))
                        {
                            Console.WriteLine("Eroare la parsarea numărului de locuri.");
                            // Tratează eroarea cum consideri necesar
                        }

                        long IDAngajat;
                        if (!long.TryParse(parts[3].Trim(), out IDAngajat))
                        {
                            Console.WriteLine("Eroare la parsarea ID-ului angajatului.");
                            // Tratează eroarea cum consideri necesar
                        }

                        long IDSpectacol;
                        if (!long.TryParse(parts[4].Trim(), out IDSpectacol))
                        {
                            Console.WriteLine("Eroare la parsarea ID-ului spectacolului.");
                            // Tratează eroarea cum consideri necesar
                        }

// Aici ai toate informațiile parsate
// Poți să le folosești în continuare în codul tău

                        FestivalModel.Bilet b=service.SaveBilet(nume,prenume,numarLocuriB,IDAngajat,IDSpectacol);
                        return ProtoUtils.CreateOkResponseBilet(b);
                    }
                    catch (ServerException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }

                if (request.Type == Proto.FestRequest.Types.Type.FindArtistiSpectacoleBileteDupaDataIndex)
                {
                    Console.WriteLine("FindArtistiSpectacoleBileteDupaDataIndex request");
                    try
                    {
                        String ind = ProtoUtils.GetStringul(request.Message);
                        Console.WriteLine(ind);
                        string[] parts = ind.Split(' '); // Separarea șirului în funcție de spațiu

// Extrage data și ora și convertește din formatul Java în formatul C#
                        DateTime dateTime;
                        if (!DateTime.TryParseExact(parts[0], "yyyy-MM-ddTHH:mm", CultureInfo.InvariantCulture, DateTimeStyles.None, out dateTime))
                        {
                            Console.WriteLine("Eroare la parsarea datei și orei.");
                            // Tratează eroarea cum consideri necesar
                        }

// Extrage indexul și îl inversează
                        int index;
                        if (!int.TryParse(parts[1], out index))
                        {
                            Console.WriteLine("Eroare la parsarea indexului.");
                            // Tratează eroarea cum consideri necesar
                        }
                        FestivalModel.Spectacol sp = service.FindArtistiSpectacoleBileteDupaDataIndex(dateTime,index);
                        Console.WriteLine("Suntem la server");
                        return ProtoUtils.CreateOkResponseSpectacol(sp);
                    }
                    catch (ServerException e)
                    {
                        return ProtoUtils.CreateErrorResponse(e.Message);
                    }
                }
                else
                {
                    return ProtoUtils.CreateOkResponse();
                }
            }

                return null;
                
        }
        

        private void sendResponse(Proto.FestResponse response)
        {
            Console.WriteLine("sending response "+response);
            lock (stream)
            {
                //formatter.Serialize(stream, response);
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }

        }

        public void rezervareReceived(Bilet bilet)
        {
            Proto.FestResponse response = ProtoUtils.CreateNewMessageResponse(bilet);
            Console.WriteLine("Rezervare received " + bilet);
            try
            {
                Console.WriteLine("Se trimite raspunsul.");
                sendResponse(response);
            }
            catch (IOException ex)
            {
                throw new ServerException("Sending error: " + ex.StackTrace);
            }        }
    }
}