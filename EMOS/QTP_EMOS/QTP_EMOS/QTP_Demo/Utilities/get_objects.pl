# --------------------------------------------------------------
# parse an html file and write obj definitions to an xml file
# script summary: 1 - create a tree obj (pointer to the html tags in a tree structure
# 								2 - If it's a tag that you want to extract as an obj, get the attributes/properties of the obj
# 								3 - create attribute => value pairs
# 								4 - write the obj def to the xml file
# usage: get_objects.pl <input html file> <output xml file> <page obj name> <tag>
# example: get_objects.pl login.html temp.xml Login input,img
# subs: you can add more tags and properties in these subs.
# 			is_object() - defines tags that correspond to obj types such as "input"(WebEdit)
# libs: sw_lib*.pl defines all the subs that need to be modified when you add a new obj.
# tags: the following tags can be parsed as of 11/05/2004
# 			<input>  -> "WebEdit", "WebRadioGroup", "WebCheckBox"
# 			<img>    -> "Image" (images for buttons only)
# 			<a>      -> "WebElement"
# 			<select> -> "WebList"
# 			<table>  -> "WebTable"
# 			
# created by: sahokoh
# -------------------------------------------------------------------------------------------------

#use strict;

# load modules
require HTML::TokeParser;
require "sw_lib.pl"; # contains subs that use switch statements
require "sw_lib2.pl";

# variables
my ($html, $xml, $page_name, $input_tags);
my ($tree, $token, $rc);
my ($reftoken, $text, @attrs, %attrval_pairs);
my @obj_counter = ();

# get the arguments from the command line
($html, $xml, $page_name, $input_tags) = @ARGV;

# load html in a tree structure
$tree = HTML::TokeParser->new($html) || die "Can't open: $!";

# open the output file
open(XML, ">$xml");
 
# find the specified tag and get the specified attribute
while ($token = $tree->get_token()) 
{
	if($token->[0] eq "S") # find start tag
	{
		$tag = $token->[1]; # token is a reference to the list ("S", $tag, %$attr, @$attrseq, $origtext)
		$rc = is_object($tag, $input_tags, $token);
		
		# if it's an object you want, then enter it to the xml file
		if($rc){
			
			# open <Object> tag for the parent Page obj
			if($tag eq "title"){
				$text = $tree->get_trimmed_text();
				write_pageobj($text, $page_name);		
			}
			else{
				# get the attribute - value pairs like "name" => "frmLogin"
				%attrval_pairs = (); #clean it up first 
				%attrval_pairs = get_attrval($page_name, $tag, @attrs, $token, @obj_counter);
				
				# write the obj definition to the xml file
				write_childobj(%attrval_pairs);
			}
		}
	}
	
}

# close the <Object> tag 
close_pageobj_tag();

close XML;




###############################################################################################

# ----------------
# write xml definition for the page object
# @param (in) title
# @param (in) name of the page object that you'd like to use
sub write_pageobj($$)
{
	my ($title, $page_name) = @_;
	
	print XML "<Object Name=\"$page_name\" Type=\"Page\">\n";
	print XML "<Def Type=\"ChildObject\">\n";
	print XML "<Description>\n";
	print XML "<title>$title</title>\n";
  print XML "</Description>\n";
  print XML "</Def>\n";
	
}

# -------------------------------------
# write obj definition to the xml file
# @param (in) %attrval_pairs is passed directly
# 						a hash that contains attribute => value pair
# 						the very first pair is child obj name => its obj type
sub write_childobj(%)
{
	my $child_obj = $attrval_pairs{"child_obj"};
	my $obj_type = $attrval_pairs{"obj_type"};
	my (@keys, $key, $val);
	
	# clean up the hash
	delete($attrval_pairs{"child_obj"});
	delete($attrval_pairs{"obj_type"});
	
	# get all the properties in an array
	@keys = keys(%attrval_pairs);
	
	if($child_obj ne ""){
		# start writing the obj definition
		print XML "<Object Name=\"$child_obj\" Type=\"$obj_type\">\n";
		print XML "<Def Type=\"ChildObject\">\n";
		print XML "<Description>\n";
		
		# loop through the properties
		foreach $key (@keys)
		{
			$val = $attrval_pairs{$key};
			if($val ne ""){
				print XML "<$key>$val</$key>\n";
		  }
		}
		
		print XML "</Description>\n";
		print XML "</Def>\n";
		print XML "</Object>\n";
	}
	
}

# -----------------
# close the page object tag
sub close_pageobj_tag()
{
	print XML "</Object>\n";
}

