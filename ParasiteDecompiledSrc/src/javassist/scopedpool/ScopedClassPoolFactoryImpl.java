/*  1:   */ package javassist.scopedpool;
/*  2:   */ 
/*  3:   */ import javassist.ClassPool;
/*  4:   */ 
/*  5:   */ public class ScopedClassPoolFactoryImpl
/*  6:   */   implements ScopedClassPoolFactory
/*  7:   */ {
/*  8:   */   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
/*  9:   */   {
/* 10:32 */     return new ScopedClassPool(cl, src, repository, false);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository)
/* 14:   */   {
/* 15:40 */     return new ScopedClassPool(null, src, repository, true);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.ScopedClassPoolFactoryImpl
 * JD-Core Version:    0.7.0.1
 */