/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class DescendingVisitor
/*   7:    */   implements Visitor
/*   8:    */ {
/*   9:    */   private JavaClass clazz;
/*  10:    */   private Visitor visitor;
/*  11: 70 */   private Stack stack = new Stack();
/*  12:    */   
/*  13:    */   public Object predecessor()
/*  14:    */   {
/*  15: 75 */     return predecessor(0);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Object predecessor(int level)
/*  19:    */   {
/*  20: 83 */     int size = this.stack.size();
/*  21: 85 */     if ((size < 2) || (level < 0)) {
/*  22: 86 */       return null;
/*  23:    */     }
/*  24: 88 */     return this.stack.elementAt(size - (level + 2));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Object current()
/*  28:    */   {
/*  29: 94 */     return this.stack.peek();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public DescendingVisitor(JavaClass clazz, Visitor visitor)
/*  33:    */   {
/*  34:102 */     this.clazz = clazz;
/*  35:103 */     this.visitor = visitor;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void visit()
/*  39:    */   {
/*  40:109 */     this.clazz.accept(this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void visitJavaClass(JavaClass clazz)
/*  44:    */   {
/*  45:112 */     this.stack.push(clazz);
/*  46:113 */     clazz.accept(this.visitor);
/*  47:    */     
/*  48:115 */     Field[] fields = clazz.getFields();
/*  49:116 */     for (int i = 0; i < fields.length; i++) {
/*  50:117 */       fields[i].accept(this);
/*  51:    */     }
/*  52:119 */     Method[] methods = clazz.getMethods();
/*  53:120 */     for (int i = 0; i < methods.length; i++) {
/*  54:121 */       methods[i].accept(this);
/*  55:    */     }
/*  56:123 */     Attribute[] attributes = clazz.getAttributes();
/*  57:124 */     for (int i = 0; i < attributes.length; i++) {
/*  58:125 */       attributes[i].accept(this);
/*  59:    */     }
/*  60:127 */     clazz.getConstantPool().accept(this);
/*  61:128 */     this.stack.pop();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void visitField(Field field)
/*  65:    */   {
/*  66:132 */     this.stack.push(field);
/*  67:133 */     field.accept(this.visitor);
/*  68:    */     
/*  69:135 */     Attribute[] attributes = field.getAttributes();
/*  70:136 */     for (int i = 0; i < attributes.length; i++) {
/*  71:137 */       attributes[i].accept(this);
/*  72:    */     }
/*  73:138 */     this.stack.pop();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void visitConstantValue(ConstantValue cv)
/*  77:    */   {
/*  78:142 */     this.stack.push(cv);
/*  79:143 */     cv.accept(this.visitor);
/*  80:144 */     this.stack.pop();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void visitMethod(Method method)
/*  84:    */   {
/*  85:148 */     this.stack.push(method);
/*  86:149 */     method.accept(this.visitor);
/*  87:    */     
/*  88:151 */     Attribute[] attributes = method.getAttributes();
/*  89:152 */     for (int i = 0; i < attributes.length; i++) {
/*  90:153 */       attributes[i].accept(this);
/*  91:    */     }
/*  92:155 */     this.stack.pop();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void visitExceptionTable(ExceptionTable table)
/*  96:    */   {
/*  97:159 */     this.stack.push(table);
/*  98:160 */     table.accept(this.visitor);
/*  99:161 */     this.stack.pop();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void visitCode(Code code)
/* 103:    */   {
/* 104:165 */     this.stack.push(code);
/* 105:166 */     code.accept(this.visitor);
/* 106:    */     
/* 107:168 */     CodeException[] table = code.getExceptionTable();
/* 108:169 */     for (int i = 0; i < table.length; i++) {
/* 109:170 */       table[i].accept(this);
/* 110:    */     }
/* 111:172 */     Attribute[] attributes = code.getAttributes();
/* 112:173 */     for (int i = 0; i < attributes.length; i++) {
/* 113:174 */       attributes[i].accept(this);
/* 114:    */     }
/* 115:175 */     this.stack.pop();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void visitCodeException(CodeException ce)
/* 119:    */   {
/* 120:179 */     this.stack.push(ce);
/* 121:180 */     ce.accept(this.visitor);
/* 122:181 */     this.stack.pop();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void visitLineNumberTable(LineNumberTable table)
/* 126:    */   {
/* 127:185 */     this.stack.push(table);
/* 128:186 */     table.accept(this.visitor);
/* 129:    */     
/* 130:188 */     LineNumber[] numbers = table.getLineNumberTable();
/* 131:189 */     for (int i = 0; i < numbers.length; i++) {
/* 132:190 */       numbers[i].accept(this);
/* 133:    */     }
/* 134:191 */     this.stack.pop();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void visitLineNumber(LineNumber number)
/* 138:    */   {
/* 139:195 */     this.stack.push(number);
/* 140:196 */     number.accept(this.visitor);
/* 141:197 */     this.stack.pop();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void visitLocalVariableTable(LocalVariableTable table)
/* 145:    */   {
/* 146:201 */     this.stack.push(table);
/* 147:202 */     table.accept(this.visitor);
/* 148:    */     
/* 149:204 */     LocalVariable[] vars = table.getLocalVariableTable();
/* 150:205 */     for (int i = 0; i < vars.length; i++) {
/* 151:206 */       vars[i].accept(this);
/* 152:    */     }
/* 153:207 */     this.stack.pop();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void visitStackMap(StackMap table)
/* 157:    */   {
/* 158:211 */     this.stack.push(table);
/* 159:212 */     table.accept(this.visitor);
/* 160:    */     
/* 161:214 */     StackMapEntry[] vars = table.getStackMap();
/* 162:216 */     for (int i = 0; i < vars.length; i++) {
/* 163:217 */       vars[i].accept(this);
/* 164:    */     }
/* 165:218 */     this.stack.pop();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void visitStackMapEntry(StackMapEntry var)
/* 169:    */   {
/* 170:222 */     this.stack.push(var);
/* 171:223 */     var.accept(this.visitor);
/* 172:224 */     this.stack.pop();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void visitLocalVariable(LocalVariable var)
/* 176:    */   {
/* 177:228 */     this.stack.push(var);
/* 178:229 */     var.accept(this.visitor);
/* 179:230 */     this.stack.pop();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void visitConstantPool(ConstantPool cp)
/* 183:    */   {
/* 184:234 */     this.stack.push(cp);
/* 185:235 */     cp.accept(this.visitor);
/* 186:    */     
/* 187:237 */     Constant[] constants = cp.getConstantPool();
/* 188:238 */     for (int i = 1; i < constants.length; i++) {
/* 189:239 */       if (constants[i] != null) {
/* 190:240 */         constants[i].accept(this);
/* 191:    */       }
/* 192:    */     }
/* 193:243 */     this.stack.pop();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void visitConstantClass(ConstantClass constant)
/* 197:    */   {
/* 198:247 */     this.stack.push(constant);
/* 199:248 */     constant.accept(this.visitor);
/* 200:249 */     this.stack.pop();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void visitConstantDouble(ConstantDouble constant)
/* 204:    */   {
/* 205:253 */     this.stack.push(constant);
/* 206:254 */     constant.accept(this.visitor);
/* 207:255 */     this.stack.pop();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void visitConstantFieldref(ConstantFieldref constant)
/* 211:    */   {
/* 212:259 */     this.stack.push(constant);
/* 213:260 */     constant.accept(this.visitor);
/* 214:261 */     this.stack.pop();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void visitConstantFloat(ConstantFloat constant)
/* 218:    */   {
/* 219:265 */     this.stack.push(constant);
/* 220:266 */     constant.accept(this.visitor);
/* 221:267 */     this.stack.pop();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void visitConstantInteger(ConstantInteger constant)
/* 225:    */   {
/* 226:271 */     this.stack.push(constant);
/* 227:272 */     constant.accept(this.visitor);
/* 228:273 */     this.stack.pop();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constant)
/* 232:    */   {
/* 233:277 */     this.stack.push(constant);
/* 234:278 */     constant.accept(this.visitor);
/* 235:279 */     this.stack.pop();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void visitConstantLong(ConstantLong constant)
/* 239:    */   {
/* 240:283 */     this.stack.push(constant);
/* 241:284 */     constant.accept(this.visitor);
/* 242:285 */     this.stack.pop();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void visitConstantMethodref(ConstantMethodref constant)
/* 246:    */   {
/* 247:289 */     this.stack.push(constant);
/* 248:290 */     constant.accept(this.visitor);
/* 249:291 */     this.stack.pop();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void visitConstantNameAndType(ConstantNameAndType constant)
/* 253:    */   {
/* 254:295 */     this.stack.push(constant);
/* 255:296 */     constant.accept(this.visitor);
/* 256:297 */     this.stack.pop();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void visitConstantString(ConstantString constant)
/* 260:    */   {
/* 261:301 */     this.stack.push(constant);
/* 262:302 */     constant.accept(this.visitor);
/* 263:303 */     this.stack.pop();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void visitConstantUtf8(ConstantUtf8 constant)
/* 267:    */   {
/* 268:307 */     this.stack.push(constant);
/* 269:308 */     constant.accept(this.visitor);
/* 270:309 */     this.stack.pop();
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void visitInnerClasses(InnerClasses ic)
/* 274:    */   {
/* 275:313 */     this.stack.push(ic);
/* 276:314 */     ic.accept(this.visitor);
/* 277:    */     
/* 278:316 */     InnerClass[] ics = ic.getInnerClasses();
/* 279:317 */     for (int i = 0; i < ics.length; i++) {
/* 280:318 */       ics[i].accept(this);
/* 281:    */     }
/* 282:319 */     this.stack.pop();
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void visitInnerClass(InnerClass inner)
/* 286:    */   {
/* 287:323 */     this.stack.push(inner);
/* 288:324 */     inner.accept(this.visitor);
/* 289:325 */     this.stack.pop();
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void visitDeprecated(Deprecated attribute)
/* 293:    */   {
/* 294:329 */     this.stack.push(attribute);
/* 295:330 */     attribute.accept(this.visitor);
/* 296:331 */     this.stack.pop();
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void visitSourceFile(SourceFile attribute)
/* 300:    */   {
/* 301:335 */     this.stack.push(attribute);
/* 302:336 */     attribute.accept(this.visitor);
/* 303:337 */     this.stack.pop();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void visitSynthetic(Synthetic attribute)
/* 307:    */   {
/* 308:341 */     this.stack.push(attribute);
/* 309:342 */     attribute.accept(this.visitor);
/* 310:343 */     this.stack.pop();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void visitUnknown(Unknown attribute)
/* 314:    */   {
/* 315:347 */     this.stack.push(attribute);
/* 316:348 */     attribute.accept(this.visitor);
/* 317:349 */     this.stack.pop();
/* 318:    */   }
/* 319:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.DescendingVisitor
 * JD-Core Version:    0.7.0.1
 */