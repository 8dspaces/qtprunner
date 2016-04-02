Option Explicit 

'****************************************************************************
'
' test101
'
' Example test case that shows the use of suiteControl invocation and the
' use of the testArray() of control data structures.
'
' Terry Horwath
'  
' 08May2007, version 0.10, initially coded.
'
'****************************************************************************
'

'############################################################################
'
'test entry control variables
'
'############################################################################
'
'
Dim testNum : testNum=101
Dim   ts    : Set  ts=testArray.getStruct(testNum, "test enter")

Dim iIterations

If ts.bStandalone Then				'running in standalone mode

	iIterations	= 10				'number of test iterations to run
	

else								'called from suiteControl
	iIterations=1
End If


'############################################################################
'
'Test code hereafter...
'
'############################################################################
'
'
Function test101()

   Dim i, s

   For i=1 to iIterations
	   s = s&"Pass #"&i&vbCRLF
   Next
   Reporter.ReportEvent micDone, "test 101", s

   ts.bPassed=True					'each test must explicitly indicate success,
									'(while failure is implicit...)
End Function
test101
