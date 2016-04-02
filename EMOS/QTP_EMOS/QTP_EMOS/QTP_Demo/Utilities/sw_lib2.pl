# ---------------------------------
# get the attribute
# it's used to name the child object. pick something that's unique
# @param (in) object type such as "WebEdit"
# @return an array of attributes such as "id" or "name"
sub sw_set_attr($)
{
	use Switch;
	
	my $type = shift(@_);
	my @selected_attr;
	
	switch ($type)
	{
		case "WebRadioGroup" { @selected_attr = ("id", "name"); }
		case "WebEdit" 			 { @selected_attr = ("name"); }
		case "WebCheckBox"   { @selected_attr = ("name"); }
		case "Image" 				 { @selected_attr = ("src");}
		case "WebList"   		 { @selected_attr = ("name"); }
		case "WebElement" 	 { @selected_attr = ("id", "name"); }
		case "WebTable" 	 	 { @selected_attr = ("id", "class"); }
		else								 { @selected_attr = ("unknown"); }
	}
	
	return @selected_attr;	
}

# -----------------------
# name the child obj and set the value for the key "child_obj"
# in the hash passed to the sub by reference
# @param (in) object type such as "WebEdit"
# @param (in) the name of the Page object - it's passed via the command line args
# @param (in) the value of the selected attribute ex - "name"(attr) => "frmLogin"(val)
# 						the attribute is selected in sw_set_attr()
# @return %attrval_pairs, a hash that contains the name of the child obj. The key is "child_obj"
sub sw_name_childobj($$$)
{
	use Switch;
	
	my ($type, $page_name, $child_name) = @_;
	my %attrval_pairs = ();
	
	$child_name = cap_firstchar($child_name);
	
	switch($type)
	{
		case "WebEdit"    	 { $child_name = discard_underscore($child_name);
													 $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_TextField"; }
		case "WebRadioGroup" { $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_RadioButton"; }
		case "WebCheckBox"   { $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_CheckBox"; }
		case "Image"				 { $child_name = get_buttonname($child_name); 
													 $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_Button";}
		case "WebList"       { $child_name = discard_underscore($child_name);
													 $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_List"; }
		case "WebElement"    { $child_name = remove_id($child_name);
													 $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_Link"; }
		case "WebTable" 		 { $attrval_pairs{"child_obj"} = $page_name."_".$child_name."_Table"; }
											
	}
	
	return %attrval_pairs;
}

# -------------------
# remove "id" from $child_name
# @param (in) $child_name like "frmLogin"
sub remove_id($)
{
	my $child_name = shift(@_);
	
	$child_name =~ m/(?i)id/;
	if($& ne ""){
		$child_name =~ s/(?i)id//;
	}
	
	return $child_name;	
}

# -----------------
# capitalize the first character
# @param (in) any string that you want to capitalize the first character of
sub cap_firstchar($)
{
	my $child_name = shift(@_);
	
	$child_name =~ m/\w/; # grab the first character 
	$char = uc($&); # cap the matched character
	$child_name =~ s/\w/$char/;
	
	return $child_name;
}

# -------------------------
# get rid of "_" in $child_name
# @param (in) child name like "OffPoint_1"
# @return modified child name
sub discard_underscore($)
{
	my $child_name = shift(@_);
	
	$child_name =~ m/\_/;
	if($& ne ""){
		$child_name =~ s/\_//;
	}
	
	return $child_name;
}

# ----------------------
# parse "src" for the image obj
# @param (in) the value of the attribute "src"
# 						like this - "../images/button_save.gif" 
sub get_buttonname($)
{
	my $child_name = shift(@_);
	my $char;
	
	$child_name =~ m/\w+.gif\d*/; # duplicate names have an index like this ../infobutton.gif2
	$child_name = $&; # grab the matched string
	
	# assumed that all the images for buttons come in this form: button_*.gif
	$child_name =~ s/(?i)button_?//; # case insensitive, "_" is matched zero or one time
	$child_name =~ s/.gif//;
	$child_name = cap_firstchar($child_name);
		
	return $child_name;
}

1;