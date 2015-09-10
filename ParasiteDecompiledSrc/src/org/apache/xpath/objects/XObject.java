/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xml.dtm.DTM;
/*   7:    */ import org.apache.xml.dtm.DTMIterator;
/*   8:    */ import org.apache.xml.utils.FastStringBuffer;
/*   9:    */ import org.apache.xml.utils.XMLString;
/*  10:    */ import org.apache.xml.utils.XMLStringFactory;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.ExpressionOwner;
/*  13:    */ import org.apache.xpath.NodeSetDTM;
/*  14:    */ import org.apache.xpath.XPathContext;
/*  15:    */ import org.apache.xpath.XPathException;
/*  16:    */ import org.apache.xpath.XPathVisitor;
/*  17:    */ import org.apache.xpath.res.XPATHMessages;
/*  18:    */ import org.w3c.dom.DocumentFragment;
/*  19:    */ import org.w3c.dom.NodeList;
/*  20:    */ import org.w3c.dom.traversal.NodeIterator;
/*  21:    */ import org.xml.sax.ContentHandler;
/*  22:    */ import org.xml.sax.SAXException;
/*  23:    */ 
/*  24:    */ public class XObject
/*  25:    */   extends Expression
/*  26:    */   implements Serializable, Cloneable
/*  27:    */ {
/*  28:    */   static final long serialVersionUID = -821887098985662951L;
/*  29:    */   protected Object m_obj;
/*  30:    */   public static final int CLASS_NULL = -1;
/*  31:    */   public static final int CLASS_UNKNOWN = 0;
/*  32:    */   public static final int CLASS_BOOLEAN = 1;
/*  33:    */   public static final int CLASS_NUMBER = 2;
/*  34:    */   public static final int CLASS_STRING = 3;
/*  35:    */   public static final int CLASS_NODESET = 4;
/*  36:    */   public static final int CLASS_RTREEFRAG = 5;
/*  37:    */   public static final int CLASS_UNRESOLVEDVARIABLE = 600;
/*  38:    */   
/*  39:    */   public XObject() {}
/*  40:    */   
/*  41:    */   public XObject(Object obj)
/*  42:    */   {
/*  43: 71 */     setObject(obj);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void setObject(Object obj)
/*  47:    */   {
/*  48: 75 */     this.m_obj = obj;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public XObject execute(XPathContext xctxt)
/*  52:    */     throws TransformerException
/*  53:    */   {
/*  54: 90 */     return this;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void allowDetachToRelease(boolean allowRelease) {}
/*  58:    */   
/*  59:    */   public void detach() {}
/*  60:    */   
/*  61:    */   public void destruct()
/*  62:    */   {
/*  63:121 */     if (null != this.m_obj)
/*  64:    */     {
/*  65:123 */       allowDetachToRelease(true);
/*  66:124 */       detach();
/*  67:    */       
/*  68:126 */       setObject(null);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void reset() {}
/*  73:    */   
/*  74:    */   public void dispatchCharactersEvents(ContentHandler ch)
/*  75:    */     throws SAXException
/*  76:    */   {
/*  77:151 */     xstr().dispatchCharactersEvents(ch);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static XObject create(Object val)
/*  81:    */   {
/*  82:165 */     return XObjectFactory.create(val);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static XObject create(Object val, XPathContext xctxt)
/*  86:    */   {
/*  87:180 */     return XObjectFactory.create(val, xctxt);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getType()
/*  91:    */   {
/*  92:214 */     return 0;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getTypeString()
/*  96:    */   {
/*  97:225 */     return "#UNKNOWN (" + object().getClass().getName() + ")";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public double num()
/* 101:    */     throws TransformerException
/* 102:    */   {
/* 103:238 */     error("ER_CANT_CONVERT_TO_NUMBER", new Object[] { getTypeString() });
/* 104:    */     
/* 105:    */ 
/* 106:241 */     return 0.0D;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public double numWithSideEffects()
/* 110:    */     throws TransformerException
/* 111:    */   {
/* 112:253 */     return num();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean bool()
/* 116:    */     throws TransformerException
/* 117:    */   {
/* 118:266 */     error("ER_CANT_CONVERT_TO_NUMBER", new Object[] { getTypeString() });
/* 119:    */     
/* 120:    */ 
/* 121:269 */     return false;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean boolWithSideEffects()
/* 125:    */     throws TransformerException
/* 126:    */   {
/* 127:280 */     return bool();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public XMLString xstr()
/* 131:    */   {
/* 132:291 */     return XMLStringFactoryImpl.getFactory().newstr(str());
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String str()
/* 136:    */   {
/* 137:301 */     return this.m_obj != null ? this.m_obj.toString() : "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String toString()
/* 141:    */   {
/* 142:312 */     return str();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int rtf(XPathContext support)
/* 146:    */   {
/* 147:325 */     int result = rtf();
/* 148:327 */     if (-1 == result)
/* 149:    */     {
/* 150:329 */       DTM frag = support.createDocumentFragment();
/* 151:    */       
/* 152:    */ 
/* 153:332 */       frag.appendTextChild(str());
/* 154:    */       
/* 155:334 */       result = frag.getDocument();
/* 156:    */     }
/* 157:337 */     return result;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public DocumentFragment rtree(XPathContext support)
/* 161:    */   {
/* 162:349 */     DocumentFragment docFrag = null;
/* 163:350 */     int result = rtf();
/* 164:352 */     if (-1 == result)
/* 165:    */     {
/* 166:354 */       DTM frag = support.createDocumentFragment();
/* 167:    */       
/* 168:    */ 
/* 169:357 */       frag.appendTextChild(str());
/* 170:    */       
/* 171:359 */       docFrag = (DocumentFragment)frag.getNode(frag.getDocument());
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:363 */       DTM frag = support.getDTM(result);
/* 176:364 */       docFrag = (DocumentFragment)frag.getNode(frag.getDocument());
/* 177:    */     }
/* 178:367 */     return docFrag;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public DocumentFragment rtree()
/* 182:    */   {
/* 183:378 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int rtf()
/* 187:    */   {
/* 188:388 */     return -1;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Object object()
/* 192:    */   {
/* 193:399 */     return this.m_obj;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DTMIterator iter()
/* 197:    */     throws TransformerException
/* 198:    */   {
/* 199:412 */     error("ER_CANT_CONVERT_TO_NODELIST", new Object[] { getTypeString() });
/* 200:    */     
/* 201:    */ 
/* 202:415 */     return null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public XObject getFresh()
/* 206:    */   {
/* 207:425 */     return this;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public NodeIterator nodeset()
/* 211:    */     throws TransformerException
/* 212:    */   {
/* 213:439 */     error("ER_CANT_CONVERT_TO_NODELIST", new Object[] { getTypeString() });
/* 214:    */     
/* 215:    */ 
/* 216:442 */     return null;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public NodeList nodelist()
/* 220:    */     throws TransformerException
/* 221:    */   {
/* 222:455 */     error("ER_CANT_CONVERT_TO_NODELIST", new Object[] { getTypeString() });
/* 223:    */     
/* 224:    */ 
/* 225:458 */     return null;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public NodeSetDTM mutableNodeset()
/* 229:    */     throws TransformerException
/* 230:    */   {
/* 231:473 */     error("ER_CANT_CONVERT_TO_MUTABLENODELIST", new Object[] { getTypeString() });
/* 232:    */     
/* 233:    */ 
/* 234:476 */     return (NodeSetDTM)this.m_obj;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Object castToType(int t, XPathContext support)
/* 238:    */     throws TransformerException
/* 239:    */   {
/* 240:    */     Object result;
/* 241:495 */     switch (t)
/* 242:    */     {
/* 243:    */     case 3: 
/* 244:498 */       result = str();
/* 245:499 */       break;
/* 246:    */     case 2: 
/* 247:501 */       result = new Double(num());
/* 248:502 */       break;
/* 249:    */     case 4: 
/* 250:504 */       result = iter();
/* 251:505 */       break;
/* 252:    */     case 1: 
/* 253:507 */       result = new Boolean(bool());
/* 254:508 */       break;
/* 255:    */     case 0: 
/* 256:510 */       result = this.m_obj;
/* 257:511 */       break;
/* 258:    */     default: 
/* 259:518 */       error("ER_CANT_CONVERT_TO_TYPE", new Object[] { getTypeString(), Integer.toString(t) });
/* 260:    */       
/* 261:    */ 
/* 262:    */ 
/* 263:522 */       result = null;
/* 264:    */     }
/* 265:525 */     return result;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean lessThan(XObject obj2)
/* 269:    */     throws TransformerException
/* 270:    */   {
/* 271:546 */     if (obj2.getType() == 4) {
/* 272:547 */       return obj2.greaterThan(this);
/* 273:    */     }
/* 274:549 */     return num() < obj2.num();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean lessThanOrEqual(XObject obj2)
/* 278:    */     throws TransformerException
/* 279:    */   {
/* 280:570 */     if (obj2.getType() == 4) {
/* 281:571 */       return obj2.greaterThanOrEqual(this);
/* 282:    */     }
/* 283:573 */     return num() <= obj2.num();
/* 284:    */   }
/* 285:    */   
/* 286:    */   public boolean greaterThan(XObject obj2)
/* 287:    */     throws TransformerException
/* 288:    */   {
/* 289:594 */     if (obj2.getType() == 4) {
/* 290:595 */       return obj2.lessThan(this);
/* 291:    */     }
/* 292:597 */     return num() > obj2.num();
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean greaterThanOrEqual(XObject obj2)
/* 296:    */     throws TransformerException
/* 297:    */   {
/* 298:618 */     if (obj2.getType() == 4) {
/* 299:619 */       return obj2.lessThanOrEqual(this);
/* 300:    */     }
/* 301:621 */     return num() >= obj2.num();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public boolean equals(XObject obj2)
/* 305:    */   {
/* 306:639 */     if (obj2.getType() == 4) {
/* 307:640 */       return obj2.equals(this);
/* 308:    */     }
/* 309:642 */     if (null != this.m_obj) {
/* 310:644 */       return this.m_obj.equals(obj2.m_obj);
/* 311:    */     }
/* 312:648 */     return obj2.m_obj == null;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean notEquals(XObject obj2)
/* 316:    */     throws TransformerException
/* 317:    */   {
/* 318:668 */     if (obj2.getType() == 4) {
/* 319:669 */       return obj2.notEquals(this);
/* 320:    */     }
/* 321:671 */     return !equals(obj2);
/* 322:    */   }
/* 323:    */   
/* 324:    */   protected void error(String msg)
/* 325:    */     throws TransformerException
/* 326:    */   {
/* 327:685 */     error(msg, null);
/* 328:    */   }
/* 329:    */   
/* 330:    */   protected void error(String msg, Object[] args)
/* 331:    */     throws TransformerException
/* 332:    */   {
/* 333:701 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/* 334:    */     
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:709 */     throw new XPathException(fmsg, this);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 345:    */   
/* 346:    */   public void appendToFsb(FastStringBuffer fsb)
/* 347:    */   {
/* 348:732 */     fsb.append(str());
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 352:    */   {
/* 353:740 */     assertion(false, "callVisitors should not be called for this object!!!");
/* 354:    */   }
/* 355:    */   
/* 356:    */   public boolean deepEquals(Expression expr)
/* 357:    */   {
/* 358:747 */     if (!isSameClass(expr)) {
/* 359:748 */       return false;
/* 360:    */     }
/* 361:753 */     if (!equals((XObject)expr)) {
/* 362:754 */       return false;
/* 363:    */     }
/* 364:756 */     return true;
/* 365:    */   }
/* 366:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XObject
 * JD-Core Version:    0.7.0.1
 */