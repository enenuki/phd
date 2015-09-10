/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.xml.parsers.FactoryConfigurationError;
/*   5:    */ import javax.xml.parsers.ParserConfigurationException;
/*   6:    */ import javax.xml.parsers.SAXParser;
/*   7:    */ import javax.xml.parsers.SAXParserFactory;
/*   8:    */ import javax.xml.transform.ErrorListener;
/*   9:    */ import javax.xml.transform.Templates;
/*  10:    */ import javax.xml.transform.Transformer;
/*  11:    */ import javax.xml.transform.TransformerConfigurationException;
/*  12:    */ import javax.xml.transform.sax.SAXResult;
/*  13:    */ import org.apache.xml.utils.XMLReaderManager;
/*  14:    */ import org.xml.sax.ContentHandler;
/*  15:    */ import org.xml.sax.InputSource;
/*  16:    */ import org.xml.sax.SAXException;
/*  17:    */ import org.xml.sax.XMLReader;
/*  18:    */ import org.xml.sax.helpers.XMLFilterImpl;
/*  19:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  20:    */ 
/*  21:    */ public class TrAXFilter
/*  22:    */   extends XMLFilterImpl
/*  23:    */ {
/*  24:    */   private Templates _templates;
/*  25:    */   private TransformerImpl _transformer;
/*  26:    */   private TransformerHandlerImpl _transformerHandler;
/*  27:    */   
/*  28:    */   public TrAXFilter(Templates templates)
/*  29:    */     throws TransformerConfigurationException
/*  30:    */   {
/*  31: 60 */     this._templates = templates;
/*  32: 61 */     this._transformer = ((TransformerImpl)templates.newTransformer());
/*  33: 62 */     this._transformerHandler = new TransformerHandlerImpl(this._transformer);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Transformer getTransformer()
/*  37:    */   {
/*  38: 66 */     return this._transformer;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void createParent()
/*  42:    */     throws SAXException
/*  43:    */   {
/*  44: 70 */     XMLReader parent = null;
/*  45:    */     try
/*  46:    */     {
/*  47: 72 */       SAXParserFactory pfactory = SAXParserFactory.newInstance();
/*  48: 73 */       pfactory.setNamespaceAware(true);
/*  49: 75 */       if (this._transformer.isSecureProcessing()) {
/*  50:    */         try
/*  51:    */         {
/*  52: 77 */           pfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  53:    */         }
/*  54:    */         catch (SAXException e) {}
/*  55:    */       }
/*  56: 82 */       SAXParser saxparser = pfactory.newSAXParser();
/*  57: 83 */       parent = saxparser.getXMLReader();
/*  58:    */     }
/*  59:    */     catch (ParserConfigurationException e)
/*  60:    */     {
/*  61: 86 */       throw new SAXException(e);
/*  62:    */     }
/*  63:    */     catch (FactoryConfigurationError e)
/*  64:    */     {
/*  65: 89 */       throw new SAXException(e.toString());
/*  66:    */     }
/*  67: 92 */     if (parent == null) {
/*  68: 93 */       parent = XMLReaderFactory.createXMLReader();
/*  69:    */     }
/*  70: 97 */     setParent(parent);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void parse(InputSource input)
/*  74:    */     throws SAXException, IOException
/*  75:    */   {
/*  76:102 */     XMLReader managedReader = null;
/*  77:    */     try
/*  78:    */     {
/*  79:105 */       if (getParent() == null) {
/*  80:    */         try
/*  81:    */         {
/*  82:107 */           managedReader = XMLReaderManager.getInstance().getXMLReader();
/*  83:    */           
/*  84:109 */           setParent(managedReader);
/*  85:    */         }
/*  86:    */         catch (SAXException e)
/*  87:    */         {
/*  88:111 */           throw new SAXException(e.toString());
/*  89:    */         }
/*  90:    */       }
/*  91:116 */       getParent().parse(input);
/*  92:    */     }
/*  93:    */     finally
/*  94:    */     {
/*  95:118 */       if (managedReader != null) {
/*  96:119 */         XMLReaderManager.getInstance().releaseXMLReader(managedReader);
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void parse(String systemId)
/* 102:    */     throws SAXException, IOException
/* 103:    */   {
/* 104:126 */     parse(new InputSource(systemId));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setContentHandler(ContentHandler handler)
/* 108:    */   {
/* 109:131 */     this._transformerHandler.setResult(new SAXResult(handler));
/* 110:132 */     if (getParent() == null) {
/* 111:    */       try
/* 112:    */       {
/* 113:134 */         createParent();
/* 114:    */       }
/* 115:    */       catch (SAXException e)
/* 116:    */       {
/* 117:137 */         return;
/* 118:    */       }
/* 119:    */     }
/* 120:140 */     getParent().setContentHandler(this._transformerHandler);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setErrorListener(ErrorListener handler) {}
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TrAXFilter
 * JD-Core Version:    0.7.0.1
 */