/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   7:    */ import org.apache.bcel.generic.InstructionConstants;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ 
/*  17:    */ final class KeyCall
/*  18:    */   extends FunctionCall
/*  19:    */ {
/*  20:    */   private Expression _name;
/*  21:    */   private Expression _value;
/*  22:    */   private Type _valueType;
/*  23: 71 */   private QName _resolvedQName = null;
/*  24:    */   
/*  25:    */   public KeyCall(QName fname, Vector arguments)
/*  26:    */   {
/*  27: 86 */     super(fname, arguments);
/*  28: 87 */     switch (argumentCount())
/*  29:    */     {
/*  30:    */     case 1: 
/*  31: 89 */       this._name = null;
/*  32: 90 */       this._value = argument(0);
/*  33: 91 */       break;
/*  34:    */     case 2: 
/*  35: 93 */       this._name = argument(0);
/*  36: 94 */       this._value = argument(1);
/*  37: 95 */       break;
/*  38:    */     default: 
/*  39: 97 */       this._name = (this._value = null);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void addParentDependency()
/*  44:    */   {
/*  45:116 */     if (this._resolvedQName == null) {
/*  46:116 */       return;
/*  47:    */     }
/*  48:118 */     SyntaxTreeNode node = this;
/*  49:119 */     while ((node != null) && (!(node instanceof TopLevelElement))) {
/*  50:120 */       node = node.getParent();
/*  51:    */     }
/*  52:123 */     TopLevelElement parent = (TopLevelElement)node;
/*  53:124 */     if (parent != null) {
/*  54:125 */       parent.addDependency(getSymbolTable().getKey(this._resolvedQName));
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Type typeCheck(SymbolTable stable)
/*  59:    */     throws TypeCheckError
/*  60:    */   {
/*  61:137 */     Type returnType = super.typeCheck(stable);
/*  62:141 */     if (this._name != null)
/*  63:    */     {
/*  64:142 */       Type nameType = this._name.typeCheck(stable);
/*  65:144 */       if ((this._name instanceof LiteralExpr))
/*  66:    */       {
/*  67:145 */         LiteralExpr literal = (LiteralExpr)this._name;
/*  68:146 */         this._resolvedQName = getParser().getQNameIgnoreDefaultNs(literal.getValue());
/*  69:    */       }
/*  70:149 */       else if (!(nameType instanceof StringType))
/*  71:    */       {
/*  72:150 */         this._name = new CastExpr(this._name, Type.String);
/*  73:    */       }
/*  74:    */     }
/*  75:163 */     this._valueType = this._value.typeCheck(stable);
/*  76:165 */     if ((this._valueType != Type.NodeSet) && (this._valueType != Type.Reference) && (this._valueType != Type.String))
/*  77:    */     {
/*  78:168 */       this._value = new CastExpr(this._value, Type.String);
/*  79:169 */       this._valueType = this._value.typeCheck(stable);
/*  80:    */     }
/*  81:173 */     addParentDependency();
/*  82:    */     
/*  83:175 */     return returnType;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  87:    */   {
/*  88:188 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  89:189 */     InstructionList il = methodGen.getInstructionList();
/*  90:    */     
/*  91:    */ 
/*  92:192 */     int getKeyIndex = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "getKeyIndex", "(Ljava/lang/String;)Lorg/apache/xalan/xsltc/dom/KeyIndex;");
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:198 */     int keyDom = cpg.addMethodref("org/apache/xalan/xsltc/dom/KeyIndex", "setDom", "(Lorg/apache/xalan/xsltc/DOM;)V");
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:203 */     int getKeyIterator = cpg.addMethodref("org/apache/xalan/xsltc/dom/KeyIndex", "getKeyIndexIterator", "(" + this._valueType.toSignature() + "Z)" + "Lorg/apache/xalan/xsltc/dom/KeyIndex$KeyIndexIterator;");
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:210 */     il.append(classGen.loadTranslet());
/* 111:211 */     if (this._name == null) {
/* 112:212 */       il.append(new PUSH(cpg, "##id"));
/* 113:213 */     } else if (this._resolvedQName != null) {
/* 114:214 */       il.append(new PUSH(cpg, this._resolvedQName.toString()));
/* 115:    */     } else {
/* 116:216 */       this._name.translate(classGen, methodGen);
/* 117:    */     }
/* 118:226 */     il.append(new INVOKEVIRTUAL(getKeyIndex));
/* 119:227 */     il.append(InstructionConstants.DUP);
/* 120:228 */     il.append(methodGen.loadDOM());
/* 121:229 */     il.append(new INVOKEVIRTUAL(keyDom));
/* 122:    */     
/* 123:231 */     this._value.translate(classGen, methodGen);
/* 124:232 */     il.append(this._name != null ? InstructionConstants.ICONST_1 : InstructionConstants.ICONST_0);
/* 125:233 */     il.append(new INVOKEVIRTUAL(getKeyIterator));
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.KeyCall
 * JD-Core Version:    0.7.0.1
 */