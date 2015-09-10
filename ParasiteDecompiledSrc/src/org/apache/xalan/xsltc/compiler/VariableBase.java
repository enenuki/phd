/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   8:    */ import org.apache.bcel.generic.Instruction;
/*   9:    */ import org.apache.bcel.generic.InstructionConstants;
/*  10:    */ import org.apache.bcel.generic.InstructionList;
/*  11:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  12:    */ import org.apache.bcel.generic.MethodGen;
/*  13:    */ import org.apache.bcel.generic.NEW;
/*  14:    */ import org.apache.bcel.generic.PUSH;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  20:    */ import org.apache.xml.utils.XML11Char;
/*  21:    */ 
/*  22:    */ class VariableBase
/*  23:    */   extends TopLevelElement
/*  24:    */ {
/*  25:    */   protected QName _name;
/*  26:    */   protected String _escapedName;
/*  27:    */   protected org.apache.xalan.xsltc.compiler.util.Type _type;
/*  28:    */   protected boolean _isLocal;
/*  29:    */   protected LocalVariableGen _local;
/*  30:    */   protected Instruction _loadInstruction;
/*  31:    */   protected Instruction _storeInstruction;
/*  32:    */   protected Expression _select;
/*  33:    */   protected String select;
/*  34: 61 */   protected Vector _refs = new Vector(2);
/*  35: 64 */   protected Vector _dependencies = null;
/*  36: 67 */   protected boolean _ignore = false;
/*  37:    */   
/*  38:    */   public void disable()
/*  39:    */   {
/*  40: 73 */     this._ignore = true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void addReference(VariableRefBase vref)
/*  44:    */   {
/*  45: 81 */     this._refs.addElement(vref);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void mapRegister(MethodGenerator methodGen)
/*  49:    */   {
/*  50: 88 */     if (this._local == null)
/*  51:    */     {
/*  52: 89 */       String name = getEscapedName();
/*  53: 90 */       org.apache.bcel.generic.Type varType = this._type.toJCType();
/*  54: 91 */       this._local = methodGen.addLocalVariable2(name, varType, null);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void unmapRegister(MethodGenerator methodGen)
/*  59:    */   {
/*  60:100 */     if (this._local != null)
/*  61:    */     {
/*  62:101 */       this._local.setEnd(methodGen.getInstructionList().getEnd());
/*  63:102 */       methodGen.removeLocalVariable(this._local);
/*  64:103 */       this._refs = null;
/*  65:104 */       this._local = null;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Instruction loadInstruction()
/*  70:    */   {
/*  71:113 */     Instruction instr = this._loadInstruction;
/*  72:114 */     if (this._loadInstruction == null) {
/*  73:115 */       this._loadInstruction = this._type.LOAD(this._local.getIndex());
/*  74:    */     }
/*  75:117 */     return this._loadInstruction;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Instruction storeInstruction()
/*  79:    */   {
/*  80:125 */     Instruction instr = this._storeInstruction;
/*  81:126 */     if (this._storeInstruction == null) {
/*  82:127 */       this._storeInstruction = this._type.STORE(this._local.getIndex());
/*  83:    */     }
/*  84:129 */     return this._storeInstruction;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Expression getExpression()
/*  88:    */   {
/*  89:136 */     return this._select;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String toString()
/*  93:    */   {
/*  94:143 */     return "variable(" + this._name + ")";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void display(int indent)
/*  98:    */   {
/*  99:150 */     indent(indent);
/* 100:151 */     System.out.println("Variable " + this._name);
/* 101:152 */     if (this._select != null)
/* 102:    */     {
/* 103:153 */       indent(indent + 4);
/* 104:154 */       System.out.println("select " + this._select.toString());
/* 105:    */     }
/* 106:156 */     displayContents(indent + 4);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public org.apache.xalan.xsltc.compiler.util.Type getType()
/* 110:    */   {
/* 111:163 */     return this._type;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public QName getName()
/* 115:    */   {
/* 116:171 */     return this._name;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getEscapedName()
/* 120:    */   {
/* 121:178 */     return this._escapedName;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setName(QName name)
/* 125:    */   {
/* 126:185 */     this._name = name;
/* 127:186 */     this._escapedName = Util.escape(name.getStringRep());
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isLocal()
/* 131:    */   {
/* 132:193 */     return this._isLocal;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void parseContents(Parser parser)
/* 136:    */   {
/* 137:201 */     String name = getAttribute("name");
/* 138:203 */     if (name.length() > 0)
/* 139:    */     {
/* 140:204 */       if (!XML11Char.isXML11ValidQName(name))
/* 141:    */       {
/* 142:205 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/* 143:206 */         parser.reportError(3, err);
/* 144:    */       }
/* 145:208 */       setName(parser.getQNameIgnoreDefaultNs(name));
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:211 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/* 150:    */     }
/* 151:214 */     VariableBase other = parser.lookupVariable(this._name);
/* 152:215 */     if ((other != null) && (other.getParent() == getParent())) {
/* 153:216 */       reportError(this, parser, "VARIABLE_REDEF_ERR", name);
/* 154:    */     }
/* 155:219 */     this.select = getAttribute("select");
/* 156:220 */     if (this.select.length() > 0)
/* 157:    */     {
/* 158:221 */       this._select = getParser().parseExpression(this, "select", null);
/* 159:222 */       if (this._select.isDummy())
/* 160:    */       {
/* 161:223 */         reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/* 162:224 */         return;
/* 163:    */       }
/* 164:    */     }
/* 165:229 */     parseChildren(parser);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void translateValue(ClassGenerator classGen, MethodGenerator methodGen)
/* 169:    */   {
/* 170:239 */     if (this._select != null)
/* 171:    */     {
/* 172:240 */       this._select.translate(classGen, methodGen);
/* 173:243 */       if ((this._select.getType() instanceof NodeSetType))
/* 174:    */       {
/* 175:244 */         ConstantPoolGen cpg = classGen.getConstantPool();
/* 176:245 */         InstructionList il = methodGen.getInstructionList();
/* 177:    */         
/* 178:247 */         int initCNI = cpg.addMethodref("org.apache.xalan.xsltc.dom.CachedNodeListIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/* 179:    */         
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:252 */         il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.CachedNodeListIterator")));
/* 184:253 */         il.append(InstructionConstants.DUP_X1);
/* 185:254 */         il.append(InstructionConstants.SWAP);
/* 186:    */         
/* 187:256 */         il.append(new INVOKESPECIAL(initCNI));
/* 188:    */       }
/* 189:258 */       this._select.startIterator(classGen, methodGen);
/* 190:    */     }
/* 191:261 */     else if (hasContents())
/* 192:    */     {
/* 193:262 */       compileResultTree(classGen, methodGen);
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:266 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 198:267 */       InstructionList il = methodGen.getInstructionList();
/* 199:268 */       il.append(new PUSH(cpg, ""));
/* 200:    */     }
/* 201:    */   }
/* 202:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.VariableBase
 * JD-Core Version:    0.7.0.1
 */