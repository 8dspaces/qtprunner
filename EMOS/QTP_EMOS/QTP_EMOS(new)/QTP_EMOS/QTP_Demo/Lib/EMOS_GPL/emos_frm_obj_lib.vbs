' -----------------------------------------------------
' wrapper functions for QTP-defined Objects
' you need to use the XML Object Repository (XMLOR) for them to work
'
' note: XMLOR takes care of all the object definitions, so these functions
' 			should work for any object types as long as the object supports the 
' 			function you want to call.
'  
' created by: Sahoko Hama & Dave Longoria

function FRM_Obj_ChildItem( obj, row, col, obj_type, index, byRef child_item )

	dim rc : rc = micPass

	if Object(obj).Exist then
		set child_item = Object(obj).ChildItem( row, col, obj_type, index )
	else
		rc = micFail
	end if
	
	FRM_Obj_ChildItem = rc
	
end function

function FRM_Obj_Click( obj )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Click
	else
		rc = micFail
	end if
	
	FRM_Obj_Click = rc

end function 

function FRM_Obj_Close( obj )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Close
	else
		rc = micFail
	end if
	
	FRM_Obj_Close = rc
	
end function

function FRM_Obj_Exist( obj )

	FRM_Obj_Exist = Object(obj).Exist

end function

function FRM_Obj_FireEvent( obj, val )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).FireEvent val
	else
		rc = micFail
	end if
	
	FRM_Obj_FireEvent = rc
	
end function

function FRM_Obj_GetROProperty( obj, property, byRef str )

	dim rc : rc = micPass
	
	if Object(obj).Exist then		
		str = Object(obj).GetROProperty( property )
	else
		rc = micFail
	end if
	
	FRM_Obj_GetROProperty = rc
	
end function

function FRM_Obj_RowCount( obj, byRef row_num )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		row_num = Object(obj).RowCount
	else
		rc = micFail
	end if
	
	FRM_Obj_RowCount = rc
	
end function

function FRM_Obj_Select( obj, val )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Select val
	else
		rc = micFail
	end if
	
	FRM_Obj_Select = rc

end function 

function FRM_Obj_Set( obj, val )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Set val
	else
		rc = micFail
	end if
	
	FRM_Obj_Set = rc

end function

function FRM_Obj_SetSecure( obj, val )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).SetSecure val
	else
		rc = micFail
	end if
	
	FRM_Obj_SetSecure = rc
end function

function FRM_Obj_Submit( obj )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Submit
	else
		rc = micFail
	end if
	
	FRM_Obj_Submit = rc

end function

function FRM_Obj_Sync( obj )

	dim rc : rc = micPass
	
	if Object(obj).Exist then
		Object(obj).Sync
	else
		rc = micFail
	end if
	
	FRM_Obj_Sync = rc

end function



