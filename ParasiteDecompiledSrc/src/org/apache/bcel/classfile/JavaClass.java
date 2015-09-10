/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.FilterOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.StringTokenizer;
/*  12:    */ import org.apache.bcel.Repository;
/*  13:    */ 
/*  14:    */ public class JavaClass
/*  15:    */   extends AccessFlags
/*  16:    */   implements Cloneable, Node
/*  17:    */ {
/*  18:    */   private String file_name;
/*  19:    */   private String package_name;
/*  20: 74 */   private String source_file_name = "<Unknown>";
/*  21:    */   private int class_name_index;
/*  22:    */   private int superclass_name_index;
/*  23:    */   private String class_name;
/*  24:    */   private String superclass_name;
/*  25:    */   private int major;
/*  26:    */   private int minor;
/*  27:    */   private ConstantPool constant_pool;
/*  28:    */   private int[] interfaces;
/*  29:    */   private String[] interface_names;
/*  30:    */   private Field[] fields;
/*  31:    */   private Method[] methods;
/*  32:    */   private Attribute[] attributes;
/*  33: 86 */   private byte source = 1;
/*  34:    */   public static final byte HEAP = 1;
/*  35:    */   public static final byte FILE = 2;
/*  36:    */   public static final byte ZIP = 3;
/*  37: 92 */   static boolean debug = false;
/*  38: 93 */   static char sep = '/';
/*  39:    */   
/*  40:    */   public JavaClass(int class_name_index, int superclass_name_index, String file_name, int major, int minor, int access_flags, ConstantPool constant_pool, int[] interfaces, Field[] fields, Method[] methods, Attribute[] attributes, byte source)
/*  41:    */   {
/*  42:124 */     if (interfaces == null) {
/*  43:125 */       interfaces = new int[0];
/*  44:    */     }
/*  45:126 */     if (attributes == null) {
/*  46:127 */       this.attributes = new Attribute[0];
/*  47:    */     }
/*  48:128 */     if (fields == null) {
/*  49:129 */       fields = new Field[0];
/*  50:    */     }
/*  51:130 */     if (methods == null) {
/*  52:131 */       methods = new Method[0];
/*  53:    */     }
/*  54:133 */     this.class_name_index = class_name_index;
/*  55:134 */     this.superclass_name_index = superclass_name_index;
/*  56:135 */     this.file_name = file_name;
/*  57:136 */     this.major = major;
/*  58:137 */     this.minor = minor;
/*  59:138 */     this.access_flags = access_flags;
/*  60:139 */     this.constant_pool = constant_pool;
/*  61:140 */     this.interfaces = interfaces;
/*  62:141 */     this.fields = fields;
/*  63:142 */     this.methods = methods;
/*  64:143 */     this.attributes = attributes;
/*  65:144 */     this.source = source;
/*  66:147 */     for (int i = 0; i < attributes.length; i++) {
/*  67:148 */       if ((attributes[i] instanceof SourceFile))
/*  68:    */       {
/*  69:149 */         this.source_file_name = ((SourceFile)attributes[i]).getSourceFileName();
/*  70:150 */         break;
/*  71:    */       }
/*  72:    */     }
/*  73:161 */     this.class_name = constant_pool.getConstantString(class_name_index, (byte)7);
/*  74:    */     
/*  75:163 */     this.class_name = Utility.compactClassName(this.class_name, false);
/*  76:    */     
/*  77:165 */     int index = this.class_name.lastIndexOf('.');
/*  78:166 */     if (index < 0) {
/*  79:167 */       this.package_name = "";
/*  80:    */     } else {
/*  81:169 */       this.package_name = this.class_name.substring(0, index);
/*  82:    */     }
/*  83:171 */     if (superclass_name_index > 0)
/*  84:    */     {
/*  85:172 */       this.superclass_name = constant_pool.getConstantString(superclass_name_index, (byte)7);
/*  86:    */       
/*  87:174 */       this.superclass_name = Utility.compactClassName(this.superclass_name, false);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:177 */       this.superclass_name = "java.lang.Object";
/*  92:    */     }
/*  93:179 */     this.interface_names = new String[interfaces.length];
/*  94:180 */     for (int i = 0; i < interfaces.length; i++)
/*  95:    */     {
/*  96:181 */       String str = constant_pool.getConstantString(interfaces[i], (byte)7);
/*  97:182 */       this.interface_names[i] = Utility.compactClassName(str, false);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JavaClass(int class_name_index, int superclass_name_index, String file_name, int major, int minor, int access_flags, ConstantPool constant_pool, int[] interfaces, Field[] fields, Method[] methods, Attribute[] attributes)
/* 102:    */   {
/* 103:212 */     this(class_name_index, superclass_name_index, file_name, major, minor, access_flags, constant_pool, interfaces, fields, methods, attributes, (byte)1);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void accept(Visitor v)
/* 107:    */   {
/* 108:225 */     v.visitJavaClass(this);
/* 109:    */   }
/* 110:    */   
/* 111:    */   static final void Debug(String str)
/* 112:    */   {
/* 113:231 */     if (debug) {
/* 114:232 */       System.out.println(str);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void dump(File file)
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:243 */     String parent = file.getParent();
/* 122:245 */     if (parent != null)
/* 123:    */     {
/* 124:246 */       File dir = new File(parent);
/* 125:248 */       if (dir != null) {
/* 126:249 */         dir.mkdirs();
/* 127:    */       }
/* 128:    */     }
/* 129:252 */     dump(new DataOutputStream(new FileOutputStream(file)));
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void dump(String file_name)
/* 133:    */     throws IOException
/* 134:    */   {
/* 135:263 */     dump(new File(file_name));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public byte[] getBytes()
/* 139:    */   {
/* 140:270 */     ByteArrayOutputStream s = new ByteArrayOutputStream();
/* 141:271 */     DataOutputStream ds = new DataOutputStream(s);
/* 142:    */     try
/* 143:    */     {
/* 144:274 */       dump(ds);
/* 145:275 */       ds.close();
/* 146:    */     }
/* 147:    */     catch (IOException e)
/* 148:    */     {
/* 149:276 */       e.printStackTrace();
/* 150:    */     }
/* 151:278 */     return s.toByteArray();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void dump(OutputStream file)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:288 */     dump(new DataOutputStream(file));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void dump(DataOutputStream file)
/* 161:    */     throws IOException
/* 162:    */   {
/* 163:299 */     file.writeInt(-889275714);
/* 164:300 */     file.writeShort(this.minor);
/* 165:301 */     file.writeShort(this.major);
/* 166:    */     
/* 167:303 */     this.constant_pool.dump(file);
/* 168:    */     
/* 169:305 */     file.writeShort(this.access_flags);
/* 170:306 */     file.writeShort(this.class_name_index);
/* 171:307 */     file.writeShort(this.superclass_name_index);
/* 172:    */     
/* 173:309 */     file.writeShort(this.interfaces.length);
/* 174:310 */     for (int i = 0; i < this.interfaces.length; i++) {
/* 175:311 */       file.writeShort(this.interfaces[i]);
/* 176:    */     }
/* 177:313 */     file.writeShort(this.fields.length);
/* 178:314 */     for (int i = 0; i < this.fields.length; i++) {
/* 179:315 */       this.fields[i].dump(file);
/* 180:    */     }
/* 181:317 */     file.writeShort(this.methods.length);
/* 182:318 */     for (int i = 0; i < this.methods.length; i++) {
/* 183:319 */       this.methods[i].dump(file);
/* 184:    */     }
/* 185:321 */     if (this.attributes != null)
/* 186:    */     {
/* 187:322 */       file.writeShort(this.attributes.length);
/* 188:323 */       for (int i = 0; i < this.attributes.length; i++) {
/* 189:324 */         this.attributes[i].dump(file);
/* 190:    */       }
/* 191:    */     }
/* 192:    */     else
/* 193:    */     {
/* 194:327 */       file.writeShort(0);
/* 195:    */     }
/* 196:329 */     file.close();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Attribute[] getAttributes()
/* 200:    */   {
/* 201:335 */     return this.attributes;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getClassName()
/* 205:    */   {
/* 206:340 */     return this.class_name;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String getPackageName()
/* 210:    */   {
/* 211:345 */     return this.package_name;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int getClassNameIndex()
/* 215:    */   {
/* 216:350 */     return this.class_name_index;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public ConstantPool getConstantPool()
/* 220:    */   {
/* 221:355 */     return this.constant_pool;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Field[] getFields()
/* 225:    */   {
/* 226:360 */     return this.fields;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String getFileName()
/* 230:    */   {
/* 231:364 */     return this.file_name;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String[] getInterfaceNames()
/* 235:    */   {
/* 236:368 */     return this.interface_names;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int[] getInterfaces()
/* 240:    */   {
/* 241:372 */     return this.interfaces;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public int getMajor()
/* 245:    */   {
/* 246:376 */     return this.major;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Method[] getMethods()
/* 250:    */   {
/* 251:380 */     return this.methods;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public int getMinor()
/* 255:    */   {
/* 256:384 */     return this.minor;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public String getSourceFileName()
/* 260:    */   {
/* 261:389 */     return this.source_file_name;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getSuperclassName()
/* 265:    */   {
/* 266:394 */     return this.superclass_name;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int getSuperclassNameIndex()
/* 270:    */   {
/* 271:398 */     return this.superclass_name_index;
/* 272:    */   }
/* 273:    */   
/* 274:    */   static
/* 275:    */   {
/* 276:402 */     String debug = System.getProperty("JavaClass.debug");
/* 277:404 */     if (debug != null) {
/* 278:405 */       debug = new Boolean(debug).booleanValue();
/* 279:    */     }
/* 280:408 */     String sep = System.getProperty("file.separator");
/* 281:410 */     if (sep != null) {
/* 282:    */       try
/* 283:    */       {
/* 284:412 */         sep = sep.charAt(0);
/* 285:    */       }
/* 286:    */       catch (StringIndexOutOfBoundsException e) {}
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setAttributes(Attribute[] attributes)
/* 291:    */   {
/* 292:420 */     this.attributes = attributes;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setClassName(String class_name)
/* 296:    */   {
/* 297:426 */     this.class_name = class_name;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setClassNameIndex(int class_name_index)
/* 301:    */   {
/* 302:432 */     this.class_name_index = class_name_index;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setConstantPool(ConstantPool constant_pool)
/* 306:    */   {
/* 307:438 */     this.constant_pool = constant_pool;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setFields(Field[] fields)
/* 311:    */   {
/* 312:444 */     this.fields = fields;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setFileName(String file_name)
/* 316:    */   {
/* 317:450 */     this.file_name = file_name;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setInterfaceNames(String[] interface_names)
/* 321:    */   {
/* 322:456 */     this.interface_names = interface_names;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setInterfaces(int[] interfaces)
/* 326:    */   {
/* 327:462 */     this.interfaces = interfaces;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void setMajor(int major)
/* 331:    */   {
/* 332:468 */     this.major = major;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setMethods(Method[] methods)
/* 336:    */   {
/* 337:474 */     this.methods = methods;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void setMinor(int minor)
/* 341:    */   {
/* 342:480 */     this.minor = minor;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setSourceFileName(String source_file_name)
/* 346:    */   {
/* 347:486 */     this.source_file_name = source_file_name;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void setSuperclassName(String superclass_name)
/* 351:    */   {
/* 352:492 */     this.superclass_name = superclass_name;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setSuperclassNameIndex(int superclass_name_index)
/* 356:    */   {
/* 357:498 */     this.superclass_name_index = superclass_name_index;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public String toString()
/* 361:    */   {
/* 362:504 */     String access = Utility.accessToString(this.access_flags, true);
/* 363:505 */     access = access + " ";
/* 364:    */     
/* 365:507 */     StringBuffer buf = new StringBuffer(access + Utility.classOrInterface(this.access_flags) + " " + this.class_name + " extends " + Utility.compactClassName(this.superclass_name, false) + '\n');
/* 366:    */     
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:513 */     int size = this.interfaces.length;
/* 372:515 */     if (size > 0)
/* 373:    */     {
/* 374:516 */       buf.append("implements\t\t");
/* 375:518 */       for (int i = 0; i < size; i++)
/* 376:    */       {
/* 377:519 */         buf.append(this.interface_names[i]);
/* 378:520 */         if (i < size - 1) {
/* 379:521 */           buf.append(", ");
/* 380:    */         }
/* 381:    */       }
/* 382:524 */       buf.append('\n');
/* 383:    */     }
/* 384:527 */     buf.append("filename\t\t" + this.file_name + '\n');
/* 385:528 */     buf.append("compiled from\t\t" + this.source_file_name + '\n');
/* 386:529 */     buf.append("compiler version\t" + this.major + "." + this.minor + '\n');
/* 387:530 */     buf.append("access flags\t\t" + this.access_flags + '\n');
/* 388:531 */     buf.append("constant pool\t\t" + this.constant_pool.getLength() + " entries\n");
/* 389:532 */     buf.append("ACC_SUPER flag\t\t" + isSuper() + "\n");
/* 390:534 */     if (this.attributes.length > 0)
/* 391:    */     {
/* 392:535 */       buf.append("\nAttribute(s):\n");
/* 393:536 */       for (int i = 0; i < this.attributes.length; i++) {
/* 394:537 */         buf.append(indent(this.attributes[i]));
/* 395:    */       }
/* 396:    */     }
/* 397:540 */     if (this.fields.length > 0)
/* 398:    */     {
/* 399:541 */       buf.append("\n" + this.fields.length + " fields:\n");
/* 400:542 */       for (int i = 0; i < this.fields.length; i++) {
/* 401:543 */         buf.append("\t" + this.fields[i] + '\n');
/* 402:    */       }
/* 403:    */     }
/* 404:546 */     if (this.methods.length > 0)
/* 405:    */     {
/* 406:547 */       buf.append("\n" + this.methods.length + " methods:\n");
/* 407:548 */       for (int i = 0; i < this.methods.length; i++) {
/* 408:549 */         buf.append("\t" + this.methods[i] + '\n');
/* 409:    */       }
/* 410:    */     }
/* 411:552 */     return buf.toString();
/* 412:    */   }
/* 413:    */   
/* 414:    */   private static final String indent(Object obj)
/* 415:    */   {
/* 416:556 */     StringTokenizer tok = new StringTokenizer(obj.toString(), "\n");
/* 417:557 */     StringBuffer buf = new StringBuffer();
/* 418:559 */     while (tok.hasMoreTokens()) {
/* 419:560 */       buf.append("\t" + tok.nextToken() + "\n");
/* 420:    */     }
/* 421:562 */     return buf.toString();
/* 422:    */   }
/* 423:    */   
/* 424:    */   public JavaClass copy()
/* 425:    */   {
/* 426:569 */     JavaClass c = null;
/* 427:    */     try
/* 428:    */     {
/* 429:572 */       c = (JavaClass)clone();
/* 430:    */     }
/* 431:    */     catch (CloneNotSupportedException e) {}
/* 432:575 */     c.constant_pool = this.constant_pool.copy();
/* 433:576 */     c.interfaces = ((int[])this.interfaces.clone());
/* 434:577 */     c.interface_names = ((String[])this.interface_names.clone());
/* 435:    */     
/* 436:579 */     c.fields = new Field[this.fields.length];
/* 437:580 */     for (int i = 0; i < this.fields.length; i++) {
/* 438:581 */       c.fields[i] = this.fields[i].copy(c.constant_pool);
/* 439:    */     }
/* 440:583 */     c.methods = new Method[this.methods.length];
/* 441:584 */     for (int i = 0; i < this.methods.length; i++) {
/* 442:585 */       c.methods[i] = this.methods[i].copy(c.constant_pool);
/* 443:    */     }
/* 444:587 */     c.attributes = new Attribute[this.attributes.length];
/* 445:588 */     for (int i = 0; i < this.attributes.length; i++) {
/* 446:589 */       c.attributes[i] = this.attributes[i].copy(c.constant_pool);
/* 447:    */     }
/* 448:591 */     return c;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public final boolean instanceOf(JavaClass super_class)
/* 452:    */   {
/* 453:595 */     return Repository.instanceOf(this, super_class);
/* 454:    */   }
/* 455:    */   
/* 456:    */   public final boolean isSuper()
/* 457:    */   {
/* 458:599 */     return (this.access_flags & 0x20) != 0;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public final boolean isClass()
/* 462:    */   {
/* 463:603 */     return (this.access_flags & 0x200) == 0;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public final byte getSource()
/* 467:    */   {
/* 468:609 */     return this.source;
/* 469:    */   }
/* 470:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.JavaClass
 * JD-Core Version:    0.7.0.1
 */