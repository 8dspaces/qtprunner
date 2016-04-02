package frm;
import mode.*;
import stereotype.*;

/**
 * Defines an interface for efficient creation of test cases unsing the FRM
 * data tables.<p>
 * A typical FRM test case defines its test data across columns. This is the 
 * plain oposite of the approach taken by Mercury and many other testers. The
 * benefit of defining test data column-wise is the ability to define many,
 * very many (up to 64k) test entries which makes very complex tests possible
 * (accross rows the limit is 256) which are comparatively easy to maintain
 * (no ugly right-left scrolling, you can see much more test data at once).
 * Additionally, you can (hypothetically) "pack" up to 254 of such complex tests
 * in a single Excel-file wich can greatly reduce the mess on your hard drive.<p>
 * A single test case contains three important parts:
 * <ul>
 * <li><code>name</code> content of the cell in the first row</li>
 * <li><code>sequence</code> a single cell containing the list of test steps
 * which are to be executed in a sequence</li>
 * <li><code>step(s)</code> an indexed block of rows containing test data</li>
 * </ul>
 * To keep things simple imagine a part of some application with two windows.
 * First window contains the list of user names and three buttons: New, Edit, Delete.
 * Imagine a table "User1.xls" with the following content:
 * <table border>
 *  <tr> <th>IDX</th> <th>Name</th><th>1</th><th>2</th><th>3</th>  </tr>
 *  <tr> <td>x</td> <th>Testsequence</th> <td><pre>select_user
 *user_data</pre></td> <td><pre>select_user
 *user_data</pre></td> <td>select_user</td> </tr>
 *  <tr> <td>x</td> <th>select_user</th> <th>&#160;</th> <th>&#160;</th> <th>&#160;</th>  </tr>
 *  <tr> <td>&#160;</td> <td>user list</td> <td></td> <td>dean</td> <td>dean</td> </tr>
 *  <tr> <td>&#160;</td> <td>New/Edit/Delete</td> <td>New</td> <td>Edit</td> <td>Delete</td> </tr>
 *  <tr> <td>&#160;</td> <td>delete? (OK/Cancel)</td> <td>&#160;</td> <td>&#160;</td> <td>OK</td> </tr>
 *  <tr> <td>x</td> <th>user_data</th> <th>&#160;</th> <th>CHK</th> <th>&#160;</th> </tr>
 *  <tr> <td>&#160;</td> <td>first name</td> <td>dean</td> <td>dean</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>last name</td> <td>rajovic</td> <td>rajovic</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>OK/Cancel</td> <td>OK</td> <td>Cancel</td> <td>&#160;</td> </tr>
 * </table>
 * This table contains three independent test cases named 1, 2 and 3.
 * Test 1 creates a new user....
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_stp_lib_tsl.html" target=tsl>emos_frm_stp_lib.tsl</A>
*/

public class emos_frm_stp_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_stp_lib (){}
/**
 * Initialises the step iterator.
 * @param tid (in)	table ID
 * @param test (in)	column name
 * @param idx (in)	(optional) index (i.e. table row) containing test steps [default: "Testvorgang"]
 * @return
 *	E_OK:	success
 *	E_NOT_FOUND:	no steps found (idx missing)
 *	else:	other error
 */

public String FRM_STP_init_steps( in tid, in test, in idx ) {}

/**
 * Loads the content of a single table cell (steps separated by newline char)
 * into the internal step table.
 */

protected String FRM_STP_load_steps( in tid, in test, in idx, in val ) {}

/**
 * Frees all references to the specified test.
 * @param tid (in)	table ID
 * @param test (in)	column name
 */

public String FRM_STP_clear_steps( in tid, in test ) {}

/**
 * Indicates whether there are steps to execute.
 * @param tid (in)	table ID
 * @param test (in)	column name
 * @return
 *	TRUE:	there are more steps; use FRM_STP_get_next_step() to get the next one
 *	FALSE:	all steps have been retrieved
 */

public String FRM_STP_has_more_steps( in tid, in test ) {}

