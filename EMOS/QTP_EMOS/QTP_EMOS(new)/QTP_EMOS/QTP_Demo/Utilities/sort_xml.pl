# -----------------------------------------------------------------------------------------------------------------------------
# sort parent and child object names in the xml repository 
# script summary: Most subs manipulate global variables directly.
# 								I'm specifying prototypes in each sub for readability even though some of them are not
# 								reassigned to other variables within the sub.
# 								The input xml file is read to a tree structure. I'm just going through each element one by one
# 								and store all the info I need, so that I can sort all the elements alphabetically and write them
# 								back to the output xml file.
# notes: store_objs() takes care of the parent objects that you want to extract. Currently it handles "Page" and "Dialog".
# 			 If we don't use any other object types for Travelport. But if you need to process other types of objects, then you'll
# 			 need to modify this function.
# usage: sort_xml.pl <input xml file> <output xml file>
# example: get_xml.pl TravelportObjectRepository.xml temp.xml
# 			
# created by: sahokoh
# -------------------------------------------------------------------------------------------------
use vars;

# load modules
require XML::TokeParser;

# variables
my ($xml, $out_xml);
my ($tree, $token);

# store_objs() uses these variables
my @child_objs = (); # stores name of child obj - @child_obj[][]: row corresponds to page id
my %obj_types = (); # key is obj's name (includes parent and child objects)
my @pages = (); # stores page name
my %pagename_id_pairs = (); # key is page name: stores the index used to access @child_objs[][]

# get_properties() uses these variables
my %properties = (); # the key is {$page_id$child_name$prop_name}
my %obj_prop_names = (); # the key is {$child_name$index} index starts from 1


# get the input and output xml file names
($xml, $out_xml) = @ARGV;

# open the output file
open(OUT, ">$out_xml");

# get all the elements
# all the elements and their attributes will be stored in @child_objs[][], %obj_types, @pages, & %pagename_id_pairs
get_all_elems($token, $tree, $xml, @pages, @child_objs, %pagename_id_pairs, %obj_types, %obj_prop_names, %properties);

# sort the page names alphabetically
@pages = sort(@pages);

# start writing to the output file
write_parentobj();

# write child obj and its properties
write_childobj_main(@pages, @child_objs, %pagename_id_pairs, %obj_types, %obj_prop_names, %properties);
	
# close the parent obj
close_obj("Object_Repository");


#####################################################################################################

# ----------------------------------------------------------
# store all the child objs' names in %child_objs
# the key is the index that is associated w/ page name
# ex: "AnyPage" => 0
# it also stores obj types
# note: you need to add more parent objects such as "Dialog"
# @param (in) name attribute of an element
# @param (in) type attribute of an element
# @param (in) $i passed directly - page id
# @param (in) $j passed directly
# @param (in) @pages passed directly
# @param (in) @child_objs passed directly
# @param (in) %pagename_id_pairs passed directly
# @param (in) %obj_types passed directly
sub store_objs($$$$@@%%)
{
	use Switch;
	
	my ($name, $type) = @_;
	
	switch ($type)
	{
			case "Page"   { $i++; # $i is set to -1 initially
											$pages[$i] = $name;		
											$pagename_id_pairs{$name} = $i;
											$obj_types{$name} = $type;
											$j=0; } # reset $j
			case "Dialog" { $i++; # $i is set to -1 initially
											$pages[$i] = $name;										
											$pagename_id_pairs{$name} = $i;
											$obj_types{$name} = $type;
											$j=0; } # reset $j
			else 				  { $child_objs[$i][$j] = $name; 
											$j++; 
										  $obj_types{$name} = $type; }											
	}

}

# ------------------
# @param (in) $tree
# @param (in) $xml
# @param (in) $token
# @param (in) @pages
# @param (in) @child_objs
# @param (in) %pagename_id_pairs
# @param (in) %obj_types
# @param (in) %obj_prop_names
# @param (in) %properties
sub get_all_elems($$$@@%%%%)
{
	my ($tag, $name, $type);
	my $i = -1; # page id
	my $j = 0;
	my $prev_i = -1;
	my $child_id = -1;
	my $win_obj = 0; # need to keep track of win obj (most objs are 'Page' obj
	my $k; # keeps track of the number of properties

	
	# load the content of the xml file in a tree structure
	$tree = XML::TokeParser->new($xml);

	# loop through all the levels
	while ($token = $tree->get_token()){ # token is a pointer to the list ["S",  $tag, $attr, $attrseq, $raw]
		
		if($token->[0] eq "S") # find start tag
		{
			$tag = $token->[1];
			$name = $token->[2]{Name};
					
			# process <Object> tag
			if($tag eq "Object"){
				$type = $token->[2]{Type};
			
				# if it's Browser obj, then process next element
				if($type eq "Browser"){	
					next;
				}		
				
				# store all the child obj names in @child_objs
				store_objs($name, $type, $i, $j, @pages, @child_objs, %pagename_id_pairs, %obj_types);
		  }
		  elsif($tag eq "Description"){
			  # store all the properties in %properties
			  # the key is <page_id><child_name><property_nam> like "1AnyPagetitle"
			  get_properties($token, $tree, $child_id, $i, $prev_i, $win_obj, $k, %obj_prop_names, %properties, @child_objs, @pages);
		  }
		}
	}

}


# ----------------------
# get properties of an element
# @param (in) $token
# @param (in) $tree
# @param (in) $child_id
# @param (in) $i - page id
# @param (in) $prev_i
# @param (in) $win_obj - a flag for window's obj such as pop up window
# @param (in) $k - keeps track of the number of properties
# @param (in) %obj_prop_names - the key is {$child_name$index} index starts from 1
# @param (in) %properties - the key is {$page_id$child_name$prop_name}
# @param (in) @child_objs - stores name of child obj - @child_obj[][]: row corresponds to page id
# @param (in) @pages - stores page name
sub get_properties($$$$$$$%%@@)
{
	my ($prop_name, $child_name, $page_name, $text); 
	
	# keep traversing the tree until you grab all the properties for the element	
	while($token = $tree->get_token()){
		 
		if($token->[0] eq "S"){
			
			# if it's Object, exit out of the loop
			if($token->[1] eq "Object"){  
				
				# need to know if it's a dialog (pop up window)
				if($token->[2]{Type} eq "Dialog"){
					$win_obj = 1;
				}
				else{
					$win_obj = 0;
				}
				
				$tree->unget_token($token); # revisit the same token for the outer loop
				$child_id++;
				$k = 0;
				last;
			}
			
			# get page name and its text
			$page_name = $pages[$i];
			$text = $tree->get_trimmed_text();
			$prop_name = $token->[1];
			
			# clean up the text - perl is grabbing what's displayed in ie
			# I need to convert it back to the code for the special character
			if($text =~ m/\&/){
				$text =~ s/\&/\&amp\;/;
			}
			
			# if it's a page obj, then skip	
			if($prop_name eq "title"){
				$properties{$i.$page_name.$prop_name} = $text; 
				$obj_prop_names{$page_name.($k+1)} = $prop_name; # first index starts from 1
				$child_id = -1; # reset for each page - $child_id is incremented by 1 whenever <Object> tag is encountered
				$k++;
				next;
			}
			
			# if it's a win obj, then skip
			if($win_obj == 1){
				$properties{$i.$page_name.$prop_name} = $text; 
				$obj_prop_names{$page_name.($k+1)} = $prop_name; # first index starts w/ 1
				$child_id = -1; # reset for each page
				$k++;
				next;				
			}
			
			# if it's a child obj, then store the child name
			# $child_id remains the same for the properties that belong to the same parent obj
			$child_name = $child_objs[$i][$child_id];		
			if($prev_i == $i){		
				$properties{$i.$child_name.$prop_name} = $text; # the key is <page_id><child_name><prop_name> ex. "1AnyPagetitle"
				$obj_prop_names{$child_name.($k+1)} = $prop_name; # first index starts w/ 1
				$k++;
		  }
	}
 }
 
 $prev_i = $i; # need to keep track of whether the token is page or child obj

}

# ----------------------
# write <Object_Repository> tag
# note: the tag is not closed
sub write_parentobj()
{
	print OUT "<?xml version=\"1.0\"?>\n";
	print OUT "<Object_Repository>\n";
  print OUT "<Object Name=\"MainBrowser\" Type=\"Browser\">\n";
  print OUT "<Def Type=\"ParentObject\">\n";
  print OUT "<Description>\"MainBrowser\"</Description>\n";
  print OUT "</Def>\n";

}

# --------------------------
# write page obj
# note: the tag is not closed
# @param (in) - page name
# @param (in) - 
sub write_pageobj($$$%%)
{
	my ($page_name, $type, $page_id) = @_;
	my ($prop_name, $prop_val);
	my $i = 1;
	
	print OUT "<Object Name=\"$page_name\" Type=\"$type\">\n";
  print OUT "<Def Type=\"ChildObject\">\n";
  print OUT "<Description>\n";
  
   # check for the prop name
  while($prop_name = $obj_prop_names{$page_name.$i}){
		
	  $prop_val = $properties{$page_id.$page_name.$prop_name};
	  print OUT "<$prop_name>$prop_val</$prop_name>\n";
	 
	  # check for the prop name
	  $prop_name = $obj_prop_names{$child_name.$i};
	  $i++;
	}
	
  print OUT "</Description>\n";
  print OUT "</Def>\n";

}

# -------------------
# close the specified tag
# @param (in) tag name
sub close_obj($)
{
	my ($close_tag) = @_;
	
	print OUT "</$close_tag>\n";
}

# ----------------
# write child obj and its properties
# @param (in) $child_name
# @param (in) $type
# @param (in) $page_name
# @param (in) $page_id
# @param (in) %obj_prop_names - the key is {$child_name$index}
# @param (in) %properties - the key is {$page_id$child_name$prop_name}
sub write_childobj($$$$%%)
{
	my ($child_name, $type, $page_name, $page_id) = @_;
	my ($prop_name, $prop_val, $num);
	my $i = 1;
	
	print OUT "<Object Name=\"$child_name\" Type=\"$type\">\n";
  print OUT "<Def Type=\"ChildObject\">\n";
  print OUT "<Description>\n";
  
  # check for the prop name
  while($prop_name = $obj_prop_names{$child_name.$i}){
		
	  $prop_val = $properties{$page_id.$child_name.$prop_name};
	  print OUT "<$prop_name>$prop_val</$prop_name>\n";
	  
	  # check for the prop name
	  $prop_name = $obj_prop_names{$child_name.$i};
	  $i++;
	}
  
  print OUT  "</Description>\n";
  print OUT  "</Def>\n";
  print OUT  "</Object>\n";
}

# ---------------------
# write child obj and its properties
# @param (in) @pages
# @param (in) @child_objs
# @param (in) %pagename_id_pairs
# @param (in) %obj_types
# @param (in) %obj_prop_names
# @param (in) %properties
sub write_childobj_main(@@%%%%)
{
	my ($page_id, $type, $title_val, @child_names, $j);

	# process each page
	foreach $page_name (@pages){
		
		# clean up @child_names
		@child_names = ();
		
		# if page name is blank, skip
		if($page_name eq ""){
			next;
		}
		
		$page_id = $pagename_id_pairs{$page_name};
		$type = $obj_types{$page_name};
		
		# check if the obj is "Dialog" instead of "Page"
		if($type eq "Dialog"){
			
			$type = $obj_types{$page_name};
			write_pageobj($page_name, $type, $page_id, %obj_prop_names, %properties);
	  }
	  else{
			# start writing the page object
			$type = $obj_types{$page_name};
			write_pageobj($page_name, $type, $page_id, %obj_prop_names, %properties);
		}
		
		# loop through 2-dimensional array @child_objs[][]
		for $j ( 0 .. $#{$child_objs[$page_id]}){
							
			$child_names[$j] = $child_objs[$page_id][$j];
		}
		
		# sort child names
		@child_names = sort(@child_names);
		
		# write child obj's and its properties
		foreach $child_names (@child_names)
		{
			$type = $obj_types{$child_names};
				
			# write child obj and its properties
			write_childobj($child_names, $type, $page_name, $page_id, %obj_prop_names, %properties);
		}
			
		# close the page object
		close_obj("Object");
	}
	
	# close the browser object
		close_obj("Object");

}
