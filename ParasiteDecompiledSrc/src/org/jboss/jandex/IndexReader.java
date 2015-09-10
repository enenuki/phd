/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public final class IndexReader
/*  12:    */ {
/*  13:    */   private static final int MAGIC = -1161945323;
/*  14:    */   private static final byte VERSION = 2;
/*  15:    */   private static final byte FIELD_TAG = 1;
/*  16:    */   private static final byte METHOD_TAG = 2;
/*  17:    */   private static final byte METHOD_PARAMATER_TAG = 3;
/*  18:    */   private static final byte CLASS_TAG = 4;
/*  19:    */   private static final int AVALUE_BYTE = 1;
/*  20:    */   private static final int AVALUE_SHORT = 2;
/*  21:    */   private static final int AVALUE_INT = 3;
/*  22:    */   private static final int AVALUE_CHAR = 4;
/*  23:    */   private static final int AVALUE_FLOAT = 5;
/*  24:    */   private static final int AVALUE_DOUBLE = 6;
/*  25:    */   private static final int AVALUE_LONG = 7;
/*  26:    */   private static final int AVALUE_BOOLEAN = 8;
/*  27:    */   private static final int AVALUE_STRING = 9;
/*  28:    */   private static final int AVALUE_CLASS = 10;
/*  29:    */   private static final int AVALUE_ENUM = 11;
/*  30:    */   private static final int AVALUE_ARRAY = 12;
/*  31:    */   private static final int AVALUE_NESTED = 13;
/*  32:    */   private InputStream input;
/*  33:    */   private DotName[] classTable;
/*  34:    */   private String[] stringTable;
/*  35:    */   private HashMap<DotName, List<AnnotationInstance>> masterAnnotations;
/*  36:    */   
/*  37:    */   public IndexReader(InputStream input)
/*  38:    */   {
/*  39: 84 */     this.input = input;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Index read()
/*  43:    */     throws IOException
/*  44:    */   {
/*  45: 97 */     PackedDataInputStream stream = new PackedDataInputStream(new BufferedInputStream(this.input));
/*  46: 98 */     if (stream.readInt() != -1161945323) {
/*  47: 99 */       throw new IllegalArgumentException("Not a jandex index");
/*  48:    */     }
/*  49:100 */     byte version = stream.readByte();
/*  50:102 */     if (version != 2) {
/*  51:103 */       throw new UnsupportedVersion("Version: " + version);
/*  52:    */     }
/*  53:    */     try
/*  54:    */     {
/*  55:106 */       this.masterAnnotations = new HashMap();
/*  56:107 */       readClassTable(stream);
/*  57:108 */       readStringTable(stream);
/*  58:109 */       return readClasses(stream);
/*  59:    */     }
/*  60:    */     finally
/*  61:    */     {
/*  62:111 */       this.classTable = null;
/*  63:112 */       this.stringTable = null;
/*  64:113 */       this.masterAnnotations = null;
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   private Index readClasses(PackedDataInputStream stream)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:119 */     int entries = stream.readPackedU32();
/*  72:120 */     HashMap<DotName, List<ClassInfo>> subclasses = new HashMap();
/*  73:121 */     HashMap<DotName, List<ClassInfo>> implementors = new HashMap();
/*  74:122 */     HashMap<DotName, ClassInfo> classes = new HashMap();
/*  75:123 */     this.masterAnnotations = new HashMap();
/*  76:125 */     for (int i = 0; i < entries; i++)
/*  77:    */     {
/*  78:126 */       DotName name = this.classTable[stream.readPackedU32()];
/*  79:127 */       DotName superName = this.classTable[stream.readPackedU32()];
/*  80:128 */       short flags = stream.readShort();
/*  81:129 */       int numIntfs = stream.readPackedU32();
/*  82:130 */       DotName[] interfaces = new DotName[numIntfs];
/*  83:131 */       for (int j = 0; j < numIntfs; j++) {
/*  84:132 */         interfaces[j] = this.classTable[stream.readPackedU32()];
/*  85:    */       }
/*  86:135 */       Map<DotName, List<AnnotationInstance>> annotations = new HashMap();
/*  87:136 */       ClassInfo clazz = new ClassInfo(name, superName, flags, interfaces, annotations);
/*  88:137 */       classes.put(name, clazz);
/*  89:138 */       addClassToMap(subclasses, superName, clazz);
/*  90:139 */       for (DotName interfaceName : interfaces) {
/*  91:140 */         addClassToMap(implementors, interfaceName, clazz);
/*  92:    */       }
/*  93:142 */       readAnnotations(stream, annotations, clazz);
/*  94:    */     }
/*  95:145 */     return Index.create(this.masterAnnotations, subclasses, implementors, classes);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void readAnnotations(PackedDataInputStream stream, Map<DotName, List<AnnotationInstance>> annotations, ClassInfo clazz)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:151 */     int numAnnotations = stream.readPackedU32();
/* 102:152 */     for (int j = 0; j < numAnnotations; j++)
/* 103:    */     {
/* 104:153 */       DotName annotationName = this.classTable[stream.readPackedU32()];
/* 105:    */       
/* 106:155 */       int numTargets = stream.readPackedU32();
/* 107:156 */       for (int k = 0; k < numTargets; k++)
/* 108:    */       {
/* 109:157 */         int tag = stream.readPackedU32();
/* 110:    */         AnnotationTarget target;
/* 111:160 */         switch (tag)
/* 112:    */         {
/* 113:    */         case 1: 
/* 114:162 */           String name = this.stringTable[stream.readPackedU32()];
/* 115:163 */           Type type = readType(stream);
/* 116:164 */           short flags = stream.readShort();
/* 117:165 */           target = new FieldInfo(clazz, name, type, flags);
/* 118:166 */           break;
/* 119:    */         case 2: 
/* 120:169 */           target = readMethod(clazz, stream);
/* 121:170 */           break;
/* 122:    */         case 3: 
/* 123:173 */           MethodInfo method = readMethod(clazz, stream);
/* 124:174 */           target = new MethodParameterInfo(method, (short)stream.readPackedU32());
/* 125:175 */           break;
/* 126:    */         case 4: 
/* 127:178 */           target = clazz;
/* 128:179 */           break;
/* 129:    */         default: 
/* 130:182 */           throw new UnsupportedOperationException();
/* 131:    */         }
/* 132:185 */         AnnotationValue[] values = readAnnotationValues(stream);
/* 133:186 */         AnnotationInstance instance = new AnnotationInstance(annotationName, target, values);
/* 134:    */         
/* 135:188 */         recordAnnotation(this.masterAnnotations, annotationName, instance);
/* 136:189 */         recordAnnotation(annotations, annotationName, instance);
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private AnnotationValue[] readAnnotationValues(PackedDataInputStream stream)
/* 142:    */     throws IOException
/* 143:    */   {
/* 144:197 */     int numValues = stream.readPackedU32();
/* 145:198 */     AnnotationValue[] values = new AnnotationValue[numValues];
/* 146:200 */     for (int i = 0; i < numValues; i++)
/* 147:    */     {
/* 148:202 */       String name = this.stringTable[stream.readPackedU32()];
/* 149:203 */       int tag = stream.readByte();
/* 150:    */       AnnotationValue value;
/* 151:205 */       switch (tag)
/* 152:    */       {
/* 153:    */       case 1: 
/* 154:207 */         value = new AnnotationValue.ByteValue(name, stream.readByte());
/* 155:208 */         break;
/* 156:    */       case 2: 
/* 157:210 */         value = new AnnotationValue.ShortValue(name, (short)stream.readPackedU32());
/* 158:211 */         break;
/* 159:    */       case 3: 
/* 160:213 */         value = new AnnotationValue.IntegerValue(name, stream.readPackedU32());
/* 161:214 */         break;
/* 162:    */       case 4: 
/* 163:216 */         value = new AnnotationValue.CharacterValue(name, (char)stream.readPackedU32());
/* 164:217 */         break;
/* 165:    */       case 5: 
/* 166:219 */         value = new AnnotationValue.FloatValue(name, stream.readFloat());
/* 167:220 */         break;
/* 168:    */       case 6: 
/* 169:222 */         value = new AnnotationValue.DoubleValue(name, stream.readDouble());
/* 170:223 */         break;
/* 171:    */       case 7: 
/* 172:225 */         value = new AnnotationValue.LongValue(name, stream.readLong());
/* 173:226 */         break;
/* 174:    */       case 8: 
/* 175:228 */         value = new AnnotationValue.BooleanValue(name, stream.readBoolean());
/* 176:229 */         break;
/* 177:    */       case 9: 
/* 178:231 */         value = new AnnotationValue.StringValue(name, this.stringTable[stream.readPackedU32()]);
/* 179:232 */         break;
/* 180:    */       case 10: 
/* 181:234 */         value = new AnnotationValue.ClassValue(name, readType(stream));
/* 182:235 */         break;
/* 183:    */       case 11: 
/* 184:237 */         value = new AnnotationValue.EnumValue(name, this.classTable[stream.readPackedU32()], this.stringTable[stream.readPackedU32()]);
/* 185:238 */         break;
/* 186:    */       case 12: 
/* 187:240 */         value = new AnnotationValue.ArrayValue(name, readAnnotationValues(stream));
/* 188:241 */         break;
/* 189:    */       case 13: 
/* 190:243 */         DotName nestedName = this.classTable[stream.readPackedU32()];
/* 191:244 */         AnnotationInstance nestedInstance = new AnnotationInstance(nestedName, null, readAnnotationValues(stream));
/* 192:245 */         value = new AnnotationValue.NestedAnnotation(name, nestedInstance);
/* 193:246 */         break;
/* 194:    */       default: 
/* 195:249 */         throw new IllegalStateException("Invalid annotation value tag:" + tag);
/* 196:    */       }
/* 197:252 */       values[i] = value;
/* 198:    */     }
/* 199:255 */     return values;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private MethodInfo readMethod(ClassInfo clazz, PackedDataInputStream stream)
/* 203:    */     throws IOException
/* 204:    */   {
/* 205:259 */     String name = this.stringTable[stream.readPackedU32()];
/* 206:260 */     int numArgs = stream.readPackedU32();
/* 207:261 */     Type[] args = new Type[numArgs];
/* 208:262 */     for (int i = 0; i < numArgs; i++) {
/* 209:263 */       args[i] = readType(stream);
/* 210:    */     }
/* 211:265 */     Type returnType = readType(stream);
/* 212:266 */     short flags = stream.readShort();
/* 213:267 */     return new MethodInfo(clazz, name, args, returnType, flags);
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void recordAnnotation(Map<DotName, List<AnnotationInstance>> annotations, DotName annotation, AnnotationInstance instance)
/* 217:    */   {
/* 218:271 */     List<AnnotationInstance> list = (List)annotations.get(annotation);
/* 219:272 */     if (list == null)
/* 220:    */     {
/* 221:273 */       list = new ArrayList();
/* 222:274 */       annotations.put(annotation, list);
/* 223:    */     }
/* 224:277 */     list.add(instance);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private void addClassToMap(HashMap<DotName, List<ClassInfo>> map, DotName name, ClassInfo currentClass)
/* 228:    */   {
/* 229:281 */     List<ClassInfo> list = (List)map.get(name);
/* 230:282 */     if (list == null)
/* 231:    */     {
/* 232:283 */       list = new ArrayList();
/* 233:284 */       map.put(name, list);
/* 234:    */     }
/* 235:287 */     list.add(currentClass);
/* 236:    */   }
/* 237:    */   
/* 238:    */   private Type readType(PackedDataInputStream stream)
/* 239:    */     throws IOException
/* 240:    */   {
/* 241:291 */     Type.Kind kind = Type.Kind.fromOrdinal(stream.readByte());
/* 242:292 */     DotName name = this.classTable[stream.readPackedU32()];
/* 243:293 */     return new Type(name, kind);
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void readStringTable(PackedDataInputStream stream)
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:298 */     int entries = stream.readPackedU32();
/* 250:299 */     this.stringTable = new String[entries];
/* 251:301 */     for (int i = 0; i < entries; i++) {
/* 252:302 */       this.stringTable[i] = stream.readUTF();
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   private void readClassTable(PackedDataInputStream stream)
/* 257:    */     throws IOException
/* 258:    */   {
/* 259:308 */     int entries = stream.readPackedU32();
/* 260:309 */     int lastDepth = -1;
/* 261:310 */     DotName curr = null;
/* 262:    */     
/* 263:    */ 
/* 264:313 */     this.classTable = new DotName[++entries];
/* 265:314 */     for (int i = 1; i < entries; i++)
/* 266:    */     {
/* 267:315 */       int depth = stream.readPackedU32();
/* 268:316 */       String local = stream.readUTF();
/* 269:318 */       if (depth <= lastDepth) {
/* 270:319 */         while (lastDepth-- >= depth) {
/* 271:320 */           curr = curr.prefix();
/* 272:    */         }
/* 273:    */       }
/* 274:323 */       void tmp85_82 = new DotName(curr, local, true);curr = tmp85_82;this.classTable[i] = tmp85_82;
/* 275:324 */       lastDepth = depth;
/* 276:    */     }
/* 277:    */   }
/* 278:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.IndexReader
 * JD-Core Version:    0.7.0.1
 */