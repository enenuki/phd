/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.IFEQ;
/*   8:    */ import org.apache.bcel.generic.ILOAD;
/*   9:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  10:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  11:    */ import org.apache.bcel.generic.Instruction;
/*  12:    */ import org.apache.bcel.generic.InstructionConstants;
/*  13:    */ import org.apache.bcel.generic.InstructionList;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.PUSH;
/*  16:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  17:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  18:    */ 
/*  19:    */ public final class ReferenceType
/*  20:    */   extends Type
/*  21:    */ {
/*  22:    */   public String toString()
/*  23:    */   {
/*  24: 49 */     return "reference";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean identicalTo(Type other)
/*  28:    */   {
/*  29: 53 */     return this == other;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String toSignature()
/*  33:    */   {
/*  34: 57 */     return "Ljava/lang/Object;";
/*  35:    */   }
/*  36:    */   
/*  37:    */   public org.apache.bcel.generic.Type toJCType()
/*  38:    */   {
/*  39: 61 */     return org.apache.bcel.generic.Type.OBJECT;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  43:    */   {
/*  44: 73 */     if (type == Type.String)
/*  45:    */     {
/*  46: 74 */       translateTo(classGen, methodGen, (StringType)type);
/*  47:    */     }
/*  48: 76 */     else if (type == Type.Real)
/*  49:    */     {
/*  50: 77 */       translateTo(classGen, methodGen, (RealType)type);
/*  51:    */     }
/*  52: 79 */     else if (type == Type.Boolean)
/*  53:    */     {
/*  54: 80 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  55:    */     }
/*  56: 82 */     else if (type == Type.NodeSet)
/*  57:    */     {
/*  58: 83 */       translateTo(classGen, methodGen, (NodeSetType)type);
/*  59:    */     }
/*  60: 85 */     else if (type == Type.Node)
/*  61:    */     {
/*  62: 86 */       translateTo(classGen, methodGen, (NodeType)type);
/*  63:    */     }
/*  64: 88 */     else if (type == Type.ResultTree)
/*  65:    */     {
/*  66: 89 */       translateTo(classGen, methodGen, (ResultTreeType)type);
/*  67:    */     }
/*  68: 91 */     else if (type == Type.Object)
/*  69:    */     {
/*  70: 92 */       translateTo(classGen, methodGen, (ObjectType)type);
/*  71:    */     }
/*  72: 94 */     else if (type != Type.Reference)
/*  73:    */     {
/*  74: 97 */       ErrorMsg err = new ErrorMsg("INTERNAL_ERR", type.toString());
/*  75: 98 */       classGen.getParser().reportError(2, err);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/*  80:    */   {
/*  81:109 */     int current = methodGen.getLocalIndex("current");
/*  82:110 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  83:111 */     InstructionList il = methodGen.getInstructionList();
/*  84:114 */     if (current < 0) {
/*  85:115 */       il.append(new PUSH(cpg, 0));
/*  86:    */     } else {
/*  87:118 */       il.append(new ILOAD(current));
/*  88:    */     }
/*  89:120 */     il.append(methodGen.loadDOM());
/*  90:121 */     int stringF = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "stringF", "(Ljava/lang/Object;ILorg/apache/xalan/xsltc/DOM;)Ljava/lang/String;");
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:128 */     il.append(new INVOKESTATIC(stringF));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/* 101:    */   {
/* 102:138 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 103:139 */     InstructionList il = methodGen.getInstructionList();
/* 104:    */     
/* 105:141 */     il.append(methodGen.loadDOM());
/* 106:142 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "numberF", "(Ljava/lang/Object;Lorg/apache/xalan/xsltc/DOM;)D");
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:147 */     il.append(new INVOKESTATIC(index));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 115:    */   {
/* 116:157 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 117:158 */     InstructionList il = methodGen.getInstructionList();
/* 118:    */     
/* 119:160 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "booleanF", "(Ljava/lang/Object;)Z");
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:164 */     il.append(new INVOKESTATIC(index));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, NodeSetType type)
/* 127:    */   {
/* 128:174 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 129:175 */     InstructionList il = methodGen.getInstructionList();
/* 130:176 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToNodeSet", "(Ljava/lang/Object;)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 131:    */     
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:181 */     il.append(new INVOKESTATIC(index));
/* 136:    */     
/* 137:    */ 
/* 138:184 */     index = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "reset", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 139:185 */     il.append(new INVOKEINTERFACE(index, 1));
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, NodeType type)
/* 143:    */   {
/* 144:195 */     translateTo(classGen, methodGen, Type.NodeSet);
/* 145:196 */     Type.NodeSet.translateTo(classGen, methodGen, type);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ResultTreeType type)
/* 149:    */   {
/* 150:206 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 151:207 */     InstructionList il = methodGen.getInstructionList();
/* 152:208 */     int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToResultTree", "(Ljava/lang/Object;)Lorg/apache/xalan/xsltc/DOM;");
/* 153:    */     
/* 154:210 */     il.append(new INVOKESTATIC(index));
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ObjectType type)
/* 158:    */   {
/* 159:220 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 163:    */   {
/* 164:228 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 165:229 */     InstructionList il = methodGen.getInstructionList();
/* 166:    */     
/* 167:231 */     int referenceToLong = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToLong", "(Ljava/lang/Object;)J");
/* 168:    */     
/* 169:    */ 
/* 170:234 */     int referenceToDouble = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToDouble", "(Ljava/lang/Object;)D");
/* 171:    */     
/* 172:    */ 
/* 173:237 */     int referenceToBoolean = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToBoolean", "(Ljava/lang/Object;)Z");
/* 174:241 */     if (clazz.getName().equals("java.lang.Object"))
/* 175:    */     {
/* 176:242 */       il.append(InstructionConstants.NOP);
/* 177:    */     }
/* 178:244 */     else if (clazz == Double.TYPE)
/* 179:    */     {
/* 180:245 */       il.append(new INVOKESTATIC(referenceToDouble));
/* 181:    */     }
/* 182:247 */     else if (clazz.getName().equals("java.lang.Double"))
/* 183:    */     {
/* 184:248 */       il.append(new INVOKESTATIC(referenceToDouble));
/* 185:249 */       Type.Real.translateTo(classGen, methodGen, Type.Reference);
/* 186:    */     }
/* 187:251 */     else if (clazz == Float.TYPE)
/* 188:    */     {
/* 189:252 */       il.append(new INVOKESTATIC(referenceToDouble));
/* 190:253 */       il.append(InstructionConstants.D2F);
/* 191:    */     }
/* 192:255 */     else if (clazz.getName().equals("java.lang.String"))
/* 193:    */     {
/* 194:256 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToString", "(Ljava/lang/Object;Lorg/apache/xalan/xsltc/DOM;)Ljava/lang/String;");
/* 195:    */       
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:262 */       il.append(methodGen.loadDOM());
/* 201:263 */       il.append(new INVOKESTATIC(index));
/* 202:    */     }
/* 203:265 */     else if (clazz.getName().equals("org.w3c.dom.Node"))
/* 204:    */     {
/* 205:266 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToNode", "(Ljava/lang/Object;Lorg/apache/xalan/xsltc/DOM;)Lorg/w3c/dom/Node;");
/* 206:    */       
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:272 */       il.append(methodGen.loadDOM());
/* 212:273 */       il.append(new INVOKESTATIC(index));
/* 213:    */     }
/* 214:275 */     else if (clazz.getName().equals("org.w3c.dom.NodeList"))
/* 215:    */     {
/* 216:276 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToNodeList", "(Ljava/lang/Object;Lorg/apache/xalan/xsltc/DOM;)Lorg/w3c/dom/NodeList;");
/* 217:    */       
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:282 */       il.append(methodGen.loadDOM());
/* 223:283 */       il.append(new INVOKESTATIC(index));
/* 224:    */     }
/* 225:285 */     else if (clazz.getName().equals("org.apache.xalan.xsltc.DOM"))
/* 226:    */     {
/* 227:286 */       translateTo(classGen, methodGen, Type.ResultTree);
/* 228:    */     }
/* 229:288 */     else if (clazz == Long.TYPE)
/* 230:    */     {
/* 231:289 */       il.append(new INVOKESTATIC(referenceToLong));
/* 232:    */     }
/* 233:291 */     else if (clazz == Integer.TYPE)
/* 234:    */     {
/* 235:292 */       il.append(new INVOKESTATIC(referenceToLong));
/* 236:293 */       il.append(InstructionConstants.L2I);
/* 237:    */     }
/* 238:295 */     else if (clazz == Short.TYPE)
/* 239:    */     {
/* 240:296 */       il.append(new INVOKESTATIC(referenceToLong));
/* 241:297 */       il.append(InstructionConstants.L2I);
/* 242:298 */       il.append(InstructionConstants.I2S);
/* 243:    */     }
/* 244:300 */     else if (clazz == Byte.TYPE)
/* 245:    */     {
/* 246:301 */       il.append(new INVOKESTATIC(referenceToLong));
/* 247:302 */       il.append(InstructionConstants.L2I);
/* 248:303 */       il.append(InstructionConstants.I2B);
/* 249:    */     }
/* 250:305 */     else if (clazz == Character.TYPE)
/* 251:    */     {
/* 252:306 */       il.append(new INVOKESTATIC(referenceToLong));
/* 253:307 */       il.append(InstructionConstants.L2I);
/* 254:308 */       il.append(InstructionConstants.I2C);
/* 255:    */     }
/* 256:310 */     else if (clazz == Boolean.TYPE)
/* 257:    */     {
/* 258:311 */       il.append(new INVOKESTATIC(referenceToBoolean));
/* 259:    */     }
/* 260:313 */     else if (clazz.getName().equals("java.lang.Boolean"))
/* 261:    */     {
/* 262:314 */       il.append(new INVOKESTATIC(referenceToBoolean));
/* 263:315 */       Type.Boolean.translateTo(classGen, methodGen, Type.Reference);
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:318 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 268:    */       
/* 269:320 */       classGen.getParser().reportError(2, err);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 274:    */   {
/* 275:330 */     if (clazz.getName().equals("java.lang.Object"))
/* 276:    */     {
/* 277:331 */       methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 278:    */     }
/* 279:    */     else
/* 280:    */     {
/* 281:334 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 282:    */       
/* 283:336 */       classGen.getParser().reportError(2, err);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 288:    */   {
/* 289:350 */     InstructionList il = methodGen.getInstructionList();
/* 290:351 */     translateTo(classGen, methodGen, type);
/* 291:352 */     return new FlowList(il.append(new IFEQ(null)));
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 295:    */   
/* 296:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 297:    */   
/* 298:    */   public Instruction LOAD(int slot)
/* 299:    */   {
/* 300:371 */     return new ALOAD(slot);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public Instruction STORE(int slot)
/* 304:    */   {
/* 305:375 */     return new ASTORE(slot);
/* 306:    */   }
/* 307:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.ReferenceType
 * JD-Core Version:    0.7.0.1
 */