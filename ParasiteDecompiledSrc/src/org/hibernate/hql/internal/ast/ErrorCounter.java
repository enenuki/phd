/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.RecognitionException;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.List;
/*  7:   */ import org.hibernate.QueryException;
/*  8:   */ import org.hibernate.internal.CoreMessageLogger;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public class ErrorCounter
/* 12:   */   implements ParseErrorHandler
/* 13:   */ {
/* 14:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ErrorCounter.class.getName());
/* 15:43 */   private List errorList = new ArrayList();
/* 16:44 */   private List warningList = new ArrayList();
/* 17:45 */   private List recognitionExceptions = new ArrayList();
/* 18:   */   
/* 19:   */   public void reportError(RecognitionException e)
/* 20:   */   {
/* 21:48 */     reportError(e.toString());
/* 22:49 */     this.recognitionExceptions.add(e);
/* 23:50 */     LOG.error(e.toString(), e);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void reportError(String message)
/* 27:   */   {
/* 28:54 */     LOG.error(message);
/* 29:55 */     this.errorList.add(message);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getErrorCount()
/* 33:   */   {
/* 34:59 */     return this.errorList.size();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void reportWarning(String message)
/* 38:   */   {
/* 39:63 */     LOG.debug(message);
/* 40:64 */     this.warningList.add(message);
/* 41:   */   }
/* 42:   */   
/* 43:   */   private String getErrorString()
/* 44:   */   {
/* 45:68 */     StringBuffer buf = new StringBuffer();
/* 46:69 */     for (Iterator iterator = this.errorList.iterator(); iterator.hasNext();)
/* 47:   */     {
/* 48:70 */       buf.append((String)iterator.next());
/* 49:71 */       if (iterator.hasNext()) {
/* 50:71 */         buf.append("\n");
/* 51:   */       }
/* 52:   */     }
/* 53:74 */     return buf.toString();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void throwQueryException()
/* 57:   */     throws QueryException
/* 58:   */   {
/* 59:78 */     if (getErrorCount() > 0)
/* 60:   */     {
/* 61:79 */       if (this.recognitionExceptions.size() > 0) {
/* 62:79 */         throw QuerySyntaxException.convert((RecognitionException)this.recognitionExceptions.get(0));
/* 63:   */       }
/* 64:80 */       throw new QueryException(getErrorString());
/* 65:   */     }
/* 66:82 */     LOG.debug("throwQueryException() : no errors");
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.ErrorCounter
 * JD-Core Version:    0.7.0.1
 */