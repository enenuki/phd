/*   1:    */ package org.hibernate.annotations.common.util;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ 
/*   7:    */ public final class StringHelper
/*   8:    */ {
/*   9:    */   private static final int ALIAS_TRUNCATE_LENGTH = 10;
/*  10:    */   public static final String WHITESPACE = " \n\r\f\t";
/*  11:    */   
/*  12:    */   public static int lastIndexOfLetter(String string)
/*  13:    */   {
/*  14: 52 */     for (int i = 0; i < string.length(); i++)
/*  15:    */     {
/*  16: 53 */       char character = string.charAt(i);
/*  17: 54 */       if (!Character.isLetter(character)) {
/*  18: 54 */         return i - 1;
/*  19:    */       }
/*  20:    */     }
/*  21: 56 */     return string.length() - 1;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String join(String seperator, String[] strings)
/*  25:    */   {
/*  26: 60 */     int length = strings.length;
/*  27: 61 */     if (length == 0) {
/*  28: 61 */       return "";
/*  29:    */     }
/*  30: 62 */     StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
/*  31: 64 */     for (int i = 1; i < length; i++) {
/*  32: 65 */       buf.append(seperator).append(strings[i]);
/*  33:    */     }
/*  34: 67 */     return buf.toString();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static String join(String seperator, Iterator objects)
/*  38:    */   {
/*  39: 71 */     StringBuffer buf = new StringBuffer();
/*  40: 72 */     if (objects.hasNext()) {
/*  41: 72 */       buf.append(objects.next());
/*  42:    */     }
/*  43: 73 */     while (objects.hasNext()) {
/*  44: 74 */       buf.append(seperator).append(objects.next());
/*  45:    */     }
/*  46: 76 */     return buf.toString();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static String[] add(String[] x, String sep, String[] y)
/*  50:    */   {
/*  51: 80 */     String[] result = new String[x.length];
/*  52: 81 */     for (int i = 0; i < x.length; i++) {
/*  53: 82 */       result[i] = (x[i] + sep + y[i]);
/*  54:    */     }
/*  55: 84 */     return result;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static String repeat(String string, int times)
/*  59:    */   {
/*  60: 88 */     StringBuffer buf = new StringBuffer(string.length() * times);
/*  61: 89 */     for (int i = 0; i < times; i++) {
/*  62: 89 */       buf.append(string);
/*  63:    */     }
/*  64: 90 */     return buf.toString();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static String repeat(char character, int times)
/*  68:    */   {
/*  69: 94 */     char[] buffer = new char[times];
/*  70: 95 */     Arrays.fill(buffer, character);
/*  71: 96 */     return new String(buffer);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static String replace(String template, String placeholder, String replacement)
/*  75:    */   {
/*  76:101 */     return replace(template, placeholder, replacement, false);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static String[] replace(String[] templates, String placeholder, String replacement)
/*  80:    */   {
/*  81:105 */     String[] result = new String[templates.length];
/*  82:106 */     for (int i = 0; i < templates.length; i++) {
/*  83:107 */       result[i] = replace(templates[i], placeholder, replacement);
/*  84:    */     }
/*  85:109 */     return result;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static String replace(String template, String placeholder, String replacement, boolean wholeWords)
/*  89:    */   {
/*  90:113 */     if (template == null) {
/*  91:114 */       return template;
/*  92:    */     }
/*  93:116 */     int loc = template.indexOf(placeholder);
/*  94:117 */     if (loc < 0) {
/*  95:118 */       return template;
/*  96:    */     }
/*  97:121 */     boolean actuallyReplace = (!wholeWords) || (loc + placeholder.length() == template.length()) || (!Character.isJavaIdentifierPart(template.charAt(loc + placeholder.length())));
/*  98:    */     
/*  99:    */ 
/* 100:124 */     String actualReplacement = actuallyReplace ? replacement : placeholder;
/* 101:125 */     return template.substring(0, loc) + actualReplacement + replace(template.substring(loc + placeholder.length()), placeholder, replacement, wholeWords);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String replaceOnce(String template, String placeholder, String replacement)
/* 105:    */   {
/* 106:136 */     if (template == null) {
/* 107:137 */       return template;
/* 108:    */     }
/* 109:139 */     int loc = template.indexOf(placeholder);
/* 110:140 */     if (loc < 0) {
/* 111:141 */       return template;
/* 112:    */     }
/* 113:144 */     return template.substring(0, loc) + replacement + template.substring(loc + placeholder.length());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String[] split(String seperators, String list)
/* 117:    */   {
/* 118:153 */     return split(seperators, list, false);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static String[] split(String seperators, String list, boolean include)
/* 122:    */   {
/* 123:157 */     StringTokenizer tokens = new StringTokenizer(list, seperators, include);
/* 124:158 */     String[] result = new String[tokens.countTokens()];
/* 125:159 */     int i = 0;
/* 126:160 */     while (tokens.hasMoreTokens()) {
/* 127:161 */       result[(i++)] = tokens.nextToken();
/* 128:    */     }
/* 129:163 */     return result;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static String unqualify(String qualifiedName)
/* 133:    */   {
/* 134:167 */     int loc = qualifiedName.lastIndexOf(".");
/* 135:168 */     return loc < 0 ? qualifiedName : qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static String qualifier(String qualifiedName)
/* 139:    */   {
/* 140:172 */     int loc = qualifiedName.lastIndexOf(".");
/* 141:173 */     return loc < 0 ? "" : qualifiedName.substring(0, loc);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static String collapse(String name)
/* 145:    */   {
/* 146:185 */     if (name == null) {
/* 147:186 */       return null;
/* 148:    */     }
/* 149:188 */     int breakPoint = name.lastIndexOf('.');
/* 150:189 */     if (breakPoint < 0) {
/* 151:190 */       return name;
/* 152:    */     }
/* 153:192 */     return collapseQualifier(name.substring(0, breakPoint), true) + name.substring(breakPoint);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static String collapseQualifier(String qualifier, boolean includeDots)
/* 157:    */   {
/* 158:204 */     StringTokenizer tokenizer = new StringTokenizer(qualifier, ".");
/* 159:205 */     String collapsed = Character.toString(tokenizer.nextToken().charAt(0));
/* 160:206 */     while (tokenizer.hasMoreTokens())
/* 161:    */     {
/* 162:207 */       if (includeDots) {
/* 163:208 */         collapsed = collapsed + '.';
/* 164:    */       }
/* 165:210 */       collapsed = collapsed + tokenizer.nextToken().charAt(0);
/* 166:    */     }
/* 167:212 */     return collapsed;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static String partiallyUnqualify(String name, String qualifierBase)
/* 171:    */   {
/* 172:225 */     if ((name == null) || (!name.startsWith(qualifierBase))) {
/* 173:226 */       return name;
/* 174:    */     }
/* 175:228 */     return name.substring(qualifierBase.length() + 1);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static String collapseQualifierBase(String name, String qualifierBase)
/* 179:    */   {
/* 180:242 */     if ((name == null) || (!name.startsWith(qualifierBase))) {
/* 181:243 */       return collapse(name);
/* 182:    */     }
/* 183:245 */     return collapseQualifier(qualifierBase, true) + name.substring(qualifierBase.length());
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static String[] suffix(String[] columns, String suffix)
/* 187:    */   {
/* 188:249 */     if (suffix == null) {
/* 189:249 */       return columns;
/* 190:    */     }
/* 191:250 */     String[] qualified = new String[columns.length];
/* 192:251 */     for (int i = 0; i < columns.length; i++) {
/* 193:252 */       qualified[i] = suffix(columns[i], suffix);
/* 194:    */     }
/* 195:254 */     return qualified;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private static String suffix(String name, String suffix)
/* 199:    */   {
/* 200:258 */     return name + suffix;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static String root(String qualifiedName)
/* 204:    */   {
/* 205:262 */     int loc = qualifiedName.indexOf(".");
/* 206:263 */     return loc < 0 ? qualifiedName : qualifiedName.substring(0, loc);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static String unroot(String qualifiedName)
/* 210:    */   {
/* 211:267 */     int loc = qualifiedName.indexOf(".");
/* 212:268 */     return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1, qualifiedName.length());
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static boolean booleanValue(String tfString)
/* 216:    */   {
/* 217:272 */     String trimmed = tfString.trim().toLowerCase();
/* 218:273 */     return (trimmed.equals("true")) || (trimmed.equals("t"));
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static String toString(Object[] array)
/* 222:    */   {
/* 223:277 */     int len = array.length;
/* 224:278 */     if (len == 0) {
/* 225:278 */       return "";
/* 226:    */     }
/* 227:279 */     StringBuffer buf = new StringBuffer(len * 12);
/* 228:280 */     for (int i = 0; i < len - 1; i++) {
/* 229:281 */       buf.append(array[i]).append(", ");
/* 230:    */     }
/* 231:283 */     return array[(len - 1)];
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static String[] multiply(String string, Iterator placeholders, Iterator replacements)
/* 235:    */   {
/* 236:287 */     String[] result = { string };
/* 237:288 */     while (placeholders.hasNext()) {
/* 238:289 */       result = multiply(result, (String)placeholders.next(), (String[])replacements.next());
/* 239:    */     }
/* 240:291 */     return result;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static String[] multiply(String[] strings, String placeholder, String[] replacements)
/* 244:    */   {
/* 245:295 */     String[] results = new String[replacements.length * strings.length];
/* 246:296 */     int n = 0;
/* 247:297 */     for (int i = 0; i < replacements.length; i++) {
/* 248:298 */       for (int j = 0; j < strings.length; j++) {
/* 249:299 */         results[(n++)] = replaceOnce(strings[j], placeholder, replacements[i]);
/* 250:    */       }
/* 251:    */     }
/* 252:302 */     return results;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static int countUnquoted(String string, char character)
/* 256:    */   {
/* 257:306 */     if ('\'' == character) {
/* 258:307 */       throw new IllegalArgumentException("Unquoted count of quotes is invalid");
/* 259:    */     }
/* 260:309 */     if (string == null) {
/* 261:310 */       return 0;
/* 262:    */     }
/* 263:314 */     int count = 0;
/* 264:315 */     int stringLength = string.length();
/* 265:316 */     boolean inQuote = false;
/* 266:317 */     for (int indx = 0; indx < stringLength; indx++)
/* 267:    */     {
/* 268:318 */       char c = string.charAt(indx);
/* 269:319 */       if (inQuote)
/* 270:    */       {
/* 271:320 */         if ('\'' == c) {
/* 272:321 */           inQuote = false;
/* 273:    */         }
/* 274:    */       }
/* 275:324 */       else if ('\'' == c) {
/* 276:325 */         inQuote = true;
/* 277:327 */       } else if (c == character) {
/* 278:328 */         count++;
/* 279:    */       }
/* 280:    */     }
/* 281:331 */     return count;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public static boolean isNotEmpty(String string)
/* 285:    */   {
/* 286:335 */     return (string != null) && (string.length() > 0);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public static boolean isEmpty(String string)
/* 290:    */   {
/* 291:339 */     return (string == null) || (string.length() == 0);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public static String qualify(String prefix, String name)
/* 295:    */   {
/* 296:343 */     if ((name == null) || (prefix == null)) {
/* 297:344 */       throw new NullPointerException();
/* 298:    */     }
/* 299:346 */     return prefix.length() + name.length() + 1 + prefix + '.' + name;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public static String[] qualify(String prefix, String[] names)
/* 303:    */   {
/* 304:354 */     if (prefix == null) {
/* 305:354 */       return names;
/* 306:    */     }
/* 307:355 */     int len = names.length;
/* 308:356 */     String[] qualified = new String[len];
/* 309:357 */     for (int i = 0; i < len; i++) {
/* 310:358 */       qualified[i] = qualify(prefix, names[i]);
/* 311:    */     }
/* 312:360 */     return qualified;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public static int firstIndexOfChar(String sqlString, String string, int startindex)
/* 316:    */   {
/* 317:364 */     int matchAt = -1;
/* 318:365 */     for (int i = 0; i < string.length(); i++)
/* 319:    */     {
/* 320:366 */       int curMatch = sqlString.indexOf(string.charAt(i), startindex);
/* 321:367 */       if (curMatch >= 0) {
/* 322:368 */         if (matchAt == -1) {
/* 323:369 */           matchAt = curMatch;
/* 324:    */         } else {
/* 325:372 */           matchAt = Math.min(matchAt, curMatch);
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:376 */     return matchAt;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public static String truncate(String string, int length)
/* 333:    */   {
/* 334:380 */     if (string.length() <= length) {
/* 335:381 */       return string;
/* 336:    */     }
/* 337:384 */     return string.substring(0, length);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public static String generateAlias(String description)
/* 341:    */   {
/* 342:389 */     return generateAliasRoot(description) + '_';
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static String generateAlias(String description, int unique)
/* 346:    */   {
/* 347:402 */     return generateAliasRoot(description) + Integer.toString(unique) + '_';
/* 348:    */   }
/* 349:    */   
/* 350:    */   private static String generateAliasRoot(String description)
/* 351:    */   {
/* 352:416 */     String result = truncate(unqualifyEntityName(description), 10).toLowerCase().replace('/', '_').replace('$', '_');
/* 353:    */     
/* 354:    */ 
/* 355:    */ 
/* 356:420 */     result = cleanAlias(result);
/* 357:421 */     if (Character.isDigit(result.charAt(result.length() - 1))) {
/* 358:422 */       return result + "x";
/* 359:    */     }
/* 360:425 */     return result;
/* 361:    */   }
/* 362:    */   
/* 363:    */   private static String cleanAlias(String alias)
/* 364:    */   {
/* 365:437 */     char[] chars = alias.toCharArray();
/* 366:439 */     if (!Character.isLetter(chars[0])) {
/* 367:440 */       for (int i = 1; i < chars.length; i++) {
/* 368:443 */         if (Character.isLetter(chars[i])) {
/* 369:444 */           return alias.substring(i);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:448 */     return alias;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public static String unqualifyEntityName(String entityName)
/* 377:    */   {
/* 378:452 */     String result = unqualify(entityName);
/* 379:453 */     int slashPos = result.indexOf('/');
/* 380:454 */     if (slashPos > 0) {
/* 381:455 */       result = result.substring(0, slashPos - 1);
/* 382:    */     }
/* 383:457 */     return result;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public static String toUpperCase(String str)
/* 387:    */   {
/* 388:461 */     return str == null ? null : str.toUpperCase();
/* 389:    */   }
/* 390:    */   
/* 391:    */   public static String toLowerCase(String str)
/* 392:    */   {
/* 393:465 */     return str == null ? null : str.toLowerCase();
/* 394:    */   }
/* 395:    */   
/* 396:    */   public static String moveAndToBeginning(String filter)
/* 397:    */   {
/* 398:469 */     if (filter.trim().length() > 0)
/* 399:    */     {
/* 400:470 */       filter = filter + " and ";
/* 401:471 */       if (filter.startsWith(" and ")) {
/* 402:471 */         filter = filter.substring(4);
/* 403:    */       }
/* 404:    */     }
/* 405:473 */     return filter;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public static boolean isQuoted(String name)
/* 409:    */   {
/* 410:483 */     return (name != null) && (name.length() != 0) && (name.charAt(0) == '`') && (name.charAt(name.length() - 1) == '`');
/* 411:    */   }
/* 412:    */   
/* 413:    */   public static String quote(String name)
/* 414:    */   {
/* 415:494 */     if ((name == null) || (name.length() == 0) || (isQuoted(name))) {
/* 416:495 */       return name;
/* 417:    */     }
/* 418:498 */     return name.length() + 2 + '`' + name + '`';
/* 419:    */   }
/* 420:    */   
/* 421:    */   public static String unquote(String name)
/* 422:    */   {
/* 423:509 */     if (isQuoted(name)) {
/* 424:510 */       return name.substring(1, name.length() - 1);
/* 425:    */     }
/* 426:513 */     return name;
/* 427:    */   }
/* 428:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.util.StringHelper
 * JD-Core Version:    0.7.0.1
 */