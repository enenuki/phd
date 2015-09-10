/*   1:    */ package javassist.tools.reflect;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.NotFoundException;
/*   6:    */ 
/*   7:    */ public class Loader
/*   8:    */   extends javassist.Loader
/*   9:    */ {
/*  10:    */   protected Reflection reflection;
/*  11:    */   
/*  12:    */   public static void main(String[] args)
/*  13:    */     throws Throwable
/*  14:    */   {
/*  15:124 */     Loader cl = new Loader();
/*  16:125 */     cl.run(args);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Loader()
/*  20:    */     throws CannotCompileException, NotFoundException
/*  21:    */   {
/*  22:133 */     delegateLoadingOf("javassist.tools.reflect.Loader");
/*  23:    */     
/*  24:135 */     this.reflection = new Reflection();
/*  25:136 */     ClassPool pool = ClassPool.getDefault();
/*  26:137 */     addTranslator(pool, this.reflection);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean makeReflective(String clazz, String metaobject, String metaclass)
/*  30:    */     throws CannotCompileException, NotFoundException
/*  31:    */   {
/*  32:161 */     return this.reflection.makeReflective(clazz, metaobject, metaclass);
/*  33:    */   }
/*  34:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Loader
 * JD-Core Version:    0.7.0.1
 */