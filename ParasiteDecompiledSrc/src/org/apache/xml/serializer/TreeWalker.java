/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import org.apache.xml.serializer.utils.AttList;
/*   5:    */ import org.apache.xml.serializer.utils.DOM2Helper;
/*   6:    */ import org.w3c.dom.CharacterData;
/*   7:    */ import org.w3c.dom.Comment;
/*   8:    */ import org.w3c.dom.Element;
/*   9:    */ import org.w3c.dom.EntityReference;
/*  10:    */ import org.w3c.dom.NamedNodeMap;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.w3c.dom.ProcessingInstruction;
/*  13:    */ import org.w3c.dom.Text;
/*  14:    */ import org.xml.sax.ContentHandler;
/*  15:    */ import org.xml.sax.Locator;
/*  16:    */ import org.xml.sax.SAXException;
/*  17:    */ import org.xml.sax.ext.LexicalHandler;
/*  18:    */ import org.xml.sax.helpers.LocatorImpl;
/*  19:    */ 
/*  20:    */ public final class TreeWalker
/*  21:    */ {
/*  22:    */   private final ContentHandler m_contentHandler;
/*  23:    */   private final SerializationHandler m_Serializer;
/*  24:    */   protected final DOM2Helper m_dh;
/*  25: 68 */   private final LocatorImpl m_locator = new LocatorImpl();
/*  26:    */   
/*  27:    */   public ContentHandler getContentHandler()
/*  28:    */   {
/*  29: 77 */     return this.m_contentHandler;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public TreeWalker(ContentHandler ch)
/*  33:    */   {
/*  34: 81 */     this(ch, null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TreeWalker(ContentHandler contentHandler, String systemId)
/*  38:    */   {
/*  39: 91 */     this.m_contentHandler = contentHandler;
/*  40: 92 */     if ((this.m_contentHandler instanceof SerializationHandler)) {
/*  41: 93 */       this.m_Serializer = ((SerializationHandler)this.m_contentHandler);
/*  42:    */     } else {
/*  43: 96 */       this.m_Serializer = null;
/*  44:    */     }
/*  45: 99 */     this.m_contentHandler.setDocumentLocator(this.m_locator);
/*  46:100 */     if (systemId != null) {
/*  47:101 */       this.m_locator.setSystemId(systemId);
/*  48:    */     } else {
/*  49:    */       try
/*  50:    */       {
/*  51:105 */         this.m_locator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  52:    */       }
/*  53:    */       catch (SecurityException se) {}
/*  54:    */     }
/*  55:112 */     if (this.m_contentHandler != null) {
/*  56:113 */       this.m_contentHandler.setDocumentLocator(this.m_locator);
/*  57:    */     }
/*  58:    */     try
/*  59:    */     {
/*  60:116 */       this.m_locator.setSystemId(System.getProperty("user.dir") + File.separator + "dummy.xsl");
/*  61:    */     }
/*  62:    */     catch (SecurityException se) {}
/*  63:121 */     this.m_dh = new DOM2Helper();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void traverse(Node pos)
/*  67:    */     throws SAXException
/*  68:    */   {
/*  69:139 */     this.m_contentHandler.startDocument();
/*  70:    */     
/*  71:141 */     Node top = pos;
/*  72:143 */     while (null != pos)
/*  73:    */     {
/*  74:145 */       startNode(pos);
/*  75:    */       
/*  76:147 */       Node nextNode = pos.getFirstChild();
/*  77:149 */       while (null == nextNode)
/*  78:    */       {
/*  79:151 */         endNode(pos);
/*  80:153 */         if (top.equals(pos)) {
/*  81:    */           break;
/*  82:    */         }
/*  83:156 */         nextNode = pos.getNextSibling();
/*  84:158 */         if (null == nextNode)
/*  85:    */         {
/*  86:160 */           pos = pos.getParentNode();
/*  87:162 */           if ((null == pos) || (top.equals(pos)))
/*  88:    */           {
/*  89:164 */             if (null != pos) {
/*  90:165 */               endNode(pos);
/*  91:    */             }
/*  92:167 */             nextNode = null;
/*  93:    */             
/*  94:169 */             break;
/*  95:    */           }
/*  96:    */         }
/*  97:    */       }
/*  98:174 */       pos = nextNode;
/*  99:    */     }
/* 100:176 */     this.m_contentHandler.endDocument();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void traverse(Node pos, Node top)
/* 104:    */     throws SAXException
/* 105:    */   {
/* 106:195 */     this.m_contentHandler.startDocument();
/* 107:197 */     while (null != pos)
/* 108:    */     {
/* 109:199 */       startNode(pos);
/* 110:    */       
/* 111:201 */       Node nextNode = pos.getFirstChild();
/* 112:203 */       while (null == nextNode)
/* 113:    */       {
/* 114:205 */         endNode(pos);
/* 115:207 */         if ((null != top) && (top.equals(pos))) {
/* 116:    */           break;
/* 117:    */         }
/* 118:210 */         nextNode = pos.getNextSibling();
/* 119:212 */         if (null == nextNode)
/* 120:    */         {
/* 121:214 */           pos = pos.getParentNode();
/* 122:216 */           if ((null == pos) || ((null != top) && (top.equals(pos))))
/* 123:    */           {
/* 124:218 */             nextNode = null;
/* 125:    */             
/* 126:220 */             break;
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:225 */       pos = nextNode;
/* 131:    */     }
/* 132:227 */     this.m_contentHandler.endDocument();
/* 133:    */   }
/* 134:    */   
/* 135:231 */   boolean nextIsRaw = false;
/* 136:    */   
/* 137:    */   private final void dispatachChars(Node node)
/* 138:    */     throws SAXException
/* 139:    */   {
/* 140:239 */     if (this.m_Serializer != null)
/* 141:    */     {
/* 142:241 */       this.m_Serializer.characters(node);
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:245 */       String data = ((Text)node).getData();
/* 147:246 */       this.m_contentHandler.characters(data.toCharArray(), 0, data.length());
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void startNode(Node node)
/* 152:    */     throws SAXException
/* 153:    */   {
/* 154:271 */     if ((node instanceof Locator))
/* 155:    */     {
/* 156:273 */       Locator loc = (Locator)node;
/* 157:274 */       this.m_locator.setColumnNumber(loc.getColumnNumber());
/* 158:275 */       this.m_locator.setLineNumber(loc.getLineNumber());
/* 159:276 */       this.m_locator.setPublicId(loc.getPublicId());
/* 160:277 */       this.m_locator.setSystemId(loc.getSystemId());
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:281 */       this.m_locator.setColumnNumber(0);
/* 165:282 */       this.m_locator.setLineNumber(0);
/* 166:    */     }
/* 167:285 */     switch (node.getNodeType())
/* 168:    */     {
/* 169:    */     case 8: 
/* 170:289 */       String data = ((Comment)node).getData();
/* 171:291 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 172:    */       {
/* 173:293 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 174:    */         
/* 175:295 */         lh.comment(data.toCharArray(), 0, data.length());
/* 176:    */       }
/* 177:298 */       break;
/* 178:    */     case 11: 
/* 179:    */       break;
/* 180:    */     case 9: 
/* 181:    */       break;
/* 182:    */     case 1: 
/* 183:307 */       Element elem_node = (Element)node;
/* 184:    */       
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:312 */       String uri = elem_node.getNamespaceURI();
/* 189:313 */       if (uri != null)
/* 190:    */       {
/* 191:314 */         String prefix = elem_node.getPrefix();
/* 192:315 */         if (prefix == null) {
/* 193:316 */           prefix = "";
/* 194:    */         }
/* 195:317 */         this.m_contentHandler.startPrefixMapping(prefix, uri);
/* 196:    */       }
/* 197:320 */       NamedNodeMap atts = elem_node.getAttributes();
/* 198:321 */       int nAttrs = atts.getLength();
/* 199:327 */       for (int i = 0; i < nAttrs; i++)
/* 200:    */       {
/* 201:329 */         Node attr = atts.item(i);
/* 202:330 */         String attrName = attr.getNodeName();
/* 203:331 */         int colon = attrName.indexOf(':');
/* 204:    */         String prefix;
/* 205:335 */         if ((attrName.equals("xmlns")) || (attrName.startsWith("xmlns:")))
/* 206:    */         {
/* 207:340 */           if (colon < 0) {
/* 208:341 */             prefix = "";
/* 209:    */           } else {
/* 210:343 */             prefix = attrName.substring(colon + 1);
/* 211:    */           }
/* 212:345 */           this.m_contentHandler.startPrefixMapping(prefix, attr.getNodeValue());
/* 213:    */         }
/* 214:348 */         else if (colon > 0)
/* 215:    */         {
/* 216:349 */           prefix = attrName.substring(0, colon);
/* 217:350 */           String uri = attr.getNamespaceURI();
/* 218:351 */           if (uri != null) {
/* 219:352 */             this.m_contentHandler.startPrefixMapping(prefix, uri);
/* 220:    */           }
/* 221:    */         }
/* 222:    */       }
/* 223:356 */       String ns = this.m_dh.getNamespaceOfNode(node);
/* 224:357 */       if (null == ns) {
/* 225:358 */         ns = "";
/* 226:    */       }
/* 227:359 */       this.m_contentHandler.startElement(ns, this.m_dh.getLocalNameOfNode(node), node.getNodeName(), new AttList(atts, this.m_dh));
/* 228:    */       
/* 229:    */ 
/* 230:    */ 
/* 231:363 */       break;
/* 232:    */     case 7: 
/* 233:366 */       ProcessingInstruction pi = (ProcessingInstruction)node;
/* 234:367 */       String name = pi.getNodeName();
/* 235:370 */       if (name.equals("xslt-next-is-raw")) {
/* 236:372 */         this.nextIsRaw = true;
/* 237:    */       } else {
/* 238:376 */         this.m_contentHandler.processingInstruction(pi.getNodeName(), pi.getData());
/* 239:    */       }
/* 240:380 */       break;
/* 241:    */     case 4: 
/* 242:383 */       boolean isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 243:384 */       LexicalHandler lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/* 244:387 */       if (isLexH) {
/* 245:389 */         lh.startCDATA();
/* 246:    */       }
/* 247:392 */       dispatachChars(node);
/* 248:395 */       if (isLexH) {
/* 249:397 */         lh.endCDATA();
/* 250:    */       }
/* 251:401 */       break;
/* 252:    */     case 3: 
/* 253:406 */       if (this.nextIsRaw)
/* 254:    */       {
/* 255:408 */         this.nextIsRaw = false;
/* 256:    */         
/* 257:410 */         this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 258:411 */         dispatachChars(node);
/* 259:412 */         this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/* 260:    */       }
/* 261:    */       else
/* 262:    */       {
/* 263:416 */         dispatachChars(node);
/* 264:    */       }
/* 265:419 */       break;
/* 266:    */     case 5: 
/* 267:422 */       EntityReference eref = (EntityReference)node;
/* 268:424 */       if ((this.m_contentHandler instanceof LexicalHandler)) {
/* 269:426 */         ((LexicalHandler)this.m_contentHandler).startEntity(eref.getNodeName());
/* 270:    */       }
/* 271:435 */       break;
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   protected void endNode(Node node)
/* 276:    */     throws SAXException
/* 277:    */   {
/* 278:451 */     switch (node.getNodeType())
/* 279:    */     {
/* 280:    */     case 9: 
/* 281:    */       break;
/* 282:    */     case 1: 
/* 283:457 */       String ns = this.m_dh.getNamespaceOfNode(node);
/* 284:458 */       if (null == ns) {
/* 285:459 */         ns = "";
/* 286:    */       }
/* 287:460 */       this.m_contentHandler.endElement(ns, this.m_dh.getLocalNameOfNode(node), node.getNodeName());
/* 288:464 */       if (this.m_Serializer == null)
/* 289:    */       {
/* 290:468 */         Element elem_node = (Element)node;
/* 291:469 */         NamedNodeMap atts = elem_node.getAttributes();
/* 292:470 */         int nAttrs = atts.getLength();
/* 293:474 */         for (int i = nAttrs - 1; 0 <= i; i--)
/* 294:    */         {
/* 295:476 */           Node attr = atts.item(i);
/* 296:477 */           String attrName = attr.getNodeName();
/* 297:478 */           int colon = attrName.indexOf(':');
/* 298:    */           String prefix;
/* 299:481 */           if ((attrName.equals("xmlns")) || (attrName.startsWith("xmlns:")))
/* 300:    */           {
/* 301:486 */             if (colon < 0) {
/* 302:487 */               prefix = "";
/* 303:    */             } else {
/* 304:489 */               prefix = attrName.substring(colon + 1);
/* 305:    */             }
/* 306:491 */             this.m_contentHandler.endPrefixMapping(prefix);
/* 307:    */           }
/* 308:493 */           else if (colon > 0)
/* 309:    */           {
/* 310:494 */             prefix = attrName.substring(0, colon);
/* 311:495 */             this.m_contentHandler.endPrefixMapping(prefix);
/* 312:    */           }
/* 313:    */         }
/* 314:499 */         String uri = elem_node.getNamespaceURI();
/* 315:500 */         if (uri != null)
/* 316:    */         {
/* 317:501 */           String prefix = elem_node.getPrefix();
/* 318:502 */           if (prefix == null) {
/* 319:503 */             prefix = "";
/* 320:    */           }
/* 321:504 */           this.m_contentHandler.endPrefixMapping(prefix);
/* 322:    */         }
/* 323:    */       }
/* 324:    */       break;
/* 325:    */     case 4: 
/* 326:    */       break;
/* 327:    */     case 5: 
/* 328:513 */       EntityReference eref = (EntityReference)node;
/* 329:515 */       if ((this.m_contentHandler instanceof LexicalHandler))
/* 330:    */       {
/* 331:517 */         LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/* 332:    */         
/* 333:519 */         lh.endEntity(eref.getNodeName());
/* 334:    */       }
/* 335:522 */       break;
/* 336:    */     }
/* 337:    */   }
/* 338:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.TreeWalker
 * JD-Core Version:    0.7.0.1
 */