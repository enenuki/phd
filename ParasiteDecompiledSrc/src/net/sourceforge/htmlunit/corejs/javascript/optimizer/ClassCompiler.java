/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.CompilerEnvirons;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.IRFactory;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaAdapter;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Parser;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*  12:    */ 
/*  13:    */ public class ClassCompiler
/*  14:    */ {
/*  15:    */   private String mainMethodClassName;
/*  16:    */   private CompilerEnvirons compilerEnv;
/*  17:    */   private Class<?> targetExtends;
/*  18:    */   private Class<?>[] targetImplements;
/*  19:    */   
/*  20:    */   public ClassCompiler(CompilerEnvirons compilerEnv)
/*  21:    */   {
/*  22: 61 */     if (compilerEnv == null) {
/*  23: 61 */       throw new IllegalArgumentException();
/*  24:    */     }
/*  25: 62 */     this.compilerEnv = compilerEnv;
/*  26: 63 */     this.mainMethodClassName = "net.sourceforge.htmlunit.corejs.javascript.optimizer.OptRuntime";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setMainMethodClass(String className)
/*  30:    */   {
/*  31: 77 */     this.mainMethodClassName = className;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getMainMethodClass()
/*  35:    */   {
/*  36: 86 */     return this.mainMethodClassName;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public CompilerEnvirons getCompilerEnv()
/*  40:    */   {
/*  41: 94 */     return this.compilerEnv;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Class<?> getTargetExtends()
/*  45:    */   {
/*  46:102 */     return this.targetExtends;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setTargetExtends(Class<?> extendsClass)
/*  50:    */   {
/*  51:112 */     this.targetExtends = extendsClass;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Class<?>[] getTargetImplements()
/*  55:    */   {
/*  56:120 */     return this.targetImplements == null ? null : (Class[])this.targetImplements.clone();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setTargetImplements(Class<?>[] implementsClasses)
/*  60:    */   {
/*  61:131 */     this.targetImplements = (implementsClasses == null ? null : (Class[])implementsClasses.clone());
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected String makeAuxiliaryClassName(String mainClassName, String auxMarker)
/*  65:    */   {
/*  66:144 */     return mainClassName + auxMarker;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object[] compileToClassFiles(String source, String sourceLocation, int lineno, String mainClassName)
/*  70:    */   {
/*  71:165 */     Parser p = new Parser(this.compilerEnv);
/*  72:166 */     AstRoot ast = p.parse(source, sourceLocation, lineno);
/*  73:167 */     IRFactory irf = new IRFactory(this.compilerEnv);
/*  74:168 */     ScriptNode tree = irf.transformTree(ast);
/*  75:    */     
/*  76:    */ 
/*  77:171 */     irf = null;
/*  78:172 */     ast = null;
/*  79:173 */     p = null;
/*  80:    */     
/*  81:175 */     Class<?> superClass = getTargetExtends();
/*  82:176 */     Class<?>[] interfaces = getTargetImplements();
/*  83:    */     
/*  84:178 */     boolean isPrimary = (interfaces == null) && (superClass == null);
/*  85:    */     String scriptClassName;
/*  86:    */     String scriptClassName;
/*  87:179 */     if (isPrimary) {
/*  88:180 */       scriptClassName = mainClassName;
/*  89:    */     } else {
/*  90:182 */       scriptClassName = makeAuxiliaryClassName(mainClassName, "1");
/*  91:    */     }
/*  92:185 */     Codegen codegen = new Codegen();
/*  93:186 */     codegen.setMainMethodClass(this.mainMethodClassName);
/*  94:187 */     byte[] scriptClassBytes = codegen.compileToClassFile(this.compilerEnv, scriptClassName, tree, tree.getEncodedSource(), false);
/*  95:192 */     if (isPrimary) {
/*  96:193 */       return new Object[] { scriptClassName, scriptClassBytes };
/*  97:    */     }
/*  98:195 */     int functionCount = tree.getFunctionCount();
/*  99:196 */     ObjToIntMap functionNames = new ObjToIntMap(functionCount);
/* 100:197 */     for (int i = 0; i != functionCount; i++)
/* 101:    */     {
/* 102:198 */       FunctionNode ofn = tree.getFunctionNode(i);
/* 103:199 */       String name = ofn.getName();
/* 104:200 */       if ((name != null) && (name.length() != 0)) {
/* 105:201 */         functionNames.put(name, ofn.getParamCount());
/* 106:    */       }
/* 107:    */     }
/* 108:204 */     if (superClass == null) {
/* 109:205 */       superClass = ScriptRuntime.ObjectClass;
/* 110:    */     }
/* 111:207 */     byte[] mainClassBytes = JavaAdapter.createAdapterCode(functionNames, mainClassName, superClass, interfaces, scriptClassName);
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:212 */     return new Object[] { mainClassName, mainClassBytes, scriptClassName, scriptClassBytes };
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.ClassCompiler
 * JD-Core Version:    0.7.0.1
 */