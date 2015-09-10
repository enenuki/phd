/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum ConnectionReleaseMode
/*  4:   */ {
/*  5:41 */   AFTER_STATEMENT("after_statement"),  AFTER_TRANSACTION("after_transaction"),  ON_CLOSE("on_close");
/*  6:   */   
/*  7:   */   private final String name;
/*  8:   */   
/*  9:   */   private ConnectionReleaseMode(String name)
/* 10:   */   {
/* 11:60 */     this.name = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static ConnectionReleaseMode parse(String name)
/* 15:   */   {
/* 16:63 */     return valueOf(name.toUpperCase());
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ConnectionReleaseMode
 * JD-Core Version:    0.7.0.1
 */