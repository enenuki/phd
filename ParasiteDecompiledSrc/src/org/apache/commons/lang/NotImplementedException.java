/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import org.apache.commons.lang.exception.Nestable;
/*   6:    */ import org.apache.commons.lang.exception.NestableDelegate;
/*   7:    */ 
/*   8:    */ public class NotImplementedException
/*   9:    */   extends UnsupportedOperationException
/*  10:    */   implements Nestable
/*  11:    */ {
/*  12:    */   private static final String DEFAULT_MESSAGE = "Code is not implemented";
/*  13:    */   private static final long serialVersionUID = -6894122266938754088L;
/*  14: 67 */   private NestableDelegate delegate = new NestableDelegate(this);
/*  15:    */   private Throwable cause;
/*  16:    */   
/*  17:    */   public NotImplementedException()
/*  18:    */   {
/*  19: 82 */     super("Code is not implemented");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public NotImplementedException(String msg)
/*  23:    */   {
/*  24: 92 */     super(msg == null ? "Code is not implemented" : msg);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public NotImplementedException(Throwable cause)
/*  28:    */   {
/*  29:103 */     super("Code is not implemented");
/*  30:104 */     this.cause = cause;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public NotImplementedException(String msg, Throwable cause)
/*  34:    */   {
/*  35:116 */     super(msg == null ? "Code is not implemented" : msg);
/*  36:117 */     this.cause = cause;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public NotImplementedException(Class clazz)
/*  40:    */   {
/*  41:127 */     super("Code is not implemented in " + clazz);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Throwable getCause()
/*  45:    */   {
/*  46:138 */     return this.cause;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getMessage()
/*  50:    */   {
/*  51:148 */     if (super.getMessage() != null) {
/*  52:149 */       return super.getMessage();
/*  53:    */     }
/*  54:150 */     if (this.cause != null) {
/*  55:151 */       return this.cause.toString();
/*  56:    */     }
/*  57:153 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getMessage(int index)
/*  61:    */   {
/*  62:169 */     if (index == 0) {
/*  63:170 */       return super.getMessage();
/*  64:    */     }
/*  65:172 */     return this.delegate.getMessage(index);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String[] getMessages()
/*  69:    */   {
/*  70:184 */     return this.delegate.getMessages();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Throwable getThrowable(int index)
/*  74:    */   {
/*  75:197 */     return this.delegate.getThrowable(index);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getThrowableCount()
/*  79:    */   {
/*  80:208 */     return this.delegate.getThrowableCount();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Throwable[] getThrowables()
/*  84:    */   {
/*  85:220 */     return this.delegate.getThrowables();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int indexOfThrowable(Class type)
/*  89:    */   {
/*  90:233 */     return this.delegate.indexOfThrowable(type, 0);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int indexOfThrowable(Class type, int fromIndex)
/*  94:    */   {
/*  95:249 */     return this.delegate.indexOfThrowable(type, fromIndex);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void printStackTrace()
/*  99:    */   {
/* 100:259 */     this.delegate.printStackTrace();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void printStackTrace(PrintStream out)
/* 104:    */   {
/* 105:270 */     this.delegate.printStackTrace(out);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void printStackTrace(PrintWriter out)
/* 109:    */   {
/* 110:281 */     this.delegate.printStackTrace(out);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final void printPartialStackTrace(PrintWriter out)
/* 114:    */   {
/* 115:292 */     super.printStackTrace(out);
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.NotImplementedException
 * JD-Core Version:    0.7.0.1
 */