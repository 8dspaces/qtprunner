package frm.tpl.scripts;
import mode.*;
import stereotype.*;

/**
 *	Make this script your startup test and place here all you allways need.
 *	If you place your complete test suite under this directory you probably
 *	won't need much more than what is already defined here.
 
 @stereotype main test
 @tsl <A HREF= "startup_tsl.html" target=tsl>startup.tsl</A>
*/

public class startup extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private startup (){}
/**
 *	Trims given characters from the end of the given <code>str</code>ing.
 *	Use this function to ensure that getvar("curr_dir") allways returns the
 *	same value. Sometimes it returns the path ended with backslash. In other
 *	occasions (e.g. re-run the same test) it returns the same path without the
 *	backslash.
 *	@param str (in)	string to be right-trimmed
 *	@param zap (in)	(optional) string to be trimmed [default: \]
 *	@return the trimmed string
 */

protected String strip_trail( in str, in zap ) {}

}
