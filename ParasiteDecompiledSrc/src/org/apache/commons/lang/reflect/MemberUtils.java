/*   1:    */ package org.apache.commons.lang.reflect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.lang.reflect.Modifier;
/*   7:    */ import org.apache.commons.lang.ArrayUtils;
/*   8:    */ import org.apache.commons.lang.ClassUtils;
/*   9:    */ import org.apache.commons.lang.SystemUtils;
/*  10:    */ 
/*  11:    */ abstract class MemberUtils
/*  12:    */ {
/*  13:    */   private static final int ACCESS_TEST = 7;
/*  14:    */   private static final Method IS_SYNTHETIC;
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 46 */     Method isSynthetic = null;
/*  19: 47 */     if (SystemUtils.isJavaVersionAtLeast(1.5F)) {
/*  20:    */       try
/*  21:    */       {
/*  22: 50 */         isSynthetic = Member.class.getMethod("isSynthetic", ArrayUtils.EMPTY_CLASS_ARRAY);
/*  23:    */       }
/*  24:    */       catch (Exception e) {}
/*  25:    */     }
/*  26: 55 */     IS_SYNTHETIC = isSynthetic;
/*  27:    */   }
/*  28:    */   
/*  29: 59 */   private static final Class[] ORDERED_PRIMITIVE_TYPES = { Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };
/*  30:    */   
/*  31:    */   static void setAccessibleWorkaround(AccessibleObject o)
/*  32:    */   {
/*  33: 75 */     if ((o == null) || (o.isAccessible())) {
/*  34: 76 */       return;
/*  35:    */     }
/*  36: 78 */     Member m = (Member)o;
/*  37: 79 */     if ((Modifier.isPublic(m.getModifiers())) && (isPackageAccess(m.getDeclaringClass().getModifiers()))) {
/*  38:    */       try
/*  39:    */       {
/*  40: 82 */         o.setAccessible(true);
/*  41:    */       }
/*  42:    */       catch (SecurityException e) {}
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   static boolean isPackageAccess(int modifiers)
/*  47:    */   {
/*  48: 95 */     return (modifiers & 0x7) == 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   static boolean isAccessible(Member m)
/*  52:    */   {
/*  53:104 */     return (m != null) && (Modifier.isPublic(m.getModifiers())) && (!isSynthetic(m));
/*  54:    */   }
/*  55:    */   
/*  56:    */   static boolean isSynthetic(Member m)
/*  57:    */   {
/*  58:113 */     if (IS_SYNTHETIC != null) {
/*  59:    */       try
/*  60:    */       {
/*  61:115 */         return ((Boolean)IS_SYNTHETIC.invoke(m, null)).booleanValue();
/*  62:    */       }
/*  63:    */       catch (Exception e) {}
/*  64:    */     }
/*  65:119 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   static int compareParameterTypes(Class[] left, Class[] right, Class[] actual)
/*  69:    */   {
/*  70:135 */     float leftCost = getTotalTransformationCost(actual, left);
/*  71:136 */     float rightCost = getTotalTransformationCost(actual, right);
/*  72:137 */     return rightCost < leftCost ? 1 : leftCost < rightCost ? -1 : 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static float getTotalTransformationCost(Class[] srcArgs, Class[] destArgs)
/*  76:    */   {
/*  77:148 */     float totalCost = 0.0F;
/*  78:149 */     for (int i = 0; i < srcArgs.length; i++)
/*  79:    */     {
/*  80:151 */       Class srcClass = srcArgs[i];
/*  81:152 */       Class destClass = destArgs[i];
/*  82:153 */       totalCost += getObjectTransformationCost(srcClass, destClass);
/*  83:    */     }
/*  84:155 */     return totalCost;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static float getObjectTransformationCost(Class srcClass, Class destClass)
/*  88:    */   {
/*  89:167 */     if (destClass.isPrimitive()) {
/*  90:168 */       return getPrimitivePromotionCost(srcClass, destClass);
/*  91:    */     }
/*  92:170 */     float cost = 0.0F;
/*  93:171 */     while ((srcClass != null) && (!destClass.equals(srcClass)))
/*  94:    */     {
/*  95:172 */       if ((destClass.isInterface()) && (ClassUtils.isAssignable(srcClass, destClass)))
/*  96:    */       {
/*  97:178 */         cost += 0.25F;
/*  98:179 */         break;
/*  99:    */       }
/* 100:181 */       cost += 1.0F;
/* 101:182 */       srcClass = srcClass.getSuperclass();
/* 102:    */     }
/* 103:188 */     if (srcClass == null) {
/* 104:189 */       cost += 1.5F;
/* 105:    */     }
/* 106:191 */     return cost;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static float getPrimitivePromotionCost(Class srcClass, Class destClass)
/* 110:    */   {
/* 111:202 */     float cost = 0.0F;
/* 112:203 */     Class cls = srcClass;
/* 113:204 */     if (!cls.isPrimitive())
/* 114:    */     {
/* 115:206 */       cost += 0.1F;
/* 116:207 */       cls = ClassUtils.wrapperToPrimitive(cls);
/* 117:    */     }
/* 118:209 */     for (int i = 0; (cls != destClass) && (i < ORDERED_PRIMITIVE_TYPES.length); i++) {
/* 119:210 */       if (cls == ORDERED_PRIMITIVE_TYPES[i])
/* 120:    */       {
/* 121:211 */         cost += 0.1F;
/* 122:212 */         if (i < ORDERED_PRIMITIVE_TYPES.length - 1) {
/* 123:213 */           cls = ORDERED_PRIMITIVE_TYPES[(i + 1)];
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:217 */     return cost;
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.reflect.MemberUtils
 * JD-Core Version:    0.7.0.1
 */