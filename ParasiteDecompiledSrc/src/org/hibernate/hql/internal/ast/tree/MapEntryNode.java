/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.hql.internal.NameGenerator;
/*  11:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  12:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  13:    */ import org.hibernate.persister.entity.Queryable;
/*  14:    */ import org.hibernate.sql.AliasGenerator;
/*  15:    */ import org.hibernate.sql.SelectExpression;
/*  16:    */ import org.hibernate.sql.SelectFragment;
/*  17:    */ import org.hibernate.transform.BasicTransformerAdapter;
/*  18:    */ import org.hibernate.transform.ResultTransformer;
/*  19:    */ import org.hibernate.type.EntityType;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ 
/*  22:    */ public class MapEntryNode
/*  23:    */   extends AbstractMapComponentNode
/*  24:    */   implements AggregatedSelectExpression
/*  25:    */ {
/*  26:    */   private int scalarColumnIndex;
/*  27:    */   private List types;
/*  28:    */   
/*  29:    */   private static class LocalAliasGenerator
/*  30:    */     implements AliasGenerator
/*  31:    */   {
/*  32:    */     private final int base;
/*  33: 51 */     private int counter = 0;
/*  34:    */     
/*  35:    */     private LocalAliasGenerator(int base)
/*  36:    */     {
/*  37: 54 */       this.base = base;
/*  38:    */     }
/*  39:    */     
/*  40:    */     public String generateAlias(String sqlExpression)
/*  41:    */     {
/*  42: 58 */       return NameGenerator.scalarName(this.base, this.counter++);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected String expressionDescription()
/*  47:    */   {
/*  48: 65 */     return "entry(*)";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Class getAggregationResultType()
/*  52:    */   {
/*  53: 70 */     return Map.Entry.class;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected Type resolveType(QueryableCollection collectionPersister)
/*  57:    */   {
/*  58: 74 */     Type keyType = collectionPersister.getIndexType();
/*  59: 75 */     Type valueType = collectionPersister.getElementType();
/*  60: 76 */     this.types.add(keyType);
/*  61: 77 */     this.types.add(valueType);
/*  62: 78 */     this.mapEntryBuilder = new MapEntryBuilder(null);
/*  63:    */     
/*  64:    */ 
/*  65: 81 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String[] resolveColumns(QueryableCollection collectionPersister)
/*  69:    */   {
/*  70: 85 */     List selections = new ArrayList();
/*  71: 86 */     determineKeySelectExpressions(collectionPersister, selections);
/*  72: 87 */     determineValueSelectExpressions(collectionPersister, selections);
/*  73:    */     
/*  74: 89 */     String text = "";
/*  75: 90 */     String[] columns = new String[selections.size()];
/*  76: 91 */     for (int i = 0; i < selections.size(); i++)
/*  77:    */     {
/*  78: 92 */       SelectExpression selectExpression = (SelectExpression)selections.get(i);
/*  79: 93 */       text = text + ", " + selectExpression.getExpression() + " as " + selectExpression.getAlias();
/*  80: 94 */       columns[i] = selectExpression.getExpression();
/*  81:    */     }
/*  82: 97 */     text = text.substring(2);
/*  83: 98 */     setText(text);
/*  84: 99 */     setResolved();
/*  85:100 */     return columns;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void determineKeySelectExpressions(QueryableCollection collectionPersister, List selections)
/*  89:    */   {
/*  90:104 */     AliasGenerator aliasGenerator = new LocalAliasGenerator(0, null);
/*  91:105 */     appendSelectExpressions(collectionPersister.getIndexColumnNames(), selections, aliasGenerator);
/*  92:106 */     Type keyType = collectionPersister.getIndexType();
/*  93:107 */     if (keyType.isAssociationType())
/*  94:    */     {
/*  95:108 */       EntityType entityType = (EntityType)keyType;
/*  96:109 */       Queryable keyEntityPersister = (Queryable)sfi().getEntityPersister(entityType.getAssociatedEntityName(sfi()));
/*  97:    */       
/*  98:    */ 
/*  99:112 */       SelectFragment fragment = keyEntityPersister.propertySelectFragmentFragment(collectionTableAlias(), null, false);
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:117 */       appendSelectExpressions(fragment, selections, aliasGenerator);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void appendSelectExpressions(String[] columnNames, List selections, AliasGenerator aliasGenerator)
/* 109:    */   {
/* 110:122 */     for (int i = 0; i < columnNames.length; i++) {
/* 111:123 */       selections.add(new BasicSelectExpression(collectionTableAlias() + '.' + columnNames[i], aliasGenerator.generateAlias(columnNames[i]), null));
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void appendSelectExpressions(SelectFragment fragment, List selections, AliasGenerator aliasGenerator)
/* 116:    */   {
/* 117:133 */     Iterator itr = fragment.getColumns().iterator();
/* 118:134 */     while (itr.hasNext())
/* 119:    */     {
/* 120:135 */       String column = (String)itr.next();
/* 121:136 */       selections.add(new BasicSelectExpression(column, aliasGenerator.generateAlias(column), null));
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void determineValueSelectExpressions(QueryableCollection collectionPersister, List selections)
/* 126:    */   {
/* 127:143 */     AliasGenerator aliasGenerator = new LocalAliasGenerator(1, null);
/* 128:144 */     appendSelectExpressions(collectionPersister.getElementColumnNames(), selections, aliasGenerator);
/* 129:145 */     Type valueType = collectionPersister.getElementType();
/* 130:146 */     if (valueType.isAssociationType())
/* 131:    */     {
/* 132:147 */       EntityType valueEntityType = (EntityType)valueType;
/* 133:148 */       Queryable valueEntityPersister = (Queryable)sfi().getEntityPersister(valueEntityType.getAssociatedEntityName(sfi()));
/* 134:    */       
/* 135:    */ 
/* 136:151 */       SelectFragment fragment = valueEntityPersister.propertySelectFragmentFragment(elementTableAlias(), null, false);
/* 137:    */       
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:156 */       appendSelectExpressions(fragment, selections, aliasGenerator);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   private String collectionTableAlias()
/* 146:    */   {
/* 147:161 */     return getFromElement().getCollectionTableAlias() != null ? getFromElement().getCollectionTableAlias() : getFromElement().getTableAlias();
/* 148:    */   }
/* 149:    */   
/* 150:    */   private String elementTableAlias()
/* 151:    */   {
/* 152:167 */     return getFromElement().getTableAlias();
/* 153:    */   }
/* 154:    */   
/* 155:    */   private static class BasicSelectExpression
/* 156:    */     implements SelectExpression
/* 157:    */   {
/* 158:    */     private final String expression;
/* 159:    */     private final String alias;
/* 160:    */     
/* 161:    */     private BasicSelectExpression(String expression, String alias)
/* 162:    */     {
/* 163:175 */       this.expression = expression;
/* 164:176 */       this.alias = alias;
/* 165:    */     }
/* 166:    */     
/* 167:    */     public String getExpression()
/* 168:    */     {
/* 169:180 */       return this.expression;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public String getAlias()
/* 173:    */     {
/* 174:184 */       return this.alias;
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public SessionFactoryImplementor sfi()
/* 179:    */   {
/* 180:189 */     return getSessionFactoryHelper().getFactory();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setText(String s)
/* 184:    */   {
/* 185:193 */     if (isResolved()) {
/* 186:194 */       return;
/* 187:    */     }
/* 188:196 */     super.setText(s);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setScalarColumn(int i)
/* 192:    */     throws SemanticException
/* 193:    */   {
/* 194:200 */     this.scalarColumnIndex = i;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int getScalarColumnIndex()
/* 198:    */   {
/* 199:204 */     return this.scalarColumnIndex;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isScalar()
/* 203:    */   {
/* 204:212 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public MapEntryNode()
/* 208:    */   {
/* 209: 62 */     this.scalarColumnIndex = -1;
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:215 */     this.types = new ArrayList(4);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public List getAggregatedSelectionTypeList()
/* 366:    */   {
/* 367:218 */     return this.types;
/* 368:    */   }
/* 369:    */   
/* 370:221 */   private static final String[] ALIASES = { null, null };
/* 371:    */   private MapEntryBuilder mapEntryBuilder;
/* 372:    */   
/* 373:    */   public String[] getAggregatedAliases()
/* 374:    */   {
/* 375:224 */     return ALIASES;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public ResultTransformer getResultTransformer()
/* 379:    */   {
/* 380:230 */     return this.mapEntryBuilder;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setScalarColumnText(int i)
/* 384:    */     throws SemanticException
/* 385:    */   {}
/* 386:    */   
/* 387:    */   private static class MapEntryBuilder
/* 388:    */     extends BasicTransformerAdapter
/* 389:    */   {
/* 390:    */     public Object transformTuple(Object[] tuple, String[] aliases)
/* 391:    */     {
/* 392:235 */       if (tuple.length != 2) {
/* 393:236 */         throw new HibernateException("Expecting exactly 2 tuples to transform into Map.Entry");
/* 394:    */       }
/* 395:238 */       return new MapEntryNode.EntryAdapter(tuple[0], tuple[1], null);
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   private static class EntryAdapter
/* 400:    */     implements Map.Entry
/* 401:    */   {
/* 402:    */     private final Object key;
/* 403:    */     private Object value;
/* 404:    */     
/* 405:    */     private EntryAdapter(Object key, Object value)
/* 406:    */     {
/* 407:247 */       this.key = key;
/* 408:248 */       this.value = value;
/* 409:    */     }
/* 410:    */     
/* 411:    */     public Object getValue()
/* 412:    */     {
/* 413:252 */       return this.value;
/* 414:    */     }
/* 415:    */     
/* 416:    */     public Object getKey()
/* 417:    */     {
/* 418:256 */       return this.key;
/* 419:    */     }
/* 420:    */     
/* 421:    */     public Object setValue(Object value)
/* 422:    */     {
/* 423:260 */       Object old = this.value;
/* 424:261 */       this.value = value;
/* 425:262 */       return old;
/* 426:    */     }
/* 427:    */     
/* 428:    */     public boolean equals(Object o)
/* 429:    */     {
/* 430:267 */       if (this == o) {
/* 431:268 */         return true;
/* 432:    */       }
/* 433:270 */       if ((o == null) || (getClass() != o.getClass())) {
/* 434:271 */         return false;
/* 435:    */       }
/* 436:273 */       EntryAdapter that = (EntryAdapter)o;
/* 437:    */       
/* 438:    */ 
/* 439:276 */       return (this.key == null ? that.key == null : this.key.equals(that.key)) && (this.value == null ? that.value == null : this.value.equals(that.value));
/* 440:    */     }
/* 441:    */     
/* 442:    */     public int hashCode()
/* 443:    */     {
/* 444:282 */       int keyHash = this.key == null ? 0 : this.key.hashCode();
/* 445:283 */       int valueHash = this.value == null ? 0 : this.value.hashCode();
/* 446:284 */       return keyHash ^ valueHash;
/* 447:    */     }
/* 448:    */   }
/* 449:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.MapEntryNode
 * JD-Core Version:    0.7.0.1
 */