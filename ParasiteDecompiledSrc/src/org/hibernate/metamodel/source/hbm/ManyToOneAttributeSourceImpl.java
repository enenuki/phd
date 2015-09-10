/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.FetchMode;
/*   5:    */ import org.hibernate.engine.FetchStyle;
/*   6:    */ import org.hibernate.engine.FetchTiming;
/*   7:    */ import org.hibernate.engine.spi.CascadeStyle;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchAttribute;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbLazyAttributeWithNoProxy;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToOneElement;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOuterJoinAttribute;
/*  12:    */ import org.hibernate.mapping.PropertyGeneration;
/*  13:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  14:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  15:    */ import org.hibernate.metamodel.source.MappingException;
/*  16:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  17:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  18:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  19:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  20:    */ import org.hibernate.metamodel.source.binder.ToOneAttributeSource;
/*  21:    */ 
/*  22:    */ class ManyToOneAttributeSourceImpl
/*  23:    */   implements ToOneAttributeSource
/*  24:    */ {
/*  25:    */   private final JaxbManyToOneElement manyToOneElement;
/*  26:    */   private final LocalBindingContext bindingContext;
/*  27:    */   private final List<RelationalValueSource> valueSources;
/*  28:    */   
/*  29:    */   ManyToOneAttributeSourceImpl(final JaxbManyToOneElement manyToOneElement, LocalBindingContext bindingContext)
/*  30:    */   {
/*  31: 53 */     this.manyToOneElement = manyToOneElement;
/*  32: 54 */     this.bindingContext = bindingContext;
/*  33: 55 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  34:    */     {
/*  35:    */       public String getColumnAttribute()
/*  36:    */       {
/*  37: 59 */         return manyToOneElement.getColumn();
/*  38:    */       }
/*  39:    */       
/*  40:    */       public String getFormulaAttribute()
/*  41:    */       {
/*  42: 64 */         return manyToOneElement.getFormula();
/*  43:    */       }
/*  44:    */       
/*  45:    */       public List getColumnOrFormulaElements()
/*  46:    */       {
/*  47: 69 */         return manyToOneElement.getColumnOrFormula();
/*  48:    */       }
/*  49:    */       
/*  50:    */       public String getContainingTableName()
/*  51:    */       {
/*  52: 75 */         return null;
/*  53:    */       }
/*  54:    */       
/*  55:    */       public boolean isIncludedInInsertByDefault()
/*  56:    */       {
/*  57: 80 */         return manyToOneElement.isInsert();
/*  58:    */       }
/*  59:    */       
/*  60:    */       public boolean isIncludedInUpdateByDefault()
/*  61:    */       {
/*  62: 85 */         return manyToOneElement.isUpdate();
/*  63:    */       }
/*  64: 85 */     }, bindingContext);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getName()
/*  68:    */   {
/*  69: 94 */     return this.manyToOneElement.getName();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ExplicitHibernateTypeSource getTypeInformation()
/*  73:    */   {
/*  74: 99 */     return Helper.TO_ONE_ATTRIBUTE_TYPE_SOURCE;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getPropertyAccessorName()
/*  78:    */   {
/*  79:104 */     return this.manyToOneElement.getAccess();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isInsertable()
/*  83:    */   {
/*  84:109 */     return this.manyToOneElement.isInsert();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isUpdatable()
/*  88:    */   {
/*  89:114 */     return this.manyToOneElement.isUpdate();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public PropertyGeneration getGeneration()
/*  93:    */   {
/*  94:119 */     return PropertyGeneration.NEVER;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isLazy()
/*  98:    */   {
/*  99:124 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean isIncludedInOptimisticLocking()
/* 103:    */   {
/* 104:129 */     return this.manyToOneElement.isOptimisticLock();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Iterable<CascadeStyle> getCascadeStyles()
/* 108:    */   {
/* 109:134 */     return Helper.interpretCascadeStyles(this.manyToOneElement.getCascade(), this.bindingContext);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public FetchTiming getFetchTiming()
/* 113:    */   {
/* 114:139 */     String fetchSelection = this.manyToOneElement.getFetch() != null ? this.manyToOneElement.getFetch().value() : null;
/* 115:    */     
/* 116:    */ 
/* 117:142 */     String lazySelection = this.manyToOneElement.getLazy() != null ? this.manyToOneElement.getLazy().value() : null;
/* 118:    */     
/* 119:    */ 
/* 120:145 */     String outerJoinSelection = this.manyToOneElement.getOuterJoin() != null ? this.manyToOneElement.getOuterJoin().value() : null;
/* 121:149 */     if (lazySelection == null)
/* 122:    */     {
/* 123:150 */       if (("join".equals(fetchSelection)) || ("true".equals(outerJoinSelection))) {
/* 124:151 */         return FetchTiming.IMMEDIATE;
/* 125:    */       }
/* 126:153 */       if ("false".equals(outerJoinSelection)) {
/* 127:154 */         return FetchTiming.DELAYED;
/* 128:    */       }
/* 129:157 */       return this.bindingContext.getMappingDefaults().areAssociationsLazy() ? FetchTiming.DELAYED : FetchTiming.IMMEDIATE;
/* 130:    */     }
/* 131:162 */     if ("extra".equals(lazySelection)) {
/* 132:163 */       return FetchTiming.EXTRA_LAZY;
/* 133:    */     }
/* 134:165 */     if ("true".equals(lazySelection)) {
/* 135:166 */       return FetchTiming.DELAYED;
/* 136:    */     }
/* 137:168 */     if ("false".equals(lazySelection)) {
/* 138:169 */       return FetchTiming.IMMEDIATE;
/* 139:    */     }
/* 140:172 */     throw new MappingException(String.format("Unexpected lazy selection [%s] on '%s'", new Object[] { lazySelection, this.manyToOneElement.getName() }), this.bindingContext.getOrigin());
/* 141:    */   }
/* 142:    */   
/* 143:    */   public FetchStyle getFetchStyle()
/* 144:    */   {
/* 145:186 */     String fetchSelection = this.manyToOneElement.getFetch() != null ? this.manyToOneElement.getFetch().value() : null;
/* 146:    */     
/* 147:    */ 
/* 148:189 */     String outerJoinSelection = this.manyToOneElement.getOuterJoin() != null ? this.manyToOneElement.getOuterJoin().value() : null;
/* 149:193 */     if (fetchSelection == null)
/* 150:    */     {
/* 151:194 */       if (outerJoinSelection == null) {
/* 152:195 */         return FetchStyle.SELECT;
/* 153:    */       }
/* 154:198 */       if ("auto".equals(outerJoinSelection)) {
/* 155:199 */         return this.bindingContext.getMappingDefaults().areAssociationsLazy() ? FetchStyle.SELECT : FetchStyle.JOIN;
/* 156:    */       }
/* 157:204 */       return "true".equals(outerJoinSelection) ? FetchStyle.JOIN : FetchStyle.SELECT;
/* 158:    */     }
/* 159:209 */     return "join".equals(fetchSelection) ? FetchStyle.JOIN : FetchStyle.SELECT;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public FetchMode getFetchMode()
/* 163:    */   {
/* 164:215 */     return this.manyToOneElement.getFetch() == null ? FetchMode.DEFAULT : FetchMode.valueOf(this.manyToOneElement.getFetch().value());
/* 165:    */   }
/* 166:    */   
/* 167:    */   public SingularAttributeNature getNature()
/* 168:    */   {
/* 169:222 */     return SingularAttributeNature.MANY_TO_ONE;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isVirtualAttribute()
/* 173:    */   {
/* 174:227 */     return false;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean areValuesIncludedInInsertByDefault()
/* 178:    */   {
/* 179:232 */     return this.manyToOneElement.isInsert();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 183:    */   {
/* 184:237 */     return this.manyToOneElement.isUpdate();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean areValuesNullableByDefault()
/* 188:    */   {
/* 189:242 */     return !Helper.getBooleanValue(this.manyToOneElement.isNotNull(), false);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<RelationalValueSource> relationalValueSources()
/* 193:    */   {
/* 194:247 */     return this.valueSources;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isSingular()
/* 198:    */   {
/* 199:252 */     return true;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 203:    */   {
/* 204:257 */     return Helper.buildMetaAttributeSources(this.manyToOneElement.getMeta());
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getReferencedEntityName()
/* 208:    */   {
/* 209:262 */     return this.manyToOneElement.getClazz() != null ? this.manyToOneElement.getClazz() : this.manyToOneElement.getEntityName();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String getReferencedEntityAttributeName()
/* 213:    */   {
/* 214:269 */     return this.manyToOneElement.getPropertyRef();
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.ManyToOneAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */