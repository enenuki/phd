/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.ErrorReporter;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*  6:   */ import org.apache.commons.logging.Log;
/*  7:   */ import org.apache.commons.logging.LogFactory;
/*  8:   */ 
/*  9:   */ public class StrictErrorReporter
/* 10:   */   implements ErrorReporter, Serializable
/* 11:   */ {
/* 12:34 */   private static final Log LOG = LogFactory.getLog(StrictErrorReporter.class);
/* 13:   */   
/* 14:   */   public void warning(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 15:   */   {
/* 16:48 */     LOG.warn(format("warning", message, sourceName, line, lineSource, lineOffset));
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void error(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 20:   */   {
/* 21:62 */     LOG.error(format("error", message, sourceName, line, lineSource, lineOffset));
/* 22:63 */     throw new EvaluatorException(message, sourceName, line, lineSource, lineOffset);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset)
/* 26:   */   {
/* 27:79 */     LOG.error(format("runtimeError", message, sourceName, line, lineSource, lineOffset));
/* 28:80 */     return new EvaluatorException(message, sourceName, line, lineSource, lineOffset);
/* 29:   */   }
/* 30:   */   
/* 31:   */   private String format(String prefix, String message, String sourceName, int line, String lineSource, int lineOffset)
/* 32:   */   {
/* 33:86 */     return prefix + ": message=[" + message + "] sourceName=[" + sourceName + "] line=[" + line + "] lineSource=[" + lineSource + "] lineOffset=[" + lineOffset + "]";
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter
 * JD-Core Version:    0.7.0.1
 */