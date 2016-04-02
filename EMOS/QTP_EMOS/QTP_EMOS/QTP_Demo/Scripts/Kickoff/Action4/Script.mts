dim rc : rc = micPass

select case lcase( parameter("aut_func_name") )
    Case  "aut_drv_call_block"
        rc =  AUT_DRV_call_block( parameter("arg1") )
 end select

exitaction(rc)


' -----------------------------------------------------------
' execute action
' @param test_step (in) the name of the action to execute
' -----------------------------------------------------------
function AUT_DRV_call_block( test_step )
	If DEBUGGING then call reporter.reportevent(micDone, "AUT_DRV_call_block( test_step ) started", test_step)

	dim rc : rc = micPass
	
	select case lcase(test_step)
		case "login"
            rc = log_login()
		case "find flights"
            rc = src_find_flights()
		case "select a flight"
            rc = slt_select_flight()
		case "book a flight"
            rc = bk_book_flight()
		case   "logoff"
            rc = cf_logoff()
		case else
			rc = micFail
	end select
	
	AUT_DRV_call_block =  rc
	
end function

' ----------------------
' log into Mercury Tour
' ----------------------
function log_login()

	dim objs, obj_username, obj_password, obj_singin_btn
    dim param_count, i : i = 0
	dim username, password, url
	dim rc : rc = micPass
	dim counter : counter = 20
	
	param_count = gen_get_paramcount()
	
	call gen_get_paramnames( objs, param_count )
	obj_username = objs( gen_increment(i) )
	obj_password = objs( gen_increment(i) )
	obj_signin_btn = objs( gen_increment(i) )
	
	username = datatable(obj_username, dtglobalsheet)
	password = datatable(obj_password, dtglobalsheet)
	
	rc = gen_wait("Login", counter)

	if rc = micPass then 
		rc = gen_wait(obj_username, counter)
	end if
	
	if rc = micPass then rc = FRM_Obj_Set( obj_username, username )
	if rc = micPass then rc = FRM_Obj_Set( obj_password, password )
	if rc = micPass then rc = FRM_Obj_Click( obj_signin_btn)
	
	log_login = rc
	
end function

' search functions
'
' QTP defines these consts internally:
' micPass=0
' micFail=1
'
' created by: Sahoko Hama

' ------------------------
' find flights

function src_find_flights()

	dim rc : rc = micPass
	dim param_count, i : i = 0
	dim objs
	dim obj_type, obj_passengers
	dim obj_from, obj_from_month, obj_from_date
	dim obj_to, obj_to_month, obj_to_date
	dim obj_service_class, obj_airline
	dim obj_continue_btn
	
	dim travel_type, passengers
	dim from, from_month, from_date
	dim dest, to_month, to_date
	dim service_class, airline
	
	param_count = gen_get_paramcount()
	call gen_get_paramnames( objs, param_count )
	
	obj_type = objs( gen_increment(i) )
	obj_passengers = objs( gen_increment(i) )
	obj_from = objs( gen_increment(i) )
	obj_from_month = objs( gen_increment(i) )
	obj_from_date = objs( gen_increment(i) )
	obj_to = objs( gen_increment(i) )
	obj_to_month = objs( gen_increment(i) )
	obj_to_date = objs( gen_increment(i) )
	obj_service_class = objs( gen_increment(i) )
	obj_airline = objs( gen_increment(i) )
	obj_continue_btn = objs( gen_increment(i) )
	
	travel_type = datatable(obj_type, dtglobalsheet)
	passengers = datatable(obj_passengers, dtglobalsheet)
	from = datatable(obj_from, dtglobalsheet)
	from_month = datatable(obj_from_month, dtglobalsheet)
	from_date = datatable(obj_from_date, dtglobalsheet)
	dest = datatable(obj_to, dtglobalsheet)
	to_month = datatable(obj_to_month, dtglobalsheet)
	to_date = datatable(obj_to_date, dtglobalsheet)
	service_class = datatable(obj_service_class, dtglobalsheet)
	airline = datatable(obj_airline, dtglobalsheet)
	
	' select search params and go
	if rc = micPass then rc = FRM_Obj_Select(obj_type, travel_type)
	if rc = micPass then rc = FRM_Obj_Select(obj_passengers, passengers)
	if rc = micPass then rc = FRM_Obj_Select(obj_from, from)
	if rc = micPass then rc = FRM_Obj_Select(obj_from_month, from_month)
	if rc = micPass then rc = FRM_Obj_Select(obj_from_date, from_date)
	if rc = micPass then rc = FRM_Obj_Select(obj_to, dest)
	if rc = micPass then rc = FRM_Obj_Select(obj_to_month, to_month)
	if rc = micPass then rc = FRM_Obj_Select(obj_to_date, to_date)
	if rc = micPass then rc = FRM_Obj_Select(obj_service_class, service_class)
	if rc = micPass then rc = FRM_Obj_Select(obj_airline, airline)
	if rc = micPass then rc = FRM_Obj_Click(obj_continue_btn)
	
	src_find_flights = rc
	
