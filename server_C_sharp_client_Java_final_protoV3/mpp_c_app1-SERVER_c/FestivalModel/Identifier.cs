using System;

namespace FestivalModel
{
    [Serializable]

    public class Identifier<ID>
    {
        public ID Id { get; set; }

    }
}