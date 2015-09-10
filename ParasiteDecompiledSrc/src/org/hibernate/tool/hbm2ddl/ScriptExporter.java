/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ class ScriptExporter
/*  6:   */   implements Exporter
/*  7:   */ {
/*  8:   */   public boolean acceptsImportScripts()
/*  9:   */   {
/* 10:32 */     return false;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void export(String string)
/* 14:   */     throws Exception
/* 15:   */   {
/* 16:37 */     System.out.println(string);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void release()
/* 20:   */     throws Exception
/* 21:   */   {}
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ScriptExporter
 * JD-Core Version:    0.7.0.1
 */