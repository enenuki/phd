/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  17:    */ import org.apache.xml.utils.XML11Char;
/*  18:    */ 
/*  19:    */ final class CallTemplate
/*  20:    */   extends Instruction
/*  21:    */ {
/*  22:    */   private QName _name;
/*  23: 57 */   private Object[] _parameters = null;
/*  24: 62 */   private Template _calleeTemplate = null;
/*  25:    */   
/*  26:    */   public void display(int indent)
/*  27:    */   {
/*  28: 65 */     indent(indent);
/*  29: 66 */     System.out.print("CallTemplate");
/*  30: 67 */     Util.println(" name " + this._name);
/*  31: 68 */     displayContents(indent + 4);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean hasWithParams()
/*  35:    */   {
/*  36: 72 */     return elementCount() > 0;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void parseContents(Parser parser)
/*  40:    */   {
/*  41: 76 */     String name = getAttribute("name");
/*  42: 77 */     if (name.length() > 0)
/*  43:    */     {
/*  44: 78 */       if (!XML11Char.isXML11ValidQName(name))
/*  45:    */       {
/*  46: 79 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  47: 80 */         parser.reportError(3, err);
/*  48:    */       }
/*  49: 82 */       this._name = parser.getQNameIgnoreDefaultNs(name);
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53: 85 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*  54:    */     }
/*  55: 87 */     parseChildren(parser);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Type typeCheck(SymbolTable stable)
/*  59:    */     throws TypeCheckError
/*  60:    */   {
/*  61: 94 */     Template template = stable.lookupTemplate(this._name);
/*  62: 95 */     if (template != null)
/*  63:    */     {
/*  64: 96 */       typeCheckContents(stable);
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68: 99 */       ErrorMsg err = new ErrorMsg("TEMPLATE_UNDEF_ERR", this._name, this);
/*  69:100 */       throw new TypeCheckError(err);
/*  70:    */     }
/*  71:102 */     return Type.Void;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  75:    */   {
/*  76:106 */     Stylesheet stylesheet = classGen.getStylesheet();
/*  77:107 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  78:108 */     InstructionList il = methodGen.getInstructionList();
/*  79:111 */     if ((stylesheet.hasLocalParams()) || (hasContents()))
/*  80:    */     {
/*  81:112 */       this._calleeTemplate = getCalleeTemplate();
/*  82:115 */       if (this._calleeTemplate != null)
/*  83:    */       {
/*  84:116 */         buildParameterList();
/*  85:    */       }
/*  86:    */       else
/*  87:    */       {
/*  88:122 */         int push = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "pushParamFrame", "()V");
/*  89:    */         
/*  90:    */ 
/*  91:125 */         il.append(classGen.loadTranslet());
/*  92:126 */         il.append(new INVOKEVIRTUAL(push));
/*  93:127 */         translateContents(classGen, methodGen);
/*  94:    */       }
/*  95:    */     }
/*  96:132 */     String className = stylesheet.getClassName();
/*  97:133 */     String methodName = Util.escape(this._name.toString());
/*  98:    */     
/*  99:    */ 
/* 100:136 */     il.append(classGen.loadTranslet());
/* 101:137 */     il.append(methodGen.loadDOM());
/* 102:138 */     il.append(methodGen.loadIterator());
/* 103:139 */     il.append(methodGen.loadHandler());
/* 104:140 */     il.append(methodGen.loadCurrentNode());
/* 105:    */     
/* 106:    */ 
/* 107:143 */     StringBuffer methodSig = new StringBuffer("(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + "I");
/* 108:147 */     if (this._calleeTemplate != null)
/* 109:    */     {
/* 110:148 */       Vector calleeParams = this._calleeTemplate.getParameters();
/* 111:149 */       int numParams = this._parameters.length;
/* 112:151 */       for (int i = 0; i < numParams; i++)
/* 113:    */       {
/* 114:152 */         SyntaxTreeNode node = (SyntaxTreeNode)this._parameters[i];
/* 115:153 */         methodSig.append("Ljava/lang/Object;");
/* 116:156 */         if ((node instanceof Param)) {
/* 117:157 */           il.append(InstructionConstants.ACONST_NULL);
/* 118:    */         } else {
/* 119:160 */           node.translate(classGen, methodGen);
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:166 */     methodSig.append(")V");
/* 124:167 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref(className, methodName, methodSig.toString())));
/* 125:173 */     if ((this._calleeTemplate == null) && ((stylesheet.hasLocalParams()) || (hasContents())))
/* 126:    */     {
/* 127:175 */       int pop = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "popParamFrame", "()V");
/* 128:    */       
/* 129:    */ 
/* 130:178 */       il.append(classGen.loadTranslet());
/* 131:179 */       il.append(new INVOKEVIRTUAL(pop));
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Template getCalleeTemplate()
/* 136:    */   {
/* 137:189 */     Template foundTemplate = getXSLTC().getParser().getSymbolTable().lookupTemplate(this._name);
/* 138:    */     
/* 139:    */ 
/* 140:192 */     return foundTemplate.isSimpleNamedTemplate() ? foundTemplate : null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void buildParameterList()
/* 144:    */   {
/* 145:204 */     Vector defaultParams = this._calleeTemplate.getParameters();
/* 146:205 */     int numParams = defaultParams.size();
/* 147:206 */     this._parameters = new Object[numParams];
/* 148:207 */     for (int i = 0; i < numParams; i++) {
/* 149:208 */       this._parameters[i] = defaultParams.elementAt(i);
/* 150:    */     }
/* 151:212 */     int count = elementCount();
/* 152:213 */     for (int i = 0; i < count; i++)
/* 153:    */     {
/* 154:214 */       Object node = elementAt(i);
/* 155:217 */       if ((node instanceof WithParam))
/* 156:    */       {
/* 157:218 */         WithParam withParam = (WithParam)node;
/* 158:219 */         QName name = withParam.getName();
/* 159:222 */         for (int k = 0; k < numParams; k++)
/* 160:    */         {
/* 161:223 */           Object object = this._parameters[k];
/* 162:224 */           if (((object instanceof Param)) && (((Param)object).getName() == name))
/* 163:    */           {
/* 164:226 */             withParam.setDoParameterOptimization(true);
/* 165:227 */             this._parameters[k] = withParam;
/* 166:228 */             break;
/* 167:    */           }
/* 168:230 */           if (((object instanceof WithParam)) && (((WithParam)object).getName() == name))
/* 169:    */           {
/* 170:232 */             withParam.setDoParameterOptimization(true);
/* 171:233 */             this._parameters[k] = withParam;
/* 172:234 */             break;
/* 173:    */           }
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CallTemplate
 * JD-Core Version:    0.7.0.1
 */