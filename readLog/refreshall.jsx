﻿#target photoshop
#include "/Users/pwl/git/readNews_remote/readLog/include.jsx"
var fileRef = File("/Users/pwl/git/readNews_remote/readLog/image/lib.psd") 
var dest = app.open(fileRef)    
app.activeDocument = dest
collegeInfo("libXML.xml")
$.write("ok")