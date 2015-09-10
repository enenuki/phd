/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import javax.xml.parsers.FactoryConfigurationError;
/*   5:    */ import javax.xml.parsers.ParserConfigurationException;
/*   6:    */ import javax.xml.parsers.SAXParser;
/*   7:    */ import javax.xml.parsers.SAXParserFactory;
/*   8:    */ import org.xml.sax.SAXException;
/*   9:    */ import org.xml.sax.XMLReader;
/*  10:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  11:    */ 
/*  12:    */ public class XMLReaderManager
/*  13:    */ {
/*  14:    */   private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
/*  15:    */   private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
/*  16: 43 */   private static final XMLReaderManager m_singletonManager = new XMLReaderManager();
/*  17:    */   private static SAXParserFactory m_parserFactory;
/*  18:    */   private ThreadLocal m_readers;
/*  19:    */   private Hashtable m_inUse;
/*  20:    */   
/*  21:    */   public static XMLReaderManager getInstance()
/*  22:    */   {
/*  23: 71 */     return m_singletonManager;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public synchronized XMLReader getXMLReader()
/*  27:    */     throws SAXException
/*  28:    */   {
/*  29: 84 */     if (this.m_readers == null) {
/*  30: 87 */       this.m_readers = new ThreadLocal();
/*  31:    */     }
/*  32: 90 */     if (this.m_inUse == null) {
/*  33: 91 */       this.m_inUse = new Hashtable();
/*  34:    */     }
/*  35: 96 */     XMLReader reader = (XMLReader)this.m_readers.get();
/*  36: 97 */     boolean threadHasReader = reader != null;
/*  37: 98 */     if ((!threadHasReader) || (this.m_inUse.get(reader) == Boolean.TRUE))
/*  38:    */     {
/*  39:    */       try
/*  40:    */       {
/*  41:    */         try
/*  42:    */         {
/*  43:105 */           reader = XMLReaderFactory.createXMLReader();
/*  44:    */         }
/*  45:    */         catch (Exception e)
/*  46:    */         {
/*  47:    */           try
/*  48:    */           {
/*  49:110 */             if (m_parserFactory == null)
/*  50:    */             {
/*  51:111 */               m_parserFactory = SAXParserFactory.newInstance();
/*  52:112 */               m_parserFactory.setNamespaceAware(true);
/*  53:    */             }
/*  54:115 */             reader = m_parserFactory.newSAXParser().getXMLReader();
/*  55:    */           }
/*  56:    */           catch (ParserConfigurationException pce)
/*  57:    */           {
/*  58:117 */             throw pce;
/*  59:    */           }
/*  60:    */         }
/*  61:    */         try
/*  62:    */         {
/*  63:121 */           reader.setFeature("http://xml.org/sax/features/namespaces", true);
/*  64:122 */           reader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/*  65:    */         }
/*  66:    */         catch (SAXException se) {}
/*  67:    */       }
/*  68:    */       catch (ParserConfigurationException ex)
/*  69:    */       {
/*  70:128 */         throw new SAXException(ex);
/*  71:    */       }
/*  72:    */       catch (FactoryConfigurationError ex1)
/*  73:    */       {
/*  74:130 */         throw new SAXException(ex1.toString());
/*  75:    */       }
/*  76:    */       catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  77:137 */       if (!threadHasReader)
/*  78:    */       {
/*  79:138 */         this.m_readers.set(reader);
/*  80:139 */         this.m_inUse.put(reader, Boolean.TRUE);
/*  81:    */       }
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:142 */       this.m_inUse.put(reader, Boolean.TRUE);
/*  86:    */     }
/*  87:145 */     return reader;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public synchronized void releaseXMLReader(XMLReader reader)
/*  91:    */   {
/*  92:157 */     if ((this.m_readers.get() == reader) && (reader != null)) {
/*  93:158 */       this.m_inUse.remove(reader);
/*  94:    */     }
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLReaderManager
 * JD-Core Version:    0.7.0.1
 */