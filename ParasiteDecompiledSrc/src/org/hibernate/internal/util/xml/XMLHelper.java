/*   1:    */ package org.hibernate.internal.util.xml;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.List;
/*   5:    */ import org.dom4j.DocumentFactory;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.io.DOMReader;
/*   8:    */ import org.dom4j.io.OutputFormat;
/*   9:    */ import org.dom4j.io.SAXReader;
/*  10:    */ import org.dom4j.io.XMLWriter;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ import org.xml.sax.EntityResolver;
/*  14:    */ import org.xml.sax.ErrorHandler;
/*  15:    */ import org.xml.sax.SAXParseException;
/*  16:    */ 
/*  17:    */ public final class XMLHelper
/*  18:    */ {
/*  19: 47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, XMLHelper.class.getName());
/*  20: 49 */   public static final EntityResolver DEFAULT_DTD_RESOLVER = new DTDEntityResolver();
/*  21:    */   private DOMReader domReader;
/*  22:    */   private SAXReader saxReader;
/*  23:    */   
/*  24:    */   public SAXReader createSAXReader(String file, List errorsList, EntityResolver entityResolver)
/*  25:    */   {
/*  26: 59 */     SAXReader saxReader = resolveSAXReader();
/*  27: 60 */     saxReader.setEntityResolver(entityResolver);
/*  28: 61 */     saxReader.setErrorHandler(new ErrorLogger(file, errorsList, null));
/*  29: 62 */     return saxReader;
/*  30:    */   }
/*  31:    */   
/*  32:    */   private SAXReader resolveSAXReader()
/*  33:    */   {
/*  34: 66 */     if (this.saxReader == null)
/*  35:    */     {
/*  36: 67 */       this.saxReader = new SAXReader();
/*  37: 68 */       this.saxReader.setMergeAdjacentText(true);
/*  38: 69 */       this.saxReader.setValidation(true);
/*  39:    */     }
/*  40: 71 */     return this.saxReader;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public DOMReader createDOMReader()
/*  44:    */   {
/*  45: 78 */     if (this.domReader == null) {
/*  46: 78 */       this.domReader = new DOMReader();
/*  47:    */     }
/*  48: 79 */     return this.domReader;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static class ErrorLogger
/*  52:    */     implements ErrorHandler
/*  53:    */   {
/*  54:    */     private String file;
/*  55:    */     private List<SAXParseException> errors;
/*  56:    */     
/*  57:    */     private ErrorLogger(String file, List errors)
/*  58:    */     {
/*  59: 87 */       this.file = file;
/*  60: 88 */       this.errors = errors;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void error(SAXParseException error)
/*  64:    */     {
/*  65: 91 */       XMLHelper.LOG.parsingXmlErrorForFile(this.file, error.getLineNumber(), error.getMessage());
/*  66: 92 */       this.errors.add(error);
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void fatalError(SAXParseException error)
/*  70:    */     {
/*  71: 95 */       error(error);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void warning(SAXParseException warn)
/*  75:    */     {
/*  76: 98 */       XMLHelper.LOG.parsingXmlWarningForFile(this.file, warn.getLineNumber(), warn.getMessage());
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static Element generateDom4jElement(String elementName)
/*  81:    */   {
/*  82:103 */     return getDocumentFactory().createElement(elementName);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static DocumentFactory getDocumentFactory()
/*  86:    */   {
/*  87:108 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  88:    */     DocumentFactory factory;
/*  89:    */     try
/*  90:    */     {
/*  91:111 */       Thread.currentThread().setContextClassLoader(XMLHelper.class.getClassLoader());
/*  92:112 */       factory = DocumentFactory.getInstance();
/*  93:    */     }
/*  94:    */     finally
/*  95:    */     {
/*  96:115 */       Thread.currentThread().setContextClassLoader(cl);
/*  97:    */     }
/*  98:117 */     return factory;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void dump(Element element)
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:123 */       OutputFormat outformat = OutputFormat.createPrettyPrint();
/* 106:124 */       XMLWriter writer = new XMLWriter(System.out, outformat);
/* 107:125 */       writer.write(element);
/* 108:126 */       writer.flush();
/* 109:127 */       System.out.println("");
/* 110:    */     }
/* 111:    */     catch (Throwable t)
/* 112:    */     {
/* 113:131 */       System.out.println(element.asXML());
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.XMLHelper
 * JD-Core Version:    0.7.0.1
 */