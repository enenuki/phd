/*  1:   */ package org.hibernate.internal.util.collections;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.LinkedHashMap;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ 
/*  7:   */ public class LRUMap
/*  8:   */   extends LinkedHashMap
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -5522608033020688048L;
/* 12:   */   private final int maxEntries;
/* 13:   */   
/* 14:   */   public LRUMap(int maxEntries)
/* 15:   */   {
/* 16:43 */     super(maxEntries, 0.75F, true);
/* 17:44 */     this.maxEntries = maxEntries;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected boolean removeEldestEntry(Map.Entry eldest)
/* 21:   */   {
/* 22:49 */     return size() > this.maxEntries;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.LRUMap
 * JD-Core Version:    0.7.0.1
 */