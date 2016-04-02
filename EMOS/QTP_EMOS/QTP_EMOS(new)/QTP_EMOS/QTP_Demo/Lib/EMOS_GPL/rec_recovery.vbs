' -------------------------------------------------
' you need to implement a function to recover from an error
' -----------------------------------------------
function rec_clean_testenv()

	dim rc : rc = micPass
	
	rec_clean_testenv = rc

end function

' ---------------------------------------------
' if rec_clean_testenv() is not working, then
' you can call a different function.
' --------------------------------------------
function rec_recover_from_error()

	rec_recover_from_error = rec_clean_testenv()
	
end function