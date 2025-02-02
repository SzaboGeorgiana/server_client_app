using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using FestivalModel;
using FestivalService;

namespace FestivalNetworking
{
    public class ServerProxy:IService
    {
        
        private string host;
        private int port;

        private IObserver client;

        private NetworkStream stream;
		
        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public ServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses=new Queue<Response>();
        }

        public virtual Angajat Valid(String pass,String userN, IObserver client)
        {
            initializeConnection();
            String dto = userN + " " + pass;
           // UserDTO udto = DTOUtils.getDTO(user);
            sendRequest(new LoginRequest(dto));
            Response response =readResponse();
            if (response is OkResponse)
            {
               this.client=client;
             
               OkResponse resp = (OkResponse)response;
               if (resp.Message == "null")
               {
	               return null;
               }
               else
               {
	               string[] entitati = resp.Message.Split(' ');
	               string usrN1=null, pass1=null;
	               if (entitati.Length == 2)
	               {
		                usrN1 = entitati[0];
		                pass1 = entitati[1];
	               }
	               Angajat an = new Angajat();
	               an.Username = userN;
	               an.Password = pass;
	               an.Prenume = pass1;
	               an.Nume = usrN1;
	               return an;
               }
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err =(ErrorResponse)response;
                closeConnection();
                throw new Exceptions(err.Message);
            }
            return null;
        }
        public List<string> FindArtistiSpectacoleBilete()
        {
	        sendRequest(new FindArtistiSpectacoleBilete());
	        Response response =readResponse();
	        if (response is ListResponse)
	        {
		        // this.client=client;
             
		        ListResponse resp = (ListResponse)response;
		        return resp.Message;
	        }
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        closeConnection();
		        throw new Exceptions(err.Message);
	        }
	        return null;
        }

        public List<string> FindArtistiSpectacoleBileteDupaData(DateTime date)
        {
	        sendRequest(new FindArtistiSpectacoleBileteDupaData(date));
	        Response response =readResponse();
	        if (response is ListResponse)
	        {
		        // this.client=client;
             
		        ListResponse resp = (ListResponse)response;
		        return resp.Message;
	        }
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        closeConnection();
		        throw new Exceptions(err.Message);
	        }
	        return null;        }

        public Spectacol FindArtistiSpectacoleBileteIndex(int index)
        {
	        sendRequest(new FindArtistiSpectacoleBileteIndex(index));
	        Response response =readResponse();
	        if (response is SpectacolResponse)
	        {
		        // this.client=client;
		        SpectacolResponse resp = (SpectacolResponse)response;
		        Console.WriteLine(resp.Message.DataIncepere);
		        return resp.Message;
	        }
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        closeConnection();
		        throw new Exceptions(err.Message);
	        }
	        return null;  
        }

        public Spectacol FindArtistiSpectacoleBileteDupaDataIndex(DateTime date, int index)
        {
	        sendRequest(new FindArtistiSpectacoleBileteDupaDataIndex(date.ToString()+" "+index.ToString()));
	        Response response =readResponse();
	        if (response is SpectacolResponse)
	        {
		        // this.client=client;
		        SpectacolResponse resp = (SpectacolResponse)response;
		        Console.WriteLine(resp.Message.DataIncepere);
		        return resp.Message;
	        }
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        closeConnection();
		        throw new Exceptions(err.Message);
	        }
	        return null;          }

        public long SavePersReturnIdPers(string nume, string prenume)
        {
            throw new NotImplementedException();
        }

        public long SaveClientReturnIdClient(string nume, string prenume)
        {
            throw new NotImplementedException();
        }

        public Bilet SaveBilet(string nume, string prenume, int numarLocuriB, long IDAngajat, long IDSpectacol)
        {
	        String ciudat = nume + " " + prenume + " " + numarLocuriB + " " + IDAngajat + " " + IDSpectacol;
	        sendRequest(new SaveBilet(ciudat));
	        Response response =readResponse();
	        if (response is Update)
	        {
		     //   this.client=client;
		     BiletDoar resp = (BiletDoar)response;
		     Console.WriteLine("in save bilet din proxi dupa read response"+resp.Message.NrLocuri);
		     return resp.Message;
		        //return null;
	        }
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        closeConnection();
		        throw new Exceptions(err.Message);
	        }
	        return null;   
        }

        public Angajat logout(Angajat user, IObserver client)
        {
	        Angajat udto =user;
	        sendRequest(new LogoutRequest(udto));
	        Response response =readResponse();
	        closeConnection();
	        if (response is ErrorResponse)
	        {
		        ErrorResponse err =(ErrorResponse)response;
		        throw new Exceptions(err.Message);
	        }

	        return user;
        }
        private void closeConnection()
        {
	        finished=true;
	        try
	        {
		        stream.Close();
			
		        connection.Close();
		        _waitHandle.Close();
		        client=null;
	        }
	        catch (Exception e)
	        {
		        Console.WriteLine(e.StackTrace);
	        }

        }

        private void sendRequest(Request request)
        {
	        try
	        {
		        formatter.Serialize(stream, request);
		        stream.Flush();
	        }
	        catch (Exception e)
	        {
		        throw new Exceptions("Error sending object "+e);
	        }

        }

        private Response readResponse()
        {
	        Response response =null;
	        try
	        {
		        _waitHandle.WaitOne();
		        lock (responses)
		        {
			        //Monitor.Wait(responses); 
			        response = responses.Dequeue();
		        }

	        }
	        catch (Exception e)
	        {
		        Console.WriteLine(e.StackTrace);
	        }
	        return response;
        }
        private void initializeConnection()
		{
			 try
			 {
				connection=new TcpClient(host,port);
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				finished=false;
                _waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw =new Thread(run);
			tw.Start();
		}


		private void handleUpdate(Response update)
		{
			
			if (update is Update)
			{
				Console.WriteLine("in handel update");
				Update msgRes =(Update)update;
				try
				{
					client.rezervareReceived(msgRes.Message);
				}
				catch (Exceptions e)
				{
                    Console.WriteLine(e.StackTrace);
				}
			}
		}
		public virtual void run()
			{
				while(!finished)
				{
					try
					{
                        object response = formatter.Deserialize(stream);
						Console.WriteLine("response received "+response);
						if (response is Update)
						{
							 handleUpdate((Response)response);
						}
						else
						{
							
							lock (responses)
							{
                                					
								 
                                responses.Enqueue((Response)response);
                               
							}
                            _waitHandle.Set();
						}
					}
					catch (Exception e)
					{
						Console.WriteLine("Reading error "+e);
					}
					
				}
			}
		//}
	}

    
}