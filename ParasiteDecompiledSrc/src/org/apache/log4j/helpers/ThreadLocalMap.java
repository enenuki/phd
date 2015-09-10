/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.util.Hashtable;
/*  4:   */ 
/*  5:   */ public final class ThreadLocalMap
/*  6:   */   extends InheritableThreadLocal
/*  7:   */ {
/*  8:   */   public final Object childValue(Object parentValue)
/*  9:   */   {
/* 10:35 */     Hashtable ht = (Hashtable)parentValue;
/* 11:36 */     if (ht != null) {
/* 12:37 */       return ht.clone();
/* 13:   */     }
/* 14:39 */     return null;
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.ThreadLocalMap
 * JD-Core Version:    0.7.0.1
 */