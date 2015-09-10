/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class EmptyFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:57 */   public static final IOFileFilter EMPTY = new EmptyFileFilter();
/* 11:60 */   public static final IOFileFilter NOT_EMPTY = new NotFileFilter(EMPTY);
/* 12:   */   
/* 13:   */   public boolean accept(File file)
/* 14:   */   {
/* 15:77 */     if (file.isDirectory())
/* 16:   */     {
/* 17:78 */       File[] files = file.listFiles();
/* 18:79 */       return (files == null) || (files.length == 0);
/* 19:   */     }
/* 20:81 */     return file.length() == 0L;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.EmptyFileFilter
 * JD-Core Version:    0.7.0.1
 */