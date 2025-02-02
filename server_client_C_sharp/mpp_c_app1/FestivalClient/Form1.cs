using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using FestivalModel;
using FestivalPersistence;
using FestivalServer;
using FestivalService;

namespace FestivalClient
{
    

    public partial class Form1 : Form, IObserver
    {


     
      private ClientCtrl ctrl;
        
        public Form1(ClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
           
        }
        
        private Angajat angajat;
       
        public void Initialize()
        {
        }


        public void InitModel()
        {
            
                try
                {
                    dataGridView1.Rows.Clear();

                    List<string> listaInregistrariBilete = new List<string>();

//                listaInregistrariBilete = service.FindArtistiSpectacoleBilete();
                    listaInregistrariBilete = ctrl.FindArtistiSpectacoleBilete();

                    foreach (string entry in listaInregistrariBilete)
                    {
                        string[] parts = entry.Split(',');

                        if (parts.Length == 5) // Ensure correct number of parts
                        {
                            string name = parts[0];
                            DateTime date = DateTime.Parse(parts[1]);
                            string location = parts[2];
                            int value1 = int.Parse(parts[3]);
                            int value2 = int.Parse(parts[4]);

                            dataGridView1.Rows.Add(name, date, location, value1 - value2, value2);
                        }
                    }
                }
                catch (Exception e)
                {
                    MessageBox.Show(e.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            
        }

        public void HandleValid()
        {
           /* 
            */
           string parola = password.Text;
           string usrn = username.Text;
           password.Clear();
           username.Clear();
           if (parola != "" && usrn != "")
           {
               try
               {
                   Angajat rez = ctrl.Valid(parola, usrn);
                   if (rez == null)
                   {
                       MessageBox.Show("Parola greșită. Încearcă din nou.", "Eroare", MessageBoxButtons.OK,
                           MessageBoxIcon.Error);
                   }
                   else
                   {
                       InitModel();
                       angajat = rez;
                       label9.Text = "Contul lui: " + angajat.Prenume + " " + angajat.Nume;
                       tabControl1.SelectTab(tabPage2);
                   }
               }
               catch(Exceptions ex)
               {
                   MessageBox.Show(this, "Login Error " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                   return;
               }

           }
           else
           {
               MessageBox.Show("Completeaza username si parola!", "Eroare", MessageBoxButtons.OK,
                   MessageBoxIcon.Error);
           }
        }      
        
        
       
        private void button1_Click(object sender, EventArgs e)
        {
            
        }


        private void button1_Click_1(object sender, EventArgs e)
        {
            HandleValid();
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            throw new System.NotImplementedException();
        }

        private void tabPage2_Click(object sender, EventArgs e)
        {
            throw new System.NotImplementedException();
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {
            InitModel2();
        }

        private void InitModel2()
        {
            dataGridView3.Rows.Clear();
            try
            {
                List<string> listaInregistrariBilete = new List<string>();

                //listaInregistrariBilete = service.FindArtistiSpectacoleBileteDupaData(dateTimePicker1.Value);
                listaInregistrariBilete = ctrl.FindArtistiSpectacoleBileteDupaData(dateTimePicker1.Value);

                foreach (string entry in listaInregistrariBilete)
                {
                    string[] parts = entry.Split(',');

                    if (parts.Length == 5) // Ensure correct number of parts
                    {
                        string name = parts[0];
                        DateTime date = DateTime.Parse(parts[1]);
                        string location = parts[2];
                        int value1 = int.Parse(parts[3]);
                        int value2 = int.Parse(parts[4]);

                        dataGridView3.Rows.Add(name, date.Hour , location, value1-value2);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Eroare", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            tabControl1.SelectTab(tabPage3);

        }
        
        /*public void userUpdate(object sender, ChatUserEventArgs e)
        {
           
            if (e.UserEventType == ChatUserEvent.NewMessage)
            {
                String messString = e.Data.ToString();
                messageData.Add(messString);
                Console.WriteLine("[ChatWindow] messString " + messString);
                messageList.BeginInvoke(new UpdateListBoxCallback(this.updateListBox), new Object[] { messageList, messageData});
            }
        }*/
        
        
        

        private void button2_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count > 0)
            {
                // Obțineți primul rând selectat (puteți accesa și alte rânduri în cazul unui DataGridView cu selecție multiplă)
                DataGridViewRow selectedRow = dataGridView1.SelectedRows[0];

                // Obțineți indexul rândului selectat în DataGridView
                int index = selectedRow.Index;

//                Spectacol spectacol = service.FindArtistiSpectacoleBileteIndex(index);
                Spectacol spectacol = ctrl.FindArtistiSpectacoleBileteIndex(index);

                if (int.TryParse(textBox5.Text, out int nrLocuri) && !string.IsNullOrEmpty(textBox3.Text) &&
                    !string.IsNullOrEmpty(textBox4.Text))
                {
                    string cellValue = dataGridView1.Rows[index].Cells[3].Value.ToString(); // Obține valoarea din celulă sub forma de șir de caractere
                    int parsedValue;

                    if (int.TryParse(cellValue, out parsedValue))
                    if (parsedValue-nrLocuri>0)
                    {
                     //   service.SaveBilet(textBox4.Text, textBox3.Text, nrLocuri, angajat.IdPersoana,
                   //         spectacol.IdSpectacol);
                   
                        ctrl.SaveBilet(textBox4.Text, textBox3.Text, nrLocuri, angajat.IdPersoana,
                            spectacol.IdSpectacol);
                        MessageBox.Show("Bilet salvat cu succes!", "OK", MessageBoxButtons.OK,
                            MessageBoxIcon.Error);
                        InitModel();
                    }
                    else
                    {
                        
                        MessageBox.Show("nu mai sunt atatea bilete disponibile", "Eroare", MessageBoxButtons.OK,
                            MessageBoxIcon.Error);

                    }
                }
                else
                {
                    MessageBox.Show("eraore: completati toate campurile", "Eroare", MessageBoxButtons.OK,
                        MessageBoxIcon.Error);

                }
            }
            else
            {
                MessageBox.Show("Nu a fost selectată nicio linie.", "Eroare", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (dataGridView3.SelectedRows.Count > 0)
            {
                // Obțineți primul rând selectat (puteți accesa și alte rânduri în cazul unui DataGridView cu selecție multiplă)
                DataGridViewRow selectedRow = dataGridView3.SelectedRows[0];

                // Obțineți indexul rândului selectat în DataGridView
                int index = selectedRow.Index;

              //  Spectacol spectacol = service.FindArtistiSpectacoleBileteDupaDataIndex(dateTimePicker1.Value,index);
              Spectacol spectacol = ctrl.FindArtistiSpectacoleBileteDupaDataIndex(dateTimePicker1.Value,index);
              
              if (int.TryParse(textBox6.Text, out int nrLocuri) && !string.IsNullOrEmpty(textBox8.Text) &&
                    !string.IsNullOrEmpty(textBox7.Text))
                {
                   // string cellValue =
                     //   dataGridView1.Rows[index].Cells[3].Value
                       //     .ToString(); // Obține valoarea din celulă sub forma de șir de caractere
                    //int parsedValue;

                  //  if (int.TryParse(cellValue, out parsedValue))
                       // if (parsedValue - nrLocuri > 0)
                        {
                            ctrl.SaveBilet(textBox8.Text, textBox7.Text, nrLocuri, angajat.IdPersoana,
                                spectacol.IdSpectacol);
                            MessageBox.Show("Bilet salvat cu succes!", "OK", MessageBoxButtons.OK,
                                MessageBoxIcon.Error);
                            dateTimePicker1_ValueChanged(dateTimePicker1, EventArgs.Empty);
                        }
                }
                else
                {
                    MessageBox.Show("eraore: completati toate campurile", "Eroare", MessageBoxButtons.OK,
                        MessageBoxIcon.Error);

                }
            }
            else
            {
                MessageBox.Show("Nu a fost selectată nicio linie.", "Eroare", MessageBoxButtons.OK,
                    MessageBoxIcon.Error);
            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            tabControl1.SelectTab(tabPage2);
        }
       /* public void userUpdate(object sender, UserEvent e)
        {
            if (e.UserEventType==UserEvent.ChatUserEvent.FriendLoggedIn)
            {
                String friendId = e.Data.ToString();
                friendsData.Add(friendId);
                Console.WriteLine("[ChatWindow] friendLoggedIn "+ friendId);
                friendList.BeginInvoke(new UpdateListBoxCallback(this.updateListBox), new Object[]{friendList, friendsData});
                //   friendList.BeginInvoke((Action) delegate { friendList.DataSource = friendsData; });

            }
            if (e.UserEventType == ChatUserEvent.FriendLoggedOut)
            {
                String friendId = e.Data.ToString();
                friendsData.Remove(friendId);
                Console.WriteLine("[ChatWindow] friendLoggedOut " + friendId);
                friendList.BeginInvoke(new UpdateListBoxCallback(this.updateListBox), new Object[] { friendList, friendsData });
            }
            if (e.UserEventType == ChatUserEvent.NewMessage)
            {
                String messString = e.Data.ToString();
                messageData.Add(messString);
                Console.WriteLine("[ChatWindow] messString " + messString);
                messageList.BeginInvoke(new UpdateListBoxCallback(this.updateListBox), new Object[] { messageList, messageData});
            }*/


      

       private void button5_Click(object sender, EventArgs e)
        {
            Console.WriteLine("ChatWindow closing ");
            ctrl.logout();
           // ctrl.updateEvent -= userUpdate;
            tabControl1.SelectTab(tabPage1);

            //    Application.Exit();
        }

   public void rezervareReceived(Bilet bilet)
        {
            Console.WriteLine("invoke ");

            if (InvokeRequired)
            {
                Console.WriteLine("invoke r");

                // Dacă apelul se face pe un alt thread, folosește Invoke pentru a executa codul pe thread-ul UI
                BeginInvoke(new Action(() => InitModel()));
                BeginInvoke(new Action(() => InitModel2()));

            }
            else
            {
                // Dacă apelul se face deja pe thread-ul UI, poți apela direct metoda InitModel
                InitModel();
                InitModel2();
            }
        }
    
    }
}
