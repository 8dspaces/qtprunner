package frm;
import mode.*;
import stereotype.*;

/**
 * This library extends the functionality of EMOS_FRM_GUI_Lib with web 
 * functionality.
 * It provides "wrappers" for most frequently used "web" functions such
 * as web_image_click(), etc.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_web_lib_tsl.html" target=tsl>emos_frm_web_lib.tsl</A>
*/

public class emos_frm_web_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_web_lib (){}
/**
 *	A standard "wrapper" for web_image_click() function.
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
 *	the logical name of the image to be clicked.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native web_image_click() function according to aforementioned description.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with web_image_click().
 *	Behaves the same as the SET-mode! 
 * <p>GEN-MODE:
 *	Not supported for type: navigation!
 *	Ignores the cell (i.e. skips the cell).
 *
 * @param table	(in)	table name
 * @param test	(in)	test name (column)
 * @param object	(in)	(optional) GUI-object where actions are to be performed
 * @param x	(in)	(optional) x-coordinate relative to upper left corner
 * @param y	(in)	(optional) y-coordinate relative to upper left corner
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String FRM_web_image_click( in table, in test, in object, in x, in y ) {}

protected String FRM_SET_web_image_click( in table, in test, in object, in x, in y ) {}

protected String FRM_GEN_web_image_click( in table, in test, in object ) {}

/**
 *	A standard "wrapper" for web_link_click() function.
 *	You may use the function in two ways. 
 *	1.) If you specify the &lt;object&gt;, then the value from the data table is 
 *	    evaluated. If the value resambles to Yes, the &lt;object&gt; is "clicked". 
 *	2.) If you do not provide the &lt;object&gt; parameter, then the value from the
 *	    data table is used as the name of the object to be "clicked".
 *	The second alternative is an elegant way of clicking on OK, Cancel and
 *	similar buttons.
 * <p>FRM-GUI-TYPE:
 *	navigation (read-only)
 * <p>TEST DATA FORMAT:
 *	String containing either Y/YES/ON (or german equivalent J/JA/ON) or
 *	the logical name of the link to be clicked.
 * <p>SET-MODE:
 *	The function reads the value from the data table and uses it as a parameter
 *	to a native web_link_click() function according to aforementioned description.
 * <p>CHK-MODE:
 *	Not supported for type: navigation!
 *	There is nothing to be checked with web_image_click().
 *	Behaves the same as the SET-mode! 
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

public String FRM_web_link_click( in table, in test, in object ) {}

protected String FRM_SET_web_link_click( in table, in test, in object ) {}

protected String FRM_GEN_web_link_click( in table, in test, in object ) {}

}
