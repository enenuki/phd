/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbBagElement;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ import org.hibernate.metamodel.source.binder.AttributeSourceContainer;
/*  6:   */ import org.hibernate.metamodel.source.binder.Orderable;
/*  7:   */ import org.hibernate.metamodel.source.binder.PluralAttributeNature;
/*  8:   */ 
/*  9:   */ public class BagAttributeSourceImpl
/* 10:   */   extends AbstractPluralAttributeSourceImpl
/* 11:   */   implements Orderable
/* 12:   */ {
/* 13:   */   public BagAttributeSourceImpl(JaxbBagElement bagElement, AttributeSourceContainer container)
/* 14:   */   {
/* 15:37 */     super(bagElement, container);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public PluralAttributeNature getPluralAttributeNature()
/* 19:   */   {
/* 20:42 */     return PluralAttributeNature.BAG;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public JaxbBagElement getPluralAttributeElement()
/* 24:   */   {
/* 25:47 */     return (JaxbBagElement)super.getPluralAttributeElement();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isOrdered()
/* 29:   */   {
/* 30:52 */     return StringHelper.isNotEmpty(getOrder());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getOrder()
/* 34:   */   {
/* 35:57 */     return getPluralAttributeElement().getOrderBy();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.BagAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */