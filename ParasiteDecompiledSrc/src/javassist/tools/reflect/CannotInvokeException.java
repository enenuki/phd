/*  1:   */ package javassist.tools.reflect;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ 
/*  5:   */ public class CannotInvokeException
/*  6:   */   extends RuntimeException
/*  7:   */ {
/*  8:31 */   private Throwable err = null;
/*  9:   */   
/* 10:   */   public Throwable getReason()
/* 11:   */   {
/* 12:36 */     return this.err;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public CannotInvokeException(String reason)
/* 16:   */   {
/* 17:42 */     super(reason);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public CannotInvokeException(InvocationTargetException e)
/* 21:   */   {
/* 22:49 */     super("by " + e.getTargetException().toString());
/* 23:50 */     this.err = e.getTargetException();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public CannotInvokeException(IllegalAccessException e)
/* 27:   */   {
/* 28:57 */     super("by " + e.toString());
/* 29:58 */     this.err = e;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public CannotInvokeException(ClassNotFoundException e)
/* 33:   */   {
/* 34:65 */     super("by " + e.toString());
/* 35:66 */     this.err = e;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.CannotInvokeException
 * JD-Core Version:    0.7.0.1
 */