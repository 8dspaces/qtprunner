package frm;
import mode.*;
import stereotype.*;

/**
 * This test processes the test suite table. A <code>test suite table</code> is
 * an Excel table that defines what tests and in what order should they be run.
 * For this to work there are four pieces if information necessary:
 * <ul>
 * <li><code>doit flag</code> stating wether to process or ignore the row</li>
 * <li><code>test script</code> to be executed</li>
 * <li><code>test table</code> containing test data to be used by the script</li>
 * <li><code>test set</code> the test cases from the test table to be executed</li>
 * </ul>
 * <p>NOTE<p>
 * If <code>doit flag</code> and/or <code>test set</code> are preceeded by "?", 
 * in interactive mode you are given the chance to superseed the given value.
 * <p>The first two collumns mut be titeld "IDX" and "Name" because we use the
 * FRM-interface to process the table (which requires this two columns). You may
 * leave them empty and (we prefer it so) hide them.
 * <p>Example<p>
 * <table border>
 *   <tr>
 *     <th>IDX</th>
 *     <th>Name</th>
 *     <th>Bearbeiten?</th>
 *     <th>Testscript</th>
 *     <th>Testtabelle</th>
 *     <th>Testset</th>
 *     <th>Kommentar</th>
 *   </tr>
 *   <tr>
 *     <td>&#160;</td>
 *     <td>&#160;</td>
 *     <td>ja</td>
 *     <td>the_script</td>
 *     <td>some\table.xls</td>
 *     <td>1-5</td>
 *     <td>Tests 1,2,3,4,5 will be executed</td>
 *   </tr>
 *   <tr>
 *     <td>&#160;</td>
 *     <td>&#160;</td>
 *     <td>?ja</td>
 *     <td>the_script</td>
 *     <td>some\other\table.xls</td>
 *     <td>?1-3,7,9,a,b,c</td>
 *     <td>Test names can also be non-numeric, a popup dalog will appear twice</td>
 *   </tr>
 * </table>
 * <p>Parameters<p>
 * <ul>
 * <li><b>script_home</b>: home directory for test scripts</li>
 * <li><b>data_home</b>: home directory for test data</li>
 * <li><b>default_suite_table</b>: name of the test suite table</li>
 * <li><b>ask</b>: (optional) TRUE: gives you the option to choose 
 * alternative suite table; FALSE: opens the defined table only [default]
 * <li><b>columns</b> (optional) comma-separated string defining the
 * titles of the four important columns 
 * [default: <pre>"Bearbeiten?,Testscript,Testtabelle,Testset"</pre>]</li>
 * </ul>
 * <p>Parameters<p>
 * <ul>
 * <li><b>E_OK</b>: success</li>
 * <li><b>!E_OK</b>: failure</li>
 * </ul>
 
 @stereotype main test
 @tsl <A HREF= "emos_frm_driver_tsl.html" target=tsl>emos_frm_driver.tsl</A>
*/

public class emos_frm_driver extends MainTest
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_frm_driver (){}
}
