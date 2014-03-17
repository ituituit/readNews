//collegeInfo("libXML.xml")
function deleteLayer(name){
    objIndex(name)
var idDlt = charIDToTypeID( "Dlt " );
    var desc4631 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1599 = new ActionReference();
        var idLyr = charIDToTypeID( "Lyr " );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1599.putEnumerated( idLyr, idOrdn, idTrgt );
    desc4631.putReference( idnull, ref1599 );
executeAction( idDlt, desc4631, DialogModes.NO );
}

function SelectArea(){
}
SelectArea.prototype.readAI = function(path){
var originalRulerUnits = preferences.rulerUnits;
preferences.rulerUnits = Units.POINTS;
imageHeight= app.activeDocument.height
var prefs = new File(path);
prefs.open("r"); 
var strs = prefs.read()
var begin = "%Adobe_Photoshop_Path_Begin:"
var end = "%Adobe_Photoshop_Path_End"
strs = strs.substring (strs.search (begin), strs.search (end));
arrs = strs.split ("\n")
var inds = new Array()
for(var i = 0; i < arrs.length; i++){
    if(arrs[i].search("1 XR") == 0){
        for(var j =i; j < arrs.length; j++){
            if(arrs[j].search("""n""") == 0){
                inds.push([i+1,j])
                i = j+1
                break;
            }
        }
    }
}
var points = new Array()
for(var i = 0; i < inds.length; i+=1){
for(var j = inds[i][0]; j < inds[i][1]; j++){
            newArr = arrs[j].split(" ")
            nexArr = arrs[inds[i][0]].split(" ")
            if(j+1 < inds[i][1]){
                nexArr = arrs[j+1].split(" ")
            }
            var last = newArr.length - 1
            curx = newArr[last - 2]
            cury = imageHeight - newArr[last - 1]
            bakx =newArr[last - 4]
            baky = imageHeight - newArr[last - 3]
            forwx = nexArr[0]
            forwy = imageHeight - nexArr[1]
            if(last != 2 && newArr[last].search("c") == 0){
                points.push([curx,cury,bakx,baky, forwx,forwy]) 
            }else{
                points.push([curx,cury]) 
            }
}
}

preferences.rulerUnits = originalRulerUnits
return points
}

SelectArea.prototype.outputPath = function(path){
    // =======================================================
var idExpr = charIDToTypeID( "Expr" );
    var desc7 = new ActionDescriptor();
    var idUsng = charIDToTypeID( "Usng" );
        var desc8 = new ActionDescriptor();
        var idIn = charIDToTypeID( "In  " );
        desc8.putPath( idIn, new File( path) );
        var idPthS = charIDToTypeID( "PthS" );
        var idPtSl = charIDToTypeID( "PtSl" );
        var idAllP = charIDToTypeID( "AllP" );
        desc8.putEnumerated( idPthS, idPtSl, idAllP );
    var idIllustratorExportThisstringmakesmeunique= stringIDToTypeID( "Illustrator Export.  This string makes me unique!" );
    desc7.putObject( idUsng, idIllustratorExportThisstringmakesmeunique, desc8 );
executeAction( idExpr, desc7, DialogModes.NO );
}
SelectArea.prototype.applyPath = function(objNames,contentBound){
    for(var i = 0; i < objNames.length; i++){
        objIndex(objNames[i]);
        this.load()
        this.expand()
        this.makePath(objNames[i])
    }
    selectBound(contentBound)
    this.makePath("newTempPath")
    this.buildArea(objNames,"newTempPath")
    for(var i =0; i < objNames.length; i++){
       // this.deletePath(objNames[i]);//buildArea invoked eraseAndDeletePath
    }
    this.deletePath("newTempPath")
    this.load()  
    deleteLayer("newTempLayer")
    this.makePath("newTempPath")
    this.buildArea("","newTempPath")
    deleteLayer("newTempLayer")
}
SelectArea.prototype.buildArea = function(objNames,contentName){
    newLayer = this.fillPath(contentName)
    for(var i = 0; i < objNames.length; i++){
        this.eraseAndDeletePath(objNames[i],newLayer)
    }
}

