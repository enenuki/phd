/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.dom4j.DocumentFactory;
/*   5:    */ import org.dom4j.QName;
/*   6:    */ import org.dom4j.tree.DefaultDocument;
/*   7:    */ import org.w3c.dom.Attr;
/*   8:    */ import org.w3c.dom.CDATASection;
/*   9:    */ import org.w3c.dom.Comment;
/*  10:    */ import org.w3c.dom.DOMException;
/*  11:    */ import org.w3c.dom.DOMImplementation;
/*  12:    */ import org.w3c.dom.Document;
/*  13:    */ import org.w3c.dom.DocumentFragment;
/*  14:    */ import org.w3c.dom.DocumentType;
/*  15:    */ import org.w3c.dom.Element;
/*  16:    */ import org.w3c.dom.EntityReference;
/*  17:    */ import org.w3c.dom.NamedNodeMap;
/*  18:    */ import org.w3c.dom.Node;
/*  19:    */ import org.w3c.dom.NodeList;
/*  20:    */ import org.w3c.dom.ProcessingInstruction;
/*  21:    */ import org.w3c.dom.Text;
/*  22:    */ 
/*  23:    */ public class DOMDocument
/*  24:    */   extends DefaultDocument
/*  25:    */   implements Document
/*  26:    */ {
/*  27: 36 */   private static final DOMDocumentFactory DOCUMENT_FACTORY = (DOMDocumentFactory)DOMDocumentFactory.getInstance();
/*  28:    */   
/*  29:    */   public DOMDocument()
/*  30:    */   {
/*  31: 40 */     init();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DOMDocument(String name)
/*  35:    */   {
/*  36: 44 */     super(name);
/*  37: 45 */     init();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public DOMDocument(DOMElement rootElement)
/*  41:    */   {
/*  42: 49 */     super(rootElement);
/*  43: 50 */     init();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DOMDocument(DOMDocumentType docType)
/*  47:    */   {
/*  48: 54 */     super(docType);
/*  49: 55 */     init();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public DOMDocument(DOMElement rootElement, DOMDocumentType docType)
/*  53:    */   {
/*  54: 59 */     super(rootElement, docType);
/*  55: 60 */     init();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public DOMDocument(String name, DOMElement rootElement, DOMDocumentType docType)
/*  59:    */   {
/*  60: 65 */     super(name, rootElement, docType);
/*  61: 66 */     init();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void init()
/*  65:    */   {
/*  66: 70 */     setDocumentFactory(DOCUMENT_FACTORY);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean supports(String feature, String version)
/*  70:    */   {
/*  71: 76 */     return DOMNodeHelper.supports(this, feature, version);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getNamespaceURI()
/*  75:    */   {
/*  76: 80 */     return DOMNodeHelper.getNamespaceURI(this);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getPrefix()
/*  80:    */   {
/*  81: 84 */     return DOMNodeHelper.getPrefix(this);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setPrefix(String prefix)
/*  85:    */     throws DOMException
/*  86:    */   {
/*  87: 88 */     DOMNodeHelper.setPrefix(this, prefix);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getLocalName()
/*  91:    */   {
/*  92: 92 */     return DOMNodeHelper.getLocalName(this);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getNodeName()
/*  96:    */   {
/*  97: 96 */     return "#document";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNodeValue()
/* 101:    */     throws DOMException
/* 102:    */   {
/* 103:103 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setNodeValue(String nodeValue)
/* 107:    */     throws DOMException
/* 108:    */   {}
/* 109:    */   
/* 110:    */   public Node getParentNode()
/* 111:    */   {
/* 112:110 */     return DOMNodeHelper.getParentNode(this);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public NodeList getChildNodes()
/* 116:    */   {
/* 117:114 */     return DOMNodeHelper.createNodeList(content());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Node getFirstChild()
/* 121:    */   {
/* 122:118 */     return DOMNodeHelper.asDOMNode(node(0));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Node getLastChild()
/* 126:    */   {
/* 127:122 */     return DOMNodeHelper.asDOMNode(node(nodeCount() - 1));
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Node getPreviousSibling()
/* 131:    */   {
/* 132:126 */     return DOMNodeHelper.getPreviousSibling(this);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Node getNextSibling()
/* 136:    */   {
/* 137:130 */     return DOMNodeHelper.getNextSibling(this);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public NamedNodeMap getAttributes()
/* 141:    */   {
/* 142:134 */     return null;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Document getOwnerDocument()
/* 146:    */   {
/* 147:138 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Node insertBefore(Node newChild, Node refChild)
/* 151:    */     throws DOMException
/* 152:    */   {
/* 153:143 */     checkNewChildNode(newChild);
/* 154:    */     
/* 155:145 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 159:    */     throws DOMException
/* 160:    */   {
/* 161:150 */     checkNewChildNode(newChild);
/* 162:    */     
/* 163:152 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Node removeChild(Node oldChild)
/* 167:    */     throws DOMException
/* 168:    */   {
/* 169:157 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Node appendChild(Node newChild)
/* 173:    */     throws DOMException
/* 174:    */   {
/* 175:162 */     checkNewChildNode(newChild);
/* 176:    */     
/* 177:164 */     return DOMNodeHelper.appendChild(this, newChild);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void checkNewChildNode(Node newChild)
/* 181:    */     throws DOMException
/* 182:    */   {
/* 183:169 */     int nodeType = newChild.getNodeType();
/* 184:171 */     if ((nodeType != 1) && (nodeType != 8) && (nodeType != 7) && (nodeType != 10)) {
/* 185:175 */       throw new DOMException((short)3, "Given node cannot be a child of document");
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean hasChildNodes()
/* 190:    */   {
/* 191:181 */     return nodeCount() > 0;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Node cloneNode(boolean deep)
/* 195:    */   {
/* 196:185 */     return DOMNodeHelper.cloneNode(this, deep);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isSupported(String feature, String version)
/* 200:    */   {
/* 201:189 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean hasAttributes()
/* 205:    */   {
/* 206:193 */     return DOMNodeHelper.hasAttributes(this);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public NodeList getElementsByTagName(String name)
/* 210:    */   {
/* 211:199 */     ArrayList list = new ArrayList();
/* 212:200 */     DOMNodeHelper.appendElementsByTagName(list, this, name);
/* 213:    */     
/* 214:202 */     return DOMNodeHelper.createNodeList(list);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public NodeList getElementsByTagNameNS(String namespace, String name)
/* 218:    */   {
/* 219:206 */     ArrayList list = new ArrayList();
/* 220:207 */     DOMNodeHelper.appendElementsByTagNameNS(list, this, namespace, name);
/* 221:    */     
/* 222:209 */     return DOMNodeHelper.createNodeList(list);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public DocumentType getDoctype()
/* 226:    */   {
/* 227:213 */     return DOMNodeHelper.asDOMDocumentType(getDocType());
/* 228:    */   }
/* 229:    */   
/* 230:    */   public DOMImplementation getImplementation()
/* 231:    */   {
/* 232:217 */     if ((getDocumentFactory() instanceof DOMImplementation)) {
/* 233:218 */       return (DOMImplementation)getDocumentFactory();
/* 234:    */     }
/* 235:220 */     return DOCUMENT_FACTORY;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Element getDocumentElement()
/* 239:    */   {
/* 240:225 */     return DOMNodeHelper.asDOMElement(getRootElement());
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Element createElement(String name)
/* 244:    */     throws DOMException
/* 245:    */   {
/* 246:229 */     return (Element)getDocumentFactory().createElement(name);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public DocumentFragment createDocumentFragment()
/* 250:    */   {
/* 251:233 */     DOMNodeHelper.notSupported();
/* 252:    */     
/* 253:235 */     return null;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Text createTextNode(String data)
/* 257:    */   {
/* 258:239 */     return (Text)getDocumentFactory().createText(data);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Comment createComment(String data)
/* 262:    */   {
/* 263:243 */     return (Comment)getDocumentFactory().createComment(data);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public CDATASection createCDATASection(String data)
/* 267:    */     throws DOMException
/* 268:    */   {
/* 269:247 */     return (CDATASection)getDocumentFactory().createCDATA(data);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/* 273:    */     throws DOMException
/* 274:    */   {
/* 275:252 */     return (ProcessingInstruction)getDocumentFactory().createProcessingInstruction(target, data);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Attr createAttribute(String name)
/* 279:    */     throws DOMException
/* 280:    */   {
/* 281:257 */     QName qname = getDocumentFactory().createQName(name);
/* 282:    */     
/* 283:259 */     return (Attr)getDocumentFactory().createAttribute(null, qname, "");
/* 284:    */   }
/* 285:    */   
/* 286:    */   public EntityReference createEntityReference(String name)
/* 287:    */     throws DOMException
/* 288:    */   {
/* 289:264 */     return (EntityReference)getDocumentFactory().createEntity(name, null);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public Node importNode(Node importedNode, boolean deep)
/* 293:    */     throws DOMException
/* 294:    */   {
/* 295:269 */     DOMNodeHelper.notSupported();
/* 296:    */     
/* 297:271 */     return null;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Element createElementNS(String namespaceURI, String qualifiedName)
/* 301:    */     throws DOMException
/* 302:    */   {
/* 303:276 */     QName qname = getDocumentFactory().createQName(qualifiedName, namespaceURI);
/* 304:    */     
/* 305:    */ 
/* 306:279 */     return (Element)getDocumentFactory().createElement(qname);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Attr createAttributeNS(String namespaceURI, String qualifiedName)
/* 310:    */     throws DOMException
/* 311:    */   {
/* 312:284 */     QName qname = getDocumentFactory().createQName(qualifiedName, namespaceURI);
/* 313:    */     
/* 314:    */ 
/* 315:287 */     return (Attr)getDocumentFactory().createAttribute(null, qname, null);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Element getElementById(String elementId)
/* 319:    */   {
/* 320:292 */     return DOMNodeHelper.asDOMElement(elementByID(elementId));
/* 321:    */   }
/* 322:    */   
/* 323:    */   protected DocumentFactory getDocumentFactory()
/* 324:    */   {
/* 325:298 */     if (super.getDocumentFactory() == null) {
/* 326:299 */       return DOCUMENT_FACTORY;
/* 327:    */     }
/* 328:301 */     return super.getDocumentFactory();
/* 329:    */   }
/* 330:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMDocument
 * JD-Core Version:    0.7.0.1
 */