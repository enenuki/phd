/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   9:    */ 
/*  10:    */ public class FunctionNode
/*  11:    */   extends ScriptNode
/*  12:    */ {
/*  13:    */   public static final int FUNCTION_STATEMENT = 1;
/*  14:    */   public static final int FUNCTION_EXPRESSION = 2;
/*  15:    */   public static final int FUNCTION_EXPRESSION_STATEMENT = 3;
/*  16:    */   
/*  17:    */   public static enum Form
/*  18:    */   {
/*  19:102 */     FUNCTION,  GETTER,  SETTER;
/*  20:    */     
/*  21:    */     private Form() {}
/*  22:    */   }
/*  23:    */   
/*  24:104 */   private static final List<AstNode> NO_PARAMS = Collections.unmodifiableList(new ArrayList());
/*  25:    */   private Name functionName;
/*  26:    */   private List<AstNode> params;
/*  27:    */   private AstNode body;
/*  28:    */   private boolean isExpressionClosure;
/*  29:111 */   private Form functionForm = Form.FUNCTION;
/*  30:112 */   private int lp = -1;
/*  31:113 */   private int rp = -1;
/*  32:    */   private int functionType;
/*  33:    */   private boolean needsActivation;
/*  34:    */   private boolean ignoreDynamicScope;
/*  35:    */   private boolean isGenerator;
/*  36:    */   private List<Node> generatorResumePoints;
/*  37:    */   private Map<Node, int[]> liveLocals;
/*  38:    */   private AstNode memberExprNode;
/*  39:    */   
/*  40:    */   public FunctionNode()
/*  41:    */   {
/*  42:125 */     this.type = 109;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public FunctionNode(int pos)
/*  46:    */   {
/*  47:132 */     super(pos);this.type = 109;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public FunctionNode(int pos, Name name)
/*  51:    */   {
/*  52:136 */     super(pos);this.type = 109;
/*  53:137 */     setFunctionName(name);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Name getFunctionName()
/*  57:    */   {
/*  58:145 */     return this.functionName;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setFunctionName(Name name)
/*  62:    */   {
/*  63:153 */     this.functionName = name;
/*  64:154 */     if (name != null) {
/*  65:155 */       name.setParent(this);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getName()
/*  70:    */   {
/*  71:163 */     return this.functionName != null ? this.functionName.getIdentifier() : "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<AstNode> getParams()
/*  75:    */   {
/*  76:172 */     return this.params != null ? this.params : NO_PARAMS;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setParams(List<AstNode> params)
/*  80:    */   {
/*  81:181 */     if (params == null)
/*  82:    */     {
/*  83:182 */       this.params = null;
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87:184 */       if (this.params != null) {
/*  88:185 */         this.params.clear();
/*  89:    */       }
/*  90:186 */       for (AstNode param : params) {
/*  91:187 */         addParam(param);
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void addParam(AstNode param)
/*  97:    */   {
/*  98:198 */     assertNotNull(param);
/*  99:199 */     if (this.params == null) {
/* 100:200 */       this.params = new ArrayList();
/* 101:    */     }
/* 102:202 */     this.params.add(param);
/* 103:203 */     param.setParent(this);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isParam(AstNode node)
/* 107:    */   {
/* 108:212 */     return this.params == null ? false : this.params.contains(node);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public AstNode getBody()
/* 112:    */   {
/* 113:222 */     return this.body;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setBody(AstNode body)
/* 117:    */   {
/* 118:237 */     assertNotNull(body);
/* 119:238 */     this.body = body;
/* 120:239 */     if (Boolean.TRUE.equals(body.getProp(25))) {
/* 121:240 */       setIsExpressionClosure(true);
/* 122:    */     }
/* 123:242 */     int absEnd = body.getPosition() + body.getLength();
/* 124:243 */     body.setParent(this);
/* 125:244 */     setLength(absEnd - this.position);
/* 126:245 */     setEncodedSourceBounds(this.position, absEnd);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getLp()
/* 130:    */   {
/* 131:252 */     return this.lp;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setLp(int lp)
/* 135:    */   {
/* 136:259 */     this.lp = lp;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getRp()
/* 140:    */   {
/* 141:266 */     return this.rp;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setRp(int rp)
/* 145:    */   {
/* 146:273 */     this.rp = rp;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setParens(int lp, int rp)
/* 150:    */   {
/* 151:280 */     this.lp = lp;
/* 152:281 */     this.rp = rp;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isExpressionClosure()
/* 156:    */   {
/* 157:288 */     return this.isExpressionClosure;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIsExpressionClosure(boolean isExpressionClosure)
/* 161:    */   {
/* 162:295 */     this.isExpressionClosure = isExpressionClosure;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean requiresActivation()
/* 166:    */   {
/* 167:310 */     return this.needsActivation;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setRequiresActivation()
/* 171:    */   {
/* 172:314 */     this.needsActivation = true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean getIgnoreDynamicScope()
/* 176:    */   {
/* 177:318 */     return this.ignoreDynamicScope;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIgnoreDynamicScope()
/* 181:    */   {
/* 182:322 */     this.ignoreDynamicScope = true;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isGenerator()
/* 186:    */   {
/* 187:326 */     return this.isGenerator;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIsGenerator()
/* 191:    */   {
/* 192:330 */     this.isGenerator = true;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void addResumptionPoint(Node target)
/* 196:    */   {
/* 197:334 */     if (this.generatorResumePoints == null) {
/* 198:335 */       this.generatorResumePoints = new ArrayList();
/* 199:    */     }
/* 200:336 */     this.generatorResumePoints.add(target);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<Node> getResumptionPoints()
/* 204:    */   {
/* 205:340 */     return this.generatorResumePoints;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Map<Node, int[]> getLiveLocals()
/* 209:    */   {
/* 210:344 */     return this.liveLocals;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void addLiveLocals(Node node, int[] locals)
/* 214:    */   {
/* 215:348 */     if (this.liveLocals == null) {
/* 216:349 */       this.liveLocals = new HashMap();
/* 217:    */     }
/* 218:350 */     this.liveLocals.put(node, locals);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public int addFunction(FunctionNode fnNode)
/* 222:    */   {
/* 223:355 */     int result = super.addFunction(fnNode);
/* 224:356 */     if (getFunctionCount() > 0) {
/* 225:357 */       this.needsActivation = true;
/* 226:    */     }
/* 227:359 */     return result;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public int getFunctionType()
/* 231:    */   {
/* 232:366 */     return this.functionType;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setFunctionType(int type)
/* 236:    */   {
/* 237:370 */     this.functionType = type;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isGetterOrSetter()
/* 241:    */   {
/* 242:374 */     return (this.functionForm == Form.GETTER) || (this.functionForm == Form.SETTER);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isGetter()
/* 246:    */   {
/* 247:378 */     return this.functionForm == Form.GETTER;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean isSetter()
/* 251:    */   {
/* 252:382 */     return this.functionForm == Form.SETTER;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setFunctionIsGetter()
/* 256:    */   {
/* 257:386 */     this.functionForm = Form.GETTER;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setFunctionIsSetter()
/* 261:    */   {
/* 262:390 */     this.functionForm = Form.SETTER;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setMemberExprNode(AstNode node)
/* 266:    */   {
/* 267:404 */     this.memberExprNode = node;
/* 268:405 */     if (node != null) {
/* 269:406 */       node.setParent(this);
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public AstNode getMemberExprNode()
/* 274:    */   {
/* 275:410 */     return this.memberExprNode;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String toSource(int depth)
/* 279:    */   {
/* 280:415 */     StringBuilder sb = new StringBuilder();
/* 281:416 */     sb.append(makeIndent(depth));
/* 282:417 */     sb.append("function");
/* 283:418 */     if (this.functionName != null)
/* 284:    */     {
/* 285:419 */       sb.append(" ");
/* 286:420 */       sb.append(this.functionName.toSource(0));
/* 287:    */     }
/* 288:422 */     if (this.params == null)
/* 289:    */     {
/* 290:423 */       sb.append("() ");
/* 291:    */     }
/* 292:    */     else
/* 293:    */     {
/* 294:425 */       sb.append("(");
/* 295:426 */       printList(this.params, sb);
/* 296:427 */       sb.append(") ");
/* 297:    */     }
/* 298:429 */     if (this.isExpressionClosure)
/* 299:    */     {
/* 300:430 */       sb.append(" ");
/* 301:431 */       sb.append(getBody().toSource(0));
/* 302:    */     }
/* 303:    */     else
/* 304:    */     {
/* 305:433 */       sb.append(getBody().toSource(depth).trim());
/* 306:    */     }
/* 307:435 */     if (this.functionType == 1) {
/* 308:436 */       sb.append("\n");
/* 309:    */     }
/* 310:438 */     return sb.toString();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void visit(NodeVisitor v)
/* 314:    */   {
/* 315:448 */     if (v.visit(this))
/* 316:    */     {
/* 317:449 */       if (this.functionName != null) {
/* 318:450 */         this.functionName.visit(v);
/* 319:    */       }
/* 320:452 */       for (AstNode param : getParams()) {
/* 321:453 */         param.visit(v);
/* 322:    */       }
/* 323:455 */       getBody().visit(v);
/* 324:456 */       if ((!this.isExpressionClosure) && 
/* 325:457 */         (this.memberExprNode != null)) {
/* 326:458 */         this.memberExprNode.visit(v);
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode
 * JD-Core Version:    0.7.0.1
 */