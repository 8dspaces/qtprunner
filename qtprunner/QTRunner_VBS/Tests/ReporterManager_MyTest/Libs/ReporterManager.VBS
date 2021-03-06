'''<summary>This file holds the core class and global variable of the reporter manager</summary>
''' <datecreated>20/01/2008</datecreated>
'''<version>0.9.2</version>



Option Explicit
'**********************************************************************'
'								Global Variable
'**********************************************************************'
''' <summary>
''' Holds the manager's global variable
''' </summary>
''' <value type="ReporterManagerClass"/>
Dim oReporterManager
Set oReporterManager = New ReporterManagerClass

'**********************************************************************'
'								Manager
'**********************************************************************'
''' <summary>
''' The manager class core
''' </summary>
''' <author>Yaron Assa</author>
''' <datecreated>20/01/2008</datecreated>
Class ReporterManagerClass
   'Written by Yaron Assa, 1/9/07. Project home at www.AdvancedQTP.com
	
''' <value type="Scripting.Dictionary"/>
''' <summary>Holds the reporters collection</summary>
   Private oReporters 'Holds the reporters collection

	  'The oReporters Data structure is :
			'oReporters 							(DIctionary)
			'  |--> Reporter Item               (DIctionary)
			'           |--> Filters  					 (DIctionary)
			'          |           |--> Filter Item  (Class)
			'         |-->  Engine                   (Class)

	''' <summary>
	''' Only init engine in the first test iteration
	''' </summary>
	''' <value type="Boolean"/>
	Public OnlyInitOnce

''' <value type="String"/>	
''' <summary>The Manager's version</summary>
	Public Property Get Version
		Version = "0.9.2"
	End Property

''' <value type="String"/>
''' <summary>The minimal version an engine must have in order to be operated</summary>
	Public Property Get MinimumEngineVersion
		MinimumEngineVersion = "0.8"
	End Property

''' <value type="String"/>
''' <summary>The minimal version a filter must have in order to be operated</summary>
	Public Property Get MinimumFilterVersion
		MinimumFilterVersion = "0.8"
	End Property

