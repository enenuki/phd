/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   6:    */ import org.apache.bcel.generic.InstructionList;
/*   7:    */ import org.apache.bcel.generic.MethodGen;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  13:    */ 
/*  14:    */ final class ApplyImports
/*  15:    */   extends Instruction
/*  16:    */ {
/*  17:    */   private QName _modeName;
/*  18:    */   private int _precedence;
/*  19:    */   
/*  20:    */   public void display(int indent)
/*  21:    */   {
/*  22: 39 */     indent(indent);
/*  23: 40 */     Util.println("ApplyTemplates");
/*  24: 41 */     indent(indent + 4);
/*  25: 42 */     if (this._modeName != null)
/*  26:    */     {
/*  27: 43 */       indent(indent + 4);
/*  28: 44 */       Util.println("mode " + this._modeName);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean hasWithParams()
/*  33:    */   {
/*  34: 52 */     return hasContents();
/*  35:    */   }
/*  36:    */   
/*  37:    */   private int getMinPrecedence(int max)
/*  38:    */   {
/*  39: 64 */     Stylesheet includeRoot = getStylesheet();
/*  40: 65 */     while (includeRoot._includedFrom != null) {
/*  41: 66 */       includeRoot = includeRoot._includedFrom;
/*  42:    */     }
/*  43: 69 */     return includeRoot.getMinimumDescendantPrecedence();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void parseContents(Parser parser)
/*  47:    */   {
/*  48: 78 */     Stylesheet stylesheet = getStylesheet();
/*  49: 79 */     stylesheet.setTemplateInlining(false);
/*  50:    */     
/*  51:    */ 
/*  52: 82 */     Template template = getTemplate();
/*  53: 83 */     this._modeName = template.getModeName();
/*  54: 84 */     this._precedence = template.getImportPrecedence();
/*  55:    */     
/*  56: 86 */     parseChildren(parser);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Type typeCheck(SymbolTable stable)
/*  60:    */     throws TypeCheckError
/*  61:    */   {
/*  62: 93 */     typeCheckContents(stable);
/*  63: 94 */     return Type.Void;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  67:    */   {
/*  68:102 */     Stylesheet stylesheet = classGen.getStylesheet();
/*  69:103 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  70:104 */     InstructionList il = methodGen.getInstructionList();
/*  71:105 */     int current = methodGen.getLocalIndex("current");
/*  72:    */     
/*  73:    */ 
/*  74:108 */     il.append(classGen.loadTranslet());
/*  75:109 */     il.append(methodGen.loadDOM());
/*  76:110 */     il.append(methodGen.loadIterator());
/*  77:111 */     il.append(methodGen.loadHandler());
/*  78:112 */     il.append(methodGen.loadCurrentNode());
/*  79:116 */     if (stylesheet.hasLocalParams())
/*  80:    */     {
/*  81:117 */       il.append(classGen.loadTranslet());
/*  82:118 */       int pushFrame = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
/*  83:    */       
/*  84:    */ 
/*  85:121 */       il.append(new INVOKEVIRTUAL(pushFrame));
/*  86:    */     }
/*  87:126 */     int maxPrecedence = this._precedence;
/*  88:127 */     int minPrecedence = getMinPrecedence(maxPrecedence);
/*  89:128 */     Mode mode = stylesheet.getMode(this._modeName);
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93:132 */     String functionName = mode.functionName(minPrecedence, maxPrecedence);
/*  94:    */     
/*  95:    */ 
/*  96:135 */     String className = classGen.getStylesheet().getClassName();
/*  97:136 */     String signature = classGen.getApplyTemplatesSigForImport();
/*  98:137 */     int applyTemplates = cpg.addMethodref(className, functionName, signature);
/*  99:    */     
/* 100:    */ 
/* 101:140 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/* 102:143 */     if (stylesheet.hasLocalParams())
/* 103:    */     {
/* 104:144 */       il.append(classGen.loadTranslet());
/* 105:145 */       int pushFrame = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
/* 106:    */       
/* 107:    */ 
/* 108:148 */       il.append(new INVOKEVIRTUAL(pushFrame));
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ApplyImports
 * JD-Core Version:    0.7.0.1
 */