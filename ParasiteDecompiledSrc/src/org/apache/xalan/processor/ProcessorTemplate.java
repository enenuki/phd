/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplate;
/*  4:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.xml.sax.SAXException;
/*  7:   */ 
/*  8:   */ class ProcessorTemplate
/*  9:   */   extends ProcessorTemplateElem
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = -8457812845473603860L;
/* 12:   */   
/* 13:   */   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem)
/* 14:   */     throws SAXException
/* 15:   */   {
/* 16:49 */     super.appendAndPush(handler, elem);
/* 17:50 */     elem.setDOMBackPointer(handler.getOriginatingNode());
/* 18:51 */     handler.getStylesheet().setTemplate((ElemTemplate)elem);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorTemplate
 * JD-Core Version:    0.7.0.1
 */