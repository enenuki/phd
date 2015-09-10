/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ abstract class Pair<T, U>
/*  4:   */ {
/*  5:   */   private final T o1;
/*  6:   */   private final U o2;
/*  7:   */   private final int hashCode;
/*  8:   */   
/*  9:   */   Pair(T o1, U o2)
/* 10:   */   {
/* 11:40 */     this.o1 = o1;
/* 12:41 */     this.o2 = o2;
/* 13:42 */     this.hashCode = doHashCode();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean equals(Object obj)
/* 17:   */   {
/* 18:47 */     if (!(obj instanceof Pair)) {
/* 19:48 */       return false;
/* 20:   */     }
/* 21:50 */     Pair other = (Pair)obj;
/* 22:51 */     return (!differentHashCode(other)) && (safeEquals(this.o1, other.o1)) && (safeEquals(this.o2, other.o2));
/* 23:   */   }
/* 24:   */   
/* 25:   */   private boolean differentHashCode(Pair other)
/* 26:   */   {
/* 27:55 */     return this.hashCode != other.hashCode;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int hashCode()
/* 31:   */   {
/* 32:61 */     return this.hashCode;
/* 33:   */   }
/* 34:   */   
/* 35:   */   private int doHashCode()
/* 36:   */   {
/* 37:65 */     return safeHashCode(this.o1) ^ safeHashCode(this.o2);
/* 38:   */   }
/* 39:   */   
/* 40:   */   private int safeHashCode(Object o)
/* 41:   */   {
/* 42:69 */     if (o == null) {
/* 43:70 */       return 0;
/* 44:   */     }
/* 45:72 */     return o.hashCode();
/* 46:   */   }
/* 47:   */   
/* 48:   */   private boolean safeEquals(Object obj1, Object obj2)
/* 49:   */   {
/* 50:76 */     if (obj1 == null) {
/* 51:77 */       return obj2 == null;
/* 52:   */     }
/* 53:79 */     return obj1.equals(obj2);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.Pair
 * JD-Core Version:    0.7.0.1
 */