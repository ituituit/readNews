#target photoshop
#include "F:\\Documents and Settings\\Administrator\\git\\readNews_remote\\readLog\\include_win.jsx"

var fileRef = File("F:\\Documents and Settings\\Administrator\\git\\readNews_remote\\readLog\\image\\news.psd") 
var dest = app.open(fileRef)    
app.activeDocument = dest
collegeInfo("newXML.xml")
$.write("ok")
fileRef.close();