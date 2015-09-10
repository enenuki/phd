/*  1:   */ package org.apache.commons.io.filefilter;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ 
/*  6:   */ public class CanReadFileFilter
/*  7:   */   extends AbstractFileFilter
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:66 */   public static final IOFileFilter CAN_READ = new CanReadFileFilter();
/* 11:69 */   public static final IOFileFilter CANNOT_READ = new NotFileFilter(CAN_READ);
/* 12:72 */   public static final IOFileFilter READ_ONLY = new AndFileFilter(CAN_READ, CanWriteFileFilter.CANNOT_WRITE);
/* 13:   */   
/* 14:   */   public boolean accept(File file)
/* 15:   */   {
/* 16:90 */     return file.canRead();
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.CanReadFileFilter
 * JD-Core Version:    0.7.0.1
 */