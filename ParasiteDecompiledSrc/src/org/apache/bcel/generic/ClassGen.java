/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.AbstractCollection;
/*   5:    */ import java.util.AbstractList;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import org.apache.bcel.classfile.AccessFlags;
/*   9:    */ import org.apache.bcel.classfile.Attribute;
/*  10:    */ import org.apache.bcel.classfile.ConstantPool;
/*  11:    */ import org.apache.bcel.classfile.Field;
/*  12:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  13:    */ import org.apache.bcel.classfile.JavaClass;
/*  14:    */ import org.apache.bcel.classfile.Method;
/*  15:    */ import org.apache.bcel.classfile.SourceFile;
/*  16:    */ 
/*  17:    */ public class ClassGen
/*  18:    */   extends AccessFlags
/*  19:    */   implements Cloneable
/*  20:    */ {
/*  21: 74 */   private int class_name_index = -1;
/*  22: 74 */   private int superclass_name_index = -1;
/*  23: 75 */   private int minor = 3;
/*  24: 75 */   private int major = 45;
/*  25: 80 */   private ArrayList field_vec = new ArrayList();
/*  26: 81 */   private ArrayList method_vec = new ArrayList();
/*  27: 82 */   private ArrayList attribute_vec = new ArrayList();
/*  28: 83 */   private ArrayList interface_vec = new ArrayList();
/*  29:    */   private String class_name;
/*  30:    */   private String super_class_name;
/*  31:    */   private String file_name;
/*  32:    */   private ConstantPoolGen cp;
/*  33:    */   private ArrayList observers;
/*  34:    */   
/*  35:    */   public ClassGen(String class_name, String super_class_name, String file_name, int access_flags, String[] interfaces)
/*  36:    */   {
/*  37: 95 */     this.class_name = class_name;
/*  38: 96 */     this.super_class_name = super_class_name;
/*  39: 97 */     this.file_name = file_name;
/*  40: 98 */     this.access_flags = access_flags;
/*  41: 99 */     this.cp = new ConstantPoolGen();
/*  42:    */     
/*  43:    */ 
/*  44:102 */     addAttribute(new SourceFile(this.cp.addUtf8("SourceFile"), 2, this.cp.addUtf8(file_name), this.cp.getConstantPool()));
/*  45:    */     
/*  46:104 */     this.class_name_index = this.cp.addClass(class_name);
/*  47:105 */     this.superclass_name_index = this.cp.addClass(super_class_name);
/*  48:107 */     if (interfaces != null) {
/*  49:108 */       for (int i = 0; i < interfaces.length; i++) {
/*  50:109 */         addInterface(interfaces[i]);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ClassGen(JavaClass clazz)
/*  56:    */   {
/*  57:117 */     this.class_name_index = clazz.getClassNameIndex();
/*  58:118 */     this.superclass_name_index = clazz.getSuperclassNameIndex();
/*  59:119 */     this.class_name = clazz.getClassName();
/*  60:120 */     this.super_class_name = clazz.getSuperclassName();
/*  61:121 */     this.file_name = clazz.getSourceFileName();
/*  62:122 */     this.access_flags = clazz.getAccessFlags();
/*  63:123 */     this.cp = new ConstantPoolGen(clazz.getConstantPool());
/*  64:124 */     this.major = clazz.getMajor();
/*  65:125 */     this.minor = clazz.getMinor();
/*  66:    */     
/*  67:127 */     Attribute[] attributes = clazz.getAttributes();
/*  68:128 */     Method[] methods = clazz.getMethods();
/*  69:129 */     Field[] fields = clazz.getFields();
/*  70:130 */     String[] interfaces = clazz.getInterfaceNames();
/*  71:132 */     for (int i = 0; i < interfaces.length; i++) {
/*  72:133 */       addInterface(interfaces[i]);
/*  73:    */     }
/*  74:135 */     for (int i = 0; i < attributes.length; i++) {
/*  75:136 */       addAttribute(attributes[i]);
/*  76:    */     }
/*  77:138 */     for (int i = 0; i < methods.length; i++) {
/*  78:139 */       addMethod(methods[i]);
/*  79:    */     }
/*  80:141 */     for (int i = 0; i < fields.length; i++) {
/*  81:142 */       addField(fields[i]);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public JavaClass getJavaClass()
/*  86:    */   {
/*  87:149 */     int[] interfaces = getInterfaces();
/*  88:150 */     Field[] fields = getFields();
/*  89:151 */     Method[] methods = getMethods();
/*  90:152 */     Attribute[] attributes = getAttributes();
/*  91:    */     
/*  92:    */ 
/*  93:155 */     ConstantPool cp = this.cp.getFinalConstantPool();
/*  94:    */     
/*  95:157 */     return new JavaClass(this.class_name_index, this.superclass_name_index, this.file_name, this.major, this.minor, this.access_flags, cp, interfaces, fields, methods, attributes);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void addInterface(String name)
/*  99:    */   {
/* 100:167 */     this.interface_vec.add(name);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void removeInterface(String name)
/* 104:    */   {
/* 105:175 */     this.interface_vec.remove(name);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getMajor()
/* 109:    */   {
/* 110:181 */     return this.major;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setMajor(int major)
/* 114:    */   {
/* 115:187 */     this.major = major;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMinor(int minor)
/* 119:    */   {
/* 120:194 */     this.minor = minor;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getMinor()
/* 124:    */   {
/* 125:200 */     return this.minor;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void addAttribute(Attribute a)
/* 129:    */   {
/* 130:206 */     this.attribute_vec.add(a);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void addMethod(Method m)
/* 134:    */   {
/* 135:212 */     this.method_vec.add(m);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void addEmptyConstructor(int access_flags)
/* 139:    */   {
/* 140:221 */     InstructionList il = new InstructionList();
/* 141:222 */     il.append(InstructionConstants.THIS);
/* 142:223 */     il.append(new INVOKESPECIAL(this.cp.addMethodref(this.super_class_name, "<init>", "()V")));
/* 143:    */     
/* 144:225 */     il.append(InstructionConstants.RETURN);
/* 145:    */     
/* 146:227 */     MethodGen mg = new MethodGen(access_flags, Type.VOID, Type.NO_ARGS, null, "<init>", this.class_name, il, this.cp);
/* 147:    */     
/* 148:229 */     mg.setMaxStack(1);
/* 149:230 */     addMethod(mg.getMethod());
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void addField(Field f)
/* 153:    */   {
/* 154:237 */     this.field_vec.add(f);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean containsField(Field f)
/* 158:    */   {
/* 159:239 */     return this.field_vec.contains(f);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Field containsField(String name)
/* 163:    */   {
/* 164:244 */     for (Iterator e = this.field_vec.iterator(); e.hasNext();)
/* 165:    */     {
/* 166:245 */       Field f = (Field)e.next();
/* 167:246 */       if (f.getName().equals(name)) {
/* 168:247 */         return f;
/* 169:    */       }
/* 170:    */     }
/* 171:250 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Method containsMethod(String name, String signature)
/* 175:    */   {
/* 176:256 */     for (Iterator e = this.method_vec.iterator(); e.hasNext();)
/* 177:    */     {
/* 178:257 */       Method m = (Method)e.next();
/* 179:258 */       if ((m.getName().equals(name)) && (m.getSignature().equals(signature))) {
/* 180:259 */         return m;
/* 181:    */       }
/* 182:    */     }
/* 183:262 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void removeAttribute(Attribute a)
/* 187:    */   {
/* 188:269 */     this.attribute_vec.remove(a);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void removeMethod(Method m)
/* 192:    */   {
/* 193:275 */     this.method_vec.remove(m);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void replaceMethod(Method old, Method new_)
/* 197:    */   {
/* 198:281 */     if (new_ == null) {
/* 199:282 */       throw new ClassGenException("Replacement method must not be null");
/* 200:    */     }
/* 201:284 */     int i = this.method_vec.indexOf(old);
/* 202:286 */     if (i < 0) {
/* 203:287 */       this.method_vec.add(new_);
/* 204:    */     } else {
/* 205:289 */       this.method_vec.set(i, new_);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void replaceField(Field old, Field new_)
/* 210:    */   {
/* 211:296 */     if (new_ == null) {
/* 212:297 */       throw new ClassGenException("Replacement method must not be null");
/* 213:    */     }
/* 214:299 */     int i = this.field_vec.indexOf(old);
/* 215:301 */     if (i < 0) {
/* 216:302 */       this.field_vec.add(new_);
/* 217:    */     } else {
/* 218:304 */       this.field_vec.set(i, new_);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void removeField(Field f)
/* 223:    */   {
/* 224:311 */     this.field_vec.remove(f);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getClassName()
/* 228:    */   {
/* 229:313 */     return this.class_name;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getSuperclassName()
/* 233:    */   {
/* 234:314 */     return this.super_class_name;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getFileName()
/* 238:    */   {
/* 239:315 */     return this.file_name;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setClassName(String name)
/* 243:    */   {
/* 244:318 */     this.class_name = name.replace('/', '.');
/* 245:319 */     this.class_name_index = this.cp.addClass(name);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setSuperclassName(String name)
/* 249:    */   {
/* 250:323 */     this.super_class_name = name.replace('/', '.');
/* 251:324 */     this.superclass_name_index = this.cp.addClass(name);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Method[] getMethods()
/* 255:    */   {
/* 256:328 */     Method[] methods = new Method[this.method_vec.size()];
/* 257:329 */     this.method_vec.toArray(methods);
/* 258:330 */     return methods;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setMethods(Method[] methods)
/* 262:    */   {
/* 263:334 */     this.method_vec.clear();
/* 264:335 */     for (int m = 0; m < methods.length; m++) {
/* 265:336 */       addMethod(methods[m]);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setMethodAt(Method method, int pos)
/* 270:    */   {
/* 271:340 */     this.method_vec.set(pos, method);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public Method getMethodAt(int pos)
/* 275:    */   {
/* 276:344 */     return (Method)this.method_vec.get(pos);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String[] getInterfaceNames()
/* 280:    */   {
/* 281:348 */     int size = this.interface_vec.size();
/* 282:349 */     String[] interfaces = new String[size];
/* 283:    */     
/* 284:351 */     this.interface_vec.toArray(interfaces);
/* 285:352 */     return interfaces;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public int[] getInterfaces()
/* 289:    */   {
/* 290:356 */     int size = this.interface_vec.size();
/* 291:357 */     int[] interfaces = new int[size];
/* 292:359 */     for (int i = 0; i < size; i++) {
/* 293:360 */       interfaces[i] = this.cp.addClass((String)this.interface_vec.get(i));
/* 294:    */     }
/* 295:362 */     return interfaces;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Field[] getFields()
/* 299:    */   {
/* 300:366 */     Field[] fields = new Field[this.field_vec.size()];
/* 301:367 */     this.field_vec.toArray(fields);
/* 302:368 */     return fields;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public Attribute[] getAttributes()
/* 306:    */   {
/* 307:372 */     Attribute[] attributes = new Attribute[this.attribute_vec.size()];
/* 308:373 */     this.attribute_vec.toArray(attributes);
/* 309:374 */     return attributes;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public ConstantPoolGen getConstantPool()
/* 313:    */   {
/* 314:377 */     return this.cp;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setConstantPool(ConstantPoolGen constant_pool)
/* 318:    */   {
/* 319:379 */     this.cp = constant_pool;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setClassNameIndex(int class_name_index)
/* 323:    */   {
/* 324:383 */     this.class_name_index = class_name_index;
/* 325:384 */     this.class_name = this.cp.getConstantPool().getConstantString(class_name_index, (byte)7).replace('/', '.');
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setSuperclassNameIndex(int superclass_name_index)
/* 329:    */   {
/* 330:389 */     this.superclass_name_index = superclass_name_index;
/* 331:390 */     this.super_class_name = this.cp.getConstantPool().getConstantString(superclass_name_index, (byte)7).replace('/', '.');
/* 332:    */   }
/* 333:    */   
/* 334:    */   public int getSuperclassNameIndex()
/* 335:    */   {
/* 336:394 */     return this.superclass_name_index;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public int getClassNameIndex()
/* 340:    */   {
/* 341:396 */     return this.class_name_index;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void addObserver(ClassObserver o)
/* 345:    */   {
/* 346:403 */     if (this.observers == null) {
/* 347:404 */       this.observers = new ArrayList();
/* 348:    */     }
/* 349:406 */     this.observers.add(o);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void removeObserver(ClassObserver o)
/* 353:    */   {
/* 354:412 */     if (this.observers != null) {
/* 355:413 */       this.observers.remove(o);
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void update()
/* 360:    */   {
/* 361:421 */     if (this.observers != null) {
/* 362:422 */       for (Iterator e = this.observers.iterator(); e.hasNext();) {
/* 363:423 */         ((ClassObserver)e.next()).notify(this);
/* 364:    */       }
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public Object clone()
/* 369:    */   {
/* 370:    */     try
/* 371:    */     {
/* 372:428 */       return super.clone();
/* 373:    */     }
/* 374:    */     catch (CloneNotSupportedException e)
/* 375:    */     {
/* 376:430 */       System.err.println(e);
/* 377:    */     }
/* 378:431 */     return null;
/* 379:    */   }
/* 380:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ClassGen
 * JD-Core Version:    0.7.0.1
 */