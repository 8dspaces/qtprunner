' Load() doesn't not accept the relative path
Dim DR 
Set DR = CreateObject("DescriptiveOR.Reader")
DR.Load ("C:\QTP_Demo\Data\MercuryTourOR.xml")

Public Function Object(name)
	Set objReturn = Nothing	
	strExec = DR.getObjectDefinition(name) 	
	if DEBUGGING then call reporter.reportevent(micDone, name, strExec)
	strExec = "Set objReturn = " & DR.getObjectDefinition(name) 	
	Execute strExec
	Set Object = objReturn
End Function