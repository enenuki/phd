/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class SortSpecification
/*  6:   */   extends NodeSupport
/*  7:   */ {
/*  8:   */   public SortKey getSortKey()
/*  9:   */   {
/* 10:40 */     return (SortKey)getFirstChild();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public CollationSpecification getCollation()
/* 14:   */   {
/* 15:49 */     AST possible = getSortKey().getNextSibling();
/* 16:50 */     return (possible != null) && (12 == possible.getType()) ? (CollationSpecification)possible : null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public OrderingSpecification getOrdering()
/* 20:   */   {
/* 21:64 */     AST possible = getSortKey().getNextSibling();
/* 22:65 */     if (possible == null) {
/* 23:67 */       return null;
/* 24:   */     }
/* 25:70 */     if (12 == possible.getType()) {
/* 26:72 */       possible = possible.getNextSibling();
/* 27:   */     }
/* 28:75 */     return (possible != null) && (6 == possible.getType()) ? (OrderingSpecification)possible : null;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.SortSpecification
 * JD-Core Version:    0.7.0.1
 */