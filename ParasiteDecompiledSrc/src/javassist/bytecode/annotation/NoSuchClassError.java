/*  1:   */ package javassist.bytecode.annotation;
/*  2:   */ 
/*  3:   */ public class NoSuchClassError
/*  4:   */   extends Error
/*  5:   */ {
/*  6:   */   private String className;
/*  7:   */   
/*  8:   */   public NoSuchClassError(String className, Error cause)
/*  9:   */   {
/* 10:29 */     super(cause.toString(), cause);
/* 11:30 */     this.className = className;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getClassName()
/* 15:   */   {
/* 16:37 */     return this.className;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.NoSuchClassError
 * JD-Core Version:    0.7.0.1
 */