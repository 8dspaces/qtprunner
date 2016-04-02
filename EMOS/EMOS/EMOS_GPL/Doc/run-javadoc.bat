dir /s/b/l java\*.java >files
set JDK_HOME=\programme\java\j2re1.4.2_03\bin\

"%JDK_HOME%java" ^
	-classpath ".;%WRDOC_HOME%\WrDoc.jar" ^
com.sun.tools.javadoc.Main ^
 	-sourcepath "java" ^
 	-d "api-doc" ^
 	-windowtitle "EMOS Framework API Documentation" ^
 	-doctitle "EMOS Framework API Documentation" ^
 	-header "<b>FRM </b><br><font size=\"-1\">v1.4.2</font>" ^
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
 	-group "Framework Core" "frm:ddt:std" ^
 	-group "Template Project" "frm.tpl.scripts:frm.tpl.scripts.drv:frm.tpl.scripts.lib.frm" ^
 	-noqualifier all ^
 	-version ^
 	-author ^
 	-nohelp ^
 	@files
pause