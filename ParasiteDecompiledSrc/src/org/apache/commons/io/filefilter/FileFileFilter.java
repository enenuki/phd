/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class FileFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:43 */   public static final IOFileFilter FILE = new FileFileFilter();
/* 11:   */   
/* 12:   */   public boolean accept(File file)
/* 13:   */   {
/* 14:59 */     return file.isFile();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.FileFileFilter
 * JD-Core Version:    0.7.0.1
 */