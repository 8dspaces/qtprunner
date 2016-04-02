package frm;
import mode.*;
import stereotype.*;

/**
 * The library routines for the EMOS_FRM_driver test.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_driver_lib_tsl.html" target=tsl>emos_frm_driver_lib.tsl</A>
*/

public class emos_frm_driver_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_driver_lib (){}
/**
 * Home directory for test scripts
 */

public String FRM_DRV_set_script_home( in dir ) {}

/**
 * Home directory for test data
 */

public String FRM_DRV_set_data_home( in dir ) {}

/**
 * Name of the test suite table
 */

public String FRM_DRV_set_default_suite_table( in name ) {}

/**
 * Set routine for activating the new test driver logic
 */

public String FRM_DRV_set_new_test_driver( in test_driver ) {}

/**
 * Get routine for activating the new test driver logic
 */

public String FRM_DRV_is_new_test_driver(  ) {}

/**
 * Turns failure on unknown test on/off [default: FALSE]
 * @param mode (in) TRUE raises an error by FRM_DRV_test_set_driver() 
 *  if an unknown test (i.e. nonexisting column) is called, FALSE doesn't
 */

public String FRM_DRV_set_fail_on_unknown_test( in mode ) {}

/**
 * Get routine for activating activating failure on unknown test
 */

public String FRM_DRV_is_fail_on_unknown_test(  ) {}

/**
 * Sets the default error mode. This value is evaluated by the emos driver in
 * in order to determine how to proceed after an error has been detected in
 * some test block. In interactive mode a dialog is displayed where you can
 * interactively choose the desired behaviour. In batchmode or if you choose
 * "Cancel" in interactive mode the default mode which is set here is applied.
 * By default the <code>E_FRM_CONTINUE</code> is used (which means that 
 * no test block will be skipped due to errors).
 * @param mode (in) possible values <code>E_FRM_TEST_STOP,  E_FRM_SET_STOP,  
 *	E_FRM_SUITE_STOP,  E_FRM_CONTINUE</code> if eny other value was passed 
 *  (including <code>E_FRM_RETRY</code>) the default mode will be set to 
 *  <code>E_FRM_CONTINUE</code>.
 * @param eror_modes (in) String list defining mode names 
 * <pre>[default:"1 - Weiter,2 - Test anhalten,3 - Testset anhalten,4 - Testsuite anhalten,5 - Block wiederholen"]</pre>
 *	Make sure to preserve the numbers, the meaning of modes and no blanks after comma!
 * @return the mode actually set (note it may be different from the wanted one!)
 */

public String FRM_DRV_set_default_error_mode( in mode, in mode_list ) {}

/**
 * TRUE: gives you the option to choose the alternative suite table;
 * FALSE: opens the defined table only [default]
 */

public String FRM_DRV_set_ask( in par ) {}

/**
 * Comma-separated string defining the
 * titles of the four important columns
 * [default: <pre>"Bearbeiten?,Testscript,Testtabelle,Testset[,Kommentar]"</pre>]
 */

public String FRM_DRV_set_columns( in cols ) {}

/**
 * The main loop processes all rows in the suite table.
 * @param arg1 home directory for test scripts
 * @param arg2 home directory for test data
 * @param arg3 name of the test suite table
 * @param arg4 TRUE: gives you the option to choose 
 * alternative suite table; FALSE: opens the defined table only [default]
 * @param arg5 comma-separated string defining the
 * titles of the four important columns 
 * [default: <pre>"Bearbeiten?,Testscript,Testtabelle,Testset[,Kommentar]"</pre>]
 * @return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_DRV_main( in arg1, in arg2, in arg3, in arg4, in arg5 ) {}

/**
 * Picks the appropriate file. If &lt;ask&gt; = TRUE [defult], then a
 * file browse dialog is shown to interactively pick the file. In batch
 * mode, the dialog is not shown.
 *@param dir (in)	directory
 *@param name (in)	file name
 *@param ask (in)	(optional) TRUE/FALSE [default: TRUE]
 *@param path (out)	full path name to be used
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

protected String FRM_DRV_choose_table( in dir, in name, in ask, out path ) {}

/**
 * Waits for the user input (an edit field and OK/Cancel buttons) if a table 
 * cell contains a specielly formatted data. The data is "specially formatted" 
 * if it either begins with the question mark or with some other string as 
 * defined by <code>flag</code> parameter.
 * @param tid (in)	table ID
 * @param param (in)	column name
 * @param msg (in)	message to be displayed
 * @param val (out)	value choosen (empty string indicates Cancel)
 * @param flag (in)	(optional) prefix which indicates the "askable" data [default: ?]
 * @return
 *	E_OK:	success; <code>val</code> contains valid data
 *	!E_OK:	failure; do not rely on <code>val</code>
 */

