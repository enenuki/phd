/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import javax.xml.parsers.DocumentBuilder;
/*   4:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.extensions.ExpressionContext;
/*   7:    */ import org.apache.xalan.res.XSLMessages;
/*   8:    */ import org.apache.xpath.NodeSet;
/*   9:    */ import org.apache.xpath.NodeSetDTM;
/*  10:    */ import org.apache.xpath.XPath;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ import org.apache.xpath.XPathContext.XPathExpressionContext;
/*  13:    */ import org.apache.xpath.objects.XBoolean;
/*  14:    */ import org.apache.xpath.objects.XNodeSet;
/*  15:    */ import org.apache.xpath.objects.XNumber;
/*  16:    */ import org.apache.xpath.objects.XObject;
/*  17:    */ import org.w3c.dom.Document;
/*  18:    */ import org.w3c.dom.Element;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ import org.w3c.dom.NodeList;
/*  21:    */ import org.w3c.dom.Text;
/*  22:    */ import org.xml.sax.SAXNotSupportedException;
/*  23:    */ 
/*  24:    */ public class ExsltDynamic
/*  25:    */   extends ExsltBase
/*  26:    */ {
/*  27:    */   public static final String EXSL_URI = "http://exslt.org/common";
/*  28:    */   
/*  29:    */   public static double max(ExpressionContext myContext, NodeList nl, String expr)
/*  30:    */     throws SAXNotSupportedException
/*  31:    */   {
/*  32:103 */     XPathContext xctxt = null;
/*  33:104 */     if ((myContext instanceof XPathContext.XPathExpressionContext)) {
/*  34:105 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*  35:    */     } else {
/*  36:107 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*  37:    */     }
/*  38:109 */     if ((expr == null) || (expr.length() == 0)) {
/*  39:110 */       return (0.0D / 0.0D);
/*  40:    */     }
/*  41:112 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/*  42:113 */     xctxt.pushContextNodeList(contextNodes);
/*  43:    */     
/*  44:115 */     double maxValue = -1.797693134862316E+308D;
/*  45:116 */     for (int i = 0; i < contextNodes.getLength(); i++)
/*  46:    */     {
/*  47:118 */       int contextNode = contextNodes.item(i);
/*  48:119 */       xctxt.pushCurrentNode(contextNode);
/*  49:    */       
/*  50:121 */       double result = 0.0D;
/*  51:    */       try
/*  52:    */       {
/*  53:124 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*  54:    */         
/*  55:    */ 
/*  56:127 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/*  57:    */       }
/*  58:    */       catch (TransformerException e)
/*  59:    */       {
/*  60:131 */         xctxt.popCurrentNode();
/*  61:132 */         xctxt.popContextNodeList();
/*  62:133 */         return (0.0D / 0.0D);
/*  63:    */       }
/*  64:136 */       xctxt.popCurrentNode();
/*  65:138 */       if (result > maxValue) {
/*  66:139 */         maxValue = result;
/*  67:    */       }
/*  68:    */     }
/*  69:142 */     xctxt.popContextNodeList();
/*  70:143 */     return maxValue;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static double min(ExpressionContext myContext, NodeList nl, String expr)
/*  74:    */     throws SAXNotSupportedException
/*  75:    */   {
/*  76:184 */     XPathContext xctxt = null;
/*  77:185 */     if ((myContext instanceof XPathContext.XPathExpressionContext)) {
/*  78:186 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/*  79:    */     } else {
/*  80:188 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/*  81:    */     }
/*  82:190 */     if ((expr == null) || (expr.length() == 0)) {
/*  83:191 */       return (0.0D / 0.0D);
/*  84:    */     }
/*  85:193 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/*  86:194 */     xctxt.pushContextNodeList(contextNodes);
/*  87:    */     
/*  88:196 */     double minValue = 1.7976931348623157E+308D;
/*  89:197 */     for (int i = 0; i < nl.getLength(); i++)
/*  90:    */     {
/*  91:199 */       int contextNode = contextNodes.item(i);
/*  92:200 */       xctxt.pushCurrentNode(contextNode);
/*  93:    */       
/*  94:202 */       double result = 0.0D;
/*  95:    */       try
/*  96:    */       {
/*  97:205 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/*  98:    */         
/*  99:    */ 
/* 100:208 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/* 101:    */       }
/* 102:    */       catch (TransformerException e)
/* 103:    */       {
/* 104:212 */         xctxt.popCurrentNode();
/* 105:213 */         xctxt.popContextNodeList();
/* 106:214 */         return (0.0D / 0.0D);
/* 107:    */       }
/* 108:217 */       xctxt.popCurrentNode();
/* 109:219 */       if (result < minValue) {
/* 110:220 */         minValue = result;
/* 111:    */       }
/* 112:    */     }
/* 113:223 */     xctxt.popContextNodeList();
/* 114:224 */     return minValue;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static double sum(ExpressionContext myContext, NodeList nl, String expr)
/* 118:    */     throws SAXNotSupportedException
/* 119:    */   {
/* 120:264 */     XPathContext xctxt = null;
/* 121:265 */     if ((myContext instanceof XPathContext.XPathExpressionContext)) {
/* 122:266 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/* 123:    */     } else {
/* 124:268 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/* 125:    */     }
/* 126:270 */     if ((expr == null) || (expr.length() == 0)) {
/* 127:271 */       return (0.0D / 0.0D);
/* 128:    */     }
/* 129:273 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 130:274 */     xctxt.pushContextNodeList(contextNodes);
/* 131:    */     
/* 132:276 */     double sum = 0.0D;
/* 133:277 */     for (int i = 0; i < nl.getLength(); i++)
/* 134:    */     {
/* 135:279 */       int contextNode = contextNodes.item(i);
/* 136:280 */       xctxt.pushCurrentNode(contextNode);
/* 137:    */       
/* 138:282 */       double result = 0.0D;
/* 139:    */       try
/* 140:    */       {
/* 141:285 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/* 142:    */         
/* 143:    */ 
/* 144:288 */         result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
/* 145:    */       }
/* 146:    */       catch (TransformerException e)
/* 147:    */       {
/* 148:292 */         xctxt.popCurrentNode();
/* 149:293 */         xctxt.popContextNodeList();
/* 150:294 */         return (0.0D / 0.0D);
/* 151:    */       }
/* 152:297 */       xctxt.popCurrentNode();
/* 153:    */       
/* 154:299 */       sum += result;
/* 155:    */     }
/* 156:303 */     xctxt.popContextNodeList();
/* 157:304 */     return sum;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static NodeList map(ExpressionContext myContext, NodeList nl, String expr)
/* 161:    */     throws SAXNotSupportedException
/* 162:    */   {
/* 163:366 */     XPathContext xctxt = null;
/* 164:367 */     Document lDoc = null;
/* 165:369 */     if ((myContext instanceof XPathContext.XPathExpressionContext)) {
/* 166:370 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/* 167:    */     } else {
/* 168:372 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/* 169:    */     }
/* 170:374 */     if ((expr == null) || (expr.length() == 0)) {
/* 171:375 */       return new NodeSet();
/* 172:    */     }
/* 173:377 */     NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
/* 174:378 */     xctxt.pushContextNodeList(contextNodes);
/* 175:    */     
/* 176:380 */     NodeSet resultSet = new NodeSet();
/* 177:381 */     resultSet.setShouldCacheNodes(true);
/* 178:383 */     for (int i = 0; i < nl.getLength(); i++)
/* 179:    */     {
/* 180:385 */       int contextNode = contextNodes.item(i);
/* 181:386 */       xctxt.pushCurrentNode(contextNode);
/* 182:    */       
/* 183:388 */       XObject object = null;
/* 184:    */       try
/* 185:    */       {
/* 186:391 */         XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/* 187:    */         
/* 188:    */ 
/* 189:394 */         object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());
/* 190:396 */         if ((object instanceof XNodeSet))
/* 191:    */         {
/* 192:398 */           NodeList nodelist = null;
/* 193:399 */           nodelist = ((XNodeSet)object).nodelist();
/* 194:401 */           for (int k = 0; k < nodelist.getLength(); k++)
/* 195:    */           {
/* 196:403 */             Node n = nodelist.item(k);
/* 197:404 */             if (!resultSet.contains(n)) {
/* 198:405 */               resultSet.addNode(n);
/* 199:    */             }
/* 200:    */           }
/* 201:    */         }
/* 202:    */         else
/* 203:    */         {
/* 204:410 */           if (lDoc == null)
/* 205:    */           {
/* 206:412 */             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 207:413 */             dbf.setNamespaceAware(true);
/* 208:414 */             DocumentBuilder db = dbf.newDocumentBuilder();
/* 209:415 */             lDoc = db.newDocument();
/* 210:    */           }
/* 211:418 */           Element element = null;
/* 212:419 */           if ((object instanceof XNumber)) {
/* 213:420 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:number");
/* 214:421 */           } else if ((object instanceof XBoolean)) {
/* 215:422 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:boolean");
/* 216:    */           } else {
/* 217:424 */             element = lDoc.createElementNS("http://exslt.org/common", "exsl:string");
/* 218:    */           }
/* 219:426 */           Text textNode = lDoc.createTextNode(object.str());
/* 220:427 */           element.appendChild(textNode);
/* 221:428 */           resultSet.addNode(element);
/* 222:    */         }
/* 223:    */       }
/* 224:    */       catch (Exception e)
/* 225:    */       {
/* 226:433 */         xctxt.popCurrentNode();
/* 227:434 */         xctxt.popContextNodeList();
/* 228:435 */         return new NodeSet();
/* 229:    */       }
/* 230:438 */       xctxt.popCurrentNode();
/* 231:    */     }
/* 232:442 */     xctxt.popContextNodeList();
/* 233:443 */     return resultSet;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public static XObject evaluate(ExpressionContext myContext, String xpathExpr)
/* 237:    */     throws SAXNotSupportedException
/* 238:    */   {
/* 239:465 */     if ((myContext instanceof XPathContext.XPathExpressionContext))
/* 240:    */     {
/* 241:467 */       XPathContext xctxt = null;
/* 242:    */       try
/* 243:    */       {
/* 244:470 */         xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/* 245:471 */         XPath dynamicXPath = new XPath(xpathExpr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/* 246:    */         
/* 247:    */ 
/* 248:    */ 
/* 249:475 */         return dynamicXPath.execute(xctxt, myContext.getContextNode(), xctxt.getNamespaceContext());
/* 250:    */       }
/* 251:    */       catch (TransformerException e)
/* 252:    */       {
/* 253:480 */         return new XNodeSet(xctxt.getDTMManager());
/* 254:    */       }
/* 255:    */     }
/* 256:484 */     throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static NodeList closure(ExpressionContext myContext, NodeList nl, String expr)
/* 260:    */     throws SAXNotSupportedException
/* 261:    */   {
/* 262:533 */     XPathContext xctxt = null;
/* 263:534 */     if ((myContext instanceof XPathContext.XPathExpressionContext)) {
/* 264:535 */       xctxt = ((XPathContext.XPathExpressionContext)myContext).getXPathContext();
/* 265:    */     } else {
/* 266:537 */       throw new SAXNotSupportedException(XSLMessages.createMessage("ER_INVALID_CONTEXT_PASSED", new Object[] { myContext }));
/* 267:    */     }
/* 268:539 */     if ((expr == null) || (expr.length() == 0)) {
/* 269:540 */       return new NodeSet();
/* 270:    */     }
/* 271:542 */     NodeSet closureSet = new NodeSet();
/* 272:543 */     closureSet.setShouldCacheNodes(true);
/* 273:    */     
/* 274:545 */     NodeList iterationList = nl;
/* 275:    */     do
/* 276:    */     {
/* 277:549 */       NodeSet iterationSet = new NodeSet();
/* 278:    */       
/* 279:551 */       NodeSetDTM contextNodes = new NodeSetDTM(iterationList, xctxt);
/* 280:552 */       xctxt.pushContextNodeList(contextNodes);
/* 281:554 */       for (int i = 0; i < iterationList.getLength(); i++)
/* 282:    */       {
/* 283:556 */         int contextNode = contextNodes.item(i);
/* 284:557 */         xctxt.pushCurrentNode(contextNode);
/* 285:    */         
/* 286:559 */         XObject object = null;
/* 287:    */         try
/* 288:    */         {
/* 289:562 */           XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(), xctxt.getNamespaceContext(), 0);
/* 290:    */           
/* 291:    */ 
/* 292:565 */           object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());
/* 293:567 */           if ((object instanceof XNodeSet))
/* 294:    */           {
/* 295:569 */             NodeList nodelist = null;
/* 296:570 */             nodelist = ((XNodeSet)object).nodelist();
/* 297:572 */             for (int k = 0; k < nodelist.getLength(); k++)
/* 298:    */             {
/* 299:574 */               Node n = nodelist.item(k);
/* 300:575 */               if (!iterationSet.contains(n)) {
/* 301:576 */                 iterationSet.addNode(n);
/* 302:    */               }
/* 303:    */             }
/* 304:    */           }
/* 305:    */           else
/* 306:    */           {
/* 307:581 */             xctxt.popCurrentNode();
/* 308:582 */             xctxt.popContextNodeList();
/* 309:583 */             return new NodeSet();
/* 310:    */           }
/* 311:    */         }
/* 312:    */         catch (TransformerException e)
/* 313:    */         {
/* 314:588 */           xctxt.popCurrentNode();
/* 315:589 */           xctxt.popContextNodeList();
/* 316:590 */           return new NodeSet();
/* 317:    */         }
/* 318:593 */         xctxt.popCurrentNode();
/* 319:    */       }
/* 320:597 */       xctxt.popContextNodeList();
/* 321:    */       
/* 322:599 */       iterationList = iterationSet;
/* 323:601 */       for (int i = 0; i < iterationList.getLength(); i++)
/* 324:    */       {
/* 325:603 */         Node n = iterationList.item(i);
/* 326:604 */         if (!closureSet.contains(n)) {
/* 327:605 */           closureSet.addNode(n);
/* 328:    */         }
/* 329:    */       }
/* 330:608 */     } while (iterationList.getLength() > 0);
/* 331:610 */     return closureSet;
/* 332:    */   }
/* 333:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltDynamic
 * JD-Core Version:    0.7.0.1
 */