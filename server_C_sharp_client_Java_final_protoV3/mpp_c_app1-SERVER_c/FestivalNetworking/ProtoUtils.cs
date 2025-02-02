
using Google.Protobuf;

using System;
using System.Collections.Generic;
using System.Linq;
using FestivalModel;
//using System.Web.ModelBinding;
using Google.Protobuf.WellKnownTypes;
using Proto;
using Angajat = Proto.Angajat;
using Bilet = FestivalModel.Bilet;

//using Cursa = Proto.Cursa;

namespace FestivalNetworking
{
    public static class ProtoUtils
    {
       /* public static Proto.User GetProtoUser(ModelNtwkPersistenceServ.domain.User user)
        {
            return new Proto.User
            {
                Username = user.getUsername(),
                Password = user.getUsername()
            };
        }

        public static Proto.Rezervare GetProtoRezervare(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            return new Proto.Rezervare
            {
                
                Cursa = GetProtoCursa(rezervare.getCursa()),
                NumeClient = rezervare.getnumeClient(),
                NrLocuri = rezervare.getnrLocuri()
            };
        }

        public static Proto.Cursa GetProtoCursa(ModelNtwkPersistenceServ.domain.Cursa cursa)
        {
            Proto.Cursa protoCursa = new Proto.Cursa
            {
                Destinatie = cursa.getDestinatie(),
                Data = GetProtoTimestamp(cursa.getData())
            };

            if (cursa.Id != 0)
            {
                protoCursa.Id = cursa.Id;
            }

            return protoCursa;
        }
        public static long ToUnixTimeSeconds(DateTime dateTime)
        {
            DateTime unixEpoch = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
            TimeSpan timeSpan = dateTime.ToUniversalTime() - unixEpoch;
            return (long)timeSpan.TotalSeconds;
        }

        public static Timestamp GetProtoTimestamp(DateTime dateTime)
        {
            dateTime = dateTime.AddHours(3);

            long seconds = ToUnixTimeSeconds(dateTime);
            int nanos = dateTime.Millisecond * 1000000; // Convertim milisecundele în nanosecunde

            return new Timestamp
            {
                Seconds = seconds,
                Nanos = nanos
            };
        }

        public static string GetError(Proto.Response response)
        {
            return response.Error;
        }

        public static ModelNtwkPersistenceServ.domain.User GetUser(Proto.User protoUser)
        {
            var user = new  ModelNtwkPersistenceServ.domain.User(protoUser.Username, protoUser.Password);
            user.Id = (int)protoUser.Id;
            return user;
        }

        public static ModelNtwkPersistenceServ.domain.Cursa GetCursa(Proto.Cursa protoCursa)
        {
            var data = DateTimeOffset.FromUnixTimeSeconds(protoCursa.Data.Seconds).DateTime;
            var cursa = new ModelNtwkPersistenceServ.domain.Cursa(protoCursa.Destinatie, data);

            if (protoCursa.Id != 0)
            {
                cursa.Id = (int)protoCursa.Id;
            }

            return cursa;
        }

        public static ModelNtwkPersistenceServ.domain.Rezervare GetRezervare(Proto.Rezervare protoRezervare)
        {
            var cursa = GetCursa(protoRezervare.Cursa);
            var rezervare = new ModelNtwkPersistenceServ.domain.Rezervare(cursa, protoRezervare.NumeClient, protoRezervare.NrLocuri);
            rezervare.Id = (int)protoRezervare.Id;
            return rezervare;
        }

        public static ICollection<string> GetNumeClienti(IEnumerable<Proto.Rezervare> rezervari)
        {
            return rezervari.Select(rezervare => rezervare.NumeClient).ToList();
        }

        public static List<string> GetClientNames(Proto.Response response)
        {
            return response.ClientNames.ToList();
        }
        
        public static Proto.Request CreateLoginRequest(ModelNtwkPersistenceServ.domain.User user)
        {
            var protoUser = GetProtoUser(user);

            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.Login,
                User = protoUser
            };
        }

        public static Proto.Request CreateGetLocuriLibereRequest()
        {
            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.GetLocuriLibere
            };
        }

        public static Proto.Request CreateLogoutRequest(long? id)
        {
            var requestBuilder = new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.Logout
            };

            if (id != null)
            {
                requestBuilder.Id = id.Value;
            }

            return requestBuilder;
        }

       
        public static Proto.Request CreateCursaByDestDate(ModelNtwkPersistenceServ.domain.Cursa cursa)
        {
            Proto.Cursa protoCursa = GetProtoCursa(cursa);

            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.GetCursa,
                Cursa = protoCursa
            };
        }

        public static Proto.Response RezervareReceived(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            Proto.Rezervare protoRezervare = GetProtoRezervare(rezervare);

            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.NewMessage,
                Rezervare = protoRezervare
            };
        }

        public static Proto.Request CreateGetNumeClientiCuRezervariMultipleRequest(long id)
        {
            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.GetLocuriMultiple,
                Id = id
            };
        }

        public static Proto.Request CreateSaveRequest(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.Add,
                Rezervare = GetProtoRezervare(rezervare)
            };
        }

        public static Proto.Request CreateGetAllCurseRequest()
        {
            return new Proto.Request
            {
                Type = Proto.Request.Types.RequestType.GetAllCurse
            };
        }
        
        public static List<ModelNtwkPersistenceServ.domain.Cursa> GetAllCurse(Proto.Response response)
        {
            List<ModelNtwkPersistenceServ.domain.Cursa> curse = new List<ModelNtwkPersistenceServ.domain.Cursa>();
            foreach (var protoCursa in response.ListaCurse)
            {
                ModelNtwkPersistenceServ.domain.Cursa cursa = GetCursa(protoCursa);
                curse.Add(cursa);
            }
            return curse;
        }

        public static List<int> GetLocuriLibere(Proto.Response response)
        {
            List<int> locuriLibere = new List<int>();
            foreach (var locuri in response.NrLocuri)
            {
                locuriLibere.Add(locuri);
            }
            return locuriLibere;
        }
*/
        public static Proto.FestResponse CreateLoginResponse(FestivalModel.Angajat user)
        {
            var protoUser = GetUser1(user);

            return new Proto.FestResponse()
            {
                Type = Proto.FestResponse.Types.Type.Ok,
                User = protoUser
            };
        }
        
