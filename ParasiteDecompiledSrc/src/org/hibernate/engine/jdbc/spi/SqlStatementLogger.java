/*   1:    */ package org.hibernate.engine.jdbc.spi;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*   5:    */ import org.hibernate.engine.jdbc.internal.Formatter;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ 
/*   9:    */ public class SqlStatementLogger
/*  10:    */ {
/*  11: 39 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, "org.hibernate.SQL");
/*  12:    */   private boolean logToStdout;
/*  13:    */   private boolean format;
/*  14:    */   
/*  15:    */   public SqlStatementLogger()
/*  16:    */   {
/*  17: 48 */     this(false, false);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public SqlStatementLogger(boolean logToStdout, boolean format)
/*  21:    */   {
/*  22: 58 */     this.logToStdout = logToStdout;
/*  23: 59 */     this.format = format;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isLogToStdout()
/*  27:    */   {
/*  28: 68 */     return this.logToStdout;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setLogToStdout(boolean logToStdout)
/*  32:    */   {
/*  33: 77 */     this.logToStdout = logToStdout;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isFormat()
/*  37:    */   {
/*  38: 81 */     return this.format;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setFormat(boolean format)
/*  42:    */   {
/*  43: 85 */     this.format = format;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void logStatement(String statement)
/*  47:    */   {
/*  48: 95 */     logStatement(statement, FormatStyle.BASIC.getFormatter());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void logStatement(String statement, Formatter formatter)
/*  52:    */   {
/*  53: 99 */     if ((this.format) && (
/*  54:100 */       (this.logToStdout) || (LOG.isDebugEnabled()))) {
/*  55:101 */       statement = formatter.format(statement);
/*  56:    */     }
/*  57:104 */     LOG.debug(statement);
/*  58:105 */     if (this.logToStdout) {
/*  59:106 */       System.out.println("Hibernate: " + statement);
/*  60:    */     }
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.SqlStatementLogger
 * JD-Core Version:    0.7.0.1
 */