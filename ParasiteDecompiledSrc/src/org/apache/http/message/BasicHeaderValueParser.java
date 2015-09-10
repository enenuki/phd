/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.http.HeaderElement;
/*   6:    */ import org.apache.http.NameValuePair;
/*   7:    */ import org.apache.http.ParseException;
/*   8:    */ import org.apache.http.protocol.HTTP;
/*   9:    */ import org.apache.http.util.CharArrayBuffer;
/*  10:    */ 
/*  11:    */ public class BasicHeaderValueParser
/*  12:    */   implements HeaderValueParser
/*  13:    */ {
/*  14: 55 */   public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
/*  15:    */   private static final char PARAM_DELIMITER = ';';
/*  16:    */   private static final char ELEM_DELIMITER = ',';
/*  17: 59 */   private static final char[] ALL_DELIMITERS = { ';', ',' };
/*  18:    */   
/*  19:    */   public static final HeaderElement[] parseElements(String value, HeaderValueParser parser)
/*  20:    */     throws ParseException
/*  21:    */   {
/*  22: 80 */     if (value == null) {
/*  23: 81 */       throw new IllegalArgumentException("Value to parse may not be null");
/*  24:    */     }
/*  25: 85 */     if (parser == null) {
/*  26: 86 */       parser = DEFAULT;
/*  27:    */     }
/*  28: 88 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/*  29: 89 */     buffer.append(value);
/*  30: 90 */     ParserCursor cursor = new ParserCursor(0, value.length());
/*  31: 91 */     return parser.parseElements(buffer, cursor);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public HeaderElement[] parseElements(CharArrayBuffer buffer, ParserCursor cursor)
/*  35:    */   {
/*  36: 99 */     if (buffer == null) {
/*  37:100 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*  38:    */     }
/*  39:102 */     if (cursor == null) {
/*  40:103 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*  41:    */     }
/*  42:106 */     List elements = new ArrayList();
/*  43:107 */     while (!cursor.atEnd())
/*  44:    */     {
/*  45:108 */       HeaderElement element = parseHeaderElement(buffer, cursor);
/*  46:109 */       if ((element.getName().length() != 0) || (element.getValue() != null)) {
/*  47:110 */         elements.add(element);
/*  48:    */       }
/*  49:    */     }
/*  50:113 */     return (HeaderElement[])elements.toArray(new HeaderElement[elements.size()]);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static final HeaderElement parseHeaderElement(String value, HeaderValueParser parser)
/*  54:    */     throws ParseException
/*  55:    */   {
/*  56:131 */     if (value == null) {
/*  57:132 */       throw new IllegalArgumentException("Value to parse may not be null");
/*  58:    */     }
/*  59:136 */     if (parser == null) {
/*  60:137 */       parser = DEFAULT;
/*  61:    */     }
/*  62:139 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/*  63:140 */     buffer.append(value);
/*  64:141 */     ParserCursor cursor = new ParserCursor(0, value.length());
/*  65:142 */     return parser.parseHeaderElement(buffer, cursor);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public HeaderElement parseHeaderElement(CharArrayBuffer buffer, ParserCursor cursor)
/*  69:    */   {
/*  70:150 */     if (buffer == null) {
/*  71:151 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*  72:    */     }
/*  73:153 */     if (cursor == null) {
/*  74:154 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*  75:    */     }
/*  76:157 */     NameValuePair nvp = parseNameValuePair(buffer, cursor);
/*  77:158 */     NameValuePair[] params = null;
/*  78:159 */     if (!cursor.atEnd())
/*  79:    */     {
/*  80:160 */       char ch = buffer.charAt(cursor.getPos() - 1);
/*  81:161 */       if (ch != ',') {
/*  82:162 */         params = parseParameters(buffer, cursor);
/*  83:    */       }
/*  84:    */     }
/*  85:165 */     return createHeaderElement(nvp.getName(), nvp.getValue(), params);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected HeaderElement createHeaderElement(String name, String value, NameValuePair[] params)
/*  89:    */   {
/*  90:179 */     return new BasicHeaderElement(name, value, params);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static final NameValuePair[] parseParameters(String value, HeaderValueParser parser)
/*  94:    */     throws ParseException
/*  95:    */   {
/*  96:196 */     if (value == null) {
/*  97:197 */       throw new IllegalArgumentException("Value to parse may not be null");
/*  98:    */     }
/*  99:201 */     if (parser == null) {
/* 100:202 */       parser = DEFAULT;
/* 101:    */     }
/* 102:204 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 103:205 */     buffer.append(value);
/* 104:206 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 105:207 */     return parser.parseParameters(buffer, cursor);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public NameValuePair[] parseParameters(CharArrayBuffer buffer, ParserCursor cursor)
/* 109:    */   {
/* 110:216 */     if (buffer == null) {
/* 111:217 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 112:    */     }
/* 113:219 */     if (cursor == null) {
/* 114:220 */       throw new IllegalArgumentException("Parser cursor may not be null");
/* 115:    */     }
/* 116:223 */     int pos = cursor.getPos();
/* 117:224 */     int indexTo = cursor.getUpperBound();
/* 118:226 */     while (pos < indexTo)
/* 119:    */     {
/* 120:227 */       char ch = buffer.charAt(pos);
/* 121:228 */       if (!HTTP.isWhitespace(ch)) {
/* 122:    */         break;
/* 123:    */       }
/* 124:229 */       pos++;
/* 125:    */     }
/* 126:234 */     cursor.updatePos(pos);
/* 127:235 */     if (cursor.atEnd()) {
/* 128:236 */       return new NameValuePair[0];
/* 129:    */     }
/* 130:239 */     List params = new ArrayList();
/* 131:240 */     while (!cursor.atEnd())
/* 132:    */     {
/* 133:241 */       NameValuePair param = parseNameValuePair(buffer, cursor);
/* 134:242 */       params.add(param);
/* 135:243 */       char ch = buffer.charAt(cursor.getPos() - 1);
/* 136:244 */       if (ch == ',') {
/* 137:    */         break;
/* 138:    */       }
/* 139:    */     }
/* 140:249 */     return (NameValuePair[])params.toArray(new NameValuePair[params.size()]);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static final NameValuePair parseNameValuePair(String value, HeaderValueParser parser)
/* 144:    */     throws ParseException
/* 145:    */   {
/* 146:266 */     if (value == null) {
/* 147:267 */       throw new IllegalArgumentException("Value to parse may not be null");
/* 148:    */     }
/* 149:271 */     if (parser == null) {
/* 150:272 */       parser = DEFAULT;
/* 151:    */     }
/* 152:274 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 153:275 */     buffer.append(value);
/* 154:276 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 155:277 */     return parser.parseNameValuePair(buffer, cursor);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor)
/* 159:    */   {
/* 160:284 */     return parseNameValuePair(buffer, cursor, ALL_DELIMITERS);
/* 161:    */   }
/* 162:    */   
/* 163:    */   private static boolean isOneOf(char ch, char[] chs)
/* 164:    */   {
/* 165:288 */     if (chs != null) {
/* 166:289 */       for (int i = 0; i < chs.length; i++) {
/* 167:290 */         if (ch == chs[i]) {
/* 168:291 */           return true;
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:295 */     return false;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor, char[] delimiters)
/* 176:    */   {
/* 177:302 */     if (buffer == null) {
/* 178:303 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 179:    */     }
/* 180:305 */     if (cursor == null) {
/* 181:306 */       throw new IllegalArgumentException("Parser cursor may not be null");
/* 182:    */     }
/* 183:309 */     boolean terminated = false;
/* 184:    */     
/* 185:311 */     int pos = cursor.getPos();
/* 186:312 */     int indexFrom = cursor.getPos();
/* 187:313 */     int indexTo = cursor.getUpperBound();
/* 188:    */     
/* 189:    */ 
/* 190:316 */     String name = null;
/* 191:317 */     while (pos < indexTo)
/* 192:    */     {
/* 193:318 */       char ch = buffer.charAt(pos);
/* 194:319 */       if (ch == '=') {
/* 195:    */         break;
/* 196:    */       }
/* 197:322 */       if (isOneOf(ch, delimiters))
/* 198:    */       {
/* 199:323 */         terminated = true;
/* 200:324 */         break;
/* 201:    */       }
/* 202:326 */       pos++;
/* 203:    */     }
/* 204:329 */     if (pos == indexTo)
/* 205:    */     {
/* 206:330 */       terminated = true;
/* 207:331 */       name = buffer.substringTrimmed(indexFrom, indexTo);
/* 208:    */     }
/* 209:    */     else
/* 210:    */     {
/* 211:333 */       name = buffer.substringTrimmed(indexFrom, pos);
/* 212:334 */       pos++;
/* 213:    */     }
/* 214:337 */     if (terminated)
/* 215:    */     {
/* 216:338 */       cursor.updatePos(pos);
/* 217:339 */       return createNameValuePair(name, null);
/* 218:    */     }
/* 219:343 */     String value = null;
/* 220:344 */     int i1 = pos;
/* 221:    */     
/* 222:346 */     boolean qouted = false;
/* 223:347 */     boolean escaped = false;
/* 224:348 */     while (pos < indexTo)
/* 225:    */     {
/* 226:349 */       char ch = buffer.charAt(pos);
/* 227:350 */       if ((ch == '"') && (!escaped)) {
/* 228:351 */         qouted = !qouted;
/* 229:    */       }
/* 230:353 */       if ((!qouted) && (!escaped) && (isOneOf(ch, delimiters)))
/* 231:    */       {
/* 232:354 */         terminated = true;
/* 233:355 */         break;
/* 234:    */       }
/* 235:357 */       if (escaped) {
/* 236:358 */         escaped = false;
/* 237:    */       } else {
/* 238:360 */         escaped = (qouted) && (ch == '\\');
/* 239:    */       }
/* 240:362 */       pos++;
/* 241:    */     }
/* 242:365 */     int i2 = pos;
/* 243:367 */     while ((i1 < i2) && (HTTP.isWhitespace(buffer.charAt(i1)))) {
/* 244:368 */       i1++;
/* 245:    */     }
/* 246:371 */     while ((i2 > i1) && (HTTP.isWhitespace(buffer.charAt(i2 - 1)))) {
/* 247:372 */       i2--;
/* 248:    */     }
/* 249:375 */     if ((i2 - i1 >= 2) && (buffer.charAt(i1) == '"') && (buffer.charAt(i2 - 1) == '"'))
/* 250:    */     {
/* 251:378 */       i1++;
/* 252:379 */       i2--;
/* 253:    */     }
/* 254:381 */     value = buffer.substring(i1, i2);
/* 255:382 */     if (terminated) {
/* 256:383 */       pos++;
/* 257:    */     }
/* 258:385 */     cursor.updatePos(pos);
/* 259:386 */     return createNameValuePair(name, value);
/* 260:    */   }
/* 261:    */   
/* 262:    */   protected NameValuePair createNameValuePair(String name, String value)
/* 263:    */   {
/* 264:399 */     return new BasicNameValuePair(name, value);
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicHeaderValueParser
 * JD-Core Version:    0.7.0.1
 */