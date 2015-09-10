/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class Validate
/*   8:    */ {
/*   9:    */   public static void isTrue(boolean expression, String message, Object value)
/*  10:    */   {
/*  11: 70 */     if (!expression) {
/*  12: 71 */       throw new IllegalArgumentException(message + value);
/*  13:    */     }
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static void isTrue(boolean expression, String message, long value)
/*  17:    */   {
/*  18: 92 */     if (!expression) {
/*  19: 93 */       throw new IllegalArgumentException(message + value);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void isTrue(boolean expression, String message, double value)
/*  24:    */   {
/*  25:114 */     if (!expression) {
/*  26:115 */       throw new IllegalArgumentException(message + value);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void isTrue(boolean expression, String message)
/*  31:    */   {
/*  32:135 */     if (!expression) {
/*  33:136 */       throw new IllegalArgumentException(message);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void isTrue(boolean expression)
/*  38:    */   {
/*  39:157 */     if (!expression) {
/*  40:158 */       throw new IllegalArgumentException("The validated expression is false");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void notNull(Object object)
/*  45:    */   {
/*  46:178 */     notNull(object, "The validated object is null");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void notNull(Object object, String message)
/*  50:    */   {
/*  51:191 */     if (object == null) {
/*  52:192 */       throw new IllegalArgumentException(message);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void notEmpty(Object[] array, String message)
/*  57:    */   {
/*  58:211 */     if ((array == null) || (array.length == 0)) {
/*  59:212 */       throw new IllegalArgumentException(message);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static void notEmpty(Object[] array)
/*  64:    */   {
/*  65:229 */     notEmpty(array, "The validated array is empty");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void notEmpty(Collection collection, String message)
/*  69:    */   {
/*  70:247 */     if ((collection == null) || (collection.size() == 0)) {
/*  71:248 */       throw new IllegalArgumentException(message);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void notEmpty(Collection collection)
/*  76:    */   {
/*  77:265 */     notEmpty(collection, "The validated collection is empty");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void notEmpty(Map map, String message)
/*  81:    */   {
/*  82:283 */     if ((map == null) || (map.size() == 0)) {
/*  83:284 */       throw new IllegalArgumentException(message);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void notEmpty(Map map)
/*  88:    */   {
/*  89:302 */     notEmpty(map, "The validated map is empty");
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void notEmpty(String string, String message)
/*  93:    */   {
/*  94:320 */     if ((string == null) || (string.length() == 0)) {
/*  95:321 */       throw new IllegalArgumentException(message);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void notEmpty(String string)
/* 100:    */   {
/* 101:339 */     notEmpty(string, "The validated string is empty");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void noNullElements(Object[] array, String message)
/* 105:    */   {
/* 106:361 */     notNull(array);
/* 107:362 */     for (int i = 0; i < array.length; i++) {
/* 108:363 */       if (array[i] == null) {
/* 109:364 */         throw new IllegalArgumentException(message);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static void noNullElements(Object[] array)
/* 115:    */   {
/* 116:388 */     notNull(array);
/* 117:389 */     for (int i = 0; i < array.length; i++) {
/* 118:390 */       if (array[i] == null) {
/* 119:391 */         throw new IllegalArgumentException("The validated array contains null element at index: " + i);
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void noNullElements(Collection collection, String message)
/* 125:    */   {
/* 126:416 */     notNull(collection);
/* 127:417 */     for (Iterator it = collection.iterator(); it.hasNext();) {
/* 128:418 */       if (it.next() == null) {
/* 129:419 */         throw new IllegalArgumentException(message);
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void noNullElements(Collection collection)
/* 135:    */   {
/* 136:443 */     notNull(collection);
/* 137:444 */     int i = 0;
/* 138:445 */     for (Iterator it = collection.iterator(); it.hasNext(); i++) {
/* 139:446 */       if (it.next() == null) {
/* 140:447 */         throw new IllegalArgumentException("The validated collection contains null element at index: " + i);
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static void allElementsOfType(Collection collection, Class clazz, String message)
/* 146:    */   {
/* 147:467 */     notNull(collection);
/* 148:468 */     notNull(clazz);
/* 149:469 */     for (Iterator it = collection.iterator(); it.hasNext();) {
/* 150:470 */       if (!clazz.isInstance(it.next())) {
/* 151:471 */         throw new IllegalArgumentException(message);
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void allElementsOfType(Collection collection, Class clazz)
/* 157:    */   {
/* 158:495 */     notNull(collection);
/* 159:496 */     notNull(clazz);
/* 160:497 */     int i = 0;
/* 161:498 */     for (Iterator it = collection.iterator(); it.hasNext(); i++) {
/* 162:499 */       if (!clazz.isInstance(it.next())) {
/* 163:500 */         throw new IllegalArgumentException("The validated collection contains an element not of type " + clazz.getName() + " at index: " + i);
/* 164:    */       }
/* 165:    */     }
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.Validate
 * JD-Core Version:    0.7.0.1
 */