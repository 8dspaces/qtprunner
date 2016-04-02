dir /s/b/l java\*.java >files

c:\Programme\java\j2re1.4.2_03\bin\java ^
	-classpath ".;%WRDOC_HOME%\WrDoc.jar" ^
com.sun.tools.javadoc.Main ^
 	-sourcepath "wrdoc;java" ^
 	-d "api-doc" ^
 	-windowtitle "EMOS Framework Template Project Documentation" ^
 	-doctitle "EMOS Framework Template Project Documentation" ^
 	-header "<b>FRM TemplateProject</b><br><font size=\"-1\">v1.4.2</font>" ^
 	-bottom "" ^
 	-overview "java\overview.html" ^
 	-stylesheetfile "wrdoc.css" ^
 	-tag stereotype:X:Stereotype ^
 	-tag tsl:t:Tsl ^
 	-tag pre:t:Precondition ^
 	-tag post:t:Postcondition ^
 	-tag input:t:Input ^
 	-tag output:t:Output ^
 	-tag status:t:Status ^
 	-group "Compiled Modules" "lib:lib.frm" ^
 	-group "Drivers" "drv" ^
 	-noqualifier all ^
 	-version ^
 	-author ^
 	-nohelp ^
 	@files
pause