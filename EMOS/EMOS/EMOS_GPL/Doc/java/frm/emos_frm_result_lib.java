package frm;
import mode.*;
import stereotype.*;

/**
 * Simple routines for calculating test statistics.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_frm_result_lib_tsl.html" target=tsl>emos_frm_result_lib.tsl</A>
*/

public class emos_frm_result_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_result_lib (){}
/**
 * Initialises the array with test statistics (by cleaning it up).
 */

public String FRM_RES_init_test_statistics(  ) {}

/**
 * Returns the array containig test statistics.
 * The format of the array is not defined.
 * It will usually contain only one dimension in form of
 * <code>array["category"]</code> where "category" is
 * defined via FRM_RES_add_test_statistics( in category ).
 * @return array of test statistics.
 */

public String FRM_RES_get_test_statistics( out arr [] ) {}

/**
 * Increases the test statistics for the specified category by one.
 * @param category (in) name of the category
 */

public String FRM_RES_add_test_statistics( in category ) {}

/**
 * Produces the most simple report on test statistics using "report_msg()".
 * This function cummulates the total over all categories and reports
 * the percentage for each category (i.e. it assumes the categories are
 * non-overlapping).
 * Overload this function with your own one to produce better report.
 */

public String FRM_RES_report_test_statistics(  ) {}

}
