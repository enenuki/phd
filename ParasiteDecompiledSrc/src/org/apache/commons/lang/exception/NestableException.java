/*   1:    */ package org.apache.commons.lang.exception;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ 
/*   6:    */ public class NestableException
/*   7:    */   extends Exception
/*   8:    */   implements Nestable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11:103 */   protected NestableDelegate delegate = new NestableDelegate(this);
/*  12:109 */   private Throwable cause = null;
/*  13:    */   
/*  14:    */   public NestableException() {}
/*  15:    */   
/*  16:    */   public NestableException(String msg)
/*  17:    */   {
/*  18:126 */     super(msg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public NestableException(Throwable cause)
/*  22:    */   {
/*  23:138 */     this.cause = cause;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public NestableException(String msg, Throwable cause)
/*  27:    */   {
/*  28:150 */     super(msg);
/*  29:151 */     this.cause = cause;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Throwable getCause()
/*  33:    */   {
/*  34:158 */     return this.cause;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMessage()
/*  38:    */   {
/*  39:169 */     if (super.getMessage() != null) {
/*  40:170 */       return super.getMessage();
/*  41:    */     }
/*  42:171 */     if (this.cause != null) {
/*  43:172 */       return this.cause.toString();
/*  44:    */     }
/*  45:174 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getMessage(int index)
/*  49:    */   {
/*  50:182 */     if (index == 0) {
/*  51:183 */       return super.getMessage();
/*  52:    */     }
/*  53:185 */     return this.delegate.getMessage(index);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String[] getMessages()
/*  57:    */   {
/*  58:192 */     return this.delegate.getMessages();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Throwable getThrowable(int index)
/*  62:    */   {
/*  63:199 */     return this.delegate.getThrowable(index);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getThrowableCount()
/*  67:    */   {
/*  68:206 */     return this.delegate.getThrowableCount();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Throwable[] getThrowables()
/*  72:    */   {
/*  73:213 */     return this.delegate.getThrowables();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int indexOfThrowable(Class type)
/*  77:    */   {
/*  78:220 */     return this.delegate.indexOfThrowable(type, 0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int indexOfThrowable(Class type, int fromIndex)
/*  82:    */   {
/*  83:227 */     return this.delegate.indexOfThrowable(type, fromIndex);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void printStackTrace()
/*  87:    */   {
/*  88:234 */     this.delegate.printStackTrace();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void printStackTrace(PrintStream out)
/*  92:    */   {
/*  93:241 */     this.delegate.printStackTrace(out);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void printStackTrace(PrintWriter out)
/*  97:    */   {
/*  98:248 */     this.delegate.printStackTrace(out);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final void printPartialStackTrace(PrintWriter out)
/* 102:    */   {
/* 103:255 */     super.printStackTrace(out);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.NestableException
 * JD-Core Version:    0.7.0.1
 */