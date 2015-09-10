/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.Bytecode;
/*   4:    */ import javassist.bytecode.ClassFile;
/*   5:    */ import javassist.bytecode.ConstPool;
/*   6:    */ import javassist.bytecode.MethodInfo;
/*   7:    */ import javassist.compiler.CompileError;
/*   8:    */ import javassist.compiler.Javac;
/*   9:    */ 
/*  10:    */ public class CtNewConstructor
/*  11:    */ {
/*  12:    */   public static final int PASS_NONE = 0;
/*  13:    */   public static final int PASS_ARRAY = 1;
/*  14:    */   public static final int PASS_PARAMS = 2;
/*  15:    */   
/*  16:    */   public static CtConstructor make(String src, CtClass declaring)
/*  17:    */     throws CannotCompileException
/*  18:    */   {
/*  19: 67 */     Javac compiler = new Javac(declaring);
/*  20:    */     try
/*  21:    */     {
/*  22: 69 */       CtMember obj = compiler.compile(src);
/*  23: 70 */       if ((obj instanceof CtConstructor)) {
/*  24: 72 */         return (CtConstructor)obj;
/*  25:    */       }
/*  26:    */     }
/*  27:    */     catch (CompileError e)
/*  28:    */     {
/*  29: 76 */       throw new CannotCompileException(e);
/*  30:    */     }
/*  31: 79 */     throw new CannotCompileException("not a constructor");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, String body, CtClass declaring)
/*  35:    */     throws CannotCompileException
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39:100 */       CtConstructor cc = new CtConstructor(parameters, declaring);
/*  40:101 */       cc.setExceptionTypes(exceptions);
/*  41:102 */       cc.setBody(body);
/*  42:103 */       return cc;
/*  43:    */     }
/*  44:    */     catch (NotFoundException e)
/*  45:    */     {
/*  46:106 */       throw new CannotCompileException(e);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static CtConstructor copy(CtConstructor c, CtClass declaring, ClassMap map)
/*  51:    */     throws CannotCompileException
/*  52:    */   {
/*  53:126 */     return new CtConstructor(c, declaring, map);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static CtConstructor defaultConstructor(CtClass declaring)
/*  57:    */     throws CannotCompileException
/*  58:    */   {
/*  59:138 */     CtConstructor cons = new CtConstructor((CtClass[])null, declaring);
/*  60:    */     
/*  61:140 */     ConstPool cp = declaring.getClassFile2().getConstPool();
/*  62:141 */     Bytecode code = new Bytecode(cp, 1, 1);
/*  63:142 */     code.addAload(0);
/*  64:    */     try
/*  65:    */     {
/*  66:144 */       code.addInvokespecial(declaring.getSuperclass(), "<init>", "()V");
/*  67:    */     }
/*  68:    */     catch (NotFoundException e)
/*  69:    */     {
/*  70:148 */       throw new CannotCompileException(e);
/*  71:    */     }
/*  72:151 */     code.add(177);
/*  73:    */     
/*  74:    */ 
/*  75:154 */     cons.getMethodInfo2().setCodeAttribute(code.toCodeAttribute());
/*  76:155 */     return cons;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static CtConstructor skeleton(CtClass[] parameters, CtClass[] exceptions, CtClass declaring)
/*  80:    */     throws CannotCompileException
/*  81:    */   {
/*  82:180 */     return make(parameters, exceptions, 0, null, null, declaring);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, CtClass declaring)
/*  86:    */     throws CannotCompileException
/*  87:    */   {
/*  88:199 */     return make(parameters, exceptions, 2, null, null, declaring);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static CtConstructor make(CtClass[] parameters, CtClass[] exceptions, int howto, CtMethod body, CtMethod.ConstParameter cparam, CtClass declaring)
/*  92:    */     throws CannotCompileException
/*  93:    */   {
/*  94:313 */     return CtNewWrappedConstructor.wrapped(parameters, exceptions, howto, body, cparam, declaring);
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtNewConstructor
 * JD-Core Version:    0.7.0.1
 */