protected String FRM_DRV_ask( in tid, in param, in msg, out val, in flag ) {}

/**
 * execute a test suite excel table. 
 * @param script_home home directory for test scripts
 * @param data_home home directory for test data
 * @param stid suite_table ID ( as returned by FRM_open() )
 * @param cols 
 * @param rows
 */

protected String FRM_DRV_test_suite_driver( in script_home, in data_home, in stid, inout cols [], in rows ) {}

/**
 * Executes a set of tests from a specified data table by the specified script. 
 * @param script (in)	test driver (full path or accessable vie search path)
 * @param table (in)	data table (full path name!)
 * @param testset (in)	names of the tests to be executed (individual entries
 * are comma-separated; numeric ranges can be shortend by hyphen, e.g. 1-5 which
 * stands for 1,2,3,4,5; ranges can also be defined within name spaces and are 
 * similarly shortened, e.g. a1-3 stands for a1,a2,a3)
 */

public String FRM_DRV_test_set_driver( in script, in table, in testset, in comment ) {}

/**
 * The main lop for driving the test navigation.
 * @param lib (in) library that contains application-specific test navigation functions
 * @param tid (in) table ID of the Excel table that contains the sepcified <code>test</code>
 * @param test (in) name of the test (i.e. column) that is to be processed
 * @return E_OK if successfull, else failure
 */

public String FRM_DRV_test_driver( in lib, in tid, in test ) {}

/**
 * Formats the report for unimplemented test blocks. Note that this function
 * increases the inout variable <code>frm_rc</code> by 1.
 * @param block (in)	the name of the test block
 * @param frm_rc (inout)	the status variable (increased by 1 upon the exit)
 */

public String FRM_DRV_handle_unimplemented_block( in block, inout frm_rc ) {}

/**
 * Formats the report for unknown test blocks. Note that this function
 * increases the inout variable <code>frm_rc</code> by 1.
 * @param block (in)	the name of the test block
 * @param frm_rc (inout)	the status variable (increased by 1 upon the exit)
 */

public String FRM_DRV_handle_unknown_block( in block, inout frm_rc ) {}

/**
 * Formats the report for procesed test blocks. Note that this function
 * increases the inout variable <code>frm_rc</code> by 1 in case of <code>rc!=0</code>.
 * @param block (in)	the name of the test block
 * @param test (in)	the name of the test case
 * @param rc (in)	status returned by the processed test block
 * @param frm_rc (in)	the current value of the status variable
 * @return 0 to continue with the test case, 1 to stop the execution of the test case
 */

public String FRM_DRV_handle_processed_block( in block, in test, in rc, inout frm_rc ) {}

/**
 * Loads driver libs.
 * <p>NOTE1<br>
 *	Each invocation of LINK|LINA|LINX instruction will cause a new "reload"
 *	of the specified driver library. This is necessary in order to ensure
 *	proper loading when multiple divers are used (big projects tend to use
 *	this strategy). If you want to skeep such unecessary reloading, you may
 *	remove the name of the driver from the LINK commands wherever you are
 *	sure that the proper driver has already been loaded, for example
 *	<pre>
 *		LINK~drv/xxx~~1_1
 *		LINK~~~1_2
 *		LINK~~~1_3
 *	</pre>
 * <p>NOTE2<br>
 *	For compatibility reasons with older projects you should name your new
 *	driver libraries using the "_lib" suffix. If you preserve the rest of
 *	the driver name (e.g. if "DRV/xxx" is changed to "DRV/xxx_lib", then you 
 *	need not modify anything in your Excel tables because this routine will
 *	automatically append "_lib" to all names that do not use this suffix.
 */

protected String load_drv_lib( in lib, in p1, in p2 ) {}

/**
 * Executes several test sets as defined by the parameter <code>callArr[]</code>.
 * <p>NOTE1<br>
 *	Input array must be a two-dimensional array. First dimension must be sequentially
 *  indexed from 0 onwards. The second dimension must be sequentially numbered from
 *  0 onwards where 0 holds "test driver name", 1 hodls "test table name", and 2 holds
 *	"test set name". The easiest way to initialise such array in the callong script
 *	is via the following construct:
 *	<pre>
 *		static callArr[] = {
 *			 { "drv/main", "table1.xls", "1-5" }
 *			,{ "drv/main", "table2.xls", "1-3" }
 *			,{ "drv/xxxx", "table2.xls", "3-9" }
 *		};
 *	</pre>
 * @param callArr (inout) array contianing test set instructions (see above)
 * @return E_OK if success else error
 */

public String FRM_DRV_execute_set_array( inout callArr [] ) {}

}
