/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.AssertionFailure;
/*  4:   */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  5:   */ import org.hibernate.metamodel.domain.PluralAttributeNature;
/*  6:   */ 
/*  7:   */ public class Helper
/*  8:   */ {
/*  9:   */   public static void checkPluralAttributeNature(PluralAttribute attribute, PluralAttributeNature expected)
/* 10:   */   {
/* 11:37 */     if (attribute.getNature() != expected) {
/* 12:38 */       throw new AssertionFailure(String.format("Mismatched collection natures; expecting %s, but found %s", new Object[] { expected.getName(), attribute.getNature().getName() }));
/* 13:   */     }
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.Helper
 * JD-Core Version:    0.7.0.1
 */