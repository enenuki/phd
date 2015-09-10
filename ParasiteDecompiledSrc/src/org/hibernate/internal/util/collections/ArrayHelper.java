/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import org.hibernate.LockMode;
/*  10:    */ import org.hibernate.LockOptions;
/*  11:    */ import org.hibernate.type.Type;
/*  12:    */ 
/*  13:    */ public final class ArrayHelper
/*  14:    */ {
/*  15:    */   public static int indexOf(Object[] array, Object object)
/*  16:    */   {
/*  17: 48 */     for (int i = 0; i < array.length; i++) {
/*  18: 49 */       if (array[i].equals(object)) {
/*  19: 49 */         return i;
/*  20:    */       }
/*  21:    */     }
/*  22: 51 */     return -1;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static String[] toStringArray(Object[] objects)
/*  26:    */   {
/*  27: 61 */     int length = objects.length;
/*  28: 62 */     String[] result = new String[length];
/*  29: 63 */     for (int i = 0; i < length; i++) {
/*  30: 64 */       result[i] = objects[i].toString();
/*  31:    */     }
/*  32: 66 */     return result;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static String[] fillArray(String value, int length)
/*  36:    */   {
/*  37: 70 */     String[] result = new String[length];
/*  38: 71 */     Arrays.fill(result, value);
/*  39: 72 */     return result;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static int[] fillArray(int value, int length)
/*  43:    */   {
/*  44: 76 */     int[] result = new int[length];
/*  45: 77 */     Arrays.fill(result, value);
/*  46: 78 */     return result;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static LockMode[] fillArray(LockMode lockMode, int length)
/*  50:    */   {
/*  51: 82 */     LockMode[] array = new LockMode[length];
/*  52: 83 */     Arrays.fill(array, lockMode);
/*  53: 84 */     return array;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static LockOptions[] fillArray(LockOptions lockOptions, int length)
/*  57:    */   {
/*  58: 88 */     LockOptions[] array = new LockOptions[length];
/*  59: 89 */     Arrays.fill(array, lockOptions);
/*  60: 90 */     return array;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String[] toStringArray(Collection coll)
/*  64:    */   {
/*  65: 94 */     return (String[])coll.toArray(new String[coll.size()]);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String[][] to2DStringArray(Collection coll)
/*  69:    */   {
/*  70: 98 */     return (String[][])coll.toArray(new String[coll.size()][]);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static int[][] to2DIntArray(Collection coll)
/*  74:    */   {
/*  75:102 */     return (int[][])coll.toArray(new int[coll.size()][]);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static Type[] toTypeArray(Collection coll)
/*  79:    */   {
/*  80:106 */     return (Type[])coll.toArray(new Type[coll.size()]);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static int[] toIntArray(Collection coll)
/*  84:    */   {
/*  85:110 */     Iterator iter = coll.iterator();
/*  86:111 */     int[] arr = new int[coll.size()];
/*  87:112 */     int i = 0;
/*  88:113 */     while (iter.hasNext()) {
/*  89:114 */       arr[(i++)] = ((Integer)iter.next()).intValue();
/*  90:    */     }
/*  91:116 */     return arr;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static boolean[] toBooleanArray(Collection coll)
/*  95:    */   {
/*  96:120 */     Iterator iter = coll.iterator();
/*  97:121 */     boolean[] arr = new boolean[coll.size()];
/*  98:122 */     int i = 0;
/*  99:123 */     while (iter.hasNext()) {
/* 100:124 */       arr[(i++)] = ((Boolean)iter.next()).booleanValue();
/* 101:    */     }
/* 102:126 */     return arr;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Object[] typecast(Object[] array, Object[] to)
/* 106:    */   {
/* 107:130 */     return Arrays.asList(array).toArray(to);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static List toList(Object array)
/* 111:    */   {
/* 112:135 */     if ((array instanceof Object[])) {
/* 113:135 */       return Arrays.asList((Object[])array);
/* 114:    */     }
/* 115:136 */     int size = Array.getLength(array);
/* 116:137 */     ArrayList list = new ArrayList(size);
/* 117:138 */     for (int i = 0; i < size; i++) {
/* 118:139 */       list.add(Array.get(array, i));
/* 119:    */     }
/* 120:141 */     return list;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static String[] slice(String[] strings, int begin, int length)
/* 124:    */   {
/* 125:145 */     String[] result = new String[length];
/* 126:146 */     System.arraycopy(strings, begin, result, 0, length);
/* 127:147 */     return result;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static Object[] slice(Object[] objects, int begin, int length)
/* 131:    */   {
/* 132:151 */     Object[] result = new Object[length];
/* 133:152 */     System.arraycopy(objects, begin, result, 0, length);
/* 134:153 */     return result;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static List toList(Iterator iter)
/* 138:    */   {
/* 139:157 */     List list = new ArrayList();
/* 140:158 */     while (iter.hasNext()) {
/* 141:159 */       list.add(iter.next());
/* 142:    */     }
/* 143:161 */     return list;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static String[] join(String[] x, String[] y)
/* 147:    */   {
/* 148:165 */     String[] result = new String[x.length + y.length];
/* 149:166 */     System.arraycopy(x, 0, result, 0, x.length);
/* 150:167 */     System.arraycopy(y, 0, result, x.length, y.length);
/* 151:168 */     return result;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static String[] join(String[] x, String[] y, boolean[] use)
/* 155:    */   {
/* 156:172 */     String[] result = new String[x.length + countTrue(use)];
/* 157:173 */     System.arraycopy(x, 0, result, 0, x.length);
/* 158:174 */     int k = x.length;
/* 159:175 */     for (int i = 0; i < y.length; i++) {
/* 160:176 */       if (use[i] != 0) {
/* 161:177 */         result[(k++)] = y[i];
/* 162:    */       }
/* 163:    */     }
/* 164:180 */     return result;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static int[] join(int[] x, int[] y)
/* 168:    */   {
/* 169:184 */     int[] result = new int[x.length + y.length];
/* 170:185 */     System.arraycopy(x, 0, result, 0, x.length);
/* 171:186 */     System.arraycopy(y, 0, result, x.length, y.length);
/* 172:187 */     return result;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static <T> T[] join(T[] x, T[] y)
/* 176:    */   {
/* 177:192 */     T[] result = (Object[])Array.newInstance(x.getClass().getComponentType(), x.length + y.length);
/* 178:193 */     System.arraycopy(x, 0, result, 0, x.length);
/* 179:194 */     System.arraycopy(y, 0, result, x.length, y.length);
/* 180:195 */     return result;
/* 181:    */   }
/* 182:    */   
/* 183:198 */   public static final boolean[] TRUE = { true };
/* 184:199 */   public static final boolean[] FALSE = { false };
/* 185:    */   
/* 186:    */   public static String toString(Object[] array)
/* 187:    */   {
/* 188:204 */     StringBuffer sb = new StringBuffer();
/* 189:205 */     sb.append("[");
/* 190:206 */     for (int i = 0; i < array.length; i++)
/* 191:    */     {
/* 192:207 */       sb.append(array[i]);
/* 193:208 */       if (i < array.length - 1) {
/* 194:208 */         sb.append(",");
/* 195:    */       }
/* 196:    */     }
/* 197:210 */     sb.append("]");
/* 198:211 */     return sb.toString();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static boolean isAllNegative(int[] array)
/* 202:    */   {
/* 203:215 */     for (int i = 0; i < array.length; i++) {
/* 204:216 */       if (array[i] >= 0) {
/* 205:216 */         return false;
/* 206:    */       }
/* 207:    */     }
/* 208:218 */     return true;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static boolean isAllTrue(boolean[] array)
/* 212:    */   {
/* 213:222 */     for (int i = 0; i < array.length; i++) {
/* 214:223 */       if (array[i] == 0) {
/* 215:223 */         return false;
/* 216:    */       }
/* 217:    */     }
/* 218:225 */     return true;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static int countTrue(boolean[] array)
/* 222:    */   {
/* 223:229 */     int result = 0;
/* 224:230 */     for (int i = 0; i < array.length; i++) {
/* 225:231 */       if (array[i] != 0) {
/* 226:231 */         result++;
/* 227:    */       }
/* 228:    */     }
/* 229:233 */     return result;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static boolean isAllFalse(boolean[] array)
/* 233:    */   {
/* 234:245 */     for (int i = 0; i < array.length; i++) {
/* 235:246 */       if (array[i] != 0) {
/* 236:246 */         return false;
/* 237:    */       }
/* 238:    */     }
/* 239:248 */     return true;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static void addAll(Collection collection, Object[] array)
/* 243:    */   {
/* 244:252 */     collection.addAll(Arrays.asList(array));
/* 245:    */   }
/* 246:    */   
/* 247:255 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/* 248:256 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/* 249:257 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/* 250:258 */   public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
/* 251:259 */   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/* 252:260 */   public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/* 253:261 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/* 254:    */   
/* 255:    */   public static int[] getBatchSizes(int maxBatchSize)
/* 256:    */   {
/* 257:264 */     int batchSize = maxBatchSize;
/* 258:265 */     int n = 1;
/* 259:266 */     while (batchSize > 1)
/* 260:    */     {
/* 261:267 */       batchSize = getNextBatchSize(batchSize);
/* 262:268 */       n++;
/* 263:    */     }
/* 264:270 */     int[] result = new int[n];
/* 265:271 */     batchSize = maxBatchSize;
/* 266:272 */     for (int i = 0; i < n; i++)
/* 267:    */     {
/* 268:273 */       result[i] = batchSize;
/* 269:274 */       batchSize = getNextBatchSize(batchSize);
/* 270:    */     }
/* 271:276 */     return result;
/* 272:    */   }
/* 273:    */   
/* 274:    */   private static int getNextBatchSize(int batchSize)
/* 275:    */   {
/* 276:280 */     if (batchSize <= 10) {
/* 277:281 */       return batchSize - 1;
/* 278:    */     }
/* 279:283 */     if (batchSize / 2 < 10) {
/* 280:284 */       return 10;
/* 281:    */     }
/* 282:287 */     return batchSize / 2;
/* 283:    */   }
/* 284:    */   
/* 285:291 */   private static int SEED = 23;
/* 286:292 */   private static int PRIME_NUMER = 37;
/* 287:    */   
/* 288:    */   public static int hash(Object[] array)
/* 289:    */   {
/* 290:298 */     int length = array.length;
/* 291:299 */     int seed = SEED;
/* 292:300 */     for (int index = 0; index < length; index++) {
/* 293:301 */       seed = hash(seed, array[index] == null ? 0 : array[index].hashCode());
/* 294:    */     }
/* 295:303 */     return seed;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static int hash(char[] array)
/* 299:    */   {
/* 300:310 */     int length = array.length;
/* 301:311 */     int seed = SEED;
/* 302:312 */     for (int index = 0; index < length; index++) {
/* 303:313 */       seed = hash(seed, array[index]);
/* 304:    */     }
/* 305:315 */     return seed;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public static int hash(byte[] bytes)
/* 309:    */   {
/* 310:322 */     int length = bytes.length;
/* 311:323 */     int seed = SEED;
/* 312:324 */     for (int index = 0; index < length; index++) {
/* 313:325 */       seed = hash(seed, bytes[index]);
/* 314:    */     }
/* 315:327 */     return seed;
/* 316:    */   }
/* 317:    */   
/* 318:    */   private static int hash(int seed, int i)
/* 319:    */   {
/* 320:331 */     return PRIME_NUMER * seed + i;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static boolean isEquals(Object[] o1, Object[] o2)
/* 324:    */   {
/* 325:338 */     if (o1 == o2) {
/* 326:338 */       return true;
/* 327:    */     }
/* 328:339 */     if ((o1 == null) || (o2 == null)) {
/* 329:339 */       return false;
/* 330:    */     }
/* 331:340 */     int length = o1.length;
/* 332:341 */     if (length != o2.length) {
/* 333:341 */       return false;
/* 334:    */     }
/* 335:342 */     for (int index = 0; index < length; index++) {
/* 336:343 */       if (!o1[index].equals(o2[index])) {
/* 337:343 */         return false;
/* 338:    */       }
/* 339:    */     }
/* 340:345 */     return true;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static boolean isEquals(char[] o1, char[] o2)
/* 344:    */   {
/* 345:352 */     if (o1 == o2) {
/* 346:352 */       return true;
/* 347:    */     }
/* 348:353 */     if ((o1 == null) || (o2 == null)) {
/* 349:353 */       return false;
/* 350:    */     }
/* 351:354 */     int length = o1.length;
/* 352:355 */     if (length != o2.length) {
/* 353:355 */       return false;
/* 354:    */     }
/* 355:356 */     for (int index = 0; index < length; index++) {
/* 356:357 */       if (o1[index] != o2[index]) {
/* 357:357 */         return false;
/* 358:    */       }
/* 359:    */     }
/* 360:359 */     return true;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public static boolean isEquals(byte[] b1, byte[] b2)
/* 364:    */   {
/* 365:366 */     if (b1 == b2) {
/* 366:366 */       return true;
/* 367:    */     }
/* 368:367 */     if ((b1 == null) || (b2 == null)) {
/* 369:367 */       return false;
/* 370:    */     }
/* 371:368 */     int length = b1.length;
/* 372:369 */     if (length != b2.length) {
/* 373:369 */       return false;
/* 374:    */     }
/* 375:370 */     for (int index = 0; index < length; index++) {
/* 376:371 */       if (b1[index] != b2[index]) {
/* 377:371 */         return false;
/* 378:    */       }
/* 379:    */     }
/* 380:373 */     return true;
/* 381:    */   }
/* 382:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.ArrayHelper
 * JD-Core Version:    0.7.0.1
 */