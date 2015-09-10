/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.BufferedOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.TreeMap;
/*  13:    */ 
/*  14:    */ public final class IndexWriter
/*  15:    */ {
/*  16:    */   private static final int MAGIC = -1161945323;
/*  17:    */   private static final byte VERSION = 2;
/*  18:    */   private static final byte FIELD_TAG = 1;
/*  19:    */   private static final byte METHOD_TAG = 2;
/*  20:    */   private static final byte METHOD_PARAMATER_TAG = 3;
/*  21:    */   private static final byte CLASS_TAG = 4;
/*  22:    */   private static final int AVALUE_BYTE = 1;
/*  23:    */   private static final int AVALUE_SHORT = 2;
/*  24:    */   private static final int AVALUE_INT = 3;
/*  25:    */   private static final int AVALUE_CHAR = 4;
/*  26:    */   private static final int AVALUE_FLOAT = 5;
/*  27:    */   private static final int AVALUE_DOUBLE = 6;
/*  28:    */   private static final int AVALUE_LONG = 7;
/*  29:    */   private static final int AVALUE_BOOLEAN = 8;
/*  30:    */   private static final int AVALUE_STRING = 9;
/*  31:    */   private static final int AVALUE_CLASS = 10;
/*  32:    */   private static final int AVALUE_ENUM = 11;
/*  33:    */   private static final int AVALUE_ARRAY = 12;
/*  34:    */   private static final int AVALUE_NESTED = 13;
/*  35:    */   private final OutputStream out;
/*  36:    */   private StrongInternPool<String> pool;
/*  37:    */   StrongInternPool<String>.Index poolIndex;
/*  38:    */   private TreeMap<DotName, Integer> classTable;
/*  39:    */   
/*  40:    */   public IndexWriter(OutputStream out)
/*  41:    */   {
/*  42: 89 */     this.out = out;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int write(Index index)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:101 */     PackedDataOutputStream stream = new PackedDataOutputStream(new BufferedOutputStream(this.out));
/*  49:102 */     stream.writeInt(-1161945323);
/*  50:103 */     stream.writeByte(2);
/*  51:    */     
/*  52:105 */     buildTables(index);
/*  53:106 */     writeClassTable(stream);
/*  54:107 */     writeStringTable(stream);
/*  55:108 */     writeClasses(stream, index);
/*  56:109 */     stream.flush();
/*  57:110 */     return stream.size();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void writeStringTable(PackedDataOutputStream stream)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:114 */     stream.writePackedU32(this.pool.size());
/*  64:115 */     Iterator<String> iter = this.pool.iterator();
/*  65:116 */     while (iter.hasNext())
/*  66:    */     {
/*  67:117 */       String string = (String)iter.next();
/*  68:118 */       stream.writeUTF(string);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void writeClassTable(PackedDataOutputStream stream)
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:123 */     stream.writePackedU32(this.classTable.size());
/*  76:    */     
/*  77:    */ 
/*  78:126 */     int pos = 1;
/*  79:127 */     for (Map.Entry<DotName, Integer> entry : this.classTable.entrySet())
/*  80:    */     {
/*  81:128 */       entry.setValue(Integer.valueOf(pos++));
/*  82:129 */       DotName name = (DotName)entry.getKey();
/*  83:130 */       assert (!name.isComponentized());
/*  84:    */       
/*  85:132 */       int nameDepth = 0;
/*  86:133 */       for (DotName prefix = name.prefix(); prefix != null; prefix = prefix.prefix()) {
/*  87:134 */         nameDepth++;
/*  88:    */       }
/*  89:136 */       stream.writePackedU32(nameDepth);
/*  90:137 */       stream.writeUTF(name.local());
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private int positionOf(String string)
/*  95:    */   {
/*  96:142 */     int i = this.poolIndex.positionOf(string);
/*  97:143 */     if (i < 0) {
/*  98:144 */       throw new IllegalStateException();
/*  99:    */     }
/* 100:146 */     return i;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private int positionOf(DotName className)
/* 104:    */   {
/* 105:150 */     Integer i = (Integer)this.classTable.get(className);
/* 106:151 */     if (i == null) {
/* 107:152 */       throw new IllegalStateException("Class not found in class table:" + className);
/* 108:    */     }
/* 109:154 */     return i.intValue();
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void writeClasses(PackedDataOutputStream stream, Index index)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:159 */     Collection<ClassInfo> classes = index.getKnownClasses();
/* 116:160 */     stream.writePackedU32(classes.size());
/* 117:161 */     for (ClassInfo clazz : classes)
/* 118:    */     {
/* 119:162 */       stream.writePackedU32(positionOf(clazz.name()));
/* 120:163 */       stream.writePackedU32(clazz.superName() == null ? 0 : positionOf(clazz.superName()));
/* 121:164 */       stream.writeShort(clazz.flags());
/* 122:165 */       DotName[] interfaces = clazz.interfaces();
/* 123:166 */       stream.writePackedU32(interfaces.length);
/* 124:167 */       for (DotName intf : interfaces) {
/* 125:168 */         stream.writePackedU32(positionOf(intf));
/* 126:    */       }
/* 127:170 */       Set<Map.Entry<DotName, List<AnnotationInstance>>> entrySet = clazz.annotations().entrySet();
/* 128:171 */       stream.writePackedU32(entrySet.size());
/* 129:172 */       for (Map.Entry<DotName, List<AnnotationInstance>> entry : entrySet)
/* 130:    */       {
/* 131:173 */         stream.writePackedU32(positionOf((DotName)entry.getKey()));
/* 132:    */         
/* 133:175 */         List<AnnotationInstance> instances = (List)entry.getValue();
/* 134:176 */         stream.writePackedU32(instances.size());
/* 135:177 */         for (AnnotationInstance instance : instances)
/* 136:    */         {
/* 137:178 */           AnnotationTarget target = instance.target();
/* 138:179 */           if ((target instanceof FieldInfo))
/* 139:    */           {
/* 140:180 */             FieldInfo field = (FieldInfo)target;
/* 141:181 */             stream.writeByte(1);
/* 142:182 */             stream.writePackedU32(positionOf(field.name()));
/* 143:183 */             writeType(stream, field.type());
/* 144:184 */             stream.writeShort(field.flags());
/* 145:    */           }
/* 146:185 */           else if ((target instanceof MethodInfo))
/* 147:    */           {
/* 148:186 */             MethodInfo method = (MethodInfo)target;
/* 149:187 */             stream.writeByte(2);
/* 150:188 */             stream.writePackedU32(positionOf(method.name()));
/* 151:189 */             stream.writePackedU32(method.args().length);
/* 152:190 */             for (int i = 0; i < method.args().length; i++) {
/* 153:191 */               writeType(stream, method.args()[i]);
/* 154:    */             }
/* 155:193 */             writeType(stream, method.returnType());
/* 156:194 */             stream.writeShort(method.flags());
/* 157:    */           }
/* 158:195 */           else if ((target instanceof MethodParameterInfo))
/* 159:    */           {
/* 160:196 */             MethodParameterInfo param = (MethodParameterInfo)target;
/* 161:197 */             MethodInfo method = param.method();
/* 162:198 */             stream.writeByte(3);
/* 163:199 */             stream.writePackedU32(positionOf(method.name()));
/* 164:200 */             stream.writePackedU32(method.args().length);
/* 165:201 */             for (int i = 0; i < method.args().length; i++) {
/* 166:202 */               writeType(stream, method.args()[i]);
/* 167:    */             }
/* 168:204 */             writeType(stream, method.returnType());
/* 169:205 */             stream.writeShort(method.flags());
/* 170:206 */             stream.writePackedU32(param.position());
/* 171:    */           }
/* 172:207 */           else if ((target instanceof ClassInfo))
/* 173:    */           {
/* 174:208 */             stream.writeByte(4);
/* 175:    */           }
/* 176:    */           else
/* 177:    */           {
/* 178:209 */             throw new IllegalStateException("Unknown target");
/* 179:    */           }
/* 180:211 */           Collection<AnnotationValue> values = instance.values();
/* 181:212 */           writeAnnotationValues(stream, values);
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void writeAnnotationValues(PackedDataOutputStream stream, Collection<AnnotationValue> values)
/* 188:    */     throws IOException
/* 189:    */   {
/* 190:219 */     stream.writePackedU32(values.size());
/* 191:220 */     for (AnnotationValue value : values) {
/* 192:221 */       writeAnnotationValue(stream, value);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void writeAnnotationValue(PackedDataOutputStream stream, AnnotationValue value)
/* 197:    */     throws IOException
/* 198:    */   {
/* 199:226 */     stream.writePackedU32(positionOf(value.name()));
/* 200:227 */     if ((value instanceof AnnotationValue.ByteValue))
/* 201:    */     {
/* 202:228 */       stream.writeByte(1);
/* 203:229 */       stream.writeByte(value.asByte() & 0xFF);
/* 204:    */     }
/* 205:230 */     else if ((value instanceof AnnotationValue.ShortValue))
/* 206:    */     {
/* 207:231 */       stream.writeByte(2);
/* 208:232 */       stream.writePackedU32(value.asShort() & 0xFFFF);
/* 209:    */     }
/* 210:233 */     else if ((value instanceof AnnotationValue.IntegerValue))
/* 211:    */     {
/* 212:234 */       stream.writeByte(3);
/* 213:235 */       stream.writePackedU32(value.asInt());
/* 214:    */     }
/* 215:236 */     else if ((value instanceof AnnotationValue.CharacterValue))
/* 216:    */     {
/* 217:237 */       stream.writeByte(4);
/* 218:238 */       stream.writePackedU32(value.asChar());
/* 219:    */     }
/* 220:239 */     else if ((value instanceof AnnotationValue.FloatValue))
/* 221:    */     {
/* 222:240 */       stream.writeByte(5);
/* 223:241 */       stream.writeFloat(value.asFloat());
/* 224:    */     }
/* 225:242 */     else if ((value instanceof AnnotationValue.DoubleValue))
/* 226:    */     {
/* 227:243 */       stream.writeByte(6);
/* 228:244 */       stream.writeDouble(value.asDouble());
/* 229:    */     }
/* 230:245 */     else if ((value instanceof AnnotationValue.LongValue))
/* 231:    */     {
/* 232:246 */       stream.writeByte(7);
/* 233:247 */       stream.writeLong(value.asLong());
/* 234:    */     }
/* 235:248 */     else if ((value instanceof AnnotationValue.BooleanValue))
/* 236:    */     {
/* 237:249 */       stream.writeByte(8);
/* 238:250 */       stream.writeBoolean(value.asBoolean());
/* 239:    */     }
/* 240:251 */     else if ((value instanceof AnnotationValue.StringValue))
/* 241:    */     {
/* 242:252 */       stream.writeByte(9);
/* 243:253 */       stream.writePackedU32(positionOf(value.asString()));
/* 244:    */     }
/* 245:254 */     else if ((value instanceof AnnotationValue.ClassValue))
/* 246:    */     {
/* 247:255 */       stream.writeByte(10);
/* 248:256 */       writeType(stream, value.asClass());
/* 249:    */     }
/* 250:257 */     else if ((value instanceof AnnotationValue.EnumValue))
/* 251:    */     {
/* 252:258 */       stream.writeByte(11);
/* 253:259 */       stream.writePackedU32(positionOf(value.asEnumType()));
/* 254:260 */       stream.writePackedU32(positionOf(value.asEnum()));
/* 255:    */     }
/* 256:261 */     else if ((value instanceof AnnotationValue.ArrayValue))
/* 257:    */     {
/* 258:262 */       AnnotationValue[] array = value.asArray();
/* 259:263 */       int length = array.length;
/* 260:264 */       stream.writeByte(12);
/* 261:265 */       stream.writePackedU32(length);
/* 262:267 */       for (int i = 0; i < length; i++) {
/* 263:268 */         writeAnnotationValue(stream, array[i]);
/* 264:    */       }
/* 265:    */     }
/* 266:270 */     else if ((value instanceof AnnotationValue.NestedAnnotation))
/* 267:    */     {
/* 268:271 */       AnnotationInstance instance = value.asNested();
/* 269:272 */       Collection<AnnotationValue> values = instance.values();
/* 270:    */       
/* 271:274 */       stream.writeByte(13);
/* 272:275 */       stream.writePackedU32(positionOf(instance.name()));
/* 273:276 */       writeAnnotationValues(stream, values);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private void writeType(PackedDataOutputStream stream, Type type)
/* 278:    */     throws IOException
/* 279:    */   {
/* 280:281 */     stream.writeByte(type.kind().ordinal());
/* 281:282 */     stream.writePackedU32(positionOf(type.name()));
/* 282:    */   }
/* 283:    */   
/* 284:    */   private void buildTables(Index index)
/* 285:    */   {
/* 286:286 */     this.pool = new StrongInternPool();
/* 287:287 */     this.classTable = new TreeMap();
/* 288:290 */     for (ClassInfo clazz : index.getKnownClasses())
/* 289:    */     {
/* 290:291 */       addClassName(clazz.name());
/* 291:292 */       if (clazz.superName() != null) {
/* 292:293 */         addClassName(clazz.superName());
/* 293:    */       }
/* 294:295 */       for (DotName intf : clazz.interfaces()) {
/* 295:296 */         addClassName(intf);
/* 296:    */       }
/* 297:298 */       for (Map.Entry<DotName, List<AnnotationInstance>> entry : clazz.annotations().entrySet())
/* 298:    */       {
/* 299:299 */         addClassName((DotName)entry.getKey());
/* 300:301 */         for (AnnotationInstance instance : (List)entry.getValue())
/* 301:    */         {
/* 302:302 */           AnnotationTarget target = instance.target();
/* 303:303 */           if ((target instanceof FieldInfo))
/* 304:    */           {
/* 305:304 */             FieldInfo field = (FieldInfo)target;
/* 306:305 */             intern(field.name());
/* 307:306 */             addClassName(field.type().name());
/* 308:    */           }
/* 309:308 */           else if ((target instanceof MethodInfo))
/* 310:    */           {
/* 311:309 */             MethodInfo method = (MethodInfo)target;
/* 312:310 */             intern(method.name());
/* 313:311 */             for (Type type : method.args()) {
/* 314:312 */               addClassName(type.name());
/* 315:    */             }
/* 316:314 */             addClassName(method.returnType().name());
/* 317:    */           }
/* 318:316 */           else if ((target instanceof MethodParameterInfo))
/* 319:    */           {
/* 320:317 */             MethodParameterInfo param = (MethodParameterInfo)target;
/* 321:318 */             intern(param.method().name());
/* 322:319 */             for (Type type : param.method().args()) {
/* 323:320 */               addClassName(type.name());
/* 324:    */             }
/* 325:322 */             addClassName(param.method().returnType().name());
/* 326:    */           }
/* 327:325 */           for (AnnotationValue value : instance.values()) {
/* 328:326 */             buildAValueEntries(index, value);
/* 329:    */           }
/* 330:    */         }
/* 331:    */       }
/* 332:    */     }
/* 333:333 */     this.poolIndex = this.pool.index();
/* 334:    */   }
/* 335:    */   
/* 336:    */   private void buildAValueEntries(Index index, AnnotationValue value)
/* 337:    */   {
/* 338:337 */     intern(value.name());
/* 339:339 */     if ((value instanceof AnnotationValue.StringValue))
/* 340:    */     {
/* 341:340 */       intern(value.asString());
/* 342:    */     }
/* 343:341 */     else if ((value instanceof AnnotationValue.ClassValue))
/* 344:    */     {
/* 345:342 */       addClassName(value.asClass().name());
/* 346:    */     }
/* 347:343 */     else if ((value instanceof AnnotationValue.EnumValue))
/* 348:    */     {
/* 349:344 */       addClassName(value.asEnumType());
/* 350:345 */       intern(value.asEnum());
/* 351:    */     }
/* 352:346 */     else if ((value instanceof AnnotationValue.ArrayValue))
/* 353:    */     {
/* 354:347 */       for (AnnotationValue entry : value.asArray()) {
/* 355:348 */         buildAValueEntries(index, entry);
/* 356:    */       }
/* 357:    */     }
/* 358:349 */     else if ((value instanceof AnnotationValue.NestedAnnotation))
/* 359:    */     {
/* 360:350 */       AnnotationInstance instance = value.asNested();
/* 361:351 */       Collection<AnnotationValue> values = instance.values();
/* 362:    */       
/* 363:353 */       addClassName(instance.name());
/* 364:354 */       for (AnnotationValue entry : values) {
/* 365:355 */         buildAValueEntries(index, entry);
/* 366:    */       }
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   private String intern(String name)
/* 371:    */   {
/* 372:361 */     return (String)this.pool.intern(name);
/* 373:    */   }
/* 374:    */   
/* 375:    */   private void addClassName(DotName name)
/* 376:    */   {
/* 377:365 */     if (!this.classTable.containsKey(name)) {
/* 378:366 */       this.classTable.put(name, null);
/* 379:    */     }
/* 380:368 */     DotName prefix = name.prefix();
/* 381:369 */     if (prefix != null) {
/* 382:370 */       addClassName(prefix);
/* 383:    */     }
/* 384:    */   }
/* 385:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.IndexWriter
 * JD-Core Version:    0.7.0.1
 */