/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.compiler.CompileError;
/*   4:    */ 
/*   5:    */ public class CannotCompileException
/*   6:    */   extends Exception
/*   7:    */ {
/*   8:    */   private Throwable myCause;
/*   9:    */   private String message;
/*  10:    */   
/*  11:    */   public Throwable getCause()
/*  12:    */   {
/*  13: 31 */     return this.myCause == this ? null : this.myCause;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public synchronized Throwable initCause(Throwable cause)
/*  17:    */   {
/*  18: 39 */     this.myCause = cause;
/*  19: 40 */     return this;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getReason()
/*  23:    */   {
/*  24: 49 */     if (this.message != null) {
/*  25: 50 */       return this.message;
/*  26:    */     }
/*  27: 52 */     return toString();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CannotCompileException(String msg)
/*  31:    */   {
/*  32: 61 */     super(msg);
/*  33: 62 */     this.message = msg;
/*  34: 63 */     initCause(null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CannotCompileException(Throwable e)
/*  38:    */   {
/*  39: 73 */     super("by " + e.toString());
/*  40: 74 */     this.message = null;
/*  41: 75 */     initCause(e);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CannotCompileException(String msg, Throwable e)
/*  45:    */   {
/*  46: 86 */     this(msg);
/*  47: 87 */     initCause(e);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public CannotCompileException(NotFoundException e)
/*  51:    */   {
/*  52: 95 */     this("cannot find " + e.getMessage(), e);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public CannotCompileException(CompileError e)
/*  56:    */   {
/*  57:102 */     this("[source error] " + e.getMessage(), e);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public CannotCompileException(ClassNotFoundException e, String name)
/*  61:    */   {
/*  62:110 */     this("cannot find " + name, e);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public CannotCompileException(ClassFormatError e, String name)
/*  66:    */   {
/*  67:117 */     this("invalid class format: " + name, e);
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CannotCompileException
 * JD-Core Version:    0.7.0.1
 */