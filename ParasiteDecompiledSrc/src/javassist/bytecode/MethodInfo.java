/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javassist.ClassPool;
/*  10:    */ import javassist.bytecode.stackmap.MapMaker;
/*  11:    */ 
/*  12:    */ public class MethodInfo
/*  13:    */ {
/*  14:    */   ConstPool constPool;
/*  15:    */   int accessFlags;
/*  16:    */   int name;
/*  17:    */   String cachedName;
/*  18:    */   int descriptor;
/*  19:    */   ArrayList attribute;
/*  20: 46 */   public static boolean doPreverify = false;
/*  21:    */   public static final String nameInit = "<init>";
/*  22:    */   public static final String nameClinit = "<clinit>";
/*  23:    */   
/*  24:    */   private MethodInfo(ConstPool cp)
/*  25:    */   {
/*  26: 60 */     this.constPool = cp;
/*  27: 61 */     this.attribute = null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public MethodInfo(ConstPool cp, String methodname, String desc)
/*  31:    */   {
/*  32: 77 */     this(cp);
/*  33: 78 */     this.accessFlags = 0;
/*  34: 79 */     this.name = cp.addUtf8Info(methodname);
/*  35: 80 */     this.cachedName = methodname;
/*  36: 81 */     this.descriptor = this.constPool.addUtf8Info(desc);
/*  37:    */   }
/*  38:    */   
/*  39:    */   MethodInfo(ConstPool cp, DataInputStream in)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 85 */     this(cp);
/*  43: 86 */     read(in);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public MethodInfo(ConstPool cp, String methodname, MethodInfo src, Map classnameMap)
/*  47:    */     throws BadBytecode
/*  48:    */   {
/*  49:110 */     this(cp);
/*  50:111 */     read(src, methodname, classnameMap);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55:118 */     return getName() + " " + getDescriptor();
/*  56:    */   }
/*  57:    */   
/*  58:    */   void compact(ConstPool cp)
/*  59:    */   {
/*  60:130 */     this.name = cp.addUtf8Info(getName());
/*  61:131 */     this.descriptor = cp.addUtf8Info(getDescriptor());
/*  62:132 */     this.attribute = AttributeInfo.copyAll(this.attribute, cp);
/*  63:133 */     this.constPool = cp;
/*  64:    */   }
/*  65:    */   
/*  66:    */   void prune(ConstPool cp)
/*  67:    */   {
/*  68:137 */     ArrayList newAttributes = new ArrayList();
/*  69:    */     
/*  70:139 */     AttributeInfo invisibleAnnotations = getAttribute("RuntimeInvisibleAnnotations");
/*  71:141 */     if (invisibleAnnotations != null)
/*  72:    */     {
/*  73:142 */       invisibleAnnotations = invisibleAnnotations.copy(cp, null);
/*  74:143 */       newAttributes.add(invisibleAnnotations);
/*  75:    */     }
/*  76:146 */     AttributeInfo visibleAnnotations = getAttribute("RuntimeVisibleAnnotations");
/*  77:148 */     if (visibleAnnotations != null)
/*  78:    */     {
/*  79:149 */       visibleAnnotations = visibleAnnotations.copy(cp, null);
/*  80:150 */       newAttributes.add(visibleAnnotations);
/*  81:    */     }
/*  82:153 */     AttributeInfo parameterInvisibleAnnotations = getAttribute("RuntimeInvisibleParameterAnnotations");
/*  83:155 */     if (parameterInvisibleAnnotations != null)
/*  84:    */     {
/*  85:156 */       parameterInvisibleAnnotations = parameterInvisibleAnnotations.copy(cp, null);
/*  86:157 */       newAttributes.add(parameterInvisibleAnnotations);
/*  87:    */     }
/*  88:160 */     AttributeInfo parameterVisibleAnnotations = getAttribute("RuntimeVisibleParameterAnnotations");
/*  89:162 */     if (parameterVisibleAnnotations != null)
/*  90:    */     {
/*  91:163 */       parameterVisibleAnnotations = parameterVisibleAnnotations.copy(cp, null);
/*  92:164 */       newAttributes.add(parameterVisibleAnnotations);
/*  93:    */     }
/*  94:167 */     AnnotationDefaultAttribute defaultAttribute = (AnnotationDefaultAttribute)getAttribute("AnnotationDefault");
/*  95:169 */     if (defaultAttribute != null) {
/*  96:170 */       newAttributes.add(defaultAttribute);
/*  97:    */     }
/*  98:172 */     ExceptionsAttribute ea = getExceptionsAttribute();
/*  99:173 */     if (ea != null) {
/* 100:174 */       newAttributes.add(ea);
/* 101:    */     }
/* 102:176 */     AttributeInfo signature = getAttribute("Signature");
/* 103:178 */     if (signature != null)
/* 104:    */     {
/* 105:179 */       signature = signature.copy(cp, null);
/* 106:180 */       newAttributes.add(signature);
/* 107:    */     }
/* 108:183 */     this.attribute = newAttributes;
/* 109:184 */     this.name = cp.addUtf8Info(getName());
/* 110:185 */     this.descriptor = cp.addUtf8Info(getDescriptor());
/* 111:186 */     this.constPool = cp;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getName()
/* 115:    */   {
/* 116:193 */     if (this.cachedName == null) {
/* 117:194 */       this.cachedName = this.constPool.getUtf8Info(this.name);
/* 118:    */     }
/* 119:196 */     return this.cachedName;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setName(String newName)
/* 123:    */   {
/* 124:203 */     this.name = this.constPool.addUtf8Info(newName);
/* 125:204 */     this.cachedName = newName;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isMethod()
/* 129:    */   {
/* 130:212 */     String n = getName();
/* 131:213 */     return (!n.equals("<init>")) && (!n.equals("<clinit>"));
/* 132:    */   }
/* 133:    */   
/* 134:    */   public ConstPool getConstPool()
/* 135:    */   {
/* 136:220 */     return this.constPool;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isConstructor()
/* 140:    */   {
/* 141:227 */     return getName().equals("<init>");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean isStaticInitializer()
/* 145:    */   {
/* 146:234 */     return getName().equals("<clinit>");
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getAccessFlags()
/* 150:    */   {
/* 151:243 */     return this.accessFlags;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setAccessFlags(int acc)
/* 155:    */   {
/* 156:252 */     this.accessFlags = acc;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getDescriptor()
/* 160:    */   {
/* 161:261 */     return this.constPool.getUtf8Info(this.descriptor);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDescriptor(String desc)
/* 165:    */   {
/* 166:270 */     if (!desc.equals(getDescriptor())) {
/* 167:271 */       this.descriptor = this.constPool.addUtf8Info(desc);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List getAttributes()
/* 172:    */   {
/* 173:285 */     if (this.attribute == null) {
/* 174:286 */       this.attribute = new ArrayList();
/* 175:    */     }
/* 176:288 */     return this.attribute;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public AttributeInfo getAttribute(String name)
/* 180:    */   {
/* 181:300 */     return AttributeInfo.lookup(this.attribute, name);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void addAttribute(AttributeInfo info)
/* 185:    */   {
/* 186:310 */     if (this.attribute == null) {
/* 187:311 */       this.attribute = new ArrayList();
/* 188:    */     }
/* 189:313 */     AttributeInfo.remove(this.attribute, info.getName());
/* 190:314 */     this.attribute.add(info);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public ExceptionsAttribute getExceptionsAttribute()
/* 194:    */   {
/* 195:323 */     AttributeInfo info = AttributeInfo.lookup(this.attribute, "Exceptions");
/* 196:    */     
/* 197:325 */     return (ExceptionsAttribute)info;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public CodeAttribute getCodeAttribute()
/* 201:    */   {
/* 202:334 */     AttributeInfo info = AttributeInfo.lookup(this.attribute, "Code");
/* 203:335 */     return (CodeAttribute)info;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void removeExceptionsAttribute()
/* 207:    */   {
/* 208:342 */     AttributeInfo.remove(this.attribute, "Exceptions");
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setExceptionsAttribute(ExceptionsAttribute cattr)
/* 212:    */   {
/* 213:353 */     removeExceptionsAttribute();
/* 214:354 */     if (this.attribute == null) {
/* 215:355 */       this.attribute = new ArrayList();
/* 216:    */     }
/* 217:357 */     this.attribute.add(cattr);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void removeCodeAttribute()
/* 221:    */   {
/* 222:364 */     AttributeInfo.remove(this.attribute, "Code");
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setCodeAttribute(CodeAttribute cattr)
/* 226:    */   {
/* 227:375 */     removeCodeAttribute();
/* 228:376 */     if (this.attribute == null) {
/* 229:377 */       this.attribute = new ArrayList();
/* 230:    */     }
/* 231:379 */     this.attribute.add(cattr);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void rebuildStackMapIf6(ClassPool pool, ClassFile cf)
/* 235:    */     throws BadBytecode
/* 236:    */   {
/* 237:397 */     if (cf.getMajorVersion() >= 50) {
/* 238:398 */       rebuildStackMap(pool);
/* 239:    */     }
/* 240:400 */     if (doPreverify) {
/* 241:401 */       rebuildStackMapForME(pool);
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void rebuildStackMap(ClassPool pool)
/* 246:    */     throws BadBytecode
/* 247:    */   {
/* 248:414 */     CodeAttribute ca = getCodeAttribute();
/* 249:415 */     if (ca != null)
/* 250:    */     {
/* 251:416 */       StackMapTable smt = MapMaker.make(pool, this);
/* 252:417 */       ca.setAttribute(smt);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void rebuildStackMapForME(ClassPool pool)
/* 257:    */     throws BadBytecode
/* 258:    */   {
/* 259:431 */     CodeAttribute ca = getCodeAttribute();
/* 260:432 */     if (ca != null)
/* 261:    */     {
/* 262:433 */       StackMap sm = MapMaker.make2(pool, this);
/* 263:434 */       ca.setAttribute(sm);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public int getLineNumber(int pos)
/* 268:    */   {
/* 269:448 */     CodeAttribute ca = getCodeAttribute();
/* 270:449 */     if (ca == null) {
/* 271:450 */       return -1;
/* 272:    */     }
/* 273:452 */     LineNumberAttribute ainfo = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
/* 274:454 */     if (ainfo == null) {
/* 275:455 */       return -1;
/* 276:    */     }
/* 277:457 */     return ainfo.toLineNumber(pos);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setSuperclass(String superclass)
/* 281:    */     throws BadBytecode
/* 282:    */   {
/* 283:482 */     if (!isConstructor()) {
/* 284:483 */       return;
/* 285:    */     }
/* 286:485 */     CodeAttribute ca = getCodeAttribute();
/* 287:486 */     byte[] code = ca.getCode();
/* 288:487 */     CodeIterator iterator = ca.iterator();
/* 289:488 */     int pos = iterator.skipSuperConstructor();
/* 290:489 */     if (pos >= 0)
/* 291:    */     {
/* 292:490 */       ConstPool cp = this.constPool;
/* 293:491 */       int mref = ByteArray.readU16bit(code, pos + 1);
/* 294:492 */       int nt = cp.getMethodrefNameAndType(mref);
/* 295:493 */       int sc = cp.addClassInfo(superclass);
/* 296:494 */       int mref2 = cp.addMethodrefInfo(sc, nt);
/* 297:495 */       ByteArray.write16bit(mref2, code, pos + 1);
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   private void read(MethodInfo src, String methodname, Map classnames)
/* 302:    */     throws BadBytecode
/* 303:    */   {
/* 304:501 */     ConstPool destCp = this.constPool;
/* 305:502 */     this.accessFlags = src.accessFlags;
/* 306:503 */     this.name = destCp.addUtf8Info(methodname);
/* 307:504 */     this.cachedName = methodname;
/* 308:505 */     ConstPool srcCp = src.constPool;
/* 309:506 */     String desc = srcCp.getUtf8Info(src.descriptor);
/* 310:507 */     String desc2 = Descriptor.rename(desc, classnames);
/* 311:508 */     this.descriptor = destCp.addUtf8Info(desc2);
/* 312:    */     
/* 313:510 */     this.attribute = new ArrayList();
/* 314:511 */     ExceptionsAttribute eattr = src.getExceptionsAttribute();
/* 315:512 */     if (eattr != null) {
/* 316:513 */       this.attribute.add(eattr.copy(destCp, classnames));
/* 317:    */     }
/* 318:515 */     CodeAttribute cattr = src.getCodeAttribute();
/* 319:516 */     if (cattr != null) {
/* 320:517 */       this.attribute.add(cattr.copy(destCp, classnames));
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private void read(DataInputStream in)
/* 325:    */     throws IOException
/* 326:    */   {
/* 327:521 */     this.accessFlags = in.readUnsignedShort();
/* 328:522 */     this.name = in.readUnsignedShort();
/* 329:523 */     this.descriptor = in.readUnsignedShort();
/* 330:524 */     int n = in.readUnsignedShort();
/* 331:525 */     this.attribute = new ArrayList();
/* 332:526 */     for (int i = 0; i < n; i++) {
/* 333:527 */       this.attribute.add(AttributeInfo.read(this.constPool, in));
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   void write(DataOutputStream out)
/* 338:    */     throws IOException
/* 339:    */   {
/* 340:531 */     out.writeShort(this.accessFlags);
/* 341:532 */     out.writeShort(this.name);
/* 342:533 */     out.writeShort(this.descriptor);
/* 343:535 */     if (this.attribute == null)
/* 344:    */     {
/* 345:536 */       out.writeShort(0);
/* 346:    */     }
/* 347:    */     else
/* 348:    */     {
/* 349:538 */       out.writeShort(this.attribute.size());
/* 350:539 */       AttributeInfo.writeAll(this.attribute, out);
/* 351:    */     }
/* 352:    */   }
/* 353:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.MethodInfo
 * JD-Core Version:    0.7.0.1
 */