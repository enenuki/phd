/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.StringWriter;
/*   8:    */ import java.io.Writer;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import javax.xml.stream.XMLEventFactory;
/*  12:    */ import javax.xml.stream.XMLOutputFactory;
/*  13:    */ import javax.xml.stream.XMLStreamException;
/*  14:    */ import javax.xml.stream.events.Characters;
/*  15:    */ import javax.xml.stream.events.DTD;
/*  16:    */ import javax.xml.stream.events.EndDocument;
/*  17:    */ import javax.xml.stream.events.EndElement;
/*  18:    */ import javax.xml.stream.events.EntityReference;
/*  19:    */ import javax.xml.stream.events.StartDocument;
/*  20:    */ import javax.xml.stream.events.StartElement;
/*  21:    */ import javax.xml.stream.util.XMLEventConsumer;
/*  22:    */ import org.dom4j.Branch;
/*  23:    */ import org.dom4j.CDATA;
/*  24:    */ import org.dom4j.Document;
/*  25:    */ import org.dom4j.DocumentType;
/*  26:    */ import org.dom4j.Element;
/*  27:    */ import org.dom4j.Entity;
/*  28:    */ import org.dom4j.Node;
/*  29:    */ import org.dom4j.Text;
/*  30:    */ 
/*  31:    */ public class STAXEventWriter
/*  32:    */ {
/*  33:    */   private XMLEventConsumer consumer;
/*  34: 56 */   private XMLEventFactory factory = XMLEventFactory.newInstance();
/*  35: 58 */   private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
/*  36:    */   
/*  37:    */   public STAXEventWriter() {}
/*  38:    */   
/*  39:    */   public STAXEventWriter(File file)
/*  40:    */     throws XMLStreamException, IOException
/*  41:    */   {
/*  42: 76 */     this.consumer = this.outputFactory.createXMLEventWriter(new FileWriter(file));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public STAXEventWriter(Writer writer)
/*  46:    */     throws XMLStreamException
/*  47:    */   {
/*  48: 91 */     this.consumer = this.outputFactory.createXMLEventWriter(writer);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public STAXEventWriter(OutputStream stream)
/*  52:    */     throws XMLStreamException
/*  53:    */   {
/*  54:106 */     this.consumer = this.outputFactory.createXMLEventWriter(stream);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public STAXEventWriter(XMLEventConsumer consumer)
/*  58:    */   {
/*  59:117 */     this.consumer = consumer;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public XMLEventConsumer getConsumer()
/*  63:    */   {
/*  64:127 */     return this.consumer;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setConsumer(XMLEventConsumer consumer)
/*  68:    */   {
/*  69:137 */     this.consumer = consumer;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public XMLEventFactory getEventFactory()
/*  73:    */   {
/*  74:146 */     return this.factory;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setEventFactory(XMLEventFactory eventFactory)
/*  78:    */   {
/*  79:156 */     this.factory = eventFactory;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void writeNode(Node n)
/*  83:    */     throws XMLStreamException
/*  84:    */   {
/*  85:170 */     switch (n.getNodeType())
/*  86:    */     {
/*  87:    */     case 1: 
/*  88:172 */       writeElement((Element)n);
/*  89:    */       
/*  90:174 */       break;
/*  91:    */     case 3: 
/*  92:177 */       writeText((Text)n);
/*  93:    */       
/*  94:179 */       break;
/*  95:    */     case 2: 
/*  96:182 */       writeAttribute((org.dom4j.Attribute)n);
/*  97:    */       
/*  98:184 */       break;
/*  99:    */     case 13: 
/* 100:187 */       writeNamespace((org.dom4j.Namespace)n);
/* 101:    */       
/* 102:189 */       break;
/* 103:    */     case 8: 
/* 104:192 */       writeComment((org.dom4j.Comment)n);
/* 105:    */       
/* 106:194 */       break;
/* 107:    */     case 4: 
/* 108:197 */       writeCDATA((CDATA)n);
/* 109:    */       
/* 110:199 */       break;
/* 111:    */     case 7: 
/* 112:202 */       writeProcessingInstruction((org.dom4j.ProcessingInstruction)n);
/* 113:    */       
/* 114:204 */       break;
/* 115:    */     case 5: 
/* 116:207 */       writeEntity((Entity)n);
/* 117:    */       
/* 118:209 */       break;
/* 119:    */     case 9: 
/* 120:212 */       writeDocument((Document)n);
/* 121:    */       
/* 122:214 */       break;
/* 123:    */     case 10: 
/* 124:217 */       writeDocumentType((DocumentType)n);
/* 125:    */       
/* 126:219 */       break;
/* 127:    */     case 6: 
/* 128:    */     case 11: 
/* 129:    */     case 12: 
/* 130:    */     default: 
/* 131:222 */       throw new XMLStreamException("Unsupported DOM4J Node: " + n);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void writeChildNodes(Branch branch)
/* 136:    */     throws XMLStreamException
/* 137:    */   {
/* 138:238 */     int i = 0;
/* 139:238 */     for (int s = branch.nodeCount(); i < s; i++)
/* 140:    */     {
/* 141:239 */       Node n = branch.node(i);
/* 142:240 */       writeNode(n);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void writeElement(Element elem)
/* 147:    */     throws XMLStreamException
/* 148:    */   {
/* 149:254 */     this.consumer.add(createStartElement(elem));
/* 150:255 */     writeChildNodes(elem);
/* 151:256 */     this.consumer.add(createEndElement(elem));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public StartElement createStartElement(Element elem)
/* 155:    */   {
/* 156:270 */     javax.xml.namespace.QName tagName = createQName(elem.getQName());
/* 157:    */     
/* 158:    */ 
/* 159:273 */     Iterator attrIter = new AttributeIterator(elem.attributeIterator());
/* 160:274 */     Iterator nsIter = new NamespaceIterator(elem.declaredNamespaces().iterator());
/* 161:    */     
/* 162:    */ 
/* 163:    */ 
/* 164:278 */     return this.factory.createStartElement(tagName, attrIter, nsIter);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public EndElement createEndElement(Element elem)
/* 168:    */   {
/* 169:290 */     javax.xml.namespace.QName tagName = createQName(elem.getQName());
/* 170:291 */     Iterator nsIter = new NamespaceIterator(elem.declaredNamespaces().iterator());
/* 171:    */     
/* 172:    */ 
/* 173:294 */     return this.factory.createEndElement(tagName, nsIter);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void writeAttribute(org.dom4j.Attribute attr)
/* 177:    */     throws XMLStreamException
/* 178:    */   {
/* 179:307 */     this.consumer.add(createAttribute(attr));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public javax.xml.stream.events.Attribute createAttribute(org.dom4j.Attribute attr)
/* 183:    */   {
/* 184:321 */     javax.xml.namespace.QName attrName = createQName(attr.getQName());
/* 185:322 */     String value = attr.getValue();
/* 186:    */     
/* 187:324 */     return this.factory.createAttribute(attrName, value);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void writeNamespace(org.dom4j.Namespace ns)
/* 191:    */     throws XMLStreamException
/* 192:    */   {
/* 193:337 */     this.consumer.add(createNamespace(ns));
/* 194:    */   }
/* 195:    */   
/* 196:    */   public javax.xml.stream.events.Namespace createNamespace(org.dom4j.Namespace ns)
/* 197:    */   {
/* 198:350 */     String prefix = ns.getPrefix();
/* 199:351 */     String uri = ns.getURI();
/* 200:    */     
/* 201:353 */     return this.factory.createNamespace(prefix, uri);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void writeText(Text text)
/* 205:    */     throws XMLStreamException
/* 206:    */   {
/* 207:366 */     this.consumer.add(createCharacters(text));
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Characters createCharacters(Text text)
/* 211:    */   {
/* 212:378 */     return this.factory.createCharacters(text.getText());
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void writeCDATA(CDATA cdata)
/* 216:    */     throws XMLStreamException
/* 217:    */   {
/* 218:391 */     this.consumer.add(createCharacters(cdata));
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Characters createCharacters(CDATA cdata)
/* 222:    */   {
/* 223:403 */     return this.factory.createCData(cdata.getText());
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void writeComment(org.dom4j.Comment comment)
/* 227:    */     throws XMLStreamException
/* 228:    */   {
/* 229:416 */     this.consumer.add(createComment(comment));
/* 230:    */   }
/* 231:    */   
/* 232:    */   public javax.xml.stream.events.Comment createComment(org.dom4j.Comment comment)
/* 233:    */   {
/* 234:429 */     return this.factory.createComment(comment.getText());
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void writeProcessingInstruction(org.dom4j.ProcessingInstruction pi)
/* 238:    */     throws XMLStreamException
/* 239:    */   {
/* 240:443 */     this.consumer.add(createProcessingInstruction(pi));
/* 241:    */   }
/* 242:    */   
/* 243:    */   public javax.xml.stream.events.ProcessingInstruction createProcessingInstruction(org.dom4j.ProcessingInstruction pi)
/* 244:    */   {
/* 245:459 */     String target = pi.getTarget();
/* 246:460 */     String data = pi.getText();
/* 247:    */     
/* 248:462 */     return this.factory.createProcessingInstruction(target, data);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void writeEntity(Entity entity)
/* 252:    */     throws XMLStreamException
/* 253:    */   {
/* 254:475 */     this.consumer.add(createEntityReference(entity));
/* 255:    */   }
/* 256:    */   
/* 257:    */   private EntityReference createEntityReference(Entity entity)
/* 258:    */   {
/* 259:488 */     return this.factory.createEntityReference(entity.getName(), null);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void writeDocumentType(DocumentType docType)
/* 263:    */     throws XMLStreamException
/* 264:    */   {
/* 265:502 */     this.consumer.add(createDTD(docType));
/* 266:    */   }
/* 267:    */   
/* 268:    */   public DTD createDTD(DocumentType docType)
/* 269:    */   {
/* 270:517 */     StringWriter decl = new StringWriter();
/* 271:    */     try
/* 272:    */     {
/* 273:520 */       docType.write(decl);
/* 274:    */     }
/* 275:    */     catch (IOException e)
/* 276:    */     {
/* 277:522 */       throw new RuntimeException("Error writing DTD", e);
/* 278:    */     }
/* 279:525 */     return this.factory.createDTD(decl.toString());
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void writeDocument(Document doc)
/* 283:    */     throws XMLStreamException
/* 284:    */   {
/* 285:539 */     this.consumer.add(createStartDocument(doc));
/* 286:    */     
/* 287:541 */     writeChildNodes(doc);
/* 288:    */     
/* 289:543 */     this.consumer.add(createEndDocument(doc));
/* 290:    */   }
/* 291:    */   
/* 292:    */   public StartDocument createStartDocument(Document doc)
/* 293:    */   {
/* 294:556 */     String encoding = doc.getXMLEncoding();
/* 295:558 */     if (encoding != null) {
/* 296:559 */       return this.factory.createStartDocument(encoding);
/* 297:    */     }
/* 298:561 */     return this.factory.createStartDocument();
/* 299:    */   }
/* 300:    */   
/* 301:    */   public EndDocument createEndDocument(Document doc)
/* 302:    */   {
/* 303:575 */     return this.factory.createEndDocument();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public javax.xml.namespace.QName createQName(org.dom4j.QName qname)
/* 307:    */   {
/* 308:588 */     return new javax.xml.namespace.QName(qname.getNamespaceURI(), qname.getName(), qname.getNamespacePrefix());
/* 309:    */   }
/* 310:    */   
/* 311:    */   private class AttributeIterator
/* 312:    */     implements Iterator
/* 313:    */   {
/* 314:    */     private Iterator iter;
/* 315:    */     
/* 316:    */     public AttributeIterator(Iterator iter)
/* 317:    */     {
/* 318:601 */       this.iter = iter;
/* 319:    */     }
/* 320:    */     
/* 321:    */     public boolean hasNext()
/* 322:    */     {
/* 323:605 */       return this.iter.hasNext();
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Object next()
/* 327:    */     {
/* 328:609 */       org.dom4j.Attribute attr = (org.dom4j.Attribute)this.iter.next();
/* 329:610 */       javax.xml.namespace.QName attrName = STAXEventWriter.this.createQName(attr.getQName());
/* 330:611 */       String value = attr.getValue();
/* 331:    */       
/* 332:613 */       return STAXEventWriter.this.factory.createAttribute(attrName, value);
/* 333:    */     }
/* 334:    */     
/* 335:    */     public void remove()
/* 336:    */     {
/* 337:617 */       throw new UnsupportedOperationException();
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   private class NamespaceIterator
/* 342:    */     implements Iterator
/* 343:    */   {
/* 344:    */     private Iterator iter;
/* 345:    */     
/* 346:    */     public NamespaceIterator(Iterator iter)
/* 347:    */     {
/* 348:629 */       this.iter = iter;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public boolean hasNext()
/* 352:    */     {
/* 353:633 */       return this.iter.hasNext();
/* 354:    */     }
/* 355:    */     
/* 356:    */     public Object next()
/* 357:    */     {
/* 358:637 */       org.dom4j.Namespace ns = (org.dom4j.Namespace)this.iter.next();
/* 359:638 */       String prefix = ns.getPrefix();
/* 360:639 */       String nsURI = ns.getURI();
/* 361:    */       
/* 362:641 */       return STAXEventWriter.this.factory.createNamespace(prefix, nsURI);
/* 363:    */     }
/* 364:    */     
/* 365:    */     public void remove()
/* 366:    */     {
/* 367:645 */       throw new UnsupportedOperationException();
/* 368:    */     }
/* 369:    */   }
/* 370:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.STAXEventWriter
 * JD-Core Version:    0.7.0.1
 */