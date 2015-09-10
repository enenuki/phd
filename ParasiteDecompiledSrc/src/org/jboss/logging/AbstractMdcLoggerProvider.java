/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ abstract class AbstractMdcLoggerProvider
/*  7:   */   extends AbstractLoggerProvider
/*  8:   */ {
/*  9:30 */   private final ThreadLocal<Map<String, Object>> mdcMap = new ThreadLocal();
/* 10:   */   
/* 11:   */   public Object getMdc(String key)
/* 12:   */   {
/* 13:33 */     return this.mdcMap.get() == null ? null : ((Map)this.mdcMap.get()).get(key);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Map<String, Object> getMdcMap()
/* 17:   */   {
/* 18:37 */     return (Map)this.mdcMap.get();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object putMdc(String key, Object value)
/* 22:   */   {
/* 23:41 */     Map<String, Object> map = (Map)this.mdcMap.get();
/* 24:42 */     if (map == null)
/* 25:   */     {
/* 26:43 */       map = new HashMap();
/* 27:44 */       this.mdcMap.set(map);
/* 28:   */     }
/* 29:46 */     return map.put(key, value);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void removeMdc(String key)
/* 33:   */   {
/* 34:50 */     Map<String, Object> map = (Map)this.mdcMap.get();
/* 35:51 */     if (map == null) {
/* 36:52 */       return;
/* 37:   */     }
/* 38:53 */     map.remove(key);
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.AbstractMdcLoggerProvider
 * JD-Core Version:    0.7.0.1
 */