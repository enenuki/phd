/*  1:   */ package org.junit.experimental.runners;
/*  2:   */ 
/*  3:   */ import org.junit.runners.Suite;
/*  4:   */ import org.junit.runners.model.RunnerBuilder;
/*  5:   */ 
/*  6:   */ public class Enclosed
/*  7:   */   extends Suite
/*  8:   */ {
/*  9:   */   public Enclosed(Class<?> klass, RunnerBuilder builder)
/* 10:   */     throws Throwable
/* 11:   */   {
/* 12:29 */     super(builder, klass, klass.getClasses());
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.runners.Enclosed
 * JD-Core Version:    0.7.0.1
 */