''' <value type="Scripting.Dictionary"/>
''' <summary>Exposes the reporters collection of the reporter manager</summary>
''' <remarks>Each reporter is a Scripting.Dictionary with two values : "Engine" and "Filters"</remarks>
	Public Property Get Reporters
	   Set Reporters = oReporters
	End Property

   '*************************************************************'
   '	Function StartEngine  (Public)
   '	Goal 								Starts engine sName of type sType.
   '	In Params					   sType - The Class of the engine (e.g. "Excel")
   '											  sName - The name of the relevant reporter (e.g. "My Log")
   '											  sSettings - The settings to send the engine as "Field1>Value1|Field2>Value2". Can be empty
   '																	Depends on the specific engine (e.g. "ClearExisting>True|File>c:\log.txt")	
   '	Out Params					Boolean - Success/Failure
   '*************************************************************'   
''' <summary>
''' Starts engine sName of type sType
''' </summary>
''' <param name="sType" type="String" paramlist="EngineType">The engine type</param>
''' <paramlist name="EngineType">
''' <item><text>"QTP"</text><description>This Engine is the native QTP reporter.Reportevent engine <see cref="QTPEngine"/></description></item>
''' <item><text>"User"</text><description>This Engine pops-up messages for the used, that disappear after a few seconds <see cref="UserEngine"/></description></item>
''' <item><text>"Text"</text><description>This Engine is a simple text file logger, with template support <see cref="TextEngine"/></description></item>
''' <item><text>"Excel"</text><description>This Engine is a an Excel logger, built upon Sameer Girolkar's ResultToXL <see cref="ExcelEngine"/></description></item>
''' <item><text>"Winlog"</text><description>This engine reports to the windows event log <see cref="WinlogEngine"/></description></item>
''' <item><text>"ScreenCapture"</text><description>This Engine captures screenshots and saves them <see cref="ScreenCaptureEngine"/></description></item>
''' <item><text>"DB"</text><description>This Engine reports to any database ADODB can access <see cref="DBEngine"/></description></item>
''' <item><text>"XML"</text><description>This Engine reports to an XML file, which can later be viewed via a stylesheet<see cref="XMLEngine"/></description></item>
''' </paramlist>
''' <param name="sName" type="String">The user logical name of the engine</param>
''' <param name="sSettings" type="String">The settings string to be passed to the engine</param>
''' <returns type="Boolean">Returns True if successful, otherwise False</returns>
''' <remarks>Please note that by default, engines are allowed to be <b>Re</b>started only in the first test iteration<hr/>
''' In order to start engines in other iterations, change the <see cref="ReporterManagerClass.OnlyInitOnce"/> parameter</remarks>
   Public Function StartEngine(sType, sName, sSettings)
			''' <value type="xEngine"/>
			Dim oReporter
			''' <value type="Boolean"/>
			Dim bResult
			''' <value type="Integer"/>
			Dim i
			bResult = True
			
			'Don't do anything if the engine exists and it's the second iteration
			If (OnlyInitOnce = True) And oReporters.Exists(sName) Then
				If Environment.Value("TestIteration") <> 1 Then
					StartEngine = True
					Exit Function
				End If
			End If
			
			On Error Resume Next
            		'Try to create an instanse of the reporter engine's class
					Execute "Set oReporter = New " & sType & "Engine" 
		
					If Err.Number <> 0 Then
						bResult = False
						Reporter.ReportEvent micFail, "ReporterManagerClass StartEngine", "Failed to create instance : " & sType
					End If

			On Error Goto 0

			If bResult = True Then 

				On Error Resume Next  'Allow legacy support with 0.8 engines
					Set oReporter.Manager = Me
					oReporter.Name = sName
					Err.Clear
				On Error Goto 0
				
				On Error Resume Next
                	bResult = oReporter.StartEngine(sSettings) 'Try to start the engine
					
					If Err.Number <> 0 Then 'We might need to stop a duplicate engine
						If oReporters.Exists(sName) Then 'The name is unique
							Err.Clear
							StopEngine(sName) 
							bResult = oReporter.StartEngine(sSettings) 'Try to start the engine
						End If
					End If
					
					If Err.Number <> 0 Then bResult = False
				On Error Goto 0
				
				If bResult = True Then 

					'Build Object Structure
					If oReporters.Exists(sName) Then 'The name is unique
							StopEngine(sName) 
					Else
                        oReporters.Add sName, BuildReporter 
					End If

					Set oReporters(sName)("Engine") = oReporter 
					
				End If 'Engine Started
				
			End If 'Instance Created

			If bResult = False Then
				Reporter.ReportEvent micFail, "ReporterManagerClass StartEngine", "Failed to start engine " & sName & " Of Type " & sType
				On Error Resume Next
					oReporter.StopEngine					
				On Error Goto 0
			End If

			Set oReporter = Nothing

			StartEngine = bResult
            
   End Function

   '*************************************************************'
   '	Sub StopEngine  (Public)
   '	Goal 								Stops the relevant reporting engine
   '	In Params						sName - The name of the relevant reporter (e.g. "My Log")
   '	Out Params					None
   '*************************************************************'
''' <summary>
''' Stops the relevant reporting engine
''' </summary>
''' <param name="sName" type="String">The user logical name of the engine</param>
	Public Sub StopEngine(sName) 

	   If oReporters.Exists(sName) Then 
			If Not oReporters(sName)("Engine") Is Nothing Then 
				oReporters(sName)("Engine").StopEngine
				Set oReporters(sName)("Engine") = Nothing
			End If		   
		End If
		
	End Sub

   '*************************************************************'
   '	Sub Report  (Public)
   '	Goal 								Reports an event to all possible reporters
   '	In Params					   sStatus - The status of the event : "Fail", "Pass", "Warning" or custom
   '											  sStepName - The name of the event (e.g. "Validate Field Value")
   '											  sExpected - The expected result (e.g. "Value = Something")
   '											  sActual - The actual result (e.g. "Value = Something Else")
   '											  sDetauls - Possile details about the event
   '	Out Params					None
   '*************************************************************'   	    
''' <summary>
''' Reports an event to all possible reporters
''' </summary>
''' <param name="sStatus" type="String" paramlist="StatusType">The event's status</param>
''' <paramlist name="StatusType">
''' <item><text>"Fail"</text><description>The Step Failed</description></item>
''' <item><text>"Pass"</text><description>The Step Passed</description></item>
''' <item><text>"Warning"</text><description>A Warning</description></item>
''' <item><text>"General"</text><description>Just report the event</description></item>
''' </paramlist>
''' <param name="sStepName" type="String">The event's step name</param>
''' <param name="sExpected" type="String">The event's expected result</param>
''' <param name="sActual" type="String">The event's actual result</param>
''' <param name="sDetails" type="String">The event's details</param>
	Public Sub Report(sStatus, sStepName,sExpected,sActual, sDetails)
	   ''' <value type="Integer"/>
	   Dim i
	   ''' <value type="Array"/>
	   Dim arrKeys

	   arrKeys = oReporters.Keys 'Get all reporter names

	   For i = 0 To oReporters.Count-1
		   'For each event check if the event matches the reporter filters
			If CheckFilters(oReporters(arrKeys(i)), sStatus, sStepName,sExpected,sActual, sDetails) = True Then _	
					Call oReporters(arrKeys(i))("Engine").Report (sStatus, sStepName,sExpected,sActual, sDetails)
					'If so - report the event in this reporter
	   Next
	End Sub   

   '*************************************************************'
   '	Sub ReportToLog  (Public)
   '	Goal 								Reports an event directly to a single specific reporter
   '	In Params					   sReporterName - The name of the relevant reporter (e.g. "My Log")
   '											  sStatus - The status of the event : "Fail", "Pass", "Warning" or custom
   '											  sStepName - The name of the event (e.g. "Validate Field Value")
   '											  sExpected - The expected result (e.g. "Value = Something")
   '											  sActual - The actual result (e.g. "Value = Something Else")
   '											  sDetauls - Possile details about the event
   '	Out Params					None
   '*************************************************************'   	
''' <summary>
''' Reports an event directly to a single specific reporter
''' </summary>
''' <param name="sReporterName" type="String">The name of the relevant reporter (e.g. "My Log")</param>
''' <param name="sStatus" type="String" paramlist="StatusType">The event's status</param>
''' <paramlist name="StatusType">
''' <item><text>"Fail"</text><description>The Step Failed</description></item>
''' <item><text>"Pass"</text><description>The Step Passed</description></item>
''' <item><text>"Warning"</text><description>A Warning</description></item>
''' <item><text>"General"</text><description>Just report the event</description></item>
''' </paramlist>
''' <param name="sStepName" type="String">The event's step name</param>
''' <param name="sExpected" type="String">The event's expected result</param>
''' <param name="sActual" type="String">The event's actual result</param>
''' <param name="sDetails" type="String">The event's details</param>
	Public Sub ReportToLog(sReporterName, sStatus, sStepName,sExpected,sActual, sDetails)'Report to a specific Log

		If oReporters.Exists(sReporterName) Then _
			Call oReporters(sReporterName)("Engine").Report (sStatus, sStepName,sExpected,sActual, sDetails)

	End Sub


   '*************************************************************'
   '	Function AddFilter (Public)
   '	Goal 								Adds a filter to different reporters
   '	In Params					   sReporterName - The names of the relevant reporters as "Reporter1>Reportet2>Reporter3..."
   '											  sFilterType - The class of the filter (e,g, "RegEx")
   '											  sFilterSettings - The settings to send the filter as "Field1>Value1|Field2>Value2". Can be empty
   '																			Depends on the specific filter (e.g. "Incldue>True|Pattern>.*Error.*")	
   '	Out Params					Boolean - Success/Failure
   '*************************************************************'   	
