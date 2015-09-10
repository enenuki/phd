/*   1:    */ package org.dom4j.datatype;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.Document;
/*   6:    */ import org.dom4j.DocumentFactory;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.Namespace;
/*   9:    */ import org.dom4j.QName;
/*  10:    */ import org.dom4j.io.SAXReader;
/*  11:    */ import org.xml.sax.EntityResolver;
/*  12:    */ import org.xml.sax.InputSource;
/*  13:    */ 
/*  14:    */ public class DatatypeDocumentFactory
/*  15:    */   extends DocumentFactory
/*  16:    */ {
/*  17:    */   private static final boolean DO_INTERN_QNAME = false;
/*  18: 36 */   protected static transient DatatypeDocumentFactory singleton = new DatatypeDocumentFactory();
/*  19: 39 */   private static final Namespace XSI_NAMESPACE = Namespace.get("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*  20: 42 */   private static final QName XSI_SCHEMA_LOCATION = QName.get("schemaLocation", XSI_NAMESPACE);
/*  21: 45 */   private static final QName XSI_NO_SCHEMA_LOCATION = QName.get("noNamespaceSchemaLocation", XSI_NAMESPACE);
/*  22:    */   private SchemaParser schemaBuilder;
/*  23: 52 */   private SAXReader xmlSchemaReader = new SAXReader();
/*  24: 55 */   private boolean autoLoadSchema = true;
/*  25:    */   
/*  26:    */   public DatatypeDocumentFactory()
/*  27:    */   {
/*  28: 58 */     this.schemaBuilder = new SchemaParser(this);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static DocumentFactory getInstance()
/*  32:    */   {
/*  33: 69 */     return singleton;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void loadSchema(Document schemaDocument)
/*  37:    */   {
/*  38: 80 */     this.schemaBuilder.build(schemaDocument);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void loadSchema(Document schemaDocument, Namespace targetNamespace)
/*  42:    */   {
/*  43: 84 */     this.schemaBuilder.build(schemaDocument, targetNamespace);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DatatypeElementFactory getElementFactory(QName elementQName)
/*  47:    */   {
/*  48: 97 */     DatatypeElementFactory result = null;
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:103 */     DocumentFactory factory = elementQName.getDocumentFactory();
/*  55:104 */     if ((factory instanceof DatatypeElementFactory)) {
/*  56:105 */       result = (DatatypeElementFactory)factory;
/*  57:    */     }
/*  58:108 */     return result;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Attribute createAttribute(Element owner, QName qname, String value)
/*  62:    */   {
/*  63:114 */     if ((this.autoLoadSchema) && (qname.equals(XSI_NO_SCHEMA_LOCATION)))
/*  64:    */     {
/*  65:115 */       Document document = owner != null ? owner.getDocument() : null;
/*  66:116 */       loadSchema(document, value);
/*  67:    */     }
/*  68:117 */     else if ((this.autoLoadSchema) && (qname.equals(XSI_SCHEMA_LOCATION)))
/*  69:    */     {
/*  70:118 */       Document document = owner != null ? owner.getDocument() : null;
/*  71:119 */       String uri = value.substring(0, value.indexOf(' '));
/*  72:120 */       Namespace namespace = owner.getNamespaceForURI(uri);
/*  73:121 */       loadSchema(document, value.substring(value.indexOf(' ') + 1), namespace);
/*  74:    */     }
/*  75:125 */     return super.createAttribute(owner, qname, value);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void loadSchema(Document document, String schemaInstanceURI)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:132 */       EntityResolver resolver = document.getEntityResolver();
/*  83:134 */       if (resolver == null)
/*  84:    */       {
/*  85:135 */         String msg = "No EntityResolver available for resolving URI: ";
/*  86:136 */         throw new InvalidSchemaException(msg + schemaInstanceURI);
/*  87:    */       }
/*  88:139 */       InputSource inputSource = resolver.resolveEntity(null, schemaInstanceURI);
/*  89:142 */       if (resolver == null) {
/*  90:143 */         throw new InvalidSchemaException("Could not resolve the URI: " + schemaInstanceURI);
/*  91:    */       }
/*  92:147 */       Document schemaDocument = this.xmlSchemaReader.read(inputSource);
/*  93:148 */       loadSchema(schemaDocument);
/*  94:    */     }
/*  95:    */     catch (Exception e)
/*  96:    */     {
/*  97:150 */       System.out.println("Failed to load schema: " + schemaInstanceURI);
/*  98:151 */       System.out.println("Caught: " + e);
/*  99:152 */       e.printStackTrace();
/* 100:153 */       throw new InvalidSchemaException("Failed to load schema: " + schemaInstanceURI);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void loadSchema(Document document, String schemaInstanceURI, Namespace namespace)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:161 */       EntityResolver resolver = document.getEntityResolver();
/* 109:163 */       if (resolver == null)
/* 110:    */       {
/* 111:164 */         String msg = "No EntityResolver available for resolving URI: ";
/* 112:165 */         throw new InvalidSchemaException(msg + schemaInstanceURI);
/* 113:    */       }
/* 114:168 */       InputSource inputSource = resolver.resolveEntity(null, schemaInstanceURI);
/* 115:171 */       if (resolver == null) {
/* 116:172 */         throw new InvalidSchemaException("Could not resolve the URI: " + schemaInstanceURI);
/* 117:    */       }
/* 118:176 */       Document schemaDocument = this.xmlSchemaReader.read(inputSource);
/* 119:177 */       loadSchema(schemaDocument, namespace);
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:179 */       System.out.println("Failed to load schema: " + schemaInstanceURI);
/* 124:180 */       System.out.println("Caught: " + e);
/* 125:181 */       e.printStackTrace();
/* 126:182 */       throw new InvalidSchemaException("Failed to load schema: " + schemaInstanceURI);
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.datatype.DatatypeDocumentFactory
 * JD-Core Version:    0.7.0.1
 */