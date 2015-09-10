/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.AttributeSetMethodGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  18:    */ import org.apache.xml.utils.XML11Char;
/*  19:    */ 
/*  20:    */ final class AttributeSet
/*  21:    */   extends TopLevelElement
/*  22:    */ {
/*  23:    */   private static final String AttributeSetPrefix = "$as$";
/*  24:    */   private QName _name;
/*  25:    */   private UseAttributeSets _useSets;
/*  26:    */   private AttributeSet _mergeSet;
/*  27:    */   private String _method;
/*  28: 54 */   private boolean _ignore = false;
/*  29:    */   
/*  30:    */   public QName getName()
/*  31:    */   {
/*  32: 60 */     return this._name;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getMethodName()
/*  36:    */   {
/*  37: 68 */     return this._method;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void ignore()
/*  41:    */   {
/*  42: 78 */     this._ignore = true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void parseContents(Parser parser)
/*  46:    */   {
/*  47: 88 */     String name = getAttribute("name");
/*  48: 90 */     if (!XML11Char.isXML11ValidQName(name))
/*  49:    */     {
/*  50: 91 */       ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  51: 92 */       parser.reportError(3, err);
/*  52:    */     }
/*  53: 94 */     this._name = parser.getQNameIgnoreDefaultNs(name);
/*  54: 95 */     if ((this._name == null) || (this._name.equals("")))
/*  55:    */     {
/*  56: 96 */       ErrorMsg msg = new ErrorMsg("UNNAMED_ATTRIBSET_ERR", this);
/*  57: 97 */       parser.reportError(3, msg);
/*  58:    */     }
/*  59:101 */     String useSets = getAttribute("use-attribute-sets");
/*  60:102 */     if (useSets.length() > 0)
/*  61:    */     {
/*  62:103 */       if (!Util.isValidQNames(useSets))
/*  63:    */       {
/*  64:104 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
/*  65:105 */         parser.reportError(3, err);
/*  66:    */       }
/*  67:107 */       this._useSets = new UseAttributeSets(useSets, parser);
/*  68:    */     }
/*  69:112 */     Vector contents = getContents();
/*  70:113 */     int count = contents.size();
/*  71:114 */     for (int i = 0; i < count; i++)
/*  72:    */     {
/*  73:115 */       SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
/*  74:116 */       if ((child instanceof XslAttribute))
/*  75:    */       {
/*  76:117 */         parser.getSymbolTable().setCurrentNode(child);
/*  77:118 */         child.parseContents(parser);
/*  78:    */       }
/*  79:120 */       else if (!(child instanceof Text))
/*  80:    */       {
/*  81:124 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_CHILD_ERR", this);
/*  82:125 */         parser.reportError(3, msg);
/*  83:    */       }
/*  84:    */     }
/*  85:130 */     parser.getSymbolTable().setCurrentNode(this);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Type typeCheck(SymbolTable stable)
/*  89:    */     throws TypeCheckError
/*  90:    */   {
/*  91:138 */     if (this._ignore) {
/*  92:138 */       return Type.Void;
/*  93:    */     }
/*  94:141 */     this._mergeSet = stable.addAttributeSet(this);
/*  95:    */     
/*  96:143 */     this._method = ("$as$" + getXSLTC().nextAttributeSetSerial());
/*  97:145 */     if (this._useSets != null) {
/*  98:145 */       this._useSets.typeCheck(stable);
/*  99:    */     }
/* 100:146 */     typeCheckContents(stable);
/* 101:147 */     return Type.Void;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 105:    */   {
/* 106:155 */     if (this._ignore) {
/* 107:155 */       return;
/* 108:    */     }
/* 109:158 */     methodGen = new AttributeSetMethodGenerator(this._method, classGen);
/* 110:162 */     if (this._mergeSet != null)
/* 111:    */     {
/* 112:163 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 113:164 */       InstructionList il = methodGen.getInstructionList();
/* 114:165 */       String methodName = this._mergeSet.getMethodName();
/* 115:    */       
/* 116:167 */       il.append(classGen.loadTranslet());
/* 117:168 */       il.append(methodGen.loadDOM());
/* 118:169 */       il.append(methodGen.loadIterator());
/* 119:170 */       il.append(methodGen.loadHandler());
/* 120:171 */       int method = cpg.addMethodref(classGen.getClassName(), methodName, Constants.ATTR_SET_SIG);
/* 121:    */       
/* 122:173 */       il.append(new INVOKESPECIAL(method));
/* 123:    */     }
/* 124:178 */     if (this._useSets != null) {
/* 125:178 */       this._useSets.translate(classGen, methodGen);
/* 126:    */     }
/* 127:181 */     Enumeration attributes = elements();
/* 128:182 */     while (attributes.hasMoreElements())
/* 129:    */     {
/* 130:183 */       SyntaxTreeNode element = (SyntaxTreeNode)attributes.nextElement();
/* 131:184 */       if ((element instanceof XslAttribute))
/* 132:    */       {
/* 133:185 */         XslAttribute attribute = (XslAttribute)element;
/* 134:186 */         attribute.translate(classGen, methodGen);
/* 135:    */       }
/* 136:    */     }
/* 137:189 */     InstructionList il = methodGen.getInstructionList();
/* 138:190 */     il.append(InstructionConstants.RETURN);
/* 139:    */     
/* 140:192 */     classGen.addMethod(methodGen);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String toString()
/* 144:    */   {
/* 145:196 */     StringBuffer buf = new StringBuffer("attribute-set: ");
/* 146:    */     
/* 147:198 */     Enumeration attributes = elements();
/* 148:199 */     while (attributes.hasMoreElements())
/* 149:    */     {
/* 150:200 */       XslAttribute attribute = (XslAttribute)attributes.nextElement();
/* 151:    */       
/* 152:202 */       buf.append(attribute);
/* 153:    */     }
/* 154:204 */     return buf.toString();
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AttributeSet
 * JD-Core Version:    0.7.0.1
 */