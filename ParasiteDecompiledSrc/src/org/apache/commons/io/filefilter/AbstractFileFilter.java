/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ 
/*  5:   */ public abstract class AbstractFileFilter
/*  6:   */   implements IOFileFilter
/*  7:   */ {
/*  8:   */   public boolean accept(File file)
/*  9:   */   {
/* 10:42 */     return accept(file.getParentFile(), file.getName());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean accept(File dir, String name)
/* 14:   */   {
/* 15:53 */     return accept(new File(dir, name));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString()
/* 19:   */   {
/* 20:63 */     return getClass().getSimpleName();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AbstractFileFilter
 * JD-Core Version:    0.7.0.1
 */