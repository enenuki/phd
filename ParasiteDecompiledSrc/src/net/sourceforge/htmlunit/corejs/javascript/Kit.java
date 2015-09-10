/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class Kit
/*  10:    */ {
/*  11: 57 */   private static Method Throwable_initCause = null;
/*  12:    */   
/*  13:    */   static
/*  14:    */   {
/*  15:    */     try
/*  16:    */     {
/*  17: 62 */       Class<?> ThrowableClass = classOrNull("java.lang.Throwable");
/*  18: 63 */       Class<?>[] signature = { ThrowableClass };
/*  19: 64 */       Throwable_initCause = ThrowableClass.getMethod("initCause", signature);
/*  20:    */     }
/*  21:    */     catch (Exception ex) {}
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static Class<?> classOrNull(String className)
/*  25:    */   {
/*  26:    */     try
/*  27:    */     {
/*  28: 74 */       return Class.forName(className);
/*  29:    */     }
/*  30:    */     catch (ClassNotFoundException ex) {}catch (SecurityException ex) {}catch (LinkageError ex) {}catch (IllegalArgumentException e) {}
/*  31: 82 */     return null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Class<?> classOrNull(ClassLoader loader, String className)
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 92 */       return loader.loadClass(className);
/*  39:    */     }
/*  40:    */     catch (ClassNotFoundException ex) {}catch (SecurityException ex) {}catch (LinkageError ex) {}catch (IllegalArgumentException e) {}
/*  41:100 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   static Object newInstanceOrNull(Class<?> cl)
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48:106 */       return cl.newInstance();
/*  49:    */     }
/*  50:    */     catch (SecurityException x) {}catch (LinkageError ex) {}catch (InstantiationException x) {}catch (IllegalAccessException x) {}
/*  51:112 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   static boolean testIfCanLoadRhinoClasses(ClassLoader loader)
/*  55:    */   {
/*  56:120 */     Class<?> testClass = ScriptRuntime.ContextFactoryClass;
/*  57:121 */     Class<?> x = classOrNull(loader, testClass.getName());
/*  58:122 */     if (x != testClass) {
/*  59:127 */       return false;
/*  60:    */     }
/*  61:129 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static RuntimeException initCause(RuntimeException ex, Throwable cause)
/*  65:    */   {
/*  66:140 */     if (Throwable_initCause != null)
/*  67:    */     {
/*  68:141 */       Object[] args = { cause };
/*  69:    */       try
/*  70:    */       {
/*  71:143 */         Throwable_initCause.invoke(ex, args);
/*  72:    */       }
/*  73:    */       catch (Exception e) {}
/*  74:    */     }
/*  75:148 */     return ex;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static int xDigitToInt(int c, int accumulator)
/*  79:    */   {
/*  80:160 */     if (c <= 57)
/*  81:    */     {
/*  82:161 */       c -= 48;
/*  83:162 */       if (0 <= c) {
/*  84:    */         break label55;
/*  85:    */       }
/*  86:    */     }
/*  87:163 */     else if (c <= 70)
/*  88:    */     {
/*  89:164 */       if (65 <= c)
/*  90:    */       {
/*  91:165 */         c -= 55;
/*  92:    */         break label55;
/*  93:    */       }
/*  94:    */     }
/*  95:168 */     else if ((c <= 102) && 
/*  96:169 */       (97 <= c))
/*  97:    */     {
/*  98:170 */       c -= 87;
/*  99:    */       break label55;
/* 100:    */     }
/* 101:174 */     return -1;
/* 102:    */     label55:
/* 103:176 */     return accumulator << 4 | c;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Object addListener(Object bag, Object listener)
/* 107:    */   {
/* 108:229 */     if (listener == null) {
/* 109:229 */       throw new IllegalArgumentException();
/* 110:    */     }
/* 111:230 */     if ((listener instanceof Object[])) {
/* 112:230 */       throw new IllegalArgumentException();
/* 113:    */     }
/* 114:232 */     if (bag == null)
/* 115:    */     {
/* 116:233 */       bag = listener;
/* 117:    */     }
/* 118:234 */     else if (!(bag instanceof Object[]))
/* 119:    */     {
/* 120:235 */       bag = new Object[] { bag, listener };
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:237 */       Object[] array = (Object[])bag;
/* 125:238 */       int L = array.length;
/* 126:240 */       if (L < 2) {
/* 127:240 */         throw new IllegalArgumentException();
/* 128:    */       }
/* 129:241 */       Object[] tmp = new Object[L + 1];
/* 130:242 */       System.arraycopy(array, 0, tmp, 0, L);
/* 131:243 */       tmp[L] = listener;
/* 132:244 */       bag = tmp;
/* 133:    */     }
/* 134:247 */     return bag;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static Object removeListener(Object bag, Object listener)
/* 138:    */   {
/* 139:268 */     if (listener == null) {
/* 140:268 */       throw new IllegalArgumentException();
/* 141:    */     }
/* 142:269 */     if ((listener instanceof Object[])) {
/* 143:269 */       throw new IllegalArgumentException();
/* 144:    */     }
/* 145:271 */     if (bag == listener)
/* 146:    */     {
/* 147:272 */       bag = null;
/* 148:    */     }
/* 149:273 */     else if ((bag instanceof Object[]))
/* 150:    */     {
/* 151:274 */       Object[] array = (Object[])bag;
/* 152:275 */       int L = array.length;
/* 153:277 */       if (L < 2) {
/* 154:277 */         throw new IllegalArgumentException();
/* 155:    */       }
/* 156:278 */       if (L == 2)
/* 157:    */       {
/* 158:279 */         if (array[1] == listener) {
/* 159:280 */           bag = array[0];
/* 160:281 */         } else if (array[0] == listener) {
/* 161:282 */           bag = array[1];
/* 162:    */         }
/* 163:    */       }
/* 164:    */       else
/* 165:    */       {
/* 166:285 */         int i = L;
/* 167:    */         do
/* 168:    */         {
/* 169:287 */           i--;
/* 170:288 */           if (array[i] == listener)
/* 171:    */           {
/* 172:289 */             Object[] tmp = new Object[L - 1];
/* 173:290 */             System.arraycopy(array, 0, tmp, 0, i);
/* 174:291 */             System.arraycopy(array, i + 1, tmp, i, L - (i + 1));
/* 175:292 */             bag = tmp;
/* 176:293 */             break;
/* 177:    */           }
/* 178:295 */         } while (i != 0);
/* 179:    */       }
/* 180:    */     }
/* 181:299 */     return bag;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static Object getListener(Object bag, int index)
/* 185:    */   {
/* 186:316 */     if (index == 0)
/* 187:    */     {
/* 188:317 */       if (bag == null) {
/* 189:318 */         return null;
/* 190:    */       }
/* 191:319 */       if (!(bag instanceof Object[])) {
/* 192:320 */         return bag;
/* 193:    */       }
/* 194:321 */       Object[] array = (Object[])bag;
/* 195:323 */       if (array.length < 2) {
/* 196:323 */         throw new IllegalArgumentException();
/* 197:    */       }
/* 198:324 */       return array[0];
/* 199:    */     }
/* 200:325 */     if (index == 1)
/* 201:    */     {
/* 202:326 */       if (!(bag instanceof Object[]))
/* 203:    */       {
/* 204:327 */         if (bag == null) {
/* 205:327 */           throw new IllegalArgumentException();
/* 206:    */         }
/* 207:328 */         return null;
/* 208:    */       }
/* 209:330 */       Object[] array = (Object[])bag;
/* 210:    */       
/* 211:332 */       return array[1];
/* 212:    */     }
/* 213:335 */     Object[] array = (Object[])bag;
/* 214:336 */     int L = array.length;
/* 215:337 */     if (L < 2) {
/* 216:337 */       throw new IllegalArgumentException();
/* 217:    */     }
/* 218:338 */     if (index == L) {
/* 219:339 */       return null;
/* 220:    */     }
/* 221:340 */     return array[index];
/* 222:    */   }
/* 223:    */   
/* 224:    */   static Object initHash(Map<Object, Object> h, Object key, Object initialValue)
/* 225:    */   {
/* 226:346 */     synchronized (h)
/* 227:    */     {
/* 228:347 */       Object current = h.get(key);
/* 229:348 */       if (current == null) {
/* 230:349 */         h.put(key, initialValue);
/* 231:    */       } else {
/* 232:351 */         initialValue = current;
/* 233:    */       }
/* 234:    */     }
/* 235:354 */     return initialValue;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private static final class ComplexKey
/* 239:    */   {
/* 240:    */     private Object key1;
/* 241:    */     private Object key2;
/* 242:    */     private int hash;
/* 243:    */     
/* 244:    */     ComplexKey(Object key1, Object key2)
/* 245:    */     {
/* 246:365 */       this.key1 = key1;
/* 247:366 */       this.key2 = key2;
/* 248:    */     }
/* 249:    */     
/* 250:    */     public boolean equals(Object anotherObj)
/* 251:    */     {
/* 252:372 */       if (!(anotherObj instanceof ComplexKey)) {
/* 253:373 */         return false;
/* 254:    */       }
/* 255:374 */       ComplexKey another = (ComplexKey)anotherObj;
/* 256:375 */       return (this.key1.equals(another.key1)) && (this.key2.equals(another.key2));
/* 257:    */     }
/* 258:    */     
/* 259:    */     public int hashCode()
/* 260:    */     {
/* 261:381 */       if (this.hash == 0) {
/* 262:382 */         this.hash = (this.key1.hashCode() ^ this.key2.hashCode());
/* 263:    */       }
/* 264:384 */       return this.hash;
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static Object makeHashKeyFromPair(Object key1, Object key2)
/* 269:    */   {
/* 270:390 */     if (key1 == null) {
/* 271:390 */       throw new IllegalArgumentException();
/* 272:    */     }
/* 273:391 */     if (key2 == null) {
/* 274:391 */       throw new IllegalArgumentException();
/* 275:    */     }
/* 276:392 */     return new ComplexKey(key1, key2);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public static String readReader(Reader r)
/* 280:    */     throws IOException
/* 281:    */   {
/* 282:398 */     char[] buffer = new char[512];
/* 283:399 */     int cursor = 0;
/* 284:    */     for (;;)
/* 285:    */     {
/* 286:401 */       int n = r.read(buffer, cursor, buffer.length - cursor);
/* 287:402 */       if (n < 0) {
/* 288:    */         break;
/* 289:    */       }
/* 290:403 */       cursor += n;
/* 291:404 */       if (cursor == buffer.length)
/* 292:    */       {
/* 293:405 */         char[] tmp = new char[buffer.length * 2];
/* 294:406 */         System.arraycopy(buffer, 0, tmp, 0, cursor);
/* 295:407 */         buffer = tmp;
/* 296:    */       }
/* 297:    */     }
/* 298:410 */     return new String(buffer, 0, cursor);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static byte[] readStream(InputStream is, int initialBufferCapacity)
/* 302:    */     throws IOException
/* 303:    */   {
/* 304:416 */     if (initialBufferCapacity <= 0) {
/* 305:417 */       throw new IllegalArgumentException("Bad initialBufferCapacity: " + initialBufferCapacity);
/* 306:    */     }
/* 307:420 */     byte[] buffer = new byte[initialBufferCapacity];
/* 308:421 */     int cursor = 0;
/* 309:    */     for (;;)
/* 310:    */     {
/* 311:423 */       int n = is.read(buffer, cursor, buffer.length - cursor);
/* 312:424 */       if (n < 0) {
/* 313:    */         break;
/* 314:    */       }
/* 315:425 */       cursor += n;
/* 316:426 */       if (cursor == buffer.length)
/* 317:    */       {
/* 318:427 */         byte[] tmp = new byte[buffer.length * 2];
/* 319:428 */         System.arraycopy(buffer, 0, tmp, 0, cursor);
/* 320:429 */         buffer = tmp;
/* 321:    */       }
/* 322:    */     }
/* 323:432 */     if (cursor != buffer.length)
/* 324:    */     {
/* 325:433 */       byte[] tmp = new byte[cursor];
/* 326:434 */       System.arraycopy(buffer, 0, tmp, 0, cursor);
/* 327:435 */       buffer = tmp;
/* 328:    */     }
/* 329:437 */     return buffer;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public static RuntimeException codeBug()
/* 333:    */     throws RuntimeException
/* 334:    */   {
/* 335:449 */     RuntimeException ex = new IllegalStateException("FAILED ASSERTION");
/* 336:    */     
/* 337:451 */     ex.printStackTrace(System.err);
/* 338:452 */     throw ex;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public static RuntimeException codeBug(String msg)
/* 342:    */     throws RuntimeException
/* 343:    */   {
/* 344:464 */     msg = "FAILED ASSERTION: " + msg;
/* 345:465 */     RuntimeException ex = new IllegalStateException(msg);
/* 346:    */     
/* 347:467 */     ex.printStackTrace(System.err);
/* 348:468 */     throw ex;
/* 349:    */   }
/* 350:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Kit
 * JD-Core Version:    0.7.0.1
 */