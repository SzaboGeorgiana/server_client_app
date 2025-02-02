using System;
using System.Collections.Generic;
using FestivalModel;

namespace FestivalService
{
    public interface IService
    {
        List<string> FindArtistiSpectacoleBilete();
        List<string> FindArtistiSpectacoleBileteDupaData(DateTime date);
        Spectacol FindArtistiSpectacoleBileteIndex(int index);
        Spectacol FindArtistiSpectacoleBileteDupaDataIndex(DateTime date, int index);
        long SavePersReturnIdPers(string nume, string prenume);
        long SaveClientReturnIdClient(string nume, string prenume);
        Bilet SaveBilet(string nume, string prenume, int numarLocuriB, long IDAngajat, long IDSpectacol);
        Angajat Valid(string pass, string u,IObserver client);
        Angajat logout(Angajat user,IObserver client);

    }
}