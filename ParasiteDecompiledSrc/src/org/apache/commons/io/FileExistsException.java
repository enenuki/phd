/*  1:   */ package org.apache.commons.io;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.IOException;
/*  5:   */ 
/*  6:   */ public class FileExistsException
/*  7:   */   extends IOException
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   
/* 11:   */   public FileExistsException() {}
/* 12:   */   
/* 13:   */   public FileExistsException(String message)
/* 14:   */   {
/* 15:48 */     super(message);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public FileExistsException(File file)
/* 19:   */   {
/* 20:57 */     super("File " + file + " exists");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FileExistsException
 * JD-Core Version:    0.7.0.1
 */