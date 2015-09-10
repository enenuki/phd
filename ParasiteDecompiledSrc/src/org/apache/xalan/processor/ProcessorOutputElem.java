/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   7:    */ import org.apache.xalan.templates.OutputProperties;
/*   8:    */ import org.apache.xalan.templates.Stylesheet;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xml.utils.SystemIDResolver;
/*  11:    */ import org.xml.sax.Attributes;
/*  12:    */ import org.xml.sax.SAXException;
/*  13:    */ 
/*  14:    */ class ProcessorOutputElem
/*  15:    */   extends XSLTElementProcessor
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 3513742319582547590L;
/*  18:    */   private OutputProperties m_outputProperties;
/*  19:    */   
/*  20:    */   public void setCdataSectionElements(Vector newValue)
/*  21:    */   {
/*  22: 54 */     this.m_outputProperties.setQNameProperties("cdata-section-elements", newValue);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setDoctypePublic(String newValue)
/*  26:    */   {
/*  27: 64 */     this.m_outputProperties.setProperty("doctype-public", newValue);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setDoctypeSystem(String newValue)
/*  31:    */   {
/*  32: 74 */     this.m_outputProperties.setProperty("doctype-system", newValue);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setEncoding(String newValue)
/*  36:    */   {
/*  37: 84 */     this.m_outputProperties.setProperty("encoding", newValue);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setIndent(boolean newValue)
/*  41:    */   {
/*  42: 94 */     this.m_outputProperties.setBooleanProperty("indent", newValue);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setMediaType(String newValue)
/*  46:    */   {
/*  47:104 */     this.m_outputProperties.setProperty("media-type", newValue);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setMethod(QName newValue)
/*  51:    */   {
/*  52:114 */     this.m_outputProperties.setQNameProperty("method", newValue);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setOmitXmlDeclaration(boolean newValue)
/*  56:    */   {
/*  57:124 */     this.m_outputProperties.setBooleanProperty("omit-xml-declaration", newValue);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setStandalone(boolean newValue)
/*  61:    */   {
/*  62:134 */     this.m_outputProperties.setBooleanProperty("standalone", newValue);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setVersion(String newValue)
/*  66:    */   {
/*  67:144 */     this.m_outputProperties.setProperty("version", newValue);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setForeignAttr(String attrUri, String attrLocalName, String attrRawName, String attrValue)
/*  71:    */   {
/*  72:153 */     QName key = new QName(attrUri, attrLocalName);
/*  73:154 */     this.m_outputProperties.setProperty(key, attrValue);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addLiteralResultAttribute(String attrUri, String attrLocalName, String attrRawName, String attrValue)
/*  77:    */   {
/*  78:163 */     QName key = new QName(attrUri, attrLocalName);
/*  79:164 */     this.m_outputProperties.setProperty(key, attrValue);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  83:    */     throws SAXException
/*  84:    */   {
/*  85:190 */     this.m_outputProperties = new OutputProperties();
/*  86:    */     
/*  87:192 */     this.m_outputProperties.setDOMBackPointer(handler.getOriginatingNode());
/*  88:193 */     this.m_outputProperties.setLocaterInfo(handler.getLocator());
/*  89:194 */     this.m_outputProperties.setUid(handler.nextUid());
/*  90:195 */     setPropertiesFromAttributes(handler, rawName, attributes, this);
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:199 */     String entitiesFileName = (String)this.m_outputProperties.getProperties().get("{http://xml.apache.org/xalan}entities");
/*  95:202 */     if (null != entitiesFileName) {
/*  96:    */       try
/*  97:    */       {
/*  98:206 */         String absURL = SystemIDResolver.getAbsoluteURI(entitiesFileName, handler.getBaseIdentifier());
/*  99:    */         
/* 100:208 */         this.m_outputProperties.getProperties().put("{http://xml.apache.org/xalan}entities", absURL);
/* 101:    */       }
/* 102:    */       catch (TransformerException te)
/* 103:    */       {
/* 104:212 */         handler.error(te.getMessage(), te);
/* 105:    */       }
/* 106:    */     }
/* 107:216 */     handler.getStylesheet().setOutput(this.m_outputProperties);
/* 108:    */     
/* 109:218 */     ElemTemplateElement parent = handler.getElemTemplateElement();
/* 110:219 */     parent.appendChild(this.m_outputProperties);
/* 111:    */     
/* 112:221 */     this.m_outputProperties = null;
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorOutputElem
 * JD-Core Version:    0.7.0.1
 */