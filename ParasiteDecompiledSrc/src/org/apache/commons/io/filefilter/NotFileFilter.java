/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class NotFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private final IOFileFilter filter;
/* 11:   */   
/* 12:   */   public NotFileFilter(IOFileFilter filter)
/* 13:   */   {
/* 14:43 */     if (filter == null) {
/* 15:44 */       throw new IllegalArgumentException("The filter must not be null");
/* 16:   */     }
/* 17:46 */     this.filter = filter;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean accept(File file)
/* 21:   */   {
/* 22:57 */     return !this.filter.accept(file);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean accept(File file, String name)
/* 26:   */   {
/* 27:69 */     return !this.filter.accept(file, name);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:79 */     return super.toString() + "(" + this.filter.toString() + ")";
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.NotFileFilter
 * JD-Core Version:    0.7.0.1
 */