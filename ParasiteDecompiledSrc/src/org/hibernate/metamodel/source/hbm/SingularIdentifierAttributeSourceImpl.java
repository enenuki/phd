/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass.JaxbId;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTypeElement;
/*   7:    */ import org.hibernate.mapping.PropertyGeneration;
/*   8:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*   9:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  10:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  11:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  12:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  13:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  14:    */ 
/*  15:    */ class SingularIdentifierAttributeSourceImpl
/*  16:    */   implements SingularAttributeSource
/*  17:    */ {
/*  18:    */   private final JaxbHibernateMapping.JaxbClass.JaxbId idElement;
/*  19:    */   private final ExplicitHibernateTypeSource typeSource;
/*  20:    */   private final List<RelationalValueSource> valueSources;
/*  21:    */   
/*  22:    */   public SingularIdentifierAttributeSourceImpl(final JaxbHibernateMapping.JaxbClass.JaxbId idElement, LocalBindingContext bindingContext)
/*  23:    */   {
/*  24: 51 */     this.idElement = idElement;
/*  25: 52 */     this.typeSource = new ExplicitHibernateTypeSource()
/*  26:    */     {
/*  27: 53 */       private final String name = idElement.getType() != null ? idElement.getType().getName() : idElement.getTypeAttribute() != null ? idElement.getTypeAttribute() : null;
/*  28: 58 */       private final Map<String, String> parameters = idElement.getType() != null ? Helper.extractParameters(idElement.getType().getParam()) : null;
/*  29:    */       
/*  30:    */       public String getName()
/*  31:    */       {
/*  32: 64 */         return this.name;
/*  33:    */       }
/*  34:    */       
/*  35:    */       public Map<String, String> getParameters()
/*  36:    */       {
/*  37: 69 */         return this.parameters;
/*  38:    */       }
/*  39: 71 */     };
/*  40: 72 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  41:    */     {
/*  42:    */       public String getColumnAttribute()
/*  43:    */       {
/*  44: 76 */         return idElement.getColumnAttribute();
/*  45:    */       }
/*  46:    */       
/*  47:    */       public String getFormulaAttribute()
/*  48:    */       {
/*  49: 81 */         return null;
/*  50:    */       }
/*  51:    */       
/*  52:    */       public List getColumnOrFormulaElements()
/*  53:    */       {
/*  54: 86 */         return idElement.getColumn();
/*  55:    */       }
/*  56:    */       
/*  57:    */       public String getContainingTableName()
/*  58:    */       {
/*  59: 92 */         return null;
/*  60:    */       }
/*  61:    */       
/*  62:    */       public boolean isIncludedInInsertByDefault()
/*  63:    */       {
/*  64: 97 */         return true;
/*  65:    */       }
/*  66:    */       
/*  67:    */       public boolean isIncludedInUpdateByDefault()
/*  68:    */       {
/*  69:102 */         return false;
/*  70:    */       }
/*  71:    */       
/*  72:    */       public boolean isForceNotNull()
/*  73:    */       {
/*  74:107 */         return true;
/*  75:    */       }
/*  76:107 */     }, bindingContext);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getName()
/*  80:    */   {
/*  81:116 */     return this.idElement.getName() == null ? "id" : this.idElement.getName();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ExplicitHibernateTypeSource getTypeInformation()
/*  85:    */   {
/*  86:123 */     return this.typeSource;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getPropertyAccessorName()
/*  90:    */   {
/*  91:128 */     return this.idElement.getAccess();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isInsertable()
/*  95:    */   {
/*  96:133 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isUpdatable()
/* 100:    */   {
/* 101:138 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public PropertyGeneration getGeneration()
/* 105:    */   {
/* 106:143 */     return PropertyGeneration.INSERT;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isLazy()
/* 110:    */   {
/* 111:148 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isIncludedInOptimisticLocking()
/* 115:    */   {
/* 116:153 */     return false;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public SingularAttributeNature getNature()
/* 120:    */   {
/* 121:158 */     return SingularAttributeNature.BASIC;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isVirtualAttribute()
/* 125:    */   {
/* 126:163 */     return false;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean areValuesIncludedInInsertByDefault()
/* 130:    */   {
/* 131:168 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 135:    */   {
/* 136:173 */     return true;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean areValuesNullableByDefault()
/* 140:    */   {
/* 141:178 */     return false;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<RelationalValueSource> relationalValueSources()
/* 145:    */   {
/* 146:183 */     return this.valueSources;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean isSingular()
/* 150:    */   {
/* 151:188 */     return true;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 155:    */   {
/* 156:193 */     return Helper.buildMetaAttributeSources(this.idElement.getMeta());
/* 157:    */   }
/* 158:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.SingularIdentifierAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */