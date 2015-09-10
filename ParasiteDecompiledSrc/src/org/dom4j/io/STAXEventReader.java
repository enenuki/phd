/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import javax.xml.stream.XMLEventReader;
/*   7:    */ import javax.xml.stream.XMLInputFactory;
/*   8:    */ import javax.xml.stream.XMLStreamException;
/*   9:    */ import javax.xml.stream.events.Characters;
/*  10:    */ import javax.xml.stream.events.EndElement;
/*  11:    */ import javax.xml.stream.events.EntityDeclaration;
/*  12:    */ import javax.xml.stream.events.EntityReference;
/*  13:    */ import javax.xml.stream.events.StartDocument;
/*  14:    */ import javax.xml.stream.events.StartElement;
/*  15:    */ import javax.xml.stream.events.XMLEvent;
/*  16:    */ import org.dom4j.CharacterData;
/*  17:    */ import org.dom4j.Document;
/*  18:    */ import org.dom4j.DocumentFactory;
/*  19:    */ import org.dom4j.Element;
/*  20:    */ import org.dom4j.Entity;
/*  21:    */ import org.dom4j.Node;
/*  22:    */ 
/*  23:    */ public class STAXEventReader
/*  24:    */ {
/*  25:    */   private DocumentFactory factory;
/*  26: 48 */   private XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/*  27:    */   
/*  28:    */   public STAXEventReader()
/*  29:    */   {
/*  30: 55 */     this.factory = DocumentFactory.getInstance();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public STAXEventReader(DocumentFactory factory)
/*  34:    */   {
/*  35: 67 */     if (factory != null) {
/*  36: 68 */       this.factory = factory;
/*  37:    */     } else {
/*  38: 70 */       this.factory = DocumentFactory.getInstance();
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/*  43:    */   {
/*  44: 82 */     if (documentFactory != null) {
/*  45: 83 */       this.factory = documentFactory;
/*  46:    */     } else {
/*  47: 85 */       this.factory = DocumentFactory.getInstance();
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Document readDocument(InputStream is)
/*  52:    */     throws XMLStreamException
/*  53:    */   {
/*  54:102 */     return readDocument(is, null);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Document readDocument(Reader reader)
/*  58:    */     throws XMLStreamException
/*  59:    */   {
/*  60:118 */     return readDocument(reader, null);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Document readDocument(InputStream is, String systemId)
/*  64:    */     throws XMLStreamException
/*  65:    */   {
/*  66:137 */     XMLEventReader eventReader = this.inputFactory.createXMLEventReader(systemId, is);
/*  67:    */     try
/*  68:    */     {
/*  69:141 */       return readDocument(eventReader);
/*  70:    */     }
/*  71:    */     finally
/*  72:    */     {
/*  73:143 */       eventReader.close();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Document readDocument(Reader reader, String systemId)
/*  78:    */     throws XMLStreamException
/*  79:    */   {
/*  80:163 */     XMLEventReader eventReader = this.inputFactory.createXMLEventReader(systemId, reader);
/*  81:    */     try
/*  82:    */     {
/*  83:167 */       return readDocument(eventReader);
/*  84:    */     }
/*  85:    */     finally
/*  86:    */     {
/*  87:169 */       eventReader.close();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Node readNode(XMLEventReader reader)
/*  92:    */     throws XMLStreamException
/*  93:    */   {
/*  94:195 */     XMLEvent event = reader.peek();
/*  95:197 */     if (event.isStartElement()) {
/*  96:198 */       return readElement(reader);
/*  97:    */     }
/*  98:199 */     if (event.isCharacters()) {
/*  99:200 */       return readCharacters(reader);
/* 100:    */     }
/* 101:201 */     if (event.isStartDocument()) {
/* 102:202 */       return readDocument(reader);
/* 103:    */     }
/* 104:203 */     if (event.isProcessingInstruction()) {
/* 105:204 */       return readProcessingInstruction(reader);
/* 106:    */     }
/* 107:205 */     if (event.isEntityReference()) {
/* 108:206 */       return readEntityReference(reader);
/* 109:    */     }
/* 110:207 */     if (event.isAttribute()) {
/* 111:208 */       return readAttribute(reader);
/* 112:    */     }
/* 113:209 */     if (event.isNamespace()) {
/* 114:210 */       return readNamespace(reader);
/* 115:    */     }
/* 116:212 */     throw new XMLStreamException("Unsupported event: " + event);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Document readDocument(XMLEventReader reader)
/* 120:    */     throws XMLStreamException
/* 121:    */   {
/* 122:231 */     Document doc = null;
/* 123:233 */     while (reader.hasNext())
/* 124:    */     {
/* 125:234 */       XMLEvent nextEvent = reader.peek();
/* 126:235 */       int type = nextEvent.getEventType();
/* 127:237 */       switch (type)
/* 128:    */       {
/* 129:    */       case 7: 
/* 130:240 */         StartDocument event = (StartDocument)reader.nextEvent();
/* 131:242 */         if (doc == null)
/* 132:    */         {
/* 133:244 */           if (event.encodingSet())
/* 134:    */           {
/* 135:245 */             String encodingScheme = event.getCharacterEncodingScheme();
/* 136:    */             
/* 137:247 */             doc = this.factory.createDocument(encodingScheme);
/* 138:    */           }
/* 139:    */           else
/* 140:    */           {
/* 141:249 */             doc = this.factory.createDocument();
/* 142:    */           }
/* 143:    */         }
/* 144:    */         else
/* 145:    */         {
/* 146:253 */           String msg = "Unexpected StartDocument event";
/* 147:254 */           throw new XMLStreamException(msg, event.getLocation());
/* 148:    */         }
/* 149:    */         break;
/* 150:    */       case 4: 
/* 151:    */       case 6: 
/* 152:    */       case 8: 
/* 153:264 */         reader.nextEvent();
/* 154:    */         
/* 155:266 */         break;
/* 156:    */       case 5: 
/* 157:    */       default: 
/* 158:270 */         if (doc == null) {
/* 159:272 */           doc = this.factory.createDocument();
/* 160:    */         }
/* 161:275 */         Node n = readNode(reader);
/* 162:276 */         doc.add(n);
/* 163:    */       }
/* 164:    */     }
/* 165:280 */     return doc;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Element readElement(XMLEventReader eventReader)
/* 169:    */     throws XMLStreamException
/* 170:    */   {
/* 171:300 */     XMLEvent event = eventReader.peek();
/* 172:302 */     if (event.isStartElement())
/* 173:    */     {
/* 174:304 */       StartElement startTag = eventReader.nextEvent().asStartElement();
/* 175:305 */       Element elem = createElement(startTag);
/* 176:    */       for (;;)
/* 177:    */       {
/* 178:309 */         if (!eventReader.hasNext())
/* 179:    */         {
/* 180:310 */           String msg = "Unexpected end of stream while reading element content";
/* 181:    */           
/* 182:312 */           throw new XMLStreamException(msg);
/* 183:    */         }
/* 184:315 */         XMLEvent nextEvent = eventReader.peek();
/* 185:317 */         if (nextEvent.isEndElement())
/* 186:    */         {
/* 187:318 */           EndElement endElem = eventReader.nextEvent().asEndElement();
/* 188:320 */           if (endElem.getName().equals(startTag.getName())) {
/* 189:    */             break;
/* 190:    */           }
/* 191:321 */           throw new XMLStreamException("Expected " + startTag.getName() + " end-tag, but found" + endElem.getName());
/* 192:    */         }
/* 193:329 */         Node child = readNode(eventReader);
/* 194:330 */         elem.add(child);
/* 195:    */       }
/* 196:333 */       return elem;
/* 197:    */     }
/* 198:335 */     throw new XMLStreamException("Expected Element event, found: " + event);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public org.dom4j.Attribute readAttribute(XMLEventReader reader)
/* 202:    */     throws XMLStreamException
/* 203:    */   {
/* 204:355 */     XMLEvent event = reader.peek();
/* 205:357 */     if (event.isAttribute())
/* 206:    */     {
/* 207:358 */       javax.xml.stream.events.Attribute attr = (javax.xml.stream.events.Attribute)reader.nextEvent();
/* 208:    */       
/* 209:360 */       return createAttribute(null, attr);
/* 210:    */     }
/* 211:362 */     throw new XMLStreamException("Expected Attribute event, found: " + event);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public org.dom4j.Namespace readNamespace(XMLEventReader reader)
/* 215:    */     throws XMLStreamException
/* 216:    */   {
/* 217:382 */     XMLEvent event = reader.peek();
/* 218:384 */     if (event.isNamespace())
/* 219:    */     {
/* 220:385 */       javax.xml.stream.events.Namespace ns = (javax.xml.stream.events.Namespace)reader.nextEvent();
/* 221:    */       
/* 222:387 */       return createNamespace(ns);
/* 223:    */     }
/* 224:389 */     throw new XMLStreamException("Expected Namespace event, found: " + event);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public CharacterData readCharacters(XMLEventReader reader)
/* 228:    */     throws XMLStreamException
/* 229:    */   {
/* 230:409 */     XMLEvent event = reader.peek();
/* 231:411 */     if (event.isCharacters())
/* 232:    */     {
/* 233:412 */       Characters characters = reader.nextEvent().asCharacters();
/* 234:    */       
/* 235:414 */       return createCharacterData(characters);
/* 236:    */     }
/* 237:416 */     throw new XMLStreamException("Expected Characters event, found: " + event);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public org.dom4j.Comment readComment(XMLEventReader reader)
/* 241:    */     throws XMLStreamException
/* 242:    */   {
/* 243:436 */     XMLEvent event = reader.peek();
/* 244:438 */     if ((event instanceof javax.xml.stream.events.Comment)) {
/* 245:439 */       return createComment((javax.xml.stream.events.Comment)reader.nextEvent());
/* 246:    */     }
/* 247:441 */     throw new XMLStreamException("Expected Comment event, found: " + event);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Entity readEntityReference(XMLEventReader reader)
/* 251:    */     throws XMLStreamException
/* 252:    */   {
/* 253:463 */     XMLEvent event = reader.peek();
/* 254:465 */     if (event.isEntityReference())
/* 255:    */     {
/* 256:466 */       EntityReference entityRef = (EntityReference)reader.nextEvent();
/* 257:    */       
/* 258:468 */       return createEntity(entityRef);
/* 259:    */     }
/* 260:470 */     throw new XMLStreamException("Expected EntityRef event, found: " + event);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public org.dom4j.ProcessingInstruction readProcessingInstruction(XMLEventReader reader)
/* 264:    */     throws XMLStreamException
/* 265:    */   {
/* 266:492 */     XMLEvent event = reader.peek();
/* 267:494 */     if (event.isProcessingInstruction())
/* 268:    */     {
/* 269:495 */       javax.xml.stream.events.ProcessingInstruction pi = (javax.xml.stream.events.ProcessingInstruction)reader.nextEvent();
/* 270:    */       
/* 271:    */ 
/* 272:498 */       return createProcessingInstruction(pi);
/* 273:    */     }
/* 274:500 */     throw new XMLStreamException("Expected PI event, found: " + event);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Element createElement(StartElement startEvent)
/* 278:    */   {
/* 279:515 */     javax.xml.namespace.QName qname = startEvent.getName();
/* 280:516 */     org.dom4j.QName elemName = createQName(qname);
/* 281:    */     
/* 282:518 */     Element elem = this.factory.createElement(elemName);
/* 283:521 */     for (Iterator i = startEvent.getAttributes(); i.hasNext();)
/* 284:    */     {
/* 285:522 */       javax.xml.stream.events.Attribute attr = (javax.xml.stream.events.Attribute)i.next();
/* 286:523 */       elem.addAttribute(createQName(attr.getName()), attr.getValue());
/* 287:    */     }
/* 288:527 */     for (Iterator i = startEvent.getNamespaces(); i.hasNext();)
/* 289:    */     {
/* 290:528 */       javax.xml.stream.events.Namespace ns = (javax.xml.stream.events.Namespace)i.next();
/* 291:529 */       elem.addNamespace(ns.getPrefix(), ns.getNamespaceURI());
/* 292:    */     }
/* 293:532 */     return elem;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public org.dom4j.Attribute createAttribute(Element elem, javax.xml.stream.events.Attribute attr)
/* 297:    */   {
/* 298:547 */     return this.factory.createAttribute(elem, createQName(attr.getName()), attr.getValue());
/* 299:    */   }
/* 300:    */   
/* 301:    */   public org.dom4j.Namespace createNamespace(javax.xml.stream.events.Namespace ns)
/* 302:    */   {
/* 303:561 */     return this.factory.createNamespace(ns.getPrefix(), ns.getNamespaceURI());
/* 304:    */   }
/* 305:    */   
/* 306:    */   public CharacterData createCharacterData(Characters characters)
/* 307:    */   {
/* 308:576 */     String data = characters.getData();
/* 309:578 */     if (characters.isCData()) {
/* 310:579 */       return this.factory.createCDATA(data);
/* 311:    */     }
/* 312:581 */     return this.factory.createText(data);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public org.dom4j.Comment createComment(javax.xml.stream.events.Comment comment)
/* 316:    */   {
/* 317:595 */     return this.factory.createComment(comment.getText());
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Entity createEntity(EntityReference entityRef)
/* 321:    */   {
/* 322:609 */     return this.factory.createEntity(entityRef.getName(), entityRef.getDeclaration().getReplacementText());
/* 323:    */   }
/* 324:    */   
/* 325:    */   public org.dom4j.ProcessingInstruction createProcessingInstruction(javax.xml.stream.events.ProcessingInstruction pi)
/* 326:    */   {
/* 327:626 */     return this.factory.createProcessingInstruction(pi.getTarget(), pi.getData());
/* 328:    */   }
/* 329:    */   
/* 330:    */   public org.dom4j.QName createQName(javax.xml.namespace.QName qname)
/* 331:    */   {
/* 332:639 */     return this.factory.createQName(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI());
/* 333:    */   }
/* 334:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.STAXEventReader
 * JD-Core Version:    0.7.0.1
 */