/*  1:   */ package org.hibernate.internal.util.compare;
/*  2:   */ 
/*  3:   */ public final class EqualsHelper
/*  4:   */ {
/*  5:   */   public static boolean equals(Object x, Object y)
/*  6:   */   {
/*  7:34 */     return (x == y) || ((x != null) && (y != null) && (x.equals(y)));
/*  8:   */   }
/*  9:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.compare.EqualsHelper
 * JD-Core Version:    0.7.0.1
 */