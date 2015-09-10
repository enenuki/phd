/*   1:    */ package org.apache.commons.lang.reflect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.Modifier;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.lang.ClassUtils;
/*   8:    */ 
/*   9:    */ public class FieldUtils
/*  10:    */ {
/*  11:    */   public static Field getField(Class cls, String fieldName)
/*  12:    */   {
/*  13: 60 */     Field field = getField(cls, fieldName, false);
/*  14: 61 */     MemberUtils.setAccessibleWorkaround(field);
/*  15: 62 */     return field;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static Field getField(Class cls, String fieldName, boolean forceAccess)
/*  19:    */   {
/*  20: 78 */     if (cls == null) {
/*  21: 79 */       throw new IllegalArgumentException("The class must not be null");
/*  22:    */     }
/*  23: 81 */     if (fieldName == null) {
/*  24: 82 */       throw new IllegalArgumentException("The field name must not be null");
/*  25:    */     }
/*  26: 98 */     for (Class acls = cls; acls != null; acls = acls.getSuperclass()) {
/*  27:    */       try
/*  28:    */       {
/*  29:100 */         Field field = acls.getDeclaredField(fieldName);
/*  30:103 */         if (!Modifier.isPublic(field.getModifiers())) {
/*  31:104 */           if (forceAccess) {
/*  32:105 */             field.setAccessible(true);
/*  33:    */           } else {
/*  34:    */             continue;
/*  35:    */           }
/*  36:    */         }
/*  37:110 */         return field;
/*  38:    */       }
/*  39:    */       catch (NoSuchFieldException ex) {}
/*  40:    */     }
/*  41:118 */     Field match = null;
/*  42:119 */     Iterator intf = ClassUtils.getAllInterfaces(cls).iterator();
/*  43:120 */     while (intf.hasNext()) {
/*  44:    */       try
/*  45:    */       {
/*  46:122 */         Field test = ((Class)intf.next()).getField(fieldName);
/*  47:123 */         if (match != null) {
/*  48:124 */           throw new IllegalArgumentException("Reference to field " + fieldName + " is ambiguous relative to " + cls + "; a matching field exists on two or more implemented interfaces.");
/*  49:    */         }
/*  50:131 */         match = test;
/*  51:    */       }
/*  52:    */       catch (NoSuchFieldException ex) {}
/*  53:    */     }
/*  54:136 */     return match;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static Field getDeclaredField(Class cls, String fieldName)
/*  58:    */   {
/*  59:149 */     return getDeclaredField(cls, fieldName, false);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static Field getDeclaredField(Class cls, String fieldName, boolean forceAccess)
/*  63:    */   {
/*  64:164 */     if (cls == null) {
/*  65:165 */       throw new IllegalArgumentException("The class must not be null");
/*  66:    */     }
/*  67:167 */     if (fieldName == null) {
/*  68:168 */       throw new IllegalArgumentException("The field name must not be null");
/*  69:    */     }
/*  70:    */     try
/*  71:    */     {
/*  72:172 */       Field field = cls.getDeclaredField(fieldName);
/*  73:173 */       if (!MemberUtils.isAccessible(field)) {
/*  74:174 */         if (forceAccess) {
/*  75:175 */           field.setAccessible(true);
/*  76:    */         } else {
/*  77:177 */           return null;
/*  78:    */         }
/*  79:    */       }
/*  80:180 */       return field;
/*  81:    */     }
/*  82:    */     catch (NoSuchFieldException e) {}
/*  83:183 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static Object readStaticField(Field field)
/*  87:    */     throws IllegalAccessException
/*  88:    */   {
/*  89:194 */     return readStaticField(field, false);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static Object readStaticField(Field field, boolean forceAccess)
/*  93:    */     throws IllegalAccessException
/*  94:    */   {
/*  95:207 */     if (field == null) {
/*  96:208 */       throw new IllegalArgumentException("The field must not be null");
/*  97:    */     }
/*  98:210 */     if (!Modifier.isStatic(field.getModifiers())) {
/*  99:211 */       throw new IllegalArgumentException("The field '" + field.getName() + "' is not static");
/* 100:    */     }
/* 101:213 */     return readField(field, (Object)null, forceAccess);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static Object readStaticField(Class cls, String fieldName)
/* 105:    */     throws IllegalAccessException
/* 106:    */   {
/* 107:225 */     return readStaticField(cls, fieldName, false);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static Object readStaticField(Class cls, String fieldName, boolean forceAccess)
/* 111:    */     throws IllegalAccessException
/* 112:    */   {
/* 113:240 */     Field field = getField(cls, fieldName, forceAccess);
/* 114:241 */     if (field == null) {
/* 115:242 */       throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
/* 116:    */     }
/* 117:245 */     return readStaticField(field, false);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static Object readDeclaredStaticField(Class cls, String fieldName)
/* 121:    */     throws IllegalAccessException
/* 122:    */   {
/* 123:259 */     return readDeclaredStaticField(cls, fieldName, false);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static Object readDeclaredStaticField(Class cls, String fieldName, boolean forceAccess)
/* 127:    */     throws IllegalAccessException
/* 128:    */   {
/* 129:277 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 130:278 */     if (field == null) {
/* 131:279 */       throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
/* 132:    */     }
/* 133:282 */     return readStaticField(field, false);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static Object readField(Field field, Object target)
/* 137:    */     throws IllegalAccessException
/* 138:    */   {
/* 139:294 */     return readField(field, target, false);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Object readField(Field field, Object target, boolean forceAccess)
/* 143:    */     throws IllegalAccessException
/* 144:    */   {
/* 145:308 */     if (field == null) {
/* 146:309 */       throw new IllegalArgumentException("The field must not be null");
/* 147:    */     }
/* 148:311 */     if ((forceAccess) && (!field.isAccessible())) {
/* 149:312 */       field.setAccessible(true);
/* 150:    */     } else {
/* 151:314 */       MemberUtils.setAccessibleWorkaround(field);
/* 152:    */     }
/* 153:316 */     return field.get(target);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static Object readField(Object target, String fieldName)
/* 157:    */     throws IllegalAccessException
/* 158:    */   {
/* 159:328 */     return readField(target, fieldName, false);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static Object readField(Object target, String fieldName, boolean forceAccess)
/* 163:    */     throws IllegalAccessException
/* 164:    */   {
/* 165:343 */     if (target == null) {
/* 166:344 */       throw new IllegalArgumentException("target object must not be null");
/* 167:    */     }
/* 168:346 */     Class cls = target.getClass();
/* 169:347 */     Field field = getField(cls, fieldName, forceAccess);
/* 170:348 */     if (field == null) {
/* 171:349 */       throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
/* 172:    */     }
/* 173:352 */     return readField(field, target);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static Object readDeclaredField(Object target, String fieldName)
/* 177:    */     throws IllegalAccessException
/* 178:    */   {
/* 179:364 */     return readDeclaredField(target, fieldName, false);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static Object readDeclaredField(Object target, String fieldName, boolean forceAccess)
/* 183:    */     throws IllegalAccessException
/* 184:    */   {
/* 185:381 */     if (target == null) {
/* 186:382 */       throw new IllegalArgumentException("target object must not be null");
/* 187:    */     }
/* 188:384 */     Class cls = target.getClass();
/* 189:385 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 190:386 */     if (field == null) {
/* 191:387 */       throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
/* 192:    */     }
/* 193:390 */     return readField(field, target);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static void writeStaticField(Field field, Object value)
/* 197:    */     throws IllegalAccessException
/* 198:    */   {
/* 199:401 */     writeStaticField(field, value, false);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static void writeStaticField(Field field, Object value, boolean forceAccess)
/* 203:    */     throws IllegalAccessException
/* 204:    */   {
/* 205:415 */     if (field == null) {
/* 206:416 */       throw new IllegalArgumentException("The field must not be null");
/* 207:    */     }
/* 208:418 */     if (!Modifier.isStatic(field.getModifiers())) {
/* 209:419 */       throw new IllegalArgumentException("The field '" + field.getName() + "' is not static");
/* 210:    */     }
/* 211:421 */     writeField(field, (Object)null, value, forceAccess);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void writeStaticField(Class cls, String fieldName, Object value)
/* 215:    */     throws IllegalAccessException
/* 216:    */   {
/* 217:433 */     writeStaticField(cls, fieldName, value, false);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public static void writeStaticField(Class cls, String fieldName, Object value, boolean forceAccess)
/* 221:    */     throws IllegalAccessException
/* 222:    */   {
/* 223:449 */     Field field = getField(cls, fieldName, forceAccess);
/* 224:450 */     if (field == null) {
/* 225:451 */       throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
/* 226:    */     }
/* 227:454 */     writeStaticField(field, value);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static void writeDeclaredStaticField(Class cls, String fieldName, Object value)
/* 231:    */     throws IllegalAccessException
/* 232:    */   {
/* 233:467 */     writeDeclaredStaticField(cls, fieldName, value, false);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public static void writeDeclaredStaticField(Class cls, String fieldName, Object value, boolean forceAccess)
/* 237:    */     throws IllegalAccessException
/* 238:    */   {
/* 239:483 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 240:484 */     if (field == null) {
/* 241:485 */       throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
/* 242:    */     }
/* 243:488 */     writeField(field, (Object)null, value);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static void writeField(Field field, Object target, Object value)
/* 247:    */     throws IllegalAccessException
/* 248:    */   {
/* 249:500 */     writeField(field, target, value, false);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static void writeField(Field field, Object target, Object value, boolean forceAccess)
/* 253:    */     throws IllegalAccessException
/* 254:    */   {
/* 255:515 */     if (field == null) {
/* 256:516 */       throw new IllegalArgumentException("The field must not be null");
/* 257:    */     }
/* 258:518 */     if ((forceAccess) && (!field.isAccessible())) {
/* 259:519 */       field.setAccessible(true);
/* 260:    */     } else {
/* 261:521 */       MemberUtils.setAccessibleWorkaround(field);
/* 262:    */     }
/* 263:523 */     field.set(target, value);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public static void writeField(Object target, String fieldName, Object value)
/* 267:    */     throws IllegalAccessException
/* 268:    */   {
/* 269:535 */     writeField(target, fieldName, value, false);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static void writeField(Object target, String fieldName, Object value, boolean forceAccess)
/* 273:    */     throws IllegalAccessException
/* 274:    */   {
/* 275:551 */     if (target == null) {
/* 276:552 */       throw new IllegalArgumentException("target object must not be null");
/* 277:    */     }
/* 278:554 */     Class cls = target.getClass();
/* 279:555 */     Field field = getField(cls, fieldName, forceAccess);
/* 280:556 */     if (field == null) {
/* 281:557 */       throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
/* 282:    */     }
/* 283:560 */     writeField(field, target, value);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static void writeDeclaredField(Object target, String fieldName, Object value)
/* 287:    */     throws IllegalAccessException
/* 288:    */   {
/* 289:572 */     writeDeclaredField(target, fieldName, value, false);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public static void writeDeclaredField(Object target, String fieldName, Object value, boolean forceAccess)
/* 293:    */     throws IllegalAccessException
/* 294:    */   {
/* 295:588 */     if (target == null) {
/* 296:589 */       throw new IllegalArgumentException("target object must not be null");
/* 297:    */     }
/* 298:591 */     Class cls = target.getClass();
/* 299:592 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 300:593 */     if (field == null) {
/* 301:594 */       throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
/* 302:    */     }
/* 303:597 */     writeField(field, target, value);
/* 304:    */   }
/* 305:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.reflect.FieldUtils
 * JD-Core Version:    0.7.0.1
 */