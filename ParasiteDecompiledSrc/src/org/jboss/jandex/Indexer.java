/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.EOFException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.nio.charset.Charset;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Arrays;
/*  11:    */ import java.util.Comparator;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ 
/*  16:    */ public final class Indexer
/*  17:    */ {
/*  18:    */   private static final int CONSTANT_CLASS = 7;
/*  19:    */   private static final int CONSTANT_FIELDREF = 9;
/*  20:    */   private static final int CONSTANT_METHODREF = 10;
/*  21:    */   private static final int CONSTANT_INTERFACEMETHODREF = 11;
/*  22:    */   private static final int CONSTANT_STRING = 8;
/*  23:    */   private static final int CONSTANT_INTEGER = 3;
/*  24:    */   private static final int CONSTANT_FLOAT = 4;
/*  25:    */   private static final int CONSTANT_LONG = 5;
/*  26:    */   private static final int CONSTANT_DOUBLE = 6;
/*  27:    */   private static final int CONSTANT_NAMEANDTYPE = 12;
/*  28:    */   private static final int CONSTANT_UTF8 = 1;
/*  29: 81 */   private static final byte[] RUNTIME_ANNOTATIONS = { 82, 117, 110, 116, 105, 109, 101, 86, 105, 115, 105, 98, 108, 101, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115 };
/*  30: 88 */   private static final byte[] RUNTIME_PARAM_ANNOTATIONS = { 82, 117, 110, 116, 105, 109, 101, 86, 105, 115, 105, 98, 108, 101, 80, 97, 114, 97, 109, 101, 116, 101, 114, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115 };
/*  31: 94 */   private static final int RUNTIME_ANNOTATIONS_LEN = RUNTIME_ANNOTATIONS.length;
/*  32: 95 */   private static final int RUNTIME_PARAM_ANNOTATIONS_LEN = RUNTIME_PARAM_ANNOTATIONS.length;
/*  33:    */   private static final int HAS_RUNTIME_ANNOTATION = 1;
/*  34:    */   private static final int HAS_RUNTIME_PARAM_ANNOTATION = 2;
/*  35:    */   private byte[] constantPool;
/*  36:    */   private int[] constantPoolOffsets;
/*  37:    */   private byte[] constantPoolAnnoAttrributes;
/*  38:    */   private ClassInfo currentClass;
/*  39:    */   private volatile ClassInfo publishClass;
/*  40:    */   private HashMap<DotName, List<AnnotationInstance>> classAnnotations;
/*  41:    */   private StrongInternPool<String> internPool;
/*  42:    */   private Map<DotName, List<AnnotationInstance>> masterAnnotations;
/*  43:    */   private Map<DotName, List<ClassInfo>> subclasses;
/*  44:    */   private Map<DotName, List<ClassInfo>> implementors;
/*  45:    */   private Map<DotName, ClassInfo> classes;
/*  46:    */   private Map<String, DotName> names;
/*  47:    */   
/*  48:    */   private static boolean match(byte[] target, int offset, byte[] expected)
/*  49:    */   {
/*  50:101 */     if (target.length - offset < expected.length) {
/*  51:102 */       return false;
/*  52:    */     }
/*  53:104 */     for (int i = 0; i < expected.length; i++) {
/*  54:105 */       if (target[(offset + i)] != expected[i]) {
/*  55:106 */         return false;
/*  56:    */       }
/*  57:    */     }
/*  58:108 */     return true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static byte[] sizeToFit(byte[] buf, int needed, int offset, int remainingEntries)
/*  62:    */   {
/*  63:112 */     if (offset + needed > buf.length) {
/*  64:113 */       buf = Arrays.copyOf(buf, buf.length + Math.max(needed, (remainingEntries + 1) * 20));
/*  65:    */     }
/*  66:115 */     return buf;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static void skipFully(InputStream s, long n)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:120 */     long total = 0L;
/*  73:122 */     while (total < n)
/*  74:    */     {
/*  75:123 */       long skipped = s.skip(n - total);
/*  76:124 */       if (skipped < 0L) {
/*  77:125 */         throw new EOFException();
/*  78:    */       }
/*  79:126 */       total += skipped;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void initIndexMaps()
/*  84:    */   {
/*  85:147 */     if (this.masterAnnotations == null) {
/*  86:148 */       this.masterAnnotations = new HashMap();
/*  87:    */     }
/*  88:150 */     if (this.subclasses == null) {
/*  89:151 */       this.subclasses = new HashMap();
/*  90:    */     }
/*  91:153 */     if (this.implementors == null) {
/*  92:154 */       this.implementors = new HashMap();
/*  93:    */     }
/*  94:156 */     if (this.classes == null) {
/*  95:157 */       this.classes = new HashMap();
/*  96:    */     }
/*  97:159 */     if (this.names == null) {
/*  98:160 */       this.names = new HashMap();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private DotName convertToName(String name)
/* 103:    */   {
/* 104:165 */     return convertToName(name, '.');
/* 105:    */   }
/* 106:    */   
/* 107:    */   private DotName convertToName(String name, char delim)
/* 108:    */   {
/* 109:169 */     DotName result = (DotName)this.names.get(name);
/* 110:170 */     if (result != null) {
/* 111:171 */       return result;
/* 112:    */     }
/* 113:173 */     int loc = name.lastIndexOf(delim);
/* 114:174 */     String local = intern(name.substring(loc + 1));
/* 115:175 */     DotName prefix = loc < 1 ? null : convertToName(intern(name.substring(0, loc)), delim);
/* 116:176 */     result = new DotName(prefix, local, true);
/* 117:    */     
/* 118:178 */     this.names.put(name, result);
/* 119:    */     
/* 120:180 */     return result;
/* 121:    */   }
/* 122:    */   
/* 123:    */   private void processMethodInfo(DataInputStream data)
/* 124:    */     throws IOException
/* 125:    */   {
/* 126:184 */     int numMethods = data.readUnsignedShort();
/* 127:186 */     for (int i = 0; i < numMethods; i++)
/* 128:    */     {
/* 129:187 */       short flags = (short)data.readUnsignedShort();
/* 130:188 */       String name = intern(decodeUtf8Entry(data.readUnsignedShort()));
/* 131:189 */       String descriptor = decodeUtf8Entry(data.readUnsignedShort());
/* 132:    */       
/* 133:191 */       IntegerHolder pos = new IntegerHolder(null);
/* 134:192 */       Type[] args = parseMethodArgs(descriptor, pos);
/* 135:193 */       IntegerHolder.access$108(pos);
/* 136:194 */       Type returnType = parseType(descriptor, pos);
/* 137:    */       
/* 138:196 */       MethodInfo method = new MethodInfo(this.currentClass, name, args, returnType, flags);
/* 139:    */       
/* 140:198 */       processAttributes(data, method);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void processFieldInfo(DataInputStream data)
/* 145:    */     throws IOException
/* 146:    */   {
/* 147:203 */     int numFields = data.readUnsignedShort();
/* 148:205 */     for (int i = 0; i < numFields; i++)
/* 149:    */     {
/* 150:206 */       short flags = (short)data.readUnsignedShort();
/* 151:207 */       String name = intern(decodeUtf8Entry(data.readUnsignedShort()));
/* 152:208 */       Type type = parseType(decodeUtf8Entry(data.readUnsignedShort()));
/* 153:209 */       FieldInfo field = new FieldInfo(this.currentClass, name, type, flags);
/* 154:    */       
/* 155:211 */       processAttributes(data, field);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private void processAttributes(DataInputStream data, AnnotationTarget target)
/* 160:    */     throws IOException
/* 161:    */   {
/* 162:216 */     int numAttrs = data.readUnsignedShort();
/* 163:217 */     for (int a = 0; a < numAttrs; a++)
/* 164:    */     {
/* 165:218 */       int index = data.readUnsignedShort();
/* 166:219 */       long attributeLen = data.readInt() & 0xFFFFFFFF;
/* 167:220 */       byte annotationAttribute = this.constantPoolAnnoAttrributes[(index - 1)];
/* 168:221 */       if (annotationAttribute == 1)
/* 169:    */       {
/* 170:222 */         int numAnnotations = data.readUnsignedShort();
/* 171:223 */         while (numAnnotations-- > 0) {
/* 172:224 */           processAnnotation(data, target);
/* 173:    */         }
/* 174:    */       }
/* 175:225 */       else if (annotationAttribute == 2)
/* 176:    */       {
/* 177:226 */         if (!(target instanceof MethodInfo)) {
/* 178:227 */           throw new IllegalStateException("RuntimeVisibleParameterAnnotaitons appeared on a non-method");
/* 179:    */         }
/* 180:228 */         int numParameters = data.readUnsignedByte();
/* 181:229 */         for (short p = 0; p < numParameters; p = (short)(p + 1))
/* 182:    */         {
/* 183:230 */           int numAnnotations = data.readUnsignedShort();
/* 184:231 */           while (numAnnotations-- > 0) {
/* 185:232 */             processAnnotation(data, new MethodParameterInfo((MethodInfo)target, p));
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:    */       else
/* 190:    */       {
/* 191:235 */         skipFully(data, attributeLen);
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private AnnotationInstance processAnnotation(DataInputStream data, AnnotationTarget target)
/* 197:    */     throws IOException
/* 198:    */   {
/* 199:241 */     String annotation = convertClassFieldDescriptor(decodeUtf8Entry(data.readUnsignedShort()));
/* 200:242 */     int valuePairs = data.readUnsignedShort();
/* 201:    */     
/* 202:244 */     AnnotationValue[] values = new AnnotationValue[valuePairs];
/* 203:245 */     for (int v = 0; v < valuePairs; v++)
/* 204:    */     {
/* 205:246 */       String name = intern(decodeUtf8Entry(data.readUnsignedShort()));
/* 206:247 */       values[v] = processAnnotationElementValue(name, data);
/* 207:    */     }
/* 208:251 */     Arrays.sort(values, new Comparator()
/* 209:    */     {
/* 210:    */       public int compare(AnnotationValue o1, AnnotationValue o2)
/* 211:    */       {
/* 212:253 */         return o1.name().compareTo(o2.name());
/* 213:    */       }
/* 214:256 */     });
/* 215:257 */     DotName annotationName = convertToName(annotation);
/* 216:258 */     AnnotationInstance instance = new AnnotationInstance(annotationName, target, values);
/* 217:261 */     if (target != null)
/* 218:    */     {
/* 219:262 */       recordAnnotation(this.classAnnotations, annotationName, instance);
/* 220:263 */       recordAnnotation(this.masterAnnotations, annotationName, instance);
/* 221:    */     }
/* 222:266 */     return instance;
/* 223:    */   }
/* 224:    */   
/* 225:    */   private void recordAnnotation(Map<DotName, List<AnnotationInstance>> classAnnotations, DotName annotation, AnnotationInstance instance)
/* 226:    */   {
/* 227:271 */     List<AnnotationInstance> list = (List)classAnnotations.get(annotation);
/* 228:272 */     if (list == null)
/* 229:    */     {
/* 230:273 */       list = new ArrayList();
/* 231:274 */       classAnnotations.put(annotation, list);
/* 232:    */     }
/* 233:277 */     list.add(instance);
/* 234:    */   }
/* 235:    */   
/* 236:    */   private String intern(String string)
/* 237:    */   {
/* 238:281 */     return (String)this.internPool.intern(string);
/* 239:    */   }
/* 240:    */   
/* 241:    */   private AnnotationValue processAnnotationElementValue(String name, DataInputStream data)
/* 242:    */     throws IOException
/* 243:    */   {
/* 244:285 */     int tag = data.readUnsignedByte();
/* 245:286 */     switch (tag)
/* 246:    */     {
/* 247:    */     case 66: 
/* 248:288 */       return new AnnotationValue.ByteValue(name, (byte)decodeIntegerEntry(data.readUnsignedShort()));
/* 249:    */     case 67: 
/* 250:290 */       return new AnnotationValue.CharacterValue(name, (char)decodeIntegerEntry(data.readUnsignedShort()));
/* 251:    */     case 73: 
/* 252:292 */       return new AnnotationValue.IntegerValue(name, decodeIntegerEntry(data.readUnsignedShort()));
/* 253:    */     case 83: 
/* 254:294 */       return new AnnotationValue.ShortValue(name, (short)decodeIntegerEntry(data.readUnsignedShort()));
/* 255:    */     case 90: 
/* 256:297 */       return new AnnotationValue.BooleanValue(name, decodeIntegerEntry(data.readUnsignedShort()) > 0);
/* 257:    */     case 70: 
/* 258:300 */       return new AnnotationValue.FloatValue(name, decodeFloatEntry(data.readUnsignedShort()));
/* 259:    */     case 68: 
/* 260:303 */       return new AnnotationValue.DoubleValue(name, decodeDoubleEntry(data.readUnsignedShort()));
/* 261:    */     case 74: 
/* 262:305 */       return new AnnotationValue.LongValue(name, decodeLongEntry(data.readUnsignedShort()));
/* 263:    */     case 115: 
/* 264:308 */       return new AnnotationValue.StringValue(name, decodeUtf8Entry(data.readUnsignedShort()));
/* 265:    */     case 99: 
/* 266:310 */       return new AnnotationValue.ClassValue(name, parseType(decodeUtf8Entry(data.readUnsignedShort())));
/* 267:    */     case 101: 
/* 268:312 */       DotName type = parseType(decodeUtf8Entry(data.readUnsignedShort())).name();
/* 269:313 */       String value = decodeUtf8Entry(data.readUnsignedShort());
/* 270:314 */       return new AnnotationValue.EnumValue(name, type, value);
/* 271:    */     case 64: 
/* 272:317 */       return new AnnotationValue.NestedAnnotation(name, processAnnotation(data, null));
/* 273:    */     case 91: 
/* 274:319 */       int numValues = data.readUnsignedShort();
/* 275:320 */       AnnotationValue[] values = new AnnotationValue[numValues];
/* 276:321 */       for (int i = 0; i < numValues; i++) {
/* 277:322 */         values[i] = processAnnotationElementValue("", data);
/* 278:    */       }
/* 279:323 */       return new AnnotationValue.ArrayValue(name, values);
/* 280:    */     }
/* 281:326 */     throw new IllegalStateException("Invalid tag value: " + tag);
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void processClassInfo(DataInputStream data)
/* 285:    */     throws IOException
/* 286:    */   {
/* 287:333 */     short flags = (short)data.readUnsignedShort();
/* 288:334 */     DotName thisName = decodeClassEntry(data.readUnsignedShort());
/* 289:335 */     int superIndex = data.readUnsignedShort();
/* 290:336 */     DotName superName = superIndex != 0 ? decodeClassEntry(superIndex) : null;
/* 291:    */     
/* 292:338 */     int numInterfaces = data.readUnsignedShort();
/* 293:339 */     DotName[] interfaces = new DotName[numInterfaces];
/* 294:341 */     for (int i = 0; i < numInterfaces; i++) {
/* 295:342 */       interfaces[i] = decodeClassEntry(data.readUnsignedShort());
/* 296:    */     }
/* 297:345 */     this.classAnnotations = new HashMap();
/* 298:346 */     this.currentClass = new ClassInfo(thisName, superName, flags, interfaces, this.classAnnotations);
/* 299:348 */     if (superName != null) {
/* 300:349 */       addSubclass(superName, this.currentClass);
/* 301:    */     }
/* 302:351 */     for (int i = 0; i < numInterfaces; i++) {
/* 303:352 */       addImplementor(interfaces[i], this.currentClass);
/* 304:    */     }
/* 305:355 */     this.classes.put(this.currentClass.name(), this.currentClass);
/* 306:    */   }
/* 307:    */   
/* 308:    */   private void addSubclass(DotName superName, ClassInfo currentClass)
/* 309:    */   {
/* 310:359 */     List<ClassInfo> list = (List)this.subclasses.get(superName);
/* 311:360 */     if (list == null)
/* 312:    */     {
/* 313:361 */       list = new ArrayList();
/* 314:362 */       this.subclasses.put(superName, list);
/* 315:    */     }
/* 316:365 */     list.add(currentClass);
/* 317:    */   }
/* 318:    */   
/* 319:    */   private void addImplementor(DotName interfaceName, ClassInfo currentClass)
/* 320:    */   {
/* 321:369 */     List<ClassInfo> list = (List)this.implementors.get(interfaceName);
/* 322:370 */     if (list == null)
/* 323:    */     {
/* 324:371 */       list = new ArrayList();
/* 325:372 */       this.implementors.put(interfaceName, list);
/* 326:    */     }
/* 327:375 */     list.add(currentClass);
/* 328:    */   }
/* 329:    */   
/* 330:    */   private boolean isJDK11OrNewer(DataInputStream stream)
/* 331:    */     throws IOException
/* 332:    */   {
/* 333:379 */     int minor = stream.readUnsignedShort();
/* 334:380 */     int major = stream.readUnsignedShort();
/* 335:381 */     return (major > 45) || ((major == 45) && (minor >= 3));
/* 336:    */   }
/* 337:    */   
/* 338:    */   private void verifyMagic(DataInputStream stream)
/* 339:    */     throws IOException
/* 340:    */   {
/* 341:385 */     byte[] buf = new byte[4];
/* 342:    */     
/* 343:387 */     stream.readFully(buf);
/* 344:388 */     if ((buf[0] != -54) || (buf[1] != -2) || (buf[2] != -70) || (buf[3] != -66)) {
/* 345:389 */       throw new IOException("Invalid Magic");
/* 346:    */     }
/* 347:    */   }
/* 348:    */   
/* 349:    */   private DotName decodeClassEntry(int classInfoIndex)
/* 350:    */   {
/* 351:394 */     byte[] pool = this.constantPool;
/* 352:395 */     int[] offsets = this.constantPoolOffsets;
/* 353:    */     
/* 354:397 */     int pos = offsets[(classInfoIndex - 1)];
/* 355:398 */     if (pool[pos] != 7) {
/* 356:399 */       throw new IllegalStateException("Constant pool entry is not a class info type: " + classInfoIndex + ":" + pos);
/* 357:    */     }
/* 358:401 */     int nameIndex = (pool[(++pos)] & 0xFF) << 8 | pool[(++pos)] & 0xFF;
/* 359:402 */     return convertToName(decodeUtf8Entry(nameIndex), '/');
/* 360:    */   }
/* 361:    */   
/* 362:    */   private String decodeUtf8Entry(int index)
/* 363:    */   {
/* 364:406 */     byte[] pool = this.constantPool;
/* 365:407 */     int[] offsets = this.constantPoolOffsets;
/* 366:    */     
/* 367:409 */     int pos = offsets[(index - 1)];
/* 368:410 */     if (pool[pos] != 1) {
/* 369:411 */       throw new IllegalStateException("Constant pool entry is not a utf8 info type: " + index + ":" + pos);
/* 370:    */     }
/* 371:413 */     int len = (pool[(++pos)] & 0xFF) << 8 | pool[(++pos)] & 0xFF;
/* 372:414 */     return new String(pool, ++pos, len, Charset.forName("UTF-8"));
/* 373:    */   }
/* 374:    */   
/* 375:    */   private int bitsToInt(byte[] pool, int pos)
/* 376:    */   {
/* 377:418 */     return (pool[(++pos)] & 0xFF) << 24 | (pool[(++pos)] & 0xFF) << 16 | (pool[(++pos)] & 0xFF) << 8 | pool[(++pos)] & 0xFF;
/* 378:    */   }
/* 379:    */   
/* 380:    */   private long bitsToLong(byte[] pool, int pos)
/* 381:    */   {
/* 382:422 */     return (pool[(++pos)] & 0xFF) << 56 | (pool[(++pos)] & 0xFF) << 48 | (pool[(++pos)] & 0xFF) << 40 | (pool[(++pos)] & 0xFF) << 32 | (pool[(++pos)] & 0xFF) << 24 | (pool[(++pos)] & 0xFF) << 16 | (pool[(++pos)] & 0xFF) << 8 | pool[(++pos)] & 0xFF;
/* 383:    */   }
/* 384:    */   
/* 385:    */   private int decodeIntegerEntry(int index)
/* 386:    */   {
/* 387:433 */     byte[] pool = this.constantPool;
/* 388:434 */     int[] offsets = this.constantPoolOffsets;
/* 389:    */     
/* 390:436 */     int pos = offsets[(index - 1)];
/* 391:437 */     if (pool[pos] != 3) {
/* 392:438 */       throw new IllegalStateException("Constant pool entry is not an integer info type: " + index + ":" + pos);
/* 393:    */     }
/* 394:440 */     return bitsToInt(pool, pos);
/* 395:    */   }
/* 396:    */   
/* 397:    */   private long decodeLongEntry(int index)
/* 398:    */   {
/* 399:445 */     byte[] pool = this.constantPool;
/* 400:446 */     int[] offsets = this.constantPoolOffsets;
/* 401:    */     
/* 402:448 */     int pos = offsets[(index - 1)];
/* 403:449 */     if (pool[pos] != 5) {
/* 404:450 */       throw new IllegalStateException("Constant pool entry is not an long info type: " + index + ":" + pos);
/* 405:    */     }
/* 406:452 */     return bitsToLong(pool, pos);
/* 407:    */   }
/* 408:    */   
/* 409:    */   private float decodeFloatEntry(int index)
/* 410:    */   {
/* 411:457 */     byte[] pool = this.constantPool;
/* 412:458 */     int[] offsets = this.constantPoolOffsets;
/* 413:    */     
/* 414:460 */     int pos = offsets[(index - 1)];
/* 415:461 */     if (pool[pos] != 4) {
/* 416:462 */       throw new IllegalStateException("Constant pool entry is not an float info type: " + index + ":" + pos);
/* 417:    */     }
/* 418:464 */     return Float.intBitsToFloat(bitsToInt(pool, pos));
/* 419:    */   }
/* 420:    */   
/* 421:    */   private double decodeDoubleEntry(int index)
/* 422:    */   {
/* 423:468 */     byte[] pool = this.constantPool;
/* 424:469 */     int[] offsets = this.constantPoolOffsets;
/* 425:    */     
/* 426:471 */     int pos = offsets[(index - 1)];
/* 427:472 */     if (pool[pos] != 6) {
/* 428:473 */       throw new IllegalStateException("Constant pool entry is not an double info type: " + index + ":" + pos);
/* 429:    */     }
/* 430:475 */     return Double.longBitsToDouble(bitsToLong(pool, pos));
/* 431:    */   }
/* 432:    */   
/* 433:    */   private static String convertClassFieldDescriptor(String descriptor)
/* 434:    */   {
/* 435:479 */     if (descriptor.charAt(0) != 'L') {
/* 436:480 */       throw new IllegalArgumentException("Non class descriptor: " + descriptor);
/* 437:    */     }
/* 438:481 */     return descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
/* 439:    */   }
/* 440:    */   
/* 441:    */   private Type[] parseMethodArgs(String descriptor, IntegerHolder pos)
/* 442:    */   {
/* 443:487 */     if (descriptor.charAt(pos.i) != '(') {
/* 444:488 */       throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
/* 445:    */     }
/* 446:490 */     ArrayList<Type> types = new ArrayList();
/* 447:491 */     while (descriptor.charAt(IntegerHolder.access$104(pos)) != ')') {
/* 448:492 */       types.add(parseType(descriptor, pos));
/* 449:    */     }
/* 450:495 */     return (Type[])types.toArray(new Type[0]);
/* 451:    */   }
/* 452:    */   
/* 453:    */   private Type parseType(String descriptor)
/* 454:    */   {
/* 455:499 */     return parseType(descriptor, new IntegerHolder(null));
/* 456:    */   }
/* 457:    */   
/* 458:    */   private Type parseType(String descriptor, IntegerHolder pos)
/* 459:    */   {
/* 460:503 */     int start = pos.i;
/* 461:    */     
/* 462:505 */     char c = descriptor.charAt(start);
/* 463:    */     
/* 464:507 */     Type.Kind kind = Type.Kind.PRIMITIVE;
/* 465:    */     DotName name;
/* 466:508 */     switch (c)
/* 467:    */     {
/* 468:    */     case 'B': 
/* 469:509 */       name = new DotName(null, "byte", true); break;
/* 470:    */     case 'C': 
/* 471:510 */       name = new DotName(null, "char", true); break;
/* 472:    */     case 'D': 
/* 473:511 */       name = new DotName(null, "double", true); break;
/* 474:    */     case 'F': 
/* 475:512 */       name = new DotName(null, "float", true); break;
/* 476:    */     case 'I': 
/* 477:513 */       name = new DotName(null, "int", true); break;
/* 478:    */     case 'J': 
/* 479:514 */       name = new DotName(null, "long", true); break;
/* 480:    */     case 'S': 
/* 481:515 */       name = new DotName(null, "short", true); break;
/* 482:    */     case 'Z': 
/* 483:516 */       name = new DotName(null, "boolean", true); break;
/* 484:    */     case 'V': 
/* 485:519 */       name = new DotName(null, "void", true);
/* 486:520 */       kind = Type.Kind.VOID;
/* 487:521 */       break;
/* 488:    */     case 'L': 
/* 489:524 */       int end = start;
/* 490:525 */       while (descriptor.charAt(++end) != ';') {}
/* 491:526 */       name = convertToName(descriptor.substring(start + 1, end), '/');
/* 492:527 */       kind = Type.Kind.CLASS;
/* 493:528 */       pos.i = end;
/* 494:529 */       break;
/* 495:    */     case '[': 
/* 496:532 */       int end = start;
/* 497:533 */       while (descriptor.charAt(++end) == '[') {}
/* 498:534 */       while ((descriptor.charAt(end) == 'L') && 
/* 499:535 */         (descriptor.charAt(++end) != ';')) {}
/* 500:540 */       name = new DotName(null, descriptor.substring(start, end + 1).replace('/', '.'), true);
/* 501:541 */       kind = Type.Kind.ARRAY;
/* 502:542 */       pos.i = end;
/* 503:543 */       break;
/* 504:    */     case 'E': 
/* 505:    */     case 'G': 
/* 506:    */     case 'H': 
/* 507:    */     case 'K': 
/* 508:    */     case 'M': 
/* 509:    */     case 'N': 
/* 510:    */     case 'O': 
/* 511:    */     case 'P': 
/* 512:    */     case 'Q': 
/* 513:    */     case 'R': 
/* 514:    */     case 'T': 
/* 515:    */     case 'U': 
/* 516:    */     case 'W': 
/* 517:    */     case 'X': 
/* 518:    */     case 'Y': 
/* 519:    */     default: 
/* 520:545 */       throw new IllegalArgumentException("Invalid descriptor: " + descriptor + " pos " + start);
/* 521:    */     }
/* 522:548 */     return new Type(name, kind);
/* 523:    */   }
/* 524:    */   
/* 525:    */   private boolean processConstantPool(DataInputStream stream)
/* 526:    */     throws IOException
/* 527:    */   {
/* 528:552 */     int poolCount = stream.readUnsignedShort() - 1;
/* 529:553 */     byte[] buf = new byte[20 * poolCount];
/* 530:554 */     byte[] annoAttributes = new byte[poolCount];
/* 531:555 */     int[] offsets = new int[poolCount];
/* 532:556 */     boolean hasAnnotations = false;
/* 533:    */     
/* 534:558 */     int pos = 0;
/* 535:558 */     for (int offset = 0; pos < poolCount; pos++)
/* 536:    */     {
/* 537:559 */       int tag = stream.readUnsignedByte();
/* 538:560 */       offsets[pos] = offset;
/* 539:561 */       switch (tag)
/* 540:    */       {
/* 541:    */       case 7: 
/* 542:    */       case 8: 
/* 543:564 */         buf = sizeToFit(buf, 3, offset, poolCount - pos);
/* 544:565 */         buf[(offset++)] = ((byte)tag);
/* 545:566 */         stream.readFully(buf, offset, 2);
/* 546:567 */         offset += 2;
/* 547:568 */         break;
/* 548:    */       case 3: 
/* 549:    */       case 4: 
/* 550:    */       case 9: 
/* 551:    */       case 10: 
/* 552:    */       case 11: 
/* 553:    */       case 12: 
/* 554:575 */         buf = sizeToFit(buf, 5, offset, poolCount - pos);
/* 555:576 */         buf[(offset++)] = ((byte)tag);
/* 556:577 */         stream.readFully(buf, offset, 4);
/* 557:578 */         offset += 4;
/* 558:579 */         break;
/* 559:    */       case 5: 
/* 560:    */       case 6: 
/* 561:582 */         buf = sizeToFit(buf, 9, offset, poolCount - pos);
/* 562:583 */         buf[(offset++)] = ((byte)tag);
/* 563:584 */         stream.readFully(buf, offset, 8);
/* 564:585 */         offset += 8;
/* 565:586 */         pos++;
/* 566:587 */         break;
/* 567:    */       case 1: 
/* 568:589 */         int len = stream.readUnsignedShort();
/* 569:590 */         buf = sizeToFit(buf, len + 3, offset, poolCount - pos);
/* 570:591 */         buf[(offset++)] = ((byte)tag);
/* 571:592 */         buf[(offset++)] = ((byte)(len >>> 8));
/* 572:593 */         buf[(offset++)] = ((byte)len);
/* 573:    */         
/* 574:595 */         stream.readFully(buf, offset, len);
/* 575:596 */         if ((len == RUNTIME_ANNOTATIONS_LEN) && (match(buf, offset, RUNTIME_ANNOTATIONS)))
/* 576:    */         {
/* 577:597 */           annoAttributes[pos] = 1;
/* 578:598 */           hasAnnotations = true;
/* 579:    */         }
/* 580:599 */         else if ((len == RUNTIME_PARAM_ANNOTATIONS_LEN) && (match(buf, offset, RUNTIME_PARAM_ANNOTATIONS)))
/* 581:    */         {
/* 582:600 */           annoAttributes[pos] = 2;
/* 583:601 */           hasAnnotations = true;
/* 584:    */         }
/* 585:603 */         offset += len;
/* 586:604 */         break;
/* 587:    */       case 2: 
/* 588:    */       default: 
/* 589:606 */         throw new IllegalStateException("Unknown tag! pos=" + pos + " poolCount = " + poolCount);
/* 590:    */       }
/* 591:    */     }
/* 592:610 */     this.constantPool = buf;
/* 593:611 */     this.constantPoolOffsets = offsets;
/* 594:612 */     this.constantPoolAnnoAttrributes = annoAttributes;
/* 595:    */     
/* 596:614 */     return hasAnnotations;
/* 597:    */   }
/* 598:    */   
/* 599:    */   public ClassInfo index(InputStream stream)
/* 600:    */     throws IOException
/* 601:    */   {
/* 602:    */     try
/* 603:    */     {
/* 604:629 */       DataInputStream data = new DataInputStream(new BufferedInputStream(stream));
/* 605:630 */       verifyMagic(data);
/* 606:634 */       if (!isJDK11OrNewer(data)) {
/* 607:635 */         return null;
/* 608:    */       }
/* 609:637 */       initIndexMaps();
/* 610:638 */       this.internPool = new StrongInternPool();
/* 611:    */       
/* 612:640 */       boolean hasAnnotations = processConstantPool(data);
/* 613:    */       
/* 614:642 */       processClassInfo(data);
/* 615:    */       ClassInfo localClassInfo2;
/* 616:643 */       if (!hasAnnotations) {
/* 617:644 */         return this.currentClass;
/* 618:    */       }
/* 619:646 */       processFieldInfo(data);
/* 620:647 */       processMethodInfo(data);
/* 621:648 */       processAttributes(data, this.currentClass);
/* 622:    */       
/* 623:    */ 
/* 624:    */ 
/* 625:652 */       this.publishClass = this.currentClass;
/* 626:    */       
/* 627:654 */       return this.publishClass;
/* 628:    */     }
/* 629:    */     finally
/* 630:    */     {
/* 631:656 */       this.constantPool = null;
/* 632:657 */       this.constantPoolOffsets = null;
/* 633:658 */       this.constantPoolAnnoAttrributes = null;
/* 634:659 */       this.currentClass = null;
/* 635:660 */       this.classAnnotations = null;
/* 636:661 */       this.internPool = null;
/* 637:    */     }
/* 638:    */   }
/* 639:    */   
/* 640:    */   public Index complete()
/* 641:    */   {
/* 642:672 */     initIndexMaps();
/* 643:    */     try
/* 644:    */     {
/* 645:674 */       return Index.create(this.masterAnnotations, this.subclasses, this.implementors, this.classes);
/* 646:    */     }
/* 647:    */     finally
/* 648:    */     {
/* 649:676 */       this.masterAnnotations = null;
/* 650:677 */       this.subclasses = null;
/* 651:678 */       this.classes = null;
/* 652:    */     }
/* 653:    */   }
/* 654:    */   
/* 655:    */   private static class IntegerHolder
/* 656:    */   {
/* 657:    */     private int i;
/* 658:    */   }
/* 659:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.Indexer
 * JD-Core Version:    0.7.0.1
 */