/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.DataOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import javassist.bytecode.ClassFile;
/*   6:    */ 
/*   7:    */ class CtNewClass
/*   8:    */   extends CtClassType
/*   9:    */ {
/*  10:    */   protected boolean hasConstructor;
/*  11:    */   
/*  12:    */   CtNewClass(String name, ClassPool cp, boolean isInterface, CtClass superclass)
/*  13:    */   {
/*  14: 29 */     super(name, cp);
/*  15: 30 */     this.wasChanged = true;
/*  16:    */     String superName;
/*  17:    */     String superName;
/*  18: 32 */     if ((isInterface) || (superclass == null)) {
/*  19: 33 */       superName = null;
/*  20:    */     } else {
/*  21: 35 */       superName = superclass.getName();
/*  22:    */     }
/*  23: 37 */     this.classfile = new ClassFile(isInterface, name, superName);
/*  24: 38 */     if ((isInterface) && (superclass != null)) {
/*  25: 39 */       this.classfile.setInterfaces(new String[] { superclass.getName() });
/*  26:    */     }
/*  27: 41 */     setModifiers(Modifier.setPublic(getModifiers()));
/*  28: 42 */     this.hasConstructor = isInterface;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void extendToString(StringBuffer buffer)
/*  32:    */   {
/*  33: 46 */     if (this.hasConstructor) {
/*  34: 47 */       buffer.append("hasConstructor ");
/*  35:    */     }
/*  36: 49 */     super.extendToString(buffer);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addConstructor(CtConstructor c)
/*  40:    */     throws CannotCompileException
/*  41:    */   {
/*  42: 55 */     this.hasConstructor = true;
/*  43: 56 */     super.addConstructor(c);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void toBytecode(DataOutputStream out)
/*  47:    */     throws CannotCompileException, IOException
/*  48:    */   {
/*  49: 62 */     if (!this.hasConstructor) {
/*  50:    */       try
/*  51:    */       {
/*  52: 64 */         inheritAllConstructors();
/*  53: 65 */         this.hasConstructor = true;
/*  54:    */       }
/*  55:    */       catch (NotFoundException e)
/*  56:    */       {
/*  57: 68 */         throw new CannotCompileException(e);
/*  58:    */       }
/*  59:    */     }
/*  60: 71 */     super.toBytecode(out);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void inheritAllConstructors()
/*  64:    */     throws CannotCompileException, NotFoundException
/*  65:    */   {
/*  66: 87 */     CtClass superclazz = getSuperclass();
/*  67: 88 */     CtConstructor[] cs = superclazz.getDeclaredConstructors();
/*  68:    */     
/*  69: 90 */     int n = 0;
/*  70: 91 */     for (int i = 0; i < cs.length; i++)
/*  71:    */     {
/*  72: 92 */       CtConstructor c = cs[i];
/*  73: 93 */       int mod = c.getModifiers();
/*  74: 94 */       if (isInheritable(mod, superclazz))
/*  75:    */       {
/*  76: 95 */         CtConstructor cons = CtNewConstructor.make(c.getParameterTypes(), c.getExceptionTypes(), this);
/*  77:    */         
/*  78:    */ 
/*  79: 98 */         cons.setModifiers(mod & 0x7);
/*  80: 99 */         addConstructor(cons);
/*  81:100 */         n++;
/*  82:    */       }
/*  83:    */     }
/*  84:104 */     if (n < 1) {
/*  85:105 */       throw new CannotCompileException("no inheritable constructor in " + superclazz.getName());
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private boolean isInheritable(int mod, CtClass superclazz)
/*  90:    */   {
/*  91:111 */     if (Modifier.isPrivate(mod)) {
/*  92:112 */       return false;
/*  93:    */     }
/*  94:114 */     if (Modifier.isPackage(mod))
/*  95:    */     {
/*  96:115 */       String pname = getPackageName();
/*  97:116 */       String pname2 = superclazz.getPackageName();
/*  98:117 */       if (pname == null) {
/*  99:118 */         return pname2 == null;
/* 100:    */       }
/* 101:120 */       return pname.equals(pname2);
/* 102:    */     }
/* 103:123 */     return true;
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewClass
 * JD-Core Version:    0.7.0.1
 */