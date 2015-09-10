/*   1:    */ package org.hibernate.hql.internal.ast;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.ASTPair;
/*   5:    */ import antlr.MismatchedTokenException;
/*   6:    */ import antlr.ParserSharedInputState;
/*   7:    */ import antlr.RecognitionException;
/*   8:    */ import antlr.Token;
/*   9:    */ import antlr.TokenStream;
/*  10:    */ import antlr.TokenStreamException;
/*  11:    */ import antlr.collections.AST;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.io.PrintWriter;
/*  14:    */ import java.io.StringReader;
/*  15:    */ import org.hibernate.QueryException;
/*  16:    */ import org.hibernate.hql.internal.antlr.HqlBaseParser;
/*  17:    */ import org.hibernate.hql.internal.antlr.HqlTokenTypes;
/*  18:    */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  19:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  20:    */ import org.hibernate.internal.CoreMessageLogger;
/*  21:    */ import org.hibernate.internal.util.StringHelper;
/*  22:    */ import org.jboss.logging.Logger;
/*  23:    */ 
/*  24:    */ public final class HqlParser
/*  25:    */   extends HqlBaseParser
/*  26:    */ {
/*  27: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HqlParser.class.getName());
/*  28:    */   private ParseErrorHandler parseErrorHandler;
/*  29: 59 */   private ASTPrinter printer = getASTPrinter();
/*  30:    */   
/*  31:    */   private static ASTPrinter getASTPrinter()
/*  32:    */   {
/*  33: 62 */     return new ASTPrinter(HqlTokenTypes.class);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static HqlParser getInstance(String hql)
/*  37:    */   {
/*  38: 67 */     HqlLexer lexer = new HqlLexer(new StringReader(hql));
/*  39: 68 */     return new HqlParser(lexer);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private HqlParser(TokenStream lexer)
/*  43:    */   {
/*  44: 72 */     super(lexer);
/*  45: 73 */     initialize();
/*  46:    */   }
/*  47:    */   
/*  48: 79 */   private int traceDepth = 0;
/*  49:    */   
/*  50:    */   public void traceIn(String ruleName)
/*  51:    */   {
/*  52: 83 */     if (!LOG.isTraceEnabled()) {
/*  53: 83 */       return;
/*  54:    */     }
/*  55: 84 */     if (this.inputState.guessing > 0) {
/*  56: 84 */       return;
/*  57:    */     }
/*  58: 85 */     String prefix = StringHelper.repeat('-', this.traceDepth++ * 2) + "-> ";
/*  59: 86 */     LOG.trace(prefix + ruleName);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void traceOut(String ruleName)
/*  63:    */   {
/*  64: 91 */     if (!LOG.isTraceEnabled()) {
/*  65: 91 */       return;
/*  66:    */     }
/*  67: 92 */     if (this.inputState.guessing > 0) {
/*  68: 92 */       return;
/*  69:    */     }
/*  70: 93 */     String prefix = "<-" + StringHelper.repeat('-', --this.traceDepth * 2) + " ";
/*  71: 94 */     LOG.trace(prefix + ruleName);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void reportError(RecognitionException e)
/*  75:    */   {
/*  76: 99 */     this.parseErrorHandler.reportError(e);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void reportError(String s)
/*  80:    */   {
/*  81:104 */     this.parseErrorHandler.reportError(s);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void reportWarning(String s)
/*  85:    */   {
/*  86:109 */     this.parseErrorHandler.reportWarning(s);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public ParseErrorHandler getParseErrorHandler()
/*  90:    */   {
/*  91:113 */     return this.parseErrorHandler;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public AST handleIdentifierError(Token token, RecognitionException ex)
/*  95:    */     throws RecognitionException, TokenStreamException
/*  96:    */   {
/*  97:128 */     if ((token instanceof HqlToken))
/*  98:    */     {
/*  99:129 */       HqlToken hqlToken = (HqlToken)token;
/* 100:132 */       if ((hqlToken.isPossibleID()) && ((ex instanceof MismatchedTokenException)))
/* 101:    */       {
/* 102:133 */         MismatchedTokenException mte = (MismatchedTokenException)ex;
/* 103:135 */         if (mte.expecting == 126)
/* 104:    */         {
/* 105:137 */           reportWarning("Keyword  '" + token.getText() + "' is being interpreted as an identifier due to: " + mte.getMessage());
/* 106:    */           
/* 107:    */ 
/* 108:    */ 
/* 109:141 */           ASTPair currentAST = new ASTPair();
/* 110:142 */           token.setType(93);
/* 111:143 */           this.astFactory.addASTChild(currentAST, this.astFactory.create(token));
/* 112:144 */           consume();
/* 113:145 */           AST identifierAST = currentAST.root;
/* 114:146 */           return identifierAST;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:151 */     return super.handleIdentifierError(token, ex);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public AST negateNode(AST x)
/* 122:    */   {
/* 123:166 */     switch (x.getType())
/* 124:    */     {
/* 125:    */     case 40: 
/* 126:168 */       x.setType(6);
/* 127:169 */       x.setText("{and}");
/* 128:170 */       x.setFirstChild(negateNode(x.getFirstChild()));
/* 129:171 */       x.getFirstChild().setNextSibling(negateNode(x.getFirstChild().getNextSibling()));
/* 130:172 */       return x;
/* 131:    */     case 6: 
/* 132:174 */       x.setType(40);
/* 133:175 */       x.setText("{or}");
/* 134:176 */       x.setFirstChild(negateNode(x.getFirstChild()));
/* 135:177 */       x.getFirstChild().setNextSibling(negateNode(x.getFirstChild().getNextSibling()));
/* 136:178 */       return x;
/* 137:    */     case 102: 
/* 138:180 */       x.setType(108);
/* 139:181 */       x.setText("{not}" + x.getText());
/* 140:182 */       return x;
/* 141:    */     case 108: 
/* 142:184 */       x.setType(102);
/* 143:185 */       x.setText("{not}" + x.getText());
/* 144:186 */       return x;
/* 145:    */     case 111: 
/* 146:188 */       x.setType(112);
/* 147:189 */       x.setText("{not}" + x.getText());
/* 148:190 */       return x;
/* 149:    */     case 110: 
/* 150:192 */       x.setType(113);
/* 151:193 */       x.setText("{not}" + x.getText());
/* 152:194 */       return x;
/* 153:    */     case 113: 
/* 154:196 */       x.setType(110);
/* 155:197 */       x.setText("{not}" + x.getText());
/* 156:198 */       return x;
/* 157:    */     case 112: 
/* 158:200 */       x.setType(111);
/* 159:201 */       x.setText("{not}" + x.getText());
/* 160:202 */       return x;
/* 161:    */     case 34: 
/* 162:204 */       x.setType(84);
/* 163:205 */       x.setText("{not}" + x.getText());
/* 164:206 */       return x;
/* 165:    */     case 84: 
/* 166:208 */       x.setType(34);
/* 167:209 */       x.setText("{not}" + x.getText());
/* 168:210 */       return x;
/* 169:    */     case 26: 
/* 170:212 */       x.setType(83);
/* 171:213 */       x.setText("{not}" + x.getText());
/* 172:214 */       return x;
/* 173:    */     case 83: 
/* 174:216 */       x.setType(26);
/* 175:217 */       x.setText("{not}" + x.getText());
/* 176:218 */       return x;
/* 177:    */     case 80: 
/* 178:220 */       x.setType(79);
/* 179:221 */       x.setText("{not}" + x.getText());
/* 180:222 */       return x;
/* 181:    */     case 79: 
/* 182:224 */       x.setType(80);
/* 183:225 */       x.setText("{not}" + x.getText());
/* 184:226 */       return x;
/* 185:    */     case 10: 
/* 186:228 */       x.setType(82);
/* 187:229 */       x.setText("{not}" + x.getText());
/* 188:230 */       return x;
/* 189:    */     case 82: 
/* 190:232 */       x.setType(10);
/* 191:233 */       x.setText("{not}" + x.getText());
/* 192:234 */       return x;
/* 193:    */     }
/* 194:240 */     AST not = super.negateNode(x);
/* 195:241 */     if (not != x)
/* 196:    */     {
/* 197:243 */       not.setNextSibling(x.getNextSibling());
/* 198:244 */       x.setNextSibling(null);
/* 199:    */     }
/* 200:246 */     return not;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public AST processEqualityExpression(AST x)
/* 204:    */   {
/* 205:258 */     if (x == null)
/* 206:    */     {
/* 207:259 */       LOG.processEqualityExpression();
/* 208:260 */       return null;
/* 209:    */     }
/* 210:263 */     int type = x.getType();
/* 211:264 */     if ((type == 102) || (type == 108))
/* 212:    */     {
/* 213:265 */       boolean negated = type == 108;
/* 214:266 */       if (x.getNumberOfChildren() == 2)
/* 215:    */       {
/* 216:267 */         AST a = x.getFirstChild();
/* 217:268 */         AST b = a.getNextSibling();
/* 218:270 */         if ((a.getType() == 39) && (b.getType() != 39)) {
/* 219:271 */           return createIsNullParent(b, negated);
/* 220:    */         }
/* 221:274 */         if ((b.getType() == 39) && (a.getType() != 39)) {
/* 222:275 */           return createIsNullParent(a, negated);
/* 223:    */         }
/* 224:277 */         if (b.getType() == 62) {
/* 225:278 */           return processIsEmpty(a, negated);
/* 226:    */         }
/* 227:281 */         return x;
/* 228:    */       }
/* 229:285 */       return x;
/* 230:    */     }
/* 231:289 */     return x;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private AST createIsNullParent(AST node, boolean negated)
/* 235:    */   {
/* 236:294 */     node.setNextSibling(null);
/* 237:295 */     int type = negated ? 79 : 80;
/* 238:296 */     String text = negated ? "is not null" : "is null";
/* 239:297 */     return ASTUtil.createParent(this.astFactory, type, text, node);
/* 240:    */   }
/* 241:    */   
/* 242:    */   private AST processIsEmpty(AST node, boolean negated)
/* 243:    */   {
/* 244:301 */     node.setNextSibling(null);
/* 245:    */     
/* 246:    */ 
/* 247:304 */     AST ast = createSubquery(node);
/* 248:305 */     ast = ASTUtil.createParent(this.astFactory, 19, "exists", ast);
/* 249:307 */     if (!negated) {
/* 250:308 */       ast = ASTUtil.createParent(this.astFactory, 38, "not", ast);
/* 251:    */     }
/* 252:310 */     return ast;
/* 253:    */   }
/* 254:    */   
/* 255:    */   private AST createSubquery(AST node)
/* 256:    */   {
/* 257:314 */     AST ast = ASTUtil.createParent(this.astFactory, 87, "RANGE", node);
/* 258:315 */     ast = ASTUtil.createParent(this.astFactory, 22, "from", ast);
/* 259:316 */     ast = ASTUtil.createParent(this.astFactory, 89, "SELECT_FROM", ast);
/* 260:317 */     ast = ASTUtil.createParent(this.astFactory, 86, "QUERY", ast);
/* 261:318 */     return ast;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void showAst(AST ast, PrintStream out)
/* 265:    */   {
/* 266:322 */     showAst(ast, new PrintWriter(out));
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void showAst(AST ast, PrintWriter pw)
/* 270:    */   {
/* 271:326 */     this.printer.showAst(ast, pw);
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void initialize()
/* 275:    */   {
/* 276:331 */     this.parseErrorHandler = new ErrorCounter();
/* 277:332 */     setASTFactory(new HqlASTFactory());
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void weakKeywords()
/* 281:    */     throws TokenStreamException
/* 282:    */   {
/* 283:338 */     int t = LA(1);
/* 284:339 */     switch (t)
/* 285:    */     {
/* 286:    */     case 24: 
/* 287:    */     case 41: 
/* 288:344 */       if (LA(2) != 105)
/* 289:    */       {
/* 290:345 */         LT(1).setType(126);
/* 291:346 */         if (LOG.isDebugEnabled()) {
/* 292:347 */           LOG.debugf("weakKeywords() : new LT(1) token - %s", LT(1));
/* 293:    */         }
/* 294:    */       }
/* 295:    */       break;
/* 296:    */     default: 
/* 297:353 */       if ((LA(0) == 22) && (t != 126) && (LA(2) == 15))
/* 298:    */       {
/* 299:354 */         HqlToken hqlToken = (HqlToken)LT(1);
/* 300:355 */         if (hqlToken.isPossibleID())
/* 301:    */         {
/* 302:356 */           hqlToken.setType(126);
/* 303:357 */           if (LOG.isDebugEnabled()) {
/* 304:358 */             LOG.debugf("weakKeywords() : new LT(1) token - %s", LT(1));
/* 305:    */           }
/* 306:    */         }
/* 307:    */       }
/* 308:    */       break;
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void handleDotIdent()
/* 313:    */     throws TokenStreamException
/* 314:    */   {
/* 315:370 */     if ((LA(1) == 15) && (LA(2) != 126))
/* 316:    */     {
/* 317:372 */       HqlToken t = (HqlToken)LT(2);
/* 318:373 */       if (t.isPossibleID())
/* 319:    */       {
/* 320:376 */         LT(2).setType(126);
/* 321:377 */         if (LOG.isDebugEnabled()) {
/* 322:378 */           LOG.debugf("handleDotIdent() : new LT(2) token - %s", LT(1));
/* 323:    */         }
/* 324:    */       }
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void processMemberOf(Token n, AST p, ASTPair currentAST)
/* 329:    */   {
/* 330:386 */     AST inAst = n == null ? this.astFactory.create(26, "in") : this.astFactory.create(83, "not in");
/* 331:387 */     this.astFactory.makeASTRoot(currentAST, inAst);
/* 332:388 */     AST ast = createSubquery(p);
/* 333:389 */     ast = ASTUtil.createParent(this.astFactory, 77, "inList", ast);
/* 334:390 */     inAst.addChild(ast);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static void panic()
/* 338:    */   {
/* 339:395 */     throw new QueryException("Parser: panic");
/* 340:    */   }
/* 341:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.HqlParser
 * JD-Core Version:    0.7.0.1
 */