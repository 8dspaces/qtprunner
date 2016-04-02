package frm;
import mode.*;
import stereotype.*;

/**
 * This library provides the low-level interface to data tables. You can use the
 * function from this library as replacement to standard ddt-functions even if
 * you don't need other nice FRM feaures.<p>
 * Here we implement our own ddt-interface and add plenty of other functions which
 * make this interface very powerful. This library maintains a set of internal
 * buffers which hold the table data and numerous other information. One of the
 * "side-effects" (but since WR 6.0 one of the most important features) is the
 * ability to use the same table from more than one main test within the same 
 * chain.<p>
 * The most important feature of this interface is that you may also index table
 * rows by some unique name (very much the same as you index columns by defining
 * their names in the first row). For this to work the table MUST contain two
 * columns named "IDX" and "Name". To index a row you need to define some name
 * in the Name-column and place an "x" in the IDX-column.<p>
 * For example imagine a table "Tab.xls" with the following content:
 * <table border>
 *  <tr> <th>IDX</th> <th>Name</th><th>a</th><th>b</th><th>c</th><th>d</th> </tr>
 *  <tr> <td>x</td> <td>Block1</td> <td>1</td> <td>2</td> <td>3</td> <td>4</td> </tr>
 *  <tr> <td>&#160;</td> <td>1</td> <td>1x1</td> <td>1x2</td> <td>1x3</td> <td>1x4</td> </tr>
 *  <tr> <td>&#160;</td> <td>2</td> <td>1x1</td> <td>2x2</td> <td>3x3</td> <td>4x4</td> </tr>
 *  <tr> <td>x</td> <td>Block2</td> <td>x</td> <td>y</td> <td>z</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>a</td> <td>ax</td> <td>ay</td> <td>az</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>b</td> <td>bx</td> <td>by</td> <td>bz</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>c</td> <td>cx</td> <td>cy</td> <td>cz</td> <td>&#160;</td> </tr>
 * </table>
 * You could use functions such as<p>
 * <code>FRM_get_cell( "Tab.xls", "b", "Block1", val );</code><p>
 * to retreive <code>"2"</code> in variable <code>val</code> no matter what row
 * used to be active before.<p>
 * From then on you could use<p>
 * <code>FRM_get_next( "Tab.xls", "b", val );</code><p>
 * to retreive <code>"1x2"</code> in variable <code>val</code> and so on.
 *<p> PUBLIC VARIABLES
 *<ul>
 * <li><b>FRM_UNKNOWN</b> Indicates an unknown value.</li>
 * <li><b>E_FRM_SKIP</b> Indicates an empty table cell.</li>
 * <li><b>FRM_SET_MODE</b> Indicates the SET modus (test data is to be entered).</li>
 * <li><b>FRM_CHK_MODE</b> Indicates the CHECK modus (test data is to be verified).</li>
 * <li><b>FRM_GEN_MODE</b> Indicates the GENERATE modus (test data is to be generated).</li>
 * <li><b>FRM_COL_IDX</b> The default title of the INDEX column.</li>
 * <li><b>FRM_COL_NAME</b> The default title of the NAME column.</li>
 *</ul>
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_lib_tsl.html" target=tsl>emos_frm_lib.tsl</A>
*/

public class emos_frm_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_lib (){}
/**
 * Opens a data lt;table&gt; and loads the desired &lt;test&gt;s (columns).
 * <p>NOTE<p>
 *	This routine returns the <code>tid</code> (table ID). Unlike the ddt_
 *	you have to use this id when calling other FRM functions.
 *
 *@param table	(in)	name of the data table
 *@param tests	(in)	list of columns (comma-separated)
 *@param tid	(out)	table ID
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_open( in table, in tests, out tid, in nameSep ) {}

/**
 * Loads an individual column (test) into an already opened data table.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	column name to be loaded
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_load_test( in tid, in test ) {}

/**
 * Saves the given table.
 * <p>NOTE!
 * This funtion only works with WR 6.0 or higher.
 *
 *@param tid	(in)	table ID
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_save( in tid ) {}

/**
 * Closes the given table and frees occupied memory.
 * <p>NOTE!
 * If table was modified, it is automatically saved before closing.
 *
 *@param tid	(in)	table ID
 *@param drop	(in)	(optional) TRUE: do not save changes (default: FALSE)
 */

public String FRM_close( in tid, in drop ) {}

/**
 * Closes all open tables. By default it automatically saves the changes.
 * You can override this by setting &lt;drop&gt; to TRUE.
 *
 *@param drop	(in)	(optional) TRUE: do not save changes (default: FALSE)
 */

public String FRM_close_all( in drop ) {}

/**
 * Closes all open tables except the specified ones. 
 * By default it automatically saves the changes.
 * You can override this by setting &lt;drop&gt; to TRUE.
 *
 *@param xtabs[]	(inout)	array of table names which are NOT to be closed
 *@param drop	(in)	(optional) TRUE: do not save changes (default: FALSE)
 */

public String FRM_close_all_except( inout xtabs [], in drop ) {}

/**
 * Reports whether a given table is open or not.
 *
 *@param table	(in)	table name
 *@return
 *	TRUE:	table is open
 *	FALSE:	table is not open
 */

public String FRM_is_table_open( in table, out tid ) {}

/**
 * Reports whether a given table is open or not.
 *
 *@param tid	(in)	table ID
 *@return
 *	TRUE:	table is open
 *	FALSE:	table is not open
 */

public String FRM_is_open( in tid ) {}

/**
 * Returns the table ID of the corresponding table or empty string ("") if
 * table unknown.
 *
 *@param table	(in)	table name
 *@return table ID orempty string
 */

public String FRM_get_tid( in table ) {}

/**
 * Returns the table name of the corresponding tid or empty string ("") if
 * tid unknown. If sheet name was specified with FRM_open(),
 * then the name returned includes the sheet name (e.g. c:\tab.xsl#sheet1).
 *
 *@param tid	(in)	table ID
 *@return table name (including sheet name) or empty string
 */

public String FRM_get_name( in tid ) {}

/**
 * Returns the table name of the corresponding tid or empty string ("") if
 * tid unknown. This function ensures that the sheet name is NOT returned.
 * For example if FRM_open("c:\tab.xsl#sheet1") was specified, then
 * only "c:\tab.xsl" is returned.
 *
 *@param tid	(in)	table ID
 *@return table name (not including sheet name) or empty string
 */

public String FRM_get_filename( in tid ) {}

/**
 *	Reports whether a given table is modified or not.
 *
 *@param tid	(in)	table ID
 *@return
 *	TRUE:	table is modified
 *	FALSE:	table is not modified
 */

public String FRM_is_modified( in tid ) {}

/**
 * Positions the focus to the row specified by its name &lt;idx&gt;. If successful,
 * the &lt;row&gt; number is returned.
 * <p> NOTE!
 * The row must be indexed.
 *
 *@param tid	(in)	table ID
 *@param idx	(in)	row name/index
 *@param row	(out)	row number
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_idx( in tid, in idx, out row ) {}

/**
 * Loads a library &lt;lib&gt; in scope of the specified &lt;tid&gt;.
 * <p>NOTE!
 * If table is closed, all libraries in its scope are automatically unloaded.
 *
 *@param tid	(in)	table ID
 *@param lib	(in)	library name (full path)
 *@param p1	(in)	(optional) first parameter to underlying reload statement
 *@param p2	(in)	(optional) second parameter to underlying reload statement
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_load_lib( in tid, in lib, in p1, in p2 ) {}

/**
 * Loads a GUI map &lt;gui&gt; in scope of the specified &lt;tid&gt;.
 * <p>NOTE!
 * If table is closed, all GUI maps in its scope are automatically unloaded.
 *
 *@param tid	(in)	table ID
 *@param gui	(in)	GUI map name (full path)
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_load_gui( in tid, in gui ) {}

/**
 * Allocates a new or overwrites an existing variable &lt;var&gt; 
 * with the initial value &lt;val&gt;. A scope of the variable depends on the
 * given &lt;tid&gt; name. 
 * <p>NOTE!
 * If it equals to "" the scope of the variable is is Frame-global.
 * Otherwise the variable has table-scope. If table is closed, all variables in 
 * its scope are automatically unloaded. Global varables are unloaded only with
 * FRM_close_all().
 *
 *@param tid	(in)	table ID (scope) or "" for global variables
 *@param var	(in)	variable name
 *@param val	(in)	value to be saved
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_setvar( in tid, in var, in val ) {}

/**
 * Retrieves the value of a given FRM variable &lt;var&gt;. 
 * A scope of the variable depends on the given &lt;tid&gt; name. 
 * <p>NOTE!
 * If it equals to "" the scope of the variable is is Frame-global.
 * Otherwise the variable has table-scope. If table is closed, all variables in 
 * its scope are automatically unloaded. Global varables are unloaded only with
 * FRM_close_all().
 *
 *@param tid	(in)	table ID (scope) or "" for global variables
 *@param var	(in)	variable name
 *@param val	(out)	value retrieved
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_getvar( in tid, in var, out val ) {}

/**
 * Returns the active frame mode.
 *
 *@param tid	(in)	table ID
 *@return 
 *	currently active mode (FRM_SET_MODE/FRM_CHK_MODE/FRM_ATR_MODE/FRM_GEN_MODE) or
 *	E_FILE_NOT_OPEN: table not open
 */

public String FRM_get_mode( in tid ) {}

/**
 * Sets the active frame mode.
 *
 *@param tid	(in)	table ID
 *@param mode	(in)	either FRM_SET_MODE, FRM_CHK_MODE, FRM_ATR_MODE or FRM_GEN_MODE
 *@return
 *	E_OK:					mode successfully set
 *	E_FILE_NOT_OPEN:		table not open
 *	E_ILLEGAL_PARAMETER:	invalid mode (mode unchanged)
 */

public String FRM_set_mode( in tid, in mode ) {}

/**
 * Convert the given <code>mode</code> from one representation to another
 * (string2code and code2string). If none of the conversion succeeds, the
 * the unchanged value is returned.
 * @param mode (in)	mode to be converted (either format)
 * @return converted mode or unchanged value if no conversion possible
 */

public String FRM_convert_mode( in mode ) {}

/**
 * Indicates whether the cell in the next row contains data or not. 
 *
 *@param tid	(in)	table ID
 *@param test	(in)	test (column) name
 *@return
 *	TRUE:	next row contains data
 *	FALSE:	next row contains no data
 */

public String FRM_has_next( in tid, in test ) {}

/**
 * Returns the content of the cell from the given column (<code>test</code>) 
 * and the row following the currently active row.
 * <p>
 * A cell which is supposed to be ignored (an empy cell, a cell that contains no
 * data), is indicated with E_FRM_SKIP. If an empty string ("") needs to be used
 * as test data, then the cell must contain one of the following two strings 
 * &lt;&lt;cler&gt;&gt; or &lt;&lt;leer&gt;&gt; (including angle brackets).
 * <p>NOTE!
 * This routine modifies the currently active row. It does this before fetching
 * the value. If an attemp is made to fatch the value beyond the last row,
 * E_OUT_OF_RANGE is returned.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	test (column) name
 *@param val	(out)	the fetched value
 *@return
 *	E_OK:			operation successful, value returned
 *	E_FRM_SKIP:		operation successful, no value returned
 *	anything else:	operation failed
 */

public String FRM_get_next( in tid, in test, out val ) {}

/**
 * Returns the content of the cell specified by the given row (<code>idx</code>) 
 * and column (<code>test</code>) names.
 * <p>
 * A cell which is supposed to be ignored (an empy cell, a cell that contains no
 * data), is indicated with E_FRM_SKIP. If an empty string ("") needs to be used
 * as test data, then the cell must contain one of the following two strings 
 * &lt;&lt;cler&gt;&gt; or &lt;&lt;leer&gt;&gt; (including angle brackets).
 * <p>NOTE!
 * For this to work the &lt;idx&gt; must be the content of some row in 
 * FRM_COL_NAME marked with "x" in FRM_COL_IDX.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	test (column) name
 *@param idx	(in)	row name (index)
 *@param val	(out)	the fetched value
 *@return
 *	E_OK:			operation successful, value returned
 *	E_FRM_SKIP:		operation successful, no value returned
 *	anything else:	operation failed
 */

public String FRM_get_cell( in tid, in test, in idx, out val ) {}

/**
 * Returns the content of the cell specified by the given column name and,
 * optionally, with the row number.
 * <p>
 * A cell which is supposed to be ignored (an empy cell, a cell that contains no
 * data), is indicated with E_FRM_SKIP. If an empty string ("") needs to be used
 * as test data, then the cell must contain one of the following two strings 
 * &lt;&lt;cler&gt;&gt; or &lt;&lt;leer&gt;&gt; (including angle brackets).
 * <p>NOTE!
 * If <code>row</code> is not defined, the current row is used.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	test (column) name
 *@param val	(out)	the fetched value
 *@param row	(in)	(optional) row number (default: current row)
 *@return
 *	E_OK:			operation successful, value returned
 *	E_FRM_SKIP:		operation successful, no value returned
 *	anything else:	operation failed
 */

public String FRM_get( in tid, in test, out val, in row ) {}

/**
 * Parses the given value and substitutes FRM keywords.
 * If val is empty, then E_FRM_SKIP is returned and val is left unchanged.
 * @param tid	(in)	table ID
 * @param val	(inout) value to be parsed; eventually modifiedd by parsing
 * @return E_OK + val in case of succesful parsing; E_FRM_SKIP in case of
 * empty val
 */

public String FRM_parse_val( in tid, inout val ) {}

/**
 * Saves the given value (<code>val</code>) in the cell specified by the column
 * name (<code>test</code>) and, optinally, the <code>row</code> number.
 * <p>NOTE!
 * The change is not made permanent yet. You must call FRM_save() in order
 * to transfer your changes back to the Excel table.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	test name (column)
 *@param val	(in)	value to be saved
 *@param row	(in)	(optional) row number (default: current row)
 *@return
 *E_OK:		operation successful
 *!E_OK:		operation failed
 */

public String FRM_set( in tid, in test, in val, in row ) {}

/**
 * Returns a two-dimensional block from the table as array. 
 * <p>
 * To define a block in the test table you must format it in a special way.
 * An example of one such table follows.
 * <table border>
 *  <tr> <th>IDX</th> <th>Name</th><th>a</th><th>b</th><th>c</th><th>d</th> </tr>
 *  <tr> <td>x</td> <td>Block1</td> <td>1</td> <td>2</td> <td>3</td> <td>4</td> </tr>
 *  <tr> <td>&#160;</td> <td>1</td> <td>1x1</td> <td>1x2</td> <td>1x3</td> <td>1x4</td> </tr>
 *  <tr> <td>&#160;</td> <td>2</td> <td>1x1</td> <td>2x2</td> <td>3x3</td> <td>4x4</td> </tr>
 *  <tr> <td>&#160;</td> <td>&lt;&lt;END&gt;&gt</td> <td>&#160;</td> <td>&#160;</td> <td>&#160;</td> <td></td> </tr>
 *  <tr> <td>x</td> <td>Block2</td> <td>x</td> <td>y</td> <td>z</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>a</td> <td>ax</td> <td>ay</td> <td>az</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>b</td> <td>bx</td> <td>by</td> <td>bz</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>c</td> <td>cx</td> <td>cy</td> <td>cz</td> <td>&#160;</td> </tr>
 *  <tr> <td>&#160;</td> <td>&lt;&lt;END&gt;&gt</td> <td>&#160;</td> <td>&#160;</td> <td>&#160;</td> <td>&#160;</td> </tr>
 * </table>
 * 
 * 
 *@param tid	(in)	table ID
 *@param idx	(in)	block index
 *@param x	(out)	nomber of columns
 *@param y	(out)	number of rows
 *@param arr[]	(out)	values
 *@return
 *	E_OK:	operation succesfull, values returned
 *	!E_OK:	operation failed
 */

public String FRM_get_block( in tid, in idx, out x, out y, out arr [] ) {}

/**
 * Executes the content of the next cell.
 *
 *@param tid	(in)	table ID
 *@param rows	(in)	column name
 */

public String FRM_exec( in tid, in test ) {}

/**
 * Skips the given number of rows. 
 * <p>NOTE!
 * If <code>rows</code> is not given, one row is skipped. Therefore 
 * <code>FRM_skip( tab );</code> is equivalent to <code>FRM_skip( tab, 1 );</code>.
 * <code>FRM_skip( tab, 0 );</code> also skips to next row because 0 is taken as
 * the equivalent for "not given".
 * <p>
 * If <code>rows</code> is negative, the function skips backwards. Therefore 
 * <code>FRM_skip( tab, -3 );</code> jumps for example from row 15 to row 12.
 *
 *@param tid	(in)	table ID
 *@param rows	(in)	(optional) number of rows to skip, negative skips backwards
 *@return
 *	E_OK:	success
 *	E_OUT_OF_RANGE:	skip beyond table bounaries attempted
 *	!anything else:	other failure
 */

public String FRM_skip( in tid, in rows ) {}

protected String FRM_SET_skip( in tid, in rows ) {}

protected String FRM_GEN_skip( in tid, in rows ) {}

/**
 * Performs the initialisation for the given test block.
 * <p>NOTE<br>
 * Important change since version 6.02.01.00: mode sent as parameter to
 * this function takes presedence over the mode of the test block header.
 *
 *@param tid	(in)	table ID
 *@param test	(in)	column name
 *@param idx	(in)	block name (row index)
 *@param mode	(in)	(optional) frame mode (default: FRM_SET_MODE)
 *@return
 *	E_OK:		success
 *	E_FRM_SKIP:	block schould be ignored (valid only for FRM_GEN_MODE)
 *	E_FRM_INIT_BLOCK:	idx not found
 *  E_FRM_ILLEGAL_PARAMETER: idx found but mode could not be set
 */

public String FRM_init_block( in tid, in test, in idx, inout mode ) {}

/**
 * In case of &lt;rc&gt; != E_OK this function formats an <code>tl_step</code>-message
 * from the given parameters. It automatically determines the current row number
 * in the data table.
 *
 *@param rc	(in)	return code to be evaluated
 *@param obj	(in)	logical name of the affected GUI-object
 *@param tid	(in)	tid of the affected data table
 *@param test	(in)	name of the test case from &lt;tid&gt;
 *@param val	(in)	content of the affected cell from &lt;tid&gt;
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String FRM_rc( in rc, in func, in tid, in test, in val, in obj ) {}

/**
 * In case of &lt;rc&gt; != E_OK this function formats an <code>tl_step</code>-message
 * from the given parameters. It automatically determines the current row number
 * in the data table.
 *
 *@param rc	(in)	return code to be evaluated
 *@param msg	(in)	message content
 *@param title	(in)	[optional] message title (default: "MSG")
 *@return echoed input variable <code>rc</code>
 */

public String FRM_rc2( in rc, in msg, in title ) {}

public String FRM_log_frm_info( in tid, in test, in val ) {}

public String FRM_log_obj_info( in obj ) {}

/**
 * Dumps the debug info for the 1 level deeper in the calling chain.
 */

public String FRM_log_short_call_chain( in level ) {}

/**
 * Dumps the debug info for the calling chain starting with <code>depth_offset</code>.
 * @param depth_offset (in) depth of the call chain to be dumped (0 = full chain, 1 = from the caller on, etc.)
 */

public String FRM_log_full_call_chain( in depth_offset ) {}

/**
 * This function was used for generating test data in GEN mode. It appends the
 * test data into a file named "gen.txt" located in the WR temp directory.
 * <p>NOTE!
 * Since implementing the save capability for FRM-tables (effectively modifying
 * test date in place) this function is not used any more.
 *
 *@param tid	(in)	table ID
 *@param gui_obj (in)	name of the affected GUI object
 *@param value	(in)	value contained in that object (test data)
 *@param rc	(in)	return code received by retreiving the test date (if not
 * E_OK, then a special message is formatted instead of the test data).
 */

public String FRM_GEN_print( in tid, in gui_obj, in val, in rc ) {}

/**
 *#???#
 *
 *#???#
 *@return
 *#???#
 */

public String FRM_GEN_set( in tid, in test, in gui_obj, in val, in rc ) {}

/**
 * Indicates whether a given &lt;row&gt; exists in the given &lt;tid&gt;.
 *
 *@param tid	(in)	table ID
 *@param row (in)	row number to be evaluated
 *@return
 *	E_OK: row number is valid
 *	E_OUT_OF_RANGE: row number is invalid
 *	else:	other error
 */

public String FRM_is_row( in tid, in row ) {}

/**
 *Returns whether a parameter in a data table is valid.
 *
 *@param tid	(in)	table ID
 *@param param (in)	The parameter name to check in the data table.
 *@return
 *	E_OK:	parameter is valid
 *	E_NOT_PARAMETER:	parameter is invalid
 *	else: other error
 */

public String FRM_is_parameter( in tid, in param ) {}

/**
 * Returns a list of all loaded parameters in a FRM data table.
 * <p>NOTE!<p>
 * Does NOT return columns <code>IDX</code> and <code>name</code> because they
 * are always there. For this reason the number returned is the number of all 
 * (loaded) parameters minus 2.
 *
 *@param tid	(in)	table ID
 *@param params (out)	column titles (tab-separated)
 *@param count (out)	nomber of loaded columns (excluding IDX and Name)
 *@return
 *	E_OK:	success; <code>params</code> and <code>count</code> provided
 *	!E_OK:	faliure; <code>params</code> and <code>count</code> not provided
 */

public String FRM_get_parameters( in tid, out params, out count ) {}

/**
 * Retrieves the active row of a data table.
 *
 *@param tid	(in)	table ID
 *@param row (out)	number of the active row (starting with 1)
 *@return
 *	E_OK:	success; active row returned
 *	!E_OK:	failure; active row not returned
 */

public String FRM_get_current_row( in tid, out row ) {}

/**
 * Retrieves the number of rows in a data table.
 *
 *@param tid	(in)	table ID
 *@param count (out)	number of rows in the data table
 *@return
 *	E_OK:	success; &lt;count&gt; returned
 *	!E_OK:	failure; &lt;count&gt; not returned
 */

public String FRM_get_row_count( in tid, out count ) {}

/**
 * Sets the active row in a data table.
 *
 *@param tid	(in)	table ID
 *@param row (in)	row to be set
 *@return
 *	E_OK:	success; &lt;row&gt; set
 *	!E_OK:	failure; &lt;row&gt; not set
 */

public String FRM_set_row( in tid, in row ) {}

/**
 * Changes the active row in a data table to the next row.
 *
 *@param tid	(in)	table ID
 *@return
 *	E_OK:	success; active row changed
 *	!E_OK:	failure; active row not changed
 */

public String FRM_next_row( in tid ) {}

/**
 * Returns the value of a parameter in the active row in a data table.
 *
 *@param tid	(in)	table ID
 *@param param	(in)	column name
 *@return
 *	E_NOT_PARAMETER:	failure; invalid parameter (column) name
 *	else:	value of the given cell
 */

public String FRM_val( in tid, in param ) {}

/**
 * Sets a value in the current row of the data table.
 *
 *@param tid	(in)	table ID
 *@param val	(in)	value to be set
 *@param param	(in)	column name
 *@return
 *	E_OK:	success; <code>val</code> set
 *	E_NOT_PARAMETER:	failure; invalid parameter (column) name
 *	else:	other error
 */

public String FRM_set_val( in tid, in param, in val ) {}

/**
 * Returns the value of a parameter in the specified row in a data table.
 *
 *@param tid	(in)	table ID
 *@param row	(in)	row number
 *@param param	(in)	column name
 *@return
 *	E_NOT_PARAMETER:	failure; invalid parameter (column) name
 *	else:	value of the given cell
 */

public String FRM_val_by_row( in tid, in row, in param ) {}

/**
 * Sets a value in a specified row of the data table.
 *
 *@param tid	(in)	table ID
 *@param row	(in)	row number
 *@param param	(in)	column name
 *@param val	(in)	the value to be set
 *@return
 *	E_OK:	success; <code>val</code> set
 *	E_NOT_PARAMETER:	failure; invalid parameter (column) name
 *	else:	other error
 */

public String FRM_set_val_by_row( in tid, in row, in param, in val ) {}

/**
 * Builds the internal data structure for (loaded) parameters (columns).
 *
 *@param tid (in) table ID
 *@param tests (in)	list of tests (columns/parameters) to be loaded
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

protected String build_params( in tid, in tests ) {}

/**
 * Builds the internal index for marked rows. You can mark a row by placing an
 * "x" in the IDX-column and defining the (uniquie!) name in the Name-column.
 *
 *@param tid (in) table ID
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

protected String build_idx( in tid ) {}

/**
 * Loads a given FRM-column from a data table. 
 *
 *@param tid	(in)	table ID
 *@param par	(in)	column name
 *@param rows	(in)	number of rows in a table (performance)
 *@return
 *	E_OK:		operation succesfull
 *	!E_OK:		operation failed
 */

protected String load_column( in tid, in par, in rows ) {}

/**
 * Saves the FRM-column back to data table. 
 *
 *@param tid	(in)	table ID
 *@param par	(in)	column name
 *@param rows	(in)	number of rows in a table (performance)
 *@return
 *E_OK:		operation succesfull
 *!E_OK:		operation failed
 */

protected String save_column( in tid, in par, in rows ) {}

}
