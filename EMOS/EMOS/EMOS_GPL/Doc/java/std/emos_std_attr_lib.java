package std;
import mode.*;
import stereotype.*;

/**
 * This library implements the iterator ot the attribute list that is used
 * in framework's ATR mode.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_attr_lib_tsl.html" target=tsl>emos_std_attr_lib.tsl</A>
*/

public class emos_std_attr_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_attr_lib (){}
/**
 *
 */

public String EMOS_ATTR_init_list( in list ) {}

/**
 *
 */

public String EMOS_ATTR_has_more(  ) {}

/**
 *
 */

public String EMOS_ATTR_get_next( out attr, out info ) {}

}
