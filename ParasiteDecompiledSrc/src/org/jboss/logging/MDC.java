/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ 
/*  5:   */ public final class MDC
/*  6:   */ {
/*  7:   */   public static Object put(String key, Object val)
/*  8:   */   {
/*  9:33 */     return LoggerProviders.PROVIDER.putMdc(key, val);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static Object get(String key)
/* 13:   */   {
/* 14:37 */     return LoggerProviders.PROVIDER.getMdc(key);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static void remove(String key)
/* 18:   */   {
/* 19:41 */     LoggerProviders.PROVIDER.removeMdc(key);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static Map<String, Object> getMap()
/* 23:   */   {
/* 24:45 */     return LoggerProviders.PROVIDER.getMdcMap();
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.MDC
 * JD-Core Version:    0.7.0.1
 */