LogFile = "Logs\QTRunnerLog"		'����Log�ļ�·�����ļ������ɴ������д��룬���Ϊ��Set logFileObj=GetLogObj(argObj(0))
Set logFileObj=GetLogObj(LogFile)
Response "--- Start a new batch task at " & Date & " " & Time
Response "Console logs are saved in " & LogFile & ".log"

' �������ļ�������ֲ���
Set ConfigParameters = CreateObject("Scripting.Dictionary")
ConfigFile = "config.ini"
ReadTestsFromConfigFile ConfigFile , ConfigParameters
Response "Reading Test Configurations From " & ConfigFile

' ���в���
RunTest
CloseLogObj logFileObj

' ����Email
'SendMail LogFile & ".log"



'----------------------------------------------------------------------------------
' ��ȡ�����ļ�
Function ReadTestsFromConfigFile( ByVal ConfigFile , ByRef ConfigParameters )
	Set GetParameters = ConfigParameters
	Dim fso, f, lineStr, isValid
	Set fso = CreateObject("Scripting.FileSystemObject")	
	If Not fso.FileExists(ConfigFile) Then
		Response ConfigFile & " not found"
		Exit Function
	End If
	Dim TestCount
	TestCount=0
	Set f=fso.OpenTextFile(ConfigFile,1,False)		'Open file for read, if not existed, don't created it.
	Do While f.AtEndOfStream <> True
		lineStr = f.ReadLine()	  
	  lineStr=Trim(lineStr)
	  If lineStr<>"" Then		'not empty line
			If InStr(1,lineStr,"#") = 1 Then 	'begin with "#"		
				TestCount = TestCount + 1	
				TestPathStr=Trim(Right(lineStr,Len(lineStr)-1))
				GetParameters.Add TestCount, TestPathStr
			ElseIf InStr(1,lineStr,"ResultPath=") = 1 Then
				ResultPathStr = Trim(Right(lineStr,Len(lineStr)-11))
				GetParameters.Add "ResultPath", ResultPathStr
			ElseIf InStr(1,lineStr,"smtpserver=") = 1 Then
				smtpserver = Trim(Right(lineStr,Len(lineStr)-11))
				GetParameters.Add "smtpserver", smtpserver
			ElseIf InStr(1,lineStr,"sendusername=") = 1 Then
				sendusername = Trim(Right(lineStr,Len(lineStr)-13))
				GetParameters.Add "sendusername", sendusername
			ElseIf InStr(1,lineStr,"sendpassword=") = 1 Then
				sendpassword = Trim(Right(lineStr,Len(lineStr)-13))
				GetParameters.Add "sendpassword", sendpassword
			ElseIf InStr(1,lineStr,"Email_Address=") = 1 Then
				Email_Address = Trim(Right(lineStr,Len(lineStr)-14))
				GetParameters.Add "Email_Address", Email_Address
			End If
	  End If
	Loop	
	f.Close
	GetParameters.Add "TestCount", TestCount
	Set ReadTestsFromConfigFile = GetParameters	
End Function

' ͨ��AOM����QTPִ�в���
Function RunTest
	Dim qtApp 
	Dim qtTest
	Dim qtResultsOpt
	stime = Now
	sdate = Year(stime) & "." & Month(stime) & "." & Day(stime) & "_" & Hour(stime) & "." & Minute(stime) & "." & Second(stime)

	Set qtApp = CreateObject("QuickTest.Application")
	qtApp.Launch
	qtApp.Visible = True
	Response "Launching QTP..."
	
	TestCount = ConfigParameters.Item("TestCount")
	For I=1 To TestCount	
		TestPath = ConfigParameters.Item(I)
		arr = Split(TestPath,";")
		testfile = arr(0)
		qtApp.Open testfile, True
		Set qtTest = qtApp.Test
		Response "-------------------------"
		Response "Opening Test " & arr(0)
		
		Set oParams = qtApp.Test.ParameterDefinitions.GetParameters()
		If UBound(arr) = 1 Then
				ParamArr = Split(arr(1),",")
				paramCount = UBound(ParamArr)
				For K=0 to paramCount
					oParam = Split(ParamArr(K),"=")
					ParamName = oParam(0)
					ParamValue = oParam(1)
					oParams.Item(ParamName).Value = ParamValue
				Next
		End If
		
		ResultPath = ConfigParameters.Item("ResultPath") & "\" & sdate & "\" & qtTest.Name ' ������ReporterManager��ܵĻ�����Ӧ�ð�qtTest.Nameȥ��������Test�Ľ����д��ͳһ��Ŀ¼�ļ���
		Set qtResultsOpt = CreateObject("QuickTest.RunResultsOptions")
		qtResultsOpt.ResultsLocation = ResultPath
		Response "The Result of Test " & testfile & " will be save in " & ResultPath

		qtTest.Run  qtResultsOpt, True, oParams  ' Run the test
		Response "Runing Test " & testfile

		Response testfile & "End Running With " & qtTest.LastRunResults.Status & "Status"
		Response "Parameter List:" 
		For P = 1 to oParams.Count
			Response oParams.Item(P).Name & "=" & oParams.Item(P).Value
		Next	

		qtTest.Close
		Response "Closing Test " & testfile
		Response "-------------------------"
		Next
	qtApp.Quit
	Set oParams = Nothing
	Set qtResultsOpt = Nothing
	Set qtTest = Nothing
	Set qtApp = Nothing
End Function

' д��־
Function Response(ByVal msg)	
	logFileObj.WriteLine Date & " " & Time & ": " & msg
	If isRunInCmd Then		
		WScript.Echo Date & " " & Time & ": " & msg
	End If
End Function

' ������־�ļ�
Function GetLogObj(ByVal cfilePath)
	Dim fso, runtimeLog
	Set fso = CreateObject("Scripting.FileSystemObject")
	
	If Not fso.FileExists(cfilePath) Then
		fso.CreateTextFile(cfilePath)
	End If
	
	runtimeLog=cfilepath & ".log"		
	Set GetLogObj = fso.OpenTextFile(runtimeLog, 8, True)   'open file for appending.	
End Function

' �ر���־�ļ�
Function CloseLogObj(ByVal cfile)
	cfile.Close
End Function

' �����ʼ�
Function SendMail(LogFile) 
	Set oMessage=WScript.CreateObject("CDO.Message")
	Set oConf=WScript.CreateObject("CDO.Configuration")
	Set fso = CreateObject("Scripting.FileSystemObject")
	
	'����CDO.Configuration�������Ҫ�����ʼ��������Ķ˿ڡ��û��ʺŵ������Ϣ 
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/sendusing")=2
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/smtpserver")=ConfigParameters.Item("smtpserver")
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/serverport")=25
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/smtpauthenticate")=1
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/sendusername")=ConfigParameters.Item("sendusername")
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/sendpassword")=ConfigParameters.Item("sendpassword")
	oConf.Fields("http://schemas.microsoft.com/cdo/configuration/smtpusessl")=0
	oConf.Fields.Update()
	 
	'ͨ��CDO�� Message���������ʼ����⡢�����������˵���Ϣ 
	oMessage.Configuration = oConf
	oMessage.To = ConfigParameters.Item("Email_Address")
	oMessage.From = ConfigParameters.Item("sendusername")
	oMessage.Subject = "QTRunner Notification"
	file = fso.GetAbsolutePathName(LogFile)
	Set fso = Nothing
	oMessage.AddAttachment( file )

	TextBody = "QTRunner Finish! See attachment for logs."	  
	oMessage.TextBody = TextBody
	oMessage.Send()
End Function
