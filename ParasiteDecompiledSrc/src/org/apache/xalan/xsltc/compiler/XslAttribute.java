/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GETFIELD;
/*   9:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  10:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.PUSH;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ import org.apache.xml.serializer.ElemDesc;
/*  22:    */ import org.apache.xml.utils.XML11Char;
/*  23:    */ 
/*  24:    */ final class XslAttribute
/*  25:    */   extends Instruction
/*  26:    */ {
/*  27:    */   private String _prefix;
/*  28:    */   private AttributeValue _name;
/*  29: 57 */   private AttributeValueTemplate _namespace = null;
/*  30: 58 */   private boolean _ignore = false;
/*  31: 59 */   private boolean _isLiteral = false;
/*  32:    */   
/*  33:    */   public AttributeValue getName()
/*  34:    */   {
/*  35: 65 */     return this._name;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void display(int indent)
/*  39:    */   {
/*  40: 72 */     indent(indent);
/*  41: 73 */     Util.println("Attribute " + this._name);
/*  42: 74 */     displayContents(indent + 4);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void parseContents(Parser parser)
/*  46:    */   {
/*  47: 81 */     boolean generated = false;
/*  48: 82 */     SymbolTable stable = parser.getSymbolTable();
/*  49:    */     
/*  50: 84 */     String name = getAttribute("name");
/*  51: 85 */     String namespace = getAttribute("namespace");
/*  52: 86 */     QName qname = parser.getQName(name, false);
/*  53: 87 */     String prefix = qname.getPrefix();
/*  54: 89 */     if (((prefix != null) && (prefix.equals("xmlns"))) || (name.equals("xmlns")))
/*  55:    */     {
/*  56: 90 */       reportError(this, parser, "ILLEGAL_ATTR_NAME_ERR", name);
/*  57: 91 */       return;
/*  58:    */     }
/*  59: 94 */     this._isLiteral = Util.isLiteral(name);
/*  60: 95 */     if ((this._isLiteral) && 
/*  61: 96 */       (!XML11Char.isXML11ValidQName(name)))
/*  62:    */     {
/*  63: 97 */       reportError(this, parser, "ILLEGAL_ATTR_NAME_ERR", name);
/*  64: 98 */       return;
/*  65:    */     }
/*  66:103 */     SyntaxTreeNode parent = getParent();
/*  67:104 */     Vector siblings = parent.getContents();
/*  68:105 */     for (int i = 0; i < parent.elementCount(); i++)
/*  69:    */     {
/*  70:106 */       SyntaxTreeNode item = (SyntaxTreeNode)siblings.elementAt(i);
/*  71:107 */       if (item == this) {
/*  72:    */         break;
/*  73:    */       }
/*  74:110 */       if ((!(item instanceof XslAttribute)) && 
/*  75:111 */         (!(item instanceof UseAttributeSets)) && 
/*  76:112 */         (!(item instanceof LiteralAttribute)) && 
/*  77:113 */         (!(item instanceof Text))) {
/*  78:117 */         if ((!(item instanceof If)) && 
/*  79:118 */           (!(item instanceof Choose)) && 
/*  80:119 */           (!(item instanceof CopyOf)) && 
/*  81:120 */           (!(item instanceof VariableBase))) {
/*  82:123 */           reportWarning(this, parser, "STRAY_ATTRIBUTE_ERR", name);
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:127 */     if ((namespace != null) && (namespace != ""))
/*  87:    */     {
/*  88:128 */       this._prefix = lookupPrefix(namespace);
/*  89:129 */       this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*  90:    */     }
/*  91:132 */     else if ((prefix != null) && (prefix != ""))
/*  92:    */     {
/*  93:133 */       this._prefix = prefix;
/*  94:134 */       namespace = lookupNamespace(prefix);
/*  95:135 */       if (namespace != null) {
/*  96:136 */         this._namespace = new AttributeValueTemplate(namespace, parser, this);
/*  97:    */       }
/*  98:    */     }
/*  99:141 */     if (this._namespace != null)
/* 100:    */     {
/* 101:143 */       if ((this._prefix == null) || (this._prefix == ""))
/* 102:    */       {
/* 103:144 */         if (prefix != null)
/* 104:    */         {
/* 105:145 */           this._prefix = prefix;
/* 106:    */         }
/* 107:    */         else
/* 108:    */         {
/* 109:148 */           this._prefix = stable.generateNamespacePrefix();
/* 110:149 */           generated = true;
/* 111:    */         }
/* 112:    */       }
/* 113:152 */       else if ((prefix != null) && (!prefix.equals(this._prefix))) {
/* 114:153 */         this._prefix = prefix;
/* 115:    */       }
/* 116:156 */       name = this._prefix + ":" + qname.getLocalPart();
/* 117:163 */       if (((parent instanceof LiteralElement)) && (!generated)) {
/* 118:164 */         ((LiteralElement)parent).registerNamespace(this._prefix, namespace, stable, false);
/* 119:    */       }
/* 120:    */     }
/* 121:170 */     if ((parent instanceof LiteralElement)) {
/* 122:171 */       ((LiteralElement)parent).addAttribute(this);
/* 123:    */     }
/* 124:174 */     this._name = AttributeValue.create(this, name, parser);
/* 125:175 */     parseChildren(parser);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Type typeCheck(SymbolTable stable)
/* 129:    */     throws TypeCheckError
/* 130:    */   {
/* 131:179 */     if (!this._ignore)
/* 132:    */     {
/* 133:180 */       this._name.typeCheck(stable);
/* 134:181 */       if (this._namespace != null) {
/* 135:182 */         this._namespace.typeCheck(stable);
/* 136:    */       }
/* 137:184 */       typeCheckContents(stable);
/* 138:    */     }
/* 139:186 */     return Type.Void;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 143:    */   {
/* 144:193 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 145:194 */     InstructionList il = methodGen.getInstructionList();
/* 146:196 */     if (this._ignore) {
/* 147:196 */       return;
/* 148:    */     }
/* 149:197 */     this._ignore = true;
/* 150:200 */     if (this._namespace != null)
/* 151:    */     {
/* 152:202 */       il.append(methodGen.loadHandler());
/* 153:203 */       il.append(new PUSH(cpg, this._prefix));
/* 154:204 */       this._namespace.translate(classGen, methodGen);
/* 155:205 */       il.append(methodGen.namespace());
/* 156:    */     }
/* 157:208 */     if (!this._isLiteral)
/* 158:    */     {
/* 159:210 */       LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", Util.getJCRefType("Ljava/lang/String;"), null);
/* 160:    */       
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:216 */       this._name.translate(classGen, methodGen);
/* 166:217 */       nameValue.setStart(il.append(new ASTORE(nameValue.getIndex())));
/* 167:218 */       il.append(new ALOAD(nameValue.getIndex()));
/* 168:    */       
/* 169:    */ 
/* 170:221 */       int check = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "checkAttribQName", "(Ljava/lang/String;)V");
/* 171:    */       
/* 172:    */ 
/* 173:    */ 
/* 174:225 */       il.append(new INVOKESTATIC(check));
/* 175:    */       
/* 176:    */ 
/* 177:228 */       il.append(methodGen.loadHandler());
/* 178:229 */       il.append(InstructionConstants.DUP);
/* 179:    */       
/* 180:    */ 
/* 181:232 */       nameValue.setEnd(il.append(new ALOAD(nameValue.getIndex())));
/* 182:    */     }
/* 183:    */     else
/* 184:    */     {
/* 185:235 */       il.append(methodGen.loadHandler());
/* 186:236 */       il.append(InstructionConstants.DUP);
/* 187:    */       
/* 188:    */ 
/* 189:239 */       this._name.translate(classGen, methodGen);
/* 190:    */     }
/* 191:244 */     if ((elementCount() == 1) && ((elementAt(0) instanceof Text)))
/* 192:    */     {
/* 193:245 */       il.append(new PUSH(cpg, ((Text)elementAt(0)).getText()));
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:248 */       il.append(classGen.loadTranslet());
/* 198:249 */       il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lorg/apache/xalan/xsltc/runtime/StringValueHandler;")));
/* 199:    */       
/* 200:    */ 
/* 201:252 */       il.append(InstructionConstants.DUP);
/* 202:253 */       il.append(methodGen.storeHandler());
/* 203:    */       
/* 204:255 */       translateContents(classGen, methodGen);
/* 205:    */       
/* 206:257 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.StringValueHandler", "getValue", "()Ljava/lang/String;")));
/* 207:    */     }
/* 208:262 */     SyntaxTreeNode parent = getParent();
/* 209:263 */     if (((parent instanceof LiteralElement)) && (((LiteralElement)parent).allAttributesUnique()))
/* 210:    */     {
/* 211:265 */       int flags = 0;
/* 212:266 */       ElemDesc elemDesc = ((LiteralElement)parent).getElemDesc();
/* 213:269 */       if ((elemDesc != null) && ((this._name instanceof SimpleAttributeValue)))
/* 214:    */       {
/* 215:270 */         String attrName = ((SimpleAttributeValue)this._name).toString();
/* 216:271 */         if (elemDesc.isAttrFlagSet(attrName, 4)) {
/* 217:272 */           flags |= 0x2;
/* 218:274 */         } else if (elemDesc.isAttrFlagSet(attrName, 2)) {
/* 219:275 */           flags |= 0x4;
/* 220:    */         }
/* 221:    */       }
/* 222:278 */       il.append(new PUSH(cpg, flags));
/* 223:279 */       il.append(methodGen.uniqueAttribute());
/* 224:    */     }
/* 225:    */     else
/* 226:    */     {
/* 227:283 */       il.append(methodGen.attribute());
/* 228:    */     }
/* 229:287 */     il.append(methodGen.storeHandler());
/* 230:    */   }
/* 231:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.XslAttribute
 * JD-Core Version:    0.7.0.1
 */