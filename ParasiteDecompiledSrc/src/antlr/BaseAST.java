/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.ASTEnumeration;
/*   5:    */ import antlr.collections.impl.ASTEnumerator;
/*   6:    */ import antlr.collections.impl.Vector;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.io.Writer;
/*  10:    */ 
/*  11:    */ public abstract class BaseAST
/*  12:    */   implements AST, Serializable
/*  13:    */ {
/*  14:    */   protected BaseAST down;
/*  15:    */   protected BaseAST right;
/*  16: 48 */   private static boolean verboseStringConversion = false;
/*  17: 49 */   private static String[] tokenNames = null;
/*  18:    */   
/*  19:    */   public void addChild(AST paramAST)
/*  20:    */   {
/*  21: 53 */     if (paramAST == null) {
/*  22: 53 */       return;
/*  23:    */     }
/*  24: 54 */     BaseAST localBaseAST = this.down;
/*  25: 55 */     if (localBaseAST != null)
/*  26:    */     {
/*  27: 56 */       while (localBaseAST.right != null) {
/*  28: 57 */         localBaseAST = localBaseAST.right;
/*  29:    */       }
/*  30: 59 */       localBaseAST.right = ((BaseAST)paramAST);
/*  31:    */     }
/*  32:    */     else
/*  33:    */     {
/*  34: 62 */       this.down = ((BaseAST)paramAST);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getNumberOfChildren()
/*  39:    */   {
/*  40: 68 */     BaseAST localBaseAST = this.down;
/*  41: 69 */     int i = 0;
/*  42: 70 */     if (localBaseAST != null)
/*  43:    */     {
/*  44: 71 */       i = 1;
/*  45: 72 */       while (localBaseAST.right != null)
/*  46:    */       {
/*  47: 73 */         localBaseAST = localBaseAST.right;
/*  48: 74 */         i++;
/*  49:    */       }
/*  50: 76 */       return i;
/*  51:    */     }
/*  52: 78 */     return i;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static void doWorkForFindAll(AST paramAST1, Vector paramVector, AST paramAST2, boolean paramBoolean)
/*  56:    */   {
/*  57: 87 */     for (AST localAST = paramAST1; localAST != null; localAST = localAST.getNextSibling())
/*  58:    */     {
/*  59: 89 */       if (((paramBoolean) && (localAST.equalsTreePartial(paramAST2))) || ((!paramBoolean) && (localAST.equalsTree(paramAST2)))) {
/*  60: 91 */         paramVector.appendElement(localAST);
/*  61:    */       }
/*  62: 94 */       if (localAST.getFirstChild() != null) {
/*  63: 95 */         doWorkForFindAll(localAST.getFirstChild(), paramVector, paramAST2, paramBoolean);
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean equals(AST paramAST)
/*  69:    */   {
/*  70:102 */     if (paramAST == null) {
/*  71:102 */       return false;
/*  72:    */     }
/*  73:103 */     if (((getText() == null) && (paramAST.getText() != null)) || ((getText() != null) && (paramAST.getText() == null))) {
/*  74:106 */       return false;
/*  75:    */     }
/*  76:108 */     if ((getText() == null) && (paramAST.getText() == null)) {
/*  77:109 */       return getType() == paramAST.getType();
/*  78:    */     }
/*  79:111 */     return (getText().equals(paramAST.getText())) && (getType() == paramAST.getType());
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean equalsList(AST paramAST)
/*  83:    */   {
/*  84:122 */     if (paramAST == null) {
/*  85:123 */       return false;
/*  86:    */     }
/*  87:127 */     Object localObject = this;
/*  88:128 */     for (; (localObject != null) && (paramAST != null); paramAST = paramAST.getNextSibling())
/*  89:    */     {
/*  90:132 */       if (!((AST)localObject).equals(paramAST)) {
/*  91:133 */         return false;
/*  92:    */       }
/*  93:136 */       if (((AST)localObject).getFirstChild() != null)
/*  94:    */       {
/*  95:137 */         if (!((AST)localObject).getFirstChild().equalsList(paramAST.getFirstChild())) {
/*  96:138 */           return false;
/*  97:    */         }
/*  98:    */       }
/*  99:142 */       else if (paramAST.getFirstChild() != null) {
/* 100:143 */         return false;
/* 101:    */       }
/* 102:129 */       localObject = ((AST)localObject).getNextSibling();
/* 103:    */     }
/* 104:146 */     if ((localObject == null) && (paramAST == null)) {
/* 105:147 */       return true;
/* 106:    */     }
/* 107:150 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean equalsListPartial(AST paramAST)
/* 111:    */   {
/* 112:160 */     if (paramAST == null) {
/* 113:161 */       return true;
/* 114:    */     }
/* 115:165 */     Object localObject = this;
/* 116:166 */     for (; (localObject != null) && (paramAST != null); paramAST = paramAST.getNextSibling())
/* 117:    */     {
/* 118:169 */       if (!((AST)localObject).equals(paramAST)) {
/* 119:169 */         return false;
/* 120:    */       }
/* 121:171 */       if ((((AST)localObject).getFirstChild() != null) && 
/* 122:172 */         (!((AST)localObject).getFirstChild().equalsListPartial(paramAST.getFirstChild()))) {
/* 123:172 */         return false;
/* 124:    */       }
/* 125:167 */       localObject = ((AST)localObject).getNextSibling();
/* 126:    */     }
/* 127:175 */     if ((localObject == null) && (paramAST != null)) {
/* 128:177 */       return false;
/* 129:    */     }
/* 130:180 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean equalsTree(AST paramAST)
/* 134:    */   {
/* 135:188 */     if (!equals(paramAST)) {
/* 136:188 */       return false;
/* 137:    */     }
/* 138:190 */     if (getFirstChild() != null)
/* 139:    */     {
/* 140:191 */       if (!getFirstChild().equalsList(paramAST.getFirstChild())) {
/* 141:191 */         return false;
/* 142:    */       }
/* 143:    */     }
/* 144:194 */     else if (paramAST.getFirstChild() != null) {
/* 145:195 */       return false;
/* 146:    */     }
/* 147:197 */     return true;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean equalsTreePartial(AST paramAST)
/* 151:    */   {
/* 152:205 */     if (paramAST == null) {
/* 153:206 */       return true;
/* 154:    */     }
/* 155:210 */     if (!equals(paramAST)) {
/* 156:210 */       return false;
/* 157:    */     }
/* 158:212 */     if ((getFirstChild() != null) && 
/* 159:213 */       (!getFirstChild().equalsListPartial(paramAST.getFirstChild()))) {
/* 160:213 */       return false;
/* 161:    */     }
/* 162:215 */     return true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public ASTEnumeration findAll(AST paramAST)
/* 166:    */   {
/* 167:223 */     Vector localVector = new Vector(10);
/* 168:227 */     if (paramAST == null) {
/* 169:228 */       return null;
/* 170:    */     }
/* 171:231 */     doWorkForFindAll(this, localVector, paramAST, false);
/* 172:    */     
/* 173:233 */     return new ASTEnumerator(localVector);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public ASTEnumeration findAllPartial(AST paramAST)
/* 177:    */   {
/* 178:241 */     Vector localVector = new Vector(10);
/* 179:245 */     if (paramAST == null) {
/* 180:246 */       return null;
/* 181:    */     }
/* 182:249 */     doWorkForFindAll(this, localVector, paramAST, true);
/* 183:    */     
/* 184:251 */     return new ASTEnumerator(localVector);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public AST getFirstChild()
/* 188:    */   {
/* 189:256 */     return this.down;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public AST getNextSibling()
/* 193:    */   {
/* 194:261 */     return this.right;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getText()
/* 198:    */   {
/* 199:266 */     return "";
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getType()
/* 203:    */   {
/* 204:271 */     return 0;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getLine()
/* 208:    */   {
/* 209:275 */     return 0;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int getColumn()
/* 213:    */   {
/* 214:279 */     return 0;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public abstract void initialize(int paramInt, String paramString);
/* 218:    */   
/* 219:    */   public abstract void initialize(AST paramAST);
/* 220:    */   
/* 221:    */   public abstract void initialize(Token paramToken);
/* 222:    */   
/* 223:    */   public void removeChildren()
/* 224:    */   {
/* 225:290 */     this.down = null;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setFirstChild(AST paramAST)
/* 229:    */   {
/* 230:294 */     this.down = ((BaseAST)paramAST);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setNextSibling(AST paramAST)
/* 234:    */   {
/* 235:298 */     this.right = ((BaseAST)paramAST);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setText(String paramString) {}
/* 239:    */   
/* 240:    */   public void setType(int paramInt) {}
/* 241:    */   
/* 242:    */   public static void setVerboseStringConversion(boolean paramBoolean, String[] paramArrayOfString)
/* 243:    */   {
/* 244:310 */     verboseStringConversion = paramBoolean;
/* 245:311 */     tokenNames = paramArrayOfString;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static String[] getTokenNames()
/* 249:    */   {
/* 250:316 */     return tokenNames;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String toString()
/* 254:    */   {
/* 255:320 */     StringBuffer localStringBuffer = new StringBuffer();
/* 256:322 */     if ((verboseStringConversion) && (getText() != null) && (!getText().equalsIgnoreCase(tokenNames[getType()])) && (!getText().equalsIgnoreCase(StringUtils.stripFrontBack(tokenNames[getType()], "\"", "\""))))
/* 257:    */     {
/* 258:326 */       localStringBuffer.append('[');
/* 259:327 */       localStringBuffer.append(getText());
/* 260:328 */       localStringBuffer.append(",<");
/* 261:329 */       localStringBuffer.append(tokenNames[getType()]);
/* 262:330 */       localStringBuffer.append(">]");
/* 263:331 */       return localStringBuffer.toString();
/* 264:    */     }
/* 265:333 */     return getText();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String toStringList()
/* 269:    */   {
/* 270:338 */     BaseAST localBaseAST = this;
/* 271:339 */     String str = "";
/* 272:340 */     if (localBaseAST.getFirstChild() != null) {
/* 273:340 */       str = str + " (";
/* 274:    */     }
/* 275:341 */     str = str + " " + toString();
/* 276:342 */     if (localBaseAST.getFirstChild() != null) {
/* 277:343 */       str = str + ((BaseAST)localBaseAST.getFirstChild()).toStringList();
/* 278:    */     }
/* 279:345 */     if (localBaseAST.getFirstChild() != null) {
/* 280:345 */       str = str + " )";
/* 281:    */     }
/* 282:346 */     if (localBaseAST.getNextSibling() != null) {
/* 283:347 */       str = str + ((BaseAST)localBaseAST.getNextSibling()).toStringList();
/* 284:    */     }
/* 285:349 */     return str;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String toStringTree()
/* 289:    */   {
/* 290:353 */     BaseAST localBaseAST = this;
/* 291:354 */     String str = "";
/* 292:355 */     if (localBaseAST.getFirstChild() != null) {
/* 293:355 */       str = str + " (";
/* 294:    */     }
/* 295:356 */     str = str + " " + toString();
/* 296:357 */     if (localBaseAST.getFirstChild() != null) {
/* 297:358 */       str = str + ((BaseAST)localBaseAST.getFirstChild()).toStringList();
/* 298:    */     }
/* 299:360 */     if (localBaseAST.getFirstChild() != null) {
/* 300:360 */       str = str + " )";
/* 301:    */     }
/* 302:361 */     return str;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static String decode(String paramString)
/* 306:    */   {
/* 307:366 */     StringBuffer localStringBuffer = new StringBuffer();
/* 308:367 */     for (int i1 = 0; i1 < paramString.length(); i1++)
/* 309:    */     {
/* 310:368 */       char c = paramString.charAt(i1);
/* 311:369 */       if (c == '&')
/* 312:    */       {
/* 313:370 */         int i = paramString.charAt(i1 + 1);
/* 314:371 */         int j = paramString.charAt(i1 + 2);
/* 315:372 */         int k = paramString.charAt(i1 + 3);
/* 316:373 */         int m = paramString.charAt(i1 + 4);
/* 317:374 */         int n = paramString.charAt(i1 + 5);
/* 318:376 */         if ((i == 97) && (j == 109) && (k == 112) && (m == 59))
/* 319:    */         {
/* 320:377 */           localStringBuffer.append("&");
/* 321:378 */           i1 += 5;
/* 322:    */         }
/* 323:380 */         else if ((i == 108) && (j == 116) && (k == 59))
/* 324:    */         {
/* 325:381 */           localStringBuffer.append("<");
/* 326:382 */           i1 += 4;
/* 327:    */         }
/* 328:384 */         else if ((i == 103) && (j == 116) && (k == 59))
/* 329:    */         {
/* 330:385 */           localStringBuffer.append(">");
/* 331:386 */           i1 += 4;
/* 332:    */         }
/* 333:388 */         else if ((i == 113) && (j == 117) && (k == 111) && (m == 116) && (n == 59))
/* 334:    */         {
/* 335:390 */           localStringBuffer.append("\"");
/* 336:391 */           i1 += 6;
/* 337:    */         }
/* 338:393 */         else if ((i == 97) && (j == 112) && (k == 111) && (m == 115) && (n == 59))
/* 339:    */         {
/* 340:395 */           localStringBuffer.append("'");
/* 341:396 */           i1 += 6;
/* 342:    */         }
/* 343:    */         else
/* 344:    */         {
/* 345:399 */           localStringBuffer.append("&");
/* 346:    */         }
/* 347:    */       }
/* 348:    */       else
/* 349:    */       {
/* 350:402 */         localStringBuffer.append(c);
/* 351:    */       }
/* 352:    */     }
/* 353:404 */     return new String(localStringBuffer);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public static String encode(String paramString)
/* 357:    */   {
/* 358:409 */     StringBuffer localStringBuffer = new StringBuffer();
/* 359:410 */     for (int i = 0; i < paramString.length(); i++)
/* 360:    */     {
/* 361:411 */       char c = paramString.charAt(i);
/* 362:412 */       switch (c)
/* 363:    */       {
/* 364:    */       case '&': 
/* 365:415 */         localStringBuffer.append("&amp;");
/* 366:416 */         break;
/* 367:    */       case '<': 
/* 368:420 */         localStringBuffer.append("&lt;");
/* 369:421 */         break;
/* 370:    */       case '>': 
/* 371:425 */         localStringBuffer.append("&gt;");
/* 372:426 */         break;
/* 373:    */       case '"': 
/* 374:430 */         localStringBuffer.append("&quot;");
/* 375:431 */         break;
/* 376:    */       case '\'': 
/* 377:435 */         localStringBuffer.append("&apos;");
/* 378:436 */         break;
/* 379:    */       default: 
/* 380:440 */         localStringBuffer.append(c);
/* 381:    */       }
/* 382:    */     }
/* 383:445 */     return new String(localStringBuffer);
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void xmlSerializeNode(Writer paramWriter)
/* 387:    */     throws IOException
/* 388:    */   {
/* 389:450 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 390:451 */     localStringBuffer.append("<");
/* 391:452 */     localStringBuffer.append(getClass().getName() + " ");
/* 392:453 */     localStringBuffer.append("text=\"" + encode(getText()) + "\" type=\"" + getType() + "\"/>");
/* 393:    */     
/* 394:455 */     paramWriter.write(localStringBuffer.toString());
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void xmlSerializeRootOpen(Writer paramWriter)
/* 398:    */     throws IOException
/* 399:    */   {
/* 400:460 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 401:461 */     localStringBuffer.append("<");
/* 402:462 */     localStringBuffer.append(getClass().getName() + " ");
/* 403:463 */     localStringBuffer.append("text=\"" + encode(getText()) + "\" type=\"" + getType() + "\">\n");
/* 404:    */     
/* 405:465 */     paramWriter.write(localStringBuffer.toString());
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void xmlSerializeRootClose(Writer paramWriter)
/* 409:    */     throws IOException
/* 410:    */   {
/* 411:470 */     paramWriter.write("</" + getClass().getName() + ">\n");
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void xmlSerialize(Writer paramWriter)
/* 415:    */     throws IOException
/* 416:    */   {
/* 417:475 */     for (Object localObject = this; localObject != null; localObject = ((AST)localObject).getNextSibling()) {
/* 418:478 */       if (((AST)localObject).getFirstChild() == null)
/* 419:    */       {
/* 420:480 */         ((BaseAST)localObject).xmlSerializeNode(paramWriter);
/* 421:    */       }
/* 422:    */       else
/* 423:    */       {
/* 424:483 */         ((BaseAST)localObject).xmlSerializeRootOpen(paramWriter);
/* 425:    */         
/* 426:    */ 
/* 427:486 */         ((BaseAST)((AST)localObject).getFirstChild()).xmlSerialize(paramWriter);
/* 428:    */         
/* 429:    */ 
/* 430:489 */         ((BaseAST)localObject).xmlSerializeRootClose(paramWriter);
/* 431:    */       }
/* 432:    */     }
/* 433:    */   }
/* 434:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.BaseAST
 * JD-Core Version:    0.7.0.1
 */