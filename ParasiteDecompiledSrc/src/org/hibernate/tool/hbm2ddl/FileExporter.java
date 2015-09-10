/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.io.FileWriter;
/*  4:   */ import java.io.IOException;
/*  5:   */ 
/*  6:   */ class FileExporter
/*  7:   */   implements Exporter
/*  8:   */ {
/*  9:   */   private final FileWriter writer;
/* 10:   */   
/* 11:   */   public FileExporter(String outputFile)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:36 */     this.writer = new FileWriter(outputFile);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean acceptsImportScripts()
/* 18:   */   {
/* 19:41 */     return false;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void export(String string)
/* 23:   */     throws Exception
/* 24:   */   {
/* 25:46 */     this.writer.write(string + '\n');
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void release()
/* 29:   */     throws Exception
/* 30:   */   {
/* 31:51 */     this.writer.flush();
/* 32:52 */     this.writer.close();
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.FileExporter
 * JD-Core Version:    0.7.0.1
 */