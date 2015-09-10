/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.CHECKCAST;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GETFIELD;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  11:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.Instruction;
/*  14:    */ import org.apache.bcel.generic.InstructionConstants;
/*  15:    */ import org.apache.bcel.generic.InstructionList;
/*  16:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  17:    */ import org.apache.bcel.generic.MethodGen;
/*  18:    */ import org.apache.bcel.generic.NEW;
/*  19:    */ import org.apache.bcel.generic.PUSH;
/*  20:    */ import org.apache.xalan.xsltc.compiler.Constants;
/*  21:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  22:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  23:    */ 
/*  24:    */ public final class ResultTreeType
/*  25:    */   extends Type
/*  26:    */ {
/*  27:    */   private final String _methodName;
/*  28:    */   
/*  29:    */   protected ResultTreeType()
/*  30:    */   {
/*  31: 50 */     this._methodName = null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ResultTreeType(String methodName)
/*  35:    */   {
/*  36: 54 */     this._methodName = methodName;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String toString()
/*  40:    */   {
/*  41: 58 */     return "result-tree";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean identicalTo(Type other)
/*  45:    */   {
/*  46: 62 */     return other instanceof ResultTreeType;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toSignature()
/*  50:    */   {
/*  51: 66 */     return "Lorg/apache/xalan/xsltc/DOM;";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public org.apache.bcel.generic.Type toJCType()
/*  55:    */   {
/*  56: 70 */     return Util.getJCRefType(toSignature());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getMethodName()
/*  60:    */   {
/*  61: 74 */     return this._methodName;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean implementedAsMethod()
/*  65:    */   {
/*  66: 78 */     return this._methodName != null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  70:    */   {
/*  71: 93 */     if (type == Type.String)
/*  72:    */     {
/*  73: 94 */       translateTo(classGen, methodGen, (StringType)type);
/*  74:    */     }
/*  75: 96 */     else if (type == Type.Boolean)
/*  76:    */     {
/*  77: 97 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  78:    */     }
/*  79: 99 */     else if (type == Type.Real)
/*  80:    */     {
/*  81:100 */       translateTo(classGen, methodGen, (RealType)type);
/*  82:    */     }
/*  83:102 */     else if (type == Type.NodeSet)
/*  84:    */     {
/*  85:103 */       translateTo(classGen, methodGen, (NodeSetType)type);
/*  86:    */     }
/*  87:105 */     else if (type == Type.Reference)
/*  88:    */     {
/*  89:106 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  90:    */     }
/*  91:108 */     else if (type == Type.Object)
/*  92:    */     {
/*  93:109 */       translateTo(classGen, methodGen, (ObjectType)type);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:112 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  98:    */       
/*  99:114 */       classGen.getParser().reportError(2, err);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 104:    */   {
/* 105:131 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 106:132 */     InstructionList il = methodGen.getInstructionList();
/* 107:133 */     il.append(InstructionConstants.POP);
/* 108:134 */     il.append(InstructionConstants.ICONST_1);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/* 112:    */   {
/* 113:147 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 114:148 */     InstructionList il = methodGen.getInstructionList();
/* 115:150 */     if (this._methodName == null)
/* 116:    */     {
/* 117:151 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getStringValue", "()Ljava/lang/String;");
/* 118:    */       
/* 119:    */ 
/* 120:154 */       il.append(new INVOKEINTERFACE(index, 1));
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:157 */       String className = classGen.getClassName();
/* 125:158 */       int current = methodGen.getLocalIndex("current");
/* 126:    */       
/* 127:    */ 
/* 128:161 */       il.append(classGen.loadTranslet());
/* 129:162 */       if (classGen.isExternal()) {
/* 130:163 */         il.append(new CHECKCAST(cpg.addClass(className)));
/* 131:    */       }
/* 132:165 */       il.append(InstructionConstants.DUP);
/* 133:166 */       il.append(new GETFIELD(cpg.addFieldref(className, "_dom", "Lorg/apache/xalan/xsltc/DOM;")));
/* 134:    */       
/* 135:    */ 
/* 136:    */ 
/* 137:170 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.StringValueHandler", "<init>", "()V");
/* 138:171 */       il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.runtime.StringValueHandler")));
/* 139:172 */       il.append(InstructionConstants.DUP);
/* 140:173 */       il.append(InstructionConstants.DUP);
/* 141:174 */       il.append(new INVOKESPECIAL(index));
/* 142:    */       
/* 143:    */ 
/* 144:177 */       LocalVariableGen handler = methodGen.addLocalVariable("rt_to_string_handler", Util.getJCRefType("Lorg/apache/xalan/xsltc/runtime/StringValueHandler;"), null, null);
/* 145:    */       
/* 146:    */ 
/* 147:    */ 
/* 148:181 */       handler.setStart(il.append(new ASTORE(handler.getIndex())));
/* 149:    */       
/* 150:    */ 
/* 151:184 */       index = cpg.addMethodref(className, this._methodName, "(Lorg/apache/xalan/xsltc/DOM;" + Constants.TRANSLET_OUTPUT_SIG + ")V");
/* 152:    */       
/* 153:186 */       il.append(new INVOKEVIRTUAL(index));
/* 154:    */       
/* 155:    */ 
/* 156:189 */       handler.setEnd(il.append(new ALOAD(handler.getIndex())));
/* 157:190 */       index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.StringValueHandler", "getValue", "()Ljava/lang/String;");
/* 158:    */       
/* 159:    */ 
/* 160:193 */       il.append(new INVOKEVIRTUAL(index));
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/* 165:    */   {
/* 166:208 */     translateTo(classGen, methodGen, Type.String);
/* 167:209 */     Type.String.translateTo(classGen, methodGen, Type.Real);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/* 171:    */   {
/* 172:223 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 173:224 */     InstructionList il = methodGen.getInstructionList();
/* 174:226 */     if (this._methodName == null)
/* 175:    */     {
/* 176:227 */       il.append(InstructionConstants.NOP);
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:231 */       String className = classGen.getClassName();
/* 181:232 */       int current = methodGen.getLocalIndex("current");
/* 182:    */       
/* 183:    */ 
/* 184:235 */       il.append(classGen.loadTranslet());
/* 185:236 */       if (classGen.isExternal()) {
/* 186:237 */         il.append(new CHECKCAST(cpg.addClass(className)));
/* 187:    */       }
/* 188:239 */       il.append(methodGen.loadDOM());
/* 189:    */       
/* 190:    */ 
/* 191:242 */       il.append(methodGen.loadDOM());
/* 192:243 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getResultTreeFrag", "(IZ)Lorg/apache/xalan/xsltc/DOM;");
/* 193:    */       
/* 194:    */ 
/* 195:246 */       il.append(new PUSH(cpg, 32));
/* 196:247 */       il.append(new PUSH(cpg, false));
/* 197:248 */       il.append(new INVOKEINTERFACE(index, 3));
/* 198:249 */       il.append(InstructionConstants.DUP);
/* 199:    */       
/* 200:    */ 
/* 201:252 */       LocalVariableGen newDom = methodGen.addLocalVariable("rt_to_reference_dom", Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), null, null);
/* 202:    */       
/* 203:    */ 
/* 204:255 */       il.append(new CHECKCAST(cpg.addClass("Lorg/apache/xalan/xsltc/DOM;")));
/* 205:256 */       newDom.setStart(il.append(new ASTORE(newDom.getIndex())));
/* 206:    */       
/* 207:    */ 
/* 208:259 */       index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getOutputDomBuilder", "()" + Constants.TRANSLET_OUTPUT_SIG);
/* 209:    */       
/* 210:    */ 
/* 211:    */ 
/* 212:263 */       il.append(new INVOKEINTERFACE(index, 1));
/* 213:    */       
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:268 */       il.append(InstructionConstants.DUP);
/* 218:269 */       il.append(InstructionConstants.DUP);
/* 219:    */       
/* 220:    */ 
/* 221:272 */       LocalVariableGen domBuilder = methodGen.addLocalVariable("rt_to_reference_handler", Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG), null, null);
/* 222:    */       
/* 223:    */ 
/* 224:    */ 
/* 225:276 */       domBuilder.setStart(il.append(new ASTORE(domBuilder.getIndex())));
/* 226:    */       
/* 227:    */ 
/* 228:279 */       index = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "startDocument", "()V");
/* 229:    */       
/* 230:281 */       il.append(new INVOKEINTERFACE(index, 1));
/* 231:    */       
/* 232:    */ 
/* 233:284 */       index = cpg.addMethodref(className, this._methodName, "(Lorg/apache/xalan/xsltc/DOM;" + Constants.TRANSLET_OUTPUT_SIG + ")V");
/* 234:    */       
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:290 */       il.append(new INVOKEVIRTUAL(index));
/* 240:    */       
/* 241:    */ 
/* 242:293 */       domBuilder.setEnd(il.append(new ALOAD(domBuilder.getIndex())));
/* 243:294 */       index = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "endDocument", "()V");
/* 244:    */       
/* 245:296 */       il.append(new INVOKEINTERFACE(index, 1));
/* 246:    */       
/* 247:    */ 
/* 248:299 */       newDom.setEnd(il.append(new ALOAD(newDom.getIndex())));
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, NodeSetType type)
/* 253:    */   {
/* 254:318 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 255:319 */     InstructionList il = methodGen.getInstructionList();
/* 256:    */     
/* 257:    */ 
/* 258:322 */     il.append(InstructionConstants.DUP);
/* 259:    */     
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:327 */     il.append(classGen.loadTranslet());
/* 264:328 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namesArray", "[Ljava/lang/String;")));
/* 265:    */     
/* 266:    */ 
/* 267:331 */     il.append(classGen.loadTranslet());
/* 268:332 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "urisArray", "[Ljava/lang/String;")));
/* 269:    */     
/* 270:    */ 
/* 271:335 */     il.append(classGen.loadTranslet());
/* 272:336 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "typesArray", "[I")));
/* 273:    */     
/* 274:    */ 
/* 275:339 */     il.append(classGen.loadTranslet());
/* 276:340 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "namespaceArray", "[Ljava/lang/String;")));
/* 277:    */     
/* 278:    */ 
/* 279:    */ 
/* 280:344 */     int mapping = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "setupMapping", "([Ljava/lang/String;[Ljava/lang/String;[I[Ljava/lang/String;)V");
/* 281:    */     
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:350 */     il.append(new INVOKEINTERFACE(mapping, 5));
/* 287:351 */     il.append(InstructionConstants.DUP);
/* 288:    */     
/* 289:    */ 
/* 290:354 */     int iter = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 291:    */     
/* 292:    */ 
/* 293:357 */     il.append(new INVOKEINTERFACE(iter, 1));
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ObjectType type)
/* 297:    */   {
/* 298:367 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 302:    */   {
/* 303:383 */     InstructionList il = methodGen.getInstructionList();
/* 304:384 */     translateTo(classGen, methodGen, Type.Boolean);
/* 305:385 */     return new FlowList(il.append(new IFEQ(null)));
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 309:    */   {
/* 310:402 */     String className = clazz.getName();
/* 311:403 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 312:404 */     InstructionList il = methodGen.getInstructionList();
/* 313:406 */     if (className.equals("org.w3c.dom.Node"))
/* 314:    */     {
/* 315:407 */       translateTo(classGen, methodGen, Type.NodeSet);
/* 316:408 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNode", "(Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/w3c/dom/Node;");
/* 317:    */       
/* 318:    */ 
/* 319:411 */       il.append(new INVOKEINTERFACE(index, 2));
/* 320:    */     }
/* 321:413 */     else if (className.equals("org.w3c.dom.NodeList"))
/* 322:    */     {
/* 323:414 */       translateTo(classGen, methodGen, Type.NodeSet);
/* 324:415 */       int index = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "makeNodeList", "(Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/w3c/dom/NodeList;");
/* 325:    */       
/* 326:    */ 
/* 327:418 */       il.append(new INVOKEINTERFACE(index, 2));
/* 328:    */     }
/* 329:420 */     else if (className.equals("java.lang.Object"))
/* 330:    */     {
/* 331:421 */       il.append(InstructionConstants.NOP);
/* 332:    */     }
/* 333:423 */     else if (className.equals("java.lang.String"))
/* 334:    */     {
/* 335:424 */       translateTo(classGen, methodGen, Type.String);
/* 336:    */     }
/* 337:    */     else
/* 338:    */     {
/* 339:427 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), className);
/* 340:    */       
/* 341:429 */       classGen.getParser().reportError(2, err);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 346:    */   {
/* 347:438 */     translateTo(classGen, methodGen, Type.Reference);
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 351:    */   {
/* 352:446 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getClassName()
/* 356:    */   {
/* 357:453 */     return "org.apache.xalan.xsltc.DOM";
/* 358:    */   }
/* 359:    */   
/* 360:    */   public Instruction LOAD(int slot)
/* 361:    */   {
/* 362:457 */     return new ALOAD(slot);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public Instruction STORE(int slot)
/* 366:    */   {
/* 367:461 */     return new ASTORE(slot);
/* 368:    */   }
/* 369:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.ResultTreeType
 * JD-Core Version:    0.7.0.1
 */