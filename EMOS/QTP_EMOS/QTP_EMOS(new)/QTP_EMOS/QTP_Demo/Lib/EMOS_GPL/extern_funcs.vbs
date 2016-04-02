' ---------------------------------------------------------------
' declare external functions
' note: csolib2.dll and clipboard.dll need to be in c:\windows\system32\
' -----------------------------------------------------------------

' ------------------------------------------------------------------------
' capture screen shot and save it as .bmp file
' @param micString (in) output img file name
' @param micString (in) "Full" - capture the desktop image
' 											"Active" - capture the image of the active window
' -------------------------------------------------------------------------
call Extern.Declare(micInteger, "CaptureBmpToFile", "csolib2.dll", "CaptureBmpToFile", micString, micString)