using System;

namespace FestivalService
{
    public class Exceptions: Exception
    {
        public Exceptions():base() { }

        public Exceptions(String msg) : base(msg) { }

        public Exceptions(String msg, Exception ex) : base(msg, ex) { }

    }
}