package std;
import mode.*;
import stereotype.*;

/**
 *	This library contains alternative implementations of standard functions
 *	that operate on list objects (list_x() functions). Reasons for alternative 
 *	implementations can be various. They should be carefully documented with
 *	each function.
 *	Another sort of functions in this library are functions that implement some
 *	additional functionality that would probably fit into Mercury's logic
 *	for list_x() functions.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_list_lib_tsl.html" target=tsl>emos_std_list_lib.tsl</A>
*/

public class emos_std_list_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_list_lib (){}
/**
 * 	Checks whether the <code>list</code> contains a particular item. You can search for
 *	items that must exactly match the search string <code>str1</code> or contain the
 *	searched substring.
 * @param obj (in)		list object to be searched
 * @param regex (in)	string to search for
 * @param pos (out)	item number in case <str1> was found
 * @param soft (in)	(optional)
 *	            TRUE = matches a substring
 *				FALSE = exact match [default: FALSE]
 * @return
 *	E_OK:		match found
 *	E_NOT_FOUND: match not found
 *	else:		errors detected
 */

public String list_item_exists( in obj, in regex, out pos, in soft ) {}

/**
 *	This function was created as an attempt to solve a particularly ugly bug
 *	in WR 6.0 / 6.02. It is obsolete now because we have discovered the real
 *	cause of the problem which you can solve by placing set_var("timeout",10)
 *	in your startup script. 
 *	We have left the function in the library to show one way how some problems
 *	could be aproached by applying alternative methods to achieve the "same"
 *	effect. In this case we rely on the fact that MFC listboxes typically
 *	position the focus on the item whose name starts with the key pressed. So
 *	we are pressing the initial key until we either find an item or we "cycle-
 *	through" the list.
 *<p> PROBLEM DESCRIPTION:<p>
 *	Hier and then we had the problem that after restarting WinRunner, list
 *	objects wouldn't work properly. For some reason list_select_item() would
 *	suddenly not work although the item was still there and neither application
 *	nor the test (that's what we thought) changed. GUI editor would show no
 *	problems whatsoever with any of the list objects. Only the selecting would
 *	not work. The only way to "solve" the problem was to re-install WinRunner.
 *	Finally, after days of searching,, we have discovered, that after some
 *	WinRunner crashes, the default timeout value (saved in wrun.ini, where else)
 *	had a value of 10 instead of 10000. This has explained why this problem 
 *	only occurs in WR 6.0. It is 6.0 when timeout was changed from seconds to
 *	miliseconds. Obviously, somewhere deep in some WinRunner dll there is still
 *	a peace of logic that thinks in seconds instead of miliseconds.
 *	Since we know what Mercury could claim, we have checked our test scripts -
 *	they are deffinitely fine. It is WinRunner who causes problems.
 *<p> PROBLEM SOLUTION:<p>
 *	There are several solutions for this problem:
 *<ul>
 * <li> You change the wrun.ini each time this happens.</li>
 * <li> You wait until Mercury fixes the problem.</li>
 * <li> You (re)set the timeout you want in your startup script.</li>
 *</ul>
 *	Obviously, the last one is the only feasible.
 * @param obj (in)		name of the list object
 * @param item (in)	item which is to be selected
 * @return
 *	E_OK:	success
 *	!E_OK:	error
 */

public String list_select_item1( in obj, in item ) {}

}
