/*   1:    */ package javassist.expr;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import javassist.CannotCompileException;
/*   6:    */ import javassist.ClassPool;
/*   7:    */ import javassist.CtBehavior;
/*   8:    */ import javassist.CtClass;
/*   9:    */ import javassist.CtConstructor;
/*  10:    */ import javassist.CtPrimitiveType;
/*  11:    */ import javassist.NotFoundException;
/*  12:    */ import javassist.bytecode.BadBytecode;
/*  13:    */ import javassist.bytecode.Bytecode;
/*  14:    */ import javassist.bytecode.ClassFile;
/*  15:    */ import javassist.bytecode.CodeAttribute;
/*  16:    */ import javassist.bytecode.CodeIterator;
/*  17:    */ import javassist.bytecode.CodeIterator.Gap;
/*  18:    */ import javassist.bytecode.ConstPool;
/*  19:    */ import javassist.bytecode.ExceptionTable;
/*  20:    */ import javassist.bytecode.ExceptionsAttribute;
/*  21:    */ import javassist.bytecode.MethodInfo;
/*  22:    */ import javassist.bytecode.Opcode;
/*  23:    */ 
/*  24:    */ public abstract class Expr
/*  25:    */   implements Opcode
/*  26:    */ {
/*  27:    */   int currentPos;
/*  28:    */   CodeIterator iterator;
/*  29:    */   CtClass thisClass;
/*  30:    */   MethodInfo thisMethod;
/*  31:    */   boolean edited;
/*  32:    */   int maxLocals;
/*  33:    */   int maxStack;
/*  34:    */   static final String javaLangObject = "java.lang.Object";
/*  35:    */   
/*  36:    */   protected Expr(int pos, CodeIterator i, CtClass declaring, MethodInfo m)
/*  37:    */   {
/*  38: 58 */     this.currentPos = pos;
/*  39: 59 */     this.iterator = i;
/*  40: 60 */     this.thisClass = declaring;
/*  41: 61 */     this.thisMethod = m;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public CtClass getEnclosingClass()
/*  45:    */   {
/*  46: 70 */     return this.thisClass;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected final ConstPool getConstPool()
/*  50:    */   {
/*  51: 73 */     return this.thisMethod.getConstPool();
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected final boolean edited()
/*  55:    */   {
/*  56: 77 */     return this.edited;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected final int locals()
/*  60:    */   {
/*  61: 81 */     return this.maxLocals;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected final int stack()
/*  65:    */   {
/*  66: 85 */     return this.maxStack;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected final boolean withinStatic()
/*  70:    */   {
/*  71: 92 */     return (this.thisMethod.getAccessFlags() & 0x8) != 0;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public CtBehavior where()
/*  75:    */   {
/*  76: 99 */     MethodInfo mi = this.thisMethod;
/*  77:100 */     CtBehavior[] cb = this.thisClass.getDeclaredBehaviors();
/*  78:101 */     for (int i = cb.length - 1; i >= 0; i--) {
/*  79:102 */       if (cb[i].getMethodInfo2() == mi) {
/*  80:103 */         return cb[i];
/*  81:    */       }
/*  82:    */     }
/*  83:105 */     CtConstructor init = this.thisClass.getClassInitializer();
/*  84:106 */     if ((init != null) && (init.getMethodInfo2() == mi)) {
/*  85:107 */       return init;
/*  86:    */     }
/*  87:114 */     for (int i = cb.length - 1; i >= 0; i--) {
/*  88:115 */       if ((this.thisMethod.getName().equals(cb[i].getMethodInfo2().getName())) && (this.thisMethod.getDescriptor().equals(cb[i].getMethodInfo2().getDescriptor()))) {
/*  89:118 */         return cb[i];
/*  90:    */       }
/*  91:    */     }
/*  92:122 */     throw new RuntimeException("fatal: not found");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public CtClass[] mayThrow()
/*  96:    */   {
/*  97:132 */     ClassPool pool = this.thisClass.getClassPool();
/*  98:133 */     ConstPool cp = this.thisMethod.getConstPool();
/*  99:134 */     LinkedList list = new LinkedList();
/* 100:    */     try
/* 101:    */     {
/* 102:136 */       CodeAttribute ca = this.thisMethod.getCodeAttribute();
/* 103:137 */       ExceptionTable et = ca.getExceptionTable();
/* 104:138 */       int pos = this.currentPos;
/* 105:139 */       int n = et.size();
/* 106:140 */       for (int i = 0; i < n; i++) {
/* 107:141 */         if ((et.startPc(i) <= pos) && (pos < et.endPc(i)))
/* 108:    */         {
/* 109:142 */           int t = et.catchType(i);
/* 110:143 */           if (t > 0) {
/* 111:    */             try
/* 112:    */             {
/* 113:145 */               addClass(list, pool.get(cp.getClassInfo(t)));
/* 114:    */             }
/* 115:    */             catch (NotFoundException e) {}
/* 116:    */           }
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:    */     catch (NullPointerException e) {}
/* 121:154 */     ExceptionsAttribute ea = this.thisMethod.getExceptionsAttribute();
/* 122:155 */     if (ea != null)
/* 123:    */     {
/* 124:156 */       String[] exceptions = ea.getExceptions();
/* 125:157 */       if (exceptions != null)
/* 126:    */       {
/* 127:158 */         int n = exceptions.length;
/* 128:159 */         for (int i = 0; i < n; i++) {
/* 129:    */           try
/* 130:    */           {
/* 131:161 */             addClass(list, pool.get(exceptions[i]));
/* 132:    */           }
/* 133:    */           catch (NotFoundException e) {}
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:168 */     return (CtClass[])list.toArray(new CtClass[list.size()]);
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static void addClass(LinkedList list, CtClass c)
/* 141:    */   {
/* 142:172 */     Iterator it = list.iterator();
/* 143:173 */     while (it.hasNext()) {
/* 144:174 */       if (it.next() == c) {
/* 145:175 */         return;
/* 146:    */       }
/* 147:    */     }
/* 148:177 */     list.add(c);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int indexOfBytecode()
/* 152:    */   {
/* 153:186 */     return this.currentPos;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getLineNumber()
/* 157:    */   {
/* 158:195 */     return this.thisMethod.getLineNumber(this.currentPos);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getFileName()
/* 162:    */   {
/* 163:204 */     ClassFile cf = this.thisClass.getClassFile2();
/* 164:205 */     if (cf == null) {
/* 165:206 */       return null;
/* 166:    */     }
/* 167:208 */     return cf.getSourceFile();
/* 168:    */   }
/* 169:    */   
/* 170:    */   static final boolean checkResultValue(CtClass retType, String prog)
/* 171:    */     throws CannotCompileException
/* 172:    */   {
/* 173:216 */     boolean hasIt = prog.indexOf("$_") >= 0;
/* 174:217 */     if ((!hasIt) && (retType != CtClass.voidType)) {
/* 175:218 */       throw new CannotCompileException("the resulting value is not stored in $_");
/* 176:    */     }
/* 177:222 */     return hasIt;
/* 178:    */   }
/* 179:    */   
/* 180:    */   static final void storeStack(CtClass[] params, boolean isStaticCall, int regno, Bytecode bytecode)
/* 181:    */   {
/* 182:234 */     storeStack0(0, params.length, params, regno + 1, bytecode);
/* 183:235 */     if (isStaticCall) {
/* 184:236 */       bytecode.addOpcode(1);
/* 185:    */     }
/* 186:238 */     bytecode.addAstore(regno);
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static void storeStack0(int i, int n, CtClass[] params, int regno, Bytecode bytecode)
/* 190:    */   {
/* 191:243 */     if (i >= n) {
/* 192:244 */       return;
/* 193:    */     }
/* 194:246 */     CtClass c = params[i];
/* 195:    */     int size;
/* 196:    */     int size;
/* 197:248 */     if ((c instanceof CtPrimitiveType)) {
/* 198:249 */       size = ((CtPrimitiveType)c).getDataSize();
/* 199:    */     } else {
/* 200:251 */       size = 1;
/* 201:    */     }
/* 202:253 */     storeStack0(i + 1, n, params, regno + size, bytecode);
/* 203:254 */     bytecode.addStore(regno, c);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public abstract void replace(String paramString)
/* 207:    */     throws CannotCompileException;
/* 208:    */   
/* 209:    */   public void replace(String statement, ExprEditor recursive)
/* 210:    */     throws CannotCompileException
/* 211:    */   {
/* 212:284 */     replace(statement);
/* 213:285 */     if (recursive != null) {
/* 214:286 */       runEditor(recursive, this.iterator);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void replace0(int pos, Bytecode bytecode, int size)
/* 219:    */     throws BadBytecode
/* 220:    */   {
/* 221:291 */     byte[] code = bytecode.get();
/* 222:292 */     this.edited = true;
/* 223:293 */     int gap = code.length - size;
/* 224:294 */     for (int i = 0; i < size; i++) {
/* 225:295 */       this.iterator.writeByte(0, pos + i);
/* 226:    */     }
/* 227:297 */     if (gap > 0) {
/* 228:298 */       pos = this.iterator.insertGapAt(pos, gap, false).position;
/* 229:    */     }
/* 230:300 */     this.iterator.write(code, pos);
/* 231:301 */     this.iterator.insert(bytecode.getExceptionTable(), pos);
/* 232:302 */     this.maxLocals = bytecode.getMaxLocals();
/* 233:303 */     this.maxStack = bytecode.getMaxStack();
/* 234:    */   }
/* 235:    */   
/* 236:    */   protected void runEditor(ExprEditor ed, CodeIterator oldIterator)
/* 237:    */     throws CannotCompileException
/* 238:    */   {
/* 239:309 */     CodeAttribute codeAttr = oldIterator.get();
/* 240:310 */     int orgLocals = codeAttr.getMaxLocals();
/* 241:311 */     int orgStack = codeAttr.getMaxStack();
/* 242:312 */     int newLocals = locals();
/* 243:313 */     codeAttr.setMaxStack(stack());
/* 244:314 */     codeAttr.setMaxLocals(newLocals);
/* 245:315 */     ExprEditor.LoopContext context = new ExprEditor.LoopContext(newLocals);
/* 246:    */     
/* 247:317 */     int size = oldIterator.getCodeLength();
/* 248:318 */     int endPos = oldIterator.lookAhead();
/* 249:319 */     oldIterator.move(this.currentPos);
/* 250:320 */     if (ed.doit(this.thisClass, this.thisMethod, context, oldIterator, endPos)) {
/* 251:321 */       this.edited = true;
/* 252:    */     }
/* 253:323 */     oldIterator.move(endPos + oldIterator.getCodeLength() - size);
/* 254:324 */     codeAttr.setMaxLocals(orgLocals);
/* 255:325 */     codeAttr.setMaxStack(orgStack);
/* 256:326 */     this.maxLocals = context.maxLocals;
/* 257:327 */     this.maxStack += context.maxStack;
/* 258:    */   }
/* 259:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.expr.Expr
 * JD-Core Version:    0.7.0.1
 */