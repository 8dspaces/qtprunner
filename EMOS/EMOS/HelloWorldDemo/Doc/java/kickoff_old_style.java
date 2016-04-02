import mode.*;
import stereotype.*;

/**
 * This is an example of a script that triggers the
 * processing of a test suite table (hence "kickoff"). 
 * <p><b>IMPORTANT!</b></p>
 * By default this suite executes the table defined 
 * in the script (you must read the code to find out 
 * which one).
 * Productive suites should be kept stable and under 
 * version control (i.e. read-only). They should 
 * define <b>the complete</b> set of tests for the 
 * particular test purpose. 
 * Ensuring that suite tables are kept up to date 
 * will allow you to see what was intended with the 
 * test suite at any point in time.
 * <p>
 * During the development of the test suite or in a 
 * multi-user environment it is however often needed 
 * to execute only  portions of the whole test suite. 
 * One way to achieve this is by creating individual 
 * suite table and play with it instead of constantly
 * changing the production suite.
 * <p>
 * You can redirect this script to use your private 
 * table by allocating the environment variable 
 * <code>MY_SUITE</code> and assiging the appropriate
 * value to it (the name of your suite table relative
 * to the DATA_HOME directory, e.g. mysuite.xls). 
 * You must remove the variable when you are ready to 
 * execute the real suite. 
 
 @stereotype main test
 @tsl <A HREF= "kickoff_old_style_tsl.html" target=tsl>kickoff_old_style.tsl</A>
*/

public class kickoff_old_style extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private kickoff_old_style (){}
}
