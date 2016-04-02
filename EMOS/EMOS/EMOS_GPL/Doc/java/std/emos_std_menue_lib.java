package std;
import mode.*;
import stereotype.*;

/**
 *	This library contains alternative implementations for standard functions
 *	that operate on menues (menu_x() functions).
 *	Another sort of functions in this library are functions that implement some
 *	additional functionality that would probably fit into Mercury's logic
 *	for menu_x() functions.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_menue_lib_tsl.html" target=tsl>emos_std_menue_lib.tsl</A>
*/

public class emos_std_menue_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_menue_lib (){}
/**
 *	Call this function to cause menu_select_item to select the complete menu path.
 *  <p>Note:<br>
 *  Behaves the same as WinRunner, the only problem is that it does not always work.
 */

public String EMOS_menu_set_complete_path_selection(  ) {}

/**
 *	Call this function to cause menu_select_item to select only the last item
 *  instead of the complete menu path.
 *  <p>Example:<br>
 *     instead of calling menu_select_item( "aaa;bbb;ccc" )<br>
 *     this mode causes menu_select_item( "ccc" ) to be called
 *  <p>Note:<br>
 *  This is the default mode.<br>
 *  It uses an undocumented feature of WR that appears to work more often than
 *  the documented one.
 */

public String EMOS_menu_set_last_entry_selection(  ) {}

/**
 *	Returns TRUE if COMPLETE_PATH selection is activated.
 * @return TRUE if COMPLETE_PATH selection FALSE otherwise
 */

public String EMOS_menu_is_complete_path_selection(  ) {}

/**
 *	Returns TRUE if LAST_ENTRY selection is activated.
 * @return TRUE if LAST_ENTRY selection FALSE otherwise
 */

public String EMOS_menu_is_last_entry_selection(  ) {}

/**
 *	This function is specially designed for a particular DOS application. This
 *	application has its own representation of a menu bar. WinRunner is capable
 *	of recognising text within the menu bar.
 *	This function clicks on a particular string (item) within the menu bar.
 *<p> NOTE!
 *	Please note that position and size of the menu bar is hard-coded.
 * @param win (in)	name of the window that contains the menu bar
 * @param item (in)	the string to be selected
 * @return
 *	E_OK:	Success
 *	!E_OK:	Error
 */

public String DOS_menu_select_item( in win, in item ) {}

/**
 * This function clicks on a menu item without requiring items to be learned.
 * @param item the item to be selected
 * @param win [optional] window where actions are to be performed
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_select_item1( in item, in win ) {}

/**
 * This function returns the info of a menu item without requiring items to be learned.
 * @param item the item to be selected
 * @param attr attribute to be examined
 * @param info (out) info retrieved
 * @param win [optional] window where actions are to be performed
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_get_info1( in item, in attr, out info, in win ) {}

/**
 * This function waits for the info of a menu item without requiring items to be learned.
 * @param item the item to be selected
 * @param attr attribute to be examined
 * @param info info to be waited for
 * @param time [optional] time to be waited for
 * @param win [optional] window where actions are to be performed
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_wait_info1( in item, in attr, in info, in time, in win ) {}

/**
 * This function compares the expected with the actual info of a menu item without requiring items to be learned.
 * @param item the item to be selected
 * @param attr attribute to be examined
 * @param info info to be checked
 * @param win [optional] window where actions are to be performed
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_check_info1( in item, in attr, in info, in win ) {}

/**
 * This function implements the actual logic for processing menu items that are not in the GUI-map.
 * @param action action to be performed
 * @param item the item to be selected
 * @param attr [optional] attribute to be examined
 * @param inInfo [optional] info to be used
 * @param outInfo (out) [optional] info to be returned
 * @param time [optional] time to be waited for
 * @param win [optional] window where actions are to be performed
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

protected String EMOS_menu_item( in action, in item, in attr, in inInfo, out outInfo, in time, in window ) {}

/**
 * This function clicks on a menu item without requiring items to be learned by
 * untilysing the WinRunner's text recognition  cpabilities.
 * <p>NOTE<p>
 *	This is an alternative to EMOS_menu_item() when this one does not seem to be
 *	stable enough (e.g. position of menu entries changes too often).
 *	This worked in very well for some applications that we were punished to test.
 *
 * @param item the item to be selected
 * @param arg_win_desc (in) (optional) physical menu window description [default: { class: object, MSW_id: 0, location: %d }]
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_item_via_text( in item, in arg_win_desc ) {}

/**
 * This function clicks on a menu item without requiring items to be learned.
 * NOTE: It was a nice try that at some point in time worked or at least seemed to.
 * @param item the item to be selected
 * @return 
 *	E_OK: cuccess
 *	!E_OK: failure
 */

public String EMOS_menu_select_item2( in item ) {}

/**
 *	This function implements an alternative for native menu_select_item().
 *	Try using it if the native function does not seem to work. For some
 *	mysterious reason tis function indeed manages to select more items than the
 *	native one. Note the different interface (parameter list).
 *	If you need more info, I must disapoint you. You must either dig into the 
 *	code yourself or try to get in touch with the author. I gave up.
 * @param gui1 (in)	gui-File	
 * @param win1 (in)	window
 * @param men1 (in)	menu_item		
 * @param win2 (inout)	window:	
 *<pre>		win2 = "" , dann nur Path von menu_item gibt zurück.		
 *		win2 = "*", dann sucht Name von Window in alle GUI-Filen
 *	 			(wenn Sie wissen nicht, welche Window taucht auf).		
 *		win2 # "*",dann sucht nicht (wenn Sie wissen, welche Window taucht auf).		
 *</pre>
 * @return
 *	E_OK:	Erfolg
 *	!E_OK:	 1	-	Object != menu-item or menu="sys_" or "parent"="none"
 *			 3  -	menu-item existiert nicht aufm Bildschirm
 *			 4	-	keine Beschreibung von aufgerufenem Fenster in GUI-Map 		
 *			 5	-	Menu is disabled 		
 */

public String EMOS_menu_select_item( in gui1, in win1, in men1, inout win2 ) {}

/**
 *	The empty (does nothing) exception handler needed for menu_select_item1().
 * @param rc (in)
 * @param func (in)
 */

public String excep1( in rc, in func ) {}

public String menu_select_item1( in gui, in win1, in men1, in ttt1, out gui2, inout win2, out menu_str ) {}

}
