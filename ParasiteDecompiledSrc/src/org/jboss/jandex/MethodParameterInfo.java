/*  1:   */ package org.jboss.jandex;
/*  2:   */ 
/*  3:   */ public final class MethodParameterInfo
/*  4:   */   implements AnnotationTarget
/*  5:   */ {
/*  6:   */   private final MethodInfo method;
/*  7:   */   private final short parameter;
/*  8:   */   
/*  9:   */   MethodParameterInfo(MethodInfo method, short parameter)
/* 10:   */   {
/* 11:38 */     this.method = method;
/* 12:39 */     this.parameter = parameter;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static final MethodParameterInfo create(MethodInfo method, short parameter)
/* 16:   */   {
/* 17:51 */     return new MethodParameterInfo(method, parameter);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public final MethodInfo method()
/* 21:   */   {
/* 22:60 */     return this.method;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public final short position()
/* 26:   */   {
/* 27:69 */     return this.parameter;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:73 */     return this.method + " #" + this.parameter;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.MethodParameterInfo
 * JD-Core Version:    0.7.0.1
 */