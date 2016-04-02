' -----------------------------------------
'  this is the only QTP script you'll have to create
' 
'  prerequisites: You need to set up the XML Object Repository
'  										    and register other utilitiy dlls.
'  									      Just follow the instructions below	    
' Install the following:
' MSXML4.0: 
' http://www.microsoft.com/downloads/details.aspx?displaylang=en&familyid=3144b72b-b4f2-46da-b4b6-c5d7485f2b42
' DOR Parser:
' http://www.qantom.shubharaghu.com/html/dor_download.html
' VB6 Runtime Files:
' http://support.microsoft.com/default.aspx?scid=kb;en-us;290887

' Register the following dlls:
' regsvr32 C:\QTP\Lib\DescriptiveOR.dll
' You need to copy these dlls below first to C:\WINDOWS\system32. They're in C:\QTP\Lib.
' regsvr32 C:\WINDOWS\system32\clipboard.dll
' regsvr32 C:\WINDOWS\system32\csolib2.dll
' regsvr32 C:\WINDOWS\system32\JSListVw.dll

' Set up QTP's built-in log file - it's getting parsed at the end of the test run
' in regedit, change the value of the following key to 1
' HKEY_LOCAL_MACHINE\SOFTWARE\Mercury Interactive\QuickTest Professional\Logger\Media\Log\Active

' this demo script was tested on:
' QTP 6.5
' WindowsXP
' IE6

'  created by: Sahoko Hama

'  variables
dim TEST_PATH : TEST_PATH = "..\..\Scripts"
dim DATA_PATH : DATA_PATH = "..\..\Data"
dim SUITE_TABLE : SUITE_TABLE = "suite1.xls"
dim ASK_TABLE : ASK_TABLE = false
dim SUITE_HDR : SUITE_HDR = "IDX,Name,run,driver,table,testset,description"


' load libraries
call load_libs()

' launch ie
call gen_launch_testenv()

' begin test
RunAction "DRV", oneIteration, "emos_testsuite_driver", TEST_PATH, DATA_PATH, SUITE_TABLE, ASK_TABLE, SUITE_HDR

' display results
call gen_show_logfile()































