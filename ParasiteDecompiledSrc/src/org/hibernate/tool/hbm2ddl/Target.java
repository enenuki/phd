/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ public enum Target
/*  4:   */ {
/*  5:30 */   EXPORT,  SCRIPT,  NONE,  BOTH;
/*  6:   */   
/*  7:   */   private Target() {}
/*  8:   */   
/*  9:   */   public boolean doExport()
/* 10:   */   {
/* 11:36 */     return (this == BOTH) || (this == EXPORT);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public boolean doScript()
/* 15:   */   {
/* 16:40 */     return (this == BOTH) || (this == SCRIPT);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static Target interpret(boolean script, boolean export)
/* 20:   */   {
/* 21:44 */     if (script) {
/* 22:45 */       return export ? BOTH : SCRIPT;
/* 23:   */     }
/* 24:48 */     return export ? EXPORT : NONE;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.Target
 * JD-Core Version:    0.7.0.1
 */