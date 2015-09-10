/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.xml.parsers.FactoryConfigurationError;
/*   5:    */ import javax.xml.parsers.ParserConfigurationException;
/*   6:    */ import javax.xml.parsers.SAXParser;
/*   7:    */ import javax.xml.parsers.SAXParserFactory;
/*   8:    */ import javax.xml.transform.ErrorListener;
/*   9:    */ import javax.xml.transform.Templates;
/*  10:    */ import javax.xml.transform.TransformerConfigurationException;
/*  11:    */ import org.apache.xalan.res.XSLMessages;
/*  12:    */ import org.apache.xalan.templates.StylesheetRoot;
/*  13:    */ import org.xml.sax.ContentHandler;
/*  14:    */ import org.xml.sax.InputSource;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ import org.xml.sax.XMLReader;
/*  17:    */ import org.xml.sax.helpers.XMLFilterImpl;
/*  18:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  19:    */ 
/*  20:    */ public class TrAXFilter
/*  21:    */   extends XMLFilterImpl
/*  22:    */ {
/*  23:    */   private Templates m_templates;
/*  24:    */   private TransformerImpl m_transformer;
/*  25:    */   
/*  26:    */   public TrAXFilter(Templates templates)
/*  27:    */     throws TransformerConfigurationException
/*  28:    */   {
/*  29: 60 */     this.m_templates = templates;
/*  30: 61 */     this.m_transformer = ((TransformerImpl)templates.newTransformer());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public TransformerImpl getTransformer()
/*  34:    */   {
/*  35: 69 */     return this.m_transformer;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setParent(XMLReader parent)
/*  39:    */   {
/*  40: 86 */     super.setParent(parent);
/*  41: 88 */     if (null != parent.getContentHandler()) {
/*  42: 89 */       setContentHandler(parent.getContentHandler());
/*  43:    */     }
/*  44: 94 */     setupParse();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void parse(InputSource input)
/*  48:    */     throws SAXException, IOException
/*  49:    */   {
/*  50:111 */     if (null == getParent())
/*  51:    */     {
/*  52:113 */       XMLReader reader = null;
/*  53:    */       try
/*  54:    */       {
/*  55:117 */         SAXParserFactory factory = SAXParserFactory.newInstance();
/*  56:    */         
/*  57:119 */         factory.setNamespaceAware(true);
/*  58:121 */         if (this.m_transformer.getStylesheet().isSecureProcessing()) {
/*  59:    */           try
/*  60:    */           {
/*  61:123 */             factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  62:    */           }
/*  63:    */           catch (SAXException se) {}
/*  64:    */         }
/*  65:128 */         SAXParser jaxpParser = factory.newSAXParser();
/*  66:    */         
/*  67:130 */         reader = jaxpParser.getXMLReader();
/*  68:    */       }
/*  69:    */       catch (ParserConfigurationException ex)
/*  70:    */       {
/*  71:133 */         throw new SAXException(ex);
/*  72:    */       }
/*  73:    */       catch (FactoryConfigurationError ex1)
/*  74:    */       {
/*  75:135 */         throw new SAXException(ex1.toString());
/*  76:    */       }
/*  77:    */       catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  78:    */       XMLReader parent;
/*  79:141 */       if (reader == null) {
/*  80:142 */         parent = XMLReaderFactory.createXMLReader();
/*  81:    */       } else {
/*  82:144 */         parent = reader;
/*  83:    */       }
/*  84:    */       try
/*  85:    */       {
/*  86:147 */         parent.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  87:    */       }
/*  88:    */       catch (SAXException se) {}
/*  89:152 */       setParent(parent);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:157 */       setupParse();
/*  94:    */     }
/*  95:159 */     if (null == this.m_transformer.getContentHandler()) {
/*  96:161 */       throw new SAXException(XSLMessages.createMessage("ER_CANNOT_CALL_PARSE", null));
/*  97:    */     }
/*  98:164 */     getParent().parse(input);
/*  99:165 */     Exception e = this.m_transformer.getExceptionThrown();
/* 100:166 */     if (null != e)
/* 101:    */     {
/* 102:168 */       if ((e instanceof SAXException)) {
/* 103:169 */         throw ((SAXException)e);
/* 104:    */       }
/* 105:171 */       throw new SAXException(e);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void parse(String systemId)
/* 110:    */     throws SAXException, IOException
/* 111:    */   {
/* 112:189 */     parse(new InputSource(systemId));
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void setupParse()
/* 116:    */   {
/* 117:202 */     XMLReader p = getParent();
/* 118:203 */     if (p == null) {
/* 119:204 */       throw new NullPointerException(XSLMessages.createMessage("ER_NO_PARENT_FOR_FILTER", null));
/* 120:    */     }
/* 121:207 */     ContentHandler ch = this.m_transformer.getInputContentHandler();
/* 122:    */     
/* 123:    */ 
/* 124:210 */     p.setContentHandler(ch);
/* 125:211 */     p.setEntityResolver(this);
/* 126:212 */     p.setDTDHandler(this);
/* 127:213 */     p.setErrorHandler(this);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setContentHandler(ContentHandler handler)
/* 131:    */   {
/* 132:226 */     this.m_transformer.setContentHandler(handler);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setErrorListener(ErrorListener handler)
/* 136:    */   {
/* 137:232 */     this.m_transformer.setErrorListener(handler);
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TrAXFilter
 * JD-Core Version:    0.7.0.1
 */