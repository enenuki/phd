/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.engine.internal.JoinHelper;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   9:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  10:    */ import org.hibernate.persister.entity.Joinable;
/*  11:    */ import org.hibernate.sql.JoinFragment;
/*  12:    */ import org.hibernate.sql.JoinType;
/*  13:    */ import org.hibernate.type.AssociationType;
/*  14:    */ import org.hibernate.type.EntityType;
/*  15:    */ 
/*  16:    */ public final class OuterJoinableAssociation
/*  17:    */ {
/*  18:    */   private final PropertyPath propertyPath;
/*  19:    */   private final AssociationType joinableType;
/*  20:    */   private final Joinable joinable;
/*  21:    */   private final String lhsAlias;
/*  22:    */   private final String[] lhsColumns;
/*  23:    */   private final String rhsAlias;
/*  24:    */   private final String[] rhsColumns;
/*  25:    */   private final JoinType joinType;
/*  26:    */   private final String on;
/*  27:    */   private final Map enabledFilters;
/*  28:    */   private final boolean hasRestriction;
/*  29:    */   
/*  30:    */   public static OuterJoinableAssociation createRoot(AssociationType joinableType, String alias, SessionFactoryImplementor factory)
/*  31:    */   {
/*  32: 62 */     return new OuterJoinableAssociation(new PropertyPath(), joinableType, null, null, alias, JoinType.LEFT_OUTER_JOIN, null, false, factory, CollectionHelper.EMPTY_MAP);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public OuterJoinableAssociation(PropertyPath propertyPath, AssociationType joinableType, String lhsAlias, String[] lhsColumns, String rhsAlias, JoinType joinType, String withClause, boolean hasRestriction, SessionFactoryImplementor factory, Map enabledFilters)
/*  36:    */     throws MappingException
/*  37:    */   {
/*  38: 87 */     this.propertyPath = propertyPath;
/*  39: 88 */     this.joinableType = joinableType;
/*  40: 89 */     this.lhsAlias = lhsAlias;
/*  41: 90 */     this.lhsColumns = lhsColumns;
/*  42: 91 */     this.rhsAlias = rhsAlias;
/*  43: 92 */     this.joinType = joinType;
/*  44: 93 */     this.joinable = joinableType.getAssociatedJoinable(factory);
/*  45: 94 */     this.rhsColumns = JoinHelper.getRHSColumnNames(joinableType, factory);
/*  46: 95 */     this.on = (joinableType.getOnCondition(rhsAlias, factory, enabledFilters) + ((withClause == null) || (withClause.trim().length() == 0) ? "" : new StringBuilder().append(" and ( ").append(withClause).append(" )").toString()));
/*  47:    */     
/*  48: 97 */     this.hasRestriction = hasRestriction;
/*  49: 98 */     this.enabledFilters = enabledFilters;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public PropertyPath getPropertyPath()
/*  53:    */   {
/*  54:102 */     return this.propertyPath;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public JoinType getJoinType()
/*  58:    */   {
/*  59:106 */     return this.joinType;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getLhsAlias()
/*  63:    */   {
/*  64:110 */     return this.lhsAlias;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getRHSAlias()
/*  68:    */   {
/*  69:114 */     return this.rhsAlias;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getRhsAlias()
/*  73:    */   {
/*  74:118 */     return this.rhsAlias;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private boolean isOneToOne()
/*  78:    */   {
/*  79:122 */     if (this.joinableType.isEntityType())
/*  80:    */     {
/*  81:123 */       EntityType etype = (EntityType)this.joinableType;
/*  82:124 */       return etype.isOneToOne();
/*  83:    */     }
/*  84:127 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public AssociationType getJoinableType()
/*  88:    */   {
/*  89:132 */     return this.joinableType;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getRHSUniqueKeyName()
/*  93:    */   {
/*  94:136 */     return this.joinableType.getRHSUniqueKeyPropertyName();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isCollection()
/*  98:    */   {
/*  99:140 */     return this.joinableType.isCollectionType();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Joinable getJoinable()
/* 103:    */   {
/* 104:144 */     return this.joinable;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean hasRestriction()
/* 108:    */   {
/* 109:148 */     return this.hasRestriction;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getOwner(List associations)
/* 113:    */   {
/* 114:152 */     if ((isOneToOne()) || (isCollection())) {
/* 115:153 */       return getPosition(this.lhsAlias, associations);
/* 116:    */     }
/* 117:156 */     return -1;
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static int getPosition(String lhsAlias, List associations)
/* 121:    */   {
/* 122:165 */     int result = 0;
/* 123:166 */     for (int i = 0; i < associations.size(); i++)
/* 124:    */     {
/* 125:167 */       OuterJoinableAssociation oj = (OuterJoinableAssociation)associations.get(i);
/* 126:168 */       if (oj.getJoinable().consumesEntityAlias())
/* 127:    */       {
/* 128:169 */         if (oj.rhsAlias.equals(lhsAlias)) {
/* 129:169 */           return result;
/* 130:    */         }
/* 131:170 */         result++;
/* 132:    */       }
/* 133:    */     }
/* 134:173 */     return -1;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void addJoins(JoinFragment outerjoin)
/* 138:    */     throws MappingException
/* 139:    */   {
/* 140:177 */     outerjoin.addJoin(this.joinable.getTableName(), this.rhsAlias, this.lhsColumns, this.rhsColumns, this.joinType, this.on);
/* 141:    */     
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:185 */     outerjoin.addJoins(this.joinable.fromJoinFragment(this.rhsAlias, false, true), this.joinable.whereJoinFragment(this.rhsAlias, false, true));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void validateJoin(String path)
/* 152:    */     throws MappingException
/* 153:    */   {
/* 154:192 */     if ((this.rhsColumns == null) || (this.lhsColumns == null) || (this.lhsColumns.length != this.rhsColumns.length) || (this.lhsColumns.length == 0)) {
/* 155:194 */       throw new MappingException("invalid join columns for association: " + path);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isManyToManyWith(OuterJoinableAssociation other)
/* 160:    */   {
/* 161:199 */     if (this.joinable.isCollection())
/* 162:    */     {
/* 163:200 */       QueryableCollection persister = (QueryableCollection)this.joinable;
/* 164:201 */       if (persister.isManyToMany()) {
/* 165:202 */         return persister.getElementType() == other.getJoinableType();
/* 166:    */       }
/* 167:    */     }
/* 168:205 */     return false;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void addManyToManyJoin(JoinFragment outerjoin, QueryableCollection collection)
/* 172:    */     throws MappingException
/* 173:    */   {
/* 174:209 */     String manyToManyFilter = collection.getManyToManyFilterFragment(this.rhsAlias, this.enabledFilters);
/* 175:210 */     String condition = this.on + " and " + manyToManyFilter;
/* 176:    */     
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:215 */     outerjoin.addJoin(this.joinable.getTableName(), this.rhsAlias, this.lhsColumns, this.rhsColumns, this.joinType, condition);
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:223 */     outerjoin.addJoins(this.joinable.fromJoinFragment(this.rhsAlias, false, true), this.joinable.whereJoinFragment(this.rhsAlias, false, true));
/* 189:    */   }
/* 190:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.OuterJoinableAssociation
 * JD-Core Version:    0.7.0.1
 */