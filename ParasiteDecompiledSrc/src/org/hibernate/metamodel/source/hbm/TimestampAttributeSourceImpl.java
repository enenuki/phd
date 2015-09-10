/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbGeneratedAttribute;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass.JaxbTimestamp;
/*   7:    */ import org.hibernate.internal.util.Value;
/*   8:    */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*   9:    */ import org.hibernate.mapping.PropertyGeneration;
/*  10:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  11:    */ import org.hibernate.metamodel.source.MappingException;
/*  12:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  13:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  14:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  15:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  16:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  17:    */ 
/*  18:    */ class TimestampAttributeSourceImpl
/*  19:    */   implements SingularAttributeSource
/*  20:    */ {
/*  21:    */   private final JaxbHibernateMapping.JaxbClass.JaxbTimestamp timestampElement;
/*  22:    */   private final LocalBindingContext bindingContext;
/*  23:    */   private final List<RelationalValueSource> valueSources;
/*  24:    */   
/*  25:    */   TimestampAttributeSourceImpl(final JaxbHibernateMapping.JaxbClass.JaxbTimestamp timestampElement, LocalBindingContext bindingContext)
/*  26:    */   {
/*  27: 53 */     this.timestampElement = timestampElement;
/*  28: 54 */     this.bindingContext = bindingContext;
/*  29: 55 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  30:    */     {
/*  31:    */       public String getColumnAttribute()
/*  32:    */       {
/*  33: 59 */         return timestampElement.getColumn();
/*  34:    */       }
/*  35:    */       
/*  36:    */       public String getFormulaAttribute()
/*  37:    */       {
/*  38: 64 */         return null;
/*  39:    */       }
/*  40:    */       
/*  41:    */       public List getColumnOrFormulaElements()
/*  42:    */       {
/*  43: 69 */         return null;
/*  44:    */       }
/*  45:    */       
/*  46:    */       public String getContainingTableName()
/*  47:    */       {
/*  48: 75 */         return null;
/*  49:    */       }
/*  50:    */       
/*  51:    */       public boolean isIncludedInInsertByDefault()
/*  52:    */       {
/*  53: 80 */         return true;
/*  54:    */       }
/*  55:    */       
/*  56:    */       public boolean isIncludedInUpdateByDefault()
/*  57:    */       {
/*  58: 85 */         return true;
/*  59:    */       }
/*  60: 85 */     }, bindingContext);
/*  61:    */   }
/*  62:    */   
/*  63: 92 */   private final ExplicitHibernateTypeSource typeSource = new ExplicitHibernateTypeSource()
/*  64:    */   {
/*  65:    */     public String getName()
/*  66:    */     {
/*  67: 95 */       return "db".equals(TimestampAttributeSourceImpl.this.timestampElement.getSource()) ? "dbtimestamp" : "timestamp";
/*  68:    */     }
/*  69:    */     
/*  70:    */     public Map<String, String> getParameters()
/*  71:    */     {
/*  72:100 */       return null;
/*  73:    */     }
/*  74:    */   };
/*  75:    */   
/*  76:    */   public String getName()
/*  77:    */   {
/*  78:106 */     return this.timestampElement.getName();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public ExplicitHibernateTypeSource getTypeInformation()
/*  82:    */   {
/*  83:111 */     return this.typeSource;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getPropertyAccessorName()
/*  87:    */   {
/*  88:116 */     return this.timestampElement.getAccess();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isInsertable()
/*  92:    */   {
/*  93:121 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isUpdatable()
/*  97:    */   {
/*  98:126 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:129 */   private Value<PropertyGeneration> propertyGenerationValue = new Value(new Value.DeferredInitializer()
/* 102:    */   {
/* 103:    */     public PropertyGeneration initialize()
/* 104:    */     {
/* 105:133 */       PropertyGeneration propertyGeneration = TimestampAttributeSourceImpl.this.timestampElement.getGenerated() == null ? PropertyGeneration.NEVER : PropertyGeneration.parse(TimestampAttributeSourceImpl.this.timestampElement.getGenerated().value());
/* 106:136 */       if (propertyGeneration == PropertyGeneration.INSERT) {
/* 107:137 */         throw new MappingException("'generated' attribute cannot be 'insert' for versioning property", TimestampAttributeSourceImpl.this.bindingContext.getOrigin());
/* 108:    */       }
/* 109:142 */       return propertyGeneration;
/* 110:    */     }
/* 111:129 */   });
/* 112:    */   
/* 113:    */   public PropertyGeneration getGeneration()
/* 114:    */   {
/* 115:149 */     return (PropertyGeneration)this.propertyGenerationValue.getValue();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isLazy()
/* 119:    */   {
/* 120:154 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isIncludedInOptimisticLocking()
/* 124:    */   {
/* 125:159 */     return false;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public SingularAttributeNature getNature()
/* 129:    */   {
/* 130:164 */     return SingularAttributeNature.BASIC;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isVirtualAttribute()
/* 134:    */   {
/* 135:169 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean areValuesIncludedInInsertByDefault()
/* 139:    */   {
/* 140:174 */     return true;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 144:    */   {
/* 145:179 */     return true;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean areValuesNullableByDefault()
/* 149:    */   {
/* 150:184 */     return true;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<RelationalValueSource> relationalValueSources()
/* 154:    */   {
/* 155:189 */     return this.valueSources;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isSingular()
/* 159:    */   {
/* 160:194 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 164:    */   {
/* 165:199 */     return Helper.buildMetaAttributeSources(this.timestampElement.getMeta());
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.TimestampAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */