/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ 
/*   8:    */ public class BasicFormatterImpl
/*   9:    */   implements Formatter
/*  10:    */ {
/*  11: 42 */   private static final Set<String> BEGIN_CLAUSES = new HashSet();
/*  12: 43 */   private static final Set<String> END_CLAUSES = new HashSet();
/*  13: 44 */   private static final Set<String> LOGICAL = new HashSet();
/*  14: 45 */   private static final Set<String> QUANTIFIERS = new HashSet();
/*  15: 46 */   private static final Set<String> DML = new HashSet();
/*  16: 47 */   private static final Set<String> MISC = new HashSet();
/*  17:    */   static final String indentString = "    ";
/*  18:    */   static final String initial = "\n    ";
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22: 50 */     BEGIN_CLAUSES.add("left");
/*  23: 51 */     BEGIN_CLAUSES.add("right");
/*  24: 52 */     BEGIN_CLAUSES.add("inner");
/*  25: 53 */     BEGIN_CLAUSES.add("outer");
/*  26: 54 */     BEGIN_CLAUSES.add("group");
/*  27: 55 */     BEGIN_CLAUSES.add("order");
/*  28:    */     
/*  29: 57 */     END_CLAUSES.add("where");
/*  30: 58 */     END_CLAUSES.add("set");
/*  31: 59 */     END_CLAUSES.add("having");
/*  32: 60 */     END_CLAUSES.add("join");
/*  33: 61 */     END_CLAUSES.add("from");
/*  34: 62 */     END_CLAUSES.add("by");
/*  35: 63 */     END_CLAUSES.add("join");
/*  36: 64 */     END_CLAUSES.add("into");
/*  37: 65 */     END_CLAUSES.add("union");
/*  38:    */     
/*  39: 67 */     LOGICAL.add("and");
/*  40: 68 */     LOGICAL.add("or");
/*  41: 69 */     LOGICAL.add("when");
/*  42: 70 */     LOGICAL.add("else");
/*  43: 71 */     LOGICAL.add("end");
/*  44:    */     
/*  45: 73 */     QUANTIFIERS.add("in");
/*  46: 74 */     QUANTIFIERS.add("all");
/*  47: 75 */     QUANTIFIERS.add("exists");
/*  48: 76 */     QUANTIFIERS.add("some");
/*  49: 77 */     QUANTIFIERS.add("any");
/*  50:    */     
/*  51: 79 */     DML.add("insert");
/*  52: 80 */     DML.add("update");
/*  53: 81 */     DML.add("delete");
/*  54:    */     
/*  55: 83 */     MISC.add("select");
/*  56: 84 */     MISC.add("on");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String format(String source)
/*  60:    */   {
/*  61: 91 */     return new FormatProcess(source).perform();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private static class FormatProcess
/*  65:    */   {
/*  66: 95 */     boolean beginLine = true;
/*  67: 96 */     boolean afterBeginBeforeEnd = false;
/*  68: 97 */     boolean afterByOrSetOrFromOrSelect = false;
/*  69: 98 */     boolean afterValues = false;
/*  70: 99 */     boolean afterOn = false;
/*  71:100 */     boolean afterBetween = false;
/*  72:101 */     boolean afterInsert = false;
/*  73:102 */     int inFunction = 0;
/*  74:103 */     int parensSinceSelect = 0;
/*  75:104 */     private LinkedList<Integer> parenCounts = new LinkedList();
/*  76:105 */     private LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList();
/*  77:107 */     int indent = 1;
/*  78:109 */     StringBuffer result = new StringBuffer();
/*  79:    */     StringTokenizer tokens;
/*  80:    */     String lastToken;
/*  81:    */     String token;
/*  82:    */     String lcToken;
/*  83:    */     
/*  84:    */     public FormatProcess(String sql)
/*  85:    */     {
/*  86:116 */       this.tokens = new StringTokenizer(sql, "()+*/-=<>'`\"[], \n\r\f\t", true);
/*  87:    */     }
/*  88:    */     
/*  89:    */     public String perform()
/*  90:    */     {
/*  91:125 */       this.result.append("\n    ");
/*  92:127 */       while (this.tokens.hasMoreTokens())
/*  93:    */       {
/*  94:128 */         this.token = this.tokens.nextToken();
/*  95:129 */         this.lcToken = this.token.toLowerCase();
/*  96:131 */         if ("'".equals(this.token))
/*  97:    */         {
/*  98:    */           String t;
/*  99:    */           do
/* 100:    */           {
/* 101:134 */             t = this.tokens.nextToken();
/* 102:135 */             this.token += t;
/* 103:137 */           } while ((!"'".equals(t)) && (this.tokens.hasMoreTokens()));
/* 104:    */         }
/* 105:139 */         else if ("\"".equals(this.token))
/* 106:    */         {
/* 107:    */           String t;
/* 108:    */           do
/* 109:    */           {
/* 110:142 */             t = this.tokens.nextToken();
/* 111:143 */             this.token += t;
/* 112:145 */           } while (!"\"".equals(t));
/* 113:    */         }
/* 114:148 */         if ((this.afterByOrSetOrFromOrSelect) && (",".equals(this.token)))
/* 115:    */         {
/* 116:149 */           commaAfterByOrFromOrSelect();
/* 117:    */         }
/* 118:151 */         else if ((this.afterOn) && (",".equals(this.token)))
/* 119:    */         {
/* 120:152 */           commaAfterOn();
/* 121:    */         }
/* 122:155 */         else if ("(".equals(this.token))
/* 123:    */         {
/* 124:156 */           openParen();
/* 125:    */         }
/* 126:158 */         else if (")".equals(this.token))
/* 127:    */         {
/* 128:159 */           closeParen();
/* 129:    */         }
/* 130:162 */         else if (BasicFormatterImpl.BEGIN_CLAUSES.contains(this.lcToken))
/* 131:    */         {
/* 132:163 */           beginNewClause();
/* 133:    */         }
/* 134:166 */         else if (BasicFormatterImpl.END_CLAUSES.contains(this.lcToken))
/* 135:    */         {
/* 136:167 */           endNewClause();
/* 137:    */         }
/* 138:170 */         else if ("select".equals(this.lcToken))
/* 139:    */         {
/* 140:171 */           select();
/* 141:    */         }
/* 142:174 */         else if (BasicFormatterImpl.DML.contains(this.lcToken))
/* 143:    */         {
/* 144:175 */           updateOrInsertOrDelete();
/* 145:    */         }
/* 146:178 */         else if ("values".equals(this.lcToken))
/* 147:    */         {
/* 148:179 */           values();
/* 149:    */         }
/* 150:182 */         else if ("on".equals(this.lcToken))
/* 151:    */         {
/* 152:183 */           on();
/* 153:    */         }
/* 154:186 */         else if ((this.afterBetween) && (this.lcToken.equals("and")))
/* 155:    */         {
/* 156:187 */           misc();
/* 157:188 */           this.afterBetween = false;
/* 158:    */         }
/* 159:191 */         else if (BasicFormatterImpl.LOGICAL.contains(this.lcToken))
/* 160:    */         {
/* 161:192 */           logical();
/* 162:    */         }
/* 163:195 */         else if (isWhitespace(this.token))
/* 164:    */         {
/* 165:196 */           white();
/* 166:    */         }
/* 167:    */         else
/* 168:    */         {
/* 169:200 */           misc();
/* 170:    */         }
/* 171:203 */         if (!isWhitespace(this.token)) {
/* 172:204 */           this.lastToken = this.lcToken;
/* 173:    */         }
/* 174:    */       }
/* 175:208 */       return this.result.toString();
/* 176:    */     }
/* 177:    */     
/* 178:    */     private void commaAfterOn()
/* 179:    */     {
/* 180:212 */       out();
/* 181:213 */       this.indent -= 1;
/* 182:214 */       newline();
/* 183:215 */       this.afterOn = false;
/* 184:216 */       this.afterByOrSetOrFromOrSelect = true;
/* 185:    */     }
/* 186:    */     
/* 187:    */     private void commaAfterByOrFromOrSelect()
/* 188:    */     {
/* 189:220 */       out();
/* 190:221 */       newline();
/* 191:    */     }
/* 192:    */     
/* 193:    */     private void logical()
/* 194:    */     {
/* 195:225 */       if ("end".equals(this.lcToken)) {
/* 196:226 */         this.indent -= 1;
/* 197:    */       }
/* 198:228 */       newline();
/* 199:229 */       out();
/* 200:230 */       this.beginLine = false;
/* 201:    */     }
/* 202:    */     
/* 203:    */     private void on()
/* 204:    */     {
/* 205:234 */       this.indent += 1;
/* 206:235 */       this.afterOn = true;
/* 207:236 */       newline();
/* 208:237 */       out();
/* 209:238 */       this.beginLine = false;
/* 210:    */     }
/* 211:    */     
/* 212:    */     private void misc()
/* 213:    */     {
/* 214:242 */       out();
/* 215:243 */       if ("between".equals(this.lcToken)) {
/* 216:244 */         this.afterBetween = true;
/* 217:    */       }
/* 218:246 */       if (this.afterInsert)
/* 219:    */       {
/* 220:247 */         newline();
/* 221:248 */         this.afterInsert = false;
/* 222:    */       }
/* 223:    */       else
/* 224:    */       {
/* 225:251 */         this.beginLine = false;
/* 226:252 */         if ("case".equals(this.lcToken)) {
/* 227:253 */           this.indent += 1;
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:    */     
/* 232:    */     private void white()
/* 233:    */     {
/* 234:259 */       if (!this.beginLine) {
/* 235:260 */         this.result.append(" ");
/* 236:    */       }
/* 237:    */     }
/* 238:    */     
/* 239:    */     private void updateOrInsertOrDelete()
/* 240:    */     {
/* 241:265 */       out();
/* 242:266 */       this.indent += 1;
/* 243:267 */       this.beginLine = false;
/* 244:268 */       if ("update".equals(this.lcToken)) {
/* 245:269 */         newline();
/* 246:    */       }
/* 247:271 */       if ("insert".equals(this.lcToken)) {
/* 248:272 */         this.afterInsert = true;
/* 249:    */       }
/* 250:    */     }
/* 251:    */     
/* 252:    */     private void select()
/* 253:    */     {
/* 254:278 */       out();
/* 255:279 */       this.indent += 1;
/* 256:280 */       newline();
/* 257:281 */       this.parenCounts.addLast(Integer.valueOf(this.parensSinceSelect));
/* 258:282 */       this.afterByOrFromOrSelects.addLast(Boolean.valueOf(this.afterByOrSetOrFromOrSelect));
/* 259:283 */       this.parensSinceSelect = 0;
/* 260:284 */       this.afterByOrSetOrFromOrSelect = true;
/* 261:    */     }
/* 262:    */     
/* 263:    */     private void out()
/* 264:    */     {
/* 265:288 */       this.result.append(this.token);
/* 266:    */     }
/* 267:    */     
/* 268:    */     private void endNewClause()
/* 269:    */     {
/* 270:292 */       if (!this.afterBeginBeforeEnd)
/* 271:    */       {
/* 272:293 */         this.indent -= 1;
/* 273:294 */         if (this.afterOn)
/* 274:    */         {
/* 275:295 */           this.indent -= 1;
/* 276:296 */           this.afterOn = false;
/* 277:    */         }
/* 278:298 */         newline();
/* 279:    */       }
/* 280:300 */       out();
/* 281:301 */       if (!"union".equals(this.lcToken)) {
/* 282:302 */         this.indent += 1;
/* 283:    */       }
/* 284:304 */       newline();
/* 285:305 */       this.afterBeginBeforeEnd = false;
/* 286:306 */       this.afterByOrSetOrFromOrSelect = (("by".equals(this.lcToken)) || ("set".equals(this.lcToken)) || ("from".equals(this.lcToken)));
/* 287:    */     }
/* 288:    */     
/* 289:    */     private void beginNewClause()
/* 290:    */     {
/* 291:312 */       if (!this.afterBeginBeforeEnd)
/* 292:    */       {
/* 293:313 */         if (this.afterOn)
/* 294:    */         {
/* 295:314 */           this.indent -= 1;
/* 296:315 */           this.afterOn = false;
/* 297:    */         }
/* 298:317 */         this.indent -= 1;
/* 299:318 */         newline();
/* 300:    */       }
/* 301:320 */       out();
/* 302:321 */       this.beginLine = false;
/* 303:322 */       this.afterBeginBeforeEnd = true;
/* 304:    */     }
/* 305:    */     
/* 306:    */     private void values()
/* 307:    */     {
/* 308:326 */       this.indent -= 1;
/* 309:327 */       newline();
/* 310:328 */       out();
/* 311:329 */       this.indent += 1;
/* 312:330 */       newline();
/* 313:331 */       this.afterValues = true;
/* 314:    */     }
/* 315:    */     
/* 316:    */     private void closeParen()
/* 317:    */     {
/* 318:336 */       this.parensSinceSelect -= 1;
/* 319:337 */       if (this.parensSinceSelect < 0)
/* 320:    */       {
/* 321:338 */         this.indent -= 1;
/* 322:339 */         this.parensSinceSelect = ((Integer)this.parenCounts.removeLast()).intValue();
/* 323:340 */         this.afterByOrSetOrFromOrSelect = ((Boolean)this.afterByOrFromOrSelects.removeLast()).booleanValue();
/* 324:    */       }
/* 325:342 */       if (this.inFunction > 0)
/* 326:    */       {
/* 327:343 */         this.inFunction -= 1;
/* 328:344 */         out();
/* 329:    */       }
/* 330:    */       else
/* 331:    */       {
/* 332:347 */         if (!this.afterByOrSetOrFromOrSelect)
/* 333:    */         {
/* 334:348 */           this.indent -= 1;
/* 335:349 */           newline();
/* 336:    */         }
/* 337:351 */         out();
/* 338:    */       }
/* 339:353 */       this.beginLine = false;
/* 340:    */     }
/* 341:    */     
/* 342:    */     private void openParen()
/* 343:    */     {
/* 344:357 */       if ((isFunctionName(this.lastToken)) || (this.inFunction > 0)) {
/* 345:358 */         this.inFunction += 1;
/* 346:    */       }
/* 347:360 */       this.beginLine = false;
/* 348:361 */       if (this.inFunction > 0)
/* 349:    */       {
/* 350:362 */         out();
/* 351:    */       }
/* 352:    */       else
/* 353:    */       {
/* 354:365 */         out();
/* 355:366 */         if (!this.afterByOrSetOrFromOrSelect)
/* 356:    */         {
/* 357:367 */           this.indent += 1;
/* 358:368 */           newline();
/* 359:369 */           this.beginLine = true;
/* 360:    */         }
/* 361:    */       }
/* 362:372 */       this.parensSinceSelect += 1;
/* 363:    */     }
/* 364:    */     
/* 365:    */     private static boolean isFunctionName(String tok)
/* 366:    */     {
/* 367:376 */       char begin = tok.charAt(0);
/* 368:377 */       boolean isIdentifier = (Character.isJavaIdentifierStart(begin)) || ('"' == begin);
/* 369:378 */       return (isIdentifier) && (!BasicFormatterImpl.LOGICAL.contains(tok)) && (!BasicFormatterImpl.END_CLAUSES.contains(tok)) && (!BasicFormatterImpl.QUANTIFIERS.contains(tok)) && (!BasicFormatterImpl.DML.contains(tok)) && (!BasicFormatterImpl.MISC.contains(tok));
/* 370:    */     }
/* 371:    */     
/* 372:    */     private static boolean isWhitespace(String token)
/* 373:    */     {
/* 374:387 */       return " \n\r\f\t".indexOf(token) >= 0;
/* 375:    */     }
/* 376:    */     
/* 377:    */     private void newline()
/* 378:    */     {
/* 379:391 */       this.result.append("\n");
/* 380:392 */       for (int i = 0; i < this.indent; i++) {
/* 381:393 */         this.result.append("    ");
/* 382:    */       }
/* 383:395 */       this.beginLine = true;
/* 384:    */     }
/* 385:    */   }
/* 386:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.BasicFormatterImpl
 * JD-Core Version:    0.7.0.1
 */