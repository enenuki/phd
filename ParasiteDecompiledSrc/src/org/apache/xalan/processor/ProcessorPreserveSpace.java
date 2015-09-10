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
/* 11:   */ class ProcessorPreserveSpace
/* 12:   */   extends XSLTElementProcessor
/* 13:   */ {
/* 14:   */   static final long serialVersionUID = -5552836470051177302L;
/* 15:   */   
/* 16:   */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/* 17:   */     throws SAXException
/* 18:   */   {
/* 19:63 */     Stylesheet thisSheet = handler.getStylesheet();
/* 20:64 */     WhitespaceInfoPaths paths = new WhitespaceInfoPaths(thisSheet);
/* 21:65 */     setPropertiesFromAttributes(handler, rawName, attributes, paths);
/* 22:   */     
/* 23:67 */     Vector xpaths = paths.getElements();
/* 24:69 */     for (int i = 0; i < xpaths.size(); i++)
/* 25:   */     {
/* 26:71 */       WhiteSpaceInfo wsi = new WhiteSpaceInfo((XPath)xpaths.elementAt(i), false, thisSheet);
/* 27:72 */       wsi.setUid(handler.nextUid());
/* 28:   */       
/* 29:74 */       thisSheet.setPreserveSpaces(wsi);
/* 30:   */     }
/* 31:76 */     paths.clearElements();
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorPreserveSpace
 * JD-Core Version:    0.7.0.1
 */