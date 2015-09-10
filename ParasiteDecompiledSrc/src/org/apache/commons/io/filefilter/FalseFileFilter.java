/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class FalseFileFilter
/*  7:   */   implements IOFileFilter, Serializable
/*  8:   */ {
/*  9:37 */   public static final IOFileFilter FALSE = new FalseFileFilter();
/* 10:44 */   public static final IOFileFilter INSTANCE = FALSE;
/* 11:   */   
/* 12:   */   public boolean accept(File file)
/* 13:   */   {
/* 14:59 */     return false;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean accept(File dir, String name)
/* 18:   */   {
/* 19:70 */     return false;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FalseFileFilter
 * JD-Core Version:    0.7.0.1
 */