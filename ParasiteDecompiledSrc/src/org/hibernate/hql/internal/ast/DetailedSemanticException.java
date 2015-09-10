/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.io.PrintWriter;
/*  6:   */ 
/*  7:   */ public class DetailedSemanticException
/*  8:   */   extends SemanticException
/*  9:   */ {
/* 10:   */   private Throwable cause;
/* 11:38 */   private boolean showCauseMessage = true;
/* 12:   */   
/* 13:   */   public DetailedSemanticException(String message)
/* 14:   */   {
/* 15:41 */     super(message);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public DetailedSemanticException(String s, Throwable e)
/* 19:   */   {
/* 20:45 */     super(s);
/* 21:46 */     this.cause = e;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toString()
/* 25:   */   {
/* 26:55 */     if ((this.cause == null) || (!this.showCauseMessage)) {
/* 27:56 */       return super.toString();
/* 28:   */     }
/* 29:59 */     return super.toString() + "\n[cause=" + this.cause.toString() + "]";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void printStackTrace()
/* 33:   */   {
/* 34:67 */     super.printStackTrace();
/* 35:68 */     if (this.cause != null) {
/* 36:69 */       this.cause.printStackTrace();
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void printStackTrace(PrintStream s)
/* 41:   */   {
/* 42:79 */     super.printStackTrace(s);
/* 43:80 */     if (this.cause != null)
/* 44:   */     {
/* 45:81 */       s.println("Cause:");
/* 46:82 */       this.cause.printStackTrace(s);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void printStackTrace(PrintWriter w)
/* 51:   */   {
/* 52:92 */     super.printStackTrace(w);
/* 53:93 */     if (this.cause != null)
/* 54:   */     {
/* 55:94 */       w.println("Cause:");
/* 56:95 */       this.cause.printStackTrace(w);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.DetailedSemanticException
 * JD-Core Version:    0.7.0.1
 */