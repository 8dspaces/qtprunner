package ddt;
import mode.*;
import stereotype.*;

/**
 * @tsl <A HREF= "emos_ddt_lib_tsl.html" target=tsl>emos_ddt_lib.tsl</A>
 */
public class emos_ddt_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_ddt_lib (){}
/**
 * Setter function for sheet separator sign (default: #).
 */

public String ddt_set_name_sep( in sep ) {}

/**
 * Getter function for sheet separator sign.
 */

public String ddt_get_name_sep(  ) {}

/**
 *	Opens the given Excel table. This function supports access to multiple Excel
 *	sheets by appending "#&lt;sheet_name&gt;" to &gt;table&lt; name, e.g.
 *<pre>
 *		DDT_open_table( "C:\\some_dir\\table.xls#sheet1" );
 *</pre>
 * @param table (in)	table name
 * @param mode (in)	(optional) DDT_MODE_READ or DDT_MODE_READWRITE [defult: DDT_MODE_READ]
 * @return
 *	E_OK:	operation successful
 *	!E_OK:	operation failed
 */

public String DDT_open_table( in table, in mode ) {}

/**
 *	Closes the table. This routine now parses the table name and removes the
 *	sheet specification if present. This way we ensure that underlying ddt_close()
 *	will not fail due to multiple sheet support integrated in DDT_open_table().
 * @param table (in)	table name
 * @return
 *	E_OK:	operation succeeded
 *	!E_OK:	operation failed
 */

public String DDT_close_table( in table ) {}

/**
 *	Displays a dialog wher you can either choose the default table or display
 *	the file-browser and pick any file you want.
 * @param def_path (in)	name of the default table (full path!)
 * @param dir (out)		name of the chosen directory
 * @param name (out)		name of the chosen file
 * @return
 *	E_OK:		table chosen ( output variables filled )
 *	E_BAD_PATH:	oeration failed
 */

public String DDT_choose_table( in def_path, out dir, out name ) {}

/**
 *	A special-purpose function. It wraps around a ddt_val function by parsing
 *	the retrieved value. If the value begins with the particular substring
 *	(default: ?), then a dialog is displayed where the retrieved value may
 *	be modified before being sent to further processing.
 * @param table (in)	table name (full path)
 * @param param (in)	column name
 * @param msg (in)		message to be displayed in case the substring is found
 * @param flag (in)	(optional) substring that triggers the dialog [default: ?]
 * @return
 *	value (either modified or unmodified) which is to be further processed
 */

public String DDT_ask( in table, in param, in msg, in flag ) {}

/**
 * Changes the active sheet in the given excel file.
 * An Excel file contains number of worksheets. Only one among them will be active.
 * Winrunner access only this active sheet. 
 * This function uses excel automation to implement the following algorithm: 
 * <pre> 
 * 				1. Create an instance of Excel application
 * 				2. Open the given file in the application
 * 				3. Iterate through each worksheet in the file
 * 				4. If the worksheet name matches with the given Sheetname
 * 					Then Set the sheet to Active
 * 				5. Save the excel file
 * 				6. Close Excel application
 *</pre> 
 * Usage : SetExcelSheet ( "C:\sample.xls", "Sheet2");
 * @param table (in)  table name/path
 * @param sheet (in) sheet name
 * @return	
 * 	-1	Couldn't Open Excel Application
 * 	-2	File not found
 * 	-4	UnExpected Error
 * 	-3	Sheet not found
 */

public extern._int SetExcelSheet( in._string noName, in._string noName ) {}

/**
 * Selects the specified sheet in the given Excel table.
 * @param excel_filename (in) name/path of the Excel table
 * @param excel_sheetname (in) name of the excel shhet to be selected
 * @return
 *	E_OK	(0)	Operation succesfull
 *	E_FILE_OPEN	(-10007)	Cannot open file. File may already be open.
 *	E_SHARING_VIOLATION	(-10041)	Sharing violation (i.e. file opened by another app)
 *	E_FILE_NOT_FOUND	(-10033)	File not found.
 * 	E_NO_EXCEL	(-20661)	Couldn't Open Excel Application
 * 	E_NO_EXCEL_FILE	(-20662)	File not found or not an excel file
 * 	E_NO_EXCEL_SHEET	(-20663)	Sheet not found
 * 	E_EXCEL_ERROR	(-20664)	UnExpected Error
 *  other errors possible
 */

public String ddt_set_excel_sheet( in excel_filename, in excel_sheetname ) {}

}
