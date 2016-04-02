#----------------------------
# there is a bug w/ Switch.pm
# When a source file gets large, you'll see weird errors
# Since get_objects.pl is getting large, I had to move some switch statements to 
# a smaller source file

# ---------------------
# get QTP obj type
# @param (in) tag like "input"
# @param (in) reference to $token, a pointer to the html tree
# @return QTP's object type
sub sw_get_qtpobj($\$)
{
	use Switch;
	
	my $tag = shift(@_);
	my $token = shift(@_);
	my $done = 0;
	my $type;
	
	switch ($tag)
	{
		case "input"  { $type = lc($token->[2]{type}); }
		case "img"	  { $type = "Image"; $done = 1; }
		case "select" { $type = "WebList"; $done = 1; }
		case "a" 			{ $type = "WebElement"; $done = 1; }
		case "table"	{ $type = "WebTable"; $done = 1; }
		else				  { $type = "Unknown"; $done = 1; } 		
	}
	
	# process more if the obj type is still not determined
	if($done == 0)
	{
		switch ($type)
		{
			case "checkbox" { $type = "WebCheckBox"; $done = 1; }
			case "radio" 		{ $type = "WebRadioGroup"; $done = 1; }
			case "text"			{ $type = "WebEdit"; $done = 1; }
			case "password"	{ $type = "WebEdit"; $done = 1; }
			case "image"		{ $type = "Image"; $done = 1; }
			case ""					{ $type = "WebEdit"; $done = 1; }
		}
	}
	
	return $type;	
}

# -------------------
# get the index for @obj_counter
# @param (in) object type like "WebEdit"
# @return index
sub sw_get_index($)
{
	use Switch;
	
	my $obj_type = shift(@_);	
	my $index;
	my %obj_index = ( "webedit" => 0,
										"webradiogroup" => 1,
										"webcheckbox" => 2,
										"image" => 3,
										"select" => 4,
										"a" => 5,
										"table" => 6
									);
	
	switch ($obj_type)
	{
		case "WebEdit"       { $index = $obj_index{"webedit"}; }
		case "WebRadioGroup" { $index = $obj_index{"webradiogroup"}; }
		case "WebCheckBox" 	 { $index = $obj_index{"webcheckbox"}; }
		case "Image" 	 			 { $index = $obj_index{"image"}; }
		case "WebList" 			 { $index = $obj_index{"select"};}
		case "WebElement" 	 { $index = $obj_index{"a"};}
		case "WebTable" 		 { $index = $obj_index{"table"}; }
	}
	
	return $index;
}

# -------------------------------------------
# select attributes (properties of an obj)
# you want to specify for the obj in the xml repository
# @param (in) obj type such as "WebEdit"
# @return an array of attributes
sub sw_select_attr($)
{
	use Switch;
	
	my ($obj_type) = shift(@_);
	my @attrs;
	
	switch ($obj_type)
{
   case "WebEdit"    		{ @attrs = ("name");  }
   case "WebRadioGroup" { @attrs = ("name", "value", "id"); }
   case "WebCheckBox" 	{ @attrs = ("name", "id"); }
   case "Image" 				{ @attrs = ("src"); }
   case "WebList"     	{ @attrs = ("name"); }
   case "WebElement " 	{ @attrs = ("id"); }
   case "WebTable" 			{ @attrs = ("id", "class", "index"); }
}

	return @attrs;
}

# needs to return true if it's getting loaded from another pl script
1;