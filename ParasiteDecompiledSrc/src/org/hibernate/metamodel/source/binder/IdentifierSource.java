/*  1:   */ package org.hibernate.metamodel.source.binder;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.binding.IdGenerator;
/*  4:   */ 
/*  5:   */ public abstract interface IdentifierSource
/*  6:   */ {
/*  7:   */   public abstract IdGenerator getIdentifierGeneratorDescriptor();
/*  8:   */   
/*  9:   */   public abstract Nature getNature();
/* 10:   */   
/* 11:   */   public static enum Nature
/* 12:   */   {
/* 13:46 */     SIMPLE,  COMPOSITE,  AGGREGATED_COMPOSITE;
/* 14:   */     
/* 15:   */     private Nature() {}
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.IdentifierSource
 * JD-Core Version:    0.7.0.1
 */