/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.IOException;
/*  5:   */ 
/*  6:   */ public class TemporaryFolder
/*  7:   */   extends ExternalResource
/*  8:   */ {
/*  9:   */   private File folder;
/* 10:   */   
/* 11:   */   protected void before()
/* 12:   */     throws Throwable
/* 13:   */   {
/* 14:32 */     create();
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void after()
/* 18:   */   {
/* 19:37 */     delete();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void create()
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:45 */     this.folder = File.createTempFile("junit", "");
/* 26:46 */     this.folder.delete();
/* 27:47 */     this.folder.mkdir();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public File newFile(String fileName)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:54 */     File file = new File(this.folder, fileName);
/* 34:55 */     file.createNewFile();
/* 35:56 */     return file;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public File newFolder(String folderName)
/* 39:   */   {
/* 40:63 */     File file = new File(this.folder, folderName);
/* 41:64 */     file.mkdir();
/* 42:65 */     return file;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public File getRoot()
/* 46:   */   {
/* 47:72 */     return this.folder;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void delete()
/* 51:   */   {
/* 52:81 */     recursiveDelete(this.folder);
/* 53:   */   }
/* 54:   */   
/* 55:   */   private void recursiveDelete(File file)
/* 56:   */   {
/* 57:85 */     File[] files = file.listFiles();
/* 58:86 */     if (files != null) {
/* 59:87 */       for (File each : files) {
/* 60:88 */         recursiveDelete(each);
/* 61:   */       }
/* 62:   */     }
/* 63:89 */     file.delete();
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.TemporaryFolder
 * JD-Core Version:    0.7.0.1
 */