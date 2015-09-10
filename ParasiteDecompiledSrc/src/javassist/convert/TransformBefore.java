/*   1:    */ package javassist.convert;
/*   2:    */ 
/*   3:    */ import javassist.CtClass;
/*   4:    */ import javassist.CtMethod;
/*   5:    */ import javassist.NotFoundException;
/*   6:    */ import javassist.bytecode.BadBytecode;
/*   7:    */ import javassist.bytecode.Bytecode;
/*   8:    */ import javassist.bytecode.CodeAttribute;
/*   9:    */ import javassist.bytecode.CodeIterator;
/*  10:    */ import javassist.bytecode.ConstPool;
/*  11:    */ import javassist.bytecode.Descriptor;
/*  12:    */ import javassist.bytecode.MethodInfo;
/*  13:    */ 
/*  14:    */ public class TransformBefore
/*  15:    */   extends TransformCall
/*  16:    */ {
/*  17:    */   protected CtClass[] parameterTypes;
/*  18:    */   protected int locals;
/*  19:    */   protected int maxLocals;
/*  20:    */   protected byte[] saveCode;
/*  21:    */   protected byte[] loadCode;
/*  22:    */   
/*  23:    */   public TransformBefore(Transformer next, CtMethod origMethod, CtMethod beforeMethod)
/*  24:    */     throws NotFoundException
/*  25:    */   {
/*  26: 33 */     super(next, origMethod, beforeMethod);
/*  27:    */     
/*  28:    */ 
/*  29: 36 */     this.methodDescriptor = origMethod.getMethodInfo2().getDescriptor();
/*  30:    */     
/*  31: 38 */     this.parameterTypes = origMethod.getParameterTypes();
/*  32: 39 */     this.locals = 0;
/*  33: 40 */     this.maxLocals = 0;
/*  34: 41 */     this.saveCode = (this.loadCode = null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void initialize(ConstPool cp, CodeAttribute attr)
/*  38:    */   {
/*  39: 45 */     super.initialize(cp, attr);
/*  40: 46 */     this.locals = 0;
/*  41: 47 */     this.maxLocals = attr.getMaxLocals();
/*  42: 48 */     this.saveCode = (this.loadCode = null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected int match(int c, int pos, CodeIterator iterator, int typedesc, ConstPool cp)
/*  46:    */     throws BadBytecode
/*  47:    */   {
/*  48: 54 */     if (this.newIndex == 0)
/*  49:    */     {
/*  50: 55 */       String desc = Descriptor.ofParameters(this.parameterTypes) + 'V';
/*  51: 56 */       desc = Descriptor.insertParameter(this.classname, desc);
/*  52: 57 */       int nt = cp.addNameAndTypeInfo(this.newMethodname, desc);
/*  53: 58 */       int ci = cp.addClassInfo(this.newClassname);
/*  54: 59 */       this.newIndex = cp.addMethodrefInfo(ci, nt);
/*  55: 60 */       this.constPool = cp;
/*  56:    */     }
/*  57: 63 */     if (this.saveCode == null) {
/*  58: 64 */       makeCode(this.parameterTypes, cp);
/*  59:    */     }
/*  60: 66 */     return match2(pos, iterator);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected int match2(int pos, CodeIterator iterator)
/*  64:    */     throws BadBytecode
/*  65:    */   {
/*  66: 70 */     iterator.move(pos);
/*  67: 71 */     iterator.insert(this.saveCode);
/*  68: 72 */     iterator.insert(this.loadCode);
/*  69: 73 */     int p = iterator.insertGap(3);
/*  70: 74 */     iterator.writeByte(184, p);
/*  71: 75 */     iterator.write16bit(this.newIndex, p + 1);
/*  72: 76 */     iterator.insert(this.loadCode);
/*  73: 77 */     return iterator.next();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int extraLocals()
/*  77:    */   {
/*  78: 80 */     return this.locals;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void makeCode(CtClass[] paramTypes, ConstPool cp)
/*  82:    */   {
/*  83: 83 */     Bytecode save = new Bytecode(cp, 0, 0);
/*  84: 84 */     Bytecode load = new Bytecode(cp, 0, 0);
/*  85:    */     
/*  86: 86 */     int var = this.maxLocals;
/*  87: 87 */     int len = paramTypes == null ? 0 : paramTypes.length;
/*  88: 88 */     load.addAload(var);
/*  89: 89 */     makeCode2(save, load, 0, len, paramTypes, var + 1);
/*  90: 90 */     save.addAstore(var);
/*  91:    */     
/*  92: 92 */     this.saveCode = save.get();
/*  93: 93 */     this.loadCode = load.get();
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void makeCode2(Bytecode save, Bytecode load, int i, int n, CtClass[] paramTypes, int var)
/*  97:    */   {
/*  98: 99 */     if (i < n)
/*  99:    */     {
/* 100:100 */       int size = load.addLoad(var, paramTypes[i]);
/* 101:101 */       makeCode2(save, load, i + 1, n, paramTypes, var + size);
/* 102:102 */       save.addStore(var, paramTypes[i]);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:105 */       this.locals = (var - this.maxLocals);
/* 107:    */     }
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformBefore
 * JD-Core Version:    0.7.0.1
 */