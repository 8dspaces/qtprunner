Option Explicit

'****************************************************************************
'
' testArray.vbs
'
' This library provides the testArray dictionary of test data objects,
' testStruct,used to control the execution of a full suite of logically
' related QTP tests. Each test is assigned its own testStruct object.
'
' Terry Horwath  
' 08May2007, version X.10, initial release
'
'******************************************************************************
'

Class testStructClass

	Dim iTestID							'integer: unique test number
	Dim sTestID							'string:  brief description of the test
	
	Dim sActionToCall					'string:  exact RunAction syntax call
	
	Dim bStandalone						'boolean: False=called from suiteControl, else True
	
	Dim bPassed							'boolean: True=test passed; False=test failed

	Private sub Class_Initialize()
		bPassed		=False				'default: test failed
		bStandalone	=True				'default: test not called from suiteControl
	End Sub
End Class


Class testArrayClass

	'##########################################################################
	'#                                                                        #
	'# Data and Properties                                                    #
	'#                                                                        #
	'##########################################################################
	'
	Private libName                 'name of this lib's "well known" instantiated object
	

	Private testArray				'Dictionary holding all testStruct objects
	
    Public Property Get dArray()
            dArray=testArray
    End Property


	'##########################################################################
	'#                                                                        #
	'# Methods                                                                #
	'#                                                                        #
	'##########################################################################
	'
	Private sub Class_Initialize()

	    libName	= "testArray"

		testArrayInit()
	End Sub

	
	'**************************************************************************
	'
	' testArrayInit() 
	'
	' This function builds the testArray(). See comments above that describe
	' this dictionary and its structures. Initialization order is not important,
	' but the iTestID must be unique across this dictionary.
	'
	'**************************************************************************
	'
	Private function testArrayInit()

		Dim id, i, testStruct
		
		set testArray=CreateObject("Scripting.Dictionary")
		 
		' Now, build the array...
		'
		id=101
		Set testStruct=new testStructClass : testArray.Add id, testStruct
			testStruct.iTestID  		=	id
			testStruct.sTestID  		=	"Example Test #101"
			testStruct.sActionToCall	=	"main [Test101]"

		id=520
		Set testStruct=new testStructClass : testArray.Add id, testStruct
			testStruct.iTestID  		=	id
			testStruct.sTestID  		=	"Example Test #520"
			testStruct.sActionToCall	=	"main [Test520]"
		
	end function
	
    '**************************************************************************
    '
    ' getArrayKeys()							                         PUBLIC
	'
	' Returns an array containing all of the testArray keys (which are of course
	' the test's unique number). 
    '
    '**************************************************************************
	'
	Public function getArrayKeys ()
		getArrayKeys=testArray.Keys()
	End function


    '**************************************************************************
    '
    ' isValidKey(key)
	'
	' Evaluates the passed testArray key (which is the test's unique number). If 
	' a valid testArray key, returns True, else returns false
    '
    '**************************************************************************
	'
	Public Function isValidKey (key)

		isValidKey=testArray.Exists(key)
	end function


    '**************************************************************************
    '
    ' getTestStruct(key)
	'
	' Evaluates the passed testArray key (which is the test's unique number). If 
	' a valid key, returns the assocated testStruct, else logs an error
	' and returns Empty.
    '
    '**************************************************************************
	'
	Public Function getStruct (key, sStepID)

		getStruct=Empty

		if NOT varType(key)=vbInteger then
		
			reporter.reportEvent micFail, sStepID, "Specified passed key is not an integer, but must be"
			Exit Function
		end if
		
		if isValidKey(key)=False then
		
			reporter.reportEvent micFail, sStepID, "Specified key: '"&key&"', not found in testArray"
			Exit Function
		end if
		Set getStruct=testArray.Item(key)
	end function
    
End Class
Dim testArray
Set testArray = new testArrayClass


