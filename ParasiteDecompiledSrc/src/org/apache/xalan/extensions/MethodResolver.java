/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xalan.res.XSLMessages;
/*   8:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*   9:    */ import org.apache.xml.dtm.DTM;
/*  10:    */ import org.apache.xml.dtm.DTMIterator;
/*  11:    */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ import org.apache.xpath.objects.XRTreeFrag;
/*  14:    */ import org.apache.xpath.objects.XString;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ import org.w3c.dom.traversal.NodeIterator;
/*  18:    */ 
/*  19:    */ public class MethodResolver
/*  20:    */ {
/*  21:    */   public static final int STATIC_ONLY = 1;
/*  22:    */   public static final int INSTANCE_ONLY = 2;
/*  23:    */   public static final int STATIC_AND_INSTANCE = 3;
/*  24:    */   public static final int DYNAMIC = 4;
/*  25:    */   private static final int SCOREBASE = 1;
/*  26:    */   
/*  27:    */   public static Constructor getConstructor(Class classObj, Object[] argsIn, Object[][] argsOut, ExpressionContext exprContext)
/*  28:    */     throws NoSuchMethodException, SecurityException, TransformerException
/*  29:    */   {
/*  30: 91 */     Constructor bestConstructor = null;
/*  31: 92 */     Class[] bestParamTypes = null;
/*  32: 93 */     Constructor[] constructors = classObj.getConstructors();
/*  33: 94 */     int nMethods = constructors.length;
/*  34: 95 */     int bestScore = 2147483647;
/*  35: 96 */     int bestScoreCount = 0;
/*  36: 97 */     for (int i = 0; i < nMethods; i++)
/*  37:    */     {
/*  38: 99 */       Constructor ctor = constructors[i];
/*  39:100 */       Class[] paramTypes = ctor.getParameterTypes();
/*  40:101 */       int numberMethodParams = paramTypes.length;
/*  41:102 */       int paramStart = 0;
/*  42:103 */       boolean isFirstExpressionContext = false;
/*  43:    */       int scoreStart;
/*  44:108 */       if (numberMethodParams == argsIn.length + 1)
/*  45:    */       {
/*  46:110 */         Class javaClass = paramTypes[0];
/*  47:112 */         if (!ExpressionContext.class.isAssignableFrom(javaClass)) {
/*  48:    */           continue;
/*  49:    */         }
/*  50:114 */         isFirstExpressionContext = true;
/*  51:115 */         scoreStart = 0;
/*  52:116 */         paramStart++;
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56:123 */         scoreStart = 1000;
/*  57:    */       }
/*  58:125 */       if (argsIn.length == numberMethodParams - paramStart)
/*  59:    */       {
/*  60:128 */         int score = scoreMatch(paramTypes, paramStart, argsIn, scoreStart);
/*  61:130 */         if (-1 != score) {
/*  62:132 */           if (score < bestScore)
/*  63:    */           {
/*  64:135 */             bestConstructor = ctor;
/*  65:136 */             bestParamTypes = paramTypes;
/*  66:137 */             bestScore = score;
/*  67:138 */             bestScoreCount = 1;
/*  68:    */           }
/*  69:140 */           else if (score == bestScore)
/*  70:    */           {
/*  71:141 */             bestScoreCount++;
/*  72:    */           }
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:145 */     if (null == bestConstructor) {
/*  77:147 */       throw new NoSuchMethodException(errString("function", "constructor", classObj, "", 0, argsIn));
/*  78:    */     }
/*  79:156 */     convertParams(argsIn, argsOut, bestParamTypes, exprContext);
/*  80:    */     
/*  81:158 */     return bestConstructor;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static Method getMethod(Class classObj, String name, Object[] argsIn, Object[][] argsOut, ExpressionContext exprContext, int searchMethod)
/*  85:    */     throws NoSuchMethodException, SecurityException, TransformerException
/*  86:    */   {
/*  87:187 */     if (name.indexOf("-") > 0) {
/*  88:188 */       name = replaceDash(name);
/*  89:    */     }
/*  90:189 */     Method bestMethod = null;
/*  91:190 */     Class[] bestParamTypes = null;
/*  92:191 */     Method[] methods = classObj.getMethods();
/*  93:192 */     int nMethods = methods.length;
/*  94:193 */     int bestScore = 2147483647;
/*  95:194 */     int bestScoreCount = 0;
/*  96:196 */     for (int i = 0; i < nMethods; i++)
/*  97:    */     {
/*  98:198 */       Method method = methods[i];
/*  99:    */       
/* 100:200 */       int xsltParamStart = 0;
/* 101:201 */       if (method.getName().equals(name))
/* 102:    */       {
/* 103:203 */         boolean isStatic = Modifier.isStatic(method.getModifiers());
/* 104:204 */         switch (searchMethod)
/* 105:    */         {
/* 106:    */         case 1: 
/* 107:207 */           if (isStatic) {
/* 108:    */             break;
/* 109:    */           }
/* 110:209 */           break;
/* 111:    */         case 2: 
/* 112:214 */           if (!isStatic) {
/* 113:    */             break;
/* 114:    */           }
/* 115:216 */           break;
/* 116:    */         case 3: 
/* 117:    */           break;
/* 118:    */         case 4: 
/* 119:224 */           if (!isStatic) {
/* 120:225 */             xsltParamStart = 1;
/* 121:    */           }
/* 122:    */           break;
/* 123:    */         }
/* 124:227 */         int javaParamStart = 0;
/* 125:228 */         Class[] paramTypes = method.getParameterTypes();
/* 126:229 */         int numberMethodParams = paramTypes.length;
/* 127:230 */         boolean isFirstExpressionContext = false;
/* 128:    */         
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:235 */         int argsLen = null != argsIn ? argsIn.length : 0;
/* 133:    */         int scoreStart;
/* 134:236 */         if (numberMethodParams == argsLen - xsltParamStart + 1)
/* 135:    */         {
/* 136:238 */           Class javaClass = paramTypes[0];
/* 137:239 */           if (!ExpressionContext.class.isAssignableFrom(javaClass)) {
/* 138:    */             continue;
/* 139:    */           }
/* 140:241 */           isFirstExpressionContext = true;
/* 141:242 */           scoreStart = 0;
/* 142:243 */           javaParamStart++;
/* 143:    */         }
/* 144:    */         else
/* 145:    */         {
/* 146:251 */           scoreStart = 1000;
/* 147:    */         }
/* 148:253 */         if (argsLen - xsltParamStart == numberMethodParams - javaParamStart)
/* 149:    */         {
/* 150:256 */           int score = scoreMatch(paramTypes, javaParamStart, argsIn, scoreStart);
/* 151:258 */           if (-1 != score) {
/* 152:260 */             if (score < bestScore)
/* 153:    */             {
/* 154:263 */               bestMethod = method;
/* 155:264 */               bestParamTypes = paramTypes;
/* 156:265 */               bestScore = score;
/* 157:266 */               bestScoreCount = 1;
/* 158:    */             }
/* 159:268 */             else if (score == bestScore)
/* 160:    */             {
/* 161:269 */               bestScoreCount++;
/* 162:    */             }
/* 163:    */           }
/* 164:    */         }
/* 165:    */       }
/* 166:    */     }
/* 167:274 */     if (null == bestMethod) {
/* 168:276 */       throw new NoSuchMethodException(errString("function", "method", classObj, name, searchMethod, argsIn));
/* 169:    */     }
/* 170:284 */     convertParams(argsIn, argsOut, bestParamTypes, exprContext);
/* 171:    */     
/* 172:286 */     return bestMethod;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static String replaceDash(String name)
/* 176:    */   {
/* 177:296 */     char dash = '-';
/* 178:297 */     StringBuffer buff = new StringBuffer("");
/* 179:298 */     for (int i = 0; i < name.length(); i++) {
/* 180:300 */       if (name.charAt(i) != dash) {
/* 181:302 */         if ((i > 0) && (name.charAt(i - 1) == dash)) {
/* 182:303 */           buff.append(Character.toUpperCase(name.charAt(i)));
/* 183:    */         } else {
/* 184:305 */           buff.append(name.charAt(i));
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:307 */     return buff.toString();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static Method getElementMethod(Class classObj, String name)
/* 192:    */     throws NoSuchMethodException, SecurityException, TransformerException
/* 193:    */   {
/* 194:327 */     Method bestMethod = null;
/* 195:328 */     Method[] methods = classObj.getMethods();
/* 196:329 */     int nMethods = methods.length;
/* 197:330 */     int bestScoreCount = 0;
/* 198:331 */     for (int i = 0; i < nMethods; i++)
/* 199:    */     {
/* 200:333 */       Method method = methods[i];
/* 201:335 */       if (method.getName().equals(name))
/* 202:    */       {
/* 203:337 */         Class[] paramTypes = method.getParameterTypes();
/* 204:338 */         if ((paramTypes.length == 2) && (paramTypes[1].isAssignableFrom(ElemExtensionCall.class)) && (paramTypes[0].isAssignableFrom(XSLProcessorContext.class)))
/* 205:    */         {
/* 206:342 */           bestScoreCount++;
/* 207:342 */           if (bestScoreCount != 1) {
/* 208:    */             break;
/* 209:    */           }
/* 210:343 */           bestMethod = method;
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:350 */     if (null == bestMethod) {
/* 215:352 */       throw new NoSuchMethodException(errString("element", "method", classObj, name, 0, null));
/* 216:    */     }
/* 217:355 */     if (bestScoreCount > 1) {
/* 218:356 */       throw new TransformerException(XSLMessages.createMessage("ER_MORE_MATCH_ELEMENT", new Object[] { name }));
/* 219:    */     }
/* 220:358 */     return bestMethod;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static void convertParams(Object[] argsIn, Object[][] argsOut, Class[] paramTypes, ExpressionContext exprContext)
/* 224:    */     throws TransformerException
/* 225:    */   {
/* 226:378 */     if (paramTypes == null)
/* 227:    */     {
/* 228:379 */       argsOut[0] = null;
/* 229:    */     }
/* 230:    */     else
/* 231:    */     {
/* 232:382 */       int nParams = paramTypes.length;
/* 233:383 */       argsOut[0] = new Object[nParams];
/* 234:384 */       int paramIndex = 0;
/* 235:385 */       if ((nParams > 0) && (ExpressionContext.class.isAssignableFrom(paramTypes[0])))
/* 236:    */       {
/* 237:388 */         argsOut[0][0] = exprContext;
/* 238:    */         
/* 239:390 */         paramIndex++;
/* 240:    */       }
/* 241:393 */       if (argsIn != null) {
/* 242:395 */         for (int i = argsIn.length - nParams + paramIndex; paramIndex < nParams; paramIndex++)
/* 243:    */         {
/* 244:398 */           argsOut[0][paramIndex] = convert(argsIn[i], paramTypes[paramIndex]);i++;
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   static class ConversionInfo
/* 251:    */   {
/* 252:    */     Class m_class;
/* 253:    */     int m_score;
/* 254:    */     
/* 255:    */     ConversionInfo(Class cl, int score)
/* 256:    */     {
/* 257:412 */       this.m_class = cl;
/* 258:413 */       this.m_score = score;
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:426 */   private static final ConversionInfo[] m_javaObjConversions = { new ConversionInfo(Double.TYPE, 11), new ConversionInfo(Float.TYPE, 12), new ConversionInfo(Long.TYPE, 13), new ConversionInfo(Integer.TYPE, 14), new ConversionInfo(Short.TYPE, 15), new ConversionInfo(Character.TYPE, 16), new ConversionInfo(Byte.TYPE, 17), new ConversionInfo(String.class, 18) };
/* 263:441 */   private static final ConversionInfo[] m_booleanConversions = { new ConversionInfo(Boolean.TYPE, 0), new ConversionInfo(Boolean.class, 1), new ConversionInfo(Object.class, 2), new ConversionInfo(String.class, 3) };
/* 264:452 */   private static final ConversionInfo[] m_numberConversions = { new ConversionInfo(Double.TYPE, 0), new ConversionInfo(Double.class, 1), new ConversionInfo(Float.TYPE, 3), new ConversionInfo(Long.TYPE, 4), new ConversionInfo(Integer.TYPE, 5), new ConversionInfo(Short.TYPE, 6), new ConversionInfo(Character.TYPE, 7), new ConversionInfo(Byte.TYPE, 8), new ConversionInfo(Boolean.TYPE, 9), new ConversionInfo(String.class, 10), new ConversionInfo(Object.class, 11) };
/* 265:470 */   private static final ConversionInfo[] m_stringConversions = { new ConversionInfo(String.class, 0), new ConversionInfo(Object.class, 1), new ConversionInfo(Character.TYPE, 2), new ConversionInfo(Double.TYPE, 3), new ConversionInfo(Float.TYPE, 3), new ConversionInfo(Long.TYPE, 3), new ConversionInfo(Integer.TYPE, 3), new ConversionInfo(Short.TYPE, 3), new ConversionInfo(Byte.TYPE, 3), new ConversionInfo(Boolean.TYPE, 4) };
/* 266:487 */   private static final ConversionInfo[] m_rtfConversions = { new ConversionInfo(NodeIterator.class, 0), new ConversionInfo(NodeList.class, 1), new ConversionInfo(Node.class, 2), new ConversionInfo(String.class, 3), new ConversionInfo(Object.class, 5), new ConversionInfo(Character.TYPE, 6), new ConversionInfo(Double.TYPE, 7), new ConversionInfo(Float.TYPE, 7), new ConversionInfo(Long.TYPE, 7), new ConversionInfo(Integer.TYPE, 7), new ConversionInfo(Short.TYPE, 7), new ConversionInfo(Byte.TYPE, 7), new ConversionInfo(Boolean.TYPE, 8) };
/* 267:507 */   private static final ConversionInfo[] m_nodesetConversions = { new ConversionInfo(NodeIterator.class, 0), new ConversionInfo(NodeList.class, 1), new ConversionInfo(Node.class, 2), new ConversionInfo(String.class, 3), new ConversionInfo(Object.class, 5), new ConversionInfo(Character.TYPE, 6), new ConversionInfo(Double.TYPE, 7), new ConversionInfo(Float.TYPE, 7), new ConversionInfo(Long.TYPE, 7), new ConversionInfo(Integer.TYPE, 7), new ConversionInfo(Short.TYPE, 7), new ConversionInfo(Byte.TYPE, 7), new ConversionInfo(Boolean.TYPE, 8) };
/* 268:527 */   private static final ConversionInfo[][] m_conversions = { m_javaObjConversions, m_booleanConversions, m_numberConversions, m_stringConversions, m_nodesetConversions, m_rtfConversions };
/* 269:    */   
/* 270:    */   public static int scoreMatch(Class[] javaParamTypes, int javaParamsStart, Object[] xsltArgs, int score)
/* 271:    */   {
/* 272:554 */     if ((xsltArgs == null) || (javaParamTypes == null)) {
/* 273:555 */       return score;
/* 274:    */     }
/* 275:556 */     int nParams = xsltArgs.length;
/* 276:557 */     int i = nParams - javaParamTypes.length + javaParamsStart;
/* 277:557 */     for (int javaParamTypesIndex = javaParamsStart; i < nParams; javaParamTypesIndex++)
/* 278:    */     {
/* 279:561 */       Object xsltObj = xsltArgs[i];
/* 280:562 */       int xsltClassType = (xsltObj instanceof XObject) ? ((XObject)xsltObj).getType() : 0;
/* 281:    */       
/* 282:    */ 
/* 283:565 */       Class javaClass = javaParamTypes[javaParamTypesIndex];
/* 284:570 */       if (xsltClassType == -1)
/* 285:    */       {
/* 286:574 */         if (!javaClass.isPrimitive()) {
/* 287:577 */           score += 10;
/* 288:    */         } else {
/* 289:581 */           return -1;
/* 290:    */         }
/* 291:    */       }
/* 292:    */       else
/* 293:    */       {
/* 294:584 */         ConversionInfo[] convInfo = m_conversions[xsltClassType];
/* 295:585 */         int nConversions = convInfo.length;
/* 296:587 */         for (int k = 0; k < nConversions; k++)
/* 297:    */         {
/* 298:589 */           ConversionInfo cinfo = convInfo[k];
/* 299:590 */           if (javaClass.isAssignableFrom(cinfo.m_class))
/* 300:    */           {
/* 301:592 */             score += cinfo.m_score;
/* 302:593 */             break;
/* 303:    */           }
/* 304:    */         }
/* 305:597 */         if (k == nConversions) {
/* 306:625 */           if (0 == xsltClassType)
/* 307:    */           {
/* 308:627 */             Class realClass = null;
/* 309:629 */             if ((xsltObj instanceof XObject))
/* 310:    */             {
/* 311:631 */               Object realObj = ((XObject)xsltObj).object();
/* 312:632 */               if (null != realObj)
/* 313:    */               {
/* 314:634 */                 realClass = realObj.getClass();
/* 315:    */               }
/* 316:    */               else
/* 317:    */               {
/* 318:639 */                 score += 10;
/* 319:    */                 break label234;
/* 320:    */               }
/* 321:    */             }
/* 322:    */             else
/* 323:    */             {
/* 324:645 */               realClass = xsltObj.getClass();
/* 325:    */             }
/* 326:648 */             if (javaClass.isAssignableFrom(realClass)) {
/* 327:650 */               score += 0;
/* 328:    */             } else {
/* 329:653 */               return -1;
/* 330:    */             }
/* 331:    */           }
/* 332:    */           else
/* 333:    */           {
/* 334:656 */             return -1;
/* 335:    */           }
/* 336:    */         }
/* 337:    */       }
/* 338:    */       label234:
/* 339:559 */       i++;
/* 340:    */     }
/* 341:659 */     return score;
/* 342:    */   }
/* 343:    */   
/* 344:    */   static Object convert(Object xsltObj, Class javaClass)
/* 345:    */     throws TransformerException
/* 346:    */   {
/* 347:675 */     if ((xsltObj instanceof XObject))
/* 348:    */     {
/* 349:677 */       XObject xobj = (XObject)xsltObj;
/* 350:678 */       int xsltClassType = xobj.getType();
/* 351:680 */       switch (xsltClassType)
/* 352:    */       {
/* 353:    */       case -1: 
/* 354:683 */         return null;
/* 355:    */       case 1: 
/* 356:687 */         if (javaClass == String.class) {
/* 357:688 */           return xobj.str();
/* 358:    */         }
/* 359:690 */         return new Boolean(xobj.bool());
/* 360:    */       case 2: 
/* 361:695 */         if (javaClass == String.class) {
/* 362:696 */           return xobj.str();
/* 363:    */         }
/* 364:697 */         if (javaClass == Boolean.TYPE) {
/* 365:698 */           return new Boolean(xobj.bool());
/* 366:    */         }
/* 367:701 */         return convertDoubleToNumber(xobj.num(), javaClass);
/* 368:    */       case 3: 
/* 369:708 */         if ((javaClass == String.class) || (javaClass == Object.class)) {
/* 370:710 */           return xobj.str();
/* 371:    */         }
/* 372:711 */         if (javaClass == Character.TYPE)
/* 373:    */         {
/* 374:713 */           String str = xobj.str();
/* 375:714 */           if (str.length() > 0) {
/* 376:715 */             return new Character(str.charAt(0));
/* 377:    */           }
/* 378:717 */           return null;
/* 379:    */         }
/* 380:719 */         if (javaClass == Boolean.TYPE) {
/* 381:720 */           return new Boolean(xobj.bool());
/* 382:    */         }
/* 383:723 */         return convertDoubleToNumber(xobj.num(), javaClass);
/* 384:    */       case 5: 
/* 385:736 */         if ((javaClass == NodeIterator.class) || (javaClass == Object.class))
/* 386:    */         {
/* 387:739 */           DTMIterator dtmIter = ((XRTreeFrag)xobj).asNodeIterator();
/* 388:740 */           return new DTMNodeIterator(dtmIter);
/* 389:    */         }
/* 390:742 */         if (javaClass == NodeList.class) {
/* 391:744 */           return ((XRTreeFrag)xobj).convertToNodeset();
/* 392:    */         }
/* 393:748 */         if (javaClass == Node.class)
/* 394:    */         {
/* 395:750 */           DTMIterator iter = ((XRTreeFrag)xobj).asNodeIterator();
/* 396:751 */           int rootHandle = iter.nextNode();
/* 397:752 */           DTM dtm = iter.getDTM(rootHandle);
/* 398:753 */           return dtm.getNode(dtm.getFirstChild(rootHandle));
/* 399:    */         }
/* 400:755 */         if (javaClass == String.class) {
/* 401:757 */           return xobj.str();
/* 402:    */         }
/* 403:759 */         if (javaClass == Boolean.TYPE) {
/* 404:761 */           return new Boolean(xobj.bool());
/* 405:    */         }
/* 406:763 */         if (javaClass.isPrimitive()) {
/* 407:765 */           return convertDoubleToNumber(xobj.num(), javaClass);
/* 408:    */         }
/* 409:769 */         DTMIterator iter = ((XRTreeFrag)xobj).asNodeIterator();
/* 410:770 */         int rootHandle = iter.nextNode();
/* 411:771 */         DTM dtm = iter.getDTM(rootHandle);
/* 412:772 */         Node child = dtm.getNode(dtm.getFirstChild(rootHandle));
/* 413:774 */         if (javaClass.isAssignableFrom(child.getClass())) {
/* 414:775 */           return child;
/* 415:    */         }
/* 416:777 */         return null;
/* 417:    */       case 4: 
/* 418:790 */         if ((javaClass == NodeIterator.class) || (javaClass == Object.class)) {
/* 419:793 */           return xobj.nodeset();
/* 420:    */         }
/* 421:797 */         if (javaClass == NodeList.class) {
/* 422:799 */           return xobj.nodelist();
/* 423:    */         }
/* 424:803 */         if (javaClass == Node.class)
/* 425:    */         {
/* 426:807 */           DTMIterator ni = xobj.iter();
/* 427:808 */           int handle = ni.nextNode();
/* 428:809 */           if (handle != -1) {
/* 429:810 */             return ni.getDTM(handle).getNode(handle);
/* 430:    */           }
/* 431:812 */           return null;
/* 432:    */         }
/* 433:814 */         if (javaClass == String.class) {
/* 434:816 */           return xobj.str();
/* 435:    */         }
/* 436:818 */         if (javaClass == Boolean.TYPE) {
/* 437:820 */           return new Boolean(xobj.bool());
/* 438:    */         }
/* 439:822 */         if (javaClass.isPrimitive()) {
/* 440:824 */           return convertDoubleToNumber(xobj.num(), javaClass);
/* 441:    */         }
/* 442:828 */         DTMIterator iter = xobj.iter();
/* 443:829 */         int childHandle = iter.nextNode();
/* 444:830 */         DTM dtm = iter.getDTM(childHandle);
/* 445:831 */         Node child = dtm.getNode(childHandle);
/* 446:832 */         if (javaClass.isAssignableFrom(child.getClass())) {
/* 447:833 */           return child;
/* 448:    */         }
/* 449:835 */         return null;
/* 450:    */       }
/* 451:842 */       xsltObj = xobj.object();
/* 452:    */     }
/* 453:847 */     if (null != xsltObj)
/* 454:    */     {
/* 455:849 */       if (javaClass == String.class) {
/* 456:851 */         return xsltObj.toString();
/* 457:    */       }
/* 458:853 */       if (javaClass.isPrimitive())
/* 459:    */       {
/* 460:856 */         XString xstr = new XString(xsltObj.toString());
/* 461:857 */         double num = xstr.num();
/* 462:858 */         return convertDoubleToNumber(num, javaClass);
/* 463:    */       }
/* 464:860 */       if (javaClass == Class.class) {
/* 465:862 */         return xsltObj.getClass();
/* 466:    */       }
/* 467:867 */       return xsltObj;
/* 468:    */     }
/* 469:873 */     return xsltObj;
/* 470:    */   }
/* 471:    */   
/* 472:    */   static Object convertDoubleToNumber(double num, Class javaClass)
/* 473:    */   {
/* 474:888 */     if ((javaClass == Double.TYPE) || (javaClass == Double.class)) {
/* 475:890 */       return new Double(num);
/* 476:    */     }
/* 477:891 */     if (javaClass == Float.TYPE) {
/* 478:892 */       return new Float(num);
/* 479:    */     }
/* 480:893 */     if (javaClass == Long.TYPE) {
/* 481:897 */       return new Long(num);
/* 482:    */     }
/* 483:899 */     if (javaClass == Integer.TYPE) {
/* 484:903 */       return new Integer((int)num);
/* 485:    */     }
/* 486:905 */     if (javaClass == Short.TYPE) {
/* 487:909 */       return new Short((short)(int)num);
/* 488:    */     }
/* 489:911 */     if (javaClass == Character.TYPE) {
/* 490:915 */       return new Character((char)(int)num);
/* 491:    */     }
/* 492:917 */     if (javaClass == Byte.TYPE) {
/* 493:921 */       return new Byte((byte)(int)num);
/* 494:    */     }
/* 495:925 */     return new Double(num);
/* 496:    */   }
/* 497:    */   
/* 498:    */   private static String errString(String callType, String searchType, Class classObj, String funcName, int searchMethod, Object[] xsltArgs)
/* 499:    */   {
/* 500:941 */     String resultString = "For extension " + callType + ", could not find " + searchType + " ";
/* 501:943 */     switch (searchMethod)
/* 502:    */     {
/* 503:    */     case 1: 
/* 504:946 */       return resultString + "static " + classObj.getName() + "." + funcName + "([ExpressionContext,] " + errArgs(xsltArgs, 0) + ").";
/* 505:    */     case 2: 
/* 506:950 */       return resultString + classObj.getName() + "." + funcName + "([ExpressionContext,] " + errArgs(xsltArgs, 0) + ").";
/* 507:    */     case 3: 
/* 508:954 */       return resultString + classObj.getName() + "." + funcName + "([ExpressionContext,] " + errArgs(xsltArgs, 0) + ").\n" + "Checked both static and instance methods.";
/* 509:    */     case 4: 
/* 510:958 */       return resultString + "static " + classObj.getName() + "." + funcName + "([ExpressionContext, ]" + errArgs(xsltArgs, 0) + ") nor\n" + classObj + "." + funcName + "([ExpressionContext,] " + errArgs(xsltArgs, 1) + ").";
/* 511:    */     }
/* 512:963 */     if (callType.equals("function")) {
/* 513:965 */       return resultString + classObj.getName() + "([ExpressionContext,] " + errArgs(xsltArgs, 0) + ").";
/* 514:    */     }
/* 515:970 */     return resultString + classObj.getName() + "." + funcName + "(org.apache.xalan.extensions.XSLProcessorContext, " + "org.apache.xalan.templates.ElemExtensionCall).";
/* 516:    */   }
/* 517:    */   
/* 518:    */   private static String errArgs(Object[] xsltArgs, int startingArg)
/* 519:    */   {
/* 520:981 */     StringBuffer returnArgs = new StringBuffer();
/* 521:982 */     for (int i = startingArg; i < xsltArgs.length; i++)
/* 522:    */     {
/* 523:984 */       if (i != startingArg) {
/* 524:985 */         returnArgs.append(", ");
/* 525:    */       }
/* 526:986 */       if ((xsltArgs[i] instanceof XObject)) {
/* 527:987 */         returnArgs.append(((XObject)xsltArgs[i]).getTypeString());
/* 528:    */       } else {
/* 529:989 */         returnArgs.append(xsltArgs[i].getClass().getName());
/* 530:    */       }
/* 531:    */     }
/* 532:991 */     return returnArgs.toString();
/* 533:    */   }
/* 534:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.MethodResolver
 * JD-Core Version:    0.7.0.1
 */