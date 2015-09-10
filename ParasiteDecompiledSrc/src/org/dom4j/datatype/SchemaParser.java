/*   1:    */ package org.dom4j.datatype;
/*   2:    */ 
/*   3:    */ import com.sun.msv.datatype.xsd.DatatypeFactory;
/*   4:    */ import com.sun.msv.datatype.xsd.TypeIncubator;
/*   5:    */ import com.sun.msv.datatype.xsd.XSDatatype;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.Attribute;
/*  11:    */ import org.dom4j.Document;
/*  12:    */ import org.dom4j.DocumentFactory;
/*  13:    */ import org.dom4j.Element;
/*  14:    */ import org.dom4j.Namespace;
/*  15:    */ import org.dom4j.QName;
/*  16:    */ import org.dom4j.io.SAXReader;
/*  17:    */ import org.dom4j.util.AttributeHelper;
/*  18:    */ import org.relaxng.datatype.DatatypeException;
/*  19:    */ import org.relaxng.datatype.ValidationContext;
/*  20:    */ import org.xml.sax.EntityResolver;
/*  21:    */ import org.xml.sax.InputSource;
/*  22:    */ 
/*  23:    */ public class SchemaParser
/*  24:    */ {
/*  25: 43 */   private static final Namespace XSD_NAMESPACE = Namespace.get("xsd", "http://www.w3.org/2001/XMLSchema");
/*  26: 47 */   private static final QName XSD_ELEMENT = QName.get("element", XSD_NAMESPACE);
/*  27: 50 */   private static final QName XSD_ATTRIBUTE = QName.get("attribute", XSD_NAMESPACE);
/*  28: 53 */   private static final QName XSD_SIMPLETYPE = QName.get("simpleType", XSD_NAMESPACE);
/*  29: 56 */   private static final QName XSD_COMPLEXTYPE = QName.get("complexType", XSD_NAMESPACE);
/*  30: 59 */   private static final QName XSD_RESTRICTION = QName.get("restriction", XSD_NAMESPACE);
/*  31: 62 */   private static final QName XSD_SEQUENCE = QName.get("sequence", XSD_NAMESPACE);
/*  32: 65 */   private static final QName XSD_CHOICE = QName.get("choice", XSD_NAMESPACE);
/*  33: 67 */   private static final QName XSD_ALL = QName.get("all", XSD_NAMESPACE);
/*  34: 69 */   private static final QName XSD_INCLUDE = QName.get("include", XSD_NAMESPACE);
/*  35:    */   private DatatypeDocumentFactory documentFactory;
/*  36: 79 */   private Map dataTypeCache = new HashMap();
/*  37:    */   private NamedTypeResolver namedTypeResolver;
/*  38:    */   private Namespace targetNamespace;
/*  39:    */   
/*  40:    */   public SchemaParser()
/*  41:    */   {
/*  42: 88 */     this(DatatypeDocumentFactory.singleton);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public SchemaParser(DatatypeDocumentFactory documentFactory)
/*  46:    */   {
/*  47: 92 */     this.documentFactory = documentFactory;
/*  48: 93 */     this.namedTypeResolver = new NamedTypeResolver(documentFactory);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void build(Document schemaDocument)
/*  52:    */   {
/*  53:103 */     this.targetNamespace = null;
/*  54:104 */     internalBuild(schemaDocument);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void build(Document schemaDocument, Namespace namespace)
/*  58:    */   {
/*  59:108 */     this.targetNamespace = namespace;
/*  60:109 */     internalBuild(schemaDocument);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private synchronized void internalBuild(Document schemaDocument)
/*  64:    */   {
/*  65:113 */     Element root = schemaDocument.getRootElement();
/*  66:115 */     if (root != null)
/*  67:    */     {
/*  68:117 */       Iterator includeIter = root.elementIterator(XSD_INCLUDE);
/*  69:119 */       while (includeIter.hasNext())
/*  70:    */       {
/*  71:120 */         Element includeElement = (Element)includeIter.next();
/*  72:121 */         String inclSchemaInstanceURI = includeElement.attributeValue("schemaLocation");
/*  73:    */         
/*  74:123 */         EntityResolver resolver = schemaDocument.getEntityResolver();
/*  75:    */         try
/*  76:    */         {
/*  77:126 */           if (resolver == null)
/*  78:    */           {
/*  79:127 */             String msg = "No EntityResolver available";
/*  80:128 */             throw new InvalidSchemaException(msg);
/*  81:    */           }
/*  82:131 */           InputSource inputSource = resolver.resolveEntity(null, inclSchemaInstanceURI);
/*  83:134 */           if (inputSource == null)
/*  84:    */           {
/*  85:135 */             String msg = "Could not resolve the schema URI: " + inclSchemaInstanceURI;
/*  86:    */             
/*  87:137 */             throw new InvalidSchemaException(msg);
/*  88:    */           }
/*  89:140 */           SAXReader reader = new SAXReader();
/*  90:141 */           Document inclSchemaDocument = reader.read(inputSource);
/*  91:142 */           build(inclSchemaDocument);
/*  92:    */         }
/*  93:    */         catch (Exception e)
/*  94:    */         {
/*  95:144 */           System.out.println("Failed to load schema: " + inclSchemaInstanceURI);
/*  96:    */           
/*  97:146 */           System.out.println("Caught: " + e);
/*  98:147 */           e.printStackTrace();
/*  99:148 */           throw new InvalidSchemaException("Failed to load schema: " + inclSchemaInstanceURI);
/* 100:    */         }
/* 101:    */       }
/* 102:154 */       Iterator iter = root.elementIterator(XSD_ELEMENT);
/* 103:156 */       while (iter.hasNext()) {
/* 104:157 */         onDatatypeElement((Element)iter.next(), this.documentFactory);
/* 105:    */       }
/* 106:161 */       iter = root.elementIterator(XSD_SIMPLETYPE);
/* 107:163 */       while (iter.hasNext()) {
/* 108:164 */         onNamedSchemaSimpleType((Element)iter.next());
/* 109:    */       }
/* 110:168 */       iter = root.elementIterator(XSD_COMPLEXTYPE);
/* 111:170 */       while (iter.hasNext()) {
/* 112:171 */         onNamedSchemaComplexType((Element)iter.next());
/* 113:    */       }
/* 114:174 */       this.namedTypeResolver.resolveNamedTypes();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void onDatatypeElement(Element xsdElement, DocumentFactory parentFactory)
/* 119:    */   {
/* 120:191 */     String name = xsdElement.attributeValue("name");
/* 121:192 */     String type = xsdElement.attributeValue("type");
/* 122:193 */     QName qname = getQName(name);
/* 123:    */     
/* 124:195 */     DatatypeElementFactory factory = getDatatypeElementFactory(qname);
/* 125:197 */     if (type != null)
/* 126:    */     {
/* 127:199 */       XSDatatype dataType = getTypeByName(type);
/* 128:201 */       if (dataType != null)
/* 129:    */       {
/* 130:202 */         factory.setChildElementXSDatatype(qname, dataType);
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:204 */         QName typeQName = getQName(type);
/* 135:205 */         this.namedTypeResolver.registerTypedElement(xsdElement, typeQName, parentFactory);
/* 136:    */       }
/* 137:209 */       return;
/* 138:    */     }
/* 139:213 */     Element xsdSimpleType = xsdElement.element(XSD_SIMPLETYPE);
/* 140:215 */     if (xsdSimpleType != null)
/* 141:    */     {
/* 142:216 */       XSDatatype dataType = loadXSDatatypeFromSimpleType(xsdSimpleType);
/* 143:218 */       if (dataType != null) {
/* 144:219 */         factory.setChildElementXSDatatype(qname, dataType);
/* 145:    */       }
/* 146:    */     }
/* 147:223 */     Element schemaComplexType = xsdElement.element(XSD_COMPLEXTYPE);
/* 148:225 */     if (schemaComplexType != null) {
/* 149:226 */       onSchemaComplexType(schemaComplexType, factory);
/* 150:    */     }
/* 151:229 */     Iterator iter = xsdElement.elementIterator(XSD_ATTRIBUTE);
/* 152:231 */     if (iter.hasNext()) {
/* 153:    */       do
/* 154:    */       {
/* 155:233 */         onDatatypeAttribute(xsdElement, factory, (Element)iter.next());
/* 156:235 */       } while (iter.hasNext());
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void onNamedSchemaComplexType(Element schemaComplexType)
/* 161:    */   {
/* 162:246 */     Attribute nameAttr = schemaComplexType.attribute("name");
/* 163:248 */     if (nameAttr == null) {
/* 164:249 */       return;
/* 165:    */     }
/* 166:252 */     String name = nameAttr.getText();
/* 167:253 */     QName qname = getQName(name);
/* 168:    */     
/* 169:255 */     DatatypeElementFactory factory = getDatatypeElementFactory(qname);
/* 170:    */     
/* 171:257 */     onSchemaComplexType(schemaComplexType, factory);
/* 172:258 */     this.namedTypeResolver.registerComplexType(qname, factory);
/* 173:    */   }
/* 174:    */   
/* 175:    */   private void onSchemaComplexType(Element schemaComplexType, DatatypeElementFactory elementFactory)
/* 176:    */   {
/* 177:271 */     Iterator iter = schemaComplexType.elementIterator(XSD_ATTRIBUTE);
/* 178:273 */     while (iter.hasNext())
/* 179:    */     {
/* 180:274 */       Element xsdAttribute = (Element)iter.next();
/* 181:275 */       String name = xsdAttribute.attributeValue("name");
/* 182:276 */       QName qname = getQName(name);
/* 183:    */       
/* 184:278 */       XSDatatype dataType = dataTypeForXsdAttribute(xsdAttribute);
/* 185:280 */       if (dataType != null) {
/* 186:284 */         elementFactory.setAttributeXSDatatype(qname, dataType);
/* 187:    */       }
/* 188:    */     }
/* 189:289 */     Element schemaSequence = schemaComplexType.element(XSD_SEQUENCE);
/* 190:291 */     if (schemaSequence != null) {
/* 191:292 */       onChildElements(schemaSequence, elementFactory);
/* 192:    */     }
/* 193:296 */     Element schemaChoice = schemaComplexType.element(XSD_CHOICE);
/* 194:298 */     if (schemaChoice != null) {
/* 195:299 */       onChildElements(schemaChoice, elementFactory);
/* 196:    */     }
/* 197:303 */     Element schemaAll = schemaComplexType.element(XSD_ALL);
/* 198:305 */     if (schemaAll != null) {
/* 199:306 */       onChildElements(schemaAll, elementFactory);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   private void onChildElements(Element element, DatatypeElementFactory fact)
/* 204:    */   {
/* 205:311 */     Iterator iter = element.elementIterator(XSD_ELEMENT);
/* 206:313 */     while (iter.hasNext())
/* 207:    */     {
/* 208:314 */       Element xsdElement = (Element)iter.next();
/* 209:315 */       onDatatypeElement(xsdElement, fact);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void onDatatypeAttribute(Element xsdElement, DatatypeElementFactory elementFactory, Element xsdAttribute)
/* 214:    */   {
/* 215:331 */     String name = xsdAttribute.attributeValue("name");
/* 216:332 */     QName qname = getQName(name);
/* 217:333 */     XSDatatype dataType = dataTypeForXsdAttribute(xsdAttribute);
/* 218:335 */     if (dataType != null)
/* 219:    */     {
/* 220:337 */       elementFactory.setAttributeXSDatatype(qname, dataType);
/* 221:    */     }
/* 222:    */     else
/* 223:    */     {
/* 224:339 */       String type = xsdAttribute.attributeValue("type");
/* 225:340 */       System.out.println("Warning: Couldn't find XSDatatype for type: " + type + " attribute: " + name);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private XSDatatype dataTypeForXsdAttribute(Element xsdAttribute)
/* 230:    */   {
/* 231:357 */     String type = xsdAttribute.attributeValue("type");
/* 232:358 */     XSDatatype dataType = null;
/* 233:360 */     if (type != null)
/* 234:    */     {
/* 235:361 */       dataType = getTypeByName(type);
/* 236:    */     }
/* 237:    */     else
/* 238:    */     {
/* 239:364 */       Element xsdSimpleType = xsdAttribute.element(XSD_SIMPLETYPE);
/* 240:366 */       if (xsdSimpleType == null)
/* 241:    */       {
/* 242:367 */         String name = xsdAttribute.attributeValue("name");
/* 243:368 */         String msg = "The attribute: " + name + " has no type attribute and does not contain a " + "<simpleType/> element";
/* 244:    */         
/* 245:    */ 
/* 246:371 */         throw new InvalidSchemaException(msg);
/* 247:    */       }
/* 248:374 */       dataType = loadXSDatatypeFromSimpleType(xsdSimpleType);
/* 249:    */     }
/* 250:377 */     return dataType;
/* 251:    */   }
/* 252:    */   
/* 253:    */   private void onNamedSchemaSimpleType(Element schemaSimpleType)
/* 254:    */   {
/* 255:387 */     Attribute nameAttr = schemaSimpleType.attribute("name");
/* 256:389 */     if (nameAttr == null) {
/* 257:390 */       return;
/* 258:    */     }
/* 259:393 */     String name = nameAttr.getText();
/* 260:394 */     QName qname = getQName(name);
/* 261:395 */     XSDatatype datatype = loadXSDatatypeFromSimpleType(schemaSimpleType);
/* 262:396 */     this.namedTypeResolver.registerSimpleType(qname, datatype);
/* 263:    */   }
/* 264:    */   
/* 265:    */   private XSDatatype loadXSDatatypeFromSimpleType(Element xsdSimpleType)
/* 266:    */   {
/* 267:409 */     Element xsdRestriction = xsdSimpleType.element(XSD_RESTRICTION);
/* 268:411 */     if (xsdRestriction != null)
/* 269:    */     {
/* 270:412 */       String base = xsdRestriction.attributeValue("base");
/* 271:414 */       if (base != null)
/* 272:    */       {
/* 273:415 */         XSDatatype baseType = getTypeByName(base);
/* 274:417 */         if (baseType == null) {
/* 275:418 */           onSchemaError("Invalid base type: " + base + " when trying to build restriction: " + xsdRestriction);
/* 276:    */         } else {
/* 277:422 */           return deriveSimpleType(baseType, xsdRestriction);
/* 278:    */         }
/* 279:    */       }
/* 280:    */       else
/* 281:    */       {
/* 282:427 */         Element xsdSubType = xsdSimpleType.element(XSD_SIMPLETYPE);
/* 283:429 */         if (xsdSubType == null)
/* 284:    */         {
/* 285:430 */           String msg = "The simpleType element: " + xsdSimpleType + " must contain a base attribute or simpleType" + " element";
/* 286:    */           
/* 287:    */ 
/* 288:433 */           onSchemaError(msg);
/* 289:    */         }
/* 290:    */         else
/* 291:    */         {
/* 292:435 */           return loadXSDatatypeFromSimpleType(xsdSubType);
/* 293:    */         }
/* 294:    */       }
/* 295:    */     }
/* 296:    */     else
/* 297:    */     {
/* 298:439 */       onSchemaError("No <restriction>. Could not create XSDatatype for simpleType: " + xsdSimpleType);
/* 299:    */     }
/* 300:443 */     return null;
/* 301:    */   }
/* 302:    */   
/* 303:    */   private XSDatatype deriveSimpleType(XSDatatype baseType, Element xsdRestriction)
/* 304:    */   {
/* 305:458 */     TypeIncubator incubator = new TypeIncubator(baseType);
/* 306:459 */     ValidationContext context = null;
/* 307:    */     try
/* 308:    */     {
/* 309:462 */       Iterator iter = xsdRestriction.elementIterator();
/* 310:463 */       while (iter.hasNext())
/* 311:    */       {
/* 312:464 */         Element element = (Element)iter.next();
/* 313:465 */         String name = element.getName();
/* 314:466 */         String value = element.attributeValue("value");
/* 315:467 */         boolean fixed = AttributeHelper.booleanValue(element, "fixed");
/* 316:    */         
/* 317:    */ 
/* 318:470 */         incubator.addFacet(name, value, fixed, context);
/* 319:    */       }
/* 320:474 */       String newTypeName = null;
/* 321:    */       
/* 322:476 */       return incubator.derive("", newTypeName);
/* 323:    */     }
/* 324:    */     catch (DatatypeException e)
/* 325:    */     {
/* 326:478 */       onSchemaError("Invalid restriction: " + e.getMessage() + " when trying to build restriction: " + xsdRestriction);
/* 327:    */     }
/* 328:481 */     return null;
/* 329:    */   }
/* 330:    */   
/* 331:    */   private DatatypeElementFactory getDatatypeElementFactory(QName name)
/* 332:    */   {
/* 333:495 */     DatatypeElementFactory factory = this.documentFactory.getElementFactory(name);
/* 334:498 */     if (factory == null)
/* 335:    */     {
/* 336:499 */       factory = new DatatypeElementFactory(name);
/* 337:500 */       name.setDocumentFactory(factory);
/* 338:    */     }
/* 339:503 */     return factory;
/* 340:    */   }
/* 341:    */   
/* 342:    */   private XSDatatype getTypeByName(String type)
/* 343:    */   {
/* 344:507 */     XSDatatype dataType = (XSDatatype)this.dataTypeCache.get(type);
/* 345:509 */     if (dataType == null)
/* 346:    */     {
/* 347:512 */       int idx = type.indexOf(':');
/* 348:514 */       if (idx >= 0)
/* 349:    */       {
/* 350:515 */         String localName = type.substring(idx + 1);
/* 351:    */         try
/* 352:    */         {
/* 353:518 */           dataType = DatatypeFactory.getTypeByName(localName);
/* 354:    */         }
/* 355:    */         catch (DatatypeException e) {}
/* 356:    */       }
/* 357:523 */       if (dataType == null) {
/* 358:    */         try
/* 359:    */         {
/* 360:525 */           dataType = DatatypeFactory.getTypeByName(type);
/* 361:    */         }
/* 362:    */         catch (DatatypeException e) {}
/* 363:    */       }
/* 364:530 */       if (dataType == null)
/* 365:    */       {
/* 366:532 */         QName typeQName = getQName(type);
/* 367:533 */         dataType = (XSDatatype)this.namedTypeResolver.simpleTypeMap.get(typeQName);
/* 368:    */       }
/* 369:537 */       if (dataType != null) {
/* 370:539 */         this.dataTypeCache.put(type, dataType);
/* 371:    */       }
/* 372:    */     }
/* 373:543 */     return dataType;
/* 374:    */   }
/* 375:    */   
/* 376:    */   private QName getQName(String name)
/* 377:    */   {
/* 378:547 */     if (this.targetNamespace == null) {
/* 379:548 */       return this.documentFactory.createQName(name);
/* 380:    */     }
/* 381:550 */     return this.documentFactory.createQName(name, this.targetNamespace);
/* 382:    */   }
/* 383:    */   
/* 384:    */   private void onSchemaError(String message)
/* 385:    */   {
/* 386:568 */     throw new InvalidSchemaException(message);
/* 387:    */   }
/* 388:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.SchemaParser
 * JD-Core Version:    0.7.0.1
 */