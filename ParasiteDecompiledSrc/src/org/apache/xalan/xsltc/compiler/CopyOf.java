/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   6:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  20:    */ 
/*  21:    */ final class CopyOf
/*  22:    */   extends Instruction
/*  23:    */ {
/*  24:    */   private Expression _select;
/*  25:    */   
/*  26:    */   public void display(int indent)
/*  27:    */   {
/*  28: 48 */     indent(indent);
/*  29: 49 */     Util.println("CopyOf");
/*  30: 50 */     indent(indent + 4);
/*  31: 51 */     Util.println("select " + this._select.toString());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void parseContents(Parser parser)
/*  35:    */   {
/*  36: 55 */     this._select = parser.parseExpression(this, "select", null);
/*  37: 57 */     if (this._select.isDummy())
/*  38:    */     {
/*  39: 58 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/*  40: 59 */       return;
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Type typeCheck(SymbolTable stable)
/*  45:    */     throws TypeCheckError
/*  46:    */   {
/*  47: 64 */     Type tselect = this._select.typeCheck(stable);
/*  48: 65 */     if ((!(tselect instanceof NodeType)) && (!(tselect instanceof NodeSetType)) && (!(tselect instanceof ReferenceType)) && (!(tselect instanceof ResultTreeType))) {
/*  49: 72 */       this._select = new CastExpr(this._select, Type.String);
/*  50:    */     }
/*  51: 74 */     return Type.Void;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  55:    */   {
/*  56: 78 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  57: 79 */     InstructionList il = methodGen.getInstructionList();
/*  58: 80 */     Type tselect = this._select.getType();
/*  59:    */     
/*  60: 82 */     String CPY1_SIG = "(Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + ")V";
/*  61: 83 */     int cpy1 = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "copy", CPY1_SIG);
/*  62:    */     
/*  63: 85 */     String CPY2_SIG = "(I" + Constants.TRANSLET_OUTPUT_SIG + ")V";
/*  64: 86 */     int cpy2 = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "copy", CPY2_SIG);
/*  65:    */     
/*  66: 88 */     String getDoc_SIG = "()I";
/*  67: 89 */     int getDoc = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getDocument", "()I");
/*  68: 92 */     if ((tselect instanceof NodeSetType))
/*  69:    */     {
/*  70: 93 */       il.append(methodGen.loadDOM());
/*  71:    */       
/*  72:    */ 
/*  73: 96 */       this._select.translate(classGen, methodGen);
/*  74: 97 */       this._select.startIterator(classGen, methodGen);
/*  75:    */       
/*  76:    */ 
/*  77:100 */       il.append(methodGen.loadHandler());
/*  78:101 */       il.append(new INVOKEINTERFACE(cpy1, 3));
/*  79:    */     }
/*  80:103 */     else if ((tselect instanceof NodeType))
/*  81:    */     {
/*  82:104 */       il.append(methodGen.loadDOM());
/*  83:105 */       this._select.translate(classGen, methodGen);
/*  84:106 */       il.append(methodGen.loadHandler());
/*  85:107 */       il.append(new INVOKEINTERFACE(cpy2, 3));
/*  86:    */     }
/*  87:109 */     else if ((tselect instanceof ResultTreeType))
/*  88:    */     {
/*  89:110 */       this._select.translate(classGen, methodGen);
/*  90:    */       
/*  91:112 */       il.append(InstructionConstants.DUP);
/*  92:113 */       il.append(new INVOKEINTERFACE(getDoc, 1));
/*  93:114 */       il.append(methodGen.loadHandler());
/*  94:115 */       il.append(new INVOKEINTERFACE(cpy2, 3));
/*  95:    */     }
/*  96:117 */     else if ((tselect instanceof ReferenceType))
/*  97:    */     {
/*  98:118 */       this._select.translate(classGen, methodGen);
/*  99:119 */       il.append(methodGen.loadHandler());
/* 100:120 */       il.append(methodGen.loadCurrentNode());
/* 101:121 */       il.append(methodGen.loadDOM());
/* 102:122 */       int copy = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "copy", "(Ljava/lang/Object;" + Constants.TRANSLET_OUTPUT_SIG + "I" + "Lorg/apache/xalan/xsltc/DOM;" + ")V");
/* 103:    */       
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:129 */       il.append(new INVOKESTATIC(copy));
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:132 */       il.append(classGen.loadTranslet());
/* 114:133 */       this._select.translate(classGen, methodGen);
/* 115:134 */       il.append(methodGen.loadHandler());
/* 116:135 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "characters", Constants.CHARACTERSW_SIG)));
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CopyOf
 * JD-Core Version:    0.7.0.1
 */