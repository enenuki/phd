/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import org.apache.log4j.lf5.LogLevel;
/*   6:    */ import org.apache.log4j.lf5.LogRecord;
/*   7:    */ 
/*   8:    */ public class AdapterLogRecord
/*   9:    */   extends LogRecord
/*  10:    */ {
/*  11: 45 */   private static LogLevel severeLevel = null;
/*  12: 47 */   private static StringWriter sw = new StringWriter();
/*  13: 48 */   private static PrintWriter pw = new PrintWriter(sw);
/*  14:    */   
/*  15:    */   public void setCategory(String category)
/*  16:    */   {
/*  17: 61 */     super.setCategory(category);
/*  18: 62 */     super.setLocation(getLocationInfo(category));
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean isSevereLevel()
/*  22:    */   {
/*  23: 66 */     if (severeLevel == null) {
/*  24: 66 */       return false;
/*  25:    */     }
/*  26: 67 */     return severeLevel.equals(getLevel());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void setSevereLevel(LogLevel level)
/*  30:    */   {
/*  31: 71 */     severeLevel = level;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static LogLevel getSevereLevel()
/*  35:    */   {
/*  36: 75 */     return severeLevel;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected String getLocationInfo(String category)
/*  40:    */   {
/*  41: 82 */     String stackTrace = stackTraceToString(new Throwable());
/*  42: 83 */     String line = parseLine(stackTrace, category);
/*  43: 84 */     return line;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected String stackTraceToString(Throwable t)
/*  47:    */   {
/*  48: 88 */     String s = null;
/*  49: 90 */     synchronized (sw)
/*  50:    */     {
/*  51: 91 */       t.printStackTrace(pw);
/*  52: 92 */       s = sw.toString();
/*  53: 93 */       sw.getBuffer().setLength(0);
/*  54:    */     }
/*  55: 96 */     return s;
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected String parseLine(String trace, String category)
/*  59:    */   {
/*  60:100 */     int index = trace.indexOf(category);
/*  61:101 */     if (index == -1) {
/*  62:101 */       return null;
/*  63:    */     }
/*  64:102 */     trace = trace.substring(index);
/*  65:103 */     trace = trace.substring(0, trace.indexOf(")") + 1);
/*  66:104 */     return trace;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.AdapterLogRecord
 * JD-Core Version:    0.7.0.1
 */