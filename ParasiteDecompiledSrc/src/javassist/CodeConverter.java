/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.BadBytecode;
/*   4:    */ import javassist.bytecode.CodeAttribute;
/*   5:    */ import javassist.bytecode.CodeIterator;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ import javassist.bytecode.MethodInfo;
/*   8:    */ import javassist.convert.TransformAccessArrayField;
/*   9:    */ import javassist.convert.TransformAfter;
/*  10:    */ import javassist.convert.TransformBefore;
/*  11:    */ import javassist.convert.TransformCall;
/*  12:    */ import javassist.convert.TransformFieldAccess;
/*  13:    */ import javassist.convert.TransformNew;
/*  14:    */ import javassist.convert.TransformNewClass;
/*  15:    */ import javassist.convert.TransformReadField;
/*  16:    */ import javassist.convert.TransformWriteField;
/*  17:    */ import javassist.convert.Transformer;
/*  18:    */ 
/*  19:    */ public class CodeConverter
/*  20:    */ {
/*  21:    */   protected Transformer transformers;
/*  22:    */   
/*  23:    */   public CodeConverter()
/*  24:    */   {
/*  25: 50 */     this.transformers = null;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void replaceNew(CtClass newClass, CtClass calledClass, String calledMethod)
/*  29:    */   {
/*  30: 96 */     this.transformers = new TransformNew(this.transformers, newClass.getName(), calledClass.getName(), calledMethod);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void replaceNew(CtClass oldClass, CtClass newClass)
/*  34:    */   {
/*  35:122 */     this.transformers = new TransformNewClass(this.transformers, oldClass.getName(), newClass.getName());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void redirectFieldAccess(CtField field, CtClass newClass, String newFieldname)
/*  39:    */   {
/*  40:145 */     this.transformers = new TransformFieldAccess(this.transformers, field, newClass.getName(), newFieldname);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void replaceFieldRead(CtField field, CtClass calledClass, String calledMethod)
/*  44:    */   {
/*  45:185 */     this.transformers = new TransformReadField(this.transformers, field, calledClass.getName(), calledMethod);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void replaceFieldWrite(CtField field, CtClass calledClass, String calledMethod)
/*  49:    */   {
/*  50:226 */     this.transformers = new TransformWriteField(this.transformers, field, calledClass.getName(), calledMethod);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void replaceArrayAccess(CtClass calledClass, ArrayAccessReplacementMethodNames names)
/*  54:    */     throws NotFoundException
/*  55:    */   {
/*  56:329 */     this.transformers = new TransformAccessArrayField(this.transformers, calledClass.getName(), names);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void redirectMethodCall(CtMethod origMethod, CtMethod substMethod)
/*  60:    */     throws CannotCompileException
/*  61:    */   {
/*  62:351 */     String d1 = origMethod.getMethodInfo2().getDescriptor();
/*  63:352 */     String d2 = substMethod.getMethodInfo2().getDescriptor();
/*  64:353 */     if (!d1.equals(d2)) {
/*  65:354 */       throw new CannotCompileException("signature mismatch: " + substMethod.getLongName());
/*  66:    */     }
/*  67:357 */     int mod1 = origMethod.getModifiers();
/*  68:358 */     int mod2 = substMethod.getModifiers();
/*  69:359 */     if ((Modifier.isStatic(mod1) != Modifier.isStatic(mod2)) || ((Modifier.isPrivate(mod1)) && (!Modifier.isPrivate(mod2))) || (origMethod.getDeclaringClass().isInterface() != substMethod.getDeclaringClass().isInterface())) {
/*  70:363 */       throw new CannotCompileException("invoke-type mismatch " + substMethod.getLongName());
/*  71:    */     }
/*  72:366 */     this.transformers = new TransformCall(this.transformers, origMethod, substMethod);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void redirectMethodCall(String oldMethodName, CtMethod newMethod)
/*  76:    */     throws CannotCompileException
/*  77:    */   {
/*  78:391 */     this.transformers = new TransformCall(this.transformers, oldMethodName, newMethod);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void insertBeforeMethod(CtMethod origMethod, CtMethod beforeMethod)
/*  82:    */     throws CannotCompileException
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:434 */       this.transformers = new TransformBefore(this.transformers, origMethod, beforeMethod);
/*  87:    */     }
/*  88:    */     catch (NotFoundException e)
/*  89:    */     {
/*  90:438 */       throw new CannotCompileException(e);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void insertAfterMethod(CtMethod origMethod, CtMethod afterMethod)
/*  95:    */     throws CannotCompileException
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:481 */       this.transformers = new TransformAfter(this.transformers, origMethod, afterMethod);
/* 100:    */     }
/* 101:    */     catch (NotFoundException e)
/* 102:    */     {
/* 103:485 */       throw new CannotCompileException(e);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void doit(CtClass clazz, MethodInfo minfo, ConstPool cp)
/* 108:    */     throws CannotCompileException
/* 109:    */   {
/* 110:496 */     CodeAttribute codeAttr = minfo.getCodeAttribute();
/* 111:497 */     if ((codeAttr == null) || (this.transformers == null)) {
/* 112:498 */       return;
/* 113:    */     }
/* 114:499 */     for (Transformer t = this.transformers; t != null; t = t.getNext()) {
/* 115:500 */       t.initialize(cp, clazz, minfo);
/* 116:    */     }
/* 117:502 */     CodeIterator iterator = codeAttr.iterator();
/* 118:503 */     while (iterator.hasNext()) {
/* 119:    */       try
/* 120:    */       {
/* 121:505 */         int pos = iterator.next();
/* 122:506 */         for (t = this.transformers; t != null; t = t.getNext()) {
/* 123:507 */           pos = t.transform(clazz, pos, iterator, cp);
/* 124:    */         }
/* 125:    */       }
/* 126:    */       catch (BadBytecode e)
/* 127:    */       {
/* 128:510 */         throw new CannotCompileException(e);
/* 129:    */       }
/* 130:    */     }
/* 131:514 */     int locals = 0;
/* 132:515 */     int stack = 0;
/* 133:516 */     for (t = this.transformers; t != null; t = t.getNext())
/* 134:    */     {
/* 135:517 */       int s = t.extraLocals();
/* 136:518 */       if (s > locals) {
/* 137:519 */         locals = s;
/* 138:    */       }
/* 139:521 */       s = t.extraStack();
/* 140:522 */       if (s > stack) {
/* 141:523 */         stack = s;
/* 142:    */       }
/* 143:    */     }
/* 144:526 */     for (t = this.transformers; t != null; t = t.getNext()) {
/* 145:527 */       t.clean();
/* 146:    */     }
/* 147:529 */     if (locals > 0) {
/* 148:530 */       codeAttr.setMaxLocals(codeAttr.getMaxLocals() + locals);
/* 149:    */     }
/* 150:532 */     if (stack > 0) {
/* 151:533 */       codeAttr.setMaxStack(codeAttr.getMaxStack() + stack);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static class DefaultArrayAccessReplacementMethodNames
/* 156:    */     implements CodeConverter.ArrayAccessReplacementMethodNames
/* 157:    */   {
/* 158:    */     public String byteOrBooleanRead()
/* 159:    */     {
/* 160:661 */       return "arrayReadByteOrBoolean";
/* 161:    */     }
/* 162:    */     
/* 163:    */     public String byteOrBooleanWrite()
/* 164:    */     {
/* 165:670 */       return "arrayWriteByteOrBoolean";
/* 166:    */     }
/* 167:    */     
/* 168:    */     public String charRead()
/* 169:    */     {
/* 170:679 */       return "arrayReadChar";
/* 171:    */     }
/* 172:    */     
/* 173:    */     public String charWrite()
/* 174:    */     {
/* 175:688 */       return "arrayWriteChar";
/* 176:    */     }
/* 177:    */     
/* 178:    */     public String doubleRead()
/* 179:    */     {
/* 180:697 */       return "arrayReadDouble";
/* 181:    */     }
/* 182:    */     
/* 183:    */     public String doubleWrite()
/* 184:    */     {
/* 185:706 */       return "arrayWriteDouble";
/* 186:    */     }
/* 187:    */     
/* 188:    */     public String floatRead()
/* 189:    */     {
/* 190:715 */       return "arrayReadFloat";
/* 191:    */     }
/* 192:    */     
/* 193:    */     public String floatWrite()
/* 194:    */     {
/* 195:724 */       return "arrayWriteFloat";
/* 196:    */     }
/* 197:    */     
/* 198:    */     public String intRead()
/* 199:    */     {
/* 200:733 */       return "arrayReadInt";
/* 201:    */     }
/* 202:    */     
/* 203:    */     public String intWrite()
/* 204:    */     {
/* 205:742 */       return "arrayWriteInt";
/* 206:    */     }
/* 207:    */     
/* 208:    */     public String longRead()
/* 209:    */     {
/* 210:751 */       return "arrayReadLong";
/* 211:    */     }
/* 212:    */     
/* 213:    */     public String longWrite()
/* 214:    */     {
/* 215:760 */       return "arrayWriteLong";
/* 216:    */     }
/* 217:    */     
/* 218:    */     public String objectRead()
/* 219:    */     {
/* 220:769 */       return "arrayReadObject";
/* 221:    */     }
/* 222:    */     
/* 223:    */     public String objectWrite()
/* 224:    */     {
/* 225:778 */       return "arrayWriteObject";
/* 226:    */     }
/* 227:    */     
/* 228:    */     public String shortRead()
/* 229:    */     {
/* 230:787 */       return "arrayReadShort";
/* 231:    */     }
/* 232:    */     
/* 233:    */     public String shortWrite()
/* 234:    */     {
/* 235:796 */       return "arrayWriteShort";
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public static abstract interface ArrayAccessReplacementMethodNames
/* 240:    */   {
/* 241:    */     public abstract String byteOrBooleanRead();
/* 242:    */     
/* 243:    */     public abstract String byteOrBooleanWrite();
/* 244:    */     
/* 245:    */     public abstract String charRead();
/* 246:    */     
/* 247:    */     public abstract String charWrite();
/* 248:    */     
/* 249:    */     public abstract String doubleRead();
/* 250:    */     
/* 251:    */     public abstract String doubleWrite();
/* 252:    */     
/* 253:    */     public abstract String floatRead();
/* 254:    */     
/* 255:    */     public abstract String floatWrite();
/* 256:    */     
/* 257:    */     public abstract String intRead();
/* 258:    */     
/* 259:    */     public abstract String intWrite();
/* 260:    */     
/* 261:    */     public abstract String longRead();
/* 262:    */     
/* 263:    */     public abstract String longWrite();
/* 264:    */     
/* 265:    */     public abstract String objectRead();
/* 266:    */     
/* 267:    */     public abstract String objectWrite();
/* 268:    */     
/* 269:    */     public abstract String shortRead();
/* 270:    */     
/* 271:    */     public abstract String shortWrite();
/* 272:    */   }
/* 273:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CodeConverter
 * JD-Core Version:    0.7.0.1
 */