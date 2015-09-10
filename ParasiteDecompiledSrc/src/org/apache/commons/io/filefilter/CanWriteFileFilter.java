/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class CanWriteFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:58 */   public static final IOFileFilter CAN_WRITE = new CanWriteFileFilter();
/* 11:61 */   public static final IOFileFilter CANNOT_WRITE = new NotFileFilter(CAN_WRITE);
/* 12:   */   
/* 13:   */   public boolean accept(File file)
/* 14:   */   {
/* 15:78 */     return file.canWrite();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.CanWriteFileFilter
 * JD-Core Version:    0.7.0.1
 */