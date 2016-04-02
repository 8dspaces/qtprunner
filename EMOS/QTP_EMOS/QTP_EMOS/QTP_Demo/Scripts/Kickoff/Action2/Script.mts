select case lcase( parameter("drv_func_name") )

case "emos_testsuite_driver"
    call emos_testsuite_driver( parameter("arg1"), parameter("arg2"), parameter("arg3"), parameter("arg4"), parameter("arg5") )
Case "emos_test_driver"
    call emos_test_driver( parameter("arg1"), parameter("arg2"), parameter("arg3"), parameter("arg4"), parameter("arg5") )
end select


' -----------------------------------------------------
' called from the main QTP script
'
' created by: Sahoko Hama & Dave Longoria

' --------------------------------------------------------------------------
' set all the necessary parameters needed for the test
' and calls FRM_DRV_main
'
' @param arg1 script_home - location of main QTP script ex) c:\QTP\Scripts
' @param arg2 data_home - location of data files ex) c:\QTP\Data
' @param arg3 suite_table - name of the data file ex) suite1.xls
' @param arg4 boolean value true or false
' @param arg5 suite table headers - column names in suite1.xls 
' 						ex) IDX,Name,run,driver,table,testset,description
' @return FRM_DRV_main() 

function emos_testsuite_driver ( arg1, arg2, arg3, arg4, arg5 )

  '  if ( arg1 <> "" ) Then FRM_DRV_set_script_home( arg1 )
  '  if ( arg2 <> "" ) Then FRM_DRV_set_data_home( arg2 )
  '  if ( arg3 <> "" ) Then FRM_DRV_set_default_suite_table( arg3 )
   ' if ( arg4 <> "" ) Then FRM_DRV_set_ask( arg4 )
   ' if ( arg5 <> "" ) Then FRM_DRV_set_columns( arg5 )
    dim rc
	'emos_testsuite_driver = FRM_DRV_main( arg1, arg2, arg3, arg4, arg5 )
    rc = RunAction( "FRM", oneIteration, "FRM_DRV_main", arg1, arg2, arg3, arg4, arg5 )

    emos_testsuite_driver = rc

End Function

' -----------------------------------------------------
' called from FRM_DRV_test_set_driver()
' defined in emos_frm_driver_lib.vbs
' 
' created by: Sahoko Hama & Dave Longoria

' -----------------------------------------
' returns FRM_DRV_test_driver()
'
' param@ test_col
' param@ test_step
' param@ params_arr
' param@ param_count
' param@ bmp_file

function emos_test_driver(test_col, test_step, byRef params_arr, byRef param_count, byRef bmp_file )

	'emos_test_driver = FRM_DRV_test_driver( test_col, test_step, params_arr, param_count, bmp_file )
    dim rc
    rc = RunAction( "FRM", oneIteration, "FRM_DRV_test_driver", test_col, test_step, params_arr, param_count, bmp_file )

    emos_test_driver = rc
	
end function

