        public static Proto.Angajat GetUser1(FestivalModel.Angajat requestUser)
        {
            Console.WriteLine("in protoutils in get");
            if (requestUser != null)
            {
                Angajat user = new Angajat();
                user.Username = requestUser.Username;
                user.Password = requestUser.Password;
                user.Id = requestUser.IdPersoana.ToString();
                user.Nume = requestUser.Nume.ToString();
                user.Prenume =requestUser.Prenume;
                user.Oficiu = requestUser.Oficiu;
                //user.Id = (int)protoUser.Id;
                return user;
            }
            else
            {
                Angajat user = new Angajat();
                user.Nume ="null";
                return user;
            }
        }
        
        public static Proto.FestResponse CreateOkResponse()
        {
            return new Proto.FestResponse
            {
                Type = Proto.FestResponse.Types.Type.Ok
            };
        }

        public static Proto.FestResponse CreateErrorResponse(string error)
        {
            return new Proto.FestResponse
            {
                Type = Proto.FestResponse.Types.Type.Error,
                Error = error
            };
        }
        /*
        public static Proto.Response CreateNewMessageResponse(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.NewMessage,
                Rezervare = GetProtoRezervare(rezervare)
            };
        }

        public static Proto.Response CreateOkResponseWithNrLocuri(List<int> nrLocuri)
        {
            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.Ok,
                NrLocuri = { nrLocuri }
            };
        }
        
        public static Proto.Response CreateOkResponseWithNrCurse(IEnumerable<ModelNtwkPersistenceServ.domain.Cursa> curse)
        {
            // Inițializăm o listă pentru a stoca obiectele Proto.Cursa convertite
            var protoCurse = new List<Proto.Cursa>();

            // Iterăm prin fiecare cursă din colecție
            foreach (var cursa in curse)
            {
                // Convertim cursa din ModelNtwkPersistenceServ.domain.Cursa în Proto.Cursa
                Proto.Cursa protoCursa = GetProtoCursa(cursa);

                // Adăugăm cursa convertită în lista de obiecte Proto.Cursa
                protoCurse.Add(protoCursa);
            }

            // Construim un răspuns OK și adăugăm lista de curse
            var response = new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.Ok
            };
            response.ListaCurse.AddRange(protoCurse);

            // Returnăm obiectul Response
            return response;
        }
        
        public static Proto.Response CreateOkResponseWithCursa(ModelNtwkPersistenceServ.domain.Cursa cursa)
        {
            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.Ok,
                Cursa = ProtoUtils.GetProtoCursa(cursa)
            };
        }
        
        public static Proto.Response CreateOkResponseWithLocuriMultiple(List<string> clients)
        {
            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.Ok,
                ClientNames = { clients }
            };
        }
        
        public static Proto.Response CreateOkResponseWithRezervare(ModelNtwkPersistenceServ.domain.Rezervare rezervare)
        {
            return new Proto.Response
            {
                Type = Proto.Response.Types.ResponseType.Ok,
                Rezervare = ProtoUtils.GetProtoRezervare(rezervare)
            };
        }

        */
        public static FestivalModel.Angajat GetUser(Proto.Angajat requestUser)
        {
            FestivalModel.Angajat user = new  FestivalModel.Angajat();
            user.Username = requestUser.Username;
            user.Password=requestUser.Password;
            user.IdPersoana = long.Parse(requestUser.Id);
            return user;        
        }

