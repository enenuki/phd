/*   1:    */ package org.hibernate.internal.util.xml;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import org.dom4j.Document;
/*   5:    */ import org.dom4j.io.SAXReader;
/*   6:    */ import org.hibernate.InvalidMappingException;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.jboss.logging.Logger;
/*   9:    */ import org.xml.sax.EntityResolver;
/*  10:    */ import org.xml.sax.InputSource;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ 
/*  13:    */ public class MappingReader
/*  14:    */ {
/*  15: 45 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MappingReader.class.getName());
/*  16:    */   public static final String ASSUMED_ORM_XSD_VERSION = "2.0";
/*  17: 48 */   public static final MappingReader INSTANCE = new MappingReader();
/*  18:    */   
/*  19:    */   public XmlDocument readMappingDocument(EntityResolver entityResolver, InputSource source, Origin origin)
/*  20:    */   {
/*  21: 64 */     ErrorLogger errorHandler = new ErrorLogger();
/*  22:    */     
/*  23: 66 */     SAXReader saxReader = new SAXReader();
/*  24: 67 */     saxReader.setEntityResolver(entityResolver);
/*  25: 68 */     saxReader.setErrorHandler(errorHandler);
/*  26: 69 */     saxReader.setMergeAdjacentText(true);
/*  27: 70 */     saxReader.setValidation(true);
/*  28:    */     
/*  29: 72 */     Document document = null;
/*  30:    */     try
/*  31:    */     {
/*  32: 75 */       setValidationFor(saxReader, "orm_2_0.xsd");
/*  33: 76 */       document = saxReader.read(source);
/*  34: 77 */       if (errorHandler.getError() != null) {
/*  35: 78 */         throw errorHandler.getError();
/*  36:    */       }
/*  37: 80 */       return new XmlDocumentImpl(document, origin.getType(), origin.getName());
/*  38:    */     }
/*  39:    */     catch (Exception orm2Problem)
/*  40:    */     {
/*  41: 83 */       if (LOG.isDebugEnabled()) {
/*  42: 84 */         LOG.debugf("Problem parsing XML using orm 2 xsd : %s", orm2Problem.getMessage());
/*  43:    */       }
/*  44: 86 */       Exception failure = orm2Problem;
/*  45: 87 */       errorHandler.reset();
/*  46: 89 */       if (document != null) {
/*  47:    */         try
/*  48:    */         {
/*  49: 92 */           setValidationFor(saxReader, "orm_1_0.xsd");
/*  50: 93 */           document = saxReader.read(new StringReader(document.asXML()));
/*  51: 94 */           if (errorHandler.getError() != null) {
/*  52: 95 */             throw errorHandler.getError();
/*  53:    */           }
/*  54: 97 */           return new XmlDocumentImpl(document, origin.getType(), origin.getName());
/*  55:    */         }
/*  56:    */         catch (Exception orm1Problem)
/*  57:    */         {
/*  58:100 */           if (LOG.isDebugEnabled()) {
/*  59:101 */             LOG.debugf("Problem parsing XML using orm 1 xsd : %s", orm1Problem.getMessage());
/*  60:    */           }
/*  61:    */         }
/*  62:    */       }
/*  63:106 */       throw new InvalidMappingException("Unable to read XML", origin.getType(), origin.getName(), failure);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void setValidationFor(SAXReader saxReader, String xsd)
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:111 */       saxReader.setFeature("http://apache.org/xml/features/validation/schema", true);
/*  72:    */       
/*  73:    */ 
/*  74:114 */       saxReader.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", "http://java.sun.com/xml/ns/persistence/orm " + xsd);
/*  75:    */     }
/*  76:    */     catch (SAXException e)
/*  77:    */     {
/*  78:120 */       saxReader.setValidation(false);
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.MappingReader
 * JD-Core Version:    0.7.0.1
 */