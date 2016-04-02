'################################################################################
'# TEST:	EMOS_FRM_driver_lib
'################################################################################
'# Copyright (C) 2000  EMOS Computer Consulting GmbH
'#
'# This library is free software; you can redistribute it and/or
'# modify it under the terms of the GNU Lesser General Public
'# License as published by the Free Software Foundation; either
'# version 2.1 of the License, or (at your option) any later version.
'#
'# This library is distributed in the hope that it will be useful,
'# but WITHOUT ANY WARRANTY; without even the implied warranty of
'# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
'# Lesser General Public License for more details.
'#
'# You should have received a copy of the GNU Lesser General Public
'# License along with this library; if not, write to the Free Software
'# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
'#
'# For further information please contact:
'#
'#	Dean Rajovic
'#	EMOS Computer Consulting GmbH
'#	Oskar-Messter-Straße 25
'#	85737 Ismaning
'#	Germany
'#	tel.: +49 89 608 765-0
'#	mailto:drajovic@emos.de
'#	http://www.emos.de


' --------------------------------------------------------------------------
' QTP EMOS Framework - emos_frm_driver_lib.vbs
' 
' purpose: to make the EMOS framework work with Quick Test Professional
' revision: 12/09/2004 
' authors: Sahoko Hama & Dave Longoria
' note: The translated version of the library in VBScript looks very different
' 			from the original. It does not have all the functionalities that the original
' 			EMOS provides. The modified version simply lets you extract all the object names
' 			and test parameters from an Excel spreadsheet. 
' --------------------------------------------------------------------------

' function call chain: FRM_DRV_main() -> 
' 										 FRM_DRV_test_suite_driver() ->
' 										 FRM_DRV_test_suite_driver() ->
' 										 FRM_DRV_test_set_driver() ->
' 										 emos_test_driver() ->
' 										 FRM_DRV_test_driver() ->
' 
dim rc : rc = micPass

select case lcase( parameter("frm_func_name") )
    case "frm_drv_main"
        rc =  FRM_DRV_main( parameter("arg1"), parameter("arg2"), parameter("arg3"), parameter("arg4"), parameter("arg5") )
    Case "frm_drv_test_driver"
        rc =  FRM_DRV_test_driver( parameter("arg1"), parameter("arg2"), parameter("arg3"), parameter("arg4"), parameter("arg5") )
end select

exitaction(rc)

' constants
const MAX_ARG_COUNT = 100 ' the max num of variables for each action
													' you can change this if you need more than 100 object names for an action

Public const PREFIX = "IDX"
Public const ENG_COLUMNS  = "run,driver,table,testset,description"
Public const ENG_ERROR_MODES = "1 - Continue,2 - Test stop,3 - Testset stop,4 - Testsuite stop,5 - Block Repeat"

const FIRST_ACTION_ROW = 3 ' the very first action starts here
const TEST_SEQUENCE_ROW = 2 ' testsequence is defined in row 2
const COL_IDX = "IDX"
const COL_NAME = "Name"
const TEST_SHEET = "TEST_TABLE"
const SUITE_SHEET = "SUITE_TABLE"
const TEST_PREFIX = "Test_" ' test case col name must begin w/ a char
									 					' in the suite table, the testset range doesn't have this prefix
									 					
' these column names used in suite1.xls								 
const COL_RUN = "run"
const COL_DRIVER = "driver"
const COL_TABLE = "table"
const COL_TESTSET = "testset"
const COL_DESC = "description"							

Public script_home
Public data_home
Public default_suite_table
Public ask : ask = FALSE
Public columns : columns = ENG_COLUMNS
Public new_test_driver : new_test_driver = FALSE
Public fail_on_unknown_test : fail_on_unknown_test = FALSE
Public def_error_mode : def_error_mode = E_FRM_CONTINUE
Public error_modes : error_modes = ENG_ERROR_MODES


' -----------------------------------------------
' set a home directory for the main QTP script
' @param scpt_home_dir

function FRM_DRV_set_script_home ( scpt_home_dir )
    script_home = scpt_home_dir
end function

