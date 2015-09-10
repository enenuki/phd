/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.collection.AbstractCollectionDecorator;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSetDecorator
/*  7:   */   extends AbstractCollectionDecorator
/*  8:   */   implements Set
/*  9:   */ {
/* 10:   */   protected AbstractSetDecorator() {}
/* 11:   */   
/* 12:   */   protected AbstractSetDecorator(Set set)
/* 13:   */   {
/* 14:50 */     super(set);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected Set getSet()
/* 18:   */   {
/* 19:59 */     return (Set)getCollection();
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.AbstractSetDecorator
 * JD-Core Version:    0.7.0.1
 */