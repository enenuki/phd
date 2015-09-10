/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import jxl.Cell;
/*   5:    */ import jxl.WorkbookSettings;
/*   6:    */ import jxl.biff.WorkbookMethods;
/*   7:    */ import jxl.common.Assert;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ 
/*  10:    */ class TokenFormulaParser
/*  11:    */   implements Parser
/*  12:    */ {
/*  13: 39 */   private static Logger logger = Logger.getLogger(TokenFormulaParser.class);
/*  14:    */   private byte[] tokenData;
/*  15:    */   private Cell relativeTo;
/*  16:    */   private int pos;
/*  17:    */   private ParseItem root;
/*  18:    */   private Stack tokenStack;
/*  19:    */   private ExternalSheet workbook;
/*  20:    */   private WorkbookMethods nameTable;
/*  21:    */   private WorkbookSettings settings;
/*  22:    */   private ParseContext parseContext;
/*  23:    */   
/*  24:    */   public TokenFormulaParser(byte[] data, Cell c, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws, ParseContext pc)
/*  25:    */   {
/*  26: 98 */     this.tokenData = data;
/*  27: 99 */     this.pos = 0;
/*  28:100 */     this.relativeTo = c;
/*  29:101 */     this.workbook = es;
/*  30:102 */     this.nameTable = nt;
/*  31:103 */     this.tokenStack = new Stack();
/*  32:104 */     this.settings = ws;
/*  33:105 */     this.parseContext = pc;
/*  34:    */     
/*  35:107 */     Assert.verify(this.nameTable != null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void parse()
/*  39:    */     throws FormulaException
/*  40:    */   {
/*  41:118 */     parseSubExpression(this.tokenData.length);
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:122 */     this.root = ((ParseItem)this.tokenStack.pop());
/*  46:    */     
/*  47:124 */     Assert.verify(this.tokenStack.empty());
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void parseSubExpression(int len)
/*  51:    */     throws FormulaException
/*  52:    */   {
/*  53:137 */     int tokenVal = 0;
/*  54:138 */     Token t = null;
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:142 */     Stack ifStack = new Stack();
/*  59:    */     
/*  60:    */ 
/*  61:145 */     int endpos = this.pos + len;
/*  62:147 */     while (this.pos < endpos)
/*  63:    */     {
/*  64:149 */       tokenVal = this.tokenData[this.pos];
/*  65:150 */       this.pos += 1;
/*  66:    */       
/*  67:152 */       t = Token.getToken(tokenVal);
/*  68:154 */       if (t == Token.UNKNOWN) {
/*  69:156 */         throw new FormulaException(FormulaException.UNRECOGNIZED_TOKEN, tokenVal);
/*  70:    */       }
/*  71:160 */       Assert.verify(t != Token.UNKNOWN);
/*  72:163 */       if (t == Token.REF)
/*  73:    */       {
/*  74:165 */         CellReference cr = new CellReference(this.relativeTo);
/*  75:166 */         this.pos += cr.read(this.tokenData, this.pos);
/*  76:167 */         this.tokenStack.push(cr);
/*  77:    */       }
/*  78:169 */       else if (t == Token.REFERR)
/*  79:    */       {
/*  80:171 */         CellReferenceError cr = new CellReferenceError();
/*  81:172 */         this.pos += cr.read(this.tokenData, this.pos);
/*  82:173 */         this.tokenStack.push(cr);
/*  83:    */       }
/*  84:175 */       else if (t == Token.ERR)
/*  85:    */       {
/*  86:177 */         ErrorConstant ec = new ErrorConstant();
/*  87:178 */         this.pos += ec.read(this.tokenData, this.pos);
/*  88:179 */         this.tokenStack.push(ec);
/*  89:    */       }
/*  90:181 */       else if (t == Token.REFV)
/*  91:    */       {
/*  92:183 */         SharedFormulaCellReference cr = new SharedFormulaCellReference(this.relativeTo);
/*  93:    */         
/*  94:185 */         this.pos += cr.read(this.tokenData, this.pos);
/*  95:186 */         this.tokenStack.push(cr);
/*  96:    */       }
/*  97:188 */       else if (t == Token.REF3D)
/*  98:    */       {
/*  99:190 */         CellReference3d cr = new CellReference3d(this.relativeTo, this.workbook);
/* 100:191 */         this.pos += cr.read(this.tokenData, this.pos);
/* 101:192 */         this.tokenStack.push(cr);
/* 102:    */       }
/* 103:194 */       else if (t == Token.AREA)
/* 104:    */       {
/* 105:196 */         Area a = new Area();
/* 106:197 */         this.pos += a.read(this.tokenData, this.pos);
/* 107:198 */         this.tokenStack.push(a);
/* 108:    */       }
/* 109:200 */       else if (t == Token.AREAV)
/* 110:    */       {
/* 111:202 */         SharedFormulaArea a = new SharedFormulaArea(this.relativeTo);
/* 112:203 */         this.pos += a.read(this.tokenData, this.pos);
/* 113:204 */         this.tokenStack.push(a);
/* 114:    */       }
/* 115:206 */       else if (t == Token.AREA3D)
/* 116:    */       {
/* 117:208 */         Area3d a = new Area3d(this.workbook);
/* 118:209 */         this.pos += a.read(this.tokenData, this.pos);
/* 119:210 */         this.tokenStack.push(a);
/* 120:    */       }
/* 121:212 */       else if (t == Token.NAME)
/* 122:    */       {
/* 123:214 */         Name n = new Name();
/* 124:215 */         this.pos += n.read(this.tokenData, this.pos);
/* 125:216 */         n.setParseContext(this.parseContext);
/* 126:217 */         this.tokenStack.push(n);
/* 127:    */       }
/* 128:219 */       else if (t == Token.NAMED_RANGE)
/* 129:    */       {
/* 130:221 */         NameRange nr = new NameRange(this.nameTable);
/* 131:222 */         this.pos += nr.read(this.tokenData, this.pos);
/* 132:223 */         nr.setParseContext(this.parseContext);
/* 133:224 */         this.tokenStack.push(nr);
/* 134:    */       }
/* 135:226 */       else if (t == Token.INTEGER)
/* 136:    */       {
/* 137:228 */         IntegerValue i = new IntegerValue();
/* 138:229 */         this.pos += i.read(this.tokenData, this.pos);
/* 139:230 */         this.tokenStack.push(i);
/* 140:    */       }
/* 141:232 */       else if (t == Token.DOUBLE)
/* 142:    */       {
/* 143:234 */         DoubleValue d = new DoubleValue();
/* 144:235 */         this.pos += d.read(this.tokenData, this.pos);
/* 145:236 */         this.tokenStack.push(d);
/* 146:    */       }
/* 147:238 */       else if (t == Token.BOOL)
/* 148:    */       {
/* 149:240 */         BooleanValue bv = new BooleanValue();
/* 150:241 */         this.pos += bv.read(this.tokenData, this.pos);
/* 151:242 */         this.tokenStack.push(bv);
/* 152:    */       }
/* 153:244 */       else if (t == Token.STRING)
/* 154:    */       {
/* 155:246 */         StringValue sv = new StringValue(this.settings);
/* 156:247 */         this.pos += sv.read(this.tokenData, this.pos);
/* 157:248 */         this.tokenStack.push(sv);
/* 158:    */       }
/* 159:250 */       else if (t == Token.MISSING_ARG)
/* 160:    */       {
/* 161:252 */         MissingArg ma = new MissingArg();
/* 162:253 */         this.pos += ma.read(this.tokenData, this.pos);
/* 163:254 */         this.tokenStack.push(ma);
/* 164:    */       }
/* 165:258 */       else if (t == Token.UNARY_PLUS)
/* 166:    */       {
/* 167:260 */         UnaryPlus up = new UnaryPlus();
/* 168:261 */         this.pos += up.read(this.tokenData, this.pos);
/* 169:262 */         addOperator(up);
/* 170:    */       }
/* 171:264 */       else if (t == Token.UNARY_MINUS)
/* 172:    */       {
/* 173:266 */         UnaryMinus um = new UnaryMinus();
/* 174:267 */         this.pos += um.read(this.tokenData, this.pos);
/* 175:268 */         addOperator(um);
/* 176:    */       }
/* 177:270 */       else if (t == Token.PERCENT)
/* 178:    */       {
/* 179:272 */         Percent p = new Percent();
/* 180:273 */         this.pos += p.read(this.tokenData, this.pos);
/* 181:274 */         addOperator(p);
/* 182:    */       }
/* 183:278 */       else if (t == Token.SUBTRACT)
/* 184:    */       {
/* 185:280 */         Subtract s = new Subtract();
/* 186:281 */         this.pos += s.read(this.tokenData, this.pos);
/* 187:282 */         addOperator(s);
/* 188:    */       }
/* 189:284 */       else if (t == Token.ADD)
/* 190:    */       {
/* 191:286 */         Add s = new Add();
/* 192:287 */         this.pos += s.read(this.tokenData, this.pos);
/* 193:288 */         addOperator(s);
/* 194:    */       }
/* 195:290 */       else if (t == Token.MULTIPLY)
/* 196:    */       {
/* 197:292 */         Multiply s = new Multiply();
/* 198:293 */         this.pos += s.read(this.tokenData, this.pos);
/* 199:294 */         addOperator(s);
/* 200:    */       }
/* 201:296 */       else if (t == Token.DIVIDE)
/* 202:    */       {
/* 203:298 */         Divide s = new Divide();
/* 204:299 */         this.pos += s.read(this.tokenData, this.pos);
/* 205:300 */         addOperator(s);
/* 206:    */       }
/* 207:302 */       else if (t == Token.CONCAT)
/* 208:    */       {
/* 209:304 */         Concatenate c = new Concatenate();
/* 210:305 */         this.pos += c.read(this.tokenData, this.pos);
/* 211:306 */         addOperator(c);
/* 212:    */       }
/* 213:308 */       else if (t == Token.POWER)
/* 214:    */       {
/* 215:310 */         Power p = new Power();
/* 216:311 */         this.pos += p.read(this.tokenData, this.pos);
/* 217:312 */         addOperator(p);
/* 218:    */       }
/* 219:314 */       else if (t == Token.LESS_THAN)
/* 220:    */       {
/* 221:316 */         LessThan lt = new LessThan();
/* 222:317 */         this.pos += lt.read(this.tokenData, this.pos);
/* 223:318 */         addOperator(lt);
/* 224:    */       }
/* 225:320 */       else if (t == Token.LESS_EQUAL)
/* 226:    */       {
/* 227:322 */         LessEqual lte = new LessEqual();
/* 228:323 */         this.pos += lte.read(this.tokenData, this.pos);
/* 229:324 */         addOperator(lte);
/* 230:    */       }
/* 231:326 */       else if (t == Token.GREATER_THAN)
/* 232:    */       {
/* 233:328 */         GreaterThan gt = new GreaterThan();
/* 234:329 */         this.pos += gt.read(this.tokenData, this.pos);
/* 235:330 */         addOperator(gt);
/* 236:    */       }
/* 237:332 */       else if (t == Token.GREATER_EQUAL)
/* 238:    */       {
/* 239:334 */         GreaterEqual gte = new GreaterEqual();
/* 240:335 */         this.pos += gte.read(this.tokenData, this.pos);
/* 241:336 */         addOperator(gte);
/* 242:    */       }
/* 243:338 */       else if (t == Token.NOT_EQUAL)
/* 244:    */       {
/* 245:340 */         NotEqual ne = new NotEqual();
/* 246:341 */         this.pos += ne.read(this.tokenData, this.pos);
/* 247:342 */         addOperator(ne);
/* 248:    */       }
/* 249:344 */       else if (t == Token.EQUAL)
/* 250:    */       {
/* 251:346 */         Equal e = new Equal();
/* 252:347 */         this.pos += e.read(this.tokenData, this.pos);
/* 253:348 */         addOperator(e);
/* 254:    */       }
/* 255:350 */       else if (t == Token.PARENTHESIS)
/* 256:    */       {
/* 257:352 */         Parenthesis p = new Parenthesis();
/* 258:353 */         this.pos += p.read(this.tokenData, this.pos);
/* 259:354 */         addOperator(p);
/* 260:    */       }
/* 261:358 */       else if (t == Token.ATTRIBUTE)
/* 262:    */       {
/* 263:360 */         Attribute a = new Attribute(this.settings);
/* 264:361 */         this.pos += a.read(this.tokenData, this.pos);
/* 265:363 */         if (a.isSum()) {
/* 266:365 */           addOperator(a);
/* 267:367 */         } else if (a.isIf()) {
/* 268:370 */           ifStack.push(a);
/* 269:    */         }
/* 270:    */       }
/* 271:373 */       else if (t == Token.FUNCTION)
/* 272:    */       {
/* 273:375 */         BuiltInFunction bif = new BuiltInFunction(this.settings);
/* 274:376 */         this.pos += bif.read(this.tokenData, this.pos);
/* 275:    */         
/* 276:378 */         addOperator(bif);
/* 277:    */       }
/* 278:380 */       else if (t == Token.FUNCTIONVARARG)
/* 279:    */       {
/* 280:382 */         VariableArgFunction vaf = new VariableArgFunction(this.settings);
/* 281:383 */         this.pos += vaf.read(this.tokenData, this.pos);
/* 282:385 */         if (vaf.getFunction() != Function.ATTRIBUTE)
/* 283:    */         {
/* 284:387 */           addOperator(vaf);
/* 285:    */         }
/* 286:    */         else
/* 287:    */         {
/* 288:393 */           vaf.getOperands(this.tokenStack);
/* 289:    */           
/* 290:395 */           Attribute ifattr = null;
/* 291:396 */           if (ifStack.empty()) {
/* 292:398 */             ifattr = new Attribute(this.settings);
/* 293:    */           } else {
/* 294:402 */             ifattr = (Attribute)ifStack.pop();
/* 295:    */           }
/* 296:405 */           ifattr.setIfConditions(vaf);
/* 297:406 */           this.tokenStack.push(ifattr);
/* 298:    */         }
/* 299:    */       }
/* 300:411 */       else if (t == Token.MEM_FUNC)
/* 301:    */       {
/* 302:413 */         MemFunc memFunc = new MemFunc();
/* 303:414 */         handleMemoryFunction(memFunc);
/* 304:    */       }
/* 305:416 */       else if (t == Token.MEM_AREA)
/* 306:    */       {
/* 307:418 */         MemArea memArea = new MemArea();
/* 308:419 */         handleMemoryFunction(memArea);
/* 309:    */       }
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   private void handleMemoryFunction(SubExpression subxp)
/* 314:    */     throws FormulaException
/* 315:    */   {
/* 316:430 */     this.pos += subxp.read(this.tokenData, this.pos);
/* 317:    */     
/* 318:    */ 
/* 319:433 */     Stack oldStack = this.tokenStack;
/* 320:434 */     this.tokenStack = new Stack();
/* 321:    */     
/* 322:436 */     parseSubExpression(subxp.getLength());
/* 323:    */     
/* 324:438 */     ParseItem[] subexpr = new ParseItem[this.tokenStack.size()];
/* 325:439 */     int i = 0;
/* 326:440 */     while (!this.tokenStack.isEmpty())
/* 327:    */     {
/* 328:442 */       subexpr[i] = ((ParseItem)this.tokenStack.pop());
/* 329:443 */       i++;
/* 330:    */     }
/* 331:446 */     subxp.setSubExpression(subexpr);
/* 332:    */     
/* 333:448 */     this.tokenStack = oldStack;
/* 334:449 */     this.tokenStack.push(subxp);
/* 335:    */   }
/* 336:    */   
/* 337:    */   private void addOperator(Operator o)
/* 338:    */   {
/* 339:459 */     o.getOperands(this.tokenStack);
/* 340:    */     
/* 341:    */ 
/* 342:462 */     this.tokenStack.push(o);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public String getFormula()
/* 346:    */   {
/* 347:470 */     StringBuffer sb = new StringBuffer();
/* 348:471 */     this.root.getString(sb);
/* 349:472 */     return sb.toString();
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/* 353:    */   {
/* 354:484 */     this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public byte[] getBytes()
/* 358:    */   {
/* 359:495 */     return this.root.getBytes();
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/* 363:    */   {
/* 364:510 */     this.root.columnInserted(sheetIndex, col, currentSheet);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 368:    */   {
/* 369:524 */     this.root.columnRemoved(sheetIndex, col, currentSheet);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void rowInserted(int sheetIndex, int row, boolean currentSheet)
/* 373:    */   {
/* 374:539 */     this.root.rowInserted(sheetIndex, row, currentSheet);
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void rowRemoved(int sheetIndex, int row, boolean currentSheet)
/* 378:    */   {
/* 379:554 */     this.root.rowRemoved(sheetIndex, row, currentSheet);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public boolean handleImportedCellReferences()
/* 383:    */   {
/* 384:565 */     this.root.handleImportedCellReferences();
/* 385:566 */     return this.root.isValid();
/* 386:    */   }
/* 387:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.TokenFormulaParser
 * JD-Core Version:    0.7.0.1
 */