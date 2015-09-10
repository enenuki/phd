/*   1:    */ package org.apache.commons.lang.text;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ 
/*  10:    */ public class StrSubstitutor
/*  11:    */ {
/*  12:    */   public static final char DEFAULT_ESCAPE = '$';
/*  13:114 */   public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
/*  14:118 */   public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
/*  15:    */   private char escapeChar;
/*  16:    */   private StrMatcher prefixMatcher;
/*  17:    */   private StrMatcher suffixMatcher;
/*  18:    */   private StrLookup variableResolver;
/*  19:    */   private boolean enableSubstitutionInVariables;
/*  20:    */   
/*  21:    */   public static String replace(Object source, Map valueMap)
/*  22:    */   {
/*  23:151 */     return new StrSubstitutor(valueMap).replace(source);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static String replace(Object source, Map valueMap, String prefix, String suffix)
/*  27:    */   {
/*  28:167 */     return new StrSubstitutor(valueMap, prefix, suffix).replace(source);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String replace(Object source, Properties valueProperties)
/*  32:    */   {
/*  33:181 */     if (valueProperties == null) {
/*  34:182 */       return source.toString();
/*  35:    */     }
/*  36:184 */     Map valueMap = new HashMap();
/*  37:185 */     Enumeration propNames = valueProperties.propertyNames();
/*  38:186 */     while (propNames.hasMoreElements())
/*  39:    */     {
/*  40:188 */       String propName = (String)propNames.nextElement();
/*  41:189 */       String propValue = valueProperties.getProperty(propName);
/*  42:190 */       valueMap.put(propName, propValue);
/*  43:    */     }
/*  44:192 */     return replace(source, valueMap);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String replaceSystemProperties(Object source)
/*  48:    */   {
/*  49:203 */     return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(source);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public StrSubstitutor()
/*  53:    */   {
/*  54:212 */     this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*  55:    */   }
/*  56:    */   
/*  57:    */   public StrSubstitutor(Map valueMap)
/*  58:    */   {
/*  59:222 */     this(StrLookup.mapLookup(valueMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*  60:    */   }
/*  61:    */   
/*  62:    */   public StrSubstitutor(Map valueMap, String prefix, String suffix)
/*  63:    */   {
/*  64:234 */     this(StrLookup.mapLookup(valueMap), prefix, suffix, '$');
/*  65:    */   }
/*  66:    */   
/*  67:    */   public StrSubstitutor(Map valueMap, String prefix, String suffix, char escape)
/*  68:    */   {
/*  69:247 */     this(StrLookup.mapLookup(valueMap), prefix, suffix, escape);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public StrSubstitutor(StrLookup variableResolver)
/*  73:    */   {
/*  74:256 */     this(variableResolver, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
/*  75:    */   }
/*  76:    */   
/*  77:    */   public StrSubstitutor(StrLookup variableResolver, String prefix, String suffix, char escape)
/*  78:    */   {
/*  79:269 */     setVariableResolver(variableResolver);
/*  80:270 */     setVariablePrefix(prefix);
/*  81:271 */     setVariableSuffix(suffix);
/*  82:272 */     setEscapeChar(escape);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public StrSubstitutor(StrLookup variableResolver, StrMatcher prefixMatcher, StrMatcher suffixMatcher, char escape)
/*  86:    */   {
/*  87:286 */     setVariableResolver(variableResolver);
/*  88:287 */     setVariablePrefixMatcher(prefixMatcher);
/*  89:288 */     setVariableSuffixMatcher(suffixMatcher);
/*  90:289 */     setEscapeChar(escape);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String replace(String source)
/*  94:    */   {
/*  95:301 */     if (source == null) {
/*  96:302 */       return null;
/*  97:    */     }
/*  98:304 */     StrBuilder buf = new StrBuilder(source);
/*  99:305 */     if (!substitute(buf, 0, source.length())) {
/* 100:306 */       return source;
/* 101:    */     }
/* 102:308 */     return buf.toString();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String replace(String source, int offset, int length)
/* 106:    */   {
/* 107:324 */     if (source == null) {
/* 108:325 */       return null;
/* 109:    */     }
/* 110:327 */     StrBuilder buf = new StrBuilder(length).append(source, offset, length);
/* 111:328 */     if (!substitute(buf, 0, length)) {
/* 112:329 */       return source.substring(offset, offset + length);
/* 113:    */     }
/* 114:331 */     return buf.toString();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String replace(char[] source)
/* 118:    */   {
/* 119:344 */     if (source == null) {
/* 120:345 */       return null;
/* 121:    */     }
/* 122:347 */     StrBuilder buf = new StrBuilder(source.length).append(source);
/* 123:348 */     substitute(buf, 0, source.length);
/* 124:349 */     return buf.toString();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String replace(char[] source, int offset, int length)
/* 128:    */   {
/* 129:366 */     if (source == null) {
/* 130:367 */       return null;
/* 131:    */     }
/* 132:369 */     StrBuilder buf = new StrBuilder(length).append(source, offset, length);
/* 133:370 */     substitute(buf, 0, length);
/* 134:371 */     return buf.toString();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String replace(StringBuffer source)
/* 138:    */   {
/* 139:384 */     if (source == null) {
/* 140:385 */       return null;
/* 141:    */     }
/* 142:387 */     StrBuilder buf = new StrBuilder(source.length()).append(source);
/* 143:388 */     substitute(buf, 0, buf.length());
/* 144:389 */     return buf.toString();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String replace(StringBuffer source, int offset, int length)
/* 148:    */   {
/* 149:406 */     if (source == null) {
/* 150:407 */       return null;
/* 151:    */     }
/* 152:409 */     StrBuilder buf = new StrBuilder(length).append(source, offset, length);
/* 153:410 */     substitute(buf, 0, length);
/* 154:411 */     return buf.toString();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String replace(StrBuilder source)
/* 158:    */   {
/* 159:424 */     if (source == null) {
/* 160:425 */       return null;
/* 161:    */     }
/* 162:427 */     StrBuilder buf = new StrBuilder(source.length()).append(source);
/* 163:428 */     substitute(buf, 0, buf.length());
/* 164:429 */     return buf.toString();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String replace(StrBuilder source, int offset, int length)
/* 168:    */   {
/* 169:446 */     if (source == null) {
/* 170:447 */       return null;
/* 171:    */     }
/* 172:449 */     StrBuilder buf = new StrBuilder(length).append(source, offset, length);
/* 173:450 */     substitute(buf, 0, length);
/* 174:451 */     return buf.toString();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String replace(Object source)
/* 178:    */   {
/* 179:464 */     if (source == null) {
/* 180:465 */       return null;
/* 181:    */     }
/* 182:467 */     StrBuilder buf = new StrBuilder().append(source);
/* 183:468 */     substitute(buf, 0, buf.length());
/* 184:469 */     return buf.toString();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean replaceIn(StringBuffer source)
/* 188:    */   {
/* 189:482 */     if (source == null) {
/* 190:483 */       return false;
/* 191:    */     }
/* 192:485 */     return replaceIn(source, 0, source.length());
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean replaceIn(StringBuffer source, int offset, int length)
/* 196:    */   {
/* 197:502 */     if (source == null) {
/* 198:503 */       return false;
/* 199:    */     }
/* 200:505 */     StrBuilder buf = new StrBuilder(length).append(source, offset, length);
/* 201:506 */     if (!substitute(buf, 0, length)) {
/* 202:507 */       return false;
/* 203:    */     }
/* 204:509 */     source.replace(offset, offset + length, buf.toString());
/* 205:510 */     return true;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean replaceIn(StrBuilder source)
/* 209:    */   {
/* 210:522 */     if (source == null) {
/* 211:523 */       return false;
/* 212:    */     }
/* 213:525 */     return substitute(source, 0, source.length());
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean replaceIn(StrBuilder source, int offset, int length)
/* 217:    */   {
/* 218:541 */     if (source == null) {
/* 219:542 */       return false;
/* 220:    */     }
/* 221:544 */     return substitute(source, offset, length);
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected boolean substitute(StrBuilder buf, int offset, int length)
/* 225:    */   {
/* 226:563 */     return substitute(buf, offset, length, null) > 0;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private int substitute(StrBuilder buf, int offset, int length, List priorVariables)
/* 230:    */   {
/* 231:579 */     StrMatcher prefixMatcher = getVariablePrefixMatcher();
/* 232:580 */     StrMatcher suffixMatcher = getVariableSuffixMatcher();
/* 233:581 */     char escape = getEscapeChar();
/* 234:    */     
/* 235:583 */     boolean top = priorVariables == null;
/* 236:584 */     boolean altered = false;
/* 237:585 */     int lengthChange = 0;
/* 238:586 */     char[] chars = buf.buffer;
/* 239:587 */     int bufEnd = offset + length;
/* 240:588 */     int pos = offset;
/* 241:589 */     while (pos < bufEnd)
/* 242:    */     {
/* 243:590 */       int startMatchLen = prefixMatcher.isMatch(chars, pos, offset, bufEnd);
/* 244:592 */       if (startMatchLen == 0)
/* 245:    */       {
/* 246:593 */         pos++;
/* 247:    */       }
/* 248:596 */       else if ((pos > offset) && (chars[(pos - 1)] == escape))
/* 249:    */       {
/* 250:598 */         buf.deleteCharAt(pos - 1);
/* 251:599 */         chars = buf.buffer;
/* 252:600 */         lengthChange--;
/* 253:601 */         altered = true;
/* 254:602 */         bufEnd--;
/* 255:    */       }
/* 256:    */       else
/* 257:    */       {
/* 258:605 */         int startPos = pos;
/* 259:606 */         pos += startMatchLen;
/* 260:607 */         int endMatchLen = 0;
/* 261:608 */         int nestedVarCount = 0;
/* 262:609 */         while (pos < bufEnd) {
/* 263:610 */           if ((isEnableSubstitutionInVariables()) && ((endMatchLen = prefixMatcher.isMatch(chars, pos, offset, bufEnd)) != 0))
/* 264:    */           {
/* 265:614 */             nestedVarCount++;
/* 266:615 */             pos += endMatchLen;
/* 267:    */           }
/* 268:    */           else
/* 269:    */           {
/* 270:619 */             endMatchLen = suffixMatcher.isMatch(chars, pos, offset, bufEnd);
/* 271:621 */             if (endMatchLen == 0)
/* 272:    */             {
/* 273:622 */               pos++;
/* 274:    */             }
/* 275:    */             else
/* 276:    */             {
/* 277:625 */               if (nestedVarCount == 0)
/* 278:    */               {
/* 279:626 */                 String varName = new String(chars, startPos + startMatchLen, pos - startPos - startMatchLen);
/* 280:629 */                 if (isEnableSubstitutionInVariables())
/* 281:    */                 {
/* 282:630 */                   StrBuilder bufName = new StrBuilder(varName);
/* 283:631 */                   substitute(bufName, 0, bufName.length());
/* 284:632 */                   varName = bufName.toString();
/* 285:    */                 }
/* 286:634 */                 pos += endMatchLen;
/* 287:635 */                 int endPos = pos;
/* 288:638 */                 if (priorVariables == null)
/* 289:    */                 {
/* 290:639 */                   priorVariables = new ArrayList();
/* 291:640 */                   priorVariables.add(new String(chars, offset, length));
/* 292:    */                 }
/* 293:645 */                 checkCyclicSubstitution(varName, priorVariables);
/* 294:646 */                 priorVariables.add(varName);
/* 295:    */                 
/* 296:    */ 
/* 297:649 */                 String varValue = resolveVariable(varName, buf, startPos, endPos);
/* 298:651 */                 if (varValue != null)
/* 299:    */                 {
/* 300:653 */                   int varLen = varValue.length();
/* 301:654 */                   buf.replace(startPos, endPos, varValue);
/* 302:655 */                   altered = true;
/* 303:656 */                   int change = substitute(buf, startPos, varLen, priorVariables);
/* 304:    */                   
/* 305:658 */                   change += varLen - (endPos - startPos);
/* 306:    */                   
/* 307:660 */                   pos += change;
/* 308:661 */                   bufEnd += change;
/* 309:662 */                   lengthChange += change;
/* 310:663 */                   chars = buf.buffer;
/* 311:    */                 }
/* 312:668 */                 priorVariables.remove(priorVariables.size() - 1);
/* 313:    */                 
/* 314:670 */                 break;
/* 315:    */               }
/* 316:672 */               nestedVarCount--;
/* 317:673 */               pos += endMatchLen;
/* 318:    */             }
/* 319:    */           }
/* 320:    */         }
/* 321:    */       }
/* 322:    */     }
/* 323:680 */     if (top) {
/* 324:681 */       return altered ? 1 : 0;
/* 325:    */     }
/* 326:683 */     return lengthChange;
/* 327:    */   }
/* 328:    */   
/* 329:    */   private void checkCyclicSubstitution(String varName, List priorVariables)
/* 330:    */   {
/* 331:693 */     if (!priorVariables.contains(varName)) {
/* 332:694 */       return;
/* 333:    */     }
/* 334:696 */     StrBuilder buf = new StrBuilder(256);
/* 335:697 */     buf.append("Infinite loop in property interpolation of ");
/* 336:698 */     buf.append(priorVariables.remove(0));
/* 337:699 */     buf.append(": ");
/* 338:700 */     buf.appendWithSeparators(priorVariables, "->");
/* 339:701 */     throw new IllegalStateException(buf.toString());
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected String resolveVariable(String variableName, StrBuilder buf, int startPos, int endPos)
/* 343:    */   {
/* 344:722 */     StrLookup resolver = getVariableResolver();
/* 345:723 */     if (resolver == null) {
/* 346:724 */       return null;
/* 347:    */     }
/* 348:726 */     return resolver.lookup(variableName);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public char getEscapeChar()
/* 352:    */   {
/* 353:737 */     return this.escapeChar;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setEscapeChar(char escapeCharacter)
/* 357:    */   {
/* 358:748 */     this.escapeChar = escapeCharacter;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public StrMatcher getVariablePrefixMatcher()
/* 362:    */   {
/* 363:763 */     return this.prefixMatcher;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public StrSubstitutor setVariablePrefixMatcher(StrMatcher prefixMatcher)
/* 367:    */   {
/* 368:778 */     if (prefixMatcher == null) {
/* 369:779 */       throw new IllegalArgumentException("Variable prefix matcher must not be null!");
/* 370:    */     }
/* 371:781 */     this.prefixMatcher = prefixMatcher;
/* 372:782 */     return this;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public StrSubstitutor setVariablePrefix(char prefix)
/* 376:    */   {
/* 377:796 */     return setVariablePrefixMatcher(StrMatcher.charMatcher(prefix));
/* 378:    */   }
/* 379:    */   
/* 380:    */   public StrSubstitutor setVariablePrefix(String prefix)
/* 381:    */   {
/* 382:810 */     if (prefix == null) {
/* 383:811 */       throw new IllegalArgumentException("Variable prefix must not be null!");
/* 384:    */     }
/* 385:813 */     return setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix));
/* 386:    */   }
/* 387:    */   
/* 388:    */   public StrMatcher getVariableSuffixMatcher()
/* 389:    */   {
/* 390:828 */     return this.suffixMatcher;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public StrSubstitutor setVariableSuffixMatcher(StrMatcher suffixMatcher)
/* 394:    */   {
/* 395:843 */     if (suffixMatcher == null) {
/* 396:844 */       throw new IllegalArgumentException("Variable suffix matcher must not be null!");
/* 397:    */     }
/* 398:846 */     this.suffixMatcher = suffixMatcher;
/* 399:847 */     return this;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public StrSubstitutor setVariableSuffix(char suffix)
/* 403:    */   {
/* 404:861 */     return setVariableSuffixMatcher(StrMatcher.charMatcher(suffix));
/* 405:    */   }
/* 406:    */   
/* 407:    */   public StrSubstitutor setVariableSuffix(String suffix)
/* 408:    */   {
/* 409:875 */     if (suffix == null) {
/* 410:876 */       throw new IllegalArgumentException("Variable suffix must not be null!");
/* 411:    */     }
/* 412:878 */     return setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix));
/* 413:    */   }
/* 414:    */   
/* 415:    */   public StrLookup getVariableResolver()
/* 416:    */   {
/* 417:889 */     return this.variableResolver;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setVariableResolver(StrLookup variableResolver)
/* 421:    */   {
/* 422:898 */     this.variableResolver = variableResolver;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public boolean isEnableSubstitutionInVariables()
/* 426:    */   {
/* 427:910 */     return this.enableSubstitutionInVariables;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setEnableSubstitutionInVariables(boolean enableSubstitutionInVariables)
/* 431:    */   {
/* 432:924 */     this.enableSubstitutionInVariables = enableSubstitutionInVariables;
/* 433:    */   }
/* 434:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.StrSubstitutor
 * JD-Core Version:    0.7.0.1
 */