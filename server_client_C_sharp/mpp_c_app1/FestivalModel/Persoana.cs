using System;

namespace FestivalModel
{
    [Serializable]

    public class Persoana : Identifier<long>
    {
        public String Nume { get; set; }
        public String Prenume { get; set; }
        public long IdPersoana { get; set; }

        public Persoana(long idPersoana, String nume, String prenume)
        {
            this.IdPersoana = idPersoana;
            this.Nume = nume;
            this.Prenume = prenume;
        }

        public Persoana(String nume, String prenume)
        {
            this.Nume = nume;
            this.Prenume = prenume;
        }

        protected Persoana()
        {
            
        }
    }
}
/*
@Override
public Long getId() {
    return id_p;
}

@Override
public void setId(Long aLong) {
    this.id_p=aLong;

}
*/
