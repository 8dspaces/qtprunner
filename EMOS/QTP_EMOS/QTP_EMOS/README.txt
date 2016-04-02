This framework is a first attempt to convert the orginal WinRunner EMOS framework to 
Quick Test Pro (QTP).  Much of the original framework has yet to be converted.   

Please reference the original EMOS framework created by Dean Rajovic 
for an understanding of the EMOS functionality.
The original EMOS framework for WinRunner can be found on Carsten's website
http://www.cbueche.de


' -----------------------------------------
'  created by: Sahoko Hama and Dave Longoria
'  prerequisites: You need to set up the XML Object Repository
'  and register other utilitiy dlls.
'  Follow the instructions below
'	    
' Install the following:
' 1. MSXML4.0: 
'    http://www.microsoft.com/downloads/details.aspx?displaylang=en&familyid=3144b72b-b4f2-46da-b4b6-c5d7485f2b42
'
' 2. DOR Parser:
'    http://www.qantom.shubharaghu.com/html/dor_download.html
'
' 3. VB6 Runtime Files:
'    http://support.microsoft.com/default.aspx?scid=kb;en-us;290887

' Unzip QTP_Demo.zip to C:\ - the zip file contains the QTP_Demo folder.
' C:\QTP_Demo is the home directory for this automation test suite.
' The home directory is defined as a user-defined environment variable.
' TO DO LIST: This actually needs to be a relative path, but I haven't done it yet.

' Register the following dlls (run the command in the command prompt):
' regsvr32 C:\QTP_Demo\Lib\DescriptiveOR.dll

' Copy these dlls below first to C:\WINDOWS\system32 before registering. They are in C:\QTP_Demo\Lib.
' C:\WINDOWS\system32\clipboard.dll - you don't have to register this one.
' C:\WINDOWS\system32\csolib2.dll - you don't have to register this one.
' regsvr32 C:\WINDOWS\system32\JSListVw.dll

' Set up QTP's built-in log file - it's getting parsed at the end of the test run
' in regedit, change the value of the following key to 1
' HKEY_LOCAL_MACHINE\SOFTWARE\Mercury Interactive\QuickTest Professional\Logger\Media\Log\Active
'
' Run the test settings configuration script C:\QTP_Demo\Utilities\configure_testsettingsoptions.vbs (just double-click the file). 
' This script sets the required QTP settings for the Framwork.
'	
' Open the kickoff script located in C:\QTP_Demo\Scripts\Kickoff in QTP
' Click Run
' Select "Temporary run results folder"
' Select http://newtours.mercuryinteractive.com and click ok 

' The test case will perform a demonstration on the Mercury Web Tours Demo site
'
' this demo script was tested on:
' QTP 6.5
' WindowsXP
' IE6