' --------------------------
' set a data directory 
' @param dt_home_dir

function FRM_DRV_set_data_home ( dt_home_dir )
    data_home = dt_home_dir
end function

' --------------------------
' set a suite table name
' @param default_suite_name

function FRM_DRV_set_default_suite_table ( default_suite_name )
    default_suite_table = default_suite_name
end function

' ------------------------------------------------------
' gives an option to choose a suite table
' note: this is not coded yet, so the only available option is false
' @param par - boolean value true or false

function FRM_DRV_set_ask ( par )
    ask = par
end function

' ----------------------------------------
' set column names used in a suite table
' @param cols - comman-separated string
'								ex) IDX,Name,run,driver,table,testset,description

function FRM_DRV_set_columns ( cols )
    columns = cols
end function


' ----------------------------------------
' get the column names in a suite table
' and call FRM_DRV_test_suite_driver()
' 
' @param arg1 script_home - location of main QTP script ex) c:\QTP\Scripts
' @param arg2 data_home - location of data files ex) c:\QTP\Data
' @param arg3 suite_table - name of the data file ex) suite1.xls
' @param arg4 boolean value true or false
' @param arg5 suite table headers - column names in suite1.xls 
' 						ex) IDX,Name,run,driver,table,testset,description
' @return FRM_DRV_test_suite_driver() 

function FRM_DRV_main( arg1, arg2, arg3, arg4, arg5 )

	dim suite_table, stid, cols, rc
  
	if ( arg1 <> "" ) Then FRM_DRV_set_script_home( arg1 )
	if ( arg2 <> "" ) Then FRM_DRV_set_data_home( arg2 )
	if ( arg3 <> "" ) Then FRM_DRV_set_default_suite_table( arg3 )
	if ( arg4 <> "" ) Then FRM_DRV_set_ask( arg4 )
	if ( arg5 <> "" ) Then FRM_DRV_set_columns( arg5 )

	if ( columns = "" ) Then
		columns = ENG_COLUMNS
	end if
	
	'# sanity check that we have at lest four cols 	
	cols = split( columns, "," )
	
	FRM_DRV_main = FRM_DRV_test_suite_driver(script_home, data_home, arg3, cols)	 

end function

' -----------------------------
' need to be converted to QTP

function FRM_DRV_choose_table ( tbl_dir, tbl_name, ask, path )

	Dim rc 
	rc = E_OK
	
	if ( ask OR ask = "" ) Then
		if ( fail( DDT_choose_table( join_path( tbl_dir, tbl_name ), tbl_dir, tbl_name ) ) ) Then 
			FRM_DRV_choose_table = getLastRc()
		end if
	end if
	path = join_path( tbl_dir, tbl_name, "\\" )
	FRM_DRV_choose_table = E_OK
end function

' -----------------------------------------------------------
' process all the test sets in the suite table 
' if its run parameter is set to yes
' 
' @param script_home - home directory for test scripts
' @param data_home - home directory for test data
' @param stid - suite table name ex) suite1.xls
' @param cols - column names, but it's not really used
' @return rc

