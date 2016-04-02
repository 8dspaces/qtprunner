package frm.tpl.scripts.drv;
import mode.*;
import stereotype.*;

/**
 * A simple driver for a single test set. This driver was designed to be used
 * with TestDirector. The idea is that we could create test sets in TestDirector
 * and replace with them the Frameworks concept of "test suite". So a test set
 * in TestDirector could act a counterpart of one test suite. What we need to do
 * is create automate tests within the test subject tree that all prepare the
 * required three parameters and call this test.
 
 @stereotype main test
 @tsl <A HREF= "emos_testset_driver_tsl.html" target=tsl>emos_testset_driver.tsl</A>
*/

public class emos_testset_driver extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_testset_driver (){}
}
