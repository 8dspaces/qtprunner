package std;
import mode.*;
import stereotype.*;

/**
 * This library implements the dummy interface to EMOS remote logging functionallity for WinRunner.
 * The real interface is available through a product EMOS WrLog.
 
 @stereotype compiled module
 @tsl <A HREF= "emos_std_wrlog_lib_tsl.html" target=tsl>emos_std_wrlog_lib.tsl</A>
*/

public class emos_std_wrlog_lib extends CompiledModule
{
	//constructor made private to prevent it from appearing in javadoc
	private emos_std_wrlog_lib (){}
public String wrlog_init_tcp_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_init_file_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_init_pipe_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_terminate_tcp_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_terminate_file_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_terminate_pipe_interface( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String log_txt( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String log_cmd( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_print( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_println( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_suite_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_suite_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_suite_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_suite_tick( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_set_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_set_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_set_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_test_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_test_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_test_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_block_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_block_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_block_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_prim_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_prim_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_prim_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_prim_obj_info( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_prim_frm_info( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_debug_call_info( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_timer_start( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_timer_data( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_timer_stop( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

public String wrlog_timer_tick( in dummy1, in dummy2, in dummy3, in dummy4, in dummy5, in dummy6 ) {}

}
