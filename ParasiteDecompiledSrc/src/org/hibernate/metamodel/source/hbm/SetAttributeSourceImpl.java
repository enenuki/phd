/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSetElement;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ import org.hibernate.metamodel.source.binder.AttributeSourceContainer;
/*  6:   */ import org.hibernate.metamodel.source.binder.Orderable;
/*  7:   */ import org.hibernate.metamodel.source.binder.PluralAttributeNature;
/*  8:   */ import org.hibernate.metamodel.source.binder.Sortable;
/*  9:   */ 
/* 10:   */ public class SetAttributeSourceImpl
/* 11:   */   extends AbstractPluralAttributeSourceImpl
/* 12:   */   implements Orderable, Sortable
/* 13:   */ {
/* 14:   */   public SetAttributeSourceImpl(JaxbSetElement setElement, AttributeSourceContainer container)
/* 15:   */   {
/* 16:38 */     super(setElement, container);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public JaxbSetElement getPluralAttributeElement()
/* 20:   */   {
/* 21:43 */     return (JaxbSetElement)super.getPluralAttributeElement();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public PluralAttributeNature getPluralAttributeNature()
/* 25:   */   {
/* 26:48 */     return PluralAttributeNature.SET;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean isSorted()
/* 30:   */   {
/* 31:53 */     return StringHelper.isNotEmpty(getComparatorName());
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getComparatorName()
/* 35:   */   {
/* 36:58 */     return getPluralAttributeElement().getSort();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean isOrdered()
/* 40:   */   {
/* 41:63 */     return StringHelper.isNotEmpty(getOrder());
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getOrder()
/* 45:   */   {
/* 46:68 */     return getPluralAttributeElement().getOrderBy();
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.SetAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */