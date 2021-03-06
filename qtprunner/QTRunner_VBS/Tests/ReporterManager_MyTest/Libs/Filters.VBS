'''<summary>This file holds all the different filter classes</summary>
''' <datecreated>01/09/2007</datecreated>
'''<version>0.9</version>



Option Explicit

'**********************************************************************'
'								Filters
'**********************************************************************'
''' <summary>
''' This filter performs Regular Expressions searches within the event's data
''' </summary>
''' <remarks>
''' Possible Settings :
''' <para>
''' Include = True/False. A success is finding the RegEx (True) or NOT finding it (False)<br/>
''' 			Default = True
''' </para>
''' <para>
''' WhatToSearch = "Value1,Value2,....", where ValueX = one of Status/StepName/Expected/Actual/Details<br/>
''' 				In what parts of the event to report should we search for the RegEx<br/>
''' 				Default = "Status, StepName, Expected, Actual, Details" (all of them)
''' </para>
''' <para>
''' IgnoreCase = True/False. Should we ignore String Case In our searchs<br/>
''' 			Default = True (ignore case)
''' </para>
''' <para>
''' Pattern = String. The RegEx pattern to seach (e.g. ".*Error.*")<br/>
''' 			Default = ".*" (Success = any string)<br/>
''' 			Remember to use || instead of | if your patten includes this char. e.g. use "Pattern>Fail||Warning|IgnoreCase>True"
''' </para>
''' See settings details at <see cref="RegExFilter.StartFilter"/>
''' </remarks>
''' <example><c>"Include>False|WhatToSearch>Status|Pattern>.*Warning.*"</c>- his will filter OUT all events with status = Warning (because Include = False)</example>
Class RegExFilter
'Written by Yaron Assa, 1/8/07. Project home at www.AdvancedQTP.com

	'This filter performs Regular Expressions searches within the event's data

	'Possible Settings :
	'Include					= True/False. A success is finding the RegEx (True) or NOT finding it (False)
	'										Default = True
	'WhatToSearch 	= "Value1, Value2, ....", where ValueX = one of Status/StepName/Expected/Actual/Details
	'										In what parts of the event to report should we search for the RegEx
	'										Default = "Status, StepName, Expected, Actual, Details" (all of them)
	'IgnoreCase			 =    True/False. Should we ignore string case in our searchs
	'										Default = True (ignore case)
	'Pattern					=   String. The RegEx pattern to seach (e.g. ".*Error.*")
	'										Default = ".*" (Success = any string)
	'										Remember to use || instead of | if your patten includes this char. e.g. use "Pattern>Fail||Warning|IgnoreCase>True"
	'
	'E.g. : "Include>False|WhatToSearch>Status|Pattern>.*Warning.*" 
	'This will filter OUT all events with status = Warning (because Include = False)
	
	''' <value type="Scripting.Dictionary"/>
	''' <summary>Will hold the settings dictionary</summary>	
	Private oSettings 'Will hold the settings dictionary
	
	''' <summary>
	''' Holds a link to the reporter manager
	''' </summary>
	''' <value type="ReporterManagerClass"/>
	''' <seealso cref="ReporterManagerClass"/>	
	Public Manager 'Holds a link to the reporter manager
	
	''' <summary>
	''' Holds a link to the relevant engine
	''' </summary>
	''' <value type="XEngine"/>
	Public Engine 'Holds a link to the relevant engine

	''' <summary>
	''' The Filter's Version
	''' </summary>
	''' <value type="String"/>
	Public Property Get Version
		Version = "0.9"
	End Property

	''' <summary>
	''' The minimum version of the manager, required for this filter to work
	''' </summary>
	''' <value type="String"/>
	Public Property Get MinimunManagerVersion
		MinimunManagerVersion = "0.8"
	End Property
	
	
	''' <summary>
	''' ID for the 	''' Type of the Filter
	''' </summary>
	''' <value type="String"/>
	Public Property Get FilterType 'For ID
		FilterType = "RegEx" 
	End Property
	
	'*************************************************************'
	'	Function StartFilter    (Public)
	'	Goal 								Makes the filter ready for use
	'	In Params					   sSettings - The Filters init settings as "Field1>Value1|Field2>Value2|...
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Makes the filter ready for use
	''' </summary>
	''' <param name="sSettings" type="String">The Filter's init settings as "Field1>Value1|Field2>Value2|...
	''' <list type="table">
	''' <item><term>Include</term>
	''' 	<description>True/False. A success is finding the RegEx (True) or NOT finding it (False)<br/>
	''' 				Default = True
	''' 	</description></item>
	''' <item><term>WhatToSearch</term>
	''' 	<description>"Value1, Value2, ....", where ValueX = one of Status/StepName/Expected/Actual/Details<br/>
	'''						In what parts of the event to report should we search for the RegEx<br/>
	'''						Default = "Status, StepName, Expected, Actual, Details" (all of them)
	''' 	</description></item>
	''' <item><term>IgnoreCase</term>
	''' 	<description>True/False. Should we ignore string case in our searchs<br/>
	''' 				Default = True (ignore case)
	''' 	</description></item>
	''' <item><term>Pattern</term>
	''' 	<description>String. The RegEx pattern to seach (e.g. ".*Error.*")<br/>
	''' 				Default = ".*" (everything)<br/>
	''' 				Remember to use || instead of | if your patten includes this char. e.g. use "Pattern>Fail||Warning|IgnoreCase>True"	
	''' 	</description></item>	
	''' </list></param>
	''' <returns type="Boolean">True if succeeded to start the filter, False if failed</returns>
	''' <example><c>"Include>False|WhatToSearch>Status|Pattern>.*Warning.*"</c>- his will filter OUT all events with status = Warning (because Include = False)</example>
	Public Function StartFilter(sSettings)
		Load_Settings(sSettings)
   		StartFilter = AllowCreate
	End Function
	
	'*************************************************************'
	'	Function Check    (Public)
	'	Goal 								Checks if an event matches the filter's criteria
   '	In Params                      sStatus - The status of the event : "Fail", "Pass", "Warning" or custom
   '											  sStepName - The name of the event (e.g. "Validate Field Value")
   '											  sExpected - The expected result (e.g. "Value = Something")
   '											  sActual - The actual result (e.g. "Value = Something Else")
   '											  sDetauls - Possile details about the event
	'	Out Params					Boolean - Does it match the criteria? If = True means the event will be reported
	'*************************************************************'  
''' <summary>Checks if an event matches the filter's criteria</summary>
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
''' <returns type="Boolean">Returns True if the event matches, otherwise False</returns>
	Public Function Check(sStatus, sStepName,sExpected,sActual, sDetails)
	   ''' <value type="Boolean"/>
	   Dim bResult
	   ''' <value type="Array"/>
	   Dim arrWhatToSearch
	   ''' <value type="Boolean"/>
	   Dim bInclude
	   ''' <value type="String"/>
	   Dim sPattern
	   ''' <value type="Integer"/>
	   Dim iMatches
	   ''' <value type="Integer"/>
	   Dim i
	   ''' <value type="String"/>
	   Dim sSearch

		'In what parts of the event should the RegEx Search
	   arrWhatToSearch = Split(oSettings("WhatToSearch"), ",")

		'Include = True -> A success is finding the RegEx.
		'Include = False -> A success is NOT finding the RegEx.
	   bInclude = CBool(oSettings("Include")) 

	   'What RegEx to search
		sPattern = oSettings("Pattern")

		For i = 0 To UBound(arrWhatToSearch)
			Execute "sSearch = s" & Trim(arrWhatToSearch(i)) 'Get the name of the part's variable

			iMatches = DoRegEx(sPattern, sSearch) 'Do RegEx Search

			bResult = iMatches > 0	'DId we find anything?

			If bInclude = False Then bResult = Not bResult	'Include = False reverses the success criteria

			If (bResult = False) And (bInclude=False) Then Exit For 'One wrong find is enough to fail the filter

			If (bResult = True) And (bInclude=True) Then Exit For 'One find is enough to pass the filter
			
		Next

	   Check = bResult
	End Function

	'*************************************************************'
	'	Function DoRegEx    (Private)
	'	Goal 								Does the actual RegEx search
   '	In Params                      patrn - The RegEx pattern (e.g. ".*Error,*")
   '											  strng - What to search in
	'	Out Params					Integer - The number of matches
	'*************************************************************'  
	''' <summary>
	''' Does the actual RegEx search
	''' </summary>
	''' <param name="patrn" type="String">The RegEx pattern (e.g. ".*Error,*")</param>
	''' <param name="strng" type="String">The string to be searched</param>
	''' <returns type="Integer">The number of matches</returns>
	Private Function DoRegEx(patrn, strng)
	   'A helper function for Check
	   ''' <value type="RegExp"/>
	   Dim regEx
	   ''' <value type="Matches"/>
	   Dim oMatches
	   
	   Set regEx = New RegExp   
		
	   regEx.Pattern = patrn   ' Set pattern.
	   regEx.IgnoreCase = CBool(oSettings("IgnoreCase"))  
	   regEx.Global = False   '
	   Set oMatches = regEx.Execute(strng)   ' Execute search.

	   DoRegEx = oMatches.Count
	   
	End Function


	'*************************************************************'
	'	Sub Init_Settings    (Private)
	'	Goal 								Specifies default values of the settings object
	'	In Params						None
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Specifies default values of the settings object
	''' </summary>
	''' <remarks><para>Include = True</para>
	''' 	<para>IgnoreCase = True</para>
	''' 	<para>WhatToSearch = "Status,StepName,Expected,Actual,Details"</para>
	''' 	<para>Pattern = ".*"	</para>
	''' 	</remarks>	
	Private Sub  Init_Settings 'Inits the filter's default settings 
	   Set oSettings = CreateObject("Scripting.Dictionary")
	   oSettings.Add "Include", "True"					'Success is when the string is found (positive search)
	   oSettings.Add "IgnoreCase", "True"		  'Ignore string case (e.g. "Name" is a match for "name")
	   oSettings.Add "WhatToSearch", "Status,StepName,Expected,Actual,Details" 'Seach evety part of the event
	   oSettings.Add "Pattern", ".*"		  'The RegEx pattern to seach
	End Sub

   '*************************************************************'
   '	Function AllowCreate  		  (Private)
   '	Goal 								Checks if there would be a problem to create the reporter engine
   '	In Params						None
   '	Out Params					Boolean - Yes/No (Yes = OK to create)
   '*************************************************************'
   ''' <summary>
	''' Checks if there would be a problem to create the reporter engine
	''' </summary>
	''' <returns type="Boolean">True if it's OK to create the filter, False otherwise</returns>
	Private Function AllowCreate
	   ''' <value type="Boolean"/>
	   Dim bResult

	   bResult = CheckVersion
		
	   AllowCreate = bResult
	End Function

   '*************************************************************'
   '	Private Function CheckVersion  		  (Private)
   '	Goal 								Checks if the manager and engine versions are ok
   '	In Params						None
   '	Out Params					Boolean - Yes/No (Yes = OK to create)
   '*************************************************************'
	''' <summary>
	''' Checks if the manager and engine versions are ok
	''' </summary>
	''' <returns type="Boolean">True if the filter passes the version check, False otherwise</returns>
	Private Function CheckVersion
	''' <value type="Integer"/>
	   Dim iDiff
	   ''' <value type="String"/>
	   Dim sEngineVersion
	   ''' <value type="String"/>
	   Dim sManagerVersion

	   'Check the manager version is good enough
	   sEngineVersion = Replace(CStr(Me.MinimunManagerVersion), ".", "")
	   sManagerVersion = Replace(CStr(Me.Manager.Version), ".","")

	   iDiff = Len(sEngineVersion) - Len(sManagerVersion)

	   If iDiff > 0 Then
			sManagerVersion = sManagerVersion & String(Abs(iDiff), "0")
		Else
			sEngineVersion = sEngineVersion & String(Abs(iDiff), "0")
	   End If

	   If CLng(sEngineVersion) > CLng(sManagerVersion)  Then
		   Reporter.ReportEvent MicFail, "Start Engine", "Failed Version Check" & vbCrLf & "Engine Expected " & Me.MinimunManagerVersion & _
																										vbCrLf & "But manager was actually " & Me.Manager.Version
		   CheckVersion = False
		   Exit Function
	   End If

	   'Check the engine version is good enough
	   sEngineVersion = Replace(CStr(Me.Version), ".", "")
	   sManagerVersion = Replace(CStr(Me.Manager.MinimumEngineVersion), ".","")

	   iDiff = Len(sEngineVersion) - Len(sManagerVersion)

	   If iDiff > 0 Then
			sManagerVersion = sManagerVersion & String(Abs(iDiff), "0")
		Else
			sEngineVersion = sEngineVersion & String(Abs(iDiff), "0")	
	   End If

	   If CLng(sEngineVersion) < CLng(sManagerVersion)  Then
		   Reporter.ReportEvent MicFail, "Start Engine", "Failed Version Check" & vbCrLf & "Manager expected " & Me.Manager.MinimumEngineVersion &_
																									  vbCrLf & "But engine actually was " & Me.Version
		   CheckVersion = False
		   Exit Function
	   End If

		CheckVersion = True
	End Function
	
	'*************************************************************'
	'	Sub Load_Settings    (Private)
	'	Goal 								Loads sSettings into the oSettings object
	'	In Params						sSettings - The Filters init settings as "Field1>Value1|Field2>Value2|...
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Loads sSettings into the oSettings object
	''' </summary>
	''' <param name="sSettings" type="String">The Filter's init settings as "Field1>Value1|Field2>Value2|...</param>
	Private Sub Load_Settings(sSettings) 
	   '***************************' No Need to change this sub! ***************************'
		''' <value type="Array"/>
		Dim ReplaceArray
		''' <value type="Array"/>
		Dim arrData, arrSettingData
		''' <value type="Integer"/>
		Dim i

		'Protect >> and || chars
		ReplaceArray = Array ("ABCDEFGHIJKLMNOP1", "ABCDEFGHIJKLMNOP2")

			sSettings  = Replace(sSettings, "||", ReplaceArray(0))
			sSettings  = Replace(sSettings, ">>", ReplaceArray(1))
			
		   arrData = Split(sSettings, "|") 'Split to individual fields
	
			   For i = 0 To UBound(arrData) 'Loop through fields
				   If arrData(i) <> "" Then
						arrSettingData = Split(arrData(i), ">") 'Break to Field and to Value
						
						'Protect | char
						arrSettingData(0) = Replace(arrSettingData(0), ReplaceArray(0), "|")
						arrSettingData(1) = Replace(arrSettingData(1), ReplaceArray(0), "|")

						'Protect > char
						arrSettingData(0) = Replace(arrSettingData(0), ReplaceArray(1), ">")
						arrSettingData(1) = Replace(arrSettingData(1), ReplaceArray(1), ">")

						If oSettings.Exists(arrSettingData(0)) Then 'There's a default value for this field
							oSettings(arrSettingData(0)) = arrSettingData(1) 'Change it to the user's value
						Else
							oSettings.Add arrSettingData(0), arrSettingData(1) 'Create new setting
						End If
						
					End If
					
			   Next
	
	End Sub


	
	'*************************************************************'
	'	Sub Class_Initialize    (Private)
	'	Goal 								Setup Initialize event.
	'	In Params						None
	'	Out Params					None
	'*************************************************************'  
''' <summary>
''' Setup Initialize event
''' </summary>
   Private Sub Class_Initialize()   ' Setup Initialize event.
		Init_Settings
		Set Manager = Nothing
		Set Engine = Nothing		
   End Sub

	'*************************************************************'
	'	Sub Class_Terminate    (Private)
	'	Goal 								Setup Terminate event.
	'	In Params						None
	'	Out Params					None
	'*************************************************************' 
''' <summary>
''' Setup Terminate event
''' </summary>
	Private Sub Class_Terminate   ' Setup Terminate event.
		Set oSettings = Nothing
		Set Manager = Nothing
		Set Engine = Nothing		
   End Sub
	
	''' <summary>
	''' Stops the current filter and clears it
	''' </summary>
	Public Sub ClearFilter
		Class_Terminate
	End Sub
	
End Class

'**************************************************************************************************************************************************************************************
'**************************************************************************************************************************************************************************************
'**************************************************************************************************************************************************************************************
'**************************************************************************************************************************************************************************************
'**************************************************************************************************************************************************************************************

''' <summary>
''' This filter screens event according to an object's condition
''' </summary>
''' <remarks>
''' Possible Settings :
''' <para>
''' ObjectProperty = String. A REPRESENTATION of an object property (QTP or other). e.g. " VBWindow(""vbname:=Something"").Exist "<br/>
''' 				For negative conditions use NOT. e.g. : " Not VBWindow(""vbname:=Something"").Exist "<br/>
''' 				ONLY USE DP, not OR objects. You can use any VBScript command that can be properly represented. Access RO properties etc.<br/>
''' 				. e.g "Trim(VBWindow(""vbname:=Something"").Object.Date)"<br/>
''' 				Default = None
''' </para>
''' <para>
''' Value = String. The wanted property of the value<br/>
''' 		Default = None
''' </para>
''' <para>
''' Condition = Boolean. 	True -> a match is considered a success.<br/>
''' 			False -> a match is considered a failture<br/>
''' 			e.g. "ObjectProperty>Window(""name:=x"").getTOProperty(""text"")|Value>Notepad|Condition>False"<br/>
''' 			This will FAIL if the window's text = "Notepad", and will PASS if it's anything else
''' </para>
''' See settings details at <see cref="ObjectPropertyFilter.StartFilter"/>
''' </remarks>
''' <example><c>"ObjectProperty>VBWindow(""vbname:=Something"").Exist|Value>True"</c> - This will only report events when the window does exist </example>
Class ObjectPropertyFilter
'Written by Yaron Assa, 1/8/07. Project home at www.AdvancedQTP.com

	'This filter screens event according to an object's condition

	'Please Notice – When using this filter, make sure your QTP object sync timeout is LOW (i.e. 1 / 2 sec.)
	'(Otherwise the script execution time will increase significantly)


	'Possible Settings :
	'ObjectProperty		= String. A REPRESENTATION of an object property (QTP or other). e.g. " VBWindow(""vbname:=Something"").Exist "
	'										For negative conditions use NOT. e.g. : " Not VBWindow(""vbname:=Something"").Exist "
	'										ONLY USE DP, not OR objects. You can use any VBScript command that can be properly represented. Access RO properties etc.
	'										. e.g "Trim(VBWindow(""vbname:=Something"").Object.Date)"
	'										Default = None. 
	'Value						= String. The wanted property of the value
	'										Default = None
	'Condition				= Boolean. 	True -> a match is considered a success.
    '														False -> a match is considered a failture
	'									   e.g. "ObjectProperty>Window(""name:=x"").getTOProperty(""text"")|Value>Notepad|Condition>False"
	'													This will FAIL if the window's text = "Notepad", and will PASS if it's anything else
	'										Default = True
	
	'
	'E.g. : "ObjectProperty>VBWindow(""vbname:=Something"").Exist|Value>True"
	'This will only report events when the window does exist
	''' <value type="Scripting.Dictionary"/>
	''' <summary>Will hold the settings dictionary</summary>	
	Private oSettings 'Will hold the settings dictionary
	
	''' <summary>
	''' Holds a link to the reporter manager
	''' </summary>
	''' <value type="ReporterManagerClass"/>
	''' <seealso cref="ReporterManagerClass"/>		
	Public Manager 'Holds a link to the reporter manager
	
	''' <summary>
	''' Holds a link to the relevant engine
	''' </summary>
	''' <value type="XEngine"/>
	Public Engine 'Holds a link to the relevant engine
	
	''' <summary>
	''' ID for the type of the filter
	''' </summary>
	''' <value type="String"/>
	''' <example>"DB"</example>	
	Public Property Get FilterType 'For ID
		FilterType = "ObjectProperty" 
	End Property

	''' <summary>
	''' The Engine's Version
	''' </summary>
	''' <value type="String"/>
	''' <example>"0.8"</example>	
	Public Property Get Version
		Version = "0.9"
	End Property

	''' <summary>
	''' The minimum version of the manager, required for this filter to work
	''' </summary>
	''' <value type="String"/>
	''' <example>"0.8"</example>	
	Public Property Get MinimunManagerVersion
		MinimunManagerVersion = "0.8"
	End Property
	
	'*************************************************************'
	'	Function StartFilter    (Public)
	'	Goal 								Makes the filter ready for use
	'	In Params					   sSettings - The Filters init settings as "Field1>Value1|Field2>Value2|...
	'	Out Params					None
	'*************************************************************'  

	''' <summary>
	''' Makes the filter ready for use
	''' </summary>
	''' <param name="sSettings" type="String">The Filter's init settings as "Field1>Value1|Field2>Value2|...
	''' <list type="table">
	''' <item><term>ObjectProperty</term>
	''' 	<description>String. A REPRESENTATION of an object property (QTP or other). e.g. " VBWindow(""vbname:=Something"").Exist "<br/>
	'''					For negative conditions use NOT. e.g. : " Not VBWindow(""vbname:=Something"").Exist "<br/>
	''' 				ONLY USE DP, not OR objects. You can use any VBScript command that can be properly represented. Access RO properties etc.<br/>
	''' 				. e.g "Trim(VBWindow(""vbname:=Something"").Object.Date)"<br/>
	''' 				Default = None
	''' 	</description></item>
	''' <item><term>Value</term>
	''' 	<description>String. The wanted property of the value<br/>
	'''						Default = None
	''' 	</description></item>
	''' <item><term>Condition</term>
	''' 	<description>Boolean. 	True -> a match is considered a success.<br/>
	''' 				False -> a match is considered a failture	<br/>
	''' 				e.g. "ObjectProperty>Window(""name:=x"").getTOProperty(""text"")|Value>Notepad|Condition>False"	<br/>
	''' 				Default = True
	''' 	</description></item>
	''' </list></param>
	''' <returns type="Boolean">True if succeeded to start the Filter, False if failed</returns>
	''' <example><c>"ObjectProperty>VBWindow(""vbname:=Something"").Exist|Value>True"</c> - This will only report events when the window does exist </example>
		Public Function StartFilter(sSettings)
		   Load_Settings(sSettings)
		   StartFilter = AllowCreate
		End Function
	
	'*************************************************************'
	'	Function Check    (Public)
	'	Goal 								Checks if an event matches the filter's criteria
   '	In Params                      sStatus - The status of the event : "Fail", "Pass", "Warning" or custom
   '											  sStepName - The name of the event (e.g. "Validate Field Value")
   '											  sExpected - The expected result (e.g. "Value = Something")
   '											  sActual - The actual result (e.g. "Value = Something Else")
   '											  sDetauls - Possile details about the event
	'	Out Params					Boolean - Does it match the criteria? If = True means the event will be reported
	'*************************************************************'  
	''' <summary>Checks if an event matches the filter's criteria</summary>
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
''' <returns type="Boolean">Returns True if the event matches, otherwise False</returns>

	Public Function Check(sStatus, sStepName,sExpected,sActual, sDetails)
	   ''' <value type="Boolean"/>
	   Dim bResult
	   ''' <value type="String"/>
	   Dim PropertyValue
	   ''' <value type="Boolean"/>
	   Dim bCondition

		If oSettings("ObjectProperty") ="" Or oSettings("Value") = "" Then
			Check = True
			Exit Function
		End If

		bCondition = CBool(oSettings("Condition"))

		bResult = True

		On Error Resume Next
			Execute "PropertyValue = " & oSettings("ObjectProperty")

			If err.Number <> 0 Then
				bResult = False
			End If
		On Error Goto 0

		If bResult = False Then 
			Check = bResult
			Exit Function
		End If

		If CStr(PropertyValue) = CStr(oSettings("Value")) Then 

			If bCondition = True Then 'Was ment to match
				Check = True
			Else
				Check = False
			End If
			
		Else
		
			If bCondition = False Then  'Was ment NOT to match
				Check = True
			Else
				Check = False
			End If
			
		End If

	End Function

	'*************************************************************'
	'	Sub Init_Settings    (Private)
	'	Goal 								Specifies default values of the settings object
	'	In Params						None
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Specifies default values of the settings object
	''' </summary>
	''' <remarks><para>ObjectProperty = ""</para>
	''' 	<para>Value = ""</para>
	''' 	<para>Condition = True</para>
	''' 	</remarks>	
	Private Sub  Init_Settings 'Inits the filter's default settings 
	   Set oSettings = CreateObject("Scripting.Dictionary")
	   oSettings.Add "ObjectProperty", ""
	   oSettings.Add "Value", ""
	   oSettings.Add "Condition", "True"
	End Sub

   '*************************************************************'
   '	Function AllowCreate  		  (Private)
   '	Goal 								Checks if there would be a problem to create the reporter engine
   '	In Params						None
   '	Out Params					Boolean - Yes/No (Yes = OK to create)
   '*************************************************************'
   ''' <summary>
	''' Checks if there would be a problem to create the reporter engine
	''' </summary>
	''' <returns type="Boolean">True if it's OK to create the filter, False otherwise</returns>
   
	Private Function AllowCreate
	   Dim bResult

	   bResult = CheckVersion
		
	   AllowCreate = bResult
	End Function

   '*************************************************************'
   '	Private Function CheckVersion  		  (Private)
   '	Goal 								Checks if the manager and engine versions are ok
   '	In Params						None
   '	Out Params					Boolean - Yes/No (Yes = OK to create)
   '*************************************************************'
		''' <summary>
	''' Checks if the manager and engine versions are ok
	''' </summary>
	''' <returns type="Boolean">True if the filter passes the version check, False otherwise</returns>

	Private Function CheckVersion
	''' <value type="Integer"/>
	   Dim iDiff
	''' <value type="String"/>
	   Dim sEngineVersion
	''' <value type="String"/>
	   Dim sManagerVersion

		
	   'Check the manager version is good enough
	   sEngineVersion = Replace(CStr(Me.MinimunManagerVersion), ".", "")
	   sManagerVersion = Replace(CStr(Me.Manager.Version), ".","")

	   iDiff = Len(sEngineVersion) - Len(sManagerVersion)

	   If iDiff > 0 Then
			sManagerVersion = sManagerVersion & String(Abs(iDiff), "0")
		Else
			sEngineVersion = sEngineVersion & String(Abs(iDiff), "0")	
	   End If

	   If CLng(sEngineVersion) > CLng(sManagerVersion)  Then
		   Reporter.ReportEvent MicFail, "Start Engine", "Failed Version Check" & vbCrLf & "Engine Expected " & Me.MinimunManagerVersion & _
																										vbCrLf & "But manager was actually " & Me.Manager.Version
		   CheckVersion = False
		   Exit Function
	   End If

	   'Check the engine version is good enough
	   sEngineVersion = Replace(CStr(Me.Version), ".", "")
	   sManagerVersion = Replace(CStr(Me.Manager.MinimumEngineVersion), ".","")

	   iDiff = Len(sEngineVersion) - Len(sManagerVersion)

	   If iDiff > 0 Then
			sManagerVersion = sManagerVersion & String(Abs(iDiff), "0")
		Else
			sEngineVersion = sEngineVersion & String(Abs(iDiff), "0")	
	   End If

	   If CLng(sEngineVersion) < CLng(sManagerVersion)  Then
		   Reporter.ReportEvent MicFail, "Start Engine", "Failed Version Check" & vbCrLf & "Manager expected " & Me.Manager.MinimumEngineVersion &_
																									  vbCrLf & "But engine actually was " & Me.Version
		   CheckVersion = False
		   Exit Function
	   End If

		CheckVersion = True
	End Function
	
	'*************************************************************'
	'	Sub Load_Settings    (Private)
	'	Goal 								Loads sSettings into the oSettings object
	'	In Params						sSettings - The Filters init settings as "Field1>Value1|Field2>Value2|...
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Loads sSettings into the oSettings object
	''' </summary>
	''' <param name="sSettings" type="String">The Filter's init settings as "Field1>Value1|Field2>Value2|...</param>
	Private Sub Load_Settings(sSettings) 
	   '***************************' No Need to change this sub! ***************************'
		
		Dim ReplaceArray
		Dim arrData, arrSettingData
		Dim i

		'Protect >> and || chars
		ReplaceArray = Array ("ABCDEFGHIJKLMNOP1", "ABCDEFGHIJKLMNOP2")

			sSettings  = Replace(sSettings, "||", ReplaceArray(0))
			sSettings  = Replace(sSettings, ">>", ReplaceArray(1))
			
		   arrData = Split(sSettings, "|") 'Split to individual fields
	
			   For i = 0 To UBound(arrData) 'Loop through fields
				   If arrData(i) <> "" Then
						arrSettingData = Split(arrData(i), ">") 'Break to Field and to Value
						
						'Protect | char
						arrSettingData(0) = Replace(arrSettingData(0), ReplaceArray(0), "|")
						arrSettingData(1) = Replace(arrSettingData(1), ReplaceArray(0), "|")

						'Protect > char
						arrSettingData(0) = Replace(arrSettingData(0), ReplaceArray(1), ">")
						arrSettingData(1) = Replace(arrSettingData(1), ReplaceArray(1), ">")

						If oSettings.Exists(arrSettingData(0)) Then 'There's a default value for this field
							oSettings(arrSettingData(0)) = arrSettingData(1) 'Change it to the user's value
						Else
							oSettings.Add arrSettingData(0), arrSettingData(1) 'Create new setting
						End If
						
					End If
					
			   Next
	
	End Sub

	'*************************************************************'
	'	Sub Class_Initialize    (Private)
	'	Goal 								Setup Initialize event.
	'	In Params						None
	'	Out Params					None
	'*************************************************************'  
	''' <summary>
	''' Setup Initialize event
	''' </summary>
   Private Sub Class_Initialize()   ' Setup Initialize event.
		Init_Settings
		Set Manager = Nothing
		Set Engine = Nothing		
   End Sub

	'*************************************************************'
	'	Sub Class_Terminate    (Private)
	'	Goal 								Setup Terminate event.
	'	In Params						None
	'	Out Params					None
	'*************************************************************' 
	''' <summary>
	''' Setup Terminate event
	''' </summary>
	Private Sub Class_Terminate   ' Setup Terminate event.
		Set oSettings = Nothing
		Set Manager = Nothing
		Set Engine = Nothing
   End Sub

	''' <summary>
	''' Stops the current filter and clears it
	''' </summary>
	Public Sub ClearFilter
		Class_Terminate
	End Sub
	
End Class
