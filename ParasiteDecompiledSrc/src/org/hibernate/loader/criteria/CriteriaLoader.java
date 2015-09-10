/*   1:    */ package org.hibernate.loader.criteria;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.LockOptions;
/*  12:    */ import org.hibernate.QueryException;
/*  13:    */ import org.hibernate.ScrollMode;
/*  14:    */ import org.hibernate.ScrollableResults;
/*  15:    */ import org.hibernate.dialect.Dialect;
/*  16:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  17:    */ import org.hibernate.engine.spi.QueryParameters;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  20:    */ import org.hibernate.internal.CriteriaImpl;
/*  21:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  22:    */ import org.hibernate.loader.OuterJoinLoader;
/*  23:    */ import org.hibernate.persister.entity.Lockable;
/*  24:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  25:    */ import org.hibernate.transform.ResultTransformer;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ 
/*  28:    */ public class CriteriaLoader
/*  29:    */   extends OuterJoinLoader
/*  30:    */ {
/*  31:    */   private final CriteriaQueryTranslator translator;
/*  32:    */   private final Set querySpaces;
/*  33:    */   private final Type[] resultTypes;
/*  34:    */   private final String[] userAliases;
/*  35:    */   private final boolean[] includeInResultRow;
/*  36:    */   private final int resultRowLength;
/*  37:    */   
/*  38:    */   public CriteriaLoader(OuterJoinLoadable persister, SessionFactoryImplementor factory, CriteriaImpl criteria, String rootEntityName, LoadQueryInfluencers loadQueryInfluencers)
/*  39:    */     throws HibernateException
/*  40:    */   {
/*  41: 82 */     super(factory, loadQueryInfluencers);
/*  42:    */     
/*  43: 84 */     this.translator = new CriteriaQueryTranslator(factory, criteria, rootEntityName, "this_");
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 91 */     this.querySpaces = this.translator.getQuerySpaces();
/*  51:    */     
/*  52: 93 */     CriteriaJoinWalker walker = new CriteriaJoinWalker(persister, this.translator, factory, criteria, rootEntityName, loadQueryInfluencers);
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:102 */     initFromWalker(walker);
/*  62:    */     
/*  63:104 */     this.userAliases = walker.getUserAliases();
/*  64:105 */     this.resultTypes = walker.getResultTypes();
/*  65:106 */     this.includeInResultRow = walker.includeInResultRow();
/*  66:107 */     this.resultRowLength = ArrayHelper.countTrue(this.includeInResultRow);
/*  67:    */     
/*  68:109 */     postInstantiate();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ScrollableResults scroll(SessionImplementor session, ScrollMode scrollMode)
/*  72:    */     throws HibernateException
/*  73:    */   {
/*  74:115 */     QueryParameters qp = this.translator.getQueryParameters();
/*  75:116 */     qp.setScrollMode(scrollMode);
/*  76:117 */     return scroll(qp, this.resultTypes, null, session);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List list(SessionImplementor session)
/*  80:    */     throws HibernateException
/*  81:    */   {
/*  82:122 */     return list(session, this.translator.getQueryParameters(), this.querySpaces, this.resultTypes);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String[] getResultRowAliases()
/*  86:    */   {
/*  87:127 */     return this.userAliases;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected ResultTransformer resolveResultTransformer(ResultTransformer resultTransformer)
/*  91:    */   {
/*  92:131 */     return this.translator.getRootCriteria().getResultTransformer();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected boolean areResultSetRowsTransformedImmediately()
/*  96:    */   {
/*  97:135 */     return true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected boolean[] includeInResultRow()
/* 101:    */   {
/* 102:139 */     return this.includeInResultRow;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/* 106:    */     throws SQLException, HibernateException
/* 107:    */   {
/* 108:144 */     return resolveResultTransformer(transformer).transformTuple(getResultRow(row, rs, session), getResultRowAliases());
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected Object[] getResultRow(Object[] row, ResultSet rs, SessionImplementor session)
/* 112:    */     throws SQLException, HibernateException
/* 113:    */   {
/* 114:    */     Object[] result;
/* 115:153 */     if (this.translator.hasProjection())
/* 116:    */     {
/* 117:154 */       Type[] types = this.translator.getProjectedTypes();
/* 118:155 */       Object[] result = new Object[types.length];
/* 119:156 */       String[] columnAliases = this.translator.getProjectedColumnAliases();
/* 120:157 */       int i = 0;
/* 121:157 */       for (int pos = 0; i < result.length; i++)
/* 122:    */       {
/* 123:158 */         int numColumns = types[i].getColumnSpan(session.getFactory());
/* 124:159 */         if (numColumns > 1)
/* 125:    */         {
/* 126:160 */           String[] typeColumnAliases = ArrayHelper.slice(columnAliases, pos, numColumns);
/* 127:161 */           result[i] = types[i].nullSafeGet(rs, typeColumnAliases, session, null);
/* 128:    */         }
/* 129:    */         else
/* 130:    */         {
/* 131:164 */           result[i] = types[i].nullSafeGet(rs, columnAliases[pos], session, null);
/* 132:    */         }
/* 133:166 */         pos += numColumns;
/* 134:    */       }
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:170 */       result = toResultRow(row);
/* 139:    */     }
/* 140:172 */     return result;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private Object[] toResultRow(Object[] row)
/* 144:    */   {
/* 145:176 */     if (this.resultRowLength == row.length) {
/* 146:177 */       return row;
/* 147:    */     }
/* 148:180 */     Object[] result = new Object[this.resultRowLength];
/* 149:181 */     int j = 0;
/* 150:182 */     for (int i = 0; i < row.length; i++) {
/* 151:183 */       if (this.includeInResultRow[i] != 0) {
/* 152:183 */         result[(j++)] = row[i];
/* 153:    */       }
/* 154:    */     }
/* 155:185 */     return result;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Set getQuerySpaces()
/* 159:    */   {
/* 160:190 */     return this.querySpaces;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected String applyLocks(String sqlSelectString, LockOptions lockOptions, Dialect dialect)
/* 164:    */     throws QueryException
/* 165:    */   {
/* 166:194 */     if ((lockOptions == null) || ((lockOptions.getLockMode() == LockMode.NONE) && (lockOptions.getAliasLockCount() == 0))) {
/* 167:196 */       return sqlSelectString;
/* 168:    */     }
/* 169:199 */     LockOptions locks = new LockOptions(lockOptions.getLockMode());
/* 170:200 */     locks.setScope(lockOptions.getScope());
/* 171:201 */     locks.setTimeOut(lockOptions.getTimeOut());
/* 172:    */     
/* 173:203 */     Map keyColumnNames = dialect.forUpdateOfColumns() ? new HashMap() : null;
/* 174:204 */     String[] drivingSqlAliases = getAliases();
/* 175:205 */     for (int i = 0; i < drivingSqlAliases.length; i++)
/* 176:    */     {
/* 177:206 */       LockMode lockMode = lockOptions.getAliasSpecificLockMode(drivingSqlAliases[i]);
/* 178:207 */       if (lockMode != null)
/* 179:    */       {
/* 180:208 */         Lockable drivingPersister = (Lockable)getEntityPersisters()[i];
/* 181:209 */         String rootSqlAlias = drivingPersister.getRootTableAlias(drivingSqlAliases[i]);
/* 182:210 */         locks.setAliasSpecificLockMode(rootSqlAlias, lockMode);
/* 183:211 */         if (keyColumnNames != null) {
/* 184:212 */           keyColumnNames.put(rootSqlAlias, drivingPersister.getRootTableIdentifierColumnNames());
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:216 */     return dialect.applyLocksToSql(sqlSelectString, locks, keyColumnNames);
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected LockMode[] getLockModes(LockOptions lockOptions)
/* 192:    */   {
/* 193:220 */     String[] entityAliases = getAliases();
/* 194:221 */     if (entityAliases == null) {
/* 195:222 */       return null;
/* 196:    */     }
/* 197:224 */     int size = entityAliases.length;
/* 198:225 */     LockMode[] lockModesArray = new LockMode[size];
/* 199:226 */     for (int i = 0; i < size; i++)
/* 200:    */     {
/* 201:227 */       LockMode lockMode = lockOptions.getAliasSpecificLockMode(entityAliases[i]);
/* 202:228 */       lockModesArray[i] = (lockMode == null ? lockOptions.getLockMode() : lockMode);
/* 203:    */     }
/* 204:230 */     return lockModesArray;
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected boolean isSubselectLoadingEnabled()
/* 208:    */   {
/* 209:234 */     return hasSubselectLoadableCollections();
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected List getResultList(List results, ResultTransformer resultTransformer)
/* 213:    */   {
/* 214:238 */     return resolveResultTransformer(resultTransformer).transformList(results);
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.CriteriaLoader
 * JD-Core Version:    0.7.0.1
 */