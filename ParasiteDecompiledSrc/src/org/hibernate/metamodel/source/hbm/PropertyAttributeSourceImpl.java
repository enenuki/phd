/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTypeElement;
/*   7:    */ import org.hibernate.mapping.PropertyGeneration;
/*   8:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*   9:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  10:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  11:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  12:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  13:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  14:    */ 
/*  15:    */ class PropertyAttributeSourceImpl
/*  16:    */   implements SingularAttributeSource
/*  17:    */ {
/*  18:    */   private final JaxbPropertyElement propertyElement;
/*  19:    */   private final ExplicitHibernateTypeSource typeSource;
/*  20:    */   private final List<RelationalValueSource> valueSources;
/*  21:    */   
/*  22:    */   PropertyAttributeSourceImpl(final JaxbPropertyElement propertyElement, LocalBindingContext bindingContext)
/*  23:    */   {
/*  24: 49 */     this.propertyElement = propertyElement;
/*  25: 50 */     this.typeSource = new ExplicitHibernateTypeSource()
/*  26:    */     {
/*  27: 51 */       private final String name = propertyElement.getType() != null ? propertyElement.getType().getName() : propertyElement.getTypeAttribute() != null ? propertyElement.getTypeAttribute() : null;
/*  28: 56 */       private final Map<String, String> parameters = propertyElement.getType() != null ? Helper.extractParameters(propertyElement.getType().getParam()) : null;
/*  29:    */       
/*  30:    */       public String getName()
/*  31:    */       {
/*  32: 62 */         return this.name;
/*  33:    */       }
/*  34:    */       
/*  35:    */       public Map<String, String> getParameters()
/*  36:    */       {
/*  37: 67 */         return this.parameters;
/*  38:    */       }
/*  39: 69 */     };
/*  40: 70 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  41:    */     {
/*  42:    */       public String getColumnAttribute()
/*  43:    */       {
/*  44: 74 */         return propertyElement.getColumn();
/*  45:    */       }
/*  46:    */       
/*  47:    */       public String getFormulaAttribute()
/*  48:    */       {
/*  49: 79 */         return propertyElement.getFormula();
/*  50:    */       }
/*  51:    */       
/*  52:    */       public List getColumnOrFormulaElements()
/*  53:    */       {
/*  54: 84 */         return propertyElement.getColumnOrFormula();
/*  55:    */       }
/*  56:    */       
/*  57:    */       public String getContainingTableName()
/*  58:    */       {
/*  59: 90 */         return null;
/*  60:    */       }
/*  61:    */       
/*  62:    */       public boolean isIncludedInInsertByDefault()
/*  63:    */       {
/*  64: 95 */         return Helper.getBooleanValue(propertyElement.isInsert(), true);
/*  65:    */       }
/*  66:    */       
/*  67:    */       public boolean isIncludedInUpdateByDefault()
/*  68:    */       {
/*  69:100 */         return Helper.getBooleanValue(propertyElement.isUpdate(), true);
/*  70:    */       }
/*  71:100 */     }, bindingContext);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getName()
/*  75:    */   {
/*  76:109 */     return this.propertyElement.getName();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ExplicitHibernateTypeSource getTypeInformation()
/*  80:    */   {
/*  81:114 */     return this.typeSource;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getPropertyAccessorName()
/*  85:    */   {
/*  86:119 */     return this.propertyElement.getAccess();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isInsertable()
/*  90:    */   {
/*  91:124 */     return Helper.getBooleanValue(this.propertyElement.isInsert(), true);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isUpdatable()
/*  95:    */   {
/*  96:129 */     return Helper.getBooleanValue(this.propertyElement.isUpdate(), true);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public PropertyGeneration getGeneration()
/* 100:    */   {
/* 101:134 */     return PropertyGeneration.parse(this.propertyElement.getGenerated());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isLazy()
/* 105:    */   {
/* 106:139 */     return Helper.getBooleanValue(Boolean.valueOf(this.propertyElement.isLazy()), false);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isIncludedInOptimisticLocking()
/* 110:    */   {
/* 111:144 */     return Helper.getBooleanValue(Boolean.valueOf(this.propertyElement.isOptimisticLock()), true);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public SingularAttributeNature getNature()
/* 115:    */   {
/* 116:149 */     return SingularAttributeNature.BASIC;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isVirtualAttribute()
/* 120:    */   {
/* 121:154 */     return false;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean areValuesIncludedInInsertByDefault()
/* 125:    */   {
/* 126:159 */     return Helper.getBooleanValue(this.propertyElement.isInsert(), true);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 130:    */   {
/* 131:164 */     return Helper.getBooleanValue(this.propertyElement.isUpdate(), true);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean areValuesNullableByDefault()
/* 135:    */   {
/* 136:169 */     return !Helper.getBooleanValue(this.propertyElement.isNotNull(), false);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<RelationalValueSource> relationalValueSources()
/* 140:    */   {
/* 141:174 */     return this.valueSources;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isSingular()
/* 145:    */   {
/* 146:179 */     return true;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 150:    */   {
/* 151:184 */     return Helper.buildMetaAttributeSources(this.propertyElement.getMeta());
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.PropertyAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */