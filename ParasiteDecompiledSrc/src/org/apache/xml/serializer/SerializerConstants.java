/*  1:   */ package org.apache.xml.serializer;
/*  2:   */ 
/*  3:   */ abstract interface SerializerConstants
/*  4:   */ {
/*  5:   */   public static final String CDATA_CONTINUE = "]]]]><![CDATA[>";
/*  6:   */   public static final String CDATA_DELIMITER_CLOSE = "]]>";
/*  7:   */   public static final String CDATA_DELIMITER_OPEN = "<![CDATA[";
/*  8:   */   public static final String EMPTYSTRING = "";
/*  9:   */   public static final String ENTITY_AMP = "&amp;";
/* 10:   */   public static final String ENTITY_CRLF = "&#xA;";
/* 11:   */   public static final String ENTITY_GT = "&gt;";
/* 12:   */   public static final String ENTITY_LT = "&lt;";
/* 13:   */   public static final String ENTITY_QUOT = "&quot;";
/* 14:   */   public static final String XML_PREFIX = "xml";
/* 15:   */   public static final String XMLNS_PREFIX = "xmlns";
/* 16:   */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/* 17:52 */   public static final String DEFAULT_SAX_SERIALIZER = SerializerBase.PKG_NAME + ".ToXMLSAXHandler";
/* 18:   */   public static final String XMLVERSION11 = "1.1";
/* 19:   */   public static final String XMLVERSION10 = "1.0";
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SerializerConstants
 * JD-Core Version:    0.7.0.1
 */