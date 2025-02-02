using FestivalModel;

namespace FestivalService
{
    public interface IObserver
    {
        void rezervareReceived(Bilet bilet);
    }
}