''' <summary>
''' Adds a filter to different reporters
''' </summary>
''' <param name="sFilterType" type="String" paramlist="FilterType">The filter's type</param>
''' <paramlist name="FilterType">
''' <item><text>"RegEx"</text><description>This filter performs Regular Expressions searches within the event's data <see cref="RegExFilter"/></description></item>
''' <item><text>"ObjectProperty"</text><description>This filter screens event according to an object's condition <see cref="ObjectPropertyFilter"/></description></item>
''' </paramlist>
''' <param name="sReporterNames" type="String">The names of the relevant reporter 
''' (e.g. "My Log>My Second Log")</param>
''' <param name="sFilterSettings" type="String">The settings to be passed to the reporter</param>
''' ''' <returns type="Boolean">Returns True if successful, otherwise False</returns>
	Public Function AddFilter(sFilterType, sReporterNames, sFilterSettings)
	   ''' <value type="Integer"/>
	   Dim i
	   ''' <value type="String"/>
	   Dim sProtectReporters
	   ''' <value type="Array"/>
	   Dim arrReporters 'Holds the reporters array
	   ''' <value type="String"/>
	   Dim sReplaceString 
	   ''' <value type="String"/>
	   Dim sReporterName 'Holds a single reporter name

	   sReplaceString = "ABCDEFGHIJKLMNOP1"

	   sProtectReporters = Replace(sReporterNames, ">>", sReplaceString)
       
	   arrReporters = Split(sProtectReporters, ">")
	   For i = 0 To UBound(arrReporters)
			sReporterName = Replace(arrReporters(i), sReplaceString, ">")
			Call AddFilterToReporter(sFilterType, sReporterName, sFilterSettings)
	   Next
	   
	End Function

   '*************************************************************'
   '	Function AddFilterToReporter (Private)
   '	Goal 								Adds a filter to a specific log
   '	In Params					   sReporterName - The name of the relevant reporter (e.g. "My Log")
   '											  sFilterType - The class of the filter (e,g, "RegEx")
   '											  sFilterSettings - The settings to send the filter as "Field1>Value1|Field2>Value2". Can be empty
   '																			Depends on the specific filter (e.g. "Incldue>True|Pattern>.*Error.*")	
   '	Out Params					Boolean - Success/Failure
   '*************************************************************'   	
