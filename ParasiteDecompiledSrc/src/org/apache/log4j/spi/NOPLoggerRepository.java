/*   1:    */ package org.apache.log4j.spi;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.log4j.Appender;
/*   6:    */ import org.apache.log4j.Category;
/*   7:    */ import org.apache.log4j.Level;
/*   8:    */ import org.apache.log4j.Logger;
/*   9:    */ 
/*  10:    */ public final class NOPLoggerRepository
/*  11:    */   implements LoggerRepository
/*  12:    */ {
/*  13:    */   public void addHierarchyEventListener(HierarchyEventListener listener) {}
/*  14:    */   
/*  15:    */   public boolean isDisabled(int level)
/*  16:    */   {
/*  17: 43 */     return true;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setThreshold(Level level) {}
/*  21:    */   
/*  22:    */   public void setThreshold(String val) {}
/*  23:    */   
/*  24:    */   public void emitNoAppenderWarning(Category cat) {}
/*  25:    */   
/*  26:    */   public Level getThreshold()
/*  27:    */   {
/*  28: 68 */     return Level.OFF;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Logger getLogger(String name)
/*  32:    */   {
/*  33: 75 */     return new NOPLogger(this, name);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Logger getLogger(String name, LoggerFactory factory)
/*  37:    */   {
/*  38: 82 */     return new NOPLogger(this, name);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Logger getRootLogger()
/*  42:    */   {
/*  43: 89 */     return new NOPLogger(this, "root");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Logger exists(String name)
/*  47:    */   {
/*  48: 96 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void shutdown() {}
/*  52:    */   
/*  53:    */   public Enumeration getCurrentLoggers()
/*  54:    */   {
/*  55:109 */     return new Vector().elements();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Enumeration getCurrentCategories()
/*  59:    */   {
/*  60:116 */     return getCurrentLoggers();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void fireAddAppenderEvent(Category logger, Appender appender) {}
/*  64:    */   
/*  65:    */   public void resetConfiguration() {}
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.NOPLoggerRepository
 * JD-Core Version:    0.7.0.1
 */