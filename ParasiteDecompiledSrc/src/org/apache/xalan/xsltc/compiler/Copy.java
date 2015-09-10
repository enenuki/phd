/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.IFEQ;
/*   9:    */ import org.apache.bcel.generic.IFNULL;
/*  10:    */ import org.apache.bcel.generic.ILOAD;
/*  11:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.ISTORE;
/*  14:    */ import org.apache.bcel.generic.InstructionConstants;
/*  15:    */ import org.apache.bcel.generic.InstructionHandle;
/*  16:    */ import org.apache.bcel.generic.InstructionList;
/*  17:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  18:    */ import org.apache.bcel.generic.MethodGen;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  25:    */ 
/*  26:    */ final class Copy
/*  27:    */   extends Instruction
/*  28:    */ {
/*  29:    */   private UseAttributeSets _useSets;
/*  30:    */   
/*  31:    */   public void parseContents(Parser parser)
/*  32:    */   {
/*  33: 52 */     String useSets = getAttribute("use-attribute-sets");
/*  34: 53 */     if (useSets.length() > 0)
/*  35:    */     {
/*  36: 54 */       if (!Util.isValidQNames(useSets))
/*  37:    */       {
/*  38: 55 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
/*  39: 56 */         parser.reportError(3, err);
/*  40:    */       }
/*  41: 58 */       this._useSets = new UseAttributeSets(useSets, parser);
/*  42:    */     }
/*  43: 60 */     parseChildren(parser);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void display(int indent)
/*  47:    */   {
/*  48: 64 */     indent(indent);
/*  49: 65 */     Util.println("Copy");
/*  50: 66 */     indent(indent + 4);
/*  51: 67 */     displayContents(indent + 4);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Type typeCheck(SymbolTable stable)
/*  55:    */     throws TypeCheckError
/*  56:    */   {
/*  57: 71 */     if (this._useSets != null) {
/*  58: 72 */       this._useSets.typeCheck(stable);
/*  59:    */     }
/*  60: 74 */     typeCheckContents(stable);
/*  61: 75 */     return Type.Void;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  65:    */   {
/*  66: 79 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  67: 80 */     InstructionList il = methodGen.getInstructionList();
/*  68:    */     
/*  69: 82 */     LocalVariableGen name = methodGen.addLocalVariable2("name", Util.getJCRefType("Ljava/lang/String;"), null);
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73: 86 */     LocalVariableGen length = methodGen.addLocalVariable2("length", Util.getJCRefType("I"), null);
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79: 92 */     il.append(methodGen.loadDOM());
/*  80: 93 */     il.append(methodGen.loadCurrentNode());
/*  81: 94 */     il.append(methodGen.loadHandler());
/*  82: 95 */     int cpy = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "shallowCopy", "(I" + Constants.TRANSLET_OUTPUT_SIG + ")" + "Ljava/lang/String;");
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:101 */     il.append(new INVOKEINTERFACE(cpy, 3));
/*  89:102 */     il.append(InstructionConstants.DUP);
/*  90:103 */     name.setStart(il.append(new ASTORE(name.getIndex())));
/*  91:104 */     BranchHandle ifBlock1 = il.append(new IFNULL(null));
/*  92:    */     
/*  93:    */ 
/*  94:107 */     il.append(new ALOAD(name.getIndex()));
/*  95:108 */     int lengthMethod = cpg.addMethodref("java.lang.String", "length", "()I");
/*  96:109 */     il.append(new INVOKEVIRTUAL(lengthMethod));
/*  97:110 */     length.setStart(il.append(new ISTORE(length.getIndex())));
/*  98:113 */     if (this._useSets != null)
/*  99:    */     {
/* 100:116 */       SyntaxTreeNode parent = getParent();
/* 101:117 */       if (((parent instanceof LiteralElement)) || ((parent instanceof LiteralElement)))
/* 102:    */       {
/* 103:119 */         this._useSets.translate(classGen, methodGen);
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:125 */         il.append(new ILOAD(length.getIndex()));
/* 108:126 */         BranchHandle ifBlock2 = il.append(new IFEQ(null));
/* 109:    */         
/* 110:128 */         this._useSets.translate(classGen, methodGen);
/* 111:    */         
/* 112:130 */         ifBlock2.setTarget(il.append(InstructionConstants.NOP));
/* 113:    */       }
/* 114:    */     }
/* 115:135 */     translateContents(classGen, methodGen);
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:139 */     length.setEnd(il.append(new ILOAD(length.getIndex())));
/* 120:140 */     BranchHandle ifBlock3 = il.append(new IFEQ(null));
/* 121:141 */     il.append(methodGen.loadHandler());
/* 122:142 */     name.setEnd(il.append(new ALOAD(name.getIndex())));
/* 123:143 */     il.append(methodGen.endElement());
/* 124:    */     
/* 125:145 */     InstructionHandle end = il.append(InstructionConstants.NOP);
/* 126:146 */     ifBlock1.setTarget(end);
/* 127:147 */     ifBlock3.setTarget(end);
/* 128:148 */     methodGen.removeLocalVariable(name);
/* 129:149 */     methodGen.removeLocalVariable(length);
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Copy
 * JD-Core Version:    0.7.0.1
 */