using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using FestivalModel;
using FestivalService;

namespace FestivalNetworking
{
    public class ClientWorker :  IObserver 
	{
		private IService server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public ClientWorker(IService server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
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
                    object request = formatter.Deserialize(stream);
					object response =handleRequest((Request)request);
					if (response!=null)
					{
					   sendResponse((Response) response);
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
	/*	public virtual void messageReceived(Message message)
		{
			MessageDTO mdto = DTOUtils.getDTO(message);
			Console.WriteLine("Message received  "+message);
			try
			{
				sendResponse(new NewMessageResponse(mdto));
			}
			catch (Exception e)
			{
				throw new ChatException("Sending error: "+e);
			}
		}

	public virtual void friendLoggedIn(User friend)
		{
			UserDTO udto =DTOUtils.getDTO(friend);
			Console.WriteLine("Friend logged in "+friend);
			try
			{
				sendResponse(new FriendLoggedInResponse(udto));
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}
		public virtual void friendLoggedOut(User friend)
		{
			UserDTO udto =DTOUtils.getDTO(friend);
			Console.WriteLine("Friend logged out "+friend);
			try
			{
				sendResponse(new FriendLoggedOutResponse(udto));
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}
*/
		private Response handleRequest(Request request)
		{
			Response response =null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq =(LoginRequest)request;
				String udto =logReq.User;
				string[] entitati = udto.Split(' ');
				string usrn=null, pass = null;
				if (entitati.Length == 2)
				{
					 usrn = entitati[0];
					 pass = entitati[1];

					Console.WriteLine("Nume utilizator: " + usrn);
					Console.WriteLine("Parolă: " + pass);
				}
				else
				{
					Console.WriteLine("Eroare: Șirul nu conține două entități separate de spațiu.");
				}
				
				try
				{
					Angajat a = null;
                    lock (server)
                    {
                       a=server.Valid(pass,usrn, this);
                    }
                    String s;
                    if (a == null)
                    {
	                    s = "null";
                    }
                    else
                    {
	                     s = a.Nume+" "+a.Prenume;
	                     					Console.WriteLine("Nume utilizator: " + usrn);

                    }
                    Console.WriteLine("Nume prenume utilizator: " + s);

					return new OkResponse(s);
				}
				catch (Exceptions e)
				{
					connected=false;
					return new ErrorResponse(e.Message);
				}
				
			}
			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logReq =(LogoutRequest)request;
				Angajat udto =logReq.User;
				try
				{
                    lock (server)
                    {

                        server.logout(udto, this);
                    }
					connected=false;
					return new OkResponse("");

				}
				catch (Exceptions e)
				{
				   return new ErrorResponse(e.Message);
				}
			}
			if (request is FindArtistiSpectacoleBilete)
			{
				Console.WriteLine("FindArtistiSpectacoleBilete ...");
				FindArtistiSpectacoleBilete senReq =(FindArtistiSpectacoleBilete)request;
				
				try
				{
					List<string> l ;
                    lock (server)
                    {
                        l=server.FindArtistiSpectacoleBilete();
                    }
                        return new ListResponse(l);
				}
				catch (Exceptions e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is FindArtistiSpectacoleBileteDupaData)
			{
				Console.WriteLine("FindArtistiSpectacoleBileteDupaData ...");
				FindArtistiSpectacoleBileteDupaData senReq =(FindArtistiSpectacoleBileteDupaData)request;
				DateTime data = senReq.User;
				try
				{
					List<string> l ;
					lock (server)
					{
						l=server.FindArtistiSpectacoleBileteDupaData(data);
					}
					return new ListResponse(l);
				}
				catch (Exceptions e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is FindArtistiSpectacoleBileteIndex)
			{
				Console.WriteLine("FindArtistiSpectacoleBileteIndex ...");
				FindArtistiSpectacoleBileteIndex senReq =(FindArtistiSpectacoleBileteIndex)request;
				int data = senReq.User;
				try
				{
					Spectacol l ;
					lock (server)
					{
						l=server.FindArtistiSpectacoleBileteIndex(data);
						Console.WriteLine("sppp"+l.DataIncepere);
					}
					return new SpectacolResponse(l);
				}
				catch (Exceptions e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is FindArtistiSpectacoleBileteDupaDataIndex)
			{
				Console.WriteLine("FindArtistiSpectacoleBileteIndex ...");
				FindArtistiSpectacoleBileteDupaDataIndex senReq =(FindArtistiSpectacoleBileteDupaDataIndex)request;
				string text = senReq.User;
				try
				{
					DateTime date = default;
					int index=0;
					string[] parts = text.Split(' ');
			
// Verificarea dacă șirul are cel puțin două componente
					if (parts.Length >= 2)
					{
						if (DateTime.TryParse(parts[0], out date))
						{
							// Încercarea conversiei pentru a doua componentă (număr)
							if (int.TryParse(parts[1], out index))
							{
								// Folosește date și index pentru operațiile ulterioare
								Console.WriteLine("Data: " + date.ToString());
								Console.WriteLine("Index: " + index.ToString());
							}
							else
							{
								// Mesaj de eroare în cazul în care nu s-a putut converti a doua componentă într-un număr întreg
								Console.WriteLine("A doua componentă nu este un număr întreg valid.");
							}
						}
						else
						{
							// Mesaj de eroare în cazul în care nu s-a putut converti prima componentă într-o dată
							Console.WriteLine("Prima componentă nu este într-un format valid pentru dată.");
						}

					}
					Spectacol l ;
					lock (server)
					{
						l=server.FindArtistiSpectacoleBileteDupaDataIndex(date,index);
						Console.WriteLine("spectacolul: "+l.DataIncepere);
					}
					return new SpectacolResponse(l);
				}
				catch (Exceptions e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is SaveBilet)
			{
				Console.WriteLine("SaveBilet ...");
				SaveBilet senReq =(SaveBilet)request;
				String data = senReq.User;
				try
				{
					string[] componente = data.Split(' ');
					Bilet B = null;

					if (componente.Length >= 5)
					{
						string nume = componente[0].Trim();
						string prenume = componente[1].Trim();
						int numarLocuriB = int.Parse(componente[2].Trim());
						long IDAngajat = long.Parse(componente[3].Trim());
						long IDSpectacol = long.Parse(componente[4].Trim());

						// Acum ai pe nume, prenume, numarLocuriB, IDAngajat, IDSpectacol
						lock (server)
						{
							B=server.SaveBilet(nume, prenume, numarLocuriB, IDAngajat, IDSpectacol);
						}
					}

					return new BiletDoar(B);
				}
				catch (Exceptions e)
				{
					return new ErrorResponse(e.Message);
				}
			}

			return response;
		}

	private void sendResponse(Response response)
		{
			Console.WriteLine("sending response "+response);
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}

		}

		public void rezervareReceived(Bilet bilet)
		{
			Update response=new Update(bilet);

			//Response response = new Response.Builder().Type(ResponseType.NEW_MESSAGE).Data(rezervare).Build();
			
			Console.WriteLine("Rezervare received " + bilet);
			try
			{
				Console.WriteLine("Se trimite raspunsul.");
				sendResponse(response);
			}
			catch (IOException ex)
			{
				throw new Exceptions("Sending error: " + ex.StackTrace);
			}		
		}
	}
    
}