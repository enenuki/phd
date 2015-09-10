/*  1:   */ package org.apache.xalan.trace;
/*  2:   */ 
/*  3:   */ import java.util.EventListener;
/*  4:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  5:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  6:   */ import org.apache.xpath.XPath;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ import org.w3c.dom.Node;
/*  9:   */ 
/* 10:   */ public class SelectionEvent
/* 11:   */   implements EventListener
/* 12:   */ {
/* 13:   */   public final ElemTemplateElement m_styleNode;
/* 14:   */   public final TransformerImpl m_processor;
/* 15:   */   public final Node m_sourceNode;
/* 16:   */   public final String m_attributeName;
/* 17:   */   public final XPath m_xpath;
/* 18:   */   public final XObject m_selection;
/* 19:   */   
/* 20:   */   public SelectionEvent(TransformerImpl processor, Node sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection)
/* 21:   */   {
/* 22:83 */     this.m_processor = processor;
/* 23:84 */     this.m_sourceNode = sourceNode;
/* 24:85 */     this.m_styleNode = styleNode;
/* 25:86 */     this.m_attributeName = attributeName;
/* 26:87 */     this.m_xpath = xpath;
/* 27:88 */     this.m_selection = selection;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.SelectionEvent
 * JD-Core Version:    0.7.0.1
 */