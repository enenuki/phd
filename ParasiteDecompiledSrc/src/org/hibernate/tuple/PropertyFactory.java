/*   1:    */ package org.hibernate.tuple;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.FetchMode;
/*   6:    */ import org.hibernate.engine.internal.UnsavedValueFactory;
/*   7:    */ import org.hibernate.engine.spi.CascadeStyle;
/*   8:    */ import org.hibernate.engine.spi.IdentifierValue;
/*   9:    */ import org.hibernate.engine.spi.VersionValue;
/*  10:    */ import org.hibernate.id.IdentifierGenerator;
/*  11:    */ import org.hibernate.internal.util.ReflectHelper;
/*  12:    */ import org.hibernate.mapping.KeyValue;
/*  13:    */ import org.hibernate.mapping.PersistentClass;
/*  14:    */ import org.hibernate.mapping.Property;
/*  15:    */ import org.hibernate.mapping.PropertyGeneration;
/*  16:    */ import org.hibernate.mapping.Value;
/*  17:    */ import org.hibernate.metamodel.binding.AbstractPluralAttributeBinding;
/*  18:    */ import org.hibernate.metamodel.binding.AssociationAttributeBinding;
/*  19:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  20:    */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*  21:    */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*  22:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  23:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  24:    */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*  25:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  26:    */ import org.hibernate.metamodel.binding.SimpleValueBinding;
/*  27:    */ import org.hibernate.metamodel.binding.SingularAttributeBinding;
/*  28:    */ import org.hibernate.metamodel.domain.Attribute;
/*  29:    */ import org.hibernate.metamodel.domain.Entity;
/*  30:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  31:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  32:    */ import org.hibernate.property.Getter;
/*  33:    */ import org.hibernate.property.PropertyAccessor;
/*  34:    */ import org.hibernate.property.PropertyAccessorFactory;
/*  35:    */ import org.hibernate.type.AssociationType;
/*  36:    */ import org.hibernate.type.Type;
/*  37:    */ import org.hibernate.type.VersionType;
/*  38:    */ 
/*  39:    */ public class PropertyFactory
/*  40:    */ {
/*  41:    */   public static IdentifierProperty buildIdentifierProperty(PersistentClass mappedEntity, IdentifierGenerator generator)
/*  42:    */   {
/*  43: 71 */     String mappedUnsavedValue = mappedEntity.getIdentifier().getNullValue();
/*  44: 72 */     Type type = mappedEntity.getIdentifier().getType();
/*  45: 73 */     Property property = mappedEntity.getIdentifierProperty();
/*  46:    */     
/*  47: 75 */     IdentifierValue unsavedValue = UnsavedValueFactory.getUnsavedIdentifierValue(mappedUnsavedValue, getGetter(property), type, getConstructor(mappedEntity));
/*  48: 82 */     if (property == null) {
/*  49: 84 */       return new IdentifierProperty(type, mappedEntity.hasEmbeddedIdentifier(), mappedEntity.hasIdentifierMapper(), unsavedValue, generator);
/*  50:    */     }
/*  51: 93 */     return new IdentifierProperty(property.getName(), property.getNodeName(), type, mappedEntity.hasEmbeddedIdentifier(), unsavedValue, generator);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static IdentifierProperty buildIdentifierProperty(EntityBinding mappedEntity, IdentifierGenerator generator)
/*  55:    */   {
/*  56:113 */     BasicAttributeBinding property = mappedEntity.getHierarchyDetails().getEntityIdentifier().getValueBinding();
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:119 */     String mappedUnsavedValue = property.getUnsavedValue();
/*  63:120 */     Type type = property.getHibernateTypeDescriptor().getResolvedTypeMapping();
/*  64:    */     
/*  65:122 */     IdentifierValue unsavedValue = UnsavedValueFactory.getUnsavedIdentifierValue(mappedUnsavedValue, getGetter(property), type, getConstructor(mappedEntity));
/*  66:129 */     if (property == null) {
/*  67:131 */       return new IdentifierProperty(type, mappedEntity.getHierarchyDetails().getEntityIdentifier().isEmbedded(), mappedEntity.getHierarchyDetails().getEntityIdentifier().isIdentifierMapper(), unsavedValue, generator);
/*  68:    */     }
/*  69:140 */     return new IdentifierProperty(property.getAttribute().getName(), null, type, mappedEntity.getHierarchyDetails().getEntityIdentifier().isEmbedded(), unsavedValue, generator);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static VersionProperty buildVersionProperty(Property property, boolean lazyAvailable)
/*  73:    */   {
/*  74:160 */     String mappedUnsavedValue = ((KeyValue)property.getValue()).getNullValue();
/*  75:    */     
/*  76:162 */     VersionValue unsavedValue = UnsavedValueFactory.getUnsavedVersionValue(mappedUnsavedValue, getGetter(property), (VersionType)property.getType(), getConstructor(property.getPersistentClass()));
/*  77:    */     
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:169 */     boolean lazy = (lazyAvailable) && (property.isLazy());
/*  84:    */     
/*  85:171 */     return new VersionProperty(property.getName(), property.getNodeName(), property.getValue().getType(), lazy, property.isInsertable(), property.isUpdateable(), (property.getGeneration() == PropertyGeneration.INSERT) || (property.getGeneration() == PropertyGeneration.ALWAYS), property.getGeneration() == PropertyGeneration.ALWAYS, property.isOptional(), (property.isUpdateable()) && (!lazy), property.isOptimisticLocked(), property.getCascadeStyle(), unsavedValue);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static VersionProperty buildVersionProperty(BasicAttributeBinding property, boolean lazyAvailable)
/*  89:    */   {
/*  90:197 */     String mappedUnsavedValue = ((KeyValue)property.getValue()).getNullValue();
/*  91:    */     
/*  92:199 */     VersionValue unsavedValue = UnsavedValueFactory.getUnsavedVersionValue(mappedUnsavedValue, getGetter(property), (VersionType)property.getHibernateTypeDescriptor().getResolvedTypeMapping(), getConstructor((EntityBinding)property.getContainer()));
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:206 */     boolean lazy = (lazyAvailable) && (property.isLazy());
/* 100:    */     
/* 101:208 */     CascadeStyle cascadeStyle = property.isAssociation() ? ((AssociationAttributeBinding)property).getCascadeStyle() : CascadeStyle.NONE;
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:212 */     return new VersionProperty(property.getAttribute().getName(), null, property.getHibernateTypeDescriptor().getResolvedTypeMapping(), lazy, true, true, (property.getGeneration() == PropertyGeneration.INSERT) || (property.getGeneration() == PropertyGeneration.ALWAYS), property.getGeneration() == PropertyGeneration.ALWAYS, property.isNullable(), !lazy, property.isIncludedInOptimisticLocking(), cascadeStyle, unsavedValue);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static StandardProperty buildStandardProperty(Property property, boolean lazyAvailable)
/* 109:    */   {
/* 110:240 */     Type type = property.getValue().getType();
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:249 */     boolean alwaysDirtyCheck = (type.isAssociationType()) && (((AssociationType)type).isAlwaysDirtyChecked());
/* 120:    */     
/* 121:    */ 
/* 122:252 */     return new StandardProperty(property.getName(), property.getNodeName(), type, (lazyAvailable) && (property.isLazy()), property.isInsertable(), property.isUpdateable(), (property.getGeneration() == PropertyGeneration.INSERT) || (property.getGeneration() == PropertyGeneration.ALWAYS), property.getGeneration() == PropertyGeneration.ALWAYS, property.isOptional(), (alwaysDirtyCheck) || (property.isUpdateable()), property.isOptimisticLocked(), property.getCascadeStyle(), property.getValue().getFetchMode());
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static StandardProperty buildStandardProperty(AttributeBinding property, boolean lazyAvailable)
/* 126:    */   {
/* 127:279 */     Type type = property.getHibernateTypeDescriptor().getResolvedTypeMapping();
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:288 */     boolean alwaysDirtyCheck = (type.isAssociationType()) && (((AssociationType)type).isAlwaysDirtyChecked());
/* 137:290 */     if (property.getAttribute().isSingular())
/* 138:    */     {
/* 139:291 */       SingularAttributeBinding singularAttributeBinding = (SingularAttributeBinding)property;
/* 140:292 */       CascadeStyle cascadeStyle = singularAttributeBinding.isAssociation() ? ((AssociationAttributeBinding)singularAttributeBinding).getCascadeStyle() : CascadeStyle.NONE;
/* 141:    */       
/* 142:    */ 
/* 143:295 */       FetchMode fetchMode = singularAttributeBinding.isAssociation() ? ((AssociationAttributeBinding)singularAttributeBinding).getFetchMode() : FetchMode.DEFAULT;
/* 144:    */       
/* 145:    */ 
/* 146:    */ 
/* 147:299 */       return new StandardProperty(singularAttributeBinding.getAttribute().getName(), null, type, (lazyAvailable) && (singularAttributeBinding.isLazy()), true, true, (singularAttributeBinding.getGeneration() == PropertyGeneration.INSERT) || (singularAttributeBinding.getGeneration() == PropertyGeneration.ALWAYS), singularAttributeBinding.getGeneration() == PropertyGeneration.ALWAYS, singularAttributeBinding.isNullable(), (alwaysDirtyCheck) || (areAllValuesIncludedInUpdate(singularAttributeBinding)), singularAttributeBinding.isIncludedInOptimisticLocking(), cascadeStyle, fetchMode);
/* 148:    */     }
/* 149:317 */     AbstractPluralAttributeBinding pluralAttributeBinding = (AbstractPluralAttributeBinding)property;
/* 150:318 */     CascadeStyle cascadeStyle = pluralAttributeBinding.isAssociation() ? pluralAttributeBinding.getCascadeStyle() : CascadeStyle.NONE;
/* 151:    */     
/* 152:    */ 
/* 153:321 */     FetchMode fetchMode = pluralAttributeBinding.isAssociation() ? pluralAttributeBinding.getFetchMode() : FetchMode.DEFAULT;
/* 154:    */     
/* 155:    */ 
/* 156:    */ 
/* 157:325 */     return new StandardProperty(pluralAttributeBinding.getAttribute().getName(), null, type, (lazyAvailable) && (pluralAttributeBinding.isLazy()), true, true, false, false, false, true, pluralAttributeBinding.isIncludedInOptimisticLocking(), cascadeStyle, fetchMode);
/* 158:    */   }
/* 159:    */   
/* 160:    */   private static boolean areAllValuesIncludedInUpdate(SingularAttributeBinding attributeBinding)
/* 161:    */   {
/* 162:347 */     if (attributeBinding.hasDerivedValue()) {
/* 163:348 */       return false;
/* 164:    */     }
/* 165:350 */     for (SimpleValueBinding valueBinding : attributeBinding.getSimpleValueBindings()) {
/* 166:351 */       if (!valueBinding.isIncludeInUpdate()) {
/* 167:352 */         return false;
/* 168:    */       }
/* 169:    */     }
/* 170:355 */     return true;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private static Constructor getConstructor(PersistentClass persistentClass)
/* 174:    */   {
/* 175:359 */     if ((persistentClass == null) || (!persistentClass.hasPojoRepresentation())) {
/* 176:360 */       return null;
/* 177:    */     }
/* 178:    */     try
/* 179:    */     {
/* 180:364 */       return ReflectHelper.getDefaultConstructor(persistentClass.getMappedClass());
/* 181:    */     }
/* 182:    */     catch (Throwable t) {}
/* 183:367 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private static Constructor getConstructor(EntityBinding entityBinding)
/* 187:    */   {
/* 188:372 */     if ((entityBinding == null) || (entityBinding.getEntity() == null)) {
/* 189:373 */       return null;
/* 190:    */     }
/* 191:    */     try
/* 192:    */     {
/* 193:377 */       return ReflectHelper.getDefaultConstructor(entityBinding.getEntity().getClassReference());
/* 194:    */     }
/* 195:    */     catch (Throwable t) {}
/* 196:380 */     return null;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static Getter getGetter(Property mappingProperty)
/* 200:    */   {
/* 201:385 */     if ((mappingProperty == null) || (!mappingProperty.getPersistentClass().hasPojoRepresentation())) {
/* 202:386 */       return null;
/* 203:    */     }
/* 204:389 */     PropertyAccessor pa = PropertyAccessorFactory.getPropertyAccessor(mappingProperty, EntityMode.POJO);
/* 205:390 */     return pa.getGetter(mappingProperty.getPersistentClass().getMappedClass(), mappingProperty.getName());
/* 206:    */   }
/* 207:    */   
/* 208:    */   private static Getter getGetter(AttributeBinding mappingProperty)
/* 209:    */   {
/* 210:394 */     if ((mappingProperty == null) || (mappingProperty.getContainer().getClassReference() == null)) {
/* 211:395 */       return null;
/* 212:    */     }
/* 213:398 */     PropertyAccessor pa = PropertyAccessorFactory.getPropertyAccessor(mappingProperty, EntityMode.POJO);
/* 214:399 */     return pa.getGetter(mappingProperty.getContainer().getClassReference(), mappingProperty.getAttribute().getName());
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.PropertyFactory
 * JD-Core Version:    0.7.0.1
 */