/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ public class OrderingSpecification
/*  4:   */   extends NodeSupport
/*  5:   */ {
/*  6:   */   private boolean resolved;
/*  7:   */   private Ordering ordering;
/*  8:   */   
/*  9:   */   public static class Ordering
/* 10:   */   {
/* 11:35 */     public static final Ordering ASCENDING = new Ordering("asc");
/* 12:36 */     public static final Ordering DESCENDING = new Ordering("desc");
/* 13:   */     private final String name;
/* 14:   */     
/* 15:   */     private Ordering(String name)
/* 16:   */     {
/* 17:41 */       this.name = name;
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Ordering getOrdering()
/* 22:   */   {
/* 23:49 */     if (!this.resolved)
/* 24:   */     {
/* 25:50 */       this.ordering = resolve(getText());
/* 26:51 */       this.resolved = true;
/* 27:   */     }
/* 28:53 */     return this.ordering;
/* 29:   */   }
/* 30:   */   
/* 31:   */   private static Ordering resolve(String text)
/* 32:   */   {
/* 33:57 */     if (Ordering.ASCENDING.name.equals(text)) {
/* 34:58 */       return Ordering.ASCENDING;
/* 35:   */     }
/* 36:60 */     if (Ordering.DESCENDING.name.equals(text)) {
/* 37:61 */       return Ordering.DESCENDING;
/* 38:   */     }
/* 39:64 */     throw new IllegalStateException("Unknown ordering [" + text + "]");
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getRenderableText()
/* 43:   */   {
/* 44:69 */     return getOrdering().name;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.OrderingSpecification
 * JD-Core Version:    0.7.0.1
 */