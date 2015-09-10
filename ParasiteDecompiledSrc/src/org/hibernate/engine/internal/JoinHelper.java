/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.engine.spi.Mapping;
/*   4:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   7:    */ import org.hibernate.persister.entity.Joinable;
/*   8:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*   9:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  10:    */ import org.hibernate.type.AssociationType;
/*  11:    */ 
/*  12:    */ public final class JoinHelper
/*  13:    */ {
/*  14:    */   public static String[] getAliasedLHSColumnNames(AssociationType type, String alias, int property, OuterJoinLoadable lhsPersister, Mapping mapping)
/*  15:    */   {
/*  16: 53 */     return getAliasedLHSColumnNames(type, alias, property, 0, lhsPersister, mapping);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static String[] getLHSColumnNames(AssociationType type, int property, OuterJoinLoadable lhsPersister, Mapping mapping)
/*  20:    */   {
/*  21: 66 */     return getLHSColumnNames(type, property, 0, lhsPersister, mapping);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String[] getAliasedLHSColumnNames(AssociationType type, String alias, int property, int begin, OuterJoinLoadable lhsPersister, Mapping mapping)
/*  25:    */   {
/*  26: 81 */     if (type.useLHSPrimaryKey()) {
/*  27: 82 */       return StringHelper.qualify(alias, lhsPersister.getIdentifierColumnNames());
/*  28:    */     }
/*  29: 85 */     String propertyName = type.getLHSPropertyName();
/*  30: 86 */     if (propertyName == null) {
/*  31: 87 */       return ArrayHelper.slice(lhsPersister.toColumns(alias, property), begin, type.getColumnSpan(mapping));
/*  32:    */     }
/*  33: 94 */     return ((PropertyMapping)lhsPersister).toColumns(alias, propertyName);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static String[] getLHSColumnNames(AssociationType type, int property, int begin, OuterJoinLoadable lhsPersister, Mapping mapping)
/*  37:    */   {
/*  38:110 */     if (type.useLHSPrimaryKey()) {
/*  39:112 */       return lhsPersister.getIdentifierColumnNames();
/*  40:    */     }
/*  41:115 */     String propertyName = type.getLHSPropertyName();
/*  42:116 */     if (propertyName == null) {
/*  43:119 */       return ArrayHelper.slice(lhsPersister.getSubclassPropertyColumnNames(property), begin, type.getColumnSpan(mapping));
/*  44:    */     }
/*  45:128 */     return lhsPersister.getPropertyColumnNames(propertyName);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static String getLHSTableName(AssociationType type, int property, OuterJoinLoadable lhsPersister)
/*  49:    */   {
/*  50:138 */     if (type.useLHSPrimaryKey()) {
/*  51:139 */       return lhsPersister.getTableName();
/*  52:    */     }
/*  53:142 */     String propertyName = type.getLHSPropertyName();
/*  54:143 */     if (propertyName == null) {
/*  55:147 */       return lhsPersister.getSubclassPropertyTableName(property);
/*  56:    */     }
/*  57:151 */     String propertyRefTable = lhsPersister.getPropertyTableName(propertyName);
/*  58:152 */     if (propertyRefTable == null) {
/*  59:160 */       propertyRefTable = lhsPersister.getSubclassPropertyTableName(property);
/*  60:    */     }
/*  61:162 */     return propertyRefTable;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static String[] getRHSColumnNames(AssociationType type, SessionFactoryImplementor factory)
/*  65:    */   {
/*  66:172 */     String uniqueKeyPropertyName = type.getRHSUniqueKeyPropertyName();
/*  67:173 */     Joinable joinable = type.getAssociatedJoinable(factory);
/*  68:174 */     if (uniqueKeyPropertyName == null) {
/*  69:175 */       return joinable.getKeyColumnNames();
/*  70:    */     }
/*  71:178 */     return ((OuterJoinLoadable)joinable).getPropertyColumnNames(uniqueKeyPropertyName);
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.JoinHelper
 * JD-Core Version:    0.7.0.1
 */