''' <summary>
''' Adds a filter to a single reporter
''' </summary>
''' <param name="sFilterType" type="String" paramlist="FilterType">The filter's type</param>
''' <paramlist name="FilterType">
''' <item><text>"RegEx"</text><description>This filter performs Regular Expressions searches within the event's data</description><see cref="RegExFilter"/></item>
''' <item><text>"ObjectState"</text><description>This filter screens event according to an object's condition</description><see cref="ObjectStateFilter"/></item>
''' </paramlist>
''' <param name="sReporterName" type="String">The name of the relevant reporter</param>
''' <param name="sFilterSettings" type="String">The settings to be passed to the reporter</param>
''' <returns type="Boolean">Returns True if successful, otherwise False</returns>
   	Private Function AddFilterToReporter(sFilterType, sReporterName, sFilterSettings)
	   ''' <value type="Boolean"/>
	   Dim bResult
	   ''' <value type="XFilter"/>
	   Dim oFilter
	      	   
	   bResult = True
	   
	   If Not oReporters.Exists(sReporterName) Then
		   AddFilterToReporter = False
		   Reporter.ReportEvent micFail, "ReporterManagerClass StartFilter", "Failed - No such engine: " & sReporterName
		   Exit Function
	   End If

		On Error Resume Next
		
				'Create a new instrance of the filter
				Execute "Set oFilter = New " & sFilterType & "Filter"
	
				If Err.Number <> 0 Then
					bResult = False
				End If

		On Error Goto 0
	
	   If bResult = False Then 'Instance wasn't created
		   AddFilterToReporter = False
		   Reporter.ReportEvent micFail, "ReporterManagerClass StartFilter", "Failed to create instance : " & sFilterType
			Set oFilter = Nothing
		   Exit Function
	   End If

	   On Error Resume Next 'Allow Legacy Support For 0.8 Filters
			Set oFilter.Manager = Me
			Set oFilter.Engine = oReporters(sReporterName)("Engine")
			Err.Clear
	   On Error Goto 0

	   bResult = oFilter.StartFilter(sFilterSettings)	'Activates the filter

	   If bResult = False Then
			Reporter.ReportEvent micFail, "ReporterManagerClass StartFilter", "Failed to start filter " & sFilterType
		Else
			'Add new filter to the filter dictionary by Key = filter index in the dictionary
		   oReporters(sReporterName)("Filters").Add oReporters(sReporterName)("Filters").Count, oFilter			
	   End If

	   AddFilterToReporter = bResult

	   Set oFilter = Nothing
	End Function

   '*************************************************************'
   '	Sub ClearFilters  		  (Private)
   '	Goal 								Removes all the filters within a filter collection
   '	In Params						sReporterName - The name of the relevant reporter (e.g. "My Log")
   '	Out Params					None
   '*************************************************************'
''' <summary>
''' Removes all the filters within a filter collection
''' </summary>
''' <param name="sReporterName" type="String">The Reporter Name</param>
Private Sub ClearFilters(sReporterName) 
	   ''' <value type="Integer"/>
	   Dim i
	   ''' <value type="Integer"/>
	   Dim iCount

       If Not oReporters.Exists(sReporterName) Then Exit Sub

	   iCount = oReporters(sReporterName)("Filters").Count

	   For i = 0 To iCount-1
	   		oReporters(sReporterName)("Filters")(i).ClearFilter
			oReporters(sReporterName)("Filters").Remove i 'Clear Each Filter
	   Next

	End Sub

   '*************************************************************'
   '	Function BuildReporter  (Private)
   '	Goal 								Builds a new reporter object structure
   '	In Params					   None
   '	Out Params					Object - The reporter data structure (Dictionary)
   '*************************************************************'   	   
''' <summary>
''' Builds a new reporter object structure
''' </summary>
''' <returns type="Scripting.Dictionary">
''' <dictionarykeys>
''' <item><text>Engine</text><description>The reporter engine class</description></item>
''' <item><text>Filters</text><description>The reporter filters collection</description></item>
''' </dictionarykeys></returns>
   Private Function BuildReporter 
''' <value type="Scripting.Dictionary" dictionarykeys="ReporterDictionary"/>
''' <dictionarykeys name="ReporterDictionary">
''' <item><text>"Engine"</text><description>The reporter engine class</description></item>
''' <item><text>"Filters"</text><description>The reporter filters collection</description></item>
''' </dictionarykeys>
	  Dim oResult
