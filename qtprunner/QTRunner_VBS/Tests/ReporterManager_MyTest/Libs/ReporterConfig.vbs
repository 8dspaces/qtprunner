'LogPath = "C:\Logs" '保存日志的路径
'Set fso = CreateObject("Scripting.FileSystemObject")
'Set ts = fso.OpenTextFile("Config\LogsPath.txt", 1, False)
'LogPath = ts.ReadLine    
'ts.Close

LogPath = Environment("ResultDir")
'Msgbox LogPath 

'Add QTP native reporter to the party. This way we wont need 2 reporting commands - one to ReporterManager, and one to the QTP result log
Call oReporterManager.StartEngine("QTP", "QTP", "")

'Set up a complete backup log - the basics
Call oReporterManager.StartEngine("Text", "Backup", "File>"&LogPath &"\QTPLog.txt|ClearExisting>False")
Call oReporterManager.StartEngine("XML", "XML Backup", "File>"&LogPath &"\QTPLog.XML|ClearExisting>False")

'Make sure to copy the demo MDB database to c:\QTP Log.mdb
Call oReporterManager.StartEngine("DB", "DB Backup", "CreateNew>False")

'Set up statistics log - with it we can later produce the step pass/fail ratio with a click of a button
Call oReporterManager.StartEngine("Text", "Stats", "File>"&LogPath &"\Statistics.CSV|BodyTemplate>""%Status%, %StepName%, %Expected%, %Actual%, %Details%"" & vbcrlf|NewTestCaseTemplate>""General, Switched TestCase, , , %TestCaseName%"" & vbcrlf")

'Set up dedicated performence log - we can load it to excel and imiddiatly extract the time-step differences between relevant steps
Call oReporterManager.StartEngine("Text", "Performance", "File>"&LogPath &"\Performance.CSV|BodyTemplate>""%Time%, %StepName%"" & vbcrlf|NewTestCaseTemplate>"" "" ")

'Set up a top view excel log, so we'll have something pretty to send to management
Call oReporterManager.StartEngine("Excel", "TopView", "File>"&LogPath &"\TopView.xls")

'Set up a dedicated error & warnning logs
Call oReporterManager.StartEngine("Text", "Errors", "File>"&LogPath &"\Errors.txt|BodyTemplate>""%Time%, %Status%, %StepName%, %Expected%, %Actual%"" & vbcrlf")
Call oReporterManager.StartEngine("Winlog", "Win Errors", "ShowAs>QTP Errors")
Call oReporterManager.StartEngine("ScreenCapture", "Error Captures", "Path>"&LogPath &"\|Prefix>Error -")

'Set up the filter for errors
Call oReporterManager.AddFilter("RegEx", "Errors>Win Errors>Error Captures", "Pattern>Fail|WhatToSearch>Status")

''Set up a user popup that alerts whoever's watching that an error has occured  - enable major time saves as a script can be stopped imidiatly
'Call oReporterManager.StartEngine("User", "Errors Pop Up", "Timer>2")
'Call oReporterManager.AddFilter("RegEx", "Errors Pop Up", "Pattern>Fail|WhatToSearch>Status")

