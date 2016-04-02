package ddt;
import mode.*;
import stereotype.*;

/** 
 *	This library defines a sequential forward "iterator" (as much as one can 
 *	emulate a real iterator in a language such as TSL) for data tables 
 *	(ddt-interface).
 *	The main benefits of this iterator are:
 *	   1.) you can loop through any table in an uniform way,
 *	   2.) you can elegantly define a set of entries to be iterated through,
 *	   3.) you can simultaneously have iterators for multiple data tables
 *	       (ane at a time per table!)
 *	   4.) you can use it to iterate anything, not only data tables.
 *<p>NOTE!
 *	To understand how this iterator works use the analogy of selecting pages to
 *	be printed in a word-processing program. This sort of dialogs typically
 *	allow you to specify the RANGE of pages by connecting a first and the last
 *	page in the range with a hyphen (e.g. 3-9 ) and INDIVIDUAL pages by
 *	separating them with commas (e.g. 1,5,9,15-17,20). Exactly the same syntax
 *	is used with this iterator. So, if you call 
 *<pre>		DDT_ACCESS_init( table, "1-3,7,9-11" );</pre>
 *	the
 *<pre>		DDT_ACCESS_get_next( table );</pre>
 *	will return you the sequence 1,2,3,7,9,10,11.
 *
 *	This iterator also accepts non-numeric expressions such as
 *<pre>		DDT_ACCESS_init( table, "foo,bar,anyway" );</pre>
 *	Note however that this iterator does not read a data table in any way. So
 *	if your table contains following five columns
 *<pre>		aaa;foo;ccc;bar;eee</pre>
 *	the expression
 *<pre>		DDT_ACCESS_init( table, "foo-bar" );</pre>
 *	will return you "foo-bar" instead of "foo", "ccc", "bar" as you might have
 *	expected. If you use alpha names you, should know them in advance, i.e.
 *<pre>		DDT_ACCESS_init( table, "foo,ccc,bar" );</pre>
 *
 *	You might think of this as a serious limitation, but once you learn how
 *	EMOS uses data tables in its framework, you will probably apreciate the
 
 @stereotype compiled module
 @tsl <A HREF= "emos_ddt_access_lib_tsl.html" target=tsl>emos_ddt_access_lib.tsl</A>
*/

public class emos_ddt_access_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_ddt_access_lib (){}
/**
 *	Initialises the standard access algorithm. The <code>tests</code> can be specified 
 *	with syntax described on library-level (e.g. "1-5,7,9,aaa,xxx,12-35).
 * @param table (in)	unique name for the iterator (normaly, name of the data table)
 * @param tests (in)	test names (e.g. "1-15" or "1-3,5,7" or "a,b,c", etc.)
 * @return
 *	E_OK:	initialisation succeeded
 *	!E_OK:	invalid test set
 */

public String DDT_ACCESS_init( in table, in tests ) {}

/**
 *	Removes access for the given <code<table</code> name and frees the internal buffers.
 * @param table (in)	the name od the iterator (data table)
 */

public String DDT_ACCESS_clean( in table ) {}

/**
 *	Indicates whether a subsequent call to DDT_ACCESS_get_next() is about to 
 *	succeed or not.
 * @param table (in)	the name od the iterator (data table)
 * @return
 *	TRUE:	there are still some tests left to be processed
 *	FALSE:	all tests processed
 */

public String DDT_ACCESS_has_more( in table ) {}

/**
 *	Returns the name of the next test to be processed. More specifically, it
 *	returns the next available name from the set of names that were specified 
 *	by the DDT_ACCES_init() command.
 * @param table (in)	the name od the iterator (data table)
 * @return
 *	!"":	a test name
 *	"":		error ocurred
 */

public String DDT_ACCESS_get_next( in table ) {}

/**
 *	Analyses the given string <code>tests</code> and builds an array that contains all
 *	individual values that were specified.
 * @param table (in)	table dimension
 * @param tests (in)	test names in format (1,3-9) 
 * @return
 	number of tests to be processed
 */

protected String init_count( in table, in tests ) {}

protected String get_range( in from, in to, out x, out y, out b ) {}

/**
 *	This function deletes a dimension from a multidimensional array.
 *<p>WARNING!<p>
 *	It will delete ALL dimensions with the same name. For example
 *   assume your three-dimensional array "arr" has the following content:
 *<pre>		arr["x",1,"x"] = "x1x";
 *		arr["x",1,"y"] = "x1y";
 *		arr["y",1,"x"] = "y1x";
 *		arr["y",1,"y"] = "y1Y";
 *</pre>
 *	then the following command
 *<pre>		delete_dimension( arr, "x" );</pre>
 *	will delete everything BUT
 *<pre>		arr["y",1,"y"] = "y1Y";</pre>
 * @param arr (inout)	array to be processed
 * @param dim (in)	dimension(s) to be removed
 */

protected String delete_dimension( inout arr [], in dim ) {}

/**
 *	Test with valid values
 */

protected String col_test_valid(  ) {}

/**
 *	Test with invalid values
 */

protected String col_test_invalid(  ) {}

}