        public static FestResponse CreateOkResponseListaStringuri(List<string> lista)
        {
            return new Proto.FestResponse()
            {
                Type = Proto.FestResponse.Types.Type.Ok,
                Lista  =
                {
                    lista
                }
            };
        }

        public static string GetStringul(string requestMessage)
        {
            return requestMessage;
        }

        public static FestResponse CreateOkResponseSpectacol(Spectacol sp)
        {
            var protoUser = GetSpectacol(sp);

            return new Proto.FestResponse()
            {
                Type = Proto.FestResponse.Types.Type.Ok,
                Spectacol = protoUser
            };    
        }

        private static Spectecol GetSpectacol(Spectacol sp)
        {
            Console.WriteLine("in protoutils in getSpectacol");
            
                Spectecol user = new Spectecol();
                user.Locatie = sp.Locatie;
                user.DataInc = sp.DataIncepere.ToString();
                user.IdArtist = sp.IdArtist;
                user.IdSpectacol = sp.IdSpectacol;
                user.NrLocuri =sp.NumarLocuri;
                //user.Id = (int)protoUser.Id;
                return user;
            
                }

        public static FestResponse CreateOkResponseBilet(Bilet bilet)
        {
            var protoUser = GetBilet(bilet);

            return new Proto.FestResponse()
            {
                Type = Proto.FestResponse.Types.Type.Ok,
                Bilet = protoUser
            };            }

        private static Proto.Bilet GetBilet(Bilet bilet)
        {
            Console.WriteLine("in protoutils in getBilet");
            
            Proto.Bilet user = new Proto.Bilet();
            user.IdAngajat = bilet.IdAngajat;
            user.IdClient = bilet.IdClient;
            user.IdSpectacol = bilet.IdSpectacol;
            user.NrLocuri =bilet.NrLocuri;
            //user.Id = (int)protoUser.Id;
            return user;
        }

        public static FestResponse CreateNewMessageResponse(Bilet bilet)
        {
            var protoUser = GetBilet(bilet);

            return new Proto.FestResponse()
            {
                Type = Proto.FestResponse.Types.Type.BiletSaved,
                Bilet = protoUser
            };           
        }
    }
}