SelectArea.prototype.expand = function(){
    // =======================================================
var idExpn = charIDToTypeID( "Expn" );
    var desc4255 = new ActionDescriptor();
    var idBy = charIDToTypeID( "By  " );
    var idPxl = charIDToTypeID( "#Pxl" );
    desc4255.putUnitDouble( idBy, idPxl, 33.000000 );
executeAction( idExpn, desc4255, DialogModes.NO );
}

SelectArea.prototype.load = function(){
    // =======================================================
var idsetd = charIDToTypeID( "setd" );
    var desc4248 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1327 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idfsel = charIDToTypeID( "fsel" );
        ref1327.putProperty( idChnl, idfsel );
    desc4248.putReference( idnull, ref1327 );
    var idT = charIDToTypeID( "T   " );
        var ref1328 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idChnl = charIDToTypeID( "Chnl" );
        var idTrsp = charIDToTypeID( "Trsp" );
        ref1328.putEnumerated( idChnl, idChnl, idTrsp );
    desc4248.putReference( idT, ref1328 );
executeAction( idsetd, desc4248, DialogModes.NO );
}
SelectArea.prototype.selectPath = function(name){    
    // =======================================================
var idslct = charIDToTypeID( "slct" );
    var desc4261 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1342 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        ref1342.putName( idPath, name );
    desc4261.putReference( idnull, ref1342 );
executeAction( idslct, desc4261, DialogModes.NO );
}

SelectArea.prototype.deletePath = function(name){
this.selectPath(name)
    // =======================================================
var idDlt = charIDToTypeID( "Dlt " );
    var desc4260 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1341 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1341.putEnumerated( idPath, idOrdn, idTrgt );
    desc4260.putReference( idnull, ref1341 );
executeAction( idDlt, desc4260, DialogModes.NO );
}

SelectArea.prototype.makePath = function(name){
var idMk = charIDToTypeID( "Mk  " );
    var desc4256 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1335 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        ref1335.putClass( idPath );
    desc4256.putReference( idnull, ref1335 );
    var idFrom = charIDToTypeID( "From" );
        var ref1336 = new ActionReference();
        var idcsel = charIDToTypeID( "csel" );
        var idfsel = charIDToTypeID( "fsel" );
        ref1336.putProperty( idcsel, idfsel );
    desc4256.putReference( idFrom, ref1336 );
    var idTlrn = charIDToTypeID( "Tlrn" );
    var idPxl = charIDToTypeID( "#Pxl" );
    desc4256.putUnitDouble( idTlrn, idPxl, 2.000000 );
executeAction( idMk, desc4256, DialogModes.NO );
// =======================================================
var idMk = charIDToTypeID( "Mk  " );
    var desc4257 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1337 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        ref1337.putClass( idPath );
    desc4257.putReference( idnull, ref1337 );
    var idFrom = charIDToTypeID( "From" );
        var ref1338 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idWrPt = charIDToTypeID( "WrPt" );
        ref1338.putProperty( idPath, idWrPt );
    desc4257.putReference( idFrom, ref1338 );
executeAction( idMk, desc4257, DialogModes.NO );
// =======================================================
var idRnm = charIDToTypeID( "Rnm " );
    var desc4259 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1340 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1340.putEnumerated( idPath, idOrdn, idTrgt );
    desc4259.putReference( idnull, ref1340 );
    var idT = charIDToTypeID( "T   " );
    desc4259.putString( idT, name );
executeAction( idRnm, desc4259, DialogModes.NO );
}

