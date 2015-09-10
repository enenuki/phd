/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.slf4j.LoggerFactory;
/*  5:   */ import org.slf4j.MDC;
/*  6:   */ import org.slf4j.spi.LocationAwareLogger;
/*  7:   */ 
/*  8:   */ final class Slf4jLoggerProvider
/*  9:   */   extends AbstractLoggerProvider
/* 10:   */   implements LoggerProvider
/* 11:   */ {
/* 12:   */   public Logger getLogger(String name)
/* 13:   */   {
/* 14:33 */     org.slf4j.Logger l = LoggerFactory.getLogger(name);
/* 15:   */     try
/* 16:   */     {
/* 17:35 */       return new Slf4jLocationAwareLogger(name, (LocationAwareLogger)l);
/* 18:   */     }
/* 19:   */     catch (Throwable ignored) {}
/* 20:37 */     return new Slf4jLogger(name, l);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object putMdc(String key, Object value)
/* 24:   */   {
/* 25:   */     try
/* 26:   */     {
/* 27:42 */       return MDC.get(key);
/* 28:   */     }
/* 29:   */     finally
/* 30:   */     {
/* 31:44 */       if (value == null) {
/* 32:45 */         MDC.remove(key);
/* 33:   */       } else {
/* 34:47 */         MDC.put(key, String.valueOf(value));
/* 35:   */       }
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Object getMdc(String key)
/* 40:   */   {
/* 41:53 */     return MDC.get(key);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void removeMdc(String key)
/* 45:   */   {
/* 46:57 */     MDC.remove(key);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Map<String, Object> getMdcMap()
/* 50:   */   {
/* 51:62 */     return MDC.getCopyOfContextMap();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Slf4jLoggerProvider
 * JD-Core Version:    0.7.0.1
 */