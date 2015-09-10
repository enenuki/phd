/*   1:    */ package org.hibernate.loader.criteria;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.Criteria;
/*   8:    */ import org.hibernate.FetchMode;
/*   9:    */ import org.hibernate.LockOptions;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  12:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.internal.CriteriaImpl;
/*  15:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  16:    */ import org.hibernate.loader.AbstractEntityJoinWalker;
/*  17:    */ import org.hibernate.loader.PropertyPath;
/*  18:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  19:    */ import org.hibernate.persister.entity.Joinable;
/*  20:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  21:    */ import org.hibernate.persister.entity.Queryable;
/*  22:    */ import org.hibernate.sql.JoinType;
/*  23:    */ import org.hibernate.type.AssociationType;
/*  24:    */ import org.hibernate.type.Type;
/*  25:    */ 
/*  26:    */ public class CriteriaJoinWalker
/*  27:    */   extends AbstractEntityJoinWalker
/*  28:    */ {
/*  29:    */   private final CriteriaQueryTranslator translator;
/*  30:    */   private final Set querySpaces;
/*  31:    */   private final Type[] resultTypes;
/*  32:    */   private final boolean[] includeInResultRow;
/*  33:    */   private final String[] userAliases;
/*  34: 69 */   private final List userAliasList = new ArrayList();
/*  35: 70 */   private final List resultTypeList = new ArrayList();
/*  36: 71 */   private final List includeInResultRowList = new ArrayList();
/*  37:    */   
/*  38:    */   public Type[] getResultTypes()
/*  39:    */   {
/*  40: 74 */     return this.resultTypes;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String[] getUserAliases()
/*  44:    */   {
/*  45: 78 */     return this.userAliases;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean[] includeInResultRow()
/*  49:    */   {
/*  50: 82 */     return this.includeInResultRow;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CriteriaJoinWalker(OuterJoinLoadable persister, CriteriaQueryTranslator translator, SessionFactoryImplementor factory, CriteriaImpl criteria, String rootEntityName, LoadQueryInfluencers loadQueryInfluencers)
/*  54:    */   {
/*  55: 92 */     this(persister, translator, factory, criteria, rootEntityName, loadQueryInfluencers, null);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public CriteriaJoinWalker(OuterJoinLoadable persister, CriteriaQueryTranslator translator, SessionFactoryImplementor factory, CriteriaImpl criteria, String rootEntityName, LoadQueryInfluencers loadQueryInfluencers, String alias)
/*  59:    */   {
/*  60:103 */     super(persister, factory, loadQueryInfluencers, alias);
/*  61:    */     
/*  62:105 */     this.translator = translator;
/*  63:    */     
/*  64:107 */     this.querySpaces = translator.getQuerySpaces();
/*  65:109 */     if (translator.hasProjection())
/*  66:    */     {
/*  67:110 */       initProjection(translator.getSelect(), translator.getWhereCondition(), translator.getOrderBy(), translator.getGroupBy(), LockOptions.NONE);
/*  68:    */       
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:117 */       this.resultTypes = translator.getProjectedTypes();
/*  75:118 */       this.userAliases = translator.getProjectedAliases();
/*  76:119 */       this.includeInResultRow = new boolean[this.resultTypes.length];
/*  77:120 */       Arrays.fill(this.includeInResultRow, true);
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:123 */       initAll(translator.getWhereCondition(), translator.getOrderBy(), LockOptions.NONE);
/*  82:    */       
/*  83:125 */       this.userAliasList.add(criteria.getAlias());
/*  84:126 */       this.resultTypeList.add(translator.getResultType(criteria));
/*  85:127 */       this.includeInResultRowList.add(Boolean.valueOf(true));
/*  86:128 */       this.userAliases = ArrayHelper.toStringArray(this.userAliasList);
/*  87:129 */       this.resultTypes = ArrayHelper.toTypeArray(this.resultTypeList);
/*  88:130 */       this.includeInResultRow = ArrayHelper.toBooleanArray(this.includeInResultRowList);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected JoinType getJoinType(OuterJoinLoadable persister, PropertyPath path, int propertyNumber, AssociationType associationType, FetchMode metadataFetchMode, CascadeStyle metadataCascadeStyle, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth)
/*  93:    */     throws MappingException
/*  94:    */   {
/*  95:145 */     if (this.translator.isJoin(path.getFullPath())) {
/*  96:146 */       return this.translator.getJoinType(path.getFullPath());
/*  97:    */     }
/*  98:149 */     if (this.translator.hasProjection()) {
/*  99:150 */       return JoinType.NONE;
/* 100:    */     }
/* 101:153 */     FetchMode fetchMode = this.translator.getRootCriteria().getFetchMode(path.getFullPath());
/* 102:154 */     if (isDefaultFetchMode(fetchMode))
/* 103:    */     {
/* 104:155 */       if (isJoinFetchEnabledByProfile(persister, path, propertyNumber)) {
/* 105:156 */         return getJoinType(nullable, currentDepth);
/* 106:    */       }
/* 107:159 */       return super.getJoinType(persister, path, propertyNumber, associationType, metadataFetchMode, metadataCascadeStyle, lhsTable, lhsColumns, nullable, currentDepth);
/* 108:    */     }
/* 109:174 */     if (fetchMode == FetchMode.JOIN)
/* 110:    */     {
/* 111:175 */       isDuplicateAssociation(lhsTable, lhsColumns, associationType);
/* 112:176 */       return getJoinType(nullable, currentDepth);
/* 113:    */     }
/* 114:179 */     return JoinType.NONE;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected JoinType getJoinType(AssociationType associationType, FetchMode config, PropertyPath path, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth, CascadeStyle cascadeStyle)
/* 118:    */     throws MappingException
/* 119:    */   {
/* 120:195 */     return this.translator.isJoin(path.getFullPath()) ? this.translator.getJoinType(path.getFullPath()) : super.getJoinType(associationType, config, path, lhsTable, lhsColumns, nullable, currentDepth, cascadeStyle);
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static boolean isDefaultFetchMode(FetchMode fetchMode)
/* 124:    */   {
/* 125:211 */     return (fetchMode == null) || (fetchMode == FetchMode.DEFAULT);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected String getWhereFragment()
/* 129:    */     throws MappingException
/* 130:    */   {
/* 131:219 */     return super.getWhereFragment() + ((Queryable)getPersister()).filterFragment(getAlias(), getLoadQueryInfluencers().getEnabledFilters());
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected String generateTableAlias(int n, PropertyPath path, Joinable joinable)
/* 135:    */   {
/* 136:233 */     boolean checkForSqlAlias = joinable.consumesEntityAlias();
/* 137:235 */     if ((!checkForSqlAlias) && (joinable.isCollection()))
/* 138:    */     {
/* 139:237 */       CollectionPersister collectionPersister = (CollectionPersister)joinable;
/* 140:238 */       Type elementType = collectionPersister.getElementType();
/* 141:239 */       if ((elementType.isComponentType()) || (!elementType.isEntityType())) {
/* 142:240 */         checkForSqlAlias = true;
/* 143:    */       }
/* 144:    */     }
/* 145:244 */     String sqlAlias = null;
/* 146:246 */     if (checkForSqlAlias)
/* 147:    */     {
/* 148:247 */       Criteria subcriteria = this.translator.getCriteria(path.getFullPath());
/* 149:248 */       sqlAlias = subcriteria == null ? null : this.translator.getSQLAlias(subcriteria);
/* 150:250 */       if ((joinable.consumesEntityAlias()) && (!this.translator.hasProjection()))
/* 151:    */       {
/* 152:251 */         this.includeInResultRowList.add(Boolean.valueOf((subcriteria != null) && (subcriteria.getAlias() != null)));
/* 153:252 */         if ((sqlAlias != null) && 
/* 154:253 */           (subcriteria.getAlias() != null))
/* 155:    */         {
/* 156:254 */           this.userAliasList.add(subcriteria.getAlias());
/* 157:255 */           this.resultTypeList.add(this.translator.getResultType(subcriteria));
/* 158:    */         }
/* 159:    */       }
/* 160:    */     }
/* 161:261 */     if (sqlAlias == null) {
/* 162:262 */       sqlAlias = super.generateTableAlias(n + this.translator.getSQLAliasCount(), path, joinable);
/* 163:    */     }
/* 164:265 */     return sqlAlias;
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected String generateRootAlias(String tableName)
/* 168:    */   {
/* 169:269 */     return "this_";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Set getQuerySpaces()
/* 173:    */   {
/* 174:273 */     return this.querySpaces;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getComment()
/* 178:    */   {
/* 179:277 */     return "criteria query";
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected String getWithClause(PropertyPath path)
/* 183:    */   {
/* 184:281 */     return this.translator.getWithClause(path.getFullPath());
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean hasRestriction(PropertyPath path)
/* 188:    */   {
/* 189:285 */     return this.translator.hasRestriction(path.getFullPath());
/* 190:    */   }
/* 191:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.CriteriaJoinWalker
 * JD-Core Version:    0.7.0.1
 */