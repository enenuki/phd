/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.mapping.Array;
/*  4:   */ import org.hibernate.mapping.Collection;
/*  5:   */ import org.hibernate.mapping.PersistentClass;
/*  6:   */ 
/*  7:   */ public class ArrayBinder
/*  8:   */   extends ListBinder
/*  9:   */ {
/* 10:   */   protected Collection createCollection(PersistentClass persistentClass)
/* 11:   */   {
/* 12:40 */     return new Array(getMappings(), persistentClass);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.ArrayBinder
 * JD-Core Version:    0.7.0.1
 */