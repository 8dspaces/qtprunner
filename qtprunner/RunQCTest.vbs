'*************************************************************************************************************************************************************************
'���˵��������QC���Լ����в��ҷ��Ͳ��Խ�����������ú��ʼ��������������ʼ��޷����ͣ����������޸�
'�������룺�μ�������������ע��
'����������ʼ��������ͷ�������
'�����Ա�����㣨LIUYI��
'���ʱ�䣺2009-03-20
'���þ�����Call RunTestSet("http://qc/qcbin","������Ŀ","ĳĳϵͳ","LIUYI",psWord,"�Զ�������\�ع����\","����","remoterun","10.31.10.1","LIUYI@AAA.COM,HUYANG@AAAA.COM")
'*************************************************************************************************************************************************************************

Public Sub RunTestSet(qcServer,qcDomain,qcProject,qcUser,qcPassword,tsFolderName,tSetName,runMode,runHostName,resultGetter)
	'Dim qcServer		����QC��������ַ
	'Dim qcDomain		����QC�������
	'Dim qcProject		����QC��Ŀ����
	'Dim qcUser			����QC�û����ƣ����뱣֤��Щ�û����ض���ִ��Ȩ��
	'Dim qcPassword		����QC�û�������
	'Dim tsFolderName	������Լ����ڵ�·����������"Root\"��
	'Dim tSetName		������Լ�������
	'Dim runMode		��������ģʽ����������(localrun)���������(remoterun)
	'Dim runHostName	����������е��������ƻ�IP��ַ������Ǳ���������˲����Զ�ʧЧ
	'Dim resultGetter	�����ʼ������ˣ��б�����������Ӣ�İ�ǵĶ��š�,���ָ�

	'ȫ�ֶ�������

	'On Error Resume Next

	Set Wshshell = CreateObject("Wscript.Shell")
	Set TDC = CreateObject("TDApiOle80.TDConnection.1")

	'�ж��ƶ��û���ָ���������Ŀ�Ƿ����ӳɹ�������Ѿ����������ȶϿ�֮�����µ�½����������ȷ�����ӵ���Ŀ��ȷ�����û��������ֱ�����ӵ�½

	If	TDC.Connected = False Then
		TDC.InitConnectionEx qcServer
		TDC.Login qcUser,qcPassword
		TDC.Connect qcDomain,qcProject
	Else
		TDC.Disconnect
		TDC.Logout
		TDC.ReleaseConnection
		TDC.IntConnectionEx qcServer
		TDC.Login qcUser,qcPassword
		TDC.Connect qcDomain,qcProject
	End If

	'�������Լ�����·��������ʵ���ҡ����Լ����ȶ���

	Set tsTreeMgr = TDC.TestSetTreeManager
	Set tsFolder = tsTreeMgr.NodeByPath("Root\"&Trim(tsFolderName))
	Set tsList = tsFolder.FindTestSets(tSetName)

	'�Բ��Լ�·��������ȷ���жϣ������쳣���������ж����˳�����

	If	tsFolder Is Nothing Then
		Wshshell.Popup "�Ҳ���ָ��·����"&nPath&"��",1,"����ʱ����",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'��ͬһĿ¼�µ�ͬ�����Լ����쳣�жϣ���û���ҵ�ָ�����Լ������쳣�жϣ��쳣���������ж����˳�����

	If	tsList.Count > 1 Then
		Wshshell.Popup "ͬ�����Լ�����һ��������ɾ��������Լ�����"&nPath&tSetName&"��",1,"����ʱ����",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	ElseIf tsList.Count < 1 Then
		Wshshell.Popup "�Ҳ������Լ�����"&nPath&tSetName&"��",1,"����ʱ����",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'���浱ǰ���в��Լ��Ĳ��Լ���Ϣ

	Set theTestSet = tsList.Item(1)

	Wshshell.Popup "��ǰ���в��Լ�IDΪ��"&theTestSet.ID&"���Լ�����Ϊ��"&tSetName&"��",1,"��ǰ���в��Լ�Ϊ��",0

	'�ж�����ģʽ������ִ�С�����ִ�У�����ѡ��������е�ִ�л�

	If	Trim(runMode) = "localrun" Then
		Set Scheduler = theTestSet.StartExecution(LocalHost)
		Scheduler.RunAllLocally = True
		Scheduler.Run
	ElseIf Trim(runMode) = "remoterun" Then
		Set Scheduler = theTestSet.StartExecution(LocalHost)
		Scheduler.TdHostName = runHostName
		Scheduler.Run
	Else
		Wshshell.Popup "���ýӿڴ����޴�����ѡ���"&runMode&"��",1,"����ʱ����",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'�ж������Ƿ������û�н��������ѭ��֪��������������к�������

	Set execStatus = Scheduler.ExecutionStatus

	mailMessage = "<HTML><HEAD><STYLE> TYPE=""TEXT/CSS"">ATS{COLOR:NAVY;FONT-SIZE:12PX;}Atest{BACKGROUND:GRAY;}</STYLE></HEAD>"&_
	"<BODY>" &_
		"<ATS>���Լ�" & tSetName &" ִ��ʱ�䣺"&Now&" �������</ATS><BR>" &_
			"<TABLE>" &_
				"<TR BGCOLOR=""NAVY""><B>��������</B></FONT></TD>" &_
				"<TR BGCOLOR=""NAVY""><B>ִ��״̬</B></FONT></TD>" &_
				"<TR BGCOLOR=""NAVY""><B>ִ������</B></FONT></TD>" &_
				"</TR>"
	
	While (RunFinished = False)
		execStatus.RefreshExecStatusInfo "all",True
		RunFinished = execStatus.Finished
		'Set EventsList = execStatus.EventsList
		Wscript.sleep 5000
	Wend

	'Set theTestSet = tcList.Item(1)
	Set TSTestFact = theTestSet.TSTestFactory
	Set TestSetTestsList = TSTestFact.NewList("")

	For i = 1 To execStatus.Count
		Set TestExecStatusObj = execStatus.Item(i)

		testName = TestSetTestsList.Item(i).Name
		resState = TestExecStatusObj.Message

		mailMessage = mailMessage&"<TR ALIGN=""MIDDLE""><TD>"&i&"</TD><TD>"&testName&"</TD><TD>"&resState&"</TD><TD>"&runHostName&"</TD></TR>"
	Next

	mailMessage = mailMessage&"</TABLE></BODY></HTML>"

	Wshshell.Popup "ִ��ȫ������ڡ�"&CStr(Now)&"��",1,"ִ�н��֪ͨ��",0

	'�˴���EXCEL�����ݿ��д���Խ��������ò��Լ��������������и������ɹ����������ɱ��������ʼ���������һ�����Լ�������

	TDC.SendMail resultGetter,"","�Զ������Լ���Root\"&tsFolderName&tSetName&"�� �� ��"&Now&"����ɵ����н������",mailMessage,"","HTML"

	TDC.Disconnect()
	TDC.Logout()
	TDC.ReleaseConnection()

	'����֮��������ж����˳�����

	Set TestSetTestsList = Nothing 
	Set TSTestFact = Nothing 
	Set theTestSet = Nothing 
	Set tsList = Nothing 
	Set tsFolder = Nothing 
	Set tsTreeMgr = Nothing 
	Set Wshshell = Nothing 
	Set TDC = Nothing 

End Sub

'���̵��ã���ѭ��������WEBҳ�洫����Լ���Ϣ������֮��ʼ����
'ʵ��˼·��ÿ�����ҳ���ϴ�д��ļ�¼֮������д����Լ���Ϣ����������򡾲��Լ����ơ��ֶ�������

Call RunTestSet("http://192.168.1.2:8080/qcbin","DEFAULT","QualityCenter_Demo","alice_qc","","Completed BPT Tutorial\","Flight_Reservation","remoterun","192.168.1.100","LIUYI@AAA.COM,HUYANG@AAAA.COM")
