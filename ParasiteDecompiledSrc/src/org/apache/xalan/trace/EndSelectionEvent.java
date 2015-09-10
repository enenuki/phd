/*  1:   */ package org.apache.xalan.trace;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  4:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  5:   */ import org.apache.xpath.XPath;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.w3c.dom.Node;
/*  8:   */ 
/*  9:   */ public class EndSelectionEvent
/* 10:   */   extends SelectionEvent
/* 11:   */ {
/* 12:   */   public EndSelectionEvent(TransformerImpl processor, Node sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection)
/* 13:   */   {
/* 14:54 */     super(processor, sourceNode, styleNode, attributeName, xpath, selection);
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.EndSelectionEvent
 * JD-Core Version:    0.7.0.1
 */