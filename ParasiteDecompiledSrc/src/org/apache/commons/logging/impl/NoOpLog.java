/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ 
/*   6:    */ public class NoOpLog
/*   7:    */   implements Log, Serializable
/*   8:    */ {
/*   9:    */   public NoOpLog() {}
/*  10:    */   
/*  11:    */   public NoOpLog(String name) {}
/*  12:    */   
/*  13:    */   public void trace(Object message) {}
/*  14:    */   
/*  15:    */   public void trace(Object message, Throwable t) {}
/*  16:    */   
/*  17:    */   public void debug(Object message) {}
/*  18:    */   
/*  19:    */   public void debug(Object message, Throwable t) {}
/*  20:    */   
/*  21:    */   public void info(Object message) {}
/*  22:    */   
/*  23:    */   public void info(Object message, Throwable t) {}
/*  24:    */   
/*  25:    */   public void warn(Object message) {}
/*  26:    */   
/*  27:    */   public void warn(Object message, Throwable t) {}
/*  28:    */   
/*  29:    */   public void error(Object message) {}
/*  30:    */   
/*  31:    */   public void error(Object message, Throwable t) {}
/*  32:    */   
/*  33:    */   public void fatal(Object message) {}
/*  34:    */   
/*  35:    */   public void fatal(Object message, Throwable t) {}
/*  36:    */   
/*  37:    */   public final boolean isDebugEnabled()
/*  38:    */   {
/*  39: 70 */     return false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final boolean isErrorEnabled()
/*  43:    */   {
/*  44: 77 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final boolean isFatalEnabled()
/*  48:    */   {
/*  49: 84 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final boolean isInfoEnabled()
/*  53:    */   {
/*  54: 91 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final boolean isTraceEnabled()
/*  58:    */   {
/*  59: 98 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final boolean isWarnEnabled()
/*  63:    */   {
/*  64:105 */     return false;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.NoOpLog
 * JD-Core Version:    0.7.0.1
 */