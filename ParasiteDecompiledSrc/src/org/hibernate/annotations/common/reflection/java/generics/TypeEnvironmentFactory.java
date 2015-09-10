/*   1:    */ package org.hibernate.annotations.common.reflection.java.generics;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.ParameterizedType;
/*   4:    */ import java.lang.reflect.Type;
/*   5:    */ import java.lang.reflect.TypeVariable;
/*   6:    */ 
/*   7:    */ public class TypeEnvironmentFactory
/*   8:    */ {
/*   9:    */   public TypeEnvironment getEnvironment(Class context)
/*  10:    */   {
/*  11: 45 */     if (context == null) {
/*  12: 46 */       return IdentityTypeEnvironment.INSTANCE;
/*  13:    */     }
/*  14: 48 */     return createEnvironment(context);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public TypeEnvironment getEnvironment(Type context)
/*  18:    */   {
/*  19: 52 */     if (context == null) {
/*  20: 53 */       return IdentityTypeEnvironment.INSTANCE;
/*  21:    */     }
/*  22: 55 */     return createEnvironment(context);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TypeEnvironment getEnvironment(Type t, TypeEnvironment context)
/*  26:    */   {
/*  27: 59 */     return CompoundTypeEnvironment.create(getEnvironment(t), context);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TypeEnvironment toApproximatingEnvironment(TypeEnvironment context)
/*  31:    */   {
/*  32: 63 */     return CompoundTypeEnvironment.create(new ApproximatingTypeEnvironment(), context);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private TypeEnvironment createEnvironment(Type context)
/*  36:    */   {
/*  37: 67 */     (TypeEnvironment)new TypeSwitch()
/*  38:    */     {
/*  39:    */       public TypeEnvironment caseClass(Class classType)
/*  40:    */       {
/*  41: 70 */         return CompoundTypeEnvironment.create(TypeEnvironmentFactory.this.createSuperTypeEnvironment(classType), TypeEnvironmentFactory.this.getEnvironment(classType.getSuperclass()));
/*  42:    */       }
/*  43:    */       
/*  44:    */       public TypeEnvironment caseParameterizedType(ParameterizedType parameterizedType)
/*  45:    */       {
/*  46: 78 */         return TypeEnvironmentFactory.this.createEnvironment(parameterizedType);
/*  47:    */       }
/*  48:    */       
/*  49:    */       public TypeEnvironment defaultCase(Type t)
/*  50:    */       {
/*  51: 83 */         throw new IllegalArgumentException("Invalid type for generating environment: " + t);
/*  52:    */       }
/*  53: 83 */     }.doSwitch(context);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private TypeEnvironment createSuperTypeEnvironment(Class clazz)
/*  57:    */   {
/*  58: 89 */     Class superclass = clazz.getSuperclass();
/*  59: 90 */     if (superclass == null) {
/*  60: 91 */       return IdentityTypeEnvironment.INSTANCE;
/*  61:    */     }
/*  62: 94 */     Type[] formalArgs = superclass.getTypeParameters();
/*  63: 95 */     Type genericSuperclass = clazz.getGenericSuperclass();
/*  64: 97 */     if ((genericSuperclass instanceof Class)) {
/*  65: 98 */       return IdentityTypeEnvironment.INSTANCE;
/*  66:    */     }
/*  67:101 */     if ((genericSuperclass instanceof ParameterizedType))
/*  68:    */     {
/*  69:102 */       Type[] actualArgs = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
/*  70:103 */       return new SimpleTypeEnvironment(formalArgs, actualArgs);
/*  71:    */     }
/*  72:106 */     throw new AssertionError("Should be unreachable");
/*  73:    */   }
/*  74:    */   
/*  75:    */   private TypeEnvironment createEnvironment(ParameterizedType t)
/*  76:    */   {
/*  77:110 */     Type[] tactuals = t.getActualTypeArguments();
/*  78:111 */     Type rawType = t.getRawType();
/*  79:112 */     if ((rawType instanceof Class))
/*  80:    */     {
/*  81:113 */       TypeVariable[] tparms = ((Class)rawType).getTypeParameters();
/*  82:114 */       return new SimpleTypeEnvironment(tparms, tactuals);
/*  83:    */     }
/*  84:116 */     return IdentityTypeEnvironment.INSTANCE;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory
 * JD-Core Version:    0.7.0.1
 */