SelectArea.prototype.newEmptyLayer = function(name){
// =======================================================
var idMk = charIDToTypeID( "Mk  " );
    var desc4361 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1443 = new ActionReference();
        var idLyr = charIDToTypeID( "Lyr " );
        ref1443.putClass( idLyr );
    desc4361.putReference( idnull, ref1443 );
executeAction( idMk, desc4361, DialogModes.NO );
// =======================================================
var idsetd = charIDToTypeID( "setd" );
    var desc4367 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1449 = new ActionReference();
        var idLyr = charIDToTypeID( "Lyr " );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1449.putEnumerated( idLyr, idOrdn, idTrgt );
    desc4367.putReference( idnull, ref1449 );
    var idT = charIDToTypeID( "T   " );
        var desc4368 = new ActionDescriptor();
        var idNm = charIDToTypeID( "Nm  " );
        desc4368.putString( idNm, name );
    var idLyr = charIDToTypeID( "Lyr " );
    desc4367.putObject( idT, idLyr, desc4368 );
executeAction( idsetd, desc4367, DialogModes.NO );
return app.activeDocument.activeLayer
}    
SelectArea.prototype.fillPath =  function(pathName){
    var newObj = this.newEmptyLayer("newTempLayer")
    this.selectPath(pathName)
// =======================================================
var idFl = charIDToTypeID( "Fl  " );
    var desc4277 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1357 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1357.putEnumerated( idPath, idOrdn, idTrgt );
    desc4277.putReference( idnull, ref1357 );
    var idWhPt = charIDToTypeID( "WhPt" );
    desc4277.putBoolean( idWhPt, true );
executeAction( idFl, desc4277, DialogModes.NO );
return newObj
}
SelectArea.prototype.selectPathArea = function(pathName){
this.selectPath(pathName)
// =======================================================
var idsetd = charIDToTypeID( "setd" );
    var desc4391 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref1478 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idfsel = charIDToTypeID( "fsel" );
        ref1478.putProperty( idChnl, idfsel );
    desc4391.putReference( idnull, ref1478 );
    var idT = charIDToTypeID( "T   " );
        var ref1479 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref1479.putEnumerated( idPath, idOrdn, idTrgt );
    desc4391.putReference( idT, ref1479 );
    var idVrsn = charIDToTypeID( "Vrsn" );
    desc4391.putInteger( idVrsn, 1 );
    var idvectorMaskParams = stringIDToTypeID( "vectorMaskParams" );
    desc4391.putBoolean( idvectorMaskParams, true );
executeAction( idsetd, desc4391, DialogModes.NO );
}

SelectArea.prototype.eraseAndDeletePath =  function(pathName,destLayer){
this.selectPathArea(pathName)
app.activeDocument.activeLayer  = destLayer
// =======================================================
var idDlt = charIDToTypeID( "Dlt " );
executeAction( idDlt, undefined, DialogModes.NO );
// =======================================================
var idDlt = charIDToTypeID( "Dlt " );
executeAction( idDlt, undefined, DialogModes.NO );
}

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>utils>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

