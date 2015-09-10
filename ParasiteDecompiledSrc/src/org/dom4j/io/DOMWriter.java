/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.List;
/*   5:    */ import org.dom4j.Attribute;
/*   6:    */ import org.dom4j.CDATA;
/*   7:    */ import org.dom4j.DocumentException;
/*   8:    */ import org.dom4j.Entity;
/*   9:    */ import org.dom4j.Namespace;
/*  10:    */ import org.dom4j.tree.NamespaceStack;
/*  11:    */ import org.w3c.dom.CDATASection;
/*  12:    */ import org.w3c.dom.DOMImplementation;
/*  13:    */ import org.w3c.dom.DocumentType;
/*  14:    */ import org.w3c.dom.EntityReference;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ 
/*  17:    */ public class DOMWriter
/*  18:    */ {
/*  19: 36 */   private static boolean loggedWarning = false;
/*  20: 38 */   private static final String[] DEFAULT_DOM_DOCUMENT_CLASSES = { "org.apache.xerces.dom.DocumentImpl", "gnu.xml.dom.DomDocument", "org.apache.crimson.tree.XmlDocument", "com.sun.xml.tree.XmlDocument", "oracle.xml.parser.v2.XMLDocument", "oracle.xml.parser.XMLDocument", "org.dom4j.dom.DOMDocument" };
/*  21:    */   private Class domDocumentClass;
/*  22: 52 */   private NamespaceStack namespaceStack = new NamespaceStack();
/*  23:    */   
/*  24:    */   public DOMWriter() {}
/*  25:    */   
/*  26:    */   public DOMWriter(Class domDocumentClass)
/*  27:    */   {
/*  28: 58 */     this.domDocumentClass = domDocumentClass;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Class getDomDocumentClass()
/*  32:    */     throws DocumentException
/*  33:    */   {
/*  34: 62 */     Class result = this.domDocumentClass;
/*  35: 64 */     if (result == null)
/*  36:    */     {
/*  37: 66 */       int size = DEFAULT_DOM_DOCUMENT_CLASSES.length;
/*  38: 68 */       for (int i = 0; i < size; i++) {
/*  39:    */         try
/*  40:    */         {
/*  41: 70 */           String name = DEFAULT_DOM_DOCUMENT_CLASSES[i];
/*  42: 71 */           result = Class.forName(name, true, DOMWriter.class.getClassLoader());
/*  43: 74 */           if (result != null) {
/*  44:    */             break;
/*  45:    */           }
/*  46:    */         }
/*  47:    */         catch (Exception e) {}
/*  48:    */       }
/*  49:    */     }
/*  50: 84 */     return result;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setDomDocumentClass(Class domDocumentClass)
/*  54:    */   {
/*  55: 96 */     this.domDocumentClass = domDocumentClass;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setDomDocumentClassName(String name)
/*  59:    */     throws DocumentException
/*  60:    */   {
/*  61:    */     try
/*  62:    */     {
/*  63:112 */       this.domDocumentClass = Class.forName(name, true, DOMWriter.class.getClassLoader());
/*  64:    */     }
/*  65:    */     catch (Exception e)
/*  66:    */     {
/*  67:115 */       throw new DocumentException("Could not load the DOM Document class: " + name, e);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public org.w3c.dom.Document write(org.dom4j.Document document)
/*  72:    */     throws DocumentException
/*  73:    */   {
/*  74:122 */     if ((document instanceof org.w3c.dom.Document)) {
/*  75:123 */       return (org.w3c.dom.Document)document;
/*  76:    */     }
/*  77:126 */     resetNamespaceStack();
/*  78:    */     
/*  79:128 */     org.w3c.dom.Document domDocument = createDomDocument(document);
/*  80:129 */     appendDOMTree(domDocument, domDocument, document.content());
/*  81:130 */     this.namespaceStack.clear();
/*  82:    */     
/*  83:132 */     return domDocument;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public org.w3c.dom.Document write(org.dom4j.Document document, DOMImplementation domImpl)
/*  87:    */     throws DocumentException
/*  88:    */   {
/*  89:137 */     if ((document instanceof org.w3c.dom.Document)) {
/*  90:138 */       return (org.w3c.dom.Document)document;
/*  91:    */     }
/*  92:141 */     resetNamespaceStack();
/*  93:    */     
/*  94:143 */     org.w3c.dom.Document domDocument = createDomDocument(document, domImpl);
/*  95:144 */     appendDOMTree(domDocument, domDocument, document.content());
/*  96:145 */     this.namespaceStack.clear();
/*  97:    */     
/*  98:147 */     return domDocument;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, List content)
/* 102:    */   {
/* 103:152 */     int size = content.size();
/* 104:154 */     for (int i = 0; i < size; i++)
/* 105:    */     {
/* 106:155 */       Object object = content.get(i);
/* 107:157 */       if ((object instanceof org.dom4j.Element))
/* 108:    */       {
/* 109:158 */         appendDOMTree(domDocument, domCurrent, (org.dom4j.Element)object);
/* 110:    */       }
/* 111:159 */       else if ((object instanceof String))
/* 112:    */       {
/* 113:160 */         appendDOMTree(domDocument, domCurrent, (String)object);
/* 114:    */       }
/* 115:161 */       else if ((object instanceof org.dom4j.Text))
/* 116:    */       {
/* 117:162 */         org.dom4j.Text text = (org.dom4j.Text)object;
/* 118:163 */         appendDOMTree(domDocument, domCurrent, text.getText());
/* 119:    */       }
/* 120:164 */       else if ((object instanceof CDATA))
/* 121:    */       {
/* 122:165 */         appendDOMTree(domDocument, domCurrent, (CDATA)object);
/* 123:    */       }
/* 124:166 */       else if ((object instanceof org.dom4j.Comment))
/* 125:    */       {
/* 126:167 */         appendDOMTree(domDocument, domCurrent, (org.dom4j.Comment)object);
/* 127:    */       }
/* 128:168 */       else if ((object instanceof Entity))
/* 129:    */       {
/* 130:169 */         appendDOMTree(domDocument, domCurrent, (Entity)object);
/* 131:    */       }
/* 132:170 */       else if ((object instanceof org.dom4j.ProcessingInstruction))
/* 133:    */       {
/* 134:171 */         appendDOMTree(domDocument, domCurrent, (org.dom4j.ProcessingInstruction)object);
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, org.dom4j.Element element)
/* 140:    */   {
/* 141:179 */     String elUri = element.getNamespaceURI();
/* 142:180 */     String elName = element.getQualifiedName();
/* 143:181 */     org.w3c.dom.Element domElement = domDocument.createElementNS(elUri, elName);
/* 144:    */     
/* 145:    */ 
/* 146:184 */     int stackSize = this.namespaceStack.size();
/* 147:    */     
/* 148:    */ 
/* 149:187 */     Namespace elementNamespace = element.getNamespace();
/* 150:189 */     if (isNamespaceDeclaration(elementNamespace))
/* 151:    */     {
/* 152:190 */       this.namespaceStack.push(elementNamespace);
/* 153:191 */       writeNamespace(domElement, elementNamespace);
/* 154:    */     }
/* 155:195 */     List declaredNamespaces = element.declaredNamespaces();
/* 156:    */     
/* 157:197 */     int i = 0;
/* 158:197 */     for (int size = declaredNamespaces.size(); i < size; i++)
/* 159:    */     {
/* 160:198 */       Namespace namespace = (Namespace)declaredNamespaces.get(i);
/* 161:200 */       if (isNamespaceDeclaration(namespace))
/* 162:    */       {
/* 163:201 */         this.namespaceStack.push(namespace);
/* 164:202 */         writeNamespace(domElement, namespace);
/* 165:    */       }
/* 166:    */     }
/* 167:207 */     int i = 0;
/* 168:207 */     for (int size = element.attributeCount(); i < size; i++)
/* 169:    */     {
/* 170:208 */       Attribute attribute = element.attribute(i);
/* 171:209 */       String attUri = attribute.getNamespaceURI();
/* 172:210 */       String attName = attribute.getQualifiedName();
/* 173:211 */       String value = attribute.getValue();
/* 174:212 */       domElement.setAttributeNS(attUri, attName, value);
/* 175:    */     }
/* 176:216 */     appendDOMTree(domDocument, domElement, element.content());
/* 177:    */     
/* 178:218 */     domCurrent.appendChild(domElement);
/* 179:220 */     while (this.namespaceStack.size() > stackSize) {
/* 180:221 */       this.namespaceStack.pop();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, CDATA cdata)
/* 185:    */   {
/* 186:227 */     CDATASection domCDATA = domDocument.createCDATASection(cdata.getText());
/* 187:    */     
/* 188:229 */     domCurrent.appendChild(domCDATA);
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, org.dom4j.Comment comment)
/* 192:    */   {
/* 193:234 */     org.w3c.dom.Comment domComment = domDocument.createComment(comment.getText());
/* 194:    */     
/* 195:236 */     domCurrent.appendChild(domComment);
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, String text)
/* 199:    */   {
/* 200:241 */     org.w3c.dom.Text domText = domDocument.createTextNode(text);
/* 201:242 */     domCurrent.appendChild(domText);
/* 202:    */   }
/* 203:    */   
/* 204:    */   protected void appendDOMTree(org.w3c.dom.Document domDocument, Node domCurrent, Entity entity)
/* 205:    */   {
/* 206:247 */     EntityReference domEntity = domDocument.createEntityReference(entity.getName());
/* 207:    */     
/* 208:249 */     domCurrent.appendChild(domEntity);
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void appendDOMTree(org.w3c.dom.Document domDoc, Node domCurrent, org.dom4j.ProcessingInstruction pi)
/* 212:    */   {
/* 213:254 */     org.w3c.dom.ProcessingInstruction domPI = domDoc.createProcessingInstruction(pi.getTarget(), pi.getText());
/* 214:    */     
/* 215:256 */     domCurrent.appendChild(domPI);
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void writeNamespace(org.w3c.dom.Element domElement, Namespace namespace)
/* 219:    */   {
/* 220:261 */     String attributeName = attributeNameForNamespace(namespace);
/* 221:    */     
/* 222:    */ 
/* 223:264 */     domElement.setAttribute(attributeName, namespace.getURI());
/* 224:    */   }
/* 225:    */   
/* 226:    */   protected String attributeNameForNamespace(Namespace namespace)
/* 227:    */   {
/* 228:268 */     String xmlns = "xmlns";
/* 229:269 */     String prefix = namespace.getPrefix();
/* 230:271 */     if (prefix.length() > 0) {
/* 231:272 */       return xmlns + ":" + prefix;
/* 232:    */     }
/* 233:275 */     return xmlns;
/* 234:    */   }
/* 235:    */   
/* 236:    */   protected org.w3c.dom.Document createDomDocument(org.dom4j.Document document)
/* 237:    */     throws DocumentException
/* 238:    */   {
/* 239:280 */     org.w3c.dom.Document result = null;
/* 240:283 */     if (this.domDocumentClass != null)
/* 241:    */     {
/* 242:    */       try
/* 243:    */       {
/* 244:285 */         result = (org.w3c.dom.Document)this.domDocumentClass.newInstance();
/* 245:    */       }
/* 246:    */       catch (Exception e)
/* 247:    */       {
/* 248:287 */         throw new DocumentException("Could not instantiate an instance of DOM Document with class: " + this.domDocumentClass.getName(), e);
/* 249:    */       }
/* 250:    */     }
/* 251:    */     else
/* 252:    */     {
/* 253:294 */       result = createDomDocumentViaJAXP();
/* 254:296 */       if (result == null)
/* 255:    */       {
/* 256:297 */         Class theClass = getDomDocumentClass();
/* 257:    */         try
/* 258:    */         {
/* 259:300 */           result = (org.w3c.dom.Document)theClass.newInstance();
/* 260:    */         }
/* 261:    */         catch (Exception e)
/* 262:    */         {
/* 263:302 */           throw new DocumentException("Could not instantiate an instance of DOM Document with class: " + theClass.getName(), e);
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:309 */     return result;
/* 268:    */   }
/* 269:    */   
/* 270:    */   protected org.w3c.dom.Document createDomDocumentViaJAXP()
/* 271:    */     throws DocumentException
/* 272:    */   {
/* 273:    */     try
/* 274:    */     {
/* 275:315 */       return JAXPHelper.createDocument(false, true);
/* 276:    */     }
/* 277:    */     catch (Throwable e)
/* 278:    */     {
/* 279:317 */       if (!loggedWarning)
/* 280:    */       {
/* 281:318 */         loggedWarning = true;
/* 282:320 */         if (SAXHelper.isVerboseErrorReporting())
/* 283:    */         {
/* 284:323 */           System.out.println("Warning: Caught exception attempting to use JAXP to create a W3C DOM document");
/* 285:    */           
/* 286:325 */           System.out.println("Warning: Exception was: " + e);
/* 287:326 */           e.printStackTrace();
/* 288:    */         }
/* 289:    */         else
/* 290:    */         {
/* 291:328 */           System.out.println("Warning: Error occurred using JAXP to create a DOM document.");
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:334 */     return null;
/* 296:    */   }
/* 297:    */   
/* 298:    */   protected org.w3c.dom.Document createDomDocument(org.dom4j.Document document, DOMImplementation domImpl)
/* 299:    */     throws DocumentException
/* 300:    */   {
/* 301:339 */     String namespaceURI = null;
/* 302:340 */     String qualifiedName = null;
/* 303:341 */     DocumentType docType = null;
/* 304:    */     
/* 305:343 */     return domImpl.createDocument(namespaceURI, qualifiedName, docType);
/* 306:    */   }
/* 307:    */   
/* 308:    */   protected boolean isNamespaceDeclaration(Namespace ns)
/* 309:    */   {
/* 310:347 */     if ((ns != null) && (ns != Namespace.NO_NAMESPACE) && (ns != Namespace.XML_NAMESPACE))
/* 311:    */     {
/* 312:349 */       String uri = ns.getURI();
/* 313:351 */       if ((uri != null) && (uri.length() > 0) && 
/* 314:352 */         (!this.namespaceStack.contains(ns))) {
/* 315:353 */         return true;
/* 316:    */       }
/* 317:    */     }
/* 318:358 */     return false;
/* 319:    */   }
/* 320:    */   
/* 321:    */   protected void resetNamespaceStack()
/* 322:    */   {
/* 323:362 */     this.namespaceStack.clear();
/* 324:363 */     this.namespaceStack.push(Namespace.XML_NAMESPACE);
/* 325:    */   }
/* 326:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DOMWriter
 * JD-Core Version:    0.7.0.1
 */