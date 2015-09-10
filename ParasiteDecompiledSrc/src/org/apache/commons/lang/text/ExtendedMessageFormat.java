/*   1:    */ package org.apache.commons.lang.text;
/*   2:    */ 
/*   3:    */ import java.text.Format;
/*   4:    */ import java.text.MessageFormat;
/*   5:    */ import java.text.ParsePosition;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.apache.commons.lang.ObjectUtils;
/*  12:    */ import org.apache.commons.lang.Validate;
/*  13:    */ 
/*  14:    */ public class ExtendedMessageFormat
/*  15:    */   extends MessageFormat
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -2362048321261811743L;
/*  18:    */   private static final int HASH_SEED = 31;
/*  19:    */   private static final String DUMMY_PATTERN = "";
/*  20:    */   private static final String ESCAPED_QUOTE = "''";
/*  21:    */   private static final char START_FMT = ',';
/*  22:    */   private static final char END_FE = '}';
/*  23:    */   private static final char START_FE = '{';
/*  24:    */   private static final char QUOTE = '\'';
/*  25:    */   private String toPattern;
/*  26:    */   private final Map registry;
/*  27:    */   
/*  28:    */   public ExtendedMessageFormat(String pattern)
/*  29:    */   {
/*  30: 92 */     this(pattern, Locale.getDefault());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ExtendedMessageFormat(String pattern, Locale locale)
/*  34:    */   {
/*  35:103 */     this(pattern, locale, null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ExtendedMessageFormat(String pattern, Map registry)
/*  39:    */   {
/*  40:114 */     this(pattern, Locale.getDefault(), registry);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ExtendedMessageFormat(String pattern, Locale locale, Map registry)
/*  44:    */   {
/*  45:126 */     super("");
/*  46:127 */     setLocale(locale);
/*  47:128 */     this.registry = registry;
/*  48:129 */     applyPattern(pattern);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toPattern()
/*  52:    */   {
/*  53:136 */     return this.toPattern;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final void applyPattern(String pattern)
/*  57:    */   {
/*  58:145 */     if (this.registry == null)
/*  59:    */     {
/*  60:146 */       super.applyPattern(pattern);
/*  61:147 */       this.toPattern = super.toPattern();
/*  62:148 */       return;
/*  63:    */     }
/*  64:150 */     ArrayList foundFormats = new ArrayList();
/*  65:151 */     ArrayList foundDescriptions = new ArrayList();
/*  66:152 */     StrBuilder stripCustom = new StrBuilder(pattern.length());
/*  67:    */     
/*  68:154 */     ParsePosition pos = new ParsePosition(0);
/*  69:155 */     char[] c = pattern.toCharArray();
/*  70:156 */     int fmtCount = 0;
/*  71:157 */     while (pos.getIndex() < pattern.length())
/*  72:    */     {
/*  73:158 */       switch (c[pos.getIndex()])
/*  74:    */       {
/*  75:    */       case '\'': 
/*  76:160 */         appendQuotedString(pattern, pos, stripCustom, true);
/*  77:161 */         break;
/*  78:    */       case '{': 
/*  79:163 */         fmtCount++;
/*  80:164 */         seekNonWs(pattern, pos);
/*  81:165 */         int start = pos.getIndex();
/*  82:166 */         int index = readArgumentIndex(pattern, next(pos));
/*  83:167 */         stripCustom.append('{').append(index);
/*  84:168 */         seekNonWs(pattern, pos);
/*  85:169 */         Format format = null;
/*  86:170 */         String formatDescription = null;
/*  87:171 */         if (c[pos.getIndex()] == ',')
/*  88:    */         {
/*  89:172 */           formatDescription = parseFormatDescription(pattern, next(pos));
/*  90:    */           
/*  91:174 */           format = getFormat(formatDescription);
/*  92:175 */           if (format == null) {
/*  93:176 */             stripCustom.append(',').append(formatDescription);
/*  94:    */           }
/*  95:    */         }
/*  96:179 */         foundFormats.add(format);
/*  97:180 */         foundDescriptions.add(format == null ? null : formatDescription);
/*  98:181 */         Validate.isTrue(foundFormats.size() == fmtCount);
/*  99:182 */         Validate.isTrue(foundDescriptions.size() == fmtCount);
/* 100:183 */         if (c[pos.getIndex()] != '}') {
/* 101:184 */           throw new IllegalArgumentException("Unreadable format element at position " + start);
/* 102:    */         }
/* 103:    */         break;
/* 104:    */       }
/* 105:189 */       stripCustom.append(c[pos.getIndex()]);
/* 106:190 */       next(pos);
/* 107:    */     }
/* 108:193 */     super.applyPattern(stripCustom.toString());
/* 109:194 */     this.toPattern = insertFormats(super.toPattern(), foundDescriptions);
/* 110:195 */     if (containsElements(foundFormats))
/* 111:    */     {
/* 112:196 */       Format[] origFormats = getFormats();
/* 113:    */       
/* 114:    */ 
/* 115:199 */       int i = 0;
/* 116:200 */       for (Iterator it = foundFormats.iterator(); it.hasNext(); i++)
/* 117:    */       {
/* 118:201 */         Format f = (Format)it.next();
/* 119:202 */         if (f != null) {
/* 120:203 */           origFormats[i] = f;
/* 121:    */         }
/* 122:    */       }
/* 123:206 */       super.setFormats(origFormats);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setFormat(int formatElementIndex, Format newFormat)
/* 128:    */   {
/* 129:218 */     throw new UnsupportedOperationException();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFormatByArgumentIndex(int argumentIndex, Format newFormat)
/* 133:    */   {
/* 134:229 */     throw new UnsupportedOperationException();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFormats(Format[] newFormats)
/* 138:    */   {
/* 139:239 */     throw new UnsupportedOperationException();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFormatsByArgumentIndex(Format[] newFormats)
/* 143:    */   {
/* 144:249 */     throw new UnsupportedOperationException();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean equals(Object obj)
/* 148:    */   {
/* 149:260 */     if (obj == this) {
/* 150:261 */       return true;
/* 151:    */     }
/* 152:263 */     if (obj == null) {
/* 153:264 */       return false;
/* 154:    */     }
/* 155:266 */     if (!super.equals(obj)) {
/* 156:267 */       return false;
/* 157:    */     }
/* 158:269 */     if (ObjectUtils.notEqual(getClass(), obj.getClass())) {
/* 159:270 */       return false;
/* 160:    */     }
/* 161:272 */     ExtendedMessageFormat rhs = (ExtendedMessageFormat)obj;
/* 162:273 */     if (ObjectUtils.notEqual(this.toPattern, rhs.toPattern)) {
/* 163:274 */       return false;
/* 164:    */     }
/* 165:276 */     if (ObjectUtils.notEqual(this.registry, rhs.registry)) {
/* 166:277 */       return false;
/* 167:    */     }
/* 168:279 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int hashCode()
/* 172:    */   {
/* 173:289 */     int result = super.hashCode();
/* 174:290 */     result = 31 * result + ObjectUtils.hashCode(this.registry);
/* 175:291 */     result = 31 * result + ObjectUtils.hashCode(this.toPattern);
/* 176:292 */     return result;
/* 177:    */   }
/* 178:    */   
/* 179:    */   private Format getFormat(String desc)
/* 180:    */   {
/* 181:302 */     if (this.registry != null)
/* 182:    */     {
/* 183:303 */       String name = desc;
/* 184:304 */       String args = null;
/* 185:305 */       int i = desc.indexOf(',');
/* 186:306 */       if (i > 0)
/* 187:    */       {
/* 188:307 */         name = desc.substring(0, i).trim();
/* 189:308 */         args = desc.substring(i + 1).trim();
/* 190:    */       }
/* 191:310 */       FormatFactory factory = (FormatFactory)this.registry.get(name);
/* 192:311 */       if (factory != null) {
/* 193:312 */         return factory.getFormat(name, args, getLocale());
/* 194:    */       }
/* 195:    */     }
/* 196:315 */     return null;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private int readArgumentIndex(String pattern, ParsePosition pos)
/* 200:    */   {
/* 201:326 */     int start = pos.getIndex();
/* 202:327 */     seekNonWs(pattern, pos);
/* 203:328 */     StrBuilder result = new StrBuilder();
/* 204:329 */     boolean error = false;
/* 205:330 */     for (; (!error) && (pos.getIndex() < pattern.length()); next(pos))
/* 206:    */     {
/* 207:331 */       char c = pattern.charAt(pos.getIndex());
/* 208:332 */       if (Character.isWhitespace(c))
/* 209:    */       {
/* 210:333 */         seekNonWs(pattern, pos);
/* 211:334 */         c = pattern.charAt(pos.getIndex());
/* 212:335 */         if ((c != ',') && (c != '}'))
/* 213:    */         {
/* 214:336 */           error = true;
/* 215:337 */           continue;
/* 216:    */         }
/* 217:    */       }
/* 218:340 */       if (((c == ',') || (c == '}')) && (result.length() > 0)) {
/* 219:    */         try
/* 220:    */         {
/* 221:342 */           return Integer.parseInt(result.toString());
/* 222:    */         }
/* 223:    */         catch (NumberFormatException e) {}
/* 224:    */       }
/* 225:348 */       error = !Character.isDigit(c);
/* 226:349 */       result.append(c);
/* 227:    */     }
/* 228:351 */     if (error) {
/* 229:352 */       throw new IllegalArgumentException("Invalid format argument index at position " + start + ": " + pattern.substring(start, pos.getIndex()));
/* 230:    */     }
/* 231:356 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
/* 232:    */   }
/* 233:    */   
/* 234:    */   private String parseFormatDescription(String pattern, ParsePosition pos)
/* 235:    */   {
/* 236:368 */     int start = pos.getIndex();
/* 237:369 */     seekNonWs(pattern, pos);
/* 238:370 */     int text = pos.getIndex();
/* 239:371 */     int depth = 1;
/* 240:372 */     for (; pos.getIndex() < pattern.length(); next(pos)) {
/* 241:373 */       switch (pattern.charAt(pos.getIndex()))
/* 242:    */       {
/* 243:    */       case '{': 
/* 244:375 */         depth++;
/* 245:376 */         break;
/* 246:    */       case '}': 
/* 247:378 */         depth--;
/* 248:379 */         if (depth == 0) {
/* 249:380 */           return pattern.substring(text, pos.getIndex());
/* 250:    */         }
/* 251:    */         break;
/* 252:    */       case '\'': 
/* 253:384 */         getQuotedString(pattern, pos, false);
/* 254:    */       }
/* 255:    */     }
/* 256:388 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
/* 257:    */   }
/* 258:    */   
/* 259:    */   private String insertFormats(String pattern, ArrayList customPatterns)
/* 260:    */   {
/* 261:400 */     if (!containsElements(customPatterns)) {
/* 262:401 */       return pattern;
/* 263:    */     }
/* 264:403 */     StrBuilder sb = new StrBuilder(pattern.length() * 2);
/* 265:404 */     ParsePosition pos = new ParsePosition(0);
/* 266:405 */     int fe = -1;
/* 267:406 */     int depth = 0;
/* 268:407 */     while (pos.getIndex() < pattern.length())
/* 269:    */     {
/* 270:408 */       char c = pattern.charAt(pos.getIndex());
/* 271:409 */       switch (c)
/* 272:    */       {
/* 273:    */       case '\'': 
/* 274:411 */         appendQuotedString(pattern, pos, sb, false);
/* 275:412 */         break;
/* 276:    */       case '{': 
/* 277:414 */         depth++;
/* 278:415 */         if (depth == 1)
/* 279:    */         {
/* 280:416 */           fe++;
/* 281:417 */           sb.append('{').append(readArgumentIndex(pattern, next(pos)));
/* 282:    */           
/* 283:419 */           String customPattern = (String)customPatterns.get(fe);
/* 284:420 */           if (customPattern != null) {
/* 285:421 */             sb.append(',').append(customPattern);
/* 286:    */           }
/* 287:    */         }
/* 288:423 */         break;
/* 289:    */       case '}': 
/* 290:426 */         depth--;
/* 291:    */       default: 
/* 292:429 */         sb.append(c);
/* 293:430 */         next(pos);
/* 294:    */       }
/* 295:    */     }
/* 296:433 */     return sb.toString();
/* 297:    */   }
/* 298:    */   
/* 299:    */   private void seekNonWs(String pattern, ParsePosition pos)
/* 300:    */   {
/* 301:443 */     int len = 0;
/* 302:444 */     char[] buffer = pattern.toCharArray();
/* 303:    */     do
/* 304:    */     {
/* 305:446 */       len = StrMatcher.splitMatcher().isMatch(buffer, pos.getIndex());
/* 306:447 */       pos.setIndex(pos.getIndex() + len);
/* 307:448 */     } while ((len > 0) && (pos.getIndex() < pattern.length()));
/* 308:    */   }
/* 309:    */   
/* 310:    */   private ParsePosition next(ParsePosition pos)
/* 311:    */   {
/* 312:458 */     pos.setIndex(pos.getIndex() + 1);
/* 313:459 */     return pos;
/* 314:    */   }
/* 315:    */   
/* 316:    */   private StrBuilder appendQuotedString(String pattern, ParsePosition pos, StrBuilder appendTo, boolean escapingOn)
/* 317:    */   {
/* 318:474 */     int start = pos.getIndex();
/* 319:475 */     char[] c = pattern.toCharArray();
/* 320:476 */     if ((escapingOn) && (c[start] == '\''))
/* 321:    */     {
/* 322:477 */       next(pos);
/* 323:478 */       return appendTo == null ? null : appendTo.append('\'');
/* 324:    */     }
/* 325:480 */     int lastHold = start;
/* 326:481 */     for (int i = pos.getIndex(); i < pattern.length(); i++) {
/* 327:482 */       if ((escapingOn) && (pattern.substring(i).startsWith("''")))
/* 328:    */       {
/* 329:483 */         appendTo.append(c, lastHold, pos.getIndex() - lastHold).append('\'');
/* 330:    */         
/* 331:485 */         pos.setIndex(i + "''".length());
/* 332:486 */         lastHold = pos.getIndex();
/* 333:    */       }
/* 334:    */       else
/* 335:    */       {
/* 336:489 */         switch (c[pos.getIndex()])
/* 337:    */         {
/* 338:    */         case '\'': 
/* 339:491 */           next(pos);
/* 340:492 */           return appendTo == null ? null : appendTo.append(c, lastHold, pos.getIndex() - lastHold);
/* 341:    */         }
/* 342:495 */         next(pos);
/* 343:    */       }
/* 344:    */     }
/* 345:498 */     throw new IllegalArgumentException("Unterminated quoted string at position " + start);
/* 346:    */   }
/* 347:    */   
/* 348:    */   private void getQuotedString(String pattern, ParsePosition pos, boolean escapingOn)
/* 349:    */   {
/* 350:511 */     appendQuotedString(pattern, pos, null, escapingOn);
/* 351:    */   }
/* 352:    */   
/* 353:    */   private boolean containsElements(Collection coll)
/* 354:    */   {
/* 355:520 */     if ((coll == null) || (coll.size() == 0)) {
/* 356:521 */       return false;
/* 357:    */     }
/* 358:523 */     for (Iterator iter = coll.iterator(); iter.hasNext();) {
/* 359:524 */       if (iter.next() != null) {
/* 360:525 */         return true;
/* 361:    */       }
/* 362:    */     }
/* 363:528 */     return false;
/* 364:    */   }
/* 365:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.ExtendedMessageFormat
 * JD-Core Version:    0.7.0.1
 */