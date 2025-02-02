using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using FestivalModel;
using FestivalPersistence;
using FestivalService;

namespace FestivalServer
{
    public class Service:IService
    {

        private IRepository<long, Bilet> repository_bilet;
        private IRepository<long, Artist> repository_artist;
        private IRepository<long, Spectacol> repository_spectacol;
        private IRepository<long, Angajat> repository_angajat;
        private IRepository<long, Client> repository_client;
        private IRepository<long, Persoana> repository_persoana;
        private AuthenticationService autentificare;
        private readonly IDictionary <String, IObserver> loggedClients;

        public Service(IRepository<long, Bilet> repo_b,
            IRepository<long, Artist> repo_artist,
            IRepository<long, Spectacol> repo_spectacol,
            IRepository<long, Angajat> repo_angajat,
            IRepository<long, Client> repo_client,
            IRepository<long, Persoana> repo_persoana,
            AuthenticationService autentificare_t
        )
        {
            this.repository_bilet = repo_b;
            this.repository_artist = repo_artist;
            this.repository_spectacol = repo_spectacol;
            this.repository_angajat = repo_angajat;
            this.repository_client = repo_client;
            this.repository_persoana = repo_persoana;
            autentificare = autentificare_t;
            loggedClients=new Dictionary<String, IObserver>();

        }

        
        public List<string> FindArtistiSpectacoleBilete()
        {

            List<string> listaInregistrariBilete = new List<string>();

            foreach (Artist artist in repository_artist.findAll())
            {
                foreach (Spectacol spectacol in repository_spectacol.findAll())
                {
                    if (spectacol.IdArtist == artist.IdPersoana)
                    {
                        int nr_bilete_date = 0;
                        foreach (Bilet bilet in repository_bilet.findAll())
                        {
                            if (bilet.IdSpectacol == spectacol.IdSpectacol)
                            {
                                nr_bilete_date += bilet.NrLocuri;
                            }
                        }

                        listaInregistrariBilete.Add(artist.Nume + ' ' + artist.Prenume + ','
                                                    + spectacol.DataIncepere + ',' + spectacol.Locatie + ',' +
                                                    spectacol.NumarLocuri + ',' + nr_bilete_date);
                    }
                }
            }


            return listaInregistrariBilete;
        }

        public List<string> FindArtistiSpectacoleBileteDupaData(DateTime date)
        {

            List<string> listaInregistrariBilete = new List<string>();

            foreach (Artist artist in repository_artist.findAll())
            {
                foreach (Spectacol spectacol in repository_spectacol.findAll())
                {
                    if (spectacol.DataIncepere.Day == date.Day && spectacol.DataIncepere.Month == date.Month &&
                        spectacol.DataIncepere.Year == date.Year)
                        if (spectacol.IdArtist == artist.IdPersoana)
                        {
                            int nr_bilete_date = 0;
                            foreach (Bilet bilet in repository_bilet.findAll())
                            {
                                if (bilet.IdSpectacol == spectacol.IdSpectacol)
                                {
                                    nr_bilete_date += bilet.NrLocuri;
                                }
                            }

                            listaInregistrariBilete.Add(artist.Nume + ' ' + artist.Prenume + ','
                                                        + spectacol.DataIncepere + ',' + spectacol.Locatie + ',' +
                                                        spectacol.NumarLocuri + ',' + nr_bilete_date);
                        }
                }
            }

            return listaInregistrariBilete;
        }


        public Spectacol FindArtistiSpectacoleBileteIndex(int index)
        {

            List<string> listaInregistrariBilete = new List<string>();
            int i = 0;
            foreach (Artist artist in repository_artist.findAll())
            {
                foreach (Spectacol spectacol in repository_spectacol.findAll())
                {
                    if (spectacol.IdArtist == artist.IdPersoana)
                    {
                        if (i == index)
                            return spectacol;
                        i += 1;
                    }
                }
            }

            return null;
        }


        public Spectacol FindArtistiSpectacoleBileteDupaDataIndex(DateTime date, int index)
        {

            List<string> listaInregistrariBilete = new List<string>();
            int i = 0;
            foreach (Artist artist in repository_artist.findAll())
            {
                foreach (Spectacol spectacol in repository_spectacol.findAll())
                {
                    if (spectacol.DataIncepere.Day == date.Day && spectacol.DataIncepere.Month == date.Month &&
                        spectacol.DataIncepere.Year == date.Year)
                        if (spectacol.IdArtist == artist.IdPersoana)
                        {
                            if (i == index)
                                return spectacol;
                            i += 1;
                        }
                }
            }

            return null;
        }



        public long SavePersReturnIdPers(string nume, string prenume)
        {
            Persoana persoana = new Persoana(nume, prenume);
            repository_persoana.save(persoana);
            List<Persoana> p = (List<Persoana>)repository_persoana.findAll();

            return p[p.Count - 1].IdPersoana;
        }


        public long SaveClientReturnIdClient(string nume, string prenume)
        {
            long id = SavePersReturnIdPers(nume, prenume);
            Client pers = new Client(id, nume, prenume);
            repository_client.save(pers);
            List<Client> p = (List<Client>)repository_client.findAll();

            return p[p.Count - 1].IdPersoana;
        }

        public Bilet SaveBilet(string nume, string prenume, int numarLocuriB, long IDAngajat, long IDSpectacol)
        {
            long idClient = SaveClientReturnIdClient(nume, prenume);
            Bilet bilet = new Bilet(IDAngajat, idClient, IDSpectacol, numarLocuriB);
            repository_bilet.save(bilet);
            foreach (var obs in loggedClients.Values)
            {
                Task.Run(() =>
                    {
                        try
                        {
                            obs.rezervareReceived(bilet);
                        }
                        catch (Exceptions e)
                        {
                            Console.WriteLine("eroare obs srv");
                        }

                    })
                ;

            }
            
            return bilet;
        }

        public Angajat Valid(string pass, string u,IObserver client)
        {

            Angajat userOk = autentificare.Authenticate(u, pass);
            if (userOk!=null){
                if(loggedClients.ContainsKey(userOk.Id.ToString()))
                    throw new Exceptions("User already logged in.");
                
                Console.WriteLine("IDDDD"+userOk.IdPersoana.ToString());
                loggedClients[userOk.IdPersoana.ToString()]= client;
               // notifyFriendsLoggedIn(user);
            }
            // Apelăm metoda authenticate
            return autentificare.Authenticate(u, pass);
            //return new Angajat(122,"nume","prenume");
        }
       /*private void notifyFriendsLoggedOut(Angajat user) {
            IEnumerable<Angajat> friends=userRepository.getFriendsOf(user);
            foreach(User us in friends){
                if (loggedClients.ContainsKey(us.Id))
                {
                    IChatObserver chatClient = loggedClients[us.Id];
                    Task.Run(() =>chatClient.friendLoggedOut(user));
                }
            }
        }*/

        public Angajat logout(Angajat user, IObserver client)
        {
            IObserver localClient=loggedClients[user.IdPersoana.ToString()];
            if (localClient==null)
                throw new Exceptions("User "+user.IdPersoana+" is not logged in.");
            loggedClients.Remove(user.IdPersoana.ToString());
           // notifyFriendsLoggedOut(user);
            return user;
        }
    }
}
