oReporterManager.Report  "Pass", "Step 1" ,"Window Should Open" ,"All OK", "No Details"
oReporterManager.Report  "Warning", "Step 2" ,"Window Should Open" ,"It Didn't", "Some Details"
oReporterManager.Report  "Fail", "Step 3" ,"Window Should Open" ,"App. Crashed", "Bla Bla Bla"


' 不能注册到Reporter对象中
'Reporter.ReportEvent micPass,"Step1","Details Information"
'Function ReportEvent( oReporter, Status, StepName,Details )
'	Call oReporterManager.Report ("Pass",StepName ,"Window Should Open" ,"All OK", Details )
'End Function 
'RegisterUserFunc "Reporter","ReportEvent","ReportEvent"
