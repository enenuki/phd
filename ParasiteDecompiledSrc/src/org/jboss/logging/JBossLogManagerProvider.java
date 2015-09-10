/*   1:    */ package org.jboss.logging;
/*   2:    */ 
/*   3:    */ import java.security.AccessController;
/*   4:    */ import java.security.PrivilegedAction;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.jboss.logmanager.LogContext;
/*   7:    */ import org.jboss.logmanager.Logger.AttachmentKey;
/*   8:    */ import org.jboss.logmanager.MDC;
/*   9:    */ import org.jboss.logmanager.NDC;
/*  10:    */ 
/*  11:    */ final class JBossLogManagerProvider
/*  12:    */   implements LoggerProvider
/*  13:    */ {
/*  14: 36 */   private static final Logger.AttachmentKey<Logger> KEY = new Logger.AttachmentKey();
/*  15:    */   
/*  16:    */   public Logger getLogger(final String name)
/*  17:    */   {
/*  18: 39 */     SecurityManager sm = System.getSecurityManager();
/*  19: 40 */     if (sm != null) {
/*  20: 41 */       (Logger)AccessController.doPrivileged(new PrivilegedAction()
/*  21:    */       {
/*  22:    */         public Logger run()
/*  23:    */         {
/*  24: 43 */           return JBossLogManagerProvider.doGetLogger(name);
/*  25:    */         }
/*  26:    */       });
/*  27:    */     }
/*  28: 47 */     return doGetLogger(name);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private static Logger doGetLogger(String name)
/*  32:    */   {
/*  33: 52 */     Logger l = (Logger)LogContext.getLogContext().getAttachment(name, KEY);
/*  34: 53 */     if (l != null) {
/*  35: 54 */       return l;
/*  36:    */     }
/*  37: 56 */     org.jboss.logmanager.Logger logger = org.jboss.logmanager.Logger.getLogger(name);
/*  38: 57 */     l = new JBossLogManagerLogger(name, logger);
/*  39: 58 */     Logger a = (Logger)logger.attachIfAbsent(KEY, l);
/*  40: 59 */     if (a == null) {
/*  41: 60 */       return l;
/*  42:    */     }
/*  43: 62 */     return a;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object putMdc(String key, Object value)
/*  47:    */   {
/*  48: 67 */     return MDC.put(key, String.valueOf(value));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object getMdc(String key)
/*  52:    */   {
/*  53: 71 */     return MDC.get(key);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void removeMdc(String key)
/*  57:    */   {
/*  58: 75 */     MDC.remove(key);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Map<String, Object> getMdcMap()
/*  62:    */   {
/*  63: 81 */     return MDC.copy();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void clearNdc() {}
/*  67:    */   
/*  68:    */   public String getNdc()
/*  69:    */   {
/*  70: 89 */     return NDC.get();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getNdcDepth()
/*  74:    */   {
/*  75: 93 */     return NDC.getDepth();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String popNdc()
/*  79:    */   {
/*  80: 97 */     return NDC.pop();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String peekNdc()
/*  84:    */   {
/*  85:101 */     return NDC.get();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void pushNdc(String message)
/*  89:    */   {
/*  90:105 */     NDC.push(message);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setNdcMaxDepth(int maxDepth)
/*  94:    */   {
/*  95:109 */     NDC.trimTo(maxDepth);
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.JBossLogManagerProvider
 * JD-Core Version:    0.7.0.1
 */