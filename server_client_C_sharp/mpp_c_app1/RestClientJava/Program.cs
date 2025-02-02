using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using FestivalModel;

namespace RestClientJava
{
    internal class Program
    {
        static HttpClient client = new HttpClient();
        public static async Task Main(string[] args)
        {
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            // Get an article …;
            Spectacol result = await GetSpectacolAsync("http://localhost:8080/festival/spectacol/2");
      //      Console.WriteLine(" data incepere: "+result.DataIncepere+" Nr locuri: "+result.NumarLocuri+" Locate: "+result.Locatie);
        }
        static async Task<Spectacol> GetSpectacolAsync(string path)
        {
            Spectacol article = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                article = await response.Content.ReadAsAsync<Spectacol>();
            }
            return article;
        }
    }
}