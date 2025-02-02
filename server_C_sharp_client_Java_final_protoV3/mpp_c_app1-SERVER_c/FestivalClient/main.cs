/*using FestivalClient.domain;
using FestivalClient.repository;

using System;
using System.Collections.Generic;
namespace FestivalClient
{
    public class MainClass
    {
        static DBRepoPersoana persoanaRepo =
            new DBRepoPersoana(
                "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp-proiect-csharp-SzaboGeorgiana/festival.db");

        static DBRepoArtist artistRepo =
            new DBRepoArtist(
                "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp-proiect-csharp-SzaboGeorgiana/festival.db");

        static DBRepoBilet biletRepo =
            new DBRepoBilet(
                "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp-proiect-csharp-SzaboGeorgiana/festival.db");

        static DBRepoSpectacol spectacolRepo =
            new DBRepoSpectacol(
                "Data Source=C:/Users/andre/Documents/facultate/an2/MAP/mpp-proiect-csharp-SzaboGeorgiana/festival.db");

        public static void Main(string[] args)
        {
            
            try
            {
                log4net.Config.XmlConfigurator.Configure(new System.IO.FileInfo("log4net.config"));
            }
            catch (Exception ex)
            {
                Console.WriteLine("Eroare în inițializarea log4net: " + ex.Message);
            }
            var logger = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
            logger.Info("Logger-ul a fost inițializat cu succes.");

            Console.WriteLine("Toate persoanele din db");

            //     persoanaRepo.save(new Persoana(1,"Alin","Adrian"));

            List<Persoana> persoane = (List<Persoana>)persoanaRepo.findAll();

            foreach (Persoana persoana in persoane)
            {
                Console.WriteLine(persoana.Nume + " " + persoana.Prenume + "\n");
            }

            Persoana p = persoanaRepo.findOne(5);
            Console.WriteLine("Persoana gasita: " + p.Nume + " " + p.Prenume + "\n");

            Console.WriteLine("Artisti:");

            List<Artist> artist = (List<Artist>)artistRepo.findAll();

            foreach (Artist persoana in artist)
            {
                Console.WriteLine(persoana.Nume + " " + persoana.Prenume + "\n");
            }


            Console.WriteLine("Bilete:");


            biletRepo.save(new Bilet(1, 1, 1, 222));

            List<Bilet> bilete = (List<Bilet>)biletRepo.findAll();

            foreach (Bilet b in bilete)
            {
                Console.WriteLine(b.IdAngajat + " " + b.IdSpectacol + " " + b.IdClient + " " + b.NrLocuri + "\n");
            }

            Console.WriteLine("Spectacole:");

            List<Spectacol> spectacols = (List<Spectacol>)spectacolRepo.findAll();

            foreach (Spectacol s in spectacols)
            {
                Console.WriteLine(s.IdArtist + " " + s.DataIncepere + " " + s.NumarLocuri + " " + s.Locatie + "\n");
            }

        }
    }
}*/