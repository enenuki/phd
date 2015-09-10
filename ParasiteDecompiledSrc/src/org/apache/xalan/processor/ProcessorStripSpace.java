/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.apache.xalan.templates.WhiteSpaceInfo;
/*  7:   */ import org.apache.xpath.XPath;
/*  8:   */ import org.xml.sax.Attributes;
/*  9:   */ import org.xml.sax.SAXException;
/* 10:   */ 
/* 11:   */ class ProcessorStripSpace
/* 12:   */   extends ProcessorPreserveSpace
/* 13:   */ {
/* 14:   */   static final long serialVersionUID = -5594493198637899591L;
/* 15:   */   
/* 16:   */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/* 17:   */     throws SAXException
/* 18:   */   {
/* 19:62 */     Stylesheet thisSheet = handler.getStylesheet();
/* 20:63 */     WhitespaceInfoPaths paths = new WhitespaceInfoPaths(thisSheet);
/* 21:64 */     setPropertiesFromAttributes(handler, rawName, attributes, paths);
/* 22:   */     
/* 23:66 */     Vector xpaths = paths.getElements();
/* 24:68 */     for (int i = 0; i < xpaths.size(); i++)
/* 25:   */     {
/* 26:70 */       WhiteSpaceInfo wsi = new WhiteSpaceInfo((XPath)xpaths.elementAt(i), true, thisSheet);
/* 27:71 */       wsi.setUid(handler.nextUid());
/* 28:   */       
/* 29:73 */       thisSheet.setStripSpaces(wsi);
/* 30:   */     }
/* 31:75 */     paths.clearElements();
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorStripSpace
 * JD-Core Version:    0.7.0.1
 */