/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import org.apache.commons.lang.exception.CloneFailedException;
/*   7:    */ import org.apache.commons.lang.reflect.MethodUtils;
/*   8:    */ 
/*   9:    */ public class ObjectUtils
/*  10:    */ {
/*  11: 63 */   public static final Null NULL = new Null();
/*  12:    */   
/*  13:    */   public static Object defaultIfNull(Object object, Object defaultValue)
/*  14:    */   {
/*  15: 96 */     return object != null ? object : defaultValue;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static boolean equals(Object object1, Object object2)
/*  19:    */   {
/*  20:119 */     if (object1 == object2) {
/*  21:120 */       return true;
/*  22:    */     }
/*  23:122 */     if ((object1 == null) || (object2 == null)) {
/*  24:123 */       return false;
/*  25:    */     }
/*  26:125 */     return object1.equals(object2);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static boolean notEqual(Object object1, Object object2)
/*  30:    */   {
/*  31:149 */     return !equals(object1, object2);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static int hashCode(Object obj)
/*  35:    */   {
/*  36:166 */     return obj == null ? 0 : obj.hashCode();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static String identityToString(Object object)
/*  40:    */   {
/*  41:188 */     if (object == null) {
/*  42:189 */       return null;
/*  43:    */     }
/*  44:191 */     StringBuffer buffer = new StringBuffer();
/*  45:192 */     identityToString(buffer, object);
/*  46:193 */     return buffer.toString();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void identityToString(StringBuffer buffer, Object object)
/*  50:    */   {
/*  51:212 */     if (object == null) {
/*  52:213 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*  53:    */     }
/*  54:215 */     buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*  55:    */   }
/*  56:    */   
/*  57:    */   /**
/*  58:    */    * @deprecated
/*  59:    */    */
/*  60:    */   public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object)
/*  61:    */   {
/*  62:240 */     if (object == null) {
/*  63:241 */       return null;
/*  64:    */     }
/*  65:243 */     if (buffer == null) {
/*  66:244 */       buffer = new StringBuffer();
/*  67:    */     }
/*  68:246 */     return buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static String toString(Object obj)
/*  72:    */   {
/*  73:272 */     return obj == null ? "" : obj.toString();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String toString(Object obj, String nullStr)
/*  77:    */   {
/*  78:295 */     return obj == null ? nullStr : obj.toString();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static Object min(Comparable c1, Comparable c2)
/*  82:    */   {
/*  83:314 */     return compare(c1, c2, true) <= 0 ? c1 : c2;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static Object max(Comparable c1, Comparable c2)
/*  87:    */   {
/*  88:331 */     return compare(c1, c2, false) >= 0 ? c1 : c2;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static int compare(Comparable c1, Comparable c2)
/*  92:    */   {
/*  93:345 */     return compare(c1, c2, false);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static int compare(Comparable c1, Comparable c2, boolean nullGreater)
/*  97:    */   {
/*  98:362 */     if (c1 == c2) {
/*  99:363 */       return 0;
/* 100:    */     }
/* 101:364 */     if (c1 == null) {
/* 102:365 */       return nullGreater ? 1 : -1;
/* 103:    */     }
/* 104:366 */     if (c2 == null) {
/* 105:367 */       return nullGreater ? -1 : 1;
/* 106:    */     }
/* 107:369 */     return c1.compareTo(c2);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static Object clone(Object o)
/* 111:    */   {
/* 112:381 */     if ((o instanceof Cloneable))
/* 113:    */     {
/* 114:    */       Object result;
/* 115:383 */       if (o.getClass().isArray())
/* 116:    */       {
/* 117:384 */         Class componentType = o.getClass().getComponentType();
/* 118:    */         Object result;
/* 119:385 */         if (!componentType.isPrimitive())
/* 120:    */         {
/* 121:386 */           result = ((Object[])o).clone();
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:388 */           int length = Array.getLength(o);
/* 126:389 */           Object result = Array.newInstance(componentType, length);
/* 127:390 */           while (length-- > 0) {
/* 128:391 */             Array.set(result, length, Array.get(o, length));
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:    */         try
/* 135:    */         {
/* 136:396 */           result = MethodUtils.invokeMethod(o, "clone", null);
/* 137:    */         }
/* 138:    */         catch (NoSuchMethodException e)
/* 139:    */         {
/* 140:398 */           throw new CloneFailedException("Cloneable type " + o.getClass().getName() + " has no clone method", e);
/* 141:    */         }
/* 142:    */         catch (IllegalAccessException e)
/* 143:    */         {
/* 144:402 */           throw new CloneFailedException("Cannot clone Cloneable type " + o.getClass().getName(), e);
/* 145:    */         }
/* 146:    */         catch (InvocationTargetException e)
/* 147:    */         {
/* 148:405 */           throw new CloneFailedException("Exception cloning Cloneable type " + o.getClass().getName(), e.getTargetException());
/* 149:    */         }
/* 150:    */       }
/* 151:409 */       return result;
/* 152:    */     }
/* 153:412 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static Object cloneIfPossible(Object o)
/* 157:    */   {
/* 158:429 */     Object clone = clone(o);
/* 159:430 */     return clone == null ? o : clone;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static class Null
/* 163:    */     implements Serializable
/* 164:    */   {
/* 165:    */     private static final long serialVersionUID = 7092611880189329093L;
/* 166:    */     
/* 167:    */     private Object readResolve()
/* 168:    */     {
/* 169:470 */       return ObjectUtils.NULL;
/* 170:    */     }
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.ObjectUtils
 * JD-Core Version:    0.7.0.1
 */