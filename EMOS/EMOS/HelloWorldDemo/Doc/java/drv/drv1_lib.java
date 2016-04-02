package drv;
import mode.*;
import stereotype.*;

/**
 *	This main test implements a "dummy" step driver which you may use to
 *	implement the real one.
 *
 *	The purpose of a step driver is to interpret the corresponding "Teststep"
 *	cell of each test case. With "interpreting" we mean the process of splitting
 *	the cell (Teststep) into individual steps and individually processing
 *	each of them either by a built-in FRM functionality (e.g. LNK or EXE steps)
 *	or by calling the specialised function.
 *
 *	This "dummy" template implements all the necessary interractions with the
 *	emos_FRM_STP_lib and very carefully handles the return code in order to
 *	produce correct WinRunner test results. All you need to do is to load the
 *	apropriate libraries and handle the individual keywords (step names).
 *
 * <p>NOTE:
 *	Do not rename the function names because tey are called from
 *	the generic EMOS test driver
 *
 * <p>REQUIREMENTS/PREREQUISITES:
 *	The rest of the test suite should be developed according to FRM-principles
 *	in order to make any use of this script.
 *
 * <p>RETURN VALUE:
 *	<ul>
 *	<li><b>0</b>:		successfull completion</li>
 *	<li><b>&gt;0</b>:		unsuccessfull comletion</li>
 *	</ul>
 
 @stereotype compiled module
 @tsl <A HREF= "drv1_lib_tsl.html" target=tsl>drv1_lib.tsl</A>
*/

public class drv1_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private drv1_lib (){}
/**
 * Implements the test reporting logic.
 */

public String AUT_DRV_report( in tid, in test ) {}

/**
 * Load the necessary LIBs & GUIs here
 * <p>NOTE:
 *	You should use FRM_load_XXX() instead of ordinry load().
 *	This way you enable EMOS Framework to manage the libs and automatically
 *	unload them when they are not needed any more.
 */

public String AUT_DRV_load( in tid, in test ) {}

/**
 * Initialises the test steps.
 * <p>NOTE:
 *	Use the third parameter to customise the name of the Testsequence row.
 */

public String AUT_DRV_init_steps( in tid, in test ) {}

/**
 * Implements the test keywords, i.e. links the names of the test blocks
 * with te corresponding block functions.
 */

public String AUT_DRV_call_block( in tid, in test, in step, inout mode ) {}

}
