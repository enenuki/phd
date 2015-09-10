/*   1:    */ package org.apache.commons.lang.exception;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ 
/*   6:    */ public class NestableRuntimeException
/*   7:    */   extends RuntimeException
/*   8:    */   implements Nestable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11: 47 */   protected NestableDelegate delegate = new NestableDelegate(this);
/*  12: 53 */   private Throwable cause = null;
/*  13:    */   
/*  14:    */   public NestableRuntimeException() {}
/*  15:    */   
/*  16:    */   public NestableRuntimeException(String msg)
/*  17:    */   {
/*  18: 70 */     super(msg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public NestableRuntimeException(Throwable cause)
/*  22:    */   {
/*  23: 82 */     this.cause = cause;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public NestableRuntimeException(String msg, Throwable cause)
/*  27:    */   {
/*  28: 94 */     super(msg);
/*  29: 95 */     this.cause = cause;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Throwable getCause()
/*  33:    */   {
/*  34:102 */     return this.cause;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMessage()
/*  38:    */   {
/*  39:113 */     if (super.getMessage() != null) {
/*  40:114 */       return super.getMessage();
/*  41:    */     }
/*  42:115 */     if (this.cause != null) {
/*  43:116 */       return this.cause.toString();
/*  44:    */     }
/*  45:118 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getMessage(int index)
/*  49:    */   {
/*  50:126 */     if (index == 0) {
/*  51:127 */       return super.getMessage();
/*  52:    */     }
/*  53:129 */     return this.delegate.getMessage(index);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String[] getMessages()
/*  57:    */   {
/*  58:136 */     return this.delegate.getMessages();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Throwable getThrowable(int index)
/*  62:    */   {
/*  63:143 */     return this.delegate.getThrowable(index);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getThrowableCount()
/*  67:    */   {
/*  68:150 */     return this.delegate.getThrowableCount();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Throwable[] getThrowables()
/*  72:    */   {
/*  73:157 */     return this.delegate.getThrowables();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int indexOfThrowable(Class type)
/*  77:    */   {
/*  78:164 */     return this.delegate.indexOfThrowable(type, 0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int indexOfThrowable(Class type, int fromIndex)
/*  82:    */   {
/*  83:171 */     return this.delegate.indexOfThrowable(type, fromIndex);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void printStackTrace()
/*  87:    */   {
/*  88:178 */     this.delegate.printStackTrace();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void printStackTrace(PrintStream out)
/*  92:    */   {
/*  93:185 */     this.delegate.printStackTrace(out);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void printStackTrace(PrintWriter out)
/*  97:    */   {
/*  98:192 */     this.delegate.printStackTrace(out);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final void printPartialStackTrace(PrintWriter out)
/* 102:    */   {
/* 103:199 */     super.printStackTrace(out);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.NestableRuntimeException
 * JD-Core Version:    0.7.0.1
 */