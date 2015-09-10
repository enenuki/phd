/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ public final class FieldInfo
/*   4:    */   implements AnnotationTarget
/*   5:    */ {
/*   6:    */   private final String name;
/*   7:    */   private final Type type;
/*   8:    */   private final short flags;
/*   9:    */   private final ClassInfo clazz;
/*  10:    */   
/*  11:    */   FieldInfo(ClassInfo clazz, String name, Type type, short flags)
/*  12:    */   {
/*  13: 42 */     this.clazz = clazz;
/*  14: 43 */     this.name = name;
/*  15: 44 */     this.type = type;
/*  16: 45 */     this.flags = flags;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static final FieldInfo create(ClassInfo clazz, String name, Type type, short flags)
/*  20:    */   {
/*  21: 58 */     if (clazz == null) {
/*  22: 59 */       throw new IllegalArgumentException("Clazz can't be null");
/*  23:    */     }
/*  24: 61 */     if (name == null) {
/*  25: 62 */       throw new IllegalArgumentException("Name can't be null");
/*  26:    */     }
/*  27: 64 */     return new FieldInfo(clazz, name, type, flags);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final String name()
/*  31:    */   {
/*  32: 74 */     return this.name;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final ClassInfo declaringClass()
/*  36:    */   {
/*  37: 83 */     return this.clazz;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final Type type()
/*  41:    */   {
/*  42: 92 */     return this.type;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final short flags()
/*  46:    */   {
/*  47:101 */     return this.flags;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toString()
/*  51:    */   {
/*  52:105 */     return this.type + " " + this.clazz.name() + "." + this.name;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.FieldInfo
 * JD-Core Version:    0.7.0.1
 */