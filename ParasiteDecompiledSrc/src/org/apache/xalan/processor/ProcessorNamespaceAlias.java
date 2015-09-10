/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  4:   */ import org.apache.xalan.templates.NamespaceAlias;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.xml.sax.Attributes;
/*  7:   */ import org.xml.sax.SAXException;
/*  8:   */ 
/*  9:   */ class ProcessorNamespaceAlias
/* 10:   */   extends XSLTElementProcessor
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -6309867839007018964L;
/* 13:   */   
/* 14:   */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/* 15:   */     throws SAXException
/* 16:   */   {
/* 17:66 */     NamespaceAlias na = new NamespaceAlias(handler.nextUid());
/* 18:   */     
/* 19:68 */     setPropertiesFromAttributes(handler, rawName, attributes, na);
/* 20:69 */     String prefix = na.getStylesheetPrefix();
/* 21:70 */     if (prefix.equals("#default"))
/* 22:   */     {
/* 23:72 */       prefix = "";
/* 24:73 */       na.setStylesheetPrefix(prefix);
/* 25:   */     }
/* 26:75 */     String stylesheetNS = handler.getNamespaceForPrefix(prefix);
/* 27:76 */     na.setStylesheetNamespace(stylesheetNS);
/* 28:77 */     prefix = na.getResultPrefix();
/* 29:   */     String resultNS;
/* 30:78 */     if (prefix.equals("#default"))
/* 31:   */     {
/* 32:80 */       prefix = "";
/* 33:81 */       na.setResultPrefix(prefix);
/* 34:82 */       resultNS = handler.getNamespaceForPrefix(prefix);
/* 35:83 */       if (null == resultNS) {
/* 36:84 */         handler.error("ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", null, null);
/* 37:   */       }
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:88 */       resultNS = handler.getNamespaceForPrefix(prefix);
/* 42:89 */       if (null == resultNS) {
/* 43:90 */         handler.error("ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", new Object[] { prefix }, null);
/* 44:   */       }
/* 45:   */     }
/* 46:93 */     na.setResultNamespace(resultNS);
/* 47:94 */     handler.getStylesheet().setNamespaceAlias(na);
/* 48:95 */     handler.getStylesheet().appendChild(na);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorNamespaceAlias
 * JD-Core Version:    0.7.0.1
 */