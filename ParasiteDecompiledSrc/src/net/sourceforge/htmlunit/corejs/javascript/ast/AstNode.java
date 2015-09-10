/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Token;
/*  11:    */ 
/*  12:    */ public abstract class AstNode
/*  13:    */   extends Node
/*  14:    */   implements Comparable<AstNode>
/*  15:    */ {
/*  16: 98 */   protected int position = -1;
/*  17: 99 */   protected int length = 1;
/*  18:    */   protected AstNode parent;
/*  19:102 */   private static Map<Integer, String> operatorNames = new HashMap();
/*  20:    */   
/*  21:    */   static
/*  22:    */   {
/*  23:106 */     operatorNames.put(Integer.valueOf(52), "in");
/*  24:107 */     operatorNames.put(Integer.valueOf(32), "typeof");
/*  25:108 */     operatorNames.put(Integer.valueOf(53), "instanceof");
/*  26:109 */     operatorNames.put(Integer.valueOf(31), "delete");
/*  27:110 */     operatorNames.put(Integer.valueOf(89), ",");
/*  28:111 */     operatorNames.put(Integer.valueOf(103), ":");
/*  29:112 */     operatorNames.put(Integer.valueOf(104), "||");
/*  30:113 */     operatorNames.put(Integer.valueOf(105), "&&");
/*  31:114 */     operatorNames.put(Integer.valueOf(106), "++");
/*  32:115 */     operatorNames.put(Integer.valueOf(107), "--");
/*  33:116 */     operatorNames.put(Integer.valueOf(9), "|");
/*  34:117 */     operatorNames.put(Integer.valueOf(10), "^");
/*  35:118 */     operatorNames.put(Integer.valueOf(11), "&");
/*  36:119 */     operatorNames.put(Integer.valueOf(12), "==");
/*  37:120 */     operatorNames.put(Integer.valueOf(13), "!=");
/*  38:121 */     operatorNames.put(Integer.valueOf(14), "<");
/*  39:122 */     operatorNames.put(Integer.valueOf(16), ">");
/*  40:123 */     operatorNames.put(Integer.valueOf(15), "<=");
/*  41:124 */     operatorNames.put(Integer.valueOf(17), ">=");
/*  42:125 */     operatorNames.put(Integer.valueOf(18), "<<");
/*  43:126 */     operatorNames.put(Integer.valueOf(19), ">>");
/*  44:127 */     operatorNames.put(Integer.valueOf(20), ">>>");
/*  45:128 */     operatorNames.put(Integer.valueOf(21), "+");
/*  46:129 */     operatorNames.put(Integer.valueOf(22), "-");
/*  47:130 */     operatorNames.put(Integer.valueOf(23), "*");
/*  48:131 */     operatorNames.put(Integer.valueOf(24), "/");
/*  49:132 */     operatorNames.put(Integer.valueOf(25), "%");
/*  50:133 */     operatorNames.put(Integer.valueOf(26), "!");
/*  51:134 */     operatorNames.put(Integer.valueOf(27), "~");
/*  52:135 */     operatorNames.put(Integer.valueOf(28), "+");
/*  53:136 */     operatorNames.put(Integer.valueOf(29), "-");
/*  54:137 */     operatorNames.put(Integer.valueOf(46), "===");
/*  55:138 */     operatorNames.put(Integer.valueOf(47), "!==");
/*  56:139 */     operatorNames.put(Integer.valueOf(90), "=");
/*  57:140 */     operatorNames.put(Integer.valueOf(91), "|=");
/*  58:141 */     operatorNames.put(Integer.valueOf(93), "&=");
/*  59:142 */     operatorNames.put(Integer.valueOf(94), "<<=");
/*  60:143 */     operatorNames.put(Integer.valueOf(95), ">>=");
/*  61:144 */     operatorNames.put(Integer.valueOf(96), ">>>=");
/*  62:145 */     operatorNames.put(Integer.valueOf(97), "+=");
/*  63:146 */     operatorNames.put(Integer.valueOf(98), "-=");
/*  64:147 */     operatorNames.put(Integer.valueOf(99), "*=");
/*  65:148 */     operatorNames.put(Integer.valueOf(100), "/=");
/*  66:149 */     operatorNames.put(Integer.valueOf(101), "%=");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static class PositionComparator
/*  70:    */     implements Comparator<AstNode>, Serializable
/*  71:    */   {
/*  72:    */     private static final long serialVersionUID = 1L;
/*  73:    */     
/*  74:    */     public int compare(AstNode n1, AstNode n2)
/*  75:    */     {
/*  76:161 */       return n1.position - n2.position;
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public AstNode()
/*  81:    */   {
/*  82:166 */     super(-1);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public AstNode(int pos)
/*  86:    */   {
/*  87:174 */     this();
/*  88:175 */     this.position = pos;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public AstNode(int pos, int len)
/*  92:    */   {
/*  93:185 */     this();
/*  94:186 */     this.position = pos;
/*  95:187 */     this.length = len;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getPosition()
/*  99:    */   {
/* 100:194 */     return this.position;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setPosition(int position)
/* 104:    */   {
/* 105:201 */     this.position = position;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getAbsolutePosition()
/* 109:    */   {
/* 110:210 */     int pos = this.position;
/* 111:211 */     AstNode parent = this.parent;
/* 112:212 */     while (parent != null)
/* 113:    */     {
/* 114:213 */       pos += parent.getPosition();
/* 115:214 */       parent = parent.getParent();
/* 116:    */     }
/* 117:216 */     return pos;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getLength()
/* 121:    */   {
/* 122:223 */     return this.length;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setLength(int length)
/* 126:    */   {
/* 127:230 */     this.length = length;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setBounds(int position, int end)
/* 131:    */   {
/* 132:238 */     setPosition(position);
/* 133:239 */     setLength(end - position);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setRelative(int parentPosition)
/* 137:    */   {
/* 138:250 */     this.position -= parentPosition;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public AstNode getParent()
/* 142:    */   {
/* 143:257 */     return this.parent;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setParent(AstNode parent)
/* 147:    */   {
/* 148:266 */     if (parent == this.parent) {
/* 149:267 */       return;
/* 150:    */     }
/* 151:271 */     if (this.parent != null) {
/* 152:272 */       setRelative(-this.parent.getPosition());
/* 153:    */     }
/* 154:275 */     this.parent = parent;
/* 155:276 */     if (parent != null) {
/* 156:277 */       setRelative(parent.getPosition());
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void addChild(AstNode kid)
/* 161:    */   {
/* 162:290 */     assertNotNull(kid);
/* 163:291 */     int end = kid.getPosition() + kid.getLength();
/* 164:292 */     setLength(end - getPosition());
/* 165:293 */     addChildToBack(kid);
/* 166:294 */     kid.setParent(this);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public AstRoot getAstRoot()
/* 170:    */   {
/* 171:303 */     AstNode parent = this;
/* 172:304 */     while ((parent != null) && (!(parent instanceof AstRoot))) {
/* 173:305 */       parent = parent.getParent();
/* 174:    */     }
/* 175:307 */     return (AstRoot)parent;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String toSource()
/* 179:    */   {
/* 180:330 */     return toSource(0);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String makeIndent(int indent)
/* 184:    */   {
/* 185:338 */     StringBuilder sb = new StringBuilder();
/* 186:339 */     for (int i = 0; i < indent; i++) {
/* 187:340 */       sb.append("  ");
/* 188:    */     }
/* 189:342 */     return sb.toString();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String shortName()
/* 193:    */   {
/* 194:350 */     String classname = getClass().getName();
/* 195:351 */     int last = classname.lastIndexOf(".");
/* 196:352 */     return classname.substring(last + 1);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static String operatorToString(int op)
/* 200:    */   {
/* 201:361 */     String result = (String)operatorNames.get(Integer.valueOf(op));
/* 202:362 */     if (result == null) {
/* 203:363 */       throw new IllegalArgumentException("Invalid operator: " + op);
/* 204:    */     }
/* 205:364 */     return result;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean hasSideEffects()
/* 209:    */   {
/* 210:389 */     switch (getType())
/* 211:    */     {
/* 212:    */     case -1: 
/* 213:    */     case 2: 
/* 214:    */     case 3: 
/* 215:    */     case 4: 
/* 216:    */     case 5: 
/* 217:    */     case 6: 
/* 218:    */     case 7: 
/* 219:    */     case 8: 
/* 220:    */     case 30: 
/* 221:    */     case 31: 
/* 222:    */     case 35: 
/* 223:    */     case 37: 
/* 224:    */     case 38: 
/* 225:    */     case 50: 
/* 226:    */     case 51: 
/* 227:    */     case 56: 
/* 228:    */     case 57: 
/* 229:    */     case 64: 
/* 230:    */     case 68: 
/* 231:    */     case 69: 
/* 232:    */     case 70: 
/* 233:    */     case 72: 
/* 234:    */     case 81: 
/* 235:    */     case 82: 
/* 236:    */     case 90: 
/* 237:    */     case 91: 
/* 238:    */     case 92: 
/* 239:    */     case 93: 
/* 240:    */     case 94: 
/* 241:    */     case 95: 
/* 242:    */     case 96: 
/* 243:    */     case 97: 
/* 244:    */     case 98: 
/* 245:    */     case 99: 
/* 246:    */     case 100: 
/* 247:    */     case 101: 
/* 248:    */     case 106: 
/* 249:    */     case 107: 
/* 250:    */     case 109: 
/* 251:    */     case 110: 
/* 252:    */     case 111: 
/* 253:    */     case 112: 
/* 254:    */     case 113: 
/* 255:    */     case 114: 
/* 256:    */     case 117: 
/* 257:    */     case 118: 
/* 258:    */     case 119: 
/* 259:    */     case 120: 
/* 260:    */     case 121: 
/* 261:    */     case 122: 
/* 262:    */     case 123: 
/* 263:    */     case 124: 
/* 264:    */     case 125: 
/* 265:    */     case 129: 
/* 266:    */     case 130: 
/* 267:    */     case 131: 
/* 268:    */     case 132: 
/* 269:    */     case 134: 
/* 270:    */     case 135: 
/* 271:    */     case 139: 
/* 272:    */     case 140: 
/* 273:    */     case 141: 
/* 274:    */     case 142: 
/* 275:    */     case 153: 
/* 276:    */     case 154: 
/* 277:    */     case 158: 
/* 278:    */     case 159: 
/* 279:457 */       return true;
/* 280:    */     }
/* 281:460 */     return false;
/* 282:    */   }
/* 283:    */   
/* 284:    */   protected void assertNotNull(Object arg)
/* 285:    */   {
/* 286:470 */     if (arg == null) {
/* 287:471 */       throw new IllegalArgumentException("arg cannot be null");
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected <T extends AstNode> void printList(List<T> items, StringBuilder sb)
/* 292:    */   {
/* 293:481 */     int max = items.size();
/* 294:482 */     int count = 0;
/* 295:483 */     for (AstNode item : items)
/* 296:    */     {
/* 297:484 */       sb.append(item.toSource(0));
/* 298:485 */       if (count++ < max - 1) {
/* 299:486 */         sb.append(", ");
/* 300:    */       }
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public static RuntimeException codeBug()
/* 305:    */     throws RuntimeException
/* 306:    */   {
/* 307:497 */     throw Kit.codeBug();
/* 308:    */   }
/* 309:    */   
/* 310:    */   public FunctionNode getEnclosingFunction()
/* 311:    */   {
/* 312:518 */     AstNode parent = getParent();
/* 313:519 */     while ((parent != null) && (!(parent instanceof FunctionNode))) {
/* 314:520 */       parent = parent.getParent();
/* 315:    */     }
/* 316:522 */     return (FunctionNode)parent;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Scope getEnclosingScope()
/* 320:    */   {
/* 321:533 */     AstNode parent = getParent();
/* 322:534 */     while ((parent != null) && (!(parent instanceof Scope))) {
/* 323:535 */       parent = parent.getParent();
/* 324:    */     }
/* 325:537 */     return (Scope)parent;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public int compareTo(AstNode other)
/* 329:    */   {
/* 330:552 */     if (equals(other)) {
/* 331:552 */       return 0;
/* 332:    */     }
/* 333:553 */     int abs1 = getAbsolutePosition();
/* 334:554 */     int abs2 = other.getAbsolutePosition();
/* 335:555 */     if (abs1 < abs2) {
/* 336:555 */       return -1;
/* 337:    */     }
/* 338:556 */     if (abs2 < abs1) {
/* 339:556 */       return 1;
/* 340:    */     }
/* 341:557 */     int len1 = getLength();
/* 342:558 */     int len2 = other.getLength();
/* 343:559 */     if (len1 < len2) {
/* 344:559 */       return -1;
/* 345:    */     }
/* 346:560 */     if (len2 < len1) {
/* 347:560 */       return 1;
/* 348:    */     }
/* 349:561 */     return hashCode() - other.hashCode();
/* 350:    */   }
/* 351:    */   
/* 352:    */   public int depth()
/* 353:    */   {
/* 354:570 */     return this.parent == null ? 0 : 1 + this.parent.depth();
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected static class DebugPrintVisitor
/* 358:    */     implements NodeVisitor
/* 359:    */   {
/* 360:    */     private StringBuilder buffer;
/* 361:    */     private static final int DEBUG_INDENT = 2;
/* 362:    */     
/* 363:    */     public DebugPrintVisitor(StringBuilder buf)
/* 364:    */     {
/* 365:577 */       this.buffer = buf;
/* 366:    */     }
/* 367:    */     
/* 368:    */     public String toString()
/* 369:    */     {
/* 370:580 */       return this.buffer.toString();
/* 371:    */     }
/* 372:    */     
/* 373:    */     private String makeIndent(int depth)
/* 374:    */     {
/* 375:583 */       StringBuilder sb = new StringBuilder(2 * depth);
/* 376:584 */       for (int i = 0; i < 2 * depth; i++) {
/* 377:585 */         sb.append(" ");
/* 378:    */       }
/* 379:587 */       return sb.toString();
/* 380:    */     }
/* 381:    */     
/* 382:    */     public boolean visit(AstNode node)
/* 383:    */     {
/* 384:590 */       int tt = node.getType();
/* 385:591 */       String name = Token.typeToName(tt);
/* 386:592 */       this.buffer.append(node.getAbsolutePosition()).append("\t");
/* 387:593 */       this.buffer.append(makeIndent(node.depth()));
/* 388:594 */       this.buffer.append(name).append(" ");
/* 389:595 */       this.buffer.append(node.getPosition()).append(" ");
/* 390:596 */       this.buffer.append(node.getLength());
/* 391:597 */       if (tt == 39) {
/* 392:598 */         this.buffer.append(" ").append(((Name)node).getIdentifier());
/* 393:    */       }
/* 394:600 */       this.buffer.append("\n");
/* 395:601 */       return true;
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   public int getLineno()
/* 400:    */   {
/* 401:612 */     if (this.lineno != -1) {
/* 402:613 */       return this.lineno;
/* 403:    */     }
/* 404:614 */     if (this.parent != null) {
/* 405:615 */       return this.parent.getLineno();
/* 406:    */     }
/* 407:616 */     return -1;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public String debugPrint()
/* 411:    */   {
/* 412:626 */     DebugPrintVisitor dpv = new DebugPrintVisitor(new StringBuilder(1000));
/* 413:627 */     visit(dpv);
/* 414:628 */     return dpv.toString();
/* 415:    */   }
/* 416:    */   
/* 417:    */   public abstract String toSource(int paramInt);
/* 418:    */   
/* 419:    */   public abstract void visit(NodeVisitor paramNodeVisitor);
/* 420:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.AstNode
 * JD-Core Version:    0.7.0.1
 */