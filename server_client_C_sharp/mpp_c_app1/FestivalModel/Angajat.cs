
using System;

namespace FestivalModel
{

    [Serializable]

    public class Angajat : Persoana
    {
        public Angajat(long idPersoana, string nume, string prenume) : base(idPersoana, nume, prenume)
        {
            idPersoana = idPersoana;
            nume = nume;
            prenume = prenume;
        }

        public String Username { get; set; }
        public String Password;

        public Angajat() : base()
        {
        }

        public String Oficiu { get; set; }


        public String toString()
        {
            return "Angajat{" +
                   "username='" + Username + '\'' +
                   ", oficiu='" + Oficiu + '\'' +
                   //   ", nume='" + nume  + '\'' +
                   //  ", prenume='" + getPrenume() + '\'' +
                   '}';
        }
    }
}