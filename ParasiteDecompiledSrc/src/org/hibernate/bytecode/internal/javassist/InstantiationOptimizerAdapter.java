/*  1:   */ package org.hibernate.bytecode.internal.javassist;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.InstantiationException;
/*  5:   */ import org.hibernate.bytecode.spi.ReflectionOptimizer.InstantiationOptimizer;
/*  6:   */ 
/*  7:   */ public class InstantiationOptimizerAdapter
/*  8:   */   implements ReflectionOptimizer.InstantiationOptimizer, Serializable
/*  9:   */ {
/* 10:   */   private final FastClass fastClass;
/* 11:   */   
/* 12:   */   public InstantiationOptimizerAdapter(FastClass fastClass)
/* 13:   */   {
/* 14:41 */     this.fastClass = fastClass;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object newInstance()
/* 18:   */   {
/* 19:   */     try
/* 20:   */     {
/* 21:46 */       return this.fastClass.newInstance();
/* 22:   */     }
/* 23:   */     catch (Throwable t)
/* 24:   */     {
/* 25:49 */       throw new InstantiationException("Could not instantiate entity with Javassist optimizer: ", this.fastClass.getJavaClass(), t);
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.InstantiationOptimizerAdapter
 * JD-Core Version:    0.7.0.1
 */