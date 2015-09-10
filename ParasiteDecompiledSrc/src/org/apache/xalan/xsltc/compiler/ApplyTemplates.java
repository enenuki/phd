/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   8:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ import org.apache.xml.utils.XML11Char;
/*  22:    */ 
/*  23:    */ final class ApplyTemplates
/*  24:    */   extends Instruction
/*  25:    */ {
/*  26:    */   private Expression _select;
/*  27: 49 */   private Type _type = null;
/*  28:    */   private QName _modeName;
/*  29:    */   private String _functionName;
/*  30:    */   
/*  31:    */   public void display(int indent)
/*  32:    */   {
/*  33: 54 */     indent(indent);
/*  34: 55 */     Util.println("ApplyTemplates");
/*  35: 56 */     indent(indent + 4);
/*  36: 57 */     Util.println("select " + this._select.toString());
/*  37: 58 */     if (this._modeName != null)
/*  38:    */     {
/*  39: 59 */       indent(indent + 4);
/*  40: 60 */       Util.println("mode " + this._modeName);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean hasWithParams()
/*  45:    */   {
/*  46: 65 */     return hasContents();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void parseContents(Parser parser)
/*  50:    */   {
/*  51: 69 */     String select = getAttribute("select");
/*  52: 70 */     String mode = getAttribute("mode");
/*  53: 72 */     if (select.length() > 0) {
/*  54: 73 */       this._select = parser.parseExpression(this, "select", null);
/*  55:    */     }
/*  56: 77 */     if (mode.length() > 0)
/*  57:    */     {
/*  58: 78 */       if (!XML11Char.isXML11ValidQName(mode))
/*  59:    */       {
/*  60: 79 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", mode, this);
/*  61: 80 */         parser.reportError(3, err);
/*  62:    */       }
/*  63: 82 */       this._modeName = parser.getQNameIgnoreDefaultNs(mode);
/*  64:    */     }
/*  65: 86 */     this._functionName = parser.getTopLevelStylesheet().getMode(this._modeName).functionName();
/*  66:    */     
/*  67: 88 */     parseChildren(parser);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Type typeCheck(SymbolTable stable)
/*  71:    */     throws TypeCheckError
/*  72:    */   {
/*  73: 92 */     if (this._select != null)
/*  74:    */     {
/*  75: 93 */       this._type = this._select.typeCheck(stable);
/*  76: 94 */       if (((this._type instanceof NodeType)) || ((this._type instanceof ReferenceType)))
/*  77:    */       {
/*  78: 95 */         this._select = new CastExpr(this._select, Type.NodeSet);
/*  79: 96 */         this._type = Type.NodeSet;
/*  80:    */       }
/*  81: 98 */       if (((this._type instanceof NodeSetType)) || ((this._type instanceof ResultTreeType)))
/*  82:    */       {
/*  83: 99 */         typeCheckContents(stable);
/*  84:100 */         return Type.Void;
/*  85:    */       }
/*  86:102 */       throw new TypeCheckError(this);
/*  87:    */     }
/*  88:105 */     typeCheckContents(stable);
/*  89:106 */     return Type.Void;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  93:    */   {
/*  94:115 */     boolean setStartNodeCalled = false;
/*  95:116 */     Stylesheet stylesheet = classGen.getStylesheet();
/*  96:117 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  97:118 */     InstructionList il = methodGen.getInstructionList();
/*  98:119 */     int current = methodGen.getLocalIndex("current");
/*  99:    */     
/* 100:    */ 
/* 101:122 */     Vector sortObjects = new Vector();
/* 102:123 */     Enumeration children = elements();
/* 103:124 */     while (children.hasMoreElements())
/* 104:    */     {
/* 105:125 */       Object child = children.nextElement();
/* 106:126 */       if ((child instanceof Sort)) {
/* 107:127 */         sortObjects.addElement(child);
/* 108:    */       }
/* 109:    */     }
/* 110:132 */     if ((stylesheet.hasLocalParams()) || (hasContents()))
/* 111:    */     {
/* 112:133 */       il.append(classGen.loadTranslet());
/* 113:134 */       int pushFrame = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
/* 114:    */       
/* 115:    */ 
/* 116:137 */       il.append(new INVOKEVIRTUAL(pushFrame));
/* 117:    */       
/* 118:139 */       translateContents(classGen, methodGen);
/* 119:    */     }
/* 120:143 */     il.append(classGen.loadTranslet());
/* 121:146 */     if ((this._type != null) && ((this._type instanceof ResultTreeType)))
/* 122:    */     {
/* 123:148 */       if (sortObjects.size() > 0)
/* 124:    */       {
/* 125:149 */         ErrorMsg err = new ErrorMsg("RESULT_TREE_SORT_ERR", this);
/* 126:150 */         getParser().reportError(4, err);
/* 127:    */       }
/* 128:153 */       this._select.translate(classGen, methodGen);
/* 129:    */       
/* 130:155 */       this._type.translateTo(classGen, methodGen, Type.NodeSet);
/* 131:    */     }
/* 132:    */     else
/* 133:    */     {
/* 134:158 */       il.append(methodGen.loadDOM());
/* 135:161 */       if (sortObjects.size() > 0)
/* 136:    */       {
/* 137:162 */         Sort.translateSortIterator(classGen, methodGen, this._select, sortObjects);
/* 138:    */         
/* 139:164 */         int setStartNode = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "setStartNode", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 140:    */         
/* 141:    */ 
/* 142:    */ 
/* 143:168 */         il.append(methodGen.loadCurrentNode());
/* 144:169 */         il.append(new INVOKEINTERFACE(setStartNode, 2));
/* 145:170 */         setStartNodeCalled = true;
/* 146:    */       }
/* 147:173 */       else if (this._select == null)
/* 148:    */       {
/* 149:174 */         Mode.compileGetChildren(classGen, methodGen, current);
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:176 */         this._select.translate(classGen, methodGen);
/* 154:    */       }
/* 155:    */     }
/* 156:180 */     if ((this._select != null) && (!setStartNodeCalled)) {
/* 157:181 */       this._select.startIterator(classGen, methodGen);
/* 158:    */     }
/* 159:185 */     String className = classGen.getStylesheet().getClassName();
/* 160:186 */     il.append(methodGen.loadHandler());
/* 161:187 */     String applyTemplatesSig = classGen.getApplyTemplatesSig();
/* 162:188 */     int applyTemplates = cpg.addMethodref(className, this._functionName, applyTemplatesSig);
/* 163:    */     
/* 164:    */ 
/* 165:191 */     il.append(new INVOKEVIRTUAL(applyTemplates));
/* 166:194 */     if ((stylesheet.hasLocalParams()) || (hasContents()))
/* 167:    */     {
/* 168:195 */       il.append(classGen.loadTranslet());
/* 169:196 */       int popFrame = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
/* 170:    */       
/* 171:    */ 
/* 172:199 */       il.append(new INVOKEVIRTUAL(popFrame));
/* 173:    */     }
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ApplyTemplates
 * JD-Core Version:    0.7.0.1
 */