/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.hibernate.QueryException;
/*   9:    */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*  10:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  11:    */ import org.hibernate.hql.internal.ast.util.ASTAppender;
/*  12:    */ import org.hibernate.hql.internal.ast.util.ASTIterator;
/*  13:    */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  14:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public class SelectClause
/*  18:    */   extends SelectExpressionList
/*  19:    */ {
/*  20: 47 */   private boolean prepared = false;
/*  21:    */   private boolean scalarSelect;
/*  22: 50 */   private List fromElementsForLoad = new ArrayList();
/*  23:    */   private Type[] queryReturnTypes;
/*  24:    */   private String[][] columnNames;
/*  25:    */   private List collectionFromElements;
/*  26:    */   private String[] aliases;
/*  27:    */   private int[] columnNamesStartPositions;
/*  28:    */   private AggregatedSelectExpression aggregatedSelectExpression;
/*  29:    */   
/*  30:    */   public boolean isScalarSelect()
/*  31:    */   {
/*  32: 67 */     return this.scalarSelect;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isDistinct()
/*  36:    */   {
/*  37: 71 */     return (getFirstChild() != null) && (getFirstChild().getType() == 16);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getFromElementsForLoad()
/*  41:    */   {
/*  42: 80 */     return this.fromElementsForLoad;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Type[] getQueryReturnTypes()
/*  46:    */   {
/*  47: 98 */     return this.queryReturnTypes;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String[] getQueryReturnAliases()
/*  51:    */   {
/*  52:107 */     return this.aliases;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String[][] getColumnNames()
/*  56:    */   {
/*  57:116 */     return this.columnNames;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AggregatedSelectExpression getAggregatedSelectExpression()
/*  61:    */   {
/*  62:120 */     return this.aggregatedSelectExpression;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void initializeExplicitSelectClause(FromClause fromClause)
/*  66:    */     throws SemanticException
/*  67:    */   {
/*  68:130 */     if (this.prepared) {
/*  69:131 */       throw new IllegalStateException("SelectClause was already prepared!");
/*  70:    */     }
/*  71:136 */     ArrayList queryReturnTypeList = new ArrayList();
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:141 */     SelectExpression[] selectExpressions = collectSelectExpressions();
/*  77:143 */     for (int i = 0; i < selectExpressions.length; i++)
/*  78:    */     {
/*  79:144 */       SelectExpression selectExpression = selectExpressions[i];
/*  80:146 */       if (AggregatedSelectExpression.class.isInstance(selectExpression))
/*  81:    */       {
/*  82:147 */         this.aggregatedSelectExpression = ((AggregatedSelectExpression)selectExpression);
/*  83:148 */         queryReturnTypeList.addAll(this.aggregatedSelectExpression.getAggregatedSelectionTypeList());
/*  84:149 */         this.scalarSelect = true;
/*  85:    */       }
/*  86:    */       else
/*  87:    */       {
/*  88:152 */         Type type = selectExpression.getDataType();
/*  89:153 */         if (type == null) {
/*  90:154 */           throw new IllegalStateException("No data type for node: " + selectExpression.getClass().getName() + " " + new ASTPrinter(SqlTokenTypes.class).showAsString((AST)selectExpression, ""));
/*  91:    */         }
/*  92:160 */         if (selectExpression.isScalar()) {
/*  93:161 */           this.scalarSelect = true;
/*  94:    */         }
/*  95:164 */         if (isReturnableEntity(selectExpression)) {
/*  96:165 */           this.fromElementsForLoad.add(selectExpression.getFromElement());
/*  97:    */         }
/*  98:169 */         queryReturnTypeList.add(type);
/*  99:    */       }
/* 100:    */     }
/* 101:174 */     initAliases(selectExpressions);
/* 102:176 */     if (!getWalker().isShallowQuery())
/* 103:    */     {
/* 104:178 */       List fromElements = fromClause.getProjectionList();
/* 105:    */       
/* 106:180 */       ASTAppender appender = new ASTAppender(getASTFactory(), this);
/* 107:181 */       int size = fromElements.size();
/* 108:    */       
/* 109:183 */       Iterator iterator = fromElements.iterator();
/* 110:184 */       for (int k = 0; iterator.hasNext(); k++)
/* 111:    */       {
/* 112:185 */         FromElement fromElement = (FromElement)iterator.next();
/* 113:187 */         if (fromElement.isFetch())
/* 114:    */         {
/* 115:188 */           FromElement origin = null;
/* 116:189 */           if (fromElement.getRealOrigin() == null)
/* 117:    */           {
/* 118:193 */             if (fromElement.getOrigin() == null) {
/* 119:194 */               throw new QueryException("Unable to determine origin of join fetch [" + fromElement.getDisplayText() + "]");
/* 120:    */             }
/* 121:197 */             origin = fromElement.getOrigin();
/* 122:    */           }
/* 123:    */           else
/* 124:    */           {
/* 125:201 */             origin = fromElement.getRealOrigin();
/* 126:    */           }
/* 127:203 */           if (!this.fromElementsForLoad.contains(origin)) {
/* 128:204 */             throw new QueryException("query specified join fetching, but the owner of the fetched association was not present in the select list [" + fromElement.getDisplayText() + "]");
/* 129:    */           }
/* 130:210 */           Type type = fromElement.getSelectType();
/* 131:211 */           addCollectionFromElement(fromElement);
/* 132:212 */           if (type != null)
/* 133:    */           {
/* 134:213 */             boolean collectionOfElements = fromElement.isCollectionOfValuesOrComponents();
/* 135:214 */             if (!collectionOfElements)
/* 136:    */             {
/* 137:216 */               fromElement.setIncludeSubclasses(true);
/* 138:217 */               this.fromElementsForLoad.add(fromElement);
/* 139:    */               
/* 140:    */ 
/* 141:220 */               String text = fromElement.renderIdentifierSelect(size, k);
/* 142:221 */               SelectExpressionImpl generatedExpr = (SelectExpressionImpl)appender.append(144, text, false);
/* 143:222 */               if (generatedExpr != null) {
/* 144:223 */                 generatedExpr.setFromElement(fromElement);
/* 145:    */               }
/* 146:    */             }
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:232 */       renderNonScalarSelects(collectSelectExpressions(), fromClause);
/* 151:    */     }
/* 152:235 */     if ((this.scalarSelect) || (getWalker().isShallowQuery())) {
/* 153:237 */       renderScalarSelects(selectExpressions, fromClause);
/* 154:    */     }
/* 155:240 */     finishInitialization(queryReturnTypeList);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private void finishInitialization(ArrayList queryReturnTypeList)
/* 159:    */   {
/* 160:244 */     this.queryReturnTypes = ((Type[])queryReturnTypeList.toArray(new Type[queryReturnTypeList.size()]));
/* 161:245 */     initializeColumnNames();
/* 162:246 */     this.prepared = true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void initializeColumnNames()
/* 166:    */   {
/* 167:255 */     this.columnNames = getSessionFactoryHelper().generateColumnNames(this.queryReturnTypes);
/* 168:256 */     this.columnNamesStartPositions = new int[this.columnNames.length];
/* 169:257 */     int startPosition = 1;
/* 170:258 */     for (int i = 0; i < this.columnNames.length; i++)
/* 171:    */     {
/* 172:259 */       this.columnNamesStartPositions[i] = startPosition;
/* 173:260 */       startPosition += this.columnNames[i].length;
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getColumnNamesStartPosition(int i)
/* 178:    */   {
/* 179:265 */     return this.columnNamesStartPositions[i];
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void initializeDerivedSelectClause(FromClause fromClause)
/* 183:    */     throws SemanticException
/* 184:    */   {
/* 185:274 */     if (this.prepared) {
/* 186:275 */       throw new IllegalStateException("SelectClause was already prepared!");
/* 187:    */     }
/* 188:282 */     List fromElements = fromClause.getProjectionList();
/* 189:    */     
/* 190:284 */     ASTAppender appender = new ASTAppender(getASTFactory(), this);
/* 191:285 */     int size = fromElements.size();
/* 192:286 */     ArrayList queryReturnTypeList = new ArrayList(size);
/* 193:    */     
/* 194:288 */     Iterator iterator = fromElements.iterator();
/* 195:289 */     for (int k = 0; iterator.hasNext(); k++)
/* 196:    */     {
/* 197:290 */       FromElement fromElement = (FromElement)iterator.next();
/* 198:291 */       Type type = fromElement.getSelectType();
/* 199:    */       
/* 200:293 */       addCollectionFromElement(fromElement);
/* 201:295 */       if (type != null)
/* 202:    */       {
/* 203:296 */         boolean collectionOfElements = fromElement.isCollectionOfValuesOrComponents();
/* 204:297 */         if (!collectionOfElements)
/* 205:    */         {
/* 206:298 */           if (!fromElement.isFetch()) {
/* 207:300 */             queryReturnTypeList.add(type);
/* 208:    */           }
/* 209:302 */           this.fromElementsForLoad.add(fromElement);
/* 210:    */           
/* 211:304 */           String text = fromElement.renderIdentifierSelect(size, k);
/* 212:305 */           SelectExpressionImpl generatedExpr = (SelectExpressionImpl)appender.append(144, text, false);
/* 213:306 */           if (generatedExpr != null) {
/* 214:307 */             generatedExpr.setFromElement(fromElement);
/* 215:    */           }
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:314 */     SelectExpression[] selectExpressions = collectSelectExpressions();
/* 220:316 */     if (getWalker().isShallowQuery()) {
/* 221:317 */       renderScalarSelects(selectExpressions, fromClause);
/* 222:    */     } else {
/* 223:320 */       renderNonScalarSelects(selectExpressions, fromClause);
/* 224:    */     }
/* 225:322 */     finishInitialization(queryReturnTypeList);
/* 226:    */   }
/* 227:    */   
/* 228:325 */   public static boolean VERSION2_SQL = false;
/* 229:    */   
/* 230:    */   private void addCollectionFromElement(FromElement fromElement)
/* 231:    */   {
/* 232:328 */     if ((fromElement.isFetch()) && (
/* 233:329 */       (fromElement.isCollectionJoin()) || (fromElement.getQueryableCollection() != null)))
/* 234:    */     {
/* 235:    */       String suffix;
/* 236:    */       String suffix;
/* 237:331 */       if (this.collectionFromElements == null)
/* 238:    */       {
/* 239:332 */         this.collectionFromElements = new ArrayList();
/* 240:333 */         suffix = VERSION2_SQL ? "__" : "0__";
/* 241:    */       }
/* 242:    */       else
/* 243:    */       {
/* 244:336 */         suffix = Integer.toString(this.collectionFromElements.size()) + "__";
/* 245:    */       }
/* 246:338 */       this.collectionFromElements.add(fromElement);
/* 247:339 */       fromElement.setCollectionSuffix(suffix);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   protected AST getFirstSelectExpression()
/* 252:    */   {
/* 253:345 */     AST n = getFirstChild();
/* 254:347 */     while ((n != null) && ((n.getType() == 16) || (n.getType() == 4))) {
/* 255:348 */       n = n.getNextSibling();
/* 256:    */     }
/* 257:350 */     return n;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private boolean isReturnableEntity(SelectExpression selectExpression)
/* 261:    */     throws SemanticException
/* 262:    */   {
/* 263:354 */     FromElement fromElement = selectExpression.getFromElement();
/* 264:355 */     boolean isFetchOrValueCollection = (fromElement != null) && ((fromElement.isFetch()) || (fromElement.isCollectionOfValuesOrComponents()));
/* 265:357 */     if (isFetchOrValueCollection) {
/* 266:358 */       return false;
/* 267:    */     }
/* 268:361 */     return selectExpression.isReturnableEntity();
/* 269:    */   }
/* 270:    */   
/* 271:    */   private void renderScalarSelects(SelectExpression[] se, FromClause currentFromClause)
/* 272:    */     throws SemanticException
/* 273:    */   {
/* 274:366 */     if (!currentFromClause.isSubQuery()) {
/* 275:367 */       for (int i = 0; i < se.length; i++)
/* 276:    */       {
/* 277:368 */         SelectExpression expr = se[i];
/* 278:369 */         expr.setScalarColumn(i);
/* 279:    */       }
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   private void initAliases(SelectExpression[] selectExpressions)
/* 284:    */   {
/* 285:375 */     if (this.aggregatedSelectExpression == null)
/* 286:    */     {
/* 287:376 */       this.aliases = new String[selectExpressions.length];
/* 288:377 */       for (int i = 0; i < selectExpressions.length; i++)
/* 289:    */       {
/* 290:378 */         String alias = selectExpressions[i].getAlias();
/* 291:379 */         this.aliases[i] = (alias == null ? Integer.toString(i) : alias);
/* 292:    */       }
/* 293:    */     }
/* 294:    */     else
/* 295:    */     {
/* 296:383 */       this.aliases = this.aggregatedSelectExpression.getAggregatedAliases();
/* 297:    */     }
/* 298:    */   }
/* 299:    */   
/* 300:    */   private void renderNonScalarSelects(SelectExpression[] selectExpressions, FromClause currentFromClause)
/* 301:    */     throws SemanticException
/* 302:    */   {
/* 303:389 */     ASTAppender appender = new ASTAppender(getASTFactory(), this);
/* 304:390 */     int size = selectExpressions.length;
/* 305:391 */     int nonscalarSize = 0;
/* 306:392 */     for (int i = 0; i < size; i++) {
/* 307:393 */       if (!selectExpressions[i].isScalar()) {
/* 308:393 */         nonscalarSize++;
/* 309:    */       }
/* 310:    */     }
/* 311:396 */     int j = 0;
/* 312:397 */     for (int i = 0; i < size; i++) {
/* 313:398 */       if (!selectExpressions[i].isScalar())
/* 314:    */       {
/* 315:399 */         SelectExpression expr = selectExpressions[i];
/* 316:400 */         FromElement fromElement = expr.getFromElement();
/* 317:401 */         if (fromElement != null)
/* 318:    */         {
/* 319:402 */           renderNonScalarIdentifiers(fromElement, nonscalarSize, j, expr, appender);
/* 320:403 */           j++;
/* 321:    */         }
/* 322:    */       }
/* 323:    */     }
/* 324:408 */     if (!currentFromClause.isSubQuery())
/* 325:    */     {
/* 326:410 */       int k = 0;
/* 327:411 */       for (int i = 0; i < size; i++) {
/* 328:412 */         if (!selectExpressions[i].isScalar())
/* 329:    */         {
/* 330:413 */           FromElement fromElement = selectExpressions[i].getFromElement();
/* 331:414 */           if (fromElement != null)
/* 332:    */           {
/* 333:415 */             renderNonScalarProperties(appender, fromElement, nonscalarSize, k);
/* 334:416 */             k++;
/* 335:    */           }
/* 336:    */         }
/* 337:    */       }
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   private void renderNonScalarIdentifiers(FromElement fromElement, int nonscalarSize, int j, SelectExpression expr, ASTAppender appender)
/* 342:    */   {
/* 343:424 */     String text = fromElement.renderIdentifierSelect(nonscalarSize, j);
/* 344:425 */     if (!fromElement.getFromClause().isSubQuery()) {
/* 345:426 */       if ((!this.scalarSelect) && (!getWalker().isShallowQuery())) {
/* 346:428 */         expr.setText(text);
/* 347:    */       } else {
/* 348:431 */         appender.append(142, text, false);
/* 349:    */       }
/* 350:    */     }
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void renderNonScalarProperties(ASTAppender appender, FromElement fromElement, int nonscalarSize, int k)
/* 354:    */   {
/* 355:437 */     String text = fromElement.renderPropertySelect(nonscalarSize, k);
/* 356:438 */     appender.append(142, text, false);
/* 357:439 */     if ((fromElement.getQueryableCollection() != null) && (fromElement.isFetch()))
/* 358:    */     {
/* 359:440 */       text = fromElement.renderCollectionSelectFragment(nonscalarSize, k);
/* 360:441 */       appender.append(142, text, false);
/* 361:    */     }
/* 362:444 */     ASTIterator iter = new ASTIterator(fromElement);
/* 363:445 */     while (iter.hasNext())
/* 364:    */     {
/* 365:446 */       FromElement child = (FromElement)iter.next();
/* 366:447 */       if ((child.isCollectionOfValuesOrComponents()) && (child.isFetch()))
/* 367:    */       {
/* 368:449 */         text = child.renderValueCollectionSelectFragment(nonscalarSize, nonscalarSize + k);
/* 369:450 */         appender.append(142, text, false);
/* 370:    */       }
/* 371:    */     }
/* 372:    */   }
/* 373:    */   
/* 374:    */   public List getCollectionFromElements()
/* 375:    */   {
/* 376:456 */     return this.collectionFromElements;
/* 377:    */   }
/* 378:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SelectClause
 * JD-Core Version:    0.7.0.1
 */