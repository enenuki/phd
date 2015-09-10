/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.EntityMode;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbAnyElement;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbComponentElement;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToManyElement;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbManyToOneElement;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToManyElement;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToOneElement;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbParentElement;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbPropertyElement;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTuplizerElement;
/*  15:    */ import org.hibernate.internal.util.StringHelper;
/*  16:    */ import org.hibernate.internal.util.Value;
/*  17:    */ import org.hibernate.mapping.PropertyGeneration;
/*  18:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  19:    */ import org.hibernate.metamodel.source.binder.AttributeSource;
/*  20:    */ import org.hibernate.metamodel.source.binder.AttributeSourceContainer;
/*  21:    */ import org.hibernate.metamodel.source.binder.ComponentAttributeSource;
/*  22:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  23:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  24:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  25:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  26:    */ 
/*  27:    */ public class ComponentAttributeSourceImpl
/*  28:    */   implements ComponentAttributeSource
/*  29:    */ {
/*  30:    */   private final JaxbComponentElement componentElement;
/*  31:    */   private final AttributeSourceContainer parentContainer;
/*  32:    */   private final Value<Class<?>> componentClassReference;
/*  33:    */   private final String path;
/*  34:    */   
/*  35:    */   public ComponentAttributeSourceImpl(JaxbComponentElement componentElement, AttributeSourceContainer parentContainer, LocalBindingContext bindingContext)
/*  36:    */   {
/*  37: 64 */     this.componentElement = componentElement;
/*  38: 65 */     this.parentContainer = parentContainer;
/*  39:    */     
/*  40: 67 */     this.componentClassReference = bindingContext.makeClassReference(bindingContext.qualifyClassName(componentElement.getClazz()));
/*  41:    */     
/*  42:    */ 
/*  43: 70 */     this.path = (parentContainer.getPath() + '.' + componentElement.getName());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getClassName()
/*  47:    */   {
/*  48: 75 */     return this.componentElement.getClazz();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Value<Class<?>> getClassReference()
/*  52:    */   {
/*  53: 80 */     return this.componentClassReference;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getPath()
/*  57:    */   {
/*  58: 85 */     return this.path;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public LocalBindingContext getLocalBindingContext()
/*  62:    */   {
/*  63: 90 */     return this.parentContainer.getLocalBindingContext();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getParentReferenceAttributeName()
/*  67:    */   {
/*  68: 95 */     return this.componentElement.getParent() == null ? null : this.componentElement.getParent().getName();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getExplicitTuplizerClassName()
/*  72:    */   {
/*  73:100 */     if (this.componentElement.getTuplizer() == null) {
/*  74:101 */       return null;
/*  75:    */     }
/*  76:103 */     EntityMode entityMode = StringHelper.isEmpty(this.componentElement.getClazz()) ? EntityMode.MAP : EntityMode.POJO;
/*  77:104 */     for (JaxbTuplizerElement tuplizerElement : this.componentElement.getTuplizer()) {
/*  78:105 */       if (entityMode == EntityMode.parse(tuplizerElement.getEntityMode())) {
/*  79:106 */         return tuplizerElement.getClazz();
/*  80:    */       }
/*  81:    */     }
/*  82:109 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Iterable<AttributeSource> attributeSources()
/*  86:    */   {
/*  87:114 */     List<AttributeSource> attributeSources = new ArrayList();
/*  88:115 */     for (Object attributeElement : this.componentElement.getPropertyOrManyToOneOrOneToOne()) {
/*  89:116 */       if (JaxbPropertyElement.class.isInstance(attributeElement)) {
/*  90:117 */         attributeSources.add(new PropertyAttributeSourceImpl((JaxbPropertyElement)JaxbPropertyElement.class.cast(attributeElement), getLocalBindingContext()));
/*  91:124 */       } else if (JaxbComponentElement.class.isInstance(attributeElement)) {
/*  92:125 */         attributeSources.add(new ComponentAttributeSourceImpl((JaxbComponentElement)attributeElement, this, getLocalBindingContext()));
/*  93:133 */       } else if (JaxbManyToOneElement.class.isInstance(attributeElement)) {
/*  94:134 */         attributeSources.add(new ManyToOneAttributeSourceImpl((JaxbManyToOneElement)JaxbManyToOneElement.class.cast(attributeElement), getLocalBindingContext()));
/*  95:141 */       } else if (!JaxbOneToOneElement.class.isInstance(attributeElement)) {
/*  96:144 */         if (!JaxbAnyElement.class.isInstance(attributeElement)) {
/*  97:147 */           if (!JaxbOneToManyElement.class.isInstance(attributeElement)) {
/*  98:150 */             if (!JaxbManyToManyElement.class.isInstance(attributeElement)) {}
/*  99:    */           }
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:154 */     return attributeSources;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isVirtualAttribute()
/* 107:    */   {
/* 108:159 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public SingularAttributeNature getNature()
/* 112:    */   {
/* 113:164 */     return SingularAttributeNature.COMPONENT;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ExplicitHibernateTypeSource getTypeInformation()
/* 117:    */   {
/* 118:170 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getName()
/* 122:    */   {
/* 123:175 */     return this.componentElement.getName();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isSingular()
/* 127:    */   {
/* 128:180 */     return true;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getPropertyAccessorName()
/* 132:    */   {
/* 133:185 */     return this.componentElement.getAccess();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isInsertable()
/* 137:    */   {
/* 138:190 */     return this.componentElement.isInsert();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isUpdatable()
/* 142:    */   {
/* 143:195 */     return this.componentElement.isUpdate();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public PropertyGeneration getGeneration()
/* 147:    */   {
/* 148:201 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isLazy()
/* 152:    */   {
/* 153:206 */     return this.componentElement.isLazy();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isIncludedInOptimisticLocking()
/* 157:    */   {
/* 158:211 */     return this.componentElement.isOptimisticLock();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 162:    */   {
/* 163:216 */     return Helper.buildMetaAttributeSources(this.componentElement.getMeta());
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean areValuesIncludedInInsertByDefault()
/* 167:    */   {
/* 168:221 */     return isInsertable();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 172:    */   {
/* 173:226 */     return isUpdatable();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean areValuesNullableByDefault()
/* 177:    */   {
/* 178:231 */     return true;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<RelationalValueSource> relationalValueSources()
/* 182:    */   {
/* 183:237 */     return null;
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.ComponentAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */