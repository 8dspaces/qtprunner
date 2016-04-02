' --------------------------------
' add functions that are used by
' the rest of the scripts
'
' created by: sahokoh
' --------------------------------

' -------------------------------------------
' launch ie and navigate to the specified url
' @param url (in)
' -------------------------------------------
function gen_launch_ie( url )

	dim rc : rc = micPass
	
	call systemutil.run( "C:\Program Files\Internet Explorer\IEXPLORE.EXE",url,"","" )
	
	gen_launch_ie = rc

end function

' ------------------------
' go to test env
' --------------------
sub gen_launch_testenv()

	dim url, rc
	
	' get test env and store it as env var
	url = gen_get_testurl()
	call gen_add_env_var("url", url)
	
	' launch ie
	gen_launch_ie(url)

end sub

' ----------------------
' get test url from user input
' note: you need to have the following libs installed on the test machine
'			  1 - VB6 Runtime Files
' 		  2 - SCRRUN.DLL v. 5.1: on xp, the file should be in c:\windows\system32\
' 		  3 - JSListVw.dll in c:\windows\system32\ - needs to be registered
' -------------------------
function gen_get_testurl()

	dim obj, arr_urls, url
	
' test env urls
	arr_urls = array("http://newtours.mercuryinteractive.com")
									 
	' create a listbox
	set obj = createobject("jslistvw.list")
	
	' add each url to the listbox
	for each url in arr_urls
		call obj.addtolist(url)
	next
	
	' show the list
	' getselitem() will return the selected item when ok button is clicked
	call obj.show()
	url = obj.getselitem()
	
	set obj = nothing
	
	gen_get_testurl = url
	
end function


' ------------------------
' activate browser
' ---------------------
sub gen_activate_browser()

	dim hwnd
	
	hwnd = Browser("MainBrowser").GetROProperty("hwnd") 
	Window("hwnd:=" & hwnd).Activate 
	
end sub

' --------------------------------
' get the number of parameters
' ------------------------------
function gen_get_paramcount()

	gen_get_paramcount = datatable.getsheet("Global").getparametercount
				 
end function

' -----------------------------------------------------------------
' store all the parameter names in an array
' @param param_names (inout) an array that stores param names
' @param param_count (in) number of parameters
' note: The index for geparameter() starts from 1.
' ----------------------------------------------------------------
function gen_get_paramnames( byRef param_names, param_count )
			 
	redim param_names( param_count - 1 )
					 
	for i=0 to ubound(param_names)
		param_names(i) = datatable.getsheet("Global").getparameter(i+1).name
	next
					 
	gen_get_paramnames = param_names
				 
end function

' --------------------------------------------------------------
' wait for the object to appear
' @param obj (in) the name of the object
' @param obj (in) the number of times that the for loop iterates
' ---------------------------------------------------------------
function gen_wait( obj, counter )
	
	dim rc : rc = micPass
	dim i
	
	for i=1 to counter
		if Object(obj).Exist(2) then
		   exit for
	   end if	   
  next
  
  if i > counter then
  	rc = micFail
  end if

  gen_wait = rc
  
end function

' --------------------
' increment index by 1
' @param i (inout) index
' ------------------------
function gen_increment( byRef i )

	i = i + 1
	gen_increment = i
	
end function

' --------------------------
' add environment variables
' @param variable (in) variable name
' @param val (in) value
' ---------------------------
sub gen_add_env_var( variable, val )
	
	environment.value(cstr(variable)) = val
	
end sub

' -------------------
' get an env variable
' ----------------------
function gen_get_env_var( variable )

	gen_get_env_var = environment.value(cstr(variable))
	
end function

' ------------------
' get a us city code
' ------------------
function gen_get_citycode ()

  dim i, city_code
  
  randomize
  i = ((14 - 1 + 1) * Rnd + 1)
  i = CInt(i)

  select case i
    case 1 city_code = "SEA"
    case 2 city_code = "LAX"
    case 3 city_code = "SFO"
    case 4 city_code = "PDX"
    case 5 city_code = "DEN"
    case 6 city_code = "ORD"
    case 7 city_code = "SLC"
    case 8 city_code = "DFW"
    case 9 city_code = "ATL"
    case 10 city_code = "LGA"
    case 11 city_code = "BOS"
    case 12 city_code = "JFK"
    case 13 city_code = "GSO"
    case 14 city_code = "MIA"
    case 15 city_code = "MKE"
  end select

  gen_get_citycode = city_code
  
end function

' ---------------------
' convert to uk date format
' @param (in) date in the us format
' ---------------------------
function gen_get_ukdate( us_date )
	dim arr_dates, temp_month, uk_date
	
	arr_dates = split(us_date, "/")
	
	temp_month = arr_dates(0) ' keep month
	
	arr_dates(0) = arr_dates(1) ' swap month and day
	arr_dates(1) = temp_month ' put back month
	
	uk_date = join(arr_dates, "/")
	
	gen_get_ukdate = uk_date

