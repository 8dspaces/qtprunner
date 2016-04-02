package std;
import mode.*;
import stereotype.*;

/**
 *	Contains some general-purpose miscellaneous function that do not seem to 
 *	fit nicely into any other EMOS library. 
 *<p> PUBLIC CONSTANTS:
 *<ul>
 * <li>	ARRSEP</li>
 *</ul>
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_misc_lib_tsl.html" target=tsl>emos_std_misc_lib.tsl</A>
*/

public class emos_std_misc_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_misc_lib (){}
/**
 * 	Turns debug mode on/off.
 * @param mode (in)		TRUE = turn debug on, else turn debug off
 */

public String set_debug( in mode ) {}

/**
 * 	Returns the debug mode.
 * @return
 *	TRUE = debug mode on
 *	FALSE = debug mode off
 */

public String is_debug(  ) {}

/**
 * 	In debug-mode produces a "report_msg" formated as "DEBUG: <code>msg</code>".
 *	Otherwise just returns (no message produced).
 * @param msg (in)	message to be reported in debug mode
 */

public String debug_msg( in msg ) {}

/**
 * 	In debug-mode produces a "report_msg" formated as "DEBUG: <code>msg</code>" if debug level >= 2.
 *	Otherwise just returns (no message produced).
 * @param msg (in)	message to be reported in debug mode
 */

public String debug_msg2( in msg ) {}

/**
 * Defines the level for logging call chains.
 * @param level (in)
 *		0 = no call logging;
 *		1 = log only full chains (in case of error);
 *		2 = log full chains and short chains (in case of E_OK)
 */

public String set_log_call_level( in level ) {}

/**
 * Returns the level for logging call chains.
 */

public String get_log_call_level(  ) {}

/**
 * 	Wrapper around GUI_load(). It makes sure that the <code>gui</code> is unloaded first
 *	before the load is attempted (avoids a message). Additionally it produces
 *	a tl_step() message documenting the outcome of invoked GUI_load() function.
 * @param gui (in)		filename/path of a GUI-map to be loaded
 * @return
 *	E_OK:		operation successfull
 *	otherwise:	see return values for GUI_load()
 */

public String load_GUI( in gui ) {}

/**
 * 	Converts a string "Y"/"Yes" or German equivalent "J"/"Ja" 
 *	(case-insensitive!) to boolean. 
 *	This function is particularly useful when reading user input (either
 *	interactivelly or from data-tables).
 *<p> NOTE!
 *	Blanks are trimmed from both sides of <str> before comparison.
 * @param str (in)	string to be evaluated
 * @return
 *	TRUE:		string resambles to "y","yes","j" or "ja"
 *	FALSE:		string resambles to anything else
 */

public String yes( in str ) {}

/**
 * 	Converts a string "Y"/"Yes" or German equivalent "J"/"Ja" 
 *	(case-insensitive!) to boolean. 
 *	This function is particularly useful when reading user input (either
 *	interactivelly or from data-tables).
 *<p> NOTE!
 *	Blanks are trimmed from both sides of <str> before comparison.
 * @param str (in)	string to be evaluated
 * @return
 *	TRUE:		string resambles to "y","yes","j" or "ja"
 *	FALSE:		string resambles to anything else
 */

public String Ja( in str ) {}

/**
 * 	Wrapper around setvar("timeout"). It saves the current timeout before
 *	setting it to the new value.
 * @param time (in)	timeout (in seconds) to be set
 */

public String set_timeout( in time ) {}

/**
 * 	Restores the timeout that has been overriden by the last invocation of the
 *	set_timeout() function.
 */

public String restore_timeout(  ) {}

/**
 * WR has a nice bug with in-operator operating on multi-dimensional
 * arrays. For example <code>for ( i in arr )</code> returns different
 * occasionally returns different separators (mostly pipe | but simetimes
 * something else that appears as bold pipe)
 * With this function we determine the separator dynamically as it seems
 * to stay constant during the single WR invocation.
 */

protected String get_arrsep(  ) {}

/**
 * returns !0 if rc == E_OK
 */

public String pass( in rc ) {}

/**
  returns !0 if rc != E_OK
 */

public String fail( in rc ) {}

/**
 * what was the last error  
 */

public String getLastRc(  ) {}

protected String addPath( in path ) {}

protected String extractPath( in searchPath ) {}

/**
 * Returns true if specified test refers to a WinRunner compiled module
 * @param test (in) name of the test
 * @return true if test is the compiled module otherwise false
 */

public String splitSearchPath( out outArr [] ) {}

/**
 * Returns true if the given test name refers to a compiled module otherwise
 * false is returned. Search path is taken into account.
 * @param test (in) name of the test
 * @return true if the given test name refers to a compiled module otherwise
 * false
 */

public String isCompiledModule( in test ) {}

/**
 * Creates a new file.
 * @param dir directory name or full path name (second parameter must be empty)
 * @param name filename (if empty, fullpath assumed in first parameter)
 * @return E_OK success, else failire
 */

public String create_file( in dir, in name ) {}

}