/**
 * Returns the next step. Note that the <b>implicit steps</b> are never returned
 * with this command. They are simply executed by this function. The implicit
 * steps are:
 * <ul>
 * <li><code>LINK</code> executes a test in this or some other data table, 
 * the test is atomatically loaded if necessary</li>
 * <li><code>LINA</code> same as LINK while loading ALL tests from the specified
 * table (sometimes impoves the overal performance)</li>
 * <li><code>LINX</code> executes a specified test from an inverted data table
 * (table in which tests are organised/indexed horizontally)</li>
 * <li><code>CALL</code> invokes an arbitrary WinRunner main test</li>
 * <li><code>EVAL</code> invokes an arbitrary WinRunner function</li>
 * <li><code>EXEC</code> evaluates a block of WinRunner functions</li>
 * <li><code>#</code> any step starting with # is treated as a comment</li>
 * <li>obsolete syntax is still valid</li>
 * <li><code><b>LNK:</b>script_name</code> links this test with another one 
 * (loading only the specified tests)</li>
 * <li><code><b>LNA:</b>script_name</code> links this test with another one 
 * (loading all tests in the referenced table)</li>
 * <li><code><b>EXE:</b>test step</code> exectutes all rows in the specified test block</li>
 * <li><code><b>###:</b>test step</code> comments out the given test step</li>
 * <li><code>&lt;&lt;PAUSE&gt;&gt;</code> pauses the test execution in interactive mode</li>
 * </ul>
 * @param tid (in)	table ID
 * @param test (in)	column name
 * @param step (out)	name (idx) of the test step
 * @param mode (out)	mode to be applied ( FRM_SET_MODE/FRM_CHK_MODE/FRM_GEN_MODE )
 * @return
 *	E_OK:	success; test step defined
 *	E_FILE_EOF:	no steps to be retreived
 *	else:	failure
 */

public String FRM_STP_get_next_step( in tid, in test, out step, out mode ) {}

/**
 * Parses the call to another FRM test, i.e. call driver( table, test ).
 * SYNTAX:
 *	<code>LINK&lt;SEP&gt;[driver]&lt;SEP&gt;[table]&lt;SEP&gt;test</code>
 * or
 *	<code>LINA&lt;SEP&gt;[driver]&lt;SEP&gt;[table]&lt;SEP&gt;test</code>
 */

protected String FRM_STP_parse_link( in line, in curr_tid, out drv, out tbl, out tst ) {}

/**
 * Executes the link call.
 * SYNTAX:
 *	<code>LINK&lt;SEP&gt;[driver]&lt;SEP&gt;[table]&lt;SEP&gt;test</code>
 * or
 *	<code>LINA&lt;SEP&gt;[driver]&lt;SEP&gt;[table]&lt;SEP&gt;test</code>
 */

protected String FRM_STP_eval_link( in typ, in curr_tid, in drv, in tbl, in tst ) {}

/**
 * Parses the call to an arbitrary test, i.e. call test ( [param]* )
 * SYNTAX:
 *	<code>CALL&lt;SEP&gt;test[&lt;SEP&gt;arg]*</code>
 */

protected String FRM_STP_eval_call( in line ) {}

/**
 * Executes a block of TSL-statements defined in the given block.
 */

protected String FRM_STP_exec( in tid, in test, in idx, in mode ) {}

/**
 * Evaluates an arbitrary command. You can specify multiple commands by separating them
 * with semicolins. The outcome of the eval statement is expected to be passed by the
 * global variable __evalRC (the assignment has to be done by the evalueated code.
 * For example use code like this "__evalRc = some_function();" in order to let
 * framework evaluate your return code. If you don't assign any value to __evalRC,
 * then eval will erturn E_OK.
 * @param cmd (in) command(s) to be executed (it is not necessary to place the
 * semicolon at the end of the last command but you must separate multiple
 * commands with the semicolon, though).
 */

protected String FRM_STP_eval( in cmd ) {}

protected String FRM_STP_eval_old( in cmd ) {}

/**
 * OBSOLETE: KEPT ONLY FOR THE SAKE OF COMPATIBILITY WITH THE OLD DATA TABLES.
 * Indicates an internal step (step containg special command).
 * @param step (in)	the step to be evaluated
 * @param tid (in)	id of the active test table
 * @return
 *	TRUE:	internal
 *	FALSE:	"ordinary" step
 * @deprecated
 */

protected String FRM_STP_is_internal_step( in step, in tid ) {}

/**
 * OBSOLETE: KEPT ONLY FOR THE SAKE OF COMPATIBILITY WITH THE OLD DATA TABLES.
 * Executes another test(s) in the same or some other table(s) running the same
 * or some other tsl script.
 *@deprecated
 */

protected String FRM_STP_link( in tid, in test, in idx, in mode, in load_all ) {}

/**
 * Turns dummy test mode on/off.
 * @param mode (in)	true/false
 * @return the sam as the input parameter mode
 */

public String FRM_STP_set_dummy_step_mode( in mode ) {}

/**
 * Indicates the dummy test mode.
 * @return TRUE: dummy mode on, FALSE: dummy mode off
 */

public String FRM_STP_is_dummy_step_mode(  ) {}

}
