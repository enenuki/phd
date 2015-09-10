/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class HiddenFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:54 */   public static final IOFileFilter HIDDEN = new HiddenFileFilter();
/* 11:57 */   public static final IOFileFilter VISIBLE = new NotFileFilter(HIDDEN);
/* 12:   */   
/* 13:   */   public boolean accept(File file)
/* 14:   */   {
/* 15:74 */     return file.isHidden();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.HiddenFileFilter
 * JD-Core Version:    0.7.0.1
 */