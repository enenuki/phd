/*  1:   */ package javassist.expr;
/*  2:   */ 
/*  3:   */ import javassist.CtClass;
/*  4:   */ import javassist.CtConstructor;
/*  5:   */ import javassist.CtMethod;
/*  6:   */ import javassist.NotFoundException;
/*  7:   */ import javassist.bytecode.CodeIterator;
/*  8:   */ import javassist.bytecode.MethodInfo;
/*  9:   */ 
/* 10:   */ public class ConstructorCall
/* 11:   */   extends MethodCall
/* 12:   */ {
/* 13:   */   protected ConstructorCall(int pos, CodeIterator i, CtClass decl, MethodInfo m)
/* 14:   */   {
/* 15:36 */     super(pos, i, decl, m);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getMethodName()
/* 19:   */   {
/* 20:43 */     return isSuper() ? "super" : "this";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public CtMethod getMethod()
/* 24:   */     throws NotFoundException
/* 25:   */   {
/* 26:52 */     throw new NotFoundException("this is a constructor call.  Call getConstructor().");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CtConstructor getConstructor()
/* 30:   */     throws NotFoundException
/* 31:   */   {
/* 32:59 */     return getCtClass().getConstructor(getSignature());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isSuper()
/* 36:   */   {
/* 37:67 */     return super.isSuper();
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.ConstructorCall
 * JD-Core Version:    0.7.0.1
 */