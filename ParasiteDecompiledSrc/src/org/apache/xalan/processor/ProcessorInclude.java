/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.xml.parsers.FactoryConfigurationError;
/*   5:    */ import javax.xml.parsers.ParserConfigurationException;
/*   6:    */ import javax.xml.parsers.SAXParser;
/*   7:    */ import javax.xml.parsers.SAXParserFactory;
/*   8:    */ import javax.xml.transform.Source;
/*   9:    */ import javax.xml.transform.TransformerException;
/*  10:    */ import javax.xml.transform.URIResolver;
/*  11:    */ import javax.xml.transform.dom.DOMSource;
/*  12:    */ import javax.xml.transform.sax.SAXSource;
/*  13:    */ import javax.xml.transform.stream.StreamSource;
/*  14:    */ import org.apache.xalan.res.XSLMessages;
/*  15:    */ import org.apache.xml.utils.DOM2Helper;
/*  16:    */ import org.apache.xml.utils.SystemIDResolver;
/*  17:    */ import org.apache.xml.utils.TreeWalker;
/*  18:    */ import org.w3c.dom.Node;
/*  19:    */ import org.xml.sax.Attributes;
/*  20:    */ import org.xml.sax.InputSource;
/*  21:    */ import org.xml.sax.SAXException;
/*  22:    */ import org.xml.sax.XMLReader;
/*  23:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  24:    */ 
/*  25:    */ public class ProcessorInclude
/*  26:    */   extends XSLTElementProcessor
/*  27:    */ {
/*  28:    */   static final long serialVersionUID = -4570078731972673481L;
/*  29: 60 */   private String m_href = null;
/*  30:    */   
/*  31:    */   public String getHref()
/*  32:    */   {
/*  33: 70 */     return this.m_href;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setHref(String baseIdent)
/*  37:    */   {
/*  38: 81 */     this.m_href = baseIdent;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected int getStylesheetType()
/*  42:    */   {
/*  43: 91 */     return 2;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected String getStylesheetInclErr()
/*  47:    */   {
/*  48:101 */     return "ER_STYLESHEET_INCLUDES_ITSELF";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  52:    */     throws SAXException
/*  53:    */   {
/*  54:129 */     setPropertiesFromAttributes(handler, rawName, attributes, this);
/*  55:    */     try
/*  56:    */     {
/*  57:135 */       Source sourceFromURIResolver = getSourceFromUriResolver(handler);
/*  58:    */       
/*  59:137 */       String hrefUrl = getBaseURIOfIncludedStylesheet(handler, sourceFromURIResolver);
/*  60:139 */       if (handler.importStackContains(hrefUrl)) {
/*  61:141 */         throw new SAXException(XSLMessages.createMessage(getStylesheetInclErr(), new Object[] { hrefUrl }));
/*  62:    */       }
/*  63:148 */       handler.pushImportURL(hrefUrl);
/*  64:149 */       handler.pushImportSource(sourceFromURIResolver);
/*  65:    */       
/*  66:151 */       int savedStylesheetType = handler.getStylesheetType();
/*  67:    */       
/*  68:153 */       handler.setStylesheetType(getStylesheetType());
/*  69:154 */       handler.pushNewNamespaceSupport();
/*  70:    */       try
/*  71:    */       {
/*  72:158 */         parse(handler, uri, localName, rawName, attributes);
/*  73:    */       }
/*  74:    */       finally
/*  75:    */       {
/*  76:162 */         handler.setStylesheetType(savedStylesheetType);
/*  77:163 */         handler.popImportURL();
/*  78:164 */         handler.popImportSource();
/*  79:165 */         handler.popNamespaceSupport();
/*  80:    */       }
/*  81:    */     }
/*  82:    */     catch (TransformerException te)
/*  83:    */     {
/*  84:170 */       handler.error(te.getMessage(), te);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void parse(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  89:    */     throws SAXException
/*  90:    */   {
/*  91:193 */     TransformerFactoryImpl processor = handler.getStylesheetProcessor();
/*  92:194 */     URIResolver uriresolver = processor.getURIResolver();
/*  93:    */     try
/*  94:    */     {
/*  95:198 */       Source source = null;
/*  96:206 */       if (null != uriresolver)
/*  97:    */       {
/*  98:212 */         source = handler.peekSourceFromURIResolver();
/*  99:214 */         if ((null != source) && ((source instanceof DOMSource)))
/* 100:    */         {
/* 101:216 */           Node node = ((DOMSource)source).getNode();
/* 102:    */           
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:224 */           String systemId = handler.peekImportURL();
/* 110:228 */           if (systemId != null) {
/* 111:229 */             handler.pushBaseIndentifier(systemId);
/* 112:    */           }
/* 113:231 */           TreeWalker walker = new TreeWalker(handler, new DOM2Helper(), systemId);
/* 114:    */           try
/* 115:    */           {
/* 116:235 */             walker.traverse(node);
/* 117:    */           }
/* 118:    */           catch (SAXException se)
/* 119:    */           {
/* 120:239 */             throw new TransformerException(se);
/* 121:    */           }
/* 122:241 */           if (systemId != null) {
/* 123:242 */             handler.popBaseIndentifier();
/* 124:    */           }
/* 125:243 */           return;
/* 126:    */         }
/* 127:    */       }
/* 128:247 */       if (null == source)
/* 129:    */       {
/* 130:249 */         String absURL = SystemIDResolver.getAbsoluteURI(getHref(), handler.getBaseIdentifier());
/* 131:    */         
/* 132:    */ 
/* 133:252 */         source = new StreamSource(absURL);
/* 134:    */       }
/* 135:256 */       source = processSource(handler, source);
/* 136:    */       
/* 137:258 */       XMLReader reader = null;
/* 138:260 */       if ((source instanceof SAXSource))
/* 139:    */       {
/* 140:262 */         SAXSource saxSource = (SAXSource)source;
/* 141:263 */         reader = saxSource.getXMLReader();
/* 142:    */       }
/* 143:266 */       InputSource inputSource = SAXSource.sourceToInputSource(source);
/* 144:268 */       if (null == reader) {
/* 145:    */         try
/* 146:    */         {
/* 147:272 */           SAXParserFactory factory = SAXParserFactory.newInstance();
/* 148:    */           
/* 149:274 */           factory.setNamespaceAware(true);
/* 150:276 */           if (handler.getStylesheetProcessor().isSecureProcessing()) {
/* 151:    */             try
/* 152:    */             {
/* 153:280 */               factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/* 154:    */             }
/* 155:    */             catch (SAXException se) {}
/* 156:    */           }
/* 157:285 */           SAXParser jaxpParser = factory.newSAXParser();
/* 158:    */           
/* 159:287 */           reader = jaxpParser.getXMLReader();
/* 160:    */         }
/* 161:    */         catch (ParserConfigurationException ex)
/* 162:    */         {
/* 163:290 */           throw new SAXException(ex);
/* 164:    */         }
/* 165:    */         catch (FactoryConfigurationError ex1)
/* 166:    */         {
/* 167:292 */           throw new SAXException(ex1.toString());
/* 168:    */         }
/* 169:    */         catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/* 170:    */       }
/* 171:299 */       if (null == reader) {
/* 172:300 */         reader = XMLReaderFactory.createXMLReader();
/* 173:    */       }
/* 174:302 */       if (null != reader)
/* 175:    */       {
/* 176:304 */         reader.setContentHandler(handler);
/* 177:    */         
/* 178:    */ 
/* 179:    */ 
/* 180:308 */         handler.pushBaseIndentifier(inputSource.getSystemId());
/* 181:    */         try
/* 182:    */         {
/* 183:312 */           reader.parse(inputSource);
/* 184:    */         }
/* 185:    */         finally
/* 186:    */         {
/* 187:316 */           handler.popBaseIndentifier();
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */     catch (IOException ioe)
/* 192:    */     {
/* 193:322 */       handler.error("ER_IOEXCEPTION", new Object[] { getHref() }, ioe);
/* 194:    */     }
/* 195:    */     catch (TransformerException te)
/* 196:    */     {
/* 197:327 */       handler.error(te.getMessage(), te);
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected Source processSource(StylesheetHandler handler, Source source)
/* 202:    */   {
/* 203:340 */     return source;
/* 204:    */   }
/* 205:    */   
/* 206:    */   private Source getSourceFromUriResolver(StylesheetHandler handler)
/* 207:    */     throws TransformerException
/* 208:    */   {
/* 209:350 */     Source s = null;
/* 210:351 */     TransformerFactoryImpl processor = handler.getStylesheetProcessor();
/* 211:352 */     URIResolver uriresolver = processor.getURIResolver();
/* 212:353 */     if (uriresolver != null)
/* 213:    */     {
/* 214:354 */       String href = getHref();
/* 215:355 */       String base = handler.getBaseIdentifier();
/* 216:356 */       s = uriresolver.resolve(href, base);
/* 217:    */     }
/* 218:359 */     return s;
/* 219:    */   }
/* 220:    */   
/* 221:    */   private String getBaseURIOfIncludedStylesheet(StylesheetHandler handler, Source s)
/* 222:    */     throws TransformerException
/* 223:    */   {
/* 224:    */     String idFromUriResolverSource;
/* 225:    */     String baseURI;
/* 226:380 */     if ((s != null) && ((idFromUriResolverSource = s.getSystemId()) != null)) {
/* 227:383 */       baseURI = idFromUriResolverSource;
/* 228:    */     } else {
/* 229:390 */       baseURI = SystemIDResolver.getAbsoluteURI(getHref(), handler.getBaseIdentifier());
/* 230:    */     }
/* 231:394 */     return baseURI;
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorInclude
 * JD-Core Version:    0.7.0.1
 */