sub load_libs()

	' load all the global variables
	call executefile("..\..\Data\global_vars.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "..\..\Data\global_vars.vbs", "")
	
	' load all the external functions
	call executefile("..\..\Lib\EMOS_GPL\extern_funcs.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "..\..\Lib\EMOS_GPL\extern_funcs.vbs", "")
	
	' to access xml repository
	call executefile("..\..\Lib\DescOR.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "DescOR", "")
	
	' wrapper functions for QTP build-in functions
	call executefile("..\..\Lib\EMOS_GPL\emos_frm_obj_lib.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "obj_lib", "")
	
	' login-related functions
	call executefile("..\..\Lib\EMOS_GPL\gen_general.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "gen_lib", "")
	call executefile("..\..\Lib\EMOS_GPL\rec_recovery.vbs")
	if DEBUGGING then call reporter.reportevent(micDone, "rec_lib", "")
				
end sub

