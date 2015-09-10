/*  1:   */ package org.hibernate.metamodel.source.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  5:   */ import org.hibernate.metamodel.binding.EntityBinding;
/*  6:   */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  7:   */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  8:   */ import org.hibernate.metamodel.binding.SingularAssociationAttributeBinding;
/*  9:   */ import org.hibernate.metamodel.domain.Attribute;
/* 10:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/* 11:   */ 
/* 12:   */ class AssociationResolver
/* 13:   */ {
/* 14:   */   private final MetadataImplementor metadata;
/* 15:   */   
/* 16:   */   AssociationResolver(MetadataImplementor metadata)
/* 17:   */   {
/* 18:38 */     this.metadata = metadata;
/* 19:   */   }
/* 20:   */   
/* 21:   */   void resolve()
/* 22:   */   {
/* 23:42 */     for (EntityBinding entityBinding : this.metadata.getEntityBindings()) {
/* 24:43 */       for (SingularAssociationAttributeBinding attributeBinding : entityBinding.getEntityReferencingAttributeBindings()) {
/* 25:44 */         resolve(attributeBinding);
/* 26:   */       }
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   private void resolve(SingularAssociationAttributeBinding attributeBinding)
/* 31:   */   {
/* 32:50 */     if (attributeBinding.getReferencedEntityName() == null) {
/* 33:51 */       throw new IllegalArgumentException("attributeBinding has null entityName: " + attributeBinding.getAttribute().getName());
/* 34:   */     }
/* 35:55 */     EntityBinding entityBinding = this.metadata.getEntityBinding(attributeBinding.getReferencedEntityName());
/* 36:56 */     if (entityBinding == null) {
/* 37:57 */       throw new MappingException(String.format("Attribute [%s] refers to unknown entity: [%s]", new Object[] { attributeBinding.getAttribute().getName(), attributeBinding.getReferencedEntityName() }));
/* 38:   */     }
/* 39:65 */     AttributeBinding referencedAttributeBinding = attributeBinding.isPropertyReference() ? entityBinding.locateAttributeBinding(attributeBinding.getReferencedAttributeName()) : entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding();
/* 40:69 */     if (referencedAttributeBinding == null) {
/* 41:71 */       throw new MappingException(String.format("Attribute [%s] refers to unknown attribute: [%s]", new Object[] { attributeBinding.getAttribute().getName(), attributeBinding.getReferencedEntityName() }));
/* 42:   */     }
/* 43:79 */     attributeBinding.resolveReference(referencedAttributeBinding);
/* 44:80 */     referencedAttributeBinding.addEntityReferencingAttributeBinding(attributeBinding);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.AssociationResolver
 * JD-Core Version:    0.7.0.1
 */