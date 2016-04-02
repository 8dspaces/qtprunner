Option Explicit 

'****************************************************************************
'
' suiteControl
'
' Example showing how the "test suite controller" uses the testArray() 
' data structures to invoke "its" tests and then evaluate their
' pass/fail status.
'
' Terry Horwath
'  
' 08May2007, version 0.10, initially coded.
'
'****************************************************************************
'
Function main()

	Dim iKey, iKeys : iKeys=testArray.getArrayKeys()
	Dim testStruct, sAction, sStep :					sStep="suiteControl"
	
	For each iKey in iKeys
	
		set testStruct 			= testArray.getStruct(iKey, sStep)
		testStruct.bStandalone	= False								'indicate test being called from suiteControl
		sAction					= testStruct.sActionToCall
	
		runAction sAction, oneIteration								'execute the next test
	
		If testStruct.bPassed=False Then
			reporter.reportEvent micFail, sStep, "Test " & sAction & " FAILED"
		else
			reporter.reportEvent micPass, sStep, "Test " & sAction & " PASSED"
		End If
	
	Next
End Function
main
ExitRun










