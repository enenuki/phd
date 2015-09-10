/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   6:    */ import org.apache.bcel.generic.InstructionConstants;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.bcel.generic.PUSH;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  17:    */ import org.apache.xml.utils.XML11Char;
/*  18:    */ 
/*  19:    */ final class WithParam
/*  20:    */   extends Instruction
/*  21:    */ {
/*  22:    */   private QName _name;
/*  23:    */   protected String _escapedName;
/*  24:    */   private Expression _select;
/*  25: 66 */   private boolean _doParameterOptimization = false;
/*  26:    */   
/*  27:    */   public void display(int indent)
/*  28:    */   {
/*  29: 72 */     indent(indent);
/*  30: 73 */     Util.println("with-param " + this._name);
/*  31: 74 */     if (this._select != null)
/*  32:    */     {
/*  33: 75 */       indent(indent + 4);
/*  34: 76 */       Util.println("select " + this._select.toString());
/*  35:    */     }
/*  36: 78 */     displayContents(indent + 4);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getEscapedName()
/*  40:    */   {
/*  41: 85 */     return this._escapedName;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public QName getName()
/*  45:    */   {
/*  46: 92 */     return this._name;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setName(QName name)
/*  50:    */   {
/*  51: 99 */     this._name = name;
/*  52:100 */     this._escapedName = Util.escape(name.getStringRep());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setDoParameterOptimization(boolean flag)
/*  56:    */   {
/*  57:107 */     this._doParameterOptimization = flag;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void parseContents(Parser parser)
/*  61:    */   {
/*  62:115 */     String name = getAttribute("name");
/*  63:116 */     if (name.length() > 0)
/*  64:    */     {
/*  65:117 */       if (!XML11Char.isXML11ValidQName(name))
/*  66:    */       {
/*  67:118 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  68:    */         
/*  69:120 */         parser.reportError(3, err);
/*  70:    */       }
/*  71:122 */       setName(parser.getQNameIgnoreDefaultNs(name));
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:125 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*  76:    */     }
/*  77:128 */     String select = getAttribute("select");
/*  78:129 */     if (select.length() > 0) {
/*  79:130 */       this._select = parser.parseExpression(this, "select", null);
/*  80:    */     }
/*  81:133 */     parseChildren(parser);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Type typeCheck(SymbolTable stable)
/*  85:    */     throws TypeCheckError
/*  86:    */   {
/*  87:141 */     if (this._select != null)
/*  88:    */     {
/*  89:142 */       Type tselect = this._select.typeCheck(stable);
/*  90:143 */       if (!(tselect instanceof ReferenceType)) {
/*  91:144 */         this._select = new CastExpr(this._select, Type.Reference);
/*  92:    */       }
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:148 */       typeCheckContents(stable);
/*  97:    */     }
/*  98:150 */     return Type.Void;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void translateValue(ClassGenerator classGen, MethodGenerator methodGen)
/* 102:    */   {
/* 103:160 */     if (this._select != null)
/* 104:    */     {
/* 105:161 */       this._select.translate(classGen, methodGen);
/* 106:162 */       this._select.startIterator(classGen, methodGen);
/* 107:    */     }
/* 108:165 */     else if (hasContents())
/* 109:    */     {
/* 110:166 */       compileResultTree(classGen, methodGen);
/* 111:    */     }
/* 112:    */     else
/* 113:    */     {
/* 114:170 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 115:171 */       InstructionList il = methodGen.getInstructionList();
/* 116:172 */       il.append(new PUSH(cpg, ""));
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 121:    */   {
/* 122:182 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 123:183 */     InstructionList il = methodGen.getInstructionList();
/* 124:186 */     if (this._doParameterOptimization)
/* 125:    */     {
/* 126:187 */       translateValue(classGen, methodGen);
/* 127:188 */       return;
/* 128:    */     }
/* 129:192 */     String name = Util.escape(getEscapedName());
/* 130:    */     
/* 131:    */ 
/* 132:195 */     il.append(classGen.loadTranslet());
/* 133:    */     
/* 134:    */ 
/* 135:198 */     il.append(new PUSH(cpg, name));
/* 136:    */     
/* 137:200 */     translateValue(classGen, methodGen);
/* 138:    */     
/* 139:202 */     il.append(new PUSH(cpg, false));
/* 140:    */     
/* 141:204 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;")));
/* 142:    */     
/* 143:    */ 
/* 144:207 */     il.append(InstructionConstants.POP);
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.WithParam
 * JD-Core Version:    0.7.0.1
 */