/*   1:    */ package org.apache.xml.dtm;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.Source;
/*   4:    */ import org.apache.xml.res.XMLMessages;
/*   5:    */ import org.apache.xml.utils.PrefixResolver;
/*   6:    */ import org.apache.xml.utils.XMLStringFactory;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ 
/*   9:    */ public abstract class DTMManager
/*  10:    */ {
/*  11:    */   private static final String defaultPropName = "org.apache.xml.dtm.DTMManager";
/*  12: 56 */   private static String defaultClassName = "org.apache.xml.dtm.ref.DTMManagerDefault";
/*  13: 63 */   protected XMLStringFactory m_xsf = null;
/*  14:    */   
/*  15:    */   public XMLStringFactory getXMLStringFactory()
/*  16:    */   {
/*  17: 78 */     return this.m_xsf;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setXMLStringFactory(XMLStringFactory xsf)
/*  21:    */   {
/*  22: 89 */     this.m_xsf = xsf;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static DTMManager newInstance(XMLStringFactory xsf)
/*  26:    */     throws DTMConfigurationException
/*  27:    */   {
/*  28:134 */     DTMManager factoryImpl = null;
/*  29:    */     try
/*  30:    */     {
/*  31:137 */       factoryImpl = (DTMManager)ObjectFactory.createObject("org.apache.xml.dtm.DTMManager", defaultClassName);
/*  32:    */     }
/*  33:    */     catch (ObjectFactory.ConfigurationError e)
/*  34:    */     {
/*  35:142 */       throw new DTMConfigurationException(XMLMessages.createXMLMessage("ER_NO_DEFAULT_IMPL", null), e.getException());
/*  36:    */     }
/*  37:147 */     if (factoryImpl == null) {
/*  38:149 */       throw new DTMConfigurationException(XMLMessages.createXMLMessage("ER_NO_DEFAULT_IMPL", null));
/*  39:    */     }
/*  40:154 */     factoryImpl.setXMLStringFactory(xsf);
/*  41:    */     
/*  42:156 */     return factoryImpl;
/*  43:    */   }
/*  44:    */   
/*  45:289 */   public boolean m_incremental = false;
/*  46:297 */   public boolean m_source_location = false;
/*  47:    */   private static boolean debug;
/*  48:    */   public static final int IDENT_DTM_NODE_BITS = 16;
/*  49:    */   public static final int IDENT_NODE_DEFAULT = 65535;
/*  50:    */   public static final int IDENT_DTM_DEFAULT = -65536;
/*  51:    */   public static final int IDENT_MAX_DTMS = 65536;
/*  52:    */   
/*  53:    */   public boolean getIncremental()
/*  54:    */   {
/*  55:306 */     return this.m_incremental;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIncremental(boolean incremental)
/*  59:    */   {
/*  60:319 */     this.m_incremental = incremental;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean getSource_location()
/*  64:    */   {
/*  65:331 */     return this.m_source_location;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setSource_location(boolean sourceLocation)
/*  69:    */   {
/*  70:344 */     this.m_source_location = sourceLocation;
/*  71:    */   }
/*  72:    */   
/*  73:    */   static
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:359 */       debug = System.getProperty("dtm.debug") != null;
/*  78:    */     }
/*  79:    */     catch (SecurityException ex) {}
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getDTMIdentityMask()
/*  83:    */   {
/*  84:417 */     return -65536;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getNodeIdentityMask()
/*  88:    */   {
/*  89:427 */     return 65535;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public abstract DTM getDTM(Source paramSource, boolean paramBoolean1, DTMWSFilter paramDTMWSFilter, boolean paramBoolean2, boolean paramBoolean3);
/*  93:    */   
/*  94:    */   public abstract DTM getDTM(int paramInt);
/*  95:    */   
/*  96:    */   public abstract int getDTMHandleFromNode(Node paramNode);
/*  97:    */   
/*  98:    */   public abstract DTM createDocumentFragment();
/*  99:    */   
/* 100:    */   public abstract boolean release(DTM paramDTM, boolean paramBoolean);
/* 101:    */   
/* 102:    */   public abstract DTMIterator createDTMIterator(Object paramObject, int paramInt);
/* 103:    */   
/* 104:    */   public abstract DTMIterator createDTMIterator(String paramString, PrefixResolver paramPrefixResolver);
/* 105:    */   
/* 106:    */   public abstract DTMIterator createDTMIterator(int paramInt, DTMFilter paramDTMFilter, boolean paramBoolean);
/* 107:    */   
/* 108:    */   public abstract DTMIterator createDTMIterator(int paramInt);
/* 109:    */   
/* 110:    */   public abstract int getDTMIdentity(DTM paramDTM);
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMManager
 * JD-Core Version:    0.7.0.1
 */