/*   1:    */ package org.hibernate.loader.custom.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.loader.custom.CustomQuery;
/*  15:    */ import org.hibernate.persister.collection.SQLLoadableCollection;
/*  16:    */ import org.hibernate.persister.entity.SQLLoadable;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class SQLCustomQuery
/*  20:    */   implements CustomQuery
/*  21:    */ {
/*  22: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SQLCustomQuery.class.getName());
/*  23:    */   private final String sql;
/*  24: 58 */   private final Set querySpaces = new HashSet();
/*  25: 59 */   private final Map namedParameterBindPoints = new HashMap();
/*  26: 60 */   private final List customQueryReturns = new ArrayList();
/*  27:    */   
/*  28:    */   public String getSQL()
/*  29:    */   {
/*  30: 64 */     return this.sql;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Set getQuerySpaces()
/*  34:    */   {
/*  35: 68 */     return this.querySpaces;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Map getNamedParameterBindPoints()
/*  39:    */   {
/*  40: 72 */     return this.namedParameterBindPoints;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List getCustomQueryReturns()
/*  44:    */   {
/*  45: 76 */     return this.customQueryReturns;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public SQLCustomQuery(String sqlQuery, NativeSQLQueryReturn[] queryReturns, Collection additionalQuerySpaces, SessionFactoryImplementor factory)
/*  49:    */     throws HibernateException
/*  50:    */   {
/*  51: 85 */     LOG.tracev("Starting processing of sql query [{0}]", sqlQuery);
/*  52: 86 */     SQLQueryReturnProcessor processor = new SQLQueryReturnProcessor(queryReturns, factory);
/*  53: 87 */     SQLQueryReturnProcessor.ResultAliasContext aliasContext = processor.process();
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:132 */     SQLQueryParser parser = new SQLQueryParser(sqlQuery, new ParserContext(aliasContext), factory);
/*  99:133 */     this.sql = parser.process();
/* 100:134 */     this.namedParameterBindPoints.putAll(parser.getNamedParameters());
/* 101:    */     
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:152 */     this.customQueryReturns.addAll(processor.generateCustomReturns(parser.queryHasAliases()));
/* 119:178 */     if (additionalQuerySpaces != null) {
/* 120:179 */       this.querySpaces.addAll(additionalQuerySpaces);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static class ParserContext
/* 125:    */     implements SQLQueryParser.ParserContext
/* 126:    */   {
/* 127:    */     private final SQLQueryReturnProcessor.ResultAliasContext aliasContext;
/* 128:    */     
/* 129:    */     public ParserContext(SQLQueryReturnProcessor.ResultAliasContext aliasContext)
/* 130:    */     {
/* 131:224 */       this.aliasContext = aliasContext;
/* 132:    */     }
/* 133:    */     
/* 134:    */     public boolean isEntityAlias(String alias)
/* 135:    */     {
/* 136:228 */       return getEntityPersisterByAlias(alias) != null;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public SQLLoadable getEntityPersisterByAlias(String alias)
/* 140:    */     {
/* 141:232 */       return this.aliasContext.getEntityPersister(alias);
/* 142:    */     }
/* 143:    */     
/* 144:    */     public String getEntitySuffixByAlias(String alias)
/* 145:    */     {
/* 146:236 */       return this.aliasContext.getEntitySuffix(alias);
/* 147:    */     }
/* 148:    */     
/* 149:    */     public boolean isCollectionAlias(String alias)
/* 150:    */     {
/* 151:240 */       return getCollectionPersisterByAlias(alias) != null;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public SQLLoadableCollection getCollectionPersisterByAlias(String alias)
/* 155:    */     {
/* 156:244 */       return this.aliasContext.getCollectionPersister(alias);
/* 157:    */     }
/* 158:    */     
/* 159:    */     public String getCollectionSuffixByAlias(String alias)
/* 160:    */     {
/* 161:248 */       return this.aliasContext.getCollectionSuffix(alias);
/* 162:    */     }
/* 163:    */     
/* 164:    */     public Map getPropertyResultsMapByAlias(String alias)
/* 165:    */     {
/* 166:252 */       return this.aliasContext.getPropertyResultsMap(alias);
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.sql.SQLCustomQuery
 * JD-Core Version:    0.7.0.1
 */