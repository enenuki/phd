/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemExsltFuncResult;
/*  4:   */ import org.apache.xalan.templates.ElemExsltFunction;
/*  5:   */ import org.apache.xalan.templates.ElemParam;
/*  6:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  7:   */ import org.apache.xalan.templates.ElemVariable;
/*  8:   */ import org.xml.sax.Attributes;
/*  9:   */ import org.xml.sax.SAXException;
/* 10:   */ 
/* 11:   */ public class ProcessorExsltFuncResult
/* 12:   */   extends ProcessorTemplateElem
/* 13:   */ {
/* 14:   */   static final long serialVersionUID = 6451230911473482423L;
/* 15:   */   
/* 16:   */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/* 17:   */     throws SAXException
/* 18:   */   {
/* 19:49 */     String msg = "";
/* 20:   */     
/* 21:51 */     super.startElement(handler, uri, localName, rawName, attributes);
/* 22:52 */     ElemTemplateElement ancestor = handler.getElemTemplateElement().getParentElem();
/* 23:53 */     while ((ancestor != null) && (!(ancestor instanceof ElemExsltFunction)))
/* 24:   */     {
/* 25:55 */       if (((ancestor instanceof ElemVariable)) || ((ancestor instanceof ElemParam)) || ((ancestor instanceof ElemExsltFuncResult)))
/* 26:   */       {
/* 27:59 */         msg = "func:result cannot appear within a variable, parameter, or another func:result.";
/* 28:60 */         handler.error(msg, new SAXException(msg));
/* 29:   */       }
/* 30:62 */       ancestor = ancestor.getParentElem();
/* 31:   */     }
/* 32:64 */     if (ancestor == null)
/* 33:   */     {
/* 34:66 */       msg = "func:result must appear in a func:function element";
/* 35:67 */       handler.error(msg, new SAXException(msg));
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorExsltFuncResult
 * JD-Core Version:    0.7.0.1
 */