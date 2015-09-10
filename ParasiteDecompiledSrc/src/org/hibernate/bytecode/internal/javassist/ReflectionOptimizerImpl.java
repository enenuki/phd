/*  1:   */ package org.hibernate.bytecode.internal.javassist;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.bytecode.spi.ReflectionOptimizer;
/*  5:   */ import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
/*  6:   */ import org.hibernate.bytecode.spi.ReflectionOptimizer.InstantiationOptimizer;
/*  7:   */ 
/*  8:   */ public class ReflectionOptimizerImpl
/*  9:   */   implements ReflectionOptimizer, Serializable
/* 10:   */ {
/* 11:   */   private final ReflectionOptimizer.InstantiationOptimizer instantiationOptimizer;
/* 12:   */   private final ReflectionOptimizer.AccessOptimizer accessOptimizer;
/* 13:   */   
/* 14:   */   public ReflectionOptimizerImpl(ReflectionOptimizer.InstantiationOptimizer instantiationOptimizer, ReflectionOptimizer.AccessOptimizer accessOptimizer)
/* 15:   */   {
/* 16:43 */     this.instantiationOptimizer = instantiationOptimizer;
/* 17:44 */     this.accessOptimizer = accessOptimizer;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ReflectionOptimizer.InstantiationOptimizer getInstantiationOptimizer()
/* 21:   */   {
/* 22:48 */     return this.instantiationOptimizer;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ReflectionOptimizer.AccessOptimizer getAccessOptimizer()
/* 26:   */   {
/* 27:52 */     return this.accessOptimizer;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.ReflectionOptimizerImpl
 * JD-Core Version:    0.7.0.1
 */