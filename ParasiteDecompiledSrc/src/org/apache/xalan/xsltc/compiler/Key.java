/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GOTO;
/*   7:    */ import org.apache.bcel.generic.IFEQ;
/*   8:    */ import org.apache.bcel.generic.IFGE;
/*   9:    */ import org.apache.bcel.generic.IFGT;
/*  10:    */ import org.apache.bcel.generic.ILOAD;
/*  11:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.ISTORE;
/*  14:    */ import org.apache.bcel.generic.InstructionConstants;
/*  15:    */ import org.apache.bcel.generic.InstructionHandle;
/*  16:    */ import org.apache.bcel.generic.InstructionList;
/*  17:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  18:    */ import org.apache.bcel.generic.MethodGen;
/*  19:    */ import org.apache.bcel.generic.PUSH;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  25:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  28:    */ import org.apache.xml.utils.XML11Char;
/*  29:    */ 
/*  30:    */ final class Key
/*  31:    */   extends TopLevelElement
/*  32:    */ {
/*  33:    */   private QName _name;
/*  34:    */   private Pattern _match;
/*  35:    */   private Expression _use;
/*  36:    */   private Type _useType;
/*  37:    */   
/*  38:    */   public void parseContents(Parser parser)
/*  39:    */   {
/*  40: 84 */     String name = getAttribute("name");
/*  41: 85 */     if (!XML11Char.isXML11ValidQName(name))
/*  42:    */     {
/*  43: 86 */       ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  44: 87 */       parser.reportError(3, err);
/*  45:    */     }
/*  46: 91 */     this._name = parser.getQNameIgnoreDefaultNs(name);
/*  47: 92 */     getSymbolTable().addKey(this._name, this);
/*  48:    */     
/*  49: 94 */     this._match = parser.parsePattern(this, "match", null);
/*  50: 95 */     this._use = parser.parseExpression(this, "use", null);
/*  51: 98 */     if (this._name == null)
/*  52:    */     {
/*  53: 99 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*  54:100 */       return;
/*  55:    */     }
/*  56:102 */     if (this._match.isDummy())
/*  57:    */     {
/*  58:103 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "match");
/*  59:104 */       return;
/*  60:    */     }
/*  61:106 */     if (this._use.isDummy())
/*  62:    */     {
/*  63:107 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "use");
/*  64:108 */       return;
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getName()
/*  69:    */   {
/*  70:117 */     return this._name.toString();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Type typeCheck(SymbolTable stable)
/*  74:    */     throws TypeCheckError
/*  75:    */   {
/*  76:122 */     this._match.typeCheck(stable);
/*  77:    */     
/*  78:    */ 
/*  79:125 */     this._useType = this._use.typeCheck(stable);
/*  80:126 */     if ((!(this._useType instanceof StringType)) && (!(this._useType instanceof NodeSetType))) {
/*  81:129 */       this._use = new CastExpr(this._use, Type.String);
/*  82:    */     }
/*  83:132 */     return Type.Void;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void traverseNodeSet(ClassGenerator classGen, MethodGenerator methodGen, int buildKeyIndex)
/*  87:    */   {
/*  88:143 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  89:144 */     InstructionList il = methodGen.getInstructionList();
/*  90:    */     
/*  91:    */ 
/*  92:147 */     int getNodeValue = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getStringValueX", "(I)Ljava/lang/String;");
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:151 */     int getNodeIdent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeIdent", "(I)I");
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:156 */     int keyDom = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "setKeyIndexDom", "(Ljava/lang/String;Lorg/apache/xalan/xsltc/DOM;)V");
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:164 */     LocalVariableGen parentNode = methodGen.addLocalVariable("parentNode", Util.getJCRefType("I"), null, null);
/* 110:    */     
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:170 */     parentNode.setStart(il.append(new ISTORE(parentNode.getIndex())));
/* 116:    */     
/* 117:    */ 
/* 118:173 */     il.append(methodGen.loadCurrentNode());
/* 119:174 */     il.append(methodGen.loadIterator());
/* 120:    */     
/* 121:    */ 
/* 122:177 */     this._use.translate(classGen, methodGen);
/* 123:178 */     this._use.startIterator(classGen, methodGen);
/* 124:179 */     il.append(methodGen.storeIterator());
/* 125:    */     
/* 126:181 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 127:182 */     InstructionHandle loop = il.append(InstructionConstants.NOP);
/* 128:    */     
/* 129:    */ 
/* 130:185 */     il.append(classGen.loadTranslet());
/* 131:186 */     il.append(new PUSH(cpg, this._name.toString()));
/* 132:187 */     parentNode.setEnd(il.append(new ILOAD(parentNode.getIndex())));
/* 133:    */     
/* 134:    */ 
/* 135:190 */     il.append(methodGen.loadDOM());
/* 136:191 */     il.append(methodGen.loadCurrentNode());
/* 137:192 */     il.append(new INVOKEINTERFACE(getNodeValue, 2));
/* 138:    */     
/* 139:    */ 
/* 140:195 */     il.append(new INVOKEVIRTUAL(buildKeyIndex));
/* 141:    */     
/* 142:197 */     il.append(classGen.loadTranslet());
/* 143:198 */     il.append(new PUSH(cpg, getName()));
/* 144:199 */     il.append(methodGen.loadDOM());
/* 145:200 */     il.append(new INVOKEVIRTUAL(keyDom));
/* 146:    */     
/* 147:202 */     nextNode.setTarget(il.append(methodGen.loadIterator()));
/* 148:203 */     il.append(methodGen.nextNode());
/* 149:    */     
/* 150:205 */     il.append(InstructionConstants.DUP);
/* 151:206 */     il.append(methodGen.storeCurrentNode());
/* 152:207 */     il.append(new IFGE(loop));
/* 153:    */     
/* 154:    */ 
/* 155:210 */     il.append(methodGen.storeIterator());
/* 156:211 */     il.append(methodGen.storeCurrentNode());
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 160:    */   {
/* 161:220 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 162:221 */     InstructionList il = methodGen.getInstructionList();
/* 163:222 */     int current = methodGen.getLocalIndex("current");
/* 164:    */     
/* 165:    */ 
/* 166:225 */     int key = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "buildKeyIndex", "(Ljava/lang/String;ILjava/lang/Object;)V");
/* 167:    */     
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:230 */     int keyDom = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "setKeyIndexDom", "(Ljava/lang/String;Lorg/apache/xalan/xsltc/DOM;)V");
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:234 */     int getNodeIdent = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeIdent", "(I)I");
/* 176:    */     
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:239 */     int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getAxisIterator", "(I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 181:    */     
/* 182:    */ 
/* 183:    */ 
/* 184:243 */     il.append(methodGen.loadCurrentNode());
/* 185:244 */     il.append(methodGen.loadIterator());
/* 186:    */     
/* 187:    */ 
/* 188:247 */     il.append(methodGen.loadDOM());
/* 189:248 */     il.append(new PUSH(cpg, 4));
/* 190:249 */     il.append(new INVOKEINTERFACE(git, 2));
/* 191:    */     
/* 192:    */ 
/* 193:252 */     il.append(methodGen.loadCurrentNode());
/* 194:253 */     il.append(methodGen.setStartNode());
/* 195:254 */     il.append(methodGen.storeIterator());
/* 196:    */     
/* 197:    */ 
/* 198:257 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 199:258 */     InstructionHandle loop = il.append(InstructionConstants.NOP);
/* 200:    */     
/* 201:    */ 
/* 202:261 */     il.append(methodGen.loadCurrentNode());
/* 203:262 */     this._match.translate(classGen, methodGen);
/* 204:263 */     this._match.synthesize(classGen, methodGen);
/* 205:264 */     BranchHandle skipNode = il.append(new IFEQ(null));
/* 206:267 */     if ((this._useType instanceof NodeSetType))
/* 207:    */     {
/* 208:269 */       il.append(methodGen.loadCurrentNode());
/* 209:270 */       traverseNodeSet(classGen, methodGen, key);
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:273 */       il.append(classGen.loadTranslet());
/* 214:274 */       il.append(InstructionConstants.DUP);
/* 215:275 */       il.append(new PUSH(cpg, this._name.toString()));
/* 216:276 */       il.append(InstructionConstants.DUP_X1);
/* 217:277 */       il.append(methodGen.loadCurrentNode());
/* 218:278 */       this._use.translate(classGen, methodGen);
/* 219:279 */       il.append(new INVOKEVIRTUAL(key));
/* 220:    */       
/* 221:281 */       il.append(methodGen.loadDOM());
/* 222:282 */       il.append(new INVOKEVIRTUAL(keyDom));
/* 223:    */     }
/* 224:286 */     InstructionHandle skip = il.append(InstructionConstants.NOP);
/* 225:    */     
/* 226:288 */     il.append(methodGen.loadIterator());
/* 227:289 */     il.append(methodGen.nextNode());
/* 228:290 */     il.append(InstructionConstants.DUP);
/* 229:291 */     il.append(methodGen.storeCurrentNode());
/* 230:292 */     il.append(new IFGT(loop));
/* 231:    */     
/* 232:    */ 
/* 233:295 */     il.append(methodGen.storeIterator());
/* 234:296 */     il.append(methodGen.storeCurrentNode());
/* 235:    */     
/* 236:298 */     nextNode.setTarget(skip);
/* 237:299 */     skipNode.setTarget(skip);
/* 238:    */   }
/* 239:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Key
 * JD-Core Version:    0.7.0.1
 */