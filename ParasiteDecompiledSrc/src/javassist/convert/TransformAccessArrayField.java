/*   1:    */ package javassist.convert;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.CodeConverter.ArrayAccessReplacementMethodNames;
/*   5:    */ import javassist.CtClass;
/*   6:    */ import javassist.NotFoundException;
/*   7:    */ import javassist.bytecode.BadBytecode;
/*   8:    */ import javassist.bytecode.CodeAttribute;
/*   9:    */ import javassist.bytecode.CodeIterator;
/*  10:    */ import javassist.bytecode.CodeIterator.Gap;
/*  11:    */ import javassist.bytecode.ConstPool;
/*  12:    */ import javassist.bytecode.Descriptor;
/*  13:    */ import javassist.bytecode.MethodInfo;
/*  14:    */ import javassist.bytecode.analysis.Analyzer;
/*  15:    */ import javassist.bytecode.analysis.Frame;
/*  16:    */ import javassist.bytecode.analysis.Type;
/*  17:    */ 
/*  18:    */ public final class TransformAccessArrayField
/*  19:    */   extends Transformer
/*  20:    */ {
/*  21:    */   private final String methodClassname;
/*  22:    */   private final CodeConverter.ArrayAccessReplacementMethodNames names;
/*  23:    */   private Frame[] frames;
/*  24:    */   private int offset;
/*  25:    */   
/*  26:    */   public TransformAccessArrayField(Transformer next, String methodClassname, CodeConverter.ArrayAccessReplacementMethodNames names)
/*  27:    */     throws NotFoundException
/*  28:    */   {
/*  29: 45 */     super(next);
/*  30: 46 */     this.methodClassname = methodClassname;
/*  31: 47 */     this.names = names;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void initialize(ConstPool cp, CtClass clazz, MethodInfo minfo)
/*  35:    */     throws CannotCompileException
/*  36:    */   {
/*  37: 62 */     CodeIterator iterator = minfo.getCodeAttribute().iterator();
/*  38: 63 */     while (iterator.hasNext()) {
/*  39:    */       try
/*  40:    */       {
/*  41: 65 */         int pos = iterator.next();
/*  42: 66 */         int c = iterator.byteAt(pos);
/*  43: 68 */         if (c == 50) {
/*  44: 69 */           initFrames(clazz, minfo);
/*  45:    */         }
/*  46: 71 */         if ((c == 50) || (c == 51) || (c == 52) || (c == 49) || (c == 48) || (c == 46) || (c == 47) || (c == 53)) {
/*  47: 74 */           pos = replace(cp, iterator, pos, c, getLoadReplacementSignature(c));
/*  48: 75 */         } else if ((c == 83) || (c == 84) || (c == 85) || (c == 82) || (c == 81) || (c == 79) || (c == 80) || (c == 86)) {
/*  49: 78 */           pos = replace(cp, iterator, pos, c, getStoreReplacementSignature(c));
/*  50:    */         }
/*  51:    */       }
/*  52:    */       catch (Exception e)
/*  53:    */       {
/*  54: 82 */         throw new CannotCompileException(e);
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void clean()
/*  60:    */   {
/*  61: 88 */     this.frames = null;
/*  62: 89 */     this.offset = -1;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int transform(CtClass tclazz, int pos, CodeIterator iterator, ConstPool cp)
/*  66:    */     throws BadBytecode
/*  67:    */   {
/*  68: 95 */     return pos;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private Frame getFrame(int pos)
/*  72:    */     throws BadBytecode
/*  73:    */   {
/*  74: 99 */     return this.frames[(pos - this.offset)];
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void initFrames(CtClass clazz, MethodInfo minfo)
/*  78:    */     throws BadBytecode
/*  79:    */   {
/*  80:103 */     if (this.frames == null)
/*  81:    */     {
/*  82:104 */       this.frames = new Analyzer().analyze(clazz, minfo);
/*  83:105 */       this.offset = 0;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private int updatePos(int pos, int increment)
/*  88:    */   {
/*  89:110 */     if (this.offset > -1) {
/*  90:111 */       this.offset += increment;
/*  91:    */     }
/*  92:113 */     return pos + increment;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private String getTopType(int pos)
/*  96:    */     throws BadBytecode
/*  97:    */   {
/*  98:117 */     Frame frame = getFrame(pos);
/*  99:118 */     if (frame == null) {
/* 100:119 */       return null;
/* 101:    */     }
/* 102:121 */     CtClass clazz = frame.peek().getCtClass();
/* 103:122 */     return clazz != null ? Descriptor.toJvmName(clazz) : null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private int replace(ConstPool cp, CodeIterator iterator, int pos, int opcode, String signature)
/* 107:    */     throws BadBytecode
/* 108:    */   {
/* 109:127 */     String castType = null;
/* 110:128 */     String methodName = getMethodName(opcode);
/* 111:129 */     if (methodName != null)
/* 112:    */     {
/* 113:131 */       if (opcode == 50)
/* 114:    */       {
/* 115:132 */         castType = getTopType(iterator.lookAhead());
/* 116:136 */         if (castType == null) {
/* 117:137 */           return pos;
/* 118:    */         }
/* 119:138 */         if ("java/lang/Object".equals(castType)) {
/* 120:139 */           castType = null;
/* 121:    */         }
/* 122:    */       }
/* 123:144 */       iterator.writeByte(0, pos);
/* 124:145 */       CodeIterator.Gap gap = iterator.insertGapAt(pos, castType != null ? 5 : 2, false);
/* 125:    */       
/* 126:147 */       pos = gap.position;
/* 127:148 */       int mi = cp.addClassInfo(this.methodClassname);
/* 128:149 */       int methodref = cp.addMethodrefInfo(mi, methodName, signature);
/* 129:150 */       iterator.writeByte(184, pos);
/* 130:151 */       iterator.write16bit(methodref, pos + 1);
/* 131:153 */       if (castType != null)
/* 132:    */       {
/* 133:154 */         int index = cp.addClassInfo(castType);
/* 134:155 */         iterator.writeByte(192, pos + 3);
/* 135:156 */         iterator.write16bit(index, pos + 4);
/* 136:    */       }
/* 137:159 */       pos = updatePos(pos, gap.length);
/* 138:    */     }
/* 139:162 */     return pos;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private String getMethodName(int opcode)
/* 143:    */   {
/* 144:166 */     String methodName = null;
/* 145:167 */     switch (opcode)
/* 146:    */     {
/* 147:    */     case 50: 
/* 148:169 */       methodName = this.names.objectRead();
/* 149:170 */       break;
/* 150:    */     case 51: 
/* 151:172 */       methodName = this.names.byteOrBooleanRead();
/* 152:173 */       break;
/* 153:    */     case 52: 
/* 154:175 */       methodName = this.names.charRead();
/* 155:176 */       break;
/* 156:    */     case 49: 
/* 157:178 */       methodName = this.names.doubleRead();
/* 158:179 */       break;
/* 159:    */     case 48: 
/* 160:181 */       methodName = this.names.floatRead();
/* 161:182 */       break;
/* 162:    */     case 46: 
/* 163:184 */       methodName = this.names.intRead();
/* 164:185 */       break;
/* 165:    */     case 53: 
/* 166:187 */       methodName = this.names.shortRead();
/* 167:188 */       break;
/* 168:    */     case 47: 
/* 169:190 */       methodName = this.names.longRead();
/* 170:191 */       break;
/* 171:    */     case 83: 
/* 172:193 */       methodName = this.names.objectWrite();
/* 173:194 */       break;
/* 174:    */     case 84: 
/* 175:196 */       methodName = this.names.byteOrBooleanWrite();
/* 176:197 */       break;
/* 177:    */     case 85: 
/* 178:199 */       methodName = this.names.charWrite();
/* 179:200 */       break;
/* 180:    */     case 82: 
/* 181:202 */       methodName = this.names.doubleWrite();
/* 182:203 */       break;
/* 183:    */     case 81: 
/* 184:205 */       methodName = this.names.floatWrite();
/* 185:206 */       break;
/* 186:    */     case 79: 
/* 187:208 */       methodName = this.names.intWrite();
/* 188:209 */       break;
/* 189:    */     case 86: 
/* 190:211 */       methodName = this.names.shortWrite();
/* 191:212 */       break;
/* 192:    */     case 80: 
/* 193:214 */       methodName = this.names.longWrite();
/* 194:    */     }
/* 195:218 */     if (methodName.equals("")) {
/* 196:219 */       methodName = null;
/* 197:    */     }
/* 198:221 */     return methodName;
/* 199:    */   }
/* 200:    */   
/* 201:    */   private String getLoadReplacementSignature(int opcode)
/* 202:    */     throws BadBytecode
/* 203:    */   {
/* 204:225 */     switch (opcode)
/* 205:    */     {
/* 206:    */     case 50: 
/* 207:227 */       return "(Ljava/lang/Object;I)Ljava/lang/Object;";
/* 208:    */     case 51: 
/* 209:229 */       return "(Ljava/lang/Object;I)B";
/* 210:    */     case 52: 
/* 211:231 */       return "(Ljava/lang/Object;I)C";
/* 212:    */     case 49: 
/* 213:233 */       return "(Ljava/lang/Object;I)D";
/* 214:    */     case 48: 
/* 215:235 */       return "(Ljava/lang/Object;I)F";
/* 216:    */     case 46: 
/* 217:237 */       return "(Ljava/lang/Object;I)I";
/* 218:    */     case 53: 
/* 219:239 */       return "(Ljava/lang/Object;I)S";
/* 220:    */     case 47: 
/* 221:241 */       return "(Ljava/lang/Object;I)J";
/* 222:    */     }
/* 223:244 */     throw new BadBytecode(opcode);
/* 224:    */   }
/* 225:    */   
/* 226:    */   private String getStoreReplacementSignature(int opcode)
/* 227:    */     throws BadBytecode
/* 228:    */   {
/* 229:248 */     switch (opcode)
/* 230:    */     {
/* 231:    */     case 83: 
/* 232:250 */       return "(Ljava/lang/Object;ILjava/lang/Object;)V";
/* 233:    */     case 84: 
/* 234:252 */       return "(Ljava/lang/Object;IB)V";
/* 235:    */     case 85: 
/* 236:254 */       return "(Ljava/lang/Object;IC)V";
/* 237:    */     case 82: 
/* 238:256 */       return "(Ljava/lang/Object;ID)V";
/* 239:    */     case 81: 
/* 240:258 */       return "(Ljava/lang/Object;IF)V";
/* 241:    */     case 79: 
/* 242:260 */       return "(Ljava/lang/Object;II)V";
/* 243:    */     case 86: 
/* 244:262 */       return "(Ljava/lang/Object;IS)V";
/* 245:    */     case 80: 
/* 246:264 */       return "(Ljava/lang/Object;IJ)V";
/* 247:    */     }
/* 248:267 */     throw new BadBytecode(opcode);
/* 249:    */   }
/* 250:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformAccessArrayField
 * JD-Core Version:    0.7.0.1
 */