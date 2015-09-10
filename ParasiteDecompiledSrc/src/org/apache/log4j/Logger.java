/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggerFactory;
/*   4:    */ import org.apache.log4j.spi.LoggerRepository;
/*   5:    */ 
/*   6:    */ public class Logger
/*   7:    */   extends Category
/*   8:    */ {
/*   9: 35 */   private static final String FQCN = Logger.class.getName();
/*  10:    */   
/*  11:    */   protected Logger(String name)
/*  12:    */   {
/*  13: 40 */     super(name);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static Logger getLogger(String name)
/*  17:    */   {
/*  18:104 */     return LogManager.getLogger(name);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Logger getLogger(Class clazz)
/*  22:    */   {
/*  23:117 */     return LogManager.getLogger(clazz.getName());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Logger getRootLogger()
/*  27:    */   {
/*  28:135 */     return LogManager.getRootLogger();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Logger getLogger(String name, LoggerFactory factory)
/*  32:    */   {
/*  33:155 */     return LogManager.getLogger(name, factory);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void trace(Object message)
/*  37:    */   {
/*  38:166 */     if (this.repository.isDisabled(5000)) {
/*  39:167 */       return;
/*  40:    */     }
/*  41:170 */     if (Level.TRACE.isGreaterOrEqual(getEffectiveLevel())) {
/*  42:171 */       forcedLog(FQCN, Level.TRACE, message, null);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void trace(Object message, Throwable t)
/*  47:    */   {
/*  48:188 */     if (this.repository.isDisabled(5000)) {
/*  49:189 */       return;
/*  50:    */     }
/*  51:192 */     if (Level.TRACE.isGreaterOrEqual(getEffectiveLevel())) {
/*  52:193 */       forcedLog(FQCN, Level.TRACE, message, t);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isTraceEnabled()
/*  57:    */   {
/*  58:205 */     if (this.repository.isDisabled(5000)) {
/*  59:206 */       return false;
/*  60:    */     }
/*  61:209 */     return Level.TRACE.isGreaterOrEqual(getEffectiveLevel());
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Logger
 * JD-Core Version:    0.7.0.1
 */