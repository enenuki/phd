/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import org.apache.xalan.templates.ElemApplyImport;
/*   5:    */ import org.apache.xalan.templates.ElemApplyTemplates;
/*   6:    */ import org.apache.xalan.templates.ElemAttribute;
/*   7:    */ import org.apache.xalan.templates.ElemCallTemplate;
/*   8:    */ import org.apache.xalan.templates.ElemChoose;
/*   9:    */ import org.apache.xalan.templates.ElemComment;
/*  10:    */ import org.apache.xalan.templates.ElemCopy;
/*  11:    */ import org.apache.xalan.templates.ElemCopyOf;
/*  12:    */ import org.apache.xalan.templates.ElemElement;
/*  13:    */ import org.apache.xalan.templates.ElemExsltFuncResult;
/*  14:    */ import org.apache.xalan.templates.ElemExsltFunction;
/*  15:    */ import org.apache.xalan.templates.ElemExtensionDecl;
/*  16:    */ import org.apache.xalan.templates.ElemExtensionScript;
/*  17:    */ import org.apache.xalan.templates.ElemFallback;
/*  18:    */ import org.apache.xalan.templates.ElemForEach;
/*  19:    */ import org.apache.xalan.templates.ElemIf;
/*  20:    */ import org.apache.xalan.templates.ElemLiteralResult;
/*  21:    */ import org.apache.xalan.templates.ElemMessage;
/*  22:    */ import org.apache.xalan.templates.ElemNumber;
/*  23:    */ import org.apache.xalan.templates.ElemOtherwise;
/*  24:    */ import org.apache.xalan.templates.ElemPI;
/*  25:    */ import org.apache.xalan.templates.ElemParam;
/*  26:    */ import org.apache.xalan.templates.ElemSort;
/*  27:    */ import org.apache.xalan.templates.ElemTemplate;
/*  28:    */ import org.apache.xalan.templates.ElemText;
/*  29:    */ import org.apache.xalan.templates.ElemTextLiteral;
/*  30:    */ import org.apache.xalan.templates.ElemUnknown;
/*  31:    */ import org.apache.xalan.templates.ElemValueOf;
/*  32:    */ import org.apache.xalan.templates.ElemVariable;
/*  33:    */ import org.apache.xalan.templates.ElemWhen;
/*  34:    */ import org.apache.xalan.templates.ElemWithParam;
/*  35:    */ import org.apache.xml.utils.QName;
/*  36:    */ 
/*  37:    */ public class XSLTSchema
/*  38:    */   extends XSLTElementDef
/*  39:    */ {
/*  40:    */   XSLTSchema()
/*  41:    */   {
/*  42: 72 */     build();
/*  43:    */   }
/*  44:    */   
/*  45:    */   void build()
/*  46:    */   {
/*  47: 82 */     XSLTAttributeDef hrefAttr = new XSLTAttributeDef(null, "href", 2, true, false, 1);
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51: 86 */     XSLTAttributeDef elementsAttr = new XSLTAttributeDef(null, "elements", 12, true, false, 1);
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 94 */     XSLTAttributeDef methodAttr = new XSLTAttributeDef(null, "method", 9, false, false, 1);
/*  60:    */     
/*  61: 96 */     XSLTAttributeDef versionAttr = new XSLTAttributeDef(null, "version", 13, false, false, 1);
/*  62:    */     
/*  63: 98 */     XSLTAttributeDef encodingAttr = new XSLTAttributeDef(null, "encoding", 1, false, false, 1);
/*  64:    */     
/*  65:100 */     XSLTAttributeDef omitXmlDeclarationAttr = new XSLTAttributeDef(null, "omit-xml-declaration", 8, false, false, 1);
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:104 */     XSLTAttributeDef standaloneAttr = new XSLTAttributeDef(null, "standalone", 8, false, false, 1);
/*  70:    */     
/*  71:    */ 
/*  72:107 */     XSLTAttributeDef doctypePublicAttr = new XSLTAttributeDef(null, "doctype-public", 1, false, false, 1);
/*  73:    */     
/*  74:    */ 
/*  75:110 */     XSLTAttributeDef doctypeSystemAttr = new XSLTAttributeDef(null, "doctype-system", 1, false, false, 1);
/*  76:    */     
/*  77:    */ 
/*  78:113 */     XSLTAttributeDef cdataSectionElementsAttr = new XSLTAttributeDef(null, "cdata-section-elements", 19, false, false, 1);
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:117 */     XSLTAttributeDef indentAttr = new XSLTAttributeDef(null, "indent", 8, false, false, 1);
/*  83:    */     
/*  84:119 */     XSLTAttributeDef mediaTypeAttr = new XSLTAttributeDef(null, "media-type", 1, false, false, 1);
/*  85:    */     
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:126 */     XSLTAttributeDef nameAttrRequired = new XSLTAttributeDef(null, "name", 9, true, false, 1);
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:131 */     XSLTAttributeDef nameAVTRequired = new XSLTAttributeDef(null, "name", 18, true, true, 2);
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:138 */     XSLTAttributeDef nameAVT_NCNAMERequired = new XSLTAttributeDef(null, "name", 17, true, true, 2);
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:144 */     XSLTAttributeDef nameAttrOpt_ERROR = new XSLTAttributeDef(null, "name", 9, false, false, 1);
/* 110:    */     
/* 111:    */ 
/* 112:    */ 
/* 113:148 */     XSLTAttributeDef useAttr = new XSLTAttributeDef(null, "use", 5, true, false, 1);
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:152 */     XSLTAttributeDef namespaceAVTOpt = new XSLTAttributeDef(null, "namespace", 2, false, true, 2);
/* 118:    */     
/* 119:    */ 
/* 120:    */ 
/* 121:156 */     XSLTAttributeDef decimalSeparatorAttr = new XSLTAttributeDef(null, "decimal-separator", 6, false, 1, ".");
/* 122:    */     
/* 123:    */ 
/* 124:159 */     XSLTAttributeDef infinityAttr = new XSLTAttributeDef(null, "infinity", 1, false, 1, "Infinity");
/* 125:    */     
/* 126:161 */     XSLTAttributeDef minusSignAttr = new XSLTAttributeDef(null, "minus-sign", 6, false, 1, "-");
/* 127:    */     
/* 128:163 */     XSLTAttributeDef NaNAttr = new XSLTAttributeDef(null, "NaN", 1, false, 1, "NaN");
/* 129:    */     
/* 130:165 */     XSLTAttributeDef percentAttr = new XSLTAttributeDef(null, "percent", 6, false, 1, "%");
/* 131:    */     
/* 132:167 */     XSLTAttributeDef perMilleAttr = new XSLTAttributeDef(null, "per-mille", 6, false, false, 1);
/* 133:    */     
/* 134:    */ 
/* 135:170 */     XSLTAttributeDef zeroDigitAttr = new XSLTAttributeDef(null, "zero-digit", 6, false, 1, "0");
/* 136:    */     
/* 137:172 */     XSLTAttributeDef digitAttr = new XSLTAttributeDef(null, "digit", 6, false, 1, "#");
/* 138:    */     
/* 139:174 */     XSLTAttributeDef patternSeparatorAttr = new XSLTAttributeDef(null, "pattern-separator", 6, false, 1, ";");
/* 140:    */     
/* 141:    */ 
/* 142:    */ 
/* 143:178 */     XSLTAttributeDef groupingSeparatorAttr = new XSLTAttributeDef(null, "grouping-separator", 6, false, 1, ",");
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:184 */     XSLTAttributeDef useAttributeSetsAttr = new XSLTAttributeDef(null, "use-attribute-sets", 10, false, false, 1);
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:190 */     XSLTAttributeDef testAttrRequired = new XSLTAttributeDef(null, "test", 5, true, false, 1);
/* 156:    */     
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:196 */     XSLTAttributeDef selectAttrRequired = new XSLTAttributeDef(null, "select", 5, true, false, 1);
/* 162:    */     
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:202 */     XSLTAttributeDef selectAttrOpt = new XSLTAttributeDef(null, "select", 5, false, false, 1);
/* 168:    */     
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:208 */     XSLTAttributeDef selectAttrDefNode = new XSLTAttributeDef(null, "select", 5, false, 1, "node()");
/* 174:    */     
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:213 */     XSLTAttributeDef selectAttrDefDot = new XSLTAttributeDef(null, "select", 5, false, 1, ".");
/* 179:    */     
/* 180:    */ 
/* 181:216 */     XSLTAttributeDef matchAttrRequired = new XSLTAttributeDef(null, "match", 4, true, false, 1);
/* 182:    */     
/* 183:    */ 
/* 184:219 */     XSLTAttributeDef matchAttrOpt = new XSLTAttributeDef(null, "match", 4, false, false, 1);
/* 185:    */     
/* 186:    */ 
/* 187:222 */     XSLTAttributeDef priorityAttr = new XSLTAttributeDef(null, "priority", 7, false, false, 1);
/* 188:    */     
/* 189:    */ 
/* 190:    */ 
/* 191:226 */     XSLTAttributeDef modeAttr = new XSLTAttributeDef(null, "mode", 9, false, false, 1);
/* 192:    */     
/* 193:    */ 
/* 194:229 */     XSLTAttributeDef spaceAttr = new XSLTAttributeDef("http://www.w3.org/XML/1998/namespace", "space", false, false, false, 2, "default", 2, "preserve", 1);
/* 195:    */     
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:235 */     XSLTAttributeDef spaceAttrLiteral = new XSLTAttributeDef("http://www.w3.org/XML/1998/namespace", "space", 2, false, true, 1);
/* 201:    */     
/* 202:    */ 
/* 203:    */ 
/* 204:239 */     XSLTAttributeDef stylesheetPrefixAttr = new XSLTAttributeDef(null, "stylesheet-prefix", 1, true, false, 1);
/* 205:    */     
/* 206:    */ 
/* 207:242 */     XSLTAttributeDef resultPrefixAttr = new XSLTAttributeDef(null, "result-prefix", 1, true, false, 1);
/* 208:    */     
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:247 */     XSLTAttributeDef disableOutputEscapingAttr = new XSLTAttributeDef(null, "disable-output-escaping", 8, false, false, 1);
/* 213:    */     
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:253 */     XSLTAttributeDef levelAttr = new XSLTAttributeDef(null, "level", false, false, false, 1, "single", 1, "multiple", 2, "any", 3);
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222:257 */     levelAttr.setDefault("single");
/* 223:258 */     XSLTAttributeDef countAttr = new XSLTAttributeDef(null, "count", 4, false, false, 1);
/* 224:    */     
/* 225:260 */     XSLTAttributeDef fromAttr = new XSLTAttributeDef(null, "from", 4, false, false, 1);
/* 226:    */     
/* 227:262 */     XSLTAttributeDef valueAttr = new XSLTAttributeDef(null, "value", 5, false, false, 1);
/* 228:    */     
/* 229:264 */     XSLTAttributeDef formatAttr = new XSLTAttributeDef(null, "format", 1, false, true, 1);
/* 230:    */     
/* 231:266 */     formatAttr.setDefault("1");
/* 232:    */     
/* 233:    */ 
/* 234:269 */     XSLTAttributeDef langAttr = new XSLTAttributeDef(null, "lang", 13, false, true, 1);
/* 235:    */     
/* 236:    */ 
/* 237:    */ 
/* 238:273 */     XSLTAttributeDef letterValueAttr = new XSLTAttributeDef(null, "letter-value", false, true, false, 1, "alphabetic", 1, "traditional", 2);
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:279 */     XSLTAttributeDef groupingSeparatorAVT = new XSLTAttributeDef(null, "grouping-separator", 6, false, true, 1);
/* 245:    */     
/* 246:    */ 
/* 247:    */ 
/* 248:283 */     XSLTAttributeDef groupingSizeAttr = new XSLTAttributeDef(null, "grouping-size", 7, false, true, 1);
/* 249:    */     
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:288 */     XSLTAttributeDef dataTypeAttr = new XSLTAttributeDef(null, "data-type", false, true, true, 1, "text", 1, "number", 1);
/* 254:    */     
/* 255:290 */     dataTypeAttr.setDefault("text");
/* 256:    */     
/* 257:    */ 
/* 258:293 */     XSLTAttributeDef orderAttr = new XSLTAttributeDef(null, "order", false, true, false, 1, "ascending", 1, "descending", 2);
/* 259:    */     
/* 260:    */ 
/* 261:296 */     orderAttr.setDefault("ascending");
/* 262:    */     
/* 263:    */ 
/* 264:299 */     XSLTAttributeDef caseOrderAttr = new XSLTAttributeDef(null, "case-order", false, true, false, 1, "upper-first", 1, "lower-first", 2);
/* 265:    */     
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:304 */     XSLTAttributeDef terminateAttr = new XSLTAttributeDef(null, "terminate", 8, false, false, 1);
/* 270:    */     
/* 271:306 */     terminateAttr.setDefault("no");
/* 272:    */     
/* 273:    */ 
/* 274:309 */     XSLTAttributeDef xslExcludeResultPrefixesAttr = new XSLTAttributeDef("http://www.w3.org/1999/XSL/Transform", "exclude-result-prefixes", 20, false, false, 1);
/* 275:    */     
/* 276:    */ 
/* 277:    */ 
/* 278:313 */     XSLTAttributeDef xslExtensionElementPrefixesAttr = new XSLTAttributeDef("http://www.w3.org/1999/XSL/Transform", "extension-element-prefixes", 15, false, false, 1);
/* 279:    */     
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:318 */     XSLTAttributeDef xslUseAttributeSetsAttr = new XSLTAttributeDef("http://www.w3.org/1999/XSL/Transform", "use-attribute-sets", 10, false, false, 1);
/* 284:    */     
/* 285:    */ 
/* 286:321 */     XSLTAttributeDef xslVersionAttr = new XSLTAttributeDef("http://www.w3.org/1999/XSL/Transform", "version", 13, false, false, 1);
/* 287:    */     
/* 288:    */ 
/* 289:    */ 
/* 290:325 */     XSLTElementDef charData = new XSLTElementDef(this, null, "text()", null, null, null, new ProcessorCharacters(), ElemTextLiteral.class);
/* 291:    */     
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:330 */     charData.setType(2);
/* 296:    */     
/* 297:332 */     XSLTElementDef whiteSpaceOnly = new XSLTElementDef(this, null, "text()", null, null, null, null, ElemTextLiteral.class);
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:338 */     charData.setType(2);
/* 304:    */     
/* 305:340 */     XSLTAttributeDef resultAttr = new XSLTAttributeDef(null, "*", 3, false, true, 2);
/* 306:    */     
/* 307:342 */     XSLTAttributeDef xslResultAttr = new XSLTAttributeDef("http://www.w3.org/1999/XSL/Transform", "*", 1, false, false, 2);
/* 308:    */     
/* 309:    */ 
/* 310:    */ 
/* 311:346 */     XSLTElementDef[] templateElements = new XSLTElementDef[23];
/* 312:347 */     XSLTElementDef[] templateElementsAndParams = new XSLTElementDef[24];
/* 313:348 */     XSLTElementDef[] templateElementsAndSort = new XSLTElementDef[24];
/* 314:    */     
/* 315:350 */     XSLTElementDef[] exsltFunctionElements = new XSLTElementDef[24];
/* 316:    */     
/* 317:352 */     XSLTElementDef[] charTemplateElements = new XSLTElementDef[15];
/* 318:353 */     XSLTElementDef resultElement = new XSLTElementDef(this, null, "*", null, templateElements, new XSLTAttributeDef[] { spaceAttrLiteral, xslExcludeResultPrefixesAttr, xslExtensionElementPrefixesAttr, xslUseAttributeSetsAttr, xslVersionAttr, xslResultAttr, resultAttr }, new ProcessorLRE(), ElemLiteralResult.class, 20, true);
/* 319:    */     
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:366 */     XSLTElementDef unknownElement = new XSLTElementDef(this, "*", "unknown", null, templateElementsAndParams, new XSLTAttributeDef[] { xslExcludeResultPrefixesAttr, xslExtensionElementPrefixesAttr, xslUseAttributeSetsAttr, xslVersionAttr, xslResultAttr, resultAttr }, new ProcessorUnknown(), ElemUnknown.class, 20, true);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:377 */     XSLTElementDef xslValueOf = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "value-of", null, null, new XSLTAttributeDef[] { selectAttrRequired, disableOutputEscapingAttr }, new ProcessorTemplateElem(), ElemValueOf.class, 20, true);
/* 343:    */     
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:384 */     XSLTElementDef xslCopyOf = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "copy-of", null, null, new XSLTAttributeDef[] { selectAttrRequired }, new ProcessorTemplateElem(), ElemCopyOf.class, 20, true);
/* 350:    */     
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:390 */     XSLTElementDef xslNumber = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "number", null, null, new XSLTAttributeDef[] { levelAttr, countAttr, fromAttr, valueAttr, formatAttr, langAttr, letterValueAttr, groupingSeparatorAVT, groupingSizeAttr }, new ProcessorTemplateElem(), ElemNumber.class, 20, true);
/* 356:    */     
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:407 */     XSLTElementDef xslSort = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "sort", null, null, new XSLTAttributeDef[] { selectAttrDefDot, langAttr, dataTypeAttr, orderAttr, caseOrderAttr }, new ProcessorTemplateElem(), ElemSort.class, 19, true);
/* 373:    */     
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:419 */     XSLTElementDef xslWithParam = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "with-param", null, templateElements, new XSLTAttributeDef[] { nameAttrRequired, selectAttrOpt }, new ProcessorTemplateElem(), ElemWithParam.class, 19, true);
/* 385:    */     
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:426 */     XSLTElementDef xslApplyTemplates = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "apply-templates", null, new XSLTElementDef[] { xslSort, xslWithParam }, new XSLTAttributeDef[] { selectAttrDefNode, modeAttr }, new ProcessorTemplateElem(), ElemApplyTemplates.class, 20, true);
/* 392:    */     
/* 393:    */ 
/* 394:    */ 
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:435 */     XSLTElementDef xslApplyImports = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "apply-imports", null, null, new XSLTAttributeDef[0], new ProcessorTemplateElem(), ElemApplyImport.class);
/* 401:    */     
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:441 */     XSLTElementDef xslForEach = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "for-each", null, templateElementsAndSort, new XSLTAttributeDef[] { selectAttrRequired, spaceAttr }, new ProcessorTemplateElem(), ElemForEach.class, true, false, true, 20, true);
/* 407:    */     
/* 408:    */ 
/* 409:    */ 
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:448 */     XSLTElementDef xslIf = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "if", null, templateElements, new XSLTAttributeDef[] { testAttrRequired, spaceAttr }, new ProcessorTemplateElem(), ElemIf.class, 20, true);
/* 414:    */     
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:456 */     XSLTElementDef xslWhen = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "when", null, templateElements, new XSLTAttributeDef[] { testAttrRequired, spaceAttr }, new ProcessorTemplateElem(), ElemWhen.class, false, true, 1, true);
/* 422:    */     
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:464 */     XSLTElementDef xslOtherwise = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "otherwise", null, templateElements, new XSLTAttributeDef[] { spaceAttr }, new ProcessorTemplateElem(), ElemOtherwise.class, false, false, 2, false);
/* 430:    */     
/* 431:    */ 
/* 432:    */ 
/* 433:    */ 
/* 434:    */ 
/* 435:    */ 
/* 436:    */ 
/* 437:472 */     XSLTElementDef xslChoose = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "choose", null, new XSLTElementDef[] { xslWhen, xslOtherwise }, new XSLTAttributeDef[] { spaceAttr }, new ProcessorTemplateElem(), ElemChoose.class, true, false, true, 20, true);
/* 438:    */     
/* 439:    */ 
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:480 */     XSLTElementDef xslAttribute = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "attribute", null, charTemplateElements, new XSLTAttributeDef[] { nameAVTRequired, namespaceAVTOpt, spaceAttr }, new ProcessorTemplateElem(), ElemAttribute.class, 20, true);
/* 446:    */     
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:489 */     XSLTElementDef xslCallTemplate = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "call-template", null, new XSLTElementDef[] { xslWithParam }, new XSLTAttributeDef[] { nameAttrRequired }, new ProcessorTemplateElem(), ElemCallTemplate.class, 20, true);
/* 455:    */     
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:496 */     XSLTElementDef xslVariable = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "variable", null, templateElements, new XSLTAttributeDef[] { nameAttrRequired, selectAttrOpt }, new ProcessorTemplateElem(), ElemVariable.class, 20, true);
/* 462:    */     
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:    */ 
/* 468:    */ 
/* 469:504 */     XSLTElementDef xslParam = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "param", null, templateElements, new XSLTAttributeDef[] { nameAttrRequired, selectAttrOpt }, new ProcessorTemplateElem(), ElemParam.class, 19, true);
/* 470:    */     
/* 471:    */ 
/* 472:    */ 
/* 473:    */ 
/* 474:    */ 
/* 475:    */ 
/* 476:    */ 
/* 477:512 */     XSLTElementDef xslText = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "text", null, new XSLTElementDef[] { charData }, new XSLTAttributeDef[] { disableOutputEscapingAttr }, new ProcessorText(), ElemText.class, 20, true);
/* 478:    */     
/* 479:    */ 
/* 480:    */ 
/* 481:    */ 
/* 482:    */ 
/* 483:    */ 
/* 484:519 */     XSLTElementDef xslProcessingInstruction = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "processing-instruction", null, charTemplateElements, new XSLTAttributeDef[] { nameAVT_NCNAMERequired, spaceAttr }, new ProcessorTemplateElem(), ElemPI.class, 20, true);
/* 485:    */     
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:528 */     XSLTElementDef xslElement = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "element", null, templateElements, new XSLTAttributeDef[] { nameAVTRequired, namespaceAVTOpt, useAttributeSetsAttr, spaceAttr }, new ProcessorTemplateElem(), ElemElement.class, 20, true);
/* 494:    */     
/* 495:    */ 
/* 496:    */ 
/* 497:    */ 
/* 498:    */ 
/* 499:    */ 
/* 500:    */ 
/* 501:    */ 
/* 502:    */ 
/* 503:538 */     XSLTElementDef xslComment = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "comment", null, charTemplateElements, new XSLTAttributeDef[] { spaceAttr }, new ProcessorTemplateElem(), ElemComment.class, 20, true);
/* 504:    */     
/* 505:    */ 
/* 506:    */ 
/* 507:    */ 
/* 508:    */ 
/* 509:    */ 
/* 510:545 */     XSLTElementDef xslCopy = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "copy", null, templateElements, new XSLTAttributeDef[] { spaceAttr, useAttributeSetsAttr }, new ProcessorTemplateElem(), ElemCopy.class, 20, true);
/* 511:    */     
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:553 */     XSLTElementDef xslMessage = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "message", null, templateElements, new XSLTAttributeDef[] { terminateAttr }, new ProcessorTemplateElem(), ElemMessage.class, 20, true);
/* 519:    */     
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:560 */     XSLTElementDef xslFallback = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "fallback", null, templateElements, new XSLTAttributeDef[] { spaceAttr }, new ProcessorTemplateElem(), ElemFallback.class, 20, true);
/* 526:    */     
/* 527:    */ 
/* 528:    */ 
/* 529:    */ 
/* 530:    */ 
/* 531:    */ 
/* 532:    */ 
/* 533:568 */     XSLTElementDef exsltFunction = new XSLTElementDef(this, "http://exslt.org/functions", "function", null, exsltFunctionElements, new XSLTAttributeDef[] { nameAttrRequired }, new ProcessorExsltFunction(), ElemExsltFunction.class);
/* 534:    */     
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:577 */     XSLTElementDef exsltResult = new XSLTElementDef(this, "http://exslt.org/functions", "result", null, templateElements, new XSLTAttributeDef[] { selectAttrOpt }, new ProcessorExsltFuncResult(), ElemExsltFuncResult.class);
/* 543:    */     
/* 544:    */ 
/* 545:    */ 
/* 546:    */ 
/* 547:    */ 
/* 548:    */ 
/* 549:    */ 
/* 550:    */ 
/* 551:    */ 
/* 552:    */ 
/* 553:588 */     int i = 0;
/* 554:    */     
/* 555:590 */     templateElements[(i++)] = charData;
/* 556:    */     
/* 557:    */ 
/* 558:593 */     templateElements[(i++)] = xslApplyTemplates;
/* 559:594 */     templateElements[(i++)] = xslCallTemplate;
/* 560:595 */     templateElements[(i++)] = xslApplyImports;
/* 561:596 */     templateElements[(i++)] = xslForEach;
/* 562:597 */     templateElements[(i++)] = xslValueOf;
/* 563:598 */     templateElements[(i++)] = xslCopyOf;
/* 564:599 */     templateElements[(i++)] = xslNumber;
/* 565:600 */     templateElements[(i++)] = xslChoose;
/* 566:601 */     templateElements[(i++)] = xslIf;
/* 567:602 */     templateElements[(i++)] = xslText;
/* 568:603 */     templateElements[(i++)] = xslCopy;
/* 569:604 */     templateElements[(i++)] = xslVariable;
/* 570:605 */     templateElements[(i++)] = xslMessage;
/* 571:606 */     templateElements[(i++)] = xslFallback;
/* 572:    */     
/* 573:    */ 
/* 574:609 */     templateElements[(i++)] = xslProcessingInstruction;
/* 575:610 */     templateElements[(i++)] = xslComment;
/* 576:611 */     templateElements[(i++)] = xslElement;
/* 577:612 */     templateElements[(i++)] = xslAttribute;
/* 578:613 */     templateElements[(i++)] = resultElement;
/* 579:614 */     templateElements[(i++)] = unknownElement;
/* 580:615 */     templateElements[(i++)] = exsltFunction;
/* 581:616 */     templateElements[(i++)] = exsltResult;
/* 582:    */     
/* 583:618 */     System.arraycopy(templateElements, 0, templateElementsAndParams, 0, i);
/* 584:619 */     System.arraycopy(templateElements, 0, templateElementsAndSort, 0, i);
/* 585:620 */     System.arraycopy(templateElements, 0, exsltFunctionElements, 0, i);
/* 586:    */     
/* 587:622 */     templateElementsAndParams[i] = xslParam;
/* 588:623 */     templateElementsAndSort[i] = xslSort;
/* 589:624 */     exsltFunctionElements[i] = xslParam;
/* 590:    */     
/* 591:626 */     i = 0;
/* 592:627 */     charTemplateElements[(i++)] = charData;
/* 593:    */     
/* 594:    */ 
/* 595:630 */     charTemplateElements[(i++)] = xslApplyTemplates;
/* 596:631 */     charTemplateElements[(i++)] = xslCallTemplate;
/* 597:632 */     charTemplateElements[(i++)] = xslApplyImports;
/* 598:633 */     charTemplateElements[(i++)] = xslForEach;
/* 599:634 */     charTemplateElements[(i++)] = xslValueOf;
/* 600:635 */     charTemplateElements[(i++)] = xslCopyOf;
/* 601:636 */     charTemplateElements[(i++)] = xslNumber;
/* 602:637 */     charTemplateElements[(i++)] = xslChoose;
/* 603:638 */     charTemplateElements[(i++)] = xslIf;
/* 604:639 */     charTemplateElements[(i++)] = xslText;
/* 605:640 */     charTemplateElements[(i++)] = xslCopy;
/* 606:641 */     charTemplateElements[(i++)] = xslVariable;
/* 607:642 */     charTemplateElements[(i++)] = xslMessage;
/* 608:643 */     charTemplateElements[(i++)] = xslFallback;
/* 609:    */     
/* 610:645 */     XSLTElementDef importDef = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "import", null, null, new XSLTAttributeDef[] { hrefAttr }, new ProcessorImport(), null, 1, true);
/* 611:    */     
/* 612:    */ 
/* 613:    */ 
/* 614:    */ 
/* 615:    */ 
/* 616:    */ 
/* 617:652 */     XSLTElementDef includeDef = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "include", null, null, new XSLTAttributeDef[] { hrefAttr }, new ProcessorInclude(), null, 20, true);
/* 618:    */     
/* 619:    */ 
/* 620:    */ 
/* 621:    */ 
/* 622:    */ 
/* 623:    */ 
/* 624:    */ 
/* 625:660 */     XSLTAttributeDef[] scriptAttrs = { new XSLTAttributeDef(null, "lang", 13, true, false, 2), new XSLTAttributeDef(null, "src", 2, false, false, 2) };
/* 626:    */     
/* 627:    */ 
/* 628:    */ 
/* 629:    */ 
/* 630:    */ 
/* 631:666 */     XSLTAttributeDef[] componentAttrs = { new XSLTAttributeDef(null, "prefix", 13, true, false, 2), new XSLTAttributeDef(null, "elements", 14, false, false, 2), new XSLTAttributeDef(null, "functions", 14, false, false, 2) };
/* 632:    */     
/* 633:    */ 
/* 634:    */ 
/* 635:    */ 
/* 636:    */ 
/* 637:    */ 
/* 638:    */ 
/* 639:674 */     XSLTElementDef[] topLevelElements = { includeDef, importDef, whiteSpaceOnly, unknownElement, new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "strip-space", null, null, new XSLTAttributeDef[] { elementsAttr }, new ProcessorStripSpace(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "preserve-space", null, null, new XSLTAttributeDef[] { elementsAttr }, new ProcessorPreserveSpace(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "output", null, null, new XSLTAttributeDef[] { methodAttr, versionAttr, encodingAttr, omitXmlDeclarationAttr, standaloneAttr, doctypePublicAttr, doctypeSystemAttr, cdataSectionElementsAttr, indentAttr, mediaTypeAttr, XSLTAttributeDef.m_foreignAttr }, new ProcessorOutputElem(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "key", null, null, new XSLTAttributeDef[] { nameAttrRequired, matchAttrRequired, useAttr }, new ProcessorKey(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "decimal-format", null, null, new XSLTAttributeDef[] { nameAttrOpt_ERROR, decimalSeparatorAttr, groupingSeparatorAttr, infinityAttr, minusSignAttr, NaNAttr, percentAttr, perMilleAttr, zeroDigitAttr, digitAttr, patternSeparatorAttr }, new ProcessorDecimalFormat(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "attribute-set", null, new XSLTElementDef[] { xslAttribute }, new XSLTAttributeDef[] { nameAttrRequired, useAttributeSetsAttr }, new ProcessorAttributeSet(), null, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "variable", null, templateElements, new XSLTAttributeDef[] { nameAttrRequired, selectAttrOpt }, new ProcessorGlobalVariableDecl(), ElemVariable.class, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "param", null, templateElements, new XSLTAttributeDef[] { nameAttrRequired, selectAttrOpt }, new ProcessorGlobalParamDecl(), ElemParam.class, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "template", null, templateElementsAndParams, new XSLTAttributeDef[] { matchAttrOpt, nameAttrOpt_ERROR, priorityAttr, modeAttr, spaceAttr }, new ProcessorTemplate(), ElemTemplate.class, true, 20, true), new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "namespace-alias", null, null, new XSLTAttributeDef[] { stylesheetPrefixAttr, resultPrefixAttr }, new ProcessorNamespaceAlias(), null, 20, true), new XSLTElementDef(this, "http://xml.apache.org/xalan", "component", null, new XSLTElementDef[] { new XSLTElementDef(this, "http://xml.apache.org/xalan", "script", null, new XSLTElementDef[] { charData }, scriptAttrs, new ProcessorLRE(), ElemExtensionScript.class, 20, true) }, componentAttrs, new ProcessorLRE(), ElemExtensionDecl.class), new XSLTElementDef(this, "http://xml.apache.org/xslt", "component", null, new XSLTElementDef[] { new XSLTElementDef(this, "http://xml.apache.org/xslt", "script", null, new XSLTElementDef[] { charData }, scriptAttrs, new ProcessorLRE(), ElemExtensionScript.class, 20, true) }, componentAttrs, new ProcessorLRE(), ElemExtensionDecl.class), exsltFunction };
/* 640:    */     
/* 641:    */ 
/* 642:    */ 
/* 643:    */ 
/* 644:    */ 
/* 645:    */ 
/* 646:    */ 
/* 647:    */ 
/* 648:    */ 
/* 649:    */ 
/* 650:    */ 
/* 651:    */ 
/* 652:    */ 
/* 653:    */ 
/* 654:    */ 
/* 655:    */ 
/* 656:    */ 
/* 657:    */ 
/* 658:    */ 
/* 659:    */ 
/* 660:    */ 
/* 661:    */ 
/* 662:    */ 
/* 663:    */ 
/* 664:    */ 
/* 665:    */ 
/* 666:    */ 
/* 667:    */ 
/* 668:    */ 
/* 669:    */ 
/* 670:    */ 
/* 671:    */ 
/* 672:    */ 
/* 673:    */ 
/* 674:    */ 
/* 675:    */ 
/* 676:    */ 
/* 677:    */ 
/* 678:    */ 
/* 679:    */ 
/* 680:    */ 
/* 681:    */ 
/* 682:    */ 
/* 683:    */ 
/* 684:    */ 
/* 685:    */ 
/* 686:    */ 
/* 687:    */ 
/* 688:    */ 
/* 689:    */ 
/* 690:    */ 
/* 691:    */ 
/* 692:    */ 
/* 693:    */ 
/* 694:    */ 
/* 695:    */ 
/* 696:    */ 
/* 697:    */ 
/* 698:    */ 
/* 699:    */ 
/* 700:    */ 
/* 701:    */ 
/* 702:    */ 
/* 703:    */ 
/* 704:    */ 
/* 705:    */ 
/* 706:    */ 
/* 707:    */ 
/* 708:    */ 
/* 709:    */ 
/* 710:    */ 
/* 711:    */ 
/* 712:    */ 
/* 713:    */ 
/* 714:    */ 
/* 715:    */ 
/* 716:    */ 
/* 717:    */ 
/* 718:    */ 
/* 719:    */ 
/* 720:    */ 
/* 721:    */ 
/* 722:    */ 
/* 723:    */ 
/* 724:    */ 
/* 725:    */ 
/* 726:    */ 
/* 727:    */ 
/* 728:    */ 
/* 729:    */ 
/* 730:    */ 
/* 731:    */ 
/* 732:    */ 
/* 733:    */ 
/* 734:    */ 
/* 735:    */ 
/* 736:    */ 
/* 737:    */ 
/* 738:    */ 
/* 739:    */ 
/* 740:    */ 
/* 741:    */ 
/* 742:    */ 
/* 743:    */ 
/* 744:    */ 
/* 745:    */ 
/* 746:    */ 
/* 747:    */ 
/* 748:    */ 
/* 749:    */ 
/* 750:    */ 
/* 751:    */ 
/* 752:    */ 
/* 753:    */ 
/* 754:    */ 
/* 755:    */ 
/* 756:    */ 
/* 757:    */ 
/* 758:    */ 
/* 759:    */ 
/* 760:    */ 
/* 761:    */ 
/* 762:    */ 
/* 763:    */ 
/* 764:    */ 
/* 765:    */ 
/* 766:    */ 
/* 767:    */ 
/* 768:    */ 
/* 769:    */ 
/* 770:    */ 
/* 771:    */ 
/* 772:    */ 
/* 773:    */ 
/* 774:    */ 
/* 775:    */ 
/* 776:    */ 
/* 777:    */ 
/* 778:    */ 
/* 779:    */ 
/* 780:    */ 
/* 781:    */ 
/* 782:    */ 
/* 783:    */ 
/* 784:    */ 
/* 785:    */ 
/* 786:    */ 
/* 787:    */ 
/* 788:    */ 
/* 789:    */ 
/* 790:    */ 
/* 791:    */ 
/* 792:    */ 
/* 793:    */ 
/* 794:    */ 
/* 795:    */ 
/* 796:    */ 
/* 797:    */ 
/* 798:    */ 
/* 799:    */ 
/* 800:    */ 
/* 801:    */ 
/* 802:    */ 
/* 803:    */ 
/* 804:    */ 
/* 805:    */ 
/* 806:    */ 
/* 807:    */ 
/* 808:    */ 
/* 809:844 */     XSLTAttributeDef excludeResultPrefixesAttr = new XSLTAttributeDef(null, "exclude-result-prefixes", 20, false, false, 2);
/* 810:    */     
/* 811:    */ 
/* 812:847 */     XSLTAttributeDef extensionElementPrefixesAttr = new XSLTAttributeDef(null, "extension-element-prefixes", 15, false, false, 2);
/* 813:    */     
/* 814:    */ 
/* 815:850 */     XSLTAttributeDef idAttr = new XSLTAttributeDef(null, "id", 1, false, false, 2);
/* 816:    */     
/* 817:852 */     XSLTAttributeDef versionAttrRequired = new XSLTAttributeDef(null, "version", 13, true, false, 2);
/* 818:    */     
/* 819:    */ 
/* 820:    */ 
/* 821:856 */     XSLTElementDef stylesheetElemDef = new XSLTElementDef(this, "http://www.w3.org/1999/XSL/Transform", "stylesheet", "transform", topLevelElements, new XSLTAttributeDef[] { extensionElementPrefixesAttr, excludeResultPrefixesAttr, idAttr, versionAttrRequired, spaceAttr }, new ProcessorStylesheetElement(), null, true, -1, false);
/* 822:    */     
/* 823:    */ 
/* 824:    */ 
/* 825:    */ 
/* 826:    */ 
/* 827:    */ 
/* 828:    */ 
/* 829:    */ 
/* 830:    */ 
/* 831:    */ 
/* 832:    */ 
/* 833:    */ 
/* 834:869 */     importDef.setElements(new XSLTElementDef[] { stylesheetElemDef, resultElement, unknownElement });
/* 835:    */     
/* 836:    */ 
/* 837:872 */     includeDef.setElements(new XSLTElementDef[] { stylesheetElemDef, resultElement, unknownElement });
/* 838:    */     
/* 839:    */ 
/* 840:875 */     build(null, null, null, new XSLTElementDef[] { stylesheetElemDef, whiteSpaceOnly, resultElement, unknownElement }, null, new ProcessorStylesheetDoc(), null);
/* 841:    */   }
/* 842:    */   
/* 843:889 */   private HashMap m_availElems = new HashMap();
/* 844:    */   
/* 845:    */   public HashMap getElemsAvailable()
/* 846:    */   {
/* 847:899 */     return this.m_availElems;
/* 848:    */   }
/* 849:    */   
/* 850:    */   void addAvailableElement(QName elemName)
/* 851:    */   {
/* 852:908 */     this.m_availElems.put(elemName, elemName);
/* 853:    */   }
/* 854:    */   
/* 855:    */   public boolean elementAvailable(QName elemName)
/* 856:    */   {
/* 857:919 */     return this.m_availElems.containsKey(elemName);
/* 858:    */   }
/* 859:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.XSLTSchema
 * JD-Core Version:    0.7.0.1
 */