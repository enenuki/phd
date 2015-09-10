/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ArrayComparisonFailure
/*  8:   */   extends AssertionError
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:16 */   private List<Integer> fIndices = new ArrayList();
/* 12:   */   private final String fMessage;
/* 13:   */   private final AssertionError fCause;
/* 14:   */   
/* 15:   */   public ArrayComparisonFailure(String message, AssertionError cause, int index)
/* 16:   */   {
/* 17:28 */     this.fMessage = message;
/* 18:29 */     this.fCause = cause;
/* 19:30 */     addDimension(index);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addDimension(int index)
/* 23:   */   {
/* 24:34 */     this.fIndices.add(0, Integer.valueOf(index));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getMessage()
/* 28:   */   {
/* 29:39 */     StringBuilder builder = new StringBuilder();
/* 30:40 */     if (this.fMessage != null) {
/* 31:41 */       builder.append(this.fMessage);
/* 32:   */     }
/* 33:42 */     builder.append("arrays first differed at element ");
/* 34:43 */     for (Iterator i$ = this.fIndices.iterator(); i$.hasNext();)
/* 35:   */     {
/* 36:43 */       int each = ((Integer)i$.next()).intValue();
/* 37:44 */       builder.append("[");
/* 38:45 */       builder.append(each);
/* 39:46 */       builder.append("]");
/* 40:   */     }
/* 41:48 */     builder.append("; ");
/* 42:49 */     builder.append(this.fCause.getMessage());
/* 43:50 */     return builder.toString();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String toString()
/* 47:   */   {
/* 48:57 */     return getMessage();
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.ArrayComparisonFailure
 * JD-Core Version:    0.7.0.1
 */