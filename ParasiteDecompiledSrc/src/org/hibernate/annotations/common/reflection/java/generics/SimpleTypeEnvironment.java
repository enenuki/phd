/*   1:    */ package org.hibernate.annotations.common.reflection.java.generics;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.GenericArrayType;
/*   4:    */ import java.lang.reflect.ParameterizedType;
/*   5:    */ import java.lang.reflect.Type;
/*   6:    */ import java.lang.reflect.TypeVariable;
/*   7:    */ import java.lang.reflect.WildcardType;
/*   8:    */ import java.util.HashMap;
/*   9:    */ 
/*  10:    */ class SimpleTypeEnvironment
/*  11:    */   extends HashMap<Type, Type>
/*  12:    */   implements TypeEnvironment
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 1L;
/*  15: 43 */   private final TypeSwitch<Type> substitute = new TypeSwitch()
/*  16:    */   {
/*  17:    */     public Type caseClass(Class classType)
/*  18:    */     {
/*  19: 46 */       return classType;
/*  20:    */     }
/*  21:    */     
/*  22:    */     public Type caseGenericArrayType(GenericArrayType genericArrayType)
/*  23:    */     {
/*  24: 51 */       Type originalComponentType = genericArrayType.getGenericComponentType();
/*  25: 52 */       Type boundComponentType = SimpleTypeEnvironment.this.bind(originalComponentType);
/*  26: 54 */       if (originalComponentType == boundComponentType) {
/*  27: 55 */         return genericArrayType;
/*  28:    */       }
/*  29: 57 */       return TypeFactory.createArrayType(boundComponentType);
/*  30:    */     }
/*  31:    */     
/*  32:    */     public Type caseParameterizedType(ParameterizedType parameterizedType)
/*  33:    */     {
/*  34: 62 */       Type[] originalArguments = parameterizedType.getActualTypeArguments();
/*  35: 63 */       Type[] boundArguments = SimpleTypeEnvironment.this.substitute(originalArguments);
/*  36: 65 */       if (areSame(originalArguments, boundArguments)) {
/*  37: 66 */         return parameterizedType;
/*  38:    */       }
/*  39: 68 */       return TypeFactory.createParameterizedType(parameterizedType.getRawType(), boundArguments, parameterizedType.getOwnerType());
/*  40:    */     }
/*  41:    */     
/*  42:    */     private boolean areSame(Object[] array1, Object[] array2)
/*  43:    */     {
/*  44: 74 */       if (array1.length != array2.length) {
/*  45: 75 */         return false;
/*  46:    */       }
/*  47: 77 */       for (int i = 0; i < array1.length; i++) {
/*  48: 78 */         if (array1[i] != array2[i]) {
/*  49: 79 */           return false;
/*  50:    */         }
/*  51:    */       }
/*  52: 82 */       return true;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public Type caseTypeVariable(TypeVariable typeVariable)
/*  56:    */     {
/*  57: 87 */       if (!SimpleTypeEnvironment.this.containsKey(typeVariable)) {
/*  58: 88 */         return typeVariable;
/*  59:    */       }
/*  60: 90 */       return (Type)SimpleTypeEnvironment.this.get(typeVariable);
/*  61:    */     }
/*  62:    */     
/*  63:    */     public Type caseWildcardType(WildcardType wildcardType)
/*  64:    */     {
/*  65: 95 */       return wildcardType;
/*  66:    */     }
/*  67:    */   };
/*  68:    */   
/*  69:    */   public SimpleTypeEnvironment(Type[] formalTypeArgs, Type[] actualTypeArgs)
/*  70:    */   {
/*  71:100 */     for (int i = 0; i < formalTypeArgs.length; i++) {
/*  72:101 */       put(formalTypeArgs[i], actualTypeArgs[i]);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Type bind(Type type)
/*  77:    */   {
/*  78:106 */     return (Type)this.substitute.doSwitch(type);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private Type[] substitute(Type[] types)
/*  82:    */   {
/*  83:110 */     Type[] substTypes = new Type[types.length];
/*  84:111 */     for (int i = 0; i < substTypes.length; i++) {
/*  85:112 */       substTypes[i] = bind(types[i]);
/*  86:    */     }
/*  87:114 */     return substTypes;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.SimpleTypeEnvironment
 * JD-Core Version:    0.7.0.1
 */