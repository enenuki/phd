/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.StringReader;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Stack;
/*   8:    */ import jxl.WorkbookSettings;
/*   9:    */ import jxl.biff.WorkbookMethods;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ 
/*  12:    */ class StringFormulaParser
/*  13:    */   implements Parser
/*  14:    */ {
/*  15: 41 */   private static Logger logger = Logger.getLogger(StringFormulaParser.class);
/*  16:    */   private String formula;
/*  17:    */   private String parsedFormula;
/*  18:    */   private ParseItem root;
/*  19:    */   private Stack arguments;
/*  20:    */   private WorkbookSettings settings;
/*  21:    */   private ExternalSheet externalSheet;
/*  22:    */   private WorkbookMethods nameTable;
/*  23:    */   private ParseContext parseContext;
/*  24:    */   
/*  25:    */   public StringFormulaParser(String f, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws, ParseContext pc)
/*  26:    */   {
/*  27: 95 */     this.formula = f;
/*  28: 96 */     this.settings = ws;
/*  29: 97 */     this.externalSheet = es;
/*  30: 98 */     this.nameTable = nt;
/*  31: 99 */     this.parseContext = pc;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void parse()
/*  35:    */     throws FormulaException
/*  36:    */   {
/*  37:109 */     ArrayList tokens = getTokens();
/*  38:    */     
/*  39:111 */     Iterator i = tokens.iterator();
/*  40:    */     
/*  41:113 */     this.root = parseCurrent(i);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private ParseItem parseCurrent(Iterator i)
/*  45:    */     throws FormulaException
/*  46:    */   {
/*  47:126 */     Stack stack = new Stack();
/*  48:127 */     Stack operators = new Stack();
/*  49:128 */     Stack args = null;
/*  50:    */     
/*  51:130 */     boolean parenthesesClosed = false;
/*  52:131 */     ParseItem lastParseItem = null;
/*  53:133 */     while ((i.hasNext()) && (!parenthesesClosed))
/*  54:    */     {
/*  55:135 */       ParseItem pi = (ParseItem)i.next();
/*  56:136 */       pi.setParseContext(this.parseContext);
/*  57:138 */       if ((pi instanceof Operand))
/*  58:    */       {
/*  59:140 */         handleOperand((Operand)pi, stack);
/*  60:    */       }
/*  61:142 */       else if ((pi instanceof StringFunction))
/*  62:    */       {
/*  63:144 */         handleFunction((StringFunction)pi, i, stack);
/*  64:    */       }
/*  65:146 */       else if ((pi instanceof Operator))
/*  66:    */       {
/*  67:148 */         Operator op = (Operator)pi;
/*  68:153 */         if ((op instanceof StringOperator))
/*  69:    */         {
/*  70:155 */           StringOperator sop = (StringOperator)op;
/*  71:156 */           if ((stack.isEmpty()) || ((lastParseItem instanceof Operator))) {
/*  72:158 */             op = sop.getUnaryOperator();
/*  73:    */           } else {
/*  74:162 */             op = sop.getBinaryOperator();
/*  75:    */           }
/*  76:    */         }
/*  77:166 */         if (operators.empty())
/*  78:    */         {
/*  79:169 */           operators.push(op);
/*  80:    */         }
/*  81:    */         else
/*  82:    */         {
/*  83:173 */           Operator operator = (Operator)operators.peek();
/*  84:177 */           if (op.getPrecedence() < operator.getPrecedence())
/*  85:    */           {
/*  86:179 */             operators.push(op);
/*  87:    */           }
/*  88:181 */           else if ((op.getPrecedence() == operator.getPrecedence()) && ((op instanceof UnaryOperator)))
/*  89:    */           {
/*  90:187 */             operators.push(op);
/*  91:    */           }
/*  92:    */           else
/*  93:    */           {
/*  94:193 */             operators.pop();
/*  95:194 */             operator.getOperands(stack);
/*  96:195 */             stack.push(operator);
/*  97:196 */             operators.push(op);
/*  98:    */           }
/*  99:    */         }
/* 100:    */       }
/* 101:200 */       else if ((pi instanceof ArgumentSeparator))
/* 102:    */       {
/* 103:203 */         while (!operators.isEmpty())
/* 104:    */         {
/* 105:205 */           Operator o = (Operator)operators.pop();
/* 106:206 */           o.getOperands(stack);
/* 107:207 */           stack.push(o);
/* 108:    */         }
/* 109:213 */         if (args == null) {
/* 110:215 */           args = new Stack();
/* 111:    */         }
/* 112:218 */         args.push(stack.pop());
/* 113:219 */         stack.clear();
/* 114:    */       }
/* 115:221 */       else if ((pi instanceof OpenParentheses))
/* 116:    */       {
/* 117:223 */         ParseItem pi2 = parseCurrent(i);
/* 118:224 */         Parenthesis p = new Parenthesis();
/* 119:225 */         pi2.setParent(p);
/* 120:226 */         p.add(pi2);
/* 121:227 */         stack.push(p);
/* 122:    */       }
/* 123:229 */       else if ((pi instanceof CloseParentheses))
/* 124:    */       {
/* 125:231 */         parenthesesClosed = true;
/* 126:    */       }
/* 127:234 */       lastParseItem = pi;
/* 128:    */     }
/* 129:237 */     while (!operators.isEmpty())
/* 130:    */     {
/* 131:239 */       Operator o = (Operator)operators.pop();
/* 132:240 */       o.getOperands(stack);
/* 133:241 */       stack.push(o);
/* 134:    */     }
/* 135:244 */     ParseItem rt = !stack.empty() ? (ParseItem)stack.pop() : null;
/* 136:248 */     if ((args != null) && (rt != null)) {
/* 137:250 */       args.push(rt);
/* 138:    */     }
/* 139:253 */     this.arguments = args;
/* 140:255 */     if ((!stack.empty()) || (!operators.empty())) {
/* 141:257 */       logger.warn("Formula " + this.formula + " has a non-empty parse stack");
/* 142:    */     }
/* 143:261 */     return rt;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private ArrayList getTokens()
/* 147:    */     throws FormulaException
/* 148:    */   {
/* 149:272 */     ArrayList tokens = new ArrayList();
/* 150:    */     
/* 151:274 */     StringReader sr = new StringReader(this.formula);
/* 152:275 */     Yylex lex = new Yylex(sr);
/* 153:276 */     lex.setExternalSheet(this.externalSheet);
/* 154:277 */     lex.setNameTable(this.nameTable);
/* 155:    */     try
/* 156:    */     {
/* 157:280 */       ParseItem pi = lex.yylex();
/* 158:281 */       while (pi != null)
/* 159:    */       {
/* 160:283 */         tokens.add(pi);
/* 161:284 */         pi = lex.yylex();
/* 162:    */       }
/* 163:    */     }
/* 164:    */     catch (IOException e)
/* 165:    */     {
/* 166:289 */       logger.warn(e.toString());
/* 167:    */     }
/* 168:    */     catch (Error e)
/* 169:    */     {
/* 170:293 */       throw new FormulaException(FormulaException.LEXICAL_ERROR, this.formula + " at char  " + lex.getPos());
/* 171:    */     }
/* 172:297 */     return tokens;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getFormula()
/* 176:    */   {
/* 177:306 */     if (this.parsedFormula == null)
/* 178:    */     {
/* 179:308 */       StringBuffer sb = new StringBuffer();
/* 180:309 */       this.root.getString(sb);
/* 181:310 */       this.parsedFormula = sb.toString();
/* 182:    */     }
/* 183:313 */     return this.parsedFormula;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public byte[] getBytes()
/* 187:    */   {
/* 188:323 */     byte[] bytes = this.root.getBytes();
/* 189:325 */     if (this.root.isVolatile())
/* 190:    */     {
/* 191:327 */       byte[] newBytes = new byte[bytes.length + 4];
/* 192:328 */       System.arraycopy(bytes, 0, newBytes, 4, bytes.length);
/* 193:329 */       newBytes[0] = Token.ATTRIBUTE.getCode();
/* 194:330 */       newBytes[1] = 1;
/* 195:331 */       bytes = newBytes;
/* 196:    */     }
/* 197:334 */     return bytes;
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void handleFunction(StringFunction sf, Iterator i, Stack stack)
/* 201:    */     throws FormulaException
/* 202:    */   {
/* 203:349 */     ParseItem pi2 = parseCurrent(i);
/* 204:352 */     if (sf.getFunction(this.settings) == Function.UNKNOWN) {
/* 205:354 */       throw new FormulaException(FormulaException.UNRECOGNIZED_FUNCTION);
/* 206:    */     }
/* 207:359 */     if ((sf.getFunction(this.settings) == Function.SUM) && (this.arguments == null))
/* 208:    */     {
/* 209:362 */       Attribute a = new Attribute(sf, this.settings);
/* 210:363 */       a.add(pi2);
/* 211:364 */       stack.push(a);
/* 212:365 */       return;
/* 213:    */     }
/* 214:368 */     if (sf.getFunction(this.settings) == Function.IF)
/* 215:    */     {
/* 216:371 */       Attribute a = new Attribute(sf, this.settings);
/* 217:    */       
/* 218:    */ 
/* 219:    */ 
/* 220:375 */       VariableArgFunction vaf = new VariableArgFunction(this.settings);
/* 221:376 */       int numargs = this.arguments.size();
/* 222:377 */       for (int j = 0; j < numargs; j++)
/* 223:    */       {
/* 224:379 */         ParseItem pi3 = (ParseItem)this.arguments.get(j);
/* 225:380 */         vaf.add(pi3);
/* 226:    */       }
/* 227:383 */       a.setIfConditions(vaf);
/* 228:384 */       stack.push(a);
/* 229:385 */       return;
/* 230:    */     }
/* 231:390 */     if (sf.getFunction(this.settings).getNumArgs() == 255)
/* 232:    */     {
/* 233:395 */       if (this.arguments == null)
/* 234:    */       {
/* 235:397 */         int numArgs = pi2 != null ? 1 : 0;
/* 236:398 */         VariableArgFunction vaf = new VariableArgFunction(sf.getFunction(this.settings), numArgs, this.settings);
/* 237:401 */         if (pi2 != null) {
/* 238:403 */           vaf.add(pi2);
/* 239:    */         }
/* 240:406 */         stack.push(vaf);
/* 241:    */       }
/* 242:    */       else
/* 243:    */       {
/* 244:411 */         int numargs = this.arguments.size();
/* 245:412 */         VariableArgFunction vaf = new VariableArgFunction(sf.getFunction(this.settings), numargs, this.settings);
/* 246:    */         
/* 247:    */ 
/* 248:415 */         ParseItem[] args = new ParseItem[numargs];
/* 249:416 */         for (int j = 0; j < numargs; j++)
/* 250:    */         {
/* 251:418 */           ParseItem pi3 = (ParseItem)this.arguments.pop();
/* 252:419 */           args[(numargs - j - 1)] = pi3;
/* 253:    */         }
/* 254:422 */         for (int j = 0; j < args.length; j++) {
/* 255:424 */           vaf.add(args[j]);
/* 256:    */         }
/* 257:426 */         stack.push(vaf);
/* 258:427 */         this.arguments.clear();
/* 259:428 */         this.arguments = null;
/* 260:    */       }
/* 261:430 */       return;
/* 262:    */     }
/* 263:434 */     BuiltInFunction bif = new BuiltInFunction(sf.getFunction(this.settings), this.settings);
/* 264:    */     
/* 265:    */ 
/* 266:437 */     int numargs = sf.getFunction(this.settings).getNumArgs();
/* 267:438 */     if (numargs == 1)
/* 268:    */     {
/* 269:441 */       bif.add(pi2);
/* 270:    */     }
/* 271:    */     else
/* 272:    */     {
/* 273:445 */       if (((this.arguments == null) && (numargs != 0)) || ((this.arguments != null) && (numargs != this.arguments.size()))) {
/* 274:448 */         throw new FormulaException(FormulaException.INCORRECT_ARGUMENTS);
/* 275:    */       }
/* 276:453 */       for (int j = 0; j < numargs; j++)
/* 277:    */       {
/* 278:455 */         ParseItem pi3 = (ParseItem)this.arguments.get(j);
/* 279:456 */         bif.add(pi3);
/* 280:    */       }
/* 281:    */     }
/* 282:459 */     stack.push(bif);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void adjustRelativeCellReferences(int colAdjust, int rowAdjust)
/* 286:    */   {
/* 287:470 */     this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void columnInserted(int sheetIndex, int col, boolean currentSheet)
/* 291:    */   {
/* 292:485 */     this.root.columnInserted(sheetIndex, col, currentSheet);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void columnRemoved(int sheetIndex, int col, boolean currentSheet)
/* 296:    */   {
/* 297:501 */     this.root.columnRemoved(sheetIndex, col, currentSheet);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void rowInserted(int sheetIndex, int row, boolean currentSheet)
/* 301:    */   {
/* 302:516 */     this.root.rowInserted(sheetIndex, row, currentSheet);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void rowRemoved(int sheetIndex, int row, boolean currentSheet)
/* 306:    */   {
/* 307:531 */     this.root.rowRemoved(sheetIndex, row, currentSheet);
/* 308:    */   }
/* 309:    */   
/* 310:    */   private void handleOperand(Operand o, Stack stack)
/* 311:    */   {
/* 312:542 */     if (!(o instanceof IntegerValue))
/* 313:    */     {
/* 314:544 */       stack.push(o);
/* 315:545 */       return;
/* 316:    */     }
/* 317:548 */     if ((o instanceof IntegerValue))
/* 318:    */     {
/* 319:550 */       IntegerValue iv = (IntegerValue)o;
/* 320:551 */       if (!iv.isOutOfRange())
/* 321:    */       {
/* 322:553 */         stack.push(iv);
/* 323:    */       }
/* 324:    */       else
/* 325:    */       {
/* 326:558 */         DoubleValue dv = new DoubleValue(iv.getValue());
/* 327:559 */         stack.push(dv);
/* 328:    */       }
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean handleImportedCellReferences()
/* 333:    */   {
/* 334:572 */     this.root.handleImportedCellReferences();
/* 335:573 */     return this.root.isValid();
/* 336:    */   }
/* 337:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.StringFormulaParser
 * JD-Core Version:    0.7.0.1
 */