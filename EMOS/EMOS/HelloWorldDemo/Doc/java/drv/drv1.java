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
 *	You should be aware of the fact	that this scripts typically call themselves
 *	recursively and very much care is necessary in order to properly propagate 
 *	the return code (i.e. indication of failure) up the call chain. Therefore
 *
 *	<p>DO NOT MODIFY THE CODE OUTSIDE OF DESIGNATED AREAS UNLESS
 *	<ul> 
 *	<li>you know what you are doing,</li>
 *	<li>you have to fix a bug (in this case please let us know) or</li>
 *	<li>you are prepared to live with the consequences.</li>
 *	</ul>
 * <p>REQUIREMENTS/PREREQUISITES:
 *	The rest of the test suite should be developed according to FRM-principles
 *	in order to make any use of this script.
 *
  <p>PARAMETERS:
 *	<ul>
 *	<li><b>table</b>:		the name of the opened data table</li>
 *	<li><b>test</b>:		the name of the individual test case</li>
 *	<li><b>reset_rc</b>:	flag indicating whether this is the first or a recursive
 *				invocation of this script.<li>
 *</ul>
 * <p>RETURN VALUE:
 *	<ul>
 *	<li><b>0</b>:		successfull completion</li>
 *	<li><b>&gt;0</b>:		unsuccessfull comletion</li>
 *	</ul>
 
 @stereotype main test
 @tsl <A HREF= "drv1_tsl.html" target=tsl>drv1.tsl</A>
*/

public class drv1 extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private drv1 (){}
}
