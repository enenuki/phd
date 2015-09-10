/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GETSTATIC;
/*   9:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  10:    */ import org.apache.bcel.generic.InstructionConstants;
/*  11:    */ import org.apache.bcel.generic.InstructionList;
/*  12:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  13:    */ import org.apache.bcel.generic.MethodGen;
/*  14:    */ import org.apache.bcel.generic.PUSH;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ import org.apache.xml.utils.XML11Char;
/*  22:    */ 
/*  23:    */ final class XslElement
/*  24:    */   extends Instruction
/*  25:    */ {
/*  26:    */   private String _prefix;
/*  27: 49 */   private boolean _ignore = false;
/*  28: 50 */   private boolean _isLiteralName = true;
/*  29:    */   private AttributeValueTemplate _name;
/*  30:    */   private AttributeValueTemplate _namespace;
/*  31:    */   
/*  32:    */   public void display(int indent)
/*  33:    */   {
/*  34: 58 */     indent(indent);
/*  35: 59 */     Util.println("Element " + this._name);
/*  36: 60 */     displayContents(indent + 4);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean declaresDefaultNS()
/*  40:    */   {
/*  41: 68 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void parseContents(Parser parser)
/*  45:    */   {
/*  46: 72 */     SymbolTable stable = parser.getSymbolTable();
/*  47:    */     
/*  48:    */ 
/*  49: 75 */     String name = getAttribute("name");
/*  50: 76 */     if (name == "")
/*  51:    */     {
/*  52: 77 */       ErrorMsg msg = new ErrorMsg("ILLEGAL_ELEM_NAME_ERR", name, this);
/*  53:    */       
/*  54: 79 */       parser.reportError(4, msg);
/*  55: 80 */       parseChildren(parser);
/*  56: 81 */       this._ignore = true;
/*  57: 82 */       return;
/*  58:    */     }
/*  59: 86 */     String namespace = getAttribute("namespace");
/*  60:    */     
/*  61:    */ 
/*  62: 89 */     this._isLiteralName = Util.isLiteral(name);
/*  63: 90 */     if (this._isLiteralName)
/*  64:    */     {
/*  65: 91 */       if (!XML11Char.isXML11ValidQName(name))
/*  66:    */       {
/*  67: 92 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_ELEM_NAME_ERR", name, this);
/*  68:    */         
/*  69: 94 */         parser.reportError(4, msg);
/*  70: 95 */         parseChildren(parser);
/*  71: 96 */         this._ignore = true;
/*  72: 97 */         return;
/*  73:    */       }
/*  74:100 */       QName qname = parser.getQNameSafe(name);
/*  75:101 */       String prefix = qname.getPrefix();
/*  76:102 */       String local = qname.getLocalPart();
/*  77:104 */       if (prefix == null) {
/*  78:105 */         prefix = "";
/*  79:    */       }
/*  80:108 */       if (!hasAttribute("namespace"))
/*  81:    */       {
/*  82:109 */         namespace = lookupNamespace(prefix);
/*  83:110 */         if (namespace == null)
/*  84:    */         {
/*  85:111 */           ErrorMsg err = new ErrorMsg("NAMESPACE_UNDEF_ERR", prefix, this);
/*  86:    */           
/*  87:113 */           parser.reportError(4, err);
/*  88:114 */           parseChildren(parser);
/*  89:115 */           this._ignore = true;
/*  90:116 */           return;
/*  91:    */         }
/*  92:118 */         this._prefix = prefix;
/*  93:119 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*  94:    */       }
/*  95:    */       else
/*  96:    */       {
/*  97:122 */         if (prefix == "")
/*  98:    */         {
/*  99:123 */           if (Util.isLiteral(namespace))
/* 100:    */           {
/* 101:124 */             prefix = lookupPrefix(namespace);
/* 102:125 */             if (prefix == null) {
/* 103:126 */               prefix = stable.generateNamespacePrefix();
/* 104:    */             }
/* 105:    */           }
/* 106:131 */           StringBuffer newName = new StringBuffer(prefix);
/* 107:132 */           if (prefix != "") {
/* 108:133 */             newName.append(':');
/* 109:    */           }
/* 110:135 */           name = local;
/* 111:    */         }
/* 112:137 */         this._prefix = prefix;
/* 113:138 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/* 114:    */       }
/* 115:    */     }
/* 116:    */     else
/* 117:    */     {
/* 118:145 */       this._namespace = (namespace == "" ? null : new AttributeValueTemplate(namespace, parser, this));
/* 119:    */     }
/* 120:149 */     this._name = new AttributeValueTemplate(name, parser, this);
/* 121:    */     
/* 122:151 */     String useSets = getAttribute("use-attribute-sets");
/* 123:152 */     if (useSets.length() > 0)
/* 124:    */     {
/* 125:153 */       if (!Util.isValidQNames(useSets))
/* 126:    */       {
/* 127:154 */         ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", useSets, this);
/* 128:155 */         parser.reportError(3, err);
/* 129:    */       }
/* 130:157 */       setFirstElement(new UseAttributeSets(useSets, parser));
/* 131:    */     }
/* 132:160 */     parseChildren(parser);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Type typeCheck(SymbolTable stable)
/* 136:    */     throws TypeCheckError
/* 137:    */   {
/* 138:167 */     if (!this._ignore)
/* 139:    */     {
/* 140:168 */       this._name.typeCheck(stable);
/* 141:169 */       if (this._namespace != null) {
/* 142:170 */         this._namespace.typeCheck(stable);
/* 143:    */       }
/* 144:    */     }
/* 145:173 */     typeCheckContents(stable);
/* 146:174 */     return Type.Void;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void translateLiteral(ClassGenerator classGen, MethodGenerator methodGen)
/* 150:    */   {
/* 151:183 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 152:184 */     InstructionList il = methodGen.getInstructionList();
/* 153:186 */     if (!this._ignore)
/* 154:    */     {
/* 155:187 */       il.append(methodGen.loadHandler());
/* 156:188 */       this._name.translate(classGen, methodGen);
/* 157:189 */       il.append(InstructionConstants.DUP2);
/* 158:190 */       il.append(methodGen.startElement());
/* 159:192 */       if (this._namespace != null)
/* 160:    */       {
/* 161:193 */         il.append(methodGen.loadHandler());
/* 162:194 */         il.append(new PUSH(cpg, this._prefix));
/* 163:195 */         this._namespace.translate(classGen, methodGen);
/* 164:196 */         il.append(methodGen.namespace());
/* 165:    */       }
/* 166:    */     }
/* 167:200 */     translateContents(classGen, methodGen);
/* 168:202 */     if (!this._ignore) {
/* 169:203 */       il.append(methodGen.endElement());
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 174:    */   {
/* 175:216 */     LocalVariableGen local = null;
/* 176:217 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 177:218 */     InstructionList il = methodGen.getInstructionList();
/* 178:221 */     if (this._isLiteralName)
/* 179:    */     {
/* 180:222 */       translateLiteral(classGen, methodGen);
/* 181:223 */       return;
/* 182:    */     }
/* 183:226 */     if (!this._ignore)
/* 184:    */     {
/* 185:229 */       LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", Util.getJCRefType("Ljava/lang/String;"), null);
/* 186:    */       
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:235 */       this._name.translate(classGen, methodGen);
/* 192:236 */       nameValue.setStart(il.append(new ASTORE(nameValue.getIndex())));
/* 193:237 */       il.append(new ALOAD(nameValue.getIndex()));
/* 194:    */       
/* 195:    */ 
/* 196:240 */       int check = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "checkQName", "(Ljava/lang/String;)V");
/* 197:    */       
/* 198:    */ 
/* 199:    */ 
/* 200:244 */       il.append(new INVOKESTATIC(check));
/* 201:    */       
/* 202:    */ 
/* 203:247 */       il.append(methodGen.loadHandler());
/* 204:    */       
/* 205:    */ 
/* 206:250 */       nameValue.setEnd(il.append(new ALOAD(nameValue.getIndex())));
/* 207:252 */       if (this._namespace != null)
/* 208:    */       {
/* 209:253 */         this._namespace.translate(classGen, methodGen);
/* 210:    */       }
/* 211:    */       else
/* 212:    */       {
/* 213:262 */         String transletClassName = getXSLTC().getClassName();
/* 214:263 */         il.append(InstructionConstants.DUP);
/* 215:264 */         il.append(new PUSH(cpg, getNodeIDForStylesheetNSLookup()));
/* 216:265 */         il.append(new GETSTATIC(cpg.addFieldref(transletClassName, "_sNamespaceAncestorsArray", "[I")));
/* 217:    */         
/* 218:    */ 
/* 219:    */ 
/* 220:269 */         il.append(new GETSTATIC(cpg.addFieldref(transletClassName, "_sPrefixURIsIdxArray", "[I")));
/* 221:    */         
/* 222:    */ 
/* 223:    */ 
/* 224:273 */         il.append(new GETSTATIC(cpg.addFieldref(transletClassName, "_sPrefixURIPairsArray", "[Ljava/lang/String;")));
/* 225:    */         
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:278 */         il.append(InstructionConstants.ICONST_0);
/* 230:279 */         il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "lookupStylesheetQNameNamespace", "(Ljava/lang/String;I[I[I[Ljava/lang/String;Z)Ljava/lang/String;")));
/* 231:    */       }
/* 232:287 */       il.append(methodGen.loadHandler());
/* 233:288 */       il.append(methodGen.loadDOM());
/* 234:289 */       il.append(methodGen.loadCurrentNode());
/* 235:    */       
/* 236:    */ 
/* 237:292 */       il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "startXslElement", "(Ljava/lang/String;Ljava/lang/String;" + Constants.TRANSLET_OUTPUT_SIG + "Lorg/apache/xalan/xsltc/DOM;" + "I)" + "Ljava/lang/String;")));
/* 238:    */     }
/* 239:302 */     translateContents(classGen, methodGen);
/* 240:304 */     if (!this._ignore) {
/* 241:305 */       il.append(methodGen.endElement());
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void translateContents(ClassGenerator classGen, MethodGenerator methodGen)
/* 246:    */   {
/* 247:315 */     int n = elementCount();
/* 248:316 */     for (int i = 0; i < n; i++)
/* 249:    */     {
/* 250:317 */       SyntaxTreeNode item = (SyntaxTreeNode)getContents().elementAt(i);
/* 251:319 */       if ((!this._ignore) || (!(item instanceof XslAttribute))) {
/* 252:320 */         item.translate(classGen, methodGen);
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.XslElement
 * JD-Core Version:    0.7.0.1
 */