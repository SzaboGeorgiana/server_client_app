/**/using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using FestivalNetworking;
using FestivalPersistence;
using FestivalService;

namespace FestivalClient
{
   
   
   
   static class StartChatClient
   {
       /// <summary>
       /// The main entry point for the application.
       /// </summary>
       [STAThread]
       static void Main()
       {
           Application.EnableVisualStyles();
           Application.SetCompatibleTextRenderingDefault(false);
            
           
           //IChatServer server=new ChatServerMock();   
           //FOLOSITI FISIERE DE CONFIGURARE PENTRU A OBTINE IP SI PORT
           //exemplu in GTKClient
           IService server = new ServerProxy("127.0.0.1", 55556);
           ClientCtrl ctrl=new ClientCtrl(server);
           Form1 win=new Form1(ctrl);
           ctrl.setForm(win);
           Application.Run(win);
       }
   }
}