package std;
import mode.*;
import stereotype.*;

/**
 *	This library contains some useful string manipulation routines.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_string_lib_tsl.html" target=tsl>emos_std_string_lib.tsl</A>
*/

public class emos_std_string_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_string_lib (){}
/**
 * 	This functions concatenates all elements of the array <arr> into a string.
 *	Elements are separated by <code>sep</code>.
 *<p> NOTE!
 *	Iy the <code>arr</code> is not sequentially inexed (starting from 0), then either the
 *	<code>count</code> should NOT be defined (in operator will be used) or <code>start</code> must
 *	be defined (sequential from then on).
 * @param arr[] (inout)	array to be "streamed"
 * @param sep (in)		(optional) separator,  [default: comma]
 * @param count (in)	(optional) number of elements in the array [default: 0]
 * @param start (in)	(optional) starting index [default: 0]
 * @return
 *	streamed array
 */

public String arr2str( inout arr [], in sep, in count, in start ) {}

/**
 * 	Replaces all <code>delchr</code> characters with <code>insstr</code> string.
 *<p> NOTE1!
 *	This function internally uses split() to process the string. Unfortunatelly,
 *	split() has an undocumented "feature" that it trims blanks from the given
 *	string before splitting. This has the effect that replace() does the same.
 *	If you don't want the string to be trimmed, use replace1().
 *<p> NOTE2!
 *	The replace() replaces each occurence of individual characters within
 *	<code>delchr</code> with the complete string <code>insstr</code>. It does NOT replace string
 *	<code>delchr</code> with the string <code>insstr</code>.
 * @param str (in)	string to be processed
 * @param delchr (in)	character(s) to be replaced
 * @param insstr (in)	string to be used as replacement
 * @return
 *	converted string
 */

public String replace( in str, in delchr, in insstr ) {}

protected String test_replace(  ) {}

/**
 * 	Removes all occuences of <code>zap</code> from the end of <code>str</code> (right trim).
 * @param str (in)	string to be processed
 * @param zap (in)	(optional) string to be removed [default: " "]
 * @return
 *	converted string
 */

public String strip_trail( in str, in zap ) {}

/**
 * 	Removes all occuences of <code>zap</code> from the front of <code>str</code> (left trim).
 * @param str (in)	string to be processed
 * @param zap (in)	(optional) string to be removed [default: " "]
 * @return
 *	converted string
 */

public String strip_front( in str, in zap ) {}

/**
 * 	Removes all occuences of <code>zap</code> from both sides of <code>str</code> (trim).
 * @param str (in)	string to be processed
 * @param zap (in)	(optional) string to be removed [default: " "]
 * @return
 *	converted string
 */

public String strip_both( in str, in zap ) {}

/**
 * 	Splits a <code>path</code> string into a <code>directory</code> and <code>file</code> parts. The path
 *	separator can be defined with <code>sep</code> (default: backslash) 
 *<p> NOTE!
 *	The function does not analyse whether the <code>file</code> part is indeed a file name.
 *	Anything following the last <sep</code> is treated as <code>file</code>. Anything before is
 *	treated as <code>dir</code>.
  NOTE2!
 	<code>dir</code> does not end with <code>sep</code>.
 * @param path (in)	string to be processed
 * @param dir (out)	directory-part (up until the last <code>sep</code>)
 * @param file (out)	name-part (anything after the last <code>sep</code>)
 * @param sep (in)	(optional) separator (default: backslash)
 * @return
 *	E_OK:	success
 *	!E_OK:	error (do not ust the out variables!)
 */

public String split_path( in path, out dir, out file, in sep ) {}

/**
 *	Diese Funktion fügt zwei Pfadteile zusammen. Dabei werden <code>sep</code> konvertiert
 *	"/-->\  bzw. \-->/). Standardmeßig werden \ durch / ersetzt.
 *	Eventuell nicht oder merfach vorhandene Separatoren in der Join-Stelle 
 *	werden auf ein <sep> gesetzt.
 *<p> EXAMPLE:
 *<pre>
 *	join_path( "C:\aaa", "bbb.xxx" )   ==>  "C:/aaa/bbb.xxx"
 *	join_path( "C:\aaa\", "\bbb.xxx" ) ==>  "C:/aaa/bbb.xxx"
 *	join_path( "", "bbb.xxx" )         ==>  "/bbb.xxx"
 *	join_path( "C:/aaa" )              ==>  "C:/aaa/"
 *</pre>
 * @param part1 (in)	erster Teil der Pfadangabe
 * @param part2 (in)	zweiter Teil der Pfadangabe
 * @param sep (in)	(optional) Pathseparator to use [default: /]
 * @return
 *	concatenated path
 */

public String join_path( in part1, in part2, in sep ) {}

public String trim( in str1 ) {}

public String ltrim( in str1 ) {}

public String alltrim( in str1 ) {}

public String random_arr( inout arr1 [], in nnn ) {}

public String replace1( in s1, in s2, in s3 ) {}

public String substr1( in str1, in s1, in s2 ) {}

public String index1( in s1, in s2 ) {}

public String index2( in s1, in s2, in n1 ) {}

public String split1( in s1, inout arr1 [], in s2 ) {}

/**
 * Converts the integer returned by the get_time function to a string formatted as YYYYMMDD.
 * This function converts the integer argument (time) returned by the get_time function to a string,
 * in the format "YYYYMMDD" For example, the string "19981205", is the return value of the function.
 * @param time (in) [optional] integer returned by get_time() or any other integer that is interpreted
 * as number of seconds passes since 1.Jan.1970. [default: today]
 * @return string formatted to YYYYMMDD or empty string if date could not be interpreted.
 */

public String yyyymmdd_str( in time ) {}

}
