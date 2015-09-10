/*  1:   */ package org.hibernate.action.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class DelayedPostInsertIdentifier
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:41 */   private static long SEQUENCE = 0L;
/*  9:   */   private final long sequence;
/* 10:   */   
/* 11:   */   public DelayedPostInsertIdentifier()
/* 12:   */   {
/* 13:45 */     synchronized (DelayedPostInsertIdentifier.class)
/* 14:   */     {
/* 15:46 */       if (SEQUENCE == 9223372036854775807L) {
/* 16:47 */         SEQUENCE = 0L;
/* 17:   */       }
/* 18:49 */       this.sequence = (SEQUENCE++);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:55 */     if (this == o) {
/* 25:56 */       return true;
/* 26:   */     }
/* 27:58 */     if ((o == null) || (getClass() != o.getClass())) {
/* 28:59 */       return false;
/* 29:   */     }
/* 30:61 */     DelayedPostInsertIdentifier that = (DelayedPostInsertIdentifier)o;
/* 31:62 */     return this.sequence == that.sequence;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int hashCode()
/* 35:   */   {
/* 36:67 */     return (int)(this.sequence ^ this.sequence >>> 32);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toString()
/* 40:   */   {
/* 41:72 */     return "<delayed:" + this.sequence + ">";
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.DelayedPostInsertIdentifier
 * JD-Core Version:    0.7.0.1
 */