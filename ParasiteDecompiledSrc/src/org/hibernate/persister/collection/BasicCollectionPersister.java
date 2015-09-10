/*   1:    */ package org.hibernate.persister.collection;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.cache.CacheException;
/*  10:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  11:    */ import org.hibernate.cfg.Configuration;
/*  12:    */ import org.hibernate.cfg.Settings;
/*  13:    */ import org.hibernate.collection.spi.PersistentCollection;
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
/*  29:    */ import org.hibernate.loader.collection.SubselectCollectionLoader;
/*  30:    */ import org.hibernate.mapping.Collection;
/*  31:    */ import org.hibernate.persister.entity.Joinable;
/*  32:    */ import org.hibernate.pretty.MessageHelper;
/*  33:    */ import org.hibernate.sql.Delete;
/*  34:    */ import org.hibernate.sql.Insert;
/*  35:    */ import org.hibernate.sql.SelectFragment;
/*  36:    */ import org.hibernate.sql.Update;
/*  37:    */ import org.hibernate.type.AssociationType;
/*  38:    */ import org.hibernate.type.CollectionType;
/*  39:    */ import org.hibernate.type.Type;
/*  40:    */ 
/*  41:    */ public class BasicCollectionPersister
/*  42:    */   extends AbstractCollectionPersister
/*  43:    */ {
/*  44:    */   private BasicBatchKey updateBatchKey;
/*  45:    */   
/*  46:    */   public boolean isCascadeDeleteEnabled()
/*  47:    */   {
/*  48: 65 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public BasicCollectionPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory)
/*  52:    */     throws MappingException, CacheException
/*  53:    */   {
/*  54: 73 */     super(collection, cacheAccessStrategy, cfg, factory);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String generateDeleteString()
/*  58:    */   {
/*  59: 82 */     Delete delete = new Delete().setTableName(this.qualifiedTableName).addPrimaryKeyColumns(this.keyColumnNames);
/*  60: 86 */     if (this.hasWhere) {
/*  61: 86 */       delete.setWhere(this.sqlWhereString);
/*  62:    */     }
/*  63: 88 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  64: 89 */       delete.setComment("delete collection " + getRole());
/*  65:    */     }
/*  66: 92 */     return delete.toStatementString();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String generateInsertRowString()
/*  70:    */   {
/*  71:101 */     Insert insert = new Insert(getDialect()).setTableName(this.qualifiedTableName).addColumns(this.keyColumnNames);
/*  72:105 */     if (this.hasIdentifier) {
/*  73:105 */       insert.addColumn(this.identifierColumnName);
/*  74:    */     }
/*  75:107 */     if (this.hasIndex) {
/*  76:108 */       insert.addColumns(this.indexColumnNames, this.indexColumnIsSettable);
/*  77:    */     }
/*  78:111 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  79:112 */       insert.setComment("insert collection row " + getRole());
/*  80:    */     }
/*  81:116 */     insert.addColumns(this.elementColumnNames, this.elementColumnIsSettable, this.elementColumnWriters);
/*  82:    */     
/*  83:    */ 
/*  84:119 */     return insert.toStatementString();
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected String generateUpdateRowString()
/*  88:    */   {
/*  89:128 */     Update update = new Update(getDialect()).setTableName(this.qualifiedTableName);
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:132 */     update.addColumns(this.elementColumnNames, this.elementColumnIsSettable, this.elementColumnWriters);
/*  94:135 */     if (this.hasIdentifier)
/*  95:    */     {
/*  96:136 */       update.addPrimaryKeyColumns(new String[] { this.identifierColumnName });
/*  97:    */     }
/*  98:138 */     else if ((this.hasIndex) && (!this.indexContainsFormula))
/*  99:    */     {
/* 100:139 */       update.addPrimaryKeyColumns(ArrayHelper.join(this.keyColumnNames, this.indexColumnNames));
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:142 */       update.addPrimaryKeyColumns(this.keyColumnNames);
/* 105:143 */       update.addPrimaryKeyColumns(this.elementColumnNames, this.elementColumnIsInPrimaryKey, this.elementColumnWriters);
/* 106:    */     }
/* 107:146 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 108:147 */       update.setComment("update collection row " + getRole());
/* 109:    */     }
/* 110:150 */     return update.toStatementString();
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected String generateDeleteRowString()
/* 114:    */   {
/* 115:159 */     Delete delete = new Delete().setTableName(this.qualifiedTableName);
/* 116:162 */     if (this.hasIdentifier)
/* 117:    */     {
/* 118:163 */       delete.addPrimaryKeyColumns(new String[] { this.identifierColumnName });
/* 119:    */     }
/* 120:165 */     else if ((this.hasIndex) && (!this.indexContainsFormula))
/* 121:    */     {
/* 122:166 */       delete.addPrimaryKeyColumns(ArrayHelper.join(this.keyColumnNames, this.indexColumnNames));
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:169 */       delete.addPrimaryKeyColumns(this.keyColumnNames);
/* 127:170 */       delete.addPrimaryKeyColumns(this.elementColumnNames, this.elementColumnIsInPrimaryKey, this.elementColumnWriters);
/* 128:    */     }
/* 129:173 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 130:174 */       delete.setComment("delete collection row " + getRole());
/* 131:    */     }
/* 132:177 */     return delete.toStatementString();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean consumesEntityAlias()
/* 136:    */   {
/* 137:181 */     return false;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean consumesCollectionAlias()
/* 141:    */   {
/* 142:186 */     return true;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isOneToMany()
/* 146:    */   {
/* 147:190 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isManyToMany()
/* 151:    */   {
/* 152:195 */     return this.elementType.isEntityType();
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected int doUpdateRows(Serializable id, PersistentCollection collection, SessionImplementor session)
/* 156:    */     throws HibernateException
/* 157:    */   {
/* 158:204 */     if (ArrayHelper.isAllFalse(this.elementColumnIsSettable)) {
/* 159:204 */       return 0;
/* 160:    */     }
/* 161:    */     try
/* 162:    */     {
/* 163:207 */       PreparedStatement st = null;
/* 164:208 */       Expectation expectation = Expectations.appropriateExpectation(getUpdateCheckStyle());
/* 165:209 */       boolean callable = isUpdateCallable();
/* 166:210 */       boolean useBatch = expectation.canBeBatched();
/* 167:211 */       Iterator entries = collection.entries(this);
/* 168:212 */       String sql = getSQLUpdateRowString();
/* 169:213 */       int i = 0;
/* 170:214 */       int count = 0;
/* 171:215 */       while (entries.hasNext())
/* 172:    */       {
/* 173:216 */         Object entry = entries.next();
/* 174:217 */         if (collection.needsUpdating(entry, i, this.elementType))
/* 175:    */         {
/* 176:218 */           int offset = 1;
/* 177:220 */           if (useBatch)
/* 178:    */           {
/* 179:221 */             if (this.updateBatchKey == null) {
/* 180:222 */               this.updateBatchKey = new BasicBatchKey(getRole() + "#UPDATE", expectation);
/* 181:    */             }
/* 182:227 */             st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.updateBatchKey).getBatchStatement(sql, callable);
/* 183:    */           }
/* 184:    */           else
/* 185:    */           {
/* 186:233 */             st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 187:    */           }
/* 188:    */           try
/* 189:    */           {
/* 190:240 */             offset += expectation.prepare(st);
/* 191:241 */             int loc = writeElement(st, collection.getElement(entry), offset, session);
/* 192:242 */             if (this.hasIdentifier)
/* 193:    */             {
/* 194:243 */               writeIdentifier(st, collection.getIdentifier(entry, i), loc, session);
/* 195:    */             }
/* 196:    */             else
/* 197:    */             {
/* 198:246 */               loc = writeKey(st, id, loc, session);
/* 199:247 */               if ((this.hasIndex) && (!this.indexContainsFormula)) {
/* 200:248 */                 writeIndexToWhere(st, collection.getIndex(entry, i, this), loc, session);
/* 201:    */               } else {
/* 202:251 */                 writeElementToWhere(st, collection.getSnapshotElement(entry, i), loc, session);
/* 203:    */               }
/* 204:    */             }
/* 205:255 */             if (useBatch) {
/* 206:256 */               session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.updateBatchKey).addToBatch();
/* 207:    */             } else {
/* 208:262 */               expectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 209:    */             }
/* 210:    */           }
/* 211:    */           catch (SQLException sqle)
/* 212:    */           {
/* 213:266 */             if (useBatch) {
/* 214:267 */               session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 215:    */             }
/* 216:269 */             throw sqle;
/* 217:    */           }
/* 218:    */           finally
/* 219:    */           {
/* 220:272 */             if (!useBatch) {
/* 221:273 */               st.close();
/* 222:    */             }
/* 223:    */           }
/* 224:276 */           count++;
/* 225:    */         }
/* 226:278 */         i++;
/* 227:    */       }
/* 228:280 */       return count;
/* 229:    */     }
/* 230:    */     catch (SQLException sqle)
/* 231:    */     {
/* 232:283 */       throw getSQLExceptionHelper().convert(sqle, "could not update collection rows: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLUpdateRowString());
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String selectFragment(Joinable rhs, String rhsAlias, String lhsAlias, String entitySuffix, String collectionSuffix, boolean includeCollectionColumns)
/* 237:    */   {
/* 238:300 */     if ((rhs != null) && (isManyToMany()) && (!rhs.isCollection()))
/* 239:    */     {
/* 240:301 */       AssociationType elementType = (AssociationType)getElementType();
/* 241:302 */       if (rhs.equals(elementType.getAssociatedJoinable(getFactory()))) {
/* 242:303 */         return manyToManySelectFragment(rhs, rhsAlias, lhsAlias, collectionSuffix);
/* 243:    */       }
/* 244:    */     }
/* 245:306 */     return includeCollectionColumns ? selectFragment(lhsAlias, collectionSuffix) : "";
/* 246:    */   }
/* 247:    */   
/* 248:    */   private String manyToManySelectFragment(Joinable rhs, String rhsAlias, String lhsAlias, String collectionSuffix)
/* 249:    */   {
/* 250:314 */     SelectFragment frag = generateSelectFragment(lhsAlias, collectionSuffix);
/* 251:    */     
/* 252:316 */     String[] elementColumnNames = rhs.getKeyColumnNames();
/* 253:317 */     frag.addColumns(rhsAlias, elementColumnNames, this.elementColumnAliases);
/* 254:318 */     appendIndexColumns(frag, lhsAlias);
/* 255:319 */     appendIdentifierColumns(frag, lhsAlias);
/* 256:    */     
/* 257:321 */     return frag.toFragmentString().substring(2);
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected CollectionInitializer createCollectionInitializer(LoadQueryInfluencers loadQueryInfluencers)
/* 261:    */     throws MappingException
/* 262:    */   {
/* 263:333 */     return BatchingCollectionInitializer.createBatchingCollectionInitializer(this, this.batchSize, getFactory(), loadQueryInfluencers);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String fromJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 267:    */   {
/* 268:337 */     return "";
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String whereJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 272:    */   {
/* 273:341 */     return "";
/* 274:    */   }
/* 275:    */   
/* 276:    */   protected CollectionInitializer createSubselectInitializer(SubselectFetch subselect, SessionImplementor session)
/* 277:    */   {
/* 278:346 */     return new SubselectCollectionLoader(this, subselect.toSubselectString(getCollectionType().getLHSPropertyName()), subselect.getResult(), subselect.getQueryParameters(), subselect.getNamedParameterLocMap(), session.getFactory(), session.getLoadQueryInfluencers());
/* 279:    */   }
/* 280:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.BasicCollectionPersister
 * JD-Core Version:    0.7.0.1
 */