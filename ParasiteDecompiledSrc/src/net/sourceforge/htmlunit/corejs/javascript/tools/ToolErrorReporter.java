/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.text.MessageFormat;
/*   5:    */ import java.util.Locale;
/*   6:    */ import java.util.MissingResourceException;
/*   7:    */ import java.util.ResourceBundle;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.ErrorReporter;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.SecurityUtilities;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.WrappedException;
/*  16:    */ 
/*  17:    */ public class ToolErrorReporter
/*  18:    */   implements ErrorReporter
/*  19:    */ {
/*  20:    */   private static final String messagePrefix = "js: ";
/*  21:    */   private boolean hasReportedErrorFlag;
/*  22:    */   private boolean reportWarnings;
/*  23:    */   private PrintStream err;
/*  24:    */   
/*  25:    */   public ToolErrorReporter(boolean reportWarnings)
/*  26:    */   {
/*  27: 56 */     this(reportWarnings, System.err);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ToolErrorReporter(boolean reportWarnings, PrintStream err)
/*  31:    */   {
/*  32: 60 */     this.reportWarnings = reportWarnings;
/*  33: 61 */     this.err = err;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static String getMessage(String messageId)
/*  37:    */   {
/*  38: 70 */     return getMessage(messageId, (Object[])null);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static String getMessage(String messageId, String argument)
/*  42:    */   {
/*  43: 74 */     Object[] args = { argument };
/*  44: 75 */     return getMessage(messageId, args);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String getMessage(String messageId, Object arg1, Object arg2)
/*  48:    */   {
/*  49: 80 */     Object[] args = { arg1, arg2 };
/*  50: 81 */     return getMessage(messageId, args);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String getMessage(String messageId, Object[] args)
/*  54:    */   {
/*  55: 85 */     Context cx = Context.getCurrentContext();
/*  56: 86 */     Locale locale = cx == null ? Locale.getDefault() : cx.getLocale();
/*  57:    */     
/*  58:    */ 
/*  59: 89 */     ResourceBundle rb = ResourceBundle.getBundle("net.sourceforge.htmlunit.corejs.javascript.tools.resources.Messages", locale);
/*  60:    */     String formatString;
/*  61:    */     try
/*  62:    */     {
/*  63: 94 */       formatString = rb.getString(messageId);
/*  64:    */     }
/*  65:    */     catch (MissingResourceException mre)
/*  66:    */     {
/*  67: 96 */       throw new RuntimeException("no message resource found for message property " + messageId);
/*  68:    */     }
/*  69:100 */     if (args == null) {
/*  70:101 */       return formatString;
/*  71:    */     }
/*  72:103 */     MessageFormat formatter = new MessageFormat(formatString);
/*  73:104 */     return formatter.format(args);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static String getExceptionMessage(RhinoException ex)
/*  77:    */   {
/*  78:    */     String msg;
/*  79:    */     String msg;
/*  80:111 */     if ((ex instanceof JavaScriptException))
/*  81:    */     {
/*  82:112 */       msg = getMessage("msg.uncaughtJSException", ex.details());
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:    */       String msg;
/*  87:113 */       if ((ex instanceof EcmaError))
/*  88:    */       {
/*  89:114 */         msg = getMessage("msg.uncaughtEcmaError", ex.details());
/*  90:    */       }
/*  91:    */       else
/*  92:    */       {
/*  93:    */         String msg;
/*  94:115 */         if ((ex instanceof EvaluatorException)) {
/*  95:116 */           msg = ex.details();
/*  96:    */         } else {
/*  97:118 */           msg = ex.toString();
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:120 */     return msg;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void warning(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 105:    */   {
/* 106:126 */     if (!this.reportWarnings) {
/* 107:127 */       return;
/* 108:    */     }
/* 109:128 */     reportErrorMessage(message, sourceName, line, lineSource, lineOffset, true);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void error(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 113:    */   {
/* 114:135 */     this.hasReportedErrorFlag = true;
/* 115:136 */     reportErrorMessage(message, sourceName, line, lineSource, lineOffset, false);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 119:    */   {
/* 120:144 */     return new EvaluatorException(message, sourceName, line, lineSource, lineOffset);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean hasReportedError()
/* 124:    */   {
/* 125:149 */     return this.hasReportedErrorFlag;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isReportingWarnings()
/* 129:    */   {
/* 130:153 */     return this.reportWarnings;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIsReportingWarnings(boolean reportWarnings)
/* 134:    */   {
/* 135:157 */     this.reportWarnings = reportWarnings;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void reportException(ErrorReporter er, RhinoException ex)
/* 139:    */   {
/* 140:162 */     if ((er instanceof ToolErrorReporter))
/* 141:    */     {
/* 142:163 */       ((ToolErrorReporter)er).reportException(ex);
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:165 */       String msg = getExceptionMessage(ex);
/* 147:166 */       er.error(msg, ex.sourceName(), ex.lineNumber(), ex.lineSource(), ex.columnNumber());
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void reportException(RhinoException ex)
/* 152:    */   {
/* 153:173 */     if ((ex instanceof WrappedException))
/* 154:    */     {
/* 155:174 */       WrappedException we = (WrappedException)ex;
/* 156:175 */       we.printStackTrace(this.err);
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160:177 */       String lineSeparator = SecurityUtilities.getSystemProperty("line.separator");
/* 161:    */       
/* 162:179 */       String msg = getExceptionMessage(ex) + lineSeparator + ex.getScriptStackTrace();
/* 163:    */       
/* 164:181 */       reportErrorMessage(msg, ex.sourceName(), ex.lineNumber(), ex.lineSource(), ex.columnNumber(), false);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void reportErrorMessage(String message, String sourceName, int line, String lineSource, int lineOffset, boolean justWarning)
/* 169:    */   {
/* 170:190 */     if (line > 0)
/* 171:    */     {
/* 172:191 */       String lineStr = String.valueOf(line);
/* 173:192 */       if (sourceName != null)
/* 174:    */       {
/* 175:193 */         Object[] args = { sourceName, lineStr, message };
/* 176:194 */         message = getMessage("msg.format3", args);
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:196 */         Object[] args = { lineStr, message };
/* 181:197 */         message = getMessage("msg.format2", args);
/* 182:    */       }
/* 183:    */     }
/* 184:    */     else
/* 185:    */     {
/* 186:200 */       Object[] args = { message };
/* 187:201 */       message = getMessage("msg.format1", args);
/* 188:    */     }
/* 189:203 */     if (justWarning) {
/* 190:204 */       message = getMessage("msg.warning", message);
/* 191:    */     }
/* 192:206 */     this.err.println("js: " + message);
/* 193:207 */     if (null != lineSource)
/* 194:    */     {
/* 195:208 */       this.err.println("js: " + lineSource);
/* 196:209 */       this.err.println("js: " + buildIndicator(lineOffset));
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   private String buildIndicator(int offset)
/* 201:    */   {
/* 202:214 */     StringBuffer sb = new StringBuffer();
/* 203:215 */     for (int i = 0; i < offset - 1; i++) {
/* 204:216 */       sb.append(".");
/* 205:    */     }
/* 206:217 */     sb.append("^");
/* 207:218 */     return sb.toString();
/* 208:    */   }
/* 209:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.ToolErrorReporter
 * JD-Core Version:    0.7.0.1
 */