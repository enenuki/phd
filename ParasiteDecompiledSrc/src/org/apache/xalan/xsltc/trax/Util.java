/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import javax.xml.parsers.ParserConfigurationException;
/*   6:    */ import javax.xml.parsers.SAXParser;
/*   7:    */ import javax.xml.parsers.SAXParserFactory;
/*   8:    */ import javax.xml.transform.Source;
/*   9:    */ import javax.xml.transform.TransformerConfigurationException;
/*  10:    */ import javax.xml.transform.dom.DOMSource;
/*  11:    */ import javax.xml.transform.sax.SAXSource;
/*  12:    */ import javax.xml.transform.stream.StreamSource;
/*  13:    */ import org.apache.xalan.xsltc.compiler.XSLTC;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  15:    */ import org.w3c.dom.Document;
/*  16:    */ import org.xml.sax.InputSource;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ import org.xml.sax.SAXNotRecognizedException;
/*  19:    */ import org.xml.sax.SAXNotSupportedException;
/*  20:    */ import org.xml.sax.XMLReader;
/*  21:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  22:    */ 
/*  23:    */ public final class Util
/*  24:    */ {
/*  25:    */   public static String baseName(String name)
/*  26:    */   {
/*  27: 56 */     return org.apache.xalan.xsltc.compiler.util.Util.baseName(name);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String noExtName(String name)
/*  31:    */   {
/*  32: 60 */     return org.apache.xalan.xsltc.compiler.util.Util.noExtName(name);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static String toJavaName(String name)
/*  36:    */   {
/*  37: 64 */     return org.apache.xalan.xsltc.compiler.util.Util.toJavaName(name);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static InputSource getInputSource(XSLTC xsltc, Source source)
/*  41:    */     throws TransformerConfigurationException
/*  42:    */   {
/*  43: 76 */     InputSource input = null;
/*  44:    */     
/*  45: 78 */     String systemId = source.getSystemId();
/*  46:    */     try
/*  47:    */     {
/*  48: 82 */       if ((source instanceof SAXSource))
/*  49:    */       {
/*  50: 83 */         SAXSource sax = (SAXSource)source;
/*  51: 84 */         input = sax.getInputSource();
/*  52:    */         try
/*  53:    */         {
/*  54: 87 */           XMLReader reader = sax.getXMLReader();
/*  55: 97 */           if (reader == null) {
/*  56:    */             try
/*  57:    */             {
/*  58: 99 */               reader = XMLReaderFactory.createXMLReader();
/*  59:    */             }
/*  60:    */             catch (Exception e)
/*  61:    */             {
/*  62:    */               try
/*  63:    */               {
/*  64:105 */                 SAXParserFactory parserFactory = SAXParserFactory.newInstance();
/*  65:    */                 
/*  66:107 */                 parserFactory.setNamespaceAware(true);
/*  67:109 */                 if (xsltc.isSecureProcessing()) {
/*  68:    */                   try
/*  69:    */                   {
/*  70:111 */                     parserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  71:    */                   }
/*  72:    */                   catch (SAXException se) {}
/*  73:    */                 }
/*  74:117 */                 reader = parserFactory.newSAXParser().getXMLReader();
/*  75:    */               }
/*  76:    */               catch (ParserConfigurationException pce)
/*  77:    */               {
/*  78:122 */                 throw new TransformerConfigurationException("ParserConfigurationException", pce);
/*  79:    */               }
/*  80:    */             }
/*  81:    */           }
/*  82:127 */           reader.setFeature("http://xml.org/sax/features/namespaces", true);
/*  83:    */           
/*  84:129 */           reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/*  85:    */           
/*  86:    */ 
/*  87:132 */           xsltc.setXMLReader(reader);
/*  88:    */         }
/*  89:    */         catch (SAXNotRecognizedException snre)
/*  90:    */         {
/*  91:134 */           throw new TransformerConfigurationException("SAXNotRecognizedException ", snre);
/*  92:    */         }
/*  93:    */         catch (SAXNotSupportedException snse)
/*  94:    */         {
/*  95:137 */           throw new TransformerConfigurationException("SAXNotSupportedException ", snse);
/*  96:    */         }
/*  97:    */         catch (SAXException se)
/*  98:    */         {
/*  99:140 */           throw new TransformerConfigurationException("SAXException ", se);
/* 100:    */         }
/* 101:    */       }
/* 102:146 */       else if ((source instanceof DOMSource))
/* 103:    */       {
/* 104:147 */         DOMSource domsrc = (DOMSource)source;
/* 105:148 */         Document dom = (Document)domsrc.getNode();
/* 106:149 */         DOM2SAX dom2sax = new DOM2SAX(dom);
/* 107:150 */         xsltc.setXMLReader(dom2sax);
/* 108:    */         
/* 109:    */ 
/* 110:153 */         input = SAXSource.sourceToInputSource(source);
/* 111:154 */         if (input == null) {
/* 112:155 */           input = new InputSource(domsrc.getSystemId());
/* 113:    */         }
/* 114:    */       }
/* 115:159 */       else if ((source instanceof StreamSource))
/* 116:    */       {
/* 117:160 */         StreamSource stream = (StreamSource)source;
/* 118:161 */         InputStream istream = stream.getInputStream();
/* 119:162 */         Reader reader = stream.getReader();
/* 120:163 */         xsltc.setXMLReader(null);
/* 121:166 */         if (istream != null) {
/* 122:167 */           input = new InputSource(istream);
/* 123:169 */         } else if (reader != null) {
/* 124:170 */           input = new InputSource(reader);
/* 125:    */         } else {
/* 126:173 */           input = new InputSource(systemId);
/* 127:    */         }
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:177 */         ErrorMsg err = new ErrorMsg("JAXP_UNKNOWN_SOURCE_ERR");
/* 132:178 */         throw new TransformerConfigurationException(err.toString());
/* 133:    */       }
/* 134:180 */       input.setSystemId(systemId);
/* 135:    */     }
/* 136:    */     catch (NullPointerException e)
/* 137:    */     {
/* 138:183 */       ErrorMsg err = new ErrorMsg("JAXP_NO_SOURCE_ERR", "TransformerFactory.newTemplates()");
/* 139:    */       
/* 140:185 */       throw new TransformerConfigurationException(err.toString());
/* 141:    */     }
/* 142:    */     catch (SecurityException e)
/* 143:    */     {
/* 144:188 */       ErrorMsg err = new ErrorMsg("FILE_ACCESS_ERR", systemId);
/* 145:189 */       throw new TransformerConfigurationException(err.toString());
/* 146:    */     }
/* 147:191 */     return input;
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.Util
 * JD-Core Version:    0.7.0.1
 */