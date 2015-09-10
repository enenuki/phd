/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ public class WordUtils
/*   4:    */ {
/*   5:    */   public static String wrap(String str, int wrapLength)
/*   6:    */   {
/*   7:142 */     return wrap(str, wrapLength, null, false);
/*   8:    */   }
/*   9:    */   
/*  10:    */   public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords)
/*  11:    */   {
/*  12:164 */     if (str == null) {
/*  13:165 */       return null;
/*  14:    */     }
/*  15:167 */     if (newLineStr == null) {
/*  16:168 */       newLineStr = SystemUtils.LINE_SEPARATOR;
/*  17:    */     }
/*  18:170 */     if (wrapLength < 1) {
/*  19:171 */       wrapLength = 1;
/*  20:    */     }
/*  21:173 */     int inputLineLength = str.length();
/*  22:174 */     int offset = 0;
/*  23:175 */     StringBuffer wrappedLine = new StringBuffer(inputLineLength + 32);
/*  24:177 */     while (inputLineLength - offset > wrapLength) {
/*  25:178 */       if (str.charAt(offset) == ' ')
/*  26:    */       {
/*  27:179 */         offset++;
/*  28:    */       }
/*  29:    */       else
/*  30:    */       {
/*  31:182 */         int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);
/*  32:184 */         if (spaceToWrapAt >= offset)
/*  33:    */         {
/*  34:186 */           wrappedLine.append(str.substring(offset, spaceToWrapAt));
/*  35:187 */           wrappedLine.append(newLineStr);
/*  36:188 */           offset = spaceToWrapAt + 1;
/*  37:    */         }
/*  38:192 */         else if (wrapLongWords)
/*  39:    */         {
/*  40:194 */           wrappedLine.append(str.substring(offset, wrapLength + offset));
/*  41:195 */           wrappedLine.append(newLineStr);
/*  42:196 */           offset += wrapLength;
/*  43:    */         }
/*  44:    */         else
/*  45:    */         {
/*  46:199 */           spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
/*  47:200 */           if (spaceToWrapAt >= 0)
/*  48:    */           {
/*  49:201 */             wrappedLine.append(str.substring(offset, spaceToWrapAt));
/*  50:202 */             wrappedLine.append(newLineStr);
/*  51:203 */             offset = spaceToWrapAt + 1;
/*  52:    */           }
/*  53:    */           else
/*  54:    */           {
/*  55:205 */             wrappedLine.append(str.substring(offset));
/*  56:206 */             offset = inputLineLength;
/*  57:    */           }
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:213 */     wrappedLine.append(str.substring(offset));
/*  62:    */     
/*  63:215 */     return wrappedLine.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String capitalize(String str)
/*  67:    */   {
/*  68:243 */     return capitalize(str, null);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static String capitalize(String str, char[] delimiters)
/*  72:    */   {
/*  73:276 */     int delimLen = delimiters == null ? -1 : delimiters.length;
/*  74:277 */     if ((str == null) || (str.length() == 0) || (delimLen == 0)) {
/*  75:278 */       return str;
/*  76:    */     }
/*  77:280 */     int strLen = str.length();
/*  78:281 */     StringBuffer buffer = new StringBuffer(strLen);
/*  79:282 */     boolean capitalizeNext = true;
/*  80:283 */     for (int i = 0; i < strLen; i++)
/*  81:    */     {
/*  82:284 */       char ch = str.charAt(i);
/*  83:286 */       if (isDelimiter(ch, delimiters))
/*  84:    */       {
/*  85:287 */         buffer.append(ch);
/*  86:288 */         capitalizeNext = true;
/*  87:    */       }
/*  88:289 */       else if (capitalizeNext)
/*  89:    */       {
/*  90:290 */         buffer.append(Character.toTitleCase(ch));
/*  91:291 */         capitalizeNext = false;
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:293 */         buffer.append(ch);
/*  96:    */       }
/*  97:    */     }
/*  98:296 */     return buffer.toString();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static String capitalizeFully(String str)
/* 102:    */   {
/* 103:320 */     return capitalizeFully(str, null);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static String capitalizeFully(String str, char[] delimiters)
/* 107:    */   {
/* 108:350 */     int delimLen = delimiters == null ? -1 : delimiters.length;
/* 109:351 */     if ((str == null) || (str.length() == 0) || (delimLen == 0)) {
/* 110:352 */       return str;
/* 111:    */     }
/* 112:354 */     str = str.toLowerCase();
/* 113:355 */     return capitalize(str, delimiters);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String uncapitalize(String str)
/* 117:    */   {
/* 118:377 */     return uncapitalize(str, null);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static String uncapitalize(String str, char[] delimiters)
/* 122:    */   {
/* 123:406 */     int delimLen = delimiters == null ? -1 : delimiters.length;
/* 124:407 */     if ((str == null) || (str.length() == 0) || (delimLen == 0)) {
/* 125:408 */       return str;
/* 126:    */     }
/* 127:410 */     int strLen = str.length();
/* 128:411 */     StringBuffer buffer = new StringBuffer(strLen);
/* 129:412 */     boolean uncapitalizeNext = true;
/* 130:413 */     for (int i = 0; i < strLen; i++)
/* 131:    */     {
/* 132:414 */       char ch = str.charAt(i);
/* 133:416 */       if (isDelimiter(ch, delimiters))
/* 134:    */       {
/* 135:417 */         buffer.append(ch);
/* 136:418 */         uncapitalizeNext = true;
/* 137:    */       }
/* 138:419 */       else if (uncapitalizeNext)
/* 139:    */       {
/* 140:420 */         buffer.append(Character.toLowerCase(ch));
/* 141:421 */         uncapitalizeNext = false;
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:423 */         buffer.append(ch);
/* 146:    */       }
/* 147:    */     }
/* 148:426 */     return buffer.toString();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static String swapCase(String str)
/* 152:    */   {
/* 153:    */     int strLen;
/* 154:454 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/* 155:455 */       return str;
/* 156:    */     }
/* 157:    */     int strLen;
/* 158:457 */     StringBuffer buffer = new StringBuffer(strLen);
/* 159:    */     
/* 160:459 */     boolean whitespace = true;
/* 161:460 */     char ch = '\000';
/* 162:461 */     char tmp = '\000';
/* 163:463 */     for (int i = 0; i < strLen; i++)
/* 164:    */     {
/* 165:464 */       ch = str.charAt(i);
/* 166:465 */       if (Character.isUpperCase(ch)) {
/* 167:466 */         tmp = Character.toLowerCase(ch);
/* 168:467 */       } else if (Character.isTitleCase(ch)) {
/* 169:468 */         tmp = Character.toLowerCase(ch);
/* 170:469 */       } else if (Character.isLowerCase(ch))
/* 171:    */       {
/* 172:470 */         if (whitespace) {
/* 173:471 */           tmp = Character.toTitleCase(ch);
/* 174:    */         } else {
/* 175:473 */           tmp = Character.toUpperCase(ch);
/* 176:    */         }
/* 177:    */       }
/* 178:    */       else {
/* 179:476 */         tmp = ch;
/* 180:    */       }
/* 181:478 */       buffer.append(tmp);
/* 182:479 */       whitespace = Character.isWhitespace(ch);
/* 183:    */     }
/* 184:481 */     return buffer.toString();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static String initials(String str)
/* 188:    */   {
/* 189:508 */     return initials(str, null);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static String initials(String str, char[] delimiters)
/* 193:    */   {
/* 194:539 */     if ((str == null) || (str.length() == 0)) {
/* 195:540 */       return str;
/* 196:    */     }
/* 197:542 */     if ((delimiters != null) && (delimiters.length == 0)) {
/* 198:543 */       return "";
/* 199:    */     }
/* 200:545 */     int strLen = str.length();
/* 201:546 */     char[] buf = new char[strLen / 2 + 1];
/* 202:547 */     int count = 0;
/* 203:548 */     boolean lastWasGap = true;
/* 204:549 */     for (int i = 0; i < strLen; i++)
/* 205:    */     {
/* 206:550 */       char ch = str.charAt(i);
/* 207:552 */       if (isDelimiter(ch, delimiters))
/* 208:    */       {
/* 209:553 */         lastWasGap = true;
/* 210:    */       }
/* 211:554 */       else if (lastWasGap)
/* 212:    */       {
/* 213:555 */         buf[(count++)] = ch;
/* 214:556 */         lastWasGap = false;
/* 215:    */       }
/* 216:    */     }
/* 217:561 */     return new String(buf, 0, count);
/* 218:    */   }
/* 219:    */   
/* 220:    */   private static boolean isDelimiter(char ch, char[] delimiters)
/* 221:    */   {
/* 222:573 */     if (delimiters == null) {
/* 223:574 */       return Character.isWhitespace(ch);
/* 224:    */     }
/* 225:576 */     int i = 0;
/* 226:576 */     for (int isize = delimiters.length; i < isize; i++) {
/* 227:577 */       if (ch == delimiters[i]) {
/* 228:578 */         return true;
/* 229:    */       }
/* 230:    */     }
/* 231:581 */     return false;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static String abbreviate(String str, int lower, int upper, String appendToEnd)
/* 235:    */   {
/* 236:607 */     if (str == null) {
/* 237:608 */       return null;
/* 238:    */     }
/* 239:610 */     if (str.length() == 0) {
/* 240:611 */       return "";
/* 241:    */     }
/* 242:616 */     if (lower > str.length()) {
/* 243:617 */       lower = str.length();
/* 244:    */     }
/* 245:621 */     if ((upper == -1) || (upper > str.length())) {
/* 246:622 */       upper = str.length();
/* 247:    */     }
/* 248:625 */     if (upper < lower) {
/* 249:626 */       upper = lower;
/* 250:    */     }
/* 251:629 */     StringBuffer result = new StringBuffer();
/* 252:630 */     int index = StringUtils.indexOf(str, " ", lower);
/* 253:631 */     if (index == -1)
/* 254:    */     {
/* 255:632 */       result.append(str.substring(0, upper));
/* 256:634 */       if (upper != str.length()) {
/* 257:635 */         result.append(StringUtils.defaultString(appendToEnd));
/* 258:    */       }
/* 259:    */     }
/* 260:637 */     else if (index > upper)
/* 261:    */     {
/* 262:638 */       result.append(str.substring(0, upper));
/* 263:639 */       result.append(StringUtils.defaultString(appendToEnd));
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:641 */       result.append(str.substring(0, index));
/* 268:642 */       result.append(StringUtils.defaultString(appendToEnd));
/* 269:    */     }
/* 270:644 */     return result.toString();
/* 271:    */   }
/* 272:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.WordUtils
 * JD-Core Version:    0.7.0.1
 */