end function

' ------------------------
' select a flight

function slt_select_flight()

	dim obj_continue_btn
	dim rc : rc = micPass
	dim param_count, i : i = 0
	dim objs
	
	param_count = gen_get_paramcount()
	call gen_get_paramnames( objs, param_count )
	
	obj_continue_btn = objs( gen_increment(i) )
	
	' click the continue button
	if rc = micPass then rc = FRM_Obj_Click(obj_continue_btn)
	
	slt_select_flight = rc

end function


' ----------------------------------
'  cf_logoff()
function cf_logoff()

	dim rc : rc = micPass
	dim param_count
	dim i : i = 0
	dim objs
	
	dim obj_logout_btn
	
	param_count = gen_get_paramcount()
	call gen_get_paramnames( objs, param_count )
	
	obj_logout_btn = objs( gen_increment(i) )
		
	' click the logout button in the confirmation page
	if rc = micPass then rc = FRM_Obj_Click(obj_logout_btn)
	
	cf_logoff = rc
	
end function


' -------------------------------------
'  bk_book_flight()
function bk_book_flight()
	
	dim rc : rc = micPass
	dim param_count, i : i = 0
	dim objs
	
	dim obj_ps_first, obj_ps_last, obj_meal
	dim obj_card_type, obj_card_num, obj_exp_month, obj_exp_date
	dim obj_cc_first, obj_cc_middle, obj_cc_last
	dim obj_bill_address1, obj_bill_address2
	dim obj_bill_city, obj_bill_state, obj_bill_postalcode, obj_bill_country
	dim obj_dl_address1, obj_dl_address2
	dim obj_dl_city, obj_dl_state, obj_dl_postalcode, obj_dl_country
	dim obj_purchase_btn
	
	dim ps_first, ps_last, meal
	dim card_type, card_num, exp_month, exp_date
	dim cc_first, cc_middle, cc_last
	dim bill_address1, bill_address2
	dim bill_city, bill_state, bill_postalcode, bill_country
	dim dl_address1, dl_address2
	dim dl_city, dl_state, dl_postalcode, dl_country
	
	param_count = gen_get_paramcount()
	call gen_get_paramnames( objs, param_count )
	
	obj_ps_first = objs( gen_increment(i) )
	obj_ps_last =  objs( gen_increment(i) )
	obj_meal = objs( gen_increment(i) )
	obj_card_type =  objs( gen_increment(i) )
	obj_card_num =  objs( gen_increment(i) )
	obj_exp_month =  objs( gen_increment(i) )
	obj_exp_date = objs( gen_increment(i) )
	obj_cc_first =  objs( gen_increment(i) )
	obj_cc_middle =  objs( gen_increment(i) )
	obj_cc_last = objs( gen_increment(i) )
	obj_bill_address1 =  objs( gen_increment(i) )
	obj_bill_address2 = objs( gen_increment(i) )
	obj_bill_city =  objs( gen_increment(i) )
	obj_bill_state =  objs( gen_increment(i) )
	obj_bill_postalcode =  objs( gen_increment(i) )
	obj_bill_country = objs( gen_increment(i) )
	obj_dl_address1 =  objs( gen_increment(i) )
	obj_dl_address2 = objs( gen_increment(i) )
	obj_dl_city =  objs( gen_increment(i) )
	obj_dl_state =  objs( gen_increment(i) )
	obj_dl_postalcode =  objs( gen_increment(i) )
	obj_dl_country = objs( gen_increment(i) )
	obj_purchase_btn = objs( gen_increment(i) )
	
	ps_first = datatable(obj_ps_first, dtglobalsheet)
	ps_last =  datatable(obj_ps_last, dtglobalsheet)
	meal = datatable(obj_meal, dtglobalsheet)
	card_type =  datatable(obj_card_type, dtglobalsheet)
	card_num =  datatable(obj_card_num, dtglobalsheet)
	exp_month =  datatable(obj_exp_month, dtglobalsheet)
	exp_date = datatable(obj_exp_date, dtglobalsheet)
	cc_first =  datatable(obj_cc_first, dtglobalsheet)
	cc_middle =  datatable(obj_cc_middle, dtglobalsheet)
	cc_last = datatable(obj_cc_last, dtglobalsheet)
	bill_address1 =  datatable(obj_bill_address1, dtglobalsheet)
	bill_address2 = datatable(obj_bill_address2, dtglobalsheet)
	bill_city =  datatable(obj_bill_city, dtglobalsheet)
	bill_state =  datatable(obj_bill_state, dtglobalsheet)
	bill_postalcode =  datatable(obj_bill_postalcode, dtglobalsheet)
	bill_country = datatable(obj_bill_country, dtglobalsheet)
	dl_address1 =  datatable(obj_dl_address1, dtglobalsheet)
	dl_address2 = datatable(obj_dl_address2, dtglobalsheet)
	dl_city =  datatable(obj_dl_city, dtglobalsheet)
	dl_state =  datatable(obj_dl_state, dtglobalsheet)
	dl_postalcode =  datatable(obj_dl_postalcode, dtglobalsheet)
	dl_country = datatable(obj_dl_country, dtglobalsheet)
	
	' you can't use the value that starts w/ "0" in the data tahle
	' since the list items for the credit card expiration month have "0" in them, I need to modify the variable here.
	if exp_month < 10 then
		exp_month = "0" & exp_month
	end if
	
	' passengers
	if rc = micPass then rc = FRM_Obj_Set(obj_ps_first, ps_first)
	if rc = micPass then rc = FRM_Obj_Set(obj_ps_last, ps_last)
	if rc = micPass then rc = FRM_Obj_Select(obj_meal, meal)
	
	' credit card
	if rc = micPass then rc = FRM_Obj_Select(obj_card_type, card_type)
	if rc = micPass then rc = FRM_Obj_Set(obj_card_num, card_num)
	if rc = micPass then rc = FRM_Obj_Select(obj_exp_month, exp_month)
	if rc = micPass then rc = FRM_Obj_Select(obj_exp_date, exp_date)
	if rc = micPass then rc = FRM_Obj_Set(obj_cc_first, cc_first)
	if rc = micPass then rc = FRM_Obj_Set(obj_cc_middle, cc_middle)
	if rc = micPass then rc = FRM_Obj_Set(obj_cc_last, cc_last)
	
	' billing address
	if rc = micPass then rc = FRM_Obj_Set(obj_bill_address1, bill_address1)
	if rc = micPass then rc = FRM_Obj_Set(obj_bill_address2, bill_address2)
	if rc = micPass then rc = FRM_Obj_Set(obj_bill_city, bill_city)
	if rc = micPass then rc = FRM_Obj_Set(obj_bill_state, bill_state)
	if rc = micPass then rc = FRM_Obj_Set(obj_bill_postalcode, bill_postalcode)
	if rc = micPass then rc = FRM_Obj_Select(obj_bill_country, bill_country)
	
	' delivery address
	if rc = micPass then rc = FRM_Obj_Set(obj_dl_address1, dl_address1)
	if rc = micPass then rc = FRM_Obj_Set(obj_dl_address2, dl_address2)
	if rc = micPass then rc = FRM_Obj_Set(obj_dl_city, dl_city)
	if rc = micPass then rc = FRM_Obj_Set(obj_dl_state, dl_state)
	if rc = micPass then rc = FRM_Obj_Set(obj_dl_postalcode, dl_postalcode)
	if rc = micPass then rc = FRM_Obj_Select(obj_dl_country, dl_country)
	
	' purchase
	if rc = micPass then rc = FRM_Obj_Click(obj_purchase_btn)
	
	bk_book_flight = rc

end function























