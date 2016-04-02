package frm;
import mode.*;
import stereotype.*;

/**
 * The library containing routines that enable EMOS_FRM_driver to retry failed 
 * tests. This is implemented by writing an execution log in a temporary file. 
 * If the retry mode had been chosen (offered only in interactive mode), this 
 * file is analysed by the succeeding run. If a failed or uncompleeted test is
 * detected, this test is reinvoked. All other tests are ignored.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_driver_retry_lib_tsl.html" target=tsl>emos_frm_driver_retry_lib.tsl</A>
*/

public class emos_frm_driver_retry_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_driver_retry_lib (){}
/**
 * Use this function to enable the retry capability. You should either enable
 * retry generally for all test suites (i.e. the best place to call it is
 * the startup test). Otherwise you can enable/disable retry for each individual
 * test suite (make sure to call enable/disable in ALL your kickoff tests).
 */

public String FRM_DRV_enable_retry(  ) {}

/**
 * Use this function to disable retry capability.
 */

public String FRM_DRV_disable_retry(  ) {}

/**
 * Indicates wheter a retry is possible which is true if retry is generally
 * enabled, test is running in interactive mode and there is an old log file
 * that could govern the retry.
 *@return TRUE: can attempt to retry, FALSE: a "normal" run will be attempted
 */

public String FRM_DRV_can_retry(  ) {}

/**
 * Invokes a dialog which asks the user whether to retry the last run.
 * An internal flag is set to indicate whether to retry or not.
 */

public String FRM_DRV_ask_retry(  ) {}

/**
 * Indicates whether retry mode is active or not.
 */

public String FRM_DRV_is_retry(  ) {}

/**
 * You can define your own separator for entries in the log file [default=TAB].
 */

public String FRM_DRV_retry_set_log_sep( in sep ) {}

/**
 * Returns the log separator.
 *@return the log separator
 */

public String FRM_DRV_retry_get_log_sep(  ) {}

/**
 * Defines the name of the log file [default=
 * &lt;WrTmpDir&gt;\\&lt;testName&gt;_&lt;resName&gt;.log].
 *@param file (in)	full path name of the log file (optional)
 */

public String FRM_DRV_retry_set_log_file_name( in file ) {}

/**
 * Retrieves the name of the active log file.
 *@return log file name
 */

public String FRM_DRV_retry_get_log_file_name(  ) {}

/**
 * Loads the log file into an internal array tho provide for a quick lookup.
 *@return E_OK: load successful else failure
 */

public String FRM_DRV_retry_load_log_table(  ) {}

/**
 * Retrieves an log entry. Empty stringis returned if entry is not found.
 *@return the entry or empty string if no entry found
 */

public String FRM_DRV_retry_lookup_log( in table, in test ) {}

/**
 * Appends the first part of the log entry. This part contains all information
 * about a single test that is attempted with the exception of the return
 * code returned by this test.
 *@param table (in) name of the test table
 *@param test (in) name of the test
 *@return E_OK: success else failure 
 */

public String FRM_DRV_retry_log_test1( in table, in test ) {}

/**
 * Appends the second part of the log entry for the particular test. The second
 * part contains the return code received from the test.
 *@param test_rc (in) the rc returned by the test
 *@return E_OK: success, else failure
 */

public String FRM_DRV_retry_log_test2( in test_rc ) {}

}
