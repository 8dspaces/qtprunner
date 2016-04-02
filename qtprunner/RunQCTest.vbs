'*************************************************************************************************************************************************************************
'设计说明：调用QC测试集运行并且发送测试结果，必须配置好邮件服务器，否则邮件无法发送，其余无需修改
'程序输入：参见函数参数定义注解
'程序输出：邮件结果报告和分析报表
'设计人员：刘毅（LIUYI）
'设计时间：2009-03-20
'调用举例：Call RunTestSet("http://qc/qcbin","核心项目","某某系统","LIUYI",psWord,"自动化测试\回归测试\","试验","remoterun","10.31.10.1","LIUYI@AAA.COM,HUYANG@AAAA.COM")
'*************************************************************************************************************************************************************************

Public Sub RunTestSet(qcServer,qcDomain,qcProject,qcUser,qcPassword,tsFolderName,tSetName,runMode,runHostName,resultGetter)
	'Dim qcServer		定义QC服务器地址
	'Dim qcDomain		定义QC域的名称
	'Dim qcProject		定义QC项目名称
	'Dim qcUser			定义QC用户名称，必须保证这些用户有特定的执行权限
	'Dim qcPassword		定义QC用户的密码
	'Dim tsFolderName	定义测试集所在的路径（不包含"Root\"）
	'Dim tSetName		定义测试集的名称
	'Dim runMode		定义运行模式：本地运行(localrun)或代理运行(remoterun)
	'Dim runHostName	定义代理运行的主机名称或IP地址，如果是本地运行则此参数自动失效
	'Dim resultGetter	定义邮件接受人（列表），多人则以英文半角的逗号”,“分隔

	'全局对象声明

	'On Error Resume Next

	Set Wshshell = CreateObject("Wscript.Shell")
	Set TDC = CreateObject("TDApiOle80.TDConnection.1")

	'判断制定用户和指定的域和项目是否连接成功，如果已经连接则首先断开之后重新登陆，这样可以确保连接的项目正确，如果没有连接则直接连接登陆

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

	'声明测试集树、路径、测试实验室、测试集名等对象

	Set tsTreeMgr = TDC.TestSetTreeManager
	Set tsFolder = tsTreeMgr.NodeByPath("Root\"&Trim(tsFolderName))
	Set tsList = tsFolder.FindTestSets(tSetName)

	'对测试集路径进行正确性判断，发生异常情况清空所有对象退出运行

	If	tsFolder Is Nothing Then
		Wshshell.Popup "找不到指定路径【"&nPath&"】",1,"运行时错误：",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'对同一目录下的同名测试集做异常判断，对没有找到指定测试集进行异常判断，异常情况清空所有对象退出运行

	If	tsList.Count > 1 Then
		Wshshell.Popup "同名测试集多于一个，请先删除多余测试集！【"&nPath&tSetName&"】",1,"运行时错误：",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	ElseIf tsList.Count < 1 Then
		Wshshell.Popup "找不到测试集！【"&nPath&tSetName&"】",1,"运行时错误：",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'报告当前运行测试集的测试集信息

	Set theTestSet = tsList.Item(1)

	Wshshell.Popup "当前运行测试集ID为："&theTestSet.ID&"测试集名称为："&tSetName&"】",1,"当前运行测试集为：",0

	'判断运行模式：本机执行、代理执行，并且选择代理运行的执行机

	If	Trim(runMode) = "localrun" Then
		Set Scheduler = theTestSet.StartExecution(LocalHost)
		Scheduler.RunAllLocally = True
		Scheduler.Run
	ElseIf Trim(runMode) = "remoterun" Then
		Set Scheduler = theTestSet.StartExecution(LocalHost)
		Scheduler.TdHostName = runHostName
		Scheduler.Run
	Else
		Wshshell.Popup "调用接口错误，无此运行选项：【"&runMode&"】",1,"运行时错误：",0
		Set tsList = Nothing 
		Set tsFolder = Nothing 
		Set tsTreeMgr = Nothing 
		Set TDC = Nothing 
		Set Wshshell = Nothing 
		Exit Sub
	End If

	'判断运行是否结束，没有结束则继续循环知道结束，否则进行后续处理

	Set execStatus = Scheduler.ExecutionStatus

	mailMessage = "<HTML><HEAD><STYLE> TYPE=""TEXT/CSS"">ATS{COLOR:NAVY;FONT-SIZE:12PX;}Atest{BACKGROUND:GRAY;}</STYLE></HEAD>"&_
	"<BODY>" &_
		"<ATS>测试集" & tSetName &" 执行时间："&Now&" 结果报告</ATS><BR>" &_
			"<TABLE>" &_
				"<TR BGCOLOR=""NAVY""><B>测试名称</B></FONT></TD>" &_
				"<TR BGCOLOR=""NAVY""><B>执行状态</B></FONT></TD>" &_
				"<TR BGCOLOR=""NAVY""><B>执行主机</B></FONT></TD>" &_
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

	Wshshell.Popup "执行全部完成于【"&CStr(Now)&"】",1,"执行结果通知：",0

	'此处向EXCEL或数据库回写测试结果，计算该测试集案例个数、运行个数、成功个数，生成报表，发送邮件，进行下一个测试集的运行

	TDC.SendMail resultGetter,"","自动化测试集【Root\"&tsFolderName&tSetName&"】 于 【"&Now&"】完成的运行结果报告",mailMessage,"","HTML"

	TDC.Disconnect()
	TDC.Logout()
	TDC.ReleaseConnection()

	'结束之后清空所有对象退出运行

	Set TestSetTestsList = Nothing 
	Set TSTestFact = Nothing 
	Set theTestSet = Nothing 
	Set tsList = Nothing 
	Set tsFolder = Nothing 
	Set tsTreeMgr = Nothing 
	Set Wshshell = Nothing 
	Set TDC = Nothing 

End Sub

'过程调用，做循环处理：从WEB页面传入测试集信息，保存之后开始运行
'实现思路：每次清空页面上次写入的记录之后重新写入测试集信息，如果建表，则【测试集名称】字段做主键

Call RunTestSet("http://192.168.1.2:8080/qcbin","DEFAULT","QualityCenter_Demo","alice_qc","","Completed BPT Tutorial\","Flight_Reservation","remoterun","192.168.1.100","LIUYI@AAA.COM,HUYANG@AAAA.COM")
