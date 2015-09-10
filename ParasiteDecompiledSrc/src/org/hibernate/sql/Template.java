/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.StringTokenizer;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.dialect.Dialect;
/*  11:    */ import org.hibernate.dialect.function.SQLFunction;
/*  12:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.sql.ordering.antlr.ColumnMapper;
/*  15:    */ import org.hibernate.sql.ordering.antlr.OrderByFragmentTranslator;
/*  16:    */ import org.hibernate.sql.ordering.antlr.TranslationContext;
/*  17:    */ 
/*  18:    */ public final class Template
/*  19:    */ {
/*  20: 50 */   private static final Set<String> KEYWORDS = new HashSet();
/*  21: 51 */   private static final Set<String> BEFORE_TABLE_KEYWORDS = new HashSet();
/*  22: 52 */   private static final Set<String> FUNCTION_KEYWORDS = new HashSet();
/*  23:    */   public static final String TEMPLATE = "$PlaceHolder$";
/*  24:    */   
/*  25:    */   static
/*  26:    */   {
/*  27: 54 */     KEYWORDS.add("and");
/*  28: 55 */     KEYWORDS.add("or");
/*  29: 56 */     KEYWORDS.add("not");
/*  30: 57 */     KEYWORDS.add("like");
/*  31: 58 */     KEYWORDS.add("is");
/*  32: 59 */     KEYWORDS.add("in");
/*  33: 60 */     KEYWORDS.add("between");
/*  34: 61 */     KEYWORDS.add("null");
/*  35: 62 */     KEYWORDS.add("select");
/*  36: 63 */     KEYWORDS.add("distinct");
/*  37: 64 */     KEYWORDS.add("from");
/*  38: 65 */     KEYWORDS.add("join");
/*  39: 66 */     KEYWORDS.add("inner");
/*  40: 67 */     KEYWORDS.add("outer");
/*  41: 68 */     KEYWORDS.add("left");
/*  42: 69 */     KEYWORDS.add("right");
/*  43: 70 */     KEYWORDS.add("on");
/*  44: 71 */     KEYWORDS.add("where");
/*  45: 72 */     KEYWORDS.add("having");
/*  46: 73 */     KEYWORDS.add("group");
/*  47: 74 */     KEYWORDS.add("order");
/*  48: 75 */     KEYWORDS.add("by");
/*  49: 76 */     KEYWORDS.add("desc");
/*  50: 77 */     KEYWORDS.add("asc");
/*  51: 78 */     KEYWORDS.add("limit");
/*  52: 79 */     KEYWORDS.add("any");
/*  53: 80 */     KEYWORDS.add("some");
/*  54: 81 */     KEYWORDS.add("exists");
/*  55: 82 */     KEYWORDS.add("all");
/*  56: 83 */     KEYWORDS.add("union");
/*  57: 84 */     KEYWORDS.add("minus");
/*  58:    */     
/*  59: 86 */     BEFORE_TABLE_KEYWORDS.add("from");
/*  60: 87 */     BEFORE_TABLE_KEYWORDS.add("join");
/*  61:    */     
/*  62: 89 */     FUNCTION_KEYWORDS.add("as");
/*  63: 90 */     FUNCTION_KEYWORDS.add("leading");
/*  64: 91 */     FUNCTION_KEYWORDS.add("trailing");
/*  65: 92 */     FUNCTION_KEYWORDS.add("from");
/*  66: 93 */     FUNCTION_KEYWORDS.add("case");
/*  67: 94 */     FUNCTION_KEYWORDS.add("when");
/*  68: 95 */     FUNCTION_KEYWORDS.add("then");
/*  69: 96 */     FUNCTION_KEYWORDS.add("else");
/*  70: 97 */     FUNCTION_KEYWORDS.add("end");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String renderWhereStringTemplate(String sqlWhereString, Dialect dialect, SQLFunctionRegistry functionRegistry)
/*  74:    */   {
/*  75:105 */     return renderWhereStringTemplate(sqlWhereString, "$PlaceHolder$", dialect, functionRegistry);
/*  76:    */   }
/*  77:    */   
/*  78:    */   @Deprecated
/*  79:    */   public static String renderWhereStringTemplate(String sqlWhereString, String placeholder, Dialect dialect)
/*  80:    */   {
/*  81:119 */     return renderWhereStringTemplate(sqlWhereString, placeholder, dialect, new SQLFunctionRegistry(dialect, Collections.EMPTY_MAP));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String renderWhereStringTemplate(String sqlWhereString, String placeholder, Dialect dialect, SQLFunctionRegistry functionRegistry)
/*  85:    */   {
/*  86:140 */     String symbols = "=><!+-*/()',|&`" + " \n\r\f\t" + dialect.openQuote() + dialect.closeQuote();
/*  87:    */     
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:146 */     StringTokenizer tokens = new StringTokenizer(sqlWhereString, symbols, true);
/*  93:147 */     StringBuffer result = new StringBuffer();
/*  94:    */     
/*  95:149 */     boolean quoted = false;
/*  96:150 */     boolean quotedIdentifier = false;
/*  97:151 */     boolean beforeTable = false;
/*  98:152 */     boolean inFromClause = false;
/*  99:153 */     boolean afterFromTable = false;
/* 100:    */     
/* 101:155 */     boolean hasMore = tokens.hasMoreTokens();
/* 102:156 */     String nextToken = hasMore ? tokens.nextToken() : null;
/* 103:157 */     while (hasMore)
/* 104:    */     {
/* 105:158 */       String token = nextToken;
/* 106:159 */       String lcToken = token.toLowerCase();
/* 107:160 */       hasMore = tokens.hasMoreTokens();
/* 108:161 */       nextToken = hasMore ? tokens.nextToken() : null;
/* 109:    */       
/* 110:163 */       boolean isQuoteCharacter = false;
/* 111:165 */       if ((!quotedIdentifier) && ("'".equals(token)))
/* 112:    */       {
/* 113:166 */         quoted = !quoted;
/* 114:167 */         isQuoteCharacter = true;
/* 115:    */       }
/* 116:170 */       if (!quoted)
/* 117:    */       {
/* 118:    */         boolean isOpenQuote;
/* 119:172 */         if ("`".equals(token))
/* 120:    */         {
/* 121:173 */           boolean isOpenQuote = !quotedIdentifier;
/* 122:174 */           token = lcToken = isOpenQuote ? Character.toString(dialect.openQuote()) : Character.toString(dialect.closeQuote());
/* 123:    */           
/* 124:    */ 
/* 125:177 */           quotedIdentifier = isOpenQuote;
/* 126:178 */           isQuoteCharacter = true;
/* 127:    */         }
/* 128:180 */         else if ((!quotedIdentifier) && (dialect.openQuote() == token.charAt(0)))
/* 129:    */         {
/* 130:181 */           boolean isOpenQuote = true;
/* 131:182 */           quotedIdentifier = true;
/* 132:183 */           isQuoteCharacter = true;
/* 133:    */         }
/* 134:    */         else
/* 135:    */         {
/* 136:    */           boolean isOpenQuote;
/* 137:185 */           if ((quotedIdentifier) && (dialect.closeQuote() == token.charAt(0)))
/* 138:    */           {
/* 139:186 */             quotedIdentifier = false;
/* 140:187 */             isQuoteCharacter = true;
/* 141:188 */             isOpenQuote = false;
/* 142:    */           }
/* 143:    */           else
/* 144:    */           {
/* 145:191 */             isOpenQuote = false;
/* 146:    */           }
/* 147:    */         }
/* 148:194 */         if (isOpenQuote) {
/* 149:195 */           result.append(placeholder).append('.');
/* 150:    */         }
/* 151:    */       }
/* 152:200 */       if (("extract".equals(lcToken)) && ("(".equals(nextToken)))
/* 153:    */       {
/* 154:201 */         String field = extractUntil(tokens, "from");
/* 155:202 */         String source = renderWhereStringTemplate(extractUntil(tokens, ")"), placeholder, dialect, functionRegistry);
/* 156:    */         
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:208 */         result.append("extract(").append(field).append(" from ").append(source).append(')');
/* 162:    */         
/* 163:210 */         hasMore = tokens.hasMoreTokens();
/* 164:211 */         nextToken = hasMore ? tokens.nextToken() : null;
/* 165:    */       }
/* 166:217 */       else if (("trim".equals(lcToken)) && ("(".equals(nextToken)))
/* 167:    */       {
/* 168:218 */         List<String> operands = new ArrayList();
/* 169:219 */         StringBuilder builder = new StringBuilder();
/* 170:    */         
/* 171:221 */         boolean hasMoreOperands = true;
/* 172:222 */         String operandToken = tokens.nextToken();
/* 173:223 */         boolean quotedOperand = false;
/* 174:224 */         while (hasMoreOperands)
/* 175:    */         {
/* 176:225 */           boolean isQuote = "'".equals(operandToken);
/* 177:226 */           if (isQuote)
/* 178:    */           {
/* 179:227 */             quotedOperand = !quotedOperand;
/* 180:228 */             if (!quotedOperand)
/* 181:    */             {
/* 182:229 */               operands.add('\'');
/* 183:230 */               builder.setLength(0);
/* 184:    */             }
/* 185:    */             else
/* 186:    */             {
/* 187:233 */               builder.append('\'');
/* 188:    */             }
/* 189:    */           }
/* 190:236 */           else if (quotedOperand)
/* 191:    */           {
/* 192:237 */             builder.append(operandToken);
/* 193:    */           }
/* 194:239 */           else if ((operandToken.length() != 1) || (!Character.isWhitespace(operandToken.charAt(0))))
/* 195:    */           {
/* 196:243 */             operands.add(operandToken);
/* 197:    */           }
/* 198:245 */           operandToken = tokens.nextToken();
/* 199:246 */           hasMoreOperands = (tokens.hasMoreTokens()) && (!")".equals(operandToken));
/* 200:    */         }
/* 201:249 */         TrimOperands trimOperands = new TrimOperands(operands, null);
/* 202:250 */         result.append("trim(");
/* 203:251 */         if (trimOperands.trimSpec != null) {
/* 204:252 */           result.append(trimOperands.trimSpec).append(' ');
/* 205:    */         }
/* 206:254 */         if (trimOperands.trimChar != null)
/* 207:    */         {
/* 208:255 */           if ((trimOperands.trimChar.startsWith("'")) && (trimOperands.trimChar.endsWith("'"))) {
/* 209:256 */             result.append(trimOperands.trimChar);
/* 210:    */           } else {
/* 211:259 */             result.append(renderWhereStringTemplate(trimOperands.trimSpec, placeholder, dialect, functionRegistry));
/* 212:    */           }
/* 213:263 */           result.append(' ');
/* 214:    */         }
/* 215:265 */         if (trimOperands.from != null) {
/* 216:266 */           result.append(trimOperands.from).append(' ');
/* 217:268 */         } else if ((trimOperands.trimSpec != null) || (trimOperands.trimChar != null)) {
/* 218:270 */           result.append("from ");
/* 219:    */         }
/* 220:273 */         result.append(renderWhereStringTemplate(trimOperands.trimSource, placeholder, dialect, functionRegistry)).append(')');
/* 221:    */         
/* 222:    */ 
/* 223:276 */         hasMore = tokens.hasMoreTokens();
/* 224:277 */         nextToken = hasMore ? tokens.nextToken() : null;
/* 225:    */       }
/* 226:    */       else
/* 227:    */       {
/* 228:282 */         boolean quotedOrWhitespace = (quoted) || (quotedIdentifier) || (isQuoteCharacter) || (Character.isWhitespace(token.charAt(0)));
/* 229:285 */         if (quotedOrWhitespace)
/* 230:    */         {
/* 231:286 */           result.append(token);
/* 232:    */         }
/* 233:288 */         else if (beforeTable)
/* 234:    */         {
/* 235:289 */           result.append(token);
/* 236:290 */           beforeTable = false;
/* 237:291 */           afterFromTable = true;
/* 238:    */         }
/* 239:293 */         else if (afterFromTable)
/* 240:    */         {
/* 241:294 */           if (!"as".equals(lcToken)) {
/* 242:295 */             afterFromTable = false;
/* 243:    */           }
/* 244:297 */           result.append(token);
/* 245:    */         }
/* 246:299 */         else if (isNamedParameter(token))
/* 247:    */         {
/* 248:300 */           result.append(token);
/* 249:    */         }
/* 250:302 */         else if ((isIdentifier(token, dialect)) && (!isFunctionOrKeyword(lcToken, nextToken, dialect, functionRegistry)))
/* 251:    */         {
/* 252:304 */           result.append(placeholder).append('.').append(dialect.quote(token));
/* 253:    */         }
/* 254:    */         else
/* 255:    */         {
/* 256:309 */           if (BEFORE_TABLE_KEYWORDS.contains(lcToken))
/* 257:    */           {
/* 258:310 */             beforeTable = true;
/* 259:311 */             inFromClause = true;
/* 260:    */           }
/* 261:313 */           else if ((inFromClause) && (",".equals(lcToken)))
/* 262:    */           {
/* 263:314 */             beforeTable = true;
/* 264:    */           }
/* 265:316 */           result.append(token);
/* 266:    */         }
/* 267:320 */         if ((inFromClause) && (KEYWORDS.contains(lcToken)) && (!BEFORE_TABLE_KEYWORDS.contains(lcToken))) {
/* 268:323 */           inFromClause = false;
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:327 */     return result.toString();
/* 273:    */   }
/* 274:    */   
/* 275:    */   private static class TrimOperands
/* 276:    */   {
/* 277:    */     private final String trimSpec;
/* 278:    */     private final String trimChar;
/* 279:    */     private final String from;
/* 280:    */     private final String trimSource;
/* 281:    */     
/* 282:    */     private TrimOperands(List<String> operands)
/* 283:    */     {
/* 284:569 */       if (operands.size() == 1)
/* 285:    */       {
/* 286:570 */         this.trimSpec = null;
/* 287:571 */         this.trimChar = null;
/* 288:572 */         this.from = null;
/* 289:573 */         this.trimSource = ((String)operands.get(0));
/* 290:    */       }
/* 291:575 */       else if (operands.size() == 4)
/* 292:    */       {
/* 293:576 */         this.trimSpec = ((String)operands.get(0));
/* 294:577 */         this.trimChar = ((String)operands.get(1));
/* 295:578 */         this.from = ((String)operands.get(2));
/* 296:579 */         this.trimSource = ((String)operands.get(3));
/* 297:    */       }
/* 298:    */       else
/* 299:    */       {
/* 300:582 */         if ((operands.size() < 1) || (operands.size() > 4)) {
/* 301:583 */           throw new HibernateException("Unexpected number of trim function operands : " + operands.size());
/* 302:    */         }
/* 303:587 */         this.trimSource = ((String)operands.get(operands.size() - 1));
/* 304:590 */         if (!"from".equals(operands.get(operands.size() - 2))) {
/* 305:591 */           throw new HibernateException("Expecting FROM, found : " + (String)operands.get(operands.size() - 2));
/* 306:    */         }
/* 307:593 */         this.from = ((String)operands.get(operands.size() - 2));
/* 308:596 */         if (("leading".equalsIgnoreCase((String)operands.get(0))) || ("trailing".equalsIgnoreCase((String)operands.get(0))) || ("both".equalsIgnoreCase((String)operands.get(0))))
/* 309:    */         {
/* 310:599 */           this.trimSpec = ((String)operands.get(0));
/* 311:600 */           this.trimChar = null;
/* 312:    */         }
/* 313:    */         else
/* 314:    */         {
/* 315:603 */           this.trimSpec = null;
/* 316:604 */           if (operands.size() - 2 == 0) {
/* 317:605 */             this.trimChar = null;
/* 318:    */           } else {
/* 319:608 */             this.trimChar = ((String)operands.get(0));
/* 320:    */           }
/* 321:    */         }
/* 322:    */       }
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   private static String extractUntil(StringTokenizer tokens, String delimiter)
/* 327:    */   {
/* 328:616 */     StringBuilder valueBuilder = new StringBuilder();
/* 329:617 */     String token = tokens.nextToken();
/* 330:618 */     while (!delimiter.equalsIgnoreCase(token))
/* 331:    */     {
/* 332:619 */       valueBuilder.append(token);
/* 333:620 */       token = tokens.nextToken();
/* 334:    */     }
/* 335:622 */     return valueBuilder.toString().trim();
/* 336:    */   }
/* 337:    */   
/* 338:    */   public static class NoOpColumnMapper
/* 339:    */     implements ColumnMapper
/* 340:    */   {
/* 341:626 */     public static final NoOpColumnMapper INSTANCE = new NoOpColumnMapper();
/* 342:    */     
/* 343:    */     public String[] map(String reference)
/* 344:    */     {
/* 345:628 */       return new String[] { reference };
/* 346:    */     }
/* 347:    */   }
/* 348:    */   
/* 349:    */   @Deprecated
/* 350:    */   public static String renderOrderByStringTemplate(String orderByFragment, Dialect dialect, SQLFunctionRegistry functionRegistry)
/* 351:    */   {
/* 352:649 */     return renderOrderByStringTemplate(orderByFragment, NoOpColumnMapper.INSTANCE, null, dialect, functionRegistry);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public static String renderOrderByStringTemplate(String orderByFragment, final ColumnMapper columnMapper, SessionFactoryImplementor sessionFactory, final Dialect dialect, final SQLFunctionRegistry functionRegistry)
/* 356:    */   {
/* 357:677 */     TranslationContext context = new TranslationContext()
/* 358:    */     {
/* 359:    */       public SessionFactoryImplementor getSessionFactory()
/* 360:    */       {
/* 361:679 */         return this.val$sessionFactory;
/* 362:    */       }
/* 363:    */       
/* 364:    */       public Dialect getDialect()
/* 365:    */       {
/* 366:683 */         return dialect;
/* 367:    */       }
/* 368:    */       
/* 369:    */       public SQLFunctionRegistry getSqlFunctionRegistry()
/* 370:    */       {
/* 371:687 */         return functionRegistry;
/* 372:    */       }
/* 373:    */       
/* 374:    */       public ColumnMapper getColumnMapper()
/* 375:    */       {
/* 376:691 */         return columnMapper;
/* 377:    */       }
/* 378:694 */     };
/* 379:695 */     OrderByFragmentTranslator translator = new OrderByFragmentTranslator(context);
/* 380:696 */     return translator.render(orderByFragment);
/* 381:    */   }
/* 382:    */   
/* 383:    */   private static boolean isNamedParameter(String token)
/* 384:    */   {
/* 385:700 */     return token.startsWith(":");
/* 386:    */   }
/* 387:    */   
/* 388:    */   private static boolean isFunctionOrKeyword(String lcToken, String nextToken, Dialect dialect, SQLFunctionRegistry functionRegistry)
/* 389:    */   {
/* 390:704 */     return ("(".equals(nextToken)) || (KEYWORDS.contains(lcToken)) || (isFunction(lcToken, nextToken, functionRegistry)) || (dialect.getKeywords().contains(lcToken)) || (FUNCTION_KEYWORDS.contains(lcToken));
/* 391:    */   }
/* 392:    */   
/* 393:    */   private static boolean isFunction(String lcToken, String nextToken, SQLFunctionRegistry functionRegistry)
/* 394:    */   {
/* 395:714 */     if ("(".equals(nextToken)) {
/* 396:715 */       return true;
/* 397:    */     }
/* 398:717 */     SQLFunction function = functionRegistry.findSQLFunction(lcToken);
/* 399:718 */     if (function == null) {
/* 400:720 */       return false;
/* 401:    */     }
/* 402:724 */     return !function.hasParenthesesIfNoArguments();
/* 403:    */   }
/* 404:    */   
/* 405:    */   private static boolean isIdentifier(String token, Dialect dialect)
/* 406:    */   {
/* 407:728 */     return (token.charAt(0) == '`') || ((Character.isLetter(token.charAt(0))) && (token.indexOf('.') < 0));
/* 408:    */   }
/* 409:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Template
 * JD-Core Version:    0.7.0.1
 */