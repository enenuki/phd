/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  4:   */ import org.apache.xalan.templates.ElemText;
/*  5:   */ import org.xml.sax.SAXException;
/*  6:   */ 
/*  7:   */ public class ProcessorText
/*  8:   */   extends ProcessorTemplateElem
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = 5170229307201307523L;
/* 11:   */   
/* 12:   */   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem)
/* 13:   */     throws SAXException
/* 14:   */   {
/* 15:52 */     ProcessorCharacters charProcessor = (ProcessorCharacters)handler.getProcessorFor(null, "text()", "text");
/* 16:   */     
/* 17:   */ 
/* 18:55 */     charProcessor.setXslTextElement((ElemText)elem);
/* 19:   */     
/* 20:57 */     ElemTemplateElement parent = handler.getElemTemplateElement();
/* 21:   */     
/* 22:59 */     parent.appendChild(elem);
/* 23:60 */     elem.setDOMBackPointer(handler.getOriginatingNode());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/* 27:   */     throws SAXException
/* 28:   */   {
/* 29:76 */     ProcessorCharacters charProcessor = (ProcessorCharacters)handler.getProcessorFor(null, "text()", "text");
/* 30:   */     
/* 31:   */ 
/* 32:79 */     charProcessor.setXslTextElement(null);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorText
 * JD-Core Version:    0.7.0.1
 */