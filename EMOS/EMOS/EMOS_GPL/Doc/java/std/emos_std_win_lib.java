package std;
import mode.*;
import stereotype.*;

/**
 *	This library contains alternative implementations for standard functions
 *	that operate on windows (win_x() functions).
 *	Another sort of functions in this library are functions that implement some
 *	additional functionality that would probably fit into Mercury's logic
 *	for win_x() functions.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_win_lib_tsl.html" target=tsl>emos_std_win_lib.tsl</A>
*/

public class emos_std_win_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_win_lib (){}
/**
 *	This function invokes win_activate() before calling set_window(). It also
 *	takes care of the timeout (which does not normally work with win_activate()).
 *	If for some reason standard set_window() does not work, try this function
 *	before trying something else.
 * @param win (in)	window name
 * @param time (in)	(optional) timeout
 * @return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String EMOS_set_window( in win1, in time1 ) {}

/**
 *	Waits for window to close.
 * @param win (in)	window name
 * @param time (in)	(optional) timeout
 * @return
 *	E_OK:	success; window closed
 *	!E_OK:	failure
 */

public String EMOS_check_win_closed( in win, in timeout ) {}

/**
 * Attempts to close all given windows. 
 * @param wins (inout[])    array indexed from 0 upwards containg names of windows to close
 * @return returns E_OK if none of the specified windows existed by the time this function exited
 *      otherwise E_EXISTS is returned
 */

public String EMOS_win_close_all( inout wins [] ) {}

}
