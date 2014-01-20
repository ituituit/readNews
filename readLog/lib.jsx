//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>utils>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

var _image_width = 1189
var _image_height = 841
var _to_point_perfix_x = 100 / _image_width
var _to_point_perfix_y = 100 / _image_height
var _ruler = 11
function initScreen(width,height){
    _image_width = width
    _image_height = height
}
function covertPercent(obj_width){
    return _image_width/obj_width * 100 / _image_width
}
function moveTo(obj,x,y){
    obj.translate(- obj.bounds[0] +x,-obj.bounds[1]+y)  
}

function resize(obj,size){
    var width = obj.bounds[2] - obj.bounds[0];
    var height=  obj.bounds[3] - obj.bounds[1];
    obj.resize(covertPercent(width)* size[0],covertPercent(height)* size[1],AnchorPosition.TOPLEFT);
}

function pushImages(filePaths,rects){
    for(var i = 0; i < filePaths.length; i++){
        image = pushImage(filePaths[i]);
        resize(image,[rects[i][2],rects[i][3]])
        var x = rects[i][0] + contentBounds[0]
        var y = rects[i][1] + contentBounds[1]
        image.translate(-image.bounds[0] + x , -image.bounds[1] +y)
    }
}
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
        var idRlt = charIDToTypeID( "#Rlt" );
        desc2220.putUnitDouble( idHrzn, idRlt, 0.000000 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc2220.putUnitDouble( idVrtc, idRlt, 0.000000 );
    var idOfst = charIDToTypeID( "Ofst" );
    desc2219.putObject( idOfst, idOfst, desc2220 );
executeAction( idPlc, desc2219, DialogModes.NO );
return app.activeDocument.activeLayer;
}

