import mode.*;
import stereotype.*;

/** 
 *	Loads/unloads all EMOS libraries. 
 
 @stereotype main test
 @tsl <A HREF= "emosinit_tsl.html" target=tsl>emosinit.tsl</A>
*/

public class emosinit extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private emosinit (){}
/**
 * Reloads all EMOS libraries.
 *@param p1 (in)	1 = load all as system modules
 *			0 = load all as user modules [default = 0]
 *@param p2 (in)	1 = close after load, do not animate
 *			0 = leave open after load, animate [default = 0]
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String reload_all_emos_libs( in p1, in p2 ) {}

/**
 *	Unloads all EMOS libraries.
 *@return
 *	E_OK:	success
 *	!E_OK:	failure
 */

public String unload_all_emos_libs(  ) {}

/**
 *	Generates function prototypes for selected EMOS_GPL functions.
 */

public String generate_emos_function_prototypes(  ) {}

}
