/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.FetchMode;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbLazyAttribute;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToManyElement;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbNotFoundAttribute;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOuterJoinAttribute;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  11:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  12:    */ import org.hibernate.metamodel.source.binder.ManyToManyPluralAttributeElementSource;
/*  13:    */ import org.hibernate.metamodel.source.binder.PluralAttributeElementNature;
/*  14:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  15:    */ 
/*  16:    */ public class ManyToManyPluralAttributeElementSourceImpl
/*  17:    */   implements ManyToManyPluralAttributeElementSource
/*  18:    */ {
/*  19:    */   private final JaxbManyToManyElement manyToManyElement;
/*  20:    */   private final LocalBindingContext bindingContext;
/*  21:    */   private final List<RelationalValueSource> valueSources;
/*  22:    */   
/*  23:    */   public ManyToManyPluralAttributeElementSourceImpl(final JaxbManyToManyElement manyToManyElement, LocalBindingContext bindingContext)
/*  24:    */   {
/*  25: 48 */     this.manyToManyElement = manyToManyElement;
/*  26: 49 */     this.bindingContext = bindingContext;
/*  27:    */     
/*  28: 51 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  29:    */     {
/*  30:    */       public String getContainingTableName()
/*  31:    */       {
/*  32: 55 */         return null;
/*  33:    */       }
/*  34:    */       
/*  35:    */       public boolean isIncludedInInsertByDefault()
/*  36:    */       {
/*  37: 60 */         return true;
/*  38:    */       }
/*  39:    */       
/*  40:    */       public boolean isIncludedInUpdateByDefault()
/*  41:    */       {
/*  42: 65 */         return true;
/*  43:    */       }
/*  44:    */       
/*  45:    */       public String getColumnAttribute()
/*  46:    */       {
/*  47: 70 */         return manyToManyElement.getColumn();
/*  48:    */       }
/*  49:    */       
/*  50:    */       public String getFormulaAttribute()
/*  51:    */       {
/*  52: 75 */         return manyToManyElement.getFormula();
/*  53:    */       }
/*  54:    */       
/*  55:    */       public List getColumnOrFormulaElements()
/*  56:    */       {
/*  57: 80 */         return manyToManyElement.getColumnOrFormula();
/*  58:    */       }
/*  59: 80 */     }, bindingContext);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public PluralAttributeElementNature getNature()
/*  63:    */   {
/*  64: 89 */     return PluralAttributeElementNature.MANY_TO_MANY;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getReferencedEntityName()
/*  68:    */   {
/*  69: 94 */     return StringHelper.isNotEmpty(this.manyToManyElement.getEntityName()) ? this.manyToManyElement.getEntityName() : this.bindingContext.qualifyClassName(this.manyToManyElement.getClazz());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getReferencedEntityAttributeName()
/*  73:    */   {
/*  74:101 */     return this.manyToManyElement.getPropertyRef();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<RelationalValueSource> getValueSources()
/*  78:    */   {
/*  79:106 */     return this.valueSources;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isNotFoundAnException()
/*  83:    */   {
/*  84:111 */     return (this.manyToManyElement.getNotFound() == null) || (!"ignore".equals(this.manyToManyElement.getNotFound().value()));
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getExplicitForeignKeyName()
/*  88:    */   {
/*  89:117 */     return this.manyToManyElement.getForeignKey();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isUnique()
/*  93:    */   {
/*  94:122 */     return this.manyToManyElement.isUnique();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getOrderBy()
/*  98:    */   {
/*  99:127 */     return this.manyToManyElement.getOrderBy();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getWhere()
/* 103:    */   {
/* 104:132 */     return this.manyToManyElement.getWhere();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public FetchMode getFetchMode()
/* 108:    */   {
/* 109:137 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean fetchImmediately()
/* 113:    */   {
/* 114:142 */     if ((this.manyToManyElement.getLazy() != null) && 
/* 115:143 */       ("false".equals(this.manyToManyElement.getLazy().value()))) {
/* 116:144 */       return true;
/* 117:    */     }
/* 118:148 */     if (this.manyToManyElement.getOuterJoin() == null) {
/* 119:149 */       return !this.bindingContext.getMappingDefaults().areAssociationsLazy();
/* 120:    */     }
/* 121:152 */     String value = this.manyToManyElement.getOuterJoin().value();
/* 122:153 */     if ("auto".equals(value)) {
/* 123:154 */       return !this.bindingContext.getMappingDefaults().areAssociationsLazy();
/* 124:    */     }
/* 125:156 */     return "true".equals(value);
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.ManyToManyPluralAttributeElementSourceImpl
 * JD-Core Version:    0.7.0.1
 */