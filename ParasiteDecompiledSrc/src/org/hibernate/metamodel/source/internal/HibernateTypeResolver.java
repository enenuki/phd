/*   1:    */ package org.hibernate.metamodel.source.internal;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.metamodel.binding.AbstractCollectionElement;
/*   6:    */ import org.hibernate.metamodel.binding.AbstractPluralAttributeBinding;
/*   7:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*   8:    */ import org.hibernate.metamodel.binding.BasicCollectionElement;
/*   9:    */ import org.hibernate.metamodel.binding.CollectionElementNature;
/*  10:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  11:    */ import org.hibernate.metamodel.binding.EntityDiscriminator;
/*  12:    */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*  13:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  14:    */ import org.hibernate.metamodel.binding.SingularAttributeBinding;
/*  15:    */ import org.hibernate.metamodel.domain.Attribute;
/*  16:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  17:    */ import org.hibernate.metamodel.domain.PluralAttributeNature;
/*  18:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  19:    */ import org.hibernate.metamodel.relational.Datatype;
/*  20:    */ import org.hibernate.metamodel.relational.SimpleValue;
/*  21:    */ import org.hibernate.metamodel.relational.Value;
/*  22:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  23:    */ import org.hibernate.type.TypeFactory;
/*  24:    */ import org.hibernate.type.TypeResolver;
/*  25:    */ 
/*  26:    */ class HibernateTypeResolver
/*  27:    */ {
/*  28:    */   private final MetadataImplementor metadata;
/*  29:    */   
/*  30:    */   HibernateTypeResolver(MetadataImplementor metadata)
/*  31:    */   {
/*  32: 57 */     this.metadata = metadata;
/*  33:    */   }
/*  34:    */   
/*  35:    */   void resolve()
/*  36:    */   {
/*  37: 61 */     for (EntityBinding entityBinding : this.metadata.getEntityBindings())
/*  38:    */     {
/*  39: 62 */       if (entityBinding.getHierarchyDetails().getEntityDiscriminator() != null) {
/*  40: 63 */         resolveDiscriminatorTypeInformation(entityBinding.getHierarchyDetails().getEntityDiscriminator());
/*  41:    */       }
/*  42: 65 */       for (AttributeBinding attributeBinding : entityBinding.attributeBindings()) {
/*  43: 66 */         if (SingularAttributeBinding.class.isInstance(attributeBinding)) {
/*  44: 67 */           resolveSingularAttributeTypeInformation((SingularAttributeBinding)SingularAttributeBinding.class.cast(attributeBinding));
/*  45: 71 */         } else if (AbstractPluralAttributeBinding.class.isInstance(attributeBinding)) {
/*  46: 72 */           resolvePluralAttributeTypeInformation((AbstractPluralAttributeBinding)AbstractPluralAttributeBinding.class.cast(attributeBinding));
/*  47:    */         } else {
/*  48: 77 */           throw new AssertionFailure("Unknown type of AttributeBinding: " + attributeBinding.getClass().getName());
/*  49:    */         }
/*  50:    */       }
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void resolveDiscriminatorTypeInformation(EntityDiscriminator discriminator)
/*  55:    */   {
/*  56: 86 */     org.hibernate.type.Type resolvedHibernateType = determineSingularTypeFromDescriptor(discriminator.getExplicitHibernateTypeDescriptor());
/*  57: 87 */     if (resolvedHibernateType != null) {
/*  58: 88 */       pushHibernateTypeInformationDownIfNeeded(discriminator.getExplicitHibernateTypeDescriptor(), discriminator.getBoundValue(), resolvedHibernateType);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private org.hibernate.type.Type determineSingularTypeFromDescriptor(HibernateTypeDescriptor hibernateTypeDescriptor)
/*  63:    */   {
/*  64: 97 */     if (hibernateTypeDescriptor.getResolvedTypeMapping() != null) {
/*  65: 98 */       return hibernateTypeDescriptor.getResolvedTypeMapping();
/*  66:    */     }
/*  67:100 */     String typeName = determineTypeName(hibernateTypeDescriptor);
/*  68:101 */     Properties typeParameters = getTypeParameters(hibernateTypeDescriptor);
/*  69:102 */     return getHeuristicType(typeName, typeParameters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static String determineTypeName(HibernateTypeDescriptor hibernateTypeDescriptor)
/*  73:    */   {
/*  74:106 */     return hibernateTypeDescriptor.getExplicitTypeName() != null ? hibernateTypeDescriptor.getExplicitTypeName() : hibernateTypeDescriptor.getJavaTypeName();
/*  75:    */   }
/*  76:    */   
/*  77:    */   private static Properties getTypeParameters(HibernateTypeDescriptor hibernateTypeDescriptor)
/*  78:    */   {
/*  79:112 */     Properties typeParameters = new Properties();
/*  80:113 */     if (hibernateTypeDescriptor.getTypeParameters() != null) {
/*  81:114 */       typeParameters.putAll(hibernateTypeDescriptor.getTypeParameters());
/*  82:    */     }
/*  83:116 */     return typeParameters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void resolveSingularAttributeTypeInformation(SingularAttributeBinding attributeBinding)
/*  87:    */   {
/*  88:121 */     if (attributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping() != null) {
/*  89:122 */       return;
/*  90:    */     }
/*  91:128 */     org.hibernate.type.Type resolvedType = determineSingularTypeFromDescriptor(attributeBinding.getHibernateTypeDescriptor());
/*  92:129 */     if (resolvedType == null)
/*  93:    */     {
/*  94:130 */       if (!attributeBinding.getAttribute().isSingular()) {
/*  95:131 */         throw new AssertionFailure("SingularAttributeBinding object has a plural attribute: " + attributeBinding.getAttribute().getName());
/*  96:    */       }
/*  97:133 */       SingularAttribute singularAttribute = (SingularAttribute)attributeBinding.getAttribute();
/*  98:134 */       if (singularAttribute.getSingularAttributeType() != null) {
/*  99:135 */         resolvedType = getHeuristicType(singularAttribute.getSingularAttributeType().getClassName(), new Properties());
/* 100:    */       }
/* 101:    */     }
/* 102:140 */     if (resolvedType != null) {
/* 103:141 */       pushHibernateTypeInformationDownIfNeeded(attributeBinding, resolvedType);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void resolvePluralAttributeTypeInformation(AbstractPluralAttributeBinding attributeBinding)
/* 108:    */   {
/* 109:147 */     if (attributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping() != null) {
/* 110:148 */       return;
/* 111:    */     }
/* 112:153 */     String typeName = attributeBinding.getHibernateTypeDescriptor().getExplicitTypeName();
/* 113:    */     org.hibernate.type.Type resolvedType;
/* 114:    */     org.hibernate.type.Type resolvedType;
/* 115:154 */     if (typeName != null) {
/* 116:155 */       resolvedType = this.metadata.getTypeResolver().getTypeFactory().customCollection(typeName, getTypeParameters(attributeBinding.getHibernateTypeDescriptor()), attributeBinding.getAttribute().getName(), attributeBinding.getReferencedPropertyName(), attributeBinding.getCollectionElement().getCollectionElementNature() == CollectionElementNature.COMPOSITE);
/* 117:    */     } else {
/* 118:168 */       resolvedType = determineDefaultCollectionInformation(attributeBinding);
/* 119:    */     }
/* 120:170 */     if (resolvedType != null) {
/* 121:171 */       pushHibernateTypeInformationDownIfNeeded(attributeBinding.getHibernateTypeDescriptor(), null, resolvedType);
/* 122:    */     }
/* 123:176 */     resolveCollectionElementTypeInformation(attributeBinding.getCollectionElement());
/* 124:    */   }
/* 125:    */   
/* 126:    */   private org.hibernate.type.Type determineDefaultCollectionInformation(AbstractPluralAttributeBinding attributeBinding)
/* 127:    */   {
/* 128:180 */     TypeFactory typeFactory = this.metadata.getTypeResolver().getTypeFactory();
/* 129:181 */     switch (attributeBinding.getAttribute().getNature())
/* 130:    */     {
/* 131:    */     case SET: 
/* 132:183 */       return typeFactory.set(attributeBinding.getAttribute().getName(), attributeBinding.getReferencedPropertyName(), attributeBinding.getCollectionElement().getCollectionElementNature() == CollectionElementNature.COMPOSITE);
/* 133:    */     case BAG: 
/* 134:190 */       return typeFactory.bag(attributeBinding.getAttribute().getName(), attributeBinding.getReferencedPropertyName(), attributeBinding.getCollectionElement().getCollectionElementNature() == CollectionElementNature.COMPOSITE);
/* 135:    */     }
/* 136:198 */     throw new UnsupportedOperationException("Collection type not supported yet:" + attributeBinding.getAttribute().getNature());
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void resolveCollectionElementTypeInformation(AbstractCollectionElement collectionElement)
/* 140:    */   {
/* 141:206 */     switch (1.$SwitchMap$org$hibernate$metamodel$binding$CollectionElementNature[collectionElement.getCollectionElementNature().ordinal()])
/* 142:    */     {
/* 143:    */     case 1: 
/* 144:208 */       resolveBasicCollectionElement((BasicCollectionElement)BasicCollectionElement.class.cast(collectionElement));
/* 145:209 */       break;
/* 146:    */     case 2: 
/* 147:    */     case 3: 
/* 148:    */     case 4: 
/* 149:    */     case 5: 
/* 150:215 */       throw new UnsupportedOperationException("Collection element nature not supported yet: " + collectionElement.getCollectionElementNature());
/* 151:    */     default: 
/* 152:218 */       throw new AssertionFailure("Unknown collection element nature : " + collectionElement.getCollectionElementNature());
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void resolveBasicCollectionElement(BasicCollectionElement basicCollectionElement)
/* 157:    */   {
/* 158:224 */     org.hibernate.type.Type resolvedHibernateType = determineSingularTypeFromDescriptor(basicCollectionElement.getHibernateTypeDescriptor());
/* 159:225 */     if (resolvedHibernateType != null) {
/* 160:226 */       pushHibernateTypeInformationDownIfNeeded(basicCollectionElement.getHibernateTypeDescriptor(), basicCollectionElement.getElementValue(), resolvedHibernateType);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private org.hibernate.type.Type getHeuristicType(String typeName, Properties typeParameters)
/* 165:    */   {
/* 166:235 */     if (typeName != null) {
/* 167:    */       try
/* 168:    */       {
/* 169:237 */         return this.metadata.getTypeResolver().heuristicType(typeName, typeParameters);
/* 170:    */       }
/* 171:    */       catch (Exception ignore) {}
/* 172:    */     }
/* 173:243 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void pushHibernateTypeInformationDownIfNeeded(SingularAttributeBinding attributeBinding, org.hibernate.type.Type resolvedHibernateType)
/* 177:    */   {
/* 178:248 */     HibernateTypeDescriptor hibernateTypeDescriptor = attributeBinding.getHibernateTypeDescriptor();
/* 179:249 */     SingularAttribute singularAttribute = (SingularAttribute)SingularAttribute.class.cast(attributeBinding.getAttribute());
/* 180:250 */     Value value = attributeBinding.getValue();
/* 181:251 */     if ((!singularAttribute.isTypeResolved()) && (hibernateTypeDescriptor.getJavaTypeName() != null)) {
/* 182:252 */       singularAttribute.resolveType(this.metadata.makeJavaType(hibernateTypeDescriptor.getJavaTypeName()));
/* 183:    */     }
/* 184:256 */     pushHibernateTypeInformationDownIfNeeded(hibernateTypeDescriptor, value, resolvedHibernateType);
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void pushHibernateTypeInformationDownIfNeeded(HibernateTypeDescriptor hibernateTypeDescriptor, Value value, org.hibernate.type.Type resolvedHibernateType)
/* 188:    */   {
/* 189:265 */     if (resolvedHibernateType == null) {
/* 190:266 */       return;
/* 191:    */     }
/* 192:268 */     if (hibernateTypeDescriptor.getResolvedTypeMapping() == null) {
/* 193:269 */       hibernateTypeDescriptor.setResolvedTypeMapping(resolvedHibernateType);
/* 194:    */     }
/* 195:274 */     if (hibernateTypeDescriptor.getJavaTypeName() == null) {
/* 196:275 */       hibernateTypeDescriptor.setJavaTypeName(resolvedHibernateType.getReturnedClass().getName());
/* 197:    */     }
/* 198:280 */     if (SimpleValue.class.isInstance(value))
/* 199:    */     {
/* 200:281 */       SimpleValue simpleValue = (SimpleValue)value;
/* 201:282 */       if (simpleValue.getDatatype() == null) {
/* 202:283 */         simpleValue.setDatatype(new Datatype(resolvedHibernateType.sqlTypes(this.metadata)[0], resolvedHibernateType.getName(), resolvedHibernateType.getReturnedClass()));
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.HibernateTypeResolver
 * JD-Core Version:    0.7.0.1
 */