package std;
import mode.*;
import stereotype.*;

/**
 * This library implements an interface to descriptions of WinRunner errors.
 * All information about error codes ant the corresponding descriptions are
 * maintained in Excel table "default.xls". To keep this library up to date 
 * some manual work is necessary. Our approach is two-fold:
 * <ul>
 *	<li>The "official" codes are collected from WinRunner on-line help 
 *      as this seems to be the only place where Mercury keeps the up-to-date 
 *		information. This codes are listed in Excel sheet named "documented codes".
 *	</li>
 *	<li>The list of additional (undocumented) codes is produced by searching
 *		the WinRunner "lib" directory and merging the codes that we found into 
 *		the official list. The merged list is kept in worksheet "all codes".
 *	</li>
 * </ul>
 * By default "all codes" are activated. However you may change this by 
 * setting the other sheet as default (note that the file is read-only).
 * <h4>Language support</h4>
 * Note that this library supports two languages so far: English and German.
 * We use codes "EN" and "DE" respectively. We have decided to keep our own
 * list of language codes (see err_get_supproted_languages()) because we cannot 
 * rely on WinRunner's undocumented features such as get_lang() function 
 * (which returns "DEU" for German and God knows what for other environments).
 * <p>You are welcome to add additional languages. Please inform us about this
 * so we can merge your language into our distribution.
 * <h4>Typical usage</h4>
 * This library is automatically loaded with EMOS Framework. By default english 
 * texts are loaded. You would normally change this setting in your startup test, e.g.
 * <pre>
 *	    err_set_language( "DE" );
 * </pre>
 * To print formatted error descriptions you could do following in your code:
 * <pre>
 *	    rc = set_window( "some non-existing window" );
 *	    if ( rc != E_OK )
 *	    {
 *	    	print( err_perror( rc ) );
 *	    	tl_step( "Argh!", FAIL, "Have enough for today." );
 *	    	treturn rc;
 *	    }
 * </pre>
 * <h4>Acknowledgements</h4>
 * We would especially like to thank Michael Skobowsky from DAT 
 * (mailto:Michael.Skobowsky@dat.de) for his German translation and for 
 * all his enthusiastic support and ideas.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_err_lib_tsl.html" target=tsl>emos_std_err_lib.tsl</A>
*/

public class emos_std_err_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_err_lib (){}
/**
 * Initialises the internal data structures from external table "default.xls".
 * @return E_OK if initialisation OK, otherwise failure.
 */

protected String err_init(  ) {}

/**
 * Returns the comma-separated list of the supported languages for
 * error descriptions.
 */

public String err_get_supproted_languages(  ) {}

/**
 * Sets the default language to the given <code>lang</code> code.
 * This function returns the code of the new default language.
 * If invalid language code is given, no change is made and the old
 * default code is returned. The available language codes are returned
 * via err_get_supported_languages().
 * @param lang language code 
 */

public String err_set_language( in lang ) {}

/**
 * Returns the code of the default language.
 */

public String err_get_language(  ) {}

/**
 * Returns <code>TRUE</code> if given <code>errNr</code> is a known error code.
 * Otherwise <code>FALSE</code> is returned.
 * @param errNr error number 
 */

public String err_is_error( in errNr ) {}

/**
 * Returns the error description for the given error number and the langauge code.
 * If no language code was given, the default language is taken.
 * For non-existing error numbers an empty string is returned.
 * @param errNr error number 
 */

public String err_get_error_desc( in errNr, in lang ) {}

/**
 * Returns the error code as text for the given error number, e.g. "E_GENERAL_ERROR".
 * For non-existing error numbers an empty string is returned.
 * @param errNr error number 
 */

public String err_get_error_code( in errNr ) {}

/**
 * Returns formatted error message, e.g. "[-10001] General error occurred. (E_GENERAL_ERROR)".
 * The message is also printed out via debug_msg(). 
 * <p>NOTE: This function only supports the default language.
 * @param errNr error number 
 */

public String err_perror( in errNr ) {}

}
