/*  1:   */ package org.hibernate.cache.internal;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.atomic.AtomicLong;
/*  4:   */ 
/*  5:   */ public final class Timestamper
/*  6:   */ {
/*  7:   */   private static final int BIN_DIGITS = 12;
/*  8:   */   public static final short ONE_MS = 4096;
/*  9:35 */   private static final AtomicLong VALUE = new AtomicLong();
/* 10:   */   
/* 11:   */   public static long next()
/* 12:   */   {
/* 13:   */     for (;;)
/* 14:   */     {
/* 15:38 */       long base = System.currentTimeMillis() << 12;
/* 16:39 */       long maxValue = base + 4096L - 1L;
/* 17:   */       
/* 18:41 */       long current = VALUE.get();
/* 19:41 */       for (long update = Math.max(base, current + 1L); update < maxValue; update = Math.max(base, current + 1L))
/* 20:   */       {
/* 21:43 */         if (VALUE.compareAndSet(current, update)) {
/* 22:44 */           return update;
/* 23:   */         }
/* 24:42 */         current = VALUE.get();
/* 25:   */       }
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.Timestamper
 * JD-Core Version:    0.7.0.1
 */