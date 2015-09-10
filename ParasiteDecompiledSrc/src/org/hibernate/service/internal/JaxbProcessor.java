/*   1:    */ package org.hibernate.service.internal;
/*   2:    */ 
/*   3:    */ import javax.xml.bind.JAXBContext;
/*   4:    */ import javax.xml.bind.JAXBException;
/*   5:    */ import javax.xml.bind.Unmarshaller;
/*   6:    */ import javax.xml.bind.ValidationEvent;
/*   7:    */ import javax.xml.bind.ValidationEventHandler;
/*   8:    */ import javax.xml.bind.ValidationEventLocator;
/*   9:    */ import javax.xml.stream.XMLInputFactory;
/*  10:    */ import javax.xml.stream.XMLStreamReader;
/*  11:    */ import javax.xml.validation.Schema;
/*  12:    */ import org.hibernate.internal.jaxb.Origin;
/*  13:    */ import org.hibernate.internal.jaxb.SourceType;
/*  14:    */ import org.hibernate.internal.jaxb.cfg.JaxbHibernateConfiguration;
/*  15:    */ import org.hibernate.internal.util.config.ConfigurationException;
/*  16:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class JaxbProcessor
/*  20:    */ {
/*  21: 57 */   private static final Logger log = Logger.getLogger(JaxbProcessor.class);
/*  22:    */   private final ClassLoaderService classLoaderService;
/*  23:    */   private XMLInputFactory staxFactory;
/*  24:    */   private Schema schema;
/*  25:    */   
/*  26:    */   public JaxbProcessor(ClassLoaderService classLoaderService)
/*  27:    */   {
/*  28: 62 */     this.classLoaderService = classLoaderService;
/*  29:    */   }
/*  30:    */   
/*  31:    */   /* Error */
/*  32:    */   public JaxbHibernateConfiguration unmarshal(java.io.InputStream stream, Origin origin)
/*  33:    */   {
/*  34:    */     // Byte code:
/*  35:    */     //   0: aload_0
/*  36:    */     //   1: invokespecial 3	org/hibernate/service/internal/JaxbProcessor:staxFactory	()Ljavax/xml/stream/XMLInputFactory;
/*  37:    */     //   4: aload_1
/*  38:    */     //   5: invokevirtual 4	javax/xml/stream/XMLInputFactory:createXMLStreamReader	(Ljava/io/InputStream;)Ljavax/xml/stream/XMLStreamReader;
/*  39:    */     //   8: astore_3
/*  40:    */     //   9: aload_0
/*  41:    */     //   10: aload_3
/*  42:    */     //   11: aload_2
/*  43:    */     //   12: invokespecial 5	org/hibernate/service/internal/JaxbProcessor:unmarshal	(Ljavax/xml/stream/XMLStreamReader;Lorg/hibernate/internal/jaxb/Origin;)Lorg/hibernate/internal/jaxb/cfg/JaxbHibernateConfiguration;
/*  44:    */     //   15: astore 4
/*  45:    */     //   17: aload_3
/*  46:    */     //   18: invokeinterface 6 1 0
/*  47:    */     //   23: goto +5 -> 28
/*  48:    */     //   26: astore 5
/*  49:    */     //   28: aload 4
/*  50:    */     //   30: areturn
/*  51:    */     //   31: astore 6
/*  52:    */     //   33: aload_3
/*  53:    */     //   34: invokeinterface 6 1 0
/*  54:    */     //   39: goto +5 -> 44
/*  55:    */     //   42: astore 7
/*  56:    */     //   44: aload 6
/*  57:    */     //   46: athrow
/*  58:    */     //   47: astore_3
/*  59:    */     //   48: new 9	org/hibernate/metamodel/source/MappingException
/*  60:    */     //   51: dup
/*  61:    */     //   52: ldc 10
/*  62:    */     //   54: aload_3
/*  63:    */     //   55: aload_2
/*  64:    */     //   56: invokespecial 11	org/hibernate/metamodel/source/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Lorg/hibernate/internal/jaxb/Origin;)V
/*  65:    */     //   59: athrow
/*  66:    */     // Line number table:
/*  67:    */     //   Java source line #67	-> byte code offset #0
/*  68:    */     //   Java source line #69	-> byte code offset #9
/*  69:    */     //   Java source line #73	-> byte code offset #17
/*  70:    */     //   Java source line #76	-> byte code offset #23
/*  71:    */     //   Java source line #75	-> byte code offset #26
/*  72:    */     //   Java source line #76	-> byte code offset #28
/*  73:    */     //   Java source line #72	-> byte code offset #31
/*  74:    */     //   Java source line #73	-> byte code offset #33
/*  75:    */     //   Java source line #76	-> byte code offset #39
/*  76:    */     //   Java source line #75	-> byte code offset #42
/*  77:    */     //   Java source line #76	-> byte code offset #44
/*  78:    */     //   Java source line #79	-> byte code offset #47
/*  79:    */     //   Java source line #80	-> byte code offset #48
/*  80:    */     // Local variable table:
/*  81:    */     //   start	length	slot	name	signature
/*  82:    */     //   0	60	0	this	JaxbProcessor
/*  83:    */     //   0	60	1	stream	java.io.InputStream
/*  84:    */     //   0	60	2	origin	Origin
/*  85:    */     //   8	26	3	staxReader	XMLStreamReader
/*  86:    */     //   47	8	3	e	javax.xml.stream.XMLStreamException
/*  87:    */     //   26	3	5	ignore	java.lang.Exception
/*  88:    */     //   31	14	6	localObject	Object
/*  89:    */     //   42	3	7	ignore	java.lang.Exception
/*  90:    */     // Exception table:
/*  91:    */     //   from	to	target	type
/*  92:    */     //   17	23	26	java/lang/Exception
/*  93:    */     //   9	17	31	finally
/*  94:    */     //   31	33	31	finally
/*  95:    */     //   33	39	42	java/lang/Exception
/*  96:    */     //   0	28	47	javax/xml/stream/XMLStreamException
/*  97:    */     //   31	47	47	javax/xml/stream/XMLStreamException
/*  98:    */   }
/*  99:    */   
/* 100:    */   private XMLInputFactory staxFactory()
/* 101:    */   {
/* 102: 87 */     if (this.staxFactory == null) {
/* 103: 88 */       this.staxFactory = buildStaxFactory();
/* 104:    */     }
/* 105: 90 */     return this.staxFactory;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private XMLInputFactory buildStaxFactory()
/* 109:    */   {
/* 110: 95 */     XMLInputFactory staxFactory = XMLInputFactory.newInstance();
/* 111: 96 */     return staxFactory;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private JaxbHibernateConfiguration unmarshal(XMLStreamReader staxReader, Origin origin)
/* 115:    */   {
/* 116:102 */     ContextProvidingValidationEventHandler handler = new ContextProvidingValidationEventHandler();
/* 117:    */     try
/* 118:    */     {
/* 119:104 */       JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { JaxbHibernateConfiguration.class });
/* 120:105 */       Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
/* 121:106 */       unmarshaller.setSchema(schema());
/* 122:107 */       unmarshaller.setEventHandler(handler);
/* 123:108 */       Object target = unmarshaller.unmarshal(staxReader);
/* 124:109 */       return (JaxbHibernateConfiguration)target;
/* 125:    */     }
/* 126:    */     catch (JAXBException e)
/* 127:    */     {
/* 128:112 */       StringBuilder builder = new StringBuilder();
/* 129:113 */       builder.append("Unable to perform unmarshalling at line number ").append(handler.getLineNumber()).append(" and column ").append(handler.getColumnNumber()).append(" in ").append(origin.getType().name()).append(" ").append(origin.getName()).append(". Message: ").append(handler.getMessage());
/* 130:    */       
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:120 */       throw new ConfigurationException(builder.toString(), e);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private Schema schema()
/* 141:    */   {
/* 142:127 */     if (this.schema == null) {
/* 143:128 */       this.schema = resolveLocalSchema("org/hibernate/hibernate-configuration-4.0.xsd");
/* 144:    */     }
/* 145:130 */     return this.schema;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private Schema resolveLocalSchema(String schemaName)
/* 149:    */   {
/* 150:134 */     return resolveLocalSchema(schemaName, "http://www.w3.org/2001/XMLSchema");
/* 151:    */   }
/* 152:    */   
/* 153:    */   /* Error */
/* 154:    */   private Schema resolveLocalSchema(String schemaName, String schemaLanguage)
/* 155:    */   {
/* 156:    */     // Byte code:
/* 157:    */     //   0: aload_0
/* 158:    */     //   1: getfield 2	org/hibernate/service/internal/JaxbProcessor:classLoaderService	Lorg/hibernate/service/classloading/spi/ClassLoaderService;
/* 159:    */     //   4: aload_1
/* 160:    */     //   5: invokeinterface 49 2 0
/* 161:    */     //   10: astore_3
/* 162:    */     //   11: aload_3
/* 163:    */     //   12: ifnonnull +36 -> 48
/* 164:    */     //   15: new 50	org/hibernate/metamodel/source/XsdException
/* 165:    */     //   18: dup
/* 166:    */     //   19: new 26	java/lang/StringBuilder
/* 167:    */     //   22: dup
/* 168:    */     //   23: invokespecial 27	java/lang/StringBuilder:<init>	()V
/* 169:    */     //   26: ldc 51
/* 170:    */     //   28: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 171:    */     //   31: aload_1
/* 172:    */     //   32: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 173:    */     //   35: ldc 52
/* 174:    */     //   37: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 175:    */     //   40: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 176:    */     //   43: aload_1
/* 177:    */     //   44: invokespecial 53	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 178:    */     //   47: athrow
/* 179:    */     //   48: aload_3
/* 180:    */     //   49: invokevirtual 54	java/net/URL:openStream	()Ljava/io/InputStream;
/* 181:    */     //   52: astore 4
/* 182:    */     //   54: new 55	javax/xml/transform/stream/StreamSource
/* 183:    */     //   57: dup
/* 184:    */     //   58: aload_3
/* 185:    */     //   59: invokevirtual 54	java/net/URL:openStream	()Ljava/io/InputStream;
/* 186:    */     //   62: invokespecial 56	javax/xml/transform/stream/StreamSource:<init>	(Ljava/io/InputStream;)V
/* 187:    */     //   65: astore 5
/* 188:    */     //   67: aload_2
/* 189:    */     //   68: invokestatic 57	javax/xml/validation/SchemaFactory:newInstance	(Ljava/lang/String;)Ljavax/xml/validation/SchemaFactory;
/* 190:    */     //   71: astore 6
/* 191:    */     //   73: aload 6
/* 192:    */     //   75: aload 5
/* 193:    */     //   77: invokevirtual 58	javax/xml/validation/SchemaFactory:newSchema	(Ljavax/xml/transform/Source;)Ljavax/xml/validation/Schema;
/* 194:    */     //   80: astore 7
/* 195:    */     //   82: aload 4
/* 196:    */     //   84: invokevirtual 59	java/io/InputStream:close	()V
/* 197:    */     //   87: goto +18 -> 105
/* 198:    */     //   90: astore 8
/* 199:    */     //   92: getstatic 61	org/hibernate/service/internal/JaxbProcessor:log	Lorg/jboss/logging/Logger;
/* 200:    */     //   95: ldc 62
/* 201:    */     //   97: aload 8
/* 202:    */     //   99: invokevirtual 63	java/io/IOException:toString	()Ljava/lang/String;
/* 203:    */     //   102: invokevirtual 64	org/jboss/logging/Logger:debugf	(Ljava/lang/String;Ljava/lang/Object;)V
/* 204:    */     //   105: aload 7
/* 205:    */     //   107: areturn
/* 206:    */     //   108: astore 5
/* 207:    */     //   110: new 50	org/hibernate/metamodel/source/XsdException
/* 208:    */     //   113: dup
/* 209:    */     //   114: new 26	java/lang/StringBuilder
/* 210:    */     //   117: dup
/* 211:    */     //   118: invokespecial 27	java/lang/StringBuilder:<init>	()V
/* 212:    */     //   121: ldc 66
/* 213:    */     //   123: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 214:    */     //   126: aload_1
/* 215:    */     //   127: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 216:    */     //   130: ldc 67
/* 217:    */     //   132: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 218:    */     //   135: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 219:    */     //   138: aload 5
/* 220:    */     //   140: aload_1
/* 221:    */     //   141: invokespecial 68	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)V
/* 222:    */     //   144: athrow
/* 223:    */     //   145: astore 5
/* 224:    */     //   147: new 50	org/hibernate/metamodel/source/XsdException
/* 225:    */     //   150: dup
/* 226:    */     //   151: new 26	java/lang/StringBuilder
/* 227:    */     //   154: dup
/* 228:    */     //   155: invokespecial 27	java/lang/StringBuilder:<init>	()V
/* 229:    */     //   158: ldc 66
/* 230:    */     //   160: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 231:    */     //   163: aload_1
/* 232:    */     //   164: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 233:    */     //   167: ldc 67
/* 234:    */     //   169: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 235:    */     //   172: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 236:    */     //   175: aload 5
/* 237:    */     //   177: aload_1
/* 238:    */     //   178: invokespecial 68	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)V
/* 239:    */     //   181: athrow
/* 240:    */     //   182: astore 9
/* 241:    */     //   184: aload 4
/* 242:    */     //   186: invokevirtual 59	java/io/InputStream:close	()V
/* 243:    */     //   189: goto +18 -> 207
/* 244:    */     //   192: astore 10
/* 245:    */     //   194: getstatic 61	org/hibernate/service/internal/JaxbProcessor:log	Lorg/jboss/logging/Logger;
/* 246:    */     //   197: ldc 62
/* 247:    */     //   199: aload 10
/* 248:    */     //   201: invokevirtual 63	java/io/IOException:toString	()Ljava/lang/String;
/* 249:    */     //   204: invokevirtual 64	org/jboss/logging/Logger:debugf	(Ljava/lang/String;Ljava/lang/Object;)V
/* 250:    */     //   207: aload 9
/* 251:    */     //   209: athrow
/* 252:    */     //   210: astore 4
/* 253:    */     //   212: new 50	org/hibernate/metamodel/source/XsdException
/* 254:    */     //   215: dup
/* 255:    */     //   216: new 26	java/lang/StringBuilder
/* 256:    */     //   219: dup
/* 257:    */     //   220: invokespecial 27	java/lang/StringBuilder:<init>	()V
/* 258:    */     //   223: ldc 69
/* 259:    */     //   225: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 260:    */     //   228: aload_3
/* 261:    */     //   229: invokevirtual 70	java/net/URL:toExternalForm	()Ljava/lang/String;
/* 262:    */     //   232: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 263:    */     //   235: ldc 67
/* 264:    */     //   237: invokevirtual 29	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 265:    */     //   240: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 266:    */     //   243: aload_1
/* 267:    */     //   244: invokespecial 53	org/hibernate/metamodel/source/XsdException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 268:    */     //   247: athrow
/* 269:    */     // Line number table:
/* 270:    */     //   Java source line #138	-> byte code offset #0
/* 271:    */     //   Java source line #139	-> byte code offset #11
/* 272:    */     //   Java source line #140	-> byte code offset #15
/* 273:    */     //   Java source line #143	-> byte code offset #48
/* 274:    */     //   Java source line #145	-> byte code offset #54
/* 275:    */     //   Java source line #146	-> byte code offset #67
/* 276:    */     //   Java source line #147	-> byte code offset #73
/* 277:    */     //   Java source line #157	-> byte code offset #82
/* 278:    */     //   Java source line #161	-> byte code offset #87
/* 279:    */     //   Java source line #159	-> byte code offset #90
/* 280:    */     //   Java source line #160	-> byte code offset #92
/* 281:    */     //   Java source line #161	-> byte code offset #105
/* 282:    */     //   Java source line #149	-> byte code offset #108
/* 283:    */     //   Java source line #150	-> byte code offset #110
/* 284:    */     //   Java source line #152	-> byte code offset #145
/* 285:    */     //   Java source line #153	-> byte code offset #147
/* 286:    */     //   Java source line #156	-> byte code offset #182
/* 287:    */     //   Java source line #157	-> byte code offset #184
/* 288:    */     //   Java source line #161	-> byte code offset #189
/* 289:    */     //   Java source line #159	-> byte code offset #192
/* 290:    */     //   Java source line #160	-> byte code offset #194
/* 291:    */     //   Java source line #161	-> byte code offset #207
/* 292:    */     //   Java source line #164	-> byte code offset #210
/* 293:    */     //   Java source line #165	-> byte code offset #212
/* 294:    */     // Local variable table:
/* 295:    */     //   start	length	slot	name	signature
/* 296:    */     //   0	248	0	this	JaxbProcessor
/* 297:    */     //   0	248	1	schemaName	String
/* 298:    */     //   0	248	2	schemaLanguage	String
/* 299:    */     //   10	219	3	url	java.net.URL
/* 300:    */     //   52	133	4	schemaStream	java.io.InputStream
/* 301:    */     //   210	3	4	e	java.io.IOException
/* 302:    */     //   65	11	5	source	javax.xml.transform.stream.StreamSource
/* 303:    */     //   108	31	5	e	org.xml.sax.SAXException
/* 304:    */     //   145	31	5	e	java.io.IOException
/* 305:    */     //   71	3	6	schemaFactory	javax.xml.validation.SchemaFactory
/* 306:    */     //   90	8	8	e	java.io.IOException
/* 307:    */     //   182	26	9	localObject	Object
/* 308:    */     //   192	8	10	e	java.io.IOException
/* 309:    */     // Exception table:
/* 310:    */     //   from	to	target	type
/* 311:    */     //   82	87	90	java/io/IOException
/* 312:    */     //   54	82	108	org/xml/sax/SAXException
/* 313:    */     //   54	82	145	java/io/IOException
/* 314:    */     //   54	82	182	finally
/* 315:    */     //   108	184	182	finally
/* 316:    */     //   184	189	192	java/io/IOException
/* 317:    */     //   48	105	210	java/io/IOException
/* 318:    */     //   108	210	210	java/io/IOException
/* 319:    */   }
/* 320:    */   
/* 321:    */   static class ContextProvidingValidationEventHandler
/* 322:    */     implements ValidationEventHandler
/* 323:    */   {
/* 324:    */     private int lineNumber;
/* 325:    */     private int columnNumber;
/* 326:    */     private String message;
/* 327:    */     
/* 328:    */     public boolean handleEvent(ValidationEvent validationEvent)
/* 329:    */     {
/* 330:176 */       ValidationEventLocator locator = validationEvent.getLocator();
/* 331:177 */       this.lineNumber = locator.getLineNumber();
/* 332:178 */       this.columnNumber = locator.getColumnNumber();
/* 333:179 */       this.message = validationEvent.getMessage();
/* 334:180 */       return false;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public int getLineNumber()
/* 338:    */     {
/* 339:184 */       return this.lineNumber;
/* 340:    */     }
/* 341:    */     
/* 342:    */     public int getColumnNumber()
/* 343:    */     {
/* 344:188 */       return this.columnNumber;
/* 345:    */     }
/* 346:    */     
/* 347:    */     public String getMessage()
/* 348:    */     {
/* 349:192 */       return this.message;
/* 350:    */     }
/* 351:    */   }
/* 352:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.JaxbProcessor
 * JD-Core Version:    0.7.0.1
 */