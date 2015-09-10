/*   1:    */ package org.hibernate.persister.collection;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.cache.CacheException;
/*   9:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  10:    */ import org.hibernate.cfg.Configuration;
/*  11:    */ import org.hibernate.cfg.Settings;
/*  12:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  13:    */ import org.hibernate.dialect.Dialect;
/*  14:    */ import org.hibernate.engine.jdbc.batch.internal.BasicBatchKey;
/*  15:    */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*  16:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  17:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  18:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  19:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  22:    */ import org.hibernate.engine.spi.SubselectFetch;
/*  23:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  24:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  25:    */ import org.hibernate.jdbc.Expectation;
/*  26:    */ import org.hibernate.jdbc.Expectations;
/*  27:    */ import org.hibernate.loader.collection.BatchingCollectionInitializer;
/*  28:    */ import org.hibernate.loader.collection.CollectionInitializer;
/*  29:    */ import org.hibernate.loader.collection.SubselectOneToManyLoader;
/*  30:    */ import org.hibernate.loader.entity.CollectionElementLoader;
/*  31:    */ import org.hibernate.mapping.Collection;
/*  32:    */ import org.hibernate.mapping.KeyValue;
/*  33:    */ import org.hibernate.persister.entity.Joinable;
/*  34:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  35:    */ import org.hibernate.pretty.MessageHelper;
/*  36:    */ import org.hibernate.sql.Update;
/*  37:    */ import org.hibernate.type.CollectionType;
/*  38:    */ 
/*  39:    */ public class OneToManyPersister
/*  40:    */   extends AbstractCollectionPersister
/*  41:    */ {
/*  42:    */   private final boolean cascadeDeleteEnabled;
/*  43:    */   private final boolean keyIsNullable;
/*  44:    */   private final boolean keyIsUpdateable;
/*  45:    */   private BasicBatchKey deleteRowBatchKey;
/*  46:    */   private BasicBatchKey insertRowBatchKey;
/*  47:    */   
/*  48:    */   protected boolean isRowDeleteEnabled()
/*  49:    */   {
/*  50: 68 */     return (this.keyIsUpdateable) && (this.keyIsNullable);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected boolean isRowInsertEnabled()
/*  54:    */   {
/*  55: 73 */     return this.keyIsUpdateable;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean isCascadeDeleteEnabled()
/*  59:    */   {
/*  60: 77 */     return this.cascadeDeleteEnabled;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public OneToManyPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory)
/*  64:    */     throws MappingException, CacheException
/*  65:    */   {
/*  66: 85 */     super(collection, cacheAccessStrategy, cfg, factory);
/*  67: 86 */     this.cascadeDeleteEnabled = ((collection.getKey().isCascadeDeleteEnabled()) && (factory.getDialect().supportsCascadeDelete()));
/*  68:    */     
/*  69: 88 */     this.keyIsNullable = collection.getKey().isNullable();
/*  70: 89 */     this.keyIsUpdateable = collection.getKey().isUpdateable();
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected String generateDeleteString()
/*  74:    */   {
/*  75: 98 */     Update update = new Update(getDialect()).setTableName(this.qualifiedTableName).addColumns(this.keyColumnNames, "null").addPrimaryKeyColumns(this.keyColumnNames);
/*  76:103 */     if ((this.hasIndex) && (!this.indexContainsFormula)) {
/*  77:103 */       update.addColumns(this.indexColumnNames, "null");
/*  78:    */     }
/*  79:105 */     if (this.hasWhere) {
/*  80:105 */       update.setWhere(this.sqlWhereString);
/*  81:    */     }
/*  82:107 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  83:108 */       update.setComment("delete one-to-many " + getRole());
/*  84:    */     }
/*  85:111 */     return update.toStatementString();
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected String generateInsertRowString()
/*  89:    */   {
/*  90:120 */     Update update = new Update(getDialect()).setTableName(this.qualifiedTableName).addColumns(this.keyColumnNames);
/*  91:124 */     if ((this.hasIndex) && (!this.indexContainsFormula)) {
/*  92:124 */       update.addColumns(this.indexColumnNames);
/*  93:    */     }
/*  94:127 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  95:128 */       update.setComment("create one-to-many row " + getRole());
/*  96:    */     }
/*  97:131 */     return update.addPrimaryKeyColumns(this.elementColumnNames, this.elementColumnWriters).toStatementString();
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected String generateUpdateRowString()
/* 101:    */   {
/* 102:140 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected String generateDeleteRowString()
/* 106:    */   {
/* 107:150 */     Update update = new Update(getDialect()).setTableName(this.qualifiedTableName).addColumns(this.keyColumnNames, "null");
/* 108:154 */     if ((this.hasIndex) && (!this.indexContainsFormula)) {
/* 109:154 */       update.addColumns(this.indexColumnNames, "null");
/* 110:    */     }
/* 111:156 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 112:157 */       update.setComment("delete one-to-many row " + getRole());
/* 113:    */     }
/* 114:163 */     String[] rowSelectColumnNames = ArrayHelper.join(this.keyColumnNames, this.elementColumnNames);
/* 115:164 */     return update.addPrimaryKeyColumns(rowSelectColumnNames).toStatementString();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean consumesEntityAlias()
/* 119:    */   {
/* 120:169 */     return true;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean consumesCollectionAlias()
/* 124:    */   {
/* 125:172 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isOneToMany()
/* 129:    */   {
/* 130:176 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isManyToMany()
/* 134:    */   {
/* 135:181 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected int doUpdateRows(Serializable id, PersistentCollection collection, SessionImplementor session)
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:194 */       int count = 0;
/* 143:195 */       if (isRowDeleteEnabled())
/* 144:    */       {
/* 145:196 */         Expectation deleteExpectation = Expectations.appropriateExpectation(getDeleteCheckStyle());
/* 146:197 */         boolean useBatch = deleteExpectation.canBeBatched();
/* 147:198 */         if ((useBatch) && (this.deleteRowBatchKey == null)) {
/* 148:199 */           this.deleteRowBatchKey = new BasicBatchKey(getRole() + "#DELETEROW", deleteExpectation);
/* 149:    */         }
/* 150:204 */         String sql = getSQLDeleteRowString();
/* 151:    */         
/* 152:206 */         PreparedStatement st = null;
/* 153:    */         try
/* 154:    */         {
/* 155:209 */           int i = 0;
/* 156:210 */           Iterator entries = collection.entries(this);
/* 157:211 */           int offset = 1;
/* 158:212 */           while (entries.hasNext())
/* 159:    */           {
/* 160:213 */             Object entry = entries.next();
/* 161:214 */             if (collection.needsUpdating(entry, i, this.elementType))
/* 162:    */             {
/* 163:215 */               if (useBatch) {
/* 164:216 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteRowBatchKey).getBatchStatement(sql, isDeleteCallable());
/* 165:    */               } else {
/* 166:222 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, isDeleteCallable());
/* 167:    */               }
/* 168:227 */               int loc = writeKey(st, id, offset, session);
/* 169:228 */               writeElementToWhere(st, collection.getSnapshotElement(entry, i), loc, session);
/* 170:229 */               if (useBatch) {
/* 171:230 */                 session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteRowBatchKey).addToBatch();
/* 172:    */               } else {
/* 173:236 */                 deleteExpectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 174:    */               }
/* 175:238 */               count++;
/* 176:    */             }
/* 177:240 */             i++;
/* 178:    */           }
/* 179:    */         }
/* 180:    */         catch (SQLException e)
/* 181:    */         {
/* 182:244 */           if (useBatch) {
/* 183:245 */             session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 184:    */           }
/* 185:247 */           throw e;
/* 186:    */         }
/* 187:    */         finally
/* 188:    */         {
/* 189:250 */           if (!useBatch) {
/* 190:251 */             st.close();
/* 191:    */           }
/* 192:    */         }
/* 193:    */       }
/* 194:256 */       if (isRowInsertEnabled())
/* 195:    */       {
/* 196:257 */         Expectation insertExpectation = Expectations.appropriateExpectation(getInsertCheckStyle());
/* 197:258 */         boolean useBatch = insertExpectation.canBeBatched();
/* 198:259 */         boolean callable = isInsertCallable();
/* 199:260 */         if ((useBatch) && (this.insertRowBatchKey == null)) {
/* 200:261 */           this.insertRowBatchKey = new BasicBatchKey(getRole() + "#INSERTROW", insertExpectation);
/* 201:    */         }
/* 202:266 */         String sql = getSQLInsertRowString();
/* 203:    */         
/* 204:268 */         PreparedStatement st = null;
/* 205:    */         try
/* 206:    */         {
/* 207:271 */           int i = 0;
/* 208:272 */           Iterator entries = collection.entries(this);
/* 209:273 */           while (entries.hasNext())
/* 210:    */           {
/* 211:274 */             Object entry = entries.next();
/* 212:275 */             int offset = 1;
/* 213:276 */             if (collection.needsUpdating(entry, i, this.elementType))
/* 214:    */             {
/* 215:277 */               if (useBatch) {
/* 216:278 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.insertRowBatchKey).getBatchStatement(sql, callable);
/* 217:    */               } else {
/* 218:284 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 219:    */               }
/* 220:290 */               offset += insertExpectation.prepare(st);
/* 221:    */               
/* 222:292 */               int loc = writeKey(st, id, offset, session);
/* 223:293 */               if ((this.hasIndex) && (!this.indexContainsFormula)) {
/* 224:294 */                 loc = writeIndexToWhere(st, collection.getIndex(entry, i, this), loc, session);
/* 225:    */               }
/* 226:297 */               writeElementToWhere(st, collection.getElement(entry), loc, session);
/* 227:299 */               if (useBatch) {
/* 228:300 */                 session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.insertRowBatchKey).addToBatch();
/* 229:    */               } else {
/* 230:303 */                 insertExpectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 231:    */               }
/* 232:305 */               count++;
/* 233:    */             }
/* 234:307 */             i++;
/* 235:    */           }
/* 236:    */         }
/* 237:    */         catch (SQLException sqle)
/* 238:    */         {
/* 239:311 */           if (useBatch) {
/* 240:312 */             session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 241:    */           }
/* 242:314 */           throw sqle;
/* 243:    */         }
/* 244:    */         finally
/* 245:    */         {
/* 246:317 */           if (!useBatch) {
/* 247:318 */             st.close();
/* 248:    */           }
/* 249:    */         }
/* 250:    */       }
/* 251:323 */       return count;
/* 252:    */     }
/* 253:    */     catch (SQLException sqle)
/* 254:    */     {
/* 255:326 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not update collection rows: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLInsertRowString());
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String selectFragment(Joinable rhs, String rhsAlias, String lhsAlias, String entitySuffix, String collectionSuffix, boolean includeCollectionColumns)
/* 260:    */   {
/* 261:342 */     StringBuffer buf = new StringBuffer();
/* 262:343 */     if (includeCollectionColumns) {
/* 263:345 */       buf.append(selectFragment(lhsAlias, collectionSuffix)).append(", ");
/* 264:    */     }
/* 265:348 */     OuterJoinLoadable ojl = (OuterJoinLoadable)getElementPersister();
/* 266:349 */     return ojl.selectFragment(lhsAlias, entitySuffix);
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected CollectionInitializer createCollectionInitializer(LoadQueryInfluencers loadQueryInfluencers)
/* 270:    */     throws MappingException
/* 271:    */   {
/* 272:361 */     return BatchingCollectionInitializer.createBatchingOneToManyInitializer(this, this.batchSize, getFactory(), loadQueryInfluencers);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String fromJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 276:    */   {
/* 277:367 */     return ((Joinable)getElementPersister()).fromJoinFragment(alias, innerJoin, includeSubclasses);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String whereJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 281:    */   {
/* 282:373 */     return ((Joinable)getElementPersister()).whereJoinFragment(alias, innerJoin, includeSubclasses);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getTableName()
/* 286:    */   {
/* 287:378 */     return ((Joinable)getElementPersister()).getTableName();
/* 288:    */   }
/* 289:    */   
/* 290:    */   public String filterFragment(String alias)
/* 291:    */     throws MappingException
/* 292:    */   {
/* 293:383 */     String result = super.filterFragment(alias);
/* 294:384 */     if ((getElementPersister() instanceof Joinable)) {
/* 295:385 */       result = result + ((Joinable)getElementPersister()).oneToManyFilterFragment(alias);
/* 296:    */     }
/* 297:387 */     return result;
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected CollectionInitializer createSubselectInitializer(SubselectFetch subselect, SessionImplementor session)
/* 301:    */   {
/* 302:393 */     return new SubselectOneToManyLoader(this, subselect.toSubselectString(getCollectionType().getLHSPropertyName()), subselect.getResult(), subselect.getQueryParameters(), subselect.getNamedParameterLocMap(), session.getFactory(), session.getLoadQueryInfluencers());
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Object getElementByIndex(Serializable key, Object index, SessionImplementor session, Object owner)
/* 306:    */   {
/* 307:406 */     return new CollectionElementLoader(this, getFactory(), session.getLoadQueryInfluencers()).loadElement(session, key, incrementIndexByBase(index));
/* 308:    */   }
/* 309:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.OneToManyPersister
 * JD-Core Version:    0.7.0.1
 */