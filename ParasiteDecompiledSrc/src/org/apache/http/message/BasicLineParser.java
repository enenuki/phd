/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import org.apache.http.Header;
/*   4:    */ import org.apache.http.HttpVersion;
/*   5:    */ import org.apache.http.ParseException;
/*   6:    */ import org.apache.http.ProtocolVersion;
/*   7:    */ import org.apache.http.RequestLine;
/*   8:    */ import org.apache.http.StatusLine;
/*   9:    */ import org.apache.http.protocol.HTTP;
/*  10:    */ import org.apache.http.util.CharArrayBuffer;
/*  11:    */ 
/*  12:    */ public class BasicLineParser
/*  13:    */   implements LineParser
/*  14:    */ {
/*  15: 65 */   public static final BasicLineParser DEFAULT = new BasicLineParser();
/*  16:    */   protected final ProtocolVersion protocol;
/*  17:    */   
/*  18:    */   public BasicLineParser(ProtocolVersion proto)
/*  19:    */   {
/*  20: 83 */     if (proto == null) {
/*  21: 84 */       proto = HttpVersion.HTTP_1_1;
/*  22:    */     }
/*  23: 86 */     this.protocol = proto;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public BasicLineParser()
/*  27:    */   {
/*  28: 94 */     this(null);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static final ProtocolVersion parseProtocolVersion(String value, LineParser parser)
/*  32:    */     throws ParseException
/*  33:    */   {
/*  34:103 */     if (value == null) {
/*  35:104 */       throw new IllegalArgumentException("Value to parse may not be null.");
/*  36:    */     }
/*  37:108 */     if (parser == null) {
/*  38:109 */       parser = DEFAULT;
/*  39:    */     }
/*  40:111 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/*  41:112 */     buffer.append(value);
/*  42:113 */     ParserCursor cursor = new ParserCursor(0, value.length());
/*  43:114 */     return parser.parseProtocolVersion(buffer, cursor);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public ProtocolVersion parseProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor)
/*  47:    */     throws ParseException
/*  48:    */   {
/*  49:123 */     if (buffer == null) {
/*  50:124 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*  51:    */     }
/*  52:126 */     if (cursor == null) {
/*  53:127 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*  54:    */     }
/*  55:130 */     String protoname = this.protocol.getProtocol();
/*  56:131 */     int protolength = protoname.length();
/*  57:    */     
/*  58:133 */     int indexFrom = cursor.getPos();
/*  59:134 */     int indexTo = cursor.getUpperBound();
/*  60:    */     
/*  61:136 */     skipWhitespace(buffer, cursor);
/*  62:    */     
/*  63:138 */     int i = cursor.getPos();
/*  64:141 */     if (i + protolength + 4 > indexTo) {
/*  65:142 */       throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
/*  66:    */     }
/*  67:148 */     boolean ok = true;
/*  68:149 */     for (int j = 0; (ok) && (j < protolength); j++) {
/*  69:150 */       ok = buffer.charAt(i + j) == protoname.charAt(j);
/*  70:    */     }
/*  71:152 */     if (ok) {
/*  72:153 */       ok = buffer.charAt(i + protolength) == '/';
/*  73:    */     }
/*  74:155 */     if (!ok) {
/*  75:156 */       throw new ParseException("Not a valid protocol version: " + buffer.substring(indexFrom, indexTo));
/*  76:    */     }
/*  77:161 */     i += protolength + 1;
/*  78:    */     
/*  79:163 */     int period = buffer.indexOf(46, i, indexTo);
/*  80:164 */     if (period == -1) {
/*  81:165 */       throw new ParseException("Invalid protocol version number: " + buffer.substring(indexFrom, indexTo));
/*  82:    */     }
/*  83:    */     int major;
/*  84:    */     try
/*  85:    */     {
/*  86:171 */       major = Integer.parseInt(buffer.substringTrimmed(i, period));
/*  87:    */     }
/*  88:    */     catch (NumberFormatException e)
/*  89:    */     {
/*  90:173 */       throw new ParseException("Invalid protocol major version number: " + buffer.substring(indexFrom, indexTo));
/*  91:    */     }
/*  92:177 */     i = period + 1;
/*  93:    */     
/*  94:179 */     int blank = buffer.indexOf(32, i, indexTo);
/*  95:180 */     if (blank == -1) {
/*  96:181 */       blank = indexTo;
/*  97:    */     }
/*  98:    */     int minor;
/*  99:    */     try
/* 100:    */     {
/* 101:185 */       minor = Integer.parseInt(buffer.substringTrimmed(i, blank));
/* 102:    */     }
/* 103:    */     catch (NumberFormatException e)
/* 104:    */     {
/* 105:187 */       throw new ParseException("Invalid protocol minor version number: " + buffer.substring(indexFrom, indexTo));
/* 106:    */     }
/* 107:192 */     cursor.updatePos(blank);
/* 108:    */     
/* 109:194 */     return createProtocolVersion(major, minor);
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected ProtocolVersion createProtocolVersion(int major, int minor)
/* 113:    */   {
/* 114:209 */     return this.protocol.forVersion(major, minor);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean hasProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor)
/* 118:    */   {
/* 119:218 */     if (buffer == null) {
/* 120:219 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 121:    */     }
/* 122:221 */     if (cursor == null) {
/* 123:222 */       throw new IllegalArgumentException("Parser cursor may not be null");
/* 124:    */     }
/* 125:224 */     int index = cursor.getPos();
/* 126:    */     
/* 127:226 */     String protoname = this.protocol.getProtocol();
/* 128:227 */     int protolength = protoname.length();
/* 129:229 */     if (buffer.length() < protolength + 4) {
/* 130:230 */       return false;
/* 131:    */     }
/* 132:232 */     if (index < 0) {
/* 133:235 */       index = buffer.length() - 4 - protolength;
/* 134:236 */     } else if (index == 0) {
/* 135:238 */       while ((index < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(index)))) {
/* 136:240 */         index++;
/* 137:    */       }
/* 138:    */     }
/* 139:245 */     if (index + protolength + 4 > buffer.length()) {
/* 140:246 */       return false;
/* 141:    */     }
/* 142:250 */     boolean ok = true;
/* 143:251 */     for (int j = 0; (ok) && (j < protolength); j++) {
/* 144:252 */       ok = buffer.charAt(index + j) == protoname.charAt(j);
/* 145:    */     }
/* 146:254 */     if (ok) {
/* 147:255 */       ok = buffer.charAt(index + protolength) == '/';
/* 148:    */     }
/* 149:258 */     return ok;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static final RequestLine parseRequestLine(String value, LineParser parser)
/* 153:    */     throws ParseException
/* 154:    */   {
/* 155:268 */     if (value == null) {
/* 156:269 */       throw new IllegalArgumentException("Value to parse may not be null.");
/* 157:    */     }
/* 158:273 */     if (parser == null) {
/* 159:274 */       parser = DEFAULT;
/* 160:    */     }
/* 161:276 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 162:277 */     buffer.append(value);
/* 163:278 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 164:279 */     return parser.parseRequestLine(buffer, cursor);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public RequestLine parseRequestLine(CharArrayBuffer buffer, ParserCursor cursor)
/* 168:    */     throws ParseException
/* 169:    */   {
/* 170:296 */     if (buffer == null) {
/* 171:297 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 172:    */     }
/* 173:299 */     if (cursor == null) {
/* 174:300 */       throw new IllegalArgumentException("Parser cursor may not be null");
/* 175:    */     }
/* 176:303 */     int indexFrom = cursor.getPos();
/* 177:304 */     int indexTo = cursor.getUpperBound();
/* 178:    */     try
/* 179:    */     {
/* 180:307 */       skipWhitespace(buffer, cursor);
/* 181:308 */       int i = cursor.getPos();
/* 182:    */       
/* 183:310 */       int blank = buffer.indexOf(32, i, indexTo);
/* 184:311 */       if (blank < 0) {
/* 185:312 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/* 186:    */       }
/* 187:315 */       String method = buffer.substringTrimmed(i, blank);
/* 188:316 */       cursor.updatePos(blank);
/* 189:    */       
/* 190:318 */       skipWhitespace(buffer, cursor);
/* 191:319 */       i = cursor.getPos();
/* 192:    */       
/* 193:321 */       blank = buffer.indexOf(32, i, indexTo);
/* 194:322 */       if (blank < 0) {
/* 195:323 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/* 196:    */       }
/* 197:326 */       String uri = buffer.substringTrimmed(i, blank);
/* 198:327 */       cursor.updatePos(blank);
/* 199:    */       
/* 200:329 */       ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
/* 201:    */       
/* 202:331 */       skipWhitespace(buffer, cursor);
/* 203:332 */       if (!cursor.atEnd()) {
/* 204:333 */         throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/* 205:    */       }
/* 206:337 */       return createRequestLine(method, uri, ver);
/* 207:    */     }
/* 208:    */     catch (IndexOutOfBoundsException e)
/* 209:    */     {
/* 210:339 */       throw new ParseException("Invalid request line: " + buffer.substring(indexFrom, indexTo));
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected RequestLine createRequestLine(String method, String uri, ProtocolVersion ver)
/* 215:    */   {
/* 216:358 */     return new BasicRequestLine(method, uri, ver);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static final StatusLine parseStatusLine(String value, LineParser parser)
/* 220:    */     throws ParseException
/* 221:    */   {
/* 222:368 */     if (value == null) {
/* 223:369 */       throw new IllegalArgumentException("Value to parse may not be null.");
/* 224:    */     }
/* 225:373 */     if (parser == null) {
/* 226:374 */       parser = DEFAULT;
/* 227:    */     }
/* 228:376 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 229:377 */     buffer.append(value);
/* 230:378 */     ParserCursor cursor = new ParserCursor(0, value.length());
/* 231:379 */     return parser.parseStatusLine(buffer, cursor);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public StatusLine parseStatusLine(CharArrayBuffer buffer, ParserCursor cursor)
/* 235:    */     throws ParseException
/* 236:    */   {
/* 237:388 */     if (buffer == null) {
/* 238:389 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 239:    */     }
/* 240:391 */     if (cursor == null) {
/* 241:392 */       throw new IllegalArgumentException("Parser cursor may not be null");
/* 242:    */     }
/* 243:395 */     int indexFrom = cursor.getPos();
/* 244:396 */     int indexTo = cursor.getUpperBound();
/* 245:    */     try
/* 246:    */     {
/* 247:400 */       ProtocolVersion ver = parseProtocolVersion(buffer, cursor);
/* 248:    */       
/* 249:    */ 
/* 250:403 */       skipWhitespace(buffer, cursor);
/* 251:404 */       int i = cursor.getPos();
/* 252:    */       
/* 253:406 */       int blank = buffer.indexOf(32, i, indexTo);
/* 254:407 */       if (blank < 0) {
/* 255:408 */         blank = indexTo;
/* 256:    */       }
/* 257:410 */       int statusCode = 0;
/* 258:411 */       String s = buffer.substringTrimmed(i, blank);
/* 259:412 */       for (int j = 0; j < s.length(); j++) {
/* 260:413 */         if (!Character.isDigit(s.charAt(j))) {
/* 261:414 */           throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
/* 262:    */         }
/* 263:    */       }
/* 264:    */       try
/* 265:    */       {
/* 266:420 */         statusCode = Integer.parseInt(s);
/* 267:    */       }
/* 268:    */       catch (NumberFormatException e)
/* 269:    */       {
/* 270:422 */         throw new ParseException("Status line contains invalid status code: " + buffer.substring(indexFrom, indexTo));
/* 271:    */       }
/* 272:427 */       i = blank;
/* 273:428 */       String reasonPhrase = null;
/* 274:429 */       if (i < indexTo) {
/* 275:430 */         reasonPhrase = buffer.substringTrimmed(i, indexTo);
/* 276:    */       } else {
/* 277:432 */         reasonPhrase = "";
/* 278:    */       }
/* 279:434 */       return createStatusLine(ver, statusCode, reasonPhrase);
/* 280:    */     }
/* 281:    */     catch (IndexOutOfBoundsException e)
/* 282:    */     {
/* 283:437 */       throw new ParseException("Invalid status line: " + buffer.substring(indexFrom, indexTo));
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected StatusLine createStatusLine(ProtocolVersion ver, int status, String reason)
/* 288:    */   {
/* 289:456 */     return new BasicStatusLine(ver, status, reason);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static final Header parseHeader(String value, LineParser parser)
/* 293:    */     throws ParseException
/* 294:    */   {
/* 295:466 */     if (value == null) {
/* 296:467 */       throw new IllegalArgumentException("Value to parse may not be null");
/* 297:    */     }
/* 298:471 */     if (parser == null) {
/* 299:472 */       parser = DEFAULT;
/* 300:    */     }
/* 301:474 */     CharArrayBuffer buffer = new CharArrayBuffer(value.length());
/* 302:475 */     buffer.append(value);
/* 303:476 */     return parser.parseHeader(buffer);
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Header parseHeader(CharArrayBuffer buffer)
/* 307:    */     throws ParseException
/* 308:    */   {
/* 309:485 */     return new BufferedHeader(buffer);
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected void skipWhitespace(CharArrayBuffer buffer, ParserCursor cursor)
/* 313:    */   {
/* 314:493 */     int pos = cursor.getPos();
/* 315:494 */     int indexTo = cursor.getUpperBound();
/* 316:495 */     while ((pos < indexTo) && (HTTP.isWhitespace(buffer.charAt(pos)))) {
/* 317:497 */       pos++;
/* 318:    */     }
/* 319:499 */     cursor.updatePos(pos);
/* 320:    */   }
/* 321:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicLineParser
 * JD-Core Version:    0.7.0.1
 */