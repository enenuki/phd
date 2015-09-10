/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.parsers.FactoryConfigurationError;
/*   6:    */ import javax.xml.parsers.ParserConfigurationException;
/*   7:    */ import javax.xml.parsers.SAXParser;
/*   8:    */ import javax.xml.parsers.SAXParserFactory;
/*   9:    */ import javax.xml.transform.Source;
/*  10:    */ import javax.xml.transform.SourceLocator;
/*  11:    */ import javax.xml.transform.TransformerException;
/*  12:    */ import javax.xml.transform.URIResolver;
/*  13:    */ import javax.xml.transform.sax.SAXSource;
/*  14:    */ import javax.xml.transform.stream.StreamSource;
/*  15:    */ import org.apache.xml.dtm.DTM;
/*  16:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  17:    */ import org.apache.xml.utils.SystemIDResolver;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ import org.xml.sax.XMLReader;
/*  20:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  21:    */ 
/*  22:    */ public class SourceTreeManager
/*  23:    */ {
/*  24: 48 */   private Vector m_sourceTree = new Vector();
/*  25:    */   URIResolver m_uriResolver;
/*  26:    */   
/*  27:    */   public void reset()
/*  28:    */   {
/*  29: 56 */     this.m_sourceTree = new Vector();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setURIResolver(URIResolver resolver)
/*  33:    */   {
/*  34: 70 */     this.m_uriResolver = resolver;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public URIResolver getURIResolver()
/*  38:    */   {
/*  39: 81 */     return this.m_uriResolver;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String findURIFromDoc(int owner)
/*  43:    */   {
/*  44: 92 */     int n = this.m_sourceTree.size();
/*  45: 94 */     for (int i = 0; i < n; i++)
/*  46:    */     {
/*  47: 96 */       SourceTree sTree = (SourceTree)this.m_sourceTree.elementAt(i);
/*  48: 98 */       if (owner == sTree.m_root) {
/*  49: 99 */         return sTree.m_url;
/*  50:    */       }
/*  51:    */     }
/*  52:102 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Source resolveURI(String base, String urlString, SourceLocator locator)
/*  56:    */     throws TransformerException, IOException
/*  57:    */   {
/*  58:123 */     Source source = null;
/*  59:125 */     if (null != this.m_uriResolver) {
/*  60:127 */       source = this.m_uriResolver.resolve(urlString, base);
/*  61:    */     }
/*  62:130 */     if (null == source)
/*  63:    */     {
/*  64:132 */       String uri = SystemIDResolver.getAbsoluteURI(urlString, base);
/*  65:    */       
/*  66:134 */       source = new StreamSource(uri);
/*  67:    */     }
/*  68:137 */     return source;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void removeDocumentFromCache(int n)
/*  72:    */   {
/*  73:147 */     if (-1 == n) {
/*  74:148 */       return;
/*  75:    */     }
/*  76:149 */     for (int i = this.m_sourceTree.size() - 1; i >= 0; i--)
/*  77:    */     {
/*  78:151 */       SourceTree st = (SourceTree)this.m_sourceTree.elementAt(i);
/*  79:152 */       if ((st != null) && (st.m_root == n))
/*  80:    */       {
/*  81:154 */         this.m_sourceTree.removeElementAt(i);
/*  82:155 */         return;
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void putDocumentInCache(int n, Source source)
/*  88:    */   {
/*  89:172 */     int cachedNode = getNode(source);
/*  90:174 */     if (-1 != cachedNode)
/*  91:    */     {
/*  92:176 */       if (cachedNode != n) {
/*  93:177 */         throw new RuntimeException("Programmer's Error!  putDocumentInCache found reparse of doc: " + source.getSystemId());
/*  94:    */       }
/*  95:181 */       return;
/*  96:    */     }
/*  97:183 */     if (null != source.getSystemId()) {
/*  98:185 */       this.m_sourceTree.addElement(new SourceTree(n, source.getSystemId()));
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getNode(Source source)
/* 103:    */   {
/* 104:203 */     String url = source.getSystemId();
/* 105:205 */     if (null == url) {
/* 106:206 */       return -1;
/* 107:    */     }
/* 108:208 */     int n = this.m_sourceTree.size();
/* 109:211 */     for (int i = 0; i < n; i++)
/* 110:    */     {
/* 111:213 */       SourceTree sTree = (SourceTree)this.m_sourceTree.elementAt(i);
/* 112:217 */       if (url.equals(sTree.m_url)) {
/* 113:218 */         return sTree.m_root;
/* 114:    */       }
/* 115:    */     }
/* 116:222 */     return -1;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getSourceTree(String base, String urlString, SourceLocator locator, XPathContext xctxt)
/* 120:    */     throws TransformerException
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:245 */       Source source = resolveURI(base, urlString, locator);
/* 125:    */       
/* 126:    */ 
/* 127:248 */       return getSourceTree(source, locator, xctxt);
/* 128:    */     }
/* 129:    */     catch (IOException ioe)
/* 130:    */     {
/* 131:252 */       throw new TransformerException(ioe.getMessage(), locator, ioe);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getSourceTree(Source source, SourceLocator locator, XPathContext xctxt)
/* 136:    */     throws TransformerException
/* 137:    */   {
/* 138:276 */     int n = getNode(source);
/* 139:278 */     if (-1 != n) {
/* 140:279 */       return n;
/* 141:    */     }
/* 142:281 */     n = parseToNode(source, locator, xctxt);
/* 143:283 */     if (-1 != n) {
/* 144:284 */       putDocumentInCache(n, source);
/* 145:    */     }
/* 146:286 */     return n;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int parseToNode(Source source, SourceLocator locator, XPathContext xctxt)
/* 150:    */     throws TransformerException
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:306 */       Object xowner = xctxt.getOwnerObject();
/* 155:    */       DTM dtm;
/* 156:308 */       if ((null != xowner) && ((xowner instanceof DTMWSFilter))) {
/* 157:310 */         dtm = xctxt.getDTM(source, false, (DTMWSFilter)xowner, false, true);
/* 158:    */       } else {
/* 159:315 */         dtm = xctxt.getDTM(source, false, null, false, true);
/* 160:    */       }
/* 161:317 */       return dtm.getDocument();
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:322 */       throw new TransformerException(e.getMessage(), locator, e);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static XMLReader getXMLReader(Source inputSource, SourceLocator locator)
/* 170:    */     throws TransformerException
/* 171:    */   {
/* 172:    */     try
/* 173:    */     {
/* 174:347 */       XMLReader reader = (inputSource instanceof SAXSource) ? ((SAXSource)inputSource).getXMLReader() : null;
/* 175:350 */       if (null == reader)
/* 176:    */       {
/* 177:    */         try
/* 178:    */         {
/* 179:353 */           SAXParserFactory factory = SAXParserFactory.newInstance();
/* 180:    */           
/* 181:355 */           factory.setNamespaceAware(true);
/* 182:356 */           SAXParser jaxpParser = factory.newSAXParser();
/* 183:    */           
/* 184:358 */           reader = jaxpParser.getXMLReader();
/* 185:    */         }
/* 186:    */         catch (ParserConfigurationException ex)
/* 187:    */         {
/* 188:361 */           throw new SAXException(ex);
/* 189:    */         }
/* 190:    */         catch (FactoryConfigurationError ex1)
/* 191:    */         {
/* 192:363 */           throw new SAXException(ex1.toString());
/* 193:    */         }
/* 194:    */         catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/* 195:367 */         if (null == reader) {
/* 196:368 */           reader = XMLReaderFactory.createXMLReader();
/* 197:    */         }
/* 198:    */       }
/* 199:    */       try
/* 200:    */       {
/* 201:373 */         reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/* 202:    */       }
/* 203:    */       catch (SAXException se) {}
/* 204:383 */       return reader;
/* 205:    */     }
/* 206:    */     catch (SAXException se)
/* 207:    */     {
/* 208:387 */       throw new TransformerException(se.getMessage(), locator, se);
/* 209:    */     }
/* 210:    */   }
/* 211:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.SourceTreeManager
 * JD-Core Version:    0.7.0.1
 */