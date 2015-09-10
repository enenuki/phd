/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class DirectoryFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:49 */   public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();
/* 11:56 */   public static final IOFileFilter INSTANCE = DIRECTORY;
/* 12:   */   
/* 13:   */   public boolean accept(File file)
/* 14:   */   {
/* 15:72 */     return file.isDirectory();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.DirectoryFileFilter
 * JD-Core Version:    0.7.0.1
 */