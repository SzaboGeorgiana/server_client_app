using System;

namespace FestivalModel
{
    [Serializable]

    public class Artist : Persoana
    {
        public Artist(long idPersoana, string nume, string prenume) : base(idPersoana, nume, prenume)
        {
        }


    }
}