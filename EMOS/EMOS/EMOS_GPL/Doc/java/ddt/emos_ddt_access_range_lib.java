package ddt;
import mode.*;
import stereotype.*;

/** 
 *	This library defines an "iterator" (as much as one can emulate a real 
 *	iterator in a language such as TSL) for data tables (ddt-interface).
 *	The main benefits of this iterator are:
 *	   1.) you can loop through any table in an uniform way,
 *	   2.) you can choose one of four algorithms to iterate through the range:
 *	         * sequntial (forward)
 *	         * sequential (reverse)
 *	         * random (values may get repeated)
 *	         * random (values will not be repeated)
 *	   3.) you can simultaneously have iterators for multiple data tables
 *	       (ane at a time per table!)
 *	   4.) you can use it to iterate any range of numbers (unrelated to data 
 *	       tables).
 * <p>NOTE!
 * <ul>
 * <li> For more detailed instruction see comments for DDT_ACCESS_lib.</li>
 * <li> This iterator does not (yet) support the syntax like DDT_ACCESS_lib.
 *		You can only specify a range with <code>from</code> and <code>to</code> parameters. It would
 *		be great if someone could find time to implement this.</li>
 * </ul>
 * PUBLIC CONSTANTS:
 * <ul>
 *	<li>DDT_ACCESS_SEQUENTIAL</li>
 *	<li>DDT_ACCESS_REVERSE_ACCESS</li>
 *	<li>DDT_ACCESS_RANDOM</li>
 *	<li>DDT_ACCESS_RANDOM_NOREPEAT</li>
 *	<li>DDT_ACCESS_INVALID_MODE</li>
 *	<li>DDT_ACCESS_DEFAULT_MODE</li>
 * </ul>
 
 @stereotype compiled module
 @tsl <A HREF= "emos_ddt_access_range_lib_tsl.html" target=tsl>emos_ddt_access_range_lib.tsl</A>
*/

public class emos_ddt_access_range_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_ddt_access_range_lib (){}
/**
 *	Initialises the range-access algorithm.
 * @param table (in)	the name od the iterator (data table)
 * @param from (in)	begining  of range
 * @param to (in)		end of range
 * @param mode (in)	one of four possible access modes
 *				[default: DDT_ACCESS_SEQUENTIAL]
 * @return
 *	E_OK:	initialisation succeeded
 *	!E_OK:	initialisation failed
 */

public String DDT_ACCESS_init_range( in table, in from, in to, in mode ) {}

/**
 *	Removes range-access for given table and frees the internal buffers.
 * @param table (in)	the name od the iterator (data table)
 */

public String DDT_ACCESS_clean_range( in table ) {}

/**
 *	Indicates whether there subsequent call to 
 *	DDT_get_next_in_range() is about to succeed.
 * @param table (in)	the name od the iterator (data table)
 * @return
 *	TRUE:	there is still something left to be processed
 *	FALSE:	all tests processed
 */

public String DDT_ACCESS_has_more_in_range( in table ) {}

/**
 *	Returns the index of the next test to be processed.
 * @param table (in)	the name od the iterator (data table)
 * @return
 *	>0:	test number
 *	<0:	error ocurred
 */

public String DDT_ACCESS_get_next_in_range( in table ) {}

protected String delete_index_array( in table ) {}

protected String init_index_array( in table ) {}

protected String set_pos_used( in table, in pos ) {}

protected String get_random_pos( in table ) {}

protected String get_norepeat_random_pos( in table ) {}

/**
 *	This function will delete a dimension from a multidimensional array.
 *<p> WARNING!
 *	It will delete ALL dimensions with the same name. For example
 *   assume your three-dimensional array "arr" has the following content:
 *<pre>		arr["x",1,"x"] = "x1x";
 *		arr["x",1,"y"] = "x1y";
 *		arr["y",1,"x"] = "y1x";
 *		arr["y",1,"y"] = "y1Y";</pre>
 *	then the following command
 *<pre>		delete_dimension( arr, "x" );</pre>
 *	will delete everything BUT
 *<pre>		arr["y",1,"y"] = "y1Y";</pre>
 * @param arr (inout)	array to be processed
 * @param dim (in)	dimension(s) to be removed
 */

protected String delete_dimension( inout arr [], in dim ) {}

}