function pushImage(filePath){
// =======================================================
var idPlc = charIDToTypeID( "Plc " );
    var desc2219 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
    desc2219.putPath( idnull, new File(filePath));
    var idFTcs = charIDToTypeID( "FTcs" );
    var idQCSt = charIDToTypeID( "QCSt" );
    var idQcsa = charIDToTypeID( "Qcsa" );
    desc2219.putEnumerated( idFTcs, idQCSt, idQcsa );
    var idOfst = charIDToTypeID( "Ofst" );
        var desc2220 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc2220.putUnitDouble( idHrzn, idRlt, 0.000000 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc2220.putUnitDouble( idVrtc, idRlt, 0.000000 );
    var idOfst = charIDToTypeID( "Ofst" );
    desc2219.putObject( idOfst, idOfst, desc2220 );
executeAction( idPlc, desc2219, DialogModes.NO );
return app.activeDocument.activeLayer;
}
 
function covertPercent(obj_width){
    _image_width = app.activeDocument.width
    return _image_width/obj_width * 100 / _image_width
}

function moveTo(obj,x,y){
    bound = obj.bounds
    obj.translate(-bound[0] + x, -bound[1] + y)  
}

function resize(obj,size){
    bound = obj.bounds
    var width = bound[2] - bound[0];
    var height=  bound[3] - bound[1];
    obj.resize(covertPercent(width)* size[0],covertPercent(height)* size[1],AnchorPosition.TOPLEFT);
}
 
function nameFromFullName(arrString){
    arr = arrString.split("/")
    return arr[arr.length - 1]
}

function objIndex(arrString){
    if(arrString.indexOf("/") == 0){
        arrString = arrString.substring (1, arrString.length)
    }
    arr = arrString.split("/")
    select(arr[0])
    var objnew = app.activeDocument.activeLayer
    for(var i = 1; i < arr.length; i++){
        objnew = objnew.layers.getByName (arr[i])
    }
    app.activeDocument.activeLayer = objnew
    return objnew
}

function select(name){
    var idslct = charIDToTypeID( "slct" );
    var desc60 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
    var ref36 = new ActionReference();
    var idLyr = charIDToTypeID( "Lyr " );
    ref36.putName( idLyr, name );
    desc60.putReference( idnull, ref36 );
    var idMkVs = charIDToTypeID( "MkVs" );
    desc60.putBoolean( idMkVs, false );
    executeAction( idslct, desc60, DialogModes.NO );
}
 

function selectBound(arrs){
    left = eval(arrs[0])
    top = eval(arrs[1])
    right = eval(arrs[2])
    bottom = eval(arrs[3])
var idsetd = charIDToTypeID( "setd" );
    var desc86 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref76 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idfsel = charIDToTypeID( "fsel" );
        ref76.putProperty( idChnl, idfsel );
    desc86.putReference( idnull, ref76 );
    var idT = charIDToTypeID( "T   " );
        var desc87 = new ActionDescriptor();
        var idTop = charIDToTypeID( "Top " );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc87.putUnitDouble( idTop, idRlt, top );
        var idLeft = charIDToTypeID( "Left" );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc87.putUnitDouble( idLeft, idRlt, left );
        var idBtom = charIDToTypeID( "Btom" );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc87.putUnitDouble( idBtom, idRlt, bottom );
        var idRght = charIDToTypeID( "Rght" );
        var idRlt = charIDToTypeID( "#Pxl" );
        desc87.putUnitDouble( idRght, idRlt, right );
    var idRctn = charIDToTypeID( "Rctn" );
    desc86.putObject( idT, idRctn, desc87 );
executeAction( idsetd, desc86, DialogModes.NO );
}

function applyMask(){
// =======================================================
var idDlt = charIDToTypeID( "Dlt " );
    var desc3487 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref704 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idChnl = charIDToTypeID( "Chnl" );
        var idMsk = charIDToTypeID( "Msk " );
        ref704.putEnumerated( idChnl, idChnl, idMsk );
    desc3487.putReference( idnull, ref704 );
    var idAply = charIDToTypeID( "Aply" );
    desc3487.putBoolean( idAply, true );
executeAction( idDlt, desc3487, DialogModes.NO );
}

function addMask(){
var idMk = charIDToTypeID( "Mk  " );
    var desc147 = new ActionDescriptor();
    var idNw = charIDToTypeID( "Nw  " );
    var idChnl = charIDToTypeID( "Chnl" );
    desc147.putClass( idNw, idChnl );
    var idAt = charIDToTypeID( "At  " );
        var ref127 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idChnl = charIDToTypeID( "Chnl" );
        var idMsk = charIDToTypeID( "Msk " );
        ref127.putEnumerated( idChnl, idChnl, idMsk );
    desc147.putReference( idAt, ref127 );
    var idUsng = charIDToTypeID( "Usng" );
    var idUsrM = charIDToTypeID( "UsrM" );
    var idRvlS = charIDToTypeID( "RvlS" );
    desc147.putEnumerated( idUsng, idUsrM, idRvlS );
executeAction( idMk, desc147, DialogModes.NO );
}

function helloworld(arrs){
    return arrs
}

function bounds(parentname,ind){
    //returnArr = new Array()
    obj = objSelect(parentname)
    targetObj = obj.layers.getName("content");
    return targetObj.bounds;
}

function dumplicate(params){
    group = params[0];
    changedText = "dumplicated_object"
    if(params.length == 2){
        changedText = params[1];
    }
    act = objIndex(group)
    newObj = act.duplicate ( app.activeDocument ); 
    newObj.move(act,ElementPlacement.PLACEBEFORE)
    newObj.name = changedText;
    app.activeDocument.activeLayer = newObj
}

function dumplicateExtendPsd(params){
    fileName = params[0];
    destName = params[1];
    group = params[2];
    changedText = "dumplicated_object"
    if(params.length == 4){
        changedText = params[3];
    }
    var fileRef = File(destName) 
    var dest = app.open(fileRef)    
    var fileRef = File(fileName) 
    var docRef = app.open(fileRef)    
    act = objIndex(group)
    act.duplicate ( dest );
    app.activeDocument = dest
    newObj = app.activeDocument.activeLayer
    newObj.name = changedText;
    newObj.move(dest.layers[0],ElementPlacement.PLACEBEFORE)
   //     app.activeDocument.activeLayer = newObj
}

function moveLayerInto(params){
   dest = params[0] 
   app.activeDocument.activeLayer .move(objIndex(dest).layers[0],ElementPlacement.PLACEBEFORE)
}

//function changeText(params){
//    objName = params[0];
 //   content = params[1];
 //   objIndex(objName).textItem.contents = content
//}

function changeName(params){
    objName = params[0];
    objNameChanged = params[1]
    objIndex(objName).name = objNameChanged
}
    
function bounds(arrs){
    returnArr = new Array()
    for(var i = 0; i < arrs.length; i++){
        returnArr.push (objIndex(arrs[i]).bounds)
    }
    return returnArr
}

function setBounds(params){
    obj = objIndex(params[0])
    x = eval(params[1])
    y = eval(params[2])
    r = eval(params[3])
    b = eval(params[4])
    if(y == b){
        objBounds = obj.bounds
        objheight = objBounds[3] - objBounds[1]
        objWidth = objBounds[2] - objBounds[0]
        b= objheight * ((r - x)/objWidth) + y
    }
    if(r != x && b!=y){
        resize(obj,[r - x,b - y])
    }
    moveTo(obj, x,y)
}

function namesInLayer(params){
    layerName = params[0]
    returnArr = new Array()
    obj = objIndex(layerName)
    if(obj.layers == null){
        returnArr.push(obj.name)
        return returnArr
    }
    for(var i = 0; i < obj.layers.length; i++){
        returnArr.push(obj.layers[i].name)
    }
    return returnArr;
}

function addImage(params){
    newObj = pushImage(params[0])
    newObj.name = params[1]
}
//collegeInfo("libXML.xml")
function collegeInfo(name){
grabInformationsToXML(_scriptPath + name)
}

function makePathSelectArea(params){
    var sa = new SelectArea()
    contentBound = new Array()
    contentBound.push( eval(params[0]))
    contentBound.push( eval(params[1]))
    contentBound.push( eval(params[2]))
    contentBound.push( eval(params[3]))
    contentBound[1] += 5
    contentBound[3] += 500
    textObjName = params[4]
    objIndex(textObjName)
    textObj = app.activeDocument.activeLayer
    text = textObj.textItem

    objNames = new Array()
    for(var i = 5; i<params.length; i++){
        objNames.push(params[i])
    }
    sa.applyPath(objNames,contentBound)
    sa.outputPath (_scriptPath + "newPath.ai" );
    points = sa.readAI (_scriptPath + "newPath.ai" );
    tempText = kaiti("tempText",points)
    sa.deletePath("newTempPath")
    //setText(["tempText",text])
    objIndex("tempText")
    active= app.activeDocument.activeLayer
    app.activeDocument.activeLayer .move(objIndex(textObjName),ElementPlacement.PLACEBEFORE)
    objIndex("tempText")
    copyTextItem(textObj.textItem,active.textItem)
    setContent(active,textObj.textItem.content)
    deleteLayer(textObjName)
    tempText.name = nameFromFullName(textObjName)
}

function setContent(dest,srcText){
    app.activeDocument.activeLayer = dest
    dest.textItem.contents = srcText
    tFont = dest.textItem.font;

    fontType("simsun");
    fontType(tFont);
}

function copyTextItem(from,to){
to.color = from.color
textDirection(from.direction)
fontType(from.font)
fontSize(from.size)
textJustify(from.justification)
firstLineIndent(from.size * 2)
if(from.useAutoLeading){
    leading(from.useAutoLeading,0)
}else{
    leading(from.useAutoLeading,from.leading)
}
idHard()
//setContent(to, from.contents)
/*
to.alternateLigatures = from.alternateLigatures
to.antiAliasMethod = from.antiAliasMethod
to.autoKerning = from.autoKerning
to.baselineShift = from.baselineShift
to.capitalization = from.capitalization
to.desiredGlyphScaling = from.desiredGlyphScaling
to.desiredLetterScaling = from.desiredLetterScaling
to.desiredWordScaling = from.desiredWordScaling
to.fauxBold = from.fauxBold
to.fauxItalic = from.fauxItalic
to.firstLineIndent = from.firstLineIndent
to.hangingPunctuation = from.hangingPunctuation
to.height = from.height
to.horizontalScale = from.horizontalScale
to.hyphenateAfterFirst = from.hyphenateAfterFirst
 to.hyphenateBeforeLast = from.hyphenateBeforeLast
 to.hyphenateCapitalWords = from.hyphenateCapitalWords
 to.hyphenateWordsLongerThan = from.hyphenateWordsLongerThan
 to.hyphenation = from.hyphenation
 to.hyphenationZone = from.hyphenationZone
 to.hyphenLimit = from.hyphenLimit
 to.kind = from.kind
 to.language = from.language
// from.leading?to.leading = from.leading:""
 to.leftIndent = from.leftIndent
 to.ligatures = from.ligatures
 to.maximumGlyphScaling = from.maximumGlyphScaling
 to.maximumLetterScaling = from.maximumLetterScaling
 to.maximumWordScaling = from.maximumWordScaling
 to.minimumGlyphScaling = from.minimumGlyphScaling
to.minimumLetterScaling = from.minimumLetterScaling
 to.minimumWordScaling = from.minimumWordScaling
 to.noBreak = from.noBreak
// to.oldStyle = from.oldStyle
 to.parent = from.parent
 to.position = from.position
 to.rightIndent = from.rightIndent

 to.spaceAfter = from.spaceAfter
 to.strikeThru = from.strikeThru
to.textComposer = from.textComposer
to.tracking = from.tracking
to.typename = from.typename
to.underline = from.underline
to.verticalScale = from.verticalScale
to.warpBend = from.warpBend
to.warpDirection = from.warpDirection
to.warpHorizontalDistortion = from.warpHorizontalDistortion
to.warpStyle = from.warpStyle
to.width = from.width
*/
}

function setText(params){
    var textName = params[0]
    text = params[1]
    
    setContent (objIndex(textName), text)
}

function objIndexJSX(params){
    objIndex(params[0])
}

function deleteLayerJSX(params){
    deleteLayer(params[0])
}

function manuPath(masklayer){
    textObj = app.activeDocument.activeLayer
    array = [textObj.bounds[0],textObj.bounds[1],textObj.bounds[2],textObj.bounds[3],genFullName(textObj),masklayer]
    makePathSelectArea(array)
}

