/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Writer;
/*   4:    */ import java.util.Stack;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.xml.res.XMLMessages;
/*   7:    */ import org.w3c.dom.CDATASection;
/*   8:    */ import org.w3c.dom.CharacterData;
/*   9:    */ import org.w3c.dom.Document;
/*  10:    */ import org.w3c.dom.DocumentFragment;
/*  11:    */ import org.w3c.dom.Element;
/*  12:    */ import org.w3c.dom.Node;
/*  13:    */ import org.w3c.dom.Text;
/*  14:    */ import org.xml.sax.Attributes;
/*  15:    */ import org.xml.sax.ContentHandler;
/*  16:    */ import org.xml.sax.Locator;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ import org.xml.sax.ext.LexicalHandler;
/*  19:    */ 
/*  20:    */ public class DOMBuilder
/*  21:    */   implements ContentHandler, LexicalHandler
/*  22:    */ {
/*  23:    */   public Document m_doc;
/*  24: 54 */   protected Node m_currentNode = null;
/*  25: 57 */   protected Node m_root = null;
/*  26: 60 */   protected Node m_nextSibling = null;
/*  27: 63 */   public DocumentFragment m_docFrag = null;
/*  28: 66 */   protected Stack m_elemStack = new Stack();
/*  29: 69 */   protected Vector m_prefixMappings = new Vector();
/*  30:    */   
/*  31:    */   public DOMBuilder(Document doc, Node node)
/*  32:    */   {
/*  33: 80 */     this.m_doc = doc;
/*  34: 81 */     this.m_currentNode = (this.m_root = node);
/*  35: 83 */     if ((node instanceof Element)) {
/*  36: 84 */       this.m_elemStack.push(node);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public DOMBuilder(Document doc, DocumentFragment docFrag)
/*  41:    */   {
/*  42: 96 */     this.m_doc = doc;
/*  43: 97 */     this.m_docFrag = docFrag;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DOMBuilder(Document doc)
/*  47:    */   {
/*  48:108 */     this.m_doc = doc;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Node getRootDocument()
/*  52:    */   {
/*  53:118 */     return null != this.m_docFrag ? this.m_docFrag : this.m_doc;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Node getRootNode()
/*  57:    */   {
/*  58:126 */     return this.m_root;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Node getCurrentNode()
/*  62:    */   {
/*  63:136 */     return this.m_currentNode;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setNextSibling(Node nextSibling)
/*  67:    */   {
/*  68:147 */     this.m_nextSibling = nextSibling;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Node getNextSibling()
/*  72:    */   {
/*  73:157 */     return this.m_nextSibling;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Writer getWriter()
/*  77:    */   {
/*  78:167 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void append(Node newNode)
/*  82:    */     throws SAXException
/*  83:    */   {
/*  84:178 */     Node currentNode = this.m_currentNode;
/*  85:180 */     if (null != currentNode)
/*  86:    */     {
/*  87:182 */       if ((currentNode == this.m_root) && (this.m_nextSibling != null)) {
/*  88:183 */         currentNode.insertBefore(newNode, this.m_nextSibling);
/*  89:    */       } else {
/*  90:185 */         currentNode.appendChild(newNode);
/*  91:    */       }
/*  92:    */     }
/*  93:189 */     else if (null != this.m_docFrag)
/*  94:    */     {
/*  95:191 */       if (this.m_nextSibling != null) {
/*  96:192 */         this.m_docFrag.insertBefore(newNode, this.m_nextSibling);
/*  97:    */       } else {
/*  98:194 */         this.m_docFrag.appendChild(newNode);
/*  99:    */       }
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:198 */       boolean ok = true;
/* 104:199 */       short type = newNode.getNodeType();
/* 105:201 */       if (type == 3)
/* 106:    */       {
/* 107:203 */         String data = newNode.getNodeValue();
/* 108:205 */         if ((null != data) && (data.trim().length() > 0)) {
/* 109:207 */           throw new SAXException(XMLMessages.createXMLMessage("ER_CANT_OUTPUT_TEXT_BEFORE_DOC", null));
/* 110:    */         }
/* 111:212 */         ok = false;
/* 112:    */       }
/* 113:214 */       else if (type == 1)
/* 114:    */       {
/* 115:216 */         if (this.m_doc.getDocumentElement() != null)
/* 116:    */         {
/* 117:218 */           ok = false;
/* 118:    */           
/* 119:220 */           throw new SAXException(XMLMessages.createXMLMessage("ER_CANT_HAVE_MORE_THAN_ONE_ROOT", null));
/* 120:    */         }
/* 121:    */       }
/* 122:226 */       if (ok) {
/* 123:228 */         if (this.m_nextSibling != null) {
/* 124:229 */           this.m_doc.insertBefore(newNode, this.m_nextSibling);
/* 125:    */         } else {
/* 126:231 */           this.m_doc.appendChild(newNode);
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDocumentLocator(Locator locator) {}
/* 133:    */   
/* 134:    */   public void startDocument()
/* 135:    */     throws SAXException
/* 136:    */   {}
/* 137:    */   
/* 138:    */   public void endDocument()
/* 139:    */     throws SAXException
/* 140:    */   {}
/* 141:    */   
/* 142:    */   public void startElement(String ns, String localName, String name, Attributes atts)
/* 143:    */     throws SAXException
/* 144:    */   {
/* 145:    */     Element elem;
/* 146:327 */     if ((null == ns) || (ns.length() == 0)) {
/* 147:328 */       elem = this.m_doc.createElementNS(null, name);
/* 148:    */     } else {
/* 149:330 */       elem = this.m_doc.createElementNS(ns, name);
/* 150:    */     }
/* 151:332 */     append(elem);
/* 152:    */     try
/* 153:    */     {
/* 154:336 */       int nAtts = atts.getLength();
/* 155:338 */       if (0 != nAtts) {
/* 156:340 */         for (int i = 0; i < nAtts; i++)
/* 157:    */         {
/* 158:345 */           if (atts.getType(i).equalsIgnoreCase("ID")) {
/* 159:346 */             setIDAttribute(atts.getValue(i), elem);
/* 160:    */           }
/* 161:348 */           String attrNS = atts.getURI(i);
/* 162:350 */           if ("".equals(attrNS)) {
/* 163:351 */             attrNS = null;
/* 164:    */           }
/* 165:356 */           String attrQName = atts.getQName(i);
/* 166:360 */           if ((attrQName.startsWith("xmlns:")) || (attrQName.equals("xmlns"))) {
/* 167:361 */             attrNS = "http://www.w3.org/2000/xmlns/";
/* 168:    */           }
/* 169:365 */           elem.setAttributeNS(attrNS, attrQName, atts.getValue(i));
/* 170:    */         }
/* 171:    */       }
/* 172:372 */       int nDecls = this.m_prefixMappings.size();
/* 173:376 */       for (int i = 0; i < nDecls; i += 2)
/* 174:    */       {
/* 175:378 */         String prefix = (String)this.m_prefixMappings.elementAt(i);
/* 176:380 */         if (prefix != null)
/* 177:    */         {
/* 178:383 */           String declURL = (String)this.m_prefixMappings.elementAt(i + 1);
/* 179:    */           
/* 180:385 */           elem.setAttributeNS("http://www.w3.org/2000/xmlns/", prefix, declURL);
/* 181:    */         }
/* 182:    */       }
/* 183:388 */       this.m_prefixMappings.clear();
/* 184:    */       
/* 185:    */ 
/* 186:    */ 
/* 187:392 */       this.m_elemStack.push(elem);
/* 188:    */       
/* 189:394 */       this.m_currentNode = elem;
/* 190:    */     }
/* 191:    */     catch (Exception de)
/* 192:    */     {
/* 193:401 */       throw new SAXException(de);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void endElement(String ns, String localName, String name)
/* 198:    */     throws SAXException
/* 199:    */   {
/* 200:428 */     this.m_elemStack.pop();
/* 201:429 */     this.m_currentNode = (this.m_elemStack.isEmpty() ? null : (Node)this.m_elemStack.peek());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setIDAttribute(String id, Element elem) {}
/* 205:    */   
/* 206:    */   public void characters(char[] ch, int start, int length)
/* 207:    */     throws SAXException
/* 208:    */   {
/* 209:469 */     if ((isOutsideDocElem()) && (XMLCharacterRecognizer.isWhiteSpace(ch, start, length))) {
/* 210:471 */       return;
/* 211:    */     }
/* 212:473 */     if (this.m_inCData)
/* 213:    */     {
/* 214:475 */       cdata(ch, start, length);
/* 215:    */       
/* 216:477 */       return;
/* 217:    */     }
/* 218:480 */     String s = new String(ch, start, length);
/* 219:    */     
/* 220:482 */     Node childNode = this.m_currentNode != null ? this.m_currentNode.getLastChild() : null;
/* 221:483 */     if ((childNode != null) && (childNode.getNodeType() == 3))
/* 222:    */     {
/* 223:484 */       ((Text)childNode).appendData(s);
/* 224:    */     }
/* 225:    */     else
/* 226:    */     {
/* 227:487 */       Text text = this.m_doc.createTextNode(s);
/* 228:488 */       append(text);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void charactersRaw(char[] ch, int start, int length)
/* 233:    */     throws SAXException
/* 234:    */   {
/* 235:505 */     if ((isOutsideDocElem()) && (XMLCharacterRecognizer.isWhiteSpace(ch, start, length))) {
/* 236:507 */       return;
/* 237:    */     }
/* 238:510 */     String s = new String(ch, start, length);
/* 239:    */     
/* 240:512 */     append(this.m_doc.createProcessingInstruction("xslt-next-is-raw", "formatter-to-dom"));
/* 241:    */     
/* 242:514 */     append(this.m_doc.createTextNode(s));
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void startEntity(String name)
/* 246:    */     throws SAXException
/* 247:    */   {}
/* 248:    */   
/* 249:    */   public void endEntity(String name)
/* 250:    */     throws SAXException
/* 251:    */   {}
/* 252:    */   
/* 253:    */   public void entityReference(String name)
/* 254:    */     throws SAXException
/* 255:    */   {
/* 256:553 */     append(this.m_doc.createEntityReference(name));
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 260:    */     throws SAXException
/* 261:    */   {
/* 262:581 */     if (isOutsideDocElem()) {
/* 263:582 */       return;
/* 264:    */     }
/* 265:584 */     String s = new String(ch, start, length);
/* 266:    */     
/* 267:586 */     append(this.m_doc.createTextNode(s));
/* 268:    */   }
/* 269:    */   
/* 270:    */   private boolean isOutsideDocElem()
/* 271:    */   {
/* 272:596 */     return (null == this.m_docFrag) && (this.m_elemStack.size() == 0) && ((null == this.m_currentNode) || (this.m_currentNode.getNodeType() == 9));
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void processingInstruction(String target, String data)
/* 276:    */     throws SAXException
/* 277:    */   {
/* 278:617 */     append(this.m_doc.createProcessingInstruction(target, data));
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void comment(char[] ch, int start, int length)
/* 282:    */     throws SAXException
/* 283:    */   {
/* 284:633 */     append(this.m_doc.createComment(new String(ch, start, length)));
/* 285:    */   }
/* 286:    */   
/* 287:637 */   protected boolean m_inCData = false;
/* 288:    */   
/* 289:    */   public void startCDATA()
/* 290:    */     throws SAXException
/* 291:    */   {
/* 292:646 */     this.m_inCData = true;
/* 293:647 */     append(this.m_doc.createCDATASection(""));
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void endCDATA()
/* 297:    */     throws SAXException
/* 298:    */   {
/* 299:657 */     this.m_inCData = false;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void cdata(char[] ch, int start, int length)
/* 303:    */     throws SAXException
/* 304:    */   {
/* 305:685 */     if ((isOutsideDocElem()) && (XMLCharacterRecognizer.isWhiteSpace(ch, start, length))) {
/* 306:687 */       return;
/* 307:    */     }
/* 308:689 */     String s = new String(ch, start, length);
/* 309:    */     
/* 310:691 */     CDATASection section = (CDATASection)this.m_currentNode.getLastChild();
/* 311:692 */     section.appendData(s);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void startDTD(String name, String publicId, String systemId)
/* 315:    */     throws SAXException
/* 316:    */   {}
/* 317:    */   
/* 318:    */   public void endDTD()
/* 319:    */     throws SAXException
/* 320:    */   {}
/* 321:    */   
/* 322:    */   public void startPrefixMapping(String prefix, String uri)
/* 323:    */     throws SAXException
/* 324:    */   {
/* 325:758 */     if ((null == prefix) || (prefix.equals(""))) {
/* 326:759 */       prefix = "xmlns";
/* 327:    */     } else {
/* 328:760 */       prefix = "xmlns:" + prefix;
/* 329:    */     }
/* 330:761 */     this.m_prefixMappings.addElement(prefix);
/* 331:762 */     this.m_prefixMappings.addElement(uri);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void endPrefixMapping(String prefix)
/* 335:    */     throws SAXException
/* 336:    */   {}
/* 337:    */   
/* 338:    */   public void skippedEntity(String name)
/* 339:    */     throws SAXException
/* 340:    */   {}
/* 341:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.DOMBuilder
 * JD-Core Version:    0.7.0.1
 */