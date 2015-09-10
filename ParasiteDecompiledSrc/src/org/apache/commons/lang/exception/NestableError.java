/*   1:    */ package org.apache.commons.lang.exception;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ 
/*   6:    */ public class NestableError
/*   7:    */   extends Error
/*   8:    */   implements Nestable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11: 43 */   protected NestableDelegate delegate = new NestableDelegate(this);
/*  12: 49 */   private Throwable cause = null;
/*  13:    */   
/*  14:    */   public NestableError() {}
/*  15:    */   
/*  16:    */   public NestableError(String msg)
/*  17:    */   {
/*  18: 66 */     super(msg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public NestableError(Throwable cause)
/*  22:    */   {
/*  23: 78 */     this.cause = cause;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public NestableError(String msg, Throwable cause)
/*  27:    */   {
/*  28: 90 */     super(msg);
/*  29: 91 */     this.cause = cause;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Throwable getCause()
/*  33:    */   {
/*  34: 98 */     return this.cause;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMessage()
/*  38:    */   {
/*  39:109 */     if (super.getMessage() != null) {
/*  40:110 */       return super.getMessage();
/*  41:    */     }
/*  42:111 */     if (this.cause != null) {
/*  43:112 */       return this.cause.toString();
/*  44:    */     }
/*  45:114 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getMessage(int index)
/*  49:    */   {
/*  50:122 */     if (index == 0) {
/*  51:123 */       return super.getMessage();
/*  52:    */     }
/*  53:125 */     return this.delegate.getMessage(index);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String[] getMessages()
/*  57:    */   {
/*  58:132 */     return this.delegate.getMessages();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Throwable getThrowable(int index)
/*  62:    */   {
/*  63:139 */     return this.delegate.getThrowable(index);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getThrowableCount()
/*  67:    */   {
/*  68:146 */     return this.delegate.getThrowableCount();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Throwable[] getThrowables()
/*  72:    */   {
/*  73:153 */     return this.delegate.getThrowables();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int indexOfThrowable(Class type)
/*  77:    */   {
/*  78:160 */     return this.delegate.indexOfThrowable(type, 0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int indexOfThrowable(Class type, int fromIndex)
/*  82:    */   {
/*  83:167 */     return this.delegate.indexOfThrowable(type, fromIndex);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void printStackTrace()
/*  87:    */   {
/*  88:174 */     this.delegate.printStackTrace();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void printStackTrace(PrintStream out)
/*  92:    */   {
/*  93:181 */     this.delegate.printStackTrace(out);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void printStackTrace(PrintWriter out)
/*  97:    */   {
/*  98:188 */     this.delegate.printStackTrace(out);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final void printPartialStackTrace(PrintWriter out)
/* 102:    */   {
/* 103:195 */     super.printStackTrace(out);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.NestableError
 * JD-Core Version:    0.7.0.1
 */