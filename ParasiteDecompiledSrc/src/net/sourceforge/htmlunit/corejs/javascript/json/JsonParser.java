/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.json;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   8:    */ 
/*   9:    */ public class JsonParser
/*  10:    */ {
/*  11:    */   private Context cx;
/*  12:    */   private Scriptable scope;
/*  13:    */   private int pos;
/*  14:    */   private int length;
/*  15:    */   private String src;
/*  16:    */   
/*  17:    */   public JsonParser(Context cx, Scriptable scope)
/*  18:    */   {
/*  19: 67 */     this.cx = cx;
/*  20: 68 */     this.scope = scope;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public synchronized Object parseValue(String json)
/*  24:    */     throws JsonParser.ParseException
/*  25:    */   {
/*  26: 72 */     if (json == null) {
/*  27: 73 */       throw new ParseException("Input string may not be null");
/*  28:    */     }
/*  29: 75 */     this.pos = 0;
/*  30: 76 */     this.length = json.length();
/*  31: 77 */     this.src = json;
/*  32: 78 */     Object value = readValue();
/*  33: 79 */     consumeWhitespace();
/*  34: 80 */     if (this.pos < this.length) {
/*  35: 81 */       throw new ParseException("Expected end of stream at char " + this.pos);
/*  36:    */     }
/*  37: 83 */     return value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private Object readValue()
/*  41:    */     throws JsonParser.ParseException
/*  42:    */   {
/*  43: 87 */     consumeWhitespace();
/*  44: 88 */     if (this.pos < this.length)
/*  45:    */     {
/*  46: 89 */       char c = this.src.charAt(this.pos++);
/*  47: 90 */       switch (c)
/*  48:    */       {
/*  49:    */       case '{': 
/*  50: 92 */         return readObject();
/*  51:    */       case '[': 
/*  52: 94 */         return readArray();
/*  53:    */       case 't': 
/*  54: 96 */         return readTrue();
/*  55:    */       case 'f': 
/*  56: 98 */         return readFalse();
/*  57:    */       case '"': 
/*  58:100 */         return readString();
/*  59:    */       case 'n': 
/*  60:102 */         return readNull();
/*  61:    */       case '-': 
/*  62:    */       case '0': 
/*  63:    */       case '1': 
/*  64:    */       case '2': 
/*  65:    */       case '3': 
/*  66:    */       case '4': 
/*  67:    */       case '5': 
/*  68:    */       case '6': 
/*  69:    */       case '7': 
/*  70:    */       case '8': 
/*  71:    */       case '9': 
/*  72:114 */         return readNumber(c);
/*  73:    */       }
/*  74:116 */       throw new ParseException("Unexpected token: " + c);
/*  75:    */     }
/*  76:119 */     throw new ParseException("Empty JSON string");
/*  77:    */   }
/*  78:    */   
/*  79:    */   private Object readObject()
/*  80:    */     throws JsonParser.ParseException
/*  81:    */   {
/*  82:123 */     Scriptable object = this.cx.newObject(this.scope);
/*  83:    */     
/*  84:    */ 
/*  85:126 */     boolean needsComma = false;
/*  86:127 */     consumeWhitespace();
/*  87:128 */     while (this.pos < this.length)
/*  88:    */     {
/*  89:129 */       char c = this.src.charAt(this.pos++);
/*  90:130 */       switch (c)
/*  91:    */       {
/*  92:    */       case '}': 
/*  93:132 */         return object;
/*  94:    */       case ',': 
/*  95:134 */         if (!needsComma) {
/*  96:135 */           throw new ParseException("Unexpected comma in object literal");
/*  97:    */         }
/*  98:137 */         needsComma = false;
/*  99:138 */         break;
/* 100:    */       case '"': 
/* 101:140 */         if (needsComma) {
/* 102:141 */           throw new ParseException("Missing comma in object literal");
/* 103:    */         }
/* 104:143 */         String id = readString();
/* 105:144 */         consume(':');
/* 106:145 */         Object value = readValue();
/* 107:    */         
/* 108:147 */         long index = ScriptRuntime.indexFromString(id);
/* 109:148 */         if (index < 0L) {
/* 110:149 */           object.put(id, object, value);
/* 111:    */         } else {
/* 112:151 */           object.put((int)index, object, value);
/* 113:    */         }
/* 114:154 */         needsComma = true;
/* 115:155 */         break;
/* 116:    */       default: 
/* 117:157 */         throw new ParseException("Unexpected token in object literal");
/* 118:    */       }
/* 119:159 */       consumeWhitespace();
/* 120:    */     }
/* 121:161 */     throw new ParseException("Unterminated object literal");
/* 122:    */   }
/* 123:    */   
/* 124:    */   private Object readArray()
/* 125:    */     throws JsonParser.ParseException
/* 126:    */   {
/* 127:165 */     List<Object> list = new ArrayList();
/* 128:166 */     boolean needsComma = false;
/* 129:167 */     consumeWhitespace();
/* 130:168 */     while (this.pos < this.length)
/* 131:    */     {
/* 132:169 */       char c = this.src.charAt(this.pos);
/* 133:170 */       switch (c)
/* 134:    */       {
/* 135:    */       case ']': 
/* 136:172 */         this.pos += 1;
/* 137:173 */         return this.cx.newArray(this.scope, list.toArray());
/* 138:    */       case ',': 
/* 139:175 */         if (!needsComma) {
/* 140:176 */           throw new ParseException("Unexpected comma in array literal");
/* 141:    */         }
/* 142:178 */         needsComma = false;
/* 143:179 */         this.pos += 1;
/* 144:180 */         break;
/* 145:    */       default: 
/* 146:182 */         if (needsComma) {
/* 147:183 */           throw new ParseException("Missing comma in array literal");
/* 148:    */         }
/* 149:185 */         list.add(readValue());
/* 150:186 */         needsComma = true;
/* 151:    */       }
/* 152:188 */       consumeWhitespace();
/* 153:    */     }
/* 154:190 */     throw new ParseException("Unterminated array literal");
/* 155:    */   }
/* 156:    */   
/* 157:    */   private String readString()
/* 158:    */     throws JsonParser.ParseException
/* 159:    */   {
/* 160:194 */     StringBuilder b = new StringBuilder();
/* 161:195 */     while (this.pos < this.length)
/* 162:    */     {
/* 163:196 */       char c = this.src.charAt(this.pos++);
/* 164:197 */       if (c <= '\037') {
/* 165:198 */         throw new ParseException("String contains control character");
/* 166:    */       }
/* 167:200 */       switch (c)
/* 168:    */       {
/* 169:    */       case '\\': 
/* 170:202 */         if (this.pos >= this.length) {
/* 171:203 */           throw new ParseException("Unterminated string");
/* 172:    */         }
/* 173:205 */         c = this.src.charAt(this.pos++);
/* 174:206 */         switch (c)
/* 175:    */         {
/* 176:    */         case '"': 
/* 177:208 */           b.append('"');
/* 178:209 */           break;
/* 179:    */         case '\\': 
/* 180:211 */           b.append('\\');
/* 181:212 */           break;
/* 182:    */         case '/': 
/* 183:214 */           b.append('/');
/* 184:215 */           break;
/* 185:    */         case 'b': 
/* 186:217 */           b.append('\b');
/* 187:218 */           break;
/* 188:    */         case 'f': 
/* 189:220 */           b.append('\f');
/* 190:221 */           break;
/* 191:    */         case 'n': 
/* 192:223 */           b.append('\n');
/* 193:224 */           break;
/* 194:    */         case 'r': 
/* 195:226 */           b.append('\r');
/* 196:227 */           break;
/* 197:    */         case 't': 
/* 198:229 */           b.append('\t');
/* 199:230 */           break;
/* 200:    */         case 'u': 
/* 201:232 */           if (this.length - this.pos < 5) {
/* 202:233 */             throw new ParseException("Invalid character code: \\u" + this.src.substring(this.pos));
/* 203:    */           }
/* 204:    */           try
/* 205:    */           {
/* 206:236 */             b.append((char)Integer.parseInt(this.src.substring(this.pos, this.pos + 4), 16));
/* 207:237 */             this.pos += 4;
/* 208:    */           }
/* 209:    */           catch (NumberFormatException nfx)
/* 210:    */           {
/* 211:239 */             throw new ParseException("Invalid character code: " + this.src.substring(this.pos, this.pos + 4));
/* 212:    */           }
/* 213:    */         default: 
/* 214:243 */           throw new ParseException("Unexcpected character in string: '\\" + c + "'");
/* 215:    */         }
/* 216:    */         break;
/* 217:    */       case '"': 
/* 218:247 */         return b.toString();
/* 219:    */       default: 
/* 220:249 */         b.append(c);
/* 221:    */       }
/* 222:    */     }
/* 223:253 */     throw new ParseException("Unterminated string literal");
/* 224:    */   }
/* 225:    */   
/* 226:    */   private Number readNumber(char first)
/* 227:    */     throws JsonParser.ParseException
/* 228:    */   {
/* 229:257 */     StringBuilder b = new StringBuilder();
/* 230:258 */     b.append(first);
/* 231:259 */     while (this.pos < this.length)
/* 232:    */     {
/* 233:260 */       char c = this.src.charAt(this.pos);
/* 234:261 */       if ((!Character.isDigit(c)) && (c != '-') && (c != '+') && (c != '.') && (c != 'e') && (c != 'E')) {
/* 235:    */         break;
/* 236:    */       }
/* 237:269 */       this.pos += 1;
/* 238:270 */       b.append(c);
/* 239:    */     }
/* 240:272 */     String num = b.toString();
/* 241:273 */     int numLength = num.length();
/* 242:    */     try
/* 243:    */     {
/* 244:276 */       for (int i = 0; i < numLength; i++)
/* 245:    */       {
/* 246:277 */         char c = num.charAt(i);
/* 247:278 */         if (Character.isDigit(c))
/* 248:    */         {
/* 249:279 */           if ((c != '0') || (numLength <= i + 1) || (!Character.isDigit(num.charAt(i + 1)))) {
/* 250:    */             break;
/* 251:    */           }
/* 252:282 */           throw new ParseException("Unsupported number format: " + num);
/* 253:    */         }
/* 254:    */       }
/* 255:287 */       double dval = Double.parseDouble(num);
/* 256:288 */       int ival = (int)dval;
/* 257:289 */       if (ival == dval) {
/* 258:290 */         return Integer.valueOf(ival);
/* 259:    */       }
/* 260:292 */       return Double.valueOf(dval);
/* 261:    */     }
/* 262:    */     catch (NumberFormatException nfe)
/* 263:    */     {
/* 264:295 */       throw new ParseException("Unsupported number format: " + num);
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   private Boolean readTrue()
/* 269:    */     throws JsonParser.ParseException
/* 270:    */   {
/* 271:300 */     if ((this.length - this.pos < 3) || (this.src.charAt(this.pos) != 'r') || (this.src.charAt(this.pos + 1) != 'u') || (this.src.charAt(this.pos + 2) != 'e')) {
/* 272:304 */       throw new ParseException("Unexpected token: t");
/* 273:    */     }
/* 274:306 */     this.pos += 3;
/* 275:307 */     return Boolean.TRUE;
/* 276:    */   }
/* 277:    */   
/* 278:    */   private Boolean readFalse()
/* 279:    */     throws JsonParser.ParseException
/* 280:    */   {
/* 281:311 */     if ((this.length - this.pos < 4) || (this.src.charAt(this.pos) != 'a') || (this.src.charAt(this.pos + 1) != 'l') || (this.src.charAt(this.pos + 2) != 's') || (this.src.charAt(this.pos + 3) != 'e')) {
/* 282:316 */       throw new ParseException("Unexpected token: f");
/* 283:    */     }
/* 284:318 */     this.pos += 4;
/* 285:319 */     return Boolean.FALSE;
/* 286:    */   }
/* 287:    */   
/* 288:    */   private Object readNull()
/* 289:    */     throws JsonParser.ParseException
/* 290:    */   {
/* 291:323 */     if ((this.length - this.pos < 3) || (this.src.charAt(this.pos) != 'u') || (this.src.charAt(this.pos + 1) != 'l') || (this.src.charAt(this.pos + 2) != 'l')) {
/* 292:327 */       throw new ParseException("Unexpected token: n");
/* 293:    */     }
/* 294:329 */     this.pos += 3;
/* 295:330 */     return null;
/* 296:    */   }
/* 297:    */   
/* 298:    */   private void consumeWhitespace()
/* 299:    */   {
/* 300:334 */     while (this.pos < this.length)
/* 301:    */     {
/* 302:335 */       char c = this.src.charAt(this.pos);
/* 303:336 */       switch (c)
/* 304:    */       {
/* 305:    */       case '\t': 
/* 306:    */       case '\n': 
/* 307:    */       case '\r': 
/* 308:    */       case ' ': 
/* 309:341 */         this.pos += 1;
/* 310:342 */         break;
/* 311:    */       default: 
/* 312:344 */         return;
/* 313:    */       }
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   private void consume(char token)
/* 318:    */     throws JsonParser.ParseException
/* 319:    */   {
/* 320:350 */     consumeWhitespace();
/* 321:351 */     if (this.pos >= this.length) {
/* 322:352 */       throw new ParseException("Expected " + token + " but reached end of stream");
/* 323:    */     }
/* 324:354 */     char c = this.src.charAt(this.pos++);
/* 325:355 */     if (c == token) {
/* 326:356 */       return;
/* 327:    */     }
/* 328:358 */     throw new ParseException("Expected " + token + " found " + c);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static class ParseException
/* 332:    */     extends Exception
/* 333:    */   {
/* 334:    */     ParseException(String message)
/* 335:    */     {
/* 336:364 */       super();
/* 337:    */     }
/* 338:    */     
/* 339:    */     ParseException(Exception cause)
/* 340:    */     {
/* 341:368 */       super();
/* 342:    */     }
/* 343:    */   }
/* 344:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.json.JsonParser
 * JD-Core Version:    0.7.0.1
 */