end function

' -----------------
' get us date
' -----------------
function gen_get_date( days )

	dim rc_date, gds
	
	gds = environment.value("gds")
	if gds = "galileo" then
		rc_date = gen_get_ukdate( cstr(date + days) )
	else
		rc_date = date + days
	end if
		
	gen_get_date = rc_date

end function

' ------------------------------------------------------------------
' get the screen shot of the desktop
' param@ file_name (in) absolute path of the output bmp file name
' -----------------------------------------------------------
function gen_get_screenshot( file_name )

	dim rc
	rc = Extern.CaptureBmpToFile(file_name, "Full")
	
	' need to convert rc to type int explicitly, otherwise QTP will complain
	if cint(rc) = 0 then
		rc = micPass
	else
		rc = micFail
	end if
	
	gen_get_screenshot = rc
	
end function

' -------------------------
' run ParserHTML.exe
' ------------------------
function gen_parse_log()

	dim log_file, temp_file : temp_file = LOG_PATH & "\temp.html"
	dim final_file : final_file = LOG_PATH & "\LogFile_Final.html"
	dim parse_html : parse_html = LOG_PATH & "\ParseHTML.exe"
	log_file = reporter.ReportPath & "\Log\LogFile.html"
	
	if Browser("MainBrowser").Exist then
		Browser("MainBrowser").Close
	end if
	
	' launch the parser
	call systemutil.Run(parse_html, "", "", "")
	
	' parse - I can't add these objs to xml file, so they're in main.tsr
	'VbWindow("frmParse").activate
	VbWindow("frmParse").VbEdit("txtInputFile").Set log_file
	VbWindow("frmParse").VbEdit("txtOutputFile").Set temp_file
	VbWindow("frmParse").VbEdit("txtFilterOn").Set "zzzzz"
	VbWindow("frmParse").VbCheckBox("Open Output").Set "OFF"
	VbWindow("frmParse").VbButton("Parse").Click
	if VbWindow("frmParse").Dialog("ParseHTML").WinButton("Yes").Exist then
		VbWindow("frmParse").Dialog("ParseHTML").WinButton("Yes").Click
	end if
	
	VbWindow("frmParse").Dialog("ParseHTML").WinButton("OK").Click
	VbWindow("frmParse").Close
	
	wait(MIN_WAIT)
	
	call gen_add_link(temp_file, final_file)
	
	gen_parse_log = final_file
	
end function

' -----------------------------
' modify the parsed logfile
' and add link to screen shot
' -----------------------------
sub gen_add_link( byRef logfile, byRef new_logfile)

	dim fso, f
	dim ln, all, newAll
	dim i, args
	
	dim reg_exp, match, matches, pattern : pattern = "\S+.bmp"
	dim new_match
	
	set reg_exp = new regexp
	reg_exp.global = true
	reg_exp.pattern = pattern
		
		
		set fso = createobject("scripting.filesystemobject")
		set f = fso.opentextfile(logfile, ForReading, false)
		all = split(f.readall, vbnewline)
		
		' ParseHTML.exe is also grabbing the steps made by this statement
		' VbWindow("frmParse").VbEdit("txtFilterOn").Set "zzzzz"
		' So I'm just cleaning them up, so that they won't appear in the final log file
		i = ubound(all)
		do until( i = ubound(all) - 14 )
			all(i) = ""
			i = i - 1
		loop
		
		
		' find details col
		for i=0 to ubound(all)
			if instr(all(i), "<TD>") <> 0 then
				if instr(all(i), ".bmp")<>0 then
					set matches = reg_exp.execute(all(i))
					for each match in matches
						new_match = mid(match.value, 5)
						all(i) =  replace(all(i), new_match, "<a href=" & chr(34) & new_match & chr(34) & "</a>see the screen shot")
					next
				end if
			end if
		next
		
		newAll = join(all,vbNewline) ' Build a new string from the arrays to be the whole document.
		f.Close
		
		set f = fso.OpenTextFile(new_logfile, ForWriting, true)
		f.Write((newAll)) 'Write the new document to the file.
		f.Close

end sub

sub gen_show_logfile()

	dim final_file
	
	' parse the log file
	final_file =  gen_parse_log()
	
	' get the absolute path for the final log file
	'final_file = gen_get_filepath(final_file)
	
	if rc = micPass then
		gen_launch_ie( final_file )
	end if 

end sub

' ------------------
' return the specified file's path
' @param file name
function gen_get_filepath( filename )

	dim fs, f, file_path
	
	set fs = createobject("scripting.filesystemobject")
	set f = fs.getfile(filename)
	
	file_path = f.path
	
	set f = nothing
	set fs = nothing
	
	gen_get_filepath = file_path
	
end function