''' <value type="Scripting.Dictionary"/>	  
	  Dim oFilters
	  
	  Set oResult = CreateObject("Scripting.Dictionary")
	  Set oFilters = CreateObject("Scripting.Dictionary")

		oResult.Add "Filters", oFilters
		oResult.Add "Engine", Nothing

		Set BuildReporter = oResult

   End Function

   '*************************************************************'
   '	Function CheckFilters  (Private)
   '	Goal 								Checks if an event report matches the reporters filters
   '	In Params					   oReporter - The reporter dictionary object
   '											  sStatus - The status of the event : "Fail", "Pass", "Warning" or custom
   '											  sStepName - The name of the event (e.g. "Validate Field Value")
   '											  sExpected - The expected result (e.g. "Value = Something")
   '											  sActual - The actual result (e.g. "Value = Something Else")
   '											  sDetauls - Possile details about the event
   '	Out Params					Boolean - Does the event match the reporter filters
   '*************************************************************'   	   
''' <summary> Checks if an event report matches the reporters filters </summary>
''' <param name="oReporter" type="Scripting.Dictionary">The reporter dictionary</param>
''' <param name="sStatus" type="String" paramlist="StatusType">The event's status</param>
''' <paramlist name="StatusType">
''' <item><text>"Fail"</text><description>The Step Failed</description></item>
''' <item><text>"Pass"</text><description>The Step Passed</description></item>
''' <item><text>"Warning"</text><description>A Warning</description></item>
''' <item><text>"General"</text><description>Just report the event</description></item>
''' </paramlist>
''' <param name="sStepName" type="String">The event's step name</param>
''' <param name="sExpected" type="String">The event's expected result</param>
''' <param name="sActual" type="String">The event's actual result</param>
''' <param name="sDetails" type="String">The event's details</param>
''' <returns type="Boolean">Returns True if successful, otherwise False</returns>
	Private Function CheckFilters(oReporter, sStatus, sStepName,sExpected,sActual, sDetails)
		''' <value type="Boolean"/>
		Dim bResult
		''' <value type="Integer"/>
		Dim i
		
		bResult = True

		'Loop through the filters
		For i = 0 To oReporter("Filters").Count-1
			'Send the event to each filter and check the result
			bResult = oReporter("Filters")(i).Check(sStatus, sStepName,sExpected,sActual, sDetails)
			If bResult = False Then Exit For				
		Next

		CheckFilters = bResult
		
	End Function

   '*************************************************************'
   '	Sub Class_Initialize    (Private)
   '	Goal 								Setup Initialize event.
   '	In Params						None
   '	Out Params					None
   '*************************************************************'
   ''' <summary> Setup Initialize event </summary>
   Private Sub Class_Initialize   
   	  OnlyInitOnce = True
	  Set oReporters = CreateObject("Scripting.Dictionary")
   End Sub

   '*************************************************************'
   '	Sub Class_Terminate    (Private)
   '	Goal 								Setup Terminate event.
   '	In Params						None
   '	Out Params					None
   '*************************************************************'
   ''' <summary> Setup Terminate event </summary>
   Private Sub Class_Terminate   
	   ''' <value type="Integer"/>
	   Dim i
	   ''' <value type="Array"/>
	   Dim arrKeys

	   If oReporters Is Nothing Then Exit Sub

	   arrKeys = oReporters.Keys
		'Stop all reporting engines and break the reporters data structure
	   For i = 0 To oReporters.Count -1
	 			StopEngine arrKeys(i)
				oReporters(arrKeys(i)).Remove "Engine"
				
				ClearFilters arrKeys(i)
				oReporters(arrKeys(i)).Remove "Filters"				
	   Next
	   
	  Set oReporters = Nothing

   End Sub
   
   ''' <summary>
	''' Stops all engines
	''' </summary>

   Public Sub StopAll
   	Dim arrKeys
	
   	   arrKeys = oReporters.Keys
		'Stop all reporting engines
	   For i = 0 To oReporters.Count -1
	 			StopEngine arrKeys(i)
	   Next

   End Sub

End Class