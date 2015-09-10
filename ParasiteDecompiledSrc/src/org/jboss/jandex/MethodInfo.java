/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ public final class MethodInfo
/*   4:    */   implements AnnotationTarget
/*   5:    */ {
/*   6:    */   private final String name;
/*   7:    */   private final Type[] args;
/*   8:    */   private final Type returnType;
/*   9:    */   private final short flags;
/*  10:    */   private final ClassInfo clazz;
/*  11:    */   
/*  12:    */   MethodInfo(ClassInfo clazz, String name, Type[] args, Type returnType, short flags)
/*  13:    */   {
/*  14: 42 */     this.clazz = clazz;
/*  15: 43 */     this.name = name;
/*  16: 44 */     this.args = args;
/*  17: 45 */     this.returnType = returnType;
/*  18: 46 */     this.flags = flags;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static final MethodInfo create(ClassInfo clazz, String name, Type[] args, Type returnType, short flags)
/*  22:    */   {
/*  23: 60 */     if (clazz == null) {
/*  24: 61 */       throw new IllegalArgumentException("Clazz can't be null");
/*  25:    */     }
/*  26: 63 */     if (name == null) {
/*  27: 64 */       throw new IllegalArgumentException("Name can't be null");
/*  28:    */     }
/*  29: 66 */     if (args == null) {
/*  30: 67 */       throw new IllegalArgumentException("Values can't be null");
/*  31:    */     }
/*  32: 69 */     if (returnType == null) {
/*  33: 70 */       throw new IllegalArgumentException("returnType can't be null");
/*  34:    */     }
/*  35: 72 */     return new MethodInfo(clazz, name, args, returnType, flags);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final String name()
/*  39:    */   {
/*  40: 82 */     return this.name;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final ClassInfo declaringClass()
/*  44:    */   {
/*  45: 91 */     return this.clazz;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final Type[] args()
/*  49:    */   {
/*  50:100 */     return this.args;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final Type returnType()
/*  54:    */   {
/*  55:110 */     return this.returnType;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final short flags()
/*  59:    */   {
/*  60:120 */     return this.flags;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65:124 */     StringBuilder builder = new StringBuilder();
/*  66:125 */     builder.append(this.returnType).append(' ').append(this.clazz.name()).append('.').append(this.name).append('(');
/*  67:126 */     for (int i = 0; i < this.args.length; i++)
/*  68:    */     {
/*  69:127 */       builder.append(this.args[i]);
/*  70:128 */       if (i + 1 < this.args.length) {
/*  71:129 */         builder.append(", ");
/*  72:    */       }
/*  73:    */     }
/*  74:131 */     builder.append(')');
/*  75:    */     
/*  76:133 */     return builder.toString();
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.MethodInfo
 * JD-Core Version:    0.7.0.1
 */