/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import org.hibernate.annotations.common.reflection.XPackage;
/*  4:   */ 
/*  5:   */ class JavaXPackage
/*  6:   */   extends JavaXAnnotatedElement
/*  7:   */   implements XPackage
/*  8:   */ {
/*  9:   */   public JavaXPackage(Package pkg, JavaReflectionManager factory)
/* 10:   */   {
/* 11:35 */     super(pkg, factory);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:39 */     return ((Package)toAnnotatedElement()).getName();
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXPackage
 * JD-Core Version:    0.7.0.1
 */