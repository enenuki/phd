/*  1:   */ package org.apache.commons.io.comparator;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.Comparator;
/*  6:   */ 
/*  7:   */ class ReverseComparator
/*  8:   */   extends AbstractFileComparator
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final Comparator<File> delegate;
/* 12:   */   
/* 13:   */   public ReverseComparator(Comparator<File> delegate)
/* 14:   */   {
/* 15:40 */     if (delegate == null) {
/* 16:41 */       throw new IllegalArgumentException("Delegate comparator is missing");
/* 17:   */     }
/* 18:43 */     this.delegate = delegate;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int compare(File file1, File file2)
/* 22:   */   {
/* 23:55 */     return this.delegate.compare(file2, file1);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:65 */     return super.toString() + "[" + this.delegate.toString() + "]";
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.ReverseComparator
 * JD-Core Version:    0.7.0.1
 */