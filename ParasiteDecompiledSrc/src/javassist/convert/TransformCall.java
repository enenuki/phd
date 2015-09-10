/*   1:    */ package javassist.convert;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.CtMethod;
/*   6:    */ import javassist.Modifier;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.BadBytecode;
/*   9:    */ import javassist.bytecode.CodeAttribute;
/*  10:    */ import javassist.bytecode.CodeIterator;
/*  11:    */ import javassist.bytecode.ConstPool;
/*  12:    */ import javassist.bytecode.MethodInfo;
/*  13:    */ 
/*  14:    */ public class TransformCall
/*  15:    */   extends Transformer
/*  16:    */ {
/*  17:    */   protected String classname;
/*  18:    */   protected String methodname;
/*  19:    */   protected String methodDescriptor;
/*  20:    */   protected String newClassname;
/*  21:    */   protected String newMethodname;
/*  22:    */   protected boolean newMethodIsPrivate;
/*  23:    */   protected int newIndex;
/*  24:    */   protected ConstPool constPool;
/*  25:    */   
/*  26:    */   public TransformCall(Transformer next, CtMethod origMethod, CtMethod substMethod)
/*  27:    */   {
/*  28: 37 */     this(next, origMethod.getName(), substMethod);
/*  29: 38 */     this.classname = origMethod.getDeclaringClass().getName();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public TransformCall(Transformer next, String oldMethodName, CtMethod substMethod)
/*  33:    */   {
/*  34: 44 */     super(next);
/*  35: 45 */     this.methodname = oldMethodName;
/*  36: 46 */     this.methodDescriptor = substMethod.getMethodInfo2().getDescriptor();
/*  37: 47 */     this.classname = (this.newClassname = substMethod.getDeclaringClass().getName());
/*  38: 48 */     this.newMethodname = substMethod.getName();
/*  39: 49 */     this.constPool = null;
/*  40: 50 */     this.newMethodIsPrivate = Modifier.isPrivate(substMethod.getModifiers());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void initialize(ConstPool cp, CodeAttribute attr)
/*  44:    */   {
/*  45: 54 */     if (this.constPool != cp) {
/*  46: 55 */       this.newIndex = 0;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp)
/*  51:    */     throws BadBytecode
/*  52:    */   {
/*  53: 68 */     int c = iterator.byteAt(pos);
/*  54: 69 */     if ((c == 185) || (c == 183) || (c == 184) || (c == 182))
/*  55:    */     {
/*  56: 71 */       int index = iterator.u16bitAt(pos + 1);
/*  57: 72 */       String cname = cp.eqMember(this.methodname, this.methodDescriptor, index);
/*  58: 73 */       if ((cname != null) && (matchClass(cname, clazz.getClassPool())))
/*  59:    */       {
/*  60: 74 */         int ntinfo = cp.getMemberNameAndType(index);
/*  61: 75 */         pos = match(c, pos, iterator, cp.getNameAndTypeDescriptor(ntinfo), cp);
/*  62:    */       }
/*  63:    */     }
/*  64: 80 */     return pos;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private boolean matchClass(String name, ClassPool pool)
/*  68:    */   {
/*  69: 84 */     if (this.classname.equals(name)) {
/*  70: 85 */       return true;
/*  71:    */     }
/*  72:    */     try
/*  73:    */     {
/*  74: 88 */       CtClass clazz = pool.get(name);
/*  75: 89 */       CtClass declClazz = pool.get(this.classname);
/*  76: 90 */       if (clazz.subtypeOf(declClazz)) {
/*  77:    */         try
/*  78:    */         {
/*  79: 92 */           CtMethod m = clazz.getMethod(this.methodname, this.methodDescriptor);
/*  80: 93 */           return m.getDeclaringClass().getName().equals(this.classname);
/*  81:    */         }
/*  82:    */         catch (NotFoundException e)
/*  83:    */         {
/*  84: 97 */           return true;
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */     catch (NotFoundException e)
/*  89:    */     {
/*  90:101 */       return false;
/*  91:    */     }
/*  92:104 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected int match(int c, int pos, CodeIterator iterator, int typedesc, ConstPool cp)
/*  96:    */     throws BadBytecode
/*  97:    */   {
/*  98:110 */     if (this.newIndex == 0)
/*  99:    */     {
/* 100:111 */       int nt = cp.addNameAndTypeInfo(cp.addUtf8Info(this.newMethodname), typedesc);
/* 101:    */       
/* 102:113 */       int ci = cp.addClassInfo(this.newClassname);
/* 103:114 */       if (c == 185)
/* 104:    */       {
/* 105:115 */         this.newIndex = cp.addInterfaceMethodrefInfo(ci, nt);
/* 106:    */       }
/* 107:    */       else
/* 108:    */       {
/* 109:117 */         if ((this.newMethodIsPrivate) && (c == 182)) {
/* 110:118 */           iterator.writeByte(183, pos);
/* 111:    */         }
/* 112:120 */         this.newIndex = cp.addMethodrefInfo(ci, nt);
/* 113:    */       }
/* 114:123 */       this.constPool = cp;
/* 115:    */     }
/* 116:126 */     iterator.write16bit(this.newIndex, pos + 1);
/* 117:127 */     return pos;
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.convert.TransformCall
 * JD-Core Version:    0.7.0.1
 */