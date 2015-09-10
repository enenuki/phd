/*   1:    */ package org.hibernate.metamodel.source.internal;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.JAXBContext;
/*   4:    */ import javax.xml.bind.JAXBException;
/*   5:    */ import javax.xml.bind.Unmarshaller;
/*   6:    */ import javax.xml.bind.ValidationEvent;
/*   7:    */ import javax.xml.bind.ValidationEventHandler;
/*   8:    */ import javax.xml.bind.ValidationEventLocator;
/*   9:    */ import javax.xml.namespace.QName;
/*  10:    */ import javax.xml.stream.XMLEventReader;
/*  11:    */ import javax.xml.stream.XMLInputFactory;
/*  12:    */ import javax.xml.stream.events.Attribute;
/*  13:    */ import javax.xml.stream.events.StartElement;
/*  14:    */ import javax.xml.stream.events.XMLEvent;
/*  15:    */ import javax.xml.transform.dom.DOMSource;
/*  16:    */ import javax.xml.validation.Schema;
/*  17:    */ import org.hibernate.internal.jaxb.JaxbRoot;
/*  18:    */ import org.hibernate.internal.jaxb.Origin;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
/*  20:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityMappings;
/*  21:    */ import org.hibernate.metamodel.MetadataSources;
/*  22:    */ import org.hibernate.metamodel.source.MappingException;
/*  23:    */ import org.jboss.logging.Logger;
/*  24:    */ import org.w3c.dom.Document;
/*  25:    */ import org.w3c.dom.Element;
/*  26:    */ 
/*  27:    */ public class JaxbHelper
/*  28:    */ {
/*  29: 69 */   private static final Logger log = Logger.getLogger(JaxbHelper.class);
/*  30:    */   public static final String ASSUMED_ORM_XSD_VERSION = "2.0";
/*  31:    */   private final MetadataSources metadataSources;
/*  32:    */   private XMLInputFactory staxFactory;
/*  33:    */   
/*  34:    */   public JaxbHelper(MetadataSources metadataSources)
/*  35:    */   {
/*  36: 76 */     this.metadataSources = metadataSources;
/*  37:    */   }
/*  38:    */   
/*  39:    */   /* Error */
/*  40:    */   public JaxbRoot unmarshal(java.io.InputStream stream, Origin origin)
/*  41:    */   {
/*  42:    */     // Byte code:
/*  43:    */     //   0: aload_0
/*  44:    */     //   1: invokespecial 3	org/hibernate/metamodel/source/internal/JaxbHelper:staxFactory	()Ljavax/xml/stream/XMLInputFactory;
/*  45:    */     //   4: aload_1
/*  46:    */     //   5: invokevirtual 4	javax/xml/stream/XMLInputFactory:createXMLEventReader	(Ljava/io/InputStream;)Ljavax/xml/stream/XMLEventReader;
/*  47:    */     //   8: astore_3
/*  48:    */     //   9: aload_0
/*  49:    */     //   10: aload_3
/*  50:    */     //   11: aload_2
/*  51:    */     //   12: invokespecial 5	org/hibernate/metamodel/source/internal/JaxbHelper:unmarshal	(Ljavax/xml/stream/XMLEventReader;Lorg/hibernate/internal/jaxb/Origin;)Lorg/hibernate/internal/jaxb/JaxbRoot;
/*  52:    */     //   15: astore 4
/*  53:    */     //   17: aload_3
/*  54:    */     //   18: invokeinterface 6 1 0
/*  55:    */     //   23: goto +5 -> 28
/*  56:    */     //   26: astore 5
/*  57:    */     //   28: aload 4
/*  58:    */     //   30: areturn
/*  59:    */     //   31: astore 6
/*  60:    */     //   33: aload_3
/*  61:    */     //   34: invokeinterface 6 1 0
/*  62:    */     //   39: goto +5 -> 44
/*  63:    */     //   42: astore 7
/*  64:    */     //   44: aload 6
/*  65:    */     //   46: athrow
/*  66:    */     //   47: astore_3
/*  67:    */     //   48: new 9	org/hibernate/metamodel/source/MappingException
/*  68:    */     //   51: dup
/*  69:    */     //   52: ldc 10
/*  70:    */     //   54: aload_3
/*  71:    */     //   55: aload_2
/*  72:    */     //   56: invokespecial 11	org/hibernate/metamodel/source/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Lorg/hibernate/internal/jaxb/Origin;)V
/*  73:    */     //   59: athrow
/*  74:    */     // Line number table:
/*  75:    */     //   Java source line #81	-> byte code offset #0
/*  76:    */     //   Java source line #83	-> byte code offset #9
/*  77:    */     //   Java source line #87	-> byte code offset #17
/*  78:    */     //   Java source line #90	-> byte code offset #23
/*  79:    */     //   Java source line #89	-> byte code offset #26
/*  80:    */     //   Java source line #90	-> byte code offset #28
/*  81:    */     //   Java source line #86	-> byte code offset #31
/*  82:    */     //   Java source line #87	-> byte code offset #33
/*  83:    */     //   Java source line #90	-> byte code offset #39
/*  84:    */     //   Java source line #89	-> byte code offset #42
/*  85:    */     //   Java source line #90	-> byte code offset #44
/*  86:    */     //   Java source line #93	-> byte code offset #47
/*  87:    */     //   Java source line #94	-> byte code offset #48
/*  88:    */     // Local variable table:
/*  89:    */     //   start	length	slot	name	signature
/*  90:    */     //   0	60	0	this	JaxbHelper
/*  91:    */     //   0	60	1	stream	java.io.InputStream
/*  92:    */     //   0	60	2	origin	Origin
/*  93:    */     //   8	26	3	staxReader	XMLEventReader
/*  94:    */     //   47	8	3	e	javax.xml.stream.XMLStreamException
/*  95:    */     //   26	3	5	ignore	Exception
/*  96:    */     //   31	14	6	localObject	Object
/*  97:    */     //   42	3	7	ignore	Exception
/*  98:    */     // Exception table:
/*  99:    */     //   from	to	target	type
/* 100:    */     //   17	23	26	java/lang/Exception
/* 101:    */     //   9	17	31	finally
/* 102:    */     //   31	33	31	finally
/* 103:    */     //   33	39	42	java/lang/Exception
/* 104:    */     //   0	28	47	javax/xml/stream/XMLStreamException
/* 105:    */     //   31	47	47	javax/xml/stream/XMLStreamException
/* 106:    */   }
/* 107:    */   
/* 108:    */   private XMLInputFactory staxFactory()
/* 109:    */   {
/* 110:101 */     if (this.staxFactory == null) {
/* 111:102 */       this.staxFactory = buildStaxFactory();
/* 112:    */     }
/* 113:104 */     return this.staxFactory;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private XMLInputFactory buildStaxFactory()
/* 117:    */   {
/* 118:109 */     XMLInputFactory staxFactory = XMLInputFactory.newInstance();
/* 119:110 */     return staxFactory;
/* 120:    */   }
/* 121:    */   
/* 122:113 */   private static final QName ORM_VERSION_ATTRIBUTE_QNAME = new QName("version");
/* 123:    */   public static final String HBM_SCHEMA_NAME = "org/hibernate/hibernate-mapping-4.0.xsd";
/* 124:    */   public static final String ORM_1_SCHEMA_NAME = "org/hibernate/ejb/orm_1_0.xsd";
/* 125:    */   public static final String ORM_2_SCHEMA_NAME = "org/hibernate/ejb/orm_2_0.xsd";
/* 126:    */   private Schema hbmSchema;
/* 127:    */   private Schema orm1Schema;
/* 128:    */   private Schema orm2Schema;
/* 129:    */   
/* 130:    */   private JaxbRoot unmarshal(XMLEventReader staxEventReader, Origin origin)
/* 131:    */   {
/* 132:    */     XMLEvent event;
/* 133:    */     try
/* 134:    */     {
/* 135:119 */       event = staxEventReader.peek();
/* 136:120 */       while ((event != null) && (!event.isStartElement()))
/* 137:    */       {
/* 138:121 */         staxEventReader.nextEvent();
/* 139:122 */         event = staxEventReader.peek();
/* 140:    */       }
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:126 */       throw new MappingException("Error accessing stax stream", e, origin);
/* 145:    */     }
/* 146:129 */     if (event == null) {
/* 147:130 */       throw new MappingException("Could not locate root element", origin);
/* 148:    */     }
/* 149:136 */     String elementName = event.asStartElement().getName().getLocalPart();
/* 150:    */     Class jaxbTarget;
/* 151:    */     Schema validationSchema;
/* 152:    */     Class jaxbTarget;
/* 153:138 */     if ("entity-mappings".equals(elementName))
/* 154:    */     {
/* 155:139 */       Attribute attribute = event.asStartElement().getAttributeByName(ORM_VERSION_ATTRIBUTE_QNAME);
/* 156:140 */       String explicitVersion = attribute == null ? null : attribute.getValue();
/* 157:141 */       Schema validationSchema = resolveSupportedOrmXsd(explicitVersion);
/* 158:142 */       jaxbTarget = JaxbEntityMappings.class;
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:145 */       validationSchema = hbmSchema();
/* 163:146 */       jaxbTarget = JaxbHibernateMapping.class;
/* 164:    */     }
/* 165:150 */     ContextProvidingValidationEventHandler handler = new ContextProvidingValidationEventHandler();
/* 166:    */     Object target;
/* 167:    */     try
/* 168:    */     {
/* 169:152 */       JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { jaxbTarget });
/* 170:153 */       Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
/* 171:154 */       unmarshaller.setSchema(validationSchema);
/* 172:155 */       unmarshaller.setEventHandler(handler);
/* 173:156 */       target = unmarshaller.unmarshal(staxEventReader);
/* 174:    */     }
/* 175:    */     catch (JAXBException e)
/* 176:    */     {
/* 177:160 */       StringBuilder builder = new StringBuilder();
/* 178:161 */       builder.append("Unable to perform unmarshalling at line number ");
/* 179:162 */       builder.append(handler.getLineNumber());
/* 180:163 */       builder.append(" and column ");
/* 181:164 */       builder.append(handler.getColumnNumber());
/* 182:165 */       builder.append(". Message: ");
/* 183:166 */       builder.append(handler.getMessage());
/* 184:167 */       throw new MappingException(builder.toString(), e, origin);
/* 185:    */     }
/* 186:170 */     return new JaxbRoot(target, origin);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public JaxbRoot unmarshal(Document document, Origin origin)
/* 190:    */   {
/* 191:175 */     Element rootElement = document.getDocumentElement();
/* 192:176 */     if (rootElement == null) {
/* 193:177 */       throw new MappingException("No root element found", origin);
/* 194:    */     }
/* 195:    */     Class jaxbTarget;
/* 196:    */     Schema validationSchema;
/* 197:    */     Class jaxbTarget;
/* 198:183 */     if ("entity-mappings".equals(rootElement.getNodeName()))
/* 199:    */     {
/* 200:184 */       String explicitVersion = rootElement.getAttribute("version");
/* 201:185 */       Schema validationSchema = resolveSupportedOrmXsd(explicitVersion);
/* 202:186 */       jaxbTarget = JaxbEntityMappings.class;
/* 203:    */     }
/* 204:    */     else
/* 205:    */     {
/* 206:189 */       validationSchema = hbmSchema();
/* 207:190 */       jaxbTarget = JaxbHibernateMapping.class;
/* 208:    */     }
/* 209:    */     Object target;
/* 210:    */     try
/* 211:    */     {
/* 212:195 */       JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { jaxbTarget });
/* 213:196 */       Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
/* 214:197 */       unmarshaller.setSchema(validationSchema);
/* 215:198 */       target = unmarshaller.unmarshal(new DOMSource(document));
/* 216:    */     }
/* 217:    */     catch (JAXBException e)
/* 218:    */     {
/* 219:201 */       throw new MappingException("Unable to perform unmarshalling", e, origin);
/* 220:    */     }
/* 221:204 */     return new JaxbRoot(target, origin);
/* 222:    */   }
/* 223:    */   
/* 224:    */   private Schema resolveSupportedOrmXsd(String explicitVersion)
/* 225:    */   {
/* 226:208 */     String xsdVersionString = explicitVersion == null ? "2.0" : explicitVersion;
/* 227:209 */     if ("1.0".equals(xsdVersionString)) {
/* 228:210 */       return orm1Schema();
/* 229:    */     }
/* 230:212 */     if ("2.0".equals(xsdVersionString)) {
/* 231:213 */       return orm2Schema();
/* 232:    */     }
/* 233:215 */     throw new IllegalArgumentException("Unsupported orm.xml XSD version encountered [" + xsdVersionString + "]");
/* 234:    */   }
/* 235:    */   
/* 236:    */   private Schema hbmSchema()
/* 237:    */   {
/* 238:225 */     if (this.hbmSchema == null) {
/* 239:226 */       this.hbmSchema = resolveLocalSchema("org/hibernate/hibernate-mapping-4.0.xsd");
/* 240:    */     }
/* 241:228 */     return this.hbmSchema;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private Schema orm1Schema()
/* 245:    */   {
/* 246:234 */     if (this.orm1Schema == null) {
/* 247:235 */       this.orm1Schema = resolveLocalSchema("org/hibernate/ejb/orm_1_0.xsd");
/* 248:    */     }
/* 249:237 */     return this.orm1Schema;
/* 250:    */   }
/* 251:    */   
/* 252:    */   private Schema orm2Schema()
/* 253:    */   {
/* 254:243 */     if (this.orm2Schema == null) {
/* 255:244 */       this.orm2Schema = resolveLocalSchema("org/hibernate/ejb/orm_2_0.xsd");
/* 256:    */     }
/* 257:246 */     return this.orm2Schema;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private Schema resolveLocalSchema(String schemaName)
/* 261:    */   {
/* 262:250 */     return resolveLocalSchema(schemaName, "http://www.w3.org/2001/XMLSchema");
/* 263:    */   }
/* 264:    */   
/* 265:    */   /* Error */
/* 266:    */   private Schema resolveLocalSchema(String schemaName, String schemaLanguage)
/* 267:    */   {
/* 268:    */     // Byte code:
/* 269:    */     //   0: aload_0
/* 270:    */     //   1: getfield 2	org/hibernate/metamodel/source/internal/JaxbHelper:metadataSources	Lorg/hibernate/metamodel/MetadataSources;
/* 271:    */     //   4: invokevirtual 81	org/hibernate/metamodel/MetadataSources:getServiceRegistry	()Lorg/hibernate/service/ServiceRegistry;
/* 272:    */     //   7: ldc_w 82
/* 273:    */     //   10: invokeinterface 83 2 0
/* 274:    */     //   15: checkcast 82	org/hibernate/service/classloading/spi/ClassLoaderService
/* 275:    */     //   18: aload_1
/* 276:    */     //   19: invokeinterface 84 2 0
/* 277:    */     //   24: astore_3
/* 278:    */     //   25: aload_3
/* 279:    */     //   26: ifnonnull +36 -> 62
/* 280:    */     //   29: new 85	org/hibernate/metamodel/source/XsdException
/* 281:    */     //   32: dup
/* 282:    */     //   33: new 42	java/lang/StringBuilder
/* 283:    */     //   36: dup
/* 284:    */     //   37: invokespecial 43	java/lang/StringBuilder:<init>	()V
/* 285:    */     //   40: ldc 86
/* 286:    */     //   42: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 287:    */     //   45: aload_1
/* 288:    */     //   46: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 289:    */     //   49: ldc 87
/* 290:    */     //   51: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 291:    */     //   54: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 292:    */     //   57: aload_1
/* 293:    */     //   58: invokespecial 88	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 294:    */     //   61: athrow
/* 295:    */     //   62: aload_3
/* 296:    */     //   63: invokevirtual 89	java/net/URL:openStream	()Ljava/io/InputStream;
/* 297:    */     //   66: astore 4
/* 298:    */     //   68: new 90	javax/xml/transform/stream/StreamSource
/* 299:    */     //   71: dup
/* 300:    */     //   72: aload_3
/* 301:    */     //   73: invokevirtual 89	java/net/URL:openStream	()Ljava/io/InputStream;
/* 302:    */     //   76: invokespecial 91	javax/xml/transform/stream/StreamSource:<init>	(Ljava/io/InputStream;)V
/* 303:    */     //   79: astore 5
/* 304:    */     //   81: aload_2
/* 305:    */     //   82: invokestatic 92	javax/xml/validation/SchemaFactory:newInstance	(Ljava/lang/String;)Ljavax/xml/validation/SchemaFactory;
/* 306:    */     //   85: astore 6
/* 307:    */     //   87: aload 6
/* 308:    */     //   89: aload 5
/* 309:    */     //   91: invokevirtual 93	javax/xml/validation/SchemaFactory:newSchema	(Ljavax/xml/transform/Source;)Ljavax/xml/validation/Schema;
/* 310:    */     //   94: astore 7
/* 311:    */     //   96: aload 4
/* 312:    */     //   98: invokevirtual 94	java/io/InputStream:close	()V
/* 313:    */     //   101: goto +18 -> 119
/* 314:    */     //   104: astore 8
/* 315:    */     //   106: getstatic 96	org/hibernate/metamodel/source/internal/JaxbHelper:log	Lorg/jboss/logging/Logger;
/* 316:    */     //   109: ldc 97
/* 317:    */     //   111: aload 8
/* 318:    */     //   113: invokevirtual 98	java/io/IOException:toString	()Ljava/lang/String;
/* 319:    */     //   116: invokevirtual 99	org/jboss/logging/Logger:debugf	(Ljava/lang/String;Ljava/lang/Object;)V
/* 320:    */     //   119: aload 7
/* 321:    */     //   121: areturn
/* 322:    */     //   122: astore 5
/* 323:    */     //   124: new 85	org/hibernate/metamodel/source/XsdException
/* 324:    */     //   127: dup
/* 325:    */     //   128: new 42	java/lang/StringBuilder
/* 326:    */     //   131: dup
/* 327:    */     //   132: invokespecial 43	java/lang/StringBuilder:<init>	()V
/* 328:    */     //   135: ldc 101
/* 329:    */     //   137: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 330:    */     //   140: aload_1
/* 331:    */     //   141: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 332:    */     //   144: ldc 70
/* 333:    */     //   146: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 334:    */     //   149: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 335:    */     //   152: aload 5
/* 336:    */     //   154: aload_1
/* 337:    */     //   155: invokespecial 102	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)V
/* 338:    */     //   158: athrow
/* 339:    */     //   159: astore 5
/* 340:    */     //   161: new 85	org/hibernate/metamodel/source/XsdException
/* 341:    */     //   164: dup
/* 342:    */     //   165: new 42	java/lang/StringBuilder
/* 343:    */     //   168: dup
/* 344:    */     //   169: invokespecial 43	java/lang/StringBuilder:<init>	()V
/* 345:    */     //   172: ldc 101
/* 346:    */     //   174: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 347:    */     //   177: aload_1
/* 348:    */     //   178: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 349:    */     //   181: ldc 70
/* 350:    */     //   183: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 351:    */     //   186: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 352:    */     //   189: aload 5
/* 353:    */     //   191: aload_1
/* 354:    */     //   192: invokespecial 102	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)V
/* 355:    */     //   195: athrow
/* 356:    */     //   196: astore 9
/* 357:    */     //   198: aload 4
/* 358:    */     //   200: invokevirtual 94	java/io/InputStream:close	()V
/* 359:    */     //   203: goto +18 -> 221
/* 360:    */     //   206: astore 10
/* 361:    */     //   208: getstatic 96	org/hibernate/metamodel/source/internal/JaxbHelper:log	Lorg/jboss/logging/Logger;
/* 362:    */     //   211: ldc 97
/* 363:    */     //   213: aload 10
/* 364:    */     //   215: invokevirtual 98	java/io/IOException:toString	()Ljava/lang/String;
/* 365:    */     //   218: invokevirtual 99	org/jboss/logging/Logger:debugf	(Ljava/lang/String;Ljava/lang/Object;)V
/* 366:    */     //   221: aload 9
/* 367:    */     //   223: athrow
/* 368:    */     //   224: astore 4
/* 369:    */     //   226: new 85	org/hibernate/metamodel/source/XsdException
/* 370:    */     //   229: dup
/* 371:    */     //   230: new 42	java/lang/StringBuilder
/* 372:    */     //   233: dup
/* 373:    */     //   234: invokespecial 43	java/lang/StringBuilder:<init>	()V
/* 374:    */     //   237: ldc 103
/* 375:    */     //   239: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 376:    */     //   242: aload_3
/* 377:    */     //   243: invokevirtual 104	java/net/URL:toExternalForm	()Ljava/lang/String;
/* 378:    */     //   246: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 379:    */     //   249: ldc 70
/* 380:    */     //   251: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 381:    */     //   254: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 382:    */     //   257: aload_1
/* 383:    */     //   258: invokespecial 88	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 384:    */     //   261: athrow
/* 385:    */     // Line number table:
/* 386:    */     //   Java source line #254	-> byte code offset #0
/* 387:    */     //   Java source line #257	-> byte code offset #25
/* 388:    */     //   Java source line #258	-> byte code offset #29
/* 389:    */     //   Java source line #261	-> byte code offset #62
/* 390:    */     //   Java source line #263	-> byte code offset #68
/* 391:    */     //   Java source line #264	-> byte code offset #81
/* 392:    */     //   Java source line #265	-> byte code offset #87
/* 393:    */     //   Java source line #275	-> byte code offset #96
/* 394:    */     //   Java source line #279	-> byte code offset #101
/* 395:    */     //   Java source line #277	-> byte code offset #104
/* 396:    */     //   Java source line #278	-> byte code offset #106
/* 397:    */     //   Java source line #279	-> byte code offset #119
/* 398:    */     //   Java source line #267	-> byte code offset #122
/* 399:    */     //   Java source line #268	-> byte code offset #124
/* 400:    */     //   Java source line #270	-> byte code offset #159
/* 401:    */     //   Java source line #271	-> byte code offset #161
/* 402:    */     //   Java source line #274	-> byte code offset #196
/* 403:    */     //   Java source line #275	-> byte code offset #198
/* 404:    */     //   Java source line #279	-> byte code offset #203
/* 405:    */     //   Java source line #277	-> byte code offset #206
/* 406:    */     //   Java source line #278	-> byte code offset #208
/* 407:    */     //   Java source line #279	-> byte code offset #221
/* 408:    */     //   Java source line #282	-> byte code offset #224
/* 409:    */     //   Java source line #283	-> byte code offset #226
/* 410:    */     // Local variable table:
/* 411:    */     //   start	length	slot	name	signature
/* 412:    */     //   0	262	0	this	JaxbHelper
/* 413:    */     //   0	262	1	schemaName	String
/* 414:    */     //   0	262	2	schemaLanguage	String
/* 415:    */     //   24	219	3	url	java.net.URL
/* 416:    */     //   66	133	4	schemaStream	java.io.InputStream
/* 417:    */     //   224	3	4	e	java.io.IOException
/* 418:    */     //   79	11	5	source	javax.xml.transform.stream.StreamSource
/* 419:    */     //   122	31	5	e	org.xml.sax.SAXException
/* 420:    */     //   159	31	5	e	java.io.IOException
/* 421:    */     //   85	3	6	schemaFactory	javax.xml.validation.SchemaFactory
/* 422:    */     //   104	8	8	e	java.io.IOException
/* 423:    */     //   196	26	9	localObject	Object
/* 424:    */     //   206	8	10	e	java.io.IOException
/* 425:    */     // Exception table:
/* 426:    */     //   from	to	target	type
/* 427:    */     //   96	101	104	java/io/IOException
/* 428:    */     //   68	96	122	org/xml/sax/SAXException
/* 429:    */     //   68	96	159	java/io/IOException
/* 430:    */     //   68	96	196	finally
/* 431:    */     //   122	198	196	finally
/* 432:    */     //   198	203	206	java/io/IOException
/* 433:    */     //   62	119	224	java/io/IOException
/* 434:    */     //   122	224	224	java/io/IOException
/* 435:    */   }
/* 436:    */   
/* 437:    */   static class ContextProvidingValidationEventHandler
/* 438:    */     implements ValidationEventHandler
/* 439:    */   {
/* 440:    */     private int lineNumber;
/* 441:    */     private int columnNumber;
/* 442:    */     private String message;
/* 443:    */     
/* 444:    */     public boolean handleEvent(ValidationEvent validationEvent)
/* 445:    */     {
/* 446:294 */       ValidationEventLocator locator = validationEvent.getLocator();
/* 447:295 */       this.lineNumber = locator.getLineNumber();
/* 448:296 */       this.columnNumber = locator.getColumnNumber();
/* 449:297 */       this.message = validationEvent.getMessage();
/* 450:298 */       return false;
/* 451:    */     }
/* 452:    */     
/* 453:    */     public int getLineNumber()
/* 454:    */     {
/* 455:302 */       return this.lineNumber;
/* 456:    */     }
/* 457:    */     
/* 458:    */     public int getColumnNumber()
/* 459:    */     {
/* 460:306 */       return this.columnNumber;
/* 461:    */     }
/* 462:    */     
/* 463:    */     public String getMessage()
/* 464:    */     {
/* 465:310 */       return this.message;
/* 466:    */     }
/* 467:    */   }
/* 468:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.JaxbHelper
 * JD-Core Version:    0.7.0.1
 */