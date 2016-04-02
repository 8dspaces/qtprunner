
re_func - Regular expression functions search and match for WinRunner

Copyright (c) 2003 Misha Verplak

Supporting regex++ c++ library at www.boost.org:
Copyright (c) 1998-2001 Dr John Maddock

Permission is granted to use, modify and redistribute this
software provided both copyrights appear in all copies.

Author:   Misha Verplak, mm_ke@yahoo.com

Version:  0.2  2003-01-21

Known bugs:
. Garbage in re can cause regex library to crash


This script and dll provides WinRunner with perl-like
regular expression search and match functions, to supplement
the limited builtin function match() and add GUI properties
"label_like" and "id_like" for window recognition.

re_func.dll is a wrapper for the regex++ c++ library found at www.boost.org.

The following functions are exported from the dll:

re_match  - match a RE to a whole string
re_search - find a match for RE within a string

The 'detail' string returned is an encoded list of the position and length
of any submatches from the expression. WinRunner functions are provided to
decode this string.

Also defined is a query and verify function for a new property called "label_like".
This can be used to find regular expressions (equivalent to re_search) in windows labels.
Note that this will probably not work with web objects or other custom controls, as it
uses the windows API GetWindowText() to extract the label.

Included is a sample script and a copy of the supported regular expression syntax.

---
Change history:

Version
0.2    Added "id_like" to match MSW_id
0.1    First release
