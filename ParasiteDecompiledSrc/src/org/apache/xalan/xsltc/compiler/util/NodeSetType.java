/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFLT;
/*  10:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  11:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  12:    */ import org.apache.bcel.generic.Instruction;
/*  13:    */ import org.apache.bcel.generic.InstructionConstants;
/*  14:    */ import org.apache.bcel.generic.InstructionList;
/*  15:    */ import org.apache.bcel.generic.MethodGen;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  18:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  19:    */ 
/*  20:    */ public final class NodeSetType
/*  21:    */   extends Type
/*  22:    */ {
/*  23:    */   public String toString()
/*  24:    */   {
/*  25: 46 */     return "node-set";
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean identicalTo(Type other)
/*  29:    */   {
/*  30: 50 */     return this == other;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toSignature()
/*  34:    */   {
/*  35: 54 */     return "Lorg/apache/xml/dtm/DTMAxisIterator;";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public org.apache.bcel.generic.Type toJCType()
/*  39:    */   {
/*  40: 58 */     return new org.apache.bcel.generic.ObjectType("org.apache.xml.dtm.DTMAxisIterator");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  44:    */   {
/*  45: 71 */     if (type == Type.String)
/*  46:    */     {
/*  47: 72 */       translateTo(classGen, methodGen, (StringType)type);
/*  48:    */     }
/*  49: 74 */     else if (type == Type.Boolean)
/*  50:    */     {
/*  51: 75 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  52:    */     }
/*  53: 77 */     else if (type == Type.Real)
/*  54:    */     {
/*  55: 78 */       translateTo(classGen, methodGen, (RealType)type);
/*  56:    */     }
/*  57: 80 */     else if (type == Type.Node)
/*  58:    */     {
/*  59: 81 */       translateTo(classGen, methodGen, (NodeType)type);
/*  60:    */     }
/*  61: 83 */     else if (type == Type.Reference)
/*  62:    */     {
/*  63: 84 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  64:    */     }
/*  65: 86 */     else if (type == Type.Object)
/*  66:    */     {
/*  67: 87 */       translateTo(classGen, methodGen, (ObjectType)type);
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 90 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  72:    */       
/*  73: 92 */       classGen.getParser().reportError(2, err);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/*  78:    */   {
/*  79:104 */     InstructionList il = methodGen.getInstructionList();
/*  80:105 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  81:106 */     if (clazz.getName().equals("org.w3c.dom.NodeList"))
/*  82:    */     {
/*  83:110 */       il.append(classGen.loadTranslet());
/*  84:111 */       il.append(methodGen.loadDOM());
/*  85:112 */       int convert = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "nodeList2Iterator", "(Lorg/w3c/dom/NodeList;Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:119 */       il.append(new INVOKESTATIC(convert));
/*  93:    */     }
/*  94:121 */     else if (clazz.getName().equals("org.w3c.dom.Node"))
/*  95:    */     {
/*  96:125 */       il.append(classGen.loadTranslet());
/*  97:126 */       il.append(methodGen.loadDOM());
/*  98:127 */       int convert = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "node2Iterator", "(Lorg/w3c/dom/Node;Lorg/apache/xalan/xsltc/Translet;Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xml/dtm/DTMAxisIterator;");
/*  99:    */       
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:134 */       il.append(new INVOKESTATIC(convert));
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:137 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 110:    */       
/* 111:139 */       classGen.getParser().reportError(2, err);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 116:    */   {
/* 117:154 */     InstructionList il = methodGen.getInstructionList();
/* 118:155 */     FlowList falsel = translateToDesynthesized(classGen, methodGen, type);
/* 119:156 */     il.append(InstructionConstants.ICONST_1);
/* 120:157 */     BranchHandle truec = il.append(new GOTO(null));
/* 121:158 */     falsel.backPatch(il.append(InstructionConstants.ICONST_0));
/* 122:159 */     truec.setTarget(il.append(InstructionConstants.NOP));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/* 126:    */   {
/* 127:170 */     InstructionList il = methodGen.getInstructionList();
/* 128:171 */     getFirstNode(classGen, methodGen);
/* 129:172 */     il.append(InstructionConstants.DUP);
/* 130:173 */     BranchHandle falsec = il.append(new IFLT(null));
/* 131:174 */     Type.Node.translateTo(classGen, methodGen, type);
/* 132:175 */     BranchHandle truec = il.append(new GOTO(null));
/* 133:176 */     falsec.setTarget(il.append(InstructionConstants.POP));
/* 134:177 */     il.append(new PUSH(classGen.getConstantPool(), ""));
/* 135:178 */     truec.setTarget(il.append(InstructionConstants.NOP));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/* 139:    */   {
/* 140:189 */     translateTo(classGen, methodGen, Type.String);
/* 141:190 */     Type.String.translateTo(classGen, methodGen, Type.Real);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, NodeType type)
/* 145:    */   {
/* 146:200 */     getFirstNode(classGen, methodGen);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ObjectType type)
/* 150:    */   {
/* 151:210 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 155:    */   {
/* 156:223 */     InstructionList il = methodGen.getInstructionList();
/* 157:224 */     getFirstNode(classGen, methodGen);
/* 158:225 */     return new FlowList(il.append(new IFLT(null)));
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/* 162:    */   {
/* 163:236 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 167:    */   {
/* 168:246 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 169:247 */     InstructionList il = methodGen.getInstructionList();
/* 170:248 */     String className = clazz.getName();
/* 171:    */     
/* 172:250 */     il.append(methodGen.loadDOM());
/* 173:251 */     il.append(InstructionConstants.SWAP);
/* 174:253 */     if (className.equals("org.w3c.dom.Node"))
/* 175:    */     {
/* 176:254 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNode", "(Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/w3c/dom/Node;");
/* 177:    */       
/* 178:    */ 
/* 179:257 */       il.append(new INVOKEINTERFACE(index, 2));
/* 180:    */     }
/* 181:259 */     else if ((className.equals("org.w3c.dom.NodeList")) || (className.equals("java.lang.Object")))
/* 182:    */     {
/* 183:261 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNodeList", "(Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/w3c/dom/NodeList;");
/* 184:    */       
/* 185:    */ 
/* 186:264 */       il.append(new INVOKEINTERFACE(index, 2));
/* 187:    */     }
/* 188:266 */     else if (className.equals("java.lang.String"))
/* 189:    */     {
/* 190:267 */       int next = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "next", "()I");
/* 191:    */       
/* 192:269 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getStringValueX", "(I)Ljava/lang/String;");
/* 193:    */       
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:274 */       il.append(new INVOKEINTERFACE(next, 1));
/* 198:    */       
/* 199:276 */       il.append(new INVOKEINTERFACE(index, 2));
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:280 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), className);
/* 204:    */       
/* 205:282 */       classGen.getParser().reportError(2, err);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   private void getFirstNode(ClassGenerator classGen, MethodGenerator methodGen)
/* 210:    */   {
/* 211:291 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 212:292 */     InstructionList il = methodGen.getInstructionList();
/* 213:293 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "next", "()I"), 1));
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 217:    */   {
/* 218:303 */     translateTo(classGen, methodGen, Type.Reference);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 222:    */   {
/* 223:311 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getClassName()
/* 227:    */   {
/* 228:318 */     return "org.apache.xml.dtm.DTMAxisIterator";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Instruction LOAD(int slot)
/* 232:    */   {
/* 233:323 */     return new ALOAD(slot);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Instruction STORE(int slot)
/* 237:    */   {
/* 238:327 */     return new ASTORE(slot);
/* 239:    */   }
/* 240:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NodeSetType
 * JD-Core Version:    0.7.0.1
 */