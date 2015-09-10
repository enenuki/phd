/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ public final class NDC
/*  4:   */ {
/*  5:   */   public static void clear()
/*  6:   */   {
/*  7:31 */     LoggerProviders.PROVIDER.clearNdc();
/*  8:   */   }
/*  9:   */   
/* 10:   */   public static String get()
/* 11:   */   {
/* 12:35 */     return LoggerProviders.PROVIDER.getNdc();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static int getDepth()
/* 16:   */   {
/* 17:39 */     return LoggerProviders.PROVIDER.getNdcDepth();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static String pop()
/* 21:   */   {
/* 22:43 */     return LoggerProviders.PROVIDER.popNdc();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static String peek()
/* 26:   */   {
/* 27:47 */     return LoggerProviders.PROVIDER.peekNdc();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static void push(String message)
/* 31:   */   {
/* 32:51 */     LoggerProviders.PROVIDER.pushNdc(message);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static void setMaxDepth(int maxDepth)
/* 36:   */   {
/* 37:55 */     LoggerProviders.PROVIDER.setNdcMaxDepth(maxDepth);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.NDC
 * JD-Core Version:    0.7.0.1
 */