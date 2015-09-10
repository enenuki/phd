/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.regexp;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.regex.Matcher;
/*   6:    */ import java.util.regex.Pattern;
/*   7:    */ import java.util.regex.PatternSyntaxException;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.RegExpProxy;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.regexp.NativeRegExp;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.regexp.RegExpImpl;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.regexp.SubString;
/*  15:    */ import org.apache.commons.lang.StringUtils;
/*  16:    */ import org.apache.commons.logging.Log;
/*  17:    */ import org.apache.commons.logging.LogFactory;
/*  18:    */ 
/*  19:    */ public class HtmlUnitRegExpProxy
/*  20:    */   extends RegExpImpl
/*  21:    */ {
/*  22: 45 */   private static final Log LOG = LogFactory.getLog(HtmlUnitRegExpProxy.class);
/*  23: 47 */   private static final Pattern REPLACE_PATTERN = Pattern.compile("\\$\\$");
/*  24:    */   private final RegExpProxy wrapped_;
/*  25:    */   
/*  26:    */   public HtmlUnitRegExpProxy(RegExpProxy wrapped)
/*  27:    */   {
/*  28: 55 */     this.wrapped_ = wrapped;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object action(Context cx, Scriptable scope, Scriptable thisObj, Object[] args, int actionType)
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 66 */       return doAction(cx, scope, thisObj, args, actionType);
/*  36:    */     }
/*  37:    */     catch (StackOverflowError e)
/*  38:    */     {
/*  39: 71 */       LOG.warn(e.getMessage(), e);
/*  40:    */     }
/*  41: 72 */     return this.wrapped_.action(cx, scope, thisObj, args, actionType);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private Object doAction(Context cx, Scriptable scope, Scriptable thisObj, Object[] args, int actionType)
/*  45:    */   {
/*  46: 79 */     if ((2 == actionType) && (args.length == 2) && ((args[1] instanceof String)))
/*  47:    */     {
/*  48: 80 */       String thisString = Context.toString(thisObj);
/*  49: 81 */       String replacement = (String)args[1];
/*  50: 82 */       Object arg0 = args[0];
/*  51: 83 */       if ((arg0 instanceof String))
/*  52:    */       {
/*  53: 84 */         replacement = REPLACE_PATTERN.matcher(replacement).replaceAll("\\$");
/*  54:    */         
/*  55: 86 */         return StringUtils.replaceOnce(thisString, (String)arg0, replacement);
/*  56:    */       }
/*  57: 88 */       if ((arg0 instanceof NativeRegExp)) {
/*  58:    */         try
/*  59:    */         {
/*  60: 90 */           NativeRegExp regexp = (NativeRegExp)arg0;
/*  61: 91 */           RegExpData reData = new RegExpData(regexp);
/*  62: 92 */           String regex = reData.getJavaPattern();
/*  63: 93 */           int flags = reData.getJavaFlags();
/*  64: 94 */           Pattern pattern = Pattern.compile(regex, flags);
/*  65: 95 */           Matcher matcher = pattern.matcher(thisString);
/*  66: 96 */           return doReplacement(thisString, replacement, matcher, reData.hasFlag('g'));
/*  67:    */         }
/*  68:    */         catch (PatternSyntaxException e)
/*  69:    */         {
/*  70: 99 */           LOG.warn(e.getMessage(), e);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:103 */     else if ((1 == actionType) || (3 == actionType))
/*  75:    */     {
/*  76:104 */       if (args.length == 0) {
/*  77:105 */         return null;
/*  78:    */       }
/*  79:107 */       Object arg0 = args[0];
/*  80:108 */       String thisString = Context.toString(thisObj);
/*  81:    */       RegExpData reData;
/*  82:    */       RegExpData reData;
/*  83:110 */       if ((arg0 instanceof NativeRegExp)) {
/*  84:111 */         reData = new RegExpData((NativeRegExp)arg0);
/*  85:    */       } else {
/*  86:114 */         reData = new RegExpData(Context.toString(arg0));
/*  87:    */       }
/*  88:117 */       Pattern pattern = Pattern.compile(reData.getJavaPattern(), reData.getJavaFlags());
/*  89:118 */       Matcher matcher = pattern.matcher(thisString);
/*  90:    */       
/*  91:120 */       boolean found = matcher.find();
/*  92:121 */       if (3 == actionType)
/*  93:    */       {
/*  94:122 */         if (found)
/*  95:    */         {
/*  96:123 */           setProperties(matcher, thisString, matcher.start(), matcher.end());
/*  97:124 */           return Integer.valueOf(matcher.start());
/*  98:    */         }
/*  99:126 */         return Integer.valueOf(-1);
/* 100:    */       }
/* 101:129 */       if (!found) {
/* 102:130 */         return null;
/* 103:    */       }
/* 104:132 */       int index = matcher.start(0);
/* 105:133 */       List<Object> groups = new ArrayList();
/* 106:134 */       if (reData.hasFlag('g'))
/* 107:    */       {
/* 108:135 */         groups.add(matcher.group(0));
/* 109:136 */         setProperties(matcher, thisString, matcher.start(0), matcher.end(0));
/* 110:138 */         while (matcher.find())
/* 111:    */         {
/* 112:139 */           groups.add(matcher.group(0));
/* 113:140 */           setProperties(matcher, thisString, matcher.start(0), matcher.end(0));
/* 114:    */         }
/* 115:    */       }
/* 116:144 */       for (int i = 0; i <= matcher.groupCount(); i++)
/* 117:    */       {
/* 118:145 */         Object group = matcher.group(i);
/* 119:146 */         if (group == null) {
/* 120:147 */           group = Context.getUndefinedValue();
/* 121:    */         }
/* 122:149 */         groups.add(group);
/* 123:    */       }
/* 124:152 */       setProperties(matcher, thisString, matcher.start(), matcher.end());
/* 125:    */       
/* 126:154 */       Scriptable response = cx.newArray(scope, groups.toArray());
/* 127:    */       
/* 128:156 */       response.put("index", response, Integer.valueOf(index));
/* 129:157 */       response.put("input", response, thisString);
/* 130:158 */       return response;
/* 131:    */     }
/* 132:161 */     return wrappedAction(cx, scope, thisObj, args, actionType);
/* 133:    */   }
/* 134:    */   
/* 135:    */   private String doReplacement(String originalString, String replacement, Matcher matcher, boolean replaceAll)
/* 136:    */   {
/* 137:167 */     StringBuffer sb = new StringBuffer();
/* 138:168 */     int previousIndex = 0;
/* 139:169 */     while (matcher.find())
/* 140:    */     {
/* 141:170 */       sb.append(originalString.substring(previousIndex, matcher.start()));
/* 142:171 */       String localReplacement = replacement;
/* 143:172 */       if (replacement.contains("$")) {
/* 144:173 */         localReplacement = computeReplacementValue(replacement, originalString, matcher);
/* 145:    */       }
/* 146:175 */       sb.append(localReplacement);
/* 147:176 */       previousIndex = matcher.end();
/* 148:    */       
/* 149:178 */       setProperties(matcher, originalString, matcher.start(), previousIndex);
/* 150:179 */       if (!replaceAll) {
/* 151:    */         break;
/* 152:    */       }
/* 153:    */     }
/* 154:183 */     sb.append(originalString.substring(previousIndex));
/* 155:184 */     return sb.toString();
/* 156:    */   }
/* 157:    */   
/* 158:    */   static String computeReplacementValue(String replacement, String originalString, Matcher matcher)
/* 159:    */   {
/* 160:190 */     int lastIndex = 0;
/* 161:191 */     StringBuilder result = new StringBuilder();
/* 162:    */     int i;
/* 163:193 */     while ((i = replacement.indexOf('$', lastIndex)) > -1)
/* 164:    */     {
/* 165:194 */       if (i > 0) {
/* 166:195 */         result.append(replacement.substring(lastIndex, i));
/* 167:    */       }
/* 168:197 */       String ss = null;
/* 169:198 */       if ((i < replacement.length() - 1) && ((i == lastIndex) || (replacement.charAt(i - 1) != '$')))
/* 170:    */       {
/* 171:199 */         char next = replacement.charAt(i + 1);
/* 172:201 */         if ((next >= '1') && (next <= '9'))
/* 173:    */         {
/* 174:202 */           int num1digit = next - '0';
/* 175:203 */           char next2 = i + 2 < replacement.length() ? replacement.charAt(i + 2) : 'x';
/* 176:    */           int num2digits;
/* 177:    */           int num2digits;
/* 178:207 */           if ((next2 >= '1') && (next2 <= '9')) {
/* 179:208 */             num2digits = num1digit * 10 + (next2 - '0');
/* 180:    */           } else {
/* 181:211 */             num2digits = 2147483647;
/* 182:    */           }
/* 183:213 */           if (num2digits <= matcher.groupCount())
/* 184:    */           {
/* 185:214 */             ss = matcher.group(num2digits);
/* 186:215 */             i++;
/* 187:    */           }
/* 188:217 */           else if (num1digit <= matcher.groupCount())
/* 189:    */           {
/* 190:218 */             ss = StringUtils.defaultString(matcher.group(num1digit));
/* 191:    */           }
/* 192:    */         }
/* 193:    */         else
/* 194:    */         {
/* 195:222 */           switch (next)
/* 196:    */           {
/* 197:    */           case '&': 
/* 198:224 */             ss = matcher.group();
/* 199:225 */             break;
/* 200:    */           case '`': 
/* 201:227 */             ss = originalString.substring(0, matcher.start());
/* 202:228 */             break;
/* 203:    */           case '\'': 
/* 204:230 */             ss = originalString.substring(matcher.end());
/* 205:231 */             break;
/* 206:    */           case '$': 
/* 207:233 */             ss = "$";
/* 208:234 */             break;
/* 209:    */           }
/* 210:    */         }
/* 211:    */       }
/* 212:239 */       if (ss != null)
/* 213:    */       {
/* 214:240 */         result.append(ss);
/* 215:241 */         lastIndex = i + 2;
/* 216:    */       }
/* 217:    */       else
/* 218:    */       {
/* 219:244 */         result.append('$');
/* 220:245 */         lastIndex = i + 1;
/* 221:    */       }
/* 222:    */     }
/* 223:249 */     result.append(replacement.substring(lastIndex));
/* 224:    */     
/* 225:251 */     return result.toString();
/* 226:    */   }
/* 227:    */   
/* 228:    */   private Object wrappedAction(Context cx, Scriptable scope, Scriptable thisObj, Object[] args, int actionType)
/* 229:    */   {
/* 230:    */     try
/* 231:    */     {
/* 232:263 */       ScriptRuntime.setRegExpProxy(cx, this.wrapped_);
/* 233:264 */       return this.wrapped_.action(cx, scope, thisObj, args, actionType);
/* 234:    */     }
/* 235:    */     finally
/* 236:    */     {
/* 237:267 */       ScriptRuntime.setRegExpProxy(cx, this);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void setProperties(Matcher matcher, String thisString, int startPos, int endPos)
/* 242:    */   {
/* 243:273 */     String match = matcher.group();
/* 244:274 */     if (match == null) {
/* 245:275 */       this.lastMatch = new SubString();
/* 246:    */     } else {
/* 247:278 */       this.lastMatch = new FixedSubString(match);
/* 248:    */     }
/* 249:282 */     int count = Math.min(9, matcher.groupCount());
/* 250:283 */     if (count == 0)
/* 251:    */     {
/* 252:284 */       this.parens = null;
/* 253:    */     }
/* 254:    */     else
/* 255:    */     {
/* 256:287 */       this.parens = new SubString[count];
/* 257:288 */       for (int i = 0; i < count; i++)
/* 258:    */       {
/* 259:289 */         String group = matcher.group(i + 1);
/* 260:290 */         if (group == null) {
/* 261:291 */           this.parens[i] = SubString.emptySubString;
/* 262:    */         } else {
/* 263:294 */           this.parens[i] = new FixedSubString(group);
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:300 */     if (matcher.groupCount() > 0)
/* 268:    */     {
/* 269:301 */       String last = matcher.group(matcher.groupCount());
/* 270:302 */       if (last == null) {
/* 271:303 */         this.lastParen = new SubString();
/* 272:    */       } else {
/* 273:306 */         this.lastParen = new FixedSubString(last);
/* 274:    */       }
/* 275:    */     }
/* 276:311 */     if (startPos > 0) {
/* 277:312 */       this.leftContext = new FixedSubString(thisString.substring(0, startPos));
/* 278:    */     } else {
/* 279:315 */       this.leftContext = new SubString();
/* 280:    */     }
/* 281:319 */     if (endPos < thisString.length()) {
/* 282:320 */       this.rightContext = new FixedSubString(thisString.substring(endPos));
/* 283:    */     } else {
/* 284:323 */       this.rightContext = new SubString();
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Object compileRegExp(Context cx, String source, String flags)
/* 289:    */   {
/* 290:    */     try
/* 291:    */     {
/* 292:333 */       return this.wrapped_.compileRegExp(cx, source, flags);
/* 293:    */     }
/* 294:    */     catch (Exception e)
/* 295:    */     {
/* 296:336 */       LOG.warn("compileRegExp() threw for >" + source + "<, flags: >" + flags + "<. " + "Replacing with a '####shouldNotFindAnything###'");
/* 297:    */     }
/* 298:338 */     return this.wrapped_.compileRegExp(cx, "####shouldNotFindAnything###", "");
/* 299:    */   }
/* 300:    */   
/* 301:    */   public int find_split(Context cx, Scriptable scope, String target, String separator, Scriptable re, int[] ip, int[] matchlen, boolean[] matched, String[][] parensp)
/* 302:    */   {
/* 303:349 */     return this.wrapped_.find_split(cx, scope, target, separator, re, ip, matchlen, matched, parensp);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean isRegExp(Scriptable obj)
/* 307:    */   {
/* 308:357 */     return this.wrapped_.isRegExp(obj);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public Scriptable wrapRegExp(Context cx, Scriptable scope, Object compiled)
/* 312:    */   {
/* 313:365 */     return this.wrapped_.wrapRegExp(cx, scope, compiled);
/* 314:    */   }
/* 315:    */   
/* 316:    */   private static class RegExpData
/* 317:    */   {
/* 318:    */     private final String jsSource_;
/* 319:    */     private final String jsFlags_;
/* 320:    */     
/* 321:    */     RegExpData(NativeRegExp re)
/* 322:    */     {
/* 323:373 */       String str = re.toString();
/* 324:374 */       this.jsSource_ = StringUtils.substringBeforeLast(str.substring(1), "/");
/* 325:375 */       this.jsFlags_ = StringUtils.substringAfterLast(str, "/");
/* 326:    */     }
/* 327:    */     
/* 328:    */     public RegExpData(String string)
/* 329:    */     {
/* 330:378 */       this.jsSource_ = string;
/* 331:379 */       this.jsFlags_ = "";
/* 332:    */     }
/* 333:    */     
/* 334:    */     public int getJavaFlags()
/* 335:    */     {
/* 336:386 */       int flags = 0;
/* 337:387 */       if (this.jsFlags_.contains("i")) {
/* 338:388 */         flags |= 0x2;
/* 339:    */       }
/* 340:390 */       if (this.jsFlags_.contains("m")) {
/* 341:391 */         flags |= 0x8;
/* 342:    */       }
/* 343:393 */       return flags;
/* 344:    */     }
/* 345:    */     
/* 346:    */     public String getJavaPattern()
/* 347:    */     {
/* 348:396 */       return HtmlUnitRegExpProxy.jsRegExpToJavaRegExp(this.jsSource_);
/* 349:    */     }
/* 350:    */     
/* 351:    */     boolean hasFlag(char c)
/* 352:    */     {
/* 353:400 */       return this.jsFlags_.indexOf(c) != -1;
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   static String jsRegExpToJavaRegExp(String re)
/* 358:    */   {
/* 359:410 */     RegExpJsToJavaConverter regExpJsToJavaFSM = new RegExpJsToJavaConverter();
/* 360:411 */     String tmpNew = regExpJsToJavaFSM.convert(re);
/* 361:412 */     return tmpNew;
/* 362:    */   }
/* 363:    */   
/* 364:    */   private static class FixedSubString
/* 365:    */     extends SubString
/* 366:    */   {
/* 367:    */     private String value_;
/* 368:    */     
/* 369:    */     public FixedSubString(String str)
/* 370:    */     {
/* 371:432 */       this.value_ = str;
/* 372:    */     }
/* 373:    */     
/* 374:    */     public String toString()
/* 375:    */     {
/* 376:437 */       return this.value_;
/* 377:    */     }
/* 378:    */   }
/* 379:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.regexp.HtmlUnitRegExpProxy
 * JD-Core Version:    0.7.0.1
 */