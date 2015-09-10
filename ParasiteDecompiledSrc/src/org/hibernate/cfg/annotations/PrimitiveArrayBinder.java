/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.mapping.Collection;
/*  4:   */ import org.hibernate.mapping.PersistentClass;
/*  5:   */ import org.hibernate.mapping.PrimitiveArray;
/*  6:   */ 
/*  7:   */ public class PrimitiveArrayBinder
/*  8:   */   extends ArrayBinder
/*  9:   */ {
/* 10:   */   protected Collection createCollection(PersistentClass persistentClass)
/* 11:   */   {
/* 12:35 */     return new PrimitiveArray(getMappings(), persistentClass);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.PrimitiveArrayBinder
 * JD-Core Version:    0.7.0.1
 */