/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ 
/*   6:    */ public class FunctorException
/*   7:    */   extends RuntimeException
/*   8:    */ {
/*   9:    */   private static final boolean JDK_SUPPORTS_NESTED;
/*  10:    */   private final Throwable rootCause;
/*  11:    */   
/*  12:    */   static
/*  13:    */   {
/*  14: 39 */     boolean flag = false;
/*  15:    */     try
/*  16:    */     {
/*  17: 41 */       Throwable.class.getDeclaredMethod("getCause", new Class[0]);
/*  18: 42 */       flag = true;
/*  19:    */     }
/*  20:    */     catch (NoSuchMethodException ex)
/*  21:    */     {
/*  22: 44 */       flag = false;
/*  23:    */     }
/*  24: 46 */     JDK_SUPPORTS_NESTED = flag;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public FunctorException()
/*  28:    */   {
/*  29: 60 */     this.rootCause = null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FunctorException(String msg)
/*  33:    */   {
/*  34: 70 */     super(msg);
/*  35: 71 */     this.rootCause = null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public FunctorException(Throwable rootCause)
/*  39:    */   {
/*  40: 82 */     super(rootCause == null ? null : rootCause.getMessage());
/*  41: 83 */     this.rootCause = rootCause;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public FunctorException(String msg, Throwable rootCause)
/*  45:    */   {
/*  46: 95 */     super(msg);
/*  47: 96 */     this.rootCause = rootCause;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Throwable getCause()
/*  51:    */   {
/*  52:105 */     return this.rootCause;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void printStackTrace()
/*  56:    */   {
/*  57:112 */     printStackTrace(System.err);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void printStackTrace(PrintStream out)
/*  61:    */   {
/*  62:121 */     synchronized (out)
/*  63:    */     {
/*  64:122 */       PrintWriter pw = new PrintWriter(out, false);
/*  65:123 */       printStackTrace(pw);
/*  66:    */       
/*  67:125 */       pw.flush();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void printStackTrace(PrintWriter out)
/*  72:    */   {
/*  73:135 */     synchronized (out)
/*  74:    */     {
/*  75:136 */       super.printStackTrace(out);
/*  76:137 */       if ((this.rootCause != null) && (!JDK_SUPPORTS_NESTED))
/*  77:    */       {
/*  78:138 */         out.print("Caused by: ");
/*  79:139 */         this.rootCause.printStackTrace(out);
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.FunctorException
 * JD-Core Version:    0.7.0.1
 */