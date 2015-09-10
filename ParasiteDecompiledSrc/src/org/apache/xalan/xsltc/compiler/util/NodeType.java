/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.CHECKCAST;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.GETFIELD;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.ILOAD;
/*  11:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  12:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  13:    */ import org.apache.bcel.generic.ISTORE;
/*  14:    */ import org.apache.bcel.generic.Instruction;
/*  15:    */ import org.apache.bcel.generic.InstructionConstants;
/*  16:    */ import org.apache.bcel.generic.InstructionList;
/*  17:    */ import org.apache.bcel.generic.MethodGen;
/*  18:    */ import org.apache.bcel.generic.NEW;
/*  19:    */ import org.apache.bcel.generic.PUSH;
/*  20:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  21:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  22:    */ 
/*  23:    */ public final class NodeType
/*  24:    */   extends Type
/*  25:    */ {
/*  26:    */   private final int _type;
/*  27:    */   
/*  28:    */   protected NodeType()
/*  29:    */   {
/*  30: 50 */     this(-1);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected NodeType(int type)
/*  34:    */   {
/*  35: 54 */     this._type = type;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getType()
/*  39:    */   {
/*  40: 58 */     return this._type;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String toString()
/*  44:    */   {
/*  45: 62 */     return "node-type";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean identicalTo(Type other)
/*  49:    */   {
/*  50: 66 */     return other instanceof NodeType;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int hashCode()
/*  54:    */   {
/*  55: 70 */     return this._type;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String toSignature()
/*  59:    */   {
/*  60: 74 */     return "I";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public org.apache.bcel.generic.Type toJCType()
/*  64:    */   {
/*  65: 78 */     return org.apache.bcel.generic.Type.INT;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  69:    */   {
/*  70: 90 */     if (type == Type.String)
/*  71:    */     {
/*  72: 91 */       translateTo(classGen, methodGen, (StringType)type);
/*  73:    */     }
/*  74: 93 */     else if (type == Type.Boolean)
/*  75:    */     {
/*  76: 94 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  77:    */     }
/*  78: 96 */     else if (type == Type.Real)
/*  79:    */     {
/*  80: 97 */       translateTo(classGen, methodGen, (RealType)type);
/*  81:    */     }
/*  82: 99 */     else if (type == Type.NodeSet)
/*  83:    */     {
/*  84:100 */       translateTo(classGen, methodGen, (NodeSetType)type);
/*  85:    */     }
/*  86:102 */     else if (type == Type.Reference)
/*  87:    */     {
/*  88:103 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  89:    */     }
/*  90:105 */     else if (type == Type.Object)
/*  91:    */     {
/*  92:106 */       translateTo(classGen, methodGen, (ObjectType)type);
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:109 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  97:    */       
/*  98:111 */       classGen.getParser().reportError(2, err);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/* 103:    */   {
/* 104:122 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 105:123 */     InstructionList il = methodGen.getInstructionList();
/* 106:    */     int index;
/* 107:125 */     switch (this._type)
/* 108:    */     {
/* 109:    */     case 1: 
/* 110:    */     case 9: 
/* 111:128 */       il.append(methodGen.loadDOM());
/* 112:129 */       il.append(InstructionConstants.SWAP);
/* 113:130 */       index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getElementValue", "(I)Ljava/lang/String;");
/* 114:    */       
/* 115:    */ 
/* 116:133 */       il.append(new INVOKEINTERFACE(index, 2));
/* 117:134 */       break;
/* 118:    */     case -1: 
/* 119:    */     case 2: 
/* 120:    */     case 7: 
/* 121:    */     case 8: 
/* 122:140 */       il.append(methodGen.loadDOM());
/* 123:141 */       il.append(InstructionConstants.SWAP);
/* 124:142 */       index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getStringValueX", "(I)Ljava/lang/String;");
/* 125:    */       
/* 126:    */ 
/* 127:145 */       il.append(new INVOKEINTERFACE(index, 2));
/* 128:146 */       break;
/* 129:    */     case 0: 
/* 130:    */     case 3: 
/* 131:    */     case 4: 
/* 132:    */     case 5: 
/* 133:    */     case 6: 
/* 134:    */     default: 
/* 135:149 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/* 136:    */       
/* 137:151 */       classGen.getParser().reportError(2, err);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 142:    */   {
/* 143:166 */     InstructionList il = methodGen.getInstructionList();
/* 144:167 */     FlowList falsel = translateToDesynthesized(classGen, methodGen, type);
/* 145:168 */     il.append(InstructionConstants.ICONST_1);
/* 146:169 */     BranchHandle truec = il.append(new GOTO(null));
/* 147:170 */     falsel.backPatch(il.append(InstructionConstants.ICONST_0));
/* 148:171 */     truec.setTarget(il.append(InstructionConstants.NOP));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/* 152:    */   {
/* 153:182 */     translateTo(classGen, methodGen, Type.String);
/* 154:183 */     Type.String.translateTo(classGen, methodGen, Type.Real);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, NodeSetType type)
/* 158:    */   {
/* 159:194 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 160:195 */     InstructionList il = methodGen.getInstructionList();
/* 161:    */     
/* 162:    */ 
/* 163:198 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.SingletonIterator")));
/* 164:199 */     il.append(InstructionConstants.DUP_X1);
/* 165:200 */     il.append(InstructionConstants.SWAP);
/* 166:201 */     int init = cpg.addMethodref("org.apache.xalan.xsltc.dom.SingletonIterator", "<init>", "(I)V");
/* 167:    */     
/* 168:203 */     il.append(new INVOKESPECIAL(init));
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ObjectType type)
/* 172:    */   {
/* 173:213 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 177:    */   {
/* 178:226 */     InstructionList il = methodGen.getInstructionList();
/* 179:227 */     return new FlowList(il.append(new IFEQ(null)));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/* 183:    */   {
/* 184:238 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 185:239 */     InstructionList il = methodGen.getInstructionList();
/* 186:240 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.runtime.Node")));
/* 187:241 */     il.append(InstructionConstants.DUP_X1);
/* 188:242 */     il.append(InstructionConstants.SWAP);
/* 189:243 */     il.append(new PUSH(cpg, this._type));
/* 190:244 */     il.append(new INVOKESPECIAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.Node", "<init>", "(II)V")));
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 194:    */   {
/* 195:255 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 196:256 */     InstructionList il = methodGen.getInstructionList();
/* 197:    */     
/* 198:258 */     String className = clazz.getName();
/* 199:259 */     if (className.equals("java.lang.String"))
/* 200:    */     {
/* 201:260 */       translateTo(classGen, methodGen, Type.String);
/* 202:261 */       return;
/* 203:    */     }
/* 204:264 */     il.append(methodGen.loadDOM());
/* 205:265 */     il.append(InstructionConstants.SWAP);
/* 206:267 */     if ((className.equals("org.w3c.dom.Node")) || (className.equals("java.lang.Object")))
/* 207:    */     {
/* 208:269 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNode", "(I)Lorg/w3c/dom/Node;");
/* 209:    */       
/* 210:    */ 
/* 211:272 */       il.append(new INVOKEINTERFACE(index, 2));
/* 212:    */     }
/* 213:274 */     else if (className.equals("org.w3c.dom.NodeList"))
/* 214:    */     {
/* 215:275 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNodeList", "(I)Lorg/w3c/dom/NodeList;");
/* 216:    */       
/* 217:    */ 
/* 218:278 */       il.append(new INVOKEINTERFACE(index, 2));
/* 219:    */     }
/* 220:    */     else
/* 221:    */     {
/* 222:281 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), className);
/* 223:    */       
/* 224:283 */       classGen.getParser().reportError(2, err);
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 229:    */   {
/* 230:292 */     translateTo(classGen, methodGen, Type.Reference);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 234:    */   {
/* 235:300 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 236:301 */     InstructionList il = methodGen.getInstructionList();
/* 237:302 */     il.append(new CHECKCAST(cpg.addClass("org.apache.xalan.xsltc.runtime.Node")));
/* 238:303 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.Node", "node", "I")));
/* 239:    */   }
/* 240:    */   
/* 241:    */   public String getClassName()
/* 242:    */   {
/* 243:312 */     return "org.apache.xalan.xsltc.runtime.Node";
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Instruction LOAD(int slot)
/* 247:    */   {
/* 248:316 */     return new ILOAD(slot);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Instruction STORE(int slot)
/* 252:    */   {
/* 253:320 */     return new ISTORE(slot);
/* 254:    */   }
/* 255:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NodeType
 * JD-Core Version:    0.7.0.1
 */