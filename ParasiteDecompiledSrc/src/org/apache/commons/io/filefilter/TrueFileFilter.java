/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class TrueFileFilter
/*  7:   */   implements IOFileFilter, Serializable
/*  8:   */ {
/*  9:37 */   public static final IOFileFilter TRUE = new TrueFileFilter();
/* 10:44 */   public static final IOFileFilter INSTANCE = TRUE;
/* 11:   */   
/* 12:   */   public boolean accept(File file)
/* 13:   */   {
/* 14:59 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean accept(File dir, String name)
/* 18:   */   {
/* 19:70 */     return true;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.TrueFileFilter
 * JD-Core Version:    0.7.0.1
 */