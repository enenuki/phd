/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.mapping.Bag;
/*  4:   */ import org.hibernate.mapping.Collection;
/*  5:   */ import org.hibernate.mapping.PersistentClass;
/*  6:   */ 
/*  7:   */ public class BagBinder
/*  8:   */   extends CollectionBinder
/*  9:   */ {
/* 10:   */   protected Collection createCollection(PersistentClass persistentClass)
/* 11:   */   {
/* 12:39 */     return new Bag(getMappings(), persistentClass);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.BagBinder
 * JD-Core Version:    0.7.0.1
 */