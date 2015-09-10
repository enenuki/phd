/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbNotFoundAttribute;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbOneToManyElement;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  7:   */ import org.hibernate.metamodel.source.binder.OneToManyPluralAttributeElementSource;
/*  8:   */ import org.hibernate.metamodel.source.binder.PluralAttributeElementNature;
/*  9:   */ 
/* 10:   */ public class OneToManyPluralAttributeElementSourceImpl
/* 11:   */   implements OneToManyPluralAttributeElementSource
/* 12:   */ {
/* 13:   */   private final JaxbOneToManyElement oneToManyElement;
/* 14:   */   private final LocalBindingContext bindingContext;
/* 15:   */   
/* 16:   */   public OneToManyPluralAttributeElementSourceImpl(JaxbOneToManyElement oneToManyElement, LocalBindingContext bindingContext)
/* 17:   */   {
/* 18:42 */     this.oneToManyElement = oneToManyElement;
/* 19:43 */     this.bindingContext = bindingContext;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public PluralAttributeElementNature getNature()
/* 23:   */   {
/* 24:48 */     return PluralAttributeElementNature.ONE_TO_MANY;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getReferencedEntityName()
/* 28:   */   {
/* 29:53 */     return StringHelper.isNotEmpty(this.oneToManyElement.getEntityName()) ? this.oneToManyElement.getEntityName() : this.bindingContext.qualifyClassName(this.oneToManyElement.getClazz());
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isNotFoundAnException()
/* 33:   */   {
/* 34:60 */     return (this.oneToManyElement.getNotFound() == null) || (!"ignore".equals(this.oneToManyElement.getNotFound().value()));
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.OneToManyPluralAttributeElementSourceImpl
 * JD-Core Version:    0.7.0.1
 */