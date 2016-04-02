package frm;
import mode.*;
import stereotype.*;

/**
 * This library implements the "essence" of EMOS test design strategy.
 * It provides "wrappers" for most frequently used "standard" functions such
 * as edit_set(), list_select_item(), etc. The main point with this wrapper-
 * functions is that they natively interface the low-level FRM-functions and,
 * most of all, transparently implement the notion of SET/CHK/ATR/GEN mode for 
 * each function.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_gui_lib_tsl.html" target=tsl>emos_frm_gui_lib.tsl</A>
*/

public class emos_frm_gui_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_gui_lib (){}
/**
 *	Sets a generic indicator wheter to perform case-sensitive comparison or not.
 *	Functions that perform this kind of comparison are responsible to use this
 *	indicator.
 *
 * @param m	(in)	mode, TRUE or FALSE [default: TRUE]
 */

public String FRM_set_case_sensitive( in m ) {}

/**
 *	Returns the state of comparison indicator.
 *
 * @return
 *	TRUE:		case-sensitive comparison enabled
 *	FALSE:		case-sensitive comparison disabled
 */

public String FRM_is_case_sensitive(  ) {}

/**
 *	A standard "wrapper" for win_type() function.
 *	The function performs a win_type() on a specified &lt;window&gt; using the value 
 *	from the data table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing value to be typed.
 * <p>SET-MODE:
 *	The function uses the value from the data table as a second parameter to the
 *	win_type() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	We could retrieve some text from the window and compare it. In practice is
 *	this rather useless because there is either too much or no text all.
 *	For testing win_type() is normally only useful to check keyboard navigation
 *	or when you have no other chance (e.g. DOS applications). In the second
 *	case you would use the SET mode to completely drive the application (i.e.
 *	navigate and enter date) but you could hardly be able to check anything.
 *	There is some chance if font is known, so you could try with win_get_text()
 *	and the like. 
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Checks the content of the specified window attributes using win_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param window	(in)	GUI-window where actions are to be performed
 * @param suffix	(in)	(optional) as string which ia automatically appended to any
 *	                value.
 * @param force_suffix	(in)	(optional) set this to TRUE if you want the &lt;suffix&gt; to
 *	                be typed even if no value present.
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_win_type( in table, in test, in window, in suffix, in force_suffix ) {}

protected String FRM_SET_win_type( in table, in test, in window, in suffix, in force_suffix ) {}

protected String FRM_CHK_win_type( in table, in test, in window, in suffix ) {}

protected String FRM_ATR_win_type( in table, in test, in window ) {}

protected String FRM_GEN_win_type( in table, in test, in window, in suffix ) {}

/**
 *	A standard "wrapper" for obj_type() function.
 *	The function performs a obj_type() on a specified &lt;object&gt; using the value 
 *	from the data table.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing value to be typed or checked.
 * <p>SET-MODE:
 *	The function uses the value from the data table as a second parameter to the
 *	obj_type() function.
 * <p>CHK-MODE:
 *	The function compares the value from the data table with the value returned
 *	by obj_get_text(). Please note that comparison is case-sensitive.
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using obj_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Uses obj_get_text() to retrieve the content of the &lt;object&gt;. Note that
 *	that no conversion takes place before generating the test data.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @param suffix	(in)	(optional) as string which ia automatically appended to any
 *	                value.
 * @param force_suffix	(in)(optional) set this to TRUE if you want the &lt;suffix&gt; to
 *	                be typed even if no value present.
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_obj_type( in table, in test, in object, in suffix, in force_suffix ) {}

protected String FRM_SET_obj_type( in table, in test, in object, in suffix, in force_suffix ) {}

protected String FRM_CHK_obj_type( in table, in test, in object, in suffix ) {}

protected String FRM_ATR_obj_type( in table, in test, in object ) {}

protected String FRM_GEN_obj_type( in table, in test, in object, in suffix ) {}

/**
 *	We implement here our own equivalent of obj_wait_info(). Use it when you
 *	need to synchronize the script wth the application.
 *	Our implementation does not use obj_wait_info() because this function does 
 *	not handle regular expressions. Instead we retrieve the value with obj_get_info()
 *	and perform the match.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing formated value <code>|reg.exp|attribute|time[|exact]</code>.
 *	The string starts with the separator (in our example | but you can use any
 *	character as long as it only occurs in specified positions). <code>reg.exp</code>
 *	is the regular expression to be waited for. <code>attribute</code> is the
 *	attribute which conten is being waited for. <code>time</code> is the number
 *	of seconds to be waited for. <code>exact</code>true/false flag indicating
 *	whether an exact (1) or a tollerant match (0) is wanted. This ais an optional
 *	parameter. By default the tollerant match is performed.
 * <p>SET-MODE:
 *	The function parses the value from the data table and waits for the given
 *	value to appear.
 * <p>CHK-MODE:
 *	the same as in SET mode
 * <p>ATR-MODE:
 *	the same as in SET mode
 * <p>GEN-MODE:
 *	Generates a dummy template of the formated string
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_obj_wait_info( in table, in test, in object ) {}

protected String FRM_SET_obj_wait_info( in table, in test, in object, in poll_sec, in poll_msec ) {}

protected String FRM_GEN_obj_wait_info( in table, in test, in object, in attr ) {}

/**
 *	A standard "wrapper" for static_get_text() function.
 *	The function performs a static_get_text() on a specified &lt;object&gt; using the value 
 *	from the data table.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read)
 * <p>TEST DATA FORMAT:
 *	String containing value to be checked (SET is not supported for static texts).
 * <p>SET-MODE:
 *	Performs the same as in CHK mode.
 * <p>CHK-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	static_check_text() function.
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using static_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Uses static_get_text() to retrieve the content of the edit object. Note that
 *	an empty string is converted to "&lt;&lt;clear&gt;&gt;" as generated test data.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_static_get( in table, in test, in object ) {}

protected String FRM_SET_static_get( in table, in test, in object ) {}

protected String FRM_CHK_static_get( in table, in test, in object ) {}

protected String FRM_ATR_static_get( in table, in test, in object ) {}

protected String FRM_GEN_static_get( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for edit_set() function.
 *	The function performs a edit_set() on a specified &lt;object&gt; using the value 
 *	from the data table.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing value to be set or checked.
 * <p>SET-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	edit_set() function.
 * <p>CHK-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	edit_check_text() function. Please note that third parameter (case-sensitive
 *	flag) can be specified via FRM_set_case_sensitive().
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using edit_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Uses edit_get_text() to retrieve the content of the edit object. Note that
 *	an empty string is converted to "&lt;&lt;clear&gt;&gt;" as generated test data.
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_edit_set( in table, in test, in object ) {}

protected String FRM_SET_edit_set( in table, in test, in object ) {}

protected String FRM_CHK_edit_set( in table, in test, in object ) {}

protected String FRM_ATR_edit_set( in table, in test, in object ) {}

protected String FRM_GEN_edit_set( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for list_select_item() function.
 *	The function performs a list_select_item() on a specified &lt;object&gt; 
 *	using the value from the data table.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing names or indices of the desired list items.
 * <p>SET-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	list_select_item() function. Third and fourth parameters are not
 *	supported.
 * <p>CHK-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	list_check_selected() function.
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using list_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Uses list_get_selected() to retrieve the selection and generate test data.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_list_select_item( in table, in test, in object ) {}

protected String FRM_SET_list_select_item( in table, in test, in object ) {}

protected String FRM_CHK_list_select_item( in table, in test, in object ) {}

protected String FRM_ATR_list_select_item( in table, in test, in object ) {}

protected String FRM_GEN_list_select_item( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for list_activate_item() function.
 *	The function performs a list_activate_item() using the value from the data 
 *	table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only, can be generated)
 * <p>TEST DATA FORMAT:
 *	String containing logical name or the index (e.g. #1) of the item to be 
 *	activated.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native list_activate_item() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with a double click on a list item.
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using list_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Supported although type: navigation!
 *	We have found it practical to generate the item name of the currently 
 *	selected item.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_list_activate_item( in table, in test, in object ) {}

protected String FRM_SET_list_activate_item( in table, in test, in object ) {}

protected String FRM_GEN_list_activate_item( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for list_select_multi_items() function.
 *	The function performs a list_select_multi_items() on a specified &lt;object&gt; 
 *	using the value from the data table. Make sure to format the string 
 *	according to the rules for the native function (i.e. comma-separated).
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing names or indices of the desired list items.
 * <p>SET-MODE:
 *	The function uses the value from the data table as a second parameter to
 *	list_select_multi_items() function. Third and fourth parameters are not
 *	supported.
 * <p>CHK-MODE:
 *	It probably won't work because native functions list_check_mult_selected() 
 *	and list_check_selection() do not seem to be available for public. 
 *	Our implementation is based on list_check_selected(). We were too lazy to
 *	implement our own checking. If you want to do it yourself, then function
 *	list_get_selected() is something to start with.
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using list_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Uses list_get_selected() to retrieve the selection. Before generating the
 *	test data the separators returned by list_get_selected() are converted to
 *	something compatible with list_select_multi_items().
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_list_select_multi_items( in table, in test, in object ) {}

protected String FRM_SET_list_select_multi_items( in table, in test, in object ) {}

protected String FRM_CHK_list_select_multi_items( in table, in test, in object ) {}

protected String FRM_GEN_list_select_multi_items( in table, in test, in object ) {}

/**
 *	The new "wrapper" for menu_select_item() function.
 *	The function performs a menu_select_item() using the value from the data 
 *	table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing logical name or the index (e.g. #1) of the tab to be 
 *	selected.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native menu_select_item() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with menu_select_item().
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Not supported for type: navigation!
 *	Behaves the same as the SET-mode! 
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param timeout	(in)	(optional) sometimes menues aren't reaady when WR thinks
 *	                they are; use the &lt;timeout&gt; to force the function
 *	                menu_wait_info( object, "enabled", timeout ) to be called
 *	                before the actual menu_select_item() is invoked.
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_menu_select_item1( in table, in test, in timeout ) {}

protected String FRM_SET_menu_select_item1( in table, in test, in timeout ) {}

protected String FRM_GEN_menu_select_item1( in table, in test ) {}

/**
 *	A standard "wrapper" for menu_select_item() function.
 *	The function performs a menu_select_item() using the value from the data 
 *	table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing logical name or the index (e.g. #1) of the tab to be 
 *	selected.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native menu_select_item() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with menu_select_item().
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Not supported for type: navigation!
 *	Behaves the same as the SET-mode! 
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param timeout	(in)	(optional) sometimes menues aren't reaady when WR thinks
 *	                they are; use the &lt;timeout&gt; to force the function
 *	                menu_wait_info( object, "enabled", timeout ) to be called
 *	                before the actual menu_select_item() is invoked.
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_menu_select_item( in table, in test, in timeout ) {}

protected String FRM_SET_menu_select_item( in table, in test, in timeout ) {}

protected String FRM_GEN_menu_select_item( in table, in test ) {}

/**
 *	A standard "wrapper" for tab_select_item() function.
 *	The function performs a tab_select_item() on a specified &lt;object&gt; using the 
 *	value from the data table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only, can be generated)
 * <p>TEST DATA FORMAT:
 *	String containing logical name or the index (e.g. #1) of the tab to be 
 *	selected.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native tab_select_item() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	One could check whether a tab is selected. In practice however this is not
 *	as much used and in FRM concept soon becomes irritating. For this reason
 *	we have implemnted this function as type navigation which means that
 *	checking is not supported (although we do have the code for it).
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using tab_get_info()
 *	and match().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Supported although type: navigation!
 *	We have found it practical to generate the tab name/index although we
 *	ignore it in CHK mode.
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_tab_select_item( in table, in test, in object ) {}

protected String FRM_SET_tab_select_item( in table, in test, in object ) {}

protected String FRM_CHK_tab_select_item( in table, in test, in object ) {}

protected String FRM_ATR_tab_select_item( in table, in test, in object ) {}

protected String FRM_GEN_tab_select_item( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for button_press() function.
 *	You may use the function in two ways. 
 *	1.) If you specify the &lt;object&gt;, then the value from the data table is 
 *	    evaluated. If the value resambles to Yes, the &lt;object&gt; is "pressed". 
 *	2.) If you do not provide the &lt;object&gt; parameter, then the value from the
 *	    data table is used as the name of the object to be "pressed".
 *	The second alternative is an elegant way of pressing on OK, Cancel and
 *	similar buttons.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing either Y/YES/ON (or german equivalent J/JA/ON) or
 *	the logical name of the button to be pressed.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native button_press() function according to aforementioned description.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with button_press().
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using button_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	(optional) GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_button_press( in table, in test, in object ) {}

protected String FRM_SET_button_press( in table, in test, in object ) {}

protected String FRM_ATR_button_press( in table, in test, in object ) {}

protected String FRM_GEN_button_press( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for toolbar_button_press() function.
 *	The function performs a toolbar_button_press() on a specified &lt;object&gt;
 *	using the value from the data table.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing either the logical name of the toolbar button or the
 *	index (e.g. #1).
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native toolbar_button_press() function.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with toolbar_button_press().
 *	Behaves the same as the SET-mode! 
 * <p>ATR-MODE:
 *	Not implemented yet!
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_toolbar_button_press( in table, in test, in object ) {}

protected String FRM_SET_toolbar_button_press( in table, in test, in object ) {}

protected String FRM_GEN_toolbar_button_press( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for button_set() function.
 *	The function performs a button_set() on a specified &lt;object&gt; using the
 *	value from the data table. You can use it for both radio buttons and
 *	check buttons. The supported states are ON and OFF. DIMMED is not supported.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing either Y/YES/ON or N/NO/OFF (or German version J/JA/ON and
 *	N/NEIN/OFF).
 * <p>SET-MODE:
 *	The function evaluates the value from the data table. If Yes, the &lt;object&gt;
 *	is set to ON. Otherwise the &lt;object&gt; is set to OFF.
 * <p>CHK-MODE:
 *	The function evaluates the value from the data table. If Yes, the &lt;object&gt;
 *	is expected to be in ON-state. Otherwise the &lt;object&gt; is expected to be in
 *	OFF-state.
 * <p>ATR-MODE:
 *	Checks the content of the specified object attributes using button_check_info().
 *	Attribues are specified in pairs <code>attribute:value</code>. If more than
 *	a single atribute needs to be checked, each pair must be separated with a
 *	newline character (in Excel: Alt-Return).
 * <p>GEN-MODE:
 *	The evaluates the state of the &lt;object&gt;. If ON, then "ON" is generated.
 *	If OFF, then "OFF" is generted. If any other state is determined (e.g.
 *	DIMMED), then "???" is generated. Nonexisting objects are silently ignored.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	GUI-object where actions are to be performed
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_button_set( in table, in test, in object ) {}

protected String FRM_SET_button_set( in table, in test, in object ) {}

protected String FRM_CHK_button_set( in table, in test, in object ) {}

protected String FRM_ATR_button_set( in table, in test, in object ) {}

protected String FRM_GEN_button_set( in table, in test, in object ) {}

/**
 *	Convenience function for GUI constructs consisting of two radio buttons
 *	where one stands for "yes" and the other one for "no". A special-purpose 
 *	"wrapper" for button_set() function.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing either "Yes"/"No", "Y"/"N" or the German equivalent
 *	( "Ja"/"Nein", "J"/"N")
 * <p>SET-MODE:
 *	The function evaluates the value from the data table. In case of Yes,
 *	the &lt;yesObject&gt; is set. Otherwise &lt;noObject&gt; is set.
 * <p>CHK-MODE:
 *	The function evaluates the value from the data table. In case of Yes,
 *	the &lt;yesObject&gt; is expected to be set. Otherwise &lt;noObject&gt; is expected
 *	to be set.
 * <p>ATR-MODE:
 *	Not implemented! Behaves the same as CHK-MODE. If you need this functionallity,
 *	then you maust define each individual button and use FRM_button_set().
 * <p>GEN-MODE:
 *	The function first evaluates the &lt;yesObject&gt;. If set, then "Y" is generated.
 *	Otherwise the &lt;noObject&gt; is evaluated. If set, then "N" is generated.
 *	If either none of objects exist or none of them is set, then no data is
 *	generated.
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param yesObject	(in)logical name of the Yes radio button
 * @param noObject	(in)logical name of the No radio button
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_button_set_YesNo( in table, in test, in yesObject, in noObject ) {}

/**
 *	German version of FRM_button_set_YesNo.
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param jaObject	(in)	logical name of the Yes radio button
 * @param neinObject	(in)	logical name of the No radio button
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 * @see FRM_button_set_YesNo
 */

public String FRM_button_set_JaNein( in table, in test, in jaObject, in neinObject ) {}

protected String FRM_SET_button_set_JaNein( in table, in test, in jaObject, in neinObject ) {}

protected String FRM_CHK_button_set_JaNein( in table, in test, in jaObject, in neinObject ) {}

protected String FRM_GEN_button_set_JaNein( in table, in test, in jaObject, in neinObject ) {}

/**
 *	Convenience function for a group of related	radio buttons. 
 *  A special-purpose "wrapper" for button_set() function.
 *	The function accepts a vector (one-dimensional array) containing names of
 *	the radio buttons (GUI map) indexed by some keyword. The keywords are used
 *	in data tables to identify particular button and can differ from the
 *	actual object name.
 *	In this way one can address multiple radio buttons (one at a time) with
 *	one data cell.
 * <p>FRM-GUI-TYPE:
 *	data-entry (read/write)
 * <p>TEST DATA FORMAT:
 *	String containing the keyword which identifies the particular radio button.
 * <p>SET-MODE:
 *	The function reads the keyword from the data table. It then tries to set the
 *	button indexed by the particular keyword.
 * <p>CHK-MODE:
 *	The function reads the keyword from the data table. It then checks whether
 *	the button indexed by the particular keyword is set.
 * <p>ATR-MODE:
 *	Not implemented! Behaves the same as CHK-MODE. If you need this functionallity,
 *	then you maust define each individual button and use FRM_button_set().
 * <p>GEN-MODE:
 *	The function scans the &lt;objArr&gt; in a random sequence and checks if the
 *	particular object (radio button) is set. If yes, then the corresponding
 *	keyword is generated as test data. Object that do not exist are silently
 *	ignored.
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param objArr[]	(inout) a vector containing logical GUI object names indexed
 *	                by the keyword. NOTE: make sure to specify keywords in
 *	                lowercase!!!
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_radio_button_set( in table, in test, inout objArr [] ) {}

protected String FRM_SET_radio_button_set( in table, in test, inout objArr [] ) {}

protected String FRM_CHK_radio_button_set( in table, in test, inout objArr [] ) {}

protected String FRM_GEN_radio_button_set( in table, in test, inout objArr [] ) {}

}
