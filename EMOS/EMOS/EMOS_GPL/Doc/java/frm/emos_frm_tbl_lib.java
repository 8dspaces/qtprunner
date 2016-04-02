package frm;
import mode.*;
import stereotype.*;

/**
 * This libary provides a family of functions for support of table GUI objects.
 * GUI objects in table format are known for being difficult to handle for 
 * many reasons. First of all, although a single object a tables can
 * potentialy carry huge amounts of data. Depending on the testing objectives
 * we sometimes need to handle only a handfull of cells and in other cases big
 * portions of it if not the whole table. Programatic interfaces to tables are
 * usually handle individual table cells and the navigation through table. 
 * More complicated actions on larger parts of table data are left to test 
 * programmers to be designed from the basic table operations.<br>
 * Secondly, tables are often very dynamic objects (i.e. their content and their
 * strucure sometimes changes within a single test session). A robust test design 
 * should provide for easy location of data (e.g. dynamcally determining the 
 * appropriate row/column) which is not a trivial task.<br>
 * Finally, individual table cells can in fact contain various sorts of other
 * GUI objects such as edit fields, drop-down lists, combo boxes, radio buttons, 
 * check buttons and other. Sometimes it seems as if vendors of GUI toolkits 
 * simply had to invent something that would distinguish their tables from all
 * the other ones. 
 * <p>
 * WinRunner's tbl interface does a fairly good job in making these various 
 * tables appear similar. However, there are plenty of situations where this is
 * not the case. Here only individual solutions can help. Our table interface
 * builds upon the strengths of WinRunner tbl-interface and is therefore able to
 * handle many different tables. Still, due to mentioned difficulties, the
 * interface is limited in some aspects and will need to be tuned for certain 
 * environments. Our code should work well with HTML tables and with tables
 * with well-working tbl-interface (tbl_set_cell_data/tbl_get_cell_data).
 * We hope also, that our code provides enough design ideas which will help you
 * handle exotic cells such as edit fields, combo-boxes, etc.
 * <p>
 * Our table support has been specially designed for use in EMOS Framework tables.
 * Pay special attention to functions FRM_TBL_set_data() and FRM_TBL_set_data_block().
 * The former implements an interface for handing individual table cells.
 * The later implements an interface for handing bigger portions of tables.
 *
 * @author drajovic
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_tbl_lib_tsl.html" target=tsl>emos_frm_tbl_lib.tsl</A>
*/

public class emos_frm_tbl_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_tbl_lib (){}
/**
 * Performs operations on single table cells for the whole test block (i.e.
 * loops through all cells of a particular test block until an empty cell
 * or the last cell is reached.
 *
 * @param table (in) table index
 * @param test (in) test name
 * @param obj (in) logical name or phisical description of the table object
 * @param desc (in) [optional] physical description of an edit object that is
 *  located in the particular table cell (attempt only if such object really
 *  appears in the cell; we had to fight one tweeked VB table once with this;
 *  hopefulla you'll never need it; see FRM_TBL_cell_aktion() for more)
 * @param list (in) [optional] physical description of a list object that is
 *  located in the particular table cell (attempt only if such object really
 *  appears in the cell, see code of FRM_TBL_cell_aktion function)
 * @return E_OK if successful, else error
 */

public String FRM_TBL_set_data( in table, in test, in obj, in desc, in list ) {}

/**
 * Performs an action(s) on a block of table cells.
 *
 * @param tid1	(in)	id of the table where the instructins come from
 * @param test	(in)	name of the test to run (as named in column "Name")
 * @param obj	(in)	table object where actions are to be performed
 */

public String FRM_TBL_set_data_block( in tid1, in test, in obj ) {}

/**
 * Process 2-dimensional table block
 */

protected String FRM_TBL_process_data_block( in tid, in block, in obj ) {}

/**
 * Function tbl_get_rows_count() sometimes returns count that is less than the
 * physical number of rows. If present, the headings row is typically not counted.
 * So the returned count is one less than the physical count. For our search
 * functions (e.g. FRM_TBL_find_cell()) we need to access the headings row to 
 * search them. Increase the count_adjustment if you notice that our functions
 * do not seem to find the value in the last row.
 * 
 * @param amount	(in)	amount to adjust
 */

public String FRM_TBL_set_rows_count_adjustment( in amount ) {}

/**
 * Function tbl_get_rows_count() sometimes returns count that is less than the
 * physical number of rows. If present, the headings row is typically not counted.
 * So the returned count is one less than the physical count. For our search
 * functions (e.g. FRM_TBL_find_cell()) we need to access the headings row to 
 * search them. Increase the count_adjustment if you notice that our functions
 * do not seem to find the value in the last row.
 * 
 * @return the row adjustment amount
 */

public String FRM_TBL_get_rows_count_adjustment(  ) {}

/**
 * Function tbl_get_cols_count() sometimes returns count that is less than the
 * physical number of columns. If present, the headings column is typically not 
 * counted. So the returned count is one less than the physical count. For our 
 * search functions (e.g. FRM_TBL_find_cell()) we need to access the headings 
 * column to search for them. Increase the count_adjustment if you notice that 
 * our functions do not seem to find the value in the last column.
 * 
 * @param amount	(in)	amount to adjust
 */

public String FRM_TBL_set_cols_count_adjustment( in amount ) {}

/**
 * Function tbl_get_cols_count() sometimes returns count that is less than the
 * physical number of columns. If present, the headings column is typically not 
 * counted. So the returned count is one less than the physical count. For our 
 * search functions (e.g. FRM_TBL_find_cell()) we need to access the headings 
 * column to search for them. Increase the count_adjustment if you notice that 
 * our functions do not seem to find the value in the last column.
 * 
 * @return the row adjustment amount
 */

public String FRM_TBL_get_cols_count_adjustment(  ) {}

/**
 * Performs the action on a table cell as specified by the command <code>cmd</code>.
 * There are four types of actions you can perform on a table cell:
 * <ul>
 *	<li>S = <b>s</b>elect the cell (i.e. tbl_set_selected_cell/tbl_activate_cell)</li>
 *	<li>E = <b>e</b>nter the value in a cell box of type edit (i.e. edit_set)</li>
 *	<li>P = <b>p</b>ick the value from a list (i.e. list_select_item)</li>
 *	<li>T = <b>t</b>ype into the cell (i.e. type)</li>
 *	<li>D = enter <b>d</b>ata using tbl functions such as tbl_set_cell_data() or 
 *		tbl_get_cell_data()</li>
 * </ul>
 *<p>
 * All actions can be specified by a generic syntax:
 * <p>
 * <pre>~action~[row]~[col][~val[~obj[~exact]]]</pre>
 * <p>
 * where<br>
 * <ul>
 * <li>~ = any character that occurs only at specified positions (separator)</li>
 * <li>action = one of the following: S|E|P|T|D, action can be prefixed with . or :
 * (e.g. .S) where . means selection (tbl_set_selected_cell) and : means
 * activation (tbl_activate_cell, i.e. double-click) [default: selection]</li>
 * <li>row = row specification either as 
 *		<ul>
 *		<li>index (e.g. <code>#1</code>)</li>
 *		<li>search expression in form <code>col1=val1[;coln=valn]...</code>
 *			where col1 ist the name of the column containing val1, etc. the
 *			separator ; (semicolon) is interpreted as logical AND</li>
 *		<li><code>&lt;LAST&gt;</code>  in which case the last valid row spec is
 *			reused (no search is performed for preformance reasons)</li>
 *		<li>name (e.g. <code>Par1</code>) it is assumed that col #0 contains row 
 *			names [obsolete; replaced by <code>#0=val</code>] </li>
 *		<li>in case of Selection row can be left empty (assuming col and val are 
 *			filled in) where val is searched for in col #0 [obsolete, replaced
 *			by construct <code>#0=val</code>]</li>
 *		</ul>
 * <li>col = column specification either as
 *		<ul>
 *		<li>index (e.g. <code>#1</code>)</li>
 *		<li>name (e.g. <code>Col1</code>)</li>
 *		<li>search expression in form <code>row1=val1[;rown=valn]...</code>
 *			where row1 ist the name of the row containing val1, etc. the
 *			separator ; (semicolon) is interpreted as logical AND</li>
 *		<li><code>&lt;LAST&gt;</code>  in which case the last valid column spec is
 *			reused (no search is performed for preformance reasons)</li>
 *		<li>in case of Selection col can be left empty (assuming row and val are 
 *			filled in) where val is searched for in row #0 [obsolete, replaced
 *			by construct <code>#0=val</code>]</li>
 *		</ul>
 * <li>val = value which can be interpreted two-fold<br>
 *	in case of Selection val can specify the content of the cell which is to be selected<br>
 *	in other cases val represents the value for the E|P|T|D operations</li>
 * <li>obj = a phisical description of the object to be actioned upon</li>
 * <li>exact = indicats whether exact or tollerant matching [default] is to be performed</li>
 * </ul>
 * <p>
 * A few examples:
 * <ul>
 * <li>~S~#1~#2<br>
 *	selects cell indexed by row=#1, col=#2<br><br></li>
 * <li>~.S~AnzahlH~Wert<br>
 *	selects cell in a row named "AnzahlH" and a column named "Wert"<br><br></li>
 * <li>~:S~~Wert~xxx<br>
 *	activates cell in a column "Wert" that contains "xxx"<br><br></li>
 * <li>~S~#1~~xxx<br>
 *	activates cell in a row #1 that contains "xxx"<br><br></li>
 * <li>~E~#1~Wert~xxx<br>
 *	sets (edit_set) cell in row #1 and column named "Wert" to "xxx"<br><br></li>
 * <li>~E~#1~#2~xxx~{class:edit,index:12}<br>
 *	sets (edit_set) object with the given description that appears in cell #1:#2 to "xxx"<br><br></li>
 * <li>~S~aaa~bbb~~~1<br>
 *	selects cell named exactly "aaa":"bbb"<br><br></li>
 * <li>~.T~#1~#2~abc<br>
 *	types "abc" in cell #1:#2 (after selecting a cell with a single click)<br><br></li>
 * </ul>
 * @param tbl	(in)	table name
 * @param cmd	(in)	command to be performed
 * @param frm_mode (in)	[optional] FRM mode (FRM_SET_MODE|FRM_CHK_MODE|FRM_ATR_MODE|FRM_GEN_MODE)
 * @param desc	(in) [optional] default physical description of the object to be acted upon
 * 		this description is used unless overridden by the cmd,
 *		if you don't provide this argument, "{class:edit}" will be used
 * @param list	(in) [optional] a description or a logical name of the combo box
 * 		containing the obj.
 */

public String FRM_TBL_cell_action( in tbl, in cmd, in frm_mode, in desc, in list ) {}

public String FRM_TBL_CHK_cell_action( in tbl, in cmd, in desc, in list ) {}

public String FRM_TBL_select( in tbl, in row, in col, in simple_select ) {}

public String FRM_TBL_GET_data( in tbl, in row, in col, out val, in simple_select ) {}

public String FRM_TBL_SET_data( in tbl, in row, in col, in val, in simple_select ) {}

public String FRM_TBL_CHK_data( in tbl, in row, in col, in val, in simple_select, in operation ) {}

protected String FRM_TBL_generic_check( in actual, in expected, in operation ) {}

public String FRM_TBL_SET_edit( in tbl, in row, in col, in obj, in val, in simple_select ) {}

public String FRM_TBL_CHK_edit( in tbl, in row, in col, in obj, in val, in simple_select, in exact ) {}

public String FRM_TBL_SET_button( in tbl, in row, in col, in obj, in val, in simple_select ) {}

public String FRM_TBL_CHK_button( in tbl, in row, in col, in obj, in val, in simple_select, in exact ) {}

public String FRM_TBL_SET_pick( in tbl, in row, in col, in obj, in list, in val, in simple_select ) {}

public String FRM_TBL_CHK_pick( in tbl, in row, in col, in obj, in list, in val, in simple_select, in exact ) {}

public String FRM_TBL_type( in tbl, in row, in col, in val, in simple_select ) {}

/**
 * Returns the name/index of the row depending on the specified row/col.
 * If &lt;row&gt; is given (i.e. != ""), the unchanged value is returned.
 * If &lt;row&gt; is not given (i.e. == ""), the row containing the given
 * &lt;val&gt; is searched for in the column specified by &lt;col&gt;.
 * If both &lt;row&gt; and &lt;col&gt; are not given, an error is returned.
 * @param tbl (in) table object
 * @param row (in) row to select (if row does not equal "", no search is
 *	performed; if row equals "<LAST>", last valid row determined by this function
 *	is returned (no search performed))
 * @param col (in) column where &lt;val&gt; is searched for
 * @param val (in) value to search for in the given column
 * @param out_row (out) the name of the determined row
 * @param exact (in) [optinal] true indicates that an exact match is to be performed
 * @return
 *	E_OK: value was found (i.e. out_row contains the name of the row)
 *	else: error
 */

public String FRM_TBL_determine_row( in tbl, in row, in col, in val, out out_row, in exact ) {}

/**
 * Returns the name/index of the column depending on the specified row/col.
 * If &lt;col&gt; is given (i.e. != ""), the unchanged value is returned.
 * If &lt;col&gt; is not given (i.e. == ""), the column containing the given
 * &lt;val&gt; is searched for in the row specified by &lt;row&gt;.
 * If both &lt;row&gt; and &lt;col&gt; are not given, an error is returned.
 * @param tbl (in) table object
 * @param row (in) row to select (if row does not match "#[0-9][0-9]*", then
 *	column #0 is searched for the value &lt;row&gt;
 * @param col (in) column to select (if col does not equal "", no search is
 *	performed;  if row equals "<LAST>", last valid row determined by this function
 *	is returned (no search performed))
 * @param val (in) value to search for in the determined row
 * @param out_col (out) the name of the determined column
 * @param exact (in) [optinal] true indicates that an exact match is to be performed
 * @return
 *	E_OK: value was found (i.e. out_col contains the name of the column)
 *	else: error
 */

public String FRM_TBL_determine_col( in tbl, in row, in col, in val, out out_col, in exact ) {}

/**
 * Searches for a row that contains given data in specified coumns. Note
 * that an exact mtch is always performed. 
 * If such row is found (first from top!) the row index is returned otherwise
 * an error is indicated. Cells can be specified using the following syntax:
 * <p>
 *	<pre>col=val[;col=val]...</pre>
 * <p>
 *	where <code>col</code> is either
 * <ul>
 *	<li><b>name</b> such as "Column1" (without quotes!)</li>
 *	<li><b>index</b> such as #3</li>
 * </ul> 
 * @param tbl(in) table to search
 * @param row_spec (in) formated cell content specification (see above)
 * @param out_row (out) row index (only if retrn == E_OK)
 * @param idx (in) [optional] internal index needed for recursion
 * @return
 *	E_OK:			row found
 *	E_NOT_FOUND:	row not found
 *	else:			other error
 */

public String FRM_TBL_find_row( in tbl, in row_spec, out out_row, in idx, in row_offset ) {}

/**
 * Searches for a cell that contains a given <code>regex</code>. 
 * You can limit the search to a particular <code>row</code> or a <code>col</code>,
 * by providing their names or indices (#idx).
 *
 * @param tbl (in)	table object
 * @param regex (in)	text to search for (regular expression)
 * @param out_row (out)	row where text was found
 * @param out_col (out)	column where text was found
 * @param row (in) [optional] limit the search to this row only
 * @param col (in) [optional] limit the search to this column only
 * @param exact (in) [optional] TRUE=exact match, FALSE=tolerant match [default=FALSE]
 * @param row_offset (in) [optional] row index where the search begins [default=0]
 * @return
 *	E_OK:			text found
 *	E_NOT_FOUND:	text not found
 *	else:			other error
 */

public String FRM_TBL_find_cell( in tbl, in regex, out out_row, out out_col, in row, in col, in exact, in row_offset ) {}

/**
 * Selects the cell and opens it's popup menue via the &lt;kAppa&gt; key.
 * @param tbl (in) table object
 * @param row (in) row to select
 * @param col (in) column to select
 */

public String FRM_TBL_popup_cell_menu( in tbl, in row, in col ) {}

/**
 * Opens a popup menue at the specified location within the table object
 *	or, if not specified, at the top-left corner of the table object.
 * @param tbl (in) table object
 * @param x (in) [optional] x-coordinate to click to [default: 0]
 * @param y (in) [optional] y-coordinate to click to [default: 0]
 */

public String FRM_TBL_popup_menu( in tbl, in x, in y ) {}

/**
 * Pops a drop-down list on a Stingray-ComboBox-cell by performing
 * a left-mouse click at some calculated position within the <code>obj</code>.
 * 
 * @param obj	(in)	object to click
 * @param list	(in)	list object that drops down after the click
 * @param item	(in)	item to select
 * @return
 *	E_OK:	success
 *	else:	failure
 */

public String FRM_TBL_list_select_item( in obj, in list, in item ) {}

/**
 * Pops a drop-down list on Stingray-ComboBox-cell by performing
 * a left-mouse click at some calculated position within the <code>obj</code>.
 * 
 * @param obj	(in)	object to click
 * @return
 *	E_OK:	success
 *	else:	failure
 */

public String FRM_TBL_obj_drop_list( in obj ) {}

public String FRM_TBL_web_get_child_item( in tbl, in row, in col, in desc ) {}

}