function emboss( content,x,y,width,height ,points){
// =======================================================
var idMk = charIDToTypeID( "Mk  " );
    var desc531 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref98 = new ActionReference();
        var idTxLr = charIDToTypeID( "TxLr" );
        ref98.putClass( idTxLr );
    desc531.putReference( idnull, ref98 );
    var idUsng = charIDToTypeID( "Usng" );
        var desc532 = new ActionDescriptor();
        var idTxt = charIDToTypeID( "Txt " );
        desc532.putString( idTxt, content );
        var idwarp = stringIDToTypeID( "warp" );
            var desc533 = new ActionDescriptor();
            var idwarpStyle = stringIDToTypeID( "warpStyle" );
            var idwarpStyle = stringIDToTypeID( "warpStyle" );
            var idwarpNone = stringIDToTypeID( "warpNone" );
            desc533.putEnumerated( idwarpStyle, idwarpStyle, idwarpNone );
            var idwarpValue = stringIDToTypeID( "warpValue" );
            desc533.putDouble( idwarpValue, 0.000000 );
            var idwarpPerspective = stringIDToTypeID( "warpPerspective" );
            desc533.putDouble( idwarpPerspective, 0.000000 );
            var idwarpPerspectiveOther = stringIDToTypeID( "warpPerspectiveOther" );
            desc533.putDouble( idwarpPerspectiveOther, 0.000000 );
            var idwarpRotate = stringIDToTypeID( "warpRotate" );
            var idOrnt = charIDToTypeID( "Ornt" );
            var idHrzn = charIDToTypeID( "Hrzn" );
            desc533.putEnumerated( idwarpRotate, idOrnt, idHrzn );
        var idwarp = stringIDToTypeID( "warp" );
        desc532.putObject( idwarp, idwarp, desc533 );
        var idTxtC = charIDToTypeID( "TxtC" );
            var desc534 = new ActionDescriptor();
            var idHrzn = charIDToTypeID( "Hrzn" );
            var idPrc = charIDToTypeID( "#Prc" );
            desc534.putUnitDouble( idHrzn, idPrc, x * _to_point_perfix_x);
            var idVrtc = charIDToTypeID( "Vrtc" );
            var idPrc = charIDToTypeID( "#Prc" );
            desc534.putUnitDouble( idVrtc, idPrc, y * _to_point_perfix_y);
        var idPnt = charIDToTypeID( "Pnt " );
        desc532.putObject( idTxtC, idPnt, desc534 );
        var idtextGridding = stringIDToTypeID( "textGridding" );
        var idtextGridding = stringIDToTypeID( "textGridding" );
        var idNone = charIDToTypeID( "None" );
        desc532.putEnumerated( idtextGridding, idtextGridding, idNone );
        var idOrnt = charIDToTypeID( "Ornt" );
        var idOrnt = charIDToTypeID( "Ornt" );
        var idHrzn = charIDToTypeID( "Hrzn" );
        desc532.putEnumerated( idOrnt, idOrnt, idHrzn );
        var idAntA = charIDToTypeID( "AntA" );
        var idAnnt = charIDToTypeID( "Annt" );
        var idantiAliasSharp = stringIDToTypeID( "antiAliasSharp" );
        desc532.putEnumerated( idAntA, idAnnt, idantiAliasSharp );
        //>>shape
        var idtextShape = stringIDToTypeID( "textShape" );
            var list97 = new ActionList();
                var desc535 = new ActionDescriptor();
                
                roundRect(desc535,points)
                var idTEXT = charIDToTypeID( "TEXT" );
                var idTEXT = charIDToTypeID( "TEXT" );
                var idbox = stringIDToTypeID( "box" );
                desc535.putEnumerated( idTEXT, idTEXT, idbox );
                var idOrnt = charIDToTypeID( "Ornt" );
                var idOrnt = charIDToTypeID( "Ornt" );
                var idHrzn = charIDToTypeID( "Hrzn" );
                desc535.putEnumerated( idOrnt, idOrnt, idHrzn );
                var idTrnf = charIDToTypeID( "Trnf" );
                    var desc536 = new ActionDescriptor();
                    var idxx = stringIDToTypeID( "xx" );
                    desc536.putDouble( idxx, 1.000000 );
                    var idxy = stringIDToTypeID( "xy" );
                    desc536.putDouble( idxy, 0.000000 );
                    var idyx = stringIDToTypeID( "yx" );
                    desc536.putDouble( idyx, 0.000000 );
                    var idyy = stringIDToTypeID( "yy" );
                    desc536.putDouble( idyy, 1.000000 );
                    var idtx = stringIDToTypeID( "tx" );
                    desc536.putDouble( idtx, 0.000000 );
                    var idty = stringIDToTypeID( "ty" );
                    desc536.putDouble( idty, 0.000000 );
                var idTrnf = charIDToTypeID( "Trnf" );
                desc535.putObject( idTrnf, idTrnf, desc536 );
                var idrowCount = stringIDToTypeID( "rowCount" );
                desc535.putInteger( idrowCount, 1 );
                var idcolumnCount = stringIDToTypeID( "columnCount" );
                desc535.putInteger( idcolumnCount, 1 );
                var idrowMajorOrder = stringIDToTypeID( "rowMajorOrder" );
                desc535.putBoolean( idrowMajorOrder, true );
                var idrowGutter = stringIDToTypeID( "rowGutter" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc535.putUnitDouble( idrowGutter, idPnt, 0.000000 );
                var idcolumnGutter = stringIDToTypeID( "columnGutter" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc535.putUnitDouble( idcolumnGutter, idPnt, 0.000000 );
                var idSpcn = charIDToTypeID( "Spcn" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc535.putUnitDouble( idSpcn, idPnt, 0.000000 );
                var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                var idalignByAscent = stringIDToTypeID( "alignByAscent" );
                desc535.putEnumerated( idframeBaselineAlignment, idframeBaselineAlignment, idalignByAscent );
                var idfirstBaselineMinimum = stringIDToTypeID( "firstBaselineMinimum" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc535.putUnitDouble( idfirstBaselineMinimum, idPnt, 0.000000 );
                var idbounds = stringIDToTypeID( "bounds" );
                    var desc537 = new ActionDescriptor();
                    var idTop = charIDToTypeID( "Top " );
                    desc537.putDouble( idTop, 0.000000 );
                    var idLeft = charIDToTypeID( "Left" );
                    desc537.putDouble( idLeft, 0.000000 );
                    var idBtom = charIDToTypeID( "Btom" );
                    desc537.putDouble( idBtom, height );
                    var idRght = charIDToTypeID( "Rght" );
                    desc537.putDouble( idRght, width );
                var idRctn = charIDToTypeID( "Rctn" );
                desc535.putObject( idbounds, idRctn, desc537 );
            var idtextShape = stringIDToTypeID( "textShape" );
            list97.putObject( idtextShape, desc535 );
            //<<<shape
        desc532.putList( idtextShape, list97 );
        var idTxtt = charIDToTypeID( "Txtt" );
            var list98 = new ActionList();
                var desc538 = new ActionDescriptor();
                var idFrom = charIDToTypeID( "From" );
                desc538.putInteger( idFrom, 0 );
                var idT = charIDToTypeID( "T   " );
                desc538.putInteger( idT, 40000);
                var idTxtS = charIDToTypeID( "TxtS" );
                    var desc539 = new ActionDescriptor();
                    var idstyleSheetHasParent = stringIDToTypeID( "styleSheetHasParent" );
                    desc539.putBoolean( idstyleSheetHasParent, true );
                    var idfontPostScriptName = stringIDToTypeID( "fontPostScriptName" );
                    desc539.putString( idfontPostScriptName, """SimSun""" );
                    var idFntN = charIDToTypeID( "FntN" );
                    desc539.putString( idFntN, """SimSun""" );
                    var idFntS = charIDToTypeID( "FntS" );
                    desc539.putString( idFntS, """Regular""" );
                    var idScrp = charIDToTypeID( "Scrp" );
                    desc539.putInteger( idScrp, 25 );
                    var idFntT = charIDToTypeID( "FntT" );
                    desc539.putInteger( idFntT, 1 );
                    
                    var idSz = charIDToTypeID( "Sz  " );
                    var idPnt = charIDToTypeID( "#Pnt" );
                    desc539.putUnitDouble( idSz, idPnt, 10.000000);
                    
                    
                    var idautoLeading = stringIDToTypeID( "autoLeading" );
                    desc539.putBoolean( idautoLeading, false );
                    var idLdng = charIDToTypeID( "Ldng" );
                    var idPnt = charIDToTypeID( "#Pnt" );
                    desc539.putUnitDouble( idLdng, idPnt, 17.500000 );
                    
                    
                    var iddigitSet = stringIDToTypeID( "digitSet" );
                    var iddigitSet = stringIDToTypeID( "digitSet" );
                    var iddefaultDigits = stringIDToTypeID( "defaultDigits" );
                    desc539.putEnumerated( iddigitSet, iddigitSet, iddefaultDigits );
                    var idmarkYDistFromBaseline = stringIDToTypeID( "markYDistFromBaseline" );
                    var idPnt = charIDToTypeID( "#Pnt" );
                    desc539.putUnitDouble( idmarkYDistFromBaseline, idPnt, 24.000000 );
                    var idClr = charIDToTypeID( "Clr " );
                        var desc540 = new ActionDescriptor();
                        var idCyn = charIDToTypeID( "Cyn " );
                        desc540.putDouble( idCyn, 92.620000 );
                        var idMgnt = charIDToTypeID( "Mgnt" );
                        desc540.putDouble( idMgnt, 87.630000 );
                        var idYlw = charIDToTypeID( "Ylw " );
                        desc540.putDouble( idYlw, 88.600000 );
                        var idBlck = charIDToTypeID( "Blck" );
                        desc540.putDouble( idBlck, 79.530000 );
                    var idCMYC = charIDToTypeID( "CMYC" );
                    desc539.putObject( idClr, idCMYC, desc540 );
                var idTxtS = charIDToTypeID( "TxtS" );
                desc538.putObject( idTxtS, idTxtS, desc539 );
            var idTxtt = charIDToTypeID( "Txtt" );
            list98.putObject( idTxtt, desc538 );
        desc532.putList( idTxtt, list98 );
        var idparagraphStyleRange = stringIDToTypeID( "paragraphStyleRange" );
            var list99 = new ActionList();
                var desc541 = new ActionDescriptor();
                var idFrom = charIDToTypeID( "From" );
                desc541.putInteger( idFrom, 0 );
                var idT = charIDToTypeID( "T   " );
                desc541.putInteger( idT, 40000 );
                var idparagraphStyle = stringIDToTypeID( "paragraphStyle" );
                    var desc542 = new ActionDescriptor();
                    var idstyleSheetHasParent = stringIDToTypeID( "styleSheetHasParent" );
                    desc542.putBoolean( idstyleSheetHasParent, true );
                    var idAlgn = charIDToTypeID( "Algn" );
                    var idAlg = charIDToTypeID( "Alg " );
                    var idjustifyLeft = stringIDToTypeID( "justifyLeft" );
                    desc542.putEnumerated( idAlgn, idAlg, idjustifyLeft );
                var idparagraphStyle = stringIDToTypeID( "paragraphStyle" );
                desc541.putObject( idparagraphStyle, idparagraphStyle, desc542 );
            var idparagraphStyleRange = stringIDToTypeID( "paragraphStyleRange" );
            list99.putObject( idparagraphStyleRange, desc541 );
        desc532.putList( idparagraphStyleRange, list99 );
        var idkerningRange = stringIDToTypeID( "kerningRange" );
            var list100 = new ActionList();
        desc532.putList( idkerningRange, list100 );
    var idTxLr = charIDToTypeID( "TxLr" );
    desc531.putObject( idUsng, idTxLr, desc532 );
executeAction( idMk, desc531, DialogModes.NO );
if(contentFolder != null){
    app.activeDocument.activeLayer.move(contentFolder,ElementPlacement.INSIDE)
}
return app.activeDocument.activeLayer;
}

function makePath(points){
    var list275 = new ActionList();
    for(var i = 0 ; i < points.length; i++){
        var desc8115 = new ActionDescriptor();
        var idAnch = charIDToTypeID( "Anch" );
        var desc8116 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idRlt = charIDToTypeID( "#Rlt" );
        $.writeln(points)
        desc8116.putUnitDouble( idHrzn, idRlt, points[i] [0]);
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc8116.putUnitDouble( idVrtc, idRlt, points[i][1] ) ;
        var idPnt = charIDToTypeID( "Pnt " );
        desc8115.putObject( idAnch, idPnt, desc8116 );                                           
        var idSmoo = charIDToTypeID( "Smoo" );
        desc8115.putBoolean( idSmoo, true );
        var idPthp = charIDToTypeID( "Pthp" );
        list275.putObject( idPthp, desc8115 );
    }
    return list275
}

function roundRect(shapeObj,points)
{
    var idPath = charIDToTypeID( "Path" );
    var desc8112 = new ActionDescriptor();
    var idpathComponents = stringIDToTypeID( "pathComponents" );
    var list273 = new ActionList();
                            var desc8113 = new ActionDescriptor();
                            var idshapeOperation = stringIDToTypeID( "shapeOperation" );
                            var idshapeOperation = stringIDToTypeID( "shapeOperation" );
                            var idxor = stringIDToTypeID( "xor" );
                            desc8113.putEnumerated( idshapeOperation, idshapeOperation, idxor );
                            var idSbpL = charIDToTypeID( "SbpL" );
                                var list274 = new ActionList();
                                    var desc8114 = new ActionDescriptor();
                                    var idClsp = charIDToTypeID( "Clsp" );
                                    desc8114.putBoolean( idClsp, true );
                                    var idPts = charIDToTypeID( "Pts " );
                                    desc8114.putList( idPts, makePath(points) );
                                var idSbpl = charIDToTypeID( "Sbpl" );
                                list274.putObject( idSbpl, desc8114 );
                            desc8113.putList( idSbpL, list274 );
                        var idPaCm = charIDToTypeID( "PaCm" );
                        list273.putObject( idPaCm, desc8113 );
                    desc8112.putList( idpathComponents, list273 );
                var idpathClass = stringIDToTypeID( "pathClass" );
                shapeObj.putObject( idPath, idpathClass, desc8112 );
}
/*
function roundRect(shapeObj)
{
    var idPath = charIDToTypeID( "Path" );
    var desc8112 = new ActionDescriptor();
    var idpathComponents = stringIDToTypeID( "pathComponents" );
    var list273 = new ActionLisidPtst();
                            var desc8113 = new ActionDescriptor();
                            var idshapeOperation = stringIDToTypeID( "shapeOperation" );
                            var idshapeOperation = stringIDToTypeID( "shapeOperation" );
                            var idxor = stringIDToTypeID( "xor" );
                            desc8113.putEnumerated( idshapeOperation, idshapeOperation, idxor );
                            var idSbpL = charIDToTypeID( "SbpL" );
                                var list274 = new ActionList();
                                    var desc8114 = new ActionDescriptor();
                                    var idClsp = charIDToTypeID( "Clsp" );
                                    desc8114.putBoolean( idClsp, true );
                                    var idPts = charIDToTypeID( "Pts " );
                                        var list275 = new ActionList();
                                        /*
                                            var desc8115 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8116 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8116.putUnitDouble( idHrzn, idRlt, 220.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8116.putUnitDouble( idVrtc, idRlt, 95.500000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8115.putObject( idAnch, idPnt, desc8116 );
                                            var idFwd = charIDToTypeID( "Fwd " );
                                                var desc8117 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8117.putUnitDouble( idHrzn, idRlt, 220.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8117.putUnitDouble( idVrtc, idRlt, 130.017792 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8115.putObject( idFwd, idPnt, desc8117 );
                                            var idBwd = charIDToTypeID( "Bwd " );
                                                var desc8118 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8118.putUnitDouble( idHrzn, idRlt, 220.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8118.putUnitDouble( idVrtc, idRlt, 80.044487 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8115.putObject( idBwd, idPnt, desc8118 );
                                            var idSmoo = charIDToTypeID( "Smoo" );
                                            desc8115.putBoolean( idSmoo, true );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8115 );
                                       
                                     list275.putObject( charIDToTypeID( "Pthp" ), makePath );
                                            var desc8119 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8120 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8120.putUnitDouble( idHrzn, idRlt, 315.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8120.putUnitDouble( idVrtc, idRlt, 158.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8119.putObject( idAnch, idPnt, desc8120 );
                                            var idFwd = charIDToTypeID( "Fwd " );
                                                var desc8121 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8121.putUnitDouble( idHrzn, idRlt, 331.282806 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8121.putUnitDouble( idVrtc, idRlt, 158.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8119.putObject( idFwd, idPnt, desc8121 );
                                            var idBwd = charIDToTypeID( "Bwd " );
                                                var desc8122 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8122.putUnitDouble( idHrzn, idRlt, 262.532959 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8122.putUnitDouble( idVrtc, idRlt, 158.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8119.putObject( idBwd, idPnt, desc8122 );
                                            var idSmoo = charIDToTypeID( "Smoo" );
                                            desc8119.putBoolean( idSmoo, true );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8119 );
                                            var desc8123 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8124 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8124.putUnitDouble( idHrzn, idRlt, 360.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8124.putUnitDouble( idVrtc, idRlt, 150.551819 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8123.putObject( idAnch, idPnt, desc8124 );
                                            var idFwd = charIDToTypeID( "Fwd " );
                                                var desc8125 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8125.putUnitDouble( idHrzn, idRlt, 360.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8125.putUnitDouble( idVrtc, idRlt, 150.551819 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8123.putObject( idFwd, idPnt, desc8125 );
                                            var idBwd = charIDToTypeID( "Bwd " );
                                                var desc8126 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8126.putUnitDouble( idHrzn, idRlt, 346.605133 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8126.putUnitDouble( idVrtc, idRlt, 155.300888 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8123.putObject( idBwd, idPnt, desc8126 );
                                            var idSmoo = charIDToTypeID( "Smoo" );
                                            desc8123.putBoolean( idSmoo, false );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8123 );
                                            var desc8127 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8128 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8128.putUnitDouble( idHrzn, idRlt, 360.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8128.putUnitDouble( idVrtc, idRlt, 232.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8127.putObject( idAnch, idPnt, desc8128 );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8127 );
                                            var desc8129 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8130 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8130.putUnitDouble( idHrzn, idRlt, 95.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8130.putUnitDouble( idVrtc, idRlt, 232.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8129.putObject( idAnch, idPnt, desc8130 );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8129 );
                                            var desc8131 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8132 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8132.putUnitDouble( idHrzn, idRlt, 95.000000 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8132.putUnitDouble( idVrtc, idRlt, 55.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8131.putObject( idAnch, idPnt, desc8132 );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8131 );
                                            var desc8133 = new ActionDescriptor();
                                            var idAnch = charIDToTypeID( "Anch" );
                                                var desc8134 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8134.putUnitDouble( idHrzn, idRlt, 242.673248 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8134.putUnitDouble( idVrtc, idRlt, 55.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8133.putObject( idAnch, idPnt, desc8134 );
                                            var idFwd = charIDToTypeID( "Fwd " );
                                                var desc8135 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8135.putUnitDouble( idHrzn, idRlt, 228.545166 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8135.putUnitDouble( idVrtc, idRlt, 65.909729 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8133.putObject( idFwd, idPnt, desc8135 );
                                            var idBwd = charIDToTypeID( "Bwd " );
                                                var desc8136 = new ActionDescriptor();
                                                var idHrzn = charIDToTypeID( "Hrzn" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8136.putUnitDouble( idHrzn, idRlt, 242.673248 );
                                                var idVrtc = charIDToTypeID( "Vrtc" );
                                                var idRlt = charIDToTypeID( "#Rlt" );
                                                desc8136.putUnitDouble( idVrtc, idRlt, 55.000000 );
                                            var idPnt = charIDToTypeID( "Pnt " );
                                            desc8133.putObject( idBwd, idPnt, desc8136 );
                                            var idSmoo = charIDToTypeID( "Smoo" );
                                            desc8133.putBoolean( idSmoo, false );
                                        var idPthp = charIDToTypeID( "Pthp" );
                                        list275.putObject( idPthp, desc8133 );
                                    desc8114.putList( idPts, list275 );
                                var idSbpl = charIDToTypeID( "Sbpl" );
                                list274.putObject( idSbpl, desc8114 );
                            desc8113.putList( idSbpL, list274 );
                        var idPaCm = charIDToTypeID( "PaCm" );
                        list273.putObject( idPaCm, desc8113 );
                    desc8112.putList( idpathComponents, list273 );
                var idpathClass = stringIDToTypeID( "pathClass" );
                shapeObj.putObject( idPath, idpathClass, desc8112 );
    }
 */
function textShape(desc8108){
    var idtextShape = stringIDToTypeID( "textShape" );
            var list272 = new ActionList();
                var desc8111 = new ActionDescriptor();
                
                    roundRect(desc8111)
                var idTEXT = charIDToTypeID( "TEXT" );
                var idTEXT = charIDToTypeID( "TEXT" );
                var idbox = stringIDToTypeID( "box" );
                desc8111.putEnumerated( idTEXT, idTEXT, idbox );
                var idOrnt = charIDToTypeID( "Ornt" );
                var idOrnt = charIDToTypeID( "Ornt" );
                var idHrzn = charIDToTypeID( "Hrzn" );
                desc8111.putEnumerated( idOrnt, idOrnt, idHrzn );
                var idTrnf = charIDToTypeID( "Trnf" );
                    var desc8137 = new ActionDescriptor();
                    var idxx = stringIDToTypeID( "xx" );
                    desc8137.putDouble( idxx, 1.000000 );
                    var idxy = stringIDToTypeID( "xy" );
                    desc8137.putDouble( idxy, 0.000000 );
                    var idyx = stringIDToTypeID( "yx" );
                    desc8137.putDouble( idyx, 0.000000 );
                    var idyy = stringIDToTypeID( "yy" );
                    desc8137.putDouble( idyy, 1.000000 );
                    var idtx = stringIDToTypeID( "tx" );
                    desc8137.putDouble( idtx, -95.000000 );
                    var idty = stringIDToTypeID( "ty" );
                    desc8137.putDouble( idty, -55.000000 );
                var idTrnf = charIDToTypeID( "Trnf" );
                desc8111.putObject( idTrnf, idTrnf, desc8137 );
                var idrowCount = stringIDToTypeID( "rowCount" );
                desc8111.putInteger( idrowCount, 1 );
                var idcolumnCount = stringIDToTypeID( "columnCount" );
                desc8111.putInteger( idcolumnCount, 1 );
                var idrowMajorOrder = stringIDToTypeID( "rowMajorOrder" );
                desc8111.putBoolean( idrowMajorOrder, true );
                var idrowGutter = stringIDToTypeID( "rowGutter" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc8111.putUnitDouble( idrowGutter, idPnt, 0.000000 );
                var idcolumnGutter = stringIDToTypeID( "columnGutter" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc8111.putUnitDouble( idcolumnGutter, idPnt, 0.000000 );
                var idSpcn = charIDToTypeID( "Spcn" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc8111.putUnitDouble( idSpcn, idPnt, 0.000000 );
                var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                var idframeBaselineAlignment = stringIDToTypeID( "frameBaselineAlignment" );
                var idalignByAscent = stringIDToTypeID( "alignByAscent" );
                desc8111.putEnumerated( idframeBaselineAlignment, idframeBaselineAlignment, idalignByAscent );
                var idfirstBaselineMinimum = stringIDToTypeID( "firstBaselineMinimum" );
                var idPnt = charIDToTypeID( "#Pnt" );
                desc8111.putUnitDouble( idfirstBaselineMinimum, idPnt, 0.000000 );
                var idbounds = stringIDToTypeID( "bounds" );
                    var desc8138 = new ActionDescriptor();
                    var idTop = charIDToTypeID( "Top " );
                    desc8138.putDouble( idTop, 55.000000 );
                    var idLeft = charIDToTypeID( "Left" );
                    desc8138.putDouble( idLeft, 95.000000 );
                    var idBtom = charIDToTypeID( "Btom" );
                    desc8138.putDouble( idBtom, 232.000000 );
                    var idRght = charIDToTypeID( "Rght" );
                    desc8138.putDouble( idRght, 360.000000 );
                var idRctn = charIDToTypeID( "Rctn" );
                desc8111.putObject( idbounds, idRctn, desc8138 );
            var idtextShape = stringIDToTypeID( "textShape" );
            list272.putObject( idtextShape, desc8111 );
        desc8108.putList( idtextShape, list272 );
}

function pathCurrentLayer(){
    var idsetd = charIDToTypeID( "setd" );
    var desc3428 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref728 = new ActionReference();
        var idChnl = charIDToTypeID( "Chnl" );
        var idfsel = charIDToTypeID( "fsel" );
        ref728.putProperty( idChnl, idfsel );
    desc3428.putReference( idnull, ref728 );
    var idT = charIDToTypeID( "T   " );
        var desc3429 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc3429.putUnitDouble( idHrzn, idRlt, 133.000000 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc3429.putUnitDouble( idVrtc, idRlt, 261.000000 );
    var idPnt = charIDToTypeID( "Pnt " );
    desc3428.putObject( idT, idPnt, desc3429 );
    var idTlrn = charIDToTypeID( "Tlrn" );
    desc3428.putInteger( idTlrn, 32 );
    var idAntA = charIDToTypeID( "AntA" );
    desc3428.putBoolean( idAntA, true );
executeAction( idsetd, desc3428, DialogModes.NO );

// =======================================================
var idInvs = charIDToTypeID( "Invs" );
executeAction( idInvs, undefined, DialogModes.NO );

var idMk = charIDToTypeID( "Mk  " );
    var desc3435 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref733 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        ref733.putClass( idPath );
    desc3435.putReference( idnull, ref733 );
    var idFrom = charIDToTypeID( "From" );
        var ref734 = new ActionReference();
        var idcsel = charIDToTypeID( "csel" );
        var idfsel = charIDToTypeID( "fsel" );
        ref734.putProperty( idcsel, idfsel );
    desc3435.putReference( idFrom, ref734 );
    var idTlrn = charIDToTypeID( "Tlrn" );
    var idPxl = charIDToTypeID( "#Pxl" );
    desc3435.putUnitDouble( idTlrn, idPxl, 2.000000 );
executeAction( idMk, desc3435, DialogModes.NO );
}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<utils<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
function resizePath(obj,size){
      var width = obj.bounds[2] - obj.bounds[0];
    var height=  obj.bounds[3] - obj.bounds[1];
    // =======================================================
var idTrnf = charIDToTypeID( "Trnf" );
    var desc47 = new ActionDescriptor();
    var idnull = charIDToTypeID( "null" );
        var ref24 = new ActionReference();
        var idPath = charIDToTypeID( "Path" );
        var idOrdn = charIDToTypeID( "Ordn" );
        var idTrgt = charIDToTypeID( "Trgt" );
        ref24.putEnumerated( idPath, idOrdn, idTrgt );
    desc47.putReference( idnull, ref24 );
    var idFTcs = charIDToTypeID( "FTcs" );
    var idQCSt = charIDToTypeID( "QCSt" );
    var idQcsa = charIDToTypeID( "Qcsa" );
    desc47.putEnumerated( idFTcs, idQCSt, idQcsa );
    var idOfst = charIDToTypeID( "Ofst" );
        var desc48 = new ActionDescriptor();
        var idHrzn = charIDToTypeID( "Hrzn" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc48.putUnitDouble( idHrzn, idRlt, 0 );
        var idVrtc = charIDToTypeID( "Vrtc" );
        var idRlt = charIDToTypeID( "#Rlt" );
        desc48.putUnitDouble( idVrtc, idRlt, 0 );
    var idOfst = charIDToTypeID( "Ofst" );
    desc47.putObject( idOfst, idOfst, desc48 );
    var idWdth = charIDToTypeID( "Wdth" );
    var idPrc = charIDToTypeID( "#Prc" );
    desc47.putUnitDouble( idWdth, idPrc, covertPercent(width)* size[0] );
    var idHght = charIDToTypeID( "Hght" );
    var idPrc = charIDToTypeID( "#Prc" );
    desc47.putUnitDouble( idHght, idPrc, covertPercent(height)* size[1] );
executeAction( idTrnf, desc47, DialogModes.NO );

 }

function objSelect(name){
    select (name)
    return app.activeDocument.activeLayer
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

function initNewsWithContents(contents,pathsPoints){
        
        //var _photo = pushImage("/Library/Application Support/Apple/iChat Icons/Flags/Argentina.png")
        for(var i = 0; i < contents.length; i++){
                if(i == 0){
                    var text1 = emboss (contents[0],contentBounds[0],contentBounds[1],contentBounds[2],_image_height / 2,pathsPoints[i])
                }else{
                    objPre =  contentFolder.layers.getByName("row_dumplicated_" + (i - 1))
                    width = objPre.bounds[2] - objPre.bounds[0] 
                    var text1 = emboss (contents[i],objPre.bounds[0] ,objPre.bounds[3] + _ruler,width,_image_height / 2,pathsPoints[i])
                }
                text1.name = "text_" + i
                
                var rowDuplicated = rowSample.duplicate(contentFolder,ElementPlacement.INSIDE)
                rowDuplicated.name = "row_dumplicated_" + i
                moveTo(rowDuplicated,text1.bounds[0] ,text1.bounds[3]  + _ruler)
                rowDuplicatedWidth = rowDuplicated.bounds[2] - rowDuplicated.bounds[0]
                textWidth = text1.bounds[2] - text1.bounds[0]
                rowDuplicated.resize(covertPercent(rowDuplicatedWidth) * textWidth,100,AnchorPosition.TOPLEFT);
        //        moveTo(_photo,text1.bounds[2] ,text1.bounds[1] )
        //        resize(_photo,text1.bounds[3] - text1.bounds[1])
                }
    }


function EnsNews(){
}

EnsNews.prototype.init = function(){
     rootObj = app.activeDocument.layers.getByName ("2版").layers.getByName ("景区快讯")
     contentFolder = rootObj.layerSets.getByName("content_empnews");
    //this.rect  =[[77.68,456.0,119.0,210.0],[378.68,213.0,133.0,290.0],[77.68,132.0,279.0,163.0],[378.68,132.0,133.0,59.0],[218.68,525.0,293.0,141.0],[218.68,317.0,138.0,117.0],[218.68,456.0,138.0,47.0],[77.68,317.0,119.0,117.0],]
    return this
}

EnsNews.prototype.reshotNews = function(names,rects){
    this.rect = rects
      //  rootObj = app.activeDocument.layers.getByName ("2版").layers.getByName ("景区快讯")
       // rowSample = rootObj.layers.getByName("head").layers.getByName("row_sample")
        contentFolder = rootObj.layerSets.getByName("content_empnews");
        for(var i = 0 ; i < names.length; i++){
       //     obj = contentFolder.layers.getByName("text_" + i)
                   select("row_sample")
                   select(names[i])
            resizePath(app.activeDocument.activeLayer, [this.rect[i][2],this.rect[i][3]])
            moveTo(app.activeDocument.activeLayer, this.rect[i][0],this.rect[i][1])
            //resize(obj,[this.rect[i][2],this.rect[i][3]])
        }
}

EnsNews.prototype.pathObjs = function(text,pics){
    
    $.write("\r")
    $.write("private static float[][] pathBounds = {{" + text.bounds + "},")
    for(var i = 0 ;i < pics.length;i++){
        $.write("{" + pics[i].bounds + "},")
    }
    $.write("}\r")
}

EnsNews.prototype.pathObjs2 = function(textObjName,arrayPoints){
    //arrayPoints = [[66.0,124.56], [334.08,124.56], [334.08,125.52], [193.2,125.52], [193.2,203.76], [327.6,203.76], [327.6,125.52], [334.08,125.52], [334.08,274.08], [66.0,274.08]]

    text = objSelect(textObjName).textItem.contents;
    //initNewsWithContents([text],[arrayPoints])
    //alert(app.activeDocument.activeLayer.bounds)
    textbounds = app.activeDocument.activeLayer.bounds
    emboss (text,textbounds[0],textbounds[1],textbounds[2],textbounds[3],arrayPoints)
}

EnsNews.prototype.drawSplitLine = function(names,rect){
    names = ["""row_sample""","""row_sample""","""row_sample""","""row_sample""","""row_sample""","""row_sample""","""cow_sample""","""cow_sample""","""cow_sample""","""cow_sample""",]
    this.rect = rect;
    contentFolder = objSelect("content_empnews")
    for(var i =0; i < names.length; i++){
        
        var rowDuplicated = objSelect(names[i]).duplicate(contentFolder,ElementPlacement.INSIDE)
        rowDuplicated.name = names[i] + "_duplicated"
        
        resize(rowDuplicated, [this.rect[i][2],this.rect[i][3]])
        moveTo(rowDuplicated, this.rect[i][0],this.rect[i][1])
    }     

}

EnsNews.prototype.boundsOfNews = function(){
    $.write("\r")
    $.write("""private static float[][] textBounds = {""");
    for(var i = 1 ; i<= 6; i++){
        obj = objSelect("text_" + i);
        
        $.write("{"+obj.bounds+"},")
    }
    $.write("""}\r""")
    $.write("""private String [] textNames = {""");
   for(var i = 1 ; i <= 6; i++){
       //obj = objSelect("text_" + i + ",")
      $.write("\"text_" + i + "\",")
    }
   $.write("""}\r""")
}

function ISpeak(){
    
}
ISpeak.prototype.init = function(){
   return this
   }
ISpeak.prototype.boundsContent = function(){
     obj = objSelect ("i_speak")
      $.write("\r")
      $.write("private static String iSpeakname = \"i_speak\";\r");
   $.write("private static float[] iSpeakBound = {" + obj.bounds);
   $.write("}\r")
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


function parentBounds(parentname){
    return objSelect(parentname).bound
}

function dumplicateExtendPsd(params){
    fileName = params[0];
    destName = params[1];
    group = params[2];
    var fileRef = File(destName) 
    var dest = app.open(fileRef)    
    var fileRef = File(fileName) 
    var docRef = app.open(fileRef)    
    act = objSelect(group)
        
    act.duplicate ( dest );
    app.activeDocument = dest
}

function changeText(params){
    objName = params[0];
    content = params[1];
    objSelect(objName).textItem.contents = content
}

function changeName(params){
    objName = params[0];
    objNameChanged = params[1]
    objSelect(objName).name = objNameChanged
}
    
function bounds(arrs){
    returnArr = new Array()
    for(var i = 0; i < arrs.length; i++){
        
        returnArr.push (objSelect(arrs[i]).bounds)
    }
    return returnArr
}
