/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  5:   */ 
/*  6:   */ public class SetBinding
/*  7:   */   extends AbstractPluralAttributeBinding
/*  8:   */ {
/*  9:   */   private Comparator comparator;
/* 10:   */   
/* 11:   */   protected SetBinding(AttributeBindingContainer container, PluralAttribute attribute, CollectionElementNature collectionElementNature)
/* 12:   */   {
/* 13:40 */     super(container, attribute, collectionElementNature);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Comparator getComparator()
/* 17:   */   {
/* 18:44 */     return this.comparator;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setComparator(Comparator comparator)
/* 22:   */   {
/* 23:48 */     this.comparator = comparator;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.SetBinding
 * JD-Core Version:    0.7.0.1
 */