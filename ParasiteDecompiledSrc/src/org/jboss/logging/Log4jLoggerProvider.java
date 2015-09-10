/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.apache.log4j.MDC;
/*  5:   */ import org.apache.log4j.NDC;
/*  6:   */ 
/*  7:   */ final class Log4jLoggerProvider
/*  8:   */   implements LoggerProvider
/*  9:   */ {
/* 10:   */   public Logger getLogger(String name)
/* 11:   */   {
/* 12:33 */     return new Log4jLogger(name);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Object getMdc(String key)
/* 16:   */   {
/* 17:37 */     return MDC.get(key);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Map<String, Object> getMdcMap()
/* 21:   */   {
/* 22:42 */     return MDC.getContext();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object putMdc(String key, Object val)
/* 26:   */   {
/* 27:   */     try
/* 28:   */     {
/* 29:47 */       return MDC.get(key);
/* 30:   */     }
/* 31:   */     finally
/* 32:   */     {
/* 33:49 */       MDC.put(key, val);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void removeMdc(String key)
/* 38:   */   {
/* 39:54 */     MDC.remove(key);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void clearNdc() {}
/* 43:   */   
/* 44:   */   public String getNdc()
/* 45:   */   {
/* 46:62 */     return NDC.get();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getNdcDepth()
/* 50:   */   {
/* 51:66 */     return NDC.getDepth();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String peekNdc()
/* 55:   */   {
/* 56:70 */     return NDC.peek();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String popNdc()
/* 60:   */   {
/* 61:74 */     return NDC.pop();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void pushNdc(String message)
/* 65:   */   {
/* 66:78 */     NDC.push(message);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setNdcMaxDepth(int maxDepth)
/* 70:   */   {
/* 71:82 */     NDC.setMaxDepth(maxDepth);
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Log4jLoggerProvider
 * JD-Core Version:    0.7.0.1
 */