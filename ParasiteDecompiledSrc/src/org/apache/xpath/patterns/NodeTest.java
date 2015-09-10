/*   1:    */ package org.apache.xpath.patterns;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xml.dtm.DTM;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.ExpressionOwner;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathVisitor;
/*  11:    */ import org.apache.xpath.objects.XNumber;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ 
/*  14:    */ public class NodeTest
/*  15:    */   extends Expression
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -5736721866747906182L;
/*  18:    */   public static final String WILD = "*";
/*  19:    */   public static final String SUPPORTS_PRE_STRIPPING = "http://xml.apache.org/xpath/features/whitespace-pre-stripping";
/*  20:    */   protected int m_whatToShow;
/*  21:    */   public static final int SHOW_BYFUNCTION = 65536;
/*  22:    */   String m_namespace;
/*  23:    */   protected String m_name;
/*  24:    */   XNumber m_score;
/*  25:    */   
/*  26:    */   public int getWhatToShow()
/*  27:    */   {
/*  28: 76 */     return this.m_whatToShow;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setWhatToShow(int what)
/*  32:    */   {
/*  33: 88 */     this.m_whatToShow = what;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getNamespace()
/*  37:    */   {
/*  38:104 */     return this.m_namespace;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setNamespace(String ns)
/*  42:    */   {
/*  43:114 */     this.m_namespace = ns;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getLocalName()
/*  47:    */   {
/*  48:130 */     return null == this.m_name ? "" : this.m_name;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setLocalName(String name)
/*  52:    */   {
/*  53:140 */     this.m_name = name;
/*  54:    */   }
/*  55:    */   
/*  56:158 */   public static final XNumber SCORE_NODETEST = new XNumber(-0.5D);
/*  57:165 */   public static final XNumber SCORE_NSWILD = new XNumber(-0.25D);
/*  58:173 */   public static final XNumber SCORE_QNAME = new XNumber(0.0D);
/*  59:181 */   public static final XNumber SCORE_OTHER = new XNumber(0.5D);
/*  60:188 */   public static final XNumber SCORE_NONE = new XNumber((-1.0D / 0.0D));
/*  61:    */   private boolean m_isTotallyWild;
/*  62:    */   
/*  63:    */   public NodeTest(int whatToShow, String namespace, String name)
/*  64:    */   {
/*  65:201 */     initNodeTest(whatToShow, namespace, name);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public NodeTest(int whatToShow)
/*  69:    */   {
/*  70:212 */     initNodeTest(whatToShow);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean deepEquals(Expression expr)
/*  74:    */   {
/*  75:220 */     if (!isSameClass(expr)) {
/*  76:221 */       return false;
/*  77:    */     }
/*  78:223 */     NodeTest nt = (NodeTest)expr;
/*  79:225 */     if (null != nt.m_name)
/*  80:    */     {
/*  81:227 */       if (null == this.m_name) {
/*  82:228 */         return false;
/*  83:    */       }
/*  84:229 */       if (!nt.m_name.equals(this.m_name)) {
/*  85:230 */         return false;
/*  86:    */       }
/*  87:    */     }
/*  88:232 */     else if (null != this.m_name)
/*  89:    */     {
/*  90:233 */       return false;
/*  91:    */     }
/*  92:235 */     if (null != nt.m_namespace)
/*  93:    */     {
/*  94:237 */       if (null == this.m_namespace) {
/*  95:238 */         return false;
/*  96:    */       }
/*  97:239 */       if (!nt.m_namespace.equals(this.m_namespace)) {
/*  98:240 */         return false;
/*  99:    */       }
/* 100:    */     }
/* 101:242 */     else if (null != this.m_namespace)
/* 102:    */     {
/* 103:243 */       return false;
/* 104:    */     }
/* 105:245 */     if (this.m_whatToShow != nt.m_whatToShow) {
/* 106:246 */       return false;
/* 107:    */     }
/* 108:248 */     if (this.m_isTotallyWild != nt.m_isTotallyWild) {
/* 109:249 */       return false;
/* 110:    */     }
/* 111:251 */     return true;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public NodeTest() {}
/* 115:    */   
/* 116:    */   public void initNodeTest(int whatToShow)
/* 117:    */   {
/* 118:269 */     this.m_whatToShow = whatToShow;
/* 119:    */     
/* 120:271 */     calcScore();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void initNodeTest(int whatToShow, String namespace, String name)
/* 124:    */   {
/* 125:287 */     this.m_whatToShow = whatToShow;
/* 126:288 */     this.m_namespace = namespace;
/* 127:289 */     this.m_name = name;
/* 128:    */     
/* 129:291 */     calcScore();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public XNumber getStaticScore()
/* 133:    */   {
/* 134:306 */     return this.m_score;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setStaticScore(XNumber score)
/* 138:    */   {
/* 139:315 */     this.m_score = score;
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void calcScore()
/* 143:    */   {
/* 144:324 */     if ((this.m_namespace == null) && (this.m_name == null)) {
/* 145:325 */       this.m_score = SCORE_NODETEST;
/* 146:326 */     } else if (((this.m_namespace == "*") || (this.m_namespace == null)) && (this.m_name == "*")) {
/* 147:328 */       this.m_score = SCORE_NODETEST;
/* 148:329 */     } else if ((this.m_namespace != "*") && (this.m_name == "*")) {
/* 149:330 */       this.m_score = SCORE_NSWILD;
/* 150:    */     } else {
/* 151:332 */       this.m_score = SCORE_QNAME;
/* 152:    */     }
/* 153:334 */     this.m_isTotallyWild = ((this.m_namespace == null) && (this.m_name == "*"));
/* 154:    */   }
/* 155:    */   
/* 156:    */   public double getDefaultScore()
/* 157:    */   {
/* 158:345 */     return this.m_score.num();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static int getNodeTypeTest(int whatToShow)
/* 162:    */   {
/* 163:362 */     if (0 != (whatToShow & 0x1)) {
/* 164:363 */       return 1;
/* 165:    */     }
/* 166:365 */     if (0 != (whatToShow & 0x2)) {
/* 167:366 */       return 2;
/* 168:    */     }
/* 169:368 */     if (0 != (whatToShow & 0x4)) {
/* 170:369 */       return 3;
/* 171:    */     }
/* 172:371 */     if (0 != (whatToShow & 0x100)) {
/* 173:372 */       return 9;
/* 174:    */     }
/* 175:374 */     if (0 != (whatToShow & 0x400)) {
/* 176:375 */       return 11;
/* 177:    */     }
/* 178:377 */     if (0 != (whatToShow & 0x1000)) {
/* 179:378 */       return 13;
/* 180:    */     }
/* 181:380 */     if (0 != (whatToShow & 0x80)) {
/* 182:381 */       return 8;
/* 183:    */     }
/* 184:383 */     if (0 != (whatToShow & 0x40)) {
/* 185:384 */       return 7;
/* 186:    */     }
/* 187:386 */     if (0 != (whatToShow & 0x200)) {
/* 188:387 */       return 10;
/* 189:    */     }
/* 190:389 */     if (0 != (whatToShow & 0x20)) {
/* 191:390 */       return 6;
/* 192:    */     }
/* 193:392 */     if (0 != (whatToShow & 0x10)) {
/* 194:393 */       return 5;
/* 195:    */     }
/* 196:395 */     if (0 != (whatToShow & 0x800)) {
/* 197:396 */       return 12;
/* 198:    */     }
/* 199:398 */     if (0 != (whatToShow & 0x8)) {
/* 200:399 */       return 4;
/* 201:    */     }
/* 202:402 */     return 0;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static void debugWhatToShow(int whatToShow)
/* 206:    */   {
/* 207:416 */     Vector v = new Vector();
/* 208:418 */     if (0 != (whatToShow & 0x2)) {
/* 209:419 */       v.addElement("SHOW_ATTRIBUTE");
/* 210:    */     }
/* 211:421 */     if (0 != (whatToShow & 0x1000)) {
/* 212:422 */       v.addElement("SHOW_NAMESPACE");
/* 213:    */     }
/* 214:424 */     if (0 != (whatToShow & 0x8)) {
/* 215:425 */       v.addElement("SHOW_CDATA_SECTION");
/* 216:    */     }
/* 217:427 */     if (0 != (whatToShow & 0x80)) {
/* 218:428 */       v.addElement("SHOW_COMMENT");
/* 219:    */     }
/* 220:430 */     if (0 != (whatToShow & 0x100)) {
/* 221:431 */       v.addElement("SHOW_DOCUMENT");
/* 222:    */     }
/* 223:433 */     if (0 != (whatToShow & 0x400)) {
/* 224:434 */       v.addElement("SHOW_DOCUMENT_FRAGMENT");
/* 225:    */     }
/* 226:436 */     if (0 != (whatToShow & 0x200)) {
/* 227:437 */       v.addElement("SHOW_DOCUMENT_TYPE");
/* 228:    */     }
/* 229:439 */     if (0 != (whatToShow & 0x1)) {
/* 230:440 */       v.addElement("SHOW_ELEMENT");
/* 231:    */     }
/* 232:442 */     if (0 != (whatToShow & 0x20)) {
/* 233:443 */       v.addElement("SHOW_ENTITY");
/* 234:    */     }
/* 235:445 */     if (0 != (whatToShow & 0x10)) {
/* 236:446 */       v.addElement("SHOW_ENTITY_REFERENCE");
/* 237:    */     }
/* 238:448 */     if (0 != (whatToShow & 0x800)) {
/* 239:449 */       v.addElement("SHOW_NOTATION");
/* 240:    */     }
/* 241:451 */     if (0 != (whatToShow & 0x40)) {
/* 242:452 */       v.addElement("SHOW_PROCESSING_INSTRUCTION");
/* 243:    */     }
/* 244:454 */     if (0 != (whatToShow & 0x4)) {
/* 245:455 */       v.addElement("SHOW_TEXT");
/* 246:    */     }
/* 247:457 */     int n = v.size();
/* 248:459 */     for (int i = 0; i < n; i++)
/* 249:    */     {
/* 250:461 */       if (i > 0) {
/* 251:462 */         System.out.print(" | ");
/* 252:    */       }
/* 253:464 */       System.out.print(v.elementAt(i));
/* 254:    */     }
/* 255:467 */     if (0 == n) {
/* 256:468 */       System.out.print("empty whatToShow: " + whatToShow);
/* 257:    */     }
/* 258:470 */     System.out.println();
/* 259:    */   }
/* 260:    */   
/* 261:    */   private static final boolean subPartMatch(String p, String t)
/* 262:    */   {
/* 263:488 */     return (p == t) || ((null != p) && ((t == "*") || (p.equals(t))));
/* 264:    */   }
/* 265:    */   
/* 266:    */   private static final boolean subPartMatchNS(String p, String t)
/* 267:    */   {
/* 268:504 */     if (p != t) {
/* 269:504 */       if (null == p) {
/* 270:    */         break label56;
/* 271:    */       }
/* 272:    */     }
/* 273:    */     label56:
/* 274:504 */     return (null == t ? 1 : p.length() > 0 ? 0 : (t == "*") || (p.equals(t)) ? 1 : 0) != 0;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public XObject execute(XPathContext xctxt, int context)
/* 278:    */     throws TransformerException
/* 279:    */   {
/* 280:529 */     DTM dtm = xctxt.getDTM(context);
/* 281:530 */     short nodeType = dtm.getNodeType(context);
/* 282:532 */     if (this.m_whatToShow == -1) {
/* 283:533 */       return this.m_score;
/* 284:    */     }
/* 285:535 */     int nodeBit = this.m_whatToShow & 1 << nodeType - 1;
/* 286:537 */     switch (nodeBit)
/* 287:    */     {
/* 288:    */     case 256: 
/* 289:    */     case 1024: 
/* 290:541 */       return SCORE_OTHER;
/* 291:    */     case 128: 
/* 292:543 */       return this.m_score;
/* 293:    */     case 4: 
/* 294:    */     case 8: 
/* 295:550 */       return this.m_score;
/* 296:    */     case 64: 
/* 297:552 */       return subPartMatch(dtm.getNodeName(context), this.m_name) ? this.m_score : SCORE_NONE;
/* 298:    */     case 4096: 
/* 299:569 */       String ns = dtm.getLocalName(context);
/* 300:    */       
/* 301:571 */       return subPartMatch(ns, this.m_name) ? this.m_score : SCORE_NONE;
/* 302:    */     case 1: 
/* 303:    */     case 2: 
/* 304:576 */       return (this.m_isTotallyWild) || ((subPartMatchNS(dtm.getNamespaceURI(context), this.m_namespace)) && (subPartMatch(dtm.getLocalName(context), this.m_name))) ? this.m_score : SCORE_NONE;
/* 305:    */     }
/* 306:580 */     return SCORE_NONE;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public XObject execute(XPathContext xctxt, int context, DTM dtm, int expType)
/* 310:    */     throws TransformerException
/* 311:    */   {
/* 312:604 */     if (this.m_whatToShow == -1) {
/* 313:605 */       return this.m_score;
/* 314:    */     }
/* 315:607 */     int nodeBit = this.m_whatToShow & 1 << dtm.getNodeType(context) - 1;
/* 316:610 */     switch (nodeBit)
/* 317:    */     {
/* 318:    */     case 256: 
/* 319:    */     case 1024: 
/* 320:614 */       return SCORE_OTHER;
/* 321:    */     case 128: 
/* 322:616 */       return this.m_score;
/* 323:    */     case 4: 
/* 324:    */     case 8: 
/* 325:623 */       return this.m_score;
/* 326:    */     case 64: 
/* 327:625 */       return subPartMatch(dtm.getNodeName(context), this.m_name) ? this.m_score : SCORE_NONE;
/* 328:    */     case 4096: 
/* 329:642 */       String ns = dtm.getLocalName(context);
/* 330:    */       
/* 331:644 */       return subPartMatch(ns, this.m_name) ? this.m_score : SCORE_NONE;
/* 332:    */     case 1: 
/* 333:    */     case 2: 
/* 334:649 */       return (this.m_isTotallyWild) || ((subPartMatchNS(dtm.getNamespaceURI(context), this.m_namespace)) && (subPartMatch(dtm.getLocalName(context), this.m_name))) ? this.m_score : SCORE_NONE;
/* 335:    */     }
/* 336:653 */     return SCORE_NONE;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public XObject execute(XPathContext xctxt)
/* 340:    */     throws TransformerException
/* 341:    */   {
/* 342:673 */     return execute(xctxt, xctxt.getCurrentNode());
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 346:    */   
/* 347:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 348:    */   {
/* 349:689 */     assertion(false, "callVisitors should not be called for this object!!!");
/* 350:    */   }
/* 351:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.patterns.NodeTest
 * JD-Core Version:    0.7.0.1
 */