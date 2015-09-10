/*  1:   */ package org.apache.xalan.xsltc.trax;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ 
/*  5:   */ public final class OutputSettings
/*  6:   */ {
/*  7:31 */   private String _cdata_section_elements = null;
/*  8:32 */   private String _doctype_public = null;
/*  9:33 */   private String _encoding = null;
/* 10:34 */   private String _indent = null;
/* 11:35 */   private String _media_type = null;
/* 12:36 */   private String _method = null;
/* 13:37 */   private String _omit_xml_declaration = null;
/* 14:38 */   private String _standalone = null;
/* 15:39 */   private String _version = null;
/* 16:   */   
/* 17:   */   public Properties getProperties()
/* 18:   */   {
/* 19:42 */     Properties properties = new Properties();
/* 20:43 */     return properties;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.OutputSettings
 * JD-Core Version:    0.7.0.1
 */