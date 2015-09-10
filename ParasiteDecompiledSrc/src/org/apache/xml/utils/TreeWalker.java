/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import org.apache.xml.dtm.ref.dom2dtm.DOM2DTM.CharacterNodeHandler;
/*   5:    */ import org.w3c.dom.CharacterData;
/*   6:    */ import org.w3c.dom.Comment;
/*   7:    */ import org.w3c.dom.Element;
/*   8:    */ import org.w3c.dom.EntityReference;
/*   9:    */ import org.w3c.dom.NamedNodeMap;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ import org.w3c.dom.ProcessingInstruction;
/*  12:    */ import org.w3c.dom.Text;
/*  13:    */ import org.xml.sax.ContentHandler;
/*  14:    */ import org.xml.sax.Locator;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ import org.xml.sax.ext.LexicalHandler;
/*  17:    */ import org.xml.sax.helpers.LocatorImpl;
/*  18:    */ 
/*  19:    */ public class TreeWalker
/*  20:    */ {
/*  21: 48 */   private ContentHandler m_contentHandler = null;
/*  22:    */   protected DOMHelper m_dh;
/*  23: 57 */   private LocatorImpl m_locator = new LocatorImpl();
/*  24:    */   
/*  25:    */   public ContentHandler getContentHandler()
/*  26:    */   {
/*  27: 66 */     return this.m_contentHandler;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setContentHandler(ContentHandler ch)
/*  31:    */   {
/*  32: 76 */     this.m_contentHandler = ch;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public TreeWalker(ContentHandler contentHandler, DOMHelper dh, String systemId)
/*  36:    */   {
/*  37: 87 */     this.m_contentHandler = contentHandler;
/*  38: 88 */     this.m_contentHandler.setDocumentLocator(this.m_locator);
/*  39: 89 */     if (systemId != null) {
/*  40: 90 */       this.m_locator.setSystemId(systemId);
/*  41:    */     } else {
/*  42:    */       try
/*  43:    */       {
/*  44: 94 */         this.m_locator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  45:    */       }
/*  46:    */       catch (SecurityException se) {}
/*  47:    */     }
/*  48: 99 */     this.m_dh = dh;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TreeWalker(ContentHandler contentHandler, DOMHelper dh)
/*  52:    */   {
/*  53:109 */     this.m_contentHandler = contentHandler;
/*  54:110 */     this.m_contentHandler.setDocumentLocator(this.m_locator);
/*  55:    */     try
/*  56:    */     {
/*  57:113 */       this.m_locator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  58:    */     }
/*  59:    */     catch (SecurityException se) {}
/*  60:117 */     this.m_dh = dh;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TreeWalker(ContentHandler contentHandler)
/*  64:    */   {
/*  65:127 */     this.m_contentHandler = contentHandler;
/*  66:128 */     if (this.m_contentHandler != null) {
/*  67:129 */       this.m_contentHandler.setDocumentLocator(this.m_locator);
/*  68:    */     }
/*  69:    */     try
/*  70:    */     {
/*  71:132 */       this.m_locator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  72:    */     }
/*  73:    */     catch (SecurityException se) {}
/*  74:137 */     this.m_dh = new DOM2Helper();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void traverse(Node pos)
/*  78:    */     throws SAXException
/*  79:    */   {
/*  80:154 */     this.m_contentHandler.startDocument();
/*  81:    */     
/*  82:156 */     traverseFragment(pos);
/*  83:    */     
/*  84:158 */     this.m_contentHandler.endDocument();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void traverseFragment(Node pos)
/*  88:    */     throws SAXException
/*  89:    */   {
/*  90:173 */     Node top = pos;
/*  91:175 */     while (null != pos)
/*  92:    */     {
/*  93:177 */       startNode(pos);
/*  94:    */       
/*  95:179 */       Node nextNode = pos.getFirstChild();
/*  96:181 */       while (null == nextNode)
/*  97:    */       {
/*  98:183 */         endNode(pos);
/*  99:185 */         if (top.equals(pos)) {
/* 100:    */           break;
/* 101:    */         }
/* 102:188 */         nextNode = pos.getNextSibling();
/* 103:190 */         if (null == nextNode)
/* 104:    */         {
/* 105:192 */           pos = pos.getParentNode();
/* 106:194 */           if ((null == pos) || (top.equals(pos)))
/* 107:    */           {
/* 108:196 */             if (null != pos) {
/* 109:197 */               endNode(pos);
/* 110:    */             }
/* 111:199 */             nextNode = null;
/* 112:    */             
/* 113:201 */             break;
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:206 */       pos = nextNode;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void traverse(Node pos, Node top)
/* 122:    */     throws SAXException
/* 123:    */   {
/* 124:226 */     this.m_contentHandler.startDocument();
/* 125:228 */     while (null != pos)
/* 126:    */     {
/* 127:230 */       startNode(pos);
/* 128:    */       
/* 129:232 */       Node nextNode = pos.getFirstChild();
/* 130:234 */       while (null == nextNode)
/* 131:    */       {
/* 132:236 */         endNode(pos);
/* 133:238 */         if ((null != top) && (top.equals(pos))) {
/* 134:    */           break;
/* 135:    */         }
/* 136:241 */         nextNode = pos.getNextSibling();
/* 137:243 */         if (null == nextNode)
/* 138:    */         {
/* 139:245 */           pos = pos.getParentNode();
/* 140:247 */           if ((null == pos) || ((null != top) && (top.equals(pos))))
/* 141:    */           {
/* 142:249 */             nextNode = null;
/* 143:    */             
/* 144:251 */             break;
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:256 */       pos = nextNode;
/* 149:    */     }
/* 150:258 */     this.m_contentHandler.endDocument();
/* 151:    */   }
/* 152:    */   
/* 153:262 */   boolean nextIsRaw = false;
/* 154:    */   
/* 155:    */   private final void dispatachChars(Node node)
/* 156:    */     throws SAXException
/* 157:    */   {
/* 158:270 */     if ((this.m_contentHandler instanceof DOM2DTM.CharacterNodeHandler))
/* 159:    */     {
/* 160:272 */       ((DOM2DTM.CharacterNodeHandler)this.m_contentHandler).characters(node);
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:276 */       String data = ((Text)node).getData();
/* 165:277 */       this.m_contentHandler.characters(data.toCharArray(), 0, data.length());
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void startNode(Node node)
/* 170:    */     throws SAXException
/* 171:    */   {
/* 172:292 */     if ((this.m_contentHandler instanceof NodeConsumer)) {
/* 173:294 */       ((NodeConsumer)this.m_contentHandler).setOriginatingNode(node);
/* 174:    */     }
/* 175:297 */     if ((node instanceof Locator))
/* 176:    */     {
/* 177:299 */       Locator loc = (Locator)node;
/* 178:300 */       this.m_locator.setColumnNumber(loc.getColumnNumber());
/* 179:301 */       this.m_locator.setLineNumber(loc.getLineNumber());
/* 180:302 */       this.m_locator.setPublicId(loc.getPublicId());
/* 181:303 */       this.m_locator.setSystemId(loc.getSystemId());
/* 182:    */     }
/* 183:    */     else
/* 184:    */     {
/* 185:307 */       this.m_locator.setColumnNumber(0);
/* 186:308 */       this.m_locator.setLineNumber(0);
/* 187:    */     }
/* 188:311 */     switch (node.getNodeType())
/* 189:    */     {
/* 190:    */     case 8: 
/* 191:315 */       String data = ((Comment)node).getData();
/* 192:317 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 193:    */       {
/* 194:319 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 195:    */         
/* 196:321 */         lh.comment(data.toCharArray(), 0, data.length());
/* 197:    */       }
/* 198:324 */       break;
/* 199:    */     case 11: 
/* 200:    */       break;
/* 201:    */     case 9: 
/* 202:    */       break;
/* 203:    */     case 1: 
/* 204:333 */       NamedNodeMap atts = ((Element)node).getAttributes();
/* 205:334 */       int nAttrs = atts.getLength();
/* 206:337 */       for (int i = 0; i < nAttrs; i++)
/* 207:    */       {
/* 208:339 */         Node attr = atts.item(i);
/* 209:340 */         String attrName = attr.getNodeName();
/* 210:343 */         if ((attrName.equals("xmlns")) || (attrName.startsWith("xmlns:")))
/* 211:    */         {
/* 212:    */           int index;
/* 213:350 */           String prefix = (index = attrName.indexOf(":")) < 0 ? "" : attrName.substring(index + 1);
/* 214:    */           
/* 215:    */ 
/* 216:353 */           this.m_contentHandler.startPrefixMapping(prefix, attr.getNodeValue());
/* 217:    */         }
/* 218:    */       }
/* 219:361 */       String ns = this.m_dh.getNamespaceOfNode(node);
/* 220:362 */       if (null == ns) {
/* 221:363 */         ns = "";
/* 222:    */       }
/* 223:364 */       this.m_contentHandler.startElement(ns, this.m_dh.getLocalNameOfNode(node), node.getNodeName(), new AttList(atts, this.m_dh));
/* 224:    */       
/* 225:    */ 
/* 226:    */ 
/* 227:368 */       break;
/* 228:    */     case 7: 
/* 229:371 */       ProcessingInstruction pi = (ProcessingInstruction)node;
/* 230:372 */       String name = pi.getNodeName();
/* 231:375 */       if (name.equals("xslt-next-is-raw")) {
/* 232:377 */         this.nextIsRaw = true;
/* 233:    */       } else {
/* 234:381 */         this.m_contentHandler.processingInstruction(pi.getNodeName(), pi.getData());
/* 235:    */       }
/* 236:385 */       break;
/* 237:    */     case 4: 
/* 238:388 */       boolean isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 239:389 */       LexicalHandler lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/* 240:392 */       if (isLexH) {
/* 241:394 */         lh.startCDATA();
/* 242:    */       }
/* 243:397 */       dispatachChars(node);
/* 244:400 */       if (isLexH) {
/* 245:402 */         lh.endCDATA();
/* 246:    */       }
/* 247:406 */       break;
/* 248:    */     case 3: 
/* 249:411 */       if (this.nextIsRaw)
/* 250:    */       {
/* 251:413 */         this.nextIsRaw = false;
/* 252:    */         
/* 253:415 */         this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 254:416 */         dispatachChars(node);
/* 255:417 */         this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/* 256:    */       }
/* 257:    */       else
/* 258:    */       {
/* 259:421 */         dispatachChars(node);
/* 260:    */       }
/* 261:424 */       break;
/* 262:    */     case 5: 
/* 263:427 */       EntityReference eref = (EntityReference)node;
/* 264:429 */       if ((this.m_contentHandler instanceof LexicalHandler)) {
/* 265:431 */         ((LexicalHandler)this.m_contentHandler).startEntity(eref.getNodeName());
/* 266:    */       }
/* 267:440 */       break;
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   protected void endNode(Node node)
/* 272:    */     throws SAXException
/* 273:    */   {
/* 274:456 */     switch (node.getNodeType())
/* 275:    */     {
/* 276:    */     case 9: 
/* 277:    */       break;
/* 278:    */     case 1: 
/* 279:462 */       String ns = this.m_dh.getNamespaceOfNode(node);
/* 280:463 */       if (null == ns) {
/* 281:464 */         ns = "";
/* 282:    */       }
/* 283:465 */       this.m_contentHandler.endElement(ns, this.m_dh.getLocalNameOfNode(node), node.getNodeName());
/* 284:    */       
/* 285:    */ 
/* 286:    */ 
/* 287:469 */       NamedNodeMap atts = ((Element)node).getAttributes();
/* 288:470 */       int nAttrs = atts.getLength();
/* 289:472 */       for (int i = 0; i < nAttrs; i++)
/* 290:    */       {
/* 291:474 */         Node attr = atts.item(i);
/* 292:475 */         String attrName = attr.getNodeName();
/* 293:477 */         if ((attrName.equals("xmlns")) || (attrName.startsWith("xmlns:")))
/* 294:    */         {
/* 295:    */           int index;
/* 296:483 */           String prefix = (index = attrName.indexOf(":")) < 0 ? "" : attrName.substring(index + 1);
/* 297:    */           
/* 298:    */ 
/* 299:486 */           this.m_contentHandler.endPrefixMapping(prefix);
/* 300:    */         }
/* 301:    */       }
/* 302:489 */       break;
/* 303:    */     case 4: 
/* 304:    */       break;
/* 305:    */     case 5: 
/* 306:494 */       EntityReference eref = (EntityReference)node;
/* 307:496 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 308:    */       {
/* 309:498 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 310:    */         
/* 311:500 */         lh.endEntity(eref.getNodeName());
/* 312:    */       }
/* 313:503 */       break;
/* 314:    */     }
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.TreeWalker
 * JD-Core Version:    0.7.0.1
 */