function FRM_DRV_test_suite_driver( script_home, data_home, stid, cols)

	Dim data_table, xtabs
	Dim run, table, driver, testset, description
	Dim doit
	Dim row
	Dim i : i = 1
	Dim rc
	Dim suite_rc

	' import the suite table to QTP's data table
	call DataTable.AddSheet(SUITE_SHEET)
	call DataTable.ImportSheet(data_home & "\" & stid, 1, SUITE_SHEET)
	
	' process the suite table
	call DataTable.GetSheet(SUITE_SHEET)
	row_count = DataTable.GetSheet(SUITE_SHEET).GetRowCount
	
	do while (i =< row_count)
		
		' fetch test-set data from excel table
		DataTable.GetSheet(SUITE_SHEET).SetCurrentRow(i)
		run = datatable.value(COL_RUN, SUITE_SHEET)
		driver = datatable.value(COL_DRIVER, SUITE_SHEET)
		table 	= DataTable.Value(COL_TABLE, 	 SUITE_SHEET)
		testset	= DataTable.Value(COL_TESTSET, SUITE_SHEET)
		description = DataTable.Value(COL_DESC, SUITE_SHEET)
		
		' process a test set table if its run parameter is yes, otherwise skip
		if run = "yes" then	
			call reporter.reportevent(micDone, LOG_PREFIX & description, table & ": " & testset)
			rc = FRM_DRV_test_set_driver(driver, table, testset, description)			
		end if
		
		' increment table index counter
		i = i + 1
		
	loop
	
	FRM_DRV_test_suite_driver = rc

end function

' ----------------------------------------------------------
' process a test set
' 
' @param driver vbs script that load all the lib files
' 			 should contain executefile statements
' @param table
' @param testset
' @param description

function FRM_DRV_test_set_driver(driver, table, testset, description ) 

	Dim test, tid, i, test_step, test_col, tmp_steps, j
	Dim via_IDX
	Dim expr, msg
	Dim rc, set_rc
	dim actions, args, row_count, action_count, action_args, num_arg, temparr
	dim val, arg, row_num, action_id, arg_name
	dim url, username, password, param_str, bmp_file
	
	
	' import a test set table
	call DataTable.AddSheet(TEST_SHEET)
	call DataTable.ImportSheet( data_home & "\" & table, 1, TEST_SHEET )
	
	' get all the action names and arguments
	row_count = DataTable.GetSheet(TEST_SHEET).GetRowCount
	call create_dictionary_obj( actions ) ' key is the action name - stores index starting from 1
	call create_dictionary_obj( arg_count ) ' key is the action name - - stores number of rows for the action
	call create_dictionary_obj( arg_row ) ' key is the index of action & ":" & argument name
													  ' ex) 1:username (actions.item("Login") returns 1)
													  ' - stores row number for that argument 
	
	' set the active row to 3
	DataTable.GetSheet(TEST_SHEET).SetCurrentRow( FIRST_ACTION_ROW ) ' first action name begins here
	
	' index all the action names
	call index_actions( actions, row_count )
	
	' set the active row to 3
	DataTable.GetSheet(TEST_SHEET).SetCurrentRow( FIRST_ACTION_ROW ) ' first action name begins here
	
	' get the number of arguments for each action
	call get_argcount( arg_count, row_count )
	
	' array to store action name and its arg names
	action_count = actions.count
	redim action_args(action_count, MAX_ARG_COUNT )
	
	' set the active row to 3
	DataTable.GetSheet( TEST_SHEET ).SetCurrentRow( FIRST_ACTION_ROW ) ' first action name begins here
	
	' get the row number for each arg
	call get_rownum( arg_row, action_args, actions, row_count )
	'msgbox arg_row("2:arrival_city")
	
	
	'# Parse testset
	test = parse_testset( testset )
  
	' load all the libs needed for the test
	'call load_drv_lib(driver)
 
	i = 0 ' array index for test case
	for each testcase in test
  
		if testcase="" then
			exit for
		end if
		
  	test_col = test(i)
  	'# Read test steps from test column
  	DataTable.GetSheet(TEST_SHEET).SetCurrentRow(TEST_SEQUENCE_ROW) 'Testsequence is defined in this row
  	tmp_steps = DataTable.Value(TEST_PREFIX & cstr(test_col), TEST_SHEET)
  	test_steps = split(lcase(tmp_steps), ",") ' perhaps log test_steps to the QTP results page??
  	
  	'# iterate through all steps	
  	for each t_step in test_steps
  				'msgbox t_step
    			num_arg = arg_count.item(t_step)
    			'msgbox t_step & " - " & num_arg
    			redim temparr(num_arg)
    			
    			action_id = actions.item(t_step)
    		
    			if num_arg<>0 then
	    			' load parameter name and its value in the global sheet
	    			param_str = ""
	    			for j=0 to num_arg-1	
	    				arg = action_args(action_id,j+1)    				
	    				row_num = arg_row.item( action_id & ":" & arg )   			
	    				
	    				' set the active row to where the arg is in the test sheet
	    				datatable.getsheet(TEST_SHEET).setcurrentrow(row_num)
	    				val = datatable( TEST_PREFIX & testcase, TEST_SHEET)
	    				
	    				' store parameter names for deletion later
	    				temparr(j) = arg
							
	    				call datatable.getsheet("Global").addparameter(arg, val)
	    				' get param=val pair for reporting
	    				if val <> "" then
	    					if val <> "random" then param_str = param_str & arg & " = " & val & vbcrlf
	    				end if
	    					
	    			next
	    			
    			end if
    			
    			'msgbox t_step
    			
    			' begin executing the action
    			'rc = emos_test_driver( test_col, t_step, temparr, num_arg, bmp_file )

                'rc = RunAction( "DRV", oneIteration, "emos_test_driver", test_col, t_step, , num_arg, bmp_file)
                rc = FRM_DRV_test_driver(  test_col, t_step, temparr, num_arg, bmp_file )
    			
    			' Get the test status
    			if rc <> micPass then
    				call reporter.reportevent(micFail, LOG_PREFIX  & TEST_PREFIX & cstr(test_col) & ": " & t_step, param_str & bmp_file )
    				exit for ' move on to next test case
    			else
    				call reporter.reportevent(micPass, LOG_PREFIX  & TEST_PREFIX & cstr(test_col) & ": " & t_step, param_str )
    			end if
    		  		
    	next
    	  	
    		' increment index for testcase
    		i = i + 1 		
  next
	
	call release( actions )
	call release( arg_row )
	call release( arg_count )
	
	FRM_DRV_test_set_driver = rc

end function

' ----------------------------------------------------
' call AUT_DRV_call_block() defined in act_actions.vbs
' this actually excutes QTP actions
' The function contans some error handling. 
' It cleans up the run-time datatable and captures a screen shot
' How to recover from an error need to be modified to meet your needs
'
' param@ test_col
' param@ test_step
' param@ params_arr
' param@ param_count
' param@ bmp_file

function FRM_DRV_test_driver (test_col, test_step, byRef params_arr, byRef param_count, byRef bmp_file )
	
	dim rc
	dim rc2 : rc2 = micPass
	dim i
	
	bmp_file = LOG_PATH & "\" & TEST_PREFIX & cstr(test_col) & "_" & test_step & ".bmp"
	
	' execute action
	'rc = AUT_DRV_call_block( test_step )
    rc = RunAction( "AUT", oneIteration, "AUT_DRV_call_block", test_step)
	
	' clean up the global sheet
   if param_count <> 0 then
	   for i=0 to param_count-1
	    call datatable.getsheet("Global").deleteparameter(params_arr(i))
	   next
   end if
	
   ' take care of the failed test case
	If rc = micFail then
        gen_Activate_Browser()
		rc2 = gen_get_screenshot(bmp_file)
		'msgbox r2
		if rc2 = micPass then rc2 =  rec_recover_from_error()
		if rc2 = micFail then
			call reporter.reportevent(micFail, "recovering from error failed", "")
		end if
  end if
   
	FRM_DRV_test_driver =  rc
	
end function




' ###########################################################################################################
' all the functions below are helper functions called from FRM_DRV_test_set_driver()

' ------------------------------
' Create a dictionary object
' @param obj (inout) 
' ------------------------------
sub create_dictionary_obj( byRef obj )

	set obj = createobject("scripting.dictionary")
	
end sub

' --------------------------
' Unassign object reference
' @param (inout)
' --------------------------
sub release( byRef obj )

	set obj = nothing
	
end sub

' ---------------------------------------------------------
' index all the action names in a dictionary obj
' @param obj (inout) a dictionary object
' @param row_count (in) number of rows in a given sheet
' ---------------------------------------------------------
sub index_actions( byRef obj, row_count )

	dim j, num_arg
	
	j = 1
	num_arg = 0
	wait(2)
	for i=1 to row_count-2 ' excluding the first 2 rows (description and testsequence)
		if datatable(COL_IDX, TEST_SHEET)<>"" then
			call obj.add( lcase(datatable(COL_NAME, TEST_SHEET)),j)		
			j=j+1
		end if
		DataTable.GetSheet(TEST_SHEET).SetNextRow
	next

end sub

' -------------------------------------------------------------
' get the number of arguments for each action
' @param obj (inout) a dictionary object
' @param row_count (in) the number of rows in a given sheet
' ------------------------------------------------------------
sub get_argcount( byRef obj, row_count )

	dim j, num_arg
	
	j = 1
	num_arg = 0
	
	for i=0 to row_count-2 ' excluding the first 2 rows (description and testsequence)
		if datatable(COL_IDX, TEST_SHEET)<>"" then
			j=j+1		
			' count num of argments for each action
			if num_arg <> 0 then
				call obj.add(lcase(action_name), num_arg)
				'msgbox action_name & "=" & obj.item(lcase(action_name))
				num_arg=0
				action_name = datatable(COL_NAME, TEST_SHEET)	
			elseif i=0 then
				action_name = datatable(COL_NAME, TEST_SHEET)	
			else
				call obj.add(lcase(action_name), num_arg)
				'msgbox action_name & "=" & obj.item(lcase(action_name))
				action_name = datatable(COL_NAME, TEST_SHEET)
			end if		
		else
			num_arg = num_arg + 1
		end if
		DataTable.GetSheet(TEST_SHEET).SetNextRow
	next
	
	' count num of argments for last argument
	if num_arg <> 0 then
		call obj.add(lcase(action_name), num_arg)
	end if

end sub

' ---------------------------------------------------------------------------------------------------
' get row number for each argument
' param@ obj (inout) a dictionary obj to store action index & ":" & arg name and row number pairs
'							ex) 1:username --> 4
' param@ action_args (in) 2 dimensional array that stores action name index and its arg names
' param@ actions (in) a dictionary obj  that stores action name & index pairs
' param@ row_count (in) the number of rows in a given sheet
' --------------------------------------------------------------------------------------------------
sub get_rownum( byRef obj, byRef action_args, byRef actions, row_count )

	dim i
	
	for i=1 to row_count
		if datatable(COL_IDX, TEST_SHEET)<>"" then
			action_name = datatable(COL_NAME, TEST_SHEET)
			DataTable.GetSheet(TEST_SHEET).SetNextRow
			j = 1
		else
			'msgbox actions.item(lcase(action_name))
			action_args( actions.item(lcase(action_name)), j) = datatable(COL_NAME, TEST_SHEET)
			'msgbox datatable.getsheet(TEST_SHEET).getcurrentrow
			call obj.add( actions.item(lcase(action_name))&":"&datatable(COL_NAME, TEST_SHEET), datatable.getsheet(TEST_SHEET).getcurrentrow)
			DataTable.GetSheet(TEST_SHEET).SetNextRow
			j = j+1
		end if	
	next

end sub

' --------------------------------------------------
' parse the testset range defined in the suite table
' @param testset (in) ex) "1, 3-6, 10-12"
' --------------------------------------------------
function parse_testset( testset )

	dim test(100) ' can hold up to 100 test cases
	dim testcomma, tc_num
	dim testdash, td, i, j, k
	
	testcomma = split( testset, "," )
	tc_num = ubound( testcomma )
	
	k=0
	for i=0 to tc_num
	
		if instr( testcomma(i), "-")=0 then ' when there is only one test case ex) 1
			test(k) = testcomma(i)
			k = k + 1
		else
			testdash = split( testcomma(i), "-")	' for the format, "1-4"
			if cint(testdash(0)) > cint(testdash(1)) then
				call reporter.reportevent(micFail, "illegal parameter", testcomma(i)&": the range is not correct")
			end if
			for j=testdash(0) to testdash(1)
				test(k) = j
				k = k+1
			next
		end if				
		
	next
	
	parse_testset = test

end function

' load all the libraries
' @param driver - a vbs file containins executefile() statements
'
' note: the relative path here is like this because the main
' 			QTP script resides in C:\QTP\Scripts\Kickoff

sub load_drv_lib ( driver )

	driver = "..\..\Lib\EMOS_GPL\DRV\" & driver & ".vbs"
	call executefile( driver )
 
end sub
































