using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using QuickTest;
using System.IO;
using System.Net.Mail;
using System.Net.Mime;
using System.Net;
using System.Collections;
using System.Diagnostics;

/*
 * TIB������ 
 * http://www.cnblogs.com/testware/
 * 
 * 2010-5-20  
 *  ��ӽű������ƶ�����
 *  ���д��ִ����־����
 * 2010-5-22
 *  ���KillProcess����������ȷ��QTPro��QTAutomationAgent���̵õ��ر�
 *  ����ʼ����͹���
 * 
 */
namespace QTRunner
{
    public partial class Form1 : Form
    {
        string QTRunnerLogPath;

        public Form1()
        {
            InitializeComponent();

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string QTPTest = "";
            if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
            {
                QTPTest = folderBrowserDialog1.SelectedPath.ToString();
                this.listBox1.Items.Add(QTPTest);                
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.listBox1.Items.Remove(this.listBox1.SelectedItem);
        }

        //�رս���
        public static void killProcess(String processName)
        {
            Process[] myprocess = System.Diagnostics.Process.GetProcesses();
            foreach (Process a in myprocess)
            {
                // ������ڽ�����Kill��
                if (a.ProcessName == processName)
                {
                    a.Kill();
                    a.Close();
                    break;
                }
            }
            Boolean j = false;
            Boolean b;
            //int n = 0;
            while (!j) //ѭ����ֱ������ȷʵ�Ѿ��ر�
            {
                b = false;
                myprocess = System.Diagnostics.Process.GetProcesses();

                foreach (Process c in myprocess)
                {
                    if (c.ProcessName == processName) // �жϸý����Ƿ񻹴���
                    {
                        b = true;
                        break;
                    }
                }
                if (!b)
                {
                    j = true;
                }
            }
        }

        /* ����ִ�в��Խű� */
        private void button3_Click(object sender, EventArgs e)
        {            
            killProcess("QTPro");
            killProcess("QTAutomationAgent");
            toolStripStatusLabel1.Text = "����QTP";
            Log("����QTP");
            QuickTest.Application QTPApplication = new QuickTest.ApplicationClass();

            bool k = QTPApplication.Launched;
            if (k)
                QTPApplication.Quit();
            QTPApplication.Launch();

            QTPApplication.Options.Run.RunMode = "Fast";
            QTPApplication.Options.Run.ViewResults = false;

            QTPApplication.AutomationSilent = true;
            QTPApplication.Visible = false;
            string ResultPath = this.textBox2.Text;
            if (this.textBox2.Text == "")
            {
                ResultPath = Environment.CurrentDirectory;
            }

            try
            {                         
                for (int i = 0; i < listBox1.Items.Count; i++)
                {
                    string TestPath = listBox1.Items[i].ToString();
                    QTPApplication.Open(TestPath, true, false);
                    QuickTest.Test qtTest = QTPApplication.Test;
                    //object obj = qtTest.DataTable;
                    QuickTest.RunResultsOptions qtResult = new QuickTest.RunResultsOptions();
                    qtResult.ResultsLocation = ResultPath + "\\" + qtTest.Name.ToString();

                    toolStripStatusLabel1.Text = "����ִ��" + qtTest.Name.ToString();
                    Log("����ִ��" + qtTest.Name.ToString());
                    qtTest.Run(qtResult, true, null);
                    //qtTest.Close();
                    toolStripStatusLabel1.Text = qtTest.Name.ToString() + "ִ�����";
                    Log(qtTest.Name.ToString() + "ִ�����");

                    System.Runtime.InteropServices.Marshal.ReleaseComObject(qtResult); qtResult = null;
                    System.Runtime.InteropServices.Marshal.ReleaseComObject(qtTest); qtTest = null;

                    //System.Windows.Forms.Application.DoEvents();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                Log(ex.Message);
            }

            Log("�˳�");
            bool launched = QTPApplication.Launched;
            if (launched)
                QTPApplication.Quit();

            System.Runtime.InteropServices.Marshal.ReleaseComObject(QTPApplication);
            QTPApplication = null;

            // �����ʼ�
            if (this.checkBox1.Checked)
            {                
                string server = this.textBox5.Text;
                string attachFile = QTRunnerLogPath;
                string msgFrom = this.textBox1.Text;
                string msgFromPassword = this.textBox3.Text;
                string msgTo = this.textBox4.Text;
                string msgSubject = "QTRunner��־";
                string msgBody = "QTRunnerִ����־����������";
                SendMail(server, attachFile, msgFrom, msgFromPassword, msgTo, msgSubject, msgBody);
            }
        }

 
        public void SendMail(string server, string attachFile, string msgFrom, string msgFromPassword,string msgTo, string msgSubject, string msgBody)
        {
            string file = attachFile;
            MailMessage message = new MailMessage( msgFrom, msgTo, msgSubject,msgBody);
            Attachment data = new Attachment(file, MediaTypeNames.Application.Octet);
            ContentDisposition disposition = data.ContentDisposition;
            disposition.CreationDate = System.IO.File.GetCreationTime(file);
            disposition.ModificationDate = System.IO.File.GetLastWriteTime(file);
            disposition.ReadDate = System.IO.File.GetLastAccessTime(file);
            message.Attachments.Add(data);
            SmtpClient client = new SmtpClient(server);
            client.Credentials = new System.Net.NetworkCredential(msgFrom, msgFromPassword); 
            client.Send(message);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            string ResultPath = "";
            if (folderBrowserDialog1.ShowDialog() == DialogResult.OK)
            {
                ResultPath = folderBrowserDialog1.SelectedPath.ToString();
                this.textBox2.Text = ResultPath;
            }
        }

        private void button5_Click(object sender, EventArgs e)
        {
            int currentIndx = this.listBox1.SelectedIndex;
            if ( currentIndx < this.listBox1.Items.Count - 1)
            {
                object currentItem = this.listBox1.Items[currentIndx];
                this.listBox1.Items[currentIndx] = this.listBox1.Items[currentIndx + 1];
                this.listBox1.Items[currentIndx + 1] = currentItem;
                this.listBox1.SelectedIndex = currentIndx + 1;
            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            int currentIndx = this.listBox1.SelectedIndex;
            if ( currentIndx > 0 )
            {
                object currentItem = this.listBox1.Items[currentIndx];
                this.listBox1.Items[currentIndx] = this.listBox1.Items[currentIndx - 1];
                this.listBox1.Items[currentIndx - 1] = currentItem;
                this.listBox1.SelectedIndex = currentIndx - 1;
            }
        }

        /* д��ִ����־ */
        private void Log( string msg )
        {
            QTRunnerLogPath = "";
            if (this.textBox2.Text == "")
            {
                QTRunnerLogPath = this.textBox2.Text + "\\QTRunnerLog.txt";
            }
            else
                QTRunnerLogPath = Environment.CurrentDirectory + "\\QTRunnerLog.txt";
            string appendText = DateTime.Now.ToString() + " " + msg + Environment.NewLine;
            File.AppendAllText(QTRunnerLogPath, appendText, Encoding.UTF8);
        }

    }
}