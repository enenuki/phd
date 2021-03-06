/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  4:   */ import org.apache.xalan.templates.ElemVariable;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.xml.sax.SAXException;
/*  7:   */ 
/*  8:   */ class ProcessorGlobalVariableDecl
/*  9:   */   extends ProcessorTemplateElem
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = -5954332402269819582L;
/* 12:   */   
/* 13:   */   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem)
/* 14:   */     throws SAXException
/* 15:   */   {
/* 16:52 */     handler.pushElemTemplateElement(elem);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/* 20:   */     throws SAXException
/* 21:   */   {
/* 22:71 */     ElemVariable v = (ElemVariable)handler.getElemTemplateElement();
/* 23:   */     
/* 24:73 */     handler.getStylesheet().appendChild(v);
/* 25:74 */     handler.getStylesheet().setVariable(v);
/* 26:75 */     super.endElement(handler, uri, localName, rawName);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorGlobalVariableDecl
 * JD-Core Version:    0.7.0.1
 */