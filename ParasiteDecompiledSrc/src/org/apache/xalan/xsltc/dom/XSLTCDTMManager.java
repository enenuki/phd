/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.transform.Source;
/*   5:    */ import javax.xml.transform.dom.DOMSource;
/*   6:    */ import javax.xml.transform.sax.SAXSource;
/*   7:    */ import javax.xml.transform.stream.StreamSource;
/*   8:    */ import org.apache.xalan.xsltc.trax.DOM2SAX;
/*   9:    */ import org.apache.xml.dtm.DTM;
/*  10:    */ import org.apache.xml.dtm.DTMException;
/*  11:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  12:    */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*  13:    */ import org.apache.xml.res.XMLMessages;
/*  14:    */ import org.apache.xml.utils.SystemIDResolver;
/*  15:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  16:    */ import org.w3c.dom.Node;
/*  17:    */ import org.xml.sax.InputSource;
/*  18:    */ import org.xml.sax.SAXNotRecognizedException;
/*  19:    */ import org.xml.sax.SAXNotSupportedException;
/*  20:    */ import org.xml.sax.XMLReader;
/*  21:    */ 
/*  22:    */ public class XSLTCDTMManager
/*  23:    */   extends DTMManagerDefault
/*  24:    */ {
/*  25:    */   private static final String DEFAULT_CLASS_NAME = "org.apache.xalan.xsltc.dom.XSLTCDTMManager";
/*  26:    */   private static final String DEFAULT_PROP_NAME = "org.apache.xalan.xsltc.dom.XSLTCDTMManager";
/*  27:    */   private static final boolean DUMPTREE = false;
/*  28:    */   private static final boolean DEBUG = false;
/*  29:    */   
/*  30:    */   public static XSLTCDTMManager newInstance()
/*  31:    */   {
/*  32: 78 */     return new XSLTCDTMManager();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Class getDTMManagerClass()
/*  36:    */   {
/*  37: 97 */     Class mgrClass = ObjectFactory.lookUpFactoryClass("org.apache.xalan.xsltc.dom.XSLTCDTMManager", null, "org.apache.xalan.xsltc.dom.XSLTCDTMManager");
/*  38:    */     
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:103 */     return XSLTCDTMManager.class;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing)
/*  47:    */   {
/*  48:131 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, true, false);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean buildIdIndex)
/*  52:    */   {
/*  53:161 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, buildIdIndex, false);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean buildIdIndex, boolean newNameTable)
/*  57:    */   {
/*  58:194 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, false, 0, buildIdIndex, newNameTable);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean hasUserReader, int size, boolean buildIdIndex)
/*  62:    */   {
/*  63:230 */     return getDTM(source, unique, whiteSpaceFilter, incremental, doIndexing, hasUserReader, size, buildIdIndex, false);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing, boolean hasUserReader, int size, boolean buildIdIndex, boolean newNameTable)
/*  67:    */   {
/*  68:275 */     int dtmPos = getFirstFreeDTMID();
/*  69:276 */     int documentID = dtmPos << 16;
/*  70:278 */     if ((null != source) && ((source instanceof DOMSource)))
/*  71:    */     {
/*  72:280 */       DOMSource domsrc = (DOMSource)source;
/*  73:281 */       Node node = domsrc.getNode();
/*  74:282 */       DOM2SAX dom2sax = new DOM2SAX(node);
/*  75:    */       SAXImpl dtm;
/*  76:286 */       if (size <= 0) {
/*  77:287 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, 512, buildIdIndex, newNameTable);
/*  78:    */       } else {
/*  79:292 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, size, buildIdIndex, newNameTable);
/*  80:    */       }
/*  81:297 */       dtm.setDocumentURI(source.getSystemId());
/*  82:    */       
/*  83:299 */       addDTM(dtm, dtmPos, 0);
/*  84:    */       
/*  85:301 */       dom2sax.setContentHandler(dtm);
/*  86:    */       try
/*  87:    */       {
/*  88:304 */         dom2sax.parse();
/*  89:    */       }
/*  90:    */       catch (RuntimeException re)
/*  91:    */       {
/*  92:307 */         throw re;
/*  93:    */       }
/*  94:    */       catch (Exception e)
/*  95:    */       {
/*  96:310 */         throw new WrappedRuntimeException(e);
/*  97:    */       }
/*  98:313 */       return dtm;
/*  99:    */     }
/* 100:317 */     boolean isSAXSource = null != source ? source instanceof SAXSource : true;
/* 101:    */     
/* 102:319 */     boolean isStreamSource = null != source ? source instanceof StreamSource : false;
/* 103:322 */     if ((isSAXSource) || (isStreamSource))
/* 104:    */     {
/* 105:    */       InputSource xmlSource;
/* 106:    */       XMLReader reader;
/* 107:326 */       if (null == source)
/* 108:    */       {
/* 109:327 */         xmlSource = null;
/* 110:328 */         reader = null;
/* 111:329 */         hasUserReader = false;
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:332 */         reader = getXMLReader(source);
/* 116:333 */         xmlSource = SAXSource.sourceToInputSource(source);
/* 117:    */         
/* 118:335 */         String urlOfSource = xmlSource.getSystemId();
/* 119:337 */         if (null != urlOfSource)
/* 120:    */         {
/* 121:    */           try
/* 122:    */           {
/* 123:339 */             urlOfSource = SystemIDResolver.getAbsoluteURI(urlOfSource);
/* 124:    */           }
/* 125:    */           catch (Exception e)
/* 126:    */           {
/* 127:343 */             System.err.println("Can not absolutize URL: " + urlOfSource);
/* 128:    */           }
/* 129:346 */           xmlSource.setSystemId(urlOfSource);
/* 130:    */         }
/* 131:    */       }
/* 132:    */       SAXImpl dtm;
/* 133:352 */       if (size <= 0) {
/* 134:353 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, 512, buildIdIndex, newNameTable);
/* 135:    */       } else {
/* 136:358 */         dtm = new SAXImpl(this, source, documentID, whiteSpaceFilter, null, doIndexing, size, buildIdIndex, newNameTable);
/* 137:    */       }
/* 138:365 */       addDTM(dtm, dtmPos, 0);
/* 139:367 */       if (null == reader) {
/* 140:369 */         return dtm;
/* 141:    */       }
/* 142:372 */       reader.setContentHandler(dtm.getBuilder());
/* 143:374 */       if ((!hasUserReader) || (null == reader.getDTDHandler())) {
/* 144:375 */         reader.setDTDHandler(dtm);
/* 145:    */       }
/* 146:378 */       if ((!hasUserReader) || (null == reader.getErrorHandler())) {
/* 147:379 */         reader.setErrorHandler(dtm);
/* 148:    */       }
/* 149:    */       try
/* 150:    */       {
/* 151:383 */         reader.setProperty("http://xml.org/sax/properties/lexical-handler", dtm);
/* 152:    */       }
/* 153:    */       catch (SAXNotRecognizedException e) {}catch (SAXNotSupportedException e) {}
/* 154:    */       try
/* 155:    */       {
/* 156:389 */         reader.parse(xmlSource);
/* 157:    */       }
/* 158:    */       catch (RuntimeException re)
/* 159:    */       {
/* 160:392 */         throw re;
/* 161:    */       }
/* 162:    */       catch (Exception e)
/* 163:    */       {
/* 164:395 */         throw new WrappedRuntimeException(e);
/* 165:    */       }
/* 166:    */       finally
/* 167:    */       {
/* 168:397 */         if (!hasUserReader) {
/* 169:398 */           releaseXMLReader(reader);
/* 170:    */         }
/* 171:    */       }
/* 172:407 */       return dtm;
/* 173:    */     }
/* 174:412 */     throw new DTMException(XMLMessages.createXMLMessage("ER_NOT_SUPPORTED", new Object[] { source }));
/* 175:    */   }
/* 176:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.XSLTCDTMManager
 * JD-Core Version:    0.7.0.1
 */