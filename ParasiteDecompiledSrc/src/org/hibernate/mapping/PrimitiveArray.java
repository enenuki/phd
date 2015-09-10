/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Mappings;
/*  4:   */ 
/*  5:   */ public class PrimitiveArray
/*  6:   */   extends Array
/*  7:   */ {
/*  8:   */   public PrimitiveArray(Mappings mappings, PersistentClass owner)
/*  9:   */   {
/* 10:33 */     super(mappings, owner);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isPrimitiveArray()
/* 14:   */   {
/* 15:37 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object accept(ValueVisitor visitor)
/* 19:   */   {
/* 20:41 */     return visitor.accept(this);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.PrimitiveArray
 * JD-Core Version:    0.7.0.1
 */