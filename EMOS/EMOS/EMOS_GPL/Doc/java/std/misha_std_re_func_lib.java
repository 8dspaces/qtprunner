package std;
import mode.*;
import stereotype.*;

/**
 * Regular expression functions search and match for WinRunner.
 *<p>
 * Copyright (c) 2003 Misha Verplak
 *<p>
 * Supporting regex++ c++ library at www.boost.org:
 * Copyright (c) 1998-2001 Dr John Maddock
 *<p>
 * Permission is granted to use, modify and redistribute this
 * software provided both copyrights appear in all copies.
 *<p>
 * This script and dll provides WinRunner with perl-like
 * regular expression search and match functions, to supplement
 * the limited builtin function match() and add GUI properties
 * "label_like" and "id_like" for window recognition.
 *<p>
 * Known bugs:
 * <ul>
 * <li> Garbage in re can cause regex library to crash</li>
 * </ul>
 * @author Misha Verplack
 * @version  0.2  2003-01-21
 *
 * @see <a href="readme.txt">Readme</a>
 * @see <a href="re_sample.tsl">sample</a>
 * @see <a href="re_startup.tsl">startup</a>
 * @see <a href="syntax.html">Regex syntax description</a>
 
 @stereotype compiled module
 @tsl <A HREF= "misha_std_re_func_lib_tsl.html" target=tsl>misha_std_re_func_lib.tsl</A>
*/

public class misha_std_re_func_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private misha_std_re_func_lib (){}
/**
 * Match a regular expression to a whole string.
 * Submatch results in 'detail', use re_get_details() or re_get_match().
 * @param str string to search
 * @param re regular expression
 * @param m_pos matched position
 * @param m_len matched length
 * @param detail detail
 * @return 0 = no match, 1 = found match, gets position and length
 */

public extern._int re_match( in._string str, in._string re, out._int m_pos, out._int m_len, inout._string detail ) {}

/**
 * Search a string for a regular expression.
 * Submatch results in 'detail', use re_get_details() or re_get_match().
 * @param str string to search
 * @param re regular expression
 * @param m_pos matched position
 * @param m_len matched length
 * @param detail detail
 * @return 0 = no match, 1 = found match, gets position and length
 */

public extern._int re_search( in._string str, in._string re, out._int m_pos, out._int m_len, inout._string detail ) {}

/**
 * initialises the re_func
 */

public String re_func_init(  ) {}

/**
 * @param detail 
 * @param position
 * @param nbytes
 * @return 
 */

protected String _detail_decode( in detail, in position, in nbytes ) {}

/**
 * Print the re match details to the debug window.
 * Typically used after re_search() or re_match().
 * @param detail
 *  (1 byte ) size of this detail, ie. number of submatches + 1
 *  (2 bytes) line number where match occurred, counting from 1
 * [(2 bytes) position of (sub)match, 0-th submatch is whole match
 * [(2 bytes) length of (sub)match
 * [--------- repeated to a maximum of 50 submatches ---]
 * @return 
 */

public String re_print_detail( in detail ) {}

/**
 * Get the (sub)match position and length from the detail.
 * Typically used after re_search() or re_match()\nsubmatch can be 0 for whole match.
 *
 * @param detail
 * @param submatch
 * @param nsubs
 * @param line
 * @param position
 * @param len
 * @return 
 */

public String re_get_detail( in detail, in submatch, out nsubs, out line, out position, out len ) {}

/**
 * Get the (sub)matched string from the detail.
 * Typically used after re_search() or re_match()\nsubmatch can be 0 for whole match.
 * @param orig_str 
 * @param detail 
 * @param submatch
 * @param match_str
 * @return 
 */

public String re_get_match( in orig_str, in detail, in submatch, out match_str ) {}

}
