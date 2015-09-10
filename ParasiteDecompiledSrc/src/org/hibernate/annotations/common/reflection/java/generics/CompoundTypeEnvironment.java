/*  1:   */ package org.hibernate.annotations.common.reflection.java.generics;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Type;
/*  4:   */ 
/*  5:   */ public class CompoundTypeEnvironment
/*  6:   */   implements TypeEnvironment
/*  7:   */ {
/*  8:   */   private final TypeEnvironment f;
/*  9:   */   private final TypeEnvironment g;
/* 10:   */   private final int hashCode;
/* 11:   */   
/* 12:   */   public static TypeEnvironment create(TypeEnvironment f, TypeEnvironment g)
/* 13:   */   {
/* 14:43 */     if (g == IdentityTypeEnvironment.INSTANCE) {
/* 15:44 */       return f;
/* 16:   */     }
/* 17:45 */     if (f == IdentityTypeEnvironment.INSTANCE) {
/* 18:46 */       return g;
/* 19:   */     }
/* 20:47 */     return new CompoundTypeEnvironment(f, g);
/* 21:   */   }
/* 22:   */   
/* 23:   */   private CompoundTypeEnvironment(TypeEnvironment f, TypeEnvironment g)
/* 24:   */   {
/* 25:51 */     this.f = f;
/* 26:52 */     this.g = g;
/* 27:53 */     this.hashCode = doHashCode();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Type bind(Type type)
/* 31:   */   {
/* 32:57 */     return this.f.bind(this.g.bind(type));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean equals(Object o)
/* 36:   */   {
/* 37:61 */     if (this == o) {
/* 38:61 */       return true;
/* 39:   */     }
/* 40:62 */     if (!(o instanceof CompoundTypeEnvironment)) {
/* 41:62 */       return false;
/* 42:   */     }
/* 43:64 */     CompoundTypeEnvironment that = (CompoundTypeEnvironment)o;
/* 44:66 */     if (differentHashCode(that)) {
/* 45:66 */       return false;
/* 46:   */     }
/* 47:68 */     if (!this.f.equals(that.f)) {
/* 48:68 */       return false;
/* 49:   */     }
/* 50:69 */     return this.g.equals(that.g);
/* 51:   */   }
/* 52:   */   
/* 53:   */   private boolean differentHashCode(CompoundTypeEnvironment that)
/* 54:   */   {
/* 55:74 */     return this.hashCode != that.hashCode;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private int doHashCode()
/* 59:   */   {
/* 60:79 */     int result = this.f.hashCode();
/* 61:80 */     result = 29 * result + this.g.hashCode();
/* 62:81 */     return result;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public int hashCode()
/* 66:   */   {
/* 67:86 */     return this.hashCode;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String toString()
/* 71:   */   {
/* 72:91 */     return this.f.toString() + "(" + this.g.toString() + ")";
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.CompoundTypeEnvironment
 * JD-Core Version:    0.7.0.1
 */