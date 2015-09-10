/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.classfile.Field;
/*   6:    */ import org.apache.bcel.generic.BranchHandle;
/*   7:    */ import org.apache.bcel.generic.CHECKCAST;
/*   8:    */ import org.apache.bcel.generic.ClassGen;
/*   9:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*  10:    */ import org.apache.bcel.generic.IFNONNULL;
/*  11:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  12:    */ import org.apache.bcel.generic.Instruction;
/*  13:    */ import org.apache.bcel.generic.InstructionConstants;
/*  14:    */ import org.apache.bcel.generic.InstructionList;
/*  15:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  16:    */ import org.apache.bcel.generic.MethodGen;
/*  17:    */ import org.apache.bcel.generic.PUSH;
/*  18:    */ import org.apache.bcel.generic.PUTFIELD;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.ObjectType;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  25:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*  26:    */ 
/*  27:    */ final class Param
/*  28:    */   extends VariableBase
/*  29:    */ {
/*  30: 57 */   private boolean _isInSimpleNamedTemplate = false;
/*  31:    */   
/*  32:    */   public String toString()
/*  33:    */   {
/*  34: 63 */     return "param(" + this._name + ")";
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Instruction setLoadInstruction(Instruction instruction)
/*  38:    */   {
/*  39: 71 */     Instruction tmp = this._loadInstruction;
/*  40: 72 */     this._loadInstruction = instruction;
/*  41: 73 */     return tmp;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Instruction setStoreInstruction(Instruction instruction)
/*  45:    */   {
/*  46: 81 */     Instruction tmp = this._storeInstruction;
/*  47: 82 */     this._storeInstruction = instruction;
/*  48: 83 */     return tmp;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void display(int indent)
/*  52:    */   {
/*  53: 90 */     indent(indent);
/*  54: 91 */     System.out.println("param " + this._name);
/*  55: 92 */     if (this._select != null)
/*  56:    */     {
/*  57: 93 */       indent(indent + 4);
/*  58: 94 */       System.out.println("select " + this._select.toString());
/*  59:    */     }
/*  60: 96 */     displayContents(indent + 4);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void parseContents(Parser parser)
/*  64:    */   {
/*  65:106 */     super.parseContents(parser);
/*  66:    */     
/*  67:    */ 
/*  68:109 */     SyntaxTreeNode parent = getParent();
/*  69:110 */     if ((parent instanceof Stylesheet))
/*  70:    */     {
/*  71:112 */       this._isLocal = false;
/*  72:    */       
/*  73:114 */       Param param = parser.getSymbolTable().lookupParam(this._name);
/*  74:116 */       if (param != null)
/*  75:    */       {
/*  76:117 */         int us = getImportPrecedence();
/*  77:118 */         int them = param.getImportPrecedence();
/*  78:120 */         if (us == them)
/*  79:    */         {
/*  80:121 */           String name = this._name.toString();
/*  81:122 */           reportError(this, parser, "VARIABLE_REDEF_ERR", name);
/*  82:    */         }
/*  83:    */         else
/*  84:    */         {
/*  85:125 */           if (them > us)
/*  86:    */           {
/*  87:126 */             this._ignore = true;
/*  88:127 */             return;
/*  89:    */           }
/*  90:130 */           param.disable();
/*  91:    */         }
/*  92:    */       }
/*  93:134 */       ((Stylesheet)parent).addParam(this);
/*  94:135 */       parser.getSymbolTable().addParam(this);
/*  95:    */     }
/*  96:137 */     else if ((parent instanceof Template))
/*  97:    */     {
/*  98:138 */       Template template = (Template)parent;
/*  99:139 */       this._isLocal = true;
/* 100:140 */       template.addParameter(this);
/* 101:141 */       if (template.isSimpleNamedTemplate()) {
/* 102:142 */         this._isInSimpleNamedTemplate = true;
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Type typeCheck(SymbolTable stable)
/* 108:    */     throws TypeCheckError
/* 109:    */   {
/* 110:153 */     if (this._select != null)
/* 111:    */     {
/* 112:154 */       this._type = this._select.typeCheck(stable);
/* 113:155 */       if ((!(this._type instanceof ReferenceType)) && (!(this._type instanceof ObjectType))) {
/* 114:156 */         this._select = new CastExpr(this._select, Type.Reference);
/* 115:    */       }
/* 116:    */     }
/* 117:159 */     else if (hasContents())
/* 118:    */     {
/* 119:160 */       typeCheckContents(stable);
/* 120:    */     }
/* 121:162 */     this._type = Type.Reference;
/* 122:    */     
/* 123:    */ 
/* 124:    */ 
/* 125:166 */     return Type.Void;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 129:    */   {
/* 130:170 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 131:171 */     InstructionList il = methodGen.getInstructionList();
/* 132:173 */     if (this._ignore) {
/* 133:173 */       return;
/* 134:    */     }
/* 135:174 */     this._ignore = true;
/* 136:    */     
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:181 */     String name = BasisLibrary.mapQNameToJavaName(this._name.toString());
/* 143:182 */     String signature = this._type.toSignature();
/* 144:183 */     String className = this._type.getClassName();
/* 145:185 */     if (isLocal())
/* 146:    */     {
/* 147:191 */       if (this._isInSimpleNamedTemplate)
/* 148:    */       {
/* 149:192 */         il.append(loadInstruction());
/* 150:193 */         BranchHandle ifBlock = il.append(new IFNONNULL(null));
/* 151:194 */         translateValue(classGen, methodGen);
/* 152:195 */         il.append(storeInstruction());
/* 153:196 */         ifBlock.setTarget(il.append(InstructionConstants.NOP));
/* 154:197 */         return;
/* 155:    */       }
/* 156:200 */       il.append(classGen.loadTranslet());
/* 157:201 */       il.append(new PUSH(cpg, name));
/* 158:202 */       translateValue(classGen, methodGen);
/* 159:203 */       il.append(new PUSH(cpg, true));
/* 160:    */       
/* 161:    */ 
/* 162:206 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;")));
/* 163:209 */       if (className != "") {
/* 164:210 */         il.append(new CHECKCAST(cpg.addClass(className)));
/* 165:    */       }
/* 166:213 */       this._type.translateUnBox(classGen, methodGen);
/* 167:215 */       if (this._refs.isEmpty())
/* 168:    */       {
/* 169:216 */         il.append(this._type.POP());
/* 170:217 */         this._local = null;
/* 171:    */       }
/* 172:    */       else
/* 173:    */       {
/* 174:220 */         this._local = methodGen.addLocalVariable2(name, this._type.toJCType(), null);
/* 175:    */         
/* 176:    */ 
/* 177:    */ 
/* 178:224 */         this._local.setStart(il.append(this._type.STORE(this._local.getIndex())));
/* 179:    */       }
/* 180:    */     }
/* 181:228 */     else if (classGen.containsField(name) == null)
/* 182:    */     {
/* 183:229 */       classGen.addField(new Field(1, cpg.addUtf8(name), cpg.addUtf8(signature), null, cpg.getConstantPool()));
/* 184:    */       
/* 185:    */ 
/* 186:232 */       il.append(classGen.loadTranslet());
/* 187:233 */       il.append(InstructionConstants.DUP);
/* 188:234 */       il.append(new PUSH(cpg, name));
/* 189:235 */       translateValue(classGen, methodGen);
/* 190:236 */       il.append(new PUSH(cpg, true));
/* 191:    */       
/* 192:    */ 
/* 193:239 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addParameter", "(Ljava/lang/String;Ljava/lang/Object;Z)Ljava/lang/Object;")));
/* 194:    */       
/* 195:    */ 
/* 196:    */ 
/* 197:243 */       this._type.translateUnBox(classGen, methodGen);
/* 198:246 */       if (className != "") {
/* 199:247 */         il.append(new CHECKCAST(cpg.addClass(className)));
/* 200:    */       }
/* 201:249 */       il.append(new PUTFIELD(cpg.addFieldref(classGen.getClassName(), name, signature)));
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Param
 * JD-Core Version:    0.7.0.1
 */