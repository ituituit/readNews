var stackorder = 0;
var str = ""
function docCheck() {
    // ensure that there is at least one document open
    if (!documents.length) {
        alert('There are no documents open.');
        return; // quit
    }
}

function getLayerDescriptor (doc, layer) {
    var ref = new ActionReference();
    ref.putEnumerated(cTID("Lyr "), cTID("Ordn"), cTID("Trgt"));
    return executeActionGet(ref)
};

function getLayerID(doc, layer) {
  var d = getLayerDescriptor(doc, layer);
  return d.getInteger(cTID('LyrI'));
};


// function from Xbytor to traverse all layers
traverseLayers = function(doc, ftn, reverse) {
  function _traverse(doc, layers, ftn, reverse) {
    var ok = true;
    for (var i = 1; i <= layers.length && ok != false; i++) {
      var index = (reverse == true) ? layers.length-i : i - 1;
      var layer = layers[index];
      if(layer.visible){
        if (layer.typename == "LayerSet") {
            ok = _traverse(doc, layer.layers, ftn, reverse);
        } else {
            stackorder = stackorder + 1;
            ok = ftn(doc, layer, stackorder);
        }
    }
  };
  return ok;
}
  return _traverse(doc, doc.layers, ftn, reverse);
};

// create a string to hold the data
var str ="";


// now a function to collect the data
function exportBounds(doc, layer, i) {
    var isVisible = layer.visible;
        objBounds = layer.bounds
        objHeight = objBounds[3] .value- objBounds[1].value
        objWidth = objBounds[2].value - objBounds[0].value
        objX = objBounds[0].value
        objY = objBounds[1].value
        leftTop = objX + "," + objY
  if(isVisible){
// Layer object main coordinates relative to its active pixels
    var parentNames = new Array()
    var obj = layer
    while(obj.parent){
        parentNames.push(obj.name)
        obj = obj.parent
    }
    var fullname =""
    for(var i = parentNames.length - 2; i >= 0; i--){
        fullname +=  "/" + parentNames[i]
    }
    var str2 = "\t<layer name=\"" + fullname 
	+ "\" stack=\"" + (i - 1) // order in which layers are stacked, starting with zero for the bottom-most layer
	+ "\" position=\"" + leftTop // this is the 
	+ "\" layerwidth=\"" + objWidth 
	+ "\" layerheight=\"" + objHeight
	//+ "\" transformpoint=\"" + "center" 
     + "\">" // hard-coding 'center' as the default transformation point
	+ layer.name + "</layer>\n" // I have to put some content here otherwise sometimes tags are ignored
str += str2.toString();
   };
};



//grabInformations(mySourceFilePath.toString().match(/([^\.]+)/)[1] + app.activeDocument.name.match(/([^\.]+)/)[1] + ".xml")
function grabInformationsToXML(outputPath){
//docCheck();
//var originalRulerUnits = preferences.rulerUnits;
//preferences.rulerUnits = Units.PIXELS;
var docRef = activeDocument;
var docWidth = docRef.width.value;
var docHeight = docRef.height.value;
var mySourceFilePath = ""//activeDocument.fullName.path + "/";
str = "<psd filename=\"" + activeDocument.name + "\" path=\"" + mySourceFilePath + "\" width=\"" + docWidth + "\" height=\"" + docHeight + "\">\n";

//  Code to get layer index / descriptor
//
cTID = function(s) { return app.charIDToTypeID(s); };
sTID = function(s) { return app.stringIDToTypeID(s); };
// call X's function using the one above
traverseLayers(app.activeDocument, exportBounds, true);
// create a reference to a file for output
    var csvFile = new File(outputPath);
// open the file, write the data, then close the file
csvFile.open("w");
csvFile.encoding = "utf-8" 
csvFile.writeln(str + "</psd>");
csvFile.close();
//preferences.rulerUnits = originalRulerUnits;
}