# ------------------
# store attribute name and its value in a hash
# @param (in) page name such as "Login"
# @param (in) tag name such as "form"
# @param (in) $token is passed directly - it's a pointer to the entire tree obj (content of html)
# @param (in) @obj_counter is passed directly
# @return  attribute => value pairs - the very first pair is child page name => its obj type
sub get_attrval($$$@)
{
	use Switch;
	
	my $page_name = shift(@_);
	my $tag = shift(@_);
	my ($child_name, $attr, $type);
	my %attrval_pairs = ();
	my @attrs = ();
	my @selected_attrs;
	
	my ($index, $temp_childname);
	
	# get the obj type for each tag first - this will keep track of the num of duplicate obj's
	$type = get_objtype($tag, $token, @obj_counter);
	
	# get index for @obj_counter
	$index = sw_get_index($type);
	
	# get the attributes for the obj
	@attrs = sw_select_attr($type);
	
	# get the attribute - it's used to name the child object. pick something that's unique
	@selected_attrs = sw_set_attr($type);
	
	# get the name for the child object
	foreach $selected_attr (@selected_attrs)
	{
		$child_name = $token->[2]{$selected_attr};
		if($child_name ne ""){
			last;
		}
	}
	
	# if none of the attributes returns anything, set it to unknown
	if($child_name eq ""){
		$child_name = "Unknown";
	}
	
	$temp_childname = $child_name;
			
	if( $obj_counter[$index]{$child_name} > 1 ){
		# get rid of the number from the original name like this "label1" -> "label"				
		$child_name =~ s/\d+//;
		$child_name .= $obj_counter[$index]{$temp_childname};
	}
		
	# write the child obj name
	# may need to edit the attribute names - for IMG obj, src is an attribute, but it corresponds to "file name" in QTP
	%attrval_pairs = sw_name_childobj($type, $page_name, $child_name);
	
	# store the object type
	$attrval_pairs{"obj_type"} = $type;
	

	# write attribute name => its value
	foreach $attr (@attrs){	
		$attrval_pairs{$attr} = $token->[2]{$attr};
	}
	
	# edit $attrval_pairs for attribute names that are different from QTP's
	# ex: the attribute "id" is "html id" in QTP
	if($tag eq "img"){
		convert_src_filename(\%attrval_pairs);
	}
	elsif($tag eq "a"){
		add_weblink_attr($tag, \%attrval_pairs);
	}

	return %attrval_pairs;
}

# ---------------------
# add "html tag" property
# @param (in) tag like "a"
# @param (in) a pointer to %attrval_pairs
# 						you need to pass %attrval_pairs by reference
# note: I'm using the global variable $tree
sub add_weblink_attr($$)
{
	my @tag = shift(@_);
	my $attrval_pairs = shift(@_);
	my $text = $tree->get_trimmed_text(); # get the innertext
	
	$attrval_pairs->{"html__XORSPACE__tag"} = $tag;
	$attrval_pairs->{"innertext"} = $text;
	
}

# -------------------
# convert an html attribute to a QTP's obj property
# @param (in) a pointer to the hash %attrval_pairs
# 						you need to pass %attrval_pairs by reference
sub convert_src_filename($)
{
	my $attrval_pairs = shift(@_);
	my $file_name = $attrval_pairs->{"src"};
	
	$file_name = get_filename($file_name);
	delete($attrval_pairs->{"src"});
	
	$attrval_pairs->{"file__XORSPACE__name"} = $file_name;
}

# ----------------
# get the file name
# @param (in) src like "../button_save.gif"
# @return file name like "button_save.gif"
sub get_filename($)
{
	my $file_name = shift(@_);
	
	$file_name =~ m/\w+.gif/;
	$file_name = $&; # grab the matched string
	
	return $file_name;
}

# -------------------------------
# get the obj type and keep track of the num of duplicate obj's
# @param (in) tag such as "input"
# @param (in) $token is passed directly
# @param (in) @obj_counter is passed directly - an array of hashes
# 						
# @return obj type => child name pair like "WebEdit"=>"label1"
sub get_objtype($$@)
{
	#use Switch;
	
	my $tag = shift(@_);
	my ($type, $counter);
	my $done = 0;
	my ($index, @attrs, $selected_attr);
	
	# get the QTP's obj type
	$type = sw_get_qtpobj($tag, $token);
	if($type ne ""){
		$done = 1;
	}
	
	# keep track of the duplicate names
	if($done == 1){
	
		# get index for @obj_counter
		$index = sw_get_index($type);
		@attrs = sw_set_attr($type);
		
		# check to see if the attr value exists for the element
		foreach $attr (@attrs){
			if($token->[2]{$attr} ne ""){
				$selected_attr = $attr;
				last;
		  }
		}
		
		# get the num of the name
		$counter = $obj_counter[$index]{$token->[2]{$selected_attr}};
					
	  # if it's empty, then set it to 1, otherwise increment by 1
		if($counter == "") {
			$counter = 1; 
			$obj_counter[$index]{$token->[2]{$selected_attr}} = $counter;
		}
		else{
			$obj_counter[$index]{$token->[2]{$selected_attr}} = ++$counter;
		}
	}

	return $type;
}


# --------------------------------------------------------------
# check if the tag is something you want to store as an obj
# @param (in) a tag name
# @param (in) a string that contains tags in this format: $str = "form,a,div"
# @param (in) passing $token directly
# @return 0 or 1: 0 is false
sub is_object($$$)
{
	my ($tag_name) = shift(@_); # get the argument
	my $tag_str = shift(@_);
	my @tags = ("title"); #"input", "img"); # "a", "div", "table");	
	my $rc = 0; # set it to false
	my $src;
	my @input_tags = split(",", $tag_str);
	my $i = 1;
	
	foreach $input_tag (@input_tags)
	{
		$tags[$i] = $input_tag;
		$i++;
	}
	
	
	# discard if the element is hidden
	if(lc($token->[2]{type}) ne "hidden")
	{
		#print STDOUT "$tag_name=$token->[2]{name}\n";
		foreach $tag (@tags)
		{
			# discard if the image file name doesn't contain the substring "button"
			if($tag_name eq "img"){
				$src = lc($token->[2]{src});
				$src =~ m/(?i)button/; 
				if($& eq ""){ # check the matched pattern. if not found, don't process the tag. it's probably not a button.
					last;
				}
			}
			
			if($tag_name eq $tag){
				$rc = 1;
				last;
			}
			
		}
	}
	